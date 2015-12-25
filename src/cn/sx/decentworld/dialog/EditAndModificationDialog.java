/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: EditAndModificationDialog.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月30日 上午10:09:49
 */
public class EditAndModificationDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "EditAndModificationDialog";
	private EditAndModificationListener listener;
	private TextView tv_edit_and_modification_title;
	private String title;
	private EditText et_edit_and_modification_info;
	private Button btn_edit_and_modification_confirm;
	private View view;
	
	/**
	 * @param listener the listener to set
	 */
	public void setListener(EditAndModificationListener listener)
	{
		this.listener = listener;
	}
	

	/**
	 * 
	 */
	public void setClickView(View view)
	{
		this.view = view;
	}
	
	/**
	 * @return the view
	 */
	public View getClickView()
	{
		return view;
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
		View view = inflater.inflate(R.layout.dialog_edit_and_modification, null);
		ViewUtil.scaleContentView((LinearLayout)view);
		tv_edit_and_modification_title = (TextView) view.findViewById(R.id.tv_edit_and_modification_title);
		et_edit_and_modification_info = (EditText) view.findViewById(R.id.et_edit_and_modification_info);
		btn_edit_and_modification_confirm = (Button) view.findViewById(R.id.btn_edit_and_modification_confirm);
		btn_edit_and_modification_confirm.setOnClickListener(this);
		return view;
	}
	

	@Override
	public void onStart()
	{
		super.onStart();
		if (title == null)
		{
			tv_edit_and_modification_title.setText("编辑并修改为");
		}
		else {
			tv_edit_and_modification_title.setText(title);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_edit_and_modification_confirm:
			String info = et_edit_and_modification_info.getText().toString();
			if(CommUtil.isNotBlank(info))
			{
				listener.confirm(info);
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et_edit_and_modification_info.getWindowToken(), 0);
				dismiss();
			}
			else
			{
				Toast.makeText(getActivity(), "备注不能为空", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	/**获取标题内容
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**设置标题内容
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * 获得编辑框控件
	 * @return
	 */
	public EditText getEditText()
	{
		return et_edit_and_modification_info;
	}
	
	public interface EditAndModificationListener
	{
		public void confirm(String info);
	}
	
}
