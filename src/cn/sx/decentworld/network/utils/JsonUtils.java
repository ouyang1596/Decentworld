/**
 * 
 */
package cn.sx.decentworld.network.utils;

import java.util.ArrayList;
import java.util.List;

import cn.sx.decentworld.network.entity.ResultBean;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: JsonUtil.java
 * @Description: json工具类
 * @author: yj
 * @date: 2015-7-26 下午8:14:27
 */
public class JsonUtils
{
	/**
	 * 
	 * 将bean转换成json串，bean中可以包含自定义属性，<br>
	 * 转换后的json串是有层次结构的<br>
	 * Date类型的将被按字符串赋给json串<br>
	 * 
	 * @param bean
	 * @return json串
	 */
	public static String bean2json(Object bean)
	{
		String jsonStr = JSON.toJSONString(bean);
		return jsonStr;
	}

	// /**
	// * 将具有JSON字符串的转换成指定的bean集合
	// * 简单Bean
	// * @param json
	// * @param type 简单bean
	// * @return
	// */
	// public static <T> List<?> json2BeanArray(String json,Class<?> type) {
	//
	// return JSON.parseArray(json, type);
	// }
	/**
	 * 将具有JSON字符串的转换成指定的bean集合 简单Bean
	 * 
	 * @param json
	 * @param type
	 *            简单bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<?> json2BeanArray(String json, Class<?> type)
	{
		try
		{
			(json + "").replace("null", "");
		}
		catch (Exception e)
		{
		}

		if (json.indexOf("[") == -1)
		{
			List<T> list = new ArrayList<T>();
			T o = (T) JSON.parseObject(json, type);
			list.add(o);
			return list;
		}
		else
		{
			return JSON.parseArray(json, type);
		}
	}

	String data = "{\"record\":\"{\"card\":\"350681199002242532\",\"classes\":\"3班\",\"department\":\"艺术\",\"ifStudent\":\"1\",\"material\":\"\",\"nick\":\"\",\"realName\":\"ou\",\"school\":\"清华大学\",\"studentCard\":\"F:\\\\Sam\\\\projects\\\\Decent-World-Server\\\\src\\\\main\\\\webapp\\\\img\\\\type\\\\151012094948Screenshot.png\",\"tel\":\"15060428393\",\"type\":\"2\",\"url\":\"192.168.1.123:8080/DecentWorldServer/img/type/151012095308Screenshot.png\"}\"}";

	/**
	 * 将具有JSON字符串的转换成指定的bean 简单Bean
	 * 
	 * @param json
	 * @param type
	 *            简单bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2Bean(String json, Class<?> type)
	{
		return (T) JSON.parseObject(json, type);
	}

	public static ResultBean getResultMsgFromJson(String json)
	{
		ResultBean msg = null;
		try
		{
			msg = json2Bean(json, ResultBean.class);
			if (msg == null)
			{
				msg = new ResultBean();
			}
		}
		catch (Exception e)
		{
			msg = new ResultBean();
		}

		return msg;
	}
}
