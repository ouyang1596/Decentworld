/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.network.request.PrivacySettingInfo;
import cn.sx.decentworld.network.request.ResetPwdInfo;

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
@EActivity(R.layout.activity_modification_phone_num_one)
public class ModificationPhoneNumOne extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "ModificationPhoneNumOne";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	@ViewById(R.id.et_identifying_code)
	EditText etIdentifyingCode;
	@ViewById(R.id.et_modification_phone_num_one_number)
	EditText etMobile;
	@ViewById(R.id.btn_modification_phone_num_one_next)
	Button btn_modification_phone_num_one_next;
	@ViewById(R.id.tv_send)
	TextView tvSend;
	private FragmentManager fm;
	@Bean
	ResetPwdInfo resetPwdInfo;
	@Bean
	PrivacySettingInfo privacySettingInfo;
	public static final String MOBILE = "mobile";

	@AfterViews
	public void init() {
		titleBar.setTitleBar("返回", "替换手机号码");
		fm = getSupportFragmentManager();
		btn_modification_phone_num_one_next.setOnClickListener(this);
		tvSend.setOnClickListener(this);
	}

	private Handler setPhoneNumHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent();
			intent.putExtra(MOBILE, etMobile.getText().toString());
			setResult(0, intent);
			finish();
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_modification_phone_num_one_next:
			if (etMobile.length() <= 0) {
				toast.show("请输入手机号码");
				return;
			}
			if (etIdentifyingCode.length() <= 0) {
				toast.show("请输入验证码");
				return;
			}
			if (code.equals(etIdentifyingCode.getText().toString())) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
				map.put("phoneNum", etMobile.getText().toString());
				privacySettingInfo.setPhoneNum(map, Constants.API_SET_PHONENUM,
						Method.GET, setPhoneNumHandler);
			} else {
				toast.show("验证码不正确");
			}
			break;
		case R.id.tv_send:
			String mobile = etMobile.getText().toString();
			ReminderDialog reminderDialog = new ReminderDialog();
			reminderDialog.setListener(listener);
			reminderDialog.setInfo("确认手机号码\n我们将发送验证码到这个手机号码\n" + mobile);
			reminderDialog.show(fm, "ModificationPhoneNumOne");
			break;
		}
	}

	private String code;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String jsonData = msg.obj.toString();
			try {
				JSONObject object = new JSONObject(jsonData);
				code = object.getString("code");
				toast.show("验证码已发送");
				// Intent intent = new Intent(mContext,
				// ForgetPwdIdentifyingCodeActivity_.class);
				// intent.putExtra(MOBILE, phoneNumber);
				// intent.putExtra("code", code);
				// startActivity(intent);
			} catch (JSONException e) {
				// toast.show("解析错误");
				return;
			}
		};
	};
	ReminderListener listener = new ReminderListener() {

		@Override
		public void confirm() {
			// 跳转到下一个页面
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("phoneNum", etMobile.getText().toString());
			resetPwdInfo.requestCode(map, Constants.API_REQUEST_CODE,
					Method.GET, handler);
		}
	};

	@Click(R.id.main_header_left)
	void back() {
		finish();
	}

}
