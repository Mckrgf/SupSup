package com.ator.supmaintenance_va.act;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.adapter.TaskAdapter;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.MyNetHelper;
import com.ator.supmaintenance_va.item.RtEnv;
import com.ator.supmaintenance_va.item.SupInspectionTask;
import com.ator.supmaintenance_va.item.SyncTaskAction;
import com.ator.supmaintenance_va.item.UserUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectionMainActivity extends BaseActivity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    private TextView mTvTitle;

    private TextView mTvUserName;
    private TextView mTvUserDepartment;
    private TextView mTvNowTimer;
    private TextView mTvTaskCount;
    private ImageView mImMenu;

    private Handler mTimerHandler = null;

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;

    private SyncTaskAction mTaskAction;


    private boolean bFirst =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(InspectionMainActivity.this));
        mTaskAdapter = new TaskAdapter(this);
        mRecyclerView.setAdapter(mTaskAdapter);

        mTaskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                SupInspectionTask task =  (SupInspectionTask) InspectionUtil.getInstance().getTaskMgr().mTasks.get(position);
                InspectionUtil.getInstance().mCurTaskid = task.taskid;
                RtEnv.startActivity(CardListActivity.class,"");

            }
        });

        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
        mTvUserDepartment = (TextView) findViewById(R.id.tv_user_department);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvNowTimer = (TextView)findViewById(R.id.tv_time);
        mTvTaskCount = (TextView)findViewById(R.id.tv_taskcount);
        mImMenu = (ImageView)findViewById(R.id.im_menu);

        findViewById(R.id.ll_NFC).setOnClickListener(this);
        findViewById(R.id.ll_map).setOnClickListener(this);
        findViewById(R.id.ll_lookup).setOnClickListener(this);
        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.ll_userlogin).setOnClickListener(this);
        findViewById(R.id.im_menu).setOnClickListener(this);


        setMyTimer();
        mTvTitle.setText("智能巡检");

        if (MyNetHelper.getNetWorkState(this) !=MyNetHelper.NETWORK_NONE){
            loadTaskDataFromSvr();
        }else {
            bFirst = false;
        }
        setInfomation();
        findViewById(R.id.ll_bottom_all).setVisibility(View.GONE);


    }

    //弹出式菜单的单击事件处理
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_update:
                break;
            case R.id.menu_map:
                RtEnv.startActivity(LocationActivity.class);
                break;
            case R.id.menu_his:
                RtEnv.startActivity(QueryInspectionActivity.class);
                break;
        }
        return false;
    }


    private void loadTaskDataFromSvr(){

        mTaskAction = new SyncTaskAction(this);
        if (!mTaskAction.start()){
            bFirst = false;
            setInfomation();
            mTaskAdapter.notifyDataSetChanged();
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
                        mTimerHandler.sendEmptyMessageDelayed(0, 60*1000);
                        mTaskAdapter.notifyDataSetChanged();
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

    private void setInfomation() {

        mTvUserName.setText(UserUtil.getInstance().mCurEmployee.name);
        mTvUserDepartment.setText(UserUtil.getInstance().mCurEmployee.department);

        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String strTime = sdf.format(dd);
        mTvNowTimer.setText(strTime);

        int n = InspectionUtil.getInstance().getTaskMgr().mTasks.size();

        String strN = String.format("%d",n);
        mTvTaskCount.setText(strN);

        if (bFirst){
            mRecyclerView.setVisibility(View.INVISIBLE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeMyTimer();
    }


    @Override
    protected void onStart(){
        super.onStart();
        setInfomation();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_inspection_main;
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
        if(mTaskAction.doNextSucc() == SyncTaskAction.END){
            InspectionUtil.getInstance().getTaskMgr().cleanTasks();
            bFirst = false;
            setInfomation();
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onGetFaile(){
        bFirst = false;
        setInfomation();
        mTaskAdapter.notifyDataSetChanged();
        alertText("提示","下载失败，请检查网络");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_NFC:
                RtEnv.startActivity(MyNFCActivity.class);
                break;
            case R.id.ll_userlogin:
                RtEnv.startActivity(LoginActivity.class);
                break;
            case R.id.ll_map:
                RtEnv.startActivity(LocationActivity.class);
                break;
            case R.id.ll_lookup:
                RtEnv.startActivity(QueryInspectionActivity.class);
                break;
            case R.id.im_back:
                finish();
                break;
            case R.id.im_menu:
                PopupMenu popup = new PopupMenu(this, v);
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.menu_pop_inspection_main, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(this);
                popup.show();
                break;

        }
    }

}
