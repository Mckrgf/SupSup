package com.ator.supmaintenance_va.act;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextPaint;
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
import com.ator.supmaintenance_va.item.SupCardData;
import com.ator.supmaintenance_va.item.SupCheckPointData;
import com.ator.supmaintenance_va.item.SupInspectionTask;
import com.ator.supmaintenance_va.item.SupInspectionTaskMgr;

import com.ator.supmaintenance_va.item.SupTempTask;
import com.ator.supmaintenance_va.item.TempTaskManager;
import com.bigkoo.pickerview.view.OptionsPickerView;

public class InputDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView    mtvTitle;
    private TextView    mtvUnit;

    private EditText        medValue;
    private EditText        medMemo;
    private ImageView       mivAdd;
    private Bitmap          mBmp = null;
    private String          mStrInputType;


    private TextView    mtvCPDes;


    private OptionsPickerView   pvNoLinkOptions;


    private boolean mIsGood = true;
    private boolean bCardRefresh = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStrInputType = bundle.getString("type");

        medValue = (EditText)findViewById(R.id.et_value);
        medMemo = (EditText)findViewById(R.id.et_memo);
        mivAdd = (ImageView)findViewById(R.id.iv_add1);
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


        if (mStrInputType.equals(Constant.TASK_TYPE_NORMAL)){

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

            mtvTitle.setText(cp.mCPCfg.cpname);
            mtvUnit.setText(cp.mCPCfg.unit);

            if (("").equals(cp.mCPCfg.require)){
                mtvCPDes.setText("无描述");
            }else{
                mtvCPDes.setText(cp.mCPCfg.require);
            }

            ShowCheckString();

        } else if (mStrInputType.equals(Constant.TASK_TYPE_TEMP)){

            TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();
            int curTempIndex = InspectionUtil.getInstance().mCurTempTaskIndex;
            SupTempTask cp = (SupTempTask)(mgr.temptasks.get(curTempIndex));

            mtvTitle.setText(cp.mCPCfg.cpname);
            mtvUnit.setText(cp.mCPCfg.unit);

            if (("").equals(cp.mCPCfg.require)){
                mtvCPDes.setText("无描述");
            }else{
                mtvCPDes.setText(cp.mCPCfg.require);
            }

        }

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

    private void initNoLinkOptionsPicker() {// 不联动的多级选项
//        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
////                mResultint = options1;
////                mBadLvint = options2;
////                mMyActionint = options3;
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
////        pvNoLinkOptions.setSelectOptions(mResultint, mBadLvint, mMyActionint);


    }

    @Override
    protected int initContentView() {
        return R.layout.activity_input_data;
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

        finish();


    }

    @Override
    protected void onPostFaile(){

        alertText("提示","上传失败，请检查网络");
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

    private void ShowCheckString(){

        InspectionUtil ins = InspectionUtil.getInstance();

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

    }

    private void SubmitCheckPoint(){

        String strV = medValue.getText().toString().trim();
        if("".equals(strV) && mBmp == null){
            return;
        }

        if(mStrInputType.equals(Constant.TASK_TYPE_NORMAL) ){

            SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
            SupInspectionTask task = mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);

            if (task ==null){
                alertText("提示：","任务已过期");
                return;
            }

            SupCardData card = task.getCardDatabyID(InspectionUtil.getInstance().mCurCardid);
            if (card ==null){
                alertText("提示：","未找到设备");
                return;
            }

            SupCheckPointData cp = card.getCPDatabyID(InspectionUtil.getInstance().mCurCpid);
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
                bCardRefresh = true;
            }else {
                strUploadCP = task.makeUploadJstring(null,cp);
                bCardRefresh = false;
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

            if ("".equals(strV)){

                cp.setFValue(0.0f);
            }else {
                float fV = Float.valueOf(strV);
                cp.setFValue(fV);
            }

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
}
