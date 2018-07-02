package com.ator.supmaintenance.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class OpStationCheckAdapter {

    public String operator_id;

    public String operator_site;
    public String software_version;
    public String patch;
    public String operatingsystem;
    public String disk_freespace;
    public String cpu_usingrate;
    public String memory_total;
    public String memory_used;
    public String machines_timesync;
    public String configserver_confsync;
    public String lower_configsync;
    public String ip_addr;
    public String harddisk_norm;
    public String drivermax;
    public String hyperthreading_closed;
    public String netcardflow_closed;
    public String colors_show;
    public String noothersoftware;
    public String loadlist;
    public String dataresponse;
    public String stationprocess;
    public String turnalarm;
    public String projectdirectory;
    public String tendency;
    public String clock;
    public String faultdiagnosis;
    public String machines_configsync;
    public String configissue;
    public String commuredundancy;


    public String suggestion;
    public String append;
    public long plandate;

    public JSONObject  jobj = null;
    public JSONObject  jNetObj = null;
    public static String  strURL = "http://118.31.42.34:8090/maintenance/operstation";

    public boolean CheckAll(){
        if (        "".equals(operator_id)
                ||  "".equals(operator_site)
                ||  "".equals(software_version)
                ||  "".equals(patch)
                ||  "".equals(operatingsystem)

                ){

            return false;
        }


        return true;
    }

    public void MakeJasonObj(){

        if(jobj != null){
            jobj = null;
        }

        if (jNetObj!= null){
            jNetObj = null;
        }

        jobj = new JSONObject();
        try{

            jobj.put("operator_id",operator_id);
            jobj.put("operator_site",operator_site);
            jobj.put("software_version",software_version);
            jobj.put("patch",patch);
            jobj.put("operatingsystem",operatingsystem);
            jobj.put("disk_freespace",disk_freespace);
            jobj.put("cpu_usingrate",cpu_usingrate);
            jobj.put("memory_total",memory_total);
            jobj.put("memory_used",memory_used);
            jobj.put("machines_timesync",machines_timesync);
            jobj.put("configserver_confsync",configserver_confsync);
            jobj.put("lower_configsync",lower_configsync);
            jobj.put("ip_addr",ip_addr);
            jobj.put("harddisk_norm",harddisk_norm);
            jobj.put("drivermax",drivermax);
            jobj.put("hyperthreading_closed",hyperthreading_closed);
            jobj.put("netcardflow_closed",netcardflow_closed);
            jobj.put("colors_show",colors_show);
            jobj.put("noothersoftware",noothersoftware);
            jobj.put("loadlist",loadlist);
            jobj.put("dataresponse",dataresponse);
            jobj.put("stationprocess",stationprocess);
            jobj.put("turnalarm",turnalarm);
            jobj.put("projectdirectory",projectdirectory);
            jobj.put("tendency",tendency);
            jobj.put("clock",clock);
            jobj.put("faultdiagnosis",faultdiagnosis);
            jobj.put("machines_configsync",machines_configsync);
            jobj.put("configissue",configissue);
            jobj.put("commuredundancy",commuredundancy);


            jobj.put("suggestion",suggestion);
            jobj.put("append",append);
            jobj.put("plandate",plandate);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        jNetObj = new JSONObject();
        try{
            JSONArray jAry = new JSONArray();
            JSONObject jItem = new JSONObject();

            jItem.put("operator_id",operator_id);
            jItem.put("operator_site",operator_site);
            jItem.put("software_version",software_version);
            jItem.put("patch",patch);
            jItem.put("operatingsystem",operatingsystem);
            jItem.put("disk_freespace",disk_freespace);
            jItem.put("cpu_usingrate",cpu_usingrate);
            jItem.put("memory_total",memory_total);
            jItem.put("memory_used",memory_used);
            jItem.put("machines_timesync",machines_timesync);
            jItem.put("configserver_confsync",configserver_confsync);
            jItem.put("lower_configsync",lower_configsync);
            jItem.put("ip_addr",ip_addr);
            jItem.put("harddisk_norm",harddisk_norm);
            jItem.put("drivermax",drivermax);
            jItem.put("hyperthreading_closed",hyperthreading_closed);
            jItem.put("netcardflow_closed",netcardflow_closed);
            jItem.put("colors_show",colors_show);
            jItem.put("noothersoftware",noothersoftware);
            jItem.put("loadlist",loadlist);
            jItem.put("dataresponse",dataresponse);
            jItem.put("stationprocess",stationprocess);
            jItem.put("turnalarm",turnalarm);
            jItem.put("projectdirectory",projectdirectory);
            jItem.put("tendency",tendency);
            jItem.put("clock",clock);
            jItem.put("faultdiagnosis",faultdiagnosis);
            jItem.put("machines_configsync",machines_configsync);
            jItem.put("configissue",configissue);
            jItem.put("commuredundancy",commuredundancy);
            jItem.put("suggestion",suggestion);

            jAry.put(jItem);
            jNetObj.put("operStationCheckList",jAry);
            jNetObj.put("plandate",plandate);
            jNetObj.put("filename",operator_id+".xlsx");

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String GetJasonString(){
        if (jobj == null){
            return "";
        }else{
            String strout = jobj.toString();
            return  strout;
        }
    }

    public String GetNetJasonString(){
        if (jNetObj == null){
            return "";
        }else{
            String strout = jNetObj.toString();
            return  strout;
        }
    }

    public void MakeID(boolean bAppend){
        //use time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        operator_id = simpleDateFormat.format(date);

        if (bAppend){
            append = operator_id+".png";
        }

        try{
            Date dateNoMill = simpleDateFormat.parse(operator_id);

            plandate = dateNoMill.getTime();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
