package com.ator.supmaintenance.item;



/**
 * Created by feizhenhua on 2018/4/6.
 */

public class MyURLs {

    public static String getSvrAddr(){
        return Constant.ADDR_TESTSVR;
    }
    public static String getSvrPort(){
        return Constant.PORT_TESTSVR;
    }



    public static String makeInspctionURL(String append){

        String strOut ="http://"+getSvrAddr() +":"+Constant.INSPECTION_PORT+"/"+Constant.URL_INSPECTION_TOP_LEVEL+"/"+append;


        return strOut;
    }



}
