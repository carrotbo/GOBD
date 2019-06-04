package com.gwkj.qixiubaodian.obd.module.login;

import android.content.Context;
import android.util.Log;

import com.gwkj.qixiubaodian.obd.Utils.BaseCacheUtil;
import com.gwkj.qixiubaodian.obd.Utils.Utils;
import com.gwkj.qixiubaodian.obd.activity.MyApplication;
import com.gwkj.qixiubaodian.obd.contants.Constant;
import com.gwkj.qixiubaodian.obd.contants.JSONUtil;
import com.gwkj.qixiubaodian.obd.contants.NetHelper;
import com.gwkj.qixiubaodian.obd.contants.NetInterfaceEngine;
import com.gwkj.qixiubaodian.obd.emty.BaseEmty;
import com.gwkj.qixiubaodian.obd.module.login.emty.LoginItem;
import com.gwkj.qixiubaodian.obd.module.login.emty.User;
import com.gwkj.qixiubaodian.obd.mvp.BasePresenterImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(final Context context, String phone, String password) {
        String deviceid = BaseCacheUtil.getString(context, "deviceid");
        if (deviceid.isEmpty()) {
            Utils.getDeviceId(context);
            deviceid = BaseCacheUtil.getString(context, "deviceid");
        }
        if (deviceid.length() > 30) {
            deviceid = deviceid.substring(0, 30);
        }

        String url = Constant.LOGIN;
        String json = postrequest(phone, password, deviceid);
        NetInterfaceEngine.getEngine().commitOKHttpJson(url, json, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                LoginItem response = JSONUtil.parse(result, LoginItem.class);
                if (response != null && response.getStatus().equals("ok") && response.getData() != null) {
                    LoginItem.DataBean item = response.getData();
                    setUserData(context,item);
                    if(mView!=null) {
                        mView.loginSuccess();
                    }
                } else {
                    BaseEmty emty = JSONUtil.parse(result, BaseEmty.class);
                    if (emty != null && emty.getMessage() != null&&mView!=null) {
                        mView.loginFail(emty.getMessage());
                    } else {
                        if(mView!=null) {
                            mView.loginFail("登录异常");
                        }
                    }
                }
            }

            @Override
            public void onFail(Exception e, String err) {
                mView.loginFail("登录异常");
            }


        });

    }

    private String postrequest(String mobile, String password, String deviceid) {
        String data = "";
        try {
            JSONObject ClientKey = new JSONObject();
            ClientKey.put("mobile", mobile);
            ClientKey.put("password", password);
            ClientKey.put("deviceid", deviceid);
            data = NetInterfaceEngine.getEngine().jsonData(ClientKey);
            Log.e("登陆参数----", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 储存登陆成功后的数据
     */
    private void setUserData(Context context,LoginItem.DataBean item) {

        MyApplication.getInstances().setLoginid(item.getLoginid());
        MyApplication.getInstances().setOpenid(item.getOpenid());
        MyApplication.getInstances().setUid(item.getUid());
        BaseCacheUtil.setString(context,"openid",item.getOpenid());

        getUserData(context,item.getOpenid());
    }
    private void getUserData(final Context context, String openid) {
        String json = postrequestOpenid(openid);
        String url = Constant.GETUSER;
        NetInterfaceEngine.getEngine().commitOKHttpJson(url, json, new NetHelper() {
            @Override
            public void onSuccess(String result) {
                User user = JSONUtil.parse(result, User.class);
                if (user != null && user.getStatus().equals("ok") && user.getData() != null) {

                    BaseCacheUtil.setString(context,"login_user",result);
//                    MyApplication.getInstances().setUser(user);

                }

            }

            @Override
            public void onFail(Exception e, String err) {
            }
        });
    }
    private String postrequestOpenid(String openid) {
        String data = "";
        try {
            JSONObject ClientKey = new JSONObject();
            ClientKey.put("openid", openid);
            data = NetInterfaceEngine.getEngine().jsonData(ClientKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
