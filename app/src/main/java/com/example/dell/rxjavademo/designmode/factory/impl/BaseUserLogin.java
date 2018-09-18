package com.example.dell.rxjavademo.designmode.factory.impl;

import android.content.Context;

import com.example.dell.rxjavademo.designmode.factory.interf.IBaseUser;

/**
 * 用户登录基类
 * @param <U>
 *  此处并没有完，还可以实现其他的接口
 */

public abstract  class BaseUserLogin<U extends IBaseUser>  implements IBaseUser.IUerLogin<U>  {

  
    /**
     *   实现不同登录方式的工厂 , 因为用户系统大部分情况下都需要和 UI 交互，
     *   所以封装了一层基类把 Context 上下文统一起来，减少子类的不必要的重复。
     */
    private Context context;

    public BaseUserLogin(Context context) {
        this.context = context;
    }

    @Override
    public void login(U u) {
        
    }

    @Override
    public void loginOut(U u) {

    }
}
