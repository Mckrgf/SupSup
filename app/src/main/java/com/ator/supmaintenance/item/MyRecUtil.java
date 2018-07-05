package com.ator.supmaintenance.item;

import android.content.Context;
import android.content.res.Resources;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.adapter.CabinetAdapter;
import com.ator.supmaintenance.adapter.CabinetCorrosionAdapter;
import com.ator.supmaintenance.adapter.ControlCabinetAdapter;
import com.ator.supmaintenance.adapter.ControlCabinetPowerAdapter;
import com.ator.supmaintenance.adapter.ControlRoomAdapter;
import com.ator.supmaintenance.adapter.ControlRoomPowerMagnetismAdapter;
import com.ator.supmaintenance.adapter.ControllerCheckAdapter;
import com.ator.supmaintenance.adapter.GroundCheckAdapter;
import com.ator.supmaintenance.adapter.MasterCardCheckAdapter;
import com.ator.supmaintenance.adapter.OpStationCheckAdapter;
import com.ator.supmaintenance.adapter.OperatingStationAdapter;
import com.ator.supmaintenance.adapter.OperatingStationCheckAdapter;
import com.ator.supmaintenance.adapter.OperatingStationCorrosionDetectionAdapter;
import com.ator.supmaintenance.adapter.OperatingStationPowerAdapter;
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
        if (type.equals("CABINETCORROSION")) {
            return "机柜腐蚀检测";
        }
        if (type.equals("CONTROLCABINET")) {
            return "控制柜";
        }
        if (type.equals("CONTROLCABINETPOWER")) {
            return "控制柜供电";
        }
        if (type.equals("CONTROLROOM")) {
            return "控制室";
        }
        if (type.equals("CONTROLROOMPOWERMAGNETISM")) {
            return "控制室电磁场强度检查记录单";
        }

        if (type.equals("MASTERCARDCHECK")) {
            return "主控卡通讯检查";
        }
        if (type.equals("OPERATINGSTATION")) {
            return "操作站";
        }

        if (type.equals("OPERATINGSTATIONCHECK")) {
            return "操作站通讯检查";
        }
        if (type.equals("OPERATINGSTATIONCORROSIONDETECTION")) {
            return "操作站腐蚀检测";
        }
        if (type.equals("OPERATINGSTATIONPOWER")) {
            return "操作站供电";
        }
        if (type.equals("POWERCHECK")) {
            return "电源冗余检查";
        }
        if (type.equals("POWERSYSTEM")) {
            return "系统供电";
        }
        if (type.equals("ROOMCORROSIONDETECTION")) {
            return "控制室腐蚀检测";
        }
        if (type.equals("SBUSCHECK")) {
            return "SBUS网络冗余检查";
        }
        if (type.equals("SCNETCHECK")) {
            return "SCNET控制网冗余检查";
        }
        if (type.equals("SYSTEMPOWERCHECK")) {
            return "系统供电冗余检查";
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
        if (type.equals("CABINETCORROSION")) {
            return CabinetCorrosionAdapter.strURL + "/" + filetime;
        }
        if (type.equals("CONTROLCABINET")) {
            return ControlCabinetAdapter.strURL + "/" + filetime;
        }
        if (type.equals("CONTROLCABINETPOWER")) {
            return ControlCabinetPowerAdapter.strURL + "/" + filetime;
        }
        if (type.equals("CONTROLROOM")) {
            return ControlRoomAdapter.strURL + "/" + filetime;
        }
        if (type.equals("CONTROLROOMPOWERMAGNETISM")) {
            return ControlRoomPowerMagnetismAdapter.strURL + "/" + filetime;
        }
        if (type.equals("MASTERCARDCHECK")) {
            return MasterCardCheckAdapter.strURL + "/" + filetime;
        }
        if (type.equals("OPERATINGSTATION")) {
            return OperatingStationAdapter.strURL + "/" + filetime;
        }
        if (type.equals("OPERATINGSTATIONCORROSIONDETECTION")) {
            return OperatingStationCorrosionDetectionAdapter.strURL + "/" + filetime;
        }
        if (type.equals("OPERATINGSTATIONCHECK")) {
            return OperatingStationCheckAdapter.strURL + "/" + filetime;
        }
        if (type.equals("OPERATINGSTATIONPOWER")) {
            return OperatingStationPowerAdapter.strURL + "/" + filetime;
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
        if (type.equals("CABINETCORROSION")) {
            return CabinetCorrosionAdapter.strURL + "/reports";
        }
        if (type.equals("CONTROLCABINET")) {
            return ControlCabinetAdapter.strURL + "/reports";
        }
        if (type.equals("CONTROLCABINETPOWER")) {
            return ControlCabinetPowerAdapter.strURL + "/reports";
        }
        if (type.equals("CONTROLROOM")) {
            return ControlRoomAdapter.strURL + "/reports";
        }
        if (type.equals("CONTROLROOMPOWERMAGNETISM")) {
            return ControlRoomPowerMagnetismAdapter.strURL + "/reports";
        }
        if (type.equals("MASTERCARDCHECK")) {
            return MasterCardCheckAdapter.strURL + "/reports";
        }
        if (type.equals("OPERATINGSTATION")) {
            return OperatingStationAdapter.strURL + "/reports";
        }
        if (type.equals("OPERATINGSTATIONCORROSIONDETECTION")) {
            return OperatingStationCorrosionDetectionAdapter.strURL + "/reports";
        }
        if (type.equals("OPERATINGSTATIONCHECK")) {
            return OperatingStationCheckAdapter.strURL + "/reports";
        }
        if (type.equals("OPERATINGSTATIONPOWER")) {
            return OperatingStationPowerAdapter.strURL + "/reports";
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
