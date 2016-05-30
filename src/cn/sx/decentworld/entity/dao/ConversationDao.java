/**
 * 
 */
package cn.sx.decentworld.entity.dao;

import java.util.List;

import com.activeandroid.query.Select;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.entity.db.Conversation;

/**
 * @ClassName: ConversationDao.java
 * @author: cj
 * @date: 2016年4月23日 下午12:35:30
 */
public class ConversationDao {

	/**
	 * 获取本地会话列表
	 * 
	 * @param chatRelationship
	 *            聊天关系
	 * @return
	 */
	public static List<Conversation> queryList(int chatRelationship) {
		String userID = DecentWorldApp.getInstance().getDwID();
		String sql = "userID = ? and chatRelationship = ?";
		List<Conversation> list = new Select().from(Conversation.class).where(sql, userID, chatRelationship)
				.orderBy("time desc").execute();
		return list;
	}
}
