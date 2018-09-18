package com.example.dell.rxjavademo.designmode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.designmode.factory.AppAountLoginerFactory;
import com.example.dell.rxjavademo.designmode.factory.bean.User;
import com.example.dell.rxjavademo.designmode.factory.interf.UserInfoFactory;
import com.example.dell.rxjavademo.designmode.proty.FindGrilInterface;
import com.example.dell.rxjavademo.designmode.proty.Jessen;
import com.example.dell.rxjavademo.designmode.proty.PateryProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 设计模式
 *
 * 参考：
 *      https://mp.weixin.qq.com/s/I4M1QCxQx49IA-hVjhelKQ
 *      https://mp.weixin.qq.com/s/ReluZMc73D6-VReFX7CgDQ
 */

public class DesignModeActivity extends AppCompatActivity {

    private static final String TAG = "DesignModeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mode);
        initData();
        initFactory();
    }

    /**
     * 工厂模式
     * 对于调用者来说很简单，只要关心当前用的是什么平台的帐号系统，而不需要关心具体的实现方式。
     * 也把不同平台(自己，第三方（微信,微博,QQ等等）)的登录、注册、获取用户信息等分离开来。
     * 以下描述需要实践 
     * 当然往往不同的平台可能退出当前帐号的方式是一样，
     * 这个时候，其实可以把 BaseUserLogin 当做代理对象，
     *             目标接口就是 IBaseUser，
     *            目标对象 另外新建一个类实现目标接口，
     *            利用代理模式。
     */
    // FIXME: 2018/9/18  先有思路，在实践
    private void initFactory() {
        UserInfoFactory userInfoFactory = AppAountLoginerFactory.create(this);
        User user   = new User();
        user.setName("xiaoQiao");
        user.setPass("123456");

        userInfoFactory.getLoginUser().login(user);
       // userInfoFactory.getFindUser().findPass(user);
       // userInfoFactory.getRegisterUser().register(user);
       // userInfoFactory.getUserInfoUser().getLoginInfo(user);

    }

    /**
     * 动态代理代理模式
     */
    private void initData() {
        /**Proxy 代理模式*/
        /**下面是典型的静态代理模式，此时Jessen 还想做别的事情，代理PateryProxy 也要做，
         * 这样不利于代码的开发和维护也就出现动态代理*/
        
        /**创建Jessen*/
        FindGrilInterface findGrilInterface = new Jessen();
        /**把Jessen 交给 PateryProxy */
        PateryProxy pateryProxy = new PateryProxy(findGrilInterface);
        /**PateryProxy帮Jessen去找对象*/
        pateryProxy.findGril("xiaoQue",23);

        
        /**动态代理*/
        /**创建Jessen*/
        final FindGrilInterface findGrilInterfaceTwo = new Jessen();
        /**创建代理对象 PateryProxy */
        /**
         * Proxy.newProxyInstance
                第一个参数：目标对象的类加载器。因为这个代理对象是运行时才创建的，没有编译时候预先准备的字节码文件提供，
                      所以需要一个类加载器来加载产生Proxy代理里的类类型，便于创建代理对象。
               第二个参数：interfaces是目标接口数组
               第三个参数：是代理对象当调用目标接口方法的时候，会先回调InvocationHandler接口的实现方法invoke。
         */
        FindGrilInterface pateryProxyTwo = (FindGrilInterface) Proxy.newProxyInstance(
                findGrilInterfaceTwo.getClass().getClassLoader(),
                findGrilInterfaceTwo.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        long currentTime = System.currentTimeMillis();
                        // 调用目标接口的方法时，就调用invoke();
                        Object returnValue = method.invoke(findGrilInterfaceTwo,args);
                        long calledMethodTime = System.currentTimeMillis();
                        long invokeMethodTime = calledMethodTime - currentTime ;
                        // 接口方法执行的时间，便于检索性能
                        Log.e(TAG,"方法执行性能时间:"+invokeMethodTime);
                        return returnValue;
                    }
                });
        /**pateryProxyTwo帮Jessen去找对象*/
        pateryProxyTwo.findGril("xLi",28);
        pateryProxyTwo.buyPhone("pateryProxyTwo","iphone8");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}