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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.MyApplication;
import com.ator.supmaintenance.item.MyDateUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.im_menu)
    ImageView imMenu;
    @BindView(R.id.et_facilitator)
    EditText etFacilitator;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_project)
    EditText etProject;
    @BindView(R.id.et_operator)
    EditText etOperator;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.ll_alltop)
    RelativeLayout llAlltop;
    @BindView(R.id.et_sys_type)
    EditText etSysType;
    @BindView(R.id.et_pid)
    EditText etPid;
    @BindView(R.id.rv_eqs)
    RecyclerView rvEqs;
    private OperationBean bean;
    private EditText et_eq;
    private AlertDialog dialog;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_detail);
        ButterKnife.bind(this);
        bean = (OperationBean) getIntent().getSerializableExtra("BEAN");
        pos = (int) getIntent().getSerializableExtra("POS");
        initData();
        initView();
    }

    private void initView() {
        imBack.setOnClickListener(this);
        tvTitle.setText("巡检项目详情");
        imMenu.setImageResource(R.drawable.add);
        imMenu.setOnClickListener(this);
    }

    private void initData() {
        etFacilitator.setText(bean.getFacilitator());
        etCompany.setText(bean.getCompany());
        etProject.setText(bean.getProject());
        etOperator.setText(bean.getOperator());
        etTime.setText(MyDateUtils.getDateFromLong(bean.getStart_time(), MyDateUtils.date_Format2));
        etSysType.setText(bean.getSystem_type());
        etPid.setText(bean.getP_id());

        rvEqs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter adapter = new Adapter();
        rvEqs.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.im_menu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //自定义的布局文件
                //自定义的布局文件
                dialog = builder
                        .setView(R.layout.dialog_add_equipment) //自定义的布局文件
                        .create();
                dialog.show();
                et_eq = dialog.getWindow().findViewById(R.id.et_eq);
                Button bt_comfirm = dialog.getWindow().findViewById(R.id.bt_confirm);
                Button bt_cancel = dialog.getWindow().findViewById(R.id.bt_cancel);
                bt_cancel.setOnClickListener(this);
                bt_comfirm.setOnClickListener(this);
                break;
            case R.id.bt_confirm:
                if (et_eq.getText().toString().equals("")){
                    Toast.makeText(OperationDetailActivity.this,"装置名称不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    bean.getEquipment().add(et_eq.getText().toString());
                    MyApplication.op_data.remove(pos);
                    MyApplication.op_data.add(pos,bean);
                    dialog.dismiss();
                    initData();
                }
                break;
            case R.id.bt_cancel:
                dialog.cancel();
                break;
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.OpViewHolder> {


        @Override
        public Adapter.OpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Adapter.OpViewHolder(View.inflate(OperationDetailActivity.this, R.layout.item_operation_detail, null));
        }

        @Override
        public void onBindViewHolder(Adapter.OpViewHolder holder, int position) {
            holder.tv_eq_name.setText(bean.getEquipment().get(position));
        }

        @Override
        public int getItemCount() {
            return bean.getEquipment().size();
        }

        class OpViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_eq_name;

            OpViewHolder(View itemView) {
                super(itemView);

                tv_eq_name = itemView.findViewById(R.id.tv_eq_name);

            }
        }
    }

}
