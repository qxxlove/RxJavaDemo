package com.example.dell.rxjavademo.adaper;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.base.BaseAdapter;
import com.example.dell.rxjavademo.base.BaseViewHolder;
import com.example.dell.rxjavademo.bean.DoubleBall;

import java.util.List;


public class RedBallAdapter extends BaseAdapter<DoubleBall> {
	private OnRedBallChangListener redBallChangListener;
	private  PopupWindow    popupWindow;
	public RedBallAdapter(Context context, List<DoubleBall> datas, int mLayoutId) {
		super(context, datas, mLayoutId);
	}

	@Override
	public void convert(BaseViewHolder holder, final DoubleBall bean) {
		final TextView redText=holder.getView(R.id.txt_redball);
		holder.setText(R.id.txt_redball, bean.getRedBall());
		
		holder.setClick(R.id.check_red, new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bean.isCheck()) {
					bean.setCheck(false);
					redText.setTextColor(mContext.getResources().getColor(R.color.text_red));
				}else {
					bean.setCheck(true);
					redText.setTextColor(mContext.getResources().getColor(R.color.text_white));
				}
				notifyDataSetChanged();
				redBallChangListener.setOnRedBallListener();
			}
		});
		 CheckBox checkBox = holder.getView(R.id.check_red);
		checkBox.setChecked(bean.isCheck());
		
	}
	public  interface OnRedBallChangListener{
		public void setOnRedBallListener();
	}
	public void setOnRedBallListener(OnRedBallChangListener redBallChangListener){
		this.redBallChangListener=redBallChangListener;
	}


}
