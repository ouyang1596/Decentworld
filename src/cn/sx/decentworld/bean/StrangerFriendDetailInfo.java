/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: UserDetailInfo.java
 * @Description:保存陌生人与朋友详情
 * @author: yj
 * @date: 2015年8月25日 下午5:30:39
 */
@Table(name = "StrangerFriendDetailInfo")
public class StrangerFriendDetailInfo extends Model {

	@Column(name = "strangerFriendId")
	public String strangerFriendId;// 陌生人或朋友ID
	@Column(name = "versionNum")
	public String versionNum = "-1";// 版本号上传到服务器用来判断是否更新数据
	@Column(name = "strangerFriendInfo")
	public String strangerFriendInfo;

	public StrangerFriendDetailInfo() {

	}

	public StrangerFriendDetailInfo(String strangerFriendId) {
		this.strangerFriendId = strangerFriendId;
	}

	/**
	 * 查询指定id号的记录并返回结果
	 */
	public static StrangerFriendDetailInfo queryById(String strangerFriendId) {
		return new Select().from(StrangerFriendDetailInfo.class).where("strangerFriendId=?", strangerFriendId).executeSingle();
	}

	@Override
	public String toString() {
		return "StrangerFriendDetailInfo [strangerFriendId=" + strangerFriendId + ", versionNum=" + versionNum
				+ ", strangerFriendInfo=" + strangerFriendInfo + "]";
	}
}
