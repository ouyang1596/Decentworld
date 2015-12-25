/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: NearbyStrangerInfo.java
 * @Description: 附近的陌生人的信息，在卡片中显示
 * @author: cj
 * @date: 2015年10月9日 下午1:13:08
 */
public class NearbyStrangerInfo {
	/**
	 * dwID
	 */
	private String dwID;
	/**
	 * 头像
	 */
	private String icon;
	/**
	 * 昵称
	 */

	private String nickName;
	/**
	 * 实名
	 * */
	private String realName;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 性别
	 */
	private String sex = "1";
	/**
	 * 身价
	 */
	private String worth;

	/**
	 * 职业
	 */
	private String job;
	/**
	 * 离自己的距离
	 */
	private String distance = "0";
	/**
	 * 判断这个对象是否被点击过 0没有 1有
	 * */
	private boolean ifLike;
	private String sign;

	public NearbyStrangerInfo() {
	}

	// public NearbyStrangerInfo(String dwID, String icon, String job,
	// String worth, String age, String nickName, String distance) {
	// super();
	// this.dwID = dwID;
	// this.icon = icon;
	// this.job = job;
	// this.worth = worth;
	// this.age = age;
	// this.nickName = nickName;
	// this.distance = distance;
	// }

	public boolean getIfLike() {
		return ifLike;
	}

	public void setIfLike(boolean ifClick) {
		this.ifLike = ifClick;
	}

	/**
	 * @return the dwID
	 */
	public String getDwID() {
		return dwID;
	}

	/**
	 * @param dwID
	 *            the dwID to set
	 */
	public void setDwID(String dwID) {
		this.dwID = dwID;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign
	 *            the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the worth
	 */
	public String getWorth() {
		return worth;
	}

	/**
	 * @param worth
	 *            the worth to set
	 */
	public void setWorth(String worth) {
		this.worth = worth;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
