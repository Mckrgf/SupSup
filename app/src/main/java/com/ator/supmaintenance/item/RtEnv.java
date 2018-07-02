package com.ator.supmaintenance.item;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feizhenhua on 2018/2/14.
 */

public class RtEnv {
    private static Map<String, Object> mData = new HashMap<>();

    private RtEnv() {}

    public static Object get(String key) {
        return mData.get(key);
    }

    public static void put(String key, Object obj) {
        mData.put(key, obj);
    }

    /**
     * 获得当前的application
     * @return
     */
    public static Application getApplication() {
        if(get(Constant.RT_APP) != null) {
            return (Application) get(Constant.RT_APP);
        } else {
            return null;
        }
    }

    /**
     * 获得当前的activity
     * @return
     */
    public static Activity getCurrentActivity() {
        if(get(Constant.RT_CURRENT_ACTIVITY) != null) {
            return (Activity) get(Constant.RT_CURRENT_ACTIVITY);
        } else {
            return null;
        }
    }

    /**
     * 生成消息的id
     * @return
     */
    private static int MSG_ID = 0x0000;
    public static int makeID() {
        return ++MSG_ID;
    }

    /**
     * 跳转到对应页面
     * @param activity
     */
    public static void startActivity(Class activity) {
        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
     //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        RtEnv.getCurrentActivity().startActivity(intent);
    }

    public static void startActivity(Class activity, String strType){

        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
        intent.putExtra("type",strType);
        RtEnv.getCurrentActivity().startActivity(intent);
    }

    public static void startActivity(Class activity, String strType,String strName){

        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
        intent.putExtra("type",strType);
        intent.putExtra("name",strName);
        RtEnv.getCurrentActivity().startActivity(intent);
    }

    public static void startActivityForResult(Class activity, int requestCode) {
        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
    //    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        RtEnv.getCurrentActivity().startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Class activity, int requestCode, String strType) {
        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
        intent.putExtra("type",strType);
        RtEnv.getCurrentActivity().startActivityForResult(intent, requestCode);
    }

    /**
     * 显示圆角图片
     */
 /*
    public static void showImageAsRound(ImageView imageView, int resourceId) {

        Glide.with(RtEnv.getApplication()).load(resourceId).transform(new GlideRoundTransform(RtEnv.getApplication())).into(imageView);


    }
 */
}
