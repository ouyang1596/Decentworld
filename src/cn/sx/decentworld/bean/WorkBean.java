/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;

/**
 * @ClassName: WorkBean.java
 * @author: yj
 * @date: 2016年4月11日 下午2:04:15
 */
public class WorkBean implements Serializable {
	/**
	 * 作品的id
	 * */
	public long id;

	/**
	 * 发表的内容类型 0文本1语音2图片
	 * */
	public int contentType;

	/**
	 * 发表作品用户id
	 */
	public String dwID;

	/**
	 * 作品的内容
	 */
	public String content;

	/**
	 * 语音的url
	 */
	public String audioUrl;
	/**
	 * 图片的url
	 */
	public String imgUrl;

	/**
	 * 赞的个数
	 */
	public int like;

	/**
	 * 踩的个数(已过时)
	 */
	public int dislike;

	/**
	 * 举报的个数
	 */
	public int report;

	/**
	 * 评论的个数
	 */
	public int comment;

	/**
	 * 踩的数目
	 */
	public int down;

	/**
	 * 发表作品的日期
	 */
	public long time;

	/**
	 * 发表作品时的金额
	 */
	public int money;
	/**
	 * 作品状态 0（热点状态），1（已下架储存）
	 */
	public int status;

	/**
	 * 作品圈分数,直接影响在激活状态时的排名
	 */
	public long score;

	/**
	 * 作品圈发布人名称
	 */
	public String publisherName;
}
