package com.gwkj.qixiubaodian.obd.module.diagnose;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.BR;
import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.activity.BaseActivity;
import com.gwkj.qixiubaodian.obd.activity.MyApplication;
import com.gwkj.qixiubaodian.obd.adapter.PidListAdapter;
import com.gwkj.qixiubaodian.obd.contants.JSONUtil;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.databinding.ActivityObdpidListBinding;
import com.gwkj.qixiubaodian.obd.item.OBDPid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OBDPidListActivity extends BaseActivity implements View.OnClickListener{

    private ActivityObdpidListBinding binding;

    private PidListAdapter<OBDPid.DataBean> pidAdapter;
    private String title="";
    private String pinpaiid="";
    private String data="";
    private String sendparam="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_obdpid_list);
        initView();
        initSendPID();

    }
    private void initView(){
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        pinpaiid=intent.getStringExtra("pinpaiid");
        data=intent.getStringExtra("data");
        sendparam=intent.getStringExtra("sendparam");
        pidAdapter = new PidListAdapter<>(this, R.layout.obd_pid_list, BR.pid);
        binding.lvPid.setAdapter(pidAdapter);
        binding.tvTitle.setText(title);

        binding.tvSure.setOnClickListener(this);
        binding.cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                pidAdapter.setAllSelect(b);
            }
        });
        binding.btnBack.setOnClickListener(this);

    }
    private void initSendPID(){

        String url="http://www.haohaoxiuche.com/wxqxbd/baodian/api/api_uds.php?type=get_pid";
        NetInterfaceEngine.getEngine().commitPostPID(this,url,pinpaiid,data,sendparam,new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if(result!=null&&!result.isEmpty()){
                    OBDPid emty= JSONUtil.parse(result,OBDPid.class);
                    if(emty!=null&&emty.getData()!=null&&emty.getData().size()>0){
                        pidAdapter.setDataList(emty.getData());
                    }
                    Log.e("data=",data);
                    Log.e("result=",result);

                }
            }

            @Override
            public void onFail(Exception e, String err) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent=new Intent();
            setResult(RESULT_OK, intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
    @Override
    public void onClick(View view) {
        if(view==binding.tvSure){
            List<OBDPid.DataBean> selectlist=new ArrayList<>();
            List<OBDPid.DataBean> mlist=pidAdapter.list;
            for (int i=0;i<mlist.size();i++){
                if(mlist.get(i).ischeck()){
                    selectlist.add(mlist.get(i));
                }
            }
            if(selectlist.size()>0){
                Intent intent=new Intent();
                intent.putExtra("list",(Serializable) selectlist);
                setResult(RESULT_OK, intent);
                finish();
            }else{
                Toast.makeText(this,"请选择数据",Toast.LENGTH_SHORT).show();
            }
        }else if(view==binding.btnBack){
            Intent intent=new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
