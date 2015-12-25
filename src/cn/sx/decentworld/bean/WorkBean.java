/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: WorkBean.java
 * @Description:
 * @author: yj
 * @date: 2015年7月21日 上午10:05:44
 */

public class WorkBean implements Serializable
{
	private static final long serialVersionUID = 3640888606505787460L;
	//头像
	private String avatar;
	//昵称
	private String nickname;
	//时间
	private String time;
	//作品描述
	private String description;
	//图片
	private List<String> urls;
	//赞
	private ArrayList<Praise> praises;
	private int good;
	//踩
	private ArrayList<Praise> tramples;
	private int bad;
	//评论
	private ArrayList<Comment> comments;

	/**
	 * @return the urls
	 */
	public List<String> getUrls()
	{
		return urls;
	}

	/**
	 * @param urls
	 *            the urls to set
	 */
	public void setUrls(List<String> urls)
	{
		this.urls = urls;
	}

	/**
	 * @return the comments
	 */
	public ArrayList<Comment> getComments()
	{
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(ArrayList<Comment> comments)
	{
		this.comments = comments;
	}

	/**
	 * @return the good
	 */
	public int getGood()
	{
		return good;
	}

	/**
	 * @param good
	 *            the good to set
	 */
	public void setGood(int good)
	{
		this.good = good;
	}

	/**
	 * @return the bad
	 */
	public int getBad()
	{
		return bad;
	}

	/**
	 * @param bad
	 *            the bad to set
	 */
	public void setBad(int bad)
	{
		this.bad = bad;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar()
	{
		return avatar;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname()
	{
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	/**
	 * @return the praises
	 */
	public ArrayList<Praise> getPraises()
	{
		return praises;
	}

	/**
	 * @param praises
	 *            the praises to set
	 */
	public void setPraises(ArrayList<Praise> praises)
	{
		this.praises = praises;
	}

	/**
	 * @return the tramples
	 */
	public ArrayList<Praise> getTramples()
	{
		return tramples;
	}

	/**
	 * @param tramples
	 *            the tramples to set
	 */
	public void setTramples(ArrayList<Praise> tramples)
	{
		this.tramples = tramples;
	}

}
