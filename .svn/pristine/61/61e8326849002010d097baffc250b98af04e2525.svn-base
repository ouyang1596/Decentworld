/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.sx.decentworld.activity.LoginActivity;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: RegisterComponent.java
 * @Description: 注册组件
 * @author: yj
 * @date: 2015年8月13日 下午3:18:23
 */

@EBean
public class RegisterComponent {
	private static final String TAG = "RegisterComponent";
	@RootContext
	Context context;

	@RootContext
	Activity activity;

	// http请求类
	private SendUrl sendUrl;

	/**
	 * 注册需要保存的变量
	 */
	public static String tel = ""; // 手机号码
	private String icode; // 验证码
	private boolean iscard; // 身份证是否正确
	private boolean iscode; // 验证码是否正确
	private File picture;

	@AfterViews
	public void init() {
		sendUrl = new SendUrl(context);
	}

	/**
	 * 请求验证码
	 * 
	 * @param tel
	 * @return 验证码
	 */
	public void requestCode(String tel, final Handler handler) {
		// showProgressDialog();
		this.tel = tel;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", tel);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SEND_CODE, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.d(TAG, "requestCode---" + msg.toString());
				if (2222 == msg.getResultCode()) {
					showToast("验证码已发送到手机");
				} else {
					showToast(msg.getMsg());
					handler.sendEmptyMessage(msg.getResultCode());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "requestCode---error---" + e);
				showToast(Constants.NET_WRONG);
				handler.sendEmptyMessage(-1);
			}
		});
	}

	/**
	 * 验证码验证
	 */
	public void identifyCode(HashMap<String, String> map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_CHECK_CODE, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.d(TAG, "identifyCode---" + msg.toString());
				hideProgressDialog();
				if (2222 == msg.getResultCode()) {
					handler.sendEmptyMessage(msg.getResultCode());
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				LogUtils.e(TAG, "identifyCode---error---" + e);
				showToast(Constants.NET_WRONG);
			}
		});
	}

	/**
	 * 设置密码
	 * 
	 * @param pwd
	 *            密码
	 * @param handler
	 */
	public void register(HashMap<String, String> map, File[] file, final Handler handler) {
		showProgressDialog();
		map.put("phoneNum", tel);
		sendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH + ConstantNet.API_REGISTER, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.d(TAG, "register---" + msg.toString());
				hideProgressDialog();
				if (2007 == msg.getResultCode()) {
					handler.sendEmptyMessage(msg.getResultCode());
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
				LogUtils.e(TAG, "register---error---" + e);
			}
		});
	}

	public void submitImageWithParams(HashMap<String, String> hashmap, File[] images, String api, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH + api, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.d(TAG, "submitImageWithParams---" + msg.toString());
				hideProgressDialog();
				if (msg.getResultCode() == 3000) {
					Message mssg = new Message();
					mssg.what = Constants.SUCC;
					if (null != handler) {
						handler.sendMessage(mssg);
					}
				} else {
					showToast(msg.getData().toString());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				LogUtils.e(TAG, "submitImageWithParams---error---" + e);
				showToast(Constants.NET_WRONG);
			}

		});
	}

	/**
	 * 判断昵称是否唯一，若是则调到下一个dialog
	 * 
	 * @param nick
	 * @param handler
	 */
	public void submitNickName(HashMap<String, String> hashmap, File[] images, String api, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH + api, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.d(TAG, "submitNickName---" + msg.toString());
				hideProgressDialog();
				if (msg.getResultCode() == 2007) {
					Message mssg = new Message();
					mssg.what = Constants.SUCC;
					handler.sendMessage(mssg);
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				LogUtils.e(TAG, "examinePass---error---" + e);
				showToast(Constants.NET_WRONG);
			}

		});
	}

	/**
	 * 审核通过
	 * 
	 */
	public void examinePass(HashMap map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_EXAMINE_PASS, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.d(TAG, "examinePass---" + msg.toString());
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					handler.sendEmptyMessage(msg.getResultCode());
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "submitNickName---error---" + e);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
				handler.sendEmptyMessage(-1);
			}
		});
	}

	/**
	 * 审核不通过
	 */
	public void examineNoPass(HashMap map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_EXAMINE_NO_PASS, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.d(TAG, "examineNoPass---" + msg.toString());
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					Message message = handler.obtainMessage();
					message.what = msg.getResultCode();
					message.obj = msg.getMsg();
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "examinePass---error---" + e);
				handler.sendEmptyMessage(-1);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	/**
	 * 审核为假
	 * 
	 */
	public void examineFake(HashMap map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + Constants.API_EXAMINE_FAKE, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.d(TAG, "examineFake---" + msg.toString());
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					Message message = handler.obtainMessage();
					message.what = msg.getResultCode();
					message.obj = msg.getMsg();
					handler.sendMessage(message);
				} else {
					handler.sendEmptyMessage(msg.getResultCode());
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "examineFake---error---" + e);
				handler.sendEmptyMessage(-1);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	public void checkNickName(HashMap<String, String> map, String api, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api, Method.POST, new HttpCallBack() {

			@Override
			public void onSuccess(String resultJSON, ResultBean resultBean) {
				LogUtils.d(TAG, "checkNickName---" + resultBean.toString());
				hideProgressDialog();
				if (resultBean.getResultCode() == 2222) {
					handler.sendEmptyMessage(resultBean.getResultCode());
				} else {
					showToast(resultBean.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "checkNickName---error---" + e);
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

	private void showToast(final String netWrong) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtil.showToast(netWrong);
			}
		});
	}
}
