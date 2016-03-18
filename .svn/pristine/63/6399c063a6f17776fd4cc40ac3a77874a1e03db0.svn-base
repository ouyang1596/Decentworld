/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.sx.decentworld.activity.LoginActivity;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: LoginComponent.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月3日 上午11:13:15
 */

@EBean
public class LoginComponent
{

	private static final String TAG = "LoginComponent";
	@RootContext
	Context context;

	@RootContext
	Activity activity;

	// http请求类
	private SendUrl sendUrl;
	private String tel;
	private String icode;

	@Bean
	ToastComponent toastComponent;

	@AfterViews
	public void init()
	{
		sendUrl = new SendUrl(context);
	}

	/**
	 * 请求验证码(当发送验证码后fragmentdialog变为下一个)
	 * 
	 * @param tel
	 * @return 验证码
	 */
	public void requestCode(String tel, final Handler handler)
	{
		this.tel = tel;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tel", tel);
		final Handler ui_handler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
					case 1:
						Message this_mssg = new Message();
						this_mssg.what = LoginActivity.toNextDialog;
						handler.sendMessage(this_mssg);
						break;
				}
			};
		};
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/reset/checkCode", Method.POST, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				if (msg.getResultCode() == 2000)
				{
					icode = msg.getData().toString();
					Message mssg = new Message();
					mssg.what = 1;
					ui_handler.sendMessage(mssg);
				}
				else
				{
					activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e)
			{

			}

		});
	}

	/**
	 * 请求验证码(当发送验证码无变化)
	 */
	public void requestCode_nonext_dialog()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tel", tel);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/reset/checkCode", Method.POST, new HttpCallBack()
		{

			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				Log.v("resultBean_from_jason", msg.getResultCode() + "");
				Log.v("resultBean_from_jason", msg.toString() + "");
				if (msg.getResultCode() == 2000)
				{
					icode = msg.getData().toString();
					toastComponent.show("发送成功");
				}
				else
				{
					activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e)
			{
				Log.v("resultBean_from_jason", e);
			}
		});
	}

	/**
	 * 验证码验证
	 */
	public void identifyCode(String icode, final Handler handler)
	{
		if (this.icode == null)
		{
			toastComponent.show("请先获取验证码！");
			// return false;
		}

		if (icode.length() != 6)
		{
			toastComponent.show("您输入的验证码格式不正确！");
			return;
			// return false;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tel", tel);
		map.put("code", icode);
		final Handler ui_handler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
					case 1:
						Message this_mssg = new Message();
						this_mssg.what = LoginActivity.toNextDialog;
						handler.sendMessage(this_mssg);
						break;
				}
			};
		};
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/register/checkCode", Method.POST, new HttpCallBack()
		{

			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				Log.v("resultBean_from_jason", msg.getResultCode() + "");
				Log.v("resultBean_from_jason", msg.toString() + "");
				if (msg.getResultCode() == 2000)
				{
					Log.v("resultBean_from_jason", "2000");
					Message mssg = new Message();
					mssg.what = 1;
					ui_handler.sendMessage(mssg);

				}
				else
				{
					activity.runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e)
			{

			}

		});
	}

	/**
	 * 身份证匹配（与注册时的身份证比配）
	 * 
	 * @param idcode
	 * @param tel
	 * @param handler
	 */
	public void matchIdCode(String idcode, String tel, final Handler handler)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id_cardnum", idcode);
		map.put("phonenum", tel);
		final Handler ui_handler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
					case 1:
						Message this_mssg = new Message();
						this_mssg.what = LoginActivity.toNextDialog;
						handler.sendMessage(this_mssg);
						break;
				}
			};
		};
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/reset/checkCardId", Method.POST, new HttpCallBack()
		{

			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				Log.v("resultBean_from_jason", msg.getResultCode() + "");
				Log.v("resultBean_from_jason", msg.toString() + "");
				if (msg.getResultCode() == 2000)
				{
					Log.v("resultBean_from_jason", "2000");
					Message mssg = new Message();
					mssg.what = 1;
					ui_handler.sendMessage(mssg);
				}
				else
				{
					activity.runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e)
			{

			}

		});
	}

	public void setNewPwd(String newpwd, final Handler handler)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("newpwd", newpwd);
		map.put("phonenum", tel);
		final Handler ui_handler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
					case 1:
						Message this_mssg = new Message();
						this_mssg.what = LoginActivity.toNextDialog;
						handler.sendMessage(this_mssg);
						break;
				}
			};
		};
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/reset/updatePassword", Method.POST, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				Log.v("resultBean_from_jason", msg.getResultCode() + "");
				Log.v("resultBean_from_jason", msg.toString() + "");
				if (msg.getResultCode() == 2000)
				{
					Log.v("resultBean_from_jason", "2000");
					Message mssg = new Message();
					mssg.what = 1;
					ui_handler.sendMessage(mssg);

				}
				else
				{
					activity.runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							toastComponent.show(msg.getMsg());
						}
					});
				}
			}

			@Override
			public void onFailure(String e)
			{

			}

		});
	}


}
