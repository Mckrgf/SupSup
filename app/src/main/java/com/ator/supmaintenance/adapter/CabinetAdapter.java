package com.ator.supmaintenance.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class CabinetAdapter {

    public String room_id;

    public String cabinet_id;
    public String twinsupply;
    public String diameter_beyond;
    public String system_a_input;
    public String system_b_input;
    public String power1;
    public String power2;
    public String power3;
    public String power4;
    public String power5;
    public String power6;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = "http://118.31.42.34:8090/maintenance/cabinetsupply";

    public boolean CheckAll(){
        if (        "".equals(cabinet_id)
                ||  "".equals(twinsupply)
                ||  "".equals(diameter_beyond)
                ||  "".equals(system_a_input)
                ||  "".equals(power1)

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

            jobj.put("cabinet_id",cabinet_id);
            jobj.put("room_id",room_id);
            jobj.put("twinsupply",twinsupply);
            jobj.put("diameter_beyond",diameter_beyond);
            jobj.put("system_a_input",system_a_input);
            jobj.put("system_b_input",system_b_input);
            jobj.put("power1",power1);
            jobj.put("power2",power2);
            jobj.put("power3",power3);
            jobj.put("power4",power4);
            jobj.put("power5",power5);
            jobj.put("power6",power6);
            jobj.put("suggestion",suggestion);
            jobj.put("append",append);
            jobj.put("plandate",plandate);

        }catch (JSONException e) {
            e.printStackTrace();
        }


        jNetObj = new JSONObject();
        try {
            JSONArray jAry = new JSONArray();
            JSONObject jItem = new JSONObject();

            jItem.put("cabinet_id",cabinet_id);
            jItem.put("room_id",room_id);
            jItem.put("twinsupply",twinsupply);
            jItem.put("diameter_beyond",diameter_beyond);
            jItem.put("system_a_input",system_a_input);
            jItem.put("system_b_input",system_b_input);
            jItem.put("power1",power1);
            jItem.put("power2",power2);
            jItem.put("power3",power3);
            jItem.put("power4",power4);
            jItem.put("power5",power5);
            jItem.put("power6",power6);
            jItem.put("suggestion",suggestion);

            jAry.put(jItem);
            jNetObj.put("cabinetSupplyList",jAry);
            jNetObj.put("plandate",plandate);
            jNetObj.put("filename",room_id+".xlsx");

        }catch (Exception e) {
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
        room_id = simpleDateFormat.format(date);

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
