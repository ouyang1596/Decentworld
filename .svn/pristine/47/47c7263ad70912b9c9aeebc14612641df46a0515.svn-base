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
	 * 账单的状态
	 */
	public static final int STATUS_TYPE_SUCCESS = 0;//成功
	public static final int STATUS_TYPE_FAILURE = 1;//失败
	public static final int STATUS_TYPE_PROCESSING = 2;//处理中
	public static final int STATUS_TYPE_TEMPORARY_STORED = 3;//转账金额不足，暂时存储到服务器
	public static final int STATUS_TYPE_STORED= 4;//用户同意暂存服务器
	
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
	 * 状态(是否转账)[已经废弃的字段]
	 */
	@Column(name = "status")
	boolean status;
	
	/**
	 * 状态类型
	 */
	@Column(name = "statusType")
	int statusType;

	/**
	 * 
	 */
	public RecommendBenefitDetail()
	{
		this.userID = DecentWorldApp.getInstance().getDwID();
		this.amount = 0;
		this.time = "";
		this.status = false;
		this.statusType = -1;
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

	/**
	 * @return the statusType
	 */
	public int getStatusType()
	{
		return statusType;
	}

	/**
	 * @param statusType the statusType to set
	 */
	public void setStatusType(int statusType)
	{
		this.statusType = statusType;
	}


}
