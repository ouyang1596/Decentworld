package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
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
	@Bean
	ToastComponent toast;
	private SendUrl sendUrl;

	@AfterViews
	public void init() {
		sendUrl = new SendUrl(this);
		setBtnState();
		etSocialStatus.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence data, int arg1, int arg2,
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
					toast.show("身家至少为1");
					return;
				}
				setSocialStatus();
			}
		});
	}

	public void setSocialStatus() {
		String worth = etSocialStatus.getText().toString();
		if ((!worth.equals("")) && (worth.length() <= 5)) {
			setWorth(DecentWorldApp.getInstance().getDwID(), worth);
		} else if (worth.length() > 5) {
			toast.show("身家值不能超过5位");
		} else if (worth.equals("")) {
			toast.show("自定义的身家值不能为空");
		}
	}

	private void setBtnState() {
		int etSocialStatusLength = etSocialStatus.length();
		if (etSocialStatusLength <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources()
					.getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.rg_btn_blue_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(
					R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.rg_btn_bg_selector);
		}
	}

	public void setWorth(final String dwID, final String worth) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("worth", worth);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH
				+ "/user/setWorth", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				if (msg.getResultCode() == 2222) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							UserInfo userInfo = UserInfo.queryByDwID(dwID);
							userInfo.setWorth(Float.valueOf(worth));
							userInfo.save();
							Intent intent = new Intent(mContext,
									MainActivity_.class);
							startActivity(intent);
						}
					});
				}
				if (msg.getResultCode() == 3333) {
					showToast(msg.getMsg());
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
				toast.show(data);
			}
		});
	}
}
