package cn.sx.decentworld.fragment.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatRoomBuildActivity_;
import cn.sx.decentworld.activity.ChatRoomMeActivity_;
import cn.sx.decentworld.activity.TopicContentActivity_;
import cn.sx.decentworld.adapter.ChatRoomListAdapter2;
import cn.sx.decentworld.bean.ChatRoomInfo;
import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.engine.ChatGroupEngine;
import cn.sx.decentworld.engine.ChatGroupEngine.GetRoomListListener;
import cn.sx.decentworld.entity.SelfUserInfo;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.utils.ToastUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: NewsFragment
 * @Description: 主界面聊天NewsFragment
 * @author cj
 * @date 2015年6月29日12:34:03
 */
@EFragment(R.layout.main_layout_news)
public class NewsFragment extends BaseFragment implements OnTouchListener, GetRoomListListener, OnClickListener {
	/**
	 * 常量
	 */
	private static String TAG = "NewsFragment";
	private List<ChatRoomInfo> mChatroomListDatas;
	public static ChatRoomListAdapter2 mChatroomAdapter;
	/**
	 * 添加聊天室
	 */
	@ViewById(R.id.add_topic_detail)
	ImageView add_topic_detail;
	@ViewById(R.id.iv_me)
	ImageView mIvMe;
	@ViewById(R.id.tv_header_title)
	TextView mTvTitle;
	// 根部局
	@ViewById(R.id.ll_layout_news_root)
	LinearLayout ll_layout_news_root;
	// 聊天室列表
	@ViewById(R.id.main_layout_news_lv)
	PullToRefreshListView mLvList;
	// 聊天室标题
	@ViewById(R.id.rel_top_title_root)
	RelativeLayout chat_room_title_root;
	private boolean hasGetChatroomList;// true已经有请求过一次 false 一次都没有请求过
	// 屏幕参数
	private int screenWidth = 0;
	private int screenHeight = 0;
	private int startX = 0;
	private int startY = 0;
	private boolean isClicked = false;

	/**
	 * 初始化
	 */
	@AfterViews
	public void init() {
		LogUtils.v(TAG, "init() ");
		getDisplayParam();
		initListView();
		isPrepared = true;// 标记为已经准备好
	}

	/**
	 * 获取屏幕参数
	 */
	private void getDisplayParam() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels - 50;
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		mTvTitle.setText("聊天室");
		mIvMe.setOnClickListener(this);
		// mIvMe.setOnTouchListener(this);
		mChatroomListDatas = new ArrayList<ChatRoomInfo>();
		mChatroomAdapter = new ChatRoomListAdapter2(getActivity(), mChatroomListDatas);
		// View v = LayoutInflater.from(getActivity()).inflate(
		// R.layout.header_chat_room_list, null);
		// main_layout_news_lv.getRefreshableView().addHeaderView(v);
		mLvList.setAdapter(mChatroomAdapter);
		mLvList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("~刷新聊天室列表~");
				ChatGroupEngine.getInstance().getRoomList(NewsFragment.this);
			}
		});
		mLvList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				LogUtils.d(TAG, Constants.TEST_TAG + "initListView() setOnItemClickListener");
				SelfUserInfo selfUserInfo = SelfInfoManager.getInstance().getSelfUserInfo();
				String nickName = "默认";
				if (selfUserInfo != null) {
					nickName = selfUserInfo.getFieldValue(Field.NICKNAME);
				}
				// UserInfo info = UserInfoEngine.getInstance().getUserInfo();
				position = position - 1;
				if (position == -1) {
					return;
				}
				Intent intent = new Intent(getActivity(), TopicContentActivity_.class);
				ChatRoomInfo item = mChatroomAdapter.getItem(position);
				intent.putExtra("roomID", item.getRoomID());
				intent.putExtra("nickName", nickName);
				intent.putExtra(ConstantIntent.SELF_INTRODUCE, item.getOwnerIntroduction());
				startActivity(intent);
			}
		});

		mLvList.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 静止状态
					LogUtils.v(TAG, "静止状态");
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					// 滚动状态
					LogUtils.v(TAG, "滚动状态");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					// 手指触摸后滚动
					LogUtils.v(TAG, "手指触摸后滚动");
					break;
				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					int itemNum = firstVisibleItem - 1;
					chat_room_title_root.setVisibility(View.VISIBLE);
					ChatRoomInfo item = mChatroomAdapter.getItem(itemNum);
					((TextView) chat_room_title_root.findViewById(R.id.tv_header_title)).setText(item.getSubjectName());
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_me:
			Intent intent = new Intent(getActivity(), ChatRoomMeActivity_.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	/**
	 * 添加聊天室
	 */
	@Click(R.id.add_topic_detail)
	public void todetail() {
		Intent intent = new Intent(getActivity(), ChatRoomBuildActivity_.class);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtils.v(TAG, "onResume() ");
		if (hasGetChatroomList) {
			ChatGroupEngine.getInstance().getRoomList(this);
		}
	}

	/**
	 * 跳到指定的选项卡
	 */
	@Override
	public void turnToTab(int tab) {

	}

	/**
	 * 每次ChatFragment出现的时候都会调用
	 */
	@Override
	protected void lazyLoad() {
		LogUtils.v(TAG, "lazyload");
		// 从网络获取聊天室列表
		ChatGroupEngine.getInstance().getRoomList(this);
		hasGetChatroomList = true;
	}

	/**
	 * 图标的触摸事件
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			LogUtils.i(TAG, "ACTION_DOWN");
			isClicked = false;
			startX = (int) event.getRawX();
			startY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			LogUtils.i(TAG, "ACTION_MOVE");
			isClicked = true;
			int dx = (int) event.getRawX() - startX;
			int dy = (int) event.getRawY() - startY;
			int left = v.getLeft() + dx;
			int right = v.getRight() + dx;
			int top = v.getTop() + dy;
			int bottom = v.getBottom() + dy;

			if (left < 0) {
				left = 0;
				right = left + v.getWidth();
			}

			if (right > screenWidth) {
				right = screenWidth;
				left = right - v.getWidth();
			}

			if (top < 0) {
				top = 0;
				bottom = top + v.getHeight();
			}

			if (bottom > screenHeight) {
				bottom = screenHeight;
				top = bottom - v.getHeight();
			}
			v.layout(left, top, right, bottom);
			startX = (int) event.getRawX();
			startY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			LogUtils.i(TAG, "ACTION_UP");
			break;
		default:
			break;
		}
		LogUtils.i(TAG, "isClicked=" + isClicked);
		return isClicked;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	// /////////////////////////////////////////获取聊天室列表///////////////////////////////////
	/**
	 * 获取聊天室成功
	 */
	@Override
	public void onSuccess(List<ChatRoomInfo> list) {
		mChatroomListDatas.clear();
		mChatroomListDatas.addAll(list);
		Collections.sort(mChatroomListDatas);// 排序
		mChatroomAdapter.notifyDataSetChanged();
		mLvList.onRefreshComplete();
	}

	/**
	 * 获取聊天室失败
	 */
	@Override
	public void onFailure(String cause) {
		ToastUtil.showToast(cause);
		mLvList.onRefreshComplete();
	}
}
