package cn.sx.decentworld.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
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
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.network.request.ResetPwdInfo;
import cn.sx.decentworld.utils.ViewUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_forget_pwd_id_card)
public class ForgetPwdIdCardActivity extends BaseFragmentActivity {
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.et_idcard)
	EditText etvIdCard;
	@ViewById(R.id.root_forget_pwd_id_card)
	LinearLayout llForgetPwdIdCard;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("输入身份证号");
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				finish();
			}
		});
		llForgetPwdIdCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		setBtnState();
		etvIdCard.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				setBtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				checkID(etvIdCard.getText().toString());
			}
		});
	}

	@Bean
	ResetPwdInfo resetPwdInfo;

	private void checkID(String ID) {
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (null == msg.obj) {
					return;
				}
				String jsonData = msg.obj.toString();
				try {
					JSONObject object = new JSONObject(jsonData);
					String token = object.getString("token");
					Intent intent = new Intent(mContext,
							ForgertPwdResetPwdActivity_.class);
					intent.putExtra("token", token);
					startActivity(intent);
				} catch (JSONException e) {
					return;
				}
			};
		};
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idCard", ID);
		map.put("phoneNum", resetPwdInfo.phoneNum);
		resetPwdInfo.checkID(map, Constants.API_CHECK_ID, Method.GET, handler);
	}

	private void setBtnState() {
		int length = etvIdCard.length();
		if (length <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources()
					.getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(
					R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_selector);
		}
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
				keyboardComponent.openKeybord(etvIdCard);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvIdCard);
	}
}
