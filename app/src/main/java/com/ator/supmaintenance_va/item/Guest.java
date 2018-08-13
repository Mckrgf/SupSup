package com.ator.supmaintenance_va.item;

import android.graphics.Bitmap;

import com.baidu.ocr.sdk.model.Word;

import java.util.Date;

/**
 * Created by feizhenhua on 2018/5/2.
 */

public class Guest {
    public Word     name = new Word();
    public Word     idcode = new Word();


    public Bitmap  cardphoto = null;
    public String  cardphotofile = "";
    public Date    comeDate;


}
