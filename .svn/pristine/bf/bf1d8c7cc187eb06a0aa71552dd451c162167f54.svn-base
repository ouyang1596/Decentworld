/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: Praise.java
 * @Description: 
 * @author: dyq
 * @date: 2015年8月12日 下午5:52:07
 */
@Table(name="praise")
public class Praise extends Model implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "time")
	private String time;
	
	
	public Praise()
	{
		
	}
	public Praise(String avatar, String nickname, String time)
	{
		super();
		this.avatar = avatar;
		this.nickname = nickname;
		this.time = time;
	}
	public String getAvatar()
	{
		return avatar;
	}
	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public static void save(Praise p){
		Praise praise = new Praise();
		praise.avatar = p.avatar;
		praise.nickname = p.nickname;
		praise.time = p.time;
		
		praise.save();
	}

	public static List<Praise> queryAllList() {
		return new Select().from(Praise.class).execute();
	}
	
	public static void upDateSingle(Praise praise){
		praise.save();
	}
	
	public static void deleteAll(){
		new Delete().from(Praise.class).execute();
	}
	

}
