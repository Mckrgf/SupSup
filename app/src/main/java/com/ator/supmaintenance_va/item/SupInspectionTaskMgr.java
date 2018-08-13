package com.ator.supmaintenance_va.item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/2/17.
 */

public class SupInspectionTaskMgr {

    public int          mCount;
    public ArrayList    mTasks;

//    public ArrayList    mDoneTasks;
    public ArrayList<String>    mDoneTaskIDs;       //不清理，永久保留


    public SupInspectionTaskMgr(){

        mTasks = new ArrayList();
//        mDoneTasks = new ArrayList();
        mDoneTaskIDs = new ArrayList<String>();
        mCount = 0;
    }

    public SupInspectionTask getCurTaskbyID(String taskid){
        SupInspectionTask sk = null;

        for (int i=0;i<mTasks.size();i++){
            SupInspectionTask task = (SupInspectionTask) mTasks.get(i);
            if (task.taskid.equals(taskid)){    //已有，不再增加
                sk = task;
                break;
            }
        }

        return sk;
    }

    public boolean  isTaskExist(String taskid){

        for (int i=0;i<mTasks.size();i++){
            SupInspectionTask task = (SupInspectionTask) mTasks.get(i);
            if (task.taskid.equals(taskid)){    //已有，不再增加
                return true;
            }
        }

        for (int i=0;i<mDoneTaskIDs.size();i++){
            String id = mDoneTaskIDs.get(i);
            if ( id.equals(taskid)){    //已有，不再增加
                return true;
            }
        }

        return false;
    }

    public void checkupTasks(){

        Date dnow = new Date();
        long lnow = dnow.getTime();

        int nCount = mTasks.size();
        int nPos = 0;
        for (int i=0;i<nCount;i++){

            SupInspectionTask task = (SupInspectionTask) mTasks.get(nPos);
            long ltaskend = task.mTimePlanEndDate.getTime();

            if (ltaskend <= lnow){
                task.doing = Constant.TASKDOING_OVERTIME;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                task.taskdata.donetime = sdf.format(dnow);
                task.taskdata.doneuser = UserUtil.getInstance().mCurEmployee.name;
                task.taskdata.miss = true;

                if (Donetask(task) == false){     //
                    nPos++;
                }

            }else{
                nPos ++;
            }

        }

    }


    public int addTask(SupInspectionTask task){ //插入时按计划结束时间排序，时间最小在最前

        int pos = 0;
        boolean bInsert = false;
        for (int i=0;i<mTasks.size();i++){
            SupInspectionTask sk = (SupInspectionTask) mTasks.get(i);
            long lin = task.mTimePlanEndDate.getTime();
            long lsk = sk.mTimePlanEndDate.getTime();

            if (lin < lsk){
                pos = i;
                bInsert = true;
                break;
            }
        }
        if (bInsert){
            mTasks.add(pos,task.clone());
        }else {
            mTasks.add(task.clone());
        }

        mCount++;
        return mCount;
    }

    public boolean Donetask(SupInspectionTask task){

        if (task.doing <= Constant.TASKDOING_STARTED){
            return false;
        }

        int index  =-1;
        boolean bfind = false;
        for (int i=0;i<mTasks.size();i++){
            SupInspectionTask sk = (SupInspectionTask) mTasks.get(i);
            if (sk.taskid.equals(task.taskid)){
                bfind = true;
                index = i;
                break;
            }
        }

        if (!bfind){
            return  false;
        }

        mDoneTaskIDs.add(task.taskid);
//        mDoneTasks.add(task);
        mTasks.remove(index);

        return true;

    }

    public void cleanTasks(){

        int count = mTasks.size();
        int pos = 0;
        for (int i=0;i<count;i++){
            SupInspectionTask task = (SupInspectionTask)mTasks.get(pos);
            if (task.doing >= 2){
                mTasks.remove(pos);
//                mDoneTasks.add(task);
            }
            else {
                pos++;
            }
        }
    }

}
