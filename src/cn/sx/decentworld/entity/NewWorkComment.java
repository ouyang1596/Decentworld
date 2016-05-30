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
public class NewWorkComment implements Comparable<NewWorkComment>
{
    private static final String TAG = "NewWorkComment";
    
    private int orderNum;
    private String workID;
    private String dwID;
    
    /**
     * 
     */
    public NewWorkComment()
    {
    }
    
    /**
     * 
     */
    public NewWorkComment(int orderNum,String workID,String dwID)
    {
        this.orderNum = orderNum;
        this.workID = workID;
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
    
    

    /**
     * @return the workID
     */
    public String getWorkID()
    {
        return workID;
    }

    /**
     * @param workID the workID to set
     */
    public void setWorkID(String workID)
    {
        this.workID = workID;
    }

   
    @Override
    public String toString()
    {
        return "NewWorkComment [orderNum=" + orderNum + ", workID=" + workID + ", dwID=" + dwID + "]";
    }

    @Override
    public int compareTo(NewWorkComment another)
    {
        return (this.orderNum-another.orderNum);
    }
    
    
}
