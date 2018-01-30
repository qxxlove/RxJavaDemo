package com.example.dell.rxjavademo.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseViewHolder {
	private int mPosition;
    /*
     * 用于存储holder里面的各个view，此集合比map效率高,但key必须为Integer
     */
    private SparseArray<View> mViews;
    /**
     * 复用的view
     */
    private View convertView;

    private BaseViewHolder(Context context,int position, int layoutId, ViewGroup parent) {
        this.mPosition = position;
        mViews = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        convertView.setTag(this);
    }

    public static BaseViewHolder getInstance(Context context,int layoutId,int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new BaseViewHolder(context, position, layoutId, parent);
        } else {
        	BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    /**
     * 通过resourceId获取item里面的view
     * @param resourceId 控件的id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int resourceId) {
        View view = mViews.get(resourceId);
        if (view == null) {
            view = convertView.findViewById(resourceId);
            mViews.put(resourceId, view);
        }
        return (T) view;
    }

    /**
     * 为textview类型填充内容
     * @param resourceId
     * @param text
     * @return CommonViewHolder
     */
    public BaseViewHolder setText(int resourceId,CharSequence text ) {
        ((TextView) getView(resourceId)).setText(text);
        return this;
    }
    public BaseViewHolder setText(int resourceId,int resid ) {
        ((TextView) getView(resourceId)).setText(resid);
        return this;
    }
    
    public BaseViewHolder setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		if (view != null) {
			view.setVisibility(visibility);
		}
		return this;
	}
    /**
     * 为ImageView设置Bitmap
     * @param resourceId
     * @param bm
     * @return
     */
    public BaseViewHolder setBitmap(int resourceId,Bitmap bm) {
        ((ImageView)getView(resourceId)).setImageBitmap(bm);
        return  this;
    }
    public BaseViewHolder setImageDrawable(int resourceId,Drawable drawable) {
        ((ImageView)getView(resourceId)).setImageDrawable(drawable);
        return  this;
    }
    public BaseViewHolder setImageResource(int resourceId,int resId) {
        ((ImageView)getView(resourceId)).setImageResource(resId);
        return  this;
    }
   

    public View getConvertView() {
        return convertView;
    }
    /**
     * 获取当前item的位置
     * @return
     */
    public int getPosition() {
        return mPosition;
    }
    
    
    public BaseViewHolder setClick(int resourceId,OnClickListener onClickListener){
    	View view = getView(resourceId);
		if (view != null) {
			view.setOnClickListener(onClickListener);
		}
		return this;
    }
    public BaseViewHolder setOnLongClick(int resourceId,OnLongClickListener onLongClickListener){
    	View view = getView(resourceId);
    	if (view != null) {
    		view.setOnLongClickListener(onLongClickListener);
    	}
    	return this;
    }
}
