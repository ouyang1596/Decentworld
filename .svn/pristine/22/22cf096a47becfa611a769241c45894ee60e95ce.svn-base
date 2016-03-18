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
 * @Description: 记录用户的登录状态
 * @author: cj
 * @date: 2015年9月14日 上午9:14:19
 */
@Table(name = "loginUser")
public class LoginUser extends Model
{
	@Column(name = "dwID")
	public String dwID;
	
	@Column(name = "username")
	public String username;
	
	@Column(name = "password")
	public String password;
	
	@Column(name = "isLogined")
	public int isLogined;

	public LoginUser()
	{
		
	}
	
	
	/**
	 * @param dwID
	 * @param username
	 * @param password
	 * @param isLogined
	 */
	public LoginUser(String dwID, String username, String password,
			int isLogined)
	{
		super();
		this.dwID = dwID;
		this.username = username;
		this.password = password;
		this.isLogined = isLogined;
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
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
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
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the isLogined
	 */
	public int getIsLogined()
	{
		return isLogined;
	}

	/**
	 * @param isLogined the isLogined to set
	 */
	public void setIsLogined(int isLogined)
	{
		this.isLogined = isLogined;
	}
	/**
	 * 清空登录表
	 * @return
	 */
	public static void deleteAll() 
	{
		new Delete().from(LoginUser.class).execute();
	}
	
	public static List<LoginUser> queryAll()
	{
		return new Select().from(LoginUser.class).execute();
	}
	
	

}
