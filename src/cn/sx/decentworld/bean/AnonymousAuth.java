package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 
 * @ClassName: AnonymousAuth.java
 * @Description: 匿名聊天时设置的信息，用户匿名信息改变的信息存放在这个表里面
 */
@Table(name = "anonymousAuth")
public class AnonymousAuth extends Model {

	// // 默认为不显示
	// public AnonymousAuth(String dwID) {
	// this.dwID = dwID;
	// this.showAge = false;
	// this.showBlog = false;
	// this.showBloodType = false;
	// this.showCate = false;
	// this.showClasses = false;
	// this.showConstellatory = false;
	// this.showCorporation = false;
	// this.showDepartment = false;
	// this.showEmail = false;
	// this.showFootprint = false;
	// this.showHometown = false;
	// this.showIdol = false;
	// this.showjob = false;
	// this.showLikebooks = false;
	// this.showLikemusic = false;
	// this.showLikesmovies = false;
	// this.showLikesport = false;
	// this.showMaritalStatus = false;
	// this.showNation = false;
	// this.showOccupation = false;
	// this.showPosition = false;
	// this.showQRCode = false;
	// this.showReligion = false;
	// this.showSchool = false;
	// this.showSpeciality = false;
	// this.showStature = false;
	// this.showVehicle = false;
	// this.showWorkChart = false;
	// this.showPhoneNum = false;
	// this.showRealName = false;
	// }

	/**
	 * 用戶id
	 */
	@Column(name = "dwID")
	private String dwID;

	public AnonymousAuth(String dwID) {
		super();
		this.dwID = dwID;
		this.showHometown = false;
		this.showAge = false;
		this.showLikesport = false;
		this.showSign = false;
		this.showFootprint = false;
		this.showLikebooks = false;
		this.showLikesmovies = false;
		this.showLikemusic = false;
		this.showType = false;
		this.showMotto = false;
		this.showSex = false;
		this.showEmail = false;
		this.showMaritalStatus = false;
		this.showStature = false;
		this.showNation = false;
		this.showQRCode = false;
		this.showBloodType = false;
		this.showWorkChart = false;
		this.showVehicle = false;
		this.showSpeciality = false;
		this.showConstellatory = false;
		this.showCate = false;
		this.showIdol = false;
		this.showReligion = false;
		this.showClasses = false;
		this.showDepartment = false;
		this.showSchool = false;
		this.showOccupation = false;
		this.showjob = false;
		this.showCorporation = false;
		this.showPosition = false;
		this.showIcon = false;
		this.showBlog = false;
		this.showNickName = false;
		this.showQQ = false;
		this.showWechat = false;
		this.showRealName = false;
		this.showPhoneNum = false;
		this.showWealth = false;
		this.showWorth = false;
	}

	/**
	 * 是否显示家乡 1
	 */
	@Column(name = "show_hometown")
	private Boolean showHometown;

	/**
	 * 是否显示家乡 2
	 */
	@Column(name = "show_age")
	private Boolean showAge;

	/**
	 * 是否显示喜欢的运动 3
	 */
	@Column(name = "show_likesport")
	private Boolean showLikesport;

	/**
	 * 是否显示个性标签 4
	 */
	@Column(name = "show_sign")
	private Boolean showSign;

	/**
	 * 是否显示旅行足迹 5
	 */
	@Column(name = "show_footprint")
	private Boolean showFootprint;

	/**
	 * 是否显示喜欢的书籍 6
	 */
	@Column(name = "show_likebooks")
	private Boolean showLikebooks;

	/**
	 * 是否显示喜欢的电影 7
	 */
	@Column(name = "show_likesmovies")
	private Boolean showLikesmovies;

	/**
	 * 是否显示喜欢的音乐 8
	 */
	@Column(name = "show_likemusic")
	private Boolean showLikemusic;

	/**
	 * 是否显示喜欢的音乐 9
	 */
	@Column(name = "show_type")
	private Boolean showType;

	/**
	 * 是否显示喜欢的音乐 10
	 */
	@Column(name = "show_motto")
	private Boolean showMotto;

	/**
	 * 是否显示电子邮箱 11
	 */
	@Column(name = "show_email")
	private Boolean showEmail;

	/**
	 * 是否显示婚姻状况 12
	 */
	@Column(name = "show_MaritalStatus")
	private Boolean showMaritalStatus;

	/**
	 * 是否显示身高 13
	 */
	@Column(name = "show_stature")
	private Boolean showStature;

	/**
	 * 是否显示民族 14
	 */
	@Column(name = "show_nation")
	private Boolean showNation;

	/**
	 * 是否显示二维码 15
	 */
	@Column(name = "show_QRCode")
	private Boolean showQRCode;

	/**
	 * 是否显示血型 16
	 */
	@Column(name = "show_bloodType")
	private Boolean showBloodType;

	/**
	 * 是否显示作品图 17
	 */
	@Column(name = "show_workChart")
	private Boolean showWorkChart;

	/**
	 * 是否显示有车子 18
	 */
	@Column(name = "show_vehicle")
	private Boolean showVehicle;

