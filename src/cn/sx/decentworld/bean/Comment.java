/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: Comment.java
 * @Description: 作品圈评论
 * @author: yj
 * @date: 2015年7月21日 上午8:51:07
 */

@Table(name = "comment")
public class Comment extends Model implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5743111662098731831L;
	//评论者的DWid
	@Column(name="c_personid")
	public String c_personid;
	
	@Column(name="c_avatar")
	public String c_avatar;
	
	@Column(name = "c_name")
	public String c_name;
	
	@Column(name = "c_content")
	public String c_content;
	
	@Column(name = "c_work")
	public Work work;
	
	//被回复的人的id
	@Column(name="c_reply_id")
	public String c_reply_id;
	
	@Column(name = "c_reply_name")
	public String c_reply_name;
	
	@Column(name = "c_isreply")
	public boolean c_isreply =false;
	
	@Column(name = "c_date")
	public String c_date;

	public String getC_date()
	{
		return c_date;
	}

	public void setC_date(String c_date)
	{
		this.c_date = c_date;
	}

	public Comment() {
		super();
	}
	
	public Comment(String c_name,String c_content){
		super();
		this.c_name = c_name;
		this.c_content = c_content;
	}

	/**
	 * @return the c_name
	 */
	public String getC_name() {
		return c_name;
	}

	/**
	 * @param c_name the c_name to set
	 */
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	/**
	 * @return the c_content
	 */
	public String getC_content() {
		return c_content;
	}

	/**
	 * @param c_content the c_content to set
	 */
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	/**
	 * @return the c_reply_name
	 */
	public String getC_reply_name() {
		return c_reply_name;
	}

	/**
	 * @param c_reply_name the c_reply_name to set
	 */
	public void setC_reply_name(String c_reply_name) {
		this.c_reply_name = c_reply_name;
	}

	/**
	 * @return the c_isreply
	 */
	public boolean isC_isreply() {
		return c_isreply;
	}

	/**
	 * @param c_isreply the c_isreply to set
	 */
	public void setC_isreply(boolean c_isreply) {
		this.c_isreply = c_isreply;
	}
	
	
	
	
	
	
	
}
