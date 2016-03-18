/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @ClassName: ChatRoomSetNickNameDialog.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月5日 下午6:09:14
 */
public class ChatRoomSetNickNameDialog extends DialogFragment {
	
	RelativeLayout root_activity_chat_room_set_name_dialog;
	public static ChatRoomSetNickNameDialog newInstance() {
		ChatRoomSetNickNameDialog adf = new ChatRoomSetNickNameDialog();
		return adf;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_chat_room_set_name_dialog, container,
				false);
		root_activity_chat_room_set_name_dialog=(RelativeLayout) view.findViewById(R.id.root_activity_chat_room_set_name_dialog);
		ViewUtil.scaleContentView(root_activity_chat_room_set_name_dialog);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog m_dialog = new Dialog(getActivity(),
				R.style.LoginAndRegisterDialog);
		m_dialog.show();
		return m_dialog;
	}
	/**
	 * @param fragmentManager
	 * @param tag
	 */
}
