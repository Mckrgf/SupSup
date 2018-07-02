package com.ator.supmaintenance.item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class SupInspectionPlan {

    public String   planID;
    public String   planName;
    public boolean  isOrdered;
    public int      period;
    public String   belongto;
    public String   starttime;
    public String   version;

    public ArrayList Cards;
    public ArrayList CheckPoints;


    public SupInspectionPlan(){

        Cards = new ArrayList();
        CheckPoints = new ArrayList();
    }

    public void addCard(SupCardcfg card){

        Cards.add(card.clone());

    }

    public SupCardcfg getCardCfgById(String cardid){
        SupCardcfg cardcfg = null;
        for (int i=0;i<Cards.size();i++){
            cardcfg = (SupCardcfg) Cards.get(i);
            if (cardcfg.cardid.equals(cardid)){
                return cardcfg;
            }
        }

        return cardcfg;
    }

    public void addCheckPoint(SupCheckPointCfg cp){

        CheckPoints.add(cp.clone());

    }

    public SupCheckPointCfg getCPCfgbyId(String cpid){
        SupCheckPointCfg checkPointCfg = null;
        for (int i=0;i<CheckPoints.size();i++){
            checkPointCfg = (SupCheckPointCfg)CheckPoints.get(i);
            if (checkPointCfg.cpid.equals(cpid)){
                return checkPointCfg;
            }
        }
        return checkPointCfg;

    }

    public static SupInspectionPlan parseJString(String jStr){

        if ("".equals(jStr)){
            return  null;
        }
        SupInspectionPlan plan = new SupInspectionPlan();

        try{
            JSONObject obj = new JSONObject(jStr);
            JSONObject planobj = obj.getJSONObject("plan");

            plan.planID = planobj.getString("planid");
            plan.planName = planobj.getString("planname");
            plan.isOrdered = planobj.getBoolean("isordered");
            plan.period = planobj.getInt("period");
            plan.belongto = planobj.getString("belongto");
            plan.starttime = planobj.getString("starttime");
            plan.version = planobj.getString("version");

            JSONArray cardAry = obj.getJSONArray("cardList");
            for (int i=0;i<cardAry.length();i++){
                JSONObject cardobj = cardAry.getJSONObject(i);
                SupCardcfg card = SupCardcfg.parseCard(cardobj);
                plan.addCard(card);

                if (!card.nfcid.isEmpty()){
                    NFCCard nfcCard = new NFCCard(card);
                    NFCCardMrg.getInstance().AddNFCCard(nfcCard);
                }

            }
            JSONArray cpAry = obj.getJSONArray("cpList");
            for (int j=0;j<cpAry.length();j++){
                JSONObject cpobj = cpAry.getJSONObject(j);
                plan.addCheckPoint(SupCheckPointCfg.parseCp(cpobj));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return plan;

    }

}
