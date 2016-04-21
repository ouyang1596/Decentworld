package cn.sx.decentworld.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.PayResult;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RechargeComponent;
import cn.sx.decentworld.logSystem.LogUtils;

import com.alipay.sdk.app.PayTask;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

@EActivity(R.layout.activity_recharge_pay_method)
public class RechargePayMethodActivity extends BaseFragmentActivity {
	private static final String TAG = "RechargePayMethodActivity";
	// @ViewById(R.id.ll_ali_pay)
	// LinearLayout llAliPay;
	// @ViewById(R.id.ll_wx)
	// LinearLayout llWX;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_recharge_alipay)
	ImageView llAliPay;
	@ViewById(R.id.iv_recharge_wx)
	ImageView llWX;
	@ViewById(R.id.tv_money)
	TextView tvMoney;

	public static int isToSocialStatusActivity = -1;// 0表示不跳转1表示跳转
	/**
	 * 支付类型
	 */
	public static final int REQUEST_CHARGE_SUCCESS = 2000;
	public static final int REQUEST_CHARGE_FAIL = 2001;
	private static final int REQUEST_CODE_PAYMENT = 0;
	public static final int ALIPAY = 0;// 阿里
	public static final int WX = 1;// 微信
	String data = "{ \"appid\":\"wxb4ba3c02aa476ea1\", \"noncestr\":\"403bda9c6cd333c17da3f27ef2a8a877\", \"package\":\"Sign=WXPay\", \"partnerid\":\"10000100\", \"prepayid\":\"wx201512231554358f1cb815080434577940\", \"timestamp\":\"1450857275\", \"sign\":\"1402743893112328B413DC09D46A3100\" }";
	@Bean
	RechargeComponent rechargeComponent;
	@Bean
	ToastComponent toast;
	private String moneyCount;
	private FragmentManager fragmentManager;
	private boolean isRecommend = false;
	private int doubtWan = -1;// 0 假的资料，再试一次 1不通过，直接买
	private String dwID = "";
	private String phoneNum = "";
	private String out_trade_no;
	private int isToWorth;
	private IWXAPI api;
	private Handler aliPayHandle = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2222:
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					out_trade_no = object.getString("data");
					startPayment(out_trade_no);
				} catch (JSONException e) {
					toast.show("解析错误");
				}
				break;
			default:
				break;
			}
		};
	};
	private Handler wxHandle = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2222:
				llWX.setEnabled(false);
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					JSONObject wxObject = object.getJSONObject("data");
					// JSONObject wxObject = new JSONObject(data);测试
					if (null != wxObject && !wxObject.has("retcode")) {
						PayReq req = new PayReq();
						// req.appId = "wxf8b4f85f3a794e77"; // 测试用appId
						req.appId = wxObject.getString("appid");
						req.partnerId = wxObject.getString("partnerid");
						req.prepayId = wxObject.getString("prepayid");
						req.nonceStr = wxObject.getString("noncestr");
						req.timeStamp = wxObject.getString("timestamp");
						req.packageValue = wxObject.getString("package");
						req.sign = wxObject.getString("sign");
						// req.extData = "app data"; // optional
						Toast.makeText(RechargePayMethodActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
						// 将该app注册到微信
						api.registerApp(Constants.APP_ID);
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
						api.sendReq(req);
					} else {
						Log.d("PAY_GET", "返回错误" + wxObject.getString("retmsg"));
						Toast.makeText(RechargePayMethodActivity.this, "返回错误" + wxObject.getString("retmsg"), Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					toast.show("解析错误");
				}
				llWX.setEnabled(true);
				break;
			default:
				break;
			}
		};
	};

	@AfterViews
	public void init() {
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
		fragmentManager = getSupportFragmentManager();
		dwID = DecentWorldApp.getInstance().getDwID();
		RGetIntent();
		tvTitle.setText("支付方式");
		llAliPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (doubtWan == 0) {
					getDoubtWanCharge(ALIPAY, ConstantNet.API_DOUBT_PAY_RETRY, aliPayHandle);
					return;
				}
				if (doubtWan == 1) {
					getDoubtWanCharge(ALIPAY, ConstantNet.API_DOUBT_NO_PASS_BUY, aliPayHandle);
					return;
				}
				if (doubtWan == 2) {
					getDoubtWanCharge(ALIPAY, ConstantNet.API_WAN_NO_PASS_BUY, aliPayHandle);
					return;
				}

				if (isRecommend) {
					getRecommendCharge(ALIPAY, moneyCount, dwID, phoneNum, aliPayHandle);
				} else {
					getCharge(ALIPAY, moneyCount, dwID, aliPayHandle);
				}
			}
		});
		llWX.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (doubtWan == 0) {
					getDoubtWanCharge(WX, ConstantNet.API_DOUBT_PAY_RETRY, wxHandle);
					return;
				}
				if (doubtWan == 1) {
					getDoubtWanCharge(WX, ConstantNet.API_DOUBT_NO_PASS_BUY, wxHandle);
					return;
				}
				if (doubtWan == 2) {
					getDoubtWanCharge(WX, ConstantNet.API_WAN_NO_PASS_BUY, aliPayHandle);
					return;
				}
				if (isRecommend) {
					getRecommendCharge(WX, moneyCount, dwID, phoneNum, wxHandle);
				} else {
					getCharge(WX, moneyCount, dwID, wxHandle);
				}
			}
		});
		if (doubtWan == 1) {
			tvMoney.setText("直接花300元去疑，请选择支付方式");
		} else if (isRecommend) {
			tvMoney.setVisibility(View.GONE);
		} else if (doubtWan == 0) {
			tvMoney.setText("上次不该作假的，再次去疑需要花费300块");
		}
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void getDoubtWanCharge(int channel, String api, Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", DecentWorldApp.getInstance().getDwID());
		map.put("channel", "" + channel);
		rechargeComponent.getDoubtWanCharge(map, api, handler);
	}

	private void RGetIntent() {
		isToSocialStatusActivity = getIntent().getIntExtra(ConstantIntent.COMMOM_IS_TO_SOCIALSTATUSACTIVITY, -1);
		doubtWan = getIntent().getIntExtra(ConstantIntent.FRAGMENT_DIALOG_DOUBT_WAN, -1);
		isRecommend = getIntent().getBooleanExtra("isRecommend", false);
		// isGuarantee = getIntent().getBooleanExtra("isGuarantee", false);
		isToWorth = getIntent().getIntExtra(Constants.IS_TO_WORTH, -1);
		if (isRecommend) {
			LogUtils.i(TAG, "推荐别人...充值");
			phoneNum = getIntent().getStringExtra("phoneNum");
			moneyCount = getIntent().getStringExtra("amount");
		} else {
			LogUtils.i(TAG, "普通...充值");
			moneyCount = getIntent().getStringExtra(RechargeAliPayWXActivity.WORTH);
		}
	}

	/**
	 * 获取charge
	 * 
	 * @param payType
	 *            支付类型
	 * @param amount
	 *            支付金额
	 * @param aliPayHandle
	 *            回调句柄
	 */
	public void getCharge(int payType, String amount, String dwID, Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", String.valueOf(payType));
		map.put("amount", amount);
		map.put("dwID", dwID);
		rechargeComponent.requestCharge(map, handler);
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
	public void getRecommendCharge(int payType, String amount, String dwID, String phoneNum, Handler handler) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", String.valueOf(payType));
		map.put("amount", amount);
		map.put("dwID", dwID);
		map.put("phoneNum", phoneNum);
		rechargeComponent.getRecommendCharge(map, handler);
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
	// public void getGuaranteeCharge(int payType, String amount, String dwID,
	// String phoneNum, Handler handler) {
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("type", String.valueOf(payType));
	// map.put("amount", amount);
	// map.put("dwID", dwID);
	// map.put("phoneNum", phoneNum);
	// rechargeComponent.getGuaranteeCharge(map, handler);
	// }

	public void startPayment(final String charge) {
		if (null == charge) {
			toast.show("请求出错,请检查URL,URL无法获取订单号");
			return;
		}
		// if (TextUtils.isEmpty(Constants.PARTNER)
		// || TextUtils.isEmpty(Constants.RSA_PRIVATE)
		// || TextUtils.isEmpty(Constants.SELLER)) {
		// new AlertDialog.Builder(this)
		// .setTitle("警告")
		// .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
		// .setPositiveButton("确定",
		// new DialogInterface.OnClickListener() {
		// public void onClick(
		// DialogInterface dialoginterface, int i) {
		// //
		// finish();
		// }
		// }).show();
		// return;
		// }
		// if (TextUtils.isEmpty(moneyCount)) {
		// toast.show("充值失败");
		// }
		// // 订单
		// String orderInfo = getOrderInfo("充值", dwID, moneyCount);
		//
		// // 对订单做RSA 签名
		// String sign = sign(orderInfo);
		// try {
		// // 仅需对sign 做URL编码
		// sign = URLEncoder.encode(sign, "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// // 完整的符合支付宝参数规范的订单信息
		// final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
		// + getSignType();
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(RechargePayMethodActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(charge);
				Message msg = new Message();
				msg.what = Constants.SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	@Click(R.id.iv_back)
	void back() {
		finish();
	}

	// ------------------支付宝支付---------------------

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(RechargePayMethodActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					EventBus.getDefault().post("完成支付，更新身家", NotifyByEventBus.NT_REFRESH_WEALTH);
					if (1 == isToSocialStatusActivity) {
						// 跳到修改身价页面
						startActivity(new Intent(mContext, SocialStatusActivity_.class));
					} else {
						finish();
					}
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(RechargePayMethodActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(RechargePayMethodActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case Constants.SDK_CHECK_FLAG: {
				Toast.makeText(RechargePayMethodActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * get the sdk version. 获取SDK版本号
	 */
	// public void getSDKVersion() {
	// PayTask payTask = new PayTask(this);
	// String version = payTask.getVersion();
	// Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	// }
	//
	// /**
	// * create the order info. 创建订单信息
	// *
	// */
	// public String getOrderInfo(String subject, String body, String price) {
	//
	// // 签约合作者身份ID
	// String orderInfo = "partner=" + "\"" + Constants.PARTNER + "\"";
	//
	// // 签约卖家支付宝账号
	// orderInfo += "&seller_id=" + "\"" + Constants.SELLER + "\"";
	//
	// // 商户网站唯一订单号
	// orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
	//
	// // 商品名称
	// orderInfo += "&subject=" + "\"" + subject + "\"";
	//
	// // 商品详情
	// orderInfo += "&body=" + "\"" + body + "\"";
	//
	// // 商品金额
	// orderInfo += "&total_fee=" + "\"" + price + "\"";
	//
	// // 服务器异步通知页面路径
	// orderInfo += "&notify_url="
	// + "\""
	// + "http://112.74.13.117:80/DecentWorldServer/charge/getOrderResponse"
	// + "\"";
	// // 服务接口名称， 固定值
	// orderInfo += "&service=\"mobile.securitypay.pay\"";
	//
	// // 支付类型， 固定值
	// orderInfo += "&payment_type=\"1\"";
	//
	// // 参数编码， 固定值
	// orderInfo += "&_input_charset=\"utf-8\"";
	//
	// // 设置未付款交易的超时时间
	// // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
	// // 取值范围：1m～15d。
	// // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
	// // 该参数数值不接受小数点，如1.5h，可转换为90m。
	// orderInfo += "&it_b_pay=\"30m\"";
	//
	// // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
	// // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
	//
	// // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
	// orderInfo += "&return_url=\"m.alipay.com\"";
	// // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
	// // orderInfo += "&paymethod=\"expressGateway\"";
	// return orderInfo;
	// }

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 */
	public String getOutTradeNo() {
		// SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
		// Locale.getDefault());
		// Date date = new Date();
		// String key = format.format(date);
		// Random r = new Random();
		// key = key + r.nextInt();
		// key = key.substring(0, 15);
		// return key;
		return out_trade_no;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	// public String sign(String content) {
	// return SignUtils.sign(content, Constants.RSA_PRIVATE);
	// }

	/**
	 * get the sign type we use. 获取签名方式
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
