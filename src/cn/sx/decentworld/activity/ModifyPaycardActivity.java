/**
 * 
 */
package cn.sx.decentworld.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.SetUserInfo;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ModifyPaycardActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年12月9日 上午10:43:17
 */
@EActivity(R.layout.activity_modify_paycard)
public class ModifyPaycardActivity extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG = "ModifyPaycardActivity";
	@Bean
	ToastComponent toast;
	@Bean
	TitleBar titleBar;
	// ali选项
	@ViewById(R.id.rb_paycard_alipay)
	RadioButton rb_paycard_alipay;
	// weixin选项
	@ViewById(R.id.rb_paycard_weixin)
	RadioButton rb_paycard_weixin;
	// 阿里账号
	@ViewById(R.id.et_paycard_alipay)
	EditText et_paycard_alipay;
	// 微信账号
	@ViewById(R.id.et_paycard_weixin)
	EditText et_paycard_weixin;
	// 确认
	@ViewById(R.id.btn_paycard_confirm)
	Button btn_paycard_confirm;
	
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	
	private int accountType = -1;
	private String account = "";
	@Bean
	SetUserInfo setUserInfo;
	

	@AfterViews
	void init()
	{
		initView();
		initEvent();
		initData();
	}

	/**
	 * 初始化视图
	 */
	private void initView()
	{
		titleBar.setTitleBar("返回", "填写或修改账号");
	}

	/**
	 * 初始化事件
	 */
	private void initEvent()
	{
		rb_paycard_alipay.setOnClickListener(this);
		rb_paycard_weixin.setOnClickListener(this);
		btn_paycard_confirm.setOnClickListener(this);
		main_header_left.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		//从数据库或网络获取数据
		accountType = 1;
		selectType(accountType);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.rb_paycard_alipay:
				//选择支付宝
				if(accountType == RechargePayMethodActivity.WX)
				{
					accountType = RechargePayMethodActivity.ALIPAY;
					selectType(accountType);
				}
				break;
			case R.id.rb_paycard_weixin:
				//选择微信
				if(accountType == RechargePayMethodActivity.ALIPAY)
				{
					accountType = RechargePayMethodActivity.WX;
					selectType(accountType);
				}
				break;
			case R.id.btn_paycard_confirm:
				//调用服务器接口
				submitResult();
				break;
			case R.id.main_header_left:
				finish();
				break;
			default:
				break;
		}
	}
	
	/**
	 * 提交结果到服务器
	 */
	private void submitResult()
	{
		if(accountType == RechargePayMethodActivity.ALIPAY)
		{
			account = et_paycard_alipay.getText().toString().trim();
		}
		else
		{
			account = et_paycard_weixin.getText().toString().trim();
		}
		if(CommUtil.isNotBlank(account))
		{
			setUserInfo.setPaycardAccount(DecentWorldApp.getInstance().getDwID(), accountType, account, this);
		}
		else
		{
			LogUtils.i(TAG, "账号为空");
			toast.show("账号为空");
		}
	}

	/**
	 * 设置选择类型
	 * @param payCardType
	 */
	private void selectType(int payCardType)
	{
		if(payCardType == RechargePayMethodActivity.ALIPAY)
		{
			rb_paycard_alipay.setChecked(true);
			et_paycard_alipay.setEnabled(true);
			rb_paycard_weixin.setChecked(false);
			et_paycard_weixin.setEnabled(false);
			
			et_paycard_alipay.setFocusable(true);
			et_paycard_alipay.setFocusableInTouchMode(true);
			et_paycard_alipay.requestFocus();
		}
		else if(payCardType == RechargePayMethodActivity.WX)
		{
			rb_paycard_alipay.setChecked(false);
			et_paycard_alipay.setEnabled(false);
			rb_paycard_weixin.setChecked(true);
			et_paycard_weixin.setEnabled(true);
			
			et_paycard_weixin.setFocusable(true);
			et_paycard_weixin.setFocusableInTouchMode(true);
			et_paycard_weixin.requestFocus();
		}
	}
}
