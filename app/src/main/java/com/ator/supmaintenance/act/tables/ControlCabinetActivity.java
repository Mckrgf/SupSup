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
import com.ator.supmaintenance.adapter.ControlCabinetAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONObject;

public class ControlCabinetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public ControlCabinetAdapter adapter = new ControlCabinetAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_cabinet);

        mivAdd = (ImageView) findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("控制柜");

        loadTmpfile();

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_control_cabinet;
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


    private String mTmpSub = "TEMP-CONTROLCABINET";

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

                ((EditText) findViewById(R.id.cabinet_name)).setText(obj.getString("cabinet_id"));

                SetRGtext(R.id.neatly_cabled, obj.getString("neatly_cabled"));
                SetRGtext(R.id.line_identified, obj.getString("line_identified"));
                SetRGtext(R.id.use_line_nose, obj.getString("use_line_nose"));
                SetRGtext(R.id.layer_grounding, obj.getString("layer_grounding"));
                SetRGtext(R.id.shielded_cable_one, obj.getString("shielded_cable_one"));
                SetRGtext(R.id.shielded_cable_two, obj.getString("shielded_cable_two"));
                SetRGtext(R.id.separate_line_one, obj.getString("separate_line_one"));
                SetRGtext(R.id.separate_line_two, obj.getString("separate_line_two"));
                SetRGtext(R.id.sbus_contact, obj.getString("sbus_contact"));
                SetRGtext(R.id.db_contact, obj.getString("db_contact"));
                SetRGtext(R.id.distribution_contact, obj.getString("distribution_contact"));
                SetRGtext(R.id.power_contact, obj.getString("power_contact"));
                SetRGtext(R.id.plague, obj.getString("plague"));

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

        ((EditText) findViewById(R.id.cabinet_id)).setText("");

        SetRGtext(R.id.neatly_cabled,           "");
        SetRGtext(R.id.line_identified,         "");
        SetRGtext(R.id.use_line_nose,           "");
        SetRGtext(R.id.layer_grounding,         "");
        SetRGtext(R.id.shielded_cable_one,      "");
        SetRGtext(R.id.shielded_cable_two,      "");
        SetRGtext(R.id.separate_line_one,       "");
        SetRGtext(R.id.separate_line_two,       "");
        SetRGtext(R.id.sbus_contact,            "");
        SetRGtext(R.id.db_contact,              "");
        SetRGtext(R.id.distribution_contact,    "");
        SetRGtext(R.id.power_contact,           "");
        SetRGtext(R.id.plague,                  "");

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
        adapter.cabinet_id = ((EditText) findViewById(R.id.cabinet_id)).getText().toString().trim();

        adapter.neatly_cabled = GetRGtext(R.id.neatly_cabled);
        adapter.line_identified = GetRGtext(R.id.line_identified);
        adapter.use_line_nose = GetRGtext(R.id.use_line_nose);
        adapter.layer_grounding = GetRGtext(R.id.layer_grounding);
        adapter.shielded_cable_one = GetRGtext(R.id.shielded_cable_one);
        adapter.shielded_cable_two = GetRGtext(R.id.shielded_cable_two);
        adapter.separate_line_one = GetRGtext(R.id.separate_line_one);
        adapter.separate_line_two = GetRGtext(R.id.separate_line_two);
        adapter.sbus_contact = GetRGtext(R.id.sbus_contact);
        adapter.db_contact = GetRGtext(R.id.db_contact);
        adapter.distribution_contact = GetRGtext(R.id.distribution_contact);
        adapter.power_contact = GetRGtext(R.id.power_contact);
        adapter.plague = GetRGtext(R.id.plague);

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

    private static final String TAG_CAB = "CONTROLCABINET";

    private boolean SubMit() {

        adapter.MakeJasonObj();
        String strOut = adapter.GetJasonString();
        //
        FileUtil.saveFile(getApplicationContext(), strOut, "TAG_CAB", adapter.room_id + ".f");
        if (mBmp != null) {
            FileUtil.saveImgFile(getApplicationContext(), mBmp, "TAG_CAB", adapter.append);
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
                RtEnv.startActivity(HisFileListActivity.class, "CONTROLCABINET");
                finish();
            }
            break;
            case R.id.im_erase: {
                new AlertDialog.Builder(ControlCabinetActivity.this).setTitle("系统提示")//设置对话框标题
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
