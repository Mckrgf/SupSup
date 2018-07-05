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

public class OperatingStationPowerAdapter {

    public String room_id;

    //Edittext
    public String operating_station_num           ;
    public String ac_value              ;
    public String harmonic_value           ;

    //RadioGroup
    public String has_two_way               ;
    public String has_switcher               ;
    public String has_more_line               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/operatingStationPower";

    public boolean CheckAll() {
        if (       "".equals(operating_station_num                   )
                || "".equals(ac_value                              )
                || "".equals(harmonic_value         )
                || "".equals(has_two_way                    )
                || "".equals(has_switcher               )
                || "".equals(has_more_line            )

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

            jobj.put("operating_station_num"                 ,        operating_station_num          );
            jobj.put("ac_value"                 ,                     ac_value                                  );
            jobj.put("harmonic_value"             ,                   harmonic_value        );
            jobj.put("has_two_way"         ,                          has_two_way                   );
            jobj.put("has_switcher"                ,                  has_switcher          );
            jobj.put("has_more_line"          ,                       has_more_line         );

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

            jItem.put("operating_station_num"                 ,        operating_station_num          );
            jItem.put("ac_value"                 ,                     ac_value                                  );
            jItem.put("harmonic_value"             ,                   harmonic_value        );
            jItem.put("has_two_way"         ,                          has_two_way                   );
            jItem.put("has_switcher"                ,                  has_switcher          );
            jItem.put("has_more_line"          ,                       has_more_line         );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("operatingStationPowerList", jAry);
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
