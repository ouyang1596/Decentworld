/**
 * 
 */
package cn.sx.decentworld.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sx.decentworld.utils.LogUtils;

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
	public Map getKeyList(Object object, String[] keys)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = object.getClass().getDeclaredMethods();
		for (String key : keys)
		{
			for (Method method : methods)
			{
				if (method.getName().toLowerCase().endsWith(key.toLowerCase()))
				{
					try
					{
//						String field = method.getName();
//						field = field.substring(field.indexOf("get") + 3);
//						field = field.toLowerCase().charAt(0) + field.substring(1);
						Object value = method.invoke(object, (Object[]) null);
						map.put(key, value == null ? "" : value.toString());

					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		for(String key:map.keySet())
		{
			LogUtils.i(TAG, "key="+key+",value="+map.get(key));
		}

		return map;
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public Map getKeyList(List<String> keys)
	{
		return null;
	}

}
