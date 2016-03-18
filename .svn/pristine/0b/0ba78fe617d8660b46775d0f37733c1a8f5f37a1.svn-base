/**
 * 
 */
package cn.sx.decentworld.utils;

import android.content.Context;
import android.content.SharedPreferences;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;

/**
 * @ClassName: LoginHelper.java
 * @Description:
 * @author: cj
 * @date: 2015年11月10日 下午10:49:53
 */
public class LoginHelper
{
	private static final String TAG = "LoginHelper";

    //	private static String token = "";
    //	private static String dwID = "";
    //	private static String username = "";
    //	private static String password = "";

	/**
	 * 判断用户是否登录
	 * 
	 * @param context
	 * @return true代表登录过，四个参数可以从内存中获取，否则没有登录过；
	 */
	public static boolean isLogined(Context context)
	{
	    //暂时屏蔽这段代码，个人感觉这段代码没有太大作用，因为内存中有数据的时候，sharedPreference一定会有。没有的时候，sharedPreference也会有。增加逻辑判断
	    //所以暂时屏蔽  add by Frewen.Wong
		// 1.判断Application中是否有我们需要的四个参数（token、dwID、username、password），有则返回true,否则接着判断
        //		token = DecentWorldApp.getInstance().getToken();
        //		dwID = DecentWorldApp.getInstance().getDwID();
        //		username = DecentWorldApp.getInstance().getUserName();
        //		password = DecentWorldApp.getInstance().getPassword();
        //		if (CommUtil.isNotBlank(token) && CommUtil.isNotBlank(dwID) && CommUtil.isNotBlank(username) && CommUtil.isNotBlank(password))
        //		{
        //			return true;
        //		}
	    
		// 2.判断SharedPreference中是否有登录信息，有则返回true,否则返回false;(注意登出后一定要销毁掉SharedPre中的数据)
		SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
		String token = preferences.getString(Constants.TOKEN, "");
		String dwID = preferences.getString(Constants.DW_ID, "");
		String username = preferences.getString(Constants.USERNAME, "");
		String password = preferences.getString(Constants.PASSWORD, "");
		if (CommUtil.isNotBlank(token) && CommUtil.isNotBlank(dwID) && CommUtil.isNotBlank(username) && CommUtil.isNotBlank(password))
		{
		    //登录成功之后，在内存中存储一份用户信息数据，注销的时候注意，销毁。
			DecentWorldApp.getInstance().setToken(token);
			DecentWorldApp.getInstance().setDwID(dwID);
			DecentWorldApp.getInstance().setUserName(username);
			DecentWorldApp.getInstance().setPassword(password);
			return true;
		}
		// 3.如果在内存和xml文件中都没有用户登录信息，则返回false
		return false;
	}

	/**
	 * 获取用户名
	 * 
	 * @param context
	 * @return 存在则返回对应的用户名，没有则返回"";
	 */
	public static String getUsername(Context context)
	{
		SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
		String username = preferences.getString(Constants.USERNAME, "");
		if (CommUtil.isNotBlank(username))
		{
			return username;
		}
		return "";
	}

	/**
	 * 获取返回码
	 */
	public static String getResultCode(Context context)
	{
		SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
		String resultCode = preferences.getString(Constants.RESULT_CODE, "");
		if (CommUtil.isNotBlank(resultCode))
		{
			return resultCode;
		}
		return "";
	}

	/**
	 * 登录成功后将用户的信息保存到SharedPre中
	 * 
	 * @param context
	 * @param token
	 *            令牌
	 * @param dwID
	 *            dwID
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public static void saveLoginInfo(Context context, String token, String dwID, String username, String password)
	{
		SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.TOKEN, token);
		editor.putString(Constants.DW_ID, dwID);
		editor.putString(Constants.USERNAME, username);
		editor.putString(Constants.PASSWORD, password);
		editor.commit();
	}

	/**
	 *用户进行注销的时候  清楚用户登录信息
	 * 
	 * @param context
	 */
	public static void clearLoginInfo(Context context)
	{
		// 清除SharedPre中的数据
		SharedPreferences preferences = context.getSharedPreferences(Constants.SETTING, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.TOKEN, "");
		editor.putString(Constants.DW_ID, "");
		editor.putString(Constants.USERNAME, "");
		editor.putString(Constants.PASSWORD, "");
		editor.commit();
		// 清除内存中的数据
		DecentWorldApp.getInstance().setDwID("");
		DecentWorldApp.getInstance().setToken("");
		DecentWorldApp.getInstance().setUserName("");
		DecentWorldApp.getInstance().setPassword("");
	}

}
