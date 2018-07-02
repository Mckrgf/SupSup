package com.ator.supmaintenance.item;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by feizhenhua on 2018/5/5.
 */

public class InspectionUtil {

        private static InspectionUtil           instance = null;
        private static PlanDiff                 mPlanDiff = null;

        private static SupInspectionTaskMgr     mTaskMgr = null;
        private static SupInspectionPlanMgr     mPlanMgr = null;
        private static TempTaskManager          mTempTaskMgr = null;


        public static String    mCurTaskid = "";
        public static String    mCurCardid = "";
        public static String    mCurCpid = "";


        public static int mCurTempTaskIndex = -1;

        public static String mStrPicPath = "";
        public static String mStrPicSub = "inspection";

        public static ArrayList<String> results = null;

        public void makeMyTasksbyPlan(){

                makeMyTasks();
                mTaskMgr.checkupTasks();
//                SupInspectionTaskUploadHelp.getInstance().getDoneTasks(mTaskMgr.mDoneTasks);
//                mTaskMgr.mDoneTasks.clear();

        }

        private boolean isMyPlan(SupInspectionPlan plan){
            if (plan.belongto.equals("")){
                return  true;
            }

            if (plan.belongto.equals(UserUtil.getInstance().mCurEmployee.department)||
                    plan.belongto.equals(UserUtil.getInstance().mCurEmployee.name) ){
                return true;
            }

            return false;

        }

        private boolean isMyTask(SupInspectionTask task){
            if (task.mOwner.equals("") && task.mBelongto.equals("")){
                return  true;
            }

            if (task.mBelongto.equals(UserUtil.getInstance().mCurEmployee.department)||
                    task.mBelongto.equals(UserUtil.getInstance().mCurEmployee.name) ){
                return true;
            }

            if (task.mOwner.equals(UserUtil.getInstance().mCurEmployee.department)||
                    task.mOwner.equals(UserUtil.getInstance().mCurEmployee.name) ){
                return true;
            }

            return false;

        }
        public void makeMyTasks(){

                int pos =0;
                for (int i=0;i<mTaskMgr.mTasks.size();i++){
                    SupInspectionTask task = (SupInspectionTask) mTaskMgr.mTasks.get(pos);
                    if (!isMyTask(task)){
                        mTaskMgr.mTasks.remove(pos);
                    }else {
                        pos++;
                    }
                }

                for (Map.Entry<String, SupInspectionPlan> entry :mPlanMgr.plans.entrySet()){

                        String planid = entry.getKey();
                        SupInspectionPlan plan = entry.getValue();
                        if (!isMyPlan(plan)){
                            continue;
                        }

                        SupInspectionTask task = new SupInspectionTask();
                        Date recentTime = task.makeRecentTime();
                        String taskid = task.makeTaskID(plan,recentTime);

                        if (mTaskMgr.isTaskExist(taskid)){
                                continue;
                        }else {
                                task.makeRecentTask(plan);
                                mTaskMgr.addTask(task);
                        }
                }

        }


        public static String getInpectionUrl(){
                String url = Constant.URL_HEAD + MyNetHelper.sInspectionAddr +":"+ MyNetHelper.sInspectionPort +"/"+Constant.URL_INSPECTION_TOP_LEVEL;
                return url;
        }

        public InspectionUtil() {

                mPlanMgr = new SupInspectionPlanMgr();
                mTaskMgr = new SupInspectionTaskMgr();
                mTempTaskMgr = new TempTaskManager();

                mPlanDiff = new PlanDiff();

        }

        public void initStrings(String[] results_in){
                results = new ArrayList<String>();
                for (String sItem:results_in){
                        results.add(sItem);
                }

        }

        public static InspectionUtil getInstance() {

                if (instance == null) {
                        instance = new InspectionUtil();

                }
                return instance;
        }

        public SupInspectionTaskMgr getTaskMgr() {
                return mTaskMgr;
        }


        public TempTaskManager getTempMrg(){
                return mTempTaskMgr;
        }

        public SupInspectionPlanMgr getmPlanMgr(){
                return mPlanMgr;
        }

        public PlanDiff getmPlanDiff(){
                return mPlanDiff;
        }

        public static String makePicFilePath(Context context, String dir) {
                String directoryPath = "";

                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
                        directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
                } else {//没外部存储就使用内部存储
                        directoryPath = context.getFilesDir() + File.separator + dir;
                }

                File file = new File(directoryPath);
                if (!file.exists()) {//判断文件目录是否存在
                        file.mkdirs();
                }

                mStrPicPath = directoryPath;

                return directoryPath;
        }


    public InspectionHelp FindLabel(String strInput) {
            InspectionHelp rt = new InspectionHelp();
            for (int i = 0; i < mTaskMgr.mTasks.size(); i++) {
                SupInspectionTask task = (SupInspectionTask) mTaskMgr.mTasks.get(i);
                for (int j = 0; j < task.getCardCount(); j++) {
                        SupCardData cardData = (SupCardData) task.getCardData(j);
                        if (cardData.mcardcfg.nfcid.equals(strInput)) {
                                rt.mTaskid =task.taskid;
                                rt.mCardid = cardData.cardid;
                                rt.mCpid ="";
                                break;
                        }
                }
            }

            return rt;
    }

    public void SetCurrent(InspectionHelp help){

            if (help.isExist()){
                    mCurTaskid = help.mTaskid;
                    mCurCardid = help.mCardid;
                    mCurCpid = help.mCpid;
            }

    }


}
