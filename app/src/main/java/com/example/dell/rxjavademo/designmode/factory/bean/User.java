package com.example.dell.rxjavademo.designmode.factory.bean;

import com.example.dell.rxjavademo.designmode.factory.interf.IBaseUser;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/18.
 * 邮箱：123123@163.com
 */

public class User  implements IBaseUser {

    private  String name ;
    private  String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
