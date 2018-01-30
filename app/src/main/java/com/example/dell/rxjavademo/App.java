package com.example.dell.rxjavademo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
	private static App application;
	private static Context context;
	private List<Activity> _Activitys = new ArrayList<Activity>();
	public static Context getAppContext() {
		return App.context;
	}

	public App() {

	}

	@Override
	public void onCreate() {
		super.onCreate();
		App.context = getApplicationContext();
		// LogWrapper.d("tag", "ApplicationActivity");

	}
	public List<Activity> getActivitys() {
		return _Activitys;
	}

	public void putActivity(Activity _Act) {
		_Activitys.add(_Act);
	}

	public void removeActivity(Activity _Act) {
		_Activitys.remove(_Act);
	}

	public void clearActivity() {
		_Activitys.clear();
	}
	
	public void exit(){
		for(Activity _activity:_Activitys){
			_activity.finish();
		}
	}
}
