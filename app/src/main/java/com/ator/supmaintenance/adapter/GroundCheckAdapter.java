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

public class GroundCheckAdapter {

    public String room_id;

    public String room_name;
    public String earthing_form;
    public String electric_distance_beyond;
    public String lightning_distance_beyond;
    public String line_length_less;
    public String trunkline_diameter_beyond;
    public String resistance_less;
    public String resistance_measurement;
    public String totalline_leakcurrent;

    public String suggestion;
    public String append;
    public long plandate;


    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = MyApplication.base_url + "/maintenance/earthing";


    public boolean CheckAll(){
        if (        "".equals(room_name)
                ||  "".equals(earthing_form)
                ||  "".equals(electric_distance_beyond)
                ||  "".equals(lightning_distance_beyond)

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

            jobj.put("room_name",room_name);
            jobj.put("room_id",room_id);
            jobj.put("earthing_form",earthing_form);
            jobj.put("electric_distance_beyond",electric_distance_beyond);
            jobj.put("lightning_distance_beyond",lightning_distance_beyond);
            jobj.put("line_length_less",line_length_less);
            jobj.put("trunkline_diameter_beyond",trunkline_diameter_beyond);
            jobj.put("resistance_less",resistance_less);
            jobj.put("resistance_measurement",resistance_measurement);
            jobj.put("totalline_leakcurrent",totalline_leakcurrent);
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

            jItem.put("room_name",room_name);
            jItem.put("room_id",room_id);
            jItem.put("earthing_form",earthing_form);
            jItem.put("electric_distance_beyond",electric_distance_beyond);
            jItem.put("lightning_distance_beyond",lightning_distance_beyond);
            jItem.put("line_length_less",line_length_less);
            jItem.put("trunkline_diameter_beyond",trunkline_diameter_beyond);
            jItem.put("resistance_less",resistance_less);
            jItem.put("resistance_measurement",resistance_measurement);
            jItem.put("totalline_leakcurrent",totalline_leakcurrent);
            jItem.put("suggestion",suggestion);

            jAry.put(jItem);
            jNetObj.put("earthingCheckList",jAry);
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
