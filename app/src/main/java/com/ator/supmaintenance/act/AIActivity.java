package com.ator.supmaintenance.act;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyAIHelper;
import com.ator.supmaintenance.item.RecognizeService;
import com.ator.supmaintenance.item.RtEnv;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class AIActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_ACCURATE_BASIC = 107;
    private boolean hasGotToken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        findViewById(R.id.letter_button).setOnClickListener(this);
        findViewById(R.id.idcard_button).setOnClickListener(this);
        findViewById(R.id.face_button).setOnClickListener(this);

        initTop();
        initAccessTokenWithAkSk();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
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

    private void initTop(){

        findViewById(R.id.im_back).setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("图像识别");

    }
    
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.letter_button:
                recLetter();
                break;
            case R.id.idcard_button:
                recIDcard();
                break;
            case R.id.face_button:
                Toast.makeText(this,"open soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.im_back:
                finish();
                break;
        }
    }

    public void recIDcard(){
        if (!hasGotToken) {
            return;
        }
//        Intent intent = new Intent(AIActivity.this, IDCardActivity.class);
//        startActivity(intent);
        RtEnv.startActivity(GuestActivity.class);

    }

    public void recLetter(){
        if (!hasGotToken) {
            return;
        }
        Intent intent = new Intent(AIActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，通用文字识别（高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            try {
                                JSONObject json = new JSONObject(result);
                                JSONArray jsonArray = json.getJSONArray("words_result");

                                String strOut = "";
                                for (int i=0;i<jsonArray.length();i++){
                                    String str = MyAIHelper.parseResult(jsonArray.get(i).toString());
                                    strOut += str;
                                }

                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(strOut);

                                if (strOut.length() >10) {
                                    alertText("文字识别","共识别出 " + strOut.length() + " 文字，已拷入剪贴板");
                                }else{
                                    alertText("文字识别","识别文字:  "+ strOut + " ,已拷入剪贴板");
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }

    private void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext());
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(), "N2cpwEVMoMhOhKN2GXNFWiWF", "0NYHhWvcsIy1oCVcXw3afFB05uX77erl");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getApplicationContext(), "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop(){
        super.onStop();
        // 释放内存资源
//        OCR.getInstance().release();
    }

}
