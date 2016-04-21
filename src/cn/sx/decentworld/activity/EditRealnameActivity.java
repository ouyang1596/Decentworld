/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ModifyRealnameDialog;
import cn.sx.decentworld.dialog.ModifyRealnameDialog.ModifyRealnameListener;
import cn.sx.decentworld.dialog.VerifyLoginPwdDialog;
import cn.sx.decentworld.dialog.VerifyLoginPwdDialog.VerifyLoginPwdListener;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.logSystem.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditSignActivity.java
 * @Description：修改实名
 * @author: cj
 * @date: 2015年10月21日 上午8:46:07
 */
@EActivity(R.layout.activity_edit_realname)
public class EditRealnameActivity extends BaseFragmentActivity implements OnClickListener {
	private static final String TAG = "EditRealnameActivity";
	@Bean
	ToastComponent toast;
	/**
	 * 输入昵称编辑框
	 */
	@ViewById(R.id.et_edit_realname)
	EditText etRealName;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_edit_realname_complete)
	Button btnSubimt;

	/**
	 * 旧的数据
	 */
	private int position = -1;
	private String oldData = "";

	@AfterViews
	void init() {
		tvTitle.setText("实名");
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		btnSubimt.setOnClickListener(this);
		position = getIntent().getIntExtra("position", -1);
		oldData = getIntent().getStringExtra("oldData");

		LogUtils.i(TAG, "position=" + position + ",oldData=" + oldData);
		if (!oldData.equals("")) {
			etRealName.setText(oldData);
		}

		/**
		 * 将光标设置到文字的末尾
		 */
		CharSequence text = etRealName.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit_realname_complete:
			modifyRealname();
			break;
		case R.id.iv_back:
			submit();
			break;
		default:
			break;
		}
	}

	/**
	 * 提交
	 */
	private void submit() {
		String newData = etRealName.getText().toString();
		if (CommUtil.isBlank(newData)) {
			toast.show("实名不能为空");
			return;
		}
		if (!newData.equals(oldData)) {
			Intent intent = new Intent();
			intent.putExtra("position", position);
			intent.putExtra("newData", newData);
			setResult(RESULT_OK, intent);
			LogUtils.i(TAG, "修改实名为：" + newData);
		} else {
			LogUtils.i(TAG, "没有修改昵称");
		}
		finish();
	}

	/**
	 * 修改实名
	 */
	private void modifyRealname() {
		VerifyLoginPwdDialog verifyLoginPwdDialog = new VerifyLoginPwdDialog();
		verifyLoginPwdDialog.setListener(new VerifyLoginPwdListener() {
			@Override
			public void onSuccess(String token) {
				LogUtils.i(TAG, "验证密码成功，返回的token=" + token);
				setRealname(token);
			}

			@Override
			public void onFailure(String cause) {
				LogUtils.i(TAG, "验证密码失败");
			}
		});
		verifyLoginPwdDialog.show(getSupportFragmentManager(), "");
	}

	/**
	 * 设置实名
	 */
	private void setRealname(String token) {
		ModifyRealnameDialog dialog = new ModifyRealnameDialog();
		dialog.setTempToken(token);
		dialog.setListener(new ModifyRealnameListener() {
			@Override
			public void onSuccess(String info) {
				onResume();
				toast.show(info);
			}

			@Override
			public void onFailure(String cause) {
				toast.show(cause);
			}
		});
		dialog.show(getSupportFragmentManager(), "");
	}

	/**
	 * 更新数据
	 */

	@Override
	protected void onResume() {
		super.onResume();
		LogUtils.i(TAG, "onResume");
		String realName = UserInfoEngine.getInstance().getUserInfo().getRealName();
		etRealName.setText(realName);
	}

}
