package com.ator.supmaintenance_va.item;

import com.amap.api.maps.model.LatLng;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/2/17.
 */

public class SupCardData implements Cloneable{

    public SupCardcfg     mcardcfg;

    public String         cardid;
    public String         doneuser;
    public boolean        isdone;
    public String         donetime;
    public String         londata;
    public String         latdata;
    public boolean         isplace;

    public boolean         isgood = true;
    public ArrayList      checkPoints;

    public SupCardData(){
        isgood = true;
        mcardcfg = new SupCardcfg();
        checkPoints = new ArrayList();
    };

    public void addcCheckPoint(SupCheckPointData cp){
        checkPoints.add(cp);
    }

    public boolean DoneCheck(SupCheckPointData cp){

        if (cp.done == false){
            return false;
        }

        int indexCP = 0;
        boolean bfind =false;
        for (int i=0;i<checkPoints.size();i++){
            SupCheckPointData cpdata = (SupCheckPointData) checkPoints.get(i);
            if (cp.cpid.equals(cpdata.cpid)){
                indexCP = i;
                bfind = true;
                break;
            }
        }
        if (!bfind){
            return false;
        }

        checkPoints.set(indexCP,cp.clone());

        if (cp.isproblem){
            isgood = false;
        }

        boolean ballok = true;
        for (int i=0;i<checkPoints.size();i++) {
            SupCheckPointData cploop = (SupCheckPointData) checkPoints.get(i);
            if (cploop.done == false) {
                ballok = false;
                break;
            }
        }

        if (ballok) {
            isdone = true;
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            donetime = sdf.format(dt);

            LatLng ll = MyApplication.getMylocation();
            londata = Double.toString(ll.longitude);
            latdata = Double.toString(ll.latitude);
            isplace = true;

            doneuser = UserUtil.getInstance().mCurEmployee.name;

        }

        return ballok;

    }

    @Override
    public SupCardData clone(){
        SupCardData st = null;
        try{
            st = (SupCardData)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        st.mcardcfg = mcardcfg.clone();
        st.checkPoints = new ArrayList();
        for (int i=0;i<checkPoints.size();i++) {
            SupCheckPointData sp = (SupCheckPointData) checkPoints.get(i);
            st.checkPoints.add(sp.clone());
        }
        return st;
    }

    public SupCheckPointData getCPDatabyID(String cpid){
        SupCheckPointData cp = null;

        for (int i=0;i<checkPoints.size();i++){
            SupCheckPointData cpdata = (SupCheckPointData)checkPoints.get(i);
            if (cpdata.cpid.equals(cpid)){
                cp = cpdata;
                break;
            }
        }

        return cp;

    }

    public JSONObject getJsonObj(){

        JSONObject obj = null;

        try{
            obj = new JSONObject();
            obj.put("cardid",cardid);
            obj.put("doneuser",doneuser);
            obj.put("isdone",isdone);
            obj.put("donetime",donetime);
            obj.put("londata",londata);
            obj.put("latdata",latdata);
            obj.put("isplace",isplace);

        }catch (Exception e){
            e.printStackTrace();
        }

        return  obj;

    }

}
