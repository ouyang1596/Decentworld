/**
 * 
 */
package cn.sx.decentworld.entity;

import java.io.Serializable;
import java.util.List;

import cn.sx.decentworld.bean.MatchBean;

import com.activeandroid.annotation.Table;

/**
 * 
 * @ClassName: SearchResult.java
 * @Description:搜索结果界面
 * @author: cj
 * @date: 2016年3月22日 上午11:00:57
 */
@Table(name = "searchResult")
public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "SearchResult";

	/** 对方dwID **/
	public String dwID;

	/** 对方名字 **/
	public String name;

	/** 对方身价 **/
	public String worth;

	/** 对方职业 **/
	public String occupation;
	/** 是否是实名 1 是 0 不是 **/
	public String realName;
	/** 用户类型: 疑、真、腕 */
	public int userType;

	/** 陌生人搜索类型 **/
	public int searchType;// 表示搜索类型而得来的结果

	/** 性别 **/
	public String gender;
	/** 是否被推荐过 */
	public boolean isRecommended;

	/** 关键字相匹配 **/
	public List<MatchBean> matchList;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the userType
	 */
	public int getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(int userType) {
		this.userType = userType;
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
	 * @return the matchList
	 */
	public List<MatchBean> getMatchList() {
		return matchList;
	}

	/**
	 * @param matchList
	 *            the matchList to set
	 */
	public void setMatchList(List<MatchBean> matchList) {
		this.matchList = matchList;
	}

	@Override
	public String toString() {
		return "SearchResult [dwID=" + dwID + ", name=" + name + ", worth=" + worth + ", occupation=" + occupation
				+ ", realName=" + realName + ", userType=" + userType + ", searchType=" + searchType + ", gender=" + gender + "]";
	}

}
