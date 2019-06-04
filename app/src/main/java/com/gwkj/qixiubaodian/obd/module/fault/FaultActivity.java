package com.gwkj.qixiubaodian.obd.module.fault;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.contants.JSONUtil;
import com.gwkj.qixiubaodian.obd.databinding.ActivityFaultDetailBinding;
import com.gwkj.qixiubaodian.obd.module.fault.item.FaultDetailItem;
import com.gwkj.qixiubaodian.obd.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FaultActivity extends MVPBaseActivity<FaultContract.View, FaultPresenter> implements FaultContract.View, View.OnClickListener {

    private ActivityFaultDetailBinding binding;
    private String code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_fault_detail);
        initIntent();
        initView();
        initData();

    }
    private void initIntent(){
        Intent intent=getIntent();
        code=intent.getStringExtra("code");

    }
    private void initView(){
        binding.btnBack.setOnClickListener(this);
    }

    private void initData(){
        if(!code.isEmpty()) {
            mPresenter.getFaultData(code);
        }
    }
    @Override
    public void onClick(View view) {
        if(view==binding.btnBack){
            finish();
        }
    }

    @Override
    public void faultData(String result) {
        if(result!=null&&!result.isEmpty()){
            FaultDetailItem emty= JSONUtil.parse(result,FaultDetailItem.class);
            if(emty!=null){
                binding.setFault(emty);
            }

        }

    }
}
