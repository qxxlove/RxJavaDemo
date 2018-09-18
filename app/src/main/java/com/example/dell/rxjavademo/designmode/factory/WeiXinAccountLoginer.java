package com.example.dell.rxjavademo.designmode.factory;

import android.content.Context;

import com.example.dell.rxjavademo.designmode.factory.bean.User;
import com.example.dell.rxjavademo.designmode.factory.impl.BaseUserLogin;

/**
 * 第三方账号登录
 * 如 微信
 */

public class WeiXinAccountLoginer extends BaseUserLogin<User> {

    public WeiXinAccountLoginer(Context context) {
        super(context);
    }

    @Override
    public void loginOut(User user) {
        super.loginOut(user);
    }

    @Override
    public void login(User user) {
        super.login(user);
    }
}
