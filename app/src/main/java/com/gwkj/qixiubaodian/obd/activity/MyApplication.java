package com.gwkj.qixiubaodian.obd.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.gwkj.qixiubaodian.obd.cockroach.Cockroach;
import com.gwkj.qixiubaodian.obd.cockroach.ExceptionHandler;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    public static MyApplication myApplication;

    private List<Activity> list = new ArrayList<Activity>();

    private static MyApplication ea;

    public static MyApplication getContext() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        myApplication = this;
        initX5WebView();
        installCock();

    }

    private void initX5WebView() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), null);

    }

    //在Application里面重写 attachBaseContext 方法 解决64k问题
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static MyApplication getInstance() {
        if (null == ea) {
            ea = new MyApplication();
        }
        return ea;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void installCock() {
        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
//        final Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
//        DebugSafeModeUI.init(this);
        Cockroach.install(this,new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(final Thread thread, final Throwable throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", throwable);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.this, thread + "<--->"+throwable, Toast.LENGTH_LONG).show(); //错误提示打包时注释掉
                    }
                });
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心

            }

            @Override
            protected void onEnterSafeMode() {
            }

            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                Thread thread = Looper.getMainLooper().getThread();
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", e);
                //黑屏时建议直接杀死app
//                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
            }

        });

    }


    private String title;
    private String content;
    private String reward;
    private String data_type;
    private String touid;

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    private String did;
    private String loginid;
    private String openid;
    private String uid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }


    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private static MyApplication instances;

    public static MyApplication getInstances() {
        return instances;
    }

}
