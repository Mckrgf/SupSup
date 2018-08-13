package com.ator.supmaintenance_va.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by feizhenhua on 2018/4/19.
 */

public class MyPhoto {

    public static Bitmap loadBmp(File bmpFile)
    {
        String strPic = bmpFile.getPath();
        Bitmap bmp = null;
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            bmp = BitmapFactory.decodeFile(strPic, opt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  bmp;
    }
}
