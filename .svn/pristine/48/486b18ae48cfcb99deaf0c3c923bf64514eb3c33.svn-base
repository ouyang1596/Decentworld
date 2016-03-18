/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

/**
 * @ClassName: MyProtege.java
 * @Description: 我是谁的贵人（徒弟）
 * @author: cj
 * @date: 2015年9月25日 上午11:11:51
 */

@Table(name = "myProteges")
public class MyProtege extends Model
{
	/**
	 * 头像
	 */
	@Column(name = "icon")
	String icon;
	/**
	 * 昵称
	 */
	@Column(name = "nickName")
	String nickName;
	/**
	 * 对方ID
	 */
	@Column(name = "dwID")
	String dwID;
	
	public MyProtege()
	{
		super();

	}
	
	
	
	
	
	/**
	 * @param icon
	 * @param nickNmae
	 * @param dwID
	 */
	public MyProtege(String icon, String nickName, String dwID)
	{
		super();
		this.icon = icon;
		this.nickName = nickName;
		this.dwID = dwID;
	}





	/**
	 * @return the icon
	 */
	public String getIcon()
	{
		return icon;
	}





	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon)
	{
		this.icon = icon;
	}


	/**
	 * @return the nickName
	 */
	public String getNickName()
	{
		return nickName;
	}





	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}





	/**
	 * @return the dwID
	 */
	public String getDwID()
	{
		return dwID;
	}





	/**
	 * @param dwID the dwID to set
	 */
	public void setDwID(String dwID)
	{
		this.dwID = dwID;
	}





	/**
	 * 删除消息列表
	 */
	public static void deleteAll()
	{
		new Delete().from(MyProtege.class).execute();
	}
	
	
}
