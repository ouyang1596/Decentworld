/**
 * 
 */
package cn.sx.decentworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.sx.decentworld.R;

/**
 * @ClassName: WorksFragment.java
 * @Description:
 * @author: yj
 * @date: 2016年1月6日 下午3:28:18
 */
public class WorksFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_works, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {

	}
}
