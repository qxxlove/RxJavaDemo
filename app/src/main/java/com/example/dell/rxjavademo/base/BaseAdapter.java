package com.example.dell.rxjavademo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.rxjavademo.utils.BaseUtils;

import java.util.List;

/**
 * @ClassName BaseAdapter
 * @Description TODO(适配器封装)
 * @author txb
 * @Date 2016年12月8日 下午2:44:39
 * @version 1.0.0
 * @param <T>
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected int mLayoutId;
	
	public BaseAdapter(Context context, List<T> datas, int mLayoutId) {
		this.mContext = context;
		this.mDatas = datas;
		this.mInflater = LayoutInflater.from(context);
		this.mLayoutId = mLayoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseViewHolder holder = BaseViewHolder.getInstance(mContext,
				mLayoutId, position, convertView, parent);
		try {
			convert(holder, mDatas.get(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return holder.getConvertView();
	}

	/**
	 * 填充holder里面控件的数据
	 * 
	 * @param holder
	 * @param bean
	 */
	public abstract void convert(BaseViewHolder holder, T bean);
	
	
	/**
	 * 删除一列数据
	 * 
	 * @param position
	 */
	public BaseAdapter remove(int position) {
		this.mDatas.remove(position);
		notifyDataSetChanged();
		return this;
	}

	public BaseAdapter remove(T t) {
		if (t != null) {
			this.mDatas.remove(t);
			notifyDataSetChanged();
		}
		return this;
	}

	public BaseAdapter removeAll() {
		if (mDatas != null) {
			this.mDatas.clear();
			notifyDataSetChanged();
		}
		return this;
	}

	public BaseAdapter update(int pos, T t) {
		if (t != null) {
			this.mDatas.set(pos, t);
			notifyDataSetChanged();
		}
		return this;
	}

	
	public BaseAdapter subList(int start,int end){
		if (BaseUtils.isListEmpty(mDatas)) {
			if (mDatas.size() > end) {
				List datas = this.mDatas.subList(start, end);
				this.mDatas.clear();
				this.mDatas.addAll(datas);
				notifyDataSetChanged();
			}
		}
		return this;
	}
	
	/**
	 * 添加多条数据到第pos 位
	 * 
	 * @param pos
	 * @param list
	 */
	public BaseAdapter add(int pos, List<T> list) {
		if (BaseUtils.isListEmpty(list)) {
			this.mDatas.addAll(pos, list);
			notifyDataSetChanged();
		}
		return this;
	}
	
	public BaseAdapter add(int pos, T data) {
		if (data != null) {
			this.mDatas.add(pos, data);
			notifyDataSetChanged();
		}
		return this;
	}

	public BaseAdapter add(List<T> list) {
		if (BaseUtils.isListEmpty(list)) {
			this.mDatas.addAll(list);
			notifyDataSetChanged();
		}
		return this;
	}

	public BaseAdapter add(T t) {
		if (t != null) {
			this.mDatas.add(t);
			notifyDataSetChanged();
		}
		return this;
	}

	public List<T> getDatas() {
		return mDatas;
	}
	
	public void update(List<T> list){
		if (BaseUtils.isListEmpty(list)) {
			this.mDatas = list;
			notifyDataSetChanged();
		}
	}
}
