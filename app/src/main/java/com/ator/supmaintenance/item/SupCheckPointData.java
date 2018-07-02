package com.ator.supmaintenance.item;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/2/17.
 */

public class SupCheckPointData implements Cloneable {

    public SupCheckPointCfg   mCPCfg;

    public String      cpid;
    public String      type = "0";
    public String      tag;
    public String      inputvalue = "";
    public String      inputbool = "";
    public String      inputfloat = "";
    public String      unit = "";
    public String      inputshcktype;
    public String      memo;
    public String      picfile1 = "";
    public String      picfile2 = "";
    public String      picfile3 = "";
    public String      videofile= "";
    public String      soundfile= "";
    public String      datafile= "";
    public boolean      isproblem = false;
    public boolean      done;


    public String getType(){
        return type;
    }

    public void setFValue(float fV){
        inputfloat = String.format("%.2f",fV);
    }
    public SupCheckPointData(){
        done = false;
        mCPCfg = new SupCheckPointCfg();
    }

    public static String makeImgFilePath(String Appendstr){
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String strFile = Appendstr + sdf2.format(new Date())+".png";
        return strFile;
    }

    @Override
    public SupCheckPointData clone(){
        SupCheckPointData cl = null;
        try{
            cl = (SupCheckPointData)super.clone();
        }catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        cl.mCPCfg = mCPCfg.clone();

        return  cl;
    }

    public void parseJson(JSONObject jcp){
        try {
            cpid = jcp.getString("cpid");
            type = jcp.getString("type");
            tag = jcp.getString("tag");
            inputvalue = jcp.getString("inputvalue");
            inputbool = jcp.getString("inputbool");
            inputfloat = jcp.getString("inputfloat");
            unit = jcp.getString("unit");
            inputshcktype = jcp.getString("inputshcktype");
            memo = jcp.getString("memo");
            picfile1 = jcp.getString("picfile1");
            picfile2 = jcp.getString("picfile2");
            picfile3 = jcp.getString("picfile3");
            videofile = jcp.getString("videofile");
            soundfile = jcp.getString("soundfile");
            datafile = jcp.getString("datafile");
            isproblem = jcp.getBoolean("isproblem");
            done = jcp.getBoolean("done");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public JSONObject makeJsonObj(){

        JSONObject obj = null;

        try{
            obj = new JSONObject();
            obj.put("cpid",cpid);
            obj.put("type",type);
            obj.put("tag",tag);
            obj.put("inputvalue",inputvalue);
            obj.put("inputbool",inputbool);
            obj.put("inputfloat",inputfloat);
            obj.put("unit",unit);
            obj.put("inputshcktype",inputshcktype);
            obj.put("memo",memo);
            obj.put("picfile1",picfile1);
            obj.put("picfile2",picfile1);
            obj.put("picfile3",picfile1);
            obj.put("videofile",videofile);
            obj.put("soundfile",soundfile);
            obj.put("datafile",datafile);
            obj.put("isproblem",isproblem);
            obj.put("done",done);

        }catch (Exception e){
            e.printStackTrace();
        }

        return  obj;

    }
}
