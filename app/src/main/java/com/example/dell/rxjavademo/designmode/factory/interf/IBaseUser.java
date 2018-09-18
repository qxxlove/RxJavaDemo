package com.example.dell.rxjavademo.designmode.factory.interf;

/**
 * 当然下面的这些接口，
 * 也可以统一用一个接口文件来写，
 * 这些模块就作为子接口嵌套在里面，这是为了方便管理。
 */
public interface IBaseUser {


    /**
     * 登录功能
     */
    public  interface  IUerLogin<U extends  IBaseUser> {
        /**
         * 登录
         */
         void   login (U u);

        /**
         * 退出
         */
         void   loginOut(U u);
    }

    /**
     * 注册功能
     */
    public  interface  IUerRegister<U extends  IBaseUser> {
        /**
         * 注册
         */
        void   register (U u);
    }

    /**
     * 找回密码功能
     */
    public  interface  IUerFindPass<U extends  IBaseUser>{
        /**
         * 找回密码
         */
        void   findPass (U u);
    }
    
    /**
     * 获取用户信息功能
     */
    public  interface  IUerLoginInfo<U extends  IBaseUser>{
        /**
         * 保存
         */
        void   saveLoginInfo (U u);

        /**
         * 获取
         */
        void   getLoginInfo (U u);
    }

}
