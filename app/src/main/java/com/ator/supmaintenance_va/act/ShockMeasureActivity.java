package com.ator.supmaintenance_va.act;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.item.Constant;
import com.ator.supmaintenance_va.item.FileUtil;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.MyNetHelper;
import com.ator.supmaintenance_va.item.MyPhoto;
import com.ator.supmaintenance_va.item.SupCheckPointData;
import com.ator.supmaintenance_va.item.SupInspectionTask;
import com.ator.supmaintenance_va.item.SupInspectionTaskMgr;
import com.ator.supmaintenance_va.item.SupCardData;
import com.ator.supmaintenance_va.item.SupTempTask;
import com.ator.supmaintenance_va.item.TempTaskManager;
import com.sytest.app.blemulti.BleDevice;
import com.sytest.app.blemulti.Rat;
import com.sytest.app.blemulti.easy.Recipe;
import com.sytest.app.blemulti.exception.BleException;
import com.sytest.app.blemulti.interfaces.Scan_CB;
import com.sytest.app.blemulti.interfaces.SucFail;
import com.sytest.app.blemulti.interfaces.Value_Tmp_CB;

public class ShockMeasureActivity extends BaseActivity implements View.OnClickListener  {

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

    private Handler mTimerHandler = null;

    //blemulti-release
    private BluetoothDevice mfirstDD = null;
    private float mFV;
    private boolean mbGet = false;

    //scan callback
    private Scan_CB scanCallback = new Scan_CB() {
        @Override
        public void onLeScan(BluetoothDevice device) {
            if (!TextUtils.isEmpty(device.getName())) {
                String strName = device.getName().trim().toLowerCase();
                if (strName.lastIndexOf("su-100") >=0) {
                    mfirstDD = device;
                    mtvEquipStatus.setText(String.format("检测到设备%s，正在连接（等待蓝灯与绿灯闪烁）",device.getName()));
                    Rat.getInstance().stopScan();

                    Rat.getInstance().connectDevice_Normal(device.getAddress(),mConSF);
                }
            }
        }
    };

    //getvalue callback
    private Value_Tmp_CB mValueCB = new Value_Tmp_CB(){
        @Override
        public void onValue_UI(float x, float y, float z, float tmp) {
            String strout = String.format("%.5f",z);
            mtvValue.setText(strout);
            mFV = z;
            mbGet = true;
        }
        @Override
        public void onFail_UI(@NonNull BleException e) {

        }
    };

    //connect callback
    private SucFail mConSF = new SucFail() {
        @Override
        public void onSucceed_UI(@Nullable String s) {
            String strout = mfirstDD.getName() + "连接成功";
            mtvEquipStatus.setText(strout);

        }

        @Override
        public void onFailed_UI(@Nullable String s) {
            String strout = mfirstDD.getName() + "连接失败，请重新打开本页面";
            mtvEquipStatus.setText(strout);

        }
    };

    public void initBlueTooth(){

        mtvEquipStatus.setText("正初始化，请打开设备（红灯闪烁）");

        Rat.initilize(getApplicationContext());
        Rat.getInstance().enableBluetooth();
        Rat.getInstance().startScan(scanCallback);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shock_measure);

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

//        initNoLinkOptionsPicker();


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            mtvEquipStatus.setText("无法启用BLE，请检查终端设置");
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
        else if (mStrInputType.equals(Constant.TASK_TYPE_TEMP))
        {
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
        setMyTimer();
    }

    private void initBold(){

        TextView tv;
        tv = (TextView)findViewById(R.id.tv_des);
        TextPaint paint = tv.getPaint();
        paint.setFakeBoldText(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeMyTimer();

        if (mfirstDD != null) {
            if (Rat.getInstance().haveAnyConneect()){
                Rat.getInstance().disConnectDevice(mfirstDD.getAddress());
            }
        }
        Rat.getInstance().stopScan();
        Rat.getInstance().disableBluetooth();
    }

//    private void initNoLinkOptionsPicker() {// 不联动的多级选项
//        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                mResultint = options1;
//                mBadLvint = options2;
//                mMyActionint = options3;
//                ShowCheckString();
//            }
//        })
//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//                    @Override
//                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//
//                    }
//                })
//                .build();
//
//        InspectionUtil ins = InspectionUtil.getInstance();
//        pvNoLinkOptions.setNPicker(ins.results,ins.badlevels,ins.myactions);
//        pvNoLinkOptions.setSelectOptions(mResultint, mBadLvint, mMyActionint);
//
//
//    }
//
//    private void ShowCheckString(){
//
//        InspectionUtil ins = InspectionUtil.getInstance();
//
//        String strResult = ins.results.get(mResultint);
//        mtvResult.setText(strResult);
//        if (mResultint ==0){
//            mtvResult.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_good));
//        }else {
//            mtvResult.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_bad));
//        }
//
//        String strBadlevel = ins.badlevels.get(mBadLvint);
//        mtvBadLevel.setText(strBadlevel);
//        if (mBadLvint ==0){
//            mtvBadLevel.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_good));
//        }else {
//            mtvBadLevel.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_bad));
//        }
//
//        String strMyAction = ins.myactions.get(mMyActionint);
//        mtvMyAction.setText(strMyAction);
//        if (mMyActionint ==0){
//            mtvMyAction.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_good));
//        }else {
//            mtvMyAction.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_bad));
//        }
//
//    }



    @Override
    protected int initContentView() {
        return R.layout.activity_shock_measure;
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
        if (!mbGet){
            return;
        }

        if(mStrInputType.equals( Constant.TASK_TYPE_NORMAL)) {

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


        }else if (mStrInputType.equals(Constant.TASK_TYPE_TEMP)) {

            TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();
            int curTempIndex = InspectionUtil.getInstance().mCurTempTaskIndex;
            SupTempTask cp = (SupTempTask)(mgr.temptasks.get(curTempIndex));

            cp.isproblem = !mIsGood;


            float fV = Float.valueOf(strV);
            cp.setFValue(fV);

            cp.memo = medMemo.getText().toString();
            cp.done = true;

            if (mBmp != null) {
                cp.picfile1 = SupCheckPointData.makeImgFilePath(cp.mCPCfg.cardid);
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
                if (mBmp != null) {
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

    private void setMyTimer() {

        mTimerHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                        mTimerHandler.removeMessages(0);
                        // 再次发出msg，循环更新
                        mTimerHandler.sendEmptyMessageDelayed(0, 1000);
                        if(mfirstDD !=null){
                            if (Rat.getInstance().haveAnyConneect()){
                                BleDevice bleDevice = Rat.getInstance().getFirstBleDevice();
                                Recipe.newInstance(bleDevice).getValue_Z_Tmp((byte)1,false,mValueCB);
                            }
                        }
                        break;
                    case 1:
                        // 直接移除，定时器停止
                        mTimerHandler.removeMessages(0);
                        break;

                    default:
                        break;
                }
            }

        };
        mTimerHandler.sendEmptyMessage(0);
    }


    private void closeMyTimer() {

        if (mTimerHandler != null)   mTimerHandler.sendEmptyMessage(1);

    }

}
