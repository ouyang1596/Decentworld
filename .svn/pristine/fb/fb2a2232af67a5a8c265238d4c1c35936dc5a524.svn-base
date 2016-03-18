/**
 * 
 */
package cn.sx.decentworld.bean;

import cn.sx.decentworld.DecentWorldApp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @ClassName: RecommendBenefitList.java
 * @Description: 推荐别人所得收益的展示列表
 * @author: cj
 * @date: 2015年12月20日 下午4:16:33
 */
@Table(name = "recommendBenefitList")
public class RecommendBenefitList extends Model
{
	/**
	 * 用户ID
	 */
	@Column(name = "userID")
	String userID;

	/**
	 * 对方ID
	 */
	@Column(name = "otherID")
	String otherID;

	/**
	 * 对方名字（优先级：备注、实名、昵称）
	 */
	@Column(name = "name")
	String name;

	/**
	 * 从该人获得总收益
	 */
	@Column(name = "benefit")
	float benefit;

	/**
	 * 从该人应该获得的总收益
	 */
	@Column(name = "amount")
	float amount;

	/**
	 * 状态（0 [还不是朋友] | 1 [已经是好友了]）
	 */
	@Column(name = "isFriend")
	boolean isFriend;

	/**
	 * 是否注册（0[否] | 1[是]） 如果0，则代表没有注册 如果1，则代表注册了
	 */
	@Column(name = "isRegister")
	boolean isRegister;
	
	/**
	 * 还没有提现的金额
	 */
	@Column(name = "stored")
	float stored;
	

	/**
	 * 
	 */
	public RecommendBenefitList()
	{
		this.userID = DecentWorldApp.getInstance().getDwID();
		this.otherID = "";
		this.name = "";
		this.amount = 0;
		this.benefit = 0;
		this.isFriend = false;
		this.isRegister = false;
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
	 * @return the otherID
	 */
	public String getOtherID()
	{
		return otherID;
	}

	/**
	 * @param otherID the otherID to set
	 */
	public void setOtherID(String otherID)
	{
		this.otherID = otherID;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the benefit
	 */
	public float getBenefit()
	{
		return benefit;
	}

	/**
	 * @param benefit the benefit to set
	 */
	public void setBenefit(float benefit)
	{
		this.benefit = benefit;
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
	 * @return the isFriend
	 */
	public boolean isFriend()
	{
		return isFriend;
	}

	/**
	 * @param isFriend the isFriend to set
	 */
	public void setFriend(boolean isFriend)
	{
		this.isFriend = isFriend;
	}

	/**
	 * @return the isRegister
	 */
	public boolean isRegister()
	{
		return isRegister;
	}

	/**
	 * @param isRegister the isRegister to set
	 */
	public void setRegister(boolean isRegister)
	{
		this.isRegister = isRegister;
	}

	/**
	 * @return the stored
	 */
	public float getStored()
	{
		return stored;
	}

	/**
	 * @param stored the stored to set
	 */
	public void setStored(float stored)
	{
		this.stored = stored;
	}
	
	
	
}
