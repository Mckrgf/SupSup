package com.ator.supmaintenance.operations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.act.MainActivity;
import com.ator.supmaintenance.item.MyApplication;
import com.ator.supmaintenance.item.MyDateUtils;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.UserUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationMainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.im_menu)
    ImageView imMenu;
    @BindView(R.id.ll_alltop)
    RelativeLayout llAlltop;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_department)
    TextView tvUserDepartment;
    @BindView(R.id.ll_userlogin)
    LinearLayout llUserlogin;
    @BindView(R.id.tv_taskcount)
    TextView tvTaskcount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private static final String TAG = "OperationMainActivity";
    @BindView(R.id.tv_add_project)
    TextView tvAddProject;
    @BindView(R.id.tv_check_history)
    TextView tvCheckHistory;
    private AlertDialog dialog;
    private EditText et_facilitator;
    private EditText et_company;
    private EditText et_project;
    private EditText et_operator;
    private EditText et_time;
    private RadioButton rb_300;
    private RadioButton rb_700;
    private EditText et_pid;
    private ArrayList<OperationBean> op_data_ing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

        //筛选出非完成/非终止状态的任务
        op_data_ing = new ArrayList<>();
        for (int i = 0; i < MyApplication.op_data.size(); i++) {
            if (MyApplication.op_data.get(i).getStatus() == 0) {
                op_data_ing.add(MyApplication.op_data.get(i));
            }
        }

        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String strTime = sdf.format(dd);
        tvTime.setText(strTime);

        tvUserName.setText(UserUtil.getInstance().mCurEmployee.name);
        tvUserDepartment.setText(UserUtil.getInstance().mCurEmployee.department);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        tvTaskcount.setText("" + op_data_ing.size());
        tvTime.setText(MyDateUtils.getCurTime(MyDateUtils.date_Format2));
    }

    private void initView() {
        tvAddProject.setOnClickListener(this);
        getSupportActionBar().hide();
        tvTitle.setText("运维巡检");
        imMenu.setOnClickListener(this);
        tvCheckHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_project:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //自定义的布局文件
                dialog = builder
                        .setView(R.layout.dialog_add_project) //自定义的布局文件
                        .create();
                dialog.show();
                Button bt_cancel = dialog.getWindow().findViewById(R.id.bt_cancel);
                bt_cancel.setOnClickListener(this);
                Button bt_confirm = dialog.getWindow().findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(this);
                et_facilitator = dialog.getWindow().findViewById(R.id.et_facilitator);
                et_company = dialog.getWindow().findViewById(R.id.et_company);
                et_project = dialog.getWindow().findViewById(R.id.et_project);
                et_operator = dialog.getWindow().findViewById(R.id.et_operator);
                et_time = dialog.getWindow().findViewById(R.id.et_time);
                et_time.setText(MyDateUtils.getCurTime(MyDateUtils.date_Format2));
                rb_700 = dialog.getWindow().findViewById(R.id.rb_700);
                rb_300 = dialog.getWindow().findViewById(R.id.rb_300);
                et_pid = dialog.getWindow().findViewById(R.id.et_pid);

                break;
            case R.id.bt_cancel:
                dialog.cancel();
                break;
            case R.id.bt_confirm:
                if (!(et_facilitator.getText().toString().equals(""))
                        && !(et_company.getText().toString().equals(""))
                        && !(et_project.getText().toString().equals(""))
                        && !(et_pid.getText().toString().equals(""))
                        && !(et_operator.getText().toString().equals(""))
                        && !(et_time.getText().toString().equals(""))) {
                    OperationBean bean = new OperationBean();
                    bean.setFacilitator(String.valueOf(et_facilitator.getText()));
                    bean.setCompany(String.valueOf(et_company.getText()));
                    bean.setProject(String.valueOf(et_project.getText()));
                    bean.setOperator(String.valueOf(et_operator.getText()));
                    bean.setP_id(String.valueOf(et_pid.getText()));
                    if (rb_300.isChecked()) {
                        bean.setSystem_type("300xp");
                    } else {
                        bean.setSystem_type("700");
                    }
                    try {
                        bean.setStart_time(MyDateUtils.getLongDateFromString(String.valueOf(et_time.getText()), MyDateUtils.date_Format2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    MyApplication.op_data.add(bean);
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                    initData();
                    dialog.cancel();
                } else {
                    Toast.makeText(this, "所有字段均不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.im_menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_check_history:
                Intent intent1 = new Intent(this, OperationHistoryActivity.class);
                startActivity(intent1);
                break;
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.OpViewHolder> {


        @Override
        public OpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OpViewHolder(View.inflate(OperationMainActivity.this, R.layout.item_operation_lists, null));
        }

        @Override
        public void onBindViewHolder(OpViewHolder holder, final int position) {
            final OperationBean bean = op_data_ing.get(position);
            holder.tv_company.setText(bean.getCompany());
            holder.tv_facilitator.setText(bean.getFacilitator());
            holder.tv_operator.setText(bean.getOperator());
            holder.tv_project.setText(bean.getProject());
            holder.tv_time.setText(MyDateUtils.getDateFromLong(bean.getStart_time(), MyDateUtils.date_Format2));
            holder.tv_type.setText(bean.getSystem_type());
            holder.pid.setText(bean.getP_id());

            holder.tv_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < MyApplication.op_data.size(); i++) {
                        if (MyApplication.op_data.get(i).equals(bean)) {
                            MyApplication.op_data.get(i).setStatus(1);
                            break;
                        }
                    }
                    Toast.makeText(OperationMainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                    initData();
                }
            });

            holder.item_op_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OperationMainActivity.this, OperationDetailActivity.class);
                    intent.putExtra("BEAN", (Serializable) op_data_ing.get(position));
                    intent.putExtra("POS", position);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return op_data_ing.size();
        }

        class OpViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_project;
            private TextView tv_company;
            private TextView tv_facilitator;
            private TextView tv_type;
            private TextView tv_time;
            private TextView tv_submit;
            private TextView tv_operator;
            private TextView tv_stop;
            private LinearLayout item_op_list;
            private TextView pid;

            OpViewHolder(View itemView) {
                super(itemView);

                tv_project = itemView.findViewById(R.id.tv_project);
                tv_company = itemView.findViewById(R.id.tv_company);
                tv_operator = itemView.findViewById(R.id.tv_operator);
                tv_type = itemView.findViewById(R.id.tv_type);
                tv_time = itemView.findViewById(R.id.tv_SDate);
                tv_submit = itemView.findViewById(R.id.tv_submit);
                tv_stop = itemView.findViewById(R.id.tv_stop);
                tv_facilitator = itemView.findViewById(R.id.tv_facilitator);
                item_op_list = itemView.findViewById(R.id.item_op_list);
                pid = itemView.findViewById(R.id.tv_pid);

            }
        }
    }
}
