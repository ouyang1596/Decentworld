package cn.sx.decentworld.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.KeyboardComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_show_to_me_lv)
public class ShowToMeLvActivity extends BaseFragmentActivity {
	@ViewById(R.id.tv_cancel)
	TextView tvCancel;
	@ViewById(R.id.et_showTome_value)
	EditText etValue;
	@ViewById(R.id.tv_showToMe_name)
	TextView tvName;
	@ViewById(R.id.iv_back)
	ImageView ivBack;

	@AfterViews
	public void init() {
		NGetIntent();
		setNameAndalue();
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				etValue.setText("");
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if (etValue.length() > 0) {
					intent.putExtra(SelectedActivity.VALUE, etValue.getText()
							.toString());
				} else {
					intent.putExtra(SelectedActivity.VALUE, "");
				}
				intent.putExtra(SelectedActivity.NAME, tvName.getText()
						.toString());
				setResult(0, intent);
				closeKeyBoard();
				finish();
			}
		});
	}

	private String name;
	private String value;

	private void NGetIntent() {
		name = getIntent().getStringExtra(SelectedActivity.NAME);
		value = getIntent().getStringExtra(SelectedActivity.VALUE);
	}

	public void setNameAndalue() {
		etValue.setText(value);
		tvName.setText(name);
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
				keyboardComponent.openKeybord(etValue);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etValue);
	}
}
