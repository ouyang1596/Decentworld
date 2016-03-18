package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Chatfragment_delete_conversation extends DialogFragment implements
		OnClickListener {
	private TextView de_message;
	private TextView de_conversation;
	private Chat_delete_dialogListener listener;
	private int item_position_click;
	
	public static Chatfragment_delete_conversation newInstance(int position) {
		Chatfragment_delete_conversation adf = new Chatfragment_delete_conversation();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		adf.setArguments(bundle);
		return adf;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		m_dialog.show();
		return m_dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_delete_dialog, container,
				false);
		de_message = (TextView) view.findViewById(R.id.delete_message);
		de_message.setOnClickListener(this);
		de_conversation = (TextView) view
				.findViewById(R.id.delete_conversation);
		de_conversation.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete_conversation:
			if (listener != null)
				listener.delete_conversation(item_position_click);
			dismiss();
			break;
		case R.id.delete_message:
			if (listener != null)
				listener.delete_message(item_position_click);
			dismiss();
			break;
		default:
			break;
		}
	}

	public void setOnListener(final Chat_delete_dialogListener listener) {
		this.listener = listener;
	}

}
