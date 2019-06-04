package com.gwkj.qixiubaodian.obd.module.diagnose;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.Utils.Utils;
import com.gwkj.qixiubaodian.obd.adapter.BaseOrderAdapter;
import com.gwkj.qixiubaodian.obd.adapter.GvOrderAdapter;
import com.gwkj.qixiubaodian.obd.adapter.GvOrderChildAdapter;
import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.contants.JSONUtil;
import com.gwkj.qixiubaodian.obd.databinding.ActivityBltSocketBinding;
import com.gwkj.qixiubaodian.obd.index.IndexTabActivity;
import com.gwkj.qixiubaodian.obd.item.InputparamBean;
import com.gwkj.qixiubaodian.obd.item.OBDItem;
import com.gwkj.qixiubaodian.obd.item.OBDOrder;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.item.OBDSer;
import com.gwkj.qixiubaodian.obd.item.PIDData;
import com.gwkj.qixiubaodian.obd.manager.BltBleManager;
import com.gwkj.qixiubaodian.obd.module.diagnose.contract.BltSocketContract;
import com.gwkj.qixiubaodian.obd.module.diagnose.contract.BltSocketPresenter;
import com.gwkj.qixiubaodian.obd.mvp.MVPBaseActivity;
import com.gwkj.qixiubaodian.obd.service.SendSocketService;
import com.gwkj.qixiubaodian.obd.thread.IniCmdBleThread;
import com.gwkj.qixiubaodian.obd.thread.TableCmdBleThread;
import com.gwkj.qixiubaodian.obd.view.ListInputDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.gwkj.qixiubaodian.obd.contants.Common.NEXT_BACK;

/**
 * Created by Luhao on 2016/9/28.
 */
public class BltBleSocketAcivity extends MVPBaseActivity<BltSocketContract.View, BltSocketPresenter> implements BltSocketContract.View, View.OnClickListener {

    private ActivityBltSocketBinding binding;
    private OBDItem item;
    private String strData = "";
    private String cmd = "";
    private String sendParam = "";
    private String type = "";
    private String isService;
    private boolean isFirst = false;

    private OBDOrder order;
    private RadioGroup group;

    private ListView lv_obd;
    private ListView lv_obd2;
    private LinearLayout ll_base;
    private GvOrderAdapter adapter;
    private GvOrderChildAdapter adapter2;
    private ListView lv_time;
    private BaseOrderAdapter timeAdapter;
    private OBDPid.DataBean sendItem;
    private RadioButton firstBtn;
    public IniCmdBleThread iniCmdThread = null;
    public ExecutorService initcachedThreadPool = null;
    public ExecutorService tablecachedThreadPool = null;
    public TableCmdBleThread tableCmdThread = null;
    private List<OBDSer> mlist = new ArrayList<>();
    private int listpos = 0;
    private List<OBDPid.DataBean> tableList;
    public static BltBleSocketAcivity bltSocketAcivity = null;

