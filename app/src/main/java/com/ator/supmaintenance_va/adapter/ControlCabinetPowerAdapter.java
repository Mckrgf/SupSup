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

public class ControlCabinetPowerAdapter {

    public String room_id;

    public String control_cabinet_num           ;
    public String has_wave_filter               ;
    public String has_more_line                 ;
    public String a_input_voltage               ;
    public String a_harmonic                    ;
    public String a_frequency                   ;
    public String b_input_voltage               ;
    public String b_harmonic                    ;
    public String b_frequency                   ;
    public String one_voltage_value_one         ;
    public String one_voltage_value_two         ;
    public String two_voltage_value_one         ;
    public String two_voltage_value_two         ;
    public String three_voltage_value_one       ;
    public String three_voltage_value_two       ;
    public String four_voltage_value_one        ;
    public String four_voltage_value_two        ;
    public String five_voltage_value_one        ;
    public String five_voltage_value_two        ;
    public String six_voltage_value_one         ;
    public String six_voltage_value_two         ;

    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/controlCabinetPower";

    public boolean CheckAll() {
        if (       "".equals(control_cabinet_num       )
                || "".equals(has_wave_filter           )
                || "".equals(has_more_line             )
                || "".equals(a_input_voltage           )
                || "".equals(a_harmonic                )
                || "".equals(a_frequency               )
                || "".equals(b_input_voltage           )
                || "".equals(b_harmonic                )
                || "".equals(b_frequency               )
                || "".equals(one_voltage_value_one     )
                || "".equals(one_voltage_value_two     )
                || "".equals(two_voltage_value_one     )
                || "".equals(two_voltage_value_two     )
                || "".equals(three_voltage_value_one   )
                || "".equals(three_voltage_value_two   )
                || "".equals(four_voltage_value_one    )
                || "".equals(four_voltage_value_two    )
                || "".equals(five_voltage_value_one    )
                || "".equals(five_voltage_value_two    )
                || "".equals(six_voltage_value_one     )
                || "".equals(six_voltage_value_two     )

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

            jobj.put("control_cabinet_num"      ,                   control_cabinet_num       );
            jobj.put("has_wave_filter"            ,                 has_wave_filter           );
            jobj.put("has_more_line"          ,                     has_more_line             );
            jobj.put("a_input_voltage"            ,                 a_input_voltage           );
            jobj.put("a_harmonic"                        ,          a_harmonic                );
            jobj.put("a_frequency"                       ,          a_frequency               );
            jobj.put("b_input_voltage"                    ,         b_input_voltage           );
            jobj.put("b_harmonic"                      ,            b_harmonic                );
            jobj.put("b_frequency"                     ,            b_frequency               );
            jobj.put("one_voltage_value_one"             ,          one_voltage_value_one     );
            jobj.put("one_voltage_value_two"               ,        one_voltage_value_two     );
            jobj.put("two_voltage_value_one"     ,                  two_voltage_value_one     );
            jobj.put("two_voltage_value_two"            ,           two_voltage_value_two     );
            jobj.put("three_voltage_value_one"               ,      three_voltage_value_one   );
            jobj.put("three_voltage_value_two"               ,      three_voltage_value_two   );
            jobj.put("four_voltage_value_one"               ,       four_voltage_value_one    );
            jobj.put("four_voltage_value_two"               ,       four_voltage_value_two    );
            jobj.put("five_voltage_value_one"               ,       five_voltage_value_one    );
            jobj.put("five_voltage_value_two"               ,       five_voltage_value_two    );
            jobj.put("six_voltage_value_one"               ,        six_voltage_value_one     );
            jobj.put("six_voltage_value_two"               ,        six_voltage_value_two     );

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

            jItem.put("control_cabinet_num"      ,                   control_cabinet_num       );
            jItem.put("has_wave_filter"            ,                 has_wave_filter           );
            jItem.put("has_more_line"          ,                     has_more_line             );
            jItem.put("a_input_voltage"            ,                 a_input_voltage           );
            jItem.put("a_harmonic"          ,                        a_harmonic                );
            jItem.put("a_frequency"       ,                          a_frequency               );
            jItem.put("b_input_voltage"       ,                      b_input_voltage           );
            jItem.put("b_harmonic"        ,                          b_harmonic                );
            jItem.put("b_frequency"        ,                         b_frequency               );
            jItem.put("one_voltage_value_one"             ,          one_voltage_value_one     );
            jItem.put("one_voltage_value_two"               ,        one_voltage_value_two     );
            jItem.put("two_voltage_value_one"     ,                  two_voltage_value_one     );
            jItem.put("two_voltage_value_two"            ,           two_voltage_value_two     );
            jItem.put("three_voltage_value_one"               ,      three_voltage_value_one   );
            jItem.put("three_voltage_value_two"               ,      three_voltage_value_two   );
            jItem.put("four_voltage_value_one"               ,       four_voltage_value_one    );
            jItem.put("four_voltage_value_two"               ,       four_voltage_value_two    );
            jItem.put("five_voltage_value_one"               ,       five_voltage_value_one    );
            jItem.put("five_voltage_value_two"               ,       five_voltage_value_two    );
            jItem.put("six_voltage_value_one"               ,        six_voltage_value_one     );
            jItem.put("six_voltage_value_two"               ,        six_voltage_value_two     );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("controlCabinetPowerList", jAry);
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
