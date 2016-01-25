package cn.sx.decentworld.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_is_student)
public class RegisterIsStudentActivity extends BaseFragmentActivity {
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.btn_NO)
	Button btnNo;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.root_activity_register_is_student)
	LinearLayout llRegisterIsStudent;
	@Bean
	ToastComponent toast;
	@Bean
	RegisterComponent registerComponent;
	private FragmentManager fragmentManager;

	@AfterViews
	public void init() {
		tvTitle.setText("您是否是学生");
		ivBack.setVisibility(View.VISIBLE);
		fragmentManager = getSupportFragmentManager();
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext,
						RegisterStudentGreenPassActivity_.class));
				finish();
			}
		});
		btnNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("0".equals(DecentWorldApp.ifGuarantee)) {
					startActivity(new Intent(mContext,
							RegisterWhatYouHaveActivity_.class));
					registerComponent.submitNoStudent(Constants.NO_STUDENT);
				} else if ("1".equals(DecentWorldApp.ifGuarantee)) {
					startActivity(new Intent(mContext,
							RegisterNickActivity_.class));
					registerComponent.submitNoStudent(Constants.NO_STUDENT);
				}
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				backAction();
			}

		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		backAction();
	}

	private void backAction() {
		startActivity(new Intent(mContext, RegisterMobileActivity_.class));
		finish();
	}
}
