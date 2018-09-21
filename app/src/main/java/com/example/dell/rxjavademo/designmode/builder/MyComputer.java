package com.example.dell.rxjavademo.designmode.builder;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/19.
 * 邮箱：123123@163.com
 */

public class MyComputer extends  Computer {


    @Override
    public void setCPU(int CPU) {
        mCPU = CPU;
    }

    @Override
    public void setRAM(int RAM) {
       mRAM = RAM;
    }

    @Override
    public void setOS(String OS) {
        mOS = OS;
    }
}
