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

public class CabinetCorrosionAdapter {

    public String room_id;

    public String cabinet_name;
    public String airtight;
    public String entry_hole;
    public String grounding_bare;
    public String scene_bare;
    public String iron_screw;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/cabinetCorrosionDetection";

    public boolean CheckAll() {
        if ("".equals(cabinet_name)
                || "".equals(airtight)
                || "".equals(entry_hole)
                || "".equals(grounding_bare)
                || "".equals(scene_bare)
                || "".equals(iron_screw)

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

            jobj.put("cabinet_name", cabinet_name);
            jobj.put("room_id", room_id);
            jobj.put("airtight", airtight);
            jobj.put("entry_hole", entry_hole);
            jobj.put("grounding_bare", grounding_bare);
            jobj.put("scene_bare", scene_bare);
            jobj.put("iron_screw", iron_screw);
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

            jItem.put("cabinet_name", cabinet_name);
            jItem.put("room_id", room_id);
            jItem.put("airtight", airtight);
            jItem.put("entry_hole", entry_hole);
            jItem.put("grounding_bare", grounding_bare);
            jItem.put("scene_bare", scene_bare);
            jItem.put("iron_screw", iron_screw);
            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("cabinetSupplyList", jAry);
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
