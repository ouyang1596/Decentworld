/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.EFragment;

/**
 * @ClassName: AddFriendDialog.java
 * @Description: 添加朋友对话框
 * @author: cj
 * @date: 2016年3月2日 下午10:23:25
 */
@EFragment(R.layout.dialog_add_friend)
public class AddFriendDialog extends DialogFragment {
	private EditText editT_myname;
	private AddFriendListener listener;
	private Dialog m_dialog;
	private TextView tvNeedMoney;
	private boolean mNeedMoeny = true;
	private String prompt = "除以其以电话号码或DW号搜索添加外，其他方式发送添加请求都会扣取5大洋";

	/**
	 * 实现回调
	 */
	public void setOnListener(final AddFriendListener listener) {
		this.listener = listener;
	}

	public static AddFriendDialog newInstance() {
		AddFriendDialog adf = new AddFriendDialog();
		return adf;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		m_dialog.show();
		return m_dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_add_friend, container, false);
		editT_myname = (EditText) view.findViewById(R.id.editT_myname);
		view.findViewById(R.id.add_friend_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				m_dialog.dismiss();
			}
		});
		view.findViewById(R.id.group_name_nickname_yes).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					String aa = editT_myname.getText().toString();
					listener.withReason(aa);
					m_dialog.dismiss();
				} else {
					LogUtils.i("Dialog_Fragment_add_friend", "onClick   listener==null");
				}
			}
		});
		tvNeedMoney = (TextView) view.findViewById(R.id.info_add_newfriend);
		SpannableStringBuilder highLightTxt = new SpannableStringBuilder(prompt);
		int indexOf = prompt.indexOf("扣取5大洋");
		highLightTxt.setSpan(new ForegroundColorSpan(Color.RED), indexOf, prompt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (mNeedMoeny) {
			tvNeedMoney.setText(highLightTxt);
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

	public void setEditText(String used_name) {
		editT_myname.setText(used_name);
	}

	public interface AddFriendListener {
		// 添加朋友的理由
		public void withReason(String reason);
	}

	public void setNeedMoney(boolean needMoney) {
		mNeedMoeny = needMoney;
	}
}
