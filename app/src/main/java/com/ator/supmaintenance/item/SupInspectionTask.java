package com.ator.supmaintenance.item;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/2/17.
 */

public class SupInspectionTask implements Cloneable{


    public SupInspectionTaskData   taskdata ;

    public String  taskid;
    public String  planid;

    public int      mPeriod = 1;
    public Date     mTaskPlanStartDate;
    public Date     mTimePlanEndDate;
    public Date     mDoneDate;

    public String  mBelongto ="";
    public String  mOwner = "";
    public int     doing = 0; //0:not started;1:started;2:end;3:abort;4:overtime
    public String  taskname ="";
    public String  mStrDes="";
    public String  type = "0";
    public boolean misordered = false;
    public String  plandate;
    public String  plantime;

    public ArrayList UndoCards;
    public ArrayList doneCards;

    public SupInspectionTask(){

        UndoCards = new ArrayList();
        doneCards = new ArrayList();

        taskdata = new SupInspectionTaskData();

    };

    private void removeCard(String cardid){
        if (cardid.equals("")){
            return;
        }

        for (int i=0;i<UndoCards.size();i++){
            SupCardData cardData = (SupCardData)UndoCards.get(i);
            if (cardData.cardid.equals(cardid)){
                UndoCards.remove(i);
                return;
            }
        }

        for (int i=0;i<doneCards.size();i++){
            SupCardData cardData = (SupCardData)doneCards.get(i);
            if (cardData.cardid.equals(cardid)){
                doneCards.remove(i);
                return;
            }
        }
    }

    public int addCard(SupCardData Card){

        if (Card.isdone){
            doneCards.add(Card);
        }else{
            UndoCards.add(Card);
        }

        return  getCardCount();
    }

    public boolean DoneCard(SupCardData Card){

        if(Card.isdone != true){
            return false;
        }

        int nIndex = -1;
        boolean bfd = false;
        SupCardData cd = null;

        for (int i=0;i<UndoCards.size();i++){
            cd = (SupCardData)UndoCards.get(i);
            if (cd.cardid.equals(Card.cardid)){
                nIndex = i;
                bfd =true;
                break;
            }
        }

        if (!bfd){
            return false;
        }



        UndoCards.remove(nIndex);
        doneCards.add(Card.clone());
        doing = 1;

        if (!Card.isgood){
            taskdata.good = false;
        }

        taskdata.doneuser = UserUtil.getInstance().mCurEmployee.name;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        mDoneDate = new Date();
        taskdata.donetime = sdf.format(mDoneDate);

        boolean balldone = true;
        if (UndoCards.size() > 0){
            balldone = false;
        }

        if (balldone){

            mDoneDate = new Date();
            doing = 2;
            boolean nomiss = true;

            for (int i=0;i<doneCards.size();i++){
                SupCardData cardData = (SupCardData) doneCards.get(i);
                if (!cardData.isgood){
                    taskdata.good = false;
                }
                if (!cardData.isdone){
                    nomiss = false;
                }
            }
            if (nomiss){
                taskdata.miss = false;
            }

            return true;

        }
        return false;
    }

    public int getCardCount(){

        return UndoCards.size()+doneCards.size();
    }

    public SupCardData getCardData(int nIndex){

        if (nIndex < UndoCards.size()){
            return (SupCardData) UndoCards.get(nIndex);
        }
        else {
            int nPos = nIndex - UndoCards.size();
            return (SupCardData)doneCards.get(nPos);
        }
    }

    @Override
    public Object clone(){
        SupInspectionTask sp = null;
        try {
            sp = (SupInspectionTask) super.clone();
        }catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        sp.taskdata = taskdata.clone();
        sp.UndoCards = new ArrayList();
        for (int i=0;i<UndoCards.size();i++)
        {
            SupCardData sk = (SupCardData)UndoCards.get(i);
            sp.UndoCards.add(sk.clone());
        }
        sp.doneCards = new ArrayList();
        for (int i=0;i<doneCards.size();i++)
        {
            SupCardData sk = (SupCardData)doneCards.get(i);
            sp.doneCards.add(sk.clone());
        }

        return  sp;
    }

