/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.UserDetailInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.ChatFragment;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: ChatFragmentComponent.java
 * @Description:
 * @author: cj
 * @date: 2015年9月8日 下午8:35:01
 */
@EBean
public class ChatFragmentComponent {
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;

	/**
	 * 以下代码为网络请求
	 */
	private static final String TAG = "ChatFragmentComponent";
	private SendUrl sendUrl;

	@AfterViews
	void init() {
		sendUrl = new SendUrl(context);
	}

	/**
	 * 设置用户头像
	 * 
	 * @param dwID
	 * @param icons
	 * @param handler
	 */
	public void setIcon(String dwID, File[] icon, final Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		LogUtils.i(TAG, "setIcon...begin");
		LogUtils.i(TAG, "setIcon...dwID=" + dwID);
		sendUrl.httpRequestWithImage(map, icon, Constants.CONTEXTPATH
				+ "/user/setIcon", new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.i(TAG, "msg.getResultCode=" + msg.getResultCode());
				if (msg.getResultCode() == 3000) {
					Message message = new Message();
					// message.what = ChatFragment.SET_LOCAL_ICON;
					message.obj = msg.getMsg();
					handler.sendMessage(message);
				}
				if (msg.getResultCode() == 3001) {
					LogUtils.i(TAG, "failure caused by:" + msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.i(TAG, "failure caused by:" + e);
			}
		});
		LogUtils.i(TAG, "setIcon...end");
	}

	public void setIsShowRealName(String dwID, final int displayRealName,
			final Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("displayRealName", String.valueOf(displayRealName));
		LogUtils.i(TAG, "setIsShowRealName...begin");
		LogUtils.i(TAG, "setIsShowRealName...dwID=" + dwID
				+ ",displayRealName=" + displayRealName);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ "/user/setDisplayRealName", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.i(TAG, "msg.getResultCode=" + msg.getResultCode());
				if (msg.getResultCode() == 2222) {
					Message message = new Message();
					if (displayRealName == 0) {
						// message.what = MeComponent.DisplayRealName_0;
					} else {
						// message.what = MeComponent.DisplayRealName_1;
					}
					message.obj = msg.getMsg();
					handler.sendMessage(message);
				}
				if (msg.getResultCode() == 3333) {
					LogUtils.i(TAG, "failure caused by:" + msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.i(TAG, "failure caused by:" + e);
			}
		});
		LogUtils.i(TAG, "setIsShowRealName...end");
	}
}
