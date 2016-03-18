/**
 * 
 */
package cn.sx.decentworld.bean.manager;

import com.activeandroid.query.Select;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: UserInfoManager.java
 * @Description: 用户信息管理者
 * @author: cj
 * @date: 2015年11月22日 下午6:59:12
 */
/**
 * 设计成单聊模式
 * 1.懒汉式单例类.在第一次调用的时候实例化自己   （线程不安全）
 * 2.饿汉式单例类.在类初始化时，已经自行实例化   （线程安全）
 */
public class UserInfoManager
{
	private static final String TAG = "UserInfoManager";
	/**
	 * 唯一实例,懒汉模式
	 */
	private static UserInfo info;
	/**
	 * 私有方法，防止通过构造函数新建实例
	 */
	private UserInfoManager()
	{
		
	}
	
	/**
	 * 在MainActivity中
	 * 将个人信息初始化到内存中
	 * @param userInfo
	 */
	public static void initUserInfo(UserInfo userInfo)
	{
		if(CommUtil.isNotBlank(userInfo))
			info = userInfo;
		else
			LogUtils.e(TAG, "初始化用户信息失败，传入的参数为空");
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static final UserInfo getUserInfoInstance()
	{
		if(info==null)
		{
			String dwID = DecentWorldApp.getInstance().getDwID();
			UserInfo user = queryByDwID(dwID);
			if(user!=null)
			{
				info = user;
			}
			else
			{
				info = new UserInfo();
			}
		}
		return info;
	}
	
	/**
	 * 查询指定dwID号的记录并返回结果
	 * @param dwID
	 */
	private static UserInfo queryByDwID(String dwID)
	{
		return new Select().from(UserInfo.class).where("userId=?", dwID).executeSingle();
	}

}
