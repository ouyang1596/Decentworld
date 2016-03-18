package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.LikeBean;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

/**
 * 照相机与拍照选择对话框
 */
public class MatchSuccessDialogFragment extends DialogFragment implements OnClickListener {
	private Dialog mDialog;
	private TextView tvCancel, tvEnsure, tvTips;
	private String tips;
	private String jsonData;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (null == mDialog) {
			mDialog = new Dialog(getActivity(), R.style.ShouchangDialog);
		}
		mDialog.show();
		return mDialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return View.inflate(getActivity(), R.layout.fragment_match_success, null);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		tvCancel = (TextView) getView().findViewById(R.id.tv_cancel);
		tvEnsure = (TextView) getView().findViewById(R.id.tv_talk);
		tvTips = (TextView) getView().findViewById(R.id.tv_tips);
		if (null != tips) {
			tvTips.setText(tips);
		}
		parserJsonData();
		tvTips.setText("您与" + likeBean.showName + "已匹配成功，是否进行聊天？");
		tvCancel.setOnClickListener(this);
		tvEnsure.setTag(likeBean);
		tvEnsure.setOnClickListener(this);
	}

	LikeBean likeBean;

	private void parserJsonData() {
		if (null == jsonData) {
			return;
		}
		likeBean = JsonUtils.json2Bean(jsonData, LikeBean.class);
		LogUtils.e("bm", "matchBean----" + likeBean.toString());
	}

	@Override
	public void onClick(View view) {
		if (null != onMatchClickListener) {
			onMatchClickListener.onMatchClick(view);
		}
		dismiss();
	}

	private OnMatchClickListener onMatchClickListener;

	public void setOnMatchClickListener(OnMatchClickListener onMatchClickListener) {
		this.onMatchClickListener = onMatchClickListener;
	};

	public interface OnMatchClickListener {
		public void onMatchClick(View view);
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}
