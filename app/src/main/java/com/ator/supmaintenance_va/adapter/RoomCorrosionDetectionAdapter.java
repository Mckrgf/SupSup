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

public class RoomCorrosionDetectionAdapter {

    public String room_id;

    //Edittext
    public String room_name           ;

    //RadioGroup
    public String has_sharp_aroma               ;
    public String has_corrosive_odor               ;
    public String has_internal_device               ;
    public String has_two_gate               ;
    public String has_window               ;
    public String has_entry_hole               ;
    public String has_air_conditioner               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/roomCorrosionDetection";

    public boolean CheckAll() {
        if (       "".equals(room_name                   )
                || "".equals(has_sharp_aroma                         )
                || "".equals(has_corrosive_odor              )
                || "".equals(has_entry_hole                  )
                || "".equals(has_internal_device              )
                || "".equals(has_two_gate                  )
                || "".equals(has_window                  )
                || "".equals(has_air_conditioner                  )

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

            jobj.put("room_name"                 ,                              room_name                );
            jobj.put("has_sharp_aroma"               ,                           has_sharp_aroma                    );
            jobj.put("has_corrosive_odor"                 ,                      has_corrosive_odor                 );
            jobj.put("has_internal_device"     ,                                 has_internal_device                );
            jobj.put("has_two_gate"                        ,                     has_two_gate                       );
            jobj.put("has_window"                       ,                        has_window                         );
            jobj.put("has_entry_hole"                   ,                        has_entry_hole                     );
            jobj.put("has_air_conditioner"              ,                        has_air_conditioner                );

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

            jItem.put("room_name"                 ,                              room_name                );
            jItem.put("has_sharp_aroma"               ,                           has_sharp_aroma                    );
            jItem.put("has_corrosive_odor"                 ,                      has_corrosive_odor                 );
            jItem.put("has_internal_device"     ,                                 has_internal_device                );
            jItem.put("has_two_gate"                        ,                     has_two_gate                       );
            jItem.put("has_window"                       ,                        has_window                         );
            jItem.put("has_entry_hole"                   ,                        has_entry_hole                     );
            jItem.put("has_air_conditioner"              ,                        has_air_conditioner                );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("roomCorrosionDetectionList", jAry);
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
