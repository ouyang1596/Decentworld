/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: RecorderDialog.java
 * @Description:
 * @author: dyq
 * @date: 2015年9月22日 下午12:36:28
 */
public class RecorderDialog {
	private ImageView image;
	private TextView text_tip;
	private Context mContext;
	private Dialog mDialog;

	public RecorderDialog(Context mContext) {
		super();
		this.mContext = mContext;
		initDialog(mContext);
	}

	private void initDialog(Context mContext) {
		mDialog = new Dialog(mContext, R.style.MyTryUseDialogFragment);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_recorder, null);
		image = (ImageView) view.findViewById(R.id.mic_image);
		text_tip = (TextView) view.findViewById(R.id.recording_hint);
		mDialog.setContentView(view);
	}

	public void showDialog() {
		if (null == mDialog) {
			initDialog(mContext);
		}
		mDialog.show();
	}

	public void tooShort() {
		// image_left.setImageResource(R.drawable.tooshort);
		// image_left.setVisibility(View.VISIBLE);
		// image_right.setVisibility(View.GONE);
		text_tip.setText(R.string.too_short);
	}

	public void wantToCancel() {
		// image_left.setImageResource(R.drawable.mg);
		// image_left.setVisibility(View.VISIBLE);
		// image_right.setVisibility(View.VISIBLE);
		if (null != text_tip) {
			text_tip.setText(R.string.str_recoder_want_cancel);
		}
	}

	public void recording() {
		// image_left.setImageResource(R.drawable.mg);
		// image_left.setVisibility(View.VISIBLE);
		// image_right.setVisibility(View.VISIBLE);
		text_tip.setText(R.string.str_recoder_recording);
	}

	public void dismiss() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	public void changeVoiceLevel(int level) {
		if (mDialog != null && mDialog.isShowing()) {

			// 通過上下文對象 獲取原本文件源的資源文件
			int VoicIC = mContext.getResources().getIdentifier(
					"record_animate_" + level, "drawable",
					mContext.getPackageName());
			image.setImageResource(VoicIC);
		}

	}
}
