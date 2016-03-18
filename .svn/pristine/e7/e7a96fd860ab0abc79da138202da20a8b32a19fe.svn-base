/**
 * 
 */
package cn.sx.decentworld.fragment.stranger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.adapter.ConversationListAdapter2;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.MsgAndInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.manager.ConversationManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: AchievementFragment.java
 * @Description:
 * @author: yj
 * @date: 2016年1月6日 下午3:28:18
 */
@EFragment(R.layout.fragment_stranger_chat)
public class StrangerMessageFragment extends Fragment implements OnCommomPromptListener {
	@ViewById(R.id.lv_stranger_message)
	ListView lvStrnagerMessage;
	private static final int GET_STRANGER_CONV_LIST = 1;
	@Bean
	GetUserInfo getUserInfo;
	// 会话列表数据
	private List<ConversationList> strangerConversationList;
	// 会话列表适配器
	private ConversationListAdapter2 strangerRecorderAdapter;
	// 会话列表键值对数据
	private Map<String, ConversationList> strangerConversationMap;
	private CommomPromptDialogFragment mCommonPromptDialogFragment;

	@AfterViews
	public void init() {
		EventBus.getDefault().register(this);
		initData();
	}

	private void initData() {
		strangerConversationList = new LinkedList<ConversationList>();
		strangerConversationMap = new ConcurrentHashMap<String, ConversationList>();
		strangerRecorderAdapter = new ConversationListAdapter2(getActivity(), strangerConversationList);
		lvStrnagerMessage.setAdapter(strangerRecorderAdapter);
		lvStrnagerMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (FileUtils.getPromptStatus(StrangerMessageFragment.this.getActivity(), Constants.STRANGER) == CommomPromptDialogFragment.STRANGER) {
					ConversationList info = strangerRecorderAdapter.getItem(position);
					Intent intent = new Intent(getActivity(), ChatActivity_.class);
					intent.putExtra(ChatActivity.OTHER_ID, info.getDwID());
					intent.putExtra(ChatActivity.OTHER_NICKNAME, info.getTitle());
					intent.putExtra(ChatActivity.CHAT_TYPE, info.getChatType());
					intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, info.getChatRelationship());
					intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(info.getWorth()));
					startActivity(intent);
					return;
				}
				mCommonPromptDialogFragment = new CommomPromptDialogFragment();
				mCommonPromptDialogFragment.setTips("你与他是陌生关系，每人前三句是不会得到费用的。之后他与你每说一句话，将按照你的身价向你付费。同理，注意他的身价哦!");
				mCommonPromptDialogFragment.setEnter(CommomPromptDialogFragment.STRANGER);
				mCommonPromptDialogFragment.setObject(strangerRecorderAdapter.getItem(position));
				mCommonPromptDialogFragment.setOnCommomPromptListener(StrangerMessageFragment.this);
				mCommonPromptDialogFragment.show(StrangerMessageFragment.this.getActivity().getSupportFragmentManager(),
						"mCommonPromptDialogFragment");
			}
		});
		getStrnagerConversationList();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_STRANGER_CONV_LIST:
				processStrangerConvList(msg);
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 处理获取的陌生人会话列表
	 */
	protected void processStrangerConvList(Message msg) {
		if (msg.arg1 == 1) {
			ConversationList.deleteConversation(DecentWorldApp.getInstance().getDwID(), 0);
			String jsonStr = msg.obj.toString();
			JSONObject jsonObject = JSON.parseObject(jsonStr);
			String jsonArrayStr = jsonObject.getString("strangerCoversations");
			if (null == jsonArrayStr) {
				return;
			}
			// 解析数据
			JSONArray array = JSON.parseArray(jsonArrayStr);
			if (array.size() > 0) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject object = array.getJSONObject(i);
					int chatRelationship = object.getIntValue("chatRelationship");
					int chatType = object.getIntValue("chatType");
					String content = object.getString("content");
					int messageType = object.getIntValue("messageType");
					String otherID = object.getString("otherID");
					String title = object.getString("title");
					String worth = object.getString("worth");
					long time = object.getLongValue("time");
					String icon = ImageUtils.getIconByDwID(otherID, ImageUtils.ICON_SMALL);
					// 构造会话对象
					ConversationList conversation = new ConversationList(DecentWorldApp.getInstance().getDwID(), otherID, icon,
							title, content, time);
					conversation.setMessageType(messageType);
					conversation.setChatType(chatType);
					conversation.setChatRelationship(1);
					conversation.setWorth(worth);
					conversation.save();
				}
			}
		} else {
			LogUtils.i(Constants.TAG_BM, "获取陌生人会话列表失败");
		}

		initStrangerConversationList();
	}

	public void getStrnagerConversationList() {
		List<ConversationList> convList = ConversationManager.getInstance().getStrangerConversationList();
		if (convList.size() > 0) {
			initStrangerConversationList();
		} else {
			if (NetworkUtils.isNetWorkConnected(getActivity())) {
				/** 有网络，从网络拿取数据，并解析和显示 **/
				getUserInfo.getStrangerConvList(DecentWorldApp.getInstance().getDwID(), handler, GET_STRANGER_CONV_LIST);
			} else {
				/** 没有网络，用空数据初始化界面 **/
				initStrangerConversationList();
			}
		}

	}

	/**
	 * 初始化陌生人会话列表
	 */
	public void initStrangerConversationList() {
		strangerConversationList.clear();
		strangerConversationMap.clear();
		// 获取陌生人会话列表
		List<ConversationList> convList = ConversationManager.getInstance().getStrangerConversationList();
		// 将数据添加到内存中
		for (ConversationList conv : convList) {
			strangerConversationList.add(conv);
			strangerConversationMap.put(conv.getDwID() + conv.getChatType(), conv);
			sort(strangerConversationList);
		}
		if (strangerRecorderAdapter != null)
			strangerRecorderAdapter.notifyDataSetChanged();
	}

	/**
	 * 根据Key值去掉对应会话列表项的未读条数
	 * 
	 * @param conversationKey
	 */
	@Subscriber(tag = NotifyByEventBus.NT_CLEAR_STRANGER_CONVERSATION_UNREAD)
	public void clearUnread(String conversationKey) {
		ConversationList conversation = strangerConversationMap.get(conversationKey);
		if (conversation != null) {
			conversation.setUnReadCount(0);
			conversation.updateUnReadCount();
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 添加会话列表
	 * 
	 * @param conversation
	 */
	@Subscriber(tag = NotifyByEventBus.NT_ADD_STRANGER_CONVERSATION)
	private void addConversation(ConversationList conversation) {
		conversation.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
		strangerConversationMap.put(conversation.getDwID() + DWMessage.CHAT_TYPE_SINGLE, conversation);
		strangerConversationList.add(conversation);
		conversation.save();
		sort(strangerConversationList);
		strangerRecorderAdapter.notifyDataSetChanged();
	}

	/**
	 * 根据Key值删除对应的会话列表
	 * 
	 * @param key
	 */
	@Subscriber(tag = NotifyByEventBus.NT_DELETE_STRANGER_CONVERSATION)
	private void deleteConversation(String key) {
		ConversationList conversation = strangerConversationMap.get(key);
		if (conversation != null) {
			strangerConversationList.remove(conversation);
			strangerConversationMap.remove(key);
			ConversationList.deleteConversation(conversation.getDwID(), DWMessage.CHAT_RELATIONSHIP_STRANGER);

			sort(strangerConversationList);
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 接收到消息后更新会话列表
	 * 
	 * @param msg
	 *            消息体和附加消息
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_STRANGER_CONVERSATION)
	public void refreshConversation(MsgAndInfo msgAndInfo) {
		ConversationManager.getInstance().refreshStrangerConvByMsg(msgAndInfo, strangerConversationList, strangerConversationMap);
		if (strangerRecorderAdapter != null) {
			// 排序
			Collections.sort(strangerConversationList);
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 接收通知初始化会话列表
	 * 
	 * @param tag
	 */
	@Subscriber(tag = NotifyByEventBus.NT_INIT_STRANGER_CONVERSATION)
	public void initStrangerConversationList(String tag) {
		LogUtils.i("bm", tag);
		initStrangerConversationList();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onCommomPromtClick(View view) {
		ConversationList info = (ConversationList) mCommonPromptDialogFragment.getObject();
		Intent intent = new Intent(getActivity(), ChatActivity_.class);
		intent.putExtra(ChatActivity.OTHER_ID, info.getDwID());
		intent.putExtra(ChatActivity.OTHER_NICKNAME, info.getTitle());
		intent.putExtra(ChatActivity.CHAT_TYPE, info.getChatType());
		intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, info.getChatRelationship());
		intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(info.getWorth()));
		startActivity(intent);
	}

	/**
	 * 排序
	 * 
	 * @param strangerConversationList
	 */
	private void sort(List<ConversationList> strangerConversationList) {
		if (strangerConversationList != null && strangerConversationList.size() > 0)
			Collections.sort(strangerConversationList);
	}
}
