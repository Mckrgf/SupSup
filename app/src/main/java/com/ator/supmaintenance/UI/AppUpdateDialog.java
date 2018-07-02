package com.ator.supmaintenance.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.FTP.FtpDownLoadFileTask;
import com.ator.supmaintenance.FTP.FtpHelper;
import com.ator.supmaintenance.FTP.FtpUploadTask;
import com.ator.supmaintenance.FTP.ftpNetCallBack;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by feizhenhua on 2018/5/7.
 */

public class AppUpdateDialog extends ProgressDialog implements ftpNetCallBack {


    public  String  strOutput;
    public  String  strAPKName ="";


    private int      updatestep = 0;
    private boolean  mbVersioncheckpassed = false;

    private int     nProcess = 0;
    private long    mAPKFileLen = 0;

    private Handler mTimerHandler = null;

    //当前本地路径
    private String localPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    //ftp工具类
    private FtpHelper ftp;
    private String currentFtpPath = FtpHelper.REMOTE_PATH;
    private boolean bAPKExist = false;
    private boolean bApkVersionExist = false;


    public AppUpdateDialog(Context context){
        super(context);
    }

    public AppUpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {//开启

        initFtp();
        super.show();
    }
    @Override
    public void dismiss() {//关闭
        super.dismiss();
        closeMyTimer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ftp != null) {
                    try {
                        ftp.closeConnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());

    }

    private void getDownloadProcess(){
        if (updatestep !=2){
            nProcess = 0;
            return;
        }
        File file = new File(localPath+ File.separator + strAPKName);

        long sz = file.length();

        float np =100 * (float) sz/(float)mAPKFileLen;
        nProcess = (int)np;
    }


    private void setMyTimer() {

        mTimerHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                        mTimerHandler.removeMessages(0);
                        // 再次发出msg，循环更新
                        mTimerHandler.sendEmptyMessageDelayed(0, 1000);
                        getDownloadProcess();
                        setProgress(nProcess);
                        break;
                    case 1:
                        // 直接移除，定时器停止
                        mTimerHandler.removeMessages(0);
                        break;

                    default:
                        break;
                }
            }

        };
        mTimerHandler.sendEmptyMessage(0);
    }

    private void closeMyTimer() {

        if (mTimerHandler != null)   mTimerHandler.sendEmptyMessage(1);

    }


    private void init(Context context) {

        setMyTimer();
        mbVersioncheckpassed = false;
        updatestep = 0;
        bApkVersionExist = false;
        bAPKExist = false;
        strOutput = "";
        strAPKName = "";
        nProcess =0;
    }


    static public String strAPKPath = "";
    //下载
    public void downLoadFinish(boolean result){

        if(result == false){
            strOutput = "下载失败";
            strAPKPath = "";
            dismiss();
            return;
        }

        //versioncheck
        if (updatestep == 1){
            File filefromftp = new File(localPath+ File.separator + "apkversion.txt");
            String ftpVersion = FileUtil.getFileString(filefromftp);
            String NowVersion = "";
            File filelocal = new File(localPath+ File.separator + "apkversion_now.txt");
            if (filelocal.exists()) {
                NowVersion  = FileUtil.getFileString(filelocal);
            }
            ftpVersion.toLowerCase().trim();
            NowVersion.toLowerCase().trim();

            if (NowVersion.compareTo(ftpVersion) ==0){
                strOutput = "APP 已是最新版本了";
                dismiss();
                return;
            }else {
                downLoadAPKFile();
            }

        }else if (updatestep == 2){

            strOutput = "";
            strAPKPath = localPath+ File.separator + strAPKName;

            File filefromftp = new File(localPath+ File.separator + "apkversion.txt");
            File filelocal = new File(localPath+ File.separator + "apkversion_now.txt");
            filefromftp.renameTo(filelocal);
            filefromftp.delete();
            dismiss();

        }else {
            strOutput = "下载失败";
            strAPKPath = "";
            dismiss();
        }

    }

    //上传
    public void uploadFinish(boolean result){

    }

    public void getFtpFileList(List<FTPFile> ftpFileList){

    }


    private void initFtp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ftp == null) {

                    try {
                        ftp = new FtpHelper();
                        ftp.openConnect();
                        //更新ftp文件列表
                        updateFtpList();
                        downLoadVersionFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //更新当前ftp文件夹下文件列表
    private void updateFtpList() {

        try {
            List<FTPFile> list = ftp.listFiles(currentFtpPath);

            for (FTPFile ff:list){

                Log.i("ftpfile",ff.getName());

                String strName =ff.getName().toLowerCase().trim();
                String strAppend = strName.substring(strName.length()-3,strName.length());
                strAppend.toLowerCase();
                if (strAppend.equals("apk")){
                    bAPKExist = true;
                    strAPKName = ff.getName();
                    mAPKFileLen = ff.getSize();
                }else{
                    if (strName.equals("apkversion.txt")) {
                        bApkVersionExist = true;
                    }
                }

                if (bApkVersionExist && bAPKExist){
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //上传
    private void upload(String localFilePath) {
        new FtpUploadTask(ftp, this, localFilePath, currentFtpPath).execute();
    }

    //下载文件
    private void downLoadFile(String fileName) {
        new FtpDownLoadFileTask(ftp, this, currentFtpPath, fileName, localPath).execute();
    }

    private void downLoadVersionFile(){
        updatestep = 1;

        if (bApkVersionExist){
            downLoadFile("apkversion.txt");
        }else
        {
            strOutput = "无法获取版本信息，更新失败";
            dismiss();
        }

    }

    private void downLoadAPKFile(){

        updatestep =2;

        if (bAPKExist){
            downLoadFile(strAPKName);
        }else{
            strOutput = "无法获取APK，更新失败";
            dismiss();
        }
    }

}
