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
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditNicknameActivity.java
 * @Description: 修改公司
 * @author: cj
 * @date: 2015年10月21日 上午10:43:25
 */

@EActivity(R.layout.activity_edit_corporation)
public class EditCorporationActivity extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "EditCorporationActivity";
	@Bean
	ToastComponent toast;

	@ViewById(R.id.et_edit_corporation)
	EditText et_edit_corporation;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_edit_corporation_complete)
	Button btn_edit_corporation_complete;

	private int position = -1;
	private String oldCorporation = "";

	@AfterViews
	void init() {
		tvTitle.setText("修改公司名称");
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		btn_edit_corporation_complete.setOnClickListener(this);
		position = getIntent().getIntExtra("position", -1);
		oldCorporation = getIntent().getStringExtra("oldCorporation");
		LogUtils.i(TAG, "position=" + position + ",oldCorporation="
				+ oldCorporation);
		if (!oldCorporation.equals("")) {
			et_edit_corporation.setText(oldCorporation);
		}
		/**
		 * 将光标设置到文字的末尾
		 */
		CharSequence text = et_edit_corporation.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit_corporation_complete: {
			String newCorporation = et_edit_corporation.getText().toString();
			if (!newCorporation.equals(oldCorporation)) {
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.putExtra("newCorporation", newCorporation);
				setResult(RESULT_OK, intent);
				LogUtils.i(TAG, "修改公司为：" + newCorporation);
			} else {
				LogUtils.i(TAG, "没有修改公司");
			}
			finish();
		}
			break;
		case R.id.iv_back:
			finish();
		}
	}

}