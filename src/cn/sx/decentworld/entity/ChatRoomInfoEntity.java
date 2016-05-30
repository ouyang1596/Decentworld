/**
 * 
 */
package cn.sx.decentworld.entity;

/**
 * @ClassName: ChatRoomInfoEntity.java
 * @Description:进入聊天室传递参数的实体类
 * @author: cj
 * @date: 2016年4月22日 下午1:06:05
 */
public class ChatRoomInfoEntity
{
    private String roomWealth;
    private String roomID;
    private String subjectName;
    private String roomNotice;
    private String roomBackground;
    private String ownerIcon;
    private String roomOwnerNickName;
    private String role;
    private int curNum;
    private String chargeAmount;
    private String ownerID;

    /**
     * 
     */
    public ChatRoomInfoEntity()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the roomWealth
     */
    public String getRoomWealth()
    {
        return roomWealth;
    }

    /**
     * @param roomWealth
     *            the roomWealth to set
     */
    public void setRoomWealth(String roomWealth)
    {
        this.roomWealth = roomWealth;
    }

    /**
     * @return the roomID
     */
    public String getRoomID()
    {
        return roomID;
    }

    /**
     * @param roomID
     *            the roomID to set
     */
    public void setRoomID(String roomID)
    {
        this.roomID = roomID;
    }

    /**
     * @return the subjectName
     */
    public String getSubjectName()
    {
        return subjectName;
    }

    /**
     * @param subjectName
     *            the subjectName to set
     */
    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    /**
     * @return the roomNotice
     */
    public String getRoomNotice()
    {
        return roomNotice;
    }

    /**
     * @param roomNotice
     *            the roomNotice to set
     */
    public void setRoomNotice(String roomNotice)
    {
        this.roomNotice = roomNotice;
    }

    /**
     * @return the roomBackground
     */
    public String getRoomBackground()
    {
        return roomBackground;
    }

    /**
     * @param roomBackground
     *            the roomBackground to set
     */
    public void setRoomBackground(String roomBackground)
    {
        this.roomBackground = roomBackground;
    }

    /**
     * @return the ownerIcon
     */
    public String getOwnerIcon()
    {
        return ownerIcon;
    }

    /**
     * @param ownerIcon
     *            the ownerIcon to set
     */
    public void setOwnerIcon(String ownerIcon)
    {
        this.ownerIcon = ownerIcon;
    }

    /**
     * @return the roomOwnerNickName
     */
    public String getRoomOwnerNickName()
    {
        return roomOwnerNickName;
    }

    /**
     * @param roomOwnerNickName
     *            the roomOwnerNickName to set
     */
    public void setRoomOwnerNickName(String roomOwnerNickName)
    {
        this.roomOwnerNickName = roomOwnerNickName;
    }

    /**
     * @return the role
     */
    public String getRole()
    {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * @return the curNum
     */
    public int getCurNum()
    {
        return curNum;
    }

    /**
     * @param curNum
     *            the curNum to set
     */
    public void setCurNum(int curNum)
    {
        this.curNum = curNum;
    }

    /**
     * @return the chargeAmount
     */
    public String getChargeAmount()
    {
        return chargeAmount;
    }

    /**
     * @param chargeAmount
     *            the chargeAmount to set
     */
    public void setChargeAmount(String chargeAmount)
    {
        this.chargeAmount = chargeAmount;
    }

    /**
     * @return the ownerID
     */
    public String getOwnerID()
    {
        return ownerID;
    }

    /**
     * @param ownerID
     *            the ownerID to set
     */
    public void setOwnerID(String ownerID)
    {
        this.ownerID = ownerID;
    }

    @Override
    public String toString()
    {
        return "ChatRoomInfoEntity [roomWealth=" + roomWealth + ", roomID=" + roomID + ", subjectName=" + subjectName + ", roomNotice=" + roomNotice + ", roomBackground=" + roomBackground
                + ", ownerIcon=" + ownerIcon + ", roomOwnerNickName=" + roomOwnerNickName + ", role=" + role + ", curNum=" + curNum + ", chargeAmount=" + chargeAmount + ", ownerID=" + ownerID + "]";
    }

}
