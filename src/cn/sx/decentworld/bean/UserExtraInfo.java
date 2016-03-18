/**
 * 
 */
package cn.sx.decentworld.bean;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.activity.RechargePayMethodActivity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: UserExtraInfo.java
 * @Description: 用户额外信息
 * @author: cj
 * @date: 2016年1月7日 上午11:04:26
 */
@Table(name = "userExtraInfo")
public class UserExtraInfo extends Model
{
    public static final int ALIPAY = RechargePayMethodActivity.ALIPAY;// 支付宝
    public static final int WX = RechargePayMethodActivity.WX;// 微信
    /** 用户ID **/
    @Column(name = "userID")
    private String userID;

    /** 用户小头像 **/
    @Column(name = "iconSmall")
    private String iconSmall;
    /** 头像 **/
    @Column(name = "icon")
    private String icon;
    /** 用户头像2 **/
    @Column(name = "icon2")
    private String icon2;
    /** 用户头像3 **/
    @Column(name = "icon3")
    private String icon3;


    /** 是否同意自动转账 true同意 false不同意 **/
    @Column(name = "autoTransfer")
    private Boolean autoTransfer;

    /** 返现账号类型 （0为支付宝 1为微信） **/
    @Column(name = "accountType")
    private int accountType;
    /** 返现账号 **/
    @Column(name = "accountName")
    private String accountName;

    /** 推荐历史总收益 **/
    @Column(name = "recomTotalBenefit")
    private float recomTotalBenefit;
    /** 作为别人的贵人总收益 **/
    @Column(name = "grTotalBenefit")
    private float grTotalBenefit;

    /** 推荐存储总收益 **/
    @Column(name = "recomStoredBenefit")
    private float recomStoredBenefit;
    
    
    /**
     * 高级设置
     */
    //是否接受服务器推送的消息 
    @Column(name = "acceptPush")
    private Boolean acceptPush;
    //系统推送审核信息 
    @Column(name = "acceptCheckPush")
    private Boolean acceptCheckPush;
    //陌生人声音提示
    @Column(name = "strangerNotice")
    private Boolean strangerNotice;
    //陌生人声音提示朋友声音提示
    @Column(name = "friendNotice")
    private Boolean friendNotice;
    
    /**
     * 暂时用不到
     */
    //陌生人消息提醒 
    @Column(name = "noticeStrangerMessage")
    private Boolean noticeStrangerMessage;

    /**
     * 构造函数
     */
    public UserExtraInfo()
    {
        this.userID = DecentWorldApp.getInstance().getDwID();
        iconSmall = "";
        icon = "";
        icon2 = "";
        icon3 = "";
        accountType = -1;
        accountName = "";
        recomTotalBenefit = -1;
        grTotalBenefit = -1;
        recomStoredBenefit = -1;
    }

    /**
     * @return the userID
     */
    public String getUserID()
    {
        return userID;
    }

    /**
     * @param userID
     *            the userID to set
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    /**
     * @return the icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * @return the icon2
     */
    public String getIcon2()
    {
        return icon2;
    }

    /**
     * @param icon2
     *            the icon2 to set
     */
    public void setIcon2(String icon2)
    {
        this.icon2 = icon2;
    }

    /**
     * @return the icon3
     */
    public String getIcon3()
    {
        return icon3;
    }

    public void setIcon3(String icon3)
    {
        this.icon3 = icon3;
    }

    public String getIconSmall()
    {
        return iconSmall;
    }

    public void setIconSmall(String iconSmall)
    {
        this.iconSmall = iconSmall;
    }

    public Boolean getNoticeStrangerMessage()
    {
        return noticeStrangerMessage;
    }

    public void setNoticeStrangerMessage(Boolean noticeStrangerMessage)
    {
        this.noticeStrangerMessage = noticeStrangerMessage;
    }

    public Boolean getAcceptPush()
    {
        return acceptPush;
    }

    /**
     * @param acceptPush
     *            the acceptPush to set
     */
    public void setAcceptPush(Boolean acceptPush)
    {
        this.acceptPush = acceptPush;
    }

    /**
     * @return the autoTransfer
     */
    public Boolean getAutoTransfer()
    {
        return autoTransfer;
    }

    /**
     * @param autoTransfer
     *            the autoTransfer to set
     */
    public void setAutoTransfer(Boolean autoTransfer)
    {
        this.autoTransfer = autoTransfer;
    }

    /**
     * @return the accountType
     */
    public int getAccountType()
    {
        return accountType;
    }

    /**
     * @param accountType
     *            the accountType to set
     */
    public void setAccountType(int accountType)
    {
        this.accountType = accountType;
    }

    /**
     * @return the accountName
     */
    public String getAccountName()
    {
        return accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * @return the acceptCheckPush
     */
    public Boolean getAcceptCheckPush()
    {
        return acceptCheckPush;
    }

    public Boolean getStrangerNotice()
    {
        return strangerNotice;
    }

    public void setStrangerNotice(Boolean strangerNotice)
    {
        this.strangerNotice = strangerNotice;
    }

    public Boolean getFriendNotice()
    {
        return friendNotice;
    }

    public void setFriendNotice(Boolean friendNotice)
    {
        this.friendNotice = friendNotice;
    }

    public void setAcceptCheckPush(Boolean acceptCheckPush)
    {
        this.acceptCheckPush = acceptCheckPush;
    }

    /**
     * @return the recomTotalBenefit
     */
    public float getRecomTotalBenefit()
    {
        return recomTotalBenefit;
    }

    /**
     * @param recomTotalBenefit
     *            the recomTotalBenefit to set
     */
    public void setRecomTotalBenefit(float recomTotalBenefit)
    {
        this.recomTotalBenefit = recomTotalBenefit;
    }

    /**
     * @return the grTotalBenefit
     */
    public float getGrTotalBenefit()
    {
        return grTotalBenefit;
    }

    /**
     * @param grTotalBenefit
     *            the grTotalBenefit to set
     */
    public void setGrTotalBenefit(float grTotalBenefit)
    {
        this.grTotalBenefit = grTotalBenefit;
    }

    /**
     * @return the recomStoredBenefit
     */
    public float getRecomStoredBenefit()
    {
        return recomStoredBenefit;
    }

    /**
     * @param recomStoredBenefit
     *            the recomStoredBenefit to set
     */
    public void setRecomStoredBenefit(float recomStoredBenefit)
    {
        this.recomStoredBenefit = recomStoredBenefit;
    }

    /**
     * 通过ID号查找单条用户记录并返回
     * 
     * @param userID
     * @return
     */
    public static UserExtraInfo queryBy(String userID)
    {
        return new Select().from(UserExtraInfo.class).where("userID = ?", userID).executeSingle();
    }

}
