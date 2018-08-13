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

public class SysRunAdapter {

    public String room_id;

    public String cabinet_id;
    public String humiture;
    public String emi;
    public String powerlight;
    public String cardlight;
    public String interchanger;
    public String networknode;
    public String networkcable;
    public String fan;
    public String door_close;
    public String staticfree;
    public String airtightness;
    public String connectingline;
    public String cable_marking;
    public String airswitch_marking;
    public String sparechannel_margin;
    public String rats;

    public String suggestion;
    public String append;
    public long plandate;


    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = MyApplication.base_url + "/maintenance/running";

    public boolean CheckAll(){
        if (        "".equals(cabinet_id)
                ||  "".equals(humiture)
                ||  "".equals(emi)
                ||  "".equals(powerlight)
                ||  "".equals(cardlight)
                ||  "".equals(interchanger)
                ||  "".equals(networknode)
                ||  "".equals(networkcable)
                ||  "".equals(fan)
                ||  "".equals(door_close)
                ||  "".equals(staticfree)
                ||  "".equals(airtightness)
                ||  "".equals(connectingline)
                ||  "".equals(cable_marking)
                ||  "".equals(airswitch_marking)
                ||  "".equals(rats)
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
            jobj.put("humiture",humiture);
            jobj.put("emi",emi);
            jobj.put("powerlight",powerlight);
            jobj.put("cardlight",cardlight);
            jobj.put("interchanger",interchanger);
            jobj.put("networknode",networknode);
            jobj.put("networkcable",networkcable);
            jobj.put("fan",fan);
            jobj.put("door_close",door_close);
            jobj.put("staticfree",staticfree);
            jobj.put("airtightness",airtightness);
            jobj.put("connectingline",connectingline);
            jobj.put("cable_marking",cable_marking);
            jobj.put("airswitch_marking",airswitch_marking);
            jobj.put("sparechannel_margin",sparechannel_margin);
            jobj.put("rats",rats);

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
            jItem.put("room_id",room_id);
            jItem.put("humiture",humiture);
            jItem.put("emi",emi);
            jItem.put("powerlight",powerlight);
            jItem.put("cardlight",cardlight);
            jItem.put("interchanger",interchanger);
            jItem.put("networknode",networknode);
            jItem.put("networkcable",networkcable);
            jItem.put("fan",fan);
            jItem.put("door_close",door_close);
            jItem.put("staticfree",staticfree);
            jItem.put("airtightness",airtightness);
            jItem.put("connectingline",connectingline);
            jItem.put("cable_marking",cable_marking);
            jItem.put("airswitch_marking",airswitch_marking);
            jItem.put("sparechannel_margin",sparechannel_margin);
            jItem.put("rats",rats);
            jItem.put("suggestion",suggestion);


            jAry.put(jItem);
            jNetObj.put("runningCheckList",jAry);
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
