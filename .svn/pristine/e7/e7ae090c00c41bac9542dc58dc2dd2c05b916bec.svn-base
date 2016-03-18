/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.utils.LogUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * @ClassName: UserDetailInfo.java
 * @Description:保存用户的详细信息，该表在登录时新建，初次保存dwID、phoneNum、password
 * @author: cj
 * @date: 2015年8月25日 下午5:30:39
 */
@Table(name = "userInfo")
public class UserInfo extends Model
{
	private static String TAG = "UserInfo";

	@Column(name = "activeTime")
	private String activeTime;

	// 主键
	@Column(name = "dwID")
	private String dwID;
	// 身价
	@Column(name = "worth")
	private String worth;
	// 身家
	@Column(name = "wealth")
	private String wealth;
	// 经度
	@Column(name = "ln")
	private double ln;
	// 纬度
	@Column(name = "lt")
	private double lt;

	// 头像
	@Column(name = "icon")
	private String icon;

	// 1 个性签名
	@Column(name = "sign")
	private String sign;

	// 2 实名
	@Column(name = "realName")
	private String realName;

	// 3 昵称
	@Column(name = "nickName")
	private String nickName;

	// 4 年龄
	@Column(name = "age")
	private int age;

	// 5 性别（男： 1  、 女：  0）
	@Column(name = "sex")
	private int sex;

	/**
	 * 6 行业
	 */
	@Column(name = "occupation")
	private String occupation;

	/**
	 * 7 公司
	 */
	@Column(name = "corporation")
	private String corporation;

	/**
	 * 8 职位
	 */
	@Column(name = "job")
	private String job;

	/**
	 * 9 学校
	 */
	@Column(name = "school")
	private String school;
	/**
	 * 10 院系
	 */
	@Column(name = "department")
	private String department;

	/**
	 * 11 班级
	 */
	@Column(name = "classes")
	private String classes;

	/**
	 * 12 类型(才、财、貌)
	 */
	@Column(name = "type")
	private int type;

	/**
	 * 13 你的家乡信息
	 */
	@Column(name = "hometown")
	private String hometown;

	/**
	 * 14 民族
	 */
	@Column(name = "nation")
	private String nation;

	// 15 手机号码
	@Column(name = "phoneNum")
	private String phoneNum;

	/**
	 * 16 邮箱
	 */
	@Column(name = "email")
	private String email;

	/**
	 * 17 qq
	 */
	@Column(name = "qq")
	private String qq;

	/**
	 * 18 微信
	 */
	@Column(name = "wechat")
	private String wechat;

	/**
	 * 19 微博
	 */
	@Column(name = "blog")
	private String blog;

	/**
	 * 20 车子
	 */
	@Column(name = "vehicle")
	private String vehicle;

	/**
	 * 21 血型
	 */
	@Column(name = "bloodType")
	private String bloodType;
	/**
	 * 22 星座
	 */
	@Column(name = "constellatory")
	private String constellatory;
	/**
	 * 23 美食
	 */
	@Column(name = "cate")
	private String cate;
	/**
	 * 24 婚姻状况
	 */
	@Column(name = "maritalStatus")
	private String maritalStatus;

	/**
	 * 25 宗教
	 */
	@Column(name = "religion")
	private String religion;

	/**
	 * 26 常去的地点
	 */
	@Column(name = "position")
	private String position;

	/**
	 * 27 特长
	 */
	@Column(name = "speciality")
	private String speciality;

	/**
	 * 28 喜欢的书和动漫
	 */
	@Column(name = "likebooks")
	private String likebooks;

	/**
	 * 29 喜欢的音乐
	 */
	@Column(name = "likemusic")
	private String likemusic;

	/**
	 * 30 喜欢的影视
	 */
	@Column(name = "likesmovies")
	private String likesmovies;

	/**
	 * 31 喜欢的运动
	 */
	@Column(name = "likesport")
	private String likesport;

	/**
	 * 32 偶像
	 */
	@Column(name = "idol")
	private String idol;

	/**
	 * 33 座右铭
	 */
	@Column(name = "motto")
	private String motto;

	/**
	 * 34旅行足迹
	 */
	@Column(name = "footprint")
	private String footprint;

	/**
	 * 用户头像2
	 */
	@Column(name = "icon2")
	private String icon2;

	/**
	 * 用户头像3
	 */
	@Column(name = "icon3")
	private String icon3;

	/**
	 * 我的贵人
	 */
	@Column(name = "grId")
	private String grId;

