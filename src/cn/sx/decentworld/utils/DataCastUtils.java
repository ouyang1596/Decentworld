/**
 * 
 */
package cn.sx.decentworld.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataCastUtils.java
 * @Description: 数据转换工具，从数据库中拿取制定字段对应的值保存到Map中
 * @author: cj
 * @date: 2015年10月24日 上午10:13:19
 */
public class DataCastUtils
{
	private static final String TAG = "DataCastUtils";

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public static Map getKeyList(Object object, String[] keys)
	{
		boolean isExsit = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = object.getClass().getDeclaredMethods();
		for (String key : keys)
		{
			isExsit = false;
			for (Method method : methods)
			{
				if (method.getName().toLowerCase().endsWith(key.toLowerCase()))
				{
					try
					{
						Object value = method.invoke(object, (Object[]) null);
						if (value == null)
						{
							LogUtils.e(TAG, "object中对应的字段" + key + "值为空");
						} else
						{
							map.put(key, value.toString());
						}
						isExsit = true;
					} catch (Exception e)
					{
						LogUtils.e(TAG, e.toString());
					}
				}
			}
			if (!isExsit)
			{
				LogUtils.e(TAG, "object中对应的字段" + key + "不存在");
			}
		}

		return map;
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public static Map getKeyList(Object object, List<String> keys)
	{
		boolean isExsit = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = object.getClass().getDeclaredMethods();
		for (String key : keys)
		{
			isExsit = false;
			for (Method method : methods)
			{
				if (method.getName().toLowerCase().endsWith(key.toLowerCase()))
				{
					try
					{
						Object value = method.invoke(object, (Object[]) null);
						if (value == null)
						{
							LogUtils.e(TAG, "object中对应的字段" + key + "值为空");
						} else
						{
							map.put(key, value.toString());
						}
						isExsit = true;
					} catch (Exception e)
					{
						LogUtils.e(TAG, e.toString());
					}
				}
			}
			if (!isExsit)
			{
				LogUtils.e(TAG, "object中对应的字段" + key + "不存在");
			}
		}

		return map;
	}

	/**
	 * 给Object对象中由map的key指定的字段设置值
	 * @param object
	 * @param map
	 */
	public static Object setValue(Object object, HashMap<String, Object> map)
	{
		boolean isExsit = false;
		Method[] methods = object.getClass().getDeclaredMethods();
		for (String key : map.keySet())
		{
			isExsit = false;
			for (Method method : methods)
			{
				if (method.getName().toLowerCase().endsWith(key.toLowerCase()))
				{
					try
					{
						method.invoke(object, new Object[]
						{ map.get(key) });
						isExsit = true;
					} catch (Exception e)
					{
						LogUtils.e(TAG, e.toString());
					}
				}
			}
			if (!isExsit)
			{
				LogUtils.e(TAG, "object中对应的字段" + key + "不存在");
			}
		}
		return object;
	}
	
	
	

}
