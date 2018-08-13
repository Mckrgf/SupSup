package com.ator.supmaintenance_va.item;

import org.json.JSONObject;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class PlanInfo {

    public String sPlanName;
    public String sVersion;
    public String sPlanID;

    public void parseInfo(JSONObject obj){

        try {
            sPlanID = obj.getString("planid");
            sPlanName = obj.getString("planname");
            sVersion = obj.getString("version");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
