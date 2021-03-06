/**
 * 
 */
package cn.sx.decentworld.fragment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.AchievementActivity_;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.fragment.ViewPagerFragment.OnPagerChangeListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: DoubtVerifyFragment.java
 * @author: yj
 * @date: 2016年1月12日 下午3:25:20
 */
public class CancelDoubtSecondStepFragment extends Fragment implements OnClickListener, OnPagerChangeListener {
	private static final String TAG = "CancelDoubtSecondStepFragment";
	private TextView tvEnsure, tvCondition, tvAchievement;
	private ViewPagerFragment mVpFragment;
	private SendUrl mSendUrl;
	private String condition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_doubt_second_step, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		mSendUrl = new SendUrl(getActivity());
		tvEnsure = (TextView) getView().findViewById(R.id.tv_ensure);
		tvEnsure.setOnClickListener(this);
		tvAchievement = (TextView) getView().findViewById(R.id.tv_achievement);
		tvAchievement.setOnClickListener(this);
		tvCondition = (TextView) getView().findViewById(R.id.tv_condition);
		tvCondition.setText(condition);
		mVpFragment = new ViewPagerFragment();
		mVpFragment.setOnlyFirstCamera(true);
		mVpFragment.setMaxCount(4);
		mVpFragment.setCondition(poetry);
		mVpFragment.setOnPagerChangeListener(this);
		getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_vp_container, mVpFragment, "vf").commit();
	}

	public interface OnDoubtSecondClickListener {
		void OnDoubtSecondClick(View view);
	}

	private OnDoubtSecondClickListener mOnDoubtSecondClickListener;

	public void setOnDoubtSecondClickListener(OnDoubtSecondClickListener onDoubtSecondClickListener) {
		mOnDoubtSecondClickListener = onDoubtSecondClickListener;
	}

	public void checkUpload() {
		if (mVpFragment.mPicPaths.size() <= 1) {
			ToastUtil.showToast("请先选择至少二张图片");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		File[] file = new File[4];
		for (Map.Entry<Integer, String> entry : mVpFragment.mPicPaths.entrySet()) {
			file[entry.getKey()] = new File(entry.getValue());
		}
		mSendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH + Constants.API_CHECK_UPLOAD, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				LogUtils.d(TAG, "checkUpload " + msg.toString());
				if (2222 == msg.getResultCode()) {
					getActivity().finish();
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "checkUpload error " + e);
				showToast(Constants.NET_WRONG);
			}
		});
	}

	private String poetry;

	public void setCondition(String condition) {
		this.condition = condition;
		if (condition.contains("【") && condition.contains("】")) {
			poetry = condition.substring(condition.indexOf("【"), condition.indexOf("】") + 1);
		}
	}

	private void showToast(final String data) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ensure:
			checkUpload();
			break;
		case R.id.tv_achievement:
			startActivity(new Intent(getActivity(), AchievementActivity_.class));
			break;
		}
	}

	@Override
	public void OnPagerChange(int position) {
		LogUtils.d(TAG, "OnPagerChange position " + position);
		if (0 == position) {
			tvCondition.setText(condition);
		} else {
			tvCondition.setText("请上传至少一张您的其他靓照，需要看得清楚脸哦");
		}
	}
}
