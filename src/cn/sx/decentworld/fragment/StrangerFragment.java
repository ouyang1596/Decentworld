package cn.sx.decentworld.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.MainActivity;
import cn.sx.decentworld.activity.NearCardDetailActivity;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.activity.SelectedActivity_;
import cn.sx.decentworld.adapter.ConversationListAdapter2;
import cn.sx.decentworld.adapter.NearStrangerListAdapter;
import cn.sx.decentworld.bean.ChoiceInfo;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NearbyStrangerInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.StrangerInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.MainFragmentComponent;
import cn.sx.decentworld.flingswipe.SwipeFlingAdapterView;
import cn.sx.decentworld.manager.CommHttp;
import cn.sx.decentworld.manager.ConversationManager;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.swipecards.CardAdapter;
import cn.sx.decentworld.utils.AnonymityNickNameCreator;
import cn.sx.decentworld.utils.DWUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.ScrollLayout;

import com.activeandroid.query.Select;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: StrangerFragment
 * @Description: 主界面聊天StrangerFragment
 * @author yj
 * @date 2015年6月29日12:34:03
 */
@EFragment(R.layout.main_layout_stranger)
public class StrangerFragment extends BaseFragment implements OnClickListener {
	public static final String TAG = "StrangerFragment";
	@Bean
	MainFragmentComponent mainFragmentComponent;
	@Bean
	ToastComponent toast;
	@ViewById(R.id.root_main_layout_stranger)
	RelativeLayout relStranger;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.root_ll_stranger_near)
	LinearLayout llStrangerNear;
	@ViewById(R.id.tv_refresh)
	TextView tvRefresh;
	private int index = 0;// 消息、附近的人
	private int index_tab = 0;// 附近的人 （列表、卡片）
	// 包含陌生人信息和附近的人
	@ViewById(R.id.stranger_scrollLayout)
	ScrollLayout stranger_scrollLayout;
	// 附近的人的展示形式（列表、卡片）
	@ViewById(R.id.near_stranger_scrollLayout)
	ScrollLayout near_stranger_scrollLayout;
	// 附近的人的列表
	@ViewById(R.id.fragment_stranger_chat_lv)
	ListView lvStrangerInfo;
	@ViewById(R.id.lv_near_stranger)
	PullToRefreshListView lvNearStranger;
	// 切换图标
	@ViewById(R.id.fragment_stranger_tab)
	ImageView fragment_stranger_tab;
	@ViewById(R.id.tv_select)
	TextView tvSelect;
	// 定义附近的人的变量
	@ViewById(R.id.root_ll_stranger_near)
	LinearLayout ll_stranger_near_root;
	// 附近的人
	@ViewById(R.id.fragment_stranger_near_lv)
	public SwipeFlingAdapterView flingContainer;
	private CardAdapter cardAdapter;
	private ArrayList<NearbyStrangerInfo> flipDataList = new ArrayList<NearbyStrangerInfo>();
	private String dwID;
	private int page = 0;
	private int count = 8;
	private boolean isNeedToRequest = true;
	private List<ConversationList> strangerConversationList;
	public static Map<String, ConversationList> strangerConversationMap;

	@Bean
	GetStrangerInfo getStrangerInfo;
	NearStrangerListAdapter nearStrangerAdapter;
	private String wealth;

	@AfterViews
	public void init() {
		tvTitle.setText("陌生人消息");
		initStrangerConversationList();
		lvStrangerInfo.setAdapter(strangerRecorderAdapter);
		lvStrangerInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ConversationList info = strangerRecorderAdapter
						.getItem(position);
				Intent intent = new Intent(getActivity(), ChatActivity_.class);
				intent.putExtra("user_dwID", info.getDwID());
				intent.putExtra("user_nickname", info.getTitle());
				intent.putExtra("chatType", info.getChatType());
				intent.putExtra("chatRelationship", info.getChatRelationship());
				intent.putExtra("user_worth", info.getWorth());
				startActivity(intent);
			}
		});
		tvRefresh.setOnClickListener(this);
		EventBus.getDefault().register(this);
		initData();
		setAdapter();
		initFlingAdapterView();
	}

	private void initFlingAdapterView() {
		if (null == cardAdapter) {
			cardAdapter = new CardAdapter(getActivity(), this, flipDataList);
			flingContainer.setAdapter(cardAdapter);
			// 设置卡片的可见张数,经过试验，不能将其设置成一张notifydataSetChange会失效
			flingContainer.setMaxVisible(2);
			flingContainer
					.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
						@Override
						public void onItemClicked(int itemPosition,
								Object dataObject) {
							NearbyStrangerInfo info = cardAdapter
									.getItem(itemPosition);
							Intent intent = new Intent(getActivity(),
									NearCardDetailActivity_.class);
							intent.putExtra(Constants.DW_ID, info.getDwID());
							intent.putExtra(POSITION, itemPosition);
							intent.putExtra("location", info.getDistance());
							startActivityForResult(intent,
									Constants.REQUEST_CODE);
						}
					});
			// 滑动监听
			flingContainer
					.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
						@Override
						public void removeFirstObjectInAdapter() {
							if (flipDataList.size() > 0) {
								flipDataList.remove(0);
								notifyDataSetChange();
							}
						}

						@Override
						public void onLeftCardExit(Object dataObject) {
							LogUtils.i(TAG, "嫌弃");
						}

						@Override
						public void onRightCardExit(Object dataObject) {
							LogUtils.i(TAG, "喜欢");
						}

						@Override
						public void onAdapterAboutToEmpty(int itemsInAdapter) {
							if (flipDataList.size() >= 1) {
								return;
							}
							LogUtils.e("bm", "page--" + page + "flipDataList--"
									+ flipDataList);
							flingContainer.setVisibility(View.GONE);
							if (isVisible) {
								if (isNeedToRequest) {
									page++;
									getNearStrangerInfo();
								}
							}
						}

						// 滚动的时候
						@SuppressLint("NewApi")
						@Override
						public void onScroll(float scrollProgressPercent) {
						}
					});
		}
	}

	@Bean
	GetUserInfo getUserInfo;
	ConversationListAdapter2 strangerRecorderAdapter;

	public void initStrangerConversationList() {
		if (strangerConversationList == null) {
			strangerConversationList = new LinkedList<ConversationList>();
		}
		if (strangerConversationMap == null) {
			strangerConversationMap = new ConcurrentHashMap<String, ConversationList>();
		}
		strangerConversationList.clear();
		strangerConversationMap.clear();
		// 获取陌生人会话列表
		List<ConversationList> convList = ConversationManager
				.getStrangerConversationList();
		// 添加到内存中
		for (ConversationList conv : convList) {
			strangerConversationList.add(conv);
			// 根据dwID和聊天类型组成一个键
			strangerConversationMap.put(conv.getDwID() + conv.getChatType(),
					conv);
		}
		if (strangerRecorderAdapter == null) {
			strangerRecorderAdapter = new ConversationListAdapter2(
					getActivity(), strangerConversationList);
		} else {
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 接收通知
	 * 
	 * @param tag
	 */
	@Subscriber(tag = NotifyByEventBus.NT_INIT_STRANGER_CONVERSATION)
	public void initStrangerConversationList(String tag) {
		LogUtils.i(TAG, tag);
		initStrangerConversationList();
	}

	/**
	 * 更新会话列表
	 * 
	 * @param msg
	 */
	@Subscriber(tag = NotifyByEventBus.NT_NOTIFY_STRANGER_UPDATA)
	public void updateStrangerList(DWMessage msg) {
		LogUtils.i(TAG, "收到通知【更新会话列表】");
		String userID = DecentWorldApp.getInstance().getDwID();
		// 更新会话列表
		String dwID = null;// 对方ID
		String from = msg.getFrom();
		if (from != null && from.equals(userID)) {
			// 为发送出去的消息
			dwID = msg.getTo();
		} else {
			dwID = from;
		}

		int chatType = msg.getChatType();
		ConversationList conversation = strangerConversationMap.get(dwID
				+ chatType);
		if (conversation == null) {
			// 获取对方的昵称和身价
			new CommHttp(getActivity()).getNicknameAndWorth(dwID, new MyHander(
					msg, dwID), GET_NICK_AND_WORTH);
		} else {
			addConversation(msg, dwID, "", "");
		}
	}

	private static final int GET_NICK_AND_WORTH = 1;

	class MyHander extends Handler {
		private DWMessage dwMessage;
		private String dwID;

		public MyHander(DWMessage dwMessage, String dwID) {
			this.dwMessage = dwMessage;
			this.dwID = dwID;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_NICK_AND_WORTH:
				JSONObject result = JSON.parseObject(msg.obj.toString());
				String nick = result.getString("nick");
				String worth = result.getString("worth");
				LogUtils.i(TAG, "会话列表为空，从网络获取对方的nick=" + nick + ",worth="
						+ worth);
				addConversation(dwMessage, dwID, nick, worth);
				break;
			default:
				break;
			}
		}
	}

	public void addConversation(DWMessage msg, String dwID, String nick,
			String worth) {

		String userID = DecentWorldApp.getInstance().getDwID();
		String from = msg.getFrom();
		int chatType = msg.getChatType();
		ConversationList conversation = strangerConversationMap.get(dwID
				+ chatType);

		// 新的列表
		if (conversation == null) {
			String conversation_icon = null;
			String title = null;
			if (chatType == DWMessage.CHAT_TYPE_SINGLE) {
				conversation_icon = ImageUtils.getIconByDwID(dwID,
						ImageUtils.ICON_SMALL);
				title = nick;// 设置名字
			} else if (chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
				conversation_icon = "";
				if (from != null && !from.equals(userID)) {
					conversation_icon = "";
					title = AnonymityNickNameCreator.getRandomName() + "[匿名聊天]";
				} else {
					conversation_icon = ImageUtils.getIconByDwID(dwID,
							ImageUtils.ICON_SMALL);
					title = nick;
				}
			}
			int msgType = msg.getMessageType();
			String body = "";
			if (msgType == DWMessage.TXT) {
				body = msg.getBody();
			}
			// 查询数据库，确保数据库中也没有数据的情况下才new
			ConversationList temp = ConversationList.queryByDwID(dwID,
					chatType, DWMessage.CHAT_RELATIONSHIP_STRANGER);
			if (temp != null) {
				conversation = temp;
			} else {
				if (from != null && from.equals(userID)) {
					conversation = new ConversationList(userID, dwID,
							conversation_icon, title, body,
							String.valueOf(System.currentTimeMillis()), 1, 0,
							1, 1);
				} else {
					conversation = new ConversationList(userID, dwID,
							conversation_icon, title, body,
							String.valueOf(System.currentTimeMillis()), 1, 1,
							1, 1);
				}
			}

			conversation.setMessageType(msgType);
			conversation.setChatType(chatType);
			conversation
					.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
			conversation.setWorth(worth);
			strangerConversationMap.put(dwID + chatType, conversation);
			strangerConversationList.add(conversation);
			conversation.save();
		} else {
			conversation.receiveMessage(msg);
		}

		// 修改数据库
		String sql = "userID = ? and dwID=? and chatType=?";
		ConversationList conversationList = new Select()
				.from(ConversationList.class)
				.where(sql, DecentWorldApp.getInstance().getDwID(), dwID,
						chatType).executeSingle();
		if (conversationList != null) {
			conversationList.setTime(msg.getTime());
			conversationList.setMessageType(msg.getMessageType());
			if (msg.getMessageType() == DWMessage.TXT) {
				conversationList.setContent(msg.getBody());
			}
			conversationList.setCount(conversationList.getCount() + 1);
			if (!msg.getFrom().equals(dwID)) {
				conversationList.setUnReadCount(conversationList
						.getUnReadCount() + 1);
			}
			conversationList.save();
			LogUtils.i(TAG, "保存数据库完成");
		}
		// 排序
		Collections.sort(strangerConversationList);
		strangerRecorderAdapter.notifyDataSetChanged();
		// 从会话列表中计算未读消息总数
		int count = calcuUnreadMsgCount();
	}

	public static ConversationList getStrangerConversation(String key) {
		return strangerConversationMap.get(key);
	}

	private int calcuUnreadMsgCount() {
		if (strangerConversationList == null) {
			return 0;
		}
		int count = 0;
		for (ConversationList conv : strangerConversationList) {
			count += conv.getUnReadCount();
		}
		return count;
	}

	private void notifyDataSetChange() {
		if (null != nearStrangerAdapter) {
			nearStrangerAdapter.notifyDataSetChanged();
		}
		if (null != cardAdapter) {
			cardAdapter.notifyDataSetChanged();
		}
	};

	private static String POSITION = "position";

	private void setAdapter() {
		nearStrangerAdapter = new NearStrangerListAdapter(getActivity(),
				flipDataList);
		nearStrangerAdapter
				.setOnClickListener(new NearStrangerListAdapter.OnClickListener() {

					@Override
					public void onClick(View view) {
						Integer position = (Integer) view
								.getTag(Constants.ITEM_KEY);
						NearbyStrangerInfo item = nearStrangerAdapter
								.getItem(position);
						switch (view.getId()) {
						case R.id.iv_stranger_detail:
							Intent intent = new Intent(getActivity(),
									NearCardDetailActivity_.class);
							intent.putExtra(Constants.DW_ID, item.getDwID());
							intent.putExtra(POSITION, position);
							intent.putExtra(Constants.IF_LIKE, item.getIfLike());
							intent.putExtra("location", item.getDistance());
							startActivityForResult(intent,
									Constants.REQUEST_CODE);
							break;
						case R.id.iv_heart:
							item.setIfLike(true);
							like(item.getDwID());
							notifyDataSetChange();
							break;
						}
					}
				});
		lvNearStranger.setAdapter(nearStrangerAdapter);
		lvNearStranger.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				position = position - 1;
				NearbyStrangerInfo item = nearStrangerAdapter.getItem(position);
				Intent intent = new Intent(getActivity(), ChatActivity_.class);
				intent.putExtra("user_dwID", item.getDwID());
				intent.putExtra("user_nickname", item.getNickName());
				intent.putExtra("user_icon", item.getIcon());
				intent.putExtra("chatType", DWMessage.CHAT_TYPE_SINGLE);
				intent.putExtra("user_worth", item.getWorth());
				if (ContactUser.isContact(item.getDwID())) {
					// 朋友关系
					intent.putExtra("chatRelationship",
							DWMessage.CHAT_RELATIONSHIP_FRIEND);
				} else {
					// 陌生人关系
					intent.putExtra("chatRelationship",
							DWMessage.CHAT_RELATIONSHIP_STRANGER);
				}
				startActivity(intent);
				saveStrangerInfo(item);

			}
		});
		// lvNearStranger.setOnRefreshListener(new OnRefreshListener<ListView>()
		// {
		//
		// @Override
		// public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// page = 0;
		// getNearStrangerInfo();
		// }
		// });
		lvNearStranger.setMode(Mode.BOTH);
		lvNearStranger.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 0;
				clearData();
				getNearStrangerInfo();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page++;
				getNearStrangerInfo();
			}
		});
		isPrepared = true;
	}

	private void saveStrangerInfo(NearbyStrangerInfo item) {
		String dwID = item.getDwID();
		StrangerInfo info = StrangerInfo.queryByDwID(dwID);
		if (null == info) {
			info = new StrangerInfo();
		}
		info.strangerDwID = dwID;
		info.age = item.getAge();
		info.nickname = item.getNickName();
		info.location = "深圳南山";
		info.icon = item.getIcon();
		info.save();
	}

	private void initData() {
		tvSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(getActivity(),
						SelectedActivity_.class), Constants.REQUEST_CODE);
			}
		});
	}

	/**
	 * 陌生人信息和附近的人点击按钮事件
	 */
	@Click(R.id.fragment_stranger_tab)
	public void fragment_stranger_tab() {
		scrollTo();
	}

	private void scrollTo() {
		if (index == 0) {
			// 附近的人
			index = 1;
			fragment_stranger_tab.setImageResource(R.drawable.stranger_back);
			stranger_scrollLayout.scrollToScreen(index);
			MainActivity.main_viewpager.setNoScroll(true);
		} else {
			// 陌生人信息
			index = 0;
			fragment_stranger_tab.setImageResource(R.drawable.iv_new_heart_);
			stranger_scrollLayout.scrollToScreen(index);
			MainActivity.main_viewpager.setNoScroll(false);
		}
	}

	@ViewById(R.id.iv_fragment_stranger_near_tab)
	ImageView iv_fragment_stranger_near_tab;

	@Click(R.id.iv_fragment_stranger_near_tab)
	public void ll_fragment_stranger_near_tab() {
		if (index_tab == 0) {
			// 卡片
			index_tab = 1;
			iv_fragment_stranger_near_tab
					.setImageResource(R.drawable.transmit_small);
			near_stranger_scrollLayout.scrollToScreen(index_tab);
			MainActivity.main_viewpager.setNoScroll(false);
		} else {
			// 列表
			index_tab = 0;
			iv_fragment_stranger_near_tab
					.setImageResource(R.drawable.transmit_big);
			near_stranger_scrollLayout.scrollToScreen(index_tab);
			MainActivity.main_viewpager.setNoScroll(true);
		}
	}

	@Override
	protected void lazyLoad() {
		clearData();
		getNearStrangerInfo();
		// LogUtils.i(TAG, "lazyload");
		// // 如果未准备完毕、Fragment还不可见、已经加载过，则返回
		// if (!isPrepared || !isVisible || mHasLoadedOnce) {
		// return;
		// }
		// new AsyncTask<Void, Void, Boolean>() {
		// @Override
		// protected void onPreExecute() {
		// super.onPreExecute();
		// // 显示加载进度对话框
		// }
		//
		// @Override
		// protected Boolean doInBackground(Void... arg0) {
		// boolean isTrue = false;
		// try {
		// Thread.sleep(500);
		// // 在这里添加调用接口获取数据的代码
		// // doSomething()
		// if (isVisible) {
		// Log.i(TAG, "调用stranger接口");
		// isTrue = true;
		// }
		// } catch (Exception e) {
		//
		// }
		// return isTrue;
		// }
		//
		// @Override
		// protected void onPostExecute(Boolean isSuccess) {
		// if (isSuccess) {
		// // 加载成功
		// mHasLoadedOnce = true;
		// // textview.setText("陌生人fragment");
		// } else {
		// // 加载失败
		// }
		// // 关闭对话框
		// }
		// }.execute();
	}

	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	public void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(getActivity(), NearCardDetailActivity.class);
		intent.putExtra(NearCardDetailActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(NearCardDetailActivity.EXTRA_IMAGE_INDEX, position);
		startActivity(intent);
	}

	private Handler mGetNearStrangerInfoHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			lvNearStranger.onRefreshComplete();
			switch (msg.what) {
			case Constants.FAILURE:
				tvRefresh.setText("很遗憾，获取列表失败！");
				break;
			case 2222:
				tvRefresh.setText("点击刷新");
				flingContainer.setVisibility(View.VISIBLE);
				String data = msg.obj.toString();
				try {
					org.json.JSONArray array = new org.json.JSONArray(data);
					LogUtils.i("bm", "nearStranger==" + data);
					ArrayList<NearbyStrangerInfo> strangerData = (ArrayList<NearbyStrangerInfo>) JsonUtils
							.json2BeanArray(data, NearbyStrangerInfo.class);
					flipDataList.addAll(strangerData);
					DWUtils.sortList(flipDataList);
					notifyDataSetChange();
				} catch (JSONException e) {
					toast.show("解析异常");
				}
				break;
			case 3333:
				tvRefresh.setText("亲，已到最后了哦");
				isNeedToRequest = false;
				break;
			}
		}
	};

	@Subscriber(tag = NotifyByEventBus.NT_DELETE_STRANGER_CONVERSATION)
	private void deleteConversation(String key) {
		ConversationList conversation = strangerConversationMap.get(key);
		if (conversation != null) {
			strangerConversationList.remove(conversation);
			strangerConversationMap.remove(key);
			ConversationList.deleteConversation(conversation.getDwID(),
					DWMessage.CHAT_RELATIONSHIP_STRANGER);
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	@Subscriber(tag = NotifyByEventBus.NT_ADD_STRANGER_CONVERSATION)
	private void addConversation(ConversationList conversation) {
		conversation.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
		strangerConversationMap.put(conversation.getDwID()
				+ DWMessage.CHAT_TYPE_SINGLE, conversation);
		strangerConversationList.add(conversation);
		conversation.save();
		strangerRecorderAdapter.notifyDataSetChanged();
	}

	private void getNearStrangerInfo() {
		if (null == getStrangerInfo) {
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
		String condition = "{\"sex\":" + info.sex + "," + "\"minAge\":"
				+ info.minAge + "," + "\"maxAge\":" + info.maxAge + ","
				+ "\"minWorth\":" + info.minWorth + "," + "\"maxWorth\":"
				+ info.maxWorth + "," + "\"scope\":" + info.scope + ","
				+ "\"school\":" + "\"" + info.school + "\"" + ","
				+ "\"occupation\":" + "\"" + info.occupation + "\"" + "}";
		LogUtils.i("bm", "condition--" + condition);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", DecentWorldApp.getInstance().getDwID());
		map.put("scope", String.valueOf(100));
		// map.put("ln", String.valueOf(113));
		// map.put("lt", String.valueOf(20));
		map.put("searchCondition", condition);
		// 此处需要重新定位，不能直接用缓存的，因为用户的位置可能已经改变
		map.put("ln", String.valueOf(LocationProvider.longitude));
		LogUtils.e("bm", "--longitude--" + LocationProvider.longitude);
		map.put("lt", String.valueOf(LocationProvider.latitude));
		LogUtils.e("bm", "--latitude--" + LocationProvider.latitude);
		map.put("page", String.valueOf(page));
		LogUtils.i("bm", "page--" + "-----strangerFragment--------" + page);
		getStrangerInfo.getNearStrangerInfo(map, mGetNearStrangerInfoHandler);
	}

	@Click(R.id.iv_near_buttons_info)
	public void buttonsInfo() {
		toast.show("详细信息");
	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int strangerUnreadMsgCount = 0;
		return strangerUnreadMsgCount;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * 跳到指定的选项卡 0代表 聊天 1代表 附近的人
	 */
	@Override
	public void turnToTab(int tab) {
		index = tab;
		// fragment_stranger_tab();
	}

	public void like(String likedID) {
		// 网络数据
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				// LogUtils.i("bm", "handleMessage==" + msg.obj.toString());
				ArrayList<NearbyStrangerInfo> strangerData = (ArrayList<NearbyStrangerInfo>) JsonUtils
						.json2BeanArray((String) msg.obj.toString(),
								NearbyStrangerInfo.class);
			}
		};
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", DecentWorldApp.getInstance().getDwID());
		map.put("likedID", likedID);
		getStrangerInfo.likeStranger(map, handler);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Constants.RESULT_CODE_DISLIKE) {
			int position = data.getIntExtra(POSITION, -1);
			if (0 == position) {
				flingContainer.getTopCardListener().selectLeft();
			} else {
				flipDataList.remove(position);
			}
			notifyDataSetChange();
		} else if (resultCode == Constants.RESULT_CODE_LIKE) {
			int position = data.getIntExtra(POSITION, -1);
			NearbyStrangerInfo info = cardAdapter.getItem(position);
			info.setIfLike(true);
			like(info.getDwID());
		}
	}

	OnClickListener listener0 = new OnClickListener() {
		public void onClick(View v) {
			flingContainer.getTopCardListener().selectRight();
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Subscriber(tag = NotifyByEventBus.NT_CLEAR_STRANGER_CONVERSATION_UNREAD)
	public void clearUnread(String conversationKey) {
		ConversationList conversation = strangerConversationMap
				.get(conversationKey);
		if (conversation != null) {
			conversation.setUnReadCount(0);
			conversation.updateUnReadCount();
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_refresh:
			page = 0;
			tvRefresh.setText("刷新中。。。");
			clearData();
			getNearStrangerInfo();
			break;

		default:
			break;
		}
	}

	public void clearData() {
		if (null != flipDataList) {
			flipDataList.clear();
			notifyDataSetChange();
		}
	}
}
