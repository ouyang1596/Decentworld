/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;

/**
 * @ClassName: SupporterBean.java
 * @Description:
 * @author: yj
 * @date: 2016年1月14日 上午9:16:53
 */
public class SupporterAndCSBean implements Serializable {
	private static final long serialVersionUID = -3137516199084709745L;
	public String id;// id
	public int userType;// 类型 0疑1真2腕
	public int worth;// 身价
	public String occupation;// 职业
	public String showName;// 昵称
	public String gender;// 性别

	@Override
	public String toString() {
		return "SupporterBean [id=" + id + ", userType=" + userType + ", worth=" + worth + ", occupation=" + occupation
				+ ", showName=" + showName + ", gender=" + gender + "]";
	}

}
