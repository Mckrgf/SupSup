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

public class PowerSystemAdapter {

    public String room_id;

    //Edittext
    public String name           ;
    public String ups_state              ;

    //RadioGroup
    public String mode               ;
    public String has_dcs               ;
    public String has_two_busbar               ;
    public String has_setting_open               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/powerSystem";

    public boolean CheckAll() {
        if (       "".equals(name                   )
                || "".equals(ups_state                              )
                || "".equals(has_setting_open         )
                || "".equals(mode                    )
                || "".equals(has_dcs               )
                || "".equals(has_two_busbar            )

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
            jobj.put("room_id"                  ,               room_id);

            jobj.put("name"                 ,        name          );
            jobj.put("ups_state"                 ,                     ups_state                                  );
            jobj.put("has_setting_open"             ,                   has_setting_open        );
            jobj.put("mode"         ,                          mode                   );
            jobj.put("has_dcs"                ,                  has_dcs          );
            jobj.put("has_two_busbar"          ,                       has_two_busbar         );

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

            jItem.put("room_id", room_id);

            jItem.put("name"                 ,        name          );
            jItem.put("ups_state"                 ,                     ups_state                                  );
            jItem.put("has_setting_open"             ,                   has_setting_open        );
            jItem.put("mode"         ,                          mode                   );
            jItem.put("has_dcs"                ,                  has_dcs          );
            jItem.put("has_two_busbar"          ,                       has_two_busbar         );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("powerSystemList", jAry);
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
