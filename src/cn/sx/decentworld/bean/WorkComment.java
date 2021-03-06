/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: WorkComment.java
 * @Description: 作品圈评论对象
 * @author: yj
 * @date: 2016年4月14日 上午9:33:40
 */
public class WorkComment {
	public long id;

	/**
	 * 作品Id
	 */
	public long workID;

	/**
	 * 作品发布者ID
	 */
	public String publisherID;
	
	/**
	 * 评论者名字
	 */
	public String publisherName;

	/**
	 * 评论人
	 */
	public String dwID;

	/**
	 * 回复人
	 */
	public String reply;

	/**
	 * 评论类型,包括普通文字评论，赞，踩,举报
	 */
	public int type;

	/**
	 * 评论内容
	 */
	public String content;

	/**
	 * 评论时间
	 */
	public long time;
}
