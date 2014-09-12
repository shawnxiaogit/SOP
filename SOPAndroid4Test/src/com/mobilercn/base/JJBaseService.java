package com.mobilercn.base;

import java.util.ArrayList;

import java.util.Hashtable;

import com.mobilercn.config.D2EConfigures;
import com.mobilercn.http.JJAsyncHttpTask;
import com.mobilercn.task.JJAsyncTaskManager;
import com.mobilercn.task.JJTask;
import com.mobilercn.task.JJTaskType;
import com.mobilercn.webservice.JJWebServiceTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class JJBaseService extends Service {
	public static final String BOUNDARY = "---------------------------7db1c523809b2";

	public static final Integer HTTP_SERVICE = new Integer(-1);
	public static final Integer BT_SERVICE = new Integer(-2);
	public static final int HTTP_SERVICE_INT = -1;
	public static final int BT_SERVICE_INT = -2;

	public static ArrayList<JJBaseActivity> g_allActivity = new ArrayList<JJBaseActivity>();
	public static boolean g_runState = false;
	public boolean mIsRun = false;
	public static ArrayList<Long> g_cancelTaskList = new ArrayList<Long>();

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mIsRun = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mIsRun = false;
	}

	public static synchronized void addCancelTask(long l) {
		g_cancelTaskList.add(new Long(l));
	}

	public static synchronized boolean isCancelTask(long l) {
		boolean result = false;
		Long tmp = new Long(l);
		if (g_cancelTaskList.contains(tmp)) {
			result = true;
			g_cancelTaskList.remove(tmp);
		}
		return result;
	}

	// 根据Activity的名字得到对应的Activity
	public static JJBaseActivity getActivityByName(String activityName) {
		for (JJBaseActivity activity : g_allActivity) {
			if (activity.getClass().getName().indexOf(activityName) >= 0) {
				return activity;
			}
		}
		return null;
	}

	// post http task
	public static long addHttpTask(String url, String body, String activity,
			int type) {
		long id = System.currentTimeMillis();
		JJAsyncHttpTask task = new JJAsyncHttpTask(url, 10000, false, mHandler);
		task.setTimeID(id);
		task.setTaskId(type);
		try {
			task.setHttpTaskBody(body.getBytes("UTF-8"));
		} catch (Exception ex) {
			task.setHttpTaskBody(body.getBytes());
		}
		task.setActivityName(activity);
		JJAsyncTaskManager.getInstance().doHttpTask(task);
		return id;
	}

	public static long addMutilpartHttpTask(String url, String[] params,
			String activity, int type) {
		long id = System.currentTimeMillis();
		JJAsyncHttpTask task = new JJAsyncHttpTask(url, 300000, false, mHandler);
		task.setTimeID(id);
		task.setTaskId(type);
		task.setPostBoundary(BOUNDARY);
		task.setMultipartHttpTaskBody(params);
		task.setActivityName(activity);
		JJAsyncTaskManager.getInstance().doHttpTask(task);
		return id;
	}

	// post webservice task
	public static long addWebServiceTask(String URL, String method,
			String action, String nameSpace, Hashtable<String, String> params,
			String activity, int type) {
		long id = System.currentTimeMillis();
		JJWebServiceTask task = new JJWebServiceTask(false, URL, method,
				action, nameSpace, mHandler);
		task.setParam(params);
		task.setTimeID(id);
		task.setTaskId(type);
		task.setActivityName(activity);
		if (D2EConfigures.TEST) {
			Log.i("addWebServiceTask   >>>>>>>> ", "" + task.getTaskId());
		}
		JJAsyncTaskManager.getInstance().doWebServiceTask(task);
		return id;
	}

	public static final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			JJTask task = (JJTask) msg.obj;
			JJBaseActivity activity = getActivityByName(task.getActivityName());
			if (JJBaseService.isCancelTask(task.getTimeID())) {
				if (activity != null) {
					activity.taskFailed(JJBaseService.HTTP_SERVICE, task);
				}
			}
			switch (msg.what) {

			case JJTaskType.TASK_HTTP_SUCCESS: {
				if (activity != null) {
					activity.taskSuccess(JJBaseService.HTTP_SERVICE, task);
				}
			}
				break;

			case JJTaskType.TASK_HTTP_FAILED: {
				if (activity != null) {
					activity.taskFailed(JJBaseService.HTTP_SERVICE, task);
				}
			}
				break;

			}
		}
	};
}
