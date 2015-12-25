//package cn.sx.decentworld.activity;
//
//import java.util.HashMap;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import cn.sx.decentworld.R;
//import cn.sx.decentworld.common.Constants;
//import cn.sx.decentworld.component.KeyboardComponent;
//import cn.sx.decentworld.component.ToastComponent;
//import cn.sx.decentworld.network.request.ResetPwdInfo;
//import cn.sx.decentworld.utils.ViewUtil;
//
//import com.android.volley.Request.Method;
//import com.googlecode.androidannotations.annotations.AfterViews;
//import com.googlecode.androidannotations.annotations.Bean;
//import com.googlecode.androidannotations.annotations.EActivity;
//import com.googlecode.androidannotations.annotations.ViewById;
//
//@EActivity(R.layout.activity_forget_pwd_mobile)
//public class ForgetPwdMobileUselessActivity extends BaseFragmentActivity {
//	@ViewById(R.id.btn_OK)
//	Button btnOk;
//	@ViewById(R.id.iv_back)
//	ImageView ivBack;
//	@ViewById(R.id.root_activity_register_mobile)
//	LinearLayout llRegisterMobile;
//	@ViewById(R.id.et_mobile)
//	EditText etvMobile;
//	@Bean
//	ResetPwdInfo resetPwdInfo;
//	@Bean
//	ToastComponent toast;
//
//	@AfterViews
//	public void init() {
//		openKeyboard();
//		ViewUtil.scaleContentView(llRegisterMobile);
//		llRegisterMobile.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				closeKeyBoard();
//			}
//		});
//		setBtnState();
//		ivBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				closeKeyBoard();
//				finish();
//			}
//		});
//		etvMobile.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//				setBtnState();
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//
//			}
//		});
//		btnOk.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				String phoneNumber = etvMobile.getText().toString();
//				if (phoneNumber.length() != 11) {
//					toast.show("手机号码格式不正确");
//					return;
//				}
//				requestCode(phoneNumber);
//			}
//
//		});
//	}
//
//	public static final String MOBILE = "mobile";
//
//	private void requestCode(final String phoneNumber) {
//		Handler handler = new Handler() {
//			public void handleMessage(android.os.Message msg) {
//				String jsonData = msg.obj.toString();
//				try {
//					JSONObject object = new JSONObject(jsonData);
//					String code = object.getString("code");
//					Intent intent = new Intent(mContext,
//							ForgetPwdIdentifyingCodeActivity_.class);
//					intent.putExtra(MOBILE, phoneNumber);
//					intent.putExtra("code", code);
//					startActivity(intent);
//				} catch (JSONException e) {
//					// toast.show("解析错误");
//					return;
//				}
//			};
//		};
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("phoneNum", phoneNumber);
//		resetPwdInfo.requestCode(map, Constants.API_REQUEST_CODE, Method.GET,
//				handler);
//	}
//
//	private void setBtnState() {
//		int length = etvMobile.length();
//		if (length <= 0) {
//			btnOk.setEnabled(false);
//			btnOk.setTextColor(getResources()
//					.getColor(R.color.rg_tv_blue_press));
//			btnOk.setBackgroundResource(R.drawable.rg_btn_blue_pressed_shape);
//		} else {
//			btnOk.setEnabled(true);
//			btnOk.setTextColor(getResources().getColor(
//					R.drawable.rg_tv_color_selector));
//			btnOk.setBackgroundResource(R.drawable.rg_btn_bg_selector);
//		}
//	}
//
//	// 对键盘的操作
//	@Bean
//	KeyboardComponent keyboardComponent;
//
//	/**
//	 * 打开软键盘
//	 */
//	private void openKeyboard() {
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			public void run() {
//				keyboardComponent.openKeybord(etvMobile);
//			}
//		}, 500);
//	}
//
//	/**
//	 * 关闭软键盘
//	 */
//	private void closeKeyBoard() {
//		keyboardComponent.closeKeyboard(etvMobile);
//	}
//}
