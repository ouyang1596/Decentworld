/**
 * 
 */
package cn.sx.decentworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * @ClassName: DoubtVerifyFragment.java
 * @author: yj
 * @date: 2016年1月12日 下午3:25:20
 */
public class DoubtWanSecondStepFragment extends Fragment implements
		OnClickListener {
	private TextView tvTalent, tvMoney,tvCommomService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_doubt_wan_second_step, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		tvTalent = (TextView) getView().findViewById(R.id.tv_talent);
		tvTalent.setOnClickListener(this);
		tvMoney = (TextView) getView().findViewById(R.id.tv_money);
		tvMoney.setOnClickListener(this);
		tvCommomService = (TextView) getView().findViewById(R.id.tv_commom_service);
		tvCommomService.setOnClickListener(this);
	}

	public interface OnDoubtWanSecondClickListener {
		void OnDoubtWanSecondClick(View view);
	}

	private OnDoubtWanSecondClickListener mOnDoubtWanSecondClickListener;

	public void setOnDoubtWanSecondClickListener(
			OnDoubtWanSecondClickListener onDoubtWanSecondClickListener) {
		mOnDoubtWanSecondClickListener = onDoubtWanSecondClickListener;
	}

	@Override
	public void onClick(View v) {
		if (mOnDoubtWanSecondClickListener != null) {
			mOnDoubtWanSecondClickListener.OnDoubtWanSecondClick(v);
		}
	}
}
