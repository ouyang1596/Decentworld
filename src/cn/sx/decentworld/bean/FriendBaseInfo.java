/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: FriendInfo.java
 * @Description:
 * @author: cj
 * @date: 2015年8月25日 下午4:39:35
 */
@Table(name = "FriendBaseInfos")
public class FriendBaseInfo extends Model
{
	@Column(name = "dwID")
	String dwID;
	
	@Column(name = "icon")
	String icon;
	
	@Column(name = "sign")
	String sign;

	@Column(name = "nickName")
	String nickName;
	
	@Column(name = "age")
	String age;
	
	@Column(name = "wealth")
	String wealth;

	@Column(name = "worth")
	String worth;
	
	@Column(name = "realName")
	String realName;

	/**
	 * 无参构造函数
	 */
	public FriendBaseInfo()
	{
		
	}

	/**
	 * @param dwID
	 * @param icon
	 * @param sign
	 * @param nickName
	 * @param age
	 * @param wealth
	 * @param worth
	 * @param realName
	 */
	public FriendBaseInfo(String dwID, String icon, String sign, String nickName, String age, String wealth, String worth, String realName)
	{
		super();
		this.dwID = dwID;
		this.icon = icon;
		this.sign = sign;
		this.nickName = nickName;
		this.age = age;
		this.wealth = wealth;
		this.worth = worth;
		this.realName = realName;
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
	 * @return the sign
	 */
	public String getSign()
	{
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign)
	{
		this.sign = sign;
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
	 * @return the age
	 */
	public String getAge()
	{
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age)
	{
		this.age = age;
	}

	/**
	 * @return the wealth
	 */
	public String getWealth()
	{
		return wealth;
	}

	/**
	 * @param wealth the wealth to set
	 */
	public void setWealth(String wealth)
	{
		this.wealth = wealth;
	}

	/**
	 * @return the worth
	 */
	public String getWorth()
	{
		return worth;
	}

	/**
	 * @param worth the worth to set
	 */
	public void setWorth(String worth)
	{
		this.worth = worth;
	}

	/**
	 * @return the realName
	 */
	public String getRealName()
	{
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName)
	{
		this.realName = realName;
	}
	
	/**
	 * 查询指定dwID号的记录并返回结果
	 * 
	 * @param dwID
	 */
	public static FriendBaseInfo queryByDwID(String dwID)
	{
		return new Select().from(FriendBaseInfo.class).where("dwID=?", dwID)
				.executeSingle();
	}
	
	/**
	 * 删除朋友详情表
	 */
	public static void deleteAll()
	{
		new Delete().from(FriendBaseInfo.class).execute();
	}


	
}
