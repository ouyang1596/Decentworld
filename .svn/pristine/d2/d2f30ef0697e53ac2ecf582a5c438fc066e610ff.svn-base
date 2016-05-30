/**
 * 
 */
package cn.sx.decentworld.entity;

/**
 * @ClassName: NewMomentComment
 * @Description: 新的朋友圈评论
 * @author: Jackchen
 * @date: 2016年5月18日 下午5:09:31
 */
public class NewMomentComment implements Comparable<NewMomentComment>
{
    private static final String TAG = "NewMomentComment";
    
    private int orderNum;
    private String momentID;
    private String dwID;
    public NewMomentComment()
    {
    }
    
    public NewMomentComment(int orderNum,String momentID,String dwID)
    {
        this.orderNum = orderNum;
        this.momentID = momentID;
        this.dwID = dwID;
    }

    /**
     * @return the orderNum
     */
    public int getOrderNum()
    {
        return orderNum;
    }

    /**
     * @param orderNum the orderNum to set
     */
    public void setOrderNum(int orderNum)
    {
        this.orderNum = orderNum;
    }

    /**
     * @return the momentID
     */
    public String getMomentID()
    {
        return momentID;
    }

    /**
     * @param momentID the momentID to set
     */
    public void setMomentID(String momentID)
    {
        this.momentID = momentID;
    }

    /**
     * @return the dwID
     */
    public String getDwID()
    {
        return dwID;
    }

    /**
     * @param dwID the dwID to set
     */
    public void setDwID(String dwID)
    {
        this.dwID = dwID;
    }

    @Override
    public String toString()
    {
        return "NewMomentComment [orderNum=" + orderNum + ", momentID=" + momentID + ", dwID=" + dwID + "]";
    }

    @Override
    public int compareTo(NewMomentComment another)
    {
        return (this.orderNum-another.orderNum);
    }
    
    
}
