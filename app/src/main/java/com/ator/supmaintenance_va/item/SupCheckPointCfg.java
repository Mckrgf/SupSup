package com.ator.supmaintenance_va.item;

import org.json.JSONObject;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class SupCheckPointCfg implements Cloneable{

    public String   cpid;
    public String   cpname;
    public String   cardid;
    public String   planid;
    public String   type;
    public String   tag;
    public String   hlimit;
    public String   llimit;
    public boolean  blimit;
    public String   unit;
    public String   require;

    @Override
    public SupCheckPointCfg clone(){
        SupCheckPointCfg st = null;
        try{
            st = (SupCheckPointCfg)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return st;
    }

    public static SupCheckPointCfg parseCp(JSONObject obj){

        SupCheckPointCfg cp = new SupCheckPointCfg();

        try {
            cp.cpid = obj.getString("cpid");
            cp.cpname = obj.getString("cpname");
            cp.cardid = obj.getString("cardid");
            cp.planid = obj.getString("planid");
            cp.type = obj.getString("type");
            cp.tag = obj.getString("tag");
            cp.hlimit = obj.getString("hlimit");
            cp.llimit = obj.getString("llimit");
            cp.blimit = obj.getBoolean("blimit");
            cp.unit = obj.getString("unit");
            cp.require = obj.getString("require");

        }catch (Exception e){
            e.printStackTrace();
        }

        return cp;
    }

}
