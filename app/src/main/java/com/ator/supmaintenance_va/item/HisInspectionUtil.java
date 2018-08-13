package com.ator.supmaintenance_va.item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/6/18.
 */

public class HisInspectionUtil {

    public static final String QUERYBASE = "query";
    public static final String QUERYALL = "all";
    public static final String QUERYGOOD = "good";
    public static final String QUERYTYPE = "type";
    public static final String QUERYMISS = "miss";

    private static HisInspectionUtil instance;

    private ArrayList aryTaskInfos;
    private SupInspectionTask oneTask;

    public HisInspectionUtil(){

        aryTaskInfos = new ArrayList();

    }

    public static HisInspectionUtil getInstance() {

        if (instance == null) {
            instance = new HisInspectionUtil();

        }
        return instance;
    }

    public ArrayList getAryTaskInfos(){
        return aryTaskInfos;
    }

    public SupInspectionTask getOneTask(){
        return oneTask;
    }

    public void makeOneTaskByJString(String jStr){

        oneTask = null;
        if ("".equals(jStr)){
            return;
        }

        try {
            JSONObject obj = new JSONObject(jStr);
            JSONObject objtask = obj.getJSONObject("task");

            String pid = objtask.getString("planid");
            SupInspectionPlan plan = InspectionUtil.getInstance().getmPlanMgr().getPlanByID(pid);
            oneTask = new SupInspectionTask();
            oneTask.taskid = objtask.getString("taskid");
            oneTask.taskdata.taskid = oneTask.taskid;
            oneTask.planid = pid;
            oneTask.type = objtask.getString("type");
            oneTask.taskdata.good = objtask.getBoolean("good");
            oneTask.taskdata.miss = objtask.getBoolean("miss");
            oneTask.doing = objtask.getInt("doing");
            oneTask.taskdata.doneuser = objtask.getString("doneuser");
            oneTask.taskdata.donetime = objtask.getString("donetime");
            oneTask.taskdata.msec = objtask.getLong("msec");
            oneTask.plandate = objtask.getString("plandate");
            oneTask.plantime = objtask.getString("plantime");
            if (plan !=null){
                oneTask.taskname = plan.planName;
                oneTask.mStrDes = plan.planName;
                oneTask.misordered = plan.isOrdered;
                oneTask.mBelongto = plan.belongto;
                oneTask.mOwner = plan.belongto;
            }

            JSONArray arycard = obj.getJSONArray("donestateList");
            JSONArray arycp = obj.getJSONArray("cpdataList");


            for (int i=0;i<arycard.length();i++){
                JSONObject jcard = arycard.getJSONObject(i);
                SupCardData cardData = new SupCardData();
                cardData.cardid = jcard.getString("cardid");
                cardData.doneuser = jcard.getString("doneuser");
                cardData.isdone = jcard.getBoolean("isdone");
                cardData.donetime = jcard.getString("donetime");
                cardData.londata = jcard.getString("londata");
                cardData.latdata = jcard.getString("latdata");
                cardData.isplace = jcard.getBoolean("isplace");

                if (plan !=null){
                    cardData.mcardcfg = plan.getCardCfgById(cardData.cardid);
                }
                oneTask.addCard(cardData);
            }

            for (int i=0;i<arycp.length();i++){
                JSONObject jcp = arycp.getJSONObject(i);
                SupCheckPointData cpdata = new SupCheckPointData();
                cpdata.parseJson(jcp);
                if (plan!=null){
                    cpdata.mCPCfg = plan.getCPCfgbyId(cpdata.cpid);

                    for (int k = 0; k<oneTask.getCardCount(); k++){
                        SupCardData cardData = (SupCardData)oneTask.getCardData(k);
                        if (cpdata.mCPCfg.cardid.equals(cardData.cardid)){
                            cardData.addcCheckPoint(cpdata);
                            break;
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private Date makeQueryStTime(int nday){

        Date dt = new Date();
        try{
            SimpleDateFormat sdf1= new SimpleDateFormat("yyyyMMdd");
            String str1 = sdf1.format(dt);
            str1 = str1+"000000";
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyyMMddHHmmss");
            dt = sdf2.parse(str1);

            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE,-1*nday);
            dt = c.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }

        return dt;
    }

    public void ClearResult(){
        aryTaskInfos.clear();
        oneTask = null;
    }

    public HisInspectionHelp makeHisOneTaskHelp(String taskid){

        HisInspectionHelp help = new HisInspectionHelp();
        help.queryURL = InspectionUtil.getInpectionUrl()  + "/" +  Constant.URL_INSPECTION_TASKBASE + "/"  + taskid;

        return help;
    }

    public HisInspectionHelp makeHisHelp(int nday,int typeFilter,String planid){

        HisInspectionHelp help = new HisInspectionHelp();

        Date dateend = new Date();
        Date dateSt = makeQueryStTime(nday);

        long lend= dateend.getTime();
        long lst = dateSt.getTime();

        boolean  good = true;
        String   type = "0";
        boolean  miss = true;
        JSONObject obj = new JSONObject();

        try{

            switch (typeFilter){
                case 0:
                    help.queryURL = InspectionUtil.getInpectionUrl() + "/" + QUERYBASE + "/" + QUERYALL;
                    break;
                case 1:     //正常
                    help.queryURL = InspectionUtil.getInpectionUrl() + "/" + QUERYBASE + "/" + QUERYGOOD;
                    good = true;
                    obj.put("good",good);
                    break;
                case 2:     //异常
                    help.queryURL = InspectionUtil.getInpectionUrl() + "/" + QUERYBASE + "/" + QUERYGOOD;
                    good = false;
                    obj.put("good",good);
                    break;
                case 3:     //临检
                    help.queryURL = InspectionUtil.getInpectionUrl() + "/" + QUERYBASE + "/" + QUERYTYPE;
                    type = "1";
                    obj.put("type",type);
                    break;
                case 4:     //隐患
                    help.queryURL = InspectionUtil.getInpectionUrl() + "/" + QUERYBASE + "/" + QUERYMISS;
                    miss =true;
                    obj.put("miss",miss);
                    break;
            }
            obj.put("starttime",lst);
            obj.put("endtime",lend);
            obj.put("planid",planid);

            help.queryString = obj.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return help;

    }

    private void addTaskInfo(SupInspectionTask task){

        boolean badd = false;
        //计划开始时间为主排序，最大最前
        for (int i=0;i<aryTaskInfos.size();i++){
            SupInspectionTask ttt = (SupInspectionTask) aryTaskInfos.get(i);
            String strin = task.plandate + task.plantime;
            String strttt = ttt.plandate + ttt.plantime;
            int nCom = strin.compareTo(strttt);
            if ( nCom >0){
                aryTaskInfos.add(i,task);
                badd = true;
                break;
            }

        }

        if (!badd){
            aryTaskInfos.add(task);
        }

    }

    public int parseTaskInfos(String jStr){
        aryTaskInfos.clear();
        try {

            JSONObject obj = new JSONObject(jStr);
            JSONArray  aryTask = obj.getJSONArray("taskList");
            for (int i=0;i<aryTask.length();i++){
                JSONObject jtask = aryTask.getJSONObject(i);
                SupInspectionTask task = new SupInspectionTask();
                if (task.parseInfoByJSon(jtask)){
                    addTaskInfo(task);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return aryTaskInfos.size();
    }

}
