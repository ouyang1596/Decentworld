package cn.sx.decentworld.dialog;


import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Chatfragment_groupchat extends DialogFragment implements
		OnClickListener {
	private ImageView cancel;
	private Button save;
	private EditText et;
	private Groupchat_DialogCallbackListener listener;
	private RelativeLayout root_dialog_groupchat_groupname;
	

	public static Chatfragment_groupchat newInstance(String title) {
		Chatfragment_groupchat adf = new Chatfragment_groupchat();
		Bundle bundle = new Bundle();
		bundle.putString("used_name", title);
		adf.setArguments(bundle);
		
		return adf;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
//		m_dialog.getWindow().setBackgroundDrawable(null);
//		Dialog dialog = super.onCreateDialog(savedInstanceState);
		m_dialog.show();
		return m_dialog;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_groupchat_groupname,
				container, false);
		root_dialog_groupchat_groupname=(RelativeLayout) view.findViewById(R.id.root_dialog_groupchat_groupname);
		ViewUtil.scaleContentView(root_dialog_groupchat_groupname);
		cancel = (ImageView) view.findViewById(R.id.group_name_cancel);
		cancel.setOnClickListener(this);
		save = (Button) view.findViewById(R.id.group_name_yes);
		save.setOnClickListener(this);
		et = (EditText) view.findViewById(R.id.editT);
		et.setText(getArguments().getString("used_name"));
//		et.setSelection(getArguments().getString("used_name").length());
		return view;
	}

	/**
	 * 实现回调
	 * 
	 * @param listener
	 */
	public void setOnListener(final Groupchat_DialogCallbackListener listener) {
		this.listener = listener;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	public void setEditText(String used_name) {
		et.setText(used_name);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.group_name_cancel:
			if (listener != null)
				listener.oncancel();
			dismiss();
			break;

		case R.id.group_name_yes:
			System.out.println("click save");
			if (listener != null) {
				listener.save(et.getText().toString());
			}
			dismiss();
			break;
		default:
			break;
		}

	}

}
