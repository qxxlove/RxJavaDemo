package com.example.dell.rxjavademo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.adaper.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View
 *     ① 组合已有的控件
 *          例子：
 *              优酷菜单
 *              ViewPager轮播图
 *              下拉列表
 *     ②  单个已有的控件进行扩展
 *              自定义ListView（上拉加载，下拉刷新）
 *     ③  完全自定义
 *             开关闭合效果
 *             侧滑面板（侧滑菜单）
 *
 *      角度： 逆时针 递减
 *             顺时针 递增
 *
 *    补间动画的缺点： 控件的点击事件还在原地，实际开发中处理起来会比较麻烦，
 *                      所以上述 优酷菜单的实现建议采用属性动画
 *       ViewPager 左右无线滑动，自动轮播，  等  存在bug。实际开发中只可借鉴
 *                  ViewPager 的item 点击事件参考：http://blog.csdn.net/luanxuye/article/details/51377411
 *
 */


public class CustomViewActivity extends AppCompatActivity {

    private RelativeLayout rel_one;
    private RelativeLayout rel_two;
    private RelativeLayout rel_three;
    private ImageView iamge_home;
    private ImageView image_menu;

    private   boolean level3isShow = true;
    private   boolean level2isShow = true;
    private   boolean level1isShow = true;
    private ViewPager viewpager;
    private TextView text_content;
    private LinearLayout linearLayout;

    private int[] intResouce;
    private  String[] content_text;
    private List<ImageView> listImageView;
    private  boolean isRuning;
    private Button btn_popup_drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        initView();
        initData();
        initClick();


    }

    private void initView() {
        rel_one = ((RelativeLayout) findViewById(R.id.relative_one));
        rel_two = ((RelativeLayout) findViewById(R.id.relative_two));
        rel_three = ((RelativeLayout) findViewById(R.id.relative_three));
        iamge_home = ((ImageView) findViewById(R.id.image_home));
        image_menu = ((ImageView) findViewById(R.id.image_menu));

        viewpager = ((ViewPager) findViewById(R.id.viewpager));
        text_content = ((TextView) findViewById(R.id.text_content));
        linearLayout = ((LinearLayout) findViewById(R.id.linear_point));

        btn_popup_drop = ((Button) findViewById(R.id.btn_popupwindow_drop));
    }

    private void initData() {
        intResouce = new int []{R.mipmap.mmexport1464971477832,R.mipmap.mmexport1464971510995,R.mipmap.mmexport1465069360735,
        R.mipmap.mmexport1464971534887,R.mipmap.mmexport1465069351620,R.mipmap.mmexport1465069346141};
         content_text = new String[]{"1","2","3","4","5","6"};

        listImageView = new ArrayList<>();

        ImageView imageview;
        View view = null;
        LinearLayout.LayoutParams layoutParams ;
        for (int i = 0; i <intResouce.length ; i++) {
            imageview = new ImageView(this);
            imageview.setImageResource(intResouce[i]);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            listImageView.add(imageview);

            view = new View(this);
            view.setBackgroundResource(R.drawable.selector_point_bc);

            layoutParams = new LinearLayout.LayoutParams(10,10);
            if (i != 0) {
               layoutParams.leftMargin = 20 ;
            }
            linearLayout.addView(view, layoutParams);
            view.setEnabled(false);

        }

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(listImageView);
        viewpager.setAdapter(viewPagerAdapter);
        linearLayout.getChildAt(0).setEnabled(true);
        text_content.setText(content_text[0]);

        // 默认设置到中间某个位置
         int pos = Integer.MAX_VALUE  / 2 - (Integer.MAX_VALUE /2 % listImageView.size());
         // 2147483647 / 2 =  103741823 - (103741823 % size)
         viewpager.setCurrentItem(pos);

        // 无线轮播
        startUnlimit();
    }

    private void startUnlimit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRuning = true;
                while (isRuning) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
                    }
                });
              }
            }
        }).start();
    }

    private void initClick() {
        if (AnimationUtils.count > 0 ) {    // 当前动画在执行，取消点击事件
            return;
        }

        iamge_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long delay = 0;
                //Toast.makeText(CustomViewActivity.this,"dianji",Toast.LENGTH_SHORT).show();
                if (level2isShow) {
                    // 如果三级菜单已经显示，先转出去
                     if (level3isShow) {
                         AnimationUtils.rotateOutAnimation(rel_three,0);
                         level3isShow = false;
                         delay += 200;
                     }
                    // 如果二级菜单已经显示，转出去
                    AnimationUtils.rotateOutAnimation(rel_two,delay);
                    // level3isShow = false;
                } else {// 如果二级菜单没有显示，转出来
                    AnimationUtils.rotateInAnimation(rel_two,0);
                    // level3isShow = true;
                }
                level2isShow = !level2isShow;
                // 相当于level3isShow = false;level3isShow = true
                // 意思是不管level3isShow 是什么值，执行完上面的if.... else....    ,都取反

            }
        });

         image_menu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //Toast.makeText(CustomViewActivity.this,"dianji",Toast.LENGTH_SHORT).show();

                 // 如果三级菜单已经显示，转出去
                 if (level3isShow) {
                     AnimationUtils.rotateOutAnimation(rel_three, 0);
                     // level3isShow = false;
                 } else {// 如果三级菜单没有显示，转出来
                     AnimationUtils.rotateInAnimation(rel_three, 0);
                     // level3isShow = true;
                 }
                 level3isShow = !level3isShow;
                 // 相当于level3isShow = false;level3isShow = true
                 // 意思是不管level3isShow 是什么值，执行完上面的if.... else....    ,都取反


             }
         });


         viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             private int lastPosition = 0;

             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(final int position) {
                 int newPostion = position % listImageView.size();
                 text_content.setText(content_text[newPostion]);
                 linearLayout.getChildAt(newPostion).setEnabled(true);
                 linearLayout.getChildAt(lastPosition).setEnabled(false);
                 lastPosition = newPostion;
                 /*listImageView.get(position).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Toast.makeText(CustomViewActivity.this,content_text[position],Toast.LENGTH_SHORT).show();
                     }
                 });*/
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });

         btn_popup_drop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(CustomViewActivity.this,CustomViewTwoActivity.class));
             }
         });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (AnimationUtils.count > 0 ) {    // 当前有动画在执行，消费掉时间，不做任何处理
                return  true;
            }
            if (level1isShow) {
                long  dlay = 0;
                  if (level3isShow) {   // 隐藏三级菜单
                      AnimationUtils.rotateOutAnimation(rel_three,0);
                      level3isShow  = false;
                      dlay += 200;
                  }
                  if (level2isShow ) {// 隐藏二级菜单
                      AnimationUtils.rotateOutAnimation(rel_two,dlay);
                      level2isShow  = false;
                  }
                AnimationUtils.rotateOutAnimation(rel_one,400);// 隐藏一级菜单

            }else {
                AnimationUtils.rotateInAnimation(rel_one,0);// 显示一级菜单
                AnimationUtils.rotateInAnimation(rel_two,200);// 显示二级菜单
                AnimationUtils.rotateInAnimation(rel_three,400);// 显示三级菜单
                 level2isShow = true;
                 level3isShow = true;
            }
            level1isShow = !level1isShow;

            return  true;   // 消费此事件
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRuning = false;
    }
}
