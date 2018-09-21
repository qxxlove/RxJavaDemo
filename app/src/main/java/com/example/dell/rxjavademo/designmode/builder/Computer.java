package com.example.dell.rxjavademo.designmode.builder;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/19.
 * 邮箱：123123@163.com
 */

public abstract class Computer {

    /**CPU*/
    protected   int  mCPU ;
    /**内存*/
    protected  int  mRAM;
    /**操作系统*/
    protected  String mOS;

    public Computer() {
    }

  

    public abstract void setCPU(int CPU);
    public  abstract void setRAM(int RAM);
    public abstract void setOS(String OS);


    @Override
    public String toString() {
        return "Computer{" +
                "mCPU=" + mCPU +
                ", mRAM=" + mRAM +
                ", mOS='" + mOS + '\'' +
                '}';
    }
}
