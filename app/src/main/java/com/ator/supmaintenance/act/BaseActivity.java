package com.ator.supmaintenance.act;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.Constant;
import com.ator.supmaintenance.UI.GlideRoundTransform;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.MyPhoto;
import com.ator.supmaintenance.item.RtEnv;
import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent = null;

    private IntentFilter tagDetected = null;
    private IntentFilter[] intentFiltersArray;
    String[] [] techListsArray;


    public boolean bUploadOK = false;
    public Handler mPostHandler = null;
    public Handler mGetHandler = null;

    public int mNetState = MyNetHelper.NETWORK_NONE;

    public String mStrGet;


    public void alertText(final String title, final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dlg_alert, null);
        //设置对话框布局
        dialog.setView(dialogView);

        TextView tvTitle = (TextView)dialogView.findViewById(R.id.tv_title);
        TextView tvResult = (TextView)dialogView.findViewById(R.id.tv_result);

        tvTitle.setText(title);
        tvResult.setText(message);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNFC();

    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this,MyNFCActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);//
        tagDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);//ACTION_TECH_DISCOVERED
        try {
            tagDetected.addDataType("*/*");
        }

        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] { tagDetected, };
        techListsArray = new String[][] {
                new String[] { NfcF.class.getName() },
                new String[] { NfcA.class.getName() },
                new String[] { NfcB.class.getName() },
                new String[] { NfcV.class.getName() },
                new String[] { Ndef.class.getName() },
                new String[] { IsoDep.class.getName()},
                new String[] { MifareClassic.class.getName() },
                new String[] { MifareUltralight.class.getName()}};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        RtEnv.put(Constant.RT_CURRENT_ACTIVITY, this);

        getSupportActionBar().hide();
        //界面初始化
        setContentView(initContentView());
        //从Intent中获取数据
        getIntentData();
        //控件初始化
        initView();

        initNet();;

        //开始处理
        start();

    }



    @Override
    protected void onResume() {
        super.onResume();
        RtEnv.put(Constant.RT_CURRENT_ACTIVITY, this);

        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, intentFiltersArray,techListsArray);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //恢复默认状态
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
    }

    /** 界面初始化，返回布局id即可 */
    protected abstract int initContentView();

    /** 从Intent中获取数据 */
    protected abstract void getIntentData();

    /** 控件初始化 */
    protected abstract void initView();

    /** 初始化完成后的处理 */
    protected abstract void start();

    /** 网络post成功 */
    protected abstract void onPostSucc();

    /** 网络post失败 */
    protected abstract void onPostFaile();

    /** 网络get成功 */
    protected abstract void onGetSucc();

    /** 网络get失败 */
    protected abstract void onGetFaile();

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public  Uri mImageUri;
    public  String mtempPicpath;
    public  File mFilePic;
    public  Bitmap mBmp = null;

    public void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mFilePic = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
        if(mFilePic.exists()){
            mFilePic.delete();
        }

        mtempPicpath =mFilePic.getAbsolutePath();

        mImageUri = Uri.fromFile(mFilePic);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mBmp = MyPhoto.loadBmp(mFilePic);
        }
    }

    public String GetRGtext(@IdRes int id) {

        RadioGroup rg = (RadioGroup)findViewById(id);
        for (int i=0;i<rg.getChildCount();i++){
            RadioButton rb = (RadioButton)rg.getChildAt(i);
            if (rb.isChecked()) {
                return rb.getText().toString();
            }
        }

        return "";
    }

    public void SetRGtext(@IdRes int id,String text) {

        RadioGroup rg = (RadioGroup)findViewById(id);
        rg.clearCheck();

        if (text.equals("")){
            return;
        }

        for (int i=0;i<rg.getChildCount();i++){
            RadioButton rb = (RadioButton)rg.getChildAt(i);
            if (rb.getText().equals(text)) {
                rg.check(rb.getId());
                return;
            }
        }

        return;
    }

    public void showCPImagDialog(final Bitmap bmp){

        Dialog dialog = new Dialog(this){
            private View.OnClickListener mOnClickListener;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dlg_showcpimg);

                mOnClickListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        switch (view.getId())
                        {
                            default:
                                dismiss();
                                break;
                        }
                    }
                };
                ImageView imv = (ImageView) findViewById(R.id.img_cpAdd1);
                imv.setOnClickListener(mOnClickListener);

                if (bmp!=null) {
                    imv.setImageBitmap(bmp);
                }

            }
            @Override
            public void onBackPressed() {//在setCanclable为false的情况下，重写onBackPressed方法还是能够成功获取到回退事件的
                super.onBackPressed();
                dismiss();
            }
        };

        dialog.show();
    }

    public static final int     WM_POST_OK = 0x10;
    public static final int     WM_POST_FAIL = 0x20;
    public static final int     WM_GET_OK = 0x30;
    public static final int     WM_GET_FAIL = 0x40;


    public boolean postJasonString(final String addr, final String jStr, final int nTimeout,final int wantcode, final boolean bShow,String title,String showstr){

        final ProgressDialog mpd = new ProgressDialog(this);

        if (mPostHandler == null){
            mPostHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what){
                        case WM_POST_OK: {
                            onPostSucc();
                        }
                        break;
                        case WM_POST_FAIL: {
                            onPostFaile();
                        }
                        break;
                    }
                    return false;
                }
            });
        }

        final long starttm  = System.currentTimeMillis();

        bUploadOK = false;

        mpd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        mpd.setCancelable(false);// 设置是否可以通过点击Back键取消
        mpd.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条

        mpd.setTitle(title);
        mpd.setMessage(showstr);
        if (bShow){
            mpd.show();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean bsucc = false;
                try {

                    byte[] data = jStr.getBytes();

                    String strUrl = addr;
                    URL url = new URL(strUrl);//
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setConnectTimeout(nTimeout);
                    conn.setReadTimeout(nTimeout);
                    conn.setRequestProperty("Charset", "utf-8");
                    conn.setUseCaches(false);               //使用Post方式不能使用缓存
                    //设置请求体的类型是文本类型
                    conn.setRequestProperty("Content-Type", "application/json");
                    //设置请求体的长度
                    conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                    //获得输出流，向服务器写入数据
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(data);

                    int response = conn.getResponseCode();            //获得服务器的响应码
                    if(response == wantcode) {

                        InputStream inptStream = conn.getInputStream();
                        bsucc = true;
                        String result = "";
                        BufferedReader br = null;
                        br = new BufferedReader(new InputStreamReader(inptStream));
                        String line = null;
                        while((line = br.readLine())!=null){
                            result+=line;
                        }
                        br.close();
                        mStrGet = result;

                    }else {
                        int i =response;
                        Log.i("res","rescode = "+i);
                        bsucc = false;

                    }
                    long end  = System.currentTimeMillis();
                    int  nDt = (int)(end - starttm);
                    if (nDt < 300){
                        Thread.currentThread().sleep(300 - nDt);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message msg = new Message();
                if (bsucc){
                    msg.what = WM_POST_OK;
                }else {
                    msg.what = WM_POST_FAIL;
                }
                mPostHandler.sendMessage(msg);
                if (bShow){
                    mpd.dismiss();
                }

            }
        }).start();

        return bUploadOK;
    }

    public boolean getJasonString(final String addr, final int nTimeout, final int wantcode, final boolean bShow,String sTitle,String msgShow ){

        mStrGet = "";
        final ProgressDialog mpd = new ProgressDialog(this);

        if (mGetHandler == null){
            mGetHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what){
                        case WM_GET_OK:
                        {
                            onGetSucc();
                        }
                        break;
                        case WM_GET_FAIL:
                        {
                            onGetFaile();
                        }
                        break;
                    }
                    return true;
                }
            });
        }

        final long starttm  = System.currentTimeMillis();

        mpd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        mpd.setCancelable(false);// 设置是否可以通过点击Back键取消
        mpd.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条


        mpd.setTitle(sTitle);
        mpd.setMessage(msgShow);
        if (bShow){
            mpd.show();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean bsucc = false;
                try {

                    URL url = new URL(addr);//
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestProperty("Charset", "utf-8");

                    InputStream in = conn.getInputStream();

                    String result = "";
                    BufferedReader br = null;
                    br = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while((line = br.readLine())!=null){
                        result+=line;
                    }
                    br.close();

                    int response = conn.getResponseCode();            //获得服务器的响应码
                    if(response == wantcode) {
                        mStrGet = result;
                        bsucc = true;

                    }else {
                        int i =response;
                        Log.i("res","rescode = "+i);
                        bsucc = false;

                    }
                    long end  = System.currentTimeMillis();
                    int  nDt = (int)(end - starttm);
                    if (nDt < 300){
                        Thread.currentThread().sleep(300 - nDt);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message msg = new Message();
                if (bsucc){
                    msg.what = WM_GET_OK;
                }else {
                    msg.what = WM_GET_FAIL;
                }
                mGetHandler.sendMessage(msg);

                if (bShow){
                    mpd.dismiss();
                }


            }
        }).start();

        return true;
    }

    //net state
    public BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mNetState = MyNetHelper.getNetWorkState(context);
        }
    };

    public void initNet(){

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
    }
    /**
     * 弹出对话框
     */
    protected void showDialog(String message) {

    }

    /**
     * 对话框消失
     */
    protected void dismissDialog() {

    }

    /**
     * 显示动态图片
     */
    protected void showImageAsGif(ImageView imageView, String path) {
        Glide.with(this).load(path).asGif().into(imageView);
    }

    /**
     * 显示动态图片
     */
    protected void showImageAsGif(ImageView imageView, int resourceId) {
        Glide.with(this).load(resourceId).asGif().into(imageView);
    }

    /**
     * 显示图片
     */
    protected void showImage(ImageView imageView, String path) {
        Glide.with(this).load(path).into(imageView);
    }

    /**
     * 显示圆角图片
     */
    protected void showImageAsRound(ImageView imageView, String path) {
        Glide.with(this).load(path).transform(new GlideRoundTransform(this)).into(imageView);
    }

    /**
     * 显示圆角图片
     */
    protected void showImageAsRound(ImageView imageView, int resourceId) {
        Glide.with(this).load(resourceId).transform(new GlideRoundTransform(this)).into(imageView);
    }
}
