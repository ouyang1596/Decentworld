/**
 * 
 */
package cn.sx.decentworld.fragment.benefit;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.WeixinInComeActivity;
import cn.sx.decentworld.adapter.RecommendBenefitListAdapter;
import cn.sx.decentworld.bean.RecommendBenefitList;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.config.ConstantConfig;
import cn.sx.decentworld.config.ConstantConfig.PayMethod;
import cn.sx.decentworld.dialog.VerifyBenefitPwdDialog;
import cn.sx.decentworld.dialog.VerifyBenefitPwdDialog.VerifyBenefitPwdListener;
import cn.sx.decentworld.dialog.incom.CashDrawDialog;
import cn.sx.decentworld.dialog.incom.CashDrawDialog.CashDrawListener;
import cn.sx.decentworld.dialog.incom.IncomeRemindDialog;
import cn.sx.decentworld.dialog.incom.IncomeRemindDialog.IncomeRemindListener;
import cn.sx.decentworld.dialog.incom.SetAlipayAccountDialog;
import cn.sx.decentworld.dialog.incom.SetAlipayAccountDialog.SetAlipayAccountListener;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.engine.BenefitEngine.DrawCashListener;
import cn.sx.decentworld.engine.BenefitEngine.GetCashBenefitAccountListener;
import cn.sx.decentworld.engine.BenefitEngine.SetPaycardAccountLitener;
import cn.sx.decentworld.entity.db.SelfExtraInfo;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfExtraInfoManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.DataConvertUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: CashBenefitFragment.java
 * @Description: 现金收益界面
 * @author: cj
 * @date: 2016年1月14日 上午11:01:28
 */
@EFragment(R.layout.fragment_cash_benefit)
public class CashBenefitFragment extends BaseFragment implements OnClickListener {
	/**
	 * 常量
	 */
	private static final String TAG = "CashBenefitFragment";
	/** 请求码 **/
	private static final int GET_RECOMMEND_BENEFIT_LIST = 3;// 获得推荐收益列表
	private static final int GET_ADVANCE_AUTH = 4;// 获取用户是否自动转账的开关权限
	private static final int RECOM_DRAW = 6;// 推荐收益提现
	private static final int SET_RECOM_BENEFIT_ACCOUNT = 7;// 设置推荐收益账号

	/**
	 * 提示语
	 */
	private static final String NO_ACCOUNT = "你还没有绑定账号";
	private static final String NO_BENEFIT = "亲，你还没有收益哦";

	/**
	 * 工具类
	 */
	@Bean
	GetUserInfo getUserInfo;
	@Bean
	ToastComponent toast;

	/**
	 * 界面资源
	 */
	/** 可预期总额 **/
	@ViewById(R.id.tv_cash_benefit_total_big)
	TextView tv_cash_benefit_total_big;
	@ViewById(R.id.tv_cash_benefit_total_small)
	TextView tv_cash_benefit_total_small;
	/** 历史返现 **/
	@ViewById(R.id.tv_cash_benefit_back_big)
	TextView tv_cash_benefit_back_big;
	@ViewById(R.id.tv_cash_benefit_back_small)
	TextView tv_cash_benefit_back_small;

	/** 推荐收益账单 **/
	@ViewById(R.id.lv_cash_benefit)
	ListViewForScrollView listView;

	/** 没有任何推荐人的提示 **/
	@ViewById(R.id.ll_cash_benefit_remind)
	LinearLayout ll_cash_benefit_remind;

	/**
	 * 变量
	 */
	private String userID = "";
	private boolean isPrepared = false;
	private boolean isAutoTransfer = false;
	private int accountType = PayMethod.NO.getIndex();// 绑定的账号类型
	private String accountName = "";// 账号名字
	private List<RecommendBenefitList> listData;
	private RecommendBenefitListAdapter listAdapter;

	/**
	 * 入口
	 */
	@AfterViews
	void init() {
		initVar();
		isPrepared = true;
		lazyLoad();
	}

	/**
	 * 初始化变量
	 */
	private void initVar() {
		EventBus.getDefault().register(this);
		userID = DecentWorldApp.getInstance().getDwID();
		listData = new ArrayList<RecommendBenefitList>();
		listAdapter = new RecommendBenefitListAdapter(getActivity(), listData);
		listView.setAdapter(listAdapter);
	}

