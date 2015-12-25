/**
 * 
 */
package cn.sx.decentworld.bean;

import cn.sx.decentworld.DecentWorldApp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: RecommendBenefitDetail.java
 * @Description: 推荐别人所得收益的展示详情
 * @author: cj
 * @date: 2015年12月20日 下午4:29:07
 */
@Table(name = "recommendBenefitDetail")
public class RecommendBenefitDetail extends Model
{
	/**
	 * 用户ID
	 */
	@Column(name = "userID")
	String userID;
	
	/**
	 * 本单收益
	 */
	@Column(name = "amount")
	float amount;
	
	/**
	 * 获得收益收件
	 */
	@Column(name = "time")
	String time;
	
	/**
	 * 状态(是否转账)
	 */
	@Column(name = "status")
	boolean status;

	/**
	 * 
	 */
	public RecommendBenefitDetail()
	{
		this.userID = DecentWorldApp.getInstance().getDwID();
		this.amount = 0;
		this.time = "";
		this.status = false;
	}

	/**
	 * @return the userID
	 */
	public String getUserID()
	{
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	/**
	 * @return the amount
	 */
	public float getAmount()
	{
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount)
	{
		this.amount = amount;
	}

	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status)
	{
		this.status = status;
	}
	

}
