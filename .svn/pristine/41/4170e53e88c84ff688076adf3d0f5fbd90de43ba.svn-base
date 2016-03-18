/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import com.activeandroid.annotation.Table;

/**
 * @ClassName: SearchResult.java
 * @Description: 搜索结果
 * @author: yj
 * @date: 2015年12月10日 上午9:58:37
 */
@Table(name = "searchResult")
public class SearchResult {
	private static final String TAG = "SearchResult";

	/** 存疑用户 **/
	private static final int USER_TYPE_IMPEACH = 0;
	/** 普通用户 **/
	private static final int USER_TYPE_NORMAL = 1;
	/** 大腕用户 **/
	private static final int USER_TYPE_VIP = 2;

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
