package com.ator.supmaintenance.act.tables;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.act.BaseActivity;
import com.ator.supmaintenance.act.HisFileListActivity;
import com.ator.supmaintenance.adapter.SysRunAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONObject;

public class SysRunActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public SysRunAdapter adapter = new SysRunAdapter();
    private boolean do_not_save_file = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_run);

        mivAdd = (ImageView)findViewById(R.id.iv_add1);


        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("系统运行检查");

        loadTmpfile();
    }


    @Override
    protected int initContentView() {
        return R.layout.activity_sys_run;
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

    private String  mTmpSub = "TEMP-SYSRUN";

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

                ((EditText)findViewById(R.id.cabinet_id)).setText(obj.getString("cabinet_id"));

                SetRGtext(R.id.humiture,obj.getString("humiture"));
                SetRGtext(R.id.emi,obj.getString("emi"));
                SetRGtext(R.id.powerlight,obj.getString("powerlight"));
                SetRGtext(R.id.cardlight,obj.getString("cardlight"));
                SetRGtext(R.id.interchanger,obj.getString("interchanger"));
                SetRGtext(R.id.networknode,obj.getString("networknode"));
                SetRGtext(R.id.networkcable,obj.getString("networkcable"));
                SetRGtext(R.id.fan,obj.getString("fan"));
                SetRGtext(R.id.door_close,obj.getString("door_close"));

                SetRGtext(R.id.staticfree,obj.getString("staticfree"));
                SetRGtext(R.id.airtightness,obj.getString("airtightness"));
                SetRGtext(R.id.connectingline,obj.getString("connectingline"));
                SetRGtext(R.id.cable_marking,obj.getString("cable_marking"));
                SetRGtext(R.id.airswitch_marking,obj.getString("airswitch_marking"));
                SetRGtext(R.id.sparechannel_margin,obj.getString("sparechannel_margin"));
                SetRGtext(R.id.rats,obj.getString("rats"));

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

        ((EditText)findViewById(R.id.cabinet_id)).setText("");

        SetRGtext(R.id.humiture,"");
        SetRGtext(R.id.emi,"");
        SetRGtext(R.id.powerlight,"");
        SetRGtext(R.id.cardlight,"");
        SetRGtext(R.id.interchanger,"");
        SetRGtext(R.id.networknode,"");
        SetRGtext(R.id.networkcable,"");
        SetRGtext(R.id.fan,"");
        SetRGtext(R.id.door_close,"");

        SetRGtext(R.id.staticfree,"");
        SetRGtext(R.id.airtightness,"");
        SetRGtext(R.id.connectingline,"");
        SetRGtext(R.id.cable_marking,"");
        SetRGtext(R.id.airswitch_marking,"");
        SetRGtext(R.id.sparechannel_margin,"");
        SetRGtext(R.id.rats,"");

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
        adapter.cabinet_id = ((EditText)findViewById(R.id.cabinet_id)).getText().toString().trim();

        adapter.humiture = GetRGtext(R.id.humiture);
        adapter.emi = GetRGtext(R.id.emi);
        adapter.powerlight = GetRGtext(R.id.powerlight);
        adapter.cardlight = GetRGtext(R.id.cardlight);
        adapter.interchanger = GetRGtext(R.id.interchanger);
        adapter.networknode = GetRGtext(R.id.networknode);
        adapter.networkcable = GetRGtext(R.id.networkcable);
        adapter.fan = GetRGtext(R.id.fan);
        adapter.door_close = GetRGtext(R.id.door_close);

        adapter.staticfree = GetRGtext(R.id.staticfree);
        adapter.airtightness = GetRGtext(R.id.airtightness);
        adapter.connectingline = GetRGtext(R.id.connectingline);
        adapter.cable_marking = GetRGtext(R.id.cable_marking);
        adapter.airswitch_marking = GetRGtext(R.id.airswitch_marking);
        adapter.sparechannel_margin = GetRGtext(R.id.sparechannel_margin);
        adapter.rats = GetRGtext(R.id.rats);

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
        FileUtil.saveFile(getApplicationContext(),strOut,"SYSRUN",adapter.room_id+".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(),mBmp,"SYSRUN",adapter.append);
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
                RtEnv.startActivity(HisFileListActivity.class, "SYSRUN");
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(SysRunActivity.this).setTitle("系统提示")//设置对话框标题
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
