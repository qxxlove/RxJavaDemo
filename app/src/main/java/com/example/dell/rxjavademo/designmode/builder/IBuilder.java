package com.example.dell.rxjavademo.designmode.builder;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/19.
 * 邮箱：123123@163.com
 */

public abstract class IBuilder {

    // 设置CPU核心数
    public abstract void buildCPU(int core);

    // 设置内存
    public abstract void buildRAM(int gb);

    // 设置操作系统
    public abstract void buildOs(String os);

    // 创建Computer
    public abstract Computer create();

}
