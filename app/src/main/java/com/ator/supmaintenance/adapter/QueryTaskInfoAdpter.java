package com.ator.supmaintenance.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.act.InputDataActivity;
import com.ator.supmaintenance.act.QueryInspectionActivity;
import com.ator.supmaintenance.act.ShockMeasureActivity;
import com.ator.supmaintenance.act.ShowCheckPointActivity;
import com.ator.supmaintenance.act.infraredActivity;
import com.ator.supmaintenance.item.Constant;
import com.ator.supmaintenance.item.HisInspectionUtil;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.SupCardData;
import com.ator.supmaintenance.item.SupCheckPointData;
import com.ator.supmaintenance.item.SupInspectionTask;
import com.ator.supmaintenance.item.SupInspectionTaskMgr;

import org.w3c.dom.Text;

/**
 * Created by feizhenhua on 2018/6/18.
 */

public class QueryTaskInfoAdpter extends RecyclerView.Adapter<QueryTaskInfoAdpter.QueryTaskInfoHolder>  {

    private QueryInspectionActivity context;

    public QueryTaskInfoAdpter(QueryInspectionActivity context){
        this.context = context;
    }

    @Override
    public QueryTaskInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_querytaskinfo, parent, false);

        return new QueryTaskInfoHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(final QueryTaskInfoHolder holder, int position) {

        final int pos = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(pos);

            }
        });

        SupInspectionTask task = (SupInspectionTask)HisInspectionUtil.getInstance().getAryTaskInfos().get(position);

        holder.mTvName.setText(task.taskname);
        holder.mTvDoneUser.setText(task.taskdata.doneuser);

        String planstr = task.plandate + task.plantime;
        String fullstr = planstr + " - " +  task.taskdata.donetime;
        holder.mTvDoneTime.setText(fullstr);


        if (task.taskdata.miss){
            holder.mTvMiss.setText("有漏检");
        }else {
            holder.mTvMiss.setText("无漏检");
        }

        if (task.type.equals("0")){
            holder.mTvType.setText("日常巡检");
        }else {
            holder.mTvType.setText("临时巡检");
        }

        String strdo = "";
        switch (task.doing){
            case 0:
                strdo = "尚未开始";
                holder.mTvDoing.setBackground(context.getResources().getDrawable(R.drawable.bg_status_blue));
                break;
            case 1:
                strdo = "正在进行";
                holder.mTvDoing.setBackground(context.getResources().getDrawable(R.drawable.bg_status_green));
                break;
            case 2:
                strdo = "正常结束";
                holder.mTvDoing.setBackground(context.getResources().getDrawable(R.drawable.bg_status_gray));
                break;
            case 3:
                strdo = "强制结束";
                holder.mTvDoing.setBackground(context.getResources().getDrawable(R.drawable.bg_status_red));
                break;
            case 4:
                strdo = "超时关闭";
                holder.mTvDoing.setBackground(context.getResources().getDrawable(R.drawable.bg_status_red));
                break;
        }

        holder.mTvDoing.setText(strdo);

        if (!task.taskdata.good && task.doing >0){
            holder.mImBad.setVisibility(View.VISIBLE);
        }else {
            holder.mImBad.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {

        int n = HisInspectionUtil.getInstance().getAryTaskInfos().size();

        return n;
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
    class QueryTaskInfoHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvType;
        private TextView mTvDoing;
        private TextView mTvDoneTime;
        private TextView mTvDoneUser;
        private TextView mTvMiss;
        private ImageView  mImBad;


        public QueryTaskInfoHolder(View itemView) {
            super(itemView);
            mTvName =(TextView) itemView.findViewById(R.id.tv_name);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvDoing = (TextView)itemView.findViewById(R.id.tv_doing);
            mTvDoneTime = (TextView) itemView.findViewById(R.id.tv_donetime);
            mTvDoneUser = (TextView) itemView.findViewById(R.id.tv_doneuser);
            mTvMiss = (TextView) itemView.findViewById(R.id.tv_miss);
            mImBad = (ImageView)itemView.findViewById(R.id.im_bad);

            initBold(itemView);
            mTvName.setMovementMethod(ScrollingMovementMethod.getInstance());
        }

        private void initBold(View itemView){
            TextView textView = (TextView)itemView.findViewById(R.id.tv_name);
            textView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            textView = (TextView)itemView.findViewById(R.id.tv_t1);
            textView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            textView = (TextView)itemView.findViewById(R.id.tv_t2);
            textView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


        }

    }

}
