package com.gwkj.qixiubaodian.obd.module.login;

import android.content.Context;

import com.gwkj.qixiubaodian.obd.mvp.BasePresenter;
import com.gwkj.qixiubaodian.obd.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginSuccess();
        void loginFail(String text);
    }

    interface  Presenter extends BasePresenter<View> {
        void login(Context mcontext,String phone, String password);
        
    }
}
