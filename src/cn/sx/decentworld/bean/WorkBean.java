/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: WorkBean.java
 * @author: yj
 * @date: 2016年4月11日 下午2:04:15
 */
@Table(name = "WorkBean")
public class WorkBean extends Model implements Serializable {
	/**
	 * 作品的id
	 * */
	@Column(name = "work_id")
	public long id;

	/**
	 * 发表的内容类型 0文本1语音2图片
	 * */
	@Column(name = "contentType")
	public int contentType;

	/**
	 * 发表作品用户id
	 */
	@Column(name = "dwID")
	public String dwID;

	/**
	 * 作品的内容
	 */
	@Column(name = "content")
	public String content;

	/**
	 * 语音的url
	 */
	@Column(name = "audioUrl")
	public String audioUrl;
	/**
	 * 图片的url
	 */
	@Column(name = "imgUrl")
	public String imgUrl;

	/**
	 * 赞的个数
	 */
	@Column(name = "likenum")
	public int like;

	/**
	 * 踩的个数(已过时)
	 */
	@Column(name = "dislikenum")
	public int dislike;

	/**
	 * 举报的个数
	 */
	@Column(name = "reportnum")
	public int report;

	/**
	 * 评论的个数
	 */
	@Column(name = "commentnum")
	public int comment;

	/**
	 * 踩的数目
	 */
	@Column(name = "downnum")
	public int down;

	/**
	 * 发表作品的日期
	 */
	@Column(name = "time")
	public long time;

	/**
	 * 发表作品时的金额
	 */
	@Column(name = "money")
	public int money;
	/**
	 * 作品状态 0（热点状态），1（已下架储存）
	 */
	@Column(name = "status")
	public int status;

	/**
	 * 作品圈分数,直接影响在激活状态时的排名
	 */
	@Column(name = "score")
	public long score;

	/**
	 * 作品圈发布人名称
	 */
	@Column(name = "publisherName")
	public String publisherName;
}
