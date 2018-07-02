package com.ator.supmaintenance.item;

/**
 * Created by feizhenhua on 2018/5/8.
 */

public class InspectionHelp {

    public String mTaskid = "";
    public String mCardid = "";
    public String mCpid = "";

    public InspectionHelp(){

    }


    public boolean isExist(){

        if (mTaskid.equals("")) {
            return false;
        }
        return true;
    }

}
