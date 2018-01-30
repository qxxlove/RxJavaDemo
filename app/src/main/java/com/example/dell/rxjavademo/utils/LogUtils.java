package com.example.dell.rxjavademo.utils;

import android.util.Log;

/**
 * @ClassName LogUtils
 * @Description TODO(log日志打印类)
 * @author txb
 * @Date 2016年12月8日 下午1:51:29
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class LogUtils {

	private LogUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	public static boolean isSaveLog = false;// 是否需要将Log保存到本地数据库中
	private static final String TAG = "bool";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		LogUtils.i(TAG, msg);
	}

	public static void d(String msg) {
		LogUtils.d(TAG, msg);
	}

	public static void e(String msg) {
		LogUtils.e(TAG, msg);
	}

	public static void v(String msg) {
		LogUtils.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(Class cls, String msg) {
		LogUtils.i(cls.getSimpleName(), msg);
	}

	public static void d(Class cls, String msg) {
		LogUtils.d(cls.getSimpleName(), msg);
	}

	public static void e(Class cls, String msg) {
		LogUtils.e(cls.getSimpleName(), msg);
	}

	public static void v(Class cls, String msg) {
		LogUtils.v(cls.getSimpleName(), msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (isDebug) {
			Log.v(tag, msg);
		}
	}

}