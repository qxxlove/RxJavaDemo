package com.example.dell.rxjavademo.designmode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.designmode.factory.AppAountLoginerFactory;
import com.example.dell.rxjavademo.designmode.factory.bean.User;
import com.example.dell.rxjavademo.designmode.factory.interf.UserInfoFactory;
/**
 * 工厂模式
 *    对于调用者来说很简单，只要关心当前用的是什么平台的帐号系统，而不需要关心具体的实现方式。
 *    也把不同平台(自己，第三方（微信,微博,QQ等等）)的登录、注册、获取用户信息等分离开来。
 * 以下描述需要实践
 *    当然往往不同的平台可能退出当前帐号的方式是一样，
 *    这个时候，其实可以把 BaseUserLogin 当做代理对象，
 *             目标接口就是 IBaseUser，
 *            目标对象 另外新建一个类实现目标接口，
 *            利用代理模式。
 *
 *  思考： 多种支付方式的切换、
 *         多种地图 SDK 的切换、
 *         多种网络框架的切换、
 *         多种持久化数据存储方式的切换、
 *         多种数据处理方式的切换、
 *         多种图片加载器的切换等等。
 *
 *   参考二 ：
 *        简单工厂模式（SimpleFactoryPattern）- 最易懂的设计模式解析
 *        https://blog.csdn.net/carson_ho/article/details/52223153
 *
 *        概念：
 *           简单工厂模式我们可以理解为负责  生产对象 的一个类 ，称为“工厂类”。
 *        用处：
 *           将“类实例化的操作”与“使用对象的操作”分开，
 *           让使用者不用知道具体参数就可以实例化出所需要的“产品”类，
 *           从而避免了在客户端代码中显式指定，实现了解耦。
 *
 */
// FIXME: 2018/9/18  先有思路，在实践


public class UserLoginFactoryActivity extends AppCompatActivity {

    private   UserInfoFactory userInfoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_factory);
        initData();
         initClick();
    }

    private void initClick() {
        findViewById(R.id.text_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user   = new User();
                user.setName("xiaoQiao");
                user.setPass("123456");

                userInfoFactory.getLoginUser().login(user);
            }
        });
        findViewById(R.id.text_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user   = new User();
                user.setName("hahahha");
                user.setPass("123456");

                userInfoFactory.getRegisterUser().register(user);
            }
        });
        findViewById(R.id.text_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user   = new User();
                user.setName("hahahha");
                user.setPass("123456");

                userInfoFactory.getLoginUser().loginOut(user);
            }
        });
    }

    private void initData() {
        userInfoFactory = AppAountLoginerFactory.create(this);

        // userInfoFactory.getFindUser().findPass(user);
        // userInfoFactory.getRegisterUser().register(user);
        // userInfoFactory.getUserInfoUser().getLoginInfo(user);
    }
}
