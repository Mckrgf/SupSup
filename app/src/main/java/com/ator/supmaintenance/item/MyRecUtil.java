package com.ator.supmaintenance.item;

import android.content.Context;
import android.content.res.Resources;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.adapter.CabinetAdapter;
import com.ator.supmaintenance.adapter.ControllerCheckAdapter;
import com.ator.supmaintenance.adapter.GroundCheckAdapter;
import com.ator.supmaintenance.adapter.OpStationCheckAdapter;
import com.ator.supmaintenance.adapter.PowerAllAdapter;
import com.ator.supmaintenance.adapter.SysInfoAdapter;
import com.ator.supmaintenance.adapter.SysRunAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class MyRecUtil {

    static public JSONObject            keyobj;
    static public ArrayList<String>     mAryKey = new ArrayList<String>();
    static public ArrayList<String>    mAryKeyDes = new ArrayList<String>();
    static public ArrayList<String>    mAryResult = new ArrayList<String>();
    static public ArrayList             mArySub = new ArrayList();

    MyRecUtil(){
    }

    static public void getInstance(String[] keymap){
        keyobj = new JSONObject();
        initKeyMap(keymap);
    }

    static public void initKeyMap(String[] keymap){
        try {
            int size = keymap.length;

            for (int i=0;i<size;i+=2) {
                keyobj.put(keymap[i],keymap[i+1]);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    static public String getKeyDes(String key){

        try{
            String str = (String) keyobj.get(key);
            return str;
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    static public String getRecTypeDes(String type){
        if (type.equals("ROOM")){
            return "总体供电";
        }
        if (type.equals("CABINET")){
            return "控制柜供电";
        }
        if (type.equals("GROUNDCHECK")){
            return "系统接地";
        }
        if (type.equals("SYSRUN")){
            return "系统运行";
        }
        if (type.equals("CONTROLLERCHECK")){
            return "控制站检查";
        }
        if (type.equals("OPSTATIONCHECK")){
            return "操作站检查";
        }
        if (type.equals("SYSINFO")){
            return  "控制规模信息";
        }

        return "";
    }

    public static String getFileURLbyTime(String type,long filetime){

        if (type.equals("ROOM")){
            return PowerAllAdapter.strURL+"/"+filetime;
        }
        if (type.equals("CABINET")){
            return CabinetAdapter.strURL+"/"+filetime;
        }
        if (type.equals("GROUNDCHECK")){
            return GroundCheckAdapter.strURL+"/"+filetime;
        }
        if (type.equals("SYSRUN")){
            return SysRunAdapter.strURL+"/"+filetime;
        }
        if (type.equals("CONTROLLERCHECK")){
            return ControllerCheckAdapter.strURL+"/"+filetime;
        }
        if (type.equals("OPSTATIONCHECK")){
            return OpStationCheckAdapter.strURL+"/"+filetime;
        }
        if (type.equals("SYSINFO")){
            return SysInfoAdapter.strURL+"/"+filetime;
        }

        return "";

    }


   public static String getListURLbyType(String type){

        if (type.equals("ROOM")){
            return PowerAllAdapter.strURL+"/reports";
        }
        if (type.equals("CABINET")){
            return CabinetAdapter.strURL+"/reports";
        }
        if (type.equals("GROUNDCHECK")){
            return GroundCheckAdapter.strURL+"/reports";
        }
        if (type.equals("SYSRUN")){
            return SysRunAdapter.strURL+"/reports";
        }
        if (type.equals("CONTROLLERCHECK")){
            return ControllerCheckAdapter.strURL+"/reports";
        }
        if (type.equals("OPSTATIONCHECK")){
            return OpStationCheckAdapter.strURL+"/reports";
        }
        if (type.equals("SYSINFO")){
            return SysInfoAdapter.strURL+"/reports";
        }

        return "";

    }

    static public void clearContent(){
        mAryKey.clear();
        mAryKeyDes.clear();
        mAryResult.clear();
        mArySub.clear();
    }

    static public boolean parseResult(String strjson){

        clearContent();

        try {
            //正式提取未知的key值
            JSONObject object = new JSONObject(strjson);
            Iterator<String> sIterator = object.keys();
            int i=0;
            //循环并得到key列表
            while (sIterator.hasNext()) {
                // 获得key
                String key = sIterator.next();
                String keyDes = getKeyDes(key);
                String value = object.getString(key);

                mAryKey.add(key);
                mAryKeyDes.add(keyDes);
                mAryResult.add(value);

                if (!keyDes.equals("")){
                    mArySub.add(i);
                }
                i++;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();

        }
        return false;
    }

}
