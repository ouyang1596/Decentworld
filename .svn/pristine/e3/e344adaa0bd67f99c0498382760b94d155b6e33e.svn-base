/**
 * 
 */
package cn.sx.decentworld.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.bean.UserInfoField.FieldGroup;

/**
 * @author Sammax 个人信息对象
 */
public class SelfUserInfo
{
    private String dwID;
    private String versionNum;
    private List<SelfUserField> fields = new ArrayList<SelfUserField>(60);
    private Map<Field, SelfUserField> map = null;

    private void initIndex()
    {
        map = new HashMap<Field, SelfUserField>(60);
        for (SelfUserField field : fields)
        {
            map.put(Field.getField(field.getFieldCode()), field);
        }
    }

    public SelfUserField getSelfUserField(Field field)
    {
        if (map == null)
        {
            initIndex();
        }
        return map.get(field);
    }

    public String getFieldValue(Field field)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf == null)
            return null;
        return sf.getFieldValue();
    }

    public String getFieldName(Field field)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf == null)
            return null;
        return sf.getFieldName();
    }

    public FieldGroup getFieldGroup(Field field)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf == null)
            return null;
        return FieldGroup.getFieldGroup(sf.getGroup());
    }

    public String getFieldGroupName(Field field)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf == null)
            return null;
        return FieldGroup.getFieldGroup(sf.getGroup()).getGroupName();
    }

    public boolean isDispaly(Field field)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf == null)
            return false;
        return sf.isDisplayAuth();
    }

    public boolean isAnonymous(Field field)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf == null)
            return false;
        return sf.isAnonymousAuth();
    }

    public String getDwID()
    {
        return dwID;
    }

    public void setDwID(String dwID)
    {
        this.dwID = dwID;
    }

    public String getVersionNum()
    {
        return versionNum;
    }

    public void setVersionNum(String versionNum)
    {
        this.versionNum = versionNum;
    }

    public List<SelfUserField> getFields()
    {
        return fields;
    }

    public void setFields(List<SelfUserField> fields)
    {
        this.fields = fields;
    }
    
    //////////////////////////
    public void setFieldValue(Field field,String value)
    {
        SelfUserField sf = getSelfUserField(field);
        if (sf != null)
        {
            sf.setFieldValue(value);
        }
    }
}
