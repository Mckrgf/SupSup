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

public class OperatingStationAdapter {

    public String room_id;

    //Edittext
    public String control_room_name           ;
    public String compute_name           ;
    public String soft_version           ;
    public String patch_version           ;
    public String os_version           ;
    public String os_patch           ;

    //RadioGroup
    public String has_driver               ;
    public String has_virtual_memory               ;
    public String net_close               ;
    public String binuclear_close               ;
    public String attribute_setting               ;
    public String power_manage               ;
    public String fault_diagnosis_normal               ;
    public String status                 ;
    public String time_setting               ;
    public String soft_dog               ;
    public String capacity_normal               ;
    public String d_dcsdata               ;
    public String keyborad_normal               ;
    public String mouse                  ;
    public String cd_drive               ;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject jobj = null;
    public JSONObject jNetObj = null;
    public static String strURL = MyApplication.base_url + "/maintenance/operatingStation";

    public boolean CheckAll() {
        if (       "".equals(compute_name                   )
                || "".equals(control_room_name                   )
                || "".equals(soft_version                   )
                || "".equals(patch_version                  )
                || "".equals(os_version                     )
                || "".equals(os_patch                       )
                || "".equals(has_driver                  )
                || "".equals(has_virtual_memory          )
                || "".equals(net_close                   )
                || "".equals(binuclear_close             )
                || "".equals(attribute_setting           )
                || "".equals(power_manage                )
                || "".equals(fault_diagnosis_normal      )
                || "".equals(status                      )
                || "".equals(time_setting                )
                || "".equals(soft_dog                    )
                || "".equals(capacity_normal             )
                || "".equals(d_dcsdata                   )
                || "".equals(keyborad_normal             )
                || "".equals(mouse                       )
                || "".equals(cd_drive                    )

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

            jobj.put("control_room_name"                 ,                     control_room_name                );
            jobj.put("compute_name"                 ,                     compute_name                );
            jobj.put("soft_version"                ,                      soft_version                );
            jobj.put("patch_version"         ,                            patch_version               );
            jobj.put("os_version"                 ,                       os_version                  );
            jobj.put("os_patch"                  ,                        os_patch                    );
            jobj.put("has_driver"                      ,                  has_driver                  );
            jobj.put("has_virtual_memory"                    ,            has_virtual_memory          );
            jobj.put("net_close"                     ,                    net_close                   );
            jobj.put("binuclear_close"                   ,                binuclear_close             );
            jobj.put("attribute_setting"            ,                     attribute_setting           );
            jobj.put("power_manage"                ,                      power_manage                );
            jobj.put("fault_diagnosis_normal",                            fault_diagnosis_normal      );
            jobj.put("status"                     ,                       status                      );
            jobj.put("time_setting"              ,                        time_setting                );
            jobj.put("soft_dog"                        ,                  soft_dog                    );
            jobj.put("capacity_normal"                       ,            capacity_normal             );
            jobj.put("d_dcsdata"                     ,                    d_dcsdata                   );
            jobj.put("keyborad_normal"                   ,                keyborad_normal             );
            jobj.put("mouse"                         ,                    mouse                       );
            jobj.put("cd_drive"                          ,                cd_drive                    );

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

            jItem.put("control_room_name"                 ,                     control_room_name                );
            jItem.put("compute_name"                 ,                     compute_name                );
            jItem.put("soft_version"                ,                      soft_version                );
            jItem.put("patch_version"         ,                            patch_version               );
            jItem.put("os_version"                 ,                       os_version                  );
            jItem.put("os_patch"                  ,                        os_patch                    );
            jItem.put("has_driver"                      ,                  has_driver                  );
            jItem.put("has_virtual_memory"                    ,            has_virtual_memory          );
            jItem.put("net_close"                     ,                    net_close                   );
            jItem.put("binuclear_close"                   ,                binuclear_close             );
            jItem.put("attribute_setting"            ,                     attribute_setting           );
            jItem.put("power_manage"                ,                      power_manage                );
            jItem.put("fault_diagnosis_normal",                            fault_diagnosis_normal      );
            jItem.put("status"                     ,                       status                      );
            jItem.put("time_setting"              ,                        time_setting                );
            jItem.put("soft_dog"                        ,                  soft_dog                    );
            jItem.put("capacity_normal"                       ,            capacity_normal             );
            jItem.put("d_dcsdata"                     ,                    d_dcsdata                   );
            jItem.put("keyborad_normal"                   ,                keyborad_normal             );
            jItem.put("mouse"                         ,                    mouse                       );
            jItem.put("cd_drive"                          ,                cd_drive                    );

            jItem.put("suggestion", suggestion);

            jAry.put(jItem);
            jNetObj.put("operatingStationList", jAry);
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
