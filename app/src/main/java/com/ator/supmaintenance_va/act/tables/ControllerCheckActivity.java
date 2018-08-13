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
import com.ator.supmaintenance_va.adapter.ControllerCheckAdapter;
import com.ator.supmaintenance_va.item.FileUtil;
import com.ator.supmaintenance_va.item.MyNetHelper;
import com.ator.supmaintenance_va.item.RtEnv;

import org.json.JSONObject;

public class ControllerCheckActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public ControllerCheckAdapter adapter = new ControllerCheckAdapter();
    private boolean do_not_save_file = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_check);

        mivAdd = (ImageView)findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("控制站供电");

        loadTmpfile();

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_controller_check;
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


    private String  mTmpSub = "TEMP-CONTROLLERCHECK";
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

                ((EditText)findViewById(R.id.controller_site)).setText(obj.getString("controller_site"));
                ((EditText)findViewById(R.id.controller_type)).setText(obj.getString("controller_type"));
                ((EditText)findViewById(R.id.controllercpu_version)).setText(obj.getString("controllercpu_version"));
                ((EditText)findViewById(R.id.net_commucpu_version)).setText(obj.getString("net_commucpu_version"));
                ((EditText)findViewById(R.id.io_commucpu_version)).setText(obj.getString("io_commucpu_version"));

                SetRGtext(R.id.redundancy,obj.getString("redundancy"));
                SetRGtext(R.id.config,obj.getString("config"));
                SetRGtext(R.id.userprogram_running,obj.getString("userprogram_running"));
                SetRGtext(R.id.scnet,obj.getString("scnet"));
                SetRGtext(R.id.ebus,obj.getString("ebus"));
                SetRGtext(R.id.lbus,obj.getString("lbus"));
                SetRGtext(R.id.load,obj.getString("load"));
                SetRGtext(R.id.cellvoltage,obj.getString("cellvoltage"));
                SetRGtext(R.id.temperature,obj.getString("temperature"));
                SetRGtext(R.id.io_oos,obj.getString("io_oos"));
                SetRGtext(R.id.io_constraint,obj.getString("io_constraint"));
                SetRGtext(R.id.analog_beyond,obj.getString("analog_beyond"));
                SetRGtext(R.id.di_joggle,obj.getString("di_joggle"));

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

        ((EditText)findViewById(R.id.controller_site)).setText("");
        ((EditText)findViewById(R.id.controller_type)).setText("");
        ((EditText)findViewById(R.id.controllercpu_version)).setText("");
        ((EditText)findViewById(R.id.net_commucpu_version)).setText("");
        ((EditText)findViewById(R.id.io_commucpu_version)).setText("");

        SetRGtext(R.id.redundancy,"");
        SetRGtext(R.id.config,"");
        SetRGtext(R.id.userprogram_running,"");
        SetRGtext(R.id.scnet,"");
        SetRGtext(R.id.ebus,"");
        SetRGtext(R.id.lbus,"");
        SetRGtext(R.id.load,"");
        SetRGtext(R.id.cellvoltage,"");
        SetRGtext(R.id.temperature,"");
        SetRGtext(R.id.io_oos,"");
        SetRGtext(R.id.io_constraint,"");
        SetRGtext(R.id.analog_beyond,"");
        SetRGtext(R.id.di_joggle,"");

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

        adapter.controller_site = ((EditText)findViewById(R.id.controller_site)).getText().toString().trim();
        adapter.controller_type = ((EditText)findViewById(R.id.controller_type)).getText().toString().trim();
        adapter.controllercpu_version = ((EditText)findViewById(R.id.controllercpu_version)).getText().toString().trim();
        adapter.net_commucpu_version = ((EditText)findViewById(R.id.net_commucpu_version)).getText().toString().trim();
        adapter.io_commucpu_version = ((EditText)findViewById(R.id.io_commucpu_version)).getText().toString().trim();

        adapter.redundancy = GetRGtext(R.id.redundancy);
        adapter.config = GetRGtext(R.id.config);
        adapter.userprogram_running = GetRGtext(R.id.userprogram_running);
        adapter.scnet = GetRGtext(R.id.scnet);
        adapter.ebus = GetRGtext(R.id.ebus);
        adapter.lbus = GetRGtext(R.id.lbus);
        adapter.load = GetRGtext(R.id.load);
        adapter.cellvoltage = GetRGtext(R.id.cellvoltage);
        adapter.temperature = GetRGtext(R.id.temperature);
        adapter.io_oos = GetRGtext(R.id.io_oos);
        adapter.io_constraint = GetRGtext(R.id.io_constraint);
        adapter.analog_beyond = GetRGtext(R.id.analog_beyond);
        adapter.di_joggle = GetRGtext(R.id.di_joggle);

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
        FileUtil.saveFile(getApplicationContext(),strOut,"CONTROLLERCHECK",adapter.controller_id+".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(),mBmp,"CONTROLLERCHECK",adapter.append);
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
                RtEnv.startActivity(HisFileListActivity.class, "CONTROLLERCHECK");
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(ControllerCheckActivity.this).setTitle("系统提示")//设置对话框标题
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
