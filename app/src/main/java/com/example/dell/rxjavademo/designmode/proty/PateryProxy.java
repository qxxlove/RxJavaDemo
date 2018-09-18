package com.example.dell.rxjavademo.designmode.proty;

/**
 * 代理对象
 */

public class PateryProxy implements FindGrilInterface {

    private FindGrilInterface findGrilInterface;

    public PateryProxy(FindGrilInterface findGrilInterface) {
        this.findGrilInterface = findGrilInterface;
    }

    /**
     *   Patery 找到对象告诉 Jessen
     * @param name  姓名
     * @param age   年龄
     */
    @Override
    public void findGril(String name, int age) {
         findGrilInterface.findGril(name,age);
    }

    /**
     * 此处我们不做任何处理，而是通过动态代理实现
     * @param phoneName 手机品牌
     */
    @Override
    public void buyPhone(String name,String phoneName) {
        
    }
}
