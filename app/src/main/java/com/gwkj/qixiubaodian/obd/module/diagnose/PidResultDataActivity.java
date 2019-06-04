package com.gwkj.qixiubaodian.obd.module.diagnose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.gwkj.qixiubaodian.obd.BR;
import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.activity.BaseActivity;
import com.gwkj.qixiubaodian.obd.activity.MyApplication;
import com.gwkj.qixiubaodian.obd.adapter.PidDataListAdapter;
import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.contants.JSONUtil;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.databinding.ActivityPidResultDataBinding;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.item.PIDData;
import com.gwkj.qixiubaodian.obd.service.ReceiveSocketService;
import com.gwkj.qixiubaodian.obd.thread.PidCmdThread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PidResultDataActivity extends BaseActivity implements View.OnClickListener{

    public PidCmdThread pidCmdThread = null;
    public ExecutorService cachedThreadPool = null;
    private ActivityPidResultDataBinding binding;
    private List<OBDPid.DataBean> list;
    private String strData;
    private OBDPid.DataBean sendItem;
    private PidDataListAdapter<List<OBDSer>> pidAdapter;
    private List<OBDSer> mlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_pid_result_data);
        initIntent();
        initView();
        myhandl.sendEmptyMessageDelayed(2,500);

    }
    private void initIntent(){
        ReceiveSocketService.setReceiveHandle(myhandl);
        Intent intent=getIntent();
        list = (List<OBDPid.DataBean>) intent.getSerializableExtra("list");
        String title=intent.getStringExtra("title");
        binding.tvTitle.setText(title);
    }
    private void initView(){

        pidAdapter = new PidDataListAdapter<>(this, R.layout.obd_pid_data_list, BR.data);
        binding.lvPid.setAdapter(pidAdapter);
        pidAdapter.setPidList(list);
        binding.tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pidCmdThread.setBreak(true);
            }
        });
        binding.btnBack.setOnClickListener(this);
        binding.tvSure.setOnClickListener(this);
    }
    private void initViewData(String data){
        OBDSer emty=new OBDSer();
        emty.setTitle(sendItem.getTitle());
        emty.setValue(data);
        emty.setCmd(sendItem.getSid()+sendItem.getPid());
        emty.setUnit(sendItem.getUnit());
        mlist.add(emty);
        synchronized (Common.sendLock1)
        {
            Common.sendLock1.notify();
        }
    }

    private void initSendData(List<OBDSer> list) {
        String url = "http://www.haohaoxiuche.com/wxqxbd/baodian/api/api_uds.php?type=set_pid";
        NetInterfaceEngine.getEngine().commitPostList(this,url, list,new NetHelper() {
            @Override
            public void onSuccess(String result) {
                mlist.clear();
                if (result != null && !result.isEmpty()) {
                    Log.e("服务器==", result);
                    PIDData emty= JSONUtil.parse(result,PIDData.class);
                    if(emty!=null&&emty.getData()!=null&&emty.getData().size()>0){
                        pidAdapter.setBaseList(emty.getData());
                    }
                    synchronized (Common.sendLock1)
                    {
                        Common.sendLock1.notify();
                    }
                }
            }
            @Override
            public void onFail(Exception e, String err) {
                mlist.clear();
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler myhandl = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    sendItem= (OBDPid.DataBean)msg.obj;
                    break;
                case 1://接收数据
                    if (TextUtils.isEmpty(msg.obj.toString())) return;
                    String receiveMsg = msg.obj.toString();
                    Log.e("receiveOBD==", receiveMsg);
                    strData = strData + receiveMsg;
                    break;
                case 2:
                    pidCmdThread= PidCmdThread.getInstnceInitThread(myhandl,list);
                    cachedThreadPool = Executors.newCachedThreadPool();
                    cachedThreadPool.execute(pidCmdThread);
                    break;
                case 3:
                    if (sendItem!=null&&!strData.isEmpty()) {
                        initViewData(strData);
                        strData="";
                    }else{
                        sendEmptyMessageDelayed(3,500);
                    }

                    break;
                case 4:
                    initSendData(mlist);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pidCmdThread.setBreak(true);
        cachedThreadPool.shutdown();
        cachedThreadPool=null;
        pidCmdThread=null;
    }

    @Override
    public void onClick(View view) {
        if(view==binding.btnBack){
            finish();
        }else if(view==binding.tvSure){
            Intent intent=new Intent(this, BltSocketAcivity.class);
            intent.putExtra("list",(Serializable) list);
            startActivity(intent);
            finish();
        }
    }
}
