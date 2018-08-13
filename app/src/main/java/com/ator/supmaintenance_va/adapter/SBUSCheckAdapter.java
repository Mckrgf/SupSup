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

public class SBUSCheckAdapter {

    public String room_id;

    //Edittext
    public String address           ;

    //RadioGroup
    public String sbus_one               ;
    public String sbus_two               ;
    public String sbus_three               ;
    public String sbus_four               ;
    public String sbus_five               ;
    public String sbus_six               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/sbusCheck";

    public boolean CheckAll() {
        if (       "".equals(address                   )
                || "".equals(sbus_one                         )
                || "".equals(sbus_two              )
                || "".equals(sbus_six                  )
                || "".equals(sbus_three              )
                || "".equals(sbus_four                  )
                || "".equals(sbus_five                  )

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

            jobj.put("address"                 ,                              address                );
            jobj.put("sbus_one"               ,                           sbus_one                    );
            jobj.put("sbus_two"                 ,                      sbus_two                 );
            jobj.put("sbus_three"     ,                                 sbus_three                );
            jobj.put("sbus_four"                        ,                     sbus_four                       );
            jobj.put("sbus_five"                       ,                        sbus_five                         );
            jobj.put("sbus_six"                   ,                        sbus_six                     );

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

            jItem.put("address"                 ,                              address                );
            jItem.put("sbus_one"               ,                           sbus_one                    );
            jItem.put("sbus_two"                 ,                      sbus_two                 );
            jItem.put("sbus_three"     ,                                 sbus_three                );
            jItem.put("sbus_four"                        ,                     sbus_four                       );
            jItem.put("sbus_five"                       ,                        sbus_five                         );
            jItem.put("sbus_six"                   ,                        sbus_six                     );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("sbusCheckList", jAry);
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
