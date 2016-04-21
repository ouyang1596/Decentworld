/**
 * 
 */
package cn.sx.decentworld.fragment.stranger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.adapter.ConversationListAdapter2;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.engine.ConversationEngine;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.FileUtils;

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
	private static final String TAG = "StrangerMessageFragment";
	@ViewById(R.id.lv_stranger_message)
	ListView lvStrnagerMessage;
	private static final int GET_STRANGER_CONV_LIST = 1;
	@Bean
	GetUserInfo getUserInfo;
	// 会话列表适配器
	private ConversationListAdapter2 strangerRecorderAdapter;
	private CommomPromptDialogFragment mCommonPromptDialogFragment;
	private List<ConversationList> data;

	@AfterViews
	public void init() {
		EventBus.getDefault().register(this);
		data = new ArrayList<ConversationList>();
		initData(data);
		initListView(data);
	}

	// private void initData()
	// {
	// // 初始化陌生人会话列表
	// ConversationEngine.getInstance().initStrangerConv(getActivity(), new
	// GetConvListCallback()
	// {
	// @Override
	// public void onCompleted(List<ConversationList> data)
	// {
	// initListView(data);
	// }
	// });
	// }

	/**
	 * 初始化会话列表数据,onResume中也会调用
	 */
	private void initData(List<ConversationList> data) {
		List<ConversationList> temp = ConversationEngine.getInstance().getConv();
		data.clear();
		for (ConversationList conv : temp) {
			if (conv.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
				data.add(conv);
			}
			Collections.sort(data);
		}
		if (strangerRecorderAdapter != null) {
			strangerRecorderAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 初始化陌生人会话列表
	 */
	private void initListView(final List<ConversationList> data) {
		strangerRecorderAdapter = new ConversationListAdapter2(getActivity(), data);
		lvStrnagerMessage.setAdapter(strangerRecorderAdapter);
		lvStrnagerMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (FileUtils.getPromptStatus(StrangerMessageFragment.this.getActivity(), Constants.STRANGER) == CommomPromptDialogFragment.STRANGER) {
					ConversationList info = strangerRecorderAdapter.getItem(position);
					Intent intent = new Intent(getActivity(), ChatActivity_.class);
					// intent.putExtra(ChatActivity.OTHER_ID, info.getDwID());
					// intent.putExtra(ChatActivity.OTHER_NICKNAME,
					// info.getTitle());
					// intent.putExtra(ChatActivity.CHAT_TYPE,
					// info.getChatType());
					// intent.putExtra(ChatActivity.CHAT_RELATIONSHIP,
					// info.getChatRelationship());
					// intent.putExtra(ChatActivity.OTHER_WORTH,
					// Float.valueOf(info.getWorth()));

					LaunchChatEntity entity = new LaunchChatEntity(info.getDwID(), info.getTitle(),
							Float.valueOf(info.getWorth()), info.getChatType(), info.getChatRelationship(), info.getUserType());
					intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);
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
	}

	/**
	 * 重新加载好友会话列表
	 */
	@Subscriber(tag = NotifyByEventBus.NT_RE_LOAD_STRANGER_CONV)
	public void reLoadStrangerConv(String casue) {
		LogUtils.i(TAG, "重新加载陌生人会话列表,cause:" + casue);
		initData(data);
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
		// intent.putExtra(ChatActivity.OTHER_ID, info.getDwID());
		// intent.putExtra(ChatActivity.OTHER_NICKNAME, info.getTitle());
		// intent.putExtra(ChatActivity.CHAT_TYPE, info.getChatType());
		// intent.putExtra(ChatActivity.CHAT_RELATIONSHIP,
		// info.getChatRelationship());
		// intent.putExtra(ChatActivity.OTHER_WORTH,
		// Float.valueOf(info.getWorth()));

		LaunchChatEntity entity = new LaunchChatEntity(info.getDwID(), info.getTitle(), Float.valueOf(info.getWorth()),
				info.getChatType(), info.getChatRelationship(), info.getUserType());
		intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		// 刷新数据
		initData(data);
	}
}