    public void makeRecentTask(SupInspectionPlan plan){

        Date recentTime = makeRecentTime();
        taskid = makeTaskID(plan,recentTime);
        type = "0";
        planid = plan.planID;

        mPeriod = plan.period;
        mTaskPlanStartDate = countPlanStartDate(plan,recentTime);
        long ttt = mTaskPlanStartDate.getTime() + mPeriod * 3600 * 1000;
        mTimePlanEndDate = new Date(ttt);

        mBelongto = plan.belongto;
        mOwner = plan.belongto;
        doing = 0;
        taskname = plan.planName;
        mStrDes = plan.planName;
        misordered =plan.isOrdered;

        taskdata.taskid = taskid;

        for (int i =0;i<plan.Cards.size();i++){
            SupCardcfg card = (SupCardcfg) plan.Cards.get(i);
            SupCardData cardData = new SupCardData();
            cardData.mcardcfg = card.clone();
            cardData.cardid = card.cardid;
            addCard(cardData);
        }

        for (int j=0;j<plan.CheckPoints.size();j++){
            SupCheckPointCfg cp = (SupCheckPointCfg) plan.CheckPoints.get(j);
            SupCheckPointData cpdata = new SupCheckPointData();
            cpdata.mCPCfg = cp.clone();
            cpdata.cpid = cp.cpid;
            cpdata.type = cp.type;

            boolean bfind = false;
            SupCardData cardData = getCardDatabyID(cp.cardid);
            if (cardData !=null){
                cardData.addcCheckPoint(cpdata);
            }
        }

    }

