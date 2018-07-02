package com.ator.supmaintenance.adapter;

import com.ator.supmaintenance.item.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class ControllerCheckAdapter {

    public String controller_id;
    public String controller_site;
    public String controller_type;
    public String controllercpu_version;
    public String net_commucpu_version;
    public String io_commucpu_version;
    public String redundancy;
    public String config;
    public String userprogram_running;
    public String scnet;
    public String ebus;
    public String lbus;
    public String load;
    public String cellvoltage;
    public String temperature;
    public String io_oos;
    public String io_constraint;
    public String analog_beyond;
    public String di_joggle;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = MyApplication.base_url + "/maintenance/ctrlstation";


    public boolean CheckAll(){
        if (        "".equals(controller_id)
                ||  "".equals(controller_site)
                ||  "".equals(controller_type)
                ||  "".equals(controllercpu_version)
                ||  "".equals(net_commucpu_version)
                ||  "".equals(io_commucpu_version)
                ||  "".equals(redundancy)
                ||  "".equals(config)
                ||  "".equals(userprogram_running)
                ||  "".equals(scnet)
                ||  "".equals(ebus)
                ||  "".equals(lbus)
                ||  "".equals(load)
                ||  "".equals(cellvoltage)
                ||  "".equals(temperature)
                ||  "".equals(io_oos)
                ||  "".equals(io_constraint)
                ||  "".equals(analog_beyond)
                ||  "".equals(di_joggle)
                ){
            return false;
        }


        return true;
    }

    public void MakeJasonObj(){

        if(jobj != null){
            jobj = null;
        }

        if (jNetObj!= null){
            jNetObj = null;
        }


        jobj = new JSONObject();
        try{

            jobj.put("controller_id",controller_id);
            jobj.put("controller_site",controller_site);
            jobj.put("controller_type",controller_type);
            jobj.put("controllercpu_version",controllercpu_version);
            jobj.put("net_commucpu_version",net_commucpu_version);
            jobj.put("redundancy",redundancy);
            jobj.put("config",config);
            jobj.put("userprogram_running",userprogram_running);
            jobj.put("scnet",scnet);
            jobj.put("ebus",ebus);
            jobj.put("lbus",lbus);
            jobj.put("load",load);
            jobj.put("cellvoltage",cellvoltage);
            jobj.put("temperature",temperature);
            jobj.put("io_oos",io_oos);
            jobj.put("io_constraint",io_constraint);
            jobj.put("analog_beyond",analog_beyond);
            jobj.put("di_joggle",di_joggle);


            jobj.put("suggestion",suggestion);
            jobj.put("append",append);
            jobj.put("plandate",plandate);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        jNetObj = new JSONObject();
        try{
            JSONArray jAry = new JSONArray();
            JSONObject jItem = new JSONObject();

            jItem.put("controller_id",controller_id);
            jItem.put("controller_site",controller_site);
            jItem.put("controller_type",controller_type);
            jItem.put("controllercpu_version",controllercpu_version);
            jItem.put("net_commucpu_version",net_commucpu_version);
            jItem.put("redundancy",redundancy);
            jItem.put("config",config);
            jItem.put("userprogram_running",userprogram_running);
            jItem.put("scnet",scnet);
            jItem.put("ebus",ebus);
            jItem.put("lbus",lbus);
            jItem.put("load",load);
            jItem.put("cellvoltage",cellvoltage);
            jItem.put("temperature",temperature);
            jItem.put("io_oos",io_oos);
            jItem.put("io_constraint",io_constraint);
            jItem.put("analog_beyond",analog_beyond);
            jItem.put("di_joggle",di_joggle);
            jItem.put("suggestion",suggestion);

            jAry.put(jItem);
            jNetObj.put("ctrlStationCheckList",jAry);
            jNetObj.put("plandate",plandate);
            jNetObj.put("filename",controller_id+".xlsx");

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String GetJasonString(){
        if (jobj == null){
            return "";
        }else{
            String strout = jobj.toString();
            return  strout;
        }
    }

    public String GetNetJasonString(){
        if (jNetObj == null){
            return "";
        }else{
            String strout = jNetObj.toString();
            return  strout;
        }
    }

    public void MakeID(boolean bAppend){
        //use time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        controller_id = simpleDateFormat.format(date);

        if (bAppend){
            append = controller_id+".png";
        }

        try{
            Date dateNoMill = simpleDateFormat.parse(controller_id);

            plandate = dateNoMill.getTime();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
