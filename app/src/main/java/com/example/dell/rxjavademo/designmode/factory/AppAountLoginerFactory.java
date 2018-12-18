package com.example.dell.rxjavademo.designmode.factory;

import android.content.Context;

import com.example.dell.rxjavademo.designmode.factory.impl.BaseUserInfoFactory;
import com.example.dell.rxjavademo.designmode.factory.interf.IBaseUser;
import com.example.dell.rxjavademo.designmode.factory.interf.UserInfoFactory;

/**
 * App用户对应自己账号的功能工厂
 */

public class AppAountLoginerFactory extends BaseUserInfoFactory {

    public AppAountLoginerFactory(Context mContext) {
        super(mContext);
    }

    public  static UserInfoFactory create (Context context){
        return  new AppAountLoginerFactory(context);
    }

    @Override
    public IBaseUser.IUerLogin getLoginUser() {
        return new AppAccountLoginer(getmContext());
    }

    @Override
    public IBaseUser.IUerRegister getRegisterUser() {
        return new AppAccountLoginer(getmContext());
    }

    @Override
    public IBaseUser.IUerLoginInfo getUserInfoUser() {
        return super.getUserInfoUser();
    }

    @Override
    public IBaseUser.IUerFindPass getFindUser() {
        return super.getFindUser();
    }
}
