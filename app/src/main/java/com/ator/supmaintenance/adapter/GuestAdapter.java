package com.ator.supmaintenance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.Guest;
import com.ator.supmaintenance.item.GuestUtil;

import java.text.SimpleDateFormat;

/**
 * Created by feizhenhua on 2018/5/2.
 */

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {



    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idcard_person, parent, false);

        return new GuestViewHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);
            }
        });
        ShowDatas(holder,position);
    }

    private void ShowDatas(GuestViewHolder holder,int position){

        Guest man = GuestUtil.mPersons.get(position);
//        if (man.cardphoto != null){
//            holder.mIvPhoto.setImageBitmap(man.cardphoto);
//        }else if (!man.cardphotofile.equals("")) {
//
//        }

        holder.mTvTitle.setText(man.name.toString());
        if (man.idcode.toString().equals("") == false){
            holder.mTvIdcode.setText(man.idcode.toString());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sTm = simpleDateFormat.format(man.comeDate);

        holder.mTvTime.setText(sTm);
        if (man.cardphoto != null){
            holder.mIvPhoto.setImageBitmap(man.cardphoto);
        }

    }

    @Override
    public int getItemCount() {

        return GuestUtil.mPersons.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        switch(position) {
            case 0:
                break;
        }
        return viewType;
    }
    class GuestViewHolder extends RecyclerView.ViewHolder {

        private TextView    mTvTitle;
        private TextView    mTvIdcode;
        private ImageView   mIvPhoto;
        private TextView    mTvPhone;
        private TextView    mTvTime;
        private TextView      mTvWhere;

        public GuestViewHolder(View itemView) {
            super(itemView);
            mTvTitle=(TextView) itemView.findViewById(R.id.tv_title);
            mTvIdcode=(TextView) itemView.findViewById(R.id.tv_idcard);
            mTvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            mTvWhere = (TextView) itemView.findViewById(R.id.tv_where);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mIvPhoto=(ImageView)itemView.findViewById(R.id.iv_photo);

        }
    }

}