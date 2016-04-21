package cn.sx.decentworld.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.XMPPConnection;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RegisterInfo;
import cn.sx.decentworld.bean.RegisterInfoUseless;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.dialog.BackDialogFragment.OnBackClickListener;
import cn.sx.decentworld.fragment.ViewPagerFragment;
import cn.sx.decentworld.utils.ToastUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_nick)
public class RegisterNickActivity extends BaseFragmentActivity implements OnBackClickListener {
	@ViewById(R.id.et_nickname)
	EditText etNickName;
	@ViewById(R.id.tv_occupation_shortIntroduce)
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
	private ViewPagerFragment mVpFragment;
	@Bean
	RegisterComponent registerComponent;
	private static final int IMAGE_REQUEST_CODE = 100;
	private static final int OCCUPATION_REQUEST_CODE = 200;
	public static RegisterInfo registerInfo;
	private XMPPConnection con;
	private Handler mCheckNickNameHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			registerInfo.nickName = etNickName.getText().toString();
			registerInfo.occupation = tvOccupation.getText().toString();
			registerInfo.picPaths = mVpFragment.mPicPaths;
			registerInfo.sex = ((RadioButton) findViewById(rgSex.getCheckedRadioButtonId())).getText().toString();
			Intent intent = new Intent(RegisterNickActivity.this, RegisterSetPasswordActivity_.class);
			startActivity(intent);
		};
	};

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
					ToastUtil.showToast("昵称不能为空");
					return;
				}
				if (etNickName.length() > 8) {
					ToastUtil.showToast("字数不能超过8个字符");
					return;
				}
				if (tvOccupation.length() <= 0) {
					ToastUtil.showToast("行业不能为空");
					return;
				}
				if (mVpFragment.mPicPaths.size() <= 0) {
					ToastUtil.showToast("请先选择至少一张图片");
					return;
				}
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("nickName", etNickName.getText().toString());
				registerComponent.checkNickName(map, ConstantNet.API_CHECK_NICKNAME, mCheckNickNameHandler);

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
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// backAction();
	}

	private String beginTime, support, unsupport, standard;

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
			if (occupation.contains("全部")) {
				occupation = occupation.substring(0, occupation.indexOf("全部"));
			}
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
