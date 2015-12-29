package cn.sx.decentworld.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
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
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.component.ui.ChatRoomComponent;
import cn.sx.decentworld.network.request.GetRoomInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
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
public class NewsFragment extends BaseFragment {
	private static String TAG = "NewsFragment";
	public static final int CHAT_ROOM_LIST = 1;
	private List<ChatRoomInfo> chatRoomInfos;
	public static ChatRoomListAdapter2 adapter;
	@Bean
	ChatRoomComponent chatRoomComponent;
	/**
	 * 添加聊天室
	 */
	@ViewById(R.id.add_topic_detail)
	ImageView add_topic_detail;
	@ViewById(R.id.iv_me)
	ImageView ivMe;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	/**
	 * 根部局
	 */
	@ViewById(R.id.ll_layout_news_root)
	LinearLayout ll_layout_news_root;
	/**
	 * 聊天室列表
	 */
	@ViewById(R.id.main_layout_news_lv)
	PullToRefreshListView main_layout_news_lv;

	/**
	 * 获取聊天室信息的网络请求代码集
	 */
	@Bean
	GetRoomInfo getRoomInfo;

	/**
	 * 聊天室标题
	 */
	@ViewById(R.id.chat_room_title_root)
	RelativeLayout chat_room_title_root;

	/**
	 * 初始化
	 */
	@AfterViews
	public void init() {
		LogUtils.i(TAG, "init");
		initListView();
		isPrepared = true;// 标记为已经准备好
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		tvTitle.setText("聊天室");
		ivMe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						ChatRoomMeActivity_.class);
				startActivity(intent);
			}
		});
		chatRoomInfos = new ArrayList<ChatRoomInfo>();
		adapter = new ChatRoomListAdapter2(getActivity(), chatRoomInfos);
		// View v = LayoutInflater.from(getActivity()).inflate(
		// R.layout.header_chat_room_list, null);
		// main_layout_news_lv.getRefreshableView().addHeaderView(v);
		main_layout_news_lv.setAdapter(adapter);
		main_layout_news_lv
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel("~刷新聊天室列表~");
						getRoomInfo.getRoomList(mHandler, CHAT_ROOM_LIST);
					}
				});
		main_layout_news_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				UserInfo info = UserInfoManager.getUserInfoInstance();
				position = position - 1;
				
				if (position == -1) {
					return;
				}
				
				Intent intent = new Intent(getActivity(),
						TopicContentActivity_.class);
				ChatRoomInfo item = adapter.getItem(position);
				intent.putExtra("roomID", item.getRoomID());
				if (info != null) {
					intent.putExtra("nickName", info.getNickName());
				} else {
					intent.putExtra("nickName", "默认");
				}
				startActivity(intent);
			}
		});

		main_layout_news_lv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 静止状态
					LogUtils.i(TAG, "静止状态");
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					// 滚动状态
					LogUtils.i(TAG, "滚动状态");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					// 手指触摸后滚动
					LogUtils.i(TAG, "手指触摸后滚动");
					break;
				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// LogUtils.i(TAG, "totalItemCount=" + totalItemCount
				// + ",firstVisibleItem=" + firstVisibleItem
				// + ",visibleItemCount=" + visibleItemCount);

				if (firstVisibleItem >= 1) {
					int itemNum = firstVisibleItem - 1;
					chat_room_title_root.setVisibility(View.VISIBLE);
					ChatRoomInfo item = adapter.getItem(itemNum);
					((TextView) chat_room_title_root
							.findViewById(R.id.tv_header_title)).setText(item
							.getSubjectName());
				}
			}
		});

	}

	/**
	 * 网络请求聊天室的列表，返回值的处理
	 */
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHAT_ROOM_LIST:
				String json = msg.obj.toString();
				List<ChatRoomInfo> chatRoomInfo = (List<ChatRoomInfo>) JsonUtils
						.json2BeanArray(json, ChatRoomInfo.class);
				chatRoomInfos.clear();
				chatRoomInfos.addAll(chatRoomInfo);
				Collections.sort(chatRoomInfos);// 排序
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			main_layout_news_lv.onRefreshComplete();
		};
	};

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
		LogUtils.i(TAG, "lazyload");
		// 从网络获取聊天室列表
		getRoomInfo.getRoomList(mHandler, CHAT_ROOM_LIST);
	}

}
