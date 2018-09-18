package com.example.dell.rxjavademo.designmode.factory;

import android.content.Context;

import com.example.dell.rxjavademo.designmode.factory.impl.BaseUserInfoFactory;
import com.example.dell.rxjavademo.designmode.factory.interf.IBaseUser;
import com.example.dell.rxjavademo.designmode.factory.interf.UserInfoFactory;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/18.
 * 邮箱：123123@163.com
 */

public class WeiXinAccountLoginerFactory  extends BaseUserInfoFactory {

    public WeiXinAccountLoginerFactory(Context mContext) {
        super(mContext);
    }

    public  static UserInfoFactory create (Context context){
        return  new WeiXinAccountLoginerFactory(context);
    }



    @Override
    public IBaseUser.IUerLogin getLoginUser() {
        return new WeiXinAccountLoginer(getmContext());
    }

    @Override
    public IBaseUser.IUerFindPass getFindUser() {
        return super.getFindUser();
    }

    @Override
    public IBaseUser.IUerRegister getRegisterUser() {
        return super.getRegisterUser();
    }


    @Override
    public IBaseUser.IUerLoginInfo getUserInfoUser() {
        return super.getUserInfoUser();
    }
}

