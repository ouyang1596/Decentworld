/**
 * 
 */
package cn.sx.decentworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;

/**
 * @ClassName: AchievementFragment.java
 * @Description:
 * @author: yj
 * @date: 2016年1月6日 下午3:28:18
 */
public class AchievementFragment extends Fragment {
	private TextView tvAchievement;
	private LinearLayout llIntroduce;
	private TextView tvShortIntroduce, tvDetailintroduce;
	private String shortIntroduce, detailIntroduce;
	private ImageView ivEditIntroduce;
	private ImageView ivAchievement;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_achievement, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		ivAchievement = (ImageView) getView().findViewById(R.id.iv_achievement);
		ivAchievement.setVisibility(View.GONE);
		ivEditIntroduce = (ImageView) getView().findViewById(R.id.iv_edit_introduce);
		ivEditIntroduce.setVisibility(View.GONE);
		tvAchievement = (TextView) getView().findViewById(R.id.tv_achievement);
		llIntroduce = (LinearLayout) getView().findViewById(R.id.ll_introduce);
		tvShortIntroduce = (TextView) getView().findViewById(R.id.tv_short_introduce);
		tvDetailintroduce = (TextView) getView().findViewById(R.id.tv_detail_introduce);
	}

	public void setIntroduce(String shortIntroduce, String detailInroduce) {
		this.shortIntroduce = shortIntroduce;
		this.detailIntroduce = detailInroduce;
		if (CommUtil.isBlank(this.shortIntroduce) && CommUtil.isBlank(this.detailIntroduce)) {
			llIntroduce.setVisibility(View.GONE);
			tvAchievement.setVisibility(View.VISIBLE);
			tvAchievement.setText("还没有添加简介哦");
			return;
		}
		tvAchievement.setVisibility(View.GONE);
		llIntroduce.setVisibility(View.VISIBLE);
		if (CommUtil.isBlank(this.shortIntroduce)) {
			tvShortIntroduce.setVisibility(View.GONE);
		} else {
			tvShortIntroduce.setVisibility(View.VISIBLE);
			tvShortIntroduce.setText(this.shortIntroduce);
		}
		if (CommUtil.isBlank(this.detailIntroduce)) {
			tvDetailintroduce.setVisibility(View.GONE);
		} else {
			tvDetailintroduce.setVisibility(View.VISIBLE);
			tvDetailintroduce.setText(this.detailIntroduce);
		}
	}
}
