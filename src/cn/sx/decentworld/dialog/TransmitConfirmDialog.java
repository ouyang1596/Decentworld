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
public class TransmitConfirmDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "TransmitConfirmfDialog";
//	Log.i(TAG, "1");
	private TextView cancel;
	private TextView send;
	private TransmitConfirmListener listener;
	private ImageView avatarIv;
	private TextView toUsernameTv;
	private int avatarId=-1;
	private String toUsername = null;
	
	/**
	 * 设置对话框的监听事件，方便在回调函数中处理消息
	 * @param listener
	 */
	public void setOnListener(final TransmitConfirmListener listener)
	{
		this.listener = listener;
	}
	
	/**
	 * 设置被发送人的图标
	 * @param imageId
	 */
	public void  setAvatar(int avatarId)
	{
		this.avatarId = avatarId;
	}
	
	/**
	 * 设置被发送人的用户名
	 * @param toUsername
	 */
	public void setToUsername(String toUsername)
	{
		this.toUsername = toUsername;
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
		View view =inflater.inflate(R.layout.dialog_transmit_confirm, container, false);
		avatarIv = (ImageView) view.findViewById(R.id.dialog_transmit_confirm_avatar);
		toUsernameTv = (TextView) view.findViewById(R.id.dialog_transmit_confirm_toUsername);
		cancel = (TextView) view.findViewById(R.id.dialog_transmit_confirm_cancel);
		cancel.setOnClickListener(this);
		send = (TextView) view.findViewById(R.id.dialog_transmit_confirm_send);
		send.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getDialog().getWindow().setLayout( dm.widthPixels*5/6, getDialog().getWindow().getAttributes().height );
		//初始化头像
		if (-1==avatarId)
			avatarIv.setImageResource(R.drawable.ic_launcher);
		else 
			avatarIv.setImageResource(avatarId);
		//初始化被发送人的用户名
		if (null==toUsername)
			toUsernameTv.setText("默认用户名");
		else 
			toUsernameTv.setText(toUsername);
		
		
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.dialog_transmit_confirm_cancel:
			listener.onCancel();
			dismiss();
			break;
		case R.id.dialog_transmit_confirm_send:
			listener.onSend();
			dismiss();
			break;
		default:
			break;
		}
		
	}
	
	public interface TransmitConfirmListener
	{
		void onCancel();
		void onSend();
	}

}
