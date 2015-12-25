/**
 * 
 */
package cn.sx.decentworld.bean;

import cn.sx.decentworld.common.CommUtil;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: OccupantList.java
 * @Description: 聊天室成员
 * @author: cj
 * @date: 2015年10月27日 下午6:02:54
 */
@Table(name = "occupantList")
public class OccupantList extends Model
{
	/**
	 * 聊天室的ID（用于标记该成员属于哪个聊天室）
	 */
	@Column(name = "roomID")
	private String roomID;
	
	/**
	 * 成员ID
	 */
	@Column(name = "dwID")
	private String dwID;
	
	
	/**
	 * 成员头像
	 */
	@Column(name = "userIcon")
	private String userIcon;
	
	/**
	 * 成员昵称
	 */
	@Column(name = "userNickName")
	private String userNickName;
	
	
	/**
	 * 成员身价
	 */
	@Column(name = "worth")
	private String worth;
	
	
	/**
	 * 成员是否被禁言(1 表示禁言，0 表示没有被禁言)
	 */
	@Column(name = "block")
	private boolean block;
	
	/**
	 * 
	 */
	public OccupantList()
	{
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
	 * @return the userIcon
	 */
	public String getUserIcon()
	{
		return userIcon;
	}

	/**
	 * @param userIcon the userIcon to set
	 */
	public void setUserIcon(String userIcon)
	{
		this.userIcon = userIcon;
	}

	/**
	 * @return the userNickName
	 */
	public String getUserNickName()
	{
		return userNickName;
	}

	/**
	 * @param userNickName the userNickName to set
	 */
	public void setUserNickName(String userNickName)
	{
		this.userNickName = userNickName;
	}

	/**
	 * @return the worth
	 */
	public String getWorth()
	{
		return worth;
	}
	
	

	/**
	 * @return the roomID
	 */
	public String getRoomID()
	{
		return roomID;
	}

	/**
	 * @param roomID the roomID to set
	 */
	public void setRoomID(String roomID)
	{
		this.roomID = roomID;
	}

	/**
	 * @param worth the worth to set
	 */
	public void setWorth(String worth)
	{
		this.worth = worth;
	}
	
	
	
	


	/**
	 * @return the block
	 */
	public boolean isBlock()
	{
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(boolean block)
	{
		this.block = block;
	}

	/**
	 * 通过dwID查找对应聊天室成员的头像
	 * @param dwID
	 * @return
	 */
	public static String getIconByDwID(String dwID)
	{
		OccupantList list = new Select().from(OccupantList.class).where("dwID=?",dwID).executeSingle();
		if(list!=null)
		{
			if(CommUtil.isNotBlank(list.getUserIcon()))
			{
				return list.getUserIcon();
			}
		}
		return "";
	}
	
	/**
	 * 通过dwID查找对应聊天室成员的身价
	 * @param dwID
	 * @return
	 */
	public static String getWorthByDwID(String dwID)
	{
		OccupantList list = new Select().from(OccupantList.class).where("dwID=?",dwID).executeSingle();
		if(list!=null)
		{
			if(CommUtil.isNotBlank(list.getWorth()))
			{
				return list.getWorth();
			}
		}
		return "";
	}
	
	/**
	 * 通过dwID查找对应聊天室成员的名字
	 * @param dwID
	 * @return
	 */
	public static String getNicknameByDwID(String dwID)
	{
		OccupantList list = new Select().from(OccupantList.class).where("dwID=?",dwID).executeSingle();
		if(list!=null)
		{
			if(CommUtil.isNotBlank(list.getUserNickName()))
			{
				return list.getUserNickName();
			}
		}
		return "";
	}
	
	
	/**
	 * 通过dwID查找对应聊天室成员是否禁言
	 * @param dwID
	 * @return
	 */
	public static String getIsBlockByDwID(String dwID)
	{
		OccupantList list = new Select().from(OccupantList.class).where("dwID=?",dwID).executeSingle();
		if(list!=null)
		{
			if(list.isBlock())
			{
				return "1";
			}
			else
			{
				return "0";
			}
		}
		return "";
	}
	

	/**
	 * 删除所有聊天室成员
	 */
	public static void deleteAll()
	{
		new Delete().from(OccupantList.class).execute();
	}
	
	/**
	 * 查询记录
	 * @param dwID
	 * @return
	 */
	public static OccupantList queryByDwID(String dwID)
	{
		return new Select().from(OccupantList.class).where("dwID=?",dwID).executeSingle();
	}

}
