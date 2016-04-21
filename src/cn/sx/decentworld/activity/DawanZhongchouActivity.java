package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment.OnTrueOrFalseClickListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;

public class DawanZhongchouActivity extends BaseFragmentActivity implements OnClickListener, OnTrueOrFalseClickListener {
	private static final String TAG = "DawanZhongchouActivity";
	private TextView tvbuy, tvPriceToday;
	private String price;
	private SendUrl mSendUrl;
	private boolean canBuy;// 判断是否能购买

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dawan_zhongchou);
		DGetIntent();
		initView();
		getShareStatus();
	}

	private void DGetIntent() {
		price = getIntent().getStringExtra(ConstantIntent.PRICE);
	}

	private void initView() {
		mSendUrl = new SendUrl(this);
		tvbuy = (TextView) findViewById(R.id.tv_buy);
		tvbuy.setOnClickListener(this);
		tvPriceToday = (TextView) findViewById(R.id.tv_price_today);
		tvPriceToday.setText("当前价格：" + price + "元");
	}

	private void getShareStatus() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		showProgressDialog();
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_GET_SHARESTATUS, Method.POST,
				new HttpCallBack() {

					@Override
					public void onSuccess(String resultJSON, ResultBean resultBean) {
						LogUtils.d(TAG, "getShareStatus---" + resultBean.toString());
						hideProgressDialog();
						if (resultBean.getResultCode() == 2222) {
							canBuy = true;
						} else {
							canBuy = false;
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.d(TAG, "getShareStatus---erroe---" + e);
						hideProgressDialog();
						showToastInfo(Constants.NET_WRONG);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_buy:
			if (canBuy) {
				TrueOrFalseDialogFragment trueOrFalseDialogFragment = new cn.sx.decentworld.dialog.TrueOrFalseDialogFragment();
				trueOrFalseDialogFragment.setTips("您确定扣除一万大洋？");
				trueOrFalseDialogFragment.setOnTrueOrFalseClickListener(this);
				trueOrFalseDialogFragment.show(getSupportFragmentManager(), "trueOrFalseDialogFragment");
			} else {
				ToastUtil.showToast("今天的份额已被购买了，请明天再来");
			}

			break;
		}
	}

	@Override
	public void onTrueOrFalseClick(TrueOrFalseDialogFragment dialog, View view) {
		switch (view.getId()) {
		case R.id.tv_ensure:
			buyShare();
			break;
		}
	}

	private Handler mBuyShareHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			finish();
		};
	};

	private void buyShare() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		showProgressDialog();
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_BUY_SHARE, Method.POST, new HttpCallBack() {

			@Override
			public void onSuccess(String resultJSON, ResultBean resultBean) {
				hideProgressDialog();
				if (resultBean.getResultCode() == 2222) {
					ToastUtil.showToast("购买成功");
					mBuyShareHandler.sendEmptyMessage(2222);
				} else {
					ToastUtil.showToast(resultBean.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				ToastUtil.showToast(Constants.NET_WRONG);
			}
		});
	}

	private ProgressDialog mProDialog;

	private void showToastInfo(final String data) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(mContext, null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null != mProDialog) {
					mProDialog.dismiss();
				}
			}
		});
	}

}