    public Date makeRecentTime(){

        Date tmNow = new Date();
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyyMMddHH");
        String str1 = sdf1.format(tmNow);
        str1+="0000";

        Date dt2 =new Date();
        try{
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyyMMddHHmmss");
            dt2 = sdf2.parse(str1);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dt2;
    }

    public String makeTaskID(SupInspectionPlan plan,Date recentTime){

        String taskid = "";

        Date dtTask = countPlanStartDate(plan,recentTime);

        SimpleDateFormat sdf3= new SimpleDateFormat("yyyyMMddHHmm");
        String sappend = sdf3.format(dtTask);

        taskid = plan.planID+sappend;

        return taskid;
    }

    private Date countPlanStartDate(SupInspectionPlan plan,Date recentTime) {

        Date dtTask = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            String stmday = sdf1.format(recentTime);
            String stmBase = stmday + "000000";

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            Date dtBase = sdf2.parse(stmBase);

            long nCount = 0;
            int per = plan.period;

            long lted = recentTime.getTime();
            long ltbase = dtBase.getTime();

            long ldt = lted - ltbase;
            nCount = ldt / (3600 * 1000 * per);

            long tmTask = ltbase + nCount * per * 3600 * 1000;
            dtTask = new Date(tmTask);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dtTask;
    }


    public SupCardData getCardDatabyID(String cardid){
        SupCardData cd = null;
        boolean bfind = false;
        for (int i=0;i<UndoCards.size();i++){
            SupCardData cardData = (SupCardData)UndoCards.get(i);
            if (cardData.cardid.equals(cardid)){
                cd = cardData;
                bfind = true;
                break;
            }
        }
        if (!bfind){
            for (int i=0;i<doneCards.size();i++){
                SupCardData cardData = (SupCardData)doneCards.get(i);
                if (cardData.cardid.equals(cardid)){
                    cd = cardData;
                    bfind = true;
                    break;
                }
            }
        }
        return cd;
    }

    private boolean isGood(){
        for (int i=0;i<UndoCards.size();i++){
            SupCardData cardData = (SupCardData) UndoCards.get(i);
            if (!cardData.isgood){
                return false;
            }
        }

        for (int i=0;i<doneCards.size();i++){
            SupCardData cardData = (SupCardData) doneCards.get(i);
            if (!cardData.isgood){
                return false;
            }
        }

        return true;
    }

    public String makeUploadJstring(SupCardData card,SupCheckPointData cpdata){

        String strout = "";

        taskdata.good = isGood();

        JSONObject obj = new JSONObject();
        try {
            obj.put("taskid",taskid);
            obj.put("taskname",taskname);
            obj.put("type",type);
            obj.put("good",taskdata.good);
            obj.put("miss",taskdata.miss);
            obj.put("doing",doing);
            obj.put("doneuser",taskdata.doneuser);
            obj.put("donetime",taskdata.donetime);
            obj.put("msec",taskdata.msec);

            JSONArray arycard = new JSONArray();
            JSONArray aryCP = new JSONArray();

            if (card != null) {
                JSONObject jcard = card.getJsonObj();
                if (jcard != null){
                    arycard.put(jcard);
                }
            }
            if (cpdata != null) {
                JSONObject jcpdata = cpdata.makeJsonObj();
                if (jcpdata!=null){
                    aryCP.put(jcpdata);
                }
            }

            obj.put("donestateList",arycard);
            obj.put("cpdataList",aryCP);

            strout = obj.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return strout;
    }

    public static String getTaskUploadURL(){
        String str = InspectionUtil.getInpectionUrl() + "/" + Constant.URL_INSPECTION_TASKBASE;
        return str;
    }

    public boolean parseInfoByJSon(JSONObject obj){

        boolean bok = true;
        try {
            taskid = obj.getString("taskid");
            taskname = obj.getString("taskname");
            type = obj.getString("type");
            taskdata.good = obj.getBoolean("good");
            taskdata.miss = obj.getBoolean("miss");
            doing = obj.getInt("doing");
            taskdata.doneuser = obj.getString("doneuser");
            taskdata.donetime = obj.getString("donetime");
            taskdata.msec = obj.getLong("msec");
            planid = obj.getString("planid");
            plandate = obj.getString("plandate");
            plantime = obj.getString("plantime");

        }catch (Exception e){
            e.printStackTrace();
            bok = false;
        }
        return bok;
    }

    public void parseJstringFullTask(String strJ){

        if ("".equals(strJ)){
            return;
        }



        try {
            JSONObject obj = new JSONObject(strJ);
            JSONObject objtask = obj.getJSONObject("task");
            String strid = objtask.getString("taskid");
            String pid = objtask.getString("planid");
            if (taskid.equals(strid) == false){
                return;
            }
            if (planid.equals(pid) == false){
                return;
            }
            type = objtask.getString("type");
            taskdata.good = objtask.getBoolean("good");
            taskdata.miss = false;//objtask.getBoolean("miss");
            doing = objtask.getInt("doing");
            taskdata.doneuser = objtask.getString("doneuser");
            taskdata.donetime = objtask.getString("donetime");
            taskdata.msec = objtask.getLong("msec");
            plandate = objtask.getString("plandate");
            plantime = objtask.getString("plantime");

            JSONArray arycard = obj.getJSONArray("donestateList");
            JSONArray arycp = obj.getJSONArray("cpdataList");

            if (arycard.length() < getCardCount()){
                taskdata.miss = true;
            }

            for (int i=0;i<arycard.length();i++){
                JSONObject jcard = arycard.getJSONObject(i);
                String cid = jcard.getString("cardid");
                SupCardData cardData = getCardDatabyID(cid);
                cardData.isdone = jcard.getBoolean("isdone");
                cardData.doneuser = jcard.getString("doneuser");
                cardData.donetime = jcard.getString("donetime");
                cardData.londata = jcard.getString("londata");
                cardData.latdata = jcard.getString("latdata");
                cardData.isplace = jcard.getBoolean("isplace");
                cardData.isgood = true;

                int pos = 0;
                int ncpcount = arycp.length();
                for (int j = 0;j<ncpcount;j++){
                    JSONObject jcp = arycp.getJSONObject(pos);
                    String cpid = jcp.getString("cpid");

                    SupCheckPointData cp = cardData.getCPDatabyID(cpid);
                    if (cp!= null){
                        cp.parseJson(jcp);
                        if (cp.isproblem){
                            cardData.isgood = false;
                            taskdata.good = false;
                        }
                        arycp.remove(pos);
                    }else {
                        pos ++;
                    }
                }
                if (cardData.isdone){
                    removeCard(cardData.cardid);
                    addCard(cardData.clone());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