	/**
	 * 加载数据
	 */
	@Override
	protected void lazyLoad() {
		if (isPrepared) {
			if (NetworkUtils.isNetWorkConnected(getActivity())) {
				/** 有网络 从服务器获取推荐收益信息 **/
				getUserInfo.getRecommendBenefitList(userID, handler, GET_RECOMMEND_BENEFIT_LIST);
				/** 有网络 从服务器获取是否自动转账 **/
				getUserInfo.getAutoAuthority(userID, handler, GET_ADVANCE_AUTH);
				/** 有网络 从服务器获取绑定的账号信息 **/
				BenefitEngine benefitEngine = new BenefitEngine();
				benefitEngine.getCashBenefitAccount(new GetCashBenefitAccountListener() {
					@Override
					public void onSuccess(int type, String account) {
						processAccountResult(type, account);
					}

					@Override
					public void onFailure(String cause) {
						toast.show(cause);
					}
				});
			} else {
				SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
				/** 没有网络 从本地获取推荐收益信息 **/
				listData.clear();
				listData = RecommendBenefitList.queryBy(userID);
				updateUiWithRec(listData, extraInfo);

				// 没有网络 从本地获取绑定的账号信息
				int t_accountType = extraInfo.getAccountType();
				String t_accountName = extraInfo.getAccountName();
				if (t_accountType != -1 && CommUtil.isNotBlank(t_accountName)) {
					accountType = t_accountType;
					accountName = t_accountName;
				}
			}
		}
	}

	/**
	 * 请求回调
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_RECOMMEND_BENEFIT_LIST:
				processBenefitListResult(msg);
				break;
			case GET_ADVANCE_AUTH:
				processAutoTransferResult(msg);
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 处理从服务器获得的推荐收益结果
	 * 
	 * @param msg
	 */
	private void processBenefitListResult(Message msg) {
		/** 清理历史数据 **/
		listData.clear();
		RecommendBenefitList.deleteBy(userID);
		/** 获取数据、定义变量 **/
		String result = msg.obj.toString();
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray jsonArray = jsonObject.getJSONArray("recommendList");

		/** 解析数据 **/
		if (jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				RecommendBenefitList temp = new RecommendBenefitList();
				float amount = object.getFloatValue("amount");
				temp.setAmount(amount * amount);
				float benefit = object.getFloatValue("benefit");
				temp.setBenefit(benefit);
				temp.setName(object.getString("name"));
				temp.setPhoneNum(object.getString("phoneNum"));
				float stored = object.getFloatValue("stored");
				temp.setStored(stored);
				temp.setUserID(userID);
				temp.setOtherID(object.getString("dwID"));
				temp.setStatus(object.getIntValue("status"));
				temp.save();
				listData.add(temp);
			}
		}

		SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
		float totalBenefit = jsonObject.getFloatValue("totalBenefit");
		extraInfo.setRecomTotalBenefit(totalBenefit);
		float storedBenefit = jsonObject.getFloatValue("storedBenefit");
		extraInfo.setRecomStoredBenefit(storedBenefit);
		extraInfo.save();

