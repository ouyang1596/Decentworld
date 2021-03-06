/**
 * 
 */
package cn.sx.decentworld.bean;

import java.io.Serializable;

import cn.sx.decentworld.entity.db.ContactUser;

/**
 * 
 * @ClassName: ContactUser.java
 * @Description: 主页面联系人 用户信息实体类
 * @author: cj
 * @date: 2015年7月8日 下午1:25:38
 */

public class PickContactUser extends ContactUser implements  Serializable 
{
	private Boolean checked;

	


//	public PickContactUser(String username, Bitmap avatar, String nick,
//			String sortLetters, Boolean checked) {
//		super(username, avatar, nick, sortLetters);
//		this.checked = checked;
//	}
//
//
//	public PickContactUser(String username, Bitmap avatar, String nick) {
//		super(username, avatar, nick);
//	}


	public PickContactUser() {
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

	
	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	/* (non-Javadoc)
//	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
//	 */
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		// TODO Auto-generated method stub
//		dest.writeString(username);  
//		dest.writeb( avatar);// 头像
//		dest.writeString( nick);// 名字
//		dest.writeString( sortLetters);// 排序的字母
//	}

}
