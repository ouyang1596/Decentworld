/**
 * 
 */
package cn.sx.decentworld.entity;

import java.io.Serializable;

/**
 * @ClassName: LaunchChat.java
 * @Description: 开启对话界面需要传递的对象，ChatActivity中需要用到对象中的属性
 * @author: cj
 * @date: 2016年4月8日 上午9:24:50
 */
public class LaunchChatEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    //对方ID
    public String otherID;
    //对方要显示的名字
    public String otherShowName;
    //对方身价
    public float otherWorth;
    //用户类型
    public int userType;
    //聊天类型
    public int chatType;
    //聊天关系
    public int chatRelationship;

    
    /**
     * 构造函数
     */
    public LaunchChatEntity(String otherID, String otherShowName, float otherWorth, int chatType, int chatRelationship, int userType)
    {
        this.otherID = otherID;
        this.otherShowName = otherShowName;
        this.otherWorth = otherWorth;
        this.chatType = chatType;
        this.chatRelationship = chatRelationship;
        this.userType = userType;
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
     * @return the otherShowName
     */
    public String getOtherShowName()
    {
        return otherShowName;
    }
    /**
     * @param otherShowName the otherShowName to set
     */
    public void setOtherShowName(String otherShowName)
    {
        this.otherShowName = otherShowName;
    }
    /**
     * @return the otherWorth
     */
    public float getOtherWorth()
    {
        return otherWorth;
    }
    /**
     * @param otherWorth the otherWorth to set
     */
    public void setOtherWorth(float otherWorth)
    {
        this.otherWorth = otherWorth;
    }
    /**
     * @return the chatType
     */
    public int getChatType()
    {
        return chatType;
    }
    /**
     * @param chatType the chatType to set
     */
    public void setChatType(int chatType)
    {
        this.chatType = chatType;
    }
    /**
     * @return the chatRelationship
     */
    public int getChatRelationship()
    {
        return chatRelationship;
    }
    /**
     * @param chatRelationship the chatRelationship to set
     */
    public void setChatRelationship(int chatRelationship)
    {
        this.chatRelationship = chatRelationship;
    }
    /**
     * @return the userType
     */
    public int getUserType()
    {
        return userType;
    }
    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType)
    {
        this.userType = userType;
    }
    
    
    @Override
    public String toString()
    {
        return "LaunchChatEntity [otherID=" + otherID + ", otherShowName=" + otherShowName + ", otherWorth=" + otherWorth + ", chatType=" + chatType + ", chatRelationship=" + chatRelationship
                + ", userType=" + userType + "]";
    }
    
    
    
    
    
    
    
    
    
}
