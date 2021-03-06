/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;
import java.util.List;

import com.activeandroid.annotation.Table;

/**
 * 
 * @ClassName: SearchResult.java
 * @Description:
 * @author: cj
 * @date: 2016年3月22日 上午11:00:57
 */
@Table(name = "searchResult")
public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "SearchResult";
	/**
	 * 用户类型
	 */
	// "疑"用户
	public static final int USER_TYPE_IMPEACH = 0;
	// "真"用户
	public static final int USER_TYPE_NORMAL = 1;
	// "腕"用户
	public static final int USER_TYPE_VIP = 2;

	/**
	 * 搜索类型
	 */
	private static final int SEARCH_TYPE_1 = 1;

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

	/** 关键字相匹配 **/
	public List<MatchBean> matchList;

}
