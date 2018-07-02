package com.ator.supmaintenance.act;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.adapter.CardListAdapter;
import com.ator.supmaintenance.item.HisInspectionUtil;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.SupCardData;
import com.ator.supmaintenance.item.SupInspectionTask;

public class CardListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ImageView mImgUser;

    private String mStrInputType = "";

    private CardListAdapter mAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_cardlist;
    }

    @Override
    protected void getIntentData() {
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardlist);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStrInputType = bundle.getString("type");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_tk);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CardListActivity.this));
        mAdapter = new CardListAdapter(this,mStrInputType);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CardListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (mStrInputType.equals("")){
                    String  curTaskid = InspectionUtil.getInstance().mCurTaskid;
                    SupInspectionTask task = InspectionUtil.getInstance().getTaskMgr().getCurTaskbyID(curTaskid);
                    if (task == null){
                        return;
                    }
                    if (position >= task.getCardCount()){
                        return;
                    }
                    SupCardData cd = (SupCardData) task.getCardData(position);
                    InspectionUtil.getInstance().mCurCardid = cd.cardid;
                }else {
                    SupInspectionTask task = HisInspectionUtil.getInstance().getOneTask();

                }

            }
        });

        findViewById(R.id.ll_NFC).setOnClickListener(this);
        findViewById(R.id.ll_map).setOnClickListener(this);
        findViewById(R.id.ll_lookup).setOnClickListener(this);

        initTop();

        if (mStrInputType.equals("")){

        }else {
            findViewById(R.id.ll_bottom_all).setVisibility(View.GONE);
        }

    }

    private void initTop(){

        findViewById(R.id.im_back).setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupInspectionTask task = null;

        if (mStrInputType.equals("")){
            String  curTaskid = InspectionUtil.getInstance().mCurTaskid;
            task = InspectionUtil.getInstance().getTaskMgr().getCurTaskbyID(curTaskid);
        }else {
            task = HisInspectionUtil.getInstance().getOneTask();
        }

        if (task == null){
            return;
        }
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(task.taskname);
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
    protected void onRestart(){
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            mAdapter.notifyDataSetChanged();
        }else if (resultCode == 3){
            finish();
            RtEnv.startActivity(CardListActivity.class,"");
        }else if (resultCode == 4){
            finish();
            RtEnv.startActivity(InspectionMainActivity.class);
        }
    }

}
