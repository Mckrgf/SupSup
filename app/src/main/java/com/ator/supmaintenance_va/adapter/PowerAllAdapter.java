package com.ator.supmaintenance_va.adapter;

import com.ator.supmaintenance_va.item.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class PowerAllAdapter {

    public String room_id;

    public String room_name;
    public String des;
    public String voltage1;
    public String voltage2;

    public String airswitch;
    public String powerfilter;

    public String bad;
    public String suggestion;
    public String append;
    public long   plandate;

    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = MyApplication.base_url + "/maintenance/bulksupply";


    public boolean CheckAll(){
        if (        "".equals(room_id)
                ||  "".equals(room_name)
                ||  "".equals(des)
                ||  "".equals(voltage1)
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
            jobj.put("room_id",room_id);
            jobj.put("room_name",room_name);
            jobj.put("des",des);
            jobj.put("voltage1",voltage1);
            jobj.put("voltage2",voltage2);
            jobj.put("airswitch",airswitch);
            jobj.put("powerfilter",powerfilter);
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

            jItem.put("room_id",room_id);
            jItem.put("room_name",room_name);
            jItem.put("des",des);
            jItem.put("voltage1",voltage1);
            jItem.put("voltage2",voltage2);
            jItem.put("airswitch",airswitch);
            jItem.put("powerfilter",powerfilter);
            jItem.put("suggestion",suggestion);

            jAry.put(jItem);
            jNetObj.put("bulkSupplyList",jAry);
            jNetObj.put("plandate",plandate);
            jNetObj.put("filename",room_id+".xlsx");


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
        //
        room_id = simpleDateFormat.format(date) + bad;
        if (bAppend){
            append = room_id+".png";
        }
        try{

            Date dateNoMill = simpleDateFormat.parse(room_id);
            plandate = dateNoMill.getTime();

        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
