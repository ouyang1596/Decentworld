/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
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
@EActivity(R.layout.activity_modification_phone_num_two)
public class ModificationPhoneNumTwo extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = "ModificationPhoneNumTwo";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.tv_modification_phone_num_two_number)
	TextView tv_modification_phone_num_two_number;
	@ViewById(R.id.et_modification_phone_num_two_num)
	EditText et_modification_phone_num_two_num;
	@ViewById(R.id.et_modification_phone_num_two_code)
	EditText et_modification_bank_card_two_code;
	@ViewById(R.id.cb_modification_phone_num_two_agree)
	CheckBox cb_modification_phone_num_two_agree;
	@ViewById(R.id.btn_modification_phone_num_two_commit)
	Button btn_modification_phone_num_two_commit;

	private Boolean cbIsChecked = false;
	private FragmentManager fm;

	@AfterViews
	public void init() {
		ActivityCollector.addActivity(this);
		titleBar.setTitleBar("返回", "填写验证码");
		initViews();
		fm = getSupportFragmentManager();
		cb_modification_phone_num_two_agree.setChecked(false);
		cb_modification_phone_num_two_agree.setOnClickListener(this);
		btn_modification_phone_num_two_commit.setEnabled(false);
		btn_modification_phone_num_two_commit.setOnClickListener(this);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String number = bundle.getString("number");
		number = "短信验证码已经发送至" + number + "，请填写验证码";
		tv_modification_phone_num_two_number.setText(number);

	}

	/**
	 * 
	 */
	private void initViews() {

	}

	ReminderListener listener = new ReminderListener() {

		@Override
		public void confirm() {
			// 处理 确认后所执行的事件
			toast.show("确定返回并重新开始");

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cb_modification_phone_num_two_agree:

			if (cbIsChecked) {
				cbIsChecked = false;
				btn_modification_phone_num_two_commit.setEnabled(false);
			} else {
				cbIsChecked = true;
				btn_modification_phone_num_two_commit.setEnabled(true);
			}
			break;
		case R.id.btn_modification_phone_num_two_commit:

			// 跳转到下一个页面
			if (et_modification_phone_num_two_num.getText().toString()
					.equals("")) {
				toast.show("卡类型不能为空");
				return;
			}
			if (et_modification_bank_card_two_code.getText().toString()
					.equals("")) {
				toast.show("账号不能为空");
				return;
			}
			toast.show("手机号码修改成功！");
			ActivityCollector.finishAll();
			break;
		default:
			break;
		}
	}

	@Click(R.id.main_header_left)
	void back() {
		finish();
		// ReminderDialog reminderDialog = new ReminderDialog();
		// reminderDialog.setListener(listener);
		// reminderDialog.setInfo("验证码短信可能会略有延迟\n确定返回并重新开始？");
		// reminderDialog.show(fm, "ModificationPhoneNumTwo");
	}

}
