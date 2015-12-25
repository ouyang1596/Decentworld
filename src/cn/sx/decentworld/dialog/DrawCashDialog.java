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
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * @ClassName: DrawCashDialog.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月26日 下午4:34:12
 */
public class DrawCashDialog extends DialogFragment implements OnClickListener
{
	private DrawCashListener listener;
	private EditText et_draw_cash_amount;
	private String amount;
	private Button btn_draw_cash_confirm;
	

	/**
	 * @param listener the listener to set
	 */
	public void setListener(DrawCashListener listener)
	{
		this.listener = listener;
	}
	
	public String getAmount()
	{
		return amount;
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
		View view = inflater.inflate(R.layout.dialog_draw_cash, container, false);
		et_draw_cash_amount = (EditText) view.findViewById(R.id.et_draw_cash_amount);
		btn_draw_cash_confirm = (Button) view.findViewById(R.id.btn_draw_cash_confirm);
		btn_draw_cash_confirm.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}


	@Override
	public void onClick(View v)
	{
		amount = et_draw_cash_amount.getText().toString();
		listener.confirm();
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_draw_cash_amount.getWindowToken(), 0);
		dismiss();
	}
	
	public interface DrawCashListener
	{
		public void confirm();
	}
}
