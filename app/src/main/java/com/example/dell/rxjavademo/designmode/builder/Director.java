package com.example.dell.rxjavademo.designmode.builder;

/**
 * 组装过程
 */

public class Director {

    IBuilder iBuilder ;

    public Director(IBuilder iBuilder) {
        this.iBuilder = iBuilder;
    }

    /**
     * 构建对象
     * @param cpu
     * @param gb
     * @param os
     */
    public  void  construct (int cpu,int gb,String os){
        iBuilder.buildCPU(cpu);
        iBuilder.buildOs(os);
        iBuilder.buildRAM(gb);
    }

    


}
