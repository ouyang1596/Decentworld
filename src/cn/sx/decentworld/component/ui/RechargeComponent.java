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

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: RechargeComponent.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月18日 下午8:57:08
 */
@EBean
public class RechargeComponent {
	private static final String TAG = "RechargeComponent";
	@RootContext
	Activity activity;
	@RootContext
	Context context;
	@Bean
	ToastComponent toast;

	private SendUrl sendUrl;
	private String channel;
	private int amount;

	@AfterViews
	public void init() {
		sendUrl = new SendUrl(context);
	}

	public void requestCharge(HashMap<String, String> map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_CREATE_ORDER, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (2222 == msg.getResultCode()) {
					LogUtils.i(Constants.TAG, "--bm--"
							+ msg.getData().toString());
					Message message = handler.obtainMessage();
					message.obj = msg.getData().toString();
					message.what = msg.getResultCode();
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
					showToast(msg.getMsg());
				}
				// LogUtils.i(TAG, "从服务器请求charge......success," + "response"
				// + response);
				// if (response != null) {
				// LogUtils.i(TAG, "从服务器请求charge......success");
				// Message message = new Message();
				// message.what = RechargeActivity.REQUEST_CHARGE_SUCCESS;
				// message.obj = response;
				// handler.sendMessage(message);
				// } else {
				// LogUtils.i(TAG, "从服务器请求charge......failure");
				// Message message = new Message();
				// handler.sendEmptyMessage(RechargeActivity.REQUEST_CHARGE_FAIL);
				// }
			}

			@Override
			public void onFailure(String error) {
				hideProgressDialog();
				LogUtils.i(Constants.TAG,
						"从服务器请求charge......failure,caused by:" + error);
			}
		});
		LogUtils.i(Constants.TAG, "从服务器请求charge......end");
	}

	/**
	 * 推荐时请求的charge
	 * 
	 * @param type
	 *            付款渠道
	 * @param amount
	 *            推荐金额
	 * @param dwID
	 *            推荐人ID
	 * @param phoneNum
	 *            被推荐人的电话号码
	 * @param handler
	 *            回调handler
	 */
	public void getRecommendCharge(HashMap<String, String> map,
			final Handler handler) {

		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_RECOMMEND, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (2222 == msg.getResultCode()) {
					LogUtils.i(Constants.TAG, "bm--" + msg.getData().toString());
					Message message = handler.obtainMessage();
					message.obj = msg.getData().toString();
					message.what = msg.getResultCode();
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String error) {
				hideProgressDialog();
				LogUtils.i(TAG, "getRecommendCharge......failure,caused by:"
						+ error);
			}
		});
		LogUtils.i(TAG, "getRecommendCharge......end");
	}

	/**
	 * 去疑支付
	 * */
	public void getDoubtWanCharge(HashMap<String, String> map, String api,
			final Handler handler) {

		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api,
				Method.GET, new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						if (2222 == msg.getResultCode()) {
							LogUtils.i(Constants.TAG, "bm--"
									+ msg.getData().toString());
							Message message = handler.obtainMessage();
							message.obj = msg.getData().toString();
							message.what = msg.getResultCode();
							handler.sendMessage(message);
						} else {
							handler.sendEmptyMessage(msg.getResultCode());
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String error) {
						hideProgressDialog();
						LogUtils.i(TAG,
								"getRecommendCharge......failure,caused by:"
										+ error);
					}
				});
	}

	/**
	 * 担保时请求的charge
	 * 
	 * @param type
	 *            付款渠道
	 * @param amount
	 *            担保金额
	 * @param dwID
	 *            担保人ID
	 * @param phoneNum
	 *            被担保人的电话号码
	 * @param handler
	 *            回调handler
	 */
	public void getGuaranteeCharge(HashMap<String, String> map,
			final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_GUARANTEE, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (2222 == msg.getResultCode()) {
					LogUtils.i(Constants.TAG, "bm--" + msg.getData().toString());
					Message message = handler.obtainMessage();
					message.obj = msg.getData().toString();
					message.what = msg.getResultCode();
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String error) {
				hideProgressDialog();
				LogUtils.i(TAG, "getGuaranteeCharge......failure,caused by:"
						+ error);
			}
		});
	}

	/**
	 * 审核时获得charge
	 * 
	 * */
	public void getCheckCharge(HashMap<String, String> map,
			final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_CHECK_BEAUTY_PAY_RETRY, Method.GET,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						hideProgressDialog();
						if (2222 == msg.getResultCode()) {
							LogUtils.i(Constants.TAG, "bm--"
									+ msg.getData().toString());
							Message message = handler.obtainMessage();
							message.obj = msg.getData().toString();
							message.what = msg.getResultCode();
							handler.sendMessage(message);
						} else {
							handler.sendEmptyMessage(msg.getResultCode());
							showToast(msg.getMsg());
						}

						// hideProgressDialog();
						// Message message = handler.obtainMessage();
						// switch (msg.getResultCode()) {
						// case 2222:
						// message.obj = msg.getData().toString();
						// break;
						// case 3333:
						// showToast(msg.getMsg());
						// break;
						// }
						// message.what = msg.getResultCode();
						// handler.sendMessage(message);
					}

					@Override
					public void onFailure(String error) {
						hideProgressDialog();
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

	private void showToast(final String message) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toast.show(message);
			}
		});
	}
}
