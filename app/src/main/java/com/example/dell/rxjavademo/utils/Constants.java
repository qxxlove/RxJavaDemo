package com.example.dell.rxjavademo.utils;

public class Constants {
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;// 拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;// 本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;// 位置
	public static final int REQUESTCODE_PHOTOSEL = 0x00004;// 相册
	public static final int RESULT_LOCATION = 0x00005;// 启动地图
	public static final int RESULT_START_ZOOM = 0x00006;// 启动裁剪界面
	public static final int REQUEST_CODE_IMAGE_SELECTOR = 0x000007;//选择图片请求码
	public static final String KEY_IMAGE_SELECTOR = "image_selector";
	//图片选择最多个数
	public static final int MAX_SELECTOR = 9;
}
