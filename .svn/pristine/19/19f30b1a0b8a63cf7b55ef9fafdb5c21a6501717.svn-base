/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: NewBenefit.java
 * @Description: 新的收益实体类，主要是推荐收益
 * @author: cj
 * @date: 2016年1月25日 上午9:40:33
 */
public class NewBenefit
{
    
    /**
     * msg={ "account": "WXPAY", "acctualBenefit": "1.0", "benefit": "1.0",
     * "dwID": "044292", "enough": "true", "fee": "1.0", "nick": "陈杰",
     * "stored": "true" //}
     */
    
    /** 用户ID(本地赋值) **/
    private String userID;
    
    /** 对方的昵称 **/
    private String nickName;
    
    /** 对方ID **/
    private String otherID;
    
    /** 对方支付的账号类型 （ALIPAY、WXPAY）**/
    private String account;
    
    /** 本次应得的收益   **/
    private float benefit;
    
    /** 如果时自动转账，需要扣除的手续费 **/
    private float fee;
    
    /** 扣除手续费最终获得的收益  **/
    private float acctualBenefit;
    
    /** 是否够手续费 **/
    private boolean enough;
    
    /** 
     * true:1）用户关闭开发 ，手动转账 ；2）用户的收益不足以缴纳手续费，手动转账 
     * false:用户打开开发，自动转账
     **/
    private boolean stored;
    
    
    /**
     * 
     */
    public NewBenefit()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param nickName
     * @param otherID
     * @param account
     * @param benefit
     * @param acctualBenefit
     * @param fee
     * @param enough
     * @param stored
     */
    public NewBenefit(String nickName, String otherID, String account, float benefit, float acctualBenefit, float fee, boolean enough, boolean stored)
    {
        super();
        this.nickName = nickName;
        this.otherID = otherID;
        this.account = account;
        this.benefit = benefit;
        this.acctualBenefit = acctualBenefit;
        this.fee = fee;
        this.enough = enough;
        this.stored = stored;
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
     * @return the account
     */
    public String getAccount()
    {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account)
    {
        this.account = account;
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
     * @return the acctualBenefit
     */
    public float getAcctualBenefit()
    {
        return acctualBenefit;
    }

    /**
     * @param acctualBenefit the acctualBenefit to set
     */
    public void setAcctualBenefit(float acctualBenefit)
    {
        this.acctualBenefit = acctualBenefit;
    }

    /**
     * @return the fee
     */
    public float getFee()
    {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(float fee)
    {
        this.fee = fee;
    }

    /**
     * @return the enough
     */
    public boolean isEnough()
    {
        return enough;
    }

    /**
     * @param enough the enough to set
     */
    public void setEnough(boolean enough)
    {
        this.enough = enough;
    }

    /**
     * @return the stored
     */
    public boolean isStored()
    {
        return stored;
    }

    /**
     * @param stored the stored to set
     */
    public void setStored(boolean stored)
    {
        this.stored = stored;
    }
    
    
    


}
