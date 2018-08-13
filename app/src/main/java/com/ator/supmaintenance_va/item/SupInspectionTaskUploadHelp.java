package com.ator.supmaintenance_va.item;

import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/6/14.
 */

public class SupInspectionTaskUploadHelp {

    private static SupInspectionTaskUploadHelp instance;
    private ArrayList mDoneTasks;


    public SupInspectionTaskUploadHelp(){
        mDoneTasks = new ArrayList();
    }

    public static SupInspectionTaskUploadHelp getInstance() {

        if (instance == null) {
            instance = new SupInspectionTaskUploadHelp();
        }
        return instance;
    }

    public void getDoneTasks(ArrayList donetasks){

        for (int i=0;i<donetasks.size();i++){
            SupInspectionTask task = (SupInspectionTask) donetasks.get(i);
            mDoneTasks.add(task);
        }

    }

}
