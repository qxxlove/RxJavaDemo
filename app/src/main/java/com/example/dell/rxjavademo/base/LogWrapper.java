package com.example.dell.rxjavademo.base;



import android.util.Log;

/**
 * @author ��־��װ��
 * 
 */
public class LogWrapper {
	/**
	 * ������־�Ŀ���
	 */
	static boolean DEBUG = true ;

	/**
	 * Debug��־
	 * 
	 * @param tag
	 *            ��־��tag
	 * @param msg
	 *            ��־��msg
	 */
	public static void d(String tag, String msg) {
		if (DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
		}
	}

}
