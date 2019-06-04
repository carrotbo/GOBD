package com.gwkj.qixiubaodian.obd.index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.Utils.Utils;
import com.gwkj.qixiubaodian.obd.activity.MyApplication;
import com.gwkj.qixiubaodian.obd.appliaction.BltAppliaction;
import com.gwkj.qixiubaodian.obd.contants.JSONUtil;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.item.OBDItem;
import com.gwkj.qixiubaodian.obd.manager.BltManager;
import com.gwkj.qixiubaodian.obd.module.diagnose.BltBleSocketAcivity;
import com.gwkj.qixiubaodian.obd.module.diagnose.BltSocketAcivity;
import com.gwkj.qixiubaodian.obd.service.ReceiveSocketService;
import com.gwkj.qixiubaodian.obd.thread.InitThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主窗体
 */
public class IndexTabActivity extends FragmentActivity implements View.OnClickListener {

    private View indexView, repairingView, askdataView, studyView;

    private int nowFragment = -1;
    public static final int INDEX = 0;
    public static final int REPAIRING = 1;
    public static final int ASKDATA = 2;
    public static final int STUDY = 3;
    public static final int MY_CONTENT = 4;

    private int frameLayoutId = R.id.containerBody;
    private DisplayFragment displayFragment;

    private IndexFragment indexPage;//诊断
//    private IndexBleFragment indexPage;//诊断
    private IndexReportFragment report;//报告
    private IndexDiscoverFragment discover;//发现
    private IndexMyFragment myFragment;//我的

    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    private InitThread initThread;
    public static ExecutorService initThreadPool = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//设置底部不上移
        setContentView(R.layout.activity_index_tab);
        BltManager.getInstance().initBltManager(this);
        initIntent();
        getPermissions();
        Utils.verifyStoragePermissions(this);
        initialization();
        showFrame(INDEX);
    }
    private void initIntent(){
        initThreadPool = Executors.newCachedThreadPool();
        initThread = InitThread.getInstnceInitThread(this);
        initThreadPool.execute(initThread);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_EXTERNAL_STORAGE);
        }
    }
    /**
     * 权限申请
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_PHONE_STATE"};

    // 申请所需的权限
    public static void verifyStoragePermissions(Context context) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(context,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(BltAppliaction.getBaseOBD()==null) {
            initBaseData();
        }
    }

    /**
     * 初始化控件
     */
    private void initialization() {

        displayFragment = new DisplayFragment();

        indexView = findViewById(R.id.btnModule1);
        indexView.setOnClickListener(this);

        repairingView = findViewById(R.id.btnModule2);
        repairingView.setOnClickListener(this);

        askdataView = findViewById(R.id.btnModule3);
        askdataView.setOnClickListener(this);

        studyView = findViewById(R.id.btn_main_study);
        studyView.setOnClickListener(this);

        indexPage = new IndexFragment();
//        indexPage = new IndexBleFragment();
        report = new IndexReportFragment();
        discover = new IndexDiscoverFragment();
        myFragment = new IndexMyFragment();

    }

    /**
     * 导航栏
     */
    public void showFrame(int frame) {
        if (nowFragment == frame)
            return;
        nowFragment = frame;
        indexView.setSelected(false);
        repairingView.setSelected(false);
        askdataView.setSelected(false);
        studyView.setSelected(false);

        switch (frame) {
            case INDEX://首页
                displayFragment.showFragment(indexPage, this, frameLayoutId);
                indexView.setSelected(true);

                break;

            case REPAIRING://汽修人
                displayFragment.showFragment(report, this, frameLayoutId);

                askdataView.setSelected(true);
                break;

            case ASKDATA://找资料
                displayFragment.showFragment(discover, this, frameLayoutId);
                repairingView.setSelected(true);

                break;

            case STUDY://学知识
                displayFragment.showFragment(myFragment, this, frameLayoutId);
                studyView.setSelected(true);

                break;

        }

    }
    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            //判断是否具有权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(this,"自Android 6.0开始需要打开位置权限才可以搜索到Ble设备",Toast.LENGTH_LONG).show();
                }
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户允许改权限，0表示允许，-1表示拒绝 PERMISSION_GRANTED = 0， PERMISSION_DENIED = -1
                //permission was granted, yay! Do the contacts-related task you need to do.
                //这里进行授权被允许的处理
            } else {
                //permission denied, boo! Disable the functionality that depends on this permission.
                //这里进行权限被拒绝的处理
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {

                }
                break;
            default:
                break;
        }
    }

    /**
     * 跳转
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnModule1:
                showFrame(INDEX);
                break;

            case R.id.btnModule2:
                showFrame(ASKDATA);
                break;

            case R.id.btnModule3:
                showFrame(REPAIRING);
                break;

            case R.id.btn_main_study:
                showFrame(STUDY);
                break;


        }
    }
    private void initBaseData(){
        String url="http://www.haohaoxiuche.com/wxqxbd/baodian/api/api_uds.php?type=get_init";
        NetInterfaceEngine.getEngine().OkHttpGet(url, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                if(result!=null&&!result.isEmpty()){
                    OBDItem item= JSONUtil.parse(result,OBDItem.class);
                    Log.e("resul",result);
                    if(item!=null) {
                        BltAppliaction.setBaseOBD(item);

                    }
                }
            }
            @Override
            public void onFail(Exception e, String err) {
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int toFragment = intent.getIntExtra("ToPage", INDEX);
            showFrame(toFragment);
        }
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    boolean isExit = false;
    @SuppressLint("HandlerLeak")
    Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
                case 1:
                    IndexTabActivity.this.finish();
                    break;
            }

        }
    };

    private long time = 0;

    //退出方法
    private void exit() {

        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(this,"再点击一次退出应用程序",Toast.LENGTH_SHORT).show();
        } else {
            BltManager.getInstance().disConnectBlt();
            ReceiveSocketService.setIsread();
            if(BltSocketAcivity.bltSocketAcivity!=null){
                BltSocketAcivity.bltSocketAcivity.finish();
            }
            if(BltBleSocketAcivity.bltSocketAcivity!=null){
                BltBleSocketAcivity.bltSocketAcivity.finish();
            }
//            Intent intent = new Intent("drc.xxx.yyy.baseActivity");
//            intent.putExtra("closeAll", 1);
//            this.sendBroadcast(intent);//发送广播
           this.finish();
        }
    }
}
