/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.wxapi.WXEntryActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: BindAccountActivity.java
 * @Description: 绑定账号（支付宝或者微信），用于推荐返现
 * @author: cj
 * @date: 2015年12月23日 上午11:05:55
 */
@EActivity(R.layout.activity_bind_account)
public class BindAccountActivity extends BaseFragmentActivity
{
	private static final String TAG = "BindAccountActivity";
	@Bean
	ToastComponent toast;

	/**
	 * 绑定的账号类型
	 */
	public static final int ALIPAY = RechargePayMethodActivity.ALIPAY;// 支付宝
	public static final int WX = RechargePayMethodActivity.WX;// 微信
	public static final int NO = 2;// 没有绑定

	/**
	 * 必要参数
	 */
	private int accountType = NO;// 绑定的账号类型
	private String accountName = "";// 账号名字

	/**
	 * 支付宝
	 */
	@ViewById(R.id.tv_bind_account_alipay)
	TextView tv_bind_account_alipay;

	/**
	 * 微信
	 */
	@ViewById(R.id.tv_bind_account_wx)
	TextView tv_bind_account_wx;

	@Bean
	GetUserInfo getUserInfo;
	private static final int GET_USER_ACCOUNT = 1;

	/**
	 * 初始化
	 */
	@AfterViews
	void init()
	{
		getUserInfo.getUserAccount(DecentWorldApp.getInstance().getDwID(), handler, GET_USER_ACCOUNT);
	}

	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case GET_USER_ACCOUNT:
					String result = msg.obj.toString();
					JSONObject object = JSON.parseObject(result);
					int type = object.getIntValue("type");
					String account = object.getString("account");
					LogUtils.i(TAG, "type=" + type + "，account = " + account);
					accountType = type;
					if(CommUtil.isNotBlank(account))
					accountName = account;
					else
						accountName = "";
					break;
				default:
					break;
			}
			afterNetReq();
		};
	};

	/**
	 * 完成网络请求之后
	 */
	private void afterNetReq()
	{
		initView();
	}

	/**
	 * 初始化界面控件
	 */
	private void initView()
	{
		if (accountType == NO)
		{
			tv_bind_account_alipay.setBackgroundResource(R.color.white);
			tv_bind_account_wx.setBackgroundResource(R.color.white);
		}
		else if (accountType == ALIPAY)
		{
			tv_bind_account_alipay.setBackgroundResource(R.color.blue);
			tv_bind_account_wx.setBackgroundResource(R.color.white);
		}
		else if (accountType == WX)
		{
			tv_bind_account_alipay.setBackgroundResource(R.color.white);
			tv_bind_account_wx.setBackgroundResource(R.color.blue);
		}
	}

	/**
	 * 选择支付宝
	 * 
	 * @param v
	 */
	@Click(R.id.tv_bind_account_alipay)
	void bindAlipay(View v)
	{
		if (accountType != WX)
		{
			// 跳转
			setAlipay();
		}
		else
		{
			ReminderDialog dialog = new ReminderDialog();
			dialog.setInfo("你目前绑定的是微信，\n确定修改吗？");
			dialog.setListener(new ReminderListener()
			{
				@Override
				public void confirm()
				{
					setAlipay();
				}
			});
			dialog.show(getSupportFragmentManager(), "alipay");
		}
	}

	/**
	 * 跳转到设置支付宝账号界面
	 */
	private void setAlipay()
	{
		Intent intent = new Intent(BindAccountActivity.this , BindAccountAlipayActivity_.class);
		intent.putExtra("accountType", ALIPAY);
		intent.putExtra("accountName", accountName);
		startActivity(intent);
	}

	/**
	 * 选择微信
	 * 
	 * @param v
	 */
	@Click(R.id.tv_bind_account_wx)
	void bindWx(View v)
	{
		if (accountType != ALIPAY)
		{
			// 跳转
			setWx();
		}
		else
		{
			toast.show("你目前绑定的是支付宝，确定修改吗？");
			ReminderDialog dialog = new ReminderDialog();
			dialog.setInfo("你目前绑定的是支付宝，\n确定修改吗？");
			dialog.setListener(new ReminderListener()
			{

				@Override
				public void confirm()
				{
					setWx();
				}
			});
			dialog.show(getSupportFragmentManager(), "wx");
		}
	}

	/**
	 * 跳转到绑定微信账号的界面
	 */
	private void setWx()
	{
		Intent intent = new Intent(BindAccountActivity.this , WXEntryActivity.class);
		intent.putExtra("accountType", WX);
		intent.putExtra("accountName", accountName);
		startActivity(intent);
	}

}
