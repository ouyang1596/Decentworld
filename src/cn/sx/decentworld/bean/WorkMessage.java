/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: WorkMessage.java
 * @Description:作品圈消息，包括消息评论
 * @author: yj
 * @date: 2016年4月14日 上午9:36:59
 */
public class WorkMessage {
	public List<WorkComment> comments;
	public WorkBean work;
	public List<WorkComment> like = new ArrayList<WorkComment>();// 赞
	public List<WorkComment> dislike = new ArrayList<WorkComment>();// 踩
	public List<WorkComment> comment = new ArrayList<WorkComment>();// 评论
	public List<WorkComment> report = new ArrayList<WorkComment>();// 举报

	public void filter() {
		if (null != comments) {
			for (int i = 0; i < comments.size(); i++) {
				switch (comments.get(i).type) {
				case CommentBean.NORMAL:
					comment.add(comments.get(i));
					break;
				case CommentBean.LIKE:
					like.add(comments.get(i));
					break;
				case CommentBean.SUPERDISLIKE:
					dislike.add(comments.get(i));
					break;
				case CommentBean.REPORT:
					report.add(comments.get(i));
					break;
				}
			}
		}
	}
}
