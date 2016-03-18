/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.R.integer;
import android.R.string;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: ReminderDialog.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月24日 下午7:20:17
 */
public class ReminderDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "ReminderDialog";
	private ReminderListener listener;
	private TextView tv_reminder_info;
	private String info=null;
	private Button bt_reminder_confirm;
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
		ViewUtil.scaleContentView((RelativeLayout)view);
		tv_reminder_info = (TextView) view.findViewById(R.id.tv_reminder_info);
		bt_reminder_confirm = (Button) view.findViewById(R.id.bt_reminder_confirm);
		bt_reminder_confirm.setOnClickListener(this);
		return view;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (info == null)
		{
			tv_reminder_info.setText("默认信息，请输入自定义信息");
		}
		else {
			tv_reminder_info.setText(info);
		}
		
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.bt_reminder_confirm:
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
