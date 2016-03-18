package cn.sx.decentworld.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RechargeComponent;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_recharge_pay_method)
public class RechargePayMethodUselessActivity extends BaseFragmentActivity {
	private static final String TAG = "RechargePayMethodActivity";
	@ViewById(R.id.ll_ali_pay)
	LinearLayout llAliPay;
	@ViewById(R.id.ll_wx)
	LinearLayout llWX;
	@ViewById(R.id.iv_back)
	ImageView ivBack;

	/**
	 * 支付类型
	 */
	// public static final int ALIPAY = 0;// 手机
	// public static final int ALIPAY_WAP = 1;// 网页
	// public static final int ALIPAY_QR = 2;// 二维码
	// public static final int ALIPAY_PC_DIRECT = 3;// 电脑
	// public static final int WX = 4;
	// public static final int WX_PUB = 5;
	// public static final int WX_PUB_QR = 6;
	public static final int REQUEST_CHARGE_SUCCESS = 2000;
	public static final int REQUEST_CHARGE_FAIL = 2001;
	private static final int REQUEST_CODE_PAYMENT = 0;

	public static final int ALIPAY = 0;// 阿里
	public static final int WX = 1;// 微信

	@Bean
	RechargeComponent rechargeComponent;
	@Bean
	ToastComponent toast;
	Integer moneyCount;
	private FragmentManager fragmentManager;
	private boolean isRecommend = false;
	private String dwID = "";
	private String phoneNum = "";

	private boolean isGuarantee = false;
	private int isToWorth;

	@AfterViews
	public void init() {
		fragmentManager = getSupportFragmentManager();
		dwID = DecentWorldApp.getInstance().getDwID();
		RGetIntent();
		llAliPay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isRecommend) {
					getRecommendCharge(ALIPAY, moneyCount, dwID, phoneNum,
							handler);
				} else if (isGuarantee) {
					getGuaranteeCharge(ALIPAY, moneyCount, dwID, phoneNum,
							handler);
				} else {
					getCharge(ALIPAY, moneyCount, dwID, handler);
				}

			}
		});
		llWX.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (isRecommend) {
					getRecommendCharge(WX, moneyCount, dwID, phoneNum, handler);
				} else if (isGuarantee) {
					getGuaranteeCharge(WX, moneyCount, dwID, phoneNum, handler);
				} else {
					getCharge(WX, moneyCount, DecentWorldApp.getInstance()
							.getDwID(), handler);
				}
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_CHARGE_SUCCESS:
				// startPayment((String) msg.obj);
				break;
			case REQUEST_CHARGE_FAIL:
				LogUtils.i("bm", "从服务器请求charge......failure");
				break;
			default:
				break;
			}
		};
	};

	private void RGetIntent() {
		isRecommend = getIntent().getBooleanExtra("isRecommend", false);
		isGuarantee = getIntent().getBooleanExtra("isGuarantee", false);
		isToWorth = getIntent().getIntExtra(Constants.IS_TO_WORTH, -1);
		if (isRecommend) {
			LogUtils.i(TAG, "推荐别人...充值");
			phoneNum = getIntent().getStringExtra("phoneNum");
			moneyCount = Integer.valueOf(getIntent().getStringExtra("amount"));
		} else if (isGuarantee) {
			LogUtils.i(TAG, "担保别人...充值");
			phoneNum = getIntent().getStringExtra("phoneNum");
			moneyCount = Integer.valueOf(getIntent().getStringExtra("amount"));
		} else {
			LogUtils.i(TAG, "普通...充值");
			String money = getIntent().getStringExtra(
					RechargeAliPayWXActivity.MONEY);
			moneyCount = Integer.valueOf(money);
		}
	}

	/**
	 * 获取charge
	 * 
	 * @param payType
	 *            支付类型
	 * @param amount
	 *            支付金额
	 * @param handler
	 *            回调句柄
	 */
	public void getCharge(int payType, int amount, String dwID, Handler handler) {
		// rechargeComponent.requestCharge(payType, amount, dwID, handler);
	}

	/**
	 * 获取推荐支付所需的charge
	 * 
	 * @param payType
	 * @param amount
	 * @param dwID
	 * @param phoneNum
	 * @param handler
	 */
	public void getRecommendCharge(int payType, int amount, String dwID,
			String phoneNum, Handler handler) {
		// rechargeComponent.getRecommendCharge(payType, amount, dwID, phoneNum,
		// handler);
	}

	/**
	 * 获取担保支付所需的charge
	 * 
	 * @param payType
	 * @param amount
	 * @param dwID
	 * @param phoneNum
	 * @param handler
	 */
	public void getGuaranteeCharge(int payType, int amount, String dwID,
			String phoneNum, Handler handler) {
		// rechargeComponent.getGuaranteeCharge(map, handler);
	}

	// public void startPayment(String charge) {
	// if (null == charge) {
	// toast.show("请求出错,请检查URL,URL无法获取charge");
	// return;
	// }
	// Intent intent = new Intent();
	// String packageName = getPackageName();
	// ComponentName componentName = new ComponentName(packageName,
	// packageName + ".wxapi.WXPayEntryActivity");
	// intent.setComponent(componentName);
	// intent.putExtra(com.pingplusplus.android.PaymentActivity.EXTRA_CHARGE,
	// charge);
	// startActivityForResult(intent, REQUEST_CODE_PAYMENT);
	// }

	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // 支付页面返回处理
	// if (requestCode == REQUEST_CODE_PAYMENT) {
	// if (resultCode == Activity.RESULT_OK) {
	// String result = data.getExtras().getString("pay_result");
	// /*
	// * 处理返回值 "success" - payment succeed "fail" - payment failed
	// * "cancel" - user canceld "invalid" - payment plugin not
	// * installed 如果是银联渠道返回 invalid，调用
	// * UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
	// */
	// String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
	// String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
	// if ("success".equals(result)) {
	// toast.show("充值成功");
	// Intent intent = new Intent(mContext,
	// SocialStatusActivity_.class);
	// if (isToWorth != 1) {
	// startActivity(intent);
	// finish();
	// return;
	// }
	// finish();
	// } else {
	// toast.show("充值失败");
	// }
	// }
	// }
	// }

	@Click(R.id.iv_back)
	void back() {
		finish();
	}
}
