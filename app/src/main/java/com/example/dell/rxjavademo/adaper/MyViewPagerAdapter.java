package com.example.dell.rxjavademo.adaper;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private List<ImageView> list;


    public MyViewPagerAdapter(List<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // 当滑到下一页，又返回这一页，view是否可以复用
        //返回规则

        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // super.destroyItem(container, position, object);
        container.removeView(((ImageView) object));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
          int newPosition = position % list.size();
          ImageView imageView = list.get(newPosition);
          container.addView(imageView);
        return imageView;
    }
}
