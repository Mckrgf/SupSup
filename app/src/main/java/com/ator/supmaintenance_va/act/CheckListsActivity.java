package com.ator.supmaintenance_va.act;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.adapter.CheckListAdapter;
import com.ator.supmaintenance_va.item.Constant;
import com.ator.supmaintenance_va.item.InspectionHelp;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.RtEnv;
import com.ator.supmaintenance_va.item.SupCardData;
import com.ator.supmaintenance_va.item.SupCheckPointData;
import com.ator.supmaintenance_va.item.SupInspectionTask;
import com.ator.supmaintenance_va.item.SupInspectionTaskMgr;

public class CheckListsActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private CheckListAdapter mchecklistAdapter;


    private void showSingleChoiceDialog(){

        Dialog dialog = new Dialog(this){

            private View.OnClickListener mOnClickListener;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dlg_choose);
                mOnClickListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        switch (view.getId())
                        {
                            case R.id.ll_hand: {
                                dismiss();
                                RtEnv.startActivity(InputDataActivity.class, Constant.TASK_TYPE_NORMAL);
                            }
                                break;
                            case R.id.ll_temp: {
                                RtEnv.startActivity(infraredActivity.class,Constant.TASK_TYPE_NORMAL);
                                dismiss();
                            }
                                break;
                            case R.id.ll_shock: {
                                RtEnv.startActivity(ShockMeasureActivity.class,Constant.TASK_TYPE_NORMAL);
                                dismiss();
                            }
                                break;
                            default:
                                break;
                        }
                    }
                };
                findViewById(R.id.ll_hand).setOnClickListener(mOnClickListener);
                findViewById(R.id.ll_temp).setOnClickListener(mOnClickListener);
                findViewById(R.id.ll_shock).setOnClickListener(mOnClickListener);
            }
            @Override
            public void onBackPressed() {//在setCanclable为false的情况下，重写onBackPressed方法还是能够成功获取到回退事件的
                super.onBackPressed();
                dismiss();
            }
        };

        dialog.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lists);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CheckListsActivity.this));
        mchecklistAdapter = new CheckListAdapter();

        SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
        SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);
        if (task == null){
            return;
        }
        SupCardData cardData = (SupCardData) task.getCardDatabyID(InspectionUtil.getInstance().mCurCardid);
        if (cardData == null){
            return;
        }

        mchecklistAdapter.SetIndexs(InspectionUtil.getInstance().mCurTaskid,InspectionUtil.getInstance().mCurCardid);
        mRecyclerView.setAdapter(mchecklistAdapter);

        findViewById(R.id.ll_NFC).setOnClickListener(this);
        findViewById(R.id.ll_lookup).setOnClickListener(this);
        findViewById(R.id.ll_map).setOnClickListener(this);

        initTop();

        mchecklistAdapter.setOnItemClickListener(new CheckListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
                SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);
                if (task == null){
                    return;
                }
                SupCardData cardData = (SupCardData) task.getCardDatabyID(InspectionUtil.getInstance().mCurCardid);
                if (cardData == null){
                    return;
                }
                SupCheckPointData cp = (SupCheckPointData)(cardData.checkPoints.get(position));

                InspectionHelp help = new InspectionHelp();
                help.mTaskid = task.taskid;
                help.mCardid = cardData.cardid;
                help.mCpid = cp.cpid;
                InspectionUtil.getInstance().SetCurrent(help);

                if (cp.done){

                    RtEnv.startActivity(ShowCheckPointActivity.class, Constant.TASK_TYPE_NORMAL);

                }else {

                    if (cp.getType().equals("手录")){
                        RtEnv.startActivity(InputDataActivity.class, Constant.TASK_TYPE_NORMAL);
                    }else if (cp.getType().equals("测温")){
                        RtEnv.startActivity(infraredActivity.class, Constant.TASK_TYPE_NORMAL);
                    }else if (cp.getType().equals("测振")){
                        RtEnv.startActivity(ShockMeasureActivity.class,Constant.TASK_TYPE_NORMAL);
                    }else {
                        RtEnv.startActivity(InputDataActivity.class, Constant.TASK_TYPE_NORMAL);
                    }
                }


            }
        });

    }

    private void initTop(){

        findViewById(R.id.im_back).setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
        SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);
        if (task == null){
            return;
        }
        SupCardData cardData = (SupCardData) task.getCardDatabyID(InspectionUtil.getInstance().mCurCardid);
        if (cardData == null){
            return;
        }


        TextView tv = (TextView) findViewById(R.id.tv_title);

        tv.setText(cardData.mcardcfg.cardname);
    }


    @Override
    protected int initContentView() {
        return R.layout.activity_check_lists;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void initView() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    protected void onResume() {
        super.onResume();
        mchecklistAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_NFC:
                RtEnv.startActivity(MyNFCActivity.class);
                finish();
                break;
            case R.id.ll_map:
                RtEnv.startActivity(LocationActivity.class);
                break;
            case R.id.ll_lookup:
                RtEnv.startActivity(QueryInspectionActivity.class);
                break;
        }
    }

}