	/**
	 * 是否显示特长 19
	 */
	@Column(name = "show_speciality")
	private Boolean showSpeciality;

	/**
	 * 是否显示星座 20
	 */
	@Column(name = "show_constellatory")
	private Boolean showConstellatory;

	/**
	 * 是否显示喜欢的美食 21
	 */
	@Column(name = "show_cate")
	private Boolean showCate;

	/**
	 * 是否显示偶像 22
	 */
	@Column(name = "show_Idol")
	private Boolean showIdol;

	/**
	 * 是否显示宗教 23
	 */
	@Column(name = "show_Religion")
	private Boolean showReligion;

	/**
	 * 是否显示班级 24
	 */
	@Column(name = "show_classes")
	private Boolean showClasses;

	/**
	 * 是否显示院系 25
	 */
	@Column(name = "show_Department")
	private Boolean showDepartment;

	/**
	 * 是否显示学校 26
	 */
	@Column(name = "show_school")
	private Boolean showSchool;

	/**
	 * 是否显示职业 27
	 */
	@Column(name = "show_occupation")
	private Boolean showOccupation;
	/**
	 * 是否显示职位 28
	 */
	@Column(name = "show_job")
	private Boolean showjob;

	/**
	 * 是否显示公司 29
	 */
	@Column(name = "show_corporation")
	private Boolean showCorporation;

	/**
	 * 是否显示你常去的地点 30
	 */
	@Column(name = "show_position")
	private Boolean showPosition;

	/**
	 * 是否显示头像 31
	 */
	@Column(name = "show_icon")
	private Boolean showIcon;

	/**
	 * 是否显示微博 32
	 */
	@Column(name = "show_blog")
	private Boolean showBlog;

	/**
	 * 是否显示昵称 33
	 */
	@Column(name = "show_nickname")
	private Boolean showNickName;

	/**
	 * 是否显示qq 34
	 */
	@Column(name = "show_qq")
	private Boolean showQQ;

	/**
	 * 是否显示昵称 35
	 */
	@Column(name = "show_wechat")
	private Boolean showWechat;

	/**
	 * 是否显示实名 36
	 */
	@Column(name = "show_realName")
	private Boolean showRealName;
	/**
	 * 是否显示昵称 37
	 */
	@Column(name = "show_phoneNum")
	private Boolean showPhoneNum;
	/**
	 * 是否显示性别38
	 */
	@Column(name = "show_sex")
	private Boolean showSex;
	/**
	 * 是否显示性别39
	 */
	@Column(name = "show_wealth")
	private Boolean showWealth;
	/**
	 * 是否显示性别40
	 */
	@Column(name = "show_worth")
	private Boolean showWorth;

	/**
	 * @return the showWealth
	 */
	public Boolean getShowWealth() {
		return showWealth;
	}

	/**
	 * @param showWealth
	 *            the showWealth to set
	 */
	public void setShowWealth(Boolean showWealth) {
		this.showWealth = showWealth;
	}

	/**
	 * @return the showWorth
	 */
	public Boolean getShowWorth() {
		return showWorth;
	}

	/**
	 * @param showWorth
	 *            the showWorth to set
	 */
	public void setShowWorth(Boolean showWorth) {
		this.showWorth = showWorth;
	}

	/**
	 * @return the showSex
	 */
	public Boolean getShowSex() {
		return showSex;
	}

	/**
	 * @param showSex
	 *            the showSex to set
	 */
	public void setShowSex(Boolean showSex) {
		this.showSex = showSex;
	}

	public Boolean getShowRealName() {
		return showRealName;
	}

	public void setShowRealName(Boolean showRealName) {
		this.showRealName = showRealName;
	}

	public Boolean getShowPhoneNum() {
		return showPhoneNum;
	}

	public void setShowPhoneNum(Boolean showPhoneNum) {
		this.showPhoneNum = showPhoneNum;
	}

	public Boolean getShowQQ() {
		return showQQ;
	}

	public void setShowQQ(Boolean showQQ) {
		this.showQQ = showQQ;
	}

	public Boolean getShowWechat() {
		return showWechat;
	}

	public void setShowWechat(Boolean showWechat) {
		this.showWechat = showWechat;
	}

	public Boolean getShowNickName() {
		return showNickName;
	}

	public void setShowNickName(Boolean showNickName) {
		this.showNickName = showNickName;
	}

	// public String getDwID()
	// {
	// return dwID;
	// }
	//
	// public void setDwID(String dwID)
	// {
	// this.dwID = dwID;
	// }

	public Boolean getShowHometown() {
		return showHometown;
	}

	public void setShowHometown(Boolean showHometown) {
		this.showHometown = showHometown;
	}

	public Boolean getShowAge() {
		return showAge;
	}

	public void setShowAge(Boolean showAge) {
		this.showAge = showAge;
	}

	public Boolean getShowLikesport() {
		return showLikesport;
	}

	public void setShowLikesport(Boolean showLikesport) {
		this.showLikesport = showLikesport;
	}

