package com.example.dell.rxjavademo.designmode;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.designmode.builder.IBuilder;
import com.example.dell.rxjavademo.designmode.builder.MyBuilder;
import com.example.dell.rxjavademo.designmode.builder.Teacher;
import com.example.dell.rxjavademo.designmode.factory.AppAountLoginerFactory;
import com.example.dell.rxjavademo.designmode.factory.bean.User;
import com.example.dell.rxjavademo.designmode.factory.interf.UserInfoFactory;
import com.example.dell.rxjavademo.designmode.proty.FindGrilInterface;
import com.example.dell.rxjavademo.designmode.proty.Jessen;
import com.example.dell.rxjavademo.designmode.proty.PateryProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 设计模式
 *
 * 参考：
 *      https://mp.weixin.qq.com/s/I4M1QCxQx49IA-hVjhelKQ
 *      https://mp.weixin.qq.com/s/ReluZMc73D6-VReFX7CgDQ
 *
 *      反射机制
 *      https://blog.csdn.net/carson_ho/article/details/80921333
 *
 *      定义：Java语言中 一种 动态（运行时）访问、检测 & 修改它本身的能力
 *
 *      作用：动态（运行时）获取类的完整结构信息 & 调用对象的方法
 *
 *      说明:  1. 类的结构信息包括：变量、方法等

              2. 正常情况下，Java类在编译前，就已经被加载到JVM中；
                 而反射机制使得程序运行时还可以动态地去操作类的变量、方法等信息
        优点：
             灵活性高。因为反射属于动态编译，即只有到运行时才动态创建 &获取对象实例。

       编译方式说明：
           1. 静态编译：在编译时确定类型 & 绑定对象。如常见的使用new关键字创建对象
           2. 动态编译：运行时确定类型 & 绑定对象。动态编译体现了Java的灵活性、多态特性 & 降低类之间的藕合性

        缺点：
          执行效率低
             因为反射的操作 主要通过JVM执行，所以时间成本会 高于 直接执行相同操作
                 1. 因为接口的通用性，Java的invoke方法是传object和object[]数组的。
                    基本类型参数需要装箱和拆箱，产生大量额外的对象和内存开销，频繁促发GC。
                 2. 编译器难以对  动态调用的代码  提前做优化，比如方法内联。
                 3. 反射需要  按名检索  类和方法，有一定的时间开销。
         容易破坏类结构
             因为反射操作饶过了源码，容易干扰类原有的内部逻辑

        使用场景：
            动态获取 类文件 结构信息（如变量、方法等） & 调用对象的方法

           常用的需求场景有：动态代理、工厂模式优化、Java JDBC数据库操作等

        实现：
           反射机制的实现 主要通过 操作java.lang.Class类

            介绍 java.lang.Class 类
                作用：存放着 对应类型对象的 运行时信息
                      1. 在Java程序运行时，Java虚拟机为所有类型维护一个java.lang.Class对象
                      2. 该Class对象存放着所有关于该对象的 运行时信息
                      3. 泛型形式为Class<T>

           Java反射机制的实现需要依靠：
                 Class类       ： 类对象
                 Constructor类 ：  类构造器对象
                 Field类       ：   类属性对象
                 Method类      ：    类方法对象


        使用步骤：
         1. 获取 目标类型的Class对象
         2. 通过 Class 对象 分别  获取  Constructor类对象、Method类对象 & Field 类对象
         3. 通过 Constructor类对象、Method类对象 & Field类对象分别 获取 类的构造函数、方法&属性的具体信息，并进行后续操作


        特别注意：访问权限问题
            背景
               反射机制的默认行为受限于Java的访问控制
               如，无法访问（ private ）私有的方法、字段
            冲突
               Java安全机制只允许查看任意对象有哪些域，而不允许读它们的值
               若强制读取，将抛出异常
            解决方案
                脱离Java程序中安全管理器的控制、屏蔽Java语言的访问检查，从而脱离访问控制
                具体实现手段：使用Field类、Method类 & Constructor类对象的setAccessible()
               void setAccessible(boolean flag)
               // 作用：为反射对象设置可访问标志
              // 规则：flag = true时 ，表示已屏蔽Java语言的访问检查，使得可以访问 & 修改对象的私有属性

               boolean isAccessible()
               // 返回反射对象的可访问标志的值

              static void setAccessible(AccessibleObject[] array, boolean flag)
              // 设置对象数组可访问标志

 *
 */

