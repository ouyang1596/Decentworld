/**
 * 
 */
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

/**
 * @ClassName: ChatActivityOperationDialog.java
 * @Description: 
 * @author: dyq
 * @date: 2015年10月20日 下午4:10:49
 */
public class ChatActivityOperationDialog extends DialogFragment implements
	OnClickListener{

	private TextView tv_forward_message;
	private TextView tv_copy_message;
	private CharOperationDialogListener listener;
	private int item_position_click;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		m_dialog.show();
		return m_dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_operation_dialog, container, false);
		tv_forward_message = (TextView) view.findViewById(R.id.transmit_message);
		tv_forward_message.setOnClickListener(this);
		tv_copy_message = (TextView) view.findViewById(R.id.copy_message);
		tv_copy_message.setOnClickListener(this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	public void setOnListener(CharOperationDialogListener listener){
		this.listener = listener;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.transmit_message:
			listener.forward_message();
			break;
		case R.id.copy_message:
			listener.copy_message();
			break;
		}
	}
	public interface CharOperationDialogListener{
		void forward_message();
		void copy_message();
	}
	/**
	 * @param position
	 * @return
	 */
	public static ChatActivityOperationDialog newInstance() {
		ChatActivityOperationDialog dialogInstance = new ChatActivityOperationDialog();
		return dialogInstance;
	}

}
