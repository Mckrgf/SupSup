package com.ator.supmaintenance.item;

import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/6/14.
 */

public class SupInspectionTaskData implements Cloneable{

    public String   taskid = "";
    public String   doneuser = "";
    public String   donetime = "";      //计划完成时间
    public long     msec = 0;           //计划开始时间    毫秒
    public boolean  good = true;
    public boolean  miss = true;

    @Override
    public SupInspectionTaskData clone(){
        SupInspectionTaskData sp = null;
        try {
            sp = (SupInspectionTaskData) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return  sp;
    }

}
