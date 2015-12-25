/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: UserBaseInfo.java
 * @Description: 
 * @author: cj
 * @date: 2015年8月17日 下午12:13:39
 */
public class BasicUserInfo
{
	private String avatar;//用户头像
	private String sign;//个性签名
	private String nickname;//昵称
	private String realname;//实名
	private String age;//年龄
	private float value;//身价
	private float asset;//身家
	
	public BasicUserInfo()
	{
		
	}
	public BasicUserInfo(String avatar, String sign, String nickname, String realname, String age, float value, float asset)
	{
		super();
		this.avatar = avatar;
		this.sign = sign;
		this.nickname = nickname;
		this.realname = realname;
		this.age = age;
		this.value = value;
		this.asset = asset;
	}
	public String getAvatar()
	{
		return avatar;
	}
	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}
	public String getSign()
	{
		return sign;
	}
	public void setSign(String sign)
	{
		this.sign = sign;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public String getRealname()
	{
		return realname;
	}
	public void setRealname(String realname)
	{
		this.realname = realname;
	}
	public String getAge()
	{
		return age;
	}
	public void setAge(String age)
	{
		this.age = age;
	}
	public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value = value;
	}
	public float getAsset()
	{
		return asset;
	}
	public void setAsset(float asset)
	{
		this.asset = asset;
	}

}
