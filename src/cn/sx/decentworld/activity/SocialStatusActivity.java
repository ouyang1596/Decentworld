package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.abstractclass.AbstractTextWatcher;
import cn.sx.decentworld.engine.UserDataEngine;
import cn.sx.decentworld.engine.UserDataEngine.SetWorthListener;
import cn.sx.decentworld.utils.ToastUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_social_status)
public class SocialStatusActivity extends BaseFragmentActivity {
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.et_social_status)
	EditText etSocialStatus;
	@ViewById(R.id.btn_OK)
	Button btnOk;

	@AfterViews
	public void init() {
		setBtnState();
		etSocialStatus.addTextChangedListener(new AbstractTextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				setBtnState();
			};
		});
		ivBack.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String data = etSocialStatus.getText().toString();
				Integer money = Integer.valueOf(data);
				if (money < 1) {
					ToastUtil.showToast("身家至少为1");
					return;
				}
				setSocialStatus();
			}
		});
	}

	/**
	 * 设置身价到服务器
	 */
	public void setSocialStatus() {
		String worth = etSocialStatus.getText().toString();
		if ((!worth.equals("")) && (worth.length() <= 5)) {
			UserDataEngine.getInstance().setWorth(worth, new SetWorthListener() {
				@Override
				public void onSuccess(String currentWorth) {
					Intent intent = new Intent(mContext, MainActivity_.class);
					startActivity(intent);
				}

				@Override
				public void onFailure(String cause) {
					ToastUtil.showToast(cause);
				}
			});
		} else if (worth.length() > 5) {
			ToastUtil.showToast("身家值不能超过5位");
		} else if (worth.equals("")) {
			ToastUtil.showToast("自定义的身家值不能为空");
		}
	}

	private void setBtnState() {
		int etSocialStatusLength = etSocialStatus.length();
		if (etSocialStatusLength <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources().getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.rg_btn_blue_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.rg_btn_bg_selector);
		}
	}

}
