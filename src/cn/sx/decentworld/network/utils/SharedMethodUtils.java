package cn.sx.decentworld.network.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @ClassName: SharedMethod.java
 * @Description: 本地保存工具类
 * @author: yj
 * @date: 2015-7-26 下午8:24:02
 */
public class SharedMethodUtils {

	public SharedMethodUtils() {
	}

	/**
	 * 存储单个键值对
	 * @param context  上下文
	 * @param fileName  文件名
	 * @param key       
	 * @param value
	 */
	public void setStringSharedValue(Context context, String fileName,
			String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();

	}

	/**
	 * 获取单个值
	 * @param context
	 * @param fileName
	 * @param key
	 * @return
	 */
	public String getStringSharedValue(Context context, String fileName,
			String key) {
		String stringValue = null;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);
		stringValue = sharedPreferences.getString(key, null);
		return stringValue;

	}

	/**
	 * 保存单个整形数据
	 * @param context
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public void setIntegerSharedValue(Context context, String fileName,
			String key, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public Integer getIntegerSharedValue(Context context, String fileName,
			String key) {
		int intValue = 0;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);
		intValue = sharedPreferences.getInt(key, -1);
		return intValue;

	}

	public void setBooleanSharedValue(Context context, String fileName,
			String key, Boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public Boolean getBooleanSharedValue(Context context, String fileName,
			String key) {
		Boolean booleanValue = false;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);
		booleanValue = sharedPreferences.getBoolean(key, false);
		return booleanValue;
	}

	public void savePictureValue(Context context, List<String> picList) {
		SharedPreferences shared = context.getSharedPreferences("PictureValue",
				0);
		SharedPreferences.Editor editor = shared.edit();
		editor.putInt("Statue_size", picList.size());
		for (int i = 0; i < picList.size(); i++) {
			editor.putString("Statue_" + i, picList.get(i));
		}
		editor.commit();
	}

	public List<String> getPictureValue(Context context) {
		List<String> picList = new ArrayList<String>();
		SharedPreferences shared = context.getSharedPreferences("PictureValue",
				0);
		int size = shared.getInt("Statue_size", 0);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				String path = shared.getString("Statue_" + i, null);
				picList.add(path);
			}
		}
		return picList;

	}

	public void saveSearchHistory(Context context, List<String> valueList) {
		SharedPreferences shared = context.getSharedPreferences(
				"Search_history", Activity.MODE_PRIVATE);
		Editor editor = shared.edit();
		int size = valueList.size();
		editor.putInt("history_num", size);
		for (int i = 0; i < size; i++) {
			String value = valueList.get(i);
			editor.putString("num_" + i, value);
		}
		editor.commit();
	}

	public List<String> getSearchHistory(Context context) {
		List<String> hList = new ArrayList<String>();
		SharedPreferences shared = context.getSharedPreferences(
				"Search_history", 0);
		int size = shared.getInt("history_num", 0);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				String value = shared.getString("num_" + i, null);
				hList.add(value);
			}
		}
		return hList;
	}

	public void removeSharedValue(Context context, String fileName,
			String valueName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, 0);
		Editor editor = sharedPreferences.edit();
		editor.remove(valueName);
		editor.commit();

	}

	public void clearSharedValue(Context context, String fileName) {
		SharedPreferences shared = context.getSharedPreferences(fileName, 0);
		SharedPreferences.Editor editor = shared.edit();
		editor.clear();
		editor.commit();
	}

}
