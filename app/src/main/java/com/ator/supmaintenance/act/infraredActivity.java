package com.ator.supmaintenance.act;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.Byte2float;
import com.ator.supmaintenance.item.Constant;
import com.ator.supmaintenance.item.DataTransform;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.MyPhoto;
import com.ator.supmaintenance.item.SupCardData;
import com.ator.supmaintenance.item.SupCheckPointData;
import com.ator.supmaintenance.item.SupInspectionTask;
import com.ator.supmaintenance.item.SupInspectionTaskMgr;

import com.ator.supmaintenance.item.SupTempTask;
import com.ator.supmaintenance.item.TempTaskManager;

import java.lang.reflect.Method;
import java.util.List;

public class infraredActivity extends BaseActivity implements View.OnClickListener {

    private TextView        mtvTitle;
    private TextView        mtvUnit;


    private TextView        mtvValue;
    private TextView        mtvEquipStatus;
    private EditText        medMemo;
    private ImageView       mivAdd;
    private Bitmap          mBmp = null;

    private TextView    mtvCPDes;

    private String taskid;
    private String cardid;
    private String cpid;


    //    private OptionsPickerView pvNoLinkOptions;

    private boolean mIsGood = true;

    private String          mStrInputType;
////////////////////////////////////////////////////////////////////////

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private boolean                 mbIsFound = false;

    private BluetoothGattService mBluetoothGattService;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;

    public  int             mCommIndex=1;
/////////////////////////////////////////////////////////////////////

