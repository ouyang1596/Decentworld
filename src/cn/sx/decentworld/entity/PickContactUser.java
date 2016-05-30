/**
 * 
 */
package cn.sx.decentworld.entity;

import java.io.Serializable;

import cn.sx.decentworld.entity.db.ContactUser;

/**
 * 
 * @ClassName: ContactUser.java
 * @Description: 主页面联系人 用户信息实体类
 * @author: cj
 * @date: 2015年7月8日 下午1:25:38
 */

public class PickContactUser extends ContactUser implements Serializable
{
    private Boolean checked;

    public PickContactUser()
    {
        super();
    }

    public Boolean getChecked()
    {
        return checked;
    }

    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }


}
