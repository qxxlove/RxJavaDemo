package com.example.dell.rxjavademo.androidversionchange;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.dell.rxjavademo.R;


/**
 * android 8.0 系统变化
 *
 * 参考： https://mp.weixin.qq.com/s/Ez-G_9hzUCOjU8rRnsW8SA
 *
 *        https://mp.weixin.qq.com/s/MhWurQy9oOf9OuDsdBLU-w
 *
 *        什么是通知渠道呢？
 *             顾名思义，就是每条通知都要属于一个对应的渠道。
 *             每个App都可以自由地创建当前App拥有哪些通知渠道，
 *             但是这些通知渠道的控制权都是掌握在用户手上的。
 *             用户可以自由地选择这些通知渠道的重要程度（等级），是否响铃、是否振动、或者是否要关闭这个渠道的通知。
 *
 *         通知渠道必须按实际开发需求，规划好，不可变更
 *
 *         创建通知渠道的代码只在第一次执行的时候才会创建，
 *         以后每次执行创建代码系统会检测到该通知渠道已经存在了，
 *         因此不会重复创建，也并不会影响任何效率。
 *
 *
 *         角标：
 *           之前： 可是国产手机厂商虽然可以订制ROM，
 *                  但是却没有制定API的能力，因此长期以来都没有一个标准的API来实现角标功能，
 *                  很多都是要通过向系统发送广播来实现的，而各个手机厂商的广播标准又不一致，经常导致代码变得极其混杂。
 *           现在及以后：
 *                  Google制定了Android系统上的角标规范，也提供了标准的API
 *                  但是默认显示的是小圆点，只有长按才能出现未读数量
 *
 *                  但是国内好多厂商都自己定制了，直接在右上角就展示了未读数量，类似于IOS效果
 *
 *                  需要注意的是，即使我们不调用setShowBadge(true)方法，
 *                  Android系统默认也是会显示角标的，
 *                  但是如果你想禁用角标功能，那么记得一定要调用setShowBadge(false)方法。
 *
 *     系统图标适配
 *     https://mp.weixin.qq.com/s/WxgHJ1stBjokPi6lTUd1Mg
 *
 *      如果你把你的targetSdkVersion 设置成26 ，系统就认为你已经做好了高版本的适配，
 *      但是你设置了26 却不适配，就会出现意想不到的效果。
 *
 *      点击res ----->右键 ---->Image Asset 然后就会蓝岛一个界面
 *
 *
 *      补充： Android 8.0 桌面快捷菜单 适配 
 *
 *
 */

public class NotificationActivity extends AppCompatActivity {

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        /**判断是否是Android8.0*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);

            /**以下代码慎用： 删除渠道。
             * 因为Google为了防止应用程序随意地创建垃圾通知渠道，会在通知设置界面显示所有被删除的通知渠道数量，不美观*/
           // NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
           // manager.deleteNotificationChannel(channelId);
        }


        initView();

    }

    private void initView() {
        findViewById(R.id.text_send_chart_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    /**获取渠道*/
                  NotificationChannel channel = manager.getNotificationChannel("chat");
                  /**防止用户误操作，将所有的渠道关闭，指引用户手动打开*/
                  if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                    startActivity(intent);
                    Toast.makeText(NotificationActivity.this, "请手动将通知打开，否则无法收到微信聊天消息", Toast.LENGTH_SHORT).show();
                  }
                 Notification notification = new NotificationCompat.Builder(NotificationActivity.this, "chat")
                        .setContentTitle("收到一条聊天消息")
                        .setContentText("今天中午吃什么？")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);
            }
            }
        });
        findViewById(R.id.text_send_subscribe_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(NotificationActivity.this, "subscribe")
                        .setContentTitle("收到一条订阅消息")
                        .setContentText("地铁沿线30万商铺抢购中！")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                        /**角标数量*/
                        .setNumber(2)
                        .setAutoCancel(true)
                        .build();
                manager.notify(2, notification);
            }
        });

    }


    /**
     * 顾名思义： 就是创建通知渠道
     * @param channelId  渠道ID :可以随便定义，只要保证全局唯一性就可以。
     * @param channelName  渠道名称 :渠道名称是给用户看的，需要能够表达清楚这个渠道的用途。
     * @param importance  重要等级 :重要等级的不同则会决定通知的不同行为，当然这里只是初始状态下的重要等级，用户可以随时手动更改某个渠道的重要等级，App是无法干预的。
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        /**展示角标*/
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


}

