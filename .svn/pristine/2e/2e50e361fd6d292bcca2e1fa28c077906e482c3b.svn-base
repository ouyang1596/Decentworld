/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.util.HashMap;
import java.util.List;

import org.apache.http.cookie.Cookie;

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

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * @ClassName: GetRoomInfo.java
 * @Description: 获取聊天室相关信息 1. 获取聊天室列表
 * @author: cj
 * @date: 2015年9月25日 上午10:31:43
 */
@EBean
public class ResetPwdInfo {
	private static String TAG = "ResetPwdInfo";
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	private SendUrl sendUrl;
	public static String phoneNum;
	private ProgressDialog mProDialog;

	@AfterViews
	void init() {
		sendUrl = new SendUrl(context);
	}

	public void requestCode(HashMap<String, String> map, String api,
			int method, final Handler handler) {
		showProgressDialog();
		phoneNum = map.get("phoneNum");
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api, method,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						if (msg.getResultCode() == 2222) {
							Message mesg = handler.obtainMessage();
							mesg.what = msg.getResultCode();
							mesg.obj = msg.getData().toString();
							handler.sendMessage(mesg);
						} else {
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String error) {
						hideProgressDialog();
						showToast(Constants.NET_WRONG);
					}
				});
	}

	public void checkID(HashMap<String, String> map, String api, int method,
			final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api, method,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						if (msg.getResultCode() == 2222) {
							Message mesg = handler.obtainMessage();
							String jsonData = msg.getData().toString();
							mesg.obj = jsonData;
							handler.sendMessage(mesg);
						} else {
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String error) {
						hideProgressDialog();
						showToast(Constants.NET_WRONG);
					}
				});
	}

	public void checkCode(HashMap<String, String> map, String api, int method,
			final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api, method,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						if (msg.getResultCode() == 2222) {
							handler.sendEmptyMessage(msg.getResultCode());
						} else {
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String error) {
						hideProgressDialog();
						showToast(Constants.NET_WRONG);
					}
				});
	}

	public void resetPwd(HashMap<String, String> map, String api, int method,
			final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api, method,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						if (msg.getResultCode() == 2222) {
							handler.sendEmptyMessage(Constants.SUCC);
						} else {
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String error) {
						hideProgressDialog();
						showToast(Constants.NET_WRONG);
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

	private void showToast(final String netWrong) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toast.show(netWrong);
			}
		});
	}

}
