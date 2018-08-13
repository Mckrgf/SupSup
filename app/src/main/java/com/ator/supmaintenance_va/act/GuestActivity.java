package com.ator.supmaintenance_va.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.adapter.GuestAdapter;
import com.ator.supmaintenance_va.item.FileUtil;
import com.ator.supmaintenance_va.item.Guest;
import com.ator.supmaintenance_va.item.GuestUtil;
import com.ator.supmaintenance_va.item.RtEnv;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;

import java.io.File;
import java.util.Date;

public class GuestActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;

    private RecyclerView mRecyclerView;
    private GuestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GuestActivity.this));
        mAdapter = new GuestAdapter();
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.ll_idcard).setOnClickListener(this);
        findViewById(R.id.ll_setting).setOnClickListener(this);

        mAdapter.setOnItemClickListener(new GuestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                Guest man = GuestUtil.mPersons.get(position);
                if (man.cardphoto != null) {
                    final Bitmap bmp = Bitmap.createBitmap(man.cardphoto);
                    showCPImagDialog(bmp);
                }else {
                    alertText("提示","无照片可查看");
                }
            }
        });

        //  初始化本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance().getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
//                        alertText("提示","初始化错误，错误原因： " + msg);
                    }
                });

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_guest;
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
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_idcard:
                recIDcardFront();
                break;
            case R.id.ll_setting:
                RtEnv.startActivity(SettingsActivity.class);
                break;
        }
    }

    public void recIDcardFront(){

        Intent intent = new Intent(GuestActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);

    }

    private void recIDCard(String idCardSide, String filePath) {
        final IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {

                    Guest man = new Guest();
                    man.name = result.getName();
                    man.idcode = result.getIdNumber();

                    File bmpFile = FileUtil.getSaveFile(getApplication());
                    String strPic = bmpFile.getPath();
                    Bitmap bmp = null;
                    try {
                        BitmapFactory.Options opt = new BitmapFactory.Options();
                        opt.inPreferredConfig = Bitmap.Config.RGB_565;
                        bmp = BitmapFactory.decodeFile(strPic, opt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (bmp != null){
                        man.cardphoto = bmp;
                    }


                    String strOut = "姓名:"+ result.getName() + ", 身份证号："+result.getIdNumber();
                    man.comeDate = new Date();

                    GuestUtil.mPersons.add(man);
                    finish();
                    RtEnv.startActivity(GuestActivity.class);

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else  {

                    }
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }
}
