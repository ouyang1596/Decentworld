/**
 * 
 */
package cn.sx.decentworld.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @ClassName: ToastUtil.java
 * @Description:
 * @author: yj
 * @date: 2016年1月15日 下午5:59:03
 */
public class ToastUtil {
	private static Toast mToast;

	public static void initToast(Context context) {
		if (mToast == null) {
			mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
	}

	public static void showToast(String text) {
		if (null == mToast) {
			return;
		}
		mToast.setText(text);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}
}
