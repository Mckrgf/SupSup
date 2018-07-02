package com.ator.supmaintenance.item;

import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/6/25.
 */

public class CompanyUtil {

    public static CompanyUtil instance = null;

    public ArrayList  companys = null;

    public static CompanyUtil getInstance(){
        if (instance == null){
            instance = new CompanyUtil();
        }

        return instance;
    }

    public CompanyUtil(){
        companys = new ArrayList();

        //test code

        Company com = new Company();
        com.name = "浙江中控";
        com.addDepartment("智能终端事业部");
        com.addDepartment("研发中心");
        com.addDepartment("大客户部");

        companys.add(com);

        com =new Company();
        com.name = "中控运维";
        com.addDepartment("上虞分公司");
        com.addDepartment("如东分公司");

        companys.add(com);

        com = new Company();
        com.name = "龙盛安诺";
        com.addDepartment("车间一");
        com.addDepartment("车间二");


    }
}
