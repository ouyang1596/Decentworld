/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: RecommendUnavaliable.java
 * @Description: 已经推荐过的人，但是对方没有注册或者没有添加为朋友
 * @author: cj
 * @date: 2016年1月28日 下午8:37:09
 */
public class RecommendUnavailable
{
    /**
     * 被推荐人的状态
     */
    public static final int STATUS_UN_REGISTER = 4;
    public static final int STATUS_UN_FRIEND = 0;
    
    //对方电话号码
    private String phoneNum;
    //推荐金额
    private float amount;
    //被推荐人状态（若为0则说明用户已注册，但未加好友，4为用户尚未注册）
    private int status;
    
    /**
     * 
     */
    public RecommendUnavailable()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the phoneNum
     */
    public String getPhoneNum()
    {
        return phoneNum;
    }

    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
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
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
    
}
