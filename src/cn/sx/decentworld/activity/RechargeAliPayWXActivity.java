package cn.sx.decentworld.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_recharge_ali_pay_wx)
public class RechargeAliPayWXActivity extends BaseFragmentActivity {
	@ViewById(R.id.et_money)
	EditText etMoney;
	@ViewById(R.id.ll_pay_method)
	LinearLayout llpayMethod;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@Bean
	ToastComponent toast;
	public static String MONEY = "money";

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("充值");
		llpayMethod.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (etMoney.length() <= 0) {
					toast.show("请先输入金额");
					return;
				}
				String money = etMoney.getText().toString();
				Integer data = Integer.valueOf(money);
				if (data < 2) {
					toast.show("输入金额小于2元");
					return;
				}
				Intent intent = new Intent(mContext,
						RechargePayMethodActivity_.class);
				intent.putExtra(MONEY, etMoney.getText().toString());
				startActivity(intent);
			}
		});
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	// 对键盘的操作
	@Bean
	KeyboardComponent keyboardComponent;

	/**
	 * 打开软键盘
	 */
	private void openKeyboard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				keyboardComponent.openKeybord(etMoney);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etMoney);
	}

	/**
	 * 返回
	 */
	@Click(R.id.iv_back)
	void back() {
		finish();
	}
}
