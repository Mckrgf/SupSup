package com.ator.supmaintenance.act.tables;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.act.BaseActivity;
import com.ator.supmaintenance.act.HisFileListActivity;
import com.ator.supmaintenance.adapter.CabinetAdapter;
import com.ator.supmaintenance.adapter.OpStationCheckAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONObject;

public class OpStationCheckActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    private OpStationCheckAdapter adapter = new OpStationCheckAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_station_check);

        mivAdd = (ImageView) findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("操作站检查");

        loadTmpfile();

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_op_station_check;
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

    private String mTmpSub = "TEMP-OPERATION";

    private void saveTmpfile() {

        setAdapter();
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

                ((EditText) findViewById(R.id.operator_site)).setText(obj.getString("operator_site"));
                ((EditText) findViewById(R.id.software_version)).setText(obj.getString("software_version"));
                ((EditText) findViewById(R.id.patch)).setText(obj.getString("patch"));
                ((EditText) findViewById(R.id.operatingsystem)).setText(obj.getString("operatingsystem"));
                ((EditText) findViewById(R.id.disk_freespace)).setText(obj.getString("disk_freespace"));
                ((EditText) findViewById(R.id.cpu_usingrate)).setText(obj.getString("cpu_usingrate"));
                ((EditText) findViewById(R.id.memory_total)).setText(obj.getString("memory_total"));
                ((EditText) findViewById(R.id.memory_used)).setText(obj.getString("memory_used"));

                SetRGtext(R.id.machines_timesync, obj.getString("machines_timesync"));
                SetRGtext(R.id.configserver_confsync, obj.getString("configserver_confsync"));
                SetRGtext(R.id.lower_configsync, obj.getString("lower_configsync"));
                SetRGtext(R.id.ip_addr, obj.getString("ip_addr"));
                SetRGtext(R.id.harddisk_norm, obj.getString("harddisk_norm"));
                SetRGtext(R.id.drivermax, obj.getString("drivermax"));
                SetRGtext(R.id.hyperthreading_closed, obj.getString("hyperthreading_closed"));
                SetRGtext(R.id.netcardflow_closed, obj.getString("netcardflow_closed"));
                SetRGtext(R.id.colors_show, obj.getString("colors_show"));
                SetRGtext(R.id.noothersoftware, obj.getString("noothersoftware"));
                SetRGtext(R.id.loadlist, obj.getString("loadlist"));
                SetRGtext(R.id.dataresponse, obj.getString("dataresponse"));
                SetRGtext(R.id.stationprocess, obj.getString("stationprocess"));
                SetRGtext(R.id.turnalarm, obj.getString("turnalarm"));
                SetRGtext(R.id.projectdirectory, obj.getString("projectdirectory"));
                SetRGtext(R.id.tendency, obj.getString("tendency"));
                SetRGtext(R.id.clock, obj.getString("clock"));
                SetRGtext(R.id.faultdiagnosis, obj.getString("faultdiagnosis"));
                SetRGtext(R.id.machines_configsync, obj.getString("machines_configsync"));
                SetRGtext(R.id.configissue, obj.getString("configissue"));
                SetRGtext(R.id.commuredundancy, obj.getString("commuredundancy"));

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

        ((EditText) findViewById(R.id.operator_site)).setText("");
        ((EditText) findViewById(R.id.software_version)).setText("");
        ((EditText) findViewById(R.id.patch)).setText("");
        ((EditText) findViewById(R.id.operatingsystem)).setText("");
        ((EditText) findViewById(R.id.disk_freespace)).setText("");
        ((EditText) findViewById(R.id.cpu_usingrate)).setText("");
        ((EditText) findViewById(R.id.memory_total)).setText("");
        ((EditText) findViewById(R.id.memory_used)).setText("");

        SetRGtext(R.id.machines_timesync, "");
        SetRGtext(R.id.configserver_confsync, "");
        SetRGtext(R.id.lower_configsync, "");
        SetRGtext(R.id.ip_addr, "");
        SetRGtext(R.id.harddisk_norm, "");
        SetRGtext(R.id.drivermax, "");
        SetRGtext(R.id.hyperthreading_closed, "");
        SetRGtext(R.id.netcardflow_closed, "");
        SetRGtext(R.id.colors_show, "");
        SetRGtext(R.id.noothersoftware, "");
        SetRGtext(R.id.loadlist, "");
        SetRGtext(R.id.dataresponse, "");
        SetRGtext(R.id.stationprocess, "");
        SetRGtext(R.id.turnalarm, "");
        SetRGtext(R.id.projectdirectory, "");
        SetRGtext(R.id.tendency, "");
        SetRGtext(R.id.clock, "");
        SetRGtext(R.id.faultdiagnosis, "");
        SetRGtext(R.id.machines_configsync, "");
        SetRGtext(R.id.configissue, "");
        SetRGtext(R.id.commuredundancy, "");

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

        adapter.operator_site = ((EditText) findViewById(R.id.operator_site)).getText().toString().trim();
        adapter.software_version = ((EditText) findViewById(R.id.software_version)).getText().toString().trim();
        adapter.patch = ((EditText) findViewById(R.id.patch)).getText().toString().trim();
        adapter.operatingsystem = ((EditText) findViewById(R.id.operatingsystem)).getText().toString().trim();
        adapter.disk_freespace = ((EditText) findViewById(R.id.disk_freespace)).getText().toString().trim();
        adapter.cpu_usingrate = ((EditText) findViewById(R.id.cpu_usingrate)).getText().toString().trim();
        adapter.memory_total = ((EditText) findViewById(R.id.memory_total)).getText().toString().trim();
        adapter.memory_used = ((EditText) findViewById(R.id.memory_used)).getText().toString().trim();

        adapter.machines_timesync = GetRGtext(R.id.machines_timesync);
        adapter.configserver_confsync = GetRGtext(R.id.configserver_confsync);
        adapter.lower_configsync = GetRGtext(R.id.lower_configsync);
        adapter.ip_addr = GetRGtext(R.id.ip_addr);
        adapter.harddisk_norm = GetRGtext(R.id.harddisk_norm);
        adapter.drivermax = GetRGtext(R.id.drivermax);
        adapter.hyperthreading_closed = GetRGtext(R.id.hyperthreading_closed);
        adapter.netcardflow_closed = GetRGtext(R.id.netcardflow_closed);
        adapter.colors_show = GetRGtext(R.id.colors_show);
        adapter.noothersoftware = GetRGtext(R.id.noothersoftware);
        adapter.loadlist = GetRGtext(R.id.loadlist);
        adapter.dataresponse = GetRGtext(R.id.dataresponse);
        adapter.stationprocess = GetRGtext(R.id.stationprocess);
        adapter.turnalarm = GetRGtext(R.id.turnalarm);
        adapter.projectdirectory = GetRGtext(R.id.projectdirectory);
        adapter.tendency = GetRGtext(R.id.tendency);
        adapter.clock = GetRGtext(R.id.clock);
        adapter.faultdiagnosis = GetRGtext(R.id.faultdiagnosis);
        adapter.machines_configsync = GetRGtext(R.id.machines_configsync);
        adapter.configissue = GetRGtext(R.id.configissue);
        adapter.commuredundancy = GetRGtext(R.id.commuredundancy);

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

    private boolean SubMit() {

        adapter.MakeJasonObj();
        String strOut = adapter.GetJasonString();
        //
        FileUtil.saveFile(getApplicationContext(), strOut, "OPSTATIONCHECK", adapter.operator_id + ".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(), mBmp, "OPSTATIONCHECK", adapter.append);
        }

        boolean bOK = false;
        String strUpload = adapter.GetNetJasonString();
        if (mNetState != MyNetHelper.NETWORK_NONE) {
            bOK = postJasonString(adapter.strURL, strUpload, 5000, 201, true, "上传", "上传中...");

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
                RtEnv.startActivity(HisFileListActivity.class, "OPSTATIONCHECK");
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(OpStationCheckActivity.this).setTitle("系统提示")//设置对话框标题
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
