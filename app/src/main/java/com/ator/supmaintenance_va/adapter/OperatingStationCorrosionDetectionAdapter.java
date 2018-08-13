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

public class OperatingStationCorrosionDetectionAdapter {

    public String room_id;

    //Edittext
    public String operating_station_name           ;

    //RadioGroup
    public String has_airtight               ;
    public String has_corrosive_odor               ;
    public String has_entry_hole               ;
    public String has_grounding_bare               ;
    public String has_iron_screw               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/operatingStationCorrosionDetection";

    public boolean CheckAll() {
        if (       "".equals(operating_station_name                   )
                || "".equals(has_airtight                         )
                || "".equals(has_corrosive_odor              )
                || "".equals(has_entry_hole                  )
                || "".equals(has_grounding_bare              )
                || "".equals(has_iron_screw                  )

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

            jobj.put("operating_station_name"                 ,                 operating_station_name                );
            jobj.put("has_airtight"                 ,                           has_airtight                     );
            jobj.put("has_corrosive_odor"                ,                      has_corrosive_odor               );
            jobj.put("has_entry_hole"         ,                                 has_entry_hole                   );
            jobj.put("has_grounding_bare"                 ,                     has_grounding_bare               );
            jobj.put("has_iron_screw"                  ,                        has_iron_screw                   );

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

            jItem.put("operating_station_name"                 ,                 operating_station_name           );
            jItem.put("has_airtight"                 ,                           has_airtight                     );
            jItem.put("has_corrosive_odor"                ,                      has_corrosive_odor               );
            jItem.put("has_entry_hole"         ,                                 has_entry_hole                   );
            jItem.put("has_grounding_bare"                 ,                     has_grounding_bare               );
            jItem.put("has_iron_screw"                  ,                        has_iron_screw                   );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("operatingStationCorrosionDetectionList", jAry);
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
