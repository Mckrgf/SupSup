package com.ator.supmaintenance.operations;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.MyApplication;
import com.ator.supmaintenance.item.MyDateUtils;
import com.ator.supmaintenance.item.UserUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationMainActivity extends AppCompatActivity implements View.OnClickListener {

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
    private EditText et_system_type;
    private EditText et_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String strTime = sdf.format(dd);
        tvTime.setText(strTime);

        tvUserName.setText(UserUtil.getInstance().mCurEmployee.name);
        tvUserDepartment.setText(UserUtil.getInstance().mCurEmployee.department);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        tvTaskcount.setText("" + MyApplication.op_data.size());
        tvTime.setText(MyDateUtils.getCurTime(MyDateUtils.date_Format2));
    }

    private void initView() {
        tvAddProject.setOnClickListener(this);
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
                et_system_type = dialog.getWindow().findViewById(R.id.et_system_type);
                et_time = dialog.getWindow().findViewById(R.id.et_time);

                break;
            case R.id.bt_cancel:
                dialog.cancel();
                break;
            case R.id.bt_confirm:
                if (!(et_facilitator.getText().toString().equals(""))
                        && !(et_company.getText().toString().equals(""))
                        && !(et_project.getText().toString().equals(""))
                        && !(et_operator.getText().toString().equals(""))
                        && !(et_system_type.getText().toString().equals(""))
                        && !(et_time.getText().toString().equals(""))) {
                    OperationBean bean = new OperationBean();
                    bean.setFacilitator(String.valueOf(et_facilitator.getText()));
                    bean.setCompany(String.valueOf(et_company.getText()));
                    bean.setProject(String.valueOf(et_project.getText()));
                    bean.setOperator(String.valueOf(et_operator.getText()));
                    bean.setSystem_type(String.valueOf(et_system_type.getText()));
                    try {
                        bean.setStart_time(MyDateUtils.getLongDateFromString(String.valueOf(et_time.getText()), MyDateUtils.date_Format2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    MyApplication.op_data.add(bean);
                    initData();
                    dialog.cancel();
                } else {
                    Toast.makeText(this, "所有字段均不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.OpViewHolder> {


        @Override
        public OpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OpViewHolder(View.inflate(OperationMainActivity.this, R.layout.item_operation_lists, null));
        }

        @Override
        public void onBindViewHolder(OpViewHolder holder, int position) {
            OperationBean bean = MyApplication.op_data.get(position);
            holder.tv_company.setText(bean.getCompany());
            holder.tv_facilitator.setText(bean.getFacilitator());
            holder.tv_operator.setText(bean.getOperator());
            holder.tv_project.setText(bean.getProject());
            holder.tv_time.setText(MyDateUtils.getDateFromLong(bean.getStart_time(), MyDateUtils.date_Format2));
            holder.tv_type.setText(bean.getSystem_type());

            holder.tv_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.tv_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
            private TextView tv_submit;
            private TextView tv_operator;
            private TextView tv_stop;

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

            }
        }
    }
}
