package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_what_you_have)
public class RegisterWhatYouHaveCheckActivity extends BaseFragmentActivity
		implements OnClickListener {
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_talent)
	ImageView ivTalent;
	@ViewById(R.id.iv_money)
	ImageView ivMoney;
	@ViewById(R.id.iv_appearance)
	ImageView ivAppearance;

	@AfterViews
	public void init() {
		tvTitle.setText("选择一项进入");
		ivBack.setVisibility(View.VISIBLE);
		ivTalent.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ivMoney.setOnClickListener(this);
		ivAppearance.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_talent:
			startActivity(new Intent(mContext,
					RegisterTalentCheckActivity_.class));
			break;

		case R.id.iv_money:
			startActivity(new Intent(mContext,
					RegisterMoneyCheckActivity_.class));
			break;
		case R.id.iv_appearance:
			startActivityForResult(new Intent(mContext,
					PayDialogActivity_.class), Constants.REQUEST_CODE);
			break;
		case R.id.iv_back:
			finish();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Constants.REQUEST_CODE:
			startActivity(new Intent(mContext,
					RegisterAppearanceCheckActivity_.class));
			break;

		default:
			break;
		}
	}
}
