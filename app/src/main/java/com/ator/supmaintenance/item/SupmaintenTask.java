package com.ator.supmaintenance.item;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/6/24.
 */

public class SupmaintenTask {

    public ArrayList<String>    mOperNames;
    public String               mOperDepartment;

    public Date   mStDate;
    public Date   mEndDate;

    public ArrayList<String>    mVerufyNames;
    public ArrayList<String>    mApporveNames;

    public String mUserCompany;
    public String mProjectName;

    public int mProcess = 0;//0- not start  1- doing 2-end 3-halt

    public SupmaintenTask(){

        mStDate = new Date();

    }



}
