package com.ator.supmaintenance.act;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.Service.MyTimerService;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.MyRecUtil;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.SyncPlanAction;
import com.ator.supmaintenance.item.UserUtil;

public class LoadingActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    private SyncPlanAction mAction = null;

    @Override
    protected int initContentView() {
        return R.layout.activity_loading;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();

        UserUtil.getInstance().initMap();

        loadInfo();


    }

    private void loadInfo(){

        Resources res = getResources();
        String[] keymap=res.getStringArray(R.array.keys);
        MyRecUtil.getInstance(keymap);

        String[] result_in = res.getStringArray(R.array.results);

        InspectionUtil.getInstance().initStrings(result_in);

        mAction = new SyncPlanAction(this);
        mAction.start();

    }

    @Override
    protected void onPostSucc(){

    }

    @Override
    protected void onPostFaile(){

    }

    @Override
    protected void onGetSucc(){

        if(mAction.doNextSucc() == SyncPlanAction.END){
            gotoNextAct();
        }

    }

    @Override
    protected void onGetFaile(){

        gotoNextAct();

    }

    @Override
    protected void start() {

    }

    private void gotoNextAct(){
        Intent intent = new Intent(this, MyTimerService.class);
        startService(intent);
        RtEnv.startActivity(LoginActivity.class);
        finish();
    }


}
