/**
 * 
 */
package cn.sx.decentworld.entity;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author Sammax
 *
 */
public class SelfUserField implements Comparable<SelfUserField>
{
    private String fieldName;
    private String fieldValue;
    private boolean displayAuth;
    private boolean anonymousAuth;
    private int orderNum;
    private int group;
    private int fieldCode;
    
    @JSONField(serialize = false)
    private boolean isModify = false;

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldValue()
    {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue)
    {
        this.fieldValue = fieldValue;
    }

    public boolean isDisplayAuth()
    {
        return displayAuth;
    }

    public void setDisplayAuth(boolean displayAuth)
    {
        this.displayAuth = displayAuth;
    }

    public boolean isAnonymousAuth()
    {
        return anonymousAuth;
    }

    public void setAnonymousAuth(boolean anonymousAuth)
    {
        this.anonymousAuth = anonymousAuth;
    }

    public int getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(int orderNum)
    {
        this.orderNum = orderNum;
    }

    public int getGroup()
    {
        return group;
    }

    public void setGroup(int group)
    {
        this.group = group;
    }

    public int getFieldCode()
    {
        return fieldCode;
    }

    public void setFieldCode(int fieldCode)
    {
        this.fieldCode = fieldCode;
    }

    /**
     * @return the isModify
     */
    public boolean isModify()
    {
        return isModify;
    }

    /**
     * @param isModify the isModify to set
     */
    public void setModify(boolean isModify)
    {
        this.isModify = isModify;
    }

    @Override
    public int compareTo(SelfUserField another)
    {
        return (this.orderNum - another.orderNum);
    }

    @Override
    public String toString()
    {
        return "SelfUserField [fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", displayAuth=" + displayAuth + ", anonymousAuth=" + anonymousAuth + ", orderNum=" + orderNum + ", group="
                + group + ", fieldCode=" + fieldCode + ", isModify=" + isModify + "]";
    }

   
    

}
