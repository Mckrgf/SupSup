package com.ator.supmaintenance.item;

import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/2/24.
 */

public class TempTaskManager {

    public ArrayList temptasks;
    public int       mDoneTaskCount;

    public TempTaskManager(){
        temptasks = new ArrayList();
        mDoneTaskCount = 0;

    }

    public void addTempTask(SupTempTask cp){
        temptasks.add(cp.clone());
    }


    public void DoneTask(SupTempTask cp,int index){
        if (!cp.done){
            return;
        }
        if (index >= temptasks.size()){
            return;
        }

        temptasks.set(index,cp.clone());
        mDoneTaskCount++;
    }

}
