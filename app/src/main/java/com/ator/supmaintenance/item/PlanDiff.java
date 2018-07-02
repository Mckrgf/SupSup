package com.ator.supmaintenance.item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class PlanDiff {

    private String  mJStringFromSvr;
    public ArrayList<PlanInfo>  mPlanInfoFromSvr;
    public ArrayList<PlanInfo>  mPlanInfoFromLocal;

    public ArrayList<PlanInfo>  mPlansNeedSyncFromSvr;

    public PlanDiff(){
        mPlanInfoFromSvr = new ArrayList<PlanInfo>();
        mPlanInfoFromLocal = new ArrayList<PlanInfo>();
        mPlansNeedSyncFromSvr = new ArrayList<PlanInfo>();
    }

    public int getSize(){
        return mPlanInfoFromSvr.size();
    }

    public int addPlansFromLocal(){
        return 0;
    }

    public int addPlanInfoFromSvr(String jString){

        mJStringFromSvr = jString;
        int nCount = 0;
        try {

            JSONObject obj = new JSONObject(mJStringFromSvr);

            JSONArray pary = obj.getJSONArray("planList");

            for (int i=0;i<pary.length();i++){
                PlanInfo info = new PlanInfo();
                JSONObject jObj = pary.getJSONObject(i);
                info.parseInfo(jObj);
                mPlanInfoFromSvr.add(info);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return nCount;
    }

    public int DifferPlans(){

        mPlansNeedSyncFromSvr.clear();
        //以server 为准，不在 svr 中的不再需要
        for (int i=0;i<mPlanInfoFromSvr.size();i++){
            PlanInfo infoS = mPlanInfoFromSvr.get(i);
            PlanInfo infoL = getPlanInfoFromLocal(infoS.sPlanID);
            if (infoL == null){ //本地没有，需要更新
                mPlansNeedSyncFromSvr.add(infoS);
            }else if (infoL.sVersion.equals(infoS.sVersion)==false){    //版本不同，需要更新
                mPlansNeedSyncFromSvr.add(infoS);
            }
        }
        //
        return mPlansNeedSyncFromSvr.size();


    }

    public PlanInfo getPlanInfoFromLocal(String pID){

        PlanInfo rtInfo = null;
        for (int i=0;i<mPlanInfoFromLocal.size();i++){
            PlanInfo info = mPlanInfoFromLocal.get(i);
            if (info.sPlanID.equals(pID)){
                rtInfo = info;
                break;
            }

        }

        return rtInfo;
    }

}
