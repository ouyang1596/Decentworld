/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.PrivacySettingInfo;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.LogUtils;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ModificationBankCardOne.java
 * @Description:
 * @author: cj
 * @date: 2015年7月25日 下午3:00:47
 */
@EActivity(R.layout.activity_modification_password_one)
public class ModificationPasswordOne extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "ModificationPasswordOne";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	@ViewById(R.id.et_old_password)
	EditText et_old_password;
	@ViewById(R.id.et_new_password)
	EditText et_new_password;
	@ViewById(R.id.et_new_password_more)
	EditText et_new_password_more;
	@ViewById(R.id.btn_modification_password_one_next)
	Button btn_ensure;
	private Boolean cbIsChecked = false;
	@Bean
	PrivacySettingInfo privacySettingInfo;
	private String newPwd;

	@AfterViews
	public void init() {
		titleBar.setTitleBar("返回", "修改密码");
		btn_ensure.setOnClickListener(this);
	}

	private Handler hadnler = new Handler() {
		public void handleMessage(Message msg) {
			toast.show("密码修改成功");
			DecentWorldApp.getInstance().setPassword(newPwd);
			finish();
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_modification_password_one_next:
			if (et_old_password.length() <= 0) {
				toast.show("老密码不能为空");
				return;
			}
			String pwd = AES.encode(et_old_password.getText().toString());
			if (!DecentWorldApp.getInstance().getPassword().equals(pwd)) {
				toast.show("老密码输入错误");
				LogUtils.e("bm", "--" + et_old_password.getText().toString()
						+ "--" + DecentWorldApp.getInstance().getUserName());
				return;
			}
			if (et_new_password_more.length() <= 0
					|| et_new_password.length() <= 0) {
				toast.show("新密码不能为空");
				return;
			}
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
			newPwd = et_new_password.getText().toString();
			map.put("newpwd", newPwd);
			privacySettingInfo.setNewPassword(map,
					Constants.API_RESET_PASSWORD, Method.GET, hadnler);
			break;
		}
	}

	@Click(R.id.main_header_left)
	void back() {
		finish();
	}

}
