///**
// * 
// */
//package cn.sx.decentworld.activity;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.util.HashMap;
//import java.util.Locale;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import cn.sx.decentworld.DecentWorldApp;
//import cn.sx.decentworld.R;
//import cn.sx.decentworld.bean.UserInfo;
//import cn.sx.decentworld.common.Constants;
//import cn.sx.decentworld.component.TitleBar;
//import cn.sx.decentworld.component.ToastComponent;
//import cn.sx.decentworld.component.ui.RechargeComponent;
//import cn.sx.decentworld.fragment.ChatFragment;
//import cn.sx.decentworld.network.SendUrl;
//import cn.sx.decentworld.network.SendUrl.HttpCallBack;
//import cn.sx.decentworld.network.entity.ResultBean;
//import cn.sx.decentworld.utils.LogUtils;
//import cn.sx.decentworld.utils.ViewUtil;
//
//import com.android.volley.Request.Method;
//import com.googlecode.androidannotations.annotations.AfterViews;
//import com.googlecode.androidannotations.annotations.Bean;
//import com.googlecode.androidannotations.annotations.Click;
//import com.googlecode.androidannotations.annotations.EActivity;
//import com.googlecode.androidannotations.annotations.ViewById;
//
///**
// * @ClassName: RechargeActivity.java
// * @Description:
// * @author: cj
// * @date: 2015年8月6日 上午9:00:18
// */
//@EActivity(R.layout.activity_recharge)
//public class RechargeActivity extends Activity implements OnClickListener {
//	private static final String TAG = "RechargeActivity";
//	// 支付请求
//	private static final int REQUEST_CODE_PAYMENT = 0;
//	/**
//	 * 支付类型
//	 */
//	// public static final int ALIPAY = 0;// 手机
//	// public static final int ALIPAY_WAP = 1;// 网页
//	// public static final int ALIPAY_QR = 2;// 二维码
//	// public static final int ALIPAY_PC_DIRECT = 3;// 电脑
//	// public static final int WX = 4;
//	// public static final int WX_PUB = 5;
//	// public static final int WX_PUB_QR = 6;
//
//	public static final int ALIPAY = 0;// 阿里
//	public static final int WX = 1;// 微信
//	@Bean
//	ToastComponent toast;
//	@ViewById(R.id.ll_recharge_root)
//	LinearLayout ll_recharge_root;
//	@ViewById(R.id.et_recharge_amount)
//	EditText et_recharge_amount;
//	@ViewById(R.id.btn_recharge_alipay)
//	Button btn_recharge_alipay;
//	@ViewById(R.id.btn_recharge_wechat)
//	Button btn_recharge_wechat;
//	@Bean
//	TitleBar titleBar;
//	@Bean
//	RechargeComponent rechargeComponent;
//	private String currentAmount = "";
//	private String charge;
//	public static final int REQUEST_CHARGE_SUCCESS = 2000;
//	public static final int REQUEST_CHARGE_FAIL = 2001;
//
//	/**
//	 * 接下来时测试设置身家、身家接口的代码
//	 */
//	private SendUrl sendUrl;
//
//	@AfterViews
//	void init() {
//		ViewUtil.scaleContentView(ll_recharge_root);
//		titleBar.setTitleBar("返回", "充值", null);
//		initListener();
//		sendUrl = new SendUrl(RechargeActivity.this);
//	}
//
//	/**
//	 * 设置支付按键监听
//	 */
//	private void initListener() {
//		btn_recharge_alipay.setOnClickListener(this);
//		btn_recharge_wechat.setOnClickListener(this);
//	}
//
//	TextWatcher amountTextWatcher = new TextWatcher() {
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before,
//				int count) {
//
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count,
//				int after) {
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			if (!s.toString().equals(currentAmount)) {
//				et_recharge_amount.removeTextChangedListener(this);
//				String replaceable = String.format("[%s, \\s.]", NumberFormat
//						.getCurrencyInstance(Locale.CHINA).getCurrency()
//						.getSymbol(Locale.CHINA));
//				String cleanString = s.toString().replaceAll(replaceable, "");
//
//				if (cleanString == ""
//						|| new BigDecimal(cleanString).toString().equals("0")) {
//					et_recharge_amount.setText(null);
//				} else {
//					double parsed = Double.parseDouble(cleanString);
//					String formatted = NumberFormat.getCurrencyInstance(
//							Locale.CHINA).format((parsed / 100));
//					currentAmount = formatted;
//					et_recharge_amount.setText(formatted);
//					et_recharge_amount.setSelection(formatted.length());
//				}
//				et_recharge_amount.addTextChangedListener(this);
//			}
//		}
//	};
//
//	@Override
//	public void onClick(View v) {
//		String dwID = DecentWorldApp.getInstance().getDwID();
//		String amountText = et_recharge_amount.getText().toString();
//		if (amountText.equals(""))
//			return;
//
//		String replaceable = String.format("[%s, \\s.]",
//				NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency()
//						.getSymbol(Locale.CHINA));
//		String cleanString = amountText.toString().replaceAll(replaceable, "");
//		int amount = Integer.valueOf(new BigDecimal(cleanString).toString());
//		// 支付宝，微信支付 按键的点击响应处理
//		switch (v.getId()) {
//		case R.id.btn_recharge_alipay:
//			LogUtils.i(TAG, "点击了支付宝支付按钮");
//			setBtnListener();
//			getCharge(ALIPAY, amount, dwID, handler);
//
//			break;
//		case R.id.btn_recharge_wechat:
//			LogUtils.i(TAG, "点击了微信支付按钮");
//			setBtnListener();
//			getCharge(WX, amount, dwID, handler);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * 按键点击之后的禁用，防止重复点击
//	 */
//	public void setBtnListener() {
//		LogUtils.i(TAG, "setBtnListener");
//		btn_recharge_wechat.setOnClickListener(null);
//		btn_recharge_alipay.setOnClickListener(null);
//	}
//
//	/**
//	 * 获取charge
//	 * 
//	 * @param payType
//	 *            支付类型
//	 * @param amount
//	 *            支付金额
//	 * @param handler
//	 *            回调句柄
//	 */
//	public void getCharge(int payType, int amount, String dwID, Handler handler) {
//		LogUtils.i(TAG, "从DW服务器获取charge");
//		rechargeComponent.requestCharge(payType, amount, dwID, handler);
//	}
//
//	public Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case REQUEST_CHARGE_SUCCESS:
//				startPayment((String) msg.obj);
//				break;
//			case REQUEST_CHARGE_FAIL:
//				LogUtils.i(TAG, "从服务器请求charge......failure");
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	public void startPayment(String charge) {
//		LogUtils.i(TAG, "requestPayment  " + charge);
//		if (null == charge) {
//			showMsg("请求出错", "请检查URL", "URL无法获取charge");
//			return;
//		}
//		Intent intent = new Intent();
//		String packageName = getPackageName();
//		ComponentName componentName = new ComponentName(packageName,
//				packageName + ".wxapi.WXPayEntryActivity");
//		intent.setComponent(componentName);
//		intent.putExtra(com.pingplusplus.android.PaymentActivity.EXTRA_CHARGE,
//				charge);
//		startActivityForResult(intent, REQUEST_CODE_PAYMENT);
//	}
//
//	/**
//	 * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。 最终支付成功根据异步通知为准
//	 */
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		LogUtils.i(TAG, "支付返回结果");
//		btn_recharge_wechat.setOnClickListener(RechargeActivity.this);
//		btn_recharge_alipay.setOnClickListener(RechargeActivity.this);
//		// 支付页面返回处理
//		if (requestCode == REQUEST_CODE_PAYMENT) {
//			if (resultCode == Activity.RESULT_OK) {
//				LogUtils.i(TAG, "支付返回结果 Result_OK");
//				String result = data.getExtras().getString("pay_result");
//				/*
//				 * 处理返回值 "success" - payment succeed "fail" - payment failed
//				 * "cancel" - user canceld "invalid" - payment plugin not
//				 * installed 如果是银联渠道返回 invalid，调用
//				 * UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
//				 */
//				String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
//				String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//				LogUtils.i(TAG, "result=" + result + ",errorMsg=" + errorMsg
//						+ ",extraMsg=" + extraMsg);
//				showMsg(result, errorMsg, extraMsg);
//			}
//		}
//	}
//
//	public void showMsg(String title, String msg1, String msg2) {
//		String str = title;
//		if (null != msg1 && msg1.length() != 0) {
//			str += "\n" + msg1;
//		}
//		if (null != msg2 && msg2.length() != 0) {
//			str += "\n" + msg2;
//		}
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(
//				RechargeActivity.this);
//		builder.setMessage(str);
//		builder.setTitle("提示");
//		builder.setPositiveButton("OK", null);
//		builder.create().show();
//	}
//
//	/**
//	 * 返回
//	 */
//	@Click(R.id.main_header_left)
//	void back() {
//		finish();
//	}
//
//	Handler mhandler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 1: {
//				et_recharge_wealth.setText("");
//				String wealth = (String) msg.obj;
//				// ChatFragment.tv_user_detail_header_asset.setText(wealth);
//				UserInfo userDetailInfo = UserInfo.queryByDwID(DecentWorldApp
//						.getInstance().getDwID());
//				userDetailInfo.setWealth(wealth);
//				userDetailInfo.save();
//				Log.i("dw", "setWealth...将身家值设置到界面上和保存到数据库操作完成！");
//			}
//				break;
//			case 2: {
//				et_recharge_worth.setText("");
//				String worth = (String) msg.obj;
//				ChatFragment.tv_chat_me_worth.setText(worth);
//				UserInfo userDetailInfo = UserInfo.queryByDwID(DecentWorldApp
//						.getInstance().getDwID());
//				userDetailInfo.setWorth(worth);
//				userDetailInfo.save();
//				LogUtils.i(TAG, "setWorth...将身价值设置到界面上和保存到数据库操作完成！");
//			}
//				break;
//
//			default:
//				break;
//			}
//
//		};
//	};
//	@ViewById(R.id.et_recharge_wealth)
//	EditText et_recharge_wealth;
//
//	@Click(R.id.btn_recharge_wealth_set)
//	public void setWealthF() {
//		String wealth = et_recharge_wealth.getText().toString().trim();
//		if ((!wealth.equals("")) && (wealth.length() <= 8)) {
//			setWealth(DecentWorldApp.getInstance().getDwID(), wealth);
//		} else if (wealth.length() > 8) {
//			toast.show("身家值不能超过8位");
//		} else if (wealth.equals("")) {
//			toast.show("自定义的身家值不能为空");
//		}
//	}
//
//	@ViewById(R.id.et_recharge_worth)
//	EditText et_recharge_worth;
//
//	@Click(R.id.btn_recharge_worth_set)
//	public void setWorthF() {
//		LogUtils.i(TAG, "设置身价");
//		String worth = et_recharge_worth.getText().toString().trim();
//		if ((!worth.equals("")) && (worth.length() <= 5)) {
//			setWorth(DecentWorldApp.getInstance().getDwID(), worth);
//		} else if (worth.length() > 5) {
//			toast.show("身家值不能超过5位");
//		} else if (worth.equals("")) {
//			toast.show("自定义的身家值不能为空");
//		}
//	}
//
//	/**
//	 * 已废弃
//	 * 
//	 * @param dwID
//	 * @param wealth
//	 */
//	public void setWealth(String dwID, final String wealth) {
//		LogUtils.i(TAG, "setWealth...begin");
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("dwID", dwID);
//		map.put("amount", wealth);
//		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "",
//				Method.GET, new HttpCallBack() {
//					@Override
//					public void onSuccess(String response, ResultBean msg) {
//						if (msg.getResultCode() == 2222) {
//							LogUtils.i(TAG,
//									"setWealth...msg.getMsg=" + msg.getMsg()
//											+ ",msg.getData" + msg.getData());
//							Message message = new Message();
//							message.what = 1;
//							message.obj = wealth;
//							mhandler.sendMessage(message);
//						}
//						if (msg.getResultCode() == 3333) {
//							LogUtils.i(TAG, "setWealth...failure");
//						}
//					}
//
//					@Override
//					public void onFailure(String e) {
//						LogUtils.i(TAG, "setWealth...failure,cause by:" + e);
//					}
//				});
//		LogUtils.i(TAG, "setWealth...end");
//	}
//
//	public void setWorth(String dwID, final String worth) {
//		LogUtils.i(TAG, "setWorth...begin");
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("dwID", dwID);
//		map.put("worth", worth);
//		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
//				+ "/user/setWorth", Method.GET, new HttpCallBack() {
//			@Override
//			public void onSuccess(String response, ResultBean msg) {
//				if (msg.getResultCode() == 2222) {
//					LogUtils.i(TAG, "setWorth...msg.getMsg=" + msg.getMsg()
//							+ ",msg.getData" + msg.getData());
//					Message message = new Message();
//					message.what = 2;
//					message.obj = worth;
//					mhandler.sendMessage(message);
//				}
//				if (msg.getResultCode() == 3333) {
//					LogUtils.i(TAG, "setWorth...failure");
//				}
//			}
//
//			@Override
//			public void onFailure(String e) {
//				LogUtils.i(TAG, "setWorth...failure,cause by:" + e);
//			}
//		});
//		LogUtils.i(TAG, "setWealth...end");
//	}
//
//}