	/**
	 * 我是谁的贵人
	 */
	@Column(name = "isgrId")
	private String isgrId;
	
	/*
	 * AdvanceSetting
	 */
	@Column(name = "canFindByID")
	private Boolean canFindByID;
	@Column(name = "canFindByPhoneNum")
	private Boolean canFindByPhoneNum;
	@Column(name = "canFindByNickName")
	private Boolean canFindByNickName;
	@Column(name = "canFindByRealName")
	private Boolean canFindByRealName;
	@Column(name = "noticeStrangerMessage")
	private Boolean noticeStrangerMessage;
	
	//是否接受服务器推送的消息
	@Column(name = "acceptPush")
	private Boolean acceptPush;
	
	//是否接受服务器推送关于貌的审核信息
	@Column(name = "acceptBeautyPush")
	private Boolean acceptBeautyPush;
	
	//是否同意自动转账
	@Column(name = "autoTransfer")
	private Boolean autoTransfer;
	

	/**
	 * 推荐人
	 */
	@Column(name = "referrer")
	private String referrer;

	/**
	 * 是否审核
	 */
	@Column(name = "ifChecked")
	private String ifChecked;
	
	/** 是否是学生 **/
	@Column(name = "ifStudent")
	private String ifStudent;
	
	/** 小头像 **/
	@Column(name = "iconSmall")
	private String iconSmall;
	
	/**
	 * 构造函数
	 */
	public UserInfo()
	{
		super();
		this.activeTime = "";
		this.dwID = "";
		this.worth = "";
		this.wealth = "";
		this.ln = 0;
		this.lt = 0;
		this.icon = "";
		this.sign = "";
		this.realName = "";
		this.nickName = "";
		this.age = 0;
		this.sex = 0;
		this.occupation = "";
		this.corporation = "";
		this.job = "";
		this.school = "";
		this.department = "";
		this.classes = "";
		this.type = 0;
		this.hometown = "";
		this.nation = "";
		this.phoneNum = "";
		this.email = "";
		this.qq = "";
		this.wechat = "";
		this.blog = "";
		this.vehicle = "";
		this.bloodType = "";
		this.constellatory = "";
		this.cate = "";
		this.maritalStatus = "";
		this.religion = "";
		this.position = "";
		this.speciality = "";
		this.likebooks = "";
		this.likemusic = "";
		this.likesmovies = "";
		this.likesport = "";
		this.idol = "";
		this.motto = "";
		this.footprint = "";
		this.icon2 = "";
		this.icon3 = "";
		this.grId = "";
		this.isgrId = "";
//		this.canFindByID = false;
//		this.canFindByPhoneNum = false;
//		this.canFindByNickName = false;
//		this.canFindByRealName = false;
//		this.noticeStrangerMessage = false;
//		this.acceptPush = false;
//		this.acceptBeautyPush = false;
		this.referrer = "";
		this.ifChecked = "";
		this.ifStudent = "";
		this.iconSmall = "";
	}

	public Boolean isCanFindByID() {
		return canFindByID;
	}




	public void setCanFindByID(Boolean canFindByID) {
		this.canFindByID = canFindByID;
	}




	public Boolean isCanFindByPhoneNum() {
		return canFindByPhoneNum;
	}




	public void setCanFindByPhoneNum(Boolean canFindByPhoneNum) {
		this.canFindByPhoneNum = canFindByPhoneNum;
	}




	public Boolean isCanFindByNickName() {
		return canFindByNickName;
	}




	public void setCanFindByNickName(Boolean canFindByNickName) {
		this.canFindByNickName = canFindByNickName;
	}




	public Boolean isCanFindByRealName() {
		return canFindByRealName;
	}




	public void setCanFindByRealName(Boolean canFindByRealName) {
		this.canFindByRealName = canFindByRealName;
	}




	public Boolean isNoticeStrangerMessage() {
		return noticeStrangerMessage;
	}




	public void setNoticeStrangerMessage(Boolean noticeStrangerMessage) {
		this.noticeStrangerMessage = noticeStrangerMessage;
	}




	/**
	 * @return the activeTime
	 */
	public String getActiveTime()
	{
		return activeTime;
	}

	/**
	 * @param activeTime the activeTime to set
	 */
	public void setActiveTime(String activeTime)
	{
		this.activeTime = activeTime;
	}

	/**
	 * @return the dwID
	 */
	public String getDwID()
	{
		return dwID;
	}

	/**
	 * @param dwID the dwID to set
	 */
	public void setDwID(String dwID)
	{
		this.dwID = dwID;
	}

