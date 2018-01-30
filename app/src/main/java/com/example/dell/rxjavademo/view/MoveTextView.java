package com.example.dell.rxjavademo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * description: 重写TextView
 * autour: TMM
 * date: 2018/1/24 11:35
 * update: 2018/1/24
 * version:
 *  注意： 对于移动的7种方式，结果需要自己验证，总结
*/

public class   MoveTextView extends TextView {

    public static final String  TAG = "VIEW";
    int x = -200; int y = -400;


    public MoveTextView(Context context) {
        super(context);
    }

    public MoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"移动前TestTextView  getX "+event.getX()+" getY "+event.getY());
                Log.e(TAG,"移动前TestTextView  getrawx "+event.getRawX()+" getrawy "+event.getRawY());
                // 实现移动的方式有7种
                  // ①  layout()    参考结果： 移动后getLeft等值改变
                //  layout(getLeft() + 200, getTop() + 400, getRight() + 200, getBottom() + 400);
                  // ②  offsetLeftAndRight、offsetTopAndBottom    参考结果： 移动后getLeft等值改变
                /*  offsetLeftAndRight(200);
                  offsetTopAndBottom(400);*/
                  // ③ 修改LayoutParams        参考结果： 移动后getLeft等值不改变
             /*   ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
                lp.leftMargin = getLeft() + 200;
                lp.topMargin = getTop() + 400;
                setLayoutParams(lp);*/
                  // ④ scrollTo    (为什么x,y 是负值就报错)   参考结果：移动后getLeft等值不改变
                //((View)getParent()).scrollTo(x,y);
                  // ⑤ scrollBy                        参考结果： 移动后getLeft等值不改变
               // ((View)getParent()).scrollBy(x,y);
                  // ⑥属性动画                        参考结果：移动后getLeft等值不改变
               /* AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(this, "translationX", 200),
                        ObjectAnimator.ofFloat(this,"translationY", 400)

                );
                set.start();*/
                  // ⑦位移动画             参考结果： 移动后getLeft等值不改变
             /*   TranslateAnimation anim = new TranslateAnimation(0,200,0,400);
                anim.setFillAfter(true);
                startAnimation(anim);*/

                Log.e(TAG,"_____________________________________________________");
                Log.e(TAG,"移动后 getX " + getX() + "  getY " + getY());
                Log.e(TAG,"移动后 getLeft " + getLeft() + "tv getTop " + getTop()
                        + " tv getRight " + getRight() + " tv getBottom " + getBottom());
                break;
            default:
                break;
        }
        return true;
    }
}


/*
只是其中的① 的结果
01-24 10:59:35.396 7296-7296/com.example.dell.rxjavademo E/VIEW: 移动前TestTextView  getX 88.0 getY 112.0
        01-24 10:59:35.396 7296-7296/com.example.dell.rxjavademo E/VIEW: 移动前TestTextView  getrawx 88.0 getrawy 274.0
        01-24 10:59:35.398 7296-7296/com.example.dell.rxjavademo E/VIEW: _____________________________________________________
        01-24 10:59:35.398 7296-7296/com.example.dell.rxjavademo E/VIEW: 移动后 getX 200.0  getY 400.0
        01-24 10:59:35.398 7296-7296/com.example.dell.rxjavademo E/VIEW: 移动后 getLeft 200tv getTop 400 tv getRight 400 tv getBottom 600
*/
