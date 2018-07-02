package com.ator.supmaintenance.item;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class FileUtil {

    public static void deleteFile(Context context, String strSub, String fileName){

        String directoryPath="";

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir(strSub).getAbsolutePath();
        }else{//没外部存储就使用内部存储
            directoryPath=context.getFilesDir()+ File.separator+strSub;
        }

        File filepath = new File(directoryPath);
        if(!filepath.exists()){//判断文件目录是否存在
            return;
        }

        // 创建指定路径的文件
        File file = new File(directoryPath + File.separator + fileName);

        file.delete();

        return;

    }


    public static Bitmap loadBmp(Context context, String strSub, String fileName) {

        String directoryPath="";

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir(strSub).getAbsolutePath();
        }else{//没外部存储就使用内部存储
            directoryPath=context.getFilesDir()+ File.separator+strSub;
        }

        File filepath = new File(directoryPath);
        if(!filepath.exists()){//判断文件目录是否存在
            filepath.mkdirs();
        }

        // 创建指定路径的文件
        File bmpFile = new File(directoryPath + File.separator + fileName);

        String strPic = bmpFile.getPath();
        Bitmap bmp = null;
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            bmp = BitmapFactory.decodeFile(strPic, opt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  bmp;
    }


    public static void saveImgFile(Context context, Bitmap bmp, String strSub, String fileName) {
        // 创建String对象保存文件名路径
        try {

            String directoryPath="";

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
                directoryPath = context.getExternalFilesDir(strSub).getAbsolutePath();
            }else{//没外部存储就使用内部存储
                directoryPath=context.getFilesDir()+ File.separator+strSub;
            }

            File filepath = new File(directoryPath);
            if(!filepath.exists()){//判断文件目录是否存在
                filepath.mkdirs();
            }

            // 创建指定路径的文件
            File file = new File(directoryPath + File.separator + fileName);
            // 如果文件不存在
            if (file.exists()) {
                // 创建新的空文件
                file.delete();
            }
            file.createNewFile();
            // 获取文件的输出流对象
            FileOutputStream outStream = new FileOutputStream(file);
            // 获取字符串对象的byte数组并写入文件流
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            // 最后关闭文件输出流
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveFile(Context context, String str, String strSub, String fileName) {
        // 创建String对象保存文件名路径
        try {

            String directoryPath="";

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
                directoryPath = context.getExternalFilesDir(strSub).getAbsolutePath();
            }else{//没外部存储就使用内部存储
                directoryPath=context.getFilesDir()+ File.separator+strSub;
            }

            File filepath = new File(directoryPath);
            if(!filepath.exists()){//判断文件目录是否存在
                filepath.mkdirs();
            }

            // 创建指定路径的文件
            File file = new File(directoryPath + File.separator + fileName);
            // 如果文件不存在
            if (file.exists()) {
                // 创建新的空文件
                file.delete();
            }
            file.createNewFile();
            // 获取文件的输出流对象
            FileOutputStream outStream = new FileOutputStream(file);
            // 获取字符串对象的byte数组并写入文件流
            outStream.write(str.getBytes());
            // 最后关闭文件输出流
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取文件里面的内容
     *
     * @return
     */
    public static String getFile(Context context, String strSub, String fileName) {
        try {
            String directoryPath="";

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
                directoryPath = context.getExternalFilesDir(strSub).getAbsolutePath();
            }else{//没外部存储就使用内部存储
                directoryPath=context.getFilesDir()+ File.separator+strSub;
            }

            File filepath = new File(directoryPath);
            if(!filepath.exists()){//判断文件目录是否存在
                filepath.mkdirs();
            }

            // 创建指定路径的文件
            File file = new File(directoryPath + File.separator + fileName);
            // 创建FileInputStream对象
            FileInputStream fis = new FileInputStream(file);
            // 创建字节数组 每次缓冲1M
            byte[] b = new byte[1024];
            int len = 0;// 一次读取1024字节大小，没有数据后返回-1.
            // 创建ByteArrayOutputStream对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 一次读取1024个字节，然后往字符输出流中写读取的字节数
            while ((len = fis.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            // 将读取的字节总数生成字节数组
            byte[] data = baos.toByteArray();
            // 关闭字节输出流
            baos.close();
            // 关闭文件输入流
            fis.close();
            // 返回字符串对象
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<String> GetFileList(Context context,String strSub,String strAppend) {

        ArrayList<String> filelist = new ArrayList<String>();
        String directoryPath = "";
        try {

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
                directoryPath = context.getExternalFilesDir(strSub).getAbsolutePath();
            } else {//没外部存储就使用内部存储
                directoryPath = context.getFilesDir() + File.separator + strSub;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        File dir = new File(directoryPath);
        File[] files = dir.listFiles();

        if (files == null)
            return null;

        for (File file : files) {
                if (file.getName().toLowerCase().endsWith(strAppend)) {
                    filelist.add(file.getName());
                }
            }


        return filelist;
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }


    public static String getFileString(File file){

        try {
            FileInputStream fis = new FileInputStream(file);
            // 创建字节数组 每次缓冲1M
            byte[] b = new byte[1024];
            int len = 0;// 一次读取1024字节大小，没有数据后返回-1.
            // 创建ByteArrayOutputStream对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 一次读取1024个字节，然后往字符输出流中写读取的字节数
            while ((len = fis.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            // 将读取的字节总数生成字节数组
            byte[] data = baos.toByteArray();
            // 关闭字节输出流
            baos.close();
            // 关闭文件输入流
            fis.close();
            // 返回字符串对象
            return new String(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
