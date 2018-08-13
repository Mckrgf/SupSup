package com.ator.supmaintenance_va.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.item.Constant;
import com.ator.supmaintenance_va.item.FileUtil;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.SupCheckPointData;
import com.ator.supmaintenance_va.item.SupInspectionTask;
import com.ator.supmaintenance_va.item.SupInspectionTaskMgr;
import com.ator.supmaintenance_va.item.SupCardData;
import com.ator.supmaintenance_va.item.TempTaskManager;

public class ShowCheckPointActivity extends BaseActivity implements View.OnClickListener {

    private TextView        mtvTitle;
    private TextView        mtvUnit;


    private TextView        mtvValue;
    private TextView        mtvMemo;
    private ImageView       mivAdd;
    private Bitmap          mBmp = null;
    private String          mStrInputType;


    private TextView    mtvCPDes;
    private TextView    mtvResult;
    private TextView    mtvTagName;

    private String mstrResul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_check_point);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStrInputType = bundle.getString("type");

        mtvValue = (TextView) findViewById(R.id.tv_value);
        mtvMemo = (TextView)findViewById(R.id.tv_memo);
        mivAdd = (ImageView)findViewById(R.id.iv_add1);
        mtvTitle = (TextView)findViewById(R.id.tv_title);
        mtvUnit = (TextView)findViewById(R.id.tv_unit);
        mtvResult = (TextView)findViewById(R.id.tv_result);
        mtvCPDes = (TextView)findViewById(R.id.tv_des);
        mtvTagName = (TextView)findViewById(R.id.tv_tagname);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);


        SupCheckPointData cp = null;
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
            cp = (SupCheckPointData) cardData.getCPDatabyID(InspectionUtil.getInstance().mCurCpid);
            if (cp == null){
                return;
            }


        } else if (mStrInputType.equals(Constant.TASK_TYPE_TEMP)){

            TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();
            int curTempIndex = InspectionUtil.getInstance().mCurTempTaskIndex;
            cp = (SupCheckPointData)(mgr.temptasks.get(curTempIndex));

        }

        mtvTitle.setText(cp.mCPCfg.cpname);
        mtvUnit.setText(cp.mCPCfg.unit);
        mtvCPDes.setText(cp.mCPCfg.require);
        mtvTagName.setText(cp.mCPCfg.tag);
        String strV = cp.inputfloat;
        mtvValue.setText(strV);
        mtvMemo.setText(cp.memo);

        if (cp.picfile1 !=null)
        {
            mBmp = FileUtil.loadBmp(getApplicationContext(), InspectionUtil.mStrPicSub, cp.picfile1);
            mivAdd.setImageBitmap(mBmp);
        }else {
            mivAdd.setVisibility(View.GONE);
        }

        if (cp.isproblem){
            mstrResul = "异常";
        }else{
            mstrResul = "正常";
        }
        ShowCheckString();
    }


    private void ShowCheckString(){

        InspectionUtil ins = InspectionUtil.getInstance();

        String strResult = ins.results.get(0);
        mtvResult.setText(mstrResul);
        if (strResult.equals(mstrResul)){
            mtvResult.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_good));
        }else {
            mtvResult.setBackground(getResources().getDrawable(R.drawable.bg_roundcorner_bad));
        }

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_show_check_point;
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

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.im_back:
                finish();
                break;
            case R.id.iv_add1:
                if (mBmp != null)
                {
                    final Bitmap bmp = Bitmap.createBitmap(mBmp);
                    showCPImagDialog(bmp);
                }

                break;
        }
    }

}
