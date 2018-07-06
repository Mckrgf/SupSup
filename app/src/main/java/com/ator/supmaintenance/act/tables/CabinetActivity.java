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
import com.ator.supmaintenance.adapter.CabinetAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONObject;

public class CabinetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public CabinetAdapter adapter = new CabinetAdapter();
    private boolean do_not_save_file = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);

        mivAdd = (ImageView)findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("控制柜供电");

        loadTmpfile();

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_cabinet;
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


    private String  mTmpSub = "TEMP-CABINET";
    private void saveTmpfile(){

        setAdapter();//
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

                SetRGtext(R.id.twinsupply,obj.getString("twinsupply"));
                SetRGtext(R.id.diameter_beyond,obj.getString("diameter_beyond"));

                ((EditText)findViewById(R.id.system_a_input)).setText(obj.getString("system_a_input"));
                ((EditText)findViewById(R.id.system_b_input)).setText(obj.getString("system_b_input"));
                ((EditText)findViewById(R.id.power1)).setText(obj.getString("power1"));
                ((EditText)findViewById(R.id.power2)).setText(obj.getString("power2"));
                ((EditText)findViewById(R.id.power3)).setText(obj.getString("power3"));
                ((EditText)findViewById(R.id.power4)).setText(obj.getString("power4"));
                ((EditText)findViewById(R.id.power5)).setText(obj.getString("power5"));
                ((EditText)findViewById(R.id.power6)).setText(obj.getString("power6"));

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

        SetRGtext(R.id.twinsupply,"");
        SetRGtext(R.id.diameter_beyond,"");

        ((EditText)findViewById(R.id.system_a_input)).setText("");
        ((EditText)findViewById(R.id.system_b_input)).setText("");
        ((EditText)findViewById(R.id.power1)).setText("");
        ((EditText)findViewById(R.id.power2)).setText("");
        ((EditText)findViewById(R.id.power3)).setText("");
        ((EditText)findViewById(R.id.power4)).setText("");
        ((EditText)findViewById(R.id.power5)).setText("");
        ((EditText)findViewById(R.id.power6)).setText("");

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

        adapter.twinsupply = GetRGtext(R.id.twinsupply);
        adapter.diameter_beyond = GetRGtext(R.id.diameter_beyond);

        adapter.system_a_input = ((EditText)findViewById(R.id.system_a_input)).getText().toString().trim();
        adapter.system_b_input = ((EditText)findViewById(R.id.system_b_input)).getText().toString().trim();
        adapter.power1 = ((EditText)findViewById(R.id.power1)).getText().toString().trim();
        adapter.power2 = ((EditText)findViewById(R.id.power2)).getText().toString().trim();
        adapter.power3 = ((EditText)findViewById(R.id.power3)).getText().toString().trim();
        adapter.power4 = ((EditText)findViewById(R.id.power4)).getText().toString().trim();
        adapter.power5 = ((EditText)findViewById(R.id.power5)).getText().toString().trim();
        adapter.power6 = ((EditText)findViewById(R.id.power6)).getText().toString().trim();

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
        FileUtil.saveFile(getApplicationContext(),strOut,"CABINET",adapter.room_id+".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(),mBmp,"CABINET",adapter.append);
        }

        boolean bOK = false;
        String strUpload = adapter.GetNetJasonString();
        if (mNetState != MyNetHelper.NETWORK_NONE){
            postJasonString(adapter.strURL,strUpload,5000,201,true,"上传","上传中...");

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
                if (CheckAllOK()) {
                    SubMit();
                } else {
                    Toast.makeText(this, "请完整录入", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_cphis: {
                RtEnv.startActivity(HisFileListActivity.class, "CABINET");
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(CabinetActivity.this).setTitle("系统提示")//设置对话框标题
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
