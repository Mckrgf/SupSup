package com.ator.supmaintenance.act;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.Constant;
import com.ator.supmaintenance.item.Employee;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.UserUtil;
import com.supcon.libzxing.activity.CaptureFragment;
import com.supcon.libzxing.activity.CodeUtils;
import com.supcon.libzxing.view.ViewfinderView;

public class QRCodeActivity extends BaseActivity implements View.OnClickListener{

    private CaptureFragment captureFragment;
    private ViewfinderView viewfinderView;

    @Override
    protected int initContentView() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void getIntentData() {
        return;
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
    protected void initView() {
        captureFragment = new CaptureFragment();

        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.view_qr_code_camera);//
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
        findViewById(R.id.iv_back).setOnClickListener(this);
        viewfinderView = (ViewfinderView)findViewById(R.id.viewfinder_view);
    }

    @Override
    protected void start() {

    }

    protected void onDestroy(){
        super.onDestroy();

    }

    protected void onPause(){
        super.onPause();

    }

    protected void onResume(){
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

//            SupApplication.ShowMsg(result);

            if (result.equals("")){
                RtEnv.startActivity(FirstActivity.class);//AllTaskActivity
                QRCodeActivity.this.finish();
            }else {
                boolean fd= false;
                Employee e = null;
                for (String key: UserUtil.staff.keySet()){
                     e = UserUtil.staff.get(key);
                    if(result.indexOf(e.name) >=0){
                        UserUtil.mCurEmployee = e;
                        fd = true;
                        break;
                    }
                }
                if (fd){
                    Toast.makeText(getApplicationContext(),e.name,Toast.LENGTH_SHORT).show();
                    RtEnv.startActivity(FirstActivity.class);
                    QRCodeActivity.this.finish();
                } else
                {
                    result.toLowerCase();

                    alertText("提示","未知二维码:"+ result);


                }

            }

        }

        @Override
        public void onAnalyzeFailed() {
            /*Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            QRCodeActivity.this.setResult(RESULT_OK, resultIntent);
            QRCodeActivity.this.finish();*/

            RtEnv.startActivity(FirstActivity.class);//AllTaskActivity
            QRCodeActivity.this.finish();
        }
    };
}
