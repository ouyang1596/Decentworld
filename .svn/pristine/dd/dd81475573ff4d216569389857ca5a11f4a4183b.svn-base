/**
 * 
 */
package cn.sx.decentworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
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
@EActivity(R.layout.activity_modification_bank_card_three)
public class ModificationBankCardThree extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG= "ModificationBankCardThree";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.et_modification_bank_card_three_code)
	EditText et_modification_bank_card_three_code;
	@ViewById(R.id.btn_modification_bank_card_three_code)
	Button btn_modification_bank_card_three_code;
	@ViewById(R.id.btn_modification_bank_card_three_next)
	Button btn_modification_bank_card_three_next;

	@ViewById(R.id.tv_modification_bank_card_three_number)
	TextView tv_modification_bank_card_three_number;

	@AfterViews
	public void init()
	{
		ActivityCollector.addActivity(this);
		titleBar.setTitleBar("返回", "替换(添加银行卡)3/3");
		btn_modification_bank_card_three_code.setOnClickListener(this);
		btn_modification_bank_card_three_next.setOnClickListener(this);
		et_modification_bank_card_three_code.addTextChangedListener(textWatcher);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String number = bundle.getString("number");
		number = "绑定银行卡需要短信确认，验证码已经发送至手机" + number + "，请按提示操作";
		tv_modification_bank_card_three_number.setText(number);

	}

	TextWatcher textWatcher = new TextWatcher()
	{
		private CharSequence temp;
		private int selectionStart;
		private int selectionEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
			temp = s;
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			selectionStart = et_modification_bank_card_three_code.getSelectionStart();
			selectionEnd = et_modification_bank_card_three_code.getSelectionEnd();

			if (temp.length() >= 6)
			{
				btn_modification_bank_card_three_code.setVisibility(View.GONE);
				btn_modification_bank_card_three_next.setVisibility(View.VISIBLE);
			} else
			{
				btn_modification_bank_card_three_code.setVisibility(View.VISIBLE);
				btn_modification_bank_card_three_next.setVisibility(View.GONE);
			}
		}

	};

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_modification_bank_card_three_code:

			break;
		case R.id.btn_modification_bank_card_three_next:
			toast.show("修改银行卡成功！");
			ActivityCollector.finishAll();
			break;
		default:
			break;
		}
	}
	
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}

}
