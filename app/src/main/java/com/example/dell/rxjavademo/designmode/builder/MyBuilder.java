package com.example.dell.rxjavademo.designmode.builder;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/19.
 * 邮箱：123123@163.com
 */

public class MyBuilder extends  IBuilder {

    private  Computer computer = new MyComputer();

    @Override
    public void buildCPU(int core) {
         computer.setCPU(core);
    }

    @Override
    public void buildRAM(int gb) {
          computer.setRAM(gb);
    }

    @Override
    public void buildOs(String os) {
          computer.setOS(os);
    }

    @Override
    public Computer create() {
        return computer;
    }
}
