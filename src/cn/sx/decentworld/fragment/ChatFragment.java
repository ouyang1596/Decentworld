package cn.sx.decentworld.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.EditUserInfoActivity_;
import cn.sx.decentworld.activity.GroupContactActivity_;
import cn.sx.decentworld.activity.NearCardDetailForMessageActivity_;
import cn.sx.decentworld.activity.NewFriendsActivity_;
import cn.sx.decentworld.activity.RechargePayMethodActivity_;
import cn.sx.decentworld.activity.SearchActivity_;
import cn.sx.decentworld.activity.ShareActivity_;
import cn.sx.decentworld.adapter.ConversationListAdapter;
import cn.sx.decentworld.adapter.FragmentContactAdapter;
import cn.sx.decentworld.adapter.FragmentContactAdapter.OnContactClickListener;
import cn.sx.decentworld.adapter.SlideImageAdapter;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.EditUserInfoItem;
import cn.sx.decentworld.bean.InviteMessage;
import cn.sx.decentworld.bean.NewFriend;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.PinyinComparator;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.LoginComponent;
import cn.sx.decentworld.component.ui.MainFragmentComponent;
import cn.sx.decentworld.component.ui.MeComponent;
import cn.sx.decentworld.dialog.Chat_delete_dialogListener;
import cn.sx.decentworld.dialog.Chatfragment_delete_conversation;
import cn.sx.decentworld.dialog.EditAndModificationDialog;
import cn.sx.decentworld.dialog.EditAndModificationDialog.EditAndModificationListener;
import cn.sx.decentworld.dialog.GuaranteeDialog;
import cn.sx.decentworld.dialog.GuaranteeDialog.GuaranteeListener;
import cn.sx.decentworld.dialog.RecommandDialog;
import cn.sx.decentworld.dialog.RecommandDialog.RecommondListener;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.manager.ConversationManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.request.SetFriendInfo;
import cn.sx.decentworld.network.request.SetUserInfo;
import cn.sx.decentworld.utils.AnonymityNickNameCreator;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.widget.FloatScrollView;
import cn.sx.decentworld.widget.FloatScrollView.OnScrollListener;
import cn.sx.decentworld.widget.HackyViewPager;
import cn.sx.decentworld.widget.ListViewForScrollView;
import cn.sx.decentworld.widget.ScrollLayout;

import com.activeandroid.query.Select;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ItemLongClick;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatFragment
 * @Description: 主界面聊天fragment
 * @author yj
 * @date 2015年6月29日12:34:03
 */
