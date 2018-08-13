package com.ator.supmaintenance_va.adapter;


import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.act.CardListActivity;
import com.ator.supmaintenance_va.act.InputDataActivity;
import com.ator.supmaintenance_va.act.ShockMeasureActivity;
import com.ator.supmaintenance_va.act.ShowCheckPointActivity;
import com.ator.supmaintenance_va.act.infraredActivity;
import com.ator.supmaintenance_va.item.Constant;
import com.ator.supmaintenance_va.item.HisInspectionUtil;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.RtEnv;
import com.ator.supmaintenance_va.item.SupCardData;
import com.ator.supmaintenance_va.item.SupCheckPointData;
import com.ator.supmaintenance_va.item.SupInspectionTask;
import com.ator.supmaintenance_va.item.SupInspectionTaskMgr;

/**
 * Created by feizhenhua on 2018/2/19.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardListViewHolder> {

    private CardListActivity context;
    private String mType;

    public CardListAdapter(CardListActivity context,String type){
        this.context = context;
        this.mType = type;
    }

    @Override
    public CardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_seclv, parent, false);

        return new CardListViewHolder(view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(final CardListViewHolder holder, int position) {

        if(holder.mRecyclerView.getAdapter()==null) {
            if (mType.equals("")){
                final CheckListAdapter adapter = new CheckListAdapter();

                SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
                SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);
                if (task == null){
                    return;
                }
                SupCardData cardData = (SupCardData) task.getCardData(position);
                if (cardData == null){
                    return;
                }

                adapter.SetIndexs(task.taskid,cardData.cardid);
                adapter.setOnItemClickListener(new CheckListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
                        SupInspectionTask task = (SupInspectionTask)mgr.getCurTaskbyID(adapter.mTaskid);
                        if (task == null){
                            return;
                        }
                        SupCardData cardData = (SupCardData) task.getCardDatabyID(adapter.mCardid);
                        if (cardData == null){
                            return;
                        }
                        SupCheckPointData cp = (SupCheckPointData) cardData.checkPoints.get(position);
                        if (cp == null){
                            return;
                        }

                        InspectionUtil.getInstance().mCurTaskid = adapter.mTaskid;
                        InspectionUtil.getInstance().mCurCardid = adapter.mCardid;
                        InspectionUtil.getInstance().mCurCpid = cp.cpid;

                        if (cp.done){
                            RtEnv.startActivityForResult(ShowCheckPointActivity.class,1, Constant.TASK_TYPE_NORMAL);
                        }else {
                            if (cp.getType().equals("手录")){
                                RtEnv.startActivityForResult(InputDataActivity.class,1, Constant.TASK_TYPE_NORMAL);
                            }else if (cp.getType().equals("测温")){
                                RtEnv.startActivityForResult(infraredActivity.class,1, Constant.TASK_TYPE_NORMAL);
                            }else if (cp.getType().equals("测振")){
                                RtEnv.startActivityForResult(ShockMeasureActivity.class,1,Constant.TASK_TYPE_NORMAL);
                            }else {
                                RtEnv.startActivityForResult(InputDataActivity.class,1, Constant.TASK_TYPE_NORMAL);
                            }
                        }

                    }
                });
                holder.mRecyclerView.setAdapter(adapter);
            }else {

            }

        }else {
            if (mType.equals("")){
                holder.mRecyclerView.getAdapter().notifyDataSetChanged();
            }

        }

        if (mType.equals("")){
            final int pos = holder.getLayoutPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(pos);

                    if (holder.mRecyclerView.getVisibility() != View.GONE){
                        holder.mRecyclerView.setVisibility(View.GONE);
                        holder.mIvArrow.setImageResource(R.drawable.ic_down_gray);
                    }else {
                        holder.mRecyclerView.setVisibility(View.VISIBLE);
                        holder.mIvArrow.setImageResource(R.drawable.ic_up_gray);
                    }
                    holder.mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }

        SupInspectionTask task = null;
        if (mType.equals("")){
            SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
            task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);

        }else {
            task = HisInspectionUtil.getInstance().getOneTask();
        }

        if (task == null){
            return;
        }

        SupCardData cardData = (SupCardData) task.getCardData(position);

        String strName = cardData.mcardcfg.cardname;
        holder.mTvName.setText(strName);

        String strIsDone = "已检";
        String time1 = "待执行";
        String strUser = "待录入";
        if (cardData.isdone == false){
            strIsDone = "未检";

        }else {
            time1 = cardData.donetime;
            strUser = cardData.doneuser;

        }

        holder.mTvStatus.setText(strIsDone);
        holder.mTvTime.setText(time1);
        holder.mTvUser.setText(strUser);

    }

    @Override
    public int getItemCount() {
        int count = 0;
        SupInspectionTask task = null;
        if (mType.equals("")){
            SupInspectionTaskMgr mgr = InspectionUtil.getInstance().getTaskMgr();
            task = (SupInspectionTask)mgr.getCurTaskbyID(InspectionUtil.getInstance().mCurTaskid);
        }else {
            task = HisInspectionUtil.getInstance().getOneTask();
        }

        if (task == null){
            return 0;
        }else {
            count = task.getCardCount();
        }

        return count;
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
    class CardListViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvArea;
        private TextView mTvStatus;
        private TextView mTvUser;
        private TextView mTvTime;
        private ImageView mIvArrow;

        private RecyclerView mRecyclerView;

        public CardListViewHolder(View itemView) {
            super(itemView);
            mTvArea = (TextView) itemView.findViewById(R.id.tv_area);
            mTvName =(TextView) itemView.findViewById(R.id.tv_name);
            mTvStatus =(TextView) itemView.findViewById(R.id.tv_status);
            mTvUser = (TextView) itemView.findViewById(R.id.tv_doneuser);
            mTvTime = (TextView)itemView.findViewById(R.id.tv_donetime);
            mIvArrow = (ImageView) itemView.findViewById(R.id.iv_arrow);

            initBold(itemView);
            mTvName.setMovementMethod(ScrollingMovementMethod.getInstance());

            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

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


