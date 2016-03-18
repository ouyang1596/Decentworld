package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_achievement)
public class AchievementActivity extends BaseFragmentActivity implements OnClickListener {
	@ViewById(R.id.tv_career_start)
	TextView tvCareerStart;
	@ViewById(R.id.tv_career_isto_success)
	TextView tvCareerIsToStart;
	@ViewById(R.id.tv_career_success)
	TextView tvCareerSuccess;
	@ViewById(R.id.tv_wan)
	TextView tvWan;
	@ViewById(R.id.tv_ensure)
	TextView tvEnsure;
	@ViewById(R.id.tv_back)
	TextView tvBack;
	@ViewById(R.id.et_introduce_detail)
	EditText etDetailIntroduce;
	@ViewById(R.id.et_short_introduce)
	EditText etShortIntroduce;
	private SendUrl mSendUrl;
	private String shortIntroduce, detailIntroduce;
	private Handler mUpdataHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ToastUtil.showToast("上传成功");
			setResult(0);
			finish();
		};
	};

	@AfterViews
	public void init() {
		mSendUrl = new SendUrl(this);
		tvCareerStart.setOnClickListener(this);
		tvCareerIsToStart.setOnClickListener(this);
		tvCareerSuccess.setOnClickListener(this);
		tvBack.setOnClickListener(this);
		tvEnsure.setOnClickListener(this);
		tvWan.setOnClickListener(this);
		AGetIntent();
		if (null != shortIntroduce) {
			etShortIntroduce.setText(shortIntroduce);
		}
		if (null != detailIntroduce) {
			etDetailIntroduce.setText(detailIntroduce);
		}
		etShortIntroduce.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 17) {
					etShortIntroduce.setText(s.subSequence(0, 17));
					return;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void AGetIntent() {
		shortIntroduce = getIntent().getStringExtra(ConstantIntent.FRAGMENT_ACHIEVEMENT_SHORT_INTRODUCE);
		detailIntroduce = getIntent().getStringExtra(ConstantIntent.FRAGMENT_ACHIEVEMENT_DETAIL_INTRODUCE);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int requestCode = 0;
		switch (v.getId()) {
		case R.id.tv_career_start:
			intent = new Intent(this, TempletActivity_.class);
			requestCode = ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_START;
			break;
		case R.id.tv_career_isto_success:
			intent = new Intent(this, TempletActivity_.class);
			requestCode = ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_ISTO_SUCCESS;
			break;
		case R.id.tv_career_success:
			intent = new Intent(this, TempletActivity_.class);
			requestCode = ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_SUCCESS;
			break;
		case R.id.tv_wan:
			intent = new Intent(this, TempletActivity_.class);
			requestCode = ConstantIntent.ACTIVITY_ACHIEVEMENT_WAN;
			break;
		case R.id.tv_ensure:
			if (etShortIntroduce.length() <= 0) {
				ToastUtil.showToast("请先完成一句话简介");
				return;
			}
			if (etShortIntroduce.length() > 50) {
				ToastUtil.showToast("字数超过50，请缩短字数");
				return;
			}
			if (etDetailIntroduce.length() <= 0) {
				ToastUtil.showToast("请先完成简介");
				return;
			}
			if (etDetailIntroduce.length() > 1000) {
				ToastUtil.showToast("字数超过1000，请缩短字数");
				return;
			}
			updateIntroduce();
			break;
		case R.id.tv_back:
			finish();
			break;
		}
		if (null != intent) {
			intent.putExtra(ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_PERIOD, requestCode);
			startActivityForResult(intent, requestCode);
		}
	}

	private void updateIntroduce() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("introduce", etDetailIntroduce.getText().toString());
		map.put("shortIntroduce", etShortIntroduce.getText().toString());
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_UPDATE_INTRODUCE, Method.POST,
				new HttpCallBack() {

					@Override
					public void onSuccess(String response, ResultBean msg) {
						if (2222 == msg.getResultCode()) {
							mUpdataHandle.sendEmptyMessage(msg.getResultCode());
						} else {
							showToast("上传失败");
						}
					}

					@Override
					public void onFailure(String e) {
						showToast(Constants.NET_WRONG);
					}
				});
	}

	private void showToast(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String tvData = null;
		if (null != data) {
			tvData = data.getStringExtra(ConstantIntent.ACTIVITY_ACHIEVEMENT_INTRODUCE_DETAIL);
		}
		switch (requestCode) {
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_START:
			etDetailIntroduce.setText(tvData);
			break;

		case ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_ISTO_SUCCESS:
			etDetailIntroduce.setText(tvData);
			break;
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_SUCCESS:
			etDetailIntroduce.setText(tvData);
			break;
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_WAN:
			etDetailIntroduce.setText(tvData);
			break;
		}
	}
}
