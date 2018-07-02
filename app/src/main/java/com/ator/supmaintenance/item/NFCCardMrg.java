package com.ator.supmaintenance.item;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class NFCCardMrg {

    private static NFCCardMrg   instance = null;
    public static Map<String,NFCCard>  cards;

    public NFCCardMrg(){
        cards = new HashMap<>();
    }


    public static NFCCardMrg getInstance(){
        if(instance == null){
            instance = new NFCCardMrg();
        }

        return instance;
    }

    public boolean AddNFCCard(NFCCard card){

        if ("".equals(card.cid)){
            return false;
        }

        if (cards.containsKey(card.cid)){
            return false;
        }
        cards.put(card.cid,card);
        return true;

    }

    public NFCCard findCard(String strid){

        if ("".equals(strid)){
            return null;
        }
        NFCCard card = cards.get(strid);
        return card;

    }

    public boolean isIDExist(String strid){

        if ("".equals(strid)){
            return false;
        }

        return  cards.containsKey(strid);
    }


}
