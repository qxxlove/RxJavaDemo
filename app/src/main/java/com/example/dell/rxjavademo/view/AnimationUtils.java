package com.example.dell.rxjavademo.view;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by DELL on 2017/6/18.
 *     startAnimation   和 setAnimation    有什么区别？
 *
 */
public class AnimationUtils {


    public   static  int count = 0;

    /**
     * 旋转出去的动画
     */
    public static void rotateOutAnimation(RelativeLayout relativeLayout,long startOffset) {
        int childCount = relativeLayout.getChildCount();
        for (int i = 0; i <childCount ; i++) {
            relativeLayout.getChildAt(i).setEnabled(false);
        }

        RotateAnimation ra = new RotateAnimation(0f,-180f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,1.0f);
        ra.setDuration(500);
        ra.setFillAfter(true);    // 停留在动画结束的位置
        ra.setStartOffset(startOffset);   // 设置延迟
        relativeLayout.startAnimation(ra);
        ra.setAnimationListener(classMyanimtionListener);
    }

    public static void rotateInAnimation(RelativeLayout relativeLayout,long delay) {
        int childCount = relativeLayout.getChildCount();
        for (int i = 0; i <childCount ; i++) {
            relativeLayout.getChildAt(i).setEnabled(true);
        }
        RotateAnimation ra = new RotateAnimation(-180f,0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,1.0f);
        ra.setDuration(500);
        ra.setFillAfter(true);    // 停留在动画结束的位置
        ra.setStartOffset(delay);   // 设置延迟
        relativeLayout.startAnimation(ra);
        ra.setAnimationListener(classMyanimtionListener);


    }


     static Animation.AnimationListener  classMyanimtionListener  = new Animation.AnimationListener() {
         @Override
         public void onAnimationStart(Animation animation) {
             count ++;
         }

         @Override
         public void onAnimationEnd(Animation animation) {
             count -- ;
         }

         @Override
         public void onAnimationRepeat(Animation animation) {

         }
     };
}
