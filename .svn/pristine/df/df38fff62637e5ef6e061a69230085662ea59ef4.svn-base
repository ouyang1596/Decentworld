/**
 * 
 */
package cn.sx.decentworld.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.UserInfoItemAdapter;
import cn.sx.decentworld.bean.EditUserInfoItem;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: UserDetailFragment.java
 * @Description: 展示用户的详细信息
 * @author: cj
 * @date: 2015年10月12日 上午10:50:19
 */
@EFragment(R.layout.fragment_user_detail)
public class UserDetailFragment extends BaseFragment {
	private static final String TAG = "UserDetailFragment";
	private Context context;

	@ViewById(R.id.lv_user_detail)
	ListViewForScrollView lv_user_detail;
	private List<EditUserInfoItem> mDatas;//原始数据
	private List<EditUserInfoItem> proDatas;//处理过的数据
	private UserInfoItemAdapter userInfoItemAdapter;

	/**
	 * 依附所在的Activity
	 */

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	/**
	 * 调用界面传入数据
	 */
	public void setData(List<EditUserInfoItem> mDatas) {
		this.mDatas = mDatas;
	}


	@AfterViews
	void init() {
		proDatas = new ArrayList<EditUserInfoItem>();
		userInfoItemAdapter = new UserInfoItemAdapter(getActivity()
				.getSupportFragmentManager(), getActivity(), proDatas);
		lv_user_detail.setAdapter(userInfoItemAdapter);
		lv_user_detail.setEnabled(false);
		// 将初始化是否完成的标志位设为完成
		isPrepared = true;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		LogUtils.i(TAG, "onResume");
		dataPrePro(mDatas);
	}

	/**
	 * 数据预处理
	 */
	private void dataPrePro(List<EditUserInfoItem> mDatas)
	{
		if(proDatas==null||mDatas==null)
		{
			return;
		}
		proDatas.clear();
		for(EditUserInfoItem item : mDatas)
		{
			if(CommUtil.isNotBlank(item.getValue()))
			{
				proDatas.add(item);
			}
		}
		if(userInfoItemAdapter!=null)
		{
			userInfoItemAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * 刷新数据
	 */
	public void refreshData()
	{
		dataPrePro(mDatas);
	}


	/**
	 * 延时加载
	 */
	@Override
	protected void lazyLoad() {

	}

	/**
	 * 跳到指定的Tab
	 */
	@Override
	public void turnToTab(int tab) {

	}
}
