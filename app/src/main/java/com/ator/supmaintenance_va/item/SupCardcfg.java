package com.ator.supmaintenance_va.item;

import org.json.JSONObject;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class SupCardcfg implements Cloneable{

    public String   cardid;
    public String   cardname;
    public String   planid;
    public String   nfcid;
    public String   qrcode;
    public String   lon;
    public String   lat;
    public boolean  bUsing;

    @Override
    public SupCardcfg clone(){
        SupCardcfg st = null;
        try{
            st = (SupCardcfg)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return st;
    }

    public static SupCardcfg parseCard(JSONObject obj){

        SupCardcfg card = new SupCardcfg();

        try {
            card.cardid = obj.getString("cardid");
            card.cardname = obj.getString("cardname");
            card.planid = obj.getString("planid");
            card.nfcid = obj.getString("nfcid");
            card.qrcode = obj.getString("qrcode");
            card.lon = obj.getString("lon");
            card.lat = obj.getString("lat");
            card.bUsing = obj.getBoolean("using");
        }catch (Exception e){
            e.printStackTrace();
        }

        if (card.cardid.equals("card2"))
        {
            card.nfcid = "048d0da29e3380";

        }else if (card.cardid.equals("card3")){
            card.nfcid = "bb166a9f000104e0";
        }

        return card;

    }

}
