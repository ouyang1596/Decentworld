/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ViewUtil;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @ClassName: BuildGroupTipsDialog.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月31日 上午11:33:36
 */
public class BuildGroupTipsDialog extends DialogFragment {
	backToGroupListener listener;
	RelativeLayout root_activity_group_tips;
	public void setListener(backToGroupListener listener){
		this.listener=listener;
	}
	public static BuildGroupTipsDialog newInstance()
			throws InterruptedException {
		final BuildGroupTipsDialog aa = new BuildGroupTipsDialog();
//		threadd.wait(4000);
		return aa;
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
		View view = inflater.inflate(R.layout.activity_group_tips, container,
				false);
		root_activity_group_tips=(RelativeLayout) view.findViewById(R.id.root_activity_group_tips);
		ViewUtil.scaleContentView(root_activity_group_tips);
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
	public interface backToGroupListener{
		void toback();
	}
}
