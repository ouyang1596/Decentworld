/**
 * 
 */
package cn.sx.decentworld.fragment.setting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * @ClassName: SettingFragment.java
 * @author: yj
 * @date: 2016年2月17日 上午10:51:46
 */
public class SettingFragment extends Fragment implements OnClickListener {
	private TextView tvAdvancedSetting, tvPrivacySetting, tvAboutUs, tvExit;
	private ImageView ivBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		ivBack = (ImageView) getView().findViewById(R.id.iv_back);
		ivBack.setOnClickListener(this);
		tvAdvancedSetting = (TextView) getView().findViewById(R.id.tv_advanced_setting);
		tvAdvancedSetting.setOnClickListener(this);
		tvPrivacySetting = (TextView) getView().findViewById(R.id.tv_privacy_setting);
		tvPrivacySetting.setOnClickListener(this);
		tvAboutUs = (TextView) getView().findViewById(R.id.tv_about_us);
		tvAboutUs.setOnClickListener(this);
		tvExit = (TextView) getView().findViewById(R.id.tv_exit);
		tvExit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (null != mOnSettingClickListener) {
			mOnSettingClickListener.OnSettingClick(v);
		}
	}

	public interface OnSettingClickListener {
		void OnSettingClick(View view);
	}

	private OnSettingClickListener mOnSettingClickListener;

	public void setOnSettingClickListener(OnSettingClickListener onDoubtWanSecondClickListener) {
		mOnSettingClickListener = onDoubtWanSecondClickListener;
	}
}
