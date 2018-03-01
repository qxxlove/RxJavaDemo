package com.example.dell.rxjavademo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
	private static App application;
	private static Context context;
	private List<Activity> _Activitys = new ArrayList<Activity>();

	private  List<Activity> activitiesTwo = new ArrayList<>();

	public static Context getAppContext() {
		return App.context;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		App.context = getApplicationContext();
		registerActivityLifecycleCallbacks(activityLifecycleCallbacks);


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


	public  int  getActivitySize (){
		 return  activitiesTwo.size();
	}


	private  ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
			activitiesTwo.add(activity);
		}

		@Override
		public void onActivityStarted(Activity activity) {

		}

		@Override
		public void onActivityResumed(Activity activity) {

		}

		@Override
		public void onActivityPaused(Activity activity) {

		}

		@Override
		public void onActivityStopped(Activity activity) {

		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			  activitiesTwo.remove(activity);
		}
	};

}
