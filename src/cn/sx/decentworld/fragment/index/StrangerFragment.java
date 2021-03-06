package cn.sx.decentworld.fragment.index;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.SelectedActivity_;
import cn.sx.decentworld.bean.ChoiceInfo;
import cn.sx.decentworld.bean.LikeBean;
import cn.sx.decentworld.bean.NearbyStrangerInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.dialog.EnsureCancelDialog;
import cn.sx.decentworld.dialog.EnsureCancelDialog.OnEnsureCancelClickListener;
import cn.sx.decentworld.dialog.MatchSuccessDialogFragment;
import cn.sx.decentworld.dialog.MatchSuccessDialogFragment.OnMatchClickListener;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.fragment.stranger.StrangerCardFragment;
import cn.sx.decentworld.fragment.stranger.StrangerCardFragment.OnGetNearStrangerInfoListener;
import cn.sx.decentworld.fragment.stranger.StrangerCardFragment_;
import cn.sx.decentworld.fragment.stranger.StrangerListFragment;
import cn.sx.decentworld.fragment.stranger.StrangerListFragment_;
import cn.sx.decentworld.fragment.stranger.StrangerMessageFragment;
import cn.sx.decentworld.fragment.stranger.StrangerMessageFragment_;
import cn.sx.decentworld.listener.NotifyCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.DWUtils;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.SoundPoolUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.utils.VibratorUtil;

import com.amap.api.location.AMapLocation;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umeng.analytics.MobclickAgent;

/**
 * @ClassName: StrangerFragment
 * @Description: 主界面聊天StrangerFragment
 * @author yj
 * @date 2015年6月29日12:34:03
 */
