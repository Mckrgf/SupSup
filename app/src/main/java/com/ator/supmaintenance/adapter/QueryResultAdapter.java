package com.ator.supmaintenance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.MyRecUtil;


/**
 * Created by feizhenhua on 2018/2/19.
 */


public class QueryResultAdapter extends RecyclerView.Adapter<QueryResultAdapter.QueryResultViewHolder> {

    @Override
    public QueryResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);

        return new QueryResultViewHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(QueryResultViewHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);
            }
        });
        ShowDatas(holder,position);
    }

    private void ShowDatas(QueryResultViewHolder holder, int position){

        int index = (int)MyRecUtil.mArySub.get(position);

        String strKeyDes = MyRecUtil.mAryKeyDes.get(index);
        String strResult = MyRecUtil.mAryResult.get(index);

        holder.mTvKeydes.setText(strKeyDes);
        holder.mTvResult.setText(strResult);


    }


    @Override
    public int getItemCount() {

        return MyRecUtil.mArySub.size();
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
    class QueryResultViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvKeydes;
        public TextView mTvResult;


        public QueryResultViewHolder(View itemView) {
            super(itemView);
            mTvKeydes=(TextView) itemView.findViewById(R.id.tv_keydes);
            mTvResult=(TextView) itemView.findViewById(R.id.tv_result);
        }
    }


}


