/**
 * 
 */
package cn.sx.decentworld.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * @ClassName: ToastUtils.java
 * @Description: 弹出提示信息
 * @author: cj
 * @date: 2015年11月28日 上午10:36:25
 */
public class ToastUtils
{
	/**
	 * 在主线程中弹出提示信息
	 * @param context
	 * @param msg
	 */
	public static void toast(final Context context,final String msg)
	{
		((Activity)context).runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				LogUtils.i("ii", "toast success");
			}
		});
		
	}
}
