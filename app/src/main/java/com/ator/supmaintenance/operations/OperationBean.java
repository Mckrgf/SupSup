package com.ator.supmaintenance.operations;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yaobing on 2018/7/9.
 * Description 项目实体类
 */

public class OperationBean implements Serializable {
    private String facilitator;      //服务商
    private String company;          //企业名称
    private String project;          //项目名
    private String operator;         //操作人
    private String system_type;      //系统类型
    private long start_time;         //开始时间
    private long status;             //项目状态 未开始 进行中 已结束
    private String p_id;             //合同编号
    private ArrayList<String> equipment;     //项目中设备列表

    public ArrayList<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<String> equipment) {
        this.equipment = equipment;
    }


    String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    String getFacilitator() {
        return facilitator;
    }

    public void setFacilitator(String facilitator) {
        this.facilitator = facilitator;
    }

    String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    String getSystem_type() {
        return system_type;
    }

    public void setSystem_type(String system_type) {
        this.system_type = system_type;
    }

    long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    private long getStatus() {
        return status;
    }

    public void setStatus() {
        this.status = (long) 0;
    }

    @Override
    public String toString() {
        return getCompany() + "\n" + getFacilitator() + "\n" + getOperator() + "\n" + getProject() + "\n" + getSystem_type() + "\n" + getStart_time() + "\n" + getStatus() + "\n";
    }
}
