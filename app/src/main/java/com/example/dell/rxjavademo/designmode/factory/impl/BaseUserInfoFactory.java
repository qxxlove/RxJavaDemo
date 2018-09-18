package com.example.dell.rxjavademo.designmode.factory.impl;

import android.content.Context;

import com.example.dell.rxjavademo.designmode.factory.interf.IBaseUser;
import com.example.dell.rxjavademo.designmode.factory.interf.UserInfoFactory;

/**
 * 用户系统工厂基类
 *
 */

public abstract class BaseUserInfoFactory  implements UserInfoFactory {

    private Context mContext ;

    public BaseUserInfoFactory(Context mContext) {
        this.mContext = mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public IBaseUser.IUerLogin getLoginUser() {
        return null;
    }

    @Override
    public IBaseUser.IUerRegister getRegisterUser() {
        return null;
    }

    @Override
    public IBaseUser.IUerFindPass getFindUser() {
        return null;
    }

    @Override
    public IBaseUser.IUerLoginInfo getUserInfoUser() {
        return null;
    }
}
