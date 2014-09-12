package com.mobilercn.config;

public class D2EConfigures {
	public static final boolean TEST=false;
	public static final int MONITOR_INIT = 2;
	public static final int MONITOR_CHECK = 3;
	
	public static  boolean ISREPLACE=false;
	/**
	 * 没有蓝牙
	 */
	public static boolean NO_BLUTH=true;
	/**
	 * 没有网络
	 */
	public static boolean NO_NET=true;
	
	public static final int MONITOR_INIT_DATA = 1;
	//测试改为Mark
	public static final int MONITOR_CHECK_DATA = 2;
	
	public static final int MONITOR_MOUSE = 1;
	public static final int MONITOR_ROACH = 2;
	
	public static final boolean G_DEBUG = false;
	public static final boolean G_USEVIRCALDEVICE = false;
	public static final boolean G_NOTDONE = false;
	
	public static final String  DONE_VALUE = "-999";
	public static final String  ADD_VALUE  = "-998";
	public static final int     WEATHER_COUNT = 6;
	
	// ==========控制grid背景=======
	public static final int GRID_CUSTOM       = 1;
	public static final int GRID_LOCATION     = 2;
	public static final int GRID_CHECK        = 3;
	public static final int GRID_DEL_LOCATION = 4;
	public static final int GRID_DEL_CHECK    = 5;
	public static final int GRID_DOCUMENT     = 6;
	
	
	
	// ==========task type========
	public static final int TASK_LOGIN              = 1; //登录
	public static final int TASK_GET_CUSTOM_INFO    = 2; //获取客户信息
	public static final int TASK_CHECKCODE          = 3;//验证码
	public static final int TASK_REFERSHMSG         = 4;//刷新消息
	
	public static final int TASK_GETWORKSHEET       = 5;//未来几天内工单情况
	public static final int TASK_GETLOCATIONBYCUS   = 6;//根据客户id获取位置点信息
	public static final int TASK_ADDLOCATION        = 7;//新增位置标签
	public static final int TASK_GETCHECKBYLOCATION = 8;//取给定位置标签下的所有检查点信息
	public static final int TASK_ADDCHECK           = 9;//增加检查点标签
	public static final int TASK_SUBMIT             = 10;//提交数据
	
	public static final int TASK_GETCUSTOMERBYDATE  = 11;//某天服务的所有客户
	public static final int TASK_GETWORKSHEETBYDATE = 12;//某天服务的某一客户的工单
	
	public static final int TASK_GETCUSTOMERSBYUSER = 13;//获取用户下的所有客户
	public static final int TASK_GETREPORTS         = 14;//获取某天某一客户的报表
	
	public static final int TASK_DELITEM            = 15;//删除标签点
	
	public static final int TASK_CHANGETAG          = 16;//更新标签
	
	public static final int TASK_CHECKIN            = 17;//签到
	
	public static final int TASK_GETSITE            = 18;//获取文档
	
	public static final int TASK_SEND_PROBLEM       = 19;//提交问题
	
	public static final int TASK_GET_POINT_BY_PIONT_ID=20;//员工卡丢失情况，通过ID来获取
	
	public static final int TASK_GETCHECKBYLOCATION_TWO = 21;//重新获取作业点
	
	public static final int TASK_CHECK_TAG = 22;//判断卡片是否被使用过了
	
	public static final int TASK_READ_MSG=23;
	
	public static final int TASK_INSERT_WATCH_DATA=24;//插入勘查模块数据
	
	public static final int TASK_GET_ALL_CUSTOM=25;//获取所有客户信息
	
	
}
