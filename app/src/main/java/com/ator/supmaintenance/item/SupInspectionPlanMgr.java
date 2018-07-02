package com.ator.supmaintenance.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class SupInspectionPlanMgr {

    public Map<String,SupInspectionPlan> plans = new LinkedHashMap<String,SupInspectionPlan>();

    public boolean addplan(SupInspectionPlan plan){

        if (("").equals(plan.planID)){
            return false;
        }

        plans.put(plan.planID,plan);
        return true;
    }

    public SupInspectionPlan getPlanByID(String planid){

        if (("").equals(planid)){
            return null;
        }

        SupInspectionPlan plan = plans.get(planid);

        return plan;
    }
}
