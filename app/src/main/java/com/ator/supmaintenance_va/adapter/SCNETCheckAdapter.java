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

public class SCNETCheckAdapter {

    public String room_id;

    //Edittext
    public String address_one           ;
    public String address_two           ;

    //RadioGroup
    public String has_normal_one               ;
    public String has_normal_two               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/scnetCheck";

    public boolean CheckAll() {
        if (       "".equals(address_one                   )
                || "".equals(address_two                         )
                || "".equals(has_normal_one                         )
                || "".equals(has_normal_two              )

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

            jobj.put("address_one"                 ,                              address_one                );
            jobj.put("address_two"                 ,                              address_two                );
            jobj.put("has_normal_one"               ,                           has_normal_one                    );
            jobj.put("has_normal_two"                 ,                      has_normal_two                 );

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

            jItem.put("address_one"                 ,                              address_one                );
            jItem.put("address_two"                 ,                              address_two                );
            jItem.put("has_normal_one"               ,                           has_normal_one                    );
            jItem.put("has_normal_two"                 ,                      has_normal_two                 );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("scnetCheckList", jAry);
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
