package com.ator.supmaintenance_va.act;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;

import com.ator.supmaintenance_va.adapter.TempTaskListAdapter;
import com.ator.supmaintenance_va.item.Constant;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.RtEnv;

import com.ator.supmaintenance_va.item.SupTempTask;
import com.ator.supmaintenance_va.item.TempTaskManager;

public class TempTaskListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private TempTaskListAdapter mAdapter;

    private void showSingleChoiceDialog() {

        Dialog dialog = new Dialog(this) {

            private View.OnClickListener mOnClickListener;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dlg_choose);
                mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.ll_hand: {
                                dismiss();
                                RtEnv.startActivity(InputDataActivity.class, Constant.TASK_TYPE_TEMP);
                            }
                            break;
                            case R.id.ll_temp: {
                                RtEnv.startActivity(infraredActivity.class, Constant.TASK_TYPE_TEMP);
                                dismiss();
                            }
                            break;
                            case R.id.ll_shock: {
                                RtEnv.startActivity(ShockMeasureActivity.class, Constant.TASK_TYPE_TEMP);
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
        setContentView(R.layout.activity_temp_task_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(TempTaskListActivity.this));
        mAdapter = new TempTaskListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.ll_lookup).setOnClickListener(this);
        findViewById(R.id.ll_map).setOnClickListener(this);
        findViewById(R.id.ll_addtemp).setOnClickListener(this);

        initTop();

        mAdapter.setOnItemClickListener(new TempTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();

                if (mgr.temptasks.size() <= position) {
                    return;
                }
                SupTempTask cp = (SupTempTask) (mgr.temptasks.get(position));
                InspectionUtil.getInstance().mCurTempTaskIndex = position;

                if (cp.done) {

                    RtEnv.startActivity(ShowCheckPointActivity.class, Constant.TASK_TYPE_TEMP);

                } else {

                    RtEnv.startActivity(InputDataActivity.class, Constant.TASK_TYPE_TEMP);
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

        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("临时巡检");
    }


    @Override
    protected int initContentView() {
        return R.layout.activity_temp_task_list;
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
    protected void onPostSucc() {

    }

    @Override
    protected void onPostFaile() {

    }

    @Override
    protected void onGetSucc() {

    }

    @Override
    protected void onGetFaile() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void showAddtempTaskDlg(){

        Dialog dialog = new Dialog(this){

            private View.OnClickListener mOnClickListener;
            private EditText medName;
            private EditText medDes;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dlg_addtemptask);
                medName = (EditText)findViewById(R.id.et_inputname);
                medDes = (EditText)findViewById(R.id.et_inputDes);

                mOnClickListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        switch (view.getId())
                        {
                            case R.id.btn_ok: {
                                String strName = medName.getText().toString().trim();
                                String strDes = medDes.getText().toString().trim();

                                if ("".equals(strName))
                                {
                                    dismiss();
                                    return;
                                }

                                SupTempTask cp = new SupTempTask();
                                cp.mCPCfg.cpname = strName;

                                if (!"".equals(strDes)) {
                                    cp.mCPCfg.require = strDes;
                                }else {
                                    cp.mCPCfg.require = "";
                                }

                                InspectionUtil.getInstance().getTempMrg().addTempTask(cp);

                                dismiss();
                                finish();
                                RtEnv.startActivity(TempTaskListActivity.class);
                            }
                            break;
                            case R.id.btn_cancel: {
                                dismiss();
                            }
                            break;
                            default:
                                break;
                        }
                    }
                };
                findViewById(R.id.btn_ok).setOnClickListener(mOnClickListener);
                findViewById(R.id.btn_cancel).setOnClickListener(mOnClickListener);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_map:
                RtEnv.startActivity(LocationActivity.class);
                break;
            case R.id.ll_addtemp:
                showAddtempTaskDlg();
                break;
            case R.id.ll_lookup:
                alertText("提示","演示项目，无历史数据授权");
                break;

        }
    }
}
