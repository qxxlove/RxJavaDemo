package com.example.dell.rxjavademo.designmode.factory;

import android.content.Context;
import android.util.Log;

import com.example.dell.rxjavademo.designmode.factory.bean.User;
import com.example.dell.rxjavademo.designmode.factory.impl.BaseUserLogin;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/18.
 * 邮箱：123123@163.com
 */

public class AppAccountLoginer  extends BaseUserLogin<User> {


    public AppAccountLoginer(Context context) {
        super(context);
    }


    /**
     * 登录APP
     * @param user
     */
    @Override
    public void login(User user) {
        super.login(user);
        Log.e("AppAccountLoginer","用户名："+user.getName()+",密码："+user.getPass()+":登录成功");
    }

    /**
     * 退出APP
     * @param user
     */
    @Override
    public void loginOut(User user) {
        super.loginOut(user);
    }
}
