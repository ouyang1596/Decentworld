/**
 * 
 */
package cn.sx.decentworld.entity.db;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.utils.ImageUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: UserExtraInfo.java
 * @Description: 用户额外信息
 * @author: cj
 * @date: 2016年1月7日 上午11:04:26
 */
@Table(name = "selfExtraInfo")
public class SelfExtraInfo extends Model {
	// 用户ID
	@Column(name = "userID")
	private String userID;

	// 用户小头像
	@Column(name = "iconSmall")
	private String iconSmall;
	// 用户头像1
	@Column(name = "icon")
	private String icon;
	// 用户头像2
	@Column(name = "icon2")
	private String icon2;
	// 用户头像3
	@Column(name = "icon3")
	private String icon3;
	// 用户头像3
	@Column(name = "icon4")
	private String icon4;
	// 用户头像3
	@Column(name = "icon5")
	private String icon5;
	// 用户头像3
	@Column(name = "icon6")
	private String icon6;

	// 是否自动转账
	@Column(name = "autoTransfer")
	private Boolean autoTransfer;
	// 账号类型
	@Column(name = "accountType")
	private int accountType;
	// 账号（支付宝、微信）
	@Column(name = "accountName")
	private String accountName;

	// 推荐获取的总收益
	@Column(name = "recomTotalBenefit")
	private float recomTotalBenefit;
	// 作为别人的贵人的总收益
	@Column(name = "grTotalBenefit")
	private float grTotalBenefit;
	// 推荐存储总收益
	@Column(name = "recomStoredBenefit")
	private float recomStoredBenefit;

	// 新的朋友圈评论,存储方式：{"dwID":"123456","momentID","100","wealth":-1}
	@Column(name = "newMomentComment")
	private String newMomentComment;

	// 新的作品圈评论,存储方式：{"dwID":"123456","momentID","100"}
	@Column(name = "newWorkComment")
	private String newWorkComment;

	// 新朋友圈发布者
	@Column(name = "newMomentPublisherID")
	private String newMomentPublisherID;

	// 新作品圈发布者
	@Column(name = "newWorkPublisherID")
	private String newWorkPublisherID;

	/**
	 * 高级设置
	 */
	// 是否接受服务器推送的消息
	@Column(name = "acceptPush")
	private Boolean acceptPush;
	// 系统推送审核信息
	@Column(name = "acceptCheckPush")
	private Boolean acceptCheckPush;
	// 陌生人消息声音提示
	@Column(name = "strangerNotice")
	private Boolean strangerNotice;
	// 好友消息声音提示
	@Column(name = "friendNotice")
	private Boolean friendNotice;

