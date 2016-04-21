/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.logSystem.LogUtils;

import com.googlecode.androidannotations.annotations.EFragment;

/**
 * @ClassName: AddFriendDialog.java
 * @Description: 添加朋友对话框
 * @author: cj
 * @date: 2016年3月2日 下午10:23:25
 */
@EFragment(R.layout.dialog_add_friend)
public class AddFriendDialog extends DialogFragment implements OnClickListener {
	private AddFriendListener listener;
	private EditText editT_myname;
	private Button btnSubmit;

	private TextView tvNeedMoney;
	private boolean mNeedMoeny = true;
	private String prompt = "注：除以其手机号或DW号搜索添加外，其他方式添加都会扣去10个大洋";

	/**
	 * 实现回调
	 */
	public void setOnListener(final AddFriendListener listener) {
		this.listener = listener;
	}

	public void setNeedMoney(boolean needMoney) {
		mNeedMoeny = needMoney;
	}

	public void setEditText(String used_name) {
		editT_myname.setText(used_name);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_add_friend, container, false);
		editT_myname = (EditText) view.findViewById(R.id.editT_myname);
		btnSubmit = (Button) view.findViewById(R.id.btn_belong_dialog_submit);
		btnSubmit.setOnClickListener(this);
		tvNeedMoney = (TextView) view.findViewById(R.id.info_add_newfriend);

		// SpannableStringBuilder highLightTxt = new
		// SpannableStringBuilder(prompt);
		// int indexOf = prompt.indexOf("扣取5大洋");
		// highLightTxt.setSpan(new ForegroundColorSpan(Color.YELLOW), indexOf,
		// prompt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (mNeedMoeny) {
			tvNeedMoney.setText(prompt);
			tvNeedMoney.setVisibility(View.VISIBLE);
		} else {
			tvNeedMoney.setVisibility(View.GONE);
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	/**
	 * 回调接口
	 */
	public interface AddFriendListener {
		// 添加朋友的理由
		public void withReason(String reason);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_belong_dialog_submit:
			if (listener != null) {
				String aa = editT_myname.getText().toString();
				listener.withReason(aa);
				dismiss();
			} else {
				LogUtils.i("Dialog_Fragment_add_friend", "onClick   listener==null");
			}
			break;
		default:
			break;
		}

	}
}