    private boolean islastIndex = false;
    private String name = "";
    private String title = "";
    private Dialog listDialog;
    private String disCmd="";
    private int reConnectCount=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bltSocketAcivity = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blt_socket);
        initIntent();
        baseListView();
        mPresenter.initBaseData();

    }

    private void initIntent() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        BltBleManager.getInstance().setHandler(myhandl);
        initcachedThreadPool = Executors.newCachedThreadPool();
        tablecachedThreadPool = Executors.newCachedThreadPool();
        myhandl.sendEmptyMessageDelayed(2, 1000);
    }

    private void baseListView() {
        ll_base = (LinearLayout) findViewById(R.id.ll_base);
        lv_time = (ListView) findViewById(R.id.lv_time);
        lv_obd = (ListView) findViewById(R.id.lv_obd);
        lv_obd2 = (ListView) findViewById(R.id.lv_obd2);
        adapter = new GvOrderAdapter(getContext());
        adapter2 = new GvOrderChildAdapter(getContext());
        timeAdapter = new BaseOrderAdapter(getContext());
        lv_time.setAdapter(timeAdapter);
        binding.btnBack.setOnClickListener(this);
        binding.imgHelp.setOnClickListener(this);
        binding.imgChange.setOnClickListener(this);
        binding.tvSend.setOnClickListener(this);
        binding.tvNote.setOnClickListener(this);
        ll_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_base.setVisibility(View.GONE);
            }
        });
        lv_obd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (islastIndex) {
                    pid2ItemClick(i);
                } else {
                    pidItemClick(i);
                }

            }
        });
        lv_obd2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (islastIndex) {
                    pidItemClick(i);
                } else {
                    pid2ItemClick(i);
                }

            }
        });
    }

    private void initView() {

        group = (RadioGroup) findViewById(R.id.group);
        LayoutInflater inflater = LayoutInflater.from(this);

        final int lenght = order.getData().size();
        for (int i = 0; i < lenght; i++) {
            final View view = (View) inflater.inflate(
                    R.layout.radiobutton_item, null);
            LinearLayout ll_rbt = (LinearLayout) view.findViewById(R.id.ll_rbt);
            final RadioButton tempButton = (RadioButton) view.findViewById(R.id.month_popwindow_radiobutton);
            tempButton.setText(order.getData().get(i).getTitle());
            tempButton.setId(i);
            ll_rbt.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.MATCH_PARENT, 1f));
            final int finalI = i;

            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_base.setVisibility(View.VISIBLE);

                    if (finalI != 0 && finalI == lenght - 1) {
                        islastIndex = true;
                        lv_obd.setAdapter(adapter2);
                        lv_obd2.setAdapter(adapter);
                        adapter2.setData(null);
                        adapter.setData(order.getData().get(finalI).getSubmenu(), true);
                        adapter.setSelectItem(-1);
                    } else if (lenght == 4 && finalI == lenght - 2) {
                        islastIndex = true;
                        lv_obd.setAdapter(adapter2);
                        lv_obd2.setAdapter(adapter);
                        adapter2.setData(null);
                        adapter.setData(order.getData().get(finalI).getSubmenu(), true);
                        adapter.setSelectItem(-1);
                    } else {
                        islastIndex = false;
                        lv_obd.setAdapter(adapter);
                        lv_obd2.setAdapter(adapter2);
                        adapter.setData(order.getData().get(finalI).getSubmenu(), false);
                        adapter2.setData(null);
                        adapter.setSelectItem(-1);
                    }

                    tempButton.setChecked(true);
                    group.check(finalI);
                    int listwith = group.getWidth() / lenght;
                    if (lenght == 3) {
                        switch (finalI) {
                            case 0:
                                ll_base.setPadding(0, 0, listwith, 0);
                                break;
                            case 1:
                                ll_base.setPadding(listwith, 0, 0, 0);
                                break;
                            case 2:
                                ll_base.setPadding(listwith, 0, 0, 0);
                                break;
                        }
                    } else if (lenght == 4) {
                        listwith = group.getWidth() / 4;
                        switch (finalI) {
                            case 0:
                                ll_base.setPadding(0, 0, listwith, 0);
                                break;
                            case 1:
                                ll_base.setPadding(listwith, 0, 0, 0);
                                break;
                            case 2:
                                ll_base.setPadding(0, 0, listwith, 0);
                                break;
                            case 3:
                                ll_base.setPadding(listwith, 0, 0, 0);
                                break;
                        }
                    }
                }
            });

            group.addView(view);
        }

    }

    private void pidItemClick(int position) {
        OBDOrder.DataBean.SubmenuBeanX item = (OBDOrder.DataBean.SubmenuBeanX) adapter.getItem(position);
        if (item.getSubmenu() != null && item.getSubmenu().size() > 0) {
            adapter2.setData(item.getSubmenu());
            adapter.setSelectItem(position);
            title = item.getTitle();
            return;
        }
        if (item != null) {
            String type = item.getType();
            stopTableThread();
            if (type.equals("PID")) {
                Intent intent = new Intent(getContext(), OBDPidListActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("pinpaiid", item.getPinpaiid());
                intent.putExtra("data", item.getData());
                intent.putExtra("sendparam", item.getSendparam());
                this.startActivityForResult(intent, NEXT_BACK);
                ll_base.setVisibility(View.GONE);
            } else {
                if (item.getInputparam() != null && item.getInputparam().size() > 0) {
                    this.sendParam = item.getSendparam();
                    this.type = item.getType();
                    this.title = item.getTitle();
                    showListDialog(item.getTitle(), item.getData(), item.getInputparam());
                    ll_base.setVisibility(View.GONE);
                } else {
                    setODBCmd(item.getType(), item.getData(), item.getSendparam(), item.getTitle());
                    ll_base.setVisibility(View.GONE);
                }
            }
        }
    }

    private void pid2ItemClick(int i) {
        OBDOrder.DataBean.SubmenuBeanX.SubmenuBean item = (OBDOrder.DataBean.SubmenuBeanX.SubmenuBean) adapter2.getItem(i);

        if (item != null) {
            String type = item.getType();
            stopTableThread();
            if (type.equals("PID")) {
                Intent intent = new Intent(getContext(), OBDPidListActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("pinpaiid", item.getPinpaiid());
                intent.putExtra("data", item.getData());
                intent.putExtra("sendparam", item.getSendparam());
                this.startActivityForResult(intent, NEXT_BACK);
                ll_base.setVisibility(View.GONE);
            } else {
                if (item.getInputparam() != null && item.getInputparam().size() > 0) {
                    this.sendParam = item.getSendparam();
                    this.type = item.getType();
                    String mtitle = item.getTitle();
                    if (!title.isEmpty()) {
                        title = title + "->" + mtitle;
                    }
                    showListDialog(item.getTitle(), item.getData(), item.getInputparam());
                    ll_base.setVisibility(View.GONE);
                } else {
                    String mtitle = item.getTitle();
                    if (!title.isEmpty()) {
                        mtitle = title + "->" + mtitle;
                    }
                    setODBCmd(item.getType(), item.getData(), item.getSendparam(), mtitle);
                    ll_base.setVisibility(View.GONE);
                }
            }

        }
    }

    private void initViewData(String data) {
        OBDSer emty = new OBDSer();
        emty.setTitle(sendItem.getTitle());
        emty.setValue(data);
        emty.setCmd(sendItem.getSid() + sendItem.getPid());
        emty.setUnit(sendItem.getUnit());
        mlist.add(emty);
        synchronized (Common.sendLock2) {
            Common.sendLock2.notify();
        }
    }

    public void setTableList(List<OBDPid.DataBean> tableList) {
        if (tableList == null) {
            return;
        }
        OBDSer tableitem = new OBDSer();
        long time = System.currentTimeMillis();
        try {
            String data = Utils.longToString(time, "yyyy-MM-dd HH:mm:ss");
            tableitem.setTime(data);
            tableitem.setTable(tableList);
            tableitem.setType("1");
            timeAdapter.addItem(tableitem);
            lv_time.smoothScrollToPosition(timeAdapter.getCount());
            listpos = timeAdapter.getCount() - 1;
            this.tableList = tableList;
            stopTableThread();
            Message msg = new Message();
            msg.what = 4;
            msg.obj = tableList;
            myhandl.sendMessageDelayed(msg, 500);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void showListDialog(String title, String cmdd, List<InputparamBean> list) {
        listDialog = new ListInputDialog(this, title, cmdd, list, new ListInputDialog.ApproveDialogHelper() {
            public void go(String inputstr) {
//                listDialog.dismiss();
                Log.e("发送到OBD：", inputstr);
                SendSocketService.sendBleMessage(inputstr,myhandl);
                cmd = inputstr;
                myhandl.sendEmptyMessageDelayed(3, 500);
            }

            @Override
            public void cancel() {
//                listDialog.dismiss();
            }

        });
        listDialog.show();
    }

    @Override
    public void BaseDataSuccess(String result) {
        if (result != null && !result.isEmpty()) {
            result = "{data:" + result + "}";
            Log.e("result", result);
            order = JSONUtil.parse(result, OBDOrder.class);
            if (order != null && order.getData() != null) {
                initView();
            }

        }
    }

    @Override
    public void sendDataSuccess(String result) {
        if (result != null && !result.isEmpty()) {
            OBDSer emty = JSONUtil.parse(result, OBDSer.class);
            if (emty != null) {
                if (emty.getVarname() != null && !emty.getVarname().isEmpty()&&emty.getData()!=null&&!emty.getData().isEmpty()) {
                    mPresenter.saveVarName(this,emty.getVarname(), emty.getData());
                }
                setAdapterItem(emty);
            }
            Log.e("revice=", result);
            synchronized (Common.sendLock1) {
                Common.sendLock1.notify();
            }
            if (tableList != null && tableList.size() > 0) {
                myhandl.sendEmptyMessageDelayed(4, 100);
            }
        }
    }

    @Override
    public void sendTableSuccess(String result) {
        mlist.clear();
        if (result != null && !result.isEmpty()) {
            Log.e("服务器==", result);
            PIDData emty = JSONUtil.parse(result, PIDData.class);
            if (emty != null && emty.getData() != null && emty.getData().size() > 0) {
                timeAdapter.setBaseList(emty.getData(), listpos);
                lv_time.setSelection(lv_time.getBottom());
            }
            synchronized (Common.sendLock2) {
                Common.sendLock2.notify();
            }
        }
    }

    private void setAdapterItem(OBDSer emty) {
        closeDialog();
        long time = System.currentTimeMillis();
        String data = null;
        try {
            data = Utils.longToString(time, "yyyy-MM-dd HH:mm:ss");
            emty.setTime(data);
            emty.setTitle(title);
            timeAdapter.addItem(emty);
            lv_time.setSelection(lv_time.getBottom());
            title = "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void setAdapterHelp(){
        OBDSer emty=new OBDSer();
        long time = System.currentTimeMillis();
        String data = null;
        try {
            data = Utils.longToString(time, "yyyy-MM-dd HH:mm:ss");
            emty.setTime(data);
            emty.setVarname("王的男人");
            emty.setType("2");
            timeAdapter.addItem(emty);
            lv_time.setSelection(lv_time.getBottom());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void setAdapterSend(String content){
        OBDSer emty=new OBDSer();
        emty.setTitle(content);
        emty.setVarname("天尘");
        emty.setType("3");
        timeAdapter.addItem(emty);
        lv_time.setSelection(lv_time.getBottom());
    }
    private void setAdapterAccept(){
        OBDSer emty=new OBDSer();
        long time = System.currentTimeMillis();
        String data = null;
        try {
            data = Utils.longToString(time, "yyyy-MM-dd HH:mm:ss");
            emty.setTime(data);
            emty.setVarname("王的男人");
            emty.setType("5");
            timeAdapter.addItem(emty);
            lv_time.setSelection(lv_time.getBottom());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            List<OBDPid.DataBean> mlist = (List<OBDPid.DataBean>) intent.getSerializableExtra("list");
            setTableList(mlist);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case NEXT_BACK:
                        if (tableList != null && tableList.size() > 0) {
                            myhandl.sendEmptyMessageDelayed(4, 100);
                        }
                        break;

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTableThread();
        stopInitThread();

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnBack) {
            Intent intent = new Intent(this, IndexTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }else if(v==binding.imgHelp){
            binding.group.setVisibility(View.GONE);
            binding.imgChange.setVisibility(View.VISIBLE);
            binding.etContent.setVisibility(View.VISIBLE);
            binding.tvSend.setVisibility(View.VISIBLE);
            setAdapterHelp();

        }else if(v==binding.imgChange){
            if(binding.group.getVisibility()==View.GONE) {
                binding.group.setVisibility(View.VISIBLE);
                binding.etContent.setVisibility(View.GONE);
                binding.tvSend.setVisibility(View.GONE);
                binding.imgChange.setImageResource(R.mipmap.img_botton_change2);
            }else{
                binding.group.setVisibility(View.GONE);
                binding.etContent.setVisibility(View.VISIBLE);
                binding.tvSend.setVisibility(View.VISIBLE);
                binding.imgChange.setImageResource(R.mipmap.img_botton_change);
            }
        }else if(v==binding.tvSend){
            if(!binding.etContent.getText().toString().isEmpty()) {
                setAdapterSend(binding.etContent.getText().toString());
            }
        }else if(v==binding.tvNote){
            setAdapterAccept();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this, IndexTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler myhandl = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    OBDItem.InitCmdBean cmddata = (OBDItem.InitCmdBean) msg.obj;
                    cmd = cmddata.getCmd();
                    sendParam = cmddata.getSendparam();
                    isService = cmddata.getIs_sendtoserver();
                    break;
                case 1://接收数据
                    removeMessages(3);
                    if (TextUtils.isEmpty(msg.obj.toString())) return;
                    String receiveMsg = msg.obj.toString();
                    Log.e("receiveOBD==", receiveMsg);
                    strData = strData + receiveMsg;
                    if(receiveMsg.contains("SEARCHING")) {
                        sendEmptyMessageDelayed(3, 800);
                    }else{
                        sendEmptyMessageDelayed(3, 500);
                    }

                    break;
                case 2:
                    showDialog("设备初始化");
                    iniCmdThread = IniCmdBleThread.getInstnceInitThread(myhandl);
                    initcachedThreadPool.execute(iniCmdThread);
                    if(BltBleManager.mBluetoothGatt==null){
                        closeDialog();
                        finish();
                        Toast.makeText(BltBleSocketAcivity.this,"连接异常，请重新连接设备",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    if (!strData.isEmpty()) {
                        if (isService != null && isService.equals("1")) {
                            mPresenter.sendData(BltBleSocketAcivity.this,strData, cmd, type, sendParam);
                            Log.e("发送到服务date：", strData + " cmd:" + cmd);
                        } else {
                            synchronized (Common.sendLock1) {
                                Common.sendLock1.notify();
                            }
                        }
                        strData = "";
                        cmd = "";
                        sendParam = "";
                        type = "";
                    } else {
                        sendEmptyMessageDelayed(3, 1000);
                    }

                    break;
                case 4:
                    tableCmdThread = TableCmdBleThread.getInstnceInitThread(myhandl, tableList);
                    tablecachedThreadPool.execute(tableCmdThread);
                    break;
                case 5://OBD数据流命令
                    sendItem = (OBDPid.DataBean) msg.obj;
                    break;
                case 6://接收OBD数据流
                    if (sendItem != null && !strData.isEmpty()) {
                        initViewData(strData);
                        strData = "";
                    } else {
                        sendEmptyMessageDelayed(3, 500);
                    }
                    break;
                case 7://解释数据流值
                    mPresenter.sendTableData(BltBleSocketAcivity.this,mlist);
                    break;
                case 8:
                    if(disCmd!=null&&disCmd.isEmpty()) {
                        disCmd = (String) msg.obj;
                    }
                    showDialog("重新连接设备");
                    removeMessages(8);
                    BltBleManager.getInstance().connectBle(Common.blueDevice,myhandl);
                    sendEmptyMessageDelayed(11,20000);
                    break;
                case 9:
                    closeDialog();
                    removeMessages(11);
                    Toast.makeText(BltBleSocketAcivity.this, "设备连接成功", Toast.LENGTH_LONG).show();
                    if(disCmd!=null&&!disCmd.isEmpty()) {
                        SendSocketService.sendBleMessage(disCmd, myhandl);
                        disCmd="";
                    }
                    reConnectCount=0;
                    break;
                case 10:
                    if(reConnectCount<=3){
                        sendEmptyMessage(8);
                        reConnectCount++;
                    }else{
                        reConnectCount=0;
                        closeDialog();
                        Toast.makeText(BltBleSocketAcivity.this, "设备连接异常，请断开重新连接！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 11:
                    removeMessages(8);
                    closeDialog();
                    Toast.makeText(BltBleSocketAcivity.this, "设备连接异常，请断开重新连接！", Toast.LENGTH_LONG).show();
                    break;

            }
            super.handleMessage(msg);
        }
    };

    private void setODBCmd(String type, String cmd, String sendParam, String title) {
        Log.e("发送到OBD：", cmd);
        SendSocketService.sendBleMessage(cmd,myhandl);
        this.cmd = cmd;
        this.sendParam = sendParam;
        this.type = type;
        this.title = title;
        myhandl.sendEmptyMessageDelayed(3, 500);
    }

    private void stopInitThread() {
        if (iniCmdThread != null) {
            iniCmdThread.setBreak(true);
            if (initcachedThreadPool.isShutdown()) {
                initcachedThreadPool.shutdown();
            }
        }
    }

    public void stopTableThread() {
        if (tableCmdThread != null) {
            tableCmdThread.setBreak(true);
            if (tablecachedThreadPool.isShutdown()) {
                tablecachedThreadPool.shutdown();
            }
        }
    }
}
