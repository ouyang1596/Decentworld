/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
public class GetFriendInfo {
	private static String TAG = "GetFriendInfo";
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	private SendUrl sendUrl;

	@AfterViews
	void init() {
		sendUrl = new SendUrl(context);
	}

	/**
	 * 从服务器获取联系人列表，存在本地数据库
	 * 
	 * @param dwID
	 */
	public void getContactUsersList(final String dwID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		LogUtils.i(TAG, "getContactUsersList...begin,dwID=" + dwID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/getFriendList", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean bean) {
				LogUtils.i(TAG, "getContactUsersList...msg.getResultCode=" + bean.getResultCode() + ",getData=" + bean.getData()
						+ ",msg.getMsg=" + bean.getMsg());
				if (bean.getResultCode() == 2222)// 获取成功
				{
					// 清空数据库
					ContactUser.deleteAll();
					String jsonString = bean.getData().toString();
					JSONObject jsonObject = JSON.parseObject(jsonString);
					JSONArray array = jsonObject.getJSONArray("result");
					if (array.size() > 0) {
						String userID = DecentWorldApp.getInstance().getDwID();
						for (int i = 0; i < array.size(); i++) {
							ContactUser temp = new ContactUser();
							JSONObject object = array.getJSONObject(i);
							temp.setUserID(userID);
							temp.setFriendID(object.getString("id"));
							temp.setShowName(object.getString("showName"));
							temp.setGender(object.getString("gender"));
							temp.setOccupation(object.getString("occupation"));
							temp.setUserType(object.getIntValue("userType"));
							temp.setWorth(object.getFloatValue("worth"));
							temp.save();
						}
						LogUtils.i(TAG, "getContactUsersList...success");
					} else {
						LogUtils.i(TAG, "getContactUsersList...获取联系人列表为空");
					}
				} else if (bean.getResultCode() == 3333) {
					LogUtils.i(TAG, "getContactUsersList...获取联系人列表为空" + bean.getMsg());
				} else {
					LogUtils.i(TAG, "getContactUsersList...failure,cause by:" + bean.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.i(TAG, "getContactUsersList...onFailure,cause by:" + e);
			}

		});
	}

	/**
	 * 从服务器获取联系人列表，存在本地数据库
	 * 
	 * @param dwID
	 */
	public void getContactUsersList(final String dwID, final Handler handler, final int requestCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		LogUtils.i(TAG, "getContactUsersList...begin,dwID=" + dwID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/getFriendList", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean bean) {
				LogUtils.i(TAG, "getContactUsersList...msg.getResultCode=" + bean.getResultCode() + ",getData=" + bean.getData()
						+ ",msg.getMsg=" + bean.getMsg());
				if (bean.getResultCode() == 2222)// 获取成功
				{
					// 清空数据库
					ContactUser.deleteAll();
					String jsonString = bean.getData().toString();
					JSONObject jsonObject = JSON.parseObject(jsonString);
					JSONArray array = jsonObject.getJSONArray("result");
					if (array.size() > 0) {
						String userID = DecentWorldApp.getInstance().getDwID();
						for (int i = 0; i < array.size(); i++) {
							ContactUser temp = new ContactUser();
							JSONObject object = array.getJSONObject(i);
							temp.setUserID(userID);
							temp.setFriendID(object.getString("id"));
							temp.setShowName(object.getString("showName"));
							temp.setGender(object.getString("gender"));
							temp.setOccupation(object.getString("occupation"));
							temp.setUserType(object.getIntValue("userType"));
							temp.setWorth(object.getFloatValue("worth"));
							temp.save();
						}
						LogUtils.i(TAG, "getContactUsersList...success");
					} else {
						LogUtils.i(TAG, "getContactUsersList...获取联系人列表为空");
					}
					extracted(handler, requestCode);
				} else if (bean.getResultCode() == 3333) {
					LogUtils.i(TAG, "getContactUsersList...获取联系人列表为空" + bean.getMsg());
					extracted(handler, requestCode);
				} else {
					LogUtils.i(TAG, "getContactUsersList...failure,cause by:" + bean.getMsg());
					extracted(handler, requestCode);
				}
			}

			private void extracted(final Handler handler, final int requestCode) {
				Message message = Message.obtain();
				message.what = requestCode;
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(String e) {
				LogUtils.i(TAG, "getContactUsersList...onFailure,cause by:" + e);
				extracted(handler, requestCode);
			}

		});
	}
}
