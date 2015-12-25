/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: LoginUser.java
 * @Description: 记录多用户的登录状态
 * @author: cj
 * @date: 2015年9月14日 上午9:14:19
 */
@Table(name = "userLogin")
public class UserLogin extends Model
{
	/**
	 * 用户的dwID
	 */
	@Column(name = "dwID")
	public String dwID;

	/**
	 * 用户名（目前为电话号码）
	 */
	@Column(name = "username")
	public String username;

	/**
	 * 密码
	 */
	@Column(name = "password")
	public String password;

	/**
	 * 优先级 1 代表目前登录的 用户(记录 个数为0/1) 2 代表最近登录的用户，目前已经退出 3 曾经登录过的用户
	 */
	@Column(name = "priority")
	public int priority;

	public UserLogin()
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
	 * @param dwID
	 *            the dwID to set
	 */
	public void setDwID(String dwID)
	{
		this.dwID = dwID;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the priority
	 */
	public int getPriority()
	{
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	/**
	 * @param dwID
	 * @param username
	 * @param password
	 * @param priority
	 */
	public UserLogin(String dwID, String username, String password, int priority)
	{
		super();
		this.dwID = dwID;
		this.username = username;
		this.password = password;
		this.priority = priority;
	}

	/**
	 * 清空登录表
	 * 
	 * @return
	 */
	public static void deleteAll()
	{
		new Delete().from(UserLogin.class).execute();
	}

	/**
	 * 查询数据库是否有记录，若返回 0 ，则代表数据库为空
	 * 
	 * @return
	 */
	public static List<UserLogin> queryAll()
	{
		return new Select().from(UserLogin.class).execute();
	}

	/**
	 * 当前是否有用户登录（priority == 1） 如果没有 则返回 "" 如果当前有用户 则返回用户的dwID
	 * 
	 * @return
	 * 
	 */
	public static String hasUserLogin()
	{
		List<UserLogin> users = new Select().from(UserLogin.class).execute();
		if (users.size() > 0)
		{
			for (UserLogin user : users)
			{
				if (user.getPriority() == 1)
				{
					return user.getDwID();
				}
			}
		}
		return "";
	}

	/**
	 * 是否有最近的用户登录（priority == 2） 如果没有 则返回 "" 如果当前有用户 则返回用户的dwID
	 * 
	 * @return
	 * 
	 */
	public static String hasLastLogin()
	{
		List<UserLogin> users = new Select().from(UserLogin.class).execute();
		if (users.size() > 0)
		{
			for (UserLogin user : users)
			{
				if (user.getPriority() == 2)
				{
					return user.getDwID();
				}
			}
		}
		return "";
	}

	/**
	 * 查询指定dwID号的记录并返回结果
	 * 
	 * @param dwID
	 */
	public static UserLogin queryByDwID(String dwID)
	{
		return new Select().from(UserLogin.class).where("dwID=?", dwID).executeSingle();
	}

}
