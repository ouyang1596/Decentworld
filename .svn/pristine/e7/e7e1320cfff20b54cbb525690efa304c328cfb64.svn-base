/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: Dialog_Fragment_add_friend.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月20日 下午4:58:08
 */
@EFragment(R.layout.dialog_add_friend)
public class Dialog_Fragment_add_friend extends DialogFragment {
//	@ViewById(R.id.editT_myname)
	EditText editT_myname;
	
	private add_friendListener listener;
	private Dialog m_dialog;
	
	/**
	 * 实现回调
	 * 
	 * @param listener
	 */
	public void setOnListener(final add_friendListener listener) {
		this.listener = listener;
	}
	
	public static Dialog_Fragment_add_friend newInstance() {
		Dialog_Fragment_add_friend adf = new Dialog_Fragment_add_friend();
		return adf;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		 m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		m_dialog.show();
		return m_dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_add_friend,
				container, false);
		
		editT_myname= (EditText) view.findViewById(R.id.editT_myname);
		view.findViewById(R.id.add_friend_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				m_dialog.dismiss();
			}
		});
		view.findViewById(R.id.group_name_nickname_yes).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					String aa =editT_myname.getText().toString();
					listener.add_friend_with_reason(aa);
					m_dialog.dismiss();
				}
				else{
					LogUtils.i("Dialog_Fragment_add_friend", "onClick   listener==null");
				}
			}
		});
		return view;
	}



	@Override
	public void onStart() {
		super.onStart();
	}

	public void setEditText(String used_name) {
		editT_myname.setText(used_name);
		
	}
	public interface add_friendListener{
		public void add_friend_with_reason(String reason);
	}

}
