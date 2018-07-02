package com.ator.supmaintenance.item;

import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/6/25.
 */

public class Company {
    public String              name;
    public ArrayList<String>   departments = new ArrayList<String>();

    public void addDepartment(String strdep){

        departments.add(strdep);
    }
}
