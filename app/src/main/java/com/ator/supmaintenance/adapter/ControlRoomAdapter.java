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

public class ControlRoomAdapter {

    public String room_id;

    public String control_room_name           ;
    public String grounding_form               ;
    public String earth_power                 ;
    public String earth_thunder               ;
    public String earth                    ;
    public String total_earth                   ;
    public String earth_resistance               ;
    public String description                    ;
    public String leakage_current                   ;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/controlRoom";

    public boolean CheckAll() {
        if (       "".equals(control_room_name      )
                || "".equals(grounding_form         )
                || "".equals(earth_power            )
                || "".equals(earth_thunder          )
                || "".equals(earth                  )
                || "".equals(total_earth            )
                || "".equals(earth_resistance       )
                || "".equals(description            )
                || "".equals(leakage_current        )

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

            jobj.put("control_room_name"      ,                     control_room_name       );
            jobj.put("grounding_form"        ,                      grounding_form          );
            jobj.put("earth_power"     ,                            earth_power             );
            jobj.put("earth_thunder"        ,                       earth_thunder           );
            jobj.put("earth"               ,                        earth                   );
            jobj.put("total_earth"               ,                  total_earth             );
            jobj.put("earth_resistance"                ,            earth_resistance        );
            jobj.put("description"             ,                    description             );
            jobj.put("leakage_current"             ,                leakage_current         );

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

            jItem.put("control_room_name"      ,                     control_room_name       );
            jItem.put("grounding_form"        ,                      grounding_form          );
            jItem.put("earth_power"    ,                             earth_power             );
            jItem.put("earth_thunder"        ,                       earth_thunder           );
            jItem.put("earth"               ,                        earth                   );
            jItem.put("total_earth"               ,                  total_earth             );
            jItem.put("earth_resistance"                ,            earth_resistance        );
            jItem.put("description"             ,                    description             );
            jItem.put("leakage_current"             ,                leakage_current         );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("controlRoomList", jAry);
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
