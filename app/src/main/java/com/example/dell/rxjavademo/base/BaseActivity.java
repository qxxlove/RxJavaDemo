package com.example.dell.rxjavademo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dell.rxjavademo.App;
import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.bean.ActionBarStyle;
import com.example.dell.rxjavademo.utils.BaseUtils;
import com.example.dell.rxjavademo.utils.Constants;
import com.example.dell.rxjavademo.view.SystemBarTintManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity {
	private FrameLayout baseLayout;
	protected App mApplication;
	protected Context mContext;
	private Button leftBtn, rightBtn;
	private TextView middleText, rightText;
	private ProgressBar progressBar;
	protected static Handler mHandler = new Handler(Looper.getMainLooper());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			mApplication = (App) App.getAppContext();
			mContext = this;
			initActionBar();
			initView(savedInstanceState);
			initTint();
			initData();
			initListener();
			execHttp();
			mApplication.putActivity(this);
		} catch (Exception e) {
			LogWrapper.e("tag",this.getClass().getSimpleName() + "初始化失败！");
			e.printStackTrace();
		}
	}

	/**
	 * @Description (设置沉浸式状态栏)
	 */
	private void initTint() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.base_action_bar_color);// 通知栏所需颜色
	}

	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
	 * @Description (初始化标题栏)
	 */
	private void initActionBar() {
		super.setContentView(R.layout.activity_base);
		leftBtn = (Button) findViewById(R.id.leftBtn);
		middleText = (TextView) findViewById(R.id.middleText);
		rightText = (TextView) findViewById(R.id.rightText);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		rightBtn = (Button) findViewById(R.id.rightBtn);
		baseLayout = (FrameLayout) findViewById(R.id.baseLayout);
		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onLeftBtnClick()) {
					onLeftBtnClick();
				} else {
					finish();
				}

			}
		});
		rightText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onRightTextClick(v);
			}
		});
		rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onRightBtnClick(v);

			}
		});
		setActionBarStyle(ActionBarStyle.HideBoth);
	}

	@Override
	public void setContentView(int layoutResID) {
		baseLayout.removeAllViews();
		View.inflate(this, layoutResID, baseLayout);
		onContentChanged();
	}

	@Override
	public void setContentView(View view) {
		baseLayout.removeAllViews();
		baseLayout.addView(view);
		onContentChanged();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		baseLayout.removeAllViews();
		baseLayout.addView(view, params);
		onContentChanged();
	}

	protected void initActionBar(ActionBarStyle style, String title) {
		middleText.setText(title);
		setActionBarStyle(style);
	}

	protected void initActionBar(ActionBarStyle style, int titleRes) {
		setTitleRes(titleRes);
		setActionBarStyle(style);
	}

	protected void setRightBtnRes(int rightBtnRes) {
		rightBtn.setBackgroundResource(rightBtnRes);
	}

	protected void setTitle(String title) {
		middleText.setText(title);
	}

	protected void setTitleRes(int titleRes) {
		setTitle(BaseUtils.getString(titleRes));
	}

	/**
	 * @Description (根据样式设定对应的显示方式)
	 * @param style
	 */
	protected void setActionBarStyle(ActionBarStyle style) {
		switch (style) {
		case ShowAll:
			leftBtn.setVisibility(View.VISIBLE);
			rightBtn.setVisibility(View.VISIBLE);
			break;
		case HideAll:
			leftBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.GONE);
			break;
		case HideLeft:
			leftBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.VISIBLE);
			break;
		case HideRight:
			leftBtn.setVisibility(View.VISIBLE);
			rightBtn.setVisibility(View.GONE);
			break;
		case HideBoth:
			leftBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.GONE);
			break;
		}
	}

	/** 初始化布局 */
	protected abstract void initView(Bundle savedInstanceState);

	/** 初始化数据 */
	protected abstract void initData();

	/** 设置监听器 */
	protected abstract void initListener();

	/** 网络请求 */
	protected abstract void execHttp();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mApplication.removeActivity(this);
	}

	protected void startActivity(Class cls) {
		Intent mIntent = new Intent(mContext, cls);
		startActivity(mIntent);
	}

	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @return
	 */
	protected boolean onLeftBtnClick() {
		return false;
	}

	protected void onLeftBtnClick(View v) {
	}

	protected void onRightBtnClick(View v) {
	}

	protected void onRightTextClick(View v) {
	}

	protected void toast(String arg0) {
		BaseUtils.toast(arg0);
	}

	protected void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	protected void hideProgress() {
		progressBar.setVisibility(View.GONE);
	}

	protected void setRightText(String rText) {
		rightText.setVisibility(View.VISIBLE);
		rightText.setText(rText);
	}

	protected void setRightText(int rTextRes) {
		rightText.setVisibility(View.VISIBLE);
		rightText.setText(BaseUtils.getString(rTextRes));
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 != RESULT_OK || arg2 == null)
			return;
		switch (arg0) {
		// 图片选择返回
		case Constants.REQUEST_CODE_IMAGE_SELECTOR:
			ArrayList<String> imageList = arg2.getStringArrayListExtra(Constants.KEY_IMAGE_SELECTOR);
			if (imageList == null || imageList.isEmpty())
				return;
			onImageSelector(imageList);
			return;
		// 图片选择返回
		case Constants.REQUESTCODE_TAKE_CAMERA:
			break;
		}

	}

	protected void onImageSelector(List<String> imageList) {
	}

	/** 获取标题栏高度 **/
	protected int getTopBarHeight() {
		View topBar = findViewById(R.id.topBar);
		int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		topBar.measure(width, height);
		height = topBar.getMeasuredHeight();
		return 0;
	}

	/** 获取状态栏高度 **/
	protected int getStatusHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			LogWrapper.e("tag","get status bar height failure!");
			e1.printStackTrace();
		}
		return sbar;
	}

	protected int getTopHeight() {
		return getTopBarHeight() + getStatusHeight();
	}

	protected int getScreenWidth() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

	protected int getScreenHeight() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getHeight();
	}
}
