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
import com.ator.supmaintenance.adapter.RoomCorrosionDetectionAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONObject;

public class RoomCorrosionDetectionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mivAdd;

    public RoomCorrosionDetectionAdapter adapter = new RoomCorrosionDetectionAdapter();
    private boolean do_not_save_file = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_room_corrosion_detection);

        mivAdd = (ImageView) findViewById(R.id.iv_add1);

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.iv_add1).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.btn_cphis).setOnClickListener(this);
        findViewById(R.id.im_erase).setOnClickListener(this);

        TextView tv_cpName = findViewById(R.id.tv_cpName);
        tv_cpName.setText("控制室腐蚀检测");

        loadTmpfile();

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_room_corrosion_detection;
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
        if (!do_not_save_file) {
            saveTmpfile();
        }
    }


    private String mTmpSub = "TEMP-ROOMCORROSIONDETECTION";

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
        do_not_save_file = true;
    }

    private void loadTmpfile() {

        try {
            String result = FileUtil.getFile(getApplicationContext(), mTmpSub, "temp.tmp");
            if (result != null) {
                JSONObject obj = new JSONObject(result);

                ((EditText) findViewById(R.id.room_name )).setText(obj.getString("room_name"  ));

                SetRGtext(R.id.has_sharp_aroma         , obj.getString("has_sharp_aroma"          ));
                SetRGtext(R.id.has_corrosive_odor      , obj.getString("has_corrosive_odor"       ));
                SetRGtext(R.id.has_internal_device     , obj.getString("has_internal_device"      ));
                SetRGtext(R.id.has_two_gate            , obj.getString("has_two_gate"             ));
                SetRGtext(R.id.has_window              , obj.getString("has_window"               ));
                SetRGtext(R.id.has_entry_hole          , obj.getString("has_entry_hole"           ));
                SetRGtext(R.id.has_air_conditioner     , obj.getString("has_air_conditioner"      ));

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

        ((EditText) findViewById(R.id.room_name )).setText("");

        SetRGtext(R.id.has_sharp_aroma         ,         "");
        SetRGtext(R.id.has_corrosive_odor      ,         "");
        SetRGtext(R.id.has_entry_hole          ,         "");
        SetRGtext(R.id.has_internal_device     ,         "");
        SetRGtext(R.id.has_two_gate            ,         "");
        SetRGtext(R.id.has_window              ,         "");
        SetRGtext(R.id.has_air_conditioner     ,         "");

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
        adapter.room_name    = ((EditText) findViewById(R.id.room_name    )).getText().toString().trim();

        adapter.has_sharp_aroma         = GetRGtext(R.id.has_sharp_aroma         );
        adapter.has_corrosive_odor      = GetRGtext(R.id.has_corrosive_odor      );
        adapter.has_internal_device     = GetRGtext(R.id.has_internal_device     );
        adapter.has_two_gate            = GetRGtext(R.id.has_two_gate            );
        adapter.has_window              = GetRGtext(R.id.has_window              );
        adapter.has_entry_hole          = GetRGtext(R.id.has_entry_hole          );
        adapter.has_air_conditioner     = GetRGtext(R.id.has_air_conditioner     );

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

    private static final String TAG_CAB = "ROOMCORROSIONDETECTION";

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
                new AlertDialog.Builder(RoomCorrosionDetectionActivity.this).setTitle("系统提示")//设置对话框标题
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
