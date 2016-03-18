/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: SearchComponent.java
 * @Description:
 * @author: cj
 * @date: 2015年9月11日 下午6:59:38
 */
@EBean
public class SearchComponent {
	private static final String TAG = "SearchComponent";
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	private SendUrl sendUrl;
	private ProgressDialog mProDialog;

	@AfterViews
	void init() {
		sendUrl = new SendUrl(context);
	}

	/**
	 * 更具关键字和类型进行搜索
	 * 
	 * @param phoneNum
	 * @param searchType
	 * @param handler
	 */
	public void searchFriendBy(HashMap<String, String> map,
			final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ "/search/searchFriend", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.i(
						TAG,
						"searchFriendBy...msg.getResultCode="
								+ msg.getResultCode() + ",msg.getMsg="
								+ msg.getMsg() + ",msg.getData="
								+ msg.getData());
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					Message message = handler.obtainMessage();
					message.what = msg.getResultCode();
					message.obj = msg.getData().toString();
					handler.sendMessage(message);
				} else if (msg.getResultCode() == 3333) {
					ToastUtils.toast(context, msg.getMsg());
					handler.sendEmptyMessage(msg.getResultCode());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				handler.sendEmptyMessage(-1);
				ToastUtils.toast(context, Constants.NET_WRONG);
				LogUtils.i("bm", "searchFriendBy...onFailure,cause by:" + e);
			}
		});
	}

	private void showProgressDialog() {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null == mProDialog) {
					mProDialog = ProgressDialog.show(context, null, "loading");
				} else {
					mProDialog.show();
				}
			}
		});
	}

	private void hideProgressDialog() {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null != mProDialog) {
					mProDialog.hide();
				}
			}
		});
	}
}