    public void initBlueTooth(){

        mtvEquipStatus.setText("正在初始化，请确保设备打开（黄灯闪烁）");
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        boolean brt = mBluetoothAdapter.enable();
        if (brt)    {
            int nScanMode = mBluetoothAdapter.getScanMode();
            if (nScanMode == BluetoothAdapter.SCAN_MODE_NONE){
                setDiscoverableTimeout(500);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothAdapter.startLeScan(mLeScanCallback);
                    }
                }, 1500);//3秒后执行Runnable中的run方法

            }else {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }

        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                    if(device != null){
                        if(device.getName()!=null){
                            String strName = device.getName();
                            Log.i("mLeScanCallback","\n\r  onLeScan :  " + strName + "\n\r");
                            if(strName.contains("T805i") && !mbIsFound){
                                mDevice = mBluetoothAdapter.getRemoteDevice(device.getAddress());
                                Message msg = new Message();
                                msg.what = Constant.WM_BLE_CONNECT;
                                mHandler.sendMessage(msg);
                                mbIsFound = true;
                            }
                        }
                    }

                }
            };

    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback(){
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Message msg=new Message();
                msg.what=Constant.WM_BLE_CONNECTED_STATE_CHANGE;
                mHandler.sendMessage(msg);
                //发现服务
                mBluetoothGatt.discoverServices();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        judgeEquip(mDevice);
                    }
                });

            }
            else if (newState == BluetoothProfile.STATE_DISCONNECTED)
            {
                mBluetoothGatt.close();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showServices(mBluetoothGatt);
                    //Toast.makeText(IndexActivity.this,"开始连接",Toast.LENGTH_LONG);
                    startConnect();
                }
            });
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
        }


        private boolean myEqualBytes(byte[] by1,byte[] by2,int size){

            if (by1.length <size)
                return  false;

            if (by2.length <size)
                return false;

            for (int i=0;i<size;i++)
            {
                if (by1[i] != by2[i])   return  false;
            }

            return true;
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            //温度为下标 10、11、12、13字节
            //head:65 6d 70 65 72 61 74 75 72 65  hex
            byte[]  byGet = null;
            byte[]  byHead_noraml = {0x65,0x6d,0x70,0x65,0x72,0x61,0x74,0x75,0x72,0x65};
            //绿灯闪等待 03 00 00 00 00 00 00 28
            byte[]  byWait = {0x03,0x00,0x00,0x00,0x00,0x00,0x00,0x28};
            //测温后保持  69 63 6b 00 3b 28
            byte[]  bykeep = {0x69,0x63,0x6b,0x00,0x3b,0x28};

            byGet = characteristic.getValue();
            if (myEqualBytes(byWait,byGet,byWait.length) == true){
                mtvEquipStatus.setText("设备就绪，可按下测温");
            }else if (myEqualBytes(bykeep,byGet,bykeep.length) == true){
                mtvEquipStatus.setText("值已保持，可确认提交");
            }else if (myEqualBytes(byHead_noraml,byGet,byHead_noraml.length) == true)
            {
                float fV = 0.0f;
                fV = Byte2float.byte2float(byGet,10);
                String strV = String.format("%.2f",fV);
                mtvEquipStatus.setText("测温中");
                mtvValue.setText(strV);
            }
            else
            {
                mtvEquipStatus.setText("就绪");
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });

          }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            /**
             * 开始写入
             */
            byte[] writeBytes = DataTransform.hexStringToByte("5600030000000c69023e81");
            mBluetoothGattCharacteristic.setValue(writeBytes);
            boolean b = mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);

        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            byte[] writeBytes = DataTransform.hexStringToByte("5600030000000c69023e81");

            switch (mCommIndex){
                case 0:
                    break;
                case 1:
                    writeBytes = DataTransform.hexStringToByte("200000000000077b");
                    break;
                case 2:
                    writeBytes = DataTransform.hexStringToByte("04001500000005930f0000004669726d77617265");
                    break;
                case 3:
                    writeBytes = DataTransform.hexStringToByte("56657273696f6e304f");
                    break;
                case 4:
                    writeBytes = DataTransform.hexStringToByte("04001500000005930f0000004669726d77617265");
                    break;
                case 5:
                    writeBytes = DataTransform.hexStringToByte("56657273696f6e304f");
                    break;
                case 6:
                    writeBytes = DataTransform.hexStringToByte("110000000000035a");
                    break;
                case 7:
                    writeBytes = DataTransform.hexStringToByte("05001a0000000756100000");
                    break;
                case 8:
                    writeBytes = DataTransform.hexStringToByte("456d697373696f6e3333733f0471");
                    break;
                default:
                    return;
            }

            mCommIndex++;
            mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic,true);
            mBluetoothGattCharacteristic.setValue(writeBytes);
            mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null && mLeScanCallback != null) {
            try {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                mBluetoothAdapter.cancelDiscovery();
                mBluetoothGatt.disconnect();
                mBluetoothGatt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static final int REQUEST_BLUETOOTH_PERMISSION=10;

    //处理所有网络请求结果
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage (Message mes){

            switch(mes.what){
                case Constant.WM_BLE_CONNECT:
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mBluetoothGatt = mDevice.connectGatt(infraredActivity.this, true, mBluetoothGattCallback);
                    //refreshDeviceCache(mBluetoothGatt);
                    mBluetoothGatt.connect();
                    break;
                case Constant.WM_BLE_CONNECTED_STATE_CHANGE:
                    break;
                case Constant.WM_BLE_ONCHARACTERISTCCHANGED:
                    break;
                case Constant.WM_BLE_ONDESCRIPTOR_WRITE:
                    break;
                case Constant.WM_BLE_ONSERVICE_DISCOVERED:
                    break;
                default:
                    break;
            }
        }
    };




    public boolean judgeEquip(BluetoothDevice mDevice){

        String strName = mDevice.getName();
        int pos = strName.indexOf(Constant.TEMP_EQUIP_NAME);
        if (pos<=0){
            //not 805i
            return false;
        }else {
            mtvEquipStatus.setText("已发现设备：" + Constant.TEMP_EQUIP_NAME + "正在连接（等待黄灯转为绿灯）");
            return true;
        }

    }

    public void showServices(BluetoothGatt gatt){
        if(gatt.getServices() != null){
            for(BluetoothGattService gattService : gatt.getServices()){
                mBluetoothGattService = gattService;
//                Log.i(TAG,"Service类型：：：："+gattService.getType());
            }

        }
    }

    public void startConnect(){

        List<BluetoothGattCharacteristic> gattCharacteristics = mBluetoothGattService.getCharacteristics();

        /**
         * 0号特征为写入，1号特征为监听
         */
        BluetoothGattCharacteristic notify = gattCharacteristics.get(1);
        BluetoothGattDescriptor config = notify.getDescriptors().get(0);
//        mTextMessage.setText(message.toString());
        /**
         * 开启监听,成功后会闪绿灯
         */
        mBluetoothGatt.setCharacteristicNotification(notify,true);
        config.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(config);
        mBluetoothGatt.readCharacteristic(notify);

        mBluetoothGattCharacteristic = gattCharacteristics.get(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrared);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStrInputType = bundle.getString("type");

        mtvValue = (TextView) findViewById(R.id.tv_tempvalue);
        medMemo = (EditText)findViewById(R.id.et_memo);
        mivAdd = (ImageView)findViewById(R.id.iv_add1);
        mtvEquipStatus = (TextView) findViewById(R.id.tv_tempstatus);
        mtvTitle = (TextView)findViewById(R.id.tv_title);
        mtvUnit = (TextView)findViewById(R.id.tv_unit);

        mtvCPDes = (TextView)findViewById(R.id.tv_des);

        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.ll_good).setOnClickListener(this);
        findViewById(R.id.ll_bad).setOnClickListener(this);

        //initNoLinkOptionsPicker();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            mtvEquipStatus.setText("本机不支持BLE");
        }

        if (mStrInputType.equals(Constant.TASK_TYPE_NORMAL)) {

            SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
            SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);
            if (task == null){
                return;
            }
            SupCardData cardData = (SupCardData) task.getCardDatabyID(InspectionUtil.getInstance().mCurCardid);
            if (cardData == null){
                return;
            }
            SupCheckPointData cp = (SupCheckPointData) cardData.getCPDatabyID(InspectionUtil.getInstance().mCurCpid);
            if (cp == null){
                return;
            }

            taskid = task.taskid;
            cardid = cardData.cardid;
            cpid = cp.cpid;

            mtvTitle.setText(cp.mCPCfg.cpname);
            mtvUnit.setText(cp.mCPCfg.unit);
            mtvCPDes.setText(cp.mCPCfg.require);

        }
        else if (mStrInputType.equals(Constant.TASK_TYPE_TEMP)) {

            TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();
            int curTempIndex = InspectionUtil.getInstance().mCurTempTaskIndex;
            SupCheckPointData cp = (SupCheckPointData)(mgr.temptasks.get(curTempIndex));

            mtvTitle.setText(cp.mCPCfg.cpname);
            mtvUnit.setText(cp.mCPCfg.unit);
            mtvCPDes.setText(cp.mCPCfg.require);

        }

        initBlueTooth();
        PressBadGood(mIsGood);
        initBold();
    }


    private void initBold(){

        TextView tv;
        TextPaint paint;
        tv = (TextView)findViewById(R.id.tv_des);
        paint = tv.getPaint();
        paint.setFakeBoldText(true);

        tv = (TextView)findViewById(R.id.tv_append);
        paint = tv.getPaint();
        paint.setFakeBoldText(true);


    }


    @Override
    protected int initContentView() {
        return R.layout.activity_infrared;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void start() {

    }


    @Override
    protected void onPostSucc(){

    }

    @Override
    protected void onPostFaile(){

    }

    @Override
    protected void onGetSucc(){

    }

    @Override
    protected void onGetFaile(){

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }


    private void SubmitCheckPoint(){

        String strV = mtvValue.getText().toString().trim();
        if("".equals(strV)){
            return;
        }

        if(mStrInputType.equals(Constant.TASK_TYPE_NORMAL) ){

            SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
            SupInspectionTask task = mgr.getCurTaskbyID(taskid);

            if (task ==null){
                alertText("提示：","任务已过期");
                return;
            }

            SupCardData card = task.getCardDatabyID(cardid);
            if (card ==null){
                alertText("提示：","未找到设备");
                return;
            }

            SupCheckPointData cp = card.getCPDatabyID(cpid);
            if (cp == null){
                alertText("提示：","未找到检查点");
                return;
            }

            cp.isproblem = !mIsGood;

            float fV = Float.valueOf(strV);
            cp.setFValue(fV);

            cp.memo = medMemo.getText().toString();
            cp.done = true;

            if (mBmp != null) {
                cp.picfile1 = SupCheckPointData.makeImgFilePath(cp.mCPCfg.cpid);
                FileUtil.saveImgFile(this,mBmp,InspectionUtil.mStrPicSub,cp.picfile1);
            }

            boolean bCardDone = card.DoneCheck(cp);
            boolean bTaskDone = task.DoneCard(card);
            mgr.Donetask(task);

            String strUploadCP = "";
            if (bCardDone){
                strUploadCP = task.makeUploadJstring(card,cp);
            }else {
                strUploadCP = task.makeUploadJstring(null,cp);
            }

            if (bTaskDone){
                setResult(4);
            }else if (bCardDone){
                setResult(3);
            }else {
                setResult(2);
            }

            String url = SupInspectionTask.getTaskUploadURL();

            if (MyNetHelper.getNetWorkState(this) != MyNetHelper.NETWORK_NONE){
                uploadData(url,strUploadCP);
            }else{
                saveData(url,strUploadCP);
                alertText("提示","无网络，已保存在本机，待网络通畅后请在文件管理中上传本记录");
            }

        }
        else if (mStrInputType.equals(Constant.TASK_TYPE_TEMP))
        {
            TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();
            int curTempIndex = InspectionUtil.getInstance().mCurTempTaskIndex;
            SupTempTask cp = (SupTempTask)(mgr.temptasks.get(curTempIndex));

            cp.isproblem = !mIsGood;

            float fV = Float.valueOf(strV);
            cp.setFValue(fV);

            cp.memo = medMemo.getText().toString();
            cp.done = true;

            if (mBmp != null) {

                cp.picfile1 = SupCheckPointData.makeImgFilePath(cp.mCPCfg.cpid);
                FileUtil.saveImgFile(this,mBmp,InspectionUtil.mStrPicSub,cp.picfile1);

            }

            mgr.DoneTask(cp,curTempIndex);

        }

    }

    private void uploadData(String url,String data){

        postJasonString(url,data,5000,201,true,"上传","上传中...");

    }

    private void saveData(String url,String data){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //            Bundle extras = data.getExtras();
            //            mBmp = (Bitmap) extras.get("data");

            mBmp = MyPhoto.loadBmp(mFilePic);

            if (mBmp != null){
                mivAdd.setImageBitmap(mBmp);
            }

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_commit:
                SubmitCheckPoint();
                finish();
                break;
            case R.id.btn_camera:
                takePhoto();
                break;

            case R.id.im_back:
                finish();
                break;
            case R.id.iv_add1:
                if (mBmp != null)
                {
                    final Bitmap bmp = Bitmap.createBitmap(mBmp);
                    showCPImagDialog(bmp);
                }else {
                    takePhoto();
                }

                break;
            case R.id.ll_good:
                PressBadGood(true);
                break;
            case R.id.ll_bad:
                PressBadGood(false);
                break;
        }
    }


    private void PressBadGood(boolean isGood){

        mIsGood = isGood;
        if (mIsGood){
            findViewById(R.id.ll_good).setBackground(getResources().getDrawable(R.drawable.bg_goodleft_sel));
            findViewById(R.id.ll_bad).setBackground(getResources().getDrawable(R.drawable.bg_badright_unsel));
        }else {
            findViewById(R.id.ll_good).setBackground(getResources().getDrawable(R.drawable.bg_goodleft_unsel));
            findViewById(R.id.ll_bad).setBackground(getResources().getDrawable(R.drawable.bg_badright_sel));
        }

    }


    /**
     * 清理本地的BluetoothGatt 的缓存，以保证在蓝牙连接设备的时候，设备的服务、特征是最新的
     * @param gatt
     * @return
     */
    public boolean refreshDeviceCache(BluetoothGatt gatt) {
        if(null != gatt){
            try {
                BluetoothGatt localBluetoothGatt = gatt;
                Method localMethod = localBluetoothGatt.getClass().getMethod( "refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(
                            localBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
        return false;
    }

    public void setDiscoverableTimeout(int timeout) {
        BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode =BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
            setScanMode.setAccessible(true);

            setDiscoverableTimeout.invoke(adapter, timeout);
            setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE,timeout);
            Log.i("mLeScanCallback","\n\r  setDiscoverableTimeout :  \n\r");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDiscoverableTimeout() {
        BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode =BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
            setScanMode.setAccessible(true);

            setDiscoverableTimeout.invoke(adapter, 1);
            setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
