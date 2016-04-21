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
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;

/**
 * @ClassName: ReminderDialog.java
 * @Description: 提示对话框
 * @author: cj
 * @date: 2015年7月24日 下午7:20:17
 */
public class ReminderDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "ReminderDialog";
	private ReminderListener listener;
	private TextView tv_reminder_info;
	private String info = "";
	/** 按钮上的文字 **/
	private String btn_text = "";
	private Button btn_reminder_confirm;
	public int param;
	

	
	/**
	 * @param listener the listener to set
	 */
	public void setListener(ReminderListener listener)
	{
		this.listener = listener;
	}
	
	public void setInfo(String info)
	{
		this.info = info;
	}
	
	public void setButtonText(String text)
	{
	    this.btn_text = text;
	}
	
	public void setExtraParam(int param)
	{
		this.param = param;
	}
	
	public int getExtraParam()
	{
		return param;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.dialog_reminder, container, false);
		tv_reminder_info = (TextView) view.findViewById(R.id.tv_reminder_info);
		btn_reminder_confirm = (Button) view.findViewById(R.id.btn_belong_dialog_submit);
		btn_reminder_confirm.setOnClickListener(this);
		return view;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		/** 设置提示信息 **/
		if (CommUtil.isNotBlank(info))
		    tv_reminder_info.setText(info);
		else
			tv_reminder_info.setText("默认信息，请输入自定义信息");
		
		/** 按钮上的文字信息 **/
		if(CommUtil.isNotBlank(btn_text))
		    btn_reminder_confirm.setText(btn_text);
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_belong_dialog_submit:
			listener.confirm();
			dismiss();
			break;

		default:
			break;
		}
	}
	
	public interface ReminderListener
	{
		public void confirm();

	}

}
