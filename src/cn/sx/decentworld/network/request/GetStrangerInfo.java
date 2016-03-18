/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.activity.NearCardDetail2Activity;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: SetFriendInfo.java
 * @Description: 获取陌生人相关信息 1. 获取附近的人的列表 2.
 * @author: cj
 * @date: 2015年9月25日 上午10:30:58
 */
@EBean
public class GetStrangerInfo {
	private static String TAG = "GetStrangerInfo";
	public static final int RESULT_GET_NEARBY_STRANGER_INFO = 1;// 获取附近的人的结果
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
	 * 1. 获取附近的人的列表
	 */
	public void getNearStrangerInfo(HashMap<String, String> map, final Handler handler) {
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_GET_NEARBY_STRANGER, Method.GET,
				new HttpCallBack() {

					@Override
					public void onSuccess(String response, ResultBean msg) {
						if (msg.getResultCode() == 2222) {
							Message message = handler.obtainMessage();
							message.obj = msg.getData().toString();

							LogUtils.i("bm", "----data-----" + msg.getData().toString());
							message.what = msg.getResultCode();
							handler.sendMessage(message);
						}
						if (msg.getResultCode() == 3333) {
							handler.sendEmptyMessage(msg.getResultCode());
						}
					}

					@Override
					public void onFailure(String e) {
						showToast(Constants.NET_WRONG);
						handler.sendEmptyMessage(Constants.FAILURE);
					}
				});
	}

	public void showToMe(HashMap<String, String> map, final Handler handler) {
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_GET_NEARBY_STRANGER, Method.GET,
				new HttpCallBack() {

					@Override
					public void onSuccess(String response, ResultBean msg) {
						if (msg.getResultCode() == 2222) {
							Message message = Message.obtain();
							message.obj = msg.getData().toString();
							handler.sendMessage(message);
						}
					}

					@Override
					public void onFailure(String e) {
						showToast(Constants.NET_WRONG + e);
					}
				});
	}

	public void likeStranger(HashMap<String, String> map, final Handler handler) {
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_LIKE_STRANGER, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				if (msg.getResultCode() == 6000) {
					Message message = Message.obtain();
					message.what = msg.getResultCode();
					message.obj = msg.getData().toString();
					handler.sendMessage(message);
				} else if (3333 == msg.getResultCode()) {
					showToast(msg.getMsg());
				} else if (6001 == msg.getResultCode()) {
					handler.sendEmptyMessage(msg.getResultCode());
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
				}
			}

			@Override
			public void onFailure(String e) {
				showToast(Constants.NET_WRONG + e);
			}
		});
	}

	public void dislikeStranger(HashMap<String, String> map, final Handler handler) {
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_DISLIKE_STRANGER, Method.GET,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						if (msg.getResultCode() == 6000) {
							Message message = Message.obtain();
							message.obj = msg.getData().toString();
							handler.sendMessage(message);
							// LogUtils.i("bm", "like" +
							// msg.getData().toString());
						} else {
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						showToast(Constants.NET_WRONG);
						// LogUtils.i("bm",
						// "getNearStrangerInfo...failure,cause by:" + e);
					}
				});
	}

	public void getNearStrangerDetailInfo(HashMap<String, String> map, final Handler handler) {
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_GET_FRIEND_INFO, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				if (msg.getResultCode() == 2222) {
					Message message = Message.obtain();
					message.obj = msg.getData().toString();
					handler.sendMessage(message);
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				showToast(Constants.NET_WRONG);
			}
		});
	}

	public void createAnonymousIdentify(HashMap<String, String> map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_CREATE_ANONYMOUS_IDENTIFY, Method.GET,
				new HttpCallBack() {

					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						Message message = Message.obtain();
						if (msg.getResultCode() == 3000) {
							message.what = NearCardDetail2Activity.CREATE_ANONYMOUS_OBJ;
							handler.sendMessage(message);
						} else {
							showToast("创建失败");
						}
					}

					@Override
					public void onFailure(String e) {
						hideProgressDialog();
						LogUtils.i(TAG, "创建匿名信息失败，原因是：" + e);
						showToast(Constants.NET_WRONG);
					}
				});
	}

	public void getAnonymousInfo(HashMap<String, String> map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_GET_ANONYMOUS, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				LogUtils.i("bm", "--data--" + msg.getData().toString());
				Message message = Message.obtain();
				message.obj = msg.getData().toString();
				message.what = NearCardDetail2Activity.GET_ANONYMOUSINFO;
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				if (null != e) {
					LogUtils.e("bm", "r--" + e);
				}
				showToast(Constants.NET_WRONG);
			}
		});
	}

	private ProgressDialog mProDialog;

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
