/**
 * 
 */
package cn.sx.decentworld.utils;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * @ClassName: TextUtils.java
 * @Description:
 * @author: yj
 * @date: 2015年11月22日 下午2:25:01
 */
public class TextUtil {
	/**
	 * 实现文本复制功能 add by wangqianzhou
	 * 
	 * @param content
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/**
	 * 实现粘贴功能 add by wangqianzhou
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}
}
