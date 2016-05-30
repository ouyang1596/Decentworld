/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.main.ConversationFragment;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: GetUserInfo.java
 * @Description: 1.根据电话号码获取dwID；
 * 
 * @author: cj
 * @date: 2015年9月25日 上午10:30:41
 */
@EBean
public class GetUserInfo {
	private static final String TAG = "GetUserInfo";
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
	 * 点击查看 我是谁的贵人的列表详情
	 * 
	 * @param dwID
	 *            明细人ID（对方）
	 * @param grID
	 *            贵人ID（自己）
	 * @param handler
	 * @param requestCode
	 */
	public void getGRBenefitHistory(final String grID, String dwID, final Handler handler, final int requestCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("grID", grID);
		map.put("dwID", dwID);
		LogUtils.i(TAG, "getGRBenefitHistory...begin，自己的ID= " + grID + ",明细人的ID=" + dwID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_GET_GR_BENEFIT_HISTORY, Method.GET,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						LogUtils.i(TAG,
								"getGRBenefitHistory...msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg());
						if (msg.getResultCode() == 2222)// 获取成功
						{
							// getData = {"result":[]}
							LogUtils.i(TAG, "getGRBenefitHistory...获取的详情为：" + msg.getData());
							Message message = Message.obtain();
							message.what = requestCode;
							message.arg1 = 1;
							message.obj = msg.getData().toString();
							handler.sendMessage(message);
						}
						if (msg.getResultCode() == 3333) {

							Message message = Message.obtain();
							message.what = requestCode;
							message.arg1 = 0;
							message.obj = msg.getMsg();
							handler.sendMessage(message);
							LogUtils.i(TAG, "getGRBenefitHistory...failure,cause by:" + msg.getMsg());
						}
					}

					@Override
					public void onFailure(String error) {
						Message message = Message.obtain();
						message.what = requestCode;
						message.arg1 = 0;
						message.obj = Constants.NET_WRONG;
						handler.sendMessage(message);
						LogUtils.i(TAG, "getGRBenefitHistory...onFailure,cause by:" + error);
					}
				});
	}

	private ProgressDialog mProDialog;

	private void showToastInfo(final String data) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtil.showToast(data);
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
					mProDialog.dismiss();
				}
			}
		});
	}

	/**
	 * 获取添加朋友的历史记录列表
	 * 
	 * @param userID
	 *            自己的dwID
	 * @param handler
	 *            回调Handler
	 * @param requestCode
	 *            请求码
	 */
	public void getAddNewFriendHistory(String userID, final Handler handler, final int requestCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", userID);
		LogUtils.i(TAG, "getAddNewFriendHistory...begin,userID=" + userID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_NEW_FRIEND_LIST, Method.GET,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						LogUtils.i(TAG, "getAddNewFriendHistory...msg.getResultCode=" + msg.getResultCode() + "\nmsg.getMsg="
								+ msg.getMsg() + "\nmsg.getData=" + msg.getData());
						if (msg.getResultCode() == 2222) {
							LogUtils.i(TAG, "getAddNewFriendHistory...success");
							Message message = Message.obtain();
							message.obj = msg.getData().toString();
							message.what = requestCode;
							handler.sendMessage(message);
						} else if (msg.getResultCode() == 3333) {
							// 数据为空，直接给用户提示
							LogUtils.i(TAG, "getAddNewFriendHistory...failure,cause by:" + msg.getMsg());
							showToastInfo(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.i(TAG, "getAddNewFriendHistory...onFailure,cause by:" + e);
						showToastInfo(Constants.NET_WRONG);
					}
				});
	}

	/**
	 * 推荐别人获得的收益列表
	 * 
	 * @param userID
	 *            自己ID
	 * @param handler
	 * @param requestCode
	 */
	public void getRecommendBenefitList(String userID, final Handler handler, final int requestCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", userID);
		LogUtils.i(TAG, "getRecommendBenefitList...begin,dwID=" + userID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_RECOMMEND_BENEFIT_LIST, Method.GET,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						LogUtils.i(TAG, "getRecommendBenefitList...begin,msg.getResultCode=" + msg.getResultCode()
								+ ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
						if (msg.getResultCode() == 2222) {
							/**
							 * ResultBean [resultCode=2222, msg=,
							 * data=[{"amount":"30.0",
							 * "benefit":"0.0","dwID":"282102","isRegister"
							 * :"1","name":"测试推荐"
							 * ,"status":"1"},{"amount":"","benefit":""
							 * ,"dwID":"","isRegister"
							 * :"0","name":"13713132653","status":
							 * ""},{"amount":"","benefit"
							 * :"","dwID":"","isRegister":"0","name"
							 * :"13925738211","status"
							 * :""},{"amount":"","benefit":"","dwID"
							 * :"","isRegister":"0",
							 * "name":"13713132657","status":""},{"amount"
							 * :"30.0","benefit"
							 * :"1.0","dwID":"689857","isRegister":"1","name"
							 * :"测试推荐2","status":"1"}]]
							 */
							LogUtils.i(TAG, "getRecommendBenefitList...success");
							Message message = Message.obtain();
							message.what = requestCode;
							message.obj = msg.getData().toString();
							handler.sendMessage(message);
						}

						if (msg.getResultCode() == 3333) {
							LogUtils.i(TAG, "getRecommendBenefitList...failure");
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.i(TAG, "getRecommendBenefitList...onFailure");
						showToastInfo(Constants.NET_WRONG);
					}
				});
	}

	/**
	 * 上传联系人列表到服务器
	 * 
	 * @param strangerID
	 * @param handler
	 * @param requestCod
	 */
	public void uploadContact(HashMap<String, String> map, final Handler handle) {
		showProgressDialog();
		LogUtils.d(TAG, "uploadContact time " + System.currentTimeMillis());
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.UPLOAD_CONTACT, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.d(TAG, "uploadContact time " + System.currentTimeMillis());
				LogUtils.d(TAG, "uploadContact---" + msg.toString());
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					LogUtils.i("bm", msg.getData().toString());
					Message message = handle.obtainMessage();
					message.obj = msg.getData().toString();
					handle.sendMessage(message);
				} else {
					showToastInfo(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "uploadContact---error---" + e);
				hideProgressDialog();
				showToastInfo(Constants.NET_WRONG);
				handle.sendEmptyMessage(Constants.NET_WRONG_CODE);
			}
		});
	}

	/**
	 * 获取自动转账的开关权限
	 * 
	 * @param dwID
	 * @param handler
	 * @param requestCode
	 */
	public void getAutoAuthority(String dwID, final Handler handler, final int requestCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		LogUtils.i(TAG, "getAutoAuthority...begin,dwID=" + dwID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_AUTOTRANFER_AUTHORITY, Method.GET,
				new HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						LogUtils.i(TAG, "getAutoAuthority...begin,msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg="
								+ msg.getMsg() + ",msg.getData=" + msg.getData());
						if (msg.getResultCode() == 2222) {
							Message message = Message.obtain();
							message.what = requestCode;
							message.arg1 = 1;
							message.obj = msg.getData().toString();
							handler.sendMessage(message);
						}
						if (msg.getResultCode() == 3333) {
							LogUtils.i(TAG, "getAutoAuthority...failure,cause by:" + msg.getMsg());
							Message message = Message.obtain();
							message.what = requestCode;
							message.arg1 = 0;
							handler.sendMessage(message);
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.i(TAG, "getAutoAuthority...onFailure,cause by:" + e);
						Message message = Message.obtain();
						message.what = requestCode;
						message.arg1 = 0;
						handler.sendMessage(message);
					}
				});
	}

	public void getShareprice(final Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		showProgressDialog();
		LogUtils.v(TAG, "getShareprice() params:" + JSON.toJSONString(map));
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_GET_SHAREPRICE, Method.POST,
				new HttpCallBack() {
					@Override
					public void onSuccess(String resultJSON, ResultBean resultBean) {
						ConversationFragment.isLoading = false;
						hideProgressDialog();
						LogUtils.v(TAG, "getShareprice() onSuccess:" + resultJSON);
						if (resultBean.getResultCode() == 2222) {
							Message message = handler.obtainMessage();
							message.obj = resultBean.getData().toString();
							handler.sendMessage(message);
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.e(TAG, "getShareprice() onFailure,cause by:" + e);
						ConversationFragment.isLoading = false;
						hideProgressDialog();
						showToastInfo(Constants.NET_WRONG);
					}
				});
	}
}
