package com.ator.supmaintenance.adapter;


import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.InspectionUtil;

import com.ator.supmaintenance.item.SupTempTask;
import com.ator.supmaintenance.item.TempTaskManager;

/**
 * Created by feizhenhua on 2018/5/6.
 */

public class TempTaskListAdapter extends RecyclerView.Adapter<TempTaskListAdapter.TempTaskListViewHolder> {

    @Override
    public TempTaskListAdapter.TempTaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temptask_lists, parent, false);

        return new TempTaskListAdapter.TempTaskListViewHolder(view);
    }

    private TempTaskListAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(TempTaskListAdapter.OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void ShowDatas(TempTaskListAdapter.TempTaskListViewHolder holder, int position) {

        TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();
        SupTempTask cp = (SupTempTask) (mgr.temptasks.get(position));

        holder.mTvName.setText(cp.mCPCfg.cpname);
        holder.mTvdes.setText(cp.mCPCfg.require);

        String strType = "临时巡检";
        holder.mTvType.setText(strType);



        String strV = "";
        String strTm = "";

        if (cp.done) {                         //显示值和时间
            strV = cp.inputfloat;

        }

    }

    @Override
    public void onBindViewHolder(TempTaskListAdapter.TempTaskListViewHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);
            }
        });

        ShowDatas(holder, position);

    }

    @Override
    public int getItemCount() {
        int count = 0;

        TempTaskManager mgr = InspectionUtil.getInstance().getTempMrg();

        count = mgr.temptasks.size();

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        return viewType;
    }

    class TempTaskListViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDate;
        private TextView mTvType;
        private TextView mTvdes;
        private TextView mTvName;
        private TextView mTvUser;

        public TempTaskListViewHolder(View itemView) {
            super(itemView);

            mTvDate=(TextView) itemView.findViewById(R.id.tv_SDate);
            mTvdes=(TextView)itemView.findViewById(R.id.tv_des);
            mTvType =  (TextView)itemView.findViewById(R.id.tv_type);
            mTvName = (TextView)itemView.findViewById(R.id.tv_name);
            mTvUser = (TextView)itemView.findViewById(R.id.tv_owner);

            initBold(itemView);
        }

        private void initBold(View itemView){

            TextView tv;
            tv = (TextView)itemView.findViewById(R.id.tv_name);
            TextPaint paint = tv.getPaint();
            paint.setFakeBoldText(true);

            tv = (TextView)itemView.findViewById(R.id.tv_t1);
            paint = tv.getPaint();
            paint.setFakeBoldText(true);

            tv = (TextView)itemView.findViewById(R.id.tv_t2);
            paint = tv.getPaint();
            paint.setFakeBoldText(true);

            tv = (TextView)itemView.findViewById(R.id.tv_t3);
            paint = tv.getPaint();
            paint.setFakeBoldText(true);

        }
    }
}