@EFragment(R.layout.main_layout_stranger)
public class StrangerFragment extends BaseFragment implements OnMatchClickListener, OnClickListener, OnCommomPromptListener,
		OnEnsureCancelClickListener {
	private static final String TAG = "StrangerFragment";
	@ViewById(R.id.iv_stranger_message)
	ImageView ivStrangerMessage;
	@ViewById(R.id.iv_small_pic)
	ImageView ivSmallPic;
	@ViewById(R.id.iv_big_pic)
	ImageView ivBigPic;
	@ViewById(R.id.iv_like)
	ImageView ivLike;
	@ViewById(R.id.iv_select)
	ImageView ivSelect;
	public static boolean isRequesting;// 判断是否正在请求当中
	public static int mRadius;
	private StrangerCardFragment mStrangerCardFragment;
	private StrangerMessageFragment mStrangerMessageFragment;
	private StrangerListFragment mStrangerListFragment;
	private CommomPromptDialogFragment mCommomPromptDialog;
	private EnsureCancelDialog mEnsureCancelDialog;
	private FragmentManager fm;
	@Bean
	GetStrangerInfo getStrangerInfo;
	private Handler mGetNearStrangerInfoHandler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			mStrangerCardFragment.tvRefresh.setVisibility(View.VISIBLE);
			mStrangerListFragment.completeRefresh();
			switch (msg.what) {
			case Constants.FAILURE:
				mStrangerCardFragment.setText("很遗憾，获取列表失败！");
				break;
			case 2222:
				mStrangerCardFragment.setText("点击刷新");
				mStrangerCardFragment.getSwipeFlingAdapterView().setVisibility(View.VISIBLE);
				String data = msg.obj.toString();
				try {
					org.json.JSONArray array = new org.json.JSONArray(data);
					ArrayList<NearbyStrangerInfo> strangerData = (ArrayList<NearbyStrangerInfo>) JsonUtils.json2BeanArray(data,
							NearbyStrangerInfo.class);
					if (strangerData.size() <= 0) {
						return;
					}
					mStrangerCardFragment.flipDataList.addAll(strangerData);
					DWUtils.sortList(mStrangerCardFragment.flipDataList);
					if (mStrangerListFragment.isPullToRefresh) {
						mStrangerCardFragment.flipDataList.add(0, new NearbyStrangerInfo());
						try {
							mStrangerCardFragment.getSwipeFlingAdapterView().getTopCardListener().selectRight();
						} catch (NullPointerException e) {
							LogUtils.e(TAG, "空指针异常  " + e);
						}
						mStrangerListFragment.isPullToRefresh = false;
					}
					notifyDataSetChange();
				} catch (JSONException e) {
					LogUtils.e(TAG, "解析异常  " + e);
				}
				break;
			case 3333:
				mStrangerCardFragment.setText("亲，已到最后了哦");
				mStrangerCardFragment.isNeedToRequest = false;
				break;
			}
			mStrangerCardFragment.ifTvRefreshShow();
		}
	};

	public void notifyDataSetChange() {
		mStrangerCardFragment.cardAdapter.notifyDataSetChanged();
		mStrangerListFragment.nearStrangerAdapter.notifyDataSetChanged();
	}

	@AfterViews
	public void init() {
		EventBus.getDefault().register(this);
		fm = getChildFragmentManager();
		initData();
		startAnimation();
	}

	private void initData() {
		ivStrangerMessage.setOnClickListener(this);
		ivSmallPic.setOnClickListener(this);
		ivLike.setOnClickListener(this);
		ivLike.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				int height = ivLike.getHeight();
				mRadius = height / 2;
				LogUtils.d(TAG, "onGlobalLayout mRadius " + mRadius);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, 0, 0, mRadius);
				mStrangerCardFragment.getSwipeFlingAdapterView().setLayoutParams(lp);
				ivLike.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		ivBigPic.setOnClickListener(this);
		ivSelect.setOnClickListener(this);
		if (null == mStrangerCardFragment) {
			mStrangerCardFragment = new StrangerCardFragment_();
		}
		mStrangerCardFragment.setOnGetNearStrangerListener(new OnGetNearStrangerInfoListener() {

			@Override
			public void onNotifyDataSetChange() {
				notifyDataSetChange();
			}

			@Override
			public void onLike(NearbyStrangerInfo nearbyStrangerInfo) {
				mStrangerListFragment.nearbyStrangerInfo = nearbyStrangerInfo;
				like(nearbyStrangerInfo.getDwID());
			}

			@Override
			public void onGetNearStrangerInfo(int tag) {
				if (tag == mStrangerCardFragment.GET_NEAR_STRANGER) {
					checkLocation();
				} else if (tag == mStrangerCardFragment.CLEAR_DATA) {
					clearData();
				}
			}
		});
		if (null == mStrangerListFragment) {
			mStrangerListFragment = new StrangerListFragment_();
		}
		mStrangerListFragment.setOnGetNearStrangerListener(new StrangerListFragment.OnGetNearStrangerInfoListener() {

			@Override
			public void onGetNearStrangerInfo() {
				checkLocation();
			}

			@Override
			public void onLike(String dwID) {
				like(dwID);
			}

			@Override
			public void onDisLike(int position) {
				if (0 == position) {
					mStrangerCardFragment.getSwipeFlingAdapterView().getTopCardListener().selectLeft();
				} else {
					mStrangerCardFragment.flipDataList.remove(position);
				}
				notifyDataSetChange();
			}

			@Override
			public void onClearData() {
				clearData();
			}
		});
		if (null == mStrangerMessageFragment) {
			mStrangerMessageFragment = new StrangerMessageFragment_();
		}
		getFragmentManager().beginTransaction().add(R.id.fl_stranger_container, mStrangerMessageFragment, "smf")
				.add(R.id.fl_stranger_container, mStrangerCardFragment, "scf")
				.add(R.id.fl_stranger_container, mStrangerListFragment, "slf").hide(mStrangerMessageFragment)
				.show(mStrangerListFragment).show(mStrangerCardFragment).commit();
		hideStrangerMessage();
	}

	public void like(String likedID) {
		// 网络数据
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 6000:
					SoundPoolUtils.play();// 匹配成功声音
					VibratorUtil.Vibrate(getActivity(), 300); // 震动300ms
					MatchSuccessDialogFragment matchSuccessDialogFragment = new MatchSuccessDialogFragment();
					matchSuccessDialogFragment.setJsonData(msg.obj.toString());
					matchSuccessDialogFragment.setTips("早就喜欢你，恭喜你得到他的一块大洋！现在去聊聊？");
					matchSuccessDialogFragment.setOnMatchClickListener(StrangerFragment.this);
					matchSuccessDialogFragment.show(getActivity().getSupportFragmentManager().beginTransaction(),
							"matchSuccessDialogFragment");
					break;
				case 3333:
					break;
				default:
					if (null != mStrangerListFragment.nearbyStrangerInfo) {
						mStrangerListFragment.nearbyStrangerInfo.setLiked("1");
						mStrangerListFragment.nearStrangerAdapter.notifyDataSetChanged();
					}
					break;
				}
			}
		};
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", DecentWorldApp.getInstance().getDwID());
		map.put("likedID", likedID);
		getStrangerInfo.likeStranger(map, handler);
	}

	@Override
	public void onMatchClick(View view) {
		switch (view.getId()) {
		case R.id.tv_talk:
			LikeBean likeBean = (LikeBean) view.getTag();
			Intent intent = new Intent(getActivity(), ChatActivity_.class);
			LaunchChatEntity entity = new LaunchChatEntity(likeBean.id, likeBean.showName, Float.valueOf(likeBean.worth),
					ChatType.SINGLE.getIndex(), ChatRelationship.FRIEND.getIndex(), Integer.valueOf(likeBean.userType));
			if (!ContactUserDao.isContact(likeBean.id))
				entity.setChatRelationship(ChatRelationship.STRANGER.getIndex());
			intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);
			startActivity(intent);
			break;
		case R.id.tv_cancel:
			if (null != mStrangerListFragment.nearbyStrangerInfo) {
				mStrangerListFragment.nearbyStrangerInfo.setLiked("1");
				mStrangerListFragment.nearStrangerAdapter.notifyDataSetChanged();
			}
			break;
		}
	}

	public void clearData() {
		if (null != mStrangerCardFragment.flipDataList) {
			mStrangerCardFragment.page = 0;
			mStrangerCardFragment.ifClearData = true;
			mStrangerCardFragment.flipDataList.clear();
			notifyDataSetChange();
		}
	}

	private void getNearStrangerInfo() {
		if (null == getStrangerInfo) {
			isRequesting = false;
			return;
		}
		ChoiceInfo info = ChoiceInfo.queryByDwID(DecentWorldApp.MAIN_KEY);
		if (null == info) {
			info = new ChoiceInfo();
			info.save();
		}
		if ("16".equals(info.minAge) && "16".equals(info.maxAge)) {
			info.minAge = "0";
			info.maxAge = "16";
		}
		if ("200".equals(info.scope)) {
			info.scope = "10000000";
		}
		String condition = "{\"sex\":" + info.sex + "," + "\"minAge\":" + info.minAge + "," + "\"maxAge\":" + info.maxAge + ","
				+ "\"minWorth\":" + info.minWorth + "," + "\"maxWorth\":" + info.maxWorth + "," + "\"scope\":" + info.scope + ","
				+ "\"school\":" + "\"" + info.school + "\"" + "," + "\"occupation\":" + "\"" + info.occupation + "\"" + "}";
		LogUtils.d(TAG, "getNearStrangerInfo condition " + condition);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", DecentWorldApp.getInstance().getDwID());
		map.put("searchCondition", condition);
		// 此处需要重新定位，不能直接用缓存的，因为用户的位置可能已经改变
		map.put("ln", String.valueOf(LocationProvider.longitude));
		map.put("lt", String.valueOf(LocationProvider.latitude));
		LogUtils.d(TAG, "getNearStrangerInfo---ln---" + LocationProvider.longitude + "---lt---" + LocationProvider.latitude);
		map.put("page", String.valueOf(mStrangerCardFragment.page));
		getStrangerInfo.getNearStrangerList(map, mGetNearStrangerInfoHandler);
	}

	@Override
	protected void lazyLoad() {
		LogUtils.d(TAG, "lazyLoad");
		if (isRequesting) {
			return;
		}
		clearData();
		checkLocation();
	}

	@Override
	public void turnToTab(int tab) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_stranger_message:
			fm.beginTransaction().hide(mStrangerCardFragment).hide(mStrangerListFragment).show(mStrangerMessageFragment).commit();
			ivBigPic.setVisibility(View.VISIBLE);
			ivSmallPic.setVisibility(View.VISIBLE);
			ivStrangerMessage.setVisibility(View.GONE);
			ivLike.setVisibility(View.GONE);
			ivSelect.setVisibility(View.GONE);
			break;
		case R.id.iv_small_pic:
			mStrangerListFragment.relLv.setVisibility(View.VISIBLE);
			getActivity().getSupportFragmentManager().beginTransaction().hide(mStrangerCardFragment)
					.hide(mStrangerMessageFragment).show(mStrangerListFragment).commit();
			ivBigPic.setVisibility(View.VISIBLE);
			ivSmallPic.setVisibility(View.GONE);
			ivStrangerMessage.setVisibility(View.VISIBLE);
			ivLike.setVisibility(View.GONE);
			ivSelect.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_big_pic:
			fm.beginTransaction().hide(mStrangerMessageFragment).hide(mStrangerListFragment).show(mStrangerCardFragment).commit();
			hideStrangerMessage();
			break;
		case R.id.iv_like:
			if (mStrangerCardFragment.flipDataList.size() <= 0) {
				ToastUtil.showToast("没有搜索到附近的人哦！");
				return;
			}
			if (FileUtils.getPromptStatus(getActivity(), Constants.LIKE) == CommomPromptDialogFragment.LIKE) {
				mStrangerListFragment.nearbyStrangerInfo = mStrangerCardFragment.flipDataList.get(0);
				if ("1".equals(mStrangerListFragment.nearbyStrangerInfo.getLiked())) {
					ToastUtil.showToast("您已经点过赞");
					return;
				}
				like(mStrangerListFragment.nearbyStrangerInfo.getDwID());
				mStrangerCardFragment.getSwipeFlingAdapterView().getTopCardListener().selectRight();
				return;
			}
			mCommomPromptDialog = new CommomPromptDialogFragment();
			mCommomPromptDialog.setEnter(CommomPromptDialogFragment.LIKE);
			mCommomPromptDialog.setOnCommomPromptListener(this);
			mCommomPromptDialog.setTips("原来你喜欢他，你的一块大洋，将飞到他身边；他同样喜欢你的时候，将得到你的大洋，并建立匹配对话框。有那么巧？呵呵，让大洋先飞一会吧。大洋探路，实在不行，三天飞回身家！");
			mCommomPromptDialog.show(this.getActivity().getSupportFragmentManager(), "mCommomPromptDialog");
			break;
		case R.id.iv_select:
			startActivityForResult(new Intent(getActivity(), SelectedActivity_.class), Constants.REQUEST_CODE);
			break;
		}
	}

	private AnimationDrawable mAnimationDrawable;

	private void startAnimation() {
		if (mAnimationDrawable == null) {
			mAnimationDrawable = (AnimationDrawable) ivLike.getDrawable();
		}
		mAnimationDrawable.start();
	}

	private void hideStrangerMessage() {
		ivBigPic.setVisibility(View.GONE);
		ivSmallPic.setVisibility(View.VISIBLE);
		ivStrangerMessage.setVisibility(View.VISIBLE);
		ivLike.setVisibility(View.VISIBLE);
		ivSelect.setVisibility(View.VISIBLE);
	}

	@Override
	public void onCommomPromtClick(View view) {
		like(mStrangerCardFragment.flipDataList.get(0).getDwID());
		mStrangerCardFragment.getSwipeFlingAdapterView().getTopCardListener().selectRight();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mStrangerCardFragment.page = 0;
		mStrangerListFragment.isPullToRefresh = true;
		mStrangerCardFragment.flipDataList.clear();
		checkLocation();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

	public void checkLocation() {
		LogUtils.d(TAG, "longitude " + LocationProvider.longitude + "latitude " + LocationProvider.latitude);
		isRequesting = true;
		if (LocationProvider.longitude <= 0.0 || LocationProvider.latitude <= 0.0) {
			if (isToLocation) {
				if (null == mEnsureCancelDialog) {
					mEnsureCancelDialog = new EnsureCancelDialog();
					mEnsureCancelDialog.setOnEnsureCancelClickListener(this);
				}
				mEnsureCancelDialog.show(this.getActivity().getSupportFragmentManager(), "EnsureCancelDialog");
			} else {
				getNearStrangerInfo();
			}
		} else {
			getNearStrangerInfo();
		}
	}

	LocationProvider provider;
	private boolean isToLocation;

	@Override
	public void OnEnsureCancelClick(View view) {
		mEnsureCancelDialog.dismiss();
		switch (view.getId()) {
		case R.id.tv_cancel:
			isToLocation = false;
			getNearStrangerInfo();
			break;
		case R.id.tv_ensure:
			isToLocation = true;
			getCurrentLocation();
			break;
		}
	}

	private void getCurrentLocation() {
		if (null == provider) {
			provider = new LocationProvider(getActivity());
		}
		showProgressDialog();
		provider.setNotify("SCHEDULE_LOCATION_2", new NotifyCallback() {
			@Override
			public void execute(AMapLocation location) {
				hideProgressDialog();
				LogUtils.d(TAG, "SCHEDULE_LOCATION_2 " + location.getLongitude());
				if (location == null || location.getLatitude() == 0.0 || location.getLongitude() == 0.0) {
					isRequesting = false;
					ToastUtil.showToast("获取地理位置失败");
					LogUtils.d(TAG, "---获取位置失败---");
					return;
				}
				ivLike.setImageResource(R.drawable.ic_launcher);
				LocationProvider.latitude = location.getLatitude();
				LocationProvider.longitude = location.getLongitude();
				getNearStrangerInfo();
				LogUtils.d(
						TAG,
						"获取到定位信息" + "\n" + "locationAddress=" + location.getAddress() + "\n" + "laititude="
								+ location.getLatitude() + "\nlongtitude=" + location.getLongitude() + "\n");
				provider.stop();
			}
		});
		provider.startLocation(true);
	}

	/**
	 * 接收定位失败推送
	 */
	@Subscriber(tag = NotifyByEventBus.NT_LOCATION_FAILURE)
	public void receiveLocationFailure(String data) {
		isRequesting = false;
		hideProgressDialog();
		ToastUtil.showToast("定位失败," + data);
		getNearStrangerInfo();
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(getActivity(), null, "正在获取地理位置。。。");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		if (null != mProDialog) {
			mProDialog.hide();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
