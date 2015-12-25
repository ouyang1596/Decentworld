/**
 * 
 */
package cn.sx.decentworld.bean.manager;

import cn.sx.decentworld.bean.DWMessage;

import com.activeandroid.query.Select;

/**
 * @ClassName: DWMMessageManager.java
 * @Description: 跨表检索消息管理器
 * @author: cj
 * @date: 2015年11月22日 下午6:43:29
 */
public class DWMessageManager
{
	private static final String TAG = "DWMessageManager";
	
	/**
	 * 
	 */
	private DWMessageManager()
	{
		// TODO Auto-generated constructor stub
	}
	/**
	 * 根据文本消息的txtMsgID搜索唯一的DWMessage
	 * 
	 * @param txtMsgID
	 * @return
	 */
	public static DWMessage queryItem(String txtMsgID) {
		return new Select().from(DWMessage.class)
				.where("txtMsgID = ?", txtMsgID).executeSingle();
	}
	
}
