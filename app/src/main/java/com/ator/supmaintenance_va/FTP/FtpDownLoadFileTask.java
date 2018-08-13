package com.ator.supmaintenance_va.FTP;

import android.os.AsyncTask;

/**
 * Created by feizhenhua on 2018/5/7.
 */

public class FtpDownLoadFileTask extends AsyncTask<String, Integer, Boolean> {

    //ftp工具类
    private FtpHelper ftpHelper;
    //回调
    private ftpNetCallBack callBack;
    //ftp目录
    private String ftpFolder;
    //本地文件夹
    private String localFilePath;
    //需要下载的文件
    private String fileName;


    public FtpDownLoadFileTask(FtpHelper ftpHelper, ftpNetCallBack callBack,
                               String ftpFolder, String fileName, String localFilePath) {
        this.ftpHelper = ftpHelper;
        this.callBack = callBack;
        this.ftpFolder = ftpFolder;
        this.localFilePath = localFilePath;
        this.fileName = fileName;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean result = false;


        try {
            if (ftpHelper != null && ftpHelper.isConnect()) {
                result = ftpHelper.downloadFile(ftpFolder, fileName, localFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        callBack.downLoadFinish(result);

    }
}
