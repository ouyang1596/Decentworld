/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.security.BankInfo;
import cn.sx.decentworld.utils.ActivityCollector;

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
@EActivity(R.layout.activity_modification_bank_card_one)
public class ModificationBankCardOne extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "ModificationBankCardOne";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	// 变量信息
	@ViewById(R.id.et_modification_bank_card_one_name)
	TextView tvRealName;
	@ViewById(R.id.et_modification_bank_card_one_account)
	EditText etBankCard;

	@ViewById(R.id.btn_modification_bank_card_one_next)
	Button btn_modification_bank_card_one_next;
	public static final String BANK_CARD = "bankCard";
	public static final String BANK_NAME = "bankName";
	public static final String REAL_NAME = "realName";

	@AfterViews
	public void init() {
		tvRealName.setText(DecentWorldApp.realName);
		ActivityCollector.clear();
		ActivityCollector.addActivity(this);
		titleBar.setTitleBar("返回", "替换(添加银行卡)");
		btn_modification_bank_card_one_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_modification_bank_card_one_next:
			// 跳转到下一个页面
			String realName = tvRealName.getText().toString();
			if (realName.equals("")) {
				toast.show("姓名不能为空");
				return;
			}
			if (etBankCard.getText().toString().equals("")) {
				toast.show("卡号不能为空");
				return;
			}
			String bankCard = etBankCard.getText().toString();
			if (bankCard.length() < 17) {
				toast.show(BankInfo.NO_BANK);
				return;
			}
			String nameOfBank = BankInfo.getNameOfBank(bankCard, 0);
			if (BankInfo.NO_BANK.equals(nameOfBank)) {
				toast.show(BankInfo.NO_BANK);
				return;
			}
			// setBankCard(realName, bankCard);
			Intent intent = new Intent(ModificationBankCardOne.this,
					ModificationBankCardTwo_.class);
			intent.putExtra(BANK_NAME, nameOfBank);
			intent.putExtra(BANK_CARD, bankCard);
			intent.putExtra(REAL_NAME, realName);
			startActivityForResult(intent,
					PrivacySettingActivity.REQUEST_BANKCARD);
			break;
		}
	}

	@Click(R.id.main_header_left)
	void setBackBtn() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		setResult(0, intent);
		finish();
	}
}