		updateUiWithRec(listData, extraInfo);
	}

	/**
	 * 获得推荐收益数据后更新界面
	 * 
	 * @param listData
	 */
	private void updateUiWithRec(List<RecommendBenefitList> listData, SelfExtraInfo extraInfo) {
		if (listData.size() > 0) {
			float allBenefit = 0;// 应得总金额
			for (RecommendBenefitList temp : listData) {
				if (temp.getStatus() != RecommendBenefitList.status_DELETED) {
					allBenefit += temp.getAmount();
				}
			}
			listAdapter.notifyDataSetChanged();
			// 计算已经获得的收益和未提现的收益并显示
			String[] total = DataConvertUtils.splitFloat(allBenefit);
			tv_cash_benefit_total_big.setText(total[0]);
			tv_cash_benefit_total_small.setText(total[1]);

			listView.setVisibility(View.VISIBLE);
			ll_cash_benefit_remind.setVisibility(View.GONE);
		} else {
			LogUtils.i(TAG, "你还没有推荐过任何人哦！");
			listView.setVisibility(View.GONE);
			ll_cash_benefit_remind.setVisibility(View.VISIBLE);
		}

		/** 初始化历史总收益 **/
		if (extraInfo != null) {
			float totalBenefit = extraInfo.getRecomTotalBenefit();
			float storedBenefit = extraInfo.getRecomStoredBenefit();

			String[] back = DataConvertUtils.splitFloat(totalBenefit);
			tv_cash_benefit_back_big.setText(back[0]);
			tv_cash_benefit_back_small.setText(back[1]);

			String[] not = DataConvertUtils.splitFloat(storedBenefit);
			String storeBenefitFoloat = DataConvertUtils.formatFloat(storedBenefit);
		}
	}

	/**
	 * 处理获取的自动转账权限的结果
	 * 
	 * @param msg
	 */
	private void processAutoTransferResult(Message msg) {
		if (msg.arg1 == 0) {
			/** 失败 **/
			getAdvanceAuthFailure();

		} else if (msg.arg1 == 1) {
			/** 成功 **/
			getAdvanceAuthSuccess(msg);
		}
	}

	/**
	 * 从服务器获取高级设置开关权限失败
	 */
	private void getAdvanceAuthFailure() {
		isAutoTransfer = false;
	}

	/**
	 * 从服务器获取高级设置开关权限成功
	 * 
	 * @param msg
	 */
	private void getAdvanceAuthSuccess(Message msg) {
		SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
		JSONObject jObject = JSON.parseObject(msg.obj.toString());
		Boolean autoTransfer = jObject.getBooleanValue("autoTransfer");
		if (autoTransfer != null) {
			isAutoTransfer = autoTransfer;
			extraInfo.setAutoTransfer(autoTransfer);
			extraInfo.save();
		}
	}

	/**
	 * 处理从服务器获取的推荐返利账号结果
	 * 
	 * @param msg
	 */
	private void processAccountResult(int type, String account) {
		LogUtils.i(TAG, "type=" + type + "，account = " + account);
		SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
		accountType = type;
		extraInfo.setAccountType(type);
		if (CommUtil.isNotBlank(account)) {
			accountName = account;
			extraInfo.setAccountName(account);
		} else {
			accountName = "";
			extraInfo.setAccountName(account);
		}
		extraInfo.save();
	}

	/**
	 * 处理推荐收益提现，提现成功，将界面的"未提零钱"/"提取"显示数目置为零
	 * @param msg
	 */
	protected void UpdateUIWithDrawCash() {
		float notTakeout = 0.0f;
		String[] not = DataConvertUtils.splitFloat(notTakeout);
		getUserInfo.getRecommendBenefitList(userID, handler, GET_RECOMMEND_BENEFIT_LIST);
	}

	@Override
	public void turnToTab(int tab) {

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	/**
	 * 点击自动提现开关时提示
	 * 
	 * @param isAutoTransfer
	 */
	private void isAutoTransfer(final boolean currentStatus) {
		verifyBenefitPwd(SET_AUTO_TRANSFER, 0, currentStatus);
	}

	/**
	 * 切换自动提现开关的状态
	 */
	private void setAutoTransferSwitch(final boolean currentStatus, String password) {
		new BenefitEngine().setAutoTransfer(currentStatus, password, new NetCallback() {
			@Override
			public void onSuccess(String msg) {
				// 设置成功
				if (currentStatus) {
					isAutoTransfer = false;
				} else {
					isAutoTransfer = true;
				}
			}

			@Override
			public void onFailure(String cause) {
				// 设置失败
				toast.show(cause);
			}
		});
	}

	/**
	 * 处理绑定支付宝的点击事件
	 */
	private void bindAlipay() {
		/** 无网络 **/
		if (!NetworkUtils.isNetWorkConnected(getActivity())) {
			toast.show("请检查网络");
			return;
		}
		/** 有网络 **/
		verifyBenefitPwd(SET_ALIPAY, 0, false);
	}

	/**
	 * 处理绑定微信的点击事件
	 * 
	 * @param v
	 */
	private void bindWx() {
		/** 无网络 **/
		if (!NetworkUtils.isNetWorkConnected(getActivity())) {
			toast.show("请检查网络");
			return;
		}
		verifyBenefitPwd(SET_WX, 0, false);
	}

	// 设置微信
	private static final int SET_WX = 1;
	private static final int SET_ALIPAY = 2;
	private static final int DRAW_CASH = 3;
	private static final int SET_AUTO_TRANSFER = 4;

	/**
	 * 验证收益密码
	 * 
	 * @param type
	 */
	private void verifyBenefitPwd(final int type, final float amount, final boolean currentStatus) {
		VerifyBenefitPwdDialog benefitPwdDialog = new VerifyBenefitPwdDialog();
		benefitPwdDialog.setListener(new VerifyBenefitPwdListener() {
			@Override
			public void onSuccess(final String tempToken) {
				switch (type) {
				case SET_WX:
					/** 有网络 **/
					Intent intent = new Intent(getActivity(), WeixinInComeActivity.class);
					intent.putExtra("tempToken", tempToken);
					startActivity(intent);
					break;
				case SET_ALIPAY:
					SetAlipayAccountDialog accountDialog = new SetAlipayAccountDialog();
					accountDialog.setListener(new SetAlipayAccountListener() {
						@Override
						public void onSubmit(final String account) {
							BenefitEngine benefitEngine = new BenefitEngine();
							benefitEngine.setPaycardAccount(0, account, tempToken, new SetPaycardAccountLitener() {

								@Override
								public void onSuccess(int maccountType, String account) {
									accountType = maccountType;
									accountName = account;
									toast.show("设置成功");
								}

								@Override
								public void onFailure(String cause) {
									toast.show(cause);
								}
							});
						}
					});
					if (accountType == 0) {
						accountDialog.setOldAccount(accountName);
					}
					accountDialog.show(getFragmentManager(), "alipay");
					break;
				case DRAW_CASH:
					final BenefitEngine benefitEngine = new BenefitEngine();
					CashDrawDialog cashDrawDialog = new CashDrawDialog();
					if (accountType == ConstantConfig.PayMethod.ALIPAY.getIndex())
						cashDrawDialog.setAlipay(amount, accountName);
					else
						cashDrawDialog.setWx(amount);
					cashDrawDialog.setListener(new CashDrawListener() {
						@Override
						public void onSubmit() {
							// 调用接口，提现
							benefitEngine.drawCash(tempToken, new DrawCashListener() {
								@Override
								public void onSuccess() {
									// 提现成功，更新界面，并从新从网络获取收益列表
									UpdateUIWithDrawCash();
								}

								@Override
								public void onFailure(String cause) {
									toast.show(cause);
								}
							});
						}
					});
					cashDrawDialog.show(getFragmentManager(), "drawCash");

					break;

				case SET_AUTO_TRANSFER:
					/** 此开关仅对微信有效，关闭时提示“关闭开关后，需要手动提现”,打开时提示“打开开关后，需要自己缴纳手续费” **/
					String info = "";
					if (currentStatus)
						// info = "你的选择很好，这样手续费最低！原因：...";// 手动提现的好处
						info = "选择手动提现，你的选择很好，这样手续费最低！";// 手动提现的好处
					else
						// info =
						// "你的选择很对，这样最方便！但是：小于2元时。。微信。。支付宝。。。；大于2时。。微信。。支付宝。。由此我们帮你避免。。。";//
						// 自动提现的好处
						info = "选择自动提现，你的选择很对，这样更方便！";// 自动提现的好处

					IncomeRemindDialog dialog = new IncomeRemindDialog();
					dialog.setTitle("设置返现方式");
					dialog.setRemindInfo(info);
					dialog.setListener(new IncomeRemindListener() {
						@Override
						public void onSubmit() {
							setAutoTransferSwitch(currentStatus, tempToken);
						}
					});
					dialog.show(getFragmentManager(), "setAutoTransferSwitch");

					break;
				default:
					break;
				}

			}

			@Override
			public void onFailure(String cause) {
				toast.show(cause);
			}
		});
		benefitPwdDialog.show(getFragmentManager(), "");

	}

	/**
	 * 设置微信成功并更新界面
	 * 
	 * @param code
	 */
	@Subscriber(tag = NotifyByEventBus.NT_RETURN_WX_CODE)
	public void setWxSuccess(String code) {
		accountType = ConstantConfig.PayMethod.WXPAY.getIndex();
		accountName = code;
		toast.show("设置成功");
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	/**
	 * 更新数据
	 */
	@Override
	public void onResume() {
		super.onResume();
		LogUtils.i(TAG, "onResume");

	}
}
