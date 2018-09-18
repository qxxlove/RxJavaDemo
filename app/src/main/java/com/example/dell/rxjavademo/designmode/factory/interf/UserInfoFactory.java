package com.example.dell.rxjavademo.designmode.factory.interf;

/**
 * 工厂的抽象接口，用于生产不同品牌的不同产品
 */

public interface UserInfoFactory {

   // 主要就是获取不同模块的产品抽象接口对象，便于客户端使用工厂的模块对象的时候多态性。
    /**登录功能*/
    IBaseUser.IUerLogin getLoginUser();
    /**注册功能*/
    IBaseUser.IUerRegister getRegisterUser();
    /**忘记密码功能*/
    IBaseUser.IUerFindPass getFindUser();
    /**用户信息功能*/
    IBaseUser.IUerLoginInfo getUserInfoUser();
    
}