	public Boolean getShowSign() {
		return this.showSign;
	}

	public void setShowSign(Boolean showSign) {
		this.showSign = showSign;
	}

	public Boolean getShowFootprint() {
		return showFootprint;
	}

	public void setShowFootprint(Boolean showFootprint) {
		this.showFootprint = showFootprint;
	}

	public Boolean getShowLikebooks() {
		return showLikebooks;
	}

	public void setShowLikebooks(Boolean showLikebooks) {
		this.showLikebooks = showLikebooks;
	}

	public Boolean getShowLikesmovies() {
		return showLikesmovies;
	}

	public void setShowLikesmovies(Boolean showLikesmovies) {
		this.showLikesmovies = showLikesmovies;
	}

	public Boolean getShowLikemusic() {
		return showLikemusic;
	}

	public void setShowLikemusic(Boolean showLikemusic) {
		this.showLikemusic = showLikemusic;
	}

	public Boolean getShowEmail() {
		return showEmail;
	}

	public void setShowEmail(Boolean showEmail) {
		this.showEmail = showEmail;
	}

	public Boolean getShowMaritalStatus() {
		return showMaritalStatus;
	}

	public void setShowMaritalStatus(Boolean showMaritalStatus) {
		this.showMaritalStatus = showMaritalStatus;
	}

	public Boolean getShowStature() {
		return showStature;
	}

	public void setShowStature(Boolean showStature) {
		this.showStature = showStature;
	}

	public Boolean getShowNation() {
		return showNation;
	}

	public void setShowNation(Boolean showNation) {
		this.showNation = showNation;
	}

	public Boolean getShowQRCode() {
		return showQRCode;
	}

	public void setShowQRCode(Boolean showQRCode) {
		this.showQRCode = showQRCode;
	}

	public Boolean getShowBloodType() {
		return showBloodType;
	}

	public void setShowBloodType(Boolean showBloodType) {
		this.showBloodType = showBloodType;
	}

	public Boolean getShowWorkChart() {
		return showWorkChart;
	}

	public String getDwID() {
		return dwID;
	}

	public void setDwID(String dwID) {
		this.dwID = dwID;
	}

	public void setShowWorkChart(Boolean showWorkChart) {
		this.showWorkChart = showWorkChart;
	}

	public Boolean getShowVehicle() {
		return showVehicle;
	}

	public void setShowVehicle(Boolean showVehicle) {
		this.showVehicle = showVehicle;
	}

	public Boolean getShowSpeciality() {
		return showSpeciality;
	}

	public void setShowSpeciality(Boolean showSpeciality) {
		this.showSpeciality = showSpeciality;
	}

	public Boolean getShowConstellatory() {
		return showConstellatory;
	}

	public void setShowConstellatory(Boolean showConstellatory) {
		this.showConstellatory = showConstellatory;
	}

	public Boolean getShowCate() {
		return showCate;
	}

	public void setShowCate(Boolean showCate) {
		this.showCate = showCate;
	}

	public Boolean getShowIdol() {
		return showIdol;
	}

	public void setShowIdol(Boolean showIdol) {
		this.showIdol = showIdol;
	}

	public Boolean getShowReligion() {
		return showReligion;
	}

	public void setShowReligion(Boolean showReligion) {
		this.showReligion = showReligion;
	}

	public Boolean getShowClasses() {
		return showClasses;
	}

	public void setShowClasses(Boolean showClasses) {
		this.showClasses = showClasses;
	}

	public Boolean getShowDepartment() {
		return showDepartment;
	}

	public void setShowDepartment(Boolean showDepartment) {
		this.showDepartment = showDepartment;
	}

	public Boolean getShowSchool() {
		return showSchool;
	}

	public void setShowSchool(Boolean showSchool) {
		this.showSchool = showSchool;
	}

	public Boolean getShowOccupation() {
		return showOccupation;
	}

	public void setShowOccupation(Boolean showOccupation) {
		this.showOccupation = showOccupation;
	}

	public Boolean getShowjob() {
		return showjob;
	}

	public void setShowjob(Boolean showjob) {
		this.showjob = showjob;
	}

	public Boolean getShowCorporation() {
		return showCorporation;
	}

	public void setShowCorporation(Boolean showCorporation) {
		this.showCorporation = showCorporation;
	}

	public Boolean getShowPosition() {
		return showPosition;
	}

	public void setShowPosition(Boolean showPosition) {
		this.showPosition = showPosition;
	}

	public Boolean getShowIcon() {
		return showIcon;
	}

	public void setShowIcon(Boolean showIcon) {
		this.showIcon = showIcon;
	}

	public Boolean getShowBlog() {
		return showBlog;
	}

	public void setShowBlog(Boolean showBlog) {
		this.showBlog = showBlog;
	}

	public Boolean getShowType() {
		return showType;
	}

	public void setShowType(Boolean showType) {
		this.showType = showType;
	}

	public Boolean getShowMotto() {
		return showMotto;
	}

	public void setShowMotto(Boolean showMotto) {
		this.showMotto = showMotto;
	}

}