public class DesignModeActivity extends AppCompatActivity {

    private static final String TAG = "DesignModeActivity";
    private TextView textOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mode);
        initView();
        initData();
        initFactory();
        initBuilder();
        initBuilderTwo();
        initReflection();
        initReflectionDemoOne();
        initReflectionDemoTwo();
        initReflectionDemoThree();
        initClick();

        Toast.makeText(DesignModeActivity.this, "Toast", Toast.LENGTH_SHORT).show();
    }

    /**
     * 利用反射调用类对象的方法
     */
    private void initReflectionDemoThree() {
        // 1. 获取Student类的Class对象
        Class studentClass = com.example.dell.rxjavademo.designmode.reflection.Student.class;

        // 2. 通过Class对象创建Student类的对象
        Object  mStudent = null;
        try {
            mStudent = studentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 3.1 通过Class对象获取方法setName1（）的Method对象:需传入方法名
        // 因为该方法 = 无参，所以不需要传入参数
        Method  msetName1 = null;
        try {
            msetName1 = studentClass.getMethod("setName1");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 通过Method对象调用setName1（）：需传入创建的实例
        try {
            msetName1.invoke(mStudent);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.2 通过Class对象获取方法setName2（）的Method对象:需传入方法名 & 参数类型
        Method msetName2 = null;
        try {
            msetName2 = studentClass.getMethod("setName2",String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 通过Method对象调用setName2（）：需传入创建的实例 & 参数值
        try {
            msetName2.invoke(mStudent,"Carson_Ho");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 利用反射调用类的构造函数
     */
    private void initReflectionDemoTwo() {
           /**利用反射调用构造函数*/
                // 1. 获取Student类的Class对象
                Class studentClass  = com.example.dell.rxjavademo.designmode.reflection.Student.class;

        // 2.1 通过Class对象获取Constructor类对象，从而调用无参构造方法
        // 注：构造函数的调用实际上是在newInstance()，而不是在getConstructor()中调用
        try {
            Object mObj1 = studentClass.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 2.2 通过Class对象获取Constructor类对象（传入参数类型），从而调用有参构造方法
        try {
            Object mObj2 = studentClass.getConstructor(String.class).newInstance("Carson");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    /**
     * 反射具体实例
     *     实例1：利用反射获取类的属性 & 赋值
     */
    private void initReflectionDemoOne() {
        /**利用反射获取属性 & 赋值*/
        // 1. 获取Student类的Class对象
        Class studentClass = com.example.dell.rxjavademo.designmode.reflection.Student.class;

        // 2. 通过Class对象创建Student类的对象
        Object mStudent = null;
        try {
            mStudent = studentClass.newInstance();
            // 3. 通过Class对象获取Student类的name属性
            Field f = studentClass.getDeclaredField("name");

            // 4. 设置私有访问权限
            f.setAccessible(true);

            // 5. 对新创建的Student对象设置name值
            f.set(mStudent, "Carson_Ho");

            // 6. 获取新创建Student对象的的name属性 & 输出
            Log.e("DesignModeActivity","name::::::"+f.get(mStudent));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
    

    /**
     * Java 反射机制的实现概念
     */
    private void initReflection() {
        // FIXME: 2018/12/17  步骤一
        /**方式1：Object.getClass() */
        // Object类中的getClass()返回一个Class类型的实例
        Boolean carson = true;
        Class<?> classType = carson.getClass();
        System.out.println(classType);
        // 输出结果：class java.lang.Boolean

        /** 方式2：T.class 语法  */
        // T = 任意Java类型
        Class<?> classTypeOne = Boolean.class;
        System.out.println(classTypeOne);
        // 输出结果：class java.lang.Boolean
        // 注：Class对象表示的是一个类型，而这个类型未必一定是类
        // 如，int不是类，但int.class是一个Class类型的对象

        /** 方式3：static method Class.forName  */
        try {
            Class<?> classTypeTwo = Class.forName("java.lang.Boolean");
            // 使用时应提供异常处理器
            System.out.println(classTypeTwo);
            // 输出结果：class java.lang.Boolean
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        
        /** 方式4：TYPE语法
         *    补充：
         *     java.lang.reflect.Type 是 Java 中所有类型的父接口
         *
         *
         * */
        Class<?> classTypeThree = Boolean.TYPE;
        System.out.println(classTypeThree);
        // 输出结果：boolean  

        
        // FIXME: 2018/12/17  步骤二  即以下方法都属于`Class` 类的方法
        /** 1. 获取类的构造函数（传入构造函数的参数类型）*/
        // a. 获取指定的构造函数 （公共 / 继承）
        // Constructor<T> getConstructor(Class<?>... parameterTypes)
        // b. 获取所有的构造函数（公共 / 继承） 
        //Constructor<?>[] getConstructors();
        // c. 获取指定的构造函数 （ 不包括继承）
        // Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)
        // d. 获取所有的构造函数（ 不包括继承）
       // Constructor<?>[] getDeclaredConstructors();
        
        // 上述 a,b,c,d,e 最终都是获得一个Constructor类对象

        // 特别注意：
        // 1. 不带  "Declared" 的方法支持 取出包括 继承、公有（Public） & 不包括有（Private）的构造函数
        // 2. 带  "Declared" 的方法是 支持取出 包括公共（Public）、保护（Protected）、默认（包）访问和私有（Private）的构造方法，
        //                                   但不包括继承的构造函数
        // 下面同理

        /** 2. 获取类的属性（传入属性名）*/
        // a. 获取指定的属性（公共 / 继承）
        //Field getField(String name);
        // b. 获取所有的属性（公共 / 继承）
        //Field[] getFields() ;
        // c. 获取指定的所有属性 （不包括继承）
        //Field getDeclaredField(String name) ；
        // d. 获取所有的所有属性 （不包括继承）
        //Field[] getDeclaredFields() ；
        // a,b,c,d 最终都是获得一个Field类对象

        /** 3. 获取类的方法（传入方法名 & 参数类型）*/
        // a. 获取指定的方法（公共 / 继承）
       // Method getMethod(String name, Class<?>... parameterTypes) ；
        // b. 获取所有的方法（公共 / 继承）
      //  Method[] getMethods() ；
        // c. 获取指定的方法 （ 不包括继承）
      //  Method getDeclaredMethod(String name, Class<?>... parameterTypes) ；
        // d. 获取所有的方法（ 不包括继承）
        //Method[] getDeclaredMethods() ；
        // 上述 a,b,c,d 最终都是获得一个Method类对象
        
        /**4. Class类的其他常用方法*/
        // 返回父类
        //getSuperclass();

        // 作用：返回完整的类名（含包名，如java.lang.String ）
       // String getName();

        // 作用：快速地创建一个类的实例
        // 具体过程：调用默认构造器（若该类无默认构造器，则抛出异常
        // 注：若需要为构造器提供参数需使用java.lang.reflect.Constructor中的newInstance（）
        // Object newInstance();

        // FIXME: 2018/12/17  步骤三
        // 即以下方法都分别属于`Constructor`类、`Method`类 & `Field`类的方法。

        /** 1. 通过Constructor 类对象获取类构造函数信息 */

        //String getName();// 获取构造器名
      //  Class getDeclaringClass()；// 获取一个用于描述类中定义的构造器的Class对象
       // int getModifiers()；// 返回整型数值，用不同的位开关描述访问修饰符的使用状况
       // Class[] getExceptionTypes()；// 获取描述方法抛出的异常类型的Class对象数组
       // Class[] getParameterTypes()；// 获取一个用于描述参数类型的Class对象数组

        /**2. 通过Field类对象获取类属性信息*/
       // String getName()；// 返回属性的名称
       // Class getDeclaringClass()； // 获取属性类型的Class类型对象
      //  Class getType()；// 获取属性类型的Class类型对象
      //  int getModifiers()； // 返回整型数值，用不同的位开关描述访问修饰符的使用状况
      //  Object get(Object obj) ；// 返回指定对象上 此属性的值
      //  void set(Object obj, Object value) // 设置 指定对象上此属性的值为value

        /** 3. 通过Method 类对象获取类方法信息*/
      //  String getName()；// 获取方法名
      //  Class getDeclaringClass()；// 获取方法的Class对象
       // int getModifiers()；// 返回整型数值，用不同的位开关描述访问修饰符的使用状况
      //  Class[] getExceptionTypes()；// 获取用于描述方法抛出的异常类型的Class对象数组
      //  Class[] getParameterTypes()；// 获取一个用于描述参数类型的Class对象数组

        /**额外：java.lang.reflect.Modifier类*/
        // 作用：获取访问修饰符
       // static String toString(int modifiers)
        // 获取对应modifiers位设置的修饰符的字符串表示

        //static boolean isXXX(int modifiers)
        // 检测方法名中对应的修饰符在modifiers中的值
        
        
    }


    
    private void initBuilderTwo() {
        Teacher.Builder builder = new Teacher.Builder();
        builder.setAge(13)
                .setJob("你猜")
                .setName("后哈")
                /**关键一步，对Teacher 进行赋值*/
                .build();
        
    }

    private void initClick() {
        textOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void initView() {
        textOne = ((TextView) findViewById(R.id.text_one_design));
    }


    /**
     * 此处只是为了分析Builder 模式
     * 更好地理解
     * @param 
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle("Title");
        builder.setMessage("Message");
        builder.setCancelable(false);
        builder.setPositiveButton("Button1",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setTitle("点击了对话框上的Button1");
                    }
                });
        builder.setNeutralButton("Button2",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setTitle("点击了对话框上的Button2");
                    }
                });
        builder.setNegativeButton("Button3",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setTitle("点击了对话框上的Button3");
                    }
                });
        // 构建AlertDialog， 并且显示
        builder.create().show();
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    


    /**
     * 最基础也是最繁琐的Builder ，实际开发中不推荐
     * 所以出来后面的Android中 AlertDialog 中Builder的经典实现
     */
    private void initBuilder() {
        // 构建器
        IBuilder builder = new MyBuilder();
        // ① Director 封装构建过程
       // Director pcDirector = new Director(builder);
        // 封装构建过程, 4核, 内存2GB, Mac系统
        //pcDirector.construct(4, 2, "Mac OS X 10.9.1");
        // ② 不封装，直接构建
        builder.buildRAM(2);
        builder.buildOs("Mac OS X 10.9.1");
        builder.buildCPU(4);
        // 构建电脑, 输出相关信息
        Log.e(TAG,"Computer Info : " + builder.create().toString());

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
     *
     *  思考： 多种支付方式的切换、
     *         多种地图 SDK 的切换、
     *         多种网络框架的切换、
     *         多种持久化数据存储方式的切换、
     *         多种数据处理方式的切换、
     *         多种图片加载器的切换等等。
     *
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





 /*       I/System.out: class java.lang.Boolean
        I/System.out: class java.lang.Boolean
        I/System.out: class java.lang.Boolean
        I/System.out: boolean
        E/DesignModeActivity: 创建了一个Student实例
        E/DesignModeActivity: name::::::Carson_Ho
        E/DesignModeActivity: 创建了一个Student实例
        E/DesignModeActivity: 调用了有参构造函数
        E/DesignModeActivity: 创建了一个Student实例
        E/DesignModeActivity: 调用了无参方法：setName1（）
        I/System.out: Carson_Ho
        E/DesignModeActivity: 调用了有参方法setName2（String str）:Carson_Ho
        I/ViewRootImpl: CPU Rendering VSync enable = true*/