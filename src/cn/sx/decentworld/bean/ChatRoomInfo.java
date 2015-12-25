/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: ChatRoomInfo.java
 * @Description: 单个聊天室的详情
 * @author: cj
 * @date: 2015年8月26日 上午9:10:41
 */
@Table(name = "chatRoomInfo")
public class ChatRoomInfo extends Model implements Comparable<ChatRoomInfo> {

	/**
	 * 聊天室名字
	 */
	// @Column(name = "roomName")
	// private String roomName;

	/**
	 * 聊天室描述
	 */
	// @Column(name = "roomDescription")
	// private String roomDescription;

	/**
	 * 聊天室角色类型（普通成员、拥有者）
	 */
	// @Column(name = "role")
	// private String role;

	// /////////////////////////////////////////
	/**
	 * 聊天室拥有者头像
	 */
	@Column(name = "ownerIcon")
	private String ownerIcon;
	/**
	 * 聊天室拥有者ID
	 */
	@Column(name = "ownerID")
	private String ownerID;

	/**
	 * 聊天室在线人数
	 */
	@Column(name = "onLineNum")
	private String onLineNum;

	/**
	 * 聊天室拥有者简介
	 */
	@Column(name = "ownerIntroduction")
	private String ownerIntroduction;

	/**
	 * 聊天室拥有者昵称
	 */
	@Column(name = "ownerName")
	private String ownerName;

	/**
	 * 聊天室背景
	 */
	@Column(name = "roomBackground")
	private String roomBackground;

	/**
	 * 聊天室主题名字
	 */
	@Column(name = "subjectName")
	private String subjectName;

	/**
	 * 聊天室ID
	 */
	@Column(name = "roomID")
	private String roomID;

	/**
	 * 离开聊天室的人数
	 */
	@Column(name = "leftAmount")
	private int leftAmount;

	/**
	 * 进入聊天室的人数
	 */
	@Column(name = "joinAmount")
	private int joinAmount;

	/**
	 * 进入聊天室收费金额
	 */
	@Column(name = "chargeAmount")
	private String chargeAmount;

	/**
	 * 无参构造函数
	 */
	public ChatRoomInfo() {

	}

	/**
	 * @return the roomID
	 */
	public String getRoomID() {
		return roomID;
	}

	/**
	 * @param roomID
	 *            the roomID to set
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	/**
	 * @return the ownerIcon
	 */
	public String getOwnerIcon() {
		return ownerIcon;
	}

	/**
	 * @param ownerIcon
	 *            the ownerIcon to set
	 */
	public void setOwnerIcon(String ownerIcon) {
		this.ownerIcon = ownerIcon;
	}

	/**
	 * @return the ownerID
	 */
	public String getOwnerID() {
		return ownerID;
	}

	/**
	 * @param ownerID
	 *            the ownerID to set
	 */
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	/**
	 * @return the ownerIntroduction
	 */
	public String getOwnerIntroduction() {
		return ownerIntroduction;
	}

	/**
	 * @param ownerIntroduction
	 *            the ownerIntroduction to set
	 */
	public void setOwnerIntroduction(String ownerIntroduction) {
		this.ownerIntroduction = ownerIntroduction;
	}

	/**
	 * @return the roomBackground
	 */
	public String getRoomBackground() {
		return roomBackground;
	}

	/**
	 * @param roomBackground
	 *            the roomBackground to set
	 */
	public void setRoomBackground(String roomBackground) {
		this.roomBackground = roomBackground;
	}

	/**
	 * @return the ownerNickName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerNickName
	 *            the ownerNickName to set
	 */
	public void setOwnerName(String ownerNickName) {
		this.ownerName = ownerNickName;
	}

	/**
	 * @return the onLineNum
	 */
	public String getOnLineNum() {
		return onLineNum;
	}

	/**
	 * @param onLineNum
	 *            the onLineNum to set
	 */
	public void setOnLineNum(String onLineNum) {
		this.onLineNum = onLineNum;
	}

	/**
	 * @param leftAmount
	 *            the leftAmount to set
	 */
	public void setLeftAmount(int leftAmount) {
		this.leftAmount = leftAmount;
	}

	/**
	 * @param joinAmount
	 *            the joinAmount to set
	 */
	public void setJoinAmount(int joinAmount) {
		this.joinAmount = joinAmount;
	}

	/**
	 * @return the leftAmount
	 */
	public int getLeftAmount() {
		return leftAmount;
	}

	/**
	 * @return the joinAmount
	 */
	public int getJoinAmount() {
		return joinAmount;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName
	 *            the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the chargeAmount
	 */
	public String getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * @param chargeAmount
	 *            the chargeAmount to set
	 */
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * 排序
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(ChatRoomInfo o) {
		int def = o.getJoinAmount() - o.getLeftAmount();
		int def_self = joinAmount - leftAmount;
		if (def > def_self) {
			return 1;
		} else if (def < def_self) {
			return -1;
		} else {
			int total = o.getJoinAmount() + o.getLeftAmount();
			int total_self = joinAmount + leftAmount;
			if (total > total_self) {
				return 1;
			} else if (total < total_self) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
