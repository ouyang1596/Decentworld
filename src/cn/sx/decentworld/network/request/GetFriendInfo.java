/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: SetFriendInfo.java
 * @Description: 获取朋友相关信息 1. 获取联系人列表 2.
 * @author: cj
 * @date: 2015年9月25日 上午10:30:58
 */
@EBean
public class GetFriendInfo
{
	private static String TAG = "GetFriendInfo";
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	private SendUrl sendUrl;

	@AfterViews
	void init()
	{
		sendUrl = new SendUrl(context);
	}

	/**
	 * 从服务器获取联系人列表，存在本地数据库
	 * @param dwID
	 */
	public void getContactUsersList(final String dwID)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		LogUtils.i(TAG, "getContactUsersList...begin,dwID=" + dwID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/getFriendList", Method.GET, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, ResultBean bean)
			{
				LogUtils.i(TAG, "getContactUsersList...msg.getResultCode=" + bean.getResultCode() + ",getData=" + bean.getData() + ",msg.getMsg=" + bean.getMsg());
				if (bean.getResultCode() == 2222)// 获取成功
				{
					// 清空数据库
					ContactUser.deleteAll();
					List<ContactUser> contactUsers = JSON.parseArray(bean.getData().toString(), ContactUser.class);
					contactUsers = removeDuplicate(contactUsers);
					for (ContactUser user : contactUsers)
					{
						user.setUserID(dwID);
						user.save();
					}
					LogUtils.i(TAG, "getContactUsersList...success");
				} else
				{
					LogUtils.i(TAG, "getContactUsersList...failure,cause by:" + bean.getMsg());
				}
			}

			@Override
			public void onFailure(String e)
			{
				LogUtils.i(TAG, "getContactUsersList...onFailure,cause by:" + e);
			}

		});
	}

	// 删除list相同的元素
	private List removeDuplicate(List<ContactUser> list)
	{
		for (int i = 0; i < list.size() - 1; i++)
		{
			for (int j = list.size() - 1; j > i; j--)
			{
				if (list.get(j).getDwID().equals(list.get(i).getDwID()))
				{
					list.remove(j);
				}
			}
		}
		return list;
	}
}
