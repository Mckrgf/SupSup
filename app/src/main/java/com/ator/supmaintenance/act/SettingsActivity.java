package com.ator.supmaintenance.act;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.UI.AppUpdateDialog;
import com.ator.supmaintenance.item.Constant;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import java.io.File;

public class SettingsActivity extends BaseActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        TextView tv = (TextView)findViewById(R.id.tv_title);
        tv.setText("设置");

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.tv_update).setOnClickListener(this);
        findViewById(R.id.tv_supcon).setOnClickListener(this);
        findViewById(R.id.tv_software).setOnClickListener(this);
        findViewById(R.id.tv_userlogon).setOnClickListener(this);
        findViewById(R.id.tv_card).setOnClickListener(this);
        findViewById(R.id.tv_net).setOnClickListener(this);

    }



    @Override
    protected int initContentView() {
        return R.layout.activity_settings;
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
    protected void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_update:
                updateAndInstall();
                break;
            case R.id.tv_supcon:
                RtEnv.startActivity(AboutActivity.class, Constant.ABOUT_SUPCON);
                break;
            case R.id.tv_software:
                RtEnv.startActivity(AboutActivity.class,Constant.ABOUT_PRODUCT);
                break;
            case R.id.tv_userlogon:
                RtEnv.startActivity(LoginActivity.class);
                break;
            case R.id.tv_card:
                RtEnv.startActivity(NFCCardActivity.class);
                break;
            case R.id.tv_net:
//                RtEnv.startActivity(NetSettingActivity.class);
                break;
        }

    }

    public String mStrApkPath;
    AppUpdateDialog mApkUpdatedlg;

    public void updateAndInstall(){

        if (mApkUpdatedlg != null) {
            mApkUpdatedlg = null;
        }

        if (MyNetHelper.getNetWorkState(this) == MyNetHelper.NETWORK_NONE) {
            alertText("提示","无网络");
            return;
        }

        mApkUpdatedlg = new AppUpdateDialog(this);
        mApkUpdatedlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条
        mApkUpdatedlg.setCancelable(false);// 设置是否可以通过点击Back键取消
        mApkUpdatedlg.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        mApkUpdatedlg.setTitle("更新");
        mApkUpdatedlg.setMessage("正在更新APK");
        mApkUpdatedlg.setMax(100);
        mApkUpdatedlg.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if (mApkUpdatedlg.strAPKPath .equals("")){
                    alertText("提示",mApkUpdatedlg.strOutput);
                }else {
                    mStrApkPath = AppUpdateDialog.strAPKPath;
                    File apkfile = new File(mStrApkPath);
                    installApk(apkfile);
                }

            }
        });
        mApkUpdatedlg.show();

    }


    public void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }




}
