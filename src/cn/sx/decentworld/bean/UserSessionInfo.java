/**
 * 
 */
package cn.sx.decentworld.bean;

import cn.sx.decentworld.common.CommUtil;

/**
 * @ClassName: UserSessionInfo.java
 * @Description: 消息发附加的额外信息，接收时由服务器添加，发送消息时自己添加，用于向会话列表传参
 * @author: cj
 * @date: 2015年12月30日 下午3:42:57
 */
public class UserSessionInfo extends ContactUser
{
    /**
     * 
     */
    public UserSessionInfo()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 构造函数
     * @param otherID 对方的ID
     * @param otherNickName 对方昵称
     * @param OtherWorth 对方身价
     */
    public UserSessionInfo(String otherID,String otherNickName,float OtherWorth)
    {
        super(otherID, otherNickName, OtherWorth);
    }


}
