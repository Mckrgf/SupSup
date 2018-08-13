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

public class MasterCardCheckAdapter {

    public String room_id;

    public String address           ;
    public String a_communication               ;
    public String b_communication                 ;
    public String has_identified               ;
    public String has_subs_zero                    ;
    public String has_subs_one                   ;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/masterCardCheck";

    public boolean CheckAll() {
        if (       "".equals(address      )
                || "".equals(a_communication         )
                || "".equals(b_communication         )
                || "".equals(has_identified          )
                || "".equals(has_subs_zero           )
                || "".equals(has_subs_one            )

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

            jobj.put("address"      ,                     address       );
            jobj.put("a_communication"        ,                      a_communication          );
            jobj.put("b_communication"   ,                            b_communication          );
            jobj.put("has_identified"         ,                       has_identified           );
            jobj.put("has_subs_zero"         ,                        has_subs_zero            );
            jobj.put("has_subs_one"                ,                  has_subs_one             );

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

            jItem.put("has_room_id", room_id);

            jItem.put("address"      ,                     address       );
            jItem.put("a_communication"        ,                      a_communication          );
            jItem.put("b_communication"   ,                            b_communication          );
            jItem.put("has_identified"         ,                       has_identified           );
            jItem.put("has_subs_zero"         ,                        has_subs_zero            );
            jItem.put("has_subs_one"                ,                  has_subs_one             );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("masterCardCheckList", jAry);
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
