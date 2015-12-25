/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: SearchResult.java
 * @Description: 搜索结果
 * @author: cj
 * @date: 2015年12月10日 上午9:58:37
 */
@Table(name = "searchResult")
public class SearchResult extends Model {
	private static final String TAG = "SearchResult";
	// 12-17 01:44:53.840: E/dw bm(5881):
	// msg--{"result":[{"age":"27","area":"河北","dwID":"245347","gender":"0","nickName":"大婶","sign":""}],"searchType":"0"}

	/**
	 * 搜索类型
	 */
	public final static int SEARCH_TYPE_PHONE = 0;// 电话号码
	public final static int SEARCH_TYPE_DWID = 1;// dwID
	public final static int SEARCH_TYPE_NICKNAME = 2;// 昵称
	public final static int SEARCH_TYPE_REALNAME = 3;// 实名

	/** 用户ID **/
	@Column(name = "userID")
	private int userID;

	/** 陌生人搜索类型 **/
	@Column(name = "searchType")
	private int searchType;// 表示搜索类型而得来的结果

	/** 陌生人年龄 **/
	@Column(name = "age")
	private String age;

	/** 陌生人地区 **/
	@Column(name = "area")
	private String area;

	/** 陌生人dwID **/
	@Column(name = "dwID")
	private String dwID;

	/** 陌生人性别 **/
	@Column(name = "gender")
	private String gender;

	/** 陌生人头像 **/
	@Column(name = "icon")
	private String icon;

	/** 陌生人昵称 **/
	@Column(name = "nickName")
	private String nickName;

	/** 陌生人个性签名 **/
	@Column(name = "sign")
	private String sign;

	/** 陌生人身价 **/
	@Column(name = "worth")
	private String worth;

	/**
	 * 构造函数
	 */
	public SearchResult() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the searchType
	 */
	public int getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType
	 *            the searchType to set
	 */
	public void setSearchType(int searchType) {
		this.searchType = searchType;
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
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
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

}