	/**
	 * 构造函数
	 */
	public SelfExtraInfo() {
		this.userID = DecentWorldApp.getInstance().getDwID();
		this.iconSmall = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_SMALL);
		this.icon = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_MAIN);
		this.icon2 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_1);
		this.icon3 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_2);
		this.icon4 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_3);
		this.icon5 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_4);
		this.icon6 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_5);
		this.accountType = -1;
		this.accountName = "";
		this.recomTotalBenefit = -1;
		this.grTotalBenefit = -1;
		this.recomStoredBenefit = -1;
		this.newMomentComment = "";
		this.newWorkComment = "";
		this.newMomentPublisherID = "";
		this.newWorkPublisherID = "";
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the iconSmall
	 */
	public String getIconSmall() {
		return iconSmall;
	}

	/**
	 * @param iconSmall
	 *            the iconSmall to set
	 */
	public void setIconSmall(String iconSmall) {
		this.iconSmall = iconSmall;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the icon2
	 */
	public String getIcon2() {
		return icon2;
	}

	/**
	 * @param icon2
	 *            the icon2 to set
	 */
	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

	/**
	 * @return the icon3
	 */
	public String getIcon3() {
		return icon3;
	}

	/**
	 * @param icon3
	 *            the icon3 to set
	 */
	public void setIcon3(String icon3) {
		this.icon3 = icon3;
	}

	/**
	 * @return the icon4
	 */
	public String getIcon4() {
		return icon4;
	}

	/**
	 * @param icon4
	 *            the icon4 to set
	 */
	public void setIcon4(String icon4) {
		this.icon4 = icon4;
	}

	/**
	 * @return the icon5
	 */
	public String getIcon5() {
		return icon5;
	}

	/**
	 * @param icon5
	 *            the icon5 to set
	 */
	public void setIcon5(String icon5) {
		this.icon5 = icon5;
	}

	/**
	 * @return the icon6
	 */
	public String getIcon6() {
		return icon6;
	}

	/**
	 * @param icon6
	 *            the icon6 to set
	 */
	public void setIcon6(String icon6) {
		this.icon6 = icon6;
	}

	/**
	 * @return the autoTransfer
	 */
	public Boolean getAutoTransfer() {
		return autoTransfer;
	}

	/**
	 * @param autoTransfer
	 *            the autoTransfer to set
	 */
	public void setAutoTransfer(Boolean autoTransfer) {
		this.autoTransfer = autoTransfer;
	}

	/**
	 * @return the accountType
	 */
	public int getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public float getRecomTotalBenefit() {
		return recomTotalBenefit;
	}

	public void setRecomTotalBenefit(float recomTotalBenefit) {
		this.recomTotalBenefit = recomTotalBenefit;
	}

	public float getGrTotalBenefit() {
		return grTotalBenefit;
	}

	public void setGrTotalBenefit(float grTotalBenefit) {
		this.grTotalBenefit = grTotalBenefit;
	}

	public float getRecomStoredBenefit() {
		return recomStoredBenefit;
	}

	public void setRecomStoredBenefit(float recomStoredBenefit) {
		this.recomStoredBenefit = recomStoredBenefit;
	}

	/**
	 * @return the acceptPush
	 */
	public Boolean getAcceptPush() {
		return acceptPush;
	}

	public void setAcceptPush(Boolean acceptPush) {
		this.acceptPush = acceptPush;
	}

	public Boolean getAcceptCheckPush() {
		return acceptCheckPush;
	}

	public void setAcceptCheckPush(Boolean acceptCheckPush) {
		this.acceptCheckPush = acceptCheckPush;
	}

	public Boolean getStrangerNotice() {
		return strangerNotice;
	}

	public void setStrangerNotice(Boolean strangerNotice) {
		this.strangerNotice = strangerNotice;
	}

	public Boolean getFriendNotice() {
		return friendNotice;
	}

	public void setFriendNotice(Boolean friendNotice) {
		this.friendNotice = friendNotice;
	}

	/**
	 * @return the newMomentComment
	 */
	public String getNewMomentComment() {
		return newMomentComment;
	}

	/**
	 * @param newMomentComment
	 *            the newMomentComment to set
	 */
	public void setNewMomentComment(String newMomentComment) {
		this.newMomentComment = newMomentComment;
	}

	/**
	 * @return the newWorkComment
	 */
	public String getNewWorkComment() {
		return newWorkComment;
	}

	/**
	 * @param newWorkComment
	 *            the newWorkComment to set
	 */
	public void setNewWorkComment(String newWorkComment) {
		this.newWorkComment = newWorkComment;
	}

	/**
	 * @return the newMomentPublisherID
	 */
	public String getNewMomentPublisherID() {
		return newMomentPublisherID;
	}

	/**
	 * @param newMomentPublisherID
	 *            the newMomentPublisherID to set
	 */
	public void setNewMomentPublisherID(String newMomentPublisherID) {
		this.newMomentPublisherID = newMomentPublisherID;
	}

	/**
	 * @return the newWorkPublisherID
	 */
	public String getNewWorkPublisherID() {
		return newWorkPublisherID;
	}

	/**
	 * @param newWorkPublisherID
	 *            the newWorkPublisherID to set
	 */
	public void setNewWorkPublisherID(String newWorkPublisherID) {
		this.newWorkPublisherID = newWorkPublisherID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SelfExtraInfo [userID=" + userID + ", iconSmall=" + iconSmall + ", icon=" + icon + ", icon2=" + icon2
				+ ", icon3=" + icon3 + ", icon4=" + icon4 + ", icon5=" + icon5 + ", icon6=" + icon6 + ", autoTransfer="
				+ autoTransfer + ", accountType=" + accountType + ", accountName=" + accountName + ", recomTotalBenefit="
				+ recomTotalBenefit + ", grTotalBenefit=" + grTotalBenefit + ", recomStoredBenefit=" + recomStoredBenefit
				+ ", newMomentComment=" + newMomentComment + ", newWorkComment=" + newWorkComment + ", newMomentPublisherID="
				+ newMomentPublisherID + ", newWorkPublisherID=" + newWorkPublisherID + ", acceptPush=" + acceptPush
				+ ", acceptCheckPush=" + acceptCheckPush + ", strangerNotice=" + strangerNotice + ", friendNotice="
				+ friendNotice + "]";
	}

}
