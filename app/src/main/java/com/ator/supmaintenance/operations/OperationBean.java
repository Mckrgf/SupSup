package com.ator.supmaintenance.operations;

/**
 * Created by yaobing on 2018/7/9.
 * Description xxx
 */

public class OperationBean {
    public String facilitator;      //服务商
    public String company;          //企业名称
    public String project;          //项目名
    public String operator;         //操作人
    public String system_type;      //系统类型
    public long start_time;       //开始时间
    public long status;           //项目状态 未开始 进行中 已结束
    public String getFacilitator() {
        return facilitator;
    }

    public void setFacilitator(String facilitator) {
        this.facilitator = facilitator;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSystem_type() {
        return system_type;
    }

    public void setSystem_type(String system_type) {
        this.system_type = system_type;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getCompany()+"\n"+getFacilitator()+"\n"+getOperator()+"\n"+getProject()+"\n"+getSystem_type()+"\n"+getStart_time()+"\n"+getStatus()+"\n";
    }
}
