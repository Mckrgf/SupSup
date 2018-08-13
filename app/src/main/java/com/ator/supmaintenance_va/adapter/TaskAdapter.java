package com.ator.supmaintenance_va.adapter;


import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.act.InspectionMainActivity;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.SupInspectionTask;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feizhenhua on 2018/2/18.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private InspectionMainActivity context;

    public TaskAdapter(InspectionMainActivity context){
        this.context = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_firstlv, parent, false);

        return new TaskViewHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);
            }
        });

        holder.itemView.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.findViewById(R.id.tv_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        SupInspectionTask task = (SupInspectionTask) (InspectionUtil.getInstance().getTaskMgr().mTasks.get(position));

        Date sDate = task.mTaskPlanStartDate;

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
        String time1 = sdf1.format(task.mTimePlanEndDate);
        time1.trim();
        time1 = time1 + "前完成";

        holder.mTvDate.setText(time1);

        String strleft;
        long dmin = 0;
        long lnow = new Date().getTime();
        long lend = task.mTimePlanEndDate.getTime();
        if (lnow <lend) {
            long dt = lend - lnow;
            dmin = dt/(1000*60);
            strleft = String.format("剩余%d分",dmin);
        }else {
            strleft = "已截止";
        }
        holder.mTvTmLeft.setText(strleft);

        if (dmin <=30){
            holder.mLLWarn.setVisibility(View.VISIBLE);
        }else {
            holder.mLLWarn.setVisibility(View.GONE);
        }

        holder.mTvPlanName.setText(task.taskname);
        holder.mTvdes.setText(task.mStrDes);


        String strStatus = "未知";
        if (task.doing == 0){
            strStatus = "未开始";
        }else if(task.doing == 1) {
            strStatus = "进行中";
        }else if (task.doing == 2){
            strStatus = "已完成";
        }else if (task.doing == 3){
            strStatus = "手动结束";
        }
        holder.mTvStatus.setText(strStatus);


        if ("".equals(task.taskdata.doneuser)){

        }else {
            holder.mTvdoneuser.setText(task.taskdata.doneuser);
        }

        String strType;
        if(task.type.equals("0")) {
            strType = "日常巡检";
        }
        else {
            strType = "临时巡检";
        }
        holder.mTvType.setText(strType);

        String strP = String.format("%d/%d",task.doneCards.size(),task.getCardCount());
        holder.mTvProcess.setText(strP);

    }

    @Override
    public int getItemCount() {

        int count = InspectionUtil.getInstance().getTaskMgr().mTasks.size();

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        return viewType;
    }
    class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvDate;
        private TextView mTvType;
        private TextView mTvdes;
        private TextView mTvdoneuser;
        private TextView mTvPlanName;
        private TextView mTvStatus;
        private TextView mTvTmLeft;
        private LinearLayout mLLWarn;
        private TextView mTvProcess;


        public TaskViewHolder(View itemView) {
            super(itemView);

            mTvDate=(TextView) itemView.findViewById(R.id.tv_SDate);
            mTvTmLeft = (TextView) itemView.findViewById(R.id.tv_timeleft);
            mTvdes=(TextView)itemView.findViewById(R.id.tv_des);
            mTvType =  (TextView)itemView.findViewById(R.id.tv_type);
            mTvPlanName = (TextView)itemView.findViewById(R.id.tv_name);
            mTvdoneuser = (TextView)itemView.findViewById(R.id.tv_doneuser);
            mTvStatus = (TextView)itemView.findViewById(R.id.tv_status);
            mLLWarn = (LinearLayout)itemView.findViewById(R.id.ll_warning);
            mTvProcess = (TextView)itemView.findViewById(R.id.tv_process);

            mTvPlanName.setMovementMethod(ScrollingMovementMethod.getInstance());
            initBold(itemView);

        }

        private void initBold(View itemView){

            TextView tv;
            tv = (TextView)itemView.findViewById(R.id.tv_name);
            TextPaint paint = tv.getPaint();
            paint.setFakeBoldText(true);

        }


    }
}

