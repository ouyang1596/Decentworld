/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.PrivacySettingInfo;
import cn.sx.decentworld.utils.ActivityCollector;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ModificationBankCardOne.java
 * @Description:
 * @author: cj
 * @date: 2015年7月25日 下午3:00:47
 */
@EActivity(R.layout.activity_modification_bank_card_two)
public class ModificationBankCardTwo extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "ModificationBankCardTwo";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.tv_modification_bank_card_two_type)
	TextView tvRealName;
	@ViewById(R.id.tv_modification_bank_card_two_number)
	TextView tvBankCard;
	@ViewById(R.id.btn_modification_bank_card_two_next)
	Button btn_modification_bank_card_two_next;
	String bankName;
	String bankCard;
	private Boolean cbIsChecked = false;
	@Bean
	PrivacySettingInfo privacyettingInfo;

	@AfterViews
	public void init() {
		NGetIntent();
		tvRealName.setText(bankName);
		tvBankCard.setText(bankCard);
		ActivityCollector.addActivity(this);
		titleBar.setTitleBar("返回", "替换(添加银行卡)");
		btn_modification_bank_card_two_next.setOnClickListener(this);
	}

	private String realName;

	private void NGetIntent() {
		bankName = getIntent()
				.getStringExtra(ModificationBankCardOne.BANK_NAME);
		bankCard = getIntent()
				.getStringExtra(ModificationBankCardOne.BANK_CARD);
		realName = getIntent()
				.getStringExtra(ModificationBankCardOne.REAL_NAME);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_modification_bank_card_two_next:
			// 跳转到下一个页面
			if (tvRealName.getText().toString().equals("")) {
				toast.show("卡类型不能为空");
				return;
			}
			if (tvBankCard.getText().toString().equals("")) {
				toast.show("账号不能为空");
				return;
			}
			setBankCard(realName, bankCard);
			break;
		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent();
			intent.putExtra(ModificationBankCardOne.BANK_CARD, tvBankCard
					.getText().toString());
			setResult(0, intent);
			finish();
		};
	};

	private void setBankCard(String realName, String bankCard) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("bankCard", bankCard);
		map.put("bankCardHolder", realName);
		privacyettingInfo.setBankCard(map, Constants.API_SET_BANKCARD,
				Method.GET, handler);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	@Click(R.id.main_header_left)
	void back() {
		finish();
	}

}
