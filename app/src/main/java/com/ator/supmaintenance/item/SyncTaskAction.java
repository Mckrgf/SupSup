package com.ator.supmaintenance.item;

import com.ator.supmaintenance.act.BaseActivity;

/**
 * Created by feizhenhua on 2018/6/11.
 */

public class SyncTaskAction {

    public static int END = -1;
    int mSyncTaskLeftCount = 0;
    private BaseActivity mAct;


    public SyncTaskAction(BaseActivity act){
        mAct = act;

    }

    public boolean start(){

        if (tryGetTaskListFromSvr() <=0){
            return false;
        }
        return true;
    }

    private int tryGetTaskListFromSvr(){

        mSyncTaskLeftCount = InspectionUtil.getInstance().getTaskMgr().mTasks.size();
        if (mSyncTaskLeftCount >0){
            trySyncTasks();
        }

        return mSyncTaskLeftCount;
    }

    public void trySyncTasks(){

        int nCount = InspectionUtil.getInstance().getTaskMgr().mTasks.size();
        int nPos = nCount - mSyncTaskLeftCount;
        SupInspectionTask task = (SupInspectionTask)InspectionUtil.getInstance().getTaskMgr().mTasks.get(nPos);
        String url = InspectionUtil.getInpectionUrl() + "/" +  Constant.URL_INSPECTION_TASKBASE + "/" + task.taskid;
        mAct.getJasonString(url,5000,200,true,"同步","正在同步任务...");

    }

    public int doNextSucc(){

        int val = 0;
        SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();

        int index = mgr.mTasks.size() - mSyncTaskLeftCount;
        SupInspectionTask task = (SupInspectionTask) mgr.mTasks.get(index);
        task.parseJstringFullTask(mAct.mStrGet);

        mSyncTaskLeftCount --;
        if (mSyncTaskLeftCount <=0){
            val = SyncTaskAction.END;
        }else {
            trySyncTasks();
        }

        return val;
    }

}
