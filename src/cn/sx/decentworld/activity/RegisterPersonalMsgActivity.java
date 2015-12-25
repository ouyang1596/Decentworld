package cn.sx.decentworld.activity;

import java.util.Timer;
import java.util.TimerTask;

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
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RegisterInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.dialog.BackDialogFragment;
import cn.sx.decentworld.dialog.BackDialogFragment.OnBackClickListener;
import cn.sx.decentworld.utils.CardUtil;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_personal_msg)
public class RegisterPersonalMsgActivity extends BaseFragmentActivity implements
		OnBackClickListener {
	@ViewById(R.id.etv_identify_card)
	EditText etvIdentifyCard;
	@ViewById(R.id.etv_name)
	EditText etvName;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.root_activity_register_personal_msg)
	LinearLayout llRegisterPersonalMsg;
	@Bean
	ToastComponent toast;
	@Bean
	RegisterComponent registerComponent;
	private FragmentManager fragmentManager;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("输入身份证");
		llRegisterPersonalMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		initData();
		fragmentManager = getSupportFragmentManager();
		setBtnState();
		etvIdentifyCard.addTextChangedListener(new TextWatcher() {

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
		etvName.addTextChangedListener(new TextWatcher() {

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
		ivBack.setVisibility(View.VISIBLE);
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
				String IdCard = etvIdentifyCard.getText().toString();
				String relName = etvName.getText().toString();
				/**
				 * 验证身份信息
				 */
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						startActivity(new Intent(mContext,
								RegisterIsStudentActivity_.class));
						finish();
					};
				};

				// 验证身份证是否有效
				if (CardUtil.validateIdCard18(IdCard)) {
					LogUtils.i("LoginActivity", "身份证号有效");
					registerComponent.identifyIDCard(relName, IdCard, handler);
				} else {
					toast.show("身份证号无效");
					LogUtils.i("LoginActivity", "身份证号无效");
				}
			}
		});
	}

	/**
	 * 获取数据库数据
	 */
	private void initData() {
		RegisterInfo info = RegisterInfo.queryByDwID(registerComponent.tel);
		if (null == info) {
			info = new RegisterInfo(registerComponent.tel);
		}
		if (null == DecentWorldApp.registerBean) {
			return;
		}
		String identifyCard = info.identifyCard;
		String name = info.name;
		if (identifyCard != null) {
			etvIdentifyCard.setText(identifyCard);
		} else {
			etvIdentifyCard.setText(DecentWorldApp.registerBean.card);
		}
		if (name != null) {
			etvName.setText(name);
		} else {
			etvName.setText(DecentWorldApp.registerBean.realName);
		}
	}

	private void setBtnState() {
		int nameLength = etvName.length();
		int identifyCardLength = etvIdentifyCard.length();
		if (nameLength <= 0 || identifyCardLength <= 0) {
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

	@Override
	public void backClick(int state) {
		if (state == Constants.SAVE) {
			RegisterInfo info = RegisterInfo.queryByDwID(registerComponent.tel);
			if (info == null) {
				return;
			}
			if (etvIdentifyCard.length() > 0) {
				String strIdentifyCard = etvIdentifyCard.getText().toString();
				info.identifyCard = strIdentifyCard;
			}
			if (etvName.length() > 0) {
				String strName = etvName.getText().toString();
				info.name = strName;
			}
			info.save();
		}
		startIntent();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		backAction();
	}

	private void backAction() {
		if (etvName.length() > 0 || etvIdentifyCard.length() > 0) {
			BackDialogFragment bf = BackDialogFragment
					.newInstance("您是否需要保存已经写好的信息？");
			bf.setOnBackClickListener(RegisterPersonalMsgActivity.this);
			bf.show(fragmentManager, "backDialogs");
		} else {
			startIntent();
		}
	}

	private void startIntent() {
		startActivity(new Intent(mContext, RegisterMobileActivity_.class));
		finish();
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
				keyboardComponent.openKeybord(etvIdentifyCard);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvIdentifyCard);
	}
}
