package com.ator.supmaintenance.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ator.supmaintenance.R;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by feizhenhua on 2018/2/19.
 */

public class HisFileListAdapter extends RecyclerView.Adapter<HisFileListAdapter.HisFileListViewHolder> {


    static public ArrayList<String> mFileAry = new  ArrayList<String>();

    @Override
    public HisFileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_list, parent, false);

        return new HisFileListViewHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(HisFileListViewHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);
            }
        });
        ShowDatas(holder,position);
    }

    private void ShowDatas(HisFileListViewHolder holder,int position){

        if (mFileAry.size()<=0){
            return;
        }

        String sIndex = "No." + position;

        holder.mTvFileNo.setText(sIndex);

        String strOut = mFileAry.get(position);
        strOut.replaceAll(".f","");

        if (strOut.lastIndexOf("bad") >=0)
        {
            strOut = "<font color='#ff0000'>" + strOut +"</font>";
            holder.mTvFileName.setText(Html.fromHtml(strOut));

        }else {
            holder.mTvFileName.setText(strOut);
        }


    }


    @Override
    public int getItemCount() {

        return mFileAry.size();
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
    class HisFileListViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvFileNo;
        private TextView mTvFileName;


        public HisFileListViewHolder(View itemView) {
            super(itemView);
            mTvFileNo=(TextView) itemView.findViewById(R.id.tv_fileindex);
            mTvFileName=(TextView) itemView.findViewById(R.id.tv_filename);
        }
    }

    public void ClearResult(){

        mFileAry.clear();
    }
}


