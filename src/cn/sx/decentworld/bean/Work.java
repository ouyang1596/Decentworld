/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.Date;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: Work.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月21日 上午8:22:12
 */

@Table(name = "work")
public class Work extends Model{
	@Column(name = "u_name")
	public String u_name;
	
	@Column(name = "u_content")
	public String u_content;
	
	@Column(name = "u_avatar")
	public String u_avatar;
	
	@Column(name = "u_imageurls")
	public List<String> u_imageurls;
	
	@Column(name = "u_date")
	public Date u_date;
	
	@Column(name = "u_like")
	public int u_like;
	
	@Column(name = "u_dislike")
	public int u_dislike;
	
	
//	@Column(name = "u_comments")
//	public String u_comments;
	
//	@Column(name="praise")
	public List<Praise> praise;
	
	public void praises(){
		praise=getMany(Praise.class, "work");
	}
//	@Column(name="tramples")
	public List<Praise> tramples;
	
	public void tramples(){
		tramples=getMany(Trample.class, "work");		
	}
	
//	@Column(name="comments")
	public List<Comment> comments;
	
	public void comments(){
		comments = getMany(Comment.class, "c_work");
	}
	
	
	public Work() {
		super();
	}
	
	
	/**
 * @param u_name
 * @param u_content
 * @param u_avatar
 * @param u_imageurls
 * @param u_date
 * @param u_like
 * @param u_dislike
 * @param praise
 * @param tramples
 * @param comments
 */
public Work(String u_name, String u_content, String u_avatar,
		List<String> u_imageurls, Date u_date, int u_like, int u_dislike,
		List<Praise> praise, List<Praise> tramples, List<Comment> comments) {
	super();
	this.u_name = u_name;
	this.u_content = u_content;
	this.u_avatar = u_avatar;
	this.u_imageurls = u_imageurls;
	this.u_date = u_date;
	this.u_like = u_like;
	this.u_dislike = u_dislike;
	this.praise = praise;
	this.tramples = tramples;
	this.comments = comments;
}


	public static void save(Work w){
		Work work = new Work();
		work.u_name = w.u_name;
		work.u_content = w.u_content;
		work.u_avatar = w.u_avatar;
		work.u_imageurls = w.u_imageurls;
		work.u_date = w.u_date;
		work.u_like = w.u_like;
		work.u_dislike = w.u_dislike;
		work.praise = w.praise;
		work.tramples = w.tramples;
		work.comments = w.comments;
		
		work.save();
	}

	public static List<Work> queryAllList() {
		return new Select().from(Work.class).execute();
	}
	
	public static void upDateSingle(Work work){
		work.save();
	}
	
	public static void deleteAll(){
		new Delete().from(Work.class).execute();
	}
}
