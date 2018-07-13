package com.ator.supmaintenance.operations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.MyApplication;
import com.ator.supmaintenance.item.MyDateUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.im_menu)
    ImageView imMenu;
    @BindView(R.id.rc_op_his)
    RecyclerView rcOpHis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_history);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initView() {
        imBack.setOnClickListener(this);
        tvTitle.setText("历史记录");
        imMenu.setVisibility(View.GONE);
    }

    private void initData() {
        rcOpHis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter adapter = new OperationHistoryActivity.Adapter();
        rcOpHis.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.OpViewHolder> {


        @Override
        public Adapter.OpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Adapter.OpViewHolder(View.inflate(OperationHistoryActivity.this, R.layout.item_operation_his_lists, null));
        }

        @Override
        public void onBindViewHolder(Adapter.OpViewHolder holder, final int position) {
            final OperationBean bean = MyApplication.op_data.get(position);
            holder.tv_company.setText(bean.getCompany());
            holder.tv_facilitator.setText(bean.getFacilitator());
            holder.tv_operator.setText(bean.getOperator());
            holder.tv_project.setText(bean.getProject());
            holder.tv_time.setText(MyDateUtils.getDateFromLong(bean.getStart_time(), MyDateUtils.date_Format2));
            holder.tv_type.setText(bean.getSystem_type());
            holder.pid.setText(bean.getP_id());

            holder.item_op_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OperationHistoryActivity.this, OperationDetailActivity.class);
                    intent.putExtra("BEAN", MyApplication.op_data.get(position));
                    intent.putExtra("POS", position);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return MyApplication.op_data.size();
        }

        class OpViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_project;
            private TextView tv_company;
            private TextView tv_facilitator;
            private TextView tv_type;
            private TextView tv_time;
            private TextView tv_operator;
            private LinearLayout item_op_list;
            private TextView pid;

            OpViewHolder(View itemView) {
                super(itemView);

                tv_project = itemView.findViewById(R.id.tv_project);
                tv_company = itemView.findViewById(R.id.tv_company);
                tv_operator = itemView.findViewById(R.id.tv_operator);
                tv_type = itemView.findViewById(R.id.tv_type);
                tv_time = itemView.findViewById(R.id.tv_SDate);
                tv_facilitator = itemView.findViewById(R.id.tv_facilitator);
                item_op_list = itemView.findViewById(R.id.item_op_list);
                pid = itemView.findViewById(R.id.tv_pid);

            }
        }
    }
}