	/**
	 * @return the worth
	 */
	public String getWorth()
	{
		return worth;
	}

	/**
	 * @param worth the worth to set
	 */
	public void setWorth(String worth)
	{
		this.worth = worth;
	}

	/**
	 * @return the wealth
	 */
	public String getWealth()
	{
		return wealth;
	}

	/**
	 * @param wealth the wealth to set
	 */
	public void setWealth(String wealth)
	{
		this.wealth = wealth;
	}

	/**
	 * @return the ln
	 */
	public double getLn()
	{
		return ln;
	}

	/**
	 * @param ln the ln to set
	 */
	public void setLn(double ln)
	{
		this.ln = ln;
	}

	/**
	 * @return the lt
	 */
	public double getLt()
	{
		return lt;
	}

	/**
	 * @param lt the lt to set
	 */
	public void setLt(double lt)
	{
		this.lt = lt;
	}


	/**
	 * @return the icon
	 */
	public String getIcon()
	{
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	/**
	 * @return the sign
	 */
	public String getSign()
	{
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign)
	{
		this.sign = sign;
	}

	/**
	 * @return the realName
	 */
	public String getRealName()
	{
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName()
	{
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	/**
	 * @return the age
	 */
	public int getAge()
	{
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age)
	{
		this.age = age;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation()
	{
		return occupation;
	}

	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation)
	{
		this.occupation = occupation;
	}

	/**
	 * @return the corporation
	 */
	public String getCorporation()
	{
		return corporation;
	}

	/**
	 * @param corporation the corporation to set
	 */
	public void setCorporation(String corporation)
	{
		this.corporation = corporation;
	}

	/**
	 * @return the job
	 */
	public String getJob()
	{
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job)
	{
		this.job = job;
	}

	/**
	 * @return the school
	 */
	public String getSchool()
	{
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school)
	{
		this.school = school;
	}

	/**
	 * @return the department
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department)
	{
		this.department = department;
	}

	/**
	 * @return the classes
	 */
	public String getClasses()
	{
		return classes;
	}

	/**
	 * @param classes the classes to set
	 */
	public void setClasses(String classes)
	{
		this.classes = classes;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the hometown
	 */
	public String getHometown()
	{
		return hometown;
	}

	/**
	 * @param hometown the hometown to set
	 */
	public void setHometown(String hometown)
	{
		this.hometown = hometown;
	}

	/**
	 * @return the nation
	 */
	public String getNation()
	{
		return nation;
	}

	/**
	 * @param nation the nation to set
	 */
	public void setNation(String nation)
	{
		this.nation = nation;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum()
	{
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum)
	{
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the qq
	 */
	public String getQq()
	{
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq)
	{
		this.qq = qq;
	}

	/**
	 * @return the wechat
	 */
	public String getWechat()
	{
		return wechat;
	}

	/**
	 * @param wechat the wechat to set
	 */
	public void setWechat(String wechat)
	{
		this.wechat = wechat;
	}

	/**
	 * @return the blog
	 */
	public String getBlog()
	{
		return blog;
	}

	/**
	 * @param blog the blog to set
	 */
	public void setBlog(String blog)
	{
		this.blog = blog;
	}

	/**
	 * @return the vehicle
	 */
	public String getVehicle()
	{
		return vehicle;
	}

	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(String vehicle)
	{
		this.vehicle = vehicle;
	}

	/**
	 * @return the bloodType
	 */
	public String getBloodType()
	{
		return bloodType;
	}

	/**
	 * @param bloodType the bloodType to set
	 */
	public void setBloodType(String bloodType)
	{
		this.bloodType = bloodType;
	}

	/**
	 * @return the constellatory
	 */
	public String getConstellatory()
	{
		return constellatory;
	}

	/**
	 * @param constellatory the constellatory to set
	 */
	public void setConstellatory(String constellatory)
	{
		this.constellatory = constellatory;
	}

	/**
	 * @return the cate
	 */
	public String getCate()
	{
		return cate;
	}

	/**
	 * @param cate the cate to set
	 */
	public void setCate(String cate)
	{
		this.cate = cate;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus()
	{
		return maritalStatus;
	}

	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the religion
	 */
	public String getReligion()
	{
		return religion;
	}

	/**
	 * @param religion the religion to set
	 */
	public void setReligion(String religion)
	{
		this.religion = religion;
	}

	/**
	 * @return the position
	 */
	public String getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position)
	{
		this.position = position;
	}

	/**
	 * @return the speciality
	 */
	public String getSpeciality()
	{
		return speciality;
	}

	/**
	 * @param speciality the speciality to set
	 */
	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

	/**
	 * @return the likebooks
	 */
	public String getLikebooks()
	{
		return likebooks;
	}

	/**
	 * @param likebooks the likebooks to set
	 */
	public void setLikebooks(String likebooks)
	{
		this.likebooks = likebooks;
	}

	/**
	 * @return the likemusic
	 */
	public String getLikemusic()
	{
		return likemusic;
	}

	/**
	 * @param likemusic the likemusic to set
	 */
	public void setLikemusic(String likemusic)
	{
		this.likemusic = likemusic;
	}

	/**
	 * @return the likesmovies
	 */
	public String getLikesmovies()
	{
		return likesmovies;
	}

	/**
	 * @param likesmovies the likesmovies to set
	 */
	public void setLikesmovies(String likesmovies)
	{
		this.likesmovies = likesmovies;
	}

	/**
	 * @return the likesport
	 */
	public String getLikesport()
	{
		return likesport;
	}

	/**
	 * @param likesport the likesport to set
	 */
	public void setLikesport(String likesport)
	{
		this.likesport = likesport;
	}

	/**
	 * @return the idol
	 */
	public String getIdol()
	{
		return idol;
	}

	/**
	 * @param idol the idol to set
	 */
	public void setIdol(String idol)
	{
		this.idol = idol;
	}

	/**
	 * @return the motto
	 */
	public String getMotto()
	{
		return motto;
	}

	/**
	 * @param motto the motto to set
	 */
	public void setMotto(String motto)
	{
		this.motto = motto;
	}

	/**
	 * @return the footprint
	 */
	public String getFootprint()
	{
		return footprint;
	}

	/**
	 * @param footprint the footprint to set
	 */
	public void setFootprint(String footprint)
	{
		this.footprint = footprint;
	}

	/**
	 * @return the icon2
	 */
	public String getIcon2()
	{
		return icon2;
	}

	/**
	 * @param icon2 the icon2 to set
	 */
	public void setIcon2(String icon2)
	{
		this.icon2 = icon2;
	}

	/**
	 * @return the icon3
	 */
	public String getIcon3()
	{
		return icon3;
	}

	/**
	 * @param icon3 the icon3 to set
	 */
	public void setIcon3(String icon3)
	{
		this.icon3 = icon3;
	}


	/**
	 * @return the grId
	 */
	public String getGrId()
	{
		return grId;
	}

	/**
	 * @param grId the grId to set
	 */
	public void setGrId(String grId)
	{
		this.grId = grId;
	}

	/**
	 * @return the isgrId
	 */
	public String getIsgrId()
	{
		return isgrId;
	}

	/**
	 * @param isgrId the isgrId to set
	 */
	public void setIsgrId(String isgrId)
	{
		this.isgrId = isgrId;
	}

	/**
	 * @return the referrer
	 */
	public String getReferrer()
	{
		return referrer;
	}

	/**
	 * @param referrer the referrer to set
	 */
	public void setReferrer(String referrer)
	{
		this.referrer = referrer;
	}
	
	

	/**
	 * @return the sex
	 */
	public int getSex()
	{
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex)
	{
		this.sex = sex;
	}
	
	
	
	

	/**
	 * @return the ifChecked
	 */
	public String getIfChecked()
	{
		return ifChecked;
	}




	/**
	 * @param ifChecked the ifChecked to set
	 */
	public void setIfChecked(String ifChecked)
	{
		this.ifChecked = ifChecked;
	}







	/**
	 * @return the ifStudent
	 */
	public String getIfStudent()
	{
		return ifStudent;
	}




	/**
	 * @param ifStudent the ifStudent to set
	 */
	public void setIfStudent(String ifStudent)
	{
		this.ifStudent = ifStudent;
	}
	
	




	/**
	 * @return the acceptPush
	 */
	public Boolean getAcceptPush()
	{
		return acceptPush;
	}




	/**
	 * @param acceptPush the acceptPush to set
	 */
	public void setAcceptPush(Boolean acceptPush)
	{
		this.acceptPush = acceptPush;
	}

	/**
	 * @return the iconSmall
	 */
	public String getIconSmall()
	{
		return iconSmall;
	}

	/**
	 * @param iconSmall the iconSmall to set
	 */
	public void setIconSmall(String iconSmall)
	{
		this.iconSmall = iconSmall;
	}

	/**
	 * @return the canFindByID
	 */
	public Boolean getCanFindByID()
	{
		return canFindByID;
	}

	/**
	 * @return the canFindByPhoneNum
	 */
	public Boolean getCanFindByPhoneNum()
	{
		return canFindByPhoneNum;
	}

	/**
	 * @return the canFindByNickName
	 */
	public Boolean getCanFindByNickName()
	{
		return canFindByNickName;
	}

	/**
	 * @return the canFindByRealName
	 */
	public Boolean getCanFindByRealName()
	{
		return canFindByRealName;
	}

	/**
	 * @return the noticeStrangerMessage
	 */
	public Boolean getNoticeStrangerMessage()
	{
		return noticeStrangerMessage;
	}

	/**
	 * @return the acceptBeautyPush
	 */
	public Boolean getAcceptBeautyPush()
	{
		return acceptBeautyPush;
	}




	/**
	 * @param acceptBeautyPush the acceptBeautyPush to set
	 */
	public void setAcceptBeautyPush(Boolean acceptBeautyPush)
	{
		this.acceptBeautyPush = acceptBeautyPush;
	}
	


	/**
	 * @return the autoTransfer
	 */
	public Boolean getAutoTransfer()
	{
		return autoTransfer;
	}

	/**
	 * @param autoTransfer the autoTransfer to set
	 */
	public void setAutoTransfer(Boolean autoTransfer)
	{
		this.autoTransfer = autoTransfer;
	}

	/**
	 * 添加一条记录，并保存账号（电话号码）和密码
	 * 
	 * @param phoneNum
	 * @param password
	 */
	public void savePhoneNumAndPwd(String phoneNum, String password)
	{
		UserInfo userDetailInfo = new UserInfo();
		userDetailInfo.setPhoneNum(phoneNum);
		userDetailInfo.save();
		LogUtils.i(TAG, "向UserDetailInfo表中增加一条记录，并且保存 phoneNum and password！" + ",其中phoneNum=" + phoneNum + ",password=" + password);
	}

	/**
	 * 根据电话号码，将对应的dwID保存在数据库中
	 * 
	 * @param phoneNum
	 * @param dwID
	 */
	public void saveByDwID(String phoneNum, String dwID)
	{
		UserInfo detailInfo = UserInfo.queryByPhoneNum(phoneNum);
		if (detailInfo != null)
		{
			detailInfo.dwID = dwID;
			detailInfo.save();
			LogUtils.i(TAG, "根据电话号码，将对应的dwID保存在数据库中完毕！" + ",其中dwID=" + dwID);
		}
		else
		{
			LogUtils.i(TAG, "数据库中无数据");
		}
	}

	/**
	 * 查找全部个人设置信息
	 * 
	 * @return
	 */
	public static List<UserInfo> queryAll()
	{
		return new Select().from(UserInfo.class).execute();
	}

	/**
	 * 查询一条指定 字段 名 和 字段值 的记录
	 * 
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @return
	 */
	public static UserInfo queryByField(String fieldName, String fieldValue)
	{
		return new Select().from(UserInfo.class).where(fieldName + "=?", fieldValue).executeSingle();
	}

	/**
	 * 删除个人设置信息
	 */
	public static void deleteAll()
	{
		new Delete().from(UserInfo.class).execute();
	}

	/**
	 * 删除指定dwID号的记录
	 * 
	 * @param dwID
	 */
	public static void deleteByDwID(String dwID)
	{
		new Delete().from(UserInfo.class).where("dwID=?", new String[]
		{ dwID }).execute();
	}



	/**
	 * 查询指定电话号码的记录
	 * 
	 * @param phoneNum
	 *            电话号码
	 */
	public static UserInfo queryByPhoneNum(String phoneNum)
	{
		return new Select().from(UserInfo.class).where("phoneNum=?", phoneNum).executeSingle();
	}
	
	/**
	 * 通过dwID查找用户的身价
	 * @param dwID
	 * @return
	 */
	public static String getWorthByDwID(String dwID)
	{
		UserInfo list = new Select().from(UserInfo.class).where("dwID=?",dwID).executeSingle();
		if(list!=null)
		{
			return list.getWorth();
		}
		return "";
	}
	
	/**
	 * 查询指定dwID号的记录并返回结果
	 * @param dwID
	 */
	public static UserInfo queryByDwID(String dwID)
	{
		return new Select().from(UserInfo.class).where("dwID=?", dwID).executeSingle();
	}


}
