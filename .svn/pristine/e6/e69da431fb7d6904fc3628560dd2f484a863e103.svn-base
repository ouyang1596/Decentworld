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
 * @ClassName: RegisterComponent.java
 * @Description: 注册组件
 * @author: yj
 * @date: 2015年8月13日 下午3:18:23
 */

@EBean
public class RegisterComponent {

	@RootContext
	Context context;

	@RootContext
	Activity activity;

	// http请求类
	private SendUrl sendUrl;

	private static final String TAG = "RegisterComponent";
	@Bean
	ToastComponent toastComponent;

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
	public void requestCode(String tel) {
		// showProgressDialog();
		this.tel = tel;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", tel);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ ConstantNet.API_SEND_CODE, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
				if (2222 == msg.getResultCode()) {
					showToast("验证码已发送到手机");
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

	/**
	 * 验证码验证
	 */
	public void identifyCode(HashMap<String, String> map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ ConstantNet.API_CHECK_CODE, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, final ResultBean msg) {
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
				LogUtils.v("bm", "wrong---" + e);
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
	public void register(HashMap<String, String> map, File[] file,
			final Handler handler) {
		showProgressDialog();
		map.put("phoneNum", tel);
		sendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH
				+ ConstantNet.API_REGISTER, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
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
			}
		});
	}

	/**
	 * 验证身份证信息
	 * 
	 * @param relname
	 * @param relid
	 * @return
	 */
	public void identifyIDCard(String relname, String relid,
			final Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", tel);
		map.put("IDCard", relid);
		map.put("realName", relname);
		// final Handler ui_handler = new Handler()
		// {
		// public void handleMessage(Message msg)
		// {
		// switch (msg.what)
		// {
		// case 1:
		// Message this_mssg = new Message();
		// this_mssg.what = LoginActivity.toNextDialog;
		// handler.sendMessage(this_mssg);
		// break;
		//
		// default:
		// break;
		// }
		// };
		// };
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ "/validate/IDCard", Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
				hideProgressDialog();
				if (msg.getResultCode() == 3000) {
					iscard = true;
					handler.sendEmptyMessage(Constants.SUCC);
				} else {
					iscode = false;
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	/**
	 * 注册，向服务器提交全部信息
	 * 
	 * @param telnum
	 * @param whitch
	 * @param images
	 * @param pwd
	 * @param id_name
	 * @param handler
	 */
	public void register(String telnum, String whitch, File[] images,
			String pwd, String id_name, String id_code, String nickname,
			final Handler handler) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("phonenum", telnum);
		hashmap.put("which", whitch);
		hashmap.put("password", pwd);
		hashmap.put("userCardName", id_name);
		hashmap.put("userCardId", id_code);
		hashmap.put("nickname", nickname);
		final Handler the_handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Message this_mssg = new Message();
					this_mssg.what = LoginActivity.toNextDialog;
					handler.sendMessage(this_mssg);
					break;

				default:
					break;
				}
			};
		};
		showProgressDialog();
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH
				+ "/register/save", new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
				hideProgressDialog();
				if (msg.getResultCode() == 2000) {
					Log.v("resultBean_from_jason",
							"registerComponent" + msg.getResultCode());
					Log.v("resultBean_from_jason",
							"registerComponent" + msg.toString());
					Message mssg = new Message();
					mssg.what = 1;
					the_handler.sendMessage(mssg);
				} else if (msg.getResultCode() == 2001) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							toastComponent.show(msg.getMsg());
						}
					});
				} else {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	public void submitImageType(File[] images, String type,
			final Handler handler) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("phoneNum", tel);
		hashmap.put("type", type);
		final Handler the_handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Message this_mssg = new Message();
					this_mssg.what = Constants.SUCC;
					handler.sendMessage(this_mssg);
					break;
				default:
					break;
				}
			};
		};
		showProgressDialog();
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH
				+ "/register/updateType", new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (msg.getResultCode() == 3000) {
					Message mssg = new Message();
					mssg.what = 1;
					the_handler.sendMessage(mssg);
				} else {
					Log.v("RegisterComponent", msg.getData().toString());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	public void submitImageWithParams(HashMap<String, String> hashmap,
			File[] images, String api, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH
				+ api, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
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
				LogUtils.i(TAG, "onFailure:" + e);
				showToast(Constants.NET_WRONG);
			}

		});
	}

	private void showToast(final String netWrong) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toastComponent.show(netWrong);
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

	/**
	 * 判断昵称是否唯一，若是则调到下一个dialog
	 * 
	 * @param nick
	 * @param handler
	 */
	public void submitNickName(HashMap<String, String> hashmap, File[] images,
			String api, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH
				+ api, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
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
				LogUtils.i(TAG, "onFailure:" + e);
				showToast(Constants.NET_WRONG);
			}

		});
	}

	/**
	 * 判断不是学生
	 * 
	 */
	public void submitNoStudent(String noStudent) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("nickName", noStudent);
		hashmap.put("phoneNum", tel);
		showProgressDialog();
		sendUrl.httpRequestWithParams(hashmap, Constants.CONTEXTPATH
				+ "/register/updateNotStudent", Method.POST,
				new HttpCallBack() {

					@Override
					public void onSuccess(String response, final ResultBean msg) {
						hideProgressDialog();
						if (msg.getResultCode() == 3000) {
							Message mssg = new Message();
							mssg.what = Constants.SUCC;
						} else {
							Log.v("RegisterComponent", msg.getData().toString());
						}
					}

					@Override
					public void onFailure(String e) {
						hideProgressDialog();
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
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_EXAMINE_PASS, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
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
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
				handler.sendEmptyMessage(-1);
			}
		});
	}

	/**
	 * 审核不通过
	 * 
	 */
	public void examineNoPass(HashMap map, final Handler handler) {
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_EXAMINE_NO_PASS, Method.GET,
				new HttpCallBack() {

					@Override
					public void onSuccess(String response, final ResultBean msg) {
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
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ Constants.API_EXAMINE_FAKE, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, final ResultBean msg) {
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
				handler.sendEmptyMessage(-1);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}
}
