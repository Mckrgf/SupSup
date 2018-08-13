package com.ator.supmaintenance_va.item;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by feizhenhua on 2018/4/23.
 */

public class MyAIHelper {

    static public String parseResult(String jString){

        try {
            //正式提取未知的key值
            JSONObject object = new JSONObject(jString);

            Iterator<String> sIterator = object.keys();

            String strOut = "";
            //循环并得到key列表
            while (sIterator.hasNext()) {
                // 获得key
                String key = sIterator.next();

                String value = object.getString(key);

                strOut += value;
            }
            return strOut;
        }catch (Exception e){
            e.printStackTrace();

        }
        return "";
    }
}
