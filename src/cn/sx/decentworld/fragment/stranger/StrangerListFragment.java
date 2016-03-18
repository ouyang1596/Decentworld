/**
 * 
 */
package cn.sx.decentworld.fragment.stranger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.adapter.NearStrangerListAdapter;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NearbyStrangerInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: StrangerListFragment.java
 * @author: yj
 * @date: 2016年1月6日 下午3:28:18
 */
@EFragment(R.layout.fragment_near_list)
public class StrangerListFragment extends Fragment implements OnClickListener, OnCommomPromptListener {
	@ViewById(R.id.lv_stranger_near)
	PullToRefreshListView lvNearStranger;
	public NearStrangerListAdapter nearStrangerAdapter;
	private static final String POSITION = "position";
	private CommomPromptDialogFragment mCommomPromptDialog;
	public boolean isPullToRefresh = false;
	public NearbyStrangerInfo nearbyStrangerInfo;

	@AfterViews
	public void init() {
		initData();
	}

	private void initData() {
		setAdapter();
	}

	public void completeRefresh() {
		lvNearStranger.onRefreshComplete();
	}

	@SuppressWarnings("unchecked")
	private void setAdapter() {
		nearStrangerAdapter = new NearStrangerListAdapter(getActivity(), StrangerCardFragment.flipDataList);
		nearStrangerAdapter.setOnClickListener(new NearStrangerListAdapter.OnClickListener() {

			@Override
			public void onClick(View view) {
				Integer position = (Integer) view.getTag(Constants.ITEM_POSITION);
				nearbyStrangerInfo = nearStrangerAdapter.getItem(position);
				switch (view.getId()) {
				case R.id.iv_stranger_detail:
					Intent intent = new Intent(getActivity(), NearCardDetailActivity_.class);
					intent.putExtra(Constants.DW_ID, nearbyStrangerInfo.getDwID());
					intent.putExtra(POSITION, position);
					// intent.putExtra(Constants.IF_LIKE, item.getLiked());
					intent.putExtra("location", nearbyStrangerInfo.getDistance());
					startActivityForResult(intent, Constants.REQUEST_CODE);
					break;
				case R.id.iv_heart:
					if ("1".equals(nearbyStrangerInfo.getLiked())) {
						ToastUtil.showToast("您已经点过喜欢了");
						return;
					}
					if (FileUtils.getPromptStatus(getActivity(), Constants.LIKE) == CommomPromptDialogFragment.LIKE) {
						like(nearbyStrangerInfo.getDwID());
						return;
					}
					showCommomDialog(nearbyStrangerInfo, CommomPromptDialogFragment.LIKE,
							"原来你喜欢他，你的一块大洋，将飞到他身边；他同样喜欢你的时候，将得到你的大洋，并建立匹配对话框。有那么巧？呵呵，让大洋先飞一会吧。大洋探路，实在不行，三天飞回身家！");
					break;
				}
			}
		});
		lvNearStranger.setAdapter(nearStrangerAdapter);
		lvNearStranger.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				position = position - 1;
				NearbyStrangerInfo item = nearStrangerAdapter.getItem(position);
				if (ContactUser.isContact(item.getDwID())) {
					if (FileUtils.getPromptStatus(StrangerListFragment.this.getActivity(), Constants.FRIEND) == CommomPromptDialogFragment.FRIEND) {
						Intent intent = new Intent(getActivity(), ChatActivity_.class);
						intent.putExtra(ChatActivity.OTHER_ID, item.getDwID());
						intent.putExtra(ChatActivity.OTHER_NICKNAME, item.getName());
						intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
						intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(item.getWorth()));
						// 朋友关系
						intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_FRIEND);
						startActivity(intent);
					} else {
						showCommomDialog(item, CommomPromptDialogFragment.FRIEND, "你与他是朋友关系，他与你每说一句话，将按照他的身价向你付费。同理，注意你的身价哦！");
					}
				} else {
					if (FileUtils.getPromptStatus(StrangerListFragment.this.getActivity(), Constants.STRANGER) == CommomPromptDialogFragment.STRANGER) {
						Intent intent = new Intent(getActivity(), ChatActivity_.class);
						intent.putExtra(ChatActivity.OTHER_ID, item.getDwID());
						intent.putExtra(ChatActivity.OTHER_NICKNAME, item.getName());
						intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
						intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(item.getWorth()));
						// 陌生人关系
						intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_STRANGER);
						startActivity(intent);
					} else {
						showCommomDialog(item, CommomPromptDialogFragment.STRANGER,
								"你与他是陌生人关系，每人前三句是不会得到费用的。之后他与你每说一句话，将按照你的身价向你付费。同理，注意他的身价哦！");
					}
				}
			}
		});
		lvNearStranger.setMode(Mode.BOTH);
		lvNearStranger.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				isPullToRefresh = true;
				clearData();
				getNearStrangerInfo();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				StrangerCardFragment.page++;
				getNearStrangerInfo();
			}
		});
		// isPrepared = true;
	}

	private void getNearStrangerInfo() {
		if (null != mOnGetNearStrangerInfo) {
			mOnGetNearStrangerInfo.onGetNearStrangerInfo();
		}
	}

	private void clearData() {
		if (null != mOnGetNearStrangerInfo) {
			mOnGetNearStrangerInfo.onClearData();
		}
	}

	private void like(String dwID) {
		if (null != mOnGetNearStrangerInfo) {
			mOnGetNearStrangerInfo.onLike(dwID);
		}
	}

	private void disLike(int position) {
		if (null != mOnGetNearStrangerInfo) {
			mOnGetNearStrangerInfo.onDisLike(position);
		}
	}

	public interface OnGetNearStrangerInfoListener {
		void onGetNearStrangerInfo();

		void onClearData();

		void onLike(String dwID);

		void onDisLike(int position);
	}

	private OnGetNearStrangerInfoListener mOnGetNearStrangerInfo;

	public void setOnGetNearStrangerListener(OnGetNearStrangerInfoListener onDoubtWanSecondClickListener) {
		mOnGetNearStrangerInfo = onDoubtWanSecondClickListener;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Constants.RESULT_CODE_DISLIKE) {
			int position = data.getIntExtra(POSITION, -1);
			disLike(position);
		} else if (resultCode == Constants.RESULT_CODE_LIKE) {
			int position = data.getIntExtra(POSITION, -1);
			NearbyStrangerInfo info = nearStrangerAdapter.getItem(position);
			if ("1".equals(info.getLiked())) {
				return;
			}
			like(info.getDwID());
		}
	}

	@Override
	public void onCommomPromtClick(View view) {
		NearbyStrangerInfo strangerInfo = (NearbyStrangerInfo) mCommomPromptDialog.getObject();
		switch (mCommomPromptDialog.getEnter()) {
		case CommomPromptDialogFragment.LIKE:
			like(strangerInfo.getDwID());
			break;
		case CommomPromptDialogFragment.STRANGER:
			Intent intent = new Intent(getActivity(), ChatActivity_.class);
			intent.putExtra(ChatActivity.OTHER_ID, strangerInfo.getDwID());
			intent.putExtra(ChatActivity.OTHER_NICKNAME, strangerInfo.getName());
			intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
			intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(strangerInfo.getWorth()));
			// 陌生人关系
			intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_STRANGER);
			startActivity(intent);
			break;
		case CommomPromptDialogFragment.FRIEND:
			intent = new Intent(getActivity(), ChatActivity_.class);
			intent.putExtra(ChatActivity.OTHER_ID, strangerInfo.getDwID());
			intent.putExtra(ChatActivity.OTHER_NICKNAME, strangerInfo.getName());
			intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
			intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(strangerInfo.getWorth()));
			// 朋友关系
			intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_FRIEND);
			startActivity(intent);
			break;
		}
	}

	private void showCommomDialog(NearbyStrangerInfo item, int enter, String prompt) {
		if (null == mCommomPromptDialog) {
			mCommomPromptDialog = new CommomPromptDialogFragment();
		}
		mCommomPromptDialog.setObject(item);
		mCommomPromptDialog.setEnter(enter);
		mCommomPromptDialog.setOnCommomPromptListener(StrangerListFragment.this);
		mCommomPromptDialog.setTips(prompt);
		mCommomPromptDialog.show(StrangerListFragment.this.getActivity().getSupportFragmentManager(), "mCommomPromptDialog");
	}
}