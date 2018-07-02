package com.ator.supmaintenance.item;

/**
 * Created by feizhenhua on 2018/6/5.
 */

public class NFCCard {

    public String   cid = "";
    public String   cdes;
    public boolean  bUsed;
    public Object   carddata = null;
    public int      datatype = 0;      //1--card,2--user

    public NFCCard(){

    }

    public NFCCard(String cid,String cdes,boolean bUsed,int datatype,Object carddata){
        this.cid = cid;
        this.cdes = cdes;
        this.bUsed = bUsed;
        this.datatype = datatype;
        this.carddata = carddata;

    }

    public NFCCard(Employee user){
        this.cid = user.idcode;
        this.cdes = user.name;
        this.carddata = user;
        this.datatype = 2;
        this.bUsed = true;
    }

    public NFCCard(SupCardcfg card){
        this.cid = card.nfcid;
        this.cdes = card.cardname;
        this.carddata = card;
        this.datatype = 1;
        this.bUsed = true;
    }

}
