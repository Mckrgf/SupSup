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
import com.ator.supmaintenance.adapter.OperatingStationAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONObject;

public class OperatingStationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public OperatingStationAdapter adapter = new OperatingStationAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_operating_station);

        mivAdd = (ImageView) findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("操作站");

        loadTmpfile();

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_operating_station;
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
    protected void onPostSucc() {

        alertText("上传", "上传成功");
        Clearall();
    }

    @Override
    protected void onPostFaile() {

        alertText("上传", "上传失败");

    }

    @Override
    protected void onGetSucc() {

    }

    @Override
    protected void onGetFaile() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveTmpfile();
    }


    private String mTmpSub = "TEMP-OPERATINGSTATION";

    private void saveTmpfile() {

        setAdapter();//
        adapter.MakeJasonObj();
        String strOut = adapter.GetJasonString();
        //
        FileUtil.saveFile(getApplicationContext(), strOut, mTmpSub, "temp.tmp");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(), mBmp, mTmpSub, "temp.png");
        }
    }

    private void deleteTmpFile() {

        FileUtil.deleteFile(getApplicationContext(), mTmpSub, "temp.tmp");
        FileUtil.deleteFile(getApplicationContext(), mTmpSub, "temp.png");
    }

    private void loadTmpfile() {

        try {
            String result = FileUtil.getFile(getApplicationContext(), mTmpSub, "temp.tmp");
            if (result != null) {
                JSONObject obj = new JSONObject(result);

                ((EditText) findViewById(R.id.control_room_name )).setText(obj.getString("control_room_name"  ));
                ((EditText) findViewById(R.id.compute_name )).setText(obj.getString("compute_name"  ));
                ((EditText) findViewById(R.id.soft_version )).setText(obj.getString("soft_version"  ));
                ((EditText) findViewById(R.id.patch_version)).setText(obj.getString("patch_version" ));
                ((EditText) findViewById(R.id.os_version   )).setText(obj.getString("os_version"    ));
                ((EditText) findViewById(R.id.os_patch     )).setText(obj.getString("os_patch"      ));

                SetRGtext(R.id.has_driver             , obj.getString("has_driver"            ));
                SetRGtext(R.id.has_virtual_memory     , obj.getString("has_virtual_memory"    ));
                SetRGtext(R.id.net_close              , obj.getString("net_close"             ));
                SetRGtext(R.id.binuclear_close        , obj.getString("binuclear_close"       ));
                SetRGtext(R.id.attribute_setting      , obj.getString("attribute_setting"     ));
                SetRGtext(R.id.power_manage           , obj.getString("power_manage"          ));
                SetRGtext(R.id.fault_diagnosis_normal , obj.getString("fault_diagnosis_normal"));
                SetRGtext(R.id.status                 , obj.getString("status"                ));
                SetRGtext(R.id.time_setting           , obj.getString("time_setting"          ));
                SetRGtext(R.id.soft_dog               , obj.getString("soft_dog"              ));
                SetRGtext(R.id.capacity_normal        , obj.getString("capacity_normal"       ));
                SetRGtext(R.id.d_dcsdata              , obj.getString("d_dcsdata"             ));
                SetRGtext(R.id.keyborad_normal        , obj.getString("keyborad_normal"       ));
                SetRGtext(R.id.mouse                  , obj.getString("mouse"                 ));
                SetRGtext(R.id.cd_drive               , obj.getString("cd_drive"              ));

                ((EditText) findViewById(R.id.suggestion)).setText(obj.getString("suggestion"));

                mBmp = FileUtil.loadBmp(getApplicationContext(), mTmpSub, "temp.png");
                if (mBmp != null) {
                    mivAdd.setImageBitmap(mBmp);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }


    private void Clearall() {

        ((EditText) findViewById(R.id.control_room_name )).setText("");
        ((EditText) findViewById(R.id.compute_name )).setText("");
        ((EditText) findViewById(R.id.soft_version )).setText("");
        ((EditText) findViewById(R.id.patch_version)).setText("");
        ((EditText) findViewById(R.id.os_version   )).setText("");
        ((EditText) findViewById(R.id.os_patch     )).setText("");

        SetRGtext(R.id.has_driver            ,         "");
        SetRGtext(R.id.has_virtual_memory    ,         "");
        SetRGtext(R.id.net_close             ,         "");
        SetRGtext(R.id.binuclear_close       ,         "");
        SetRGtext(R.id.attribute_setting     ,         "");
        SetRGtext(R.id.power_manage          ,         "");
        SetRGtext(R.id.fault_diagnosis_normal,         "");
        SetRGtext(R.id.status                ,         "");
        SetRGtext(R.id.time_setting          ,         "");
        SetRGtext(R.id.soft_dog              ,         "");
        SetRGtext(R.id.capacity_normal       ,         "");
        SetRGtext(R.id.d_dcsdata             ,         "");
        SetRGtext(R.id.keyborad_normal       ,         "");
        SetRGtext(R.id.mouse                 ,         "");
        SetRGtext(R.id.cd_drive              ,         "");

        ((EditText) findViewById(R.id.suggestion)).setText("");

        mBmp = null;
        mivAdd.setImageBitmap(null);
        deleteTmpFile();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mBmp != null) {
            mivAdd.setImageBitmap(mBmp);
        }
    }

    private void setAdapter() {
        adapter.control_room_name    = ((EditText) findViewById(R.id.control_room_name    )).getText().toString().trim();
        adapter.compute_name    = ((EditText) findViewById(R.id.compute_name    )).getText().toString().trim();
        adapter.soft_version    = ((EditText) findViewById(R.id.soft_version    )).getText().toString().trim();
        adapter.patch_version   = ((EditText) findViewById(R.id.patch_version   )).getText().toString().trim();
        adapter.os_version      = ((EditText) findViewById(R.id.os_version      )).getText().toString().trim();
        adapter.os_patch        = ((EditText) findViewById(R.id.os_patch        )).getText().toString().trim();

        adapter.has_driver             = GetRGtext(R.id.has_driver             );
        adapter.has_virtual_memory     = GetRGtext(R.id.has_virtual_memory     );
        adapter.net_close              = GetRGtext(R.id.net_close              );
        adapter.binuclear_close        = GetRGtext(R.id.binuclear_close        );
        adapter.attribute_setting      = GetRGtext(R.id.attribute_setting      );
        adapter.power_manage           = GetRGtext(R.id.power_manage           );
        adapter.fault_diagnosis_normal = GetRGtext(R.id.fault_diagnosis_normal );
        adapter.status                 = GetRGtext(R.id.status                 );
        adapter.time_setting           = GetRGtext(R.id.time_setting           );
        adapter.soft_dog               = GetRGtext(R.id.soft_dog               );
        adapter.capacity_normal        = GetRGtext(R.id.capacity_normal        );
        adapter.d_dcsdata              = GetRGtext(R.id.d_dcsdata              );
        adapter.keyborad_normal        = GetRGtext(R.id.keyborad_normal        );
        adapter.mouse                  = GetRGtext(R.id.mouse                  );
        adapter.cd_drive               = GetRGtext(R.id.cd_drive               );

        adapter.suggestion = ((EditText) findViewById(R.id.suggestion)).getText().toString().trim();

        boolean bAppend = false;
        if (mBmp != null) {
            bAppend = true;
        }
        adapter.MakeID(bAppend);
    }

    private boolean CheckAllOK() {
        setAdapter();
        return adapter.CheckAll();
    }

    private static final String TAG_CAB = "OPERATINGSTATION";

    private boolean SubMit() {

        adapter.MakeJasonObj();
        String strOut = adapter.GetJasonString();
        //
        FileUtil.saveFile(getApplicationContext(), strOut, TAG_CAB, adapter.room_id + ".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(), mBmp, TAG_CAB, adapter.append);
        }

        boolean bOK = false;
        String strUpload = adapter.GetNetJasonString();
        if (mNetState != MyNetHelper.NETWORK_NONE) {
            postJasonString(adapter.strURL, strUpload, 5000, 201, true, "上传", "上传中...");

        } else {
            Toast.makeText(this, "未联入网络，无法上传", Toast.LENGTH_LONG);
        }

        return bOK;
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
                RtEnv.startActivity(HisFileListActivity.class, TAG_CAB);
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(OperatingStationActivity.this).setTitle("系统提示")//设置对话框标题
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
