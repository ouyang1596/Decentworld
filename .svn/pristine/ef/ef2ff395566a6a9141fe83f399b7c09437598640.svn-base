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
 * @ClassName: Trample.java
 * @Description: 
 * @author: dyq
 * @date: 2015年9月10日 下午6:42:18
 */
@Table(name = "trample")
public class Trample extends Model implements Serializable{
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "time")
	private String time;
	

	public Trample()
	{
		
	}
	public Trample(String avatar, String nickname, String time)
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
	public static void save(Trample t){
		Trample trample = new Trample();
		trample.avatar = t.avatar;
		trample.nickname = t.nickname;
		trample.time = t.time;
		
		trample.save();
	}

	public static List<Trample> queryAllList() {
		return new Select().from(Trample.class).execute();
	}
	
	public static void upDateSingle(Trample trample){
		trample.save();
	}
	
	public static void deleteAll(){
		new Delete().from(Trample.class).execute();
	}
}
