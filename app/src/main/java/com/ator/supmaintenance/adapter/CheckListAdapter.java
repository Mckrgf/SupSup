package com.ator.supmaintenance.adapter;


import android.support.v7.widget.RecyclerView;
import android.graphics.Bitmap;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.MyApplication;
import com.ator.supmaintenance.item.SupCheckPointData;
import com.ator.supmaintenance.item.SupInspectionTask;
import com.ator.supmaintenance.item.SupInspectionTaskMgr;
import com.ator.supmaintenance.item.SupCardData;

/**
 * Created by feizhenhua on 2018/2/20.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder> {

    public String mTaskid = "";
    public String mCardid = "";

    public void SetIndexs(String taskid,String cardid){
        mTaskid = taskid;
        mCardid = cardid;
    }

    @Override
    public CheckListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_lists, parent, false);

        return new CheckListViewHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void ShowDatas(CheckListViewHolder holder,int position)
    {

        SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();

        SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(mTaskid);
        if (task == null){
            return;
        }
        SupCardData cardData = (SupCardData) task.getCardDatabyID(mCardid);
        if (cardData == null){
            return;
        }
        SupCheckPointData cp = (SupCheckPointData)(cardData.checkPoints.get(position));

        //特殊代码
        if (cp.cpid.equals("3")||cp.cpid.equals("6")||cp.cpid.equals("9")){
            cp.type = "测温";
            cp.mCPCfg.type = "测温";
        }
        if (cp.cpid.equals("4")||cp.cpid.equals("8")){
            cp.type = "测振";
            cp.mCPCfg.type = "测振";
        }

        holder.mTvName.setText(cp.mCPCfg.cpname);

        String strType =cp.getType();

        holder.mTvType.setText(strType);

        String strCheck ;
        if(cp.done == false){
            strCheck = "未录";

            holder.mRoundStatus.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.bg_round_gray));

        }else {
            strCheck= "已录";
            if (cp.isproblem){
                holder.mRoundStatus.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.bg_round_red));
            }else {
                holder.mRoundStatus.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.bg_round_green));
            }

        }


        String strV="";
        String strTm = "";

        if(cp.done){                         //显示值和时间
            strV = String.format("%s %s",cp.inputfloat,cp.unit );
        }

        holder.mTvValue.setText(strV);

        if ("".equals(cp.picfile1) == false){
            Bitmap bmp = FileUtil.loadBmp(holder.itemView.getContext(),InspectionUtil.mStrPicSub,cp.picfile1);
        }

        int count = getCpcount();
        if (position >= count-1){
            holder.mBottomLine.setVisibility(View.VISIBLE);
        }else {
            holder.mBottomLine.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBindViewHolder(CheckListViewHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);
            }
        });

        ShowDatas(holder,position);

    }

    @Override
    public int getItemCount() {
        int count = getCpcount();



        return count;
    }

    private int getCpcount(){

        int count;
        SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
        SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(mTaskid);
        if (task == null){
            return 0;
        }
        SupCardData cardData = (SupCardData) task.getCardDatabyID(mCardid);
        if (cardData == null){
            return 0;
        }

        count = cardData.checkPoints.size();

        return count;
    }
    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        return viewType;
    }
    class CheckListViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvName;
        private TextView mTvType;
        private TextView mTvValue;
        private View     mBottomLine;
        private View     mRoundStatus;

        public CheckListViewHolder(View itemView){
            super(itemView);

            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvValue = (TextView)itemView.findViewById(R.id.tv_value);
            mBottomLine = (View) itemView.findViewById(R.id.line_bottom);
            mRoundStatus = (View) itemView.findViewById(R.id.roung_status);

            initBold(itemView);

        }

        private void initBold(View itemView){

//            TextView textView = (TextView)itemView.findViewById(R.id.tv_name);
//            textView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            TextView tv;
            tv = (TextView)itemView.findViewById(R.id.tv_name);
            TextPaint paint = tv.getPaint();
            paint.setFakeBoldText(true);

        }
    }
}
