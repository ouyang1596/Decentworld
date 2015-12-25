package cn.sx.decentworld.dialog;

import org.apache.commons.lang.ObjectUtils.Null;

import cn.sx.decentworld.R;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @ClassName: TransmitConfirmfDialog.java
 * @Description: 转发消息时弹出的 确定与取消对话框
 * @author: cj
 * @date: 2015年7月9日 下午4:56:08
 */
public class EmptyMsgDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "EmptyMsgDialog";
//	Log.i(TAG, "1");
	private TextView cancel;
	private TextView empty;
	private EmptyMsgListener listener;

	/**
	 * 设置对话框的监听事件，方便在回调函数中处理消息
	 * @param listener
	 */
	public void setOnListener(final EmptyMsgListener listener)
	{
		this.listener = listener;
	}
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view =inflater.inflate(R.layout.dialog_empty_message, container, false);
		
		empty = (TextView) view.findViewById(R.id.dialog_empty_message_empty);
		empty.setOnClickListener(this);
		
		cancel = (TextView) view.findViewById(R.id.dialog_empty_message_cancel);
		cancel.setOnClickListener(this);

		return view;
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getDialog().getWindow().setLayout( dm.widthPixels*5/6, getDialog().getWindow().getAttributes().height );
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

		case R.id.dialog_empty_message_empty:
			listener.onEmpty();
			dismiss();
			break;
		case R.id.dialog_empty_message_cancel:
			listener.onCancel();
			dismiss();
			break;
		default:
			break;
		}

	}
	
	public interface EmptyMsgListener
	{
		void onCancel();
		void onEmpty();
	}
}
