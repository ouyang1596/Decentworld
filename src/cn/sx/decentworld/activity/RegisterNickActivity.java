package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.XMPPConnection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RegisterInfo;
import cn.sx.decentworld.bean.RegisterInfoUseless;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.dialog.BackDialogFragment.OnBackClickListener;
import cn.sx.decentworld.fragment.ViewPagerFragment;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.ToastUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_nick)
public class RegisterNickActivity extends BaseFragmentActivity implements OnBackClickListener {
	@ViewById(R.id.et_nickname)
	EditText etNickName;
	@ViewById(R.id.tv_occupation)
	TextView tvOccupation;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.rg_sex)
	RadioGroup rgSex;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.root_activity_register_nick)
	LinearLayout llRegisterNick;
	private FragmentManager fragmentManager;
	// @ViewById(R.id.iv_nickname)
	// ImageView ivNickName;
	private ViewPagerFragment mVpFragment;
	@Bean
	RegisterComponent registerComponent;
	private static final int IMAGE_REQUEST_CODE = 100;
	private static final int OCCUPATION_REQUEST_CODE = 200;
	public static RegisterInfo registerInfo;
	@Bean
	GetUserInfo getUserInfo;
	@Bean
	ToastComponent toast;
	// private String filePath;
	private XMPPConnection con;

	@AfterViews
	public void init() {
		openKeyboard();
		mVpFragment = new ViewPagerFragment();
		mVpFragment.setMaxCount(3);
		getSupportFragmentManager().beginTransaction().add(R.id.fl_container, mVpFragment, "vf").commit();
		registerInfo = new RegisterInfo();
		tvTitle.setText("昵称");
		ivBack.setVisibility(View.VISIBLE);
		llRegisterNick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});

		// ivNickName.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(getApplicationContext(),
		// TakePhotosAndpictureActivity.class);
		// intent.putExtra("max_count", 1);
		// startActivityForResult(intent, IMAGE_REQUEST_CODE);
		// }
		// });
		initData();
		setBtnState();
		fragmentManager = getSupportFragmentManager();
		etNickName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				setBtnState();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				finish();
				// backAction();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (etNickName.length() <= 0) {
					toast.show("昵称不能为空");
					return;
				}
				if (etNickName.length() > 8) {
					toast.show("字数不能超过8个字符");
					return;
				}
				if (tvOccupation.length() <= 0) {
					toast.show("行业不能为空");
					return;
				}
				if (mVpFragment.mPicPaths.size() <= 0) {
					ToastUtil.showToast("请先选择至少一张图片");
					return;
				}
				String nickName = etNickName.getText().toString();
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						startIntent(LoginActivity_.class);
						finish();
						// login();
					}
				};
				// if (null == filePath) {
				// toast.show("请先选择一张图片");
				// return;
				// }
				registerInfo.nickName = nickName;
				registerInfo.occupation = tvOccupation.getText().toString();
				registerInfo.picPaths = mVpFragment.mPicPaths;
				registerInfo.sex = ((RadioButton) findViewById(rgSex.getCheckedRadioButtonId())).getText().toString();
				Intent intent = new Intent(RegisterNickActivity.this, RegisterSetPasswordActivity_.class);
				startActivity(intent);
				// File[] images = new File[1];
				// File file = new File(filePath);
				// images[0] = file;
				// HashMap<String, String> map = new HashMap<String, String>();
				// map.put("nickName", nickName);
				// map.put("phoneNum", RegisterComponent.tel);
				// map.put("occupation", tvOccupation.getText().toString());
				// map.put("sex", ((RadioButton) findViewById(rgSex
				// .getCheckedRadioButtonId())).getText().toString());
				// // registerComponent.submitNickName(nickName, handler);
				// registerComponent.submitNickName(map, images,
				// Constants.API_REGISTER_NICK_IMAGES, handler);
			}
		});
		tvOccupation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(mContext, OccupationActivity_.class), OCCUPATION_REQUEST_CODE);
			}
		});
		rgSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

			}
		});
	}

	private void setBtnState() {
		int length = etNickName.length();
		if (length <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources().getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_selector);
		}
	}

	/**
	 * 获取数据库数据
	 */
	private void initData() {
		RegisterInfoUseless info = RegisterInfoUseless.queryByDwID(registerComponent.tel);
		if (info != null) {
			String nickname = info.nickname;
			if (nickname != null) {
				etNickName.setText(nickname);
			}
		}
	}

	@Override
	public void backClick(int state) {
		// if (state == Constants.SAVE) {
		// RegisterInfo info = RegisterInfo.queryByDwID(registerComponent.tel);
		// if (info == null) {
		// info = new RegisterInfo(registerComponent.tel);
		// }
		// if (etNickName.length() > 0) {
		// String strIdentifyCard = etNickName.getText().toString();
		// info.identifyCard = strIdentifyCard;
		// }
		// info.save();
		// startIntent(RegisterWhatYouHaveActivity_.class);
		// } else {
		// startIntent(RegisterWhatYouHaveActivity_.class);
		// }
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// backAction();
	}

	private String beginTime, support, unsupport, standard;

	// private void backAction() {
	// if (etNickName.length() > 0) {
	// BackDialogFragment bf = BackDialogFragment
	// .newInstance("您是否需要保存已经写好的信息？");
	// bf.setOnBackClickListener(RegisterNickActivity.this);
	// bf.show(fragmentManager, "backDialogs");
	// } else {
	// finish();
	// }
	// }
	// private void login() {
	// final RegisterInfo info = RegisterInfo
	// .queryByDwID(registerComponent.tel);
	// if (null == info) {
	// startIntent(LoginActivity_.class);
	// return;
	// }
	// Handler handler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// switch (msg.what) {
	// case 2222:
	// try {
	// JSONObject jsonObject = new JSONObject(
	// msg.obj.toString());
	// String dwID = jsonObject.getString("dwID");
	// String token = jsonObject.getString("token");
	// showProgressDialog();
	// new LoginXMPPAsyncTask().execute(registerComponent.tel,
	// dwID, info.password, token);
	// } catch (JSONException e) {
	// }
	// break;
	// case 2010:
	// try {
	// JSONObject jsonObject = new JSONObject(
	// msg.obj.toString());
	// beginTime = jsonObject.getString("beginTime");
	// support = jsonObject.getString("support");
	// unsupport = jsonObject.getString("unsupport");
	// standard = jsonObject.getString("standard");
	// } catch (JSONException e) {
	// }
	// break;
	// }
	//
	// }
	// };
	// getUserInfo.getUserdwID(registerComponent.tel, handler);
	// }

	// class LoginXMPPAsyncTask extends AsyncTask<String, Void, Integer> {
	//
	// @Override
	// protected Integer doInBackground(String... params) {
	// try {
	// con = XmppHelper.getConnection(null);
	// con.login(params[0], params[1], params[2], "Smack");
	// con.sendPacket(new Presence(Presence.Type.available));
	// // 登录成功后保存相应的数据到Application和SQLite中
	// DecentWorldApp.getInstance().setDwID(params[1]);
	// DecentWorldApp.getInstance().setToken(params[3]);
	// DecentWorldApp.getInstance().setUserName(params[0]);
	// DecentWorldApp.getInstance().setPassword(params[2]);
	// SharedPreferences preferences = getSharedPreferences("setting",
	// 0);
	// SharedPreferences.Editor editor = preferences.edit();
	// editor.putString("token", params[3]);
	// editor.commit();
	// UserLogin user = UserLogin.queryByDwID(params[1]);
	// if (user == null) {
	// UserLogin userLogin = new UserLogin(params[1], params[0],
	// params[2], 1);
	// userLogin.save();
	// } else {
	// UserLogin userLogin = UserLogin.queryByDwID(params[1]);
	// userLogin.setUsername(params[0]);
	// userLogin.setPassword(params[2]);
	// userLogin.setPriority(1);
	// userLogin.save();
	// }
	// } catch (Exception e) {
	// LogUtils.e("bm", "register---" + e.getLocalizedMessage());
	// return Constants.FAILURE;
	// }
	// return Constants.SUCC;
	// }
	//
	// @Override
	// protected void onPostExecute(Integer result) {
	// super.onPostExecute(result);
	// hideProgressDialog();
	// if (result == Constants.SUCC) {
	// startIntent(MainActivity_.class);
	// } else {
	// startIntent(LoginActivity_.class);
	// }
	// finish();
	// }
	// }

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
				keyboardComponent.openKeybord(etNickName);
			}
		}, 500);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		// if (IMAGE_REQUEST_CODE == requestCode) {
		// // 判断返回的数据
		// int max_count = data.getExtras().getInt("max_count");
		// ArrayList<String> pic_filse =
		// data.getExtras().getStringArrayList("pic_paths");
		// if (pic_filse.isEmpty()) {
		// return;
		// }
		// filePath = pic_filse.get(0);
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inSampleSize = 2;
		// Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		// // ivNickName.setImageBitmap(bitmap);
		// }
		if (OCCUPATION_REQUEST_CODE == requestCode) {
			String occupation = data.getStringExtra(OccupationActivity.OCCUPATION);
			tvOccupation.setText(occupation);
		}
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etNickName);
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
