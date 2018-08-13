package com.ator.supmaintenance_va.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by feizhenhua on 2018/5/5.
 */

public class UserUtil {

    public static Map<String, Employee>   staff=null;
    public static Employee mCurEmployee = null;
    public static UserUtil instance = null;

    public UserUtil(){

        staff = new HashMap<String, Employee>();
        mCurEmployee =  new Employee();

        initData();

    }

    public static UserUtil getInstance(){
        if (instance == null){
            instance = new UserUtil();
        }
        return instance;
    }

    public static boolean   initMap(){
        //should from server


        return true;
    }

    public static int  Login(String strIn){
        int nRtcode = 0;

        if (strIn.equals(""))
        {
            return -1;
        }
        //find user
        Employee e = staff.get(strIn);
        if (e!= null){
            mCurEmployee = e;
            nRtcode = 1;
        }

        return nRtcode;
    }

    private static void initData(){

        Employee  user = new Employee();
        user.name = "费振华";
        user.idcode = "da673e10";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }

        user = new Employee();
        user.name = "张雪吟";
        user.idcode = "dae02c10";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }


        user = new Employee();
        user.name = "杨晶";
        user.idcode = "1917d228";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }

        user = new Employee();
        user.name = "赵蔚文";
        user.idcode = "d16daf2d";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }

        user = new Employee();
        user.name = "姚冰";
        user.idcode = "518dfa0b";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }

        user = new Employee();
        user.name = "费振华";
        user.idcode = "e165c12f";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }

        user = new Employee();
        user.name = "杨晶";
        user.idcode = "c1e8ec2f";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }


        user = new Employee();
        user.name = "张雪吟";
        user.idcode = "7304616e";
        user.department = "智能终端事业部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }


        user = new Employee();
        user.name = "陈源之";
        user.idcode = "104c5d52";
        user.department = "大客户部";
        user.company = "浙江中控";

        staff.put(user.name,user);
        if (!user.idcode.isEmpty()){
            NFCCard card = new NFCCard(user);
            NFCCardMrg.getInstance().AddNFCCard(card);
        }

        user = new Employee();
        user.name = "宋奇";
        user.department = "上虞分公司";
        user.company = "中控运维";

        staff.put(user.name,user);

        user = new Employee();
        user.name = "施胜建";
        user.department = "如东分公司";
        user.company = "中控运维";

        staff.put(user.name,user);

        user = new Employee();
        user.name = "demo";

        staff.put(user.name,user);

    }

    public static Employee  finduser(String strIn){

        if (strIn.equals(""))
        {
            return null;
        }
        //find user
        Employee e = staff.get(strIn);
        if (e!= null){

        }
        return e;
    }

    public ArrayList<String> getUsers(String company,String dep){

        ArrayList<String> ay = new ArrayList<String>();

        Set keys = staff.keySet( );

        if(keys != null) {

            Iterator iterator = keys.iterator( );

            while(iterator.hasNext( )) {

                Object key = iterator.next( );
                Employee user = staff.get(key);

                if (user.company.equals(company) && user.department.equals(dep)){
                    ay.add(user.name);
                }

            }
        }

        return ay;
    }

}
