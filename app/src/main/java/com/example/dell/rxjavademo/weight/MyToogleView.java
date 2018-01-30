package com.example.dell.rxjavademo.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DELL on 2017/7/2.
 *
 * 完全自定义控件：
 *
 *    onMearsure()  ----- >    onLayout () ---- >    onDraw()   他们都是在activity的onResume之后开始执行的
 *    然而： 如果自定义控件继承 view
 *            onMearsure()  ----- >    onDraw()
 *            自定义控件继承 viewgroup
 *            onMearsure()  ----- >    onLayout () (控件的摆放位置)---- >    onDraw()
 */
public class MyToogleView  extends View {

    private Bitmap bitmap_background;
    private Bitmap bitmap_slide;

    private Paint paint;
    private boolean currentState ;
    private float currentX;

    private ToggleSwitchStateListener toggleSwitchStateListener;

    public ToggleSwitchStateListener getToggleSwitchStateListener() {
        return toggleSwitchStateListener;
    }

    public void setToggleSwitchStateListener(ToggleSwitchStateListener toggleSwitchStateListener) {
        this.toggleSwitchStateListener = toggleSwitchStateListener;
    }

    /**
     *  用于控件实例化（new）
     * @param context
     */
    public MyToogleView(Context context) {
        super(context);
        init();
    }

    /**
     * 用于在xml文件中使用，自定义属性
     * @param context
     * @param attrs
     */
    public MyToogleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 用于在xml文件中使用，自定义属性 ,如果设置了样式，则会执行（defStyleAttr）
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyToogleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();


    }

    private void init() {
        paint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap_background.getWidth(),bitmap_background.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 先绘制背景
        canvas.drawBitmap(bitmap_background, 0, 0, paint);
        // 在绘制滑块

        // 根据用户触摸到的位置绘制滑块
        if (isTouchMode) {
           // 让滑块想左移动自身位置的一半
            float newLeft  = currentX - bitmap_slide.getWidth() / 2.0f;
            int  maxLeft = bitmap_background.getWidth() - bitmap_slide.getWidth();

            // 限定滑块的位置
            if (newLeft <0) {
                newLeft = 0;  // 限定左边
            } else  if (newLeft > maxLeft) {
                newLeft = maxLeft;// 限定右边
            }

            canvas.drawBitmap(bitmap_slide,newLeft,0,paint);


        }else {
            // 根据开关状态,直接设置滑块
            if (currentState) {   // 开
                int  maginleft = bitmap_background.getWidth() - bitmap_slide.getWidth();
                canvas.drawBitmap(bitmap_slide,maginleft,0,paint);
            }else  { // 关
                canvas.drawBitmap(bitmap_slide,0,0,paint);
            }
        }



    }


    boolean isTouchMode = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                currentX = event.getX();
                isTouchMode = true;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchMode = false;
                currentX = event.getX();

               float center =  bitmap_background.getWidth() / 2.0f;
                boolean state = currentX > center;

                if (currentState != state && toggleSwitchStateListener != null ){
                    toggleSwitchStateListener.onStateChange(state);
                }
               currentState = state;
                break;
        }

        // 重新绘制界面
        invalidate();   // 会重复调用onDraw()

        return true; //消费用户的触摸事件
    }

    public void setBackgroundImageView(int switch_background) {
        bitmap_background = BitmapFactory.decodeResource(getResources(), switch_background);

    }

    public void setBackgroundScroll(int slide_button) {
        bitmap_slide = BitmapFactory.decodeResource(getResources(), slide_button);
    }

    public void setCurrentState(boolean b) {
        this.currentState = b;

    }


    public  interface  ToggleSwitchStateListener {
         void  onStateChange(boolean state);
    }



}
