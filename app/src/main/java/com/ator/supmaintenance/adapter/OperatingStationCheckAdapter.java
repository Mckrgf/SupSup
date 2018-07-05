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

public class OperatingStationCheckAdapter {

    public String room_id;

    //Edittext
    public String operating_station_num           ;

    //RadioGroup
    public String has_line_identified               ;
    public String a_communication               ;
    public String b_communication               ;
    public String c_communication               ;
    public String net_close               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/operatingStationCheck";

    public boolean CheckAll() {
        if (       "".equals(operating_station_num                   )
                || "".equals(has_line_identified                         )
                || "".equals(a_communication              )
                || "".equals(b_communication                  )
                || "".equals(c_communication              )
                || "".equals(net_close                  )

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

            jobj.put("operating_station_num"                 ,                 operating_station_num                );
            jobj.put("has_line_identified"                 ,                           has_line_identified                     );
            jobj.put("a_communication"                ,                      a_communication               );
            jobj.put("b_communication"         ,                                 b_communication                   );
            jobj.put("c_communication"                 ,                     c_communication               );
            jobj.put("net_close"                  ,                        net_close                   );

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

            jItem.put("operating_station_num"                 ,                 operating_station_num           );
            jItem.put("has_line_identified"                 ,                           has_line_identified                     );
            jItem.put("a_communication"                ,                      a_communication               );
            jItem.put("b_communication"         ,                                 b_communication                   );
            jItem.put("c_communication"                 ,                     c_communication               );
            jItem.put("net_close"                  ,                        net_close                   );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("operatingStationCheckList", jAry);
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
