/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.CheckIdentityDialog;
import cn.sx.decentworld.dialog.CheckIdentityDialog.CheckIdentityListener;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: PrivacySettingActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年7月24日 上午10:46:33
 */
@EActivity(R.layout.activity_privacy_setting)
public class PrivacySettingActivity extends BaseFragmentActivity
{
	private static final String TAG = "PrivacySettingActivity";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.ll_root)
	LinearLayout ll_root;
	@ViewById(R.id.ll_privacy_setting_root)
	LinearLayout ll_privacy_setting_root;
	@ViewById(R.id.tv_phone_number)
	TextView tvMobile;
	public static final int REQUEST_BANKCARD = 1;
	public static final int REQUEST_MOBILE = 2;
	public static final int REQUEST_RESET_PWD = 3;
	private FragmentManager fragmentManager;
	private CheckIdentityDialog checkIdentityDialog;
	private ReminderDialog reminderDialog;

	// 变量信息
	@ViewById(R.id.privacy_setting_bank_card)
	TextView tvBankCard;
	@ViewById(R.id.tv_phone_number)
	TextView privacy_setting_phone_number;
	@ViewById(R.id.privacy_setting_password)
	TextView privacy_setting_password;
	
	public static final String PAYACCOUNT = "payaccount";//支付账户
	

	@AfterViews
	public void init()
	{
		fragmentManager = getSupportFragmentManager();
		titleBar.setTitleBar("设置", "隐私设置");
	}

	@Click(
	{ R.id.privacy_setting_modification_bank_card, R.id.privacy_setting_modification_phone_number, R.id.privacy_setting_modification_password })
	void bankCardModification(View view)
	{
		checkIdentityDialog = new CheckIdentityDialog();
		checkIdentityDialog.setListener(listener);
		checkIdentityDialog.setClickView(view);
		checkIdentityDialog.show(fragmentManager, "CheckIdentityDialog");
	}

	// 认证密码监听器
	CheckIdentityListener listener = new CheckIdentityListener()
	{
		@Override
		public void onConfirm()
		{
			if (checkIdentityDialog.getPassword().equals(""))
			{
				toast.show("密码为空");
				return;
			}
			// LogUtils.e("bm", "checkIdentityDialog.getPassword()--"
			// + checkIdentityDialog.getPassword()
			// + "DecentWorldApp.getInstance().getPassword()--"
			// + DecentWorldApp.getInstance().getPassword());
			String pwd = AES.encode(checkIdentityDialog.getPassword());
			if (pwd.equals(DecentWorldApp.getInstance().getPassword()))
			{
				Intent intent;
				// 跳转到替换（取消银行卡） 界面
				toast.show("密码正确");
				switch (checkIdentityDialog.getClickView().getId())
				{
					case R.id.privacy_setting_modification_bank_card:
//						intent = new Intent(PrivacySettingActivity.this , ModifyPaycardActivity_.class);
//						startActivityForResult(intent, REQUEST_BANKCARD);
						//绑定账号（支付宝或者微信），用于推荐返现
						intent = new Intent(PrivacySettingActivity.this , BindAccountActivity_.class);
						startActivityForResult(intent, REQUEST_BANKCARD);
						break;

					case R.id.privacy_setting_modification_phone_number:
						intent = new Intent(PrivacySettingActivity.this , ModificationPhoneNumOne_.class);
						startActivityForResult(intent, REQUEST_MOBILE);
						break;

					case R.id.privacy_setting_modification_password:
						intent = new Intent(PrivacySettingActivity.this , ModificationPasswordOne_.class);
						startActivity(intent);
						break;
				}
			}
			else
			{
				// 弹出提醒对话框
				reminderDialog = new ReminderDialog();
				reminderDialog.setListener(listener2);
				reminderDialog.setInfo("密码输入错误");
				reminderDialog.show(fragmentManager, "ReminderDialog");
			}
		}
	};

	// 提醒对话框监听器
	ReminderListener listener2 = new ReminderListener()
	{
		@Override
		public void confirm()
		{
			// // 跳转到修改密码 界面
			// toast.show("密码错误");
			// Intent intent = new Intent(PrivacySettingActivity.this,
			// ModificationPasswordOne_.class);
			// startActivity(intent);
			reminderDialog.dismiss();
		}
	};

	@Click(R.id.main_header_left)
	void setBackBtn()
	{
		finish();
		InputMethodManager imm = (InputMethodManager) PrivacySettingActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ll_root.getWindowToken(), 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		LogUtils.i(TAG, "进来了");
		if (null == intent)
		{
			return;
		}
		switch (requestCode)
		{
			case REQUEST_BANKCARD:
//				String bankCard = intent.getStringExtra(ModificationBankCardOne.BANK_CARD);
//				String bankCardEnd = bankCard.substring(bankCard.length() - 4);
//				tvBankCard.setText("*************" + bankCardEnd);
//				toast.show("succ");
				String payAccount = intent.getStringExtra(PrivacySettingActivity.PAYACCOUNT);
				tvBankCard.setText(payAccount);
				LogUtils.i(TAG, "设置支付账号成功");
				break;
			case REQUEST_MOBILE:
				String mobile = intent.getStringExtra(ModificationPhoneNumOne.MOBILE);
				tvMobile.setText("*******" + mobile.substring(mobile.length() - 4));
				break;
			case REQUEST_RESET_PWD:

				break;
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// 重新加载数据

	}
}
