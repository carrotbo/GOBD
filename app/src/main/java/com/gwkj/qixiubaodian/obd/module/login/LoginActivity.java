package com.gwkj.qixiubaodian.obd.module.login;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.gwkj.qixiubaodian.obd.R;
import com.gwkj.qixiubaodian.obd.databinding.LoginActivityBinding;
import com.gwkj.qixiubaodian.obd.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View, View.OnClickListener {

    private LoginActivityBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        initView();

    }

    private void initView() {
        binding.ilTitle.tvTitle.setText("欢迎使用GOBD");

        binding.btLogin.setOnClickListener(this);
        binding.ilTitle.btnBack.setOnClickListener(this);
    }

    @Override
    public void loginSuccess() {
        toast("登录成功");
        finish();
        closeDialog();
    }

    @Override
    public void loginFail(String text) {
        toast(text);
        closeDialog();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btLogin) {
            String phone = binding.editAccount.getText().toString();
            String name = binding.editPw.getText().toString();
            if (phone.isEmpty()) {
                toast("请输入手机号");
                return;
            }
            if (name.isEmpty()) {
                toast("请输入密码");
                return;
            }
            showDialog("正在登录");
            mPresenter.login(this,phone, name);
        } else if (view == binding.ilTitle.btnBack) {
            finish();
        }
    }
}
