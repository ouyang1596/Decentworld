/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: UserInfoItem.java
 * @Description: 模拟环境下的 bean 类，用于测试展示设置选项的Item，当从服务器拿取数据成功后可删除；
 * @author: cj
 * @date: 2015年10月11日 上午10:13:18
 */
public class UserInfoItem
{
	String title;
	String key;
	String value;
	int image;
	/**
	 * 
	 */
	public UserInfoItem()
	{
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param title
	 * @param key
	 * @param value
	 * @param image
	 */
	public UserInfoItem(String title, String key, String value)
	{
		super();
		this.title = title;
		this.key = key;
		this.value = value;
	}
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	/**
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key)
	{
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

}
