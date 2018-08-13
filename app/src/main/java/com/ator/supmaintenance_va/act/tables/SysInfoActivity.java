package com.ator.supmaintenance_va.act.tables;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.act.BaseActivity;
import com.ator.supmaintenance_va.act.HisFileListActivity;
import com.ator.supmaintenance_va.adapter.SysInfoAdapter;
import com.ator.supmaintenance_va.item.FileUtil;
import com.ator.supmaintenance_va.item.MyNetHelper;
import com.ator.supmaintenance_va.item.RtEnv;

import org.json.JSONObject;

public class SysInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public SysInfoAdapter adapter = new SysInfoAdapter();
    private boolean do_not_save_file = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_info);

        mivAdd = (ImageView)findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("系统规模信息");

        loadTmpfile();

    }


    @Override
    protected int initContentView() {
        return R.layout.activity_sys_info;
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

        alertText("上传","上传成功");
        Clearall();
    }

    @Override
    protected void onPostFaile(){

        alertText("上传","上传失败");

    }

    @Override
    protected void onGetSucc(){

    }

    @Override
    protected void onGetFaile(){

    }

    @Override
    protected void onStop(){
        super.onStop();
        if (!do_not_save_file) {
            saveTmpfile();
        }
    }

    private String  mTmpSub = "TEMP-SYSINFO";

    private void saveTmpfile(){

        setAdapter();
        adapter.MakeJasonObj();
        String strOut = adapter.GetJasonString();
        //
        FileUtil.saveFile(getApplicationContext(),strOut,mTmpSub,"temp.tmp");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(),mBmp,mTmpSub,"temp.png");
        }

    }

    private void deleteTmpFile(){

        FileUtil.deleteFile(getApplicationContext(),mTmpSub,"temp.tmp");
        FileUtil.deleteFile(getApplicationContext(),mTmpSub,"temp.png");
        do_not_save_file = true;
    }

    private void loadTmpfile(){

        try {
            String result = FileUtil.getFile(getApplicationContext(),mTmpSub,"temp.tmp");
            if (result !=null){
                JSONObject obj = new JSONObject(result);

                ((EditText)findViewById(R.id.room_name)).setText(obj.getString("room_name"));
                ((EditText)findViewById(R.id.cabinet_id)).setText(obj.getString("cabinet_id"));
                ((EditText)findViewById(R.id.historicalcontract_id)).setText(obj.getString("historicalcontract_id"));
                ((EditText)findViewById(R.id.fcu712)).setText(obj.getString("fcu712"));
                ((EditText)findViewById(R.id.com711)).setText(obj.getString("com711"));
                ((EditText)findViewById(R.id.com741)).setText(obj.getString("com741"));
                ((EditText)findViewById(R.id.ai711)).setText(obj.getString("ai711"));
                ((EditText)findViewById(R.id.ai721)).setText(obj.getString("ai721"));
                ((EditText)findViewById(R.id.am712)).setText(obj.getString("am712"));
                ((EditText)findViewById(R.id.ai731)).setText(obj.getString("ai731"));
                ((EditText)findViewById(R.id.ao711)).setText(obj.getString("ao711"));
                ((EditText)findViewById(R.id.di711)).setText(obj.getString("di711"));
                ((EditText)findViewById(R.id.do711)).setText(obj.getString("do711"));
                ((EditText)findViewById(R.id.do712)).setText(obj.getString("do712"));
                ((EditText)findViewById(R.id.tu713_r1200)).setText(obj.getString("tu713_r1200"));
                ((EditText)findViewById(R.id.tu721_r00)).setText(obj.getString("tu721_r00"));
                ((EditText)findViewById(R.id.tu721_r01)).setText(obj.getString("tu721_r01"));
                ((EditText)findViewById(R.id.tua712_dor16)).setText(obj.getString("tua712_dor16"));
                ((EditText)findViewById(R.id.tua711_dor16)).setText(obj.getString("tua711_dor16"));

                ((EditText)findViewById(R.id.suggestion)).setText(obj.getString("suggestion"));

                mBmp = FileUtil.loadBmp(getApplicationContext(),mTmpSub,"temp.png");
                if (mBmp != null){
                    mivAdd.setImageBitmap(mBmp);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return;
    }

    private void Clearall(){

        ((EditText)findViewById(R.id.room_name)).setText("");
        ((EditText)findViewById(R.id.cabinet_id)).setText("");
        ((EditText)findViewById(R.id.historicalcontract_id)).setText("");
        ((EditText)findViewById(R.id.fcu712)).setText("");
        ((EditText)findViewById(R.id.com711)).setText("");
        ((EditText)findViewById(R.id.com741)).setText("");
        ((EditText)findViewById(R.id.ai711)).setText("");
        ((EditText)findViewById(R.id.ai721)).setText("");
        ((EditText)findViewById(R.id.am712)).setText("");
        ((EditText)findViewById(R.id.ai731)).setText("");
        ((EditText)findViewById(R.id.ao711)).setText("");
        ((EditText)findViewById(R.id.di711)).setText("");
        ((EditText)findViewById(R.id.do711)).setText("");
        ((EditText)findViewById(R.id.do712)).setText("");
        ((EditText)findViewById(R.id.tu713_r1200)).setText("");
        ((EditText)findViewById(R.id.tu721_r00)).setText("");
        ((EditText)findViewById(R.id.tu721_r01)).setText("");
        ((EditText)findViewById(R.id.tua712_dor16)).setText("");
        ((EditText)findViewById(R.id.tua711_dor16)).setText("");

        ((EditText)findViewById(R.id.suggestion)).setText("");

        mBmp = null;
        mivAdd.setImageBitmap(null);
        deleteTmpFile();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (mBmp != null){
            mivAdd.setImageBitmap(mBmp);
        }
    }

    private void setAdapter(){

        adapter.room_name = ((EditText)findViewById(R.id.room_name)).getText().toString().trim();
        adapter.cabinet_id = ((EditText)findViewById(R.id.cabinet_id)).getText().toString().trim();
        adapter.historicalcontract_id = ((EditText)findViewById(R.id.historicalcontract_id)).getText().toString().trim();
        adapter.fcu712 = ((EditText)findViewById(R.id.fcu712)).getText().toString().trim();
        adapter.com711 = ((EditText)findViewById(R.id.com711)).getText().toString().trim();
        adapter.com741 = ((EditText)findViewById(R.id.com741)).getText().toString().trim();
        adapter.ai711 = ((EditText)findViewById(R.id.ai711)).getText().toString().trim();
        adapter.ai721 = ((EditText)findViewById(R.id.ai721)).getText().toString().trim();
        adapter.am712 = ((EditText)findViewById(R.id.am712)).getText().toString().trim();
        adapter.ai731 = ((EditText)findViewById(R.id.ai731)).getText().toString().trim();
        adapter.ao711 = ((EditText)findViewById(R.id.ao711)).getText().toString().trim();
        adapter.di711 = ((EditText)findViewById(R.id.di711)).getText().toString().trim();
        adapter.do711 = ((EditText)findViewById(R.id.do711)).getText().toString().trim();
        adapter.do712 = ((EditText)findViewById(R.id.do712)).getText().toString().trim();
        adapter.tu713_r1200 = ((EditText)findViewById(R.id.tu713_r1200)).getText().toString().trim();
        adapter.tu721_r00 = ((EditText)findViewById(R.id.tu721_r00)).getText().toString().trim();
        adapter.tu721_r01 = ((EditText)findViewById(R.id.tu721_r01)).getText().toString().trim();
        adapter.tua712_dor16 = ((EditText)findViewById(R.id.tua712_dor16)).getText().toString().trim();
        adapter.tua711_dor16 = ((EditText)findViewById(R.id.tua711_dor16)).getText().toString().trim();

        adapter.suggestion = ((EditText)findViewById(R.id.suggestion)).getText().toString().trim();

        boolean bAppend = false;
        if(mBmp != null){
            bAppend = true;
        }
        adapter.MakeID(bAppend);
    }

    private boolean CheckAllOK(){
        setAdapter();
        return adapter.CheckAll();
    }

    private boolean SubMit(){

        adapter.MakeJasonObj();
        String strOut = adapter.GetJasonString();
        //
        FileUtil.saveFile(getApplicationContext(),strOut,"SYSINFO",adapter.room_id+".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(),mBmp,"SYSINFO",adapter.append);
        }

        boolean bOK = false;
        String strUpload = adapter.GetNetJasonString();
        if (mNetState != MyNetHelper.NETWORK_NONE){
            bOK = postJasonString(adapter.strURL,strUpload,5000,201,true,"上传","上传中...");

        }else{
            Toast.makeText(this,"未联入网络，无法上传",Toast.LENGTH_LONG);
        }

        return  bOK;


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.btn_camera:
                takePhoto();
                break;
            case R.id.iv_add1:
                if (mBmp != null) {
                    final Bitmap bmp = Bitmap.createBitmap(mBmp);
                    showCPImagDialog(bmp);
                }
                break;
            case R.id.btn_commit:
                if(CheckAllOK()){
                    SubMit();
                }else {
                    Toast.makeText(this, "请完整录入", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_cphis: {
                RtEnv.startActivity(HisFileListActivity.class, "SYSINFO");
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(SysInfoActivity.this).setTitle("系统提示")//设置对话框标题
                        .setMessage("是否清空数据？")//设置显示的内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                Clearall();
                                dismissDialog();
                            }
                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        dismissDialog();
                    }
                }).show();//在按键响应事件中显示此对话框
                break;
            }
        }
    }
}
