package com.ator.supmaintenance_va.act;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;

public class NFCCardActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfccard);

        findViewById(R.id.im_back).setOnClickListener(this);

        TextView tv = (TextView)findViewById(R.id.tv_title);
        tv.setText("NFC 卡管理");

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_nfccard;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void initView() {

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
    protected void start() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;

        }
    }
}