@EFragment(R.layout.main_layout_chat)
public class ChatFragment extends BaseFragment implements OnScrollListener,
		OnClickListener {
	private static String TAG = "ChatFragment";
	private Context context;
	private FragmentManager fragmentManager;
	@Bean
	MainFragmentComponent mainFragmentComponent;
	@Bean
	LoginComponent loginComponent;
	@Bean
	ToastComponent toast;
	@ViewById(R.id.chat_room_title_root)
	RelativeLayout relTitle;
	@ViewById(R.id.main_bottom_ll)
	LinearLayout main_bottom_ll;
	// 底部TAB的显示图片
	@ViewById(R.id.menu_tab1_iv)
	ImageView menu_tab1_iv;
	@ViewById(R.id.menu_tab2_iv)
	ImageView menu_tab2_iv;
	@ViewById(R.id.menu_tab3_iv)
	ImageView menu_tab3_iv;
	@ViewById(R.id.menu_tab4_iv)
	ImageView menu_tab4_iv;
	@ViewById(R.id.vp_near_card_detail_pager)
	HackyViewPager mPager;
	// 底部TAB的显示文字
	@ViewById(R.id.menu_tab1_tv)
	TextView menu_tab1_tv;
	@ViewById(R.id.menu_tab2_tv)
	TextView menu_tab2_tv;
	@ViewById(R.id.menu_tab3_tv)
	TextView menu_tab3_tv;
	@ViewById(R.id.menu_tab4_tv)
	TextView menu_tab4_tv;
	@ViewById(R.id.iv_search)
	ImageView ivSearch;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	// 获取数据库中已存在的用户相对应的DWid
	private String dwID = DecentWorldApp.getInstance().getDwID();
	@ViewById(R.id.chat_scrollLayout)
	public ScrollLayout chat_scrollLayout;
	private int index = 0;
	private CustomPageAdapter pagerAdapter;

	/**
	 * 消息资源list
	 */
	public static List<ConversationList> conversationLists;
	/**
	 * 消息资源Map
	 */
	private Map<String, ConversationList> conversationMap;
	private ConversationListAdapter conversationAdapter;
	/**
	 * 聊天界面的资源
	 */
	@ViewById(R.id.fragment_chat_chat_lv)
	ListView fragment_chat_chat_lv;
	@ViewById(R.id.rl_error_item)
	public RelativeLayout rl_error_item;
	@ViewById(R.id.tv_connect_errormsg)
	public TextView errorText;
	// 会话处未读消息textview
	@ViewById(R.id.menu_tab1_unread_msg_number)
	TextView unreadLabel;
	// 各种组件
	@Bean
	MeComponent meComponent;
	@Bean
	GetUserInfo getUserInfo;
	@Bean
	SetUserInfo setUserInfo;

	@Bean
	SetFriendInfo setFriendInfo;

	@Bean
	ChoceAndTakePictureComponent choceAndTakePictureComponent;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	@Override
	protected void lazyLoad() {
		LogUtils.i(TAG, "lazyload");
		if (!isPrepared || !isVisible) {
			return;
		}
	}

	@AfterViews
	public void init() {
		LogUtils.i(TAG, "init");
		ivSearch.setVisibility(View.VISIBLE);
		tvTitle.setText(getResources().getString(R.string.main_bottom_chat));
		setHasOptionsMenu(true);
		EventBus.getDefault().register(this);
		fragmentManager = getActivity().getSupportFragmentManager();
		initListener();
		// 页面tab跳转
		totap();
		isPrepared = true;
	}

	/**
	 * 主界面的底部选项卡
	 */
	@ViewById(R.id.menu_tab_1)
	RelativeLayout menu_tab_1;
	@ViewById(R.id.menu_tab_2)
	LinearLayout menu_tab_2;
	@ViewById(R.id.menu_tab_3)
	LinearLayout menu_tab_3;
	@ViewById(R.id.menu_tab_4)
	LinearLayout menu_tab_4;

	/**
	 * 监听事件初始化
	 */
	private void initListener() {
		ll_contact_list_recommend.setOnClickListener(this);
		ll_contact_list_guarantee.setOnClickListener(this);
		ll_contact_list_invite.setOnClickListener(this);
		new_friends_ll.setOnClickListener(this);
		group_chat_ll.setOnClickListener(this);
		interesting_group_ll.setOnClickListener(this);
		menu_tab_1.setOnClickListener(this);
		menu_tab_2.setOnClickListener(this);
		menu_tab_3.setOnClickListener(this);
		menu_tab_4.setOnClickListener(this);
		ivSearch.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_tab_1:
			index = 0;
			initMuen(index);
			ivSearch.setVisibility(View.VISIBLE);
			chat_scrollLayout.setToScreen(index);
			tvTitle.setText(getResources().getString(R.string.main_bottom_chat));
			break;
		case R.id.menu_tab_2:
			index = 1;
			initMuen(index);
			ivSearch.setVisibility(View.VISIBLE);
			chat_scrollLayout.setToScreen(index);
			tvTitle.setText(getResources().getString(
					R.string.main_bottom_contact));
			break;
		case R.id.menu_tab_3:
			index = 2;
			initMuen(index);
			ivSearch.setVisibility(View.GONE);
			chat_scrollLayout.setToScreen(index);
			tvTitle.setText(getResources().getString(R.string.main_bottom_find));
			break;
		case R.id.menu_tab_4:
			index = 3;
			initMuen(index);
			ivSearch.setVisibility(View.GONE);
			chat_scrollLayout.setToScreen(index);
			break;
		case R.id.ll_contact_list_recommend:
			recommend();// 推荐
			break;
		case R.id.ll_contact_list_guarantee:
			guarantee();// 担保
			break;
		case R.id.ll_contact_list_invite:
			invite();// 邀请
			break;
		case R.id.new_friends_ll:
			startActivity(new Intent(context, NewFriendsActivity_.class));
			break;
		case R.id.group_chat_ll:
			toast.show(Constants.DEVELOPING_NOTIFY);
			break;
		case R.id.interesting_group_ll:
			toast.show(Constants.DEVELOPING_NOTIFY);
			break;
		case R.id.iv_search:
			startActivity(new Intent(context, SearchActivity_.class));
			break;
		}
	}

	/**
	 * 邀请
	 */
	private void invite() {
		Intent intent = new Intent(getActivity(), ShareActivity_.class);
		startActivity(intent);
	}

	/**
	 * 担保
	 */
	private void guarantee() {
		final GuaranteeDialog guaranteeDialog = new GuaranteeDialog();
		guaranteeDialog.setListener(new GuaranteeListener() {
			@Override
			public void confirm(final String phoneNum, final String money) {
				final int VALIDATE_GUARANTEE = 1;
				Handler vHandler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						switch (msg.what) {
						case VALIDATE_GUARANTEE:
							Intent intent = new Intent(getActivity(),
									RechargePayMethodActivity_.class);
							intent.putExtra("isGuarantee", true);
							intent.putExtra("phoneNum", phoneNum);
							intent.putExtra("amount", money);
							startActivity(intent);
							guaranteeDialog.dismiss();
							break;
						default:
							break;
						}
					};
				};
				getUserInfo.validateGuarantee(phoneNum, vHandler,
						VALIDATE_GUARANTEE);
			}
		});
		guaranteeDialog.setName(UserInfoManager.getUserInfoInstance()
				.getNickName());
		guaranteeDialog.show(fragmentManager, "GuaranteeDialog");
	}

	/**
	 * 推荐
	 */
	private void recommend() {
		final RecommandDialog recommandDialog = new RecommandDialog();
		recommandDialog.setListener(new RecommondListener() {
			@Override
			public void confirm(final String phoneNum, final String money) {
				final int VALIDATERECOMMEND = 1;
				Handler vHandler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						switch (msg.what) {
						case VALIDATERECOMMEND:
							Intent intent = new Intent(getActivity(),
									RechargePayMethodActivity_.class);
							intent.putExtra("isRecommend", true);
							intent.putExtra("phoneNum", phoneNum);
							intent.putExtra("amount", money);
							startActivity(intent);
							recommandDialog.dismiss();
							break;
						default:
							break;
						}
					};
				};
				getUserInfo.validateRecommend(dwID, phoneNum, vHandler,
						VALIDATERECOMMEND);
			}
		});
		recommandDialog.setName(UserInfoManager.getUserInfoInstance()
				.getNickName());
		recommandDialog.show(fragmentManager, "RecommandDialog");
	}

	private void initMuen(int index) {
		switch (index) {
		case 0:
			setTabDefaultBg();
			menu_tab1_iv.setImageResource(R.drawable.main_chat_click);
			menu_tab1_tv.setTextColor(getResources().getColor(
					R.color.new_yellow_deep));
			relTitle.setVisibility(View.VISIBLE);
			initChat();
			break;
		case 1:
			setTabDefaultBg();
			menu_tab2_iv.setImageResource(R.drawable.main_contact_click);
			menu_tab2_tv.setTextColor(getResources().getColor(
					R.color.new_yellow_deep));
			initContact();
			relTitle.setVisibility(View.VISIBLE);
			break;
		case 2:
			setTabDefaultBg();
			menu_tab3_iv.setImageResource(R.drawable.main_find_click);
			menu_tab3_tv.setTextColor(getResources().getColor(
					R.color.new_yellow_deep));
			initFind();
			relTitle.setVisibility(View.VISIBLE);
			break;
		case 3:
			setTabDefaultBg();
			menu_tab4_iv.setImageResource(R.drawable.main_me_click);
			menu_tab4_tv.setTextColor(getResources().getColor(
					R.color.new_yellow_deep));
			relTitle.setVisibility(View.GONE);
			initMe();
			break;
		}
	}

	/**
	 * 设置默认的图标和文字
	 */
	private void setTabDefaultBg() {
		menu_tab1_iv.setImageResource(R.drawable.main_chat_default);
		menu_tab1_tv.setTextColor(getResources().getColor(R.color.main_grey));
		menu_tab2_iv.setImageResource(R.drawable.main_contact_default);
		menu_tab2_tv.setTextColor(getResources().getColor(R.color.main_grey));
		menu_tab3_iv.setImageResource(R.drawable.main_find_default);
		menu_tab3_tv.setTextColor(getResources().getColor(R.color.main_grey));
		menu_tab4_iv.setImageResource(R.drawable.main_me_default);
		menu_tab4_tv.setTextColor(getResources().getColor(R.color.main_grey));
	}

	@Subscriber(tag = NotifyByEventBus.NT_CHECK_NETWORK)
	public void checkNetwork() {
		if (!NetworkUtils.isNetWorkConnected(getActivity())) {
			rl_error_item.setVisibility(View.VISIBLE);
			rl_error_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toast.show(Constants.DEVELOPING_NOTIFY);
				}
			});
		} else {
			rl_error_item.setVisibility(View.GONE);
		}
	}

	/**
	 * 初始化聊天界面
	 */
	private void initChat() {
		// 检查网络是否可用
		checkNetwork();
		if (conversationLists == null) {
			initConversationList();
		}
		if (conversationAdapter == null) {
			conversationAdapter = new ConversationListAdapter(getActivity(),
					conversationLists);
		}
		fragment_chat_chat_lv.setAdapter(conversationAdapter);
		fragment_chat_chat_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final ConversationList conversationList = conversationLists
						.get(position);
				int GET_WORTH = 1;
				Handler handler2 = new Handler() {
					public void handleMessage(Message msg) {
						String user_worth = msg.obj.toString();
						LogUtils.i(TAG, "用户身价user_worth=" + user_worth);
						if (CommUtil.isNotBlank(user_worth)) {
							Intent intent = new Intent(getActivity(),
									ChatActivity_.class);
							intent.putExtra("user_dwID",
									conversationList.getDwID());
							intent.putExtra("user_nickname",
									conversationList.getTitle());
							if (ContactUser.isContact(conversationList
									.getDwID()))
								intent.putExtra("chatRelationship",
										DWMessage.CHAT_RELATIONSHIP_FRIEND);
							else
								intent.putExtra("chatRelationship",
										DWMessage.CHAT_RELATIONSHIP_STRANGER);
							intent.putExtra("chatType",
									conversationList.getChatType());
							intent.putExtra("user_worth", user_worth);
							startActivity(intent);
						} else {
							toast.show("用户身价为空");
						}
					};
				};
				getUserInfo.getWorth(conversationList.getDwID(), handler2,
						GET_WORTH);
			}
		});
		refreshUnReadMsg();
	}

	@Subscriber(tag = NotifyByEventBus.NT_CLEAR_CONVERSATION_UNREAD)
	public void clearUnread(String conversationKey) {
		ConversationList conversation = conversationMap.get(conversationKey);
		if (conversation != null) {
			conversation.setUnReadCount(0);
			conversation.updateUnReadCount();
			refreshUnReadMsg();
			conversationAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 刷新会话列表和chatTab选项卡
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_CONVERSATION)
	public void refreshConversation(DWMessage dwMessage) {
		LogUtils.i(TAG, "收到更新会话列表的通知");
		if (conversationMap == null) {
			LogUtils.i(TAG, "conversationMap为空");
			return;
		}
		String userID = DecentWorldApp.getInstance().getDwID();
		// 更新会话列表
		String otherID = null;// 对方id
		String fromDwId = dwMessage.getFrom();
		if (fromDwId != null && fromDwId.equals(userID)) {
			// 为发送出去的消息
			otherID = dwMessage.getTo();
		} else {
			otherID = fromDwId;
		}

		int chatType = dwMessage.getChatType();
		// 首先查询数据库，如果数据库中有数据就不要new，应该在app崩溃时，这个内存中的conversationMap会销毁掉，再次进入时自然没有；
		ConversationList conversation = conversationMap.get(otherID + chatType);
		// 新的列表
		if (conversation == null) {
			String conversation_icon = null;
			String title = null;
			if (chatType == DWMessage.CHAT_TYPE_SINGLE) {
				conversation_icon = ImageUtils.getIconByDwID(otherID,
						ImageUtils.ICON_SMALL);
				title = ContactUser.getDisplayNameByDwID(otherID);
				if (title.equals("")) {
					// 说明对方为陌生人
					title = dwID;
				}
			} else if (chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
				if (fromDwId != null && !fromDwId.equals(userID)) {
					conversation_icon = "";
					title = AnonymityNickNameCreator.getRandomName() + "[匿名聊天]";
				} else {
					conversation_icon = ImageUtils.getIconByDwID(otherID,
							ImageUtils.ICON_SMALL);
					// 存在隐患，如果other不是我的好友，那么就为空
					title = ContactUser.getDisplayNameByDwID(otherID)
							+ "[匿名聊天]";
				}
			}
			int msgType = dwMessage.getMessageType();
			String body = "";
			if (msgType == DWMessage.TXT) {
				body = dwMessage.getBody();
			}
			// 查询数据库，确保数据库中也没有数据的情况下才new
			ConversationList temp = ConversationList.queryByDwID(otherID,
					chatType, DWMessage.CHAT_RELATIONSHIP_FRIEND);
			if (temp != null) {
				conversation = temp;
			} else {
				if (fromDwId != null && fromDwId.equals(userID)) {
					// 为发送出去的消息
					conversation = new ConversationList(userID, otherID,
							conversation_icon, title, body,
							String.valueOf(System.currentTimeMillis()), 1, 0,
							1, 1);
				} else {
					// 查询数据库，确保数据库中也没有数据的情况下才new
					conversation = new ConversationList(userID, otherID,
							conversation_icon, title, body,
							String.valueOf(System.currentTimeMillis()), 1, 1,
							1, 1);
				}
			}
			conversation.setMessageType(msgType);
			conversation.setChatType(chatType);
			conversation
					.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_FRIEND);
			conversationMap.put(otherID + chatType, conversation);
			conversationLists.add(conversation);
			conversation.save();
		} else {
			conversation.receiveMessage(dwMessage);

			// 修改数据库
			String sql = "userID = ? and dwID=? and chatType=?";
			ConversationList conversationList = new Select()
					.from(ConversationList.class)
					.where(sql, userID, otherID, chatType).executeSingle();
			if (conversationList != null) {
				conversationList.setTime(dwMessage.getTime());
				conversationList.setMessageType(dwMessage.getMessageType());
				if (dwMessage.getMessageType() == DWMessage.TXT) {
					conversationList.setContent(dwMessage.getBody());
				}
				conversationList.setCount(conversationList.getCount() + 1);
				if (!dwMessage.getFrom().equals(otherID)) {
					conversationList.setUnReadCount(conversationList
							.getUnReadCount() + 1);
				}
				conversationList.save();
				LogUtils.i(TAG, "保存数据库完成");
			}
		}

		// 显示小红点
		refreshUnReadMsg();
		// 排序
		Collections.sort(conversationLists);
		conversationAdapter.notifyDataSetChanged();
	}

	/**
	 * 初始化APP时从数据库加载消息列表
	 */

	private void initConversationList() {
		LogUtils.i(TAG, "init conversationList----->");
		if (conversationLists == null) {
			LogUtils.i(TAG, "init conversationList----->conversationLists null");
			conversationLists = new LinkedList<ConversationList>();
		}
		if (conversationMap == null) {
			LogUtils.i(TAG, "init conversationList----->conversationMap null");
			conversationMap = new ConcurrentHashMap<String, ConversationList>();
		}
		conversationLists.clear();
		conversationMap.clear();

		// 筛选此用户的好友会话列表
		List<ConversationList> convList = ConversationManager.getInstance()
				.getFriendConversationList();

		for (ConversationList conv : convList) {
			conversationLists.add(conv);
			// 根据dwID和聊天类型组成一个键
			conversationMap.put(conv.getDwID() + conv.getChatType(), conv);
		}
		if (conversationAdapter != null) {
			conversationAdapter.notifyDataSetChanged();
		}
		refreshUnReadMsg();
	}

	/**
	 * 接收通知
	 * 
	 * @param tag
	 */
	@Subscriber(tag = NotifyByEventBus.NT_INIT_FRIEND_CONVERSATION)
	public void initConversationList(String tag) {
		LogUtils.i(TAG, tag);
		initConversationList();
	}

	public List<ConversationList> getCoversationLists() {
		return conversationLists;
	}

	/**
	 * 更新好友的未读消息
	 */
	public void refreshUnReadMsg() {
		// 从会话列表中计算未读消息总数
		int count = 0;
		if (conversationLists == null) {
			count = 0;
		} else {
			for (ConversationList conv : conversationLists) {
				if (conv.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
					count += conv.getUnReadCount();
				}
			}
		}
		// 更新ChatTab标签页，并显示数字
		if (count > 0) {
			unreadLabel.setText(String.valueOf(count));
			unreadLabel.setVisibility(View.VISIBLE);
		} else {
			unreadLabel.setVisibility(View.GONE);
		}
	}

	/**
	 * 由PacketListenerService路由过来的【申请加为好友】的通知 刷新新的朋友Item上的小红点数
	 */
	@Subscriber(tag = NotifyByEventBus.NT_SHOW_FRIENDS_ADDED)
	public void refreshUnreadContactMsg(String name) {
		LogUtils.i(TAG, "接到通知【申请加为好友】");
		int newfriend_count = NewFriend.selectAllIsNotShown();
		if (newfriend_count > 0) {
			LogUtils.i(TAG, "refreshNewFriendList---NotShown---count>0");
			// 新的朋友的小红点
			new_friends_ll_unread_msg_number.setVisibility(View.VISIBLE);
			new_friends_ll_unread_msg_number.setText(String
					.valueOf(newfriend_count));
		} else {
			LogUtils.i(TAG, "refreshNewFriendList---NotShown---count=0");
			new_friends_ll_unread_msg_number.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 联系人列表资源
	 */
	// 推荐
	@ViewById(R.id.ll_contact_list_recommend)
	LinearLayout ll_contact_list_recommend;
	// 担保
	@ViewById(R.id.ll_contact_list_guarantee)
	LinearLayout ll_contact_list_guarantee;
	// 邀请
	@ViewById(R.id.ll_contact_list_invite)
	LinearLayout ll_contact_list_invite;
	// 新的朋友
	@ViewById(R.id.new_friends_ll)
	LinearLayout new_friends_ll;
	// 群聊
	@ViewById(R.id.group_chat_ll)
	LinearLayout group_chat_ll;
	// 兴趣组
	@ViewById(R.id.interesting_group_ll)
	LinearLayout interesting_group_ll;
	// 未读通讯录textview
	// @ViewById(R.id.menu_tab2_unread_msg_number)
	// TextView unreadAddressLable;
	// 新朋友未读数
	@ViewById(R.id.new_friends_ll_unread_msg_number)
	TextView new_friends_ll_unread_msg_number;

	@ViewById(R.id.fragment_contact_choice_ll)
	LinearLayout fragment_contact_choice_ll;

	@ViewById(R.id.fragment_chat_contact_sv)
	FloatScrollView fragment_chat_contact_sv;

	@ViewById(R.id.fragment_chat_contact_header)
	LinearLayout fragment_chat_contact_header;

	@ViewById(R.id.fragment_contact_choice_ll_child)
	LinearLayout fragment_contact_choice_ll_child;

	@ViewById(R.id.fragment_chat_contact_lv)
	ListViewForScrollView fragment_chat_contact_lv;

	public static List<ContactUser> mDatas = new ArrayList<ContactUser>();
	// 黑名单
	// private List<String> blackList;
	// private List<User> pydatas;
	// private List<User> xinbiaoList = new ArrayList<User>();
	private FragmentContactAdapter contactAdapter;
	private int searchLayoutTop;

	/**
	 * 初始化通讯录界面
	 */
	private void initContact() {
		// 检查网络是否可用
		checkNetwork();
		// 初始化联系人数据，并显示
		if (contactAdapter == null) {
			contactAdapter = new FragmentContactAdapter(getActivity(), mDatas);
		}
		fragment_chat_contact_lv.setAdapter(contactAdapter);
		contactAdapter.setOnContactClickListener(new OnContactClickListener() {
			@Override
			public void onClick(View view) {
				int position = (Integer) view.getTag(Constants.ITEM_KEY);
				switch (view.getId()) {
				case R.id.fragment_chat_contact_lv_item_icon:
					Intent intent = new Intent(getActivity(),
							NearCardDetailForMessageActivity_.class);
					intent.putExtra(Constants.DW_ID, mDatas.get(position)
							.getDwID());
					startActivity(intent);
					break;
				case R.id.fragment_chat_contact_lv_item_name:
					startActivity(new Intent(getActivity(), ChatActivity_.class)
							.putExtra("user_dwID",
									mDatas.get(position).getDwID())
							.putExtra("user_nickname",
									mDatas.get(position).getNickName())
							.putExtra("chatType", DWMessage.CHAT_TYPE_SINGLE)
							.putExtra("chatRelationship",
									DWMessage.CHAT_RELATIONSHIP_FRIEND)
							.putExtra("user_worth",
									mDatas.get(position).getWorth()));
					break;
				}
			}
		});
		fragment_chat_contact_sv.setOnScrollListener(this);
		searchLayoutTop = fragment_chat_contact_header.getBottom();
		refreshContact();
	}

	/**
	 * 无参 刷新联系人列表
	 */
	public void refreshContact() {
		mDatas.clear();
		List<ContactUser> temp = getContactList();
		if (temp.size() > 0) {
			mDatas.addAll(temp);
			if (contactAdapter != null) {
				contactAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 有参 dwID 刷新联系人列表
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_CONTACT)
	public void refreshContact(String dwID) {
		LogUtils.i(TAG, "收到一条通知【更新联系人列表】：" + dwID);
		refreshContact();
		// 有待优化
		// 若在陌生人的对话框中有聊天列表，则移植到主界面
		// String conversationKey = dwID + DWMessage.CHAT_TYPE_SINGLE;
		// ConversationList conversation =
		// StrangerFragment.getStrangerConversation(conversationKey);
		// if (conversation != null && conversation.getChatType() !=
		// DWMessage.CHAT_TYPE_SINGLE_ANONYMITY)
		// {
		// conversation.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_FRIEND);
		// conversationLists.add(conversation);
		// conversationMap.put(conversation.getDwID() +
		// conversation.getChatType(), conversation);
		// conversation.save();
		// conversationAdapter.notifyDataSetChanged();
		// EventBus.getDefault().post(conversationKey,
		// NotifyByEventBus.NT_DELETE_STRANGER_CONVERSATION);
		// }
		// else
		// {
		// // 若在朋友的对话框中有聊天列表，则移植到陌生人界面
		// String friendconversationKey = dwID + DWMessage.CHAT_TYPE_SINGLE;
		// ConversationList friendConversation =
		// conversationMap.get(friendconversationKey);
		// if (friendConversation != null && friendConversation.getChatType() !=
		// DWMessage.CHAT_TYPE_SINGLE_ANONYMITY)
		// {
		// EventBus.getDefault().post(friendConversation,
		// NotifyByEventBus.NT_ADD_STRANGER_CONVERSATION);
		// conversationLists.remove(friendConversation);
		// conversationMap.remove(friendConversation);
		// ConversationList.deleteConversation(friendConversation.getDwID(),
		// DWMessage.CHAT_RELATIONSHIP_FRIEND);
		// conversationAdapter.notifyDataSetChanged();
		// }
		// }
	}

	/**
	 * 从数据库中获取联系人数据，并进行排序
	 */
	private List<ContactUser> getContactList() {
		List<ContactUser> contactUsers = new ArrayList<ContactUser>();
		List<ContactUser> temp = ContactUser.queryAllList();
		if (temp.size() > 0) {
			ContactUser t;
			for (ContactUser user : temp) {
				t = new ContactUser();
				t.setDwID(user.getDwID());
				t.setNickName(ContactUser.getDisplayNameByBean(user));
				t.setIcon(user.getIcon());
				t.setWorth(user.getWorth());
				contactUsers.add(t);
			}
			contactUsers = (List<ContactUser>) mainFragmentComponent
					.filledData(contactUsers);
			Collections.sort(contactUsers, new PinyinComparator());
		}
		return contactUsers;
	}

	/**
	 * 发现的资源
	 */

	/**
	 * 初始化发现界面
	 */
	private void initFind() {
		// 检查网络是否可用
		checkNetwork();
	}

	/**
	 * 初始化我的设置界面
	 */

	// 用户头像(圆形)
	@ViewById(R.id.iv_chat_me_avatar)
	ImageView iv_chat_me_avatar;
	// 身价
	@ViewById(R.id.tv_chat_me_worth)
	public static TextView tv_chat_me_worth;
	@ViewById(R.id.tv_chat_me_wealth)
	public static TextView tv_chat_me_wealth;
	// 用户详细信息 Tab
	@ViewById(R.id.ll_chat_me_user_detail)
	LinearLayout ll_chat_me_user_detail;
	// 用户信息（唯一）
	public static List<EditUserInfoItem> userInfoDatas;
	private static final int GET_USER_WEALTH = 2;
	private static final int GET_USER_WORTH = 3;

	Handler meHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_USER_WEALTH:
				tv_chat_me_wealth.setText(msg.obj.toString());
				break;
			case GET_USER_WORTH:
				tv_chat_me_worth.setText(msg.obj.toString());
				break;
			default:
				break;
			}
		};
	};

	private List<String> icons;
	private SlideImageAdapter slideImageAdapter;
	private List<ImageView> imgs;

	@ViewById(R.id.ll_chat_me_chat_room)
	LinearLayout ll_chat_me_chat_room;

	/**
	 * 如果自己有多个聊天室，则点击可以打开自己创建的聊天室列表
	 */
	@Click(R.id.ll_chat_me_chat_room)
	void editRoomList() {
		toast.show("打开聊天室列表");
	}

	@ViewById(R.id.tv_chat_me_name)
	TextView tv_chat_me_name;

	@ViewById(R.id.btn_chat_me_send_msg)
	Button btn_chat_me_send_msg;

	/**
	 * 给自己发送短信
	 */
	@Click(R.id.btn_chat_me_send_msg)
	void sendMsg() {
		toast.show("给自己发送短信");
	}

	/**
	 * 自己的毕业院校 或 职业
	 */
	@ViewById(R.id.tv_chat_me_school)
	TextView tv_chat_me_school;

	/**
	 * 自己的毕业院校
	 */
	@ViewById(R.id.tv_chat_me_sign)
	TextView tv_chat_me_sign;

	/**
	 * 我的资源
	 */
	private List<String> picUrls = new ArrayList<String>();
	private List<ImageView> imgvs = new ArrayList<ImageView>();

	@ViewById(R.id.tv_chat_me_introduce)
	TextView tv_chat_me_introduce;

	@ViewById(R.id.tv_chat_me_edit)
	TextView tv_chat_me_edit;
	public static UserDetailFragment userDetailFragment;

	/**
	 * 初始化我的资料
	 */
	private void initMe() {
		// 检查网络是否可用
		checkNetwork();
		meComponent.setFragmentManager(fragmentManager);

		initHeader(UserInfoManager.getUserInfoInstance());
		if (userInfoDatas == null) {
			userInfoDatas = new ArrayList<EditUserInfoItem>();
		}
		meComponent.initUserInfoDatas(userInfoDatas);
		initIntroduction();
		meComponent.getIconPath(dwID);
		initPicUrl();
		meComponent.initFindScrollView();
	}

	/**
	 * 初始化我的资料处的头部信息
	 */
	private void initHeader(UserInfo userInfo) {
		if (null == userInfo) {
			LogUtils.i(TAG, "userInfo为空");
			return;
		}
		// 显示用户的实名
		tv_chat_me_name.setText(userInfo.getRealName());
		if (userInfo.getIfStudent().equals("1")) {
			tv_chat_me_school.setText(userInfo.getSchool());
		} else {
			tv_chat_me_school.setText(userInfo.getOccupation());
		}
		tv_chat_me_sign.setText(userInfo.getSign());
	}

	/**
	 * 初始化显示用户的详细资料
	 */
	private void initIntroduction() {
		if (userDetailFragment == null) {
			tv_chat_me_introduce.setVisibility(View.GONE);
			tv_chat_me_edit.setVisibility(View.VISIBLE);
			userDetailFragment = new UserDetailFragment_();
			userDetailFragment.setData(userInfoDatas);
			FragmentManager fragmentManager = getActivity()
					.getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.ll_chat_me_container,
					userDetailFragment);
			fragmentTransaction.commit();
		}
	}

	/**
	 * 初始化头像
	 */
	private void initPicUrl() {
		picUrls.clear();
		UserInfo userInfo = UserInfoManager.getUserInfoInstance();
		if (null == userInfo) {
			return;
		}
		if (CommUtil.isNotBlank(userInfo.getIcon())) {
			ImageView v = new ImageView(getActivity());
			v.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imgvs.add(v);
			picUrls.add(userInfo.getIcon());
		}
		if (CommUtil.isNotBlank(userInfo.getIcon2())) {
			ImageView v = new ImageView(getActivity());
			v.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imgvs.add(v);
			picUrls.add(userInfo.getIcon2());
		}

		if (CommUtil.isNotBlank(userInfo.getIcon3())) {
			ImageView v = new ImageView(getActivity());
			v.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imgvs.add(v);
			picUrls.add(userInfo.getIcon3());
		}
		pagerAdapter = new CustomPageAdapter();
		mPager.setAdapter(pagerAdapter);
		// 显示用户圆形头像
		if (CommUtil.isNotBlank(userInfo.getIcon())) {
			ImageLoaderHelper.mImageLoader.displayImage(userInfo.getIcon(),
					iv_chat_me_avatar, ImageLoaderHelper.mOptions);
		}
	}

	@Click(R.id.tv_chat_me_edit)
	void editUserInfo(View view) {
		LogUtils.i(TAG, "点击了编辑按钮");
		Intent intent = new Intent(context, EditUserInfoActivity_.class);
		startActivityForResult(intent, R_GET_USER_THREE_ICON);
	}

	private static final int GET_USER_DISPALY_INFO = 1;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_USER_DISPALY_INFO:
				LogUtils.i(TAG, "获取用户要显示的信息 ok");
				JSONObject jsonObject = JSON.parseObject(msg.obj.toString());
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 长按消息列表中的item
	 * 
	 * @param position
	 */
	@ItemLongClick(R.id.fragment_chat_chat_lv)
	void show_delete_dialog(int position) {
		Chatfragment_delete_conversation dialog2 = Chatfragment_delete_conversation
				.newInstance(position);
		dialog2.setOnListener(cdd_listener);
		dialog2.show(fragmentManager, "dialog2");
	}

	Chat_delete_dialogListener cdd_listener = new Chat_delete_dialogListener() {
		@Override
		public void delete_message(int position) {
			boolean handled = true;
			boolean deleteMessage = false;

		}

		@Override
		public void delete_conversation(int position) {
			boolean handled = false;
			boolean deleteMessage = false;
			// 更新消息未读数
		}

	};

	/**
	 * 已加入的群组
	 */
	@Click(R.id.group_chat_ll)
	public void showGroupChat() {
		mainFragmentComponent.toActivity(GroupContactActivity_.class);
	}

	// 监听滚动Y值变化，通过addView和removeView来实现悬停效果
	@Override
	public void onScroll(int scrollY) {
		// if (scrollY >= searchLayoutTop)
		// {
		// if (fragment_contact_choice_ll_child.getParent() !=
		// fragment_contact_choice_ll_top)
		// {
		// fragment_contact_choice_ll.removeView(fragment_contact_choice_ll_child);
		// fragment_contact_choice_ll_top.addView(fragment_contact_choice_ll_child);
		// fragment_contact_choice_ll_top.setBackgroundResource(R.drawable.fragment_contact_choice_child_bg);
		// }
		// } else
		// {
		//
		// if (fragment_contact_choice_ll_top.getChildCount() > 0)
		// {
		// fragment_contact_choice_ll_top.removeView(fragment_contact_choice_ll_child);
		// fragment_contact_choice_ll.addView(fragment_contact_choice_ll_child);
		// fragment_contact_choice_ll_top.setBackgroundColor(Color.TRANSPARENT);
		// }
		//
		// }

	}

	/**
	 * 获取所有会话(加上置顶TopList表)
	 * 
	 * @return +
	 */
	private void getAllConversation() {
		// List<MsgTop> list = MsgTop.queryAll();
		// // all_conversationlist返回根据时间排序的序列
		// List<ConversationList> all_conversationlist = ConversationList
		// .queryAll();
		// // 不显示陌生人的
		// for (ConversationList conv : all_conversationlist) {
		//
		// if (conv.getChatRelationship() ==
		// DWMessage.CHAT_RELATIONSHIP_STRANGER) {
		// all_conversationlist.remove(conv);
		// }
		// }
		// // ConversationList增
		// for (int i = 0; i < list.size(); i++) {
		// boolean tocreate = true;
		// for (int j = 0; j < all_conversationlist.size(); j++) {
		// if (list.get(i).getOtherID()
		// .equals(all_conversationlist.get(j).getDwID())) {
		// all_conversationlist.get(j).setSetToTop(
		// ConversationList.SET_TOP_YES);
		// all_conversationlist.get(j).save();
		// tocreate = false;
		// break;
		// }
		// }
		// if (tocreate) {
		// ConversationList ll = new ConversationList(list.get(i)
		// .getOtherID(), ContactUser.queryByDwID(
		// list.get(i).getOtherID()).getIcon(), ContactUser
		// .queryByDwID(list.get(i).getOtherID()).getNickName(),
		// "", list.get(i).getTime(), 0, 0, 1, 0, DWMessage.TXT, 0);
		// ll.save();
		// all_conversationlist.add(ll);
		// }
		// }
		// // ConversationList减
		// for (int i = 0; i < all_conversationlist.size(); i++) {
		// boolean change_not_totop = true;
		// for (int j = 0; j < list.size(); j++) {
		// if (all_conversationlist.get(i).getDwID()
		// .equals(list.get(j).getOtherID())) {
		// change_not_totop = false;
		// break;
		// }
		// }
		// if (change_not_totop) {
		// all_conversationlist.get(i).setSetToTop(1);
		// all_conversationlist.get(i).save();
		// }
		// }
		// conversationLists.addAll(all_conversationlist);
		// refreshUnremain(conversationLists);
		// departTopOrnot(conversationLists);
	}

	/**
	 * 将置顶会话与普通对话分开
	 */
	private void departTopOrnot(List<ConversationList> list) {
		// List<ConversationList> topList = new ArrayList<ConversationList>();
		// for(int i =0;i<list.size();i++){
		// if(list.get(i).getSet_totop()==0){
		// topList.add(list.get(i));
		//
		// }
		// }
	}

	/**
	 * 刷新List<ConversationList>，设置是否免打扰（优化）
	 * 
	 * @param conversationLists
	 */
	// private void refreshUnremain(List<ConversationList> conversationLists) {
	// List<NoDisturb> list = NoDisturb.queryAll();
	// // 根据NoremindList,设置ConversationList的Chat_unremind为0，即免打扰
	// for (int i = 0; i < list.size(); i++) {
	// for (int j = 0; j < conversationLists.size(); j++) {
	// if (list.get(i).getOtherID() == conversationLists.get(j)
	// .getDwID()
	// && conversationLists.get(j).getUnRemind() == 1) {
	// conversationLists.get(j).setUnRemind(0);
	// conversationLists.get(j).save();
	// break;
	// }
	// }
	// }
	// //
	// ConversationList可能（比NoremindList）设置多了免打扰字段，将ConversationList的Chat_unremind为1，即正常模式
	// for (int i = 0; i < conversationLists.size(); i++) {
	// boolean set_not_unremain = true;
	// for (int j = 0; j < list.size(); j++) {
	// if (conversationLists.get(i).getDwID() == list.get(j)
	// .getOtherID()) {
	// set_not_unremain = false;
	// break;
	// }
	// }
	// if (set_not_unremain) {
	// conversationLists.get(i).setUnRemind(1);
	// conversationLists.get(i).save();
	// }
	// }
	// }

	/**
	 * 
	 * @ClassName: ChatFragment.java
	 * @Description: 根据最后一条消息的时间排序
	 * @author: dyq
	 * @date: 2015年10月22日 下午6:49:46
	 */
	class sortConversationList implements Comparator {

		@Override
		public int compare(Object lhs, Object rhs) {
			ConversationList l1 = (ConversationList) lhs;
			ConversationList l2 = (ConversationList) rhs;
			Date date1 = TimeUtils.getDateType(l1.getTime());
			Date date2 = TimeUtils.getDateType(l2.getTime());
			if (date1.compareTo(date2) > 1) {
				return 1;
			}
			if (date1.compareTo(date2) < 1) {
				return -1;
			}
			return 0;
		}

	}

	/**
	 * 初始化发现界面
	 */
	/**
	 * 作品圈入口
	 */
	@Click(R.id.fragment_chat_find_work_circle)
	public void fragment_chat_find_work_circle() {
		toast.show(Constants.DEVELOPING_NOTIFY);
		// mainFragmentComponent.toActivity(WorkActivity_.class);
	}

	/**
	 * 兴趣组入口
	 */
	@Click(R.id.fragment_chat_find_group_interested)
	public void fragment_chat_find_group_interested() {
		toast.show(Constants.DEVELOPING_NOTIFY);
		// mainFragmentComponent.toActivity(InterestingGroupActivity_.class);
	}

	/**
	 * 保存邀请等msg
	 *
	 * @param msg
	 */
	private void saveInviteMsg(InviteMessage msg) {
		// // 保存msg
		// inviteMessgeDao.saveMessage(msg);
		// // 未读数加1
		// cn.sx.decentworld.domain.User user =
		// DecentWorldApp.getInstance().getContactList().get(Constants.NEW_FRIENDS_USERNAME);
		// if (user.getUnreadMsgCount() == 0)
		// user.setUnreadMsgCount(user.getUnreadMsgCount() + 1);
	}

	private boolean isFirst = true;
	public static boolean update_introduction = false;

	@Override
	public void onResume() {
		LogUtils.i(TAG, "onResume");
		super.onResume();
		if (conversationLists == null) {
			initConversationList();
		}

		if (contactAdapter != null) {
			refreshContact();
		}
		ImageLoaderHelper.clearCache();
		if (null != pagerAdapter) {
			pagerAdapter.notifyDataSetChanged();
		}
		/**
		 * 获取身价、身家
		 */
		getUserInfo.getWealth(dwID, meHandler, GET_USER_WEALTH);
		getUserInfo.getWorth(dwID, meHandler, GET_USER_WORTH);
	}

	/**
	 * 跳到指定Tab项
	 */
	@Override
	public void turnToTab(int tab) {
		if (tab != -1) {
			chat_scrollLayout.scrollToScreen(tab);
			initMuen(tab);
		}
	}

	/**
	 * 根据传入得tab值，进入不同tab，默认是0，即第一个
	 */
	private void totap() {
		chat_scrollLayout.scrollToScreen(index);
		initMuen(index);
	}

	public static final int R_GET_USER_THREE_ICON = 1;// 获取用户的三张头像

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		LogUtils.i(TAG, "进入到：onActivityResult,requestCode=" + requestCode
				+ ",resultCode" + resultCode);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == R_GET_USER_THREE_ICON) {
				LogUtils.i(TAG, "进入数据更新");
				meComponent.getIconPath(dwID);
				UserInfo info = UserInfoManager.getUserInfoInstance();
				initHeader(info);
				if (pagerAdapter != null) {
					LogUtils.i(TAG, "pagerAdapter更新");
					pagerAdapter.notifyDataSetChanged();
				} else {
					LogUtils.i(TAG, "pagerAdapter==null");
				}
				ImageLoaderHelper.mImageLoader.displayImage(info.getIcon(),
						iv_chat_me_avatar, ImageLoaderHelper.mOptions);
			}
		}
	}

	private class CustomPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return picUrls.size();
		}

		@Override
		public Object instantiateItem(View arg0, int position) {
			ImageView imageView = imgvs.get(position);
			LogUtils.e("bm", "url---" + picUrls.get(position));
			ImageLoaderHelper.mImageLoader.displayImage(picUrls.get(position),
					imageView, ImageLoaderHelper.mOptions);
			((ViewPager) arg0).addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	/**
	 * 上下午菜单
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	/**
	 * 当上下文菜单被点击时触发事件 return true 代表截断 return false 代表继续分发 1.修改备注 2.删除好友
	 */
	private String friendID;
	private String remark;

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		// 如果Fragment可见，则截取事件，否则继续分发
		final int SET_REMARK = 1;
		final int DELETE_CONTACT = 2;
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				ConversationList conversationList = ConversationList
						.queryByDwID(friendID,
								DWMessage.CHAT_RELATIONSHIP_FRIEND);
				switch (msg.what) {
				case SET_REMARK:
					// 修改会话列表中与此好友有关的会话列表的名字
					if (conversationList != null) {
						conversationList.setTitle(remark);
						conversationList.save();
						initConversationList();
					}
					// 成功
					LogUtils.i(TAG, "设置备注成功，修改为:" + remark);
					ContactUser contactUser = ContactUser.queryByDwID(friendID);
					contactUser.setRemark(remark);
					contactUser.save();
					// 更新界面
					refreshContact();
					toast.show("设置备注成功");
					break;
				case DELETE_CONTACT:
					ContactUser user = ContactUser.queryByDwID(friendID);
					if (user != null) {
						// 将好友从联系人列表中删除
						user.delete();
						// 更新联系人列表
						refreshContact(user.getDwID());
						// 将消息页面该对象的聊天记录删除
						LogUtils.i(TAG, "删除好友成功");
						toast.show("删除好友成功");
					}

					// 将好友处的会话列表移动到陌生人处
					if (conversationList != null) {
						conversationList
								.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
						// conversationList.setTitle("");//设置为昵称
						conversationList.save();
						EventBus.getDefault().post("【删除好友，更新好友会话列表】",
								NotifyByEventBus.NT_INIT_FRIEND_CONVERSATION);
						EventBus.getDefault().post("【删除好友，更新陌生人会话列表】",
								NotifyByEventBus.NT_INIT_STRANGER_CONVERSATION);
					}
					break;
				default:
					break;
				}
			};
		};

		if (getUserVisibleHint()) {
			if (item.getItemId() == 1) {
				LogUtils.i(TAG, "ChatFragment可见,点击了菜单 修改备注");
				editAndModificationDialog = new EditAndModificationDialog();
				editAndModificationDialog.setTitle("修改备注");
				editAndModificationDialog
						.setListener(new EditAndModificationListener() {

							@Override
							public void confirm(String info) {
								friendID = mDatas.get(
										item.getIntent().getIntExtra(
												"position", -1)).getDwID();
								remark = info;
								setFriendInfo.setRemark(dwID, friendID, info,
										handler, SET_REMARK);
							}
						});
				editAndModificationDialog.show(fragmentManager, "setremark");
			} else if (item.getItemId() == 2) {
				LogUtils.i(TAG, "ChatFragment可见,点击了菜单 删除好友");
				reminderDialog = new ReminderDialog();
				reminderDialog.setInfo("删除好友");
				reminderDialog.setListener(new ReminderListener() {
					@Override
					public void confirm() {
						friendID = mDatas.get(
								item.getIntent().getIntExtra("position", -1))
								.getDwID();
						setFriendInfo.deleteContact(dwID, friendID, handler,
								DELETE_CONTACT);
					}
				});
				reminderDialog.show(fragmentManager, "deleteFriend");
			}
			return super.onContextItemSelected(item);
		}
		LogUtils.i(TAG, "ChatFragment不可见");
		return false;
	}

	private EditAndModificationDialog editAndModificationDialog;
	private ReminderDialog reminderDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.i(TAG, "onCreate");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtils.i(TAG, "onStart");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtils.i(TAG, "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtils.i(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.conversationMap = null;
		conversationLists = null;
		LogUtils.i(TAG, "onDestroy");
	}

}