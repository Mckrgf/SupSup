package com.ator.supmaintenance.act.tables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.act.BaseActivity;
import com.ator.supmaintenance.item.MyApplication;
import com.ator.supmaintenance.item.SPUtils;

public class NetSettingActivity extends BaseActivity {
    private Button bt_change_url;
    private TextView tv_url_now;
    private static final String TAG = "NetSettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_setting);
        bt_change_url = findViewById(R.id.bt_change_url);
        tv_url_now = findViewById(R.id.tv_url_now);
        final String url = (String) SPUtils.get(this,"net_url","");
        tv_url_now.setText("当前url地址为: " + url);
        //192.168.8.105
        bt_change_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(NetSettingActivity.this,"net_url","").toString().contains("192.168")) {
                    SPUtils.put(NetSettingActivity.this,"net_url","http://118.31.42.34:8090");
                }else {
                    SPUtils.put(NetSettingActivity.this,"net_url","http://192.168.8.105:8080");
                }
                tv_url_now.setText("当前url地址为: " + (String) SPUtils.get(NetSettingActivity.this,"net_url",""));
                MyApplication.base_url = (String) SPUtils.get(NetSettingActivity.this,"net_url","");
                Log.i(TAG,"当前url地址为:" + MyApplication.base_url);
            }
        });
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_net_setting;
    }

    @Override
    protected void getIntentData() {

    }

    @Override
    protected void initView() {

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
}
