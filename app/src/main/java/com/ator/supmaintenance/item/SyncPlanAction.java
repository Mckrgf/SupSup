package com.ator.supmaintenance.item;

import android.nfc.FormatException;

import com.ator.supmaintenance.act.BaseActivity;

/**
 * Created by feizhenhua on 2018/6/11.
 */

public class SyncPlanAction {

    public static int END = -1;
    private int mStep = -1;
    int mSyncPlanLeftCount = 0;
    private BaseActivity mAct;


    public SyncPlanAction(BaseActivity act){
        mStep = -1;
        mAct = act;

    }

    private void clear(){

    }

    public void start(){

        mStep =0;
        getLocalPlanList();
        tryGetPlanListFromSvr();
    }
    private void getLocalPlanList(){

        InspectionUtil.getInstance().getmPlanDiff().addPlansFromLocal();

        return;
    }

    private void tryGetPlanListFromSvr(){
        String url = InspectionUtil.getInpectionUrl() + "/" +  Constant.URL_INSPECTION_PLAN;
        mAct.getJasonString(url,5000,200,false,"","");
    }

    private void parsePlanList(){

        InspectionUtil.getInstance().getmPlanDiff().addPlanInfoFromSvr(mAct.mStrGet);
    }

    private void diffPlansVersion(){

        mSyncPlanLeftCount = InspectionUtil.getInstance().getmPlanDiff().DifferPlans();

    }

    public void trySyncPlans(){

        int nCount = InspectionUtil.getInstance().getmPlanDiff().mPlansNeedSyncFromSvr.size();
        int nPos = nCount - mSyncPlanLeftCount;

        PlanInfo info = InspectionUtil.getInstance().getmPlanDiff().mPlansNeedSyncFromSvr.get(nPos);

        String url = InspectionUtil.getInpectionUrl() + "/" +  Constant.URL_INSPECTION_PLAN + "/" + info.sPlanID;
        mAct.getJasonString(url,5000,200,false,"","");

    }

    public int doNextSucc(){

        switch (mStep){
            case 0: //获得服务器上计划清单，比对计划内容，下载差异性计划
            {
                parsePlanList();
                diffPlansVersion();
                if (mSyncPlanLeftCount >0)  //需要更新计划
                {
                    mStep =1;
                    trySyncPlans();
                }else {
                    mStep = SyncPlanAction.END;
                }
            }
            break;
            case 1: //当step=1 时，需要逐个计划下载
            {
                SupInspectionPlan plan = SupInspectionPlan.parseJString(mAct.mStrGet);

                InspectionUtil.getInstance().getmPlanMgr().addplan(plan);

                mSyncPlanLeftCount --;
                if (mSyncPlanLeftCount <=0){
                    mStep = SyncPlanAction.END;
                }else {
                    trySyncPlans();
                }
            }
            break;

        }
        return mStep;
    }

}
