/**
 * 
 */
package cn.sx.decentworld.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: SettingSp.java
 * @Description: 保存设置的相关变量
 * @author: cj
 * @date: 2015年12月16日 下午8:06:38
 */
public class SettingSp
{
	public static final String TAG = "SettingSp";
	public static final long updateInterval= 1000*60*60*24*2;//当用户取消更新后，隔断时间再次进行提醒2天
	
	/**
	 * 是否检查版本更新
	 * @param context
	 * @return
	 */
	public static boolean isUpdate(Context context)
	{
		SharedPreferences preferences = context.getSharedPreferences("globalSetting", 0);
		long oldTime = preferences.getLong("oldTime", -1);
		if(oldTime == -1)
		{
			SharedPreferences.Editor editor = preferences.edit();
			editor.putLong("oldTime", System.currentTimeMillis());
			editor.commit();
			return true;
		}
		if(System.currentTimeMillis() - oldTime>updateInterval)
		{
			SharedPreferences.Editor editor = preferences.edit();
			editor.putLong("oldTime", System.currentTimeMillis());
			editor.commit();
			return true;
		} 
		return false;
	}

}
