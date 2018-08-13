package com.ator.supmaintenance_va.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feizhenhua on 2018/5/2.
 */

public class PicturePagerAdapter extends PagerAdapter {

    private List<ImageView> images = new ArrayList<ImageView>();

    public PicturePagerAdapter(List<ImageView> images){
        this.images  = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {

        view.removeView(images.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(images.get(position));
        return images.get(position);
    }

}