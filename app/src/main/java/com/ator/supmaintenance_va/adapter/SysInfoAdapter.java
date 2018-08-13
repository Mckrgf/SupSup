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

public class SysInfoAdapter {

    public String room_id;

    public String room_name;
    public String cabinet_id;
    public String historicalcontract_id;
    public String fcu712;
    public String com711;
    public String com741;
    public String ai711;
    public String ai721;
    public String am712;
    public String ai731;
    public String ao711;
    public String di711;
    public String do711;
    public String do712;
    public String tu713_r1200;
    public String tu721_r00;
    public String tu721_r01;
    public String tua712_dor16;
    public String tua711_dor16;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = MyApplication.base_url + "/maintenance/scale";

    public boolean CheckAll(){

        if (        "".equals(room_name)
                ||  "".equals(cabinet_id)

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
            jobj.put("cabinet_id",cabinet_id);
            jobj.put("historicalcontract_id",historicalcontract_id);
            jobj.put("fcu712",fcu712);
            jobj.put("com711",com711);
            jobj.put("com741",com741);
            jobj.put("ai711",ai711);
            jobj.put("ai721",ai721);
            jobj.put("am712",am712);
            jobj.put("ai731",ai731);
            jobj.put("ao711",ao711);
            jobj.put("di711",di711);
            jobj.put("do711",do711);
            jobj.put("do712",do712);
            jobj.put("tu713_r1200",tu713_r1200);
            jobj.put("tu721_r00",tu721_r00);
            jobj.put("tu721_r01",tu721_r01);
            jobj.put("tua712_dor16",tua712_dor16);
            jobj.put("tua711_dor16",tua711_dor16);

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

            jItem.put("cabinet_id",cabinet_id);
            jItem.put("historicalcontract_id",historicalcontract_id);
            jItem.put("fcu712",fcu712);
            jItem.put("com711",com711);
            jItem.put("com741",com741);
            jItem.put("ai711",ai711);
            jItem.put("ai721",ai721);
            jItem.put("am712",am712);
            jItem.put("ai731",ai731);
            jItem.put("ao711",ao711);
            jItem.put("di711",di711);
            jItem.put("do711",do711);
            jItem.put("do712",do712);
            jItem.put("tu713_r1200",tu713_r1200);
            jItem.put("tu721_r00",tu721_r00);
            jItem.put("tu721_r01",tu721_r01);
            jItem.put("tua712_dor16",tua712_dor16);
            jItem.put("tua711_dor16",tua711_dor16);
            jItem.put("suggestion",suggestion);

            jAry.put(jItem);
            jNetObj.put("scaleList",jAry);
            jNetObj.put("plandate",plandate);
            jNetObj.put("room_name",room_name);
            jNetObj.put("room_id",room_id);
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
