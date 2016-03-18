package cn.sx.decentworld.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RegisterInfo;
import cn.sx.decentworld.bean.UserLogin;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.dialog.BackDialogFragment;
import cn.sx.decentworld.dialog.BackDialogFragment.OnBackClickListener;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_nick)
public class RegisterNickActivity extends BaseFragmentActivity implements
		OnBackClickListener {
	@ViewById(R.id.et_nickname)
	EditText etvNickName;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.root_activity_register_nick)
	LinearLayout llRegisterNick;
	private FragmentManager fragmentManager;
	@Bean
	RegisterComponent registerComponent;
	@Bean
	GetUserInfo getUserInfo;
	@Bean
	ToastComponent toast;
	private XMPPConnection con;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("昵称");
		ivBack.setVisibility(View.VISIBLE);
		llRegisterNick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		initData();
		setBtnState();
		fragmentManager = getSupportFragmentManager();
		etvNickName.addTextChangedListener(new TextWatcher() {

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
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				backAction();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String nickName = etvNickName.getText().toString();
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						startIntent(LoginActivity_.class);
						finish();
						// login();
					}
				};
				registerComponent.submitNickName(nickName, handler);
			}
		});
	}

	private void setBtnState() {
		int length = etvNickName.length();
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

	/**
	 * 获取数据库数据
	 */
	private void initData() {
		RegisterInfo info = RegisterInfo.queryByDwID(registerComponent.tel);
		if (info != null) {
			String nickname = info.nickname;
			if (nickname != null) {
				etvNickName.setText(nickname);
			}
		}
	}

	@Override
	public void backClick(int state) {
		if (state == Constants.SAVE) {
			RegisterInfo info = RegisterInfo.queryByDwID(registerComponent.tel);
			if (info == null) {
				info = new RegisterInfo(registerComponent.tel);
			}
			if (etvNickName.length() > 0) {
				String strIdentifyCard = etvNickName.getText().toString();
				info.identifyCard = strIdentifyCard;
			}
			info.save();
			startIntent(RegisterWhatYouHaveActivity_.class);
		} else {
			startIntent(RegisterWhatYouHaveActivity_.class);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		backAction();
	}

	private String beginTime, support, unsupport, standard;

	private void backAction() {
		if (etvNickName.length() > 0) {
			BackDialogFragment bf = BackDialogFragment
					.newInstance("您是否需要保存已经写好的信息？");
			bf.setOnBackClickListener(RegisterNickActivity.this);
			bf.show(fragmentManager, "backDialogs");
		} else {
			startIntent(RegisterWhatYouHaveActivity_.class);
		}
	}

	private void login() {
		final RegisterInfo info = RegisterInfo
				.queryByDwID(registerComponent.tel);
		if (null == info) {
			startIntent(LoginActivity_.class);
			return;
		}
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 2222:
					try {
						JSONObject jsonObject = new JSONObject(
								msg.obj.toString());
						String dwID = jsonObject.getString("dwID");
						String token = jsonObject.getString("token");
						showProgressDialog();
						new LoginXMPPAsyncTask().execute(registerComponent.tel,
								dwID, info.password, token);
					} catch (JSONException e) {
					}
					break;
				case 2010:
					try {
						JSONObject jsonObject = new JSONObject(
								msg.obj.toString());
						beginTime = jsonObject.getString("beginTime");
						support = jsonObject.getString("support");
						unsupport = jsonObject.getString("unsupport");
						standard = jsonObject.getString("standard");
					} catch (JSONException e) {
					}
					break;
				}

			}
		};
		getUserInfo.getUserdwID(registerComponent.tel, handler);
	}

	class LoginXMPPAsyncTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			try {
				con = DecentWorldApp.getInstance().getConnectionImpl();
				con.login(params[0], params[1], params[2], "Smack");
				con.sendPacket(new Presence(Presence.Type.available));
				// 登录成功后保存相应的数据到Application和SQLite中
				DecentWorldApp.getInstance().setDwID(params[1]);
				DecentWorldApp.getInstance().setToken(params[3]);
				DecentWorldApp.getInstance().setUserName(params[0]);
				DecentWorldApp.getInstance().setPassword(params[2]);
				SharedPreferences preferences = getSharedPreferences("setting",
						0);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("token", params[3]);
				editor.commit();
				UserLogin user = UserLogin.queryByDwID(params[1]);
				if (user == null) {
					UserLogin userLogin = new UserLogin(params[1], params[0],
							params[2], 1);
					userLogin.save();
				} else {
					UserLogin userLogin = UserLogin.queryByDwID(params[1]);
					userLogin.setUsername(params[0]);
					userLogin.setPassword(params[2]);
					userLogin.setPriority(1);
					userLogin.save();
				}
			} catch (Exception e) {
				LogUtils.e("bm", "register---" + e.getLocalizedMessage());
				return Constants.FAILURE;
			}
			return Constants.SUCC;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			hideProgressDialog();
			if (result == Constants.SUCC) {
				startIntent(MainActivity_.class);
			} else {
				startIntent(LoginActivity_.class);
			}
			finish();
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
				keyboardComponent.openKeybord(etvNickName);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvNickName);
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(this, null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		if (null != mProDialog) {
			mProDialog.hide();
		}
	}
}
