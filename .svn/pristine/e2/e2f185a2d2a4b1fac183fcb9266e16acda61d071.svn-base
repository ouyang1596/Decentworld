package cn.sx.decentworld.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.fragment.CancelDoubtFirstStepFragment;
import cn.sx.decentworld.fragment.CancelDoubtFirstStepFragment.OnDoubtFirstClickListener;
import cn.sx.decentworld.fragment.CancelDoubtSecondStepFragment;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_doubt_verify)
public class DoubtCancelDoubtActivity extends BaseFragmentActivity implements OnDoubtFirstClickListener, OnClickListener {
	private static final String TAG = "DoubtCancelDoubtActivity";
	private CancelDoubtFirstStepFragment mDoubtFirstStepFragment;
	private CancelDoubtSecondStepFragment mDoubtSecondFragment;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	private boolean isToSecond;
	private String condition;
	private int status;

	@AfterViews
	public void init() {
		DGetIntent();
		initData();
	}

	private void DGetIntent() {
		condition = getIntent().getStringExtra(ConstantIntent.ACTIVITY_DOUBT_CONDITION);
		status = getIntent().getIntExtra(ConstantIntent.ACTIVITY_DOUBT_STATUS, -1);
	}

	private void initData() {
		mDoubtFirstStepFragment = new CancelDoubtFirstStepFragment();
		mDoubtSecondFragment = new CancelDoubtSecondStepFragment();
		mDoubtSecondFragment.setCondition(condition);
		mDoubtFirstStepFragment.setOnDoubtFirstClickListener(this);
		ivBack.setOnClickListener(this);
		if (status == 5) {
			getSupportFragmentManager().beginTransaction().add(R.id.fl_doubt_wan_container, mDoubtSecondFragment, "dsf").commit();
		} else {
			getSupportFragmentManager().beginTransaction().add(R.id.fl_doubt_wan_container, mDoubtFirstStepFragment, "dff")
					.commit();
		}
	}

	@Override
	public void OnDoubtFirstClick(View view) {
		isToSecond = true;
		getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.fade_out, R.anim.fade_in, R.anim.fade_out, R.anim.fade_in)
				.hide(mDoubtFirstStepFragment).add(R.id.fl_doubt_wan_container, mDoubtSecondFragment, "dsf").addToBackStack(null)
				.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			if (isToSecond) {
				getSupportFragmentManager().popBackStack();
				isToSecond = false;
			} else {
				finish();
			}
			break;
		}
	}
}
