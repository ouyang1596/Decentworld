/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.dialog.LoginPwdWrongDialogFragment.LoginPwdWrongCallbackListener;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: BuildGroupLocationDialog.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月31日 上午9:40:51
 */
public class BuildGroupLocationDialog extends DialogFragment implements
		OnClickListener {

	private TextView address1;
	private EditText address2;
	private Button button;
	private AddressListener listener;
	private RelativeLayout root_build_group_location_dialog;
	
	public static BuildGroupLocationDialog newInstance(String address1){
		BuildGroupLocationDialog aa=new BuildGroupLocationDialog();
		Bundle bundle=new Bundle();
		bundle.putString("address1", address1);
		aa.setArguments(bundle);
		return aa;
	}
	
	
	public void setListener (AddressListener listener){
		this.listener=listener;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.activity_build_group_locatioin_dialog, container,
				false);
		root_build_group_location_dialog=(RelativeLayout) view.findViewById(R.id.root_build_group_location_dialog);
		ViewUtil.scaleContentView(root_build_group_location_dialog);
		
		address1 = (TextView) view.findViewById(R.id.address1);
		address2 = (EditText) view.findViewById(R.id.address2);
		button = (Button) view.findViewById(R.id.bt_to_next);
		button.setOnClickListener(this);
		address1.setText(getArguments().getString("address1"));
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Dialog dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		return dialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(address2.getText().toString().equals("")){
			listener.PutAddress(address1.getText().toString());
		}
		else{
			listener.PutAddress(address1.getText().toString()+"+"+address2.getText().toString());
		}
		dismiss();
	}
	

	public interface AddressListener {
		public void PutAddress(String address);
	}

}
