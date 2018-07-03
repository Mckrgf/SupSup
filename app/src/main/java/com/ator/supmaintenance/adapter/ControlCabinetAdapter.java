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

public class ControlCabinetAdapter {

    public String room_id;

    public String cabinet_id;
    public String neatly_cabled;
    public String line_identified;
    public String use_line_nose;
    public String layer_grounding;
    public String shielded_cable_one;
    public String shielded_cable_two;
    public String separate_line_one;
    public String separate_line_two;
    public String sbus_contact;
    public String db_contact;
    public String distribution_contact;
    public String power_contact;
    public String plague;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/controlCabinet";

    public boolean CheckAll() {
        if ("".equals(cabinet_id)
                || "".equals(neatly_cabled)
                || "".equals(line_identified)
                || "".equals(use_line_nose)
                || "".equals(layer_grounding)
                || "".equals(shielded_cable_one)
                || "".equals(shielded_cable_two)
                || "".equals(separate_line_one)
                || "".equals(separate_line_two)
                || "".equals(sbus_contact)
                || "".equals(db_contact)
                || "".equals(distribution_contact)
                || "".equals(power_contact)
                || "".equals(plague)

                ) {

            return false;
        }


        return true;
    }

    public void MakeJasonObj() {

        if (jobj != null) {
            jobj = null;
        }

        if (jNetObj != null) {
            jNetObj = null;
        }


        jobj = new JSONObject();
        try {

            jobj.put("control_cabinet_num", cabinet_id);
            jobj.put("room_id", room_id);
            jobj.put("neatly_cabled", neatly_cabled);
            jobj.put("line_identified", line_identified);
            jobj.put("use_line_nose", use_line_nose);
            jobj.put("layer_grounding", layer_grounding);
            jobj.put("shielded_cable_one", shielded_cable_one);
            jobj.put("shielded_cable_two", shielded_cable_two);
            jobj.put("separate_line_one", separate_line_one);
            jobj.put("separate_line_two", separate_line_two);
            jobj.put("sbus_contact", sbus_contact);
            jobj.put("db_contact", db_contact);
            jobj.put("distribution_contact", distribution_contact);
            jobj.put("power_contact", power_contact);
            jobj.put("has_plague",plague);

            jobj.put("suggestion", suggestion);
            jobj.put("append", append);
            jobj.put("plandate", plandate);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        jNetObj = new JSONObject();
        try {
            JSONArray jAry = new JSONArray();
            JSONObject jItem = new JSONObject();

            jItem.put("control_cabinet_num", cabinet_id);
            jItem.put("has_room_id", room_id);
            jItem.put("has_neatly_cabled", neatly_cabled);
            jItem.put("has_line_identified", line_identified);
            jItem.put("has_use_line_nose", use_line_nose);
            jItem.put("has_layer_grounding", layer_grounding);
            jItem.put("has_shielded_cable_one", shielded_cable_one);
            jItem.put("has_shielded_cable_two", shielded_cable_two);
            jItem.put("has_separate_line_one", separate_line_one);
            jItem.put("has_separate_line_two", separate_line_two);
            jItem.put("has_sbus_contact", sbus_contact);
            jItem.put("has_db_contact", db_contact);
            jItem.put("has_distribution_contact", distribution_contact);
            jItem.put("has_power_contact", power_contact);
            jItem.put("has_plague",plague);

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("controlCabinetList", jAry);
            jNetObj.put("plandate", plandate);
            jNetObj.put("filename", room_id + ".xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String GetJasonString() {
        if (jobj == null) {
            return "";
        } else {
            String strout = jobj.toString();
            return strout;
        }
    }

    public String GetNetJasonString() {
        if (jNetObj == null) {
            return "";
        } else {
            String strout = jNetObj.toString();
            return strout;
        }
    }

    public void MakeID(boolean bAppend) {
        //use time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        room_id = simpleDateFormat.format(date);

        if (bAppend) {
            append = room_id + ".png";
        }

        try {
            Date dateNoMill = simpleDateFormat.parse(room_id);
            plandate = dateNoMill.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
