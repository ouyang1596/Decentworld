/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.json.JSONException;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.CRMessageAdapter;
import cn.sx.decentworld.adapter.ChatRoomImgListAdapter;
import cn.sx.decentworld.adapter.ExpressionAdapter;
import cn.sx.decentworld.adapter.ExpressionPagerAdapter;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.OccupantList;
import cn.sx.decentworld.bean.manager.DWMMessageManager;
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.SmileUtils;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.ChatComponent;
import cn.sx.decentworld.component.ui.ChatRoomChatComponent;
import cn.sx.decentworld.manager.ChatRoomManager;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.network.request.GetRoomInfo;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.request.SetRoomInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.HttpDownloader;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.XmppHelper;
import cn.sx.decentworld.widget.CircularImage;
import cn.sx.decentworld.widget.ExpandGridView;
import cn.sx.decentworld.widget.HorizontalListView;
import cn.sx.decentworld.widget.PasteEditText;
import cn.sx.decentworld.widget.RecorderButton;
import cn.sx.decentworld.widget.RecorderButton.AudioFinishedRecordeListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: ChatActivity.java
 * @Description: 聊天界面
 * @author: yj
 * @date: 2015年7月8日 下午12:42:05
 */
@EActivity(R.layout.activity_chat_room_chat)
public class ChatRoomChatActivity extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG = "ChatRoomChatActivity";
	public static final int REQUEST_CODE_LOCAL = 19;
	private static final int REQUEST_CODE_MAP = 4;
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	@Bean
	ChatRoomChatComponent chatRoomChatComponent;
	// 对键盘的操作
	@Bean
	KeyboardComponent KeyboardComponent;
	// 选择相片
	@Bean
	ChoceAndTakePictureComponent choceAndTakePictureComponent;
	@ViewById(R.id.iv_fixed)
	ImageView ivFixed;
	@ViewById(R.id.chat_room_imgList)
	HorizontalListView chat_room_imgList;
	private List<String> imageList;
	private ChatRoomImgListAdapter adapter;
	// 仅主持人头像
	@ViewById(R.id.civ_chat_room_chat_owner_icon)
	CircularImage civ_chat_room_chat_owner_icon;
	// 聊天界面背景
	@ViewById(R.id.activity_chat_bg)
	ImageView activity_chat_bg;
	/** 底部所有按钮 start **/
	@ViewById(R.id.btn_set_mode_voice)
	Button btn_set_mode_voice;
	@ViewById(R.id.edittext_layout)
	RelativeLayout edittext_layout;
	@ViewById(R.id.et_sendmessage)
	PasteEditText mEditTextContent;
	@ViewById(R.id.iv_emoticons_normal)
	ImageView iv_emoticons_normal;
	@ViewById(R.id.iv_emoticons_checked)
	ImageView iv_emoticons_checked;
	@ViewById(R.id.btn_more)
	Button btnMore;

	@ViewById(R.id.btn_set_mode_keyboard)
	Button buttonSetModeKeyboard;
	@ViewById(R.id.btn_press_to_speak)
	RecorderButton buttonPressToSpeak;
	@ViewById(R.id.btn_send)
	Button buttonSend;

	@ViewById(R.id.more)
	LinearLayout more;
	@ViewById(R.id.ll_face_container)
	LinearLayout emojiIconContainer;
	@ViewById(R.id.ll_btn_container)
	LinearLayout btnContainer;
	/** 底部所有按钮 end **/
	// 消息列表
	@ViewById(R.id.lv_chat_room_list)
	PullToRefreshListView listView;
	// 表情
	@ViewById(R.id.vPager)
	ViewPager expressionViewpager;
	private List<String> reslist;
	private Drawable[] micImages;// 录音时的动画资源

	private FragmentManager fm;
	private ClipboardManager clipboard;
	private PowerManager.WakeLock wakeLock;
	private InputMethodManager manager;

	private CRMessageAdapter dwMessageAdapter;
	private List<DWMessage> listMsg = new ArrayList<DWMessage>();

	private String dwID;
	private int curNum;
	private String role;
	public static String roomID;
	private String roomBackground;
	private String roomNotice;
	private String roomOwnerNickName;
	private String roomWealth;
	private String chargeAmount = "";// 聊天室收费金额
	private String subjectName;
	private String ownerIcon;

	public static String ownerID;
	private List<OccupantList> lists;
	private OccupantList owner;// 主持人信息；

	@Bean
	GetUserInfo getUserInfo;
	@Bean
	SetRoomInfo setRoomInfo;
	@Bean
	GetRoomInfo getRoomInfo;
	@Bean
	ChatComponent chatComponent;
	private int chatType;

	private ChatRoomManager chatRoomManager;

	@AfterViews
	void init()
	{
		EventBus.getDefault().register(this);
		chatRoomManager = new ChatRoomManager(this);
		parseData();
		initData();
		initExpression();
		/**
		 * 语音接收按钮完成收音的监听
		 */
		buttonPressToSpeak.setAudioFishedRecorde(new AudioFinishedRecordeListener()
		{
			@Override
			public void FinishedRecordeListener(Float mTime, String filePath)
			{
				// 构造Message
				Message message = new Message();
				LogUtils.i(TAG, "发送一条语音，Message.packetID=" + message.getPacketID());
				// 构造DWMessage
				DWMessage dwMessage = new DWMessage(DWMessage.VOICE , DWMessage.SEND);
				dwMessage.setTo(roomID);
				dwMessage.setChatType(DWMessage.CHAT_TYPE_MULTI);
				dwMessage.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
				dwMessage.setTxtMsgID(message.getPacketID());
				dwMessage.setUri(filePath);
				dwMessage.ifFromNet = 1;
				dwMessage.setVoiceTime(mTime);
				dwMessage.save();
				// 处理界面
				refreshListView(dwMessage);
				hideKeyboard();
				// 发送语音
				sendAudio(mTime, filePath, dwMessage.getTxtMsgID());
			}
		});
		chatRoomChatComponent.initVoiceIcon(micImages);
		setUpView();
		initImageList();
		initListView(listView);
		initEditText(dwID);
		int count = DWMessage.getRoomMsgCount(roomID);
		LogUtils.i(TAG, "fromDwID = " + dwID + ",toDwID = " + roomID + "/n数据库中的聊天室消息条数为：" + count + "条");
		// listView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// final int position1, long arg3) {
		// final int position = position1 - 1;
		// // 消息类型位语音类型
		// if (listMsg.get(position).getMessageType() == DWMessage.VOICE) {
		// LogUtils.i(TAG, "listView.setOnItemClickListener 消息类型位语音类型");
		// if (!CommUtil.isBlank(listMsg.get(position).getUri())) {
		// // 让其他正在播放的语音动画停止
		// final View v;
		//
		// v = arg1.findViewById(R.id.iiv_voice);
		// // 消息接受成功情况
		// if (v != null) {
		// if (listMsg.get(position).getSendSuccess() == 1) {
		// if (listMsg.get(position).getDirect() == DWMessage.RECEIVE) {
		// v.setBackgroundResource(R.anim.voice_from_icon);
		// } else {
		// v.setBackgroundResource(R.anim.voice_to_icon);
		// }
		// // 播放声音,其中一个参数为监听声音播放完毕
		// MediaManager.startLocal(mContext,
		// listMsg.get(position).getUri(),
		// new OnCompletionListener() {
		//
		// @Override
		// public void onCompletion(
		// MediaPlayer mp) {
		// if (listMsg.get(position)
		// .getDirect() == DWMessage.RECEIVE) {
		// v.setBackgroundResource(R.drawable.chatfrom_voice_playing);
		// } else {
		// v.setBackgroundResource(R.drawable.chatto_voice_playing);
		// }
		// }
		// }, new OnExceptionListener() {
		//
		// @Override
		// public void onException(Exception e) {
		//
		// }
		// });
		// // }
		// // 播放动画
		// AnimationDrawable ani = (AnimationDrawable) v
		// .getBackground();
		// ani.start();
		// } else {
		// LogUtils.i("点击语音", "语音消息未成功接收或发送");
		// }
		// } else {
		// LogUtils.i("点击语音", "v=null");
		// }
		//
		// } else {
		// toast.show("语音路径为空");
		// }
		// }
		// }
		// });
		// 滚动到最后一项
		listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
		// 关闭软键盘
		KeyboardComponent.dismissKeyboard(mEditTextContent);
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		chatType = DWMessage.CHAT_TYPE_MULTI;
		fm = getSupportFragmentManager();
		dwID = DecentWorldApp.getInstance().getDwID();
		// 显示聊天室收费标准
		if (chargeAmount.equals("0.0"))
		{
			titleBar.setTitleBar("返回", subjectName + "(" + UserInfoManager.getUserInfoInstance().getWorth() + ")", roomWealth);
		}
		else
		{
			titleBar.setTitleBar("返回", subjectName + "(" + chargeAmount + ")", roomWealth);
		}
	}

	/**
	 * 解析服务器返回的Jsons数据
	 */
	private void parseData()
	{
		String json_data = getIntent().getStringExtra("json_data");
		getRoomInfo(json_data);
		lists = getRoomMemberInfo(json_data);
		// 判断是主持人是否在线
		JSONObject jsonObject = JSONObject.parseObject(json_data);
		if (CommUtil.isNotBlank(jsonObject.getString("owner")))
		{
			owner = JsonUtils.json2Bean(jsonObject.getString("owner"), OccupantList.class);
		}
	}

	/**
	 * 获得该聊天室信息
	 */
	private void getRoomInfo(String json)
	{
		JSONObject jsonObject = JSONObject.parseObject(json);
		roomWealth = jsonObject.getString("roomWealth");
		roomID = jsonObject.getString("roomID");
		subjectName = jsonObject.getString("subjectName");
		roomNotice = jsonObject.getString("roomNotice");
		roomBackground = jsonObject.getString("roomBackground");
		ownerIcon = jsonObject.getString("ownerIcon");
		roomOwnerNickName = jsonObject.getString("roomOwnerNickName");
		role = jsonObject.getString("role");
		curNum = jsonObject.getIntValue("curNum");
		chargeAmount = jsonObject.getString("chargeAmount");
		ownerID = jsonObject.getString("ownerID");
	}

	/**
	 * 解析数据获得聊天室成员信息
	 */
	private List<OccupantList> getRoomMemberInfo(String json)
	{
		String json_data = getIntent().getStringExtra("json_data");
		JSONObject jsonObject = JSONObject.parseObject(json_data);
		JSONArray jsonArray = jsonObject.getJSONArray("occupantList");
		String json1 = jsonObject.getString("occupantList");
		List<OccupantList> lists = (List<OccupantList>) JsonUtils.json2BeanArray(json1, OccupantList.class);
		for (OccupantList occupantList : lists)
		{
			occupantList.setRoomID(roomID);
			occupantList.save();
		}
		return lists;
	}

	/**
	 * 初始化文本框里面的表情
	 */
	private void initExpression()
	{
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
		reslist = chatRoomChatComponent.getExpressionRes(35);
		// reslist = getExpressionRes(35);
		List<View> views = new ArrayList<View>();
		View gv1 = getGridChildView(1);
		View gv2 = getGridChildView(2);
		views.add(gv1);
		views.add(gv2);
		expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
		edittext_layout.requestFocus();
		mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus)
				{
					edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_active);
				}
				else
				{
					edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
				}

			}
		});
		mEditTextContent.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_active);
				more.setVisibility(View.GONE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.GONE);

				// 滚动到最后一项
				listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
				// 打开软键盘
				KeyboardComponent.openKeybord(mEditTextContent);
			}
		});
		// 监听文字框
		mEditTextContent.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				if (!TextUtils.isEmpty(s))
				{
					btnMore.setVisibility(View.GONE);
					buttonSend.setVisibility(View.VISIBLE);
				}
				else
				{
					btnMore.setVisibility(View.VISIBLE);
					buttonSend.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});
	}

	/**
	 * 初始化 监听和 输入管理者
	 */
	private void setUpView()
	{
		iv_emoticons_normal.setOnClickListener(this);
		iv_emoticons_checked.setOnClickListener(this);
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");

		ivFixed.setVisibility(View.VISIBLE);
		ivFixed.setOnClickListener(this);
	}

	/**
	 * 初始化主持人和成员头像并显示
	 */
	private void initImageList()
	{
		// 显示聊天室主持人的头像
		chatRoomManager.initOwnerIcon(owner, civ_chat_room_chat_owner_icon);
		// 显示聊天室成员的头像
		imageList = new ArrayList<String>();
		for (int i = 0; i < lists.size(); i++)
		{
			imageList.add(lists.get(i).getDwID());
		}
		chat_room_imgList.setVisibility(View.VISIBLE);
		adapter = new ChatRoomImgListAdapter(ChatRoomChatActivity.this , imageList);
		chat_room_imgList.setAdapter(adapter);
	}

	/**
	 * 初始化下拉刷新及显示历史消息
	 */
	private void initListView(final PullToRefreshListView listView)
	{
		loadHistoryMsg(dwID, roomID);
		dwMessageAdapter = new CRMessageAdapter(ChatRoomChatActivity.this , listMsg , fm);
		listView.setAdapter(dwMessageAdapter);
		listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
		listView.getRefreshableView().setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				hideKeyboard();
				more.setVisibility(View.GONE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.GONE);
				return false;
			}
		});
		listView.setOnRefreshListener(new OnRefreshListener<ListView>()
		{
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("~加载聊天室历史记录~");
				loadHistoryMsg(dwID, roomID);
			}
		});
	}

	// 群消息管理器
	@Bean
	DWMMessageManager dwmMessageManager;

	/**
	 * 拿取历史记录
	 * 
	 * @param dwID
	 * @param user_dwID
	 */
	private void loadHistoryMsg(String dwID, String roomID)
	{
		int count = listMsg.size();
		if (count > 0)
		{
			// 再次从本地数据库拿取10条记录，传入列表中最小想id
			long minMsgID = listMsg.get(0).getMid();
			LogUtils.i(TAG, "count = " + count + ",minMsgID=" + minMsgID);
			dwmMessageManager.manualGetRoomHistoryMsg(dwID, roomID, minMsgID);
			// 如果本地数据库提供的记录<=0条，则调用接口从服务器拿取服务器拿取，传入列表中最小想id
		}
		else
		{
			// 首次进入聊天室，聊天室的记录为空，自动从服务器拿取历史记录
			dwmMessageManager.autoGetRoomHistoryMsg(roomID);
		}
	}

	/**
	 * 更新界面
	 * 
	 * @param list
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_ROOM_HISTORY_MSG)
	public void loadHistoryMsgAndUpdataUI(List<DWMessage> list)
	{
		LogUtils.i(TAG, "更新界面,list.size =" + list.size());
		if (list.size() > 0)
		{
			listMsg.addAll(0, list);
		}
		else
		{
			LogUtils.i(TAG, "从网络上获取的数据为空，或者其它错误");
			toast.show("无历史聊天记录");
		}
		if (dwMessageAdapter != null)
		{
			Collections.sort(listMsg);
			dwMessageAdapter.notifyDataSetChanged();
		}
		listView.onRefreshComplete();
	}

	/**
	 * 获取历史记录Handle
	 */
	private Handler getMsgHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 1:// 成功
					String result = msg.obj.toString();
					LogUtils.i(TAG, "获取用户历史记录成功，内容为：" + result);
					JSONObject jsonObject = JSON.parseObject(result);
					JSONArray array = jsonObject.getJSONArray("history");
					DWMessage dwMessage;
					for (int i = 0; i < array.size(); i++)
					{
						JSONObject ob = array.getJSONObject(i);
						int messageType = ob.getIntValue("messageType");
						dwMessage = new DWMessage();
						dwMessage.setFrom(ob.getString("senderID"));
						LogUtils.i(TAG, "roomID=" + roomID);
						dwMessage.setTo(roomID);
						dwMessage.setTime(ob.getString("sendTime"));
						dwMessage.setChatType(ob.getIntValue("chatType"));
						dwMessage.setMessageType(messageType);

						if (messageType == DWMessage.TXT)
						{
							// 如果是文字，message就是body
							dwMessage.setBody(ob.getString("message"));
							dwMessage.setSendSuccess(1);
						}
						else if (messageType == DWMessage.VOICE)
						{
							// 如果是语音，需要uri,length
							String data = ob.getString("message");
							try
							{
								org.json.JSONObject object = new org.json.JSONObject(data);
								dwMessage.setUri(object.getString("uri"));
								dwMessage.setVoiceTime(Integer.valueOf(object.getString("length")));
								String fileName = Constants.HOME_PATH + Constants.AUDIO_RECEIVE_PATH + File.separator + FileUtils.generateFileName() + ".mp3";
								int send = downloadAudio(object.getString("uri"), fileName);
								dwMessage.setBody(fileName);
								dwMessage.setSendSuccess(send);
							}
							catch (Exception e)
							{

							}
						}
						else if (messageType == DWMessage.IMAGE)
						{
							// 如果是图片，需要uri,img
							try
							{
								String data = ob.getString("message");
								org.json.JSONObject object = new org.json.JSONObject(data);
								dwMessage.setUri(object.getString("uri"));
								String img = object.getString("img");
								File file = ImageUtils.AnalyticThumbnail(img);
								dwMessage.setBody(file.getAbsolutePath());
								dwMessage.setSendSuccess(1);
							}
							catch (JSONException e)
							{

							}
						}
						dwMessage.setIsRead(1);
						if (ob.getString("senderID").equals(dwID))
						{
							dwMessage.setDirect(DWMessage.SEND);
						}
						else
						{
							dwMessage.setDirect(DWMessage.RECEIVE);
						}
						dwMessage.save();
					}
					// 解析数据保存到数据库
					// 刷新完成
					listView.onRefreshComplete();
					// 并通知适配器更新
					updateMsgStatus(null);
					// initDWMessage(listMsg);
					// dwMessageAdapter.notifyDataSetChanged();
					// listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount()-1);
					// 标记
					break;
				case 2:// 失败
					LogUtils.i(TAG, "获取聊天室历史记录失败，原因为：" + "onfailure");
					String resultStr = msg.obj.toString();
					toast.show(resultStr);
					listView.onRefreshComplete();
					break;
				case 3:// 失败
					LogUtils.i(TAG, "获取聊天室历史记录失败，原因为：" + "onFailure");
					listView.onRefreshComplete();
					toast.show("网络错误");
					break;

				default:
					break;
			}
		};
	};

	/**
	 * 下载语音
	 */
	private int downloadAudio(String uri, String fileName)
	{
		// InputStream inputStream = null;
		int result = Constants.SUCC;
		if (!FileUtils.isFileExist(Constants.HOME_PATH))
		{
			FileUtils.createSDDir(Constants.HOME_PATH);
		}
		if (!FileUtils.isFileExist(Constants.HOME_PATH + Constants.AUDIO_RECEIVE_PATH))
		{
			FileUtils.createSDDir(Constants.HOME_PATH + Constants.AUDIO_RECEIVE_PATH);
		}
		result = HttpDownloader.downFile(uri, fileName);
		return result;
	}

	/**
	 * 初始化身家
	 */
	private void initEditText(String dwID)
	{
		getUserInfo.getWealth(dwID, handler, 1);
	}

	/**
	 * 处理接受到的消息的handler
	 */
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			// 编辑框显示 1200-34（身家 - 聊天室收费标准）
			String wealth = msg.obj.toString();
			if (CommUtil.isNotBlank(wealth))
			{
				if (!ownerID.equals(dwID))
				{
					if (chargeAmount.equals("0.0"))
					{
						mEditTextContent.setHint(wealth + "-");
					}
					else
					{
						mEditTextContent.setHint(wealth + "-");
					}
				}
				else
				{
					mEditTextContent.setHint("");
				}
			}
		}
	};

	/**
	 * 获取以前的消息
	 */
	private void initDWMessage(List<DWMessage> list)
	{
		list.clear();
		List<DWMessage> msgList = DWMessage.queryAll();
		for (DWMessage dwMessage : msgList)
		{
			if (dwMessage.getChatType() == DWMessage.CHAT_TYPE_MULTI && dwMessage.getTo().equals(roomID))
			{
				list.add(dwMessage);
				// Collections.sort(list);
			}
		}
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard()
	{
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
		{
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 获取表情的gridview的子view
	 * 
	 * @param i
	 * @return
	 */
	private View getGridChildView(int i)
	{
		View view = View.inflate(this, R.layout.expression_gridview, null);
		ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();
		if (i == 1)
		{
			List<String> list1 = reslist.subList(0, 20);
			list.addAll(list1);
		}
		else if (i == 2)
		{
			list.addAll(reslist.subList(20, reslist.size()));
		}
		list.add("delete_expression");
		final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this , 1 , list);
		gv.setAdapter(expressionAdapter);
		gv.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String filename = expressionAdapter.getItem(position);
				try
				{
					// 文字输入框可见时，才可输入表情
					// 按住说话可见，不让输入表情
					if (buttonSetModeKeyboard.getVisibility() != View.VISIBLE)
					{

						if (filename != "delete_expression")
						{ // 不是删除键，显示表情
							// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
							Class clz = Class.forName("cn.sx.decentworld.common.SmileUtils");
							Field field = clz.getField(filename);
							mEditTextContent.append(SmileUtils.getSmiledText(ChatRoomChatActivity.this, (String) field.get(null)));
						}
						else
						{ // 删除文字或者表情
							if (!TextUtils.isEmpty(mEditTextContent.getText()))
							{

								int selectionStart = mEditTextContent.getSelectionStart();// 获取光标的位置
								if (selectionStart > 0)
								{
									String body = mEditTextContent.getText().toString();
									String tempStr = body.substring(0, selectionStart);
									int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
									if (i != -1)
									{
										CharSequence cs = tempStr.substring(i, selectionStart);
										if (SmileUtils.containsKey(cs.toString()))
											mEditTextContent.getEditableText().delete(i, selectionStart);
										else
											mEditTextContent.getEditableText().delete(selectionStart - 1, selectionStart);
									}
									else
									{
										mEditTextContent.getEditableText().delete(selectionStart - 1, selectionStart);
									}
								}
							}

						}
					}
				}
				catch (Exception e)
				{
				}

			}
		});
		return view;
	}

	/**
	 * 点击声音图标
	 */
	@Click(R.id.btn_set_mode_voice)
	public void btn_set_mode_voice()
	{
		KeyboardComponent.dismissKeyboard(btn_set_mode_voice);
		edittext_layout.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
		btn_set_mode_voice.setVisibility(View.GONE);
		buttonSetModeKeyboard.setVisibility(View.VISIBLE);
		buttonSend.setVisibility(View.GONE);
		btnMore.setVisibility(View.VISIBLE);
		buttonPressToSpeak.setVisibility(View.VISIBLE);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		btnContainer.setVisibility(View.VISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
	}

	/**
	 * 显示或隐藏图标按钮页
	 *
	 * @param view
	 */
	public void toggleMore(View view)
	{
		if (more.getVisibility() == View.GONE)
		{
			KeyboardComponent.dismissKeyboard(view);
			more.setVisibility(View.VISIBLE);
			btnContainer.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
		}
		else
		{
			if (emojiIconContainer.getVisibility() == View.VISIBLE)
			{
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.VISIBLE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
			}
			else
			{
				more.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 显示键盘图标
	 * 
	 * @param view
	 */
	public void setModeKeyboard(View view)
	{
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		edittext_layout.setVisibility(View.VISIBLE);
		more.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		btn_set_mode_voice.setVisibility(View.VISIBLE);
		// mEditTextContent.setVisibility(View.VISIBLE);
		mEditTextContent.requestFocus();
		// buttonSend.setVisibility(View.VISIBLE);
		buttonPressToSpeak.setVisibility(View.GONE);
		if (TextUtils.isEmpty(mEditTextContent.getText()))
		{
			btnMore.setVisibility(View.VISIBLE);
			buttonSend.setVisibility(View.GONE);
		}
		else
		{
			btnMore.setVisibility(View.GONE);
			buttonSend.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 显示语音图标按钮,开启语音聊天模式
	 *
	 * @param view
	 */
	private void setModeVoice(View view)
	{
		KeyboardComponent.dismissKeyboard(view);
		edittext_layout.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		buttonSetModeKeyboard.setVisibility(View.VISIBLE);
		buttonSend.setVisibility(View.GONE);
		btnMore.setVisibility(View.VISIBLE);
		buttonPressToSpeak.setVisibility(View.VISIBLE);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		btnContainer.setVisibility(View.VISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
	}

	/**
	 * 点击文字输入框
	 *
	 * @param v
	 */
	private void editClick(View v)
	{
		// listView.setSelection(listView.getCount() - 1);
		if (more.getVisibility() == View.VISIBLE)
		{
			more.setVisibility(View.GONE);
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 图标点击事件
	 * 
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{
		String st1 = getResources().getString(R.string.activity_chat_not_connect_to_server);
		int id = view.getId();
		if (id == R.id.btn_send)
		{
			String textContent = mEditTextContent.getText().toString();

			// 构造Message
			Message message = new Message(roomID + "@conference." + Constants.BACK_SERVER_NAME , Message.Type.groupchat);
			message.setFrom(dwID);
			message.setSubject("chat");

			// 构造DWMessage
			DWMessage dwMessage = new DWMessage(DWMessage.TXT , DWMessage.SEND);
			dwMessage.setTo(roomID);
			dwMessage.setChatType(DWMessage.CHAT_TYPE_MULTI);
			dwMessage.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
			dwMessage.setBody(textContent);
			dwMessage.setTxtMsgID(message.getPacketID());
			dwMessage.save();
			message.setBody(DWMessage.toJson(dwMessage));
			// 发送信息
			XmppHelper.sendMessage(message);
			// 处理界面
			refreshListView(dwMessage);
			KeyboardComponent.dismissKeyboard(mEditTextContent);
			mEditTextContent.setText("");
			LogUtils.i(TAG, "发送一条聊天室消息：" + DWMessage.toJson(dwMessage));

		}
		else if (id == R.id.btn_take_picture)
		{
			toast.show("开发中...");
			LogUtils.i(TAG, "点击了拍照");
			// 点击拍照图标
			// selectPicFromCamera();
		}
		else if (id == R.id.btn_picture)
		{
			// 点击图片图标
			selectPicFromLocal();
		}
		else if (id == R.id.btn_location)
		{
			toast.show("开发中...");
			LogUtils.i(TAG, "点击了位置");
			// 位置
			// startActivityForResult(new Intent(this , BaiduMapActivity.class),
			// REQUEST_CODE_MAP);
		}
		else if (id == R.id.iv_emoticons_normal)
		{
			// 点击显示表情框
			more.setVisibility(View.VISIBLE);
			iv_emoticons_normal.setVisibility(View.INVISIBLE);
			iv_emoticons_checked.setVisibility(View.VISIBLE);
			btnContainer.setVisibility(View.GONE);
			emojiIconContainer.setVisibility(View.VISIBLE);
			KeyboardComponent.dismissKeyboard(view);
		}
		else if (id == R.id.iv_emoticons_checked)
		{
			// 点击隐藏表情框
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			btnContainer.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
		}
		else if (id == R.id.btn_voice)
		{
			toast.show("开发中...");
			LogUtils.i(TAG, "点击了语音图标");
		}
		else if (id == R.id.btn_video)
		{
			toast.show("开发中...");
			LogUtils.i(TAG, "点击了视频图标");
		}
		else if (id == R.id.iv_fixed)
		{
			if (DecentWorldApp.ifFixed)
				DecentWorldApp.ifFixed = false;
			else
				DecentWorldApp.ifFixed = true;
			LogUtils.i(TAG, "boolean===" + DecentWorldApp.ifFixed);
		}
	}

	/**
	 * 照相获取图片
	 */
	private void selectPicFromCamera()
	{
		LogUtils.i(TAG, "从照相机选取照片");
		choceAndTakePictureComponent.takePicture();
	}

	private static final int OPEN_GALLERY = 0;

	/**
	 * 从图库获取图片
	 */
	private void selectPicFromLocal()
	{
		// LogUtils.i(TAG, "从本地选取照片");
		// if (Build.VERSION.SDK_INT < 19) {
		// choceAndTakePictureComponent.choicePicture(REQUEST_CODE_LOCAL);
		// LogUtils.i(TAG, "选择照片 sdk_version<19");
		//
		// } else {
		// // intent = new Intent(
		// // Intent.ACTION_PICK,
		// // android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// // startActivityForResult(intent, REQUEST_CODE_LOCAL);
		// choceAndTakePictureComponent.choicePicture(REQUEST_CODE_LOCAL);
		// LogUtils.i(TAG, "选择照片 sdk_version>=19");
		// }
		Intent intent = new Intent(Intent.ACTION_PICK , null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, OPEN_GALLERY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
		{
			if (requestCode == ChoceAndTakePictureComponent.TAKE_PICKTURE)
			{
				// 发送照片
				// sendPicture(choceAndTakePictureComponent.getImageName());
			}
			else if (requestCode == OPEN_GALLERY)
			{
				// 发送本地图片
				if (data != null && null != data.getData())
				{
					Uri uri = data.getData();
					String picturePath = ImageUtils.getRealFilePath(mContext, uri);
					File file = new File(picturePath);

					Bitmap bimap = ImageUtils.scalePic(file.getAbsolutePath());
					String picPath = Constants.HOME_PATH + "/temp" + ImageUtils.generateFileName() + ".png";
					ImageUtils.saveBitmap(picPath, bimap);
					file = new File(picPath);

					LogUtils.i(TAG, "照片路径为：" + file.getAbsolutePath());
					// 构造Message
					Message message = new Message();
					LogUtils.i(TAG, "Message.packetID=" + message.getPacketID());
					// 构造DWMessage
					DWMessage dwMessage = new DWMessage(DWMessage.IMAGE , DWMessage.SEND);
					dwMessage.setTo(roomID);
					dwMessage.setChatType(DWMessage.CHAT_TYPE_MULTI);
					dwMessage.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_STRANGER);
					dwMessage.setTxtMsgID(message.getPacketID());
					dwMessage.setUri(file.getAbsolutePath());
					dwMessage.setLocalUrl(file.getAbsolutePath());
					dwMessage.save();
					// 处理界面
					refreshListView(dwMessage);
					btnContainer.setVisibility(View.GONE);
					sendPicture(file, message.getPacketID());
				}
			}
			else if (requestCode == REQUEST_CODE_MAP)
			{
				// 地图
				double latitude = data.getDoubleExtra("latitude", 0);
				double longitude = data.getDoubleExtra("longitude", 0);
				String locationAddress = data.getStringExtra("address");
				if (locationAddress != null && !locationAddress.equals(""))
				{
					toggleMore(more);
					sendLocationMsg(latitude, longitude, "", locationAddress);
				}
				else
				{
					String st = getResources().getString(R.string.unable_to_get_loaction);
					Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
				}

				// 重发消息
			}
		}

	}

	private File handlerFile(String filePath)
	{
		File file = null;
		if (ImageUtils.fileLength(filePath) > 2 * 1024 * 1024)
		{
			Bitmap bitmap = ImageUtils.scalePic(filePath);
			String picPath = Constants.HOME_PATH + "/temp" + ImageUtils.generateFileName() + ".png";
			ImageUtils.saveBitmap(picPath, bitmap);
			file = new File(picPath);
		}
		else
		{
			file = new File(filePath);
		}
		return file;
	}

	/**
	 * 发送图片
	 * 
	 * @param file
	 * @param dwMessage
	 */
	private void sendPicture(File file, String txtMsgID)
	{
		LogUtils.i(TAG, "发送一条图片信息");
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("dwID", dwID);
		hashmap.put("toID", roomID);
		hashmap.put("type", chatType + "");
		hashmap.put("msgID", txtMsgID);
		// 暂时上限为一次上传十张
		File[] images = new File[]
		{ file };
		getRoomInfo.sendFileWithParams(hashmap, images, "/sendPicture", fileProgHandler, txtMsgID);
	}

	/**
	 * 发送语音
	 * 
	 * @param mTime
	 * @param FilePath
	 * @param dwMessage
	 */
	private void sendAudio(Float mTime, String FilePath, String txtMsgID)
	{
		LogUtils.i(TAG, "发送一条语音信息");
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("dwID", dwID);
		hashmap.put("toID", roomID);
		hashmap.put("type", chatType + "");
		hashmap.put("length", Math.round(mTime) + "");
		hashmap.put("msgID", txtMsgID);
		// 语音文件
		File[] images = new File[]
		{ new File(FilePath) };
		getRoomInfo.sendFileWithParams(hashmap, images, "/sendAudio", fileProgHandler, txtMsgID);
	}

	/**
	 * 更新DWMessage的传输状态
	 */
	private Handler fileProgHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			String txtMsgID = msg.obj.toString();
			DWMessage dwMessage = DWSMessageManager.queryItem(txtMsgID);
			if (dwMessage == null)
			{
				LogUtils.i(TAG, "不存在这条消息");
				return;
			}
			switch (msg.what)
			{
				case Constants.SUCC:
					dwMessage.setSendSuccess(1);// 1代表成功
					dwMessage.save();
					break;
				case Constants.FAILURE:
					dwMessage.setSendSuccess(0);// 0代表失败
					dwMessage.save();
					break;
			}
		};
	};

	/**
	 * 发送位置信息
	 *
	 * @param latitude
	 * @param longitude
	 * @param imagePath
	 * @param locationAddress
	 */
	private void sendLocationMsg(double latitude, double longitude, String imagePath, String locationAddress)
	{
		toast.show("发送地理位置待完善");
		LogUtils.i(TAG, "发送地理位置待完善");
	}

	/**
	 * 离开聊天室时通知服务器
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (DecentWorldApp.ifFixed)
		{
			return;
		}
		LogUtils.i(TAG, "onDestroy,执行退出聊天室的网络请求");
		setRoomInfo.leaveChatRoom(dwID, roomID);
		// 删除所有聊天室成员
		OccupantList.deleteAll();
		// 退出时删除聊天记录·
		dwmMessageManager.deleteHistory(roomID);
		EventBus.getDefault().unregister(this);
	}

	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}

	/**
	 * 接受到PacketListenerService传递过来的【更新身家】通知
	 * 
	 * @param message
	 * 
	 *            聊天室的总资产是在接收到一条聊天室成员发送过来的消息时进行更新，资产值包含在消息中；
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_WEALTH)
	public void updateWealth(Message message)
	{

		LogUtils.i(TAG, "接收到一条聊天室更新身家通知,message.getBody=" + message.getBody());
		// 编辑框显示 1200-34（身家 - 聊天室收费标准）
		LogUtils.i(TAG, "chargeAmount=" + chargeAmount);
		if (message.getSubject().equals("wealth"))
		{
			// 1.根据服务器回执，更新身家
			JSONObject jsonObject = JSON.parseObject(message.getBody());
			int chatType = jsonObject.getIntValue("chatType");
			if (chatType == DWMessage.CHAT_TYPE_MULTI)
			{
				String wealth = jsonObject.getString("wealth");
				long mid = jsonObject.getLongValue("id");
				LogUtils.i(TAG, "收到一条subject = wealth的消息，wealth=" + wealth + "，mid=" + mid + "，chatType=" + chatType);
				if (CommUtil.isNotBlank(wealth))
				{
					if (!ownerID.equals(dwID))
					{
						if (chargeAmount.equals("0.0"))
						{
							mEditTextContent.setHint(wealth + "-");
						}
						else
						{
							mEditTextContent.setHint(wealth + "-");
						}
					}
					else
					{
						mEditTextContent.setHint("");
					}
				}
				// 2.根据服务器回执，更新UI
				updateUiWithWealthArk(message.getPacketID(), mid);
			}
		}
	}

	/**
	 * 根据openfire消息id，更新UI，即更新内存中的数据
	 * 
	 * @param txtMsgID
	 */
	public void updateUiWithWealthArk(String txtMsgID, long mId)
	{
		LogUtils.i(TAG, "根据openfire消息id，更新UI，即更新内存中的数据");
		for (int i = listMsg.size() - 1; i >= 0; i--)
		{
			if (listMsg.get(i).getTxtMsgID().equals(txtMsgID))
			{
				LogUtils.i(TAG, "txtMsgID=" + txtMsgID + ",mId=" + mId + ",i=" + i);
				listMsg.get(i).setSendSuccess(1);
				listMsg.get(i).setMid(mId);
				dwMessageAdapter.notifyDataSetChanged();
				return;
			}
		}
		LogUtils.i(TAG, "数据库中无此消息");
	}

	/**
	 * 接受到PacketListenerService传递过来的消息通知 包括文字、语音、图片
	 */
	@Subscriber(tag = NotifyByEventBus.NT_CHAT_ROOM_MSG)
	public void revRoomMsg(DWMessage dwMessage)
	{
		int messageType_ = dwMessage.getMessageType();
		if (messageType_ == DWMessage.TXT)
		{
			LogUtils.i(TAG, "#接收到一条聊天室文字信息的通知");
		}
		else if (messageType_ == DWMessage.VOICE)
		{
			LogUtils.i(TAG, "#接收到一条聊天室语音信息的通知");
		}
		else if (messageType_ == DWMessage.IMAGE)
		{
			LogUtils.i(TAG, "#接收到一条聊天室图片信息的通知");
		}
		if (dwMessage.getWealth() != null && !dwMessage.getWealth().equals(""))
		{
			titleBar.setRightText(dwMessage.getWealth());
		}
		refreshListView(dwMessage);
	}

	/**
	 * 添加一项数据，刷新界面，滚动到最后一项
	 * 
	 * @param dwMessage
	 */
	private void refreshListView(DWMessage dwMessage)
	{
		listMsg.add(dwMessage);
		dwMessageAdapter.notifyDataSetChanged();
		// 滚动到最后一项
		listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
		LogUtils.i(TAG, "界面更新");
	}

	/**
	 * 聊天室新成员上线通知
	 */
	@Subscriber(tag = NotifyByEventBus.NT_CHAT_ROOM_JOIN)
	public void occupanOnLine(Message message)
	{
		if (message.getSubject().equals("room_presence"))
		{
			LogUtils.i(TAG, "收到聊天室上线新通知：" + message.getBody());
			JSONObject jsonObject = JSON.parseObject(message.getBody());
			String occupan_dwID = jsonObject.getString("dwID");
			String occupan_nick = jsonObject.getString("nick");
			String occupan_roomID = jsonObject.getString("roomID");
			String occupan_worth = jsonObject.getString("worth");
			if (occupan_roomID.equals(roomID))
			{
				OccupantList occupantList = new OccupantList();
				occupantList.setRoomID(occupan_roomID);
				occupantList.setDwID(occupan_dwID);
				occupantList.setWorth(occupan_worth);
				occupantList.setUserNickName(occupan_nick);
				LogUtils.i(TAG, "dwID=" + occupan_dwID + ",nick=" + occupan_nick + ",roomID" + occupan_roomID + ",worth=" + occupan_worth);
				if (occupan_dwID.equals(ownerID))
				{
					// 使用服务器传递过来的url
					ownerIcon = jsonObject.getString("icon");
					LogUtils.i(TAG, "主持人上线，icon=" + ownerIcon);
					occupantList.setUserIcon(ownerIcon);
					ImageLoaderHelper.mImageLoader.displayImage(ownerIcon, civ_chat_room_chat_owner_icon, ImageLoaderHelper.mOptions);
					MsgNotifyManager.getInstance().roomMemberOnline("主持人", subjectName);
				}
				else
				{
					occupantList.setUserIcon(ImageUtils.getIconByDwID(occupan_dwID, ImageUtils.ICON_SMALL));
					// 普通成员通过固定格式拿取头像
					imageList.add(occupan_dwID);
					adapter.notifyDataSetChanged();
					MsgNotifyManager.getInstance().roomMemberOnline(occupan_nick, subjectName);
				}
				occupantList.save();
			}
		}
	}

	/**
	 * 聊天室成员下线通知
	 * 
	 * @param message
	 */
	@Subscriber(tag = NotifyByEventBus.NT_CHAT_ROOM_LEAVE)
	public void occupanOffLine(Message message)
	{
		if (message.getSubject().equals("room_leave"))
		{
			LogUtils.i(TAG, "收到聊天室成员下线通知：" + message.getBody());
			JSONObject jsonObject = JSON.parseObject(message.getBody());
			String occupan_roomID = jsonObject.getString("roomID");
			String occupan_dwID = jsonObject.getString("dwID");
			String occupan_nick = jsonObject.getString("nick");
			if (occupan_roomID.equals(roomID))
			{
				if (occupan_dwID.equals(ownerID))
				{
					LogUtils.i(TAG, "主持人离开");
					civ_chat_room_chat_owner_icon.setImageResource(R.drawable.default_icon);
					MsgNotifyManager.getInstance().roomMemberOffline("主持人", subjectName);
				}
				else
				{
					LogUtils.i(TAG, "非主持人离开");
					imageList.remove(occupan_dwID);
					adapter.notifyDataSetChanged();
					MsgNotifyManager.getInstance().roomMemberOffline(occupan_nick, subjectName);
				}
			}
		}
	}

	/**
	 * 更新消息状态
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_MSG_STATUS)
	public void updateMsgStatus(DWMessage dwMessage)
	{
		LogUtils.i(TAG, "更新消息状态");
		initDWMessage(listMsg);
		dwMessageAdapter.notifyDataSetChanged();
		listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
	}

	/**
	 * 身家不足，消息状态改为发送失败
	 * 
	 * @param msgID
	 *            消息ID 从最后一项向前开始遍历，当找到对应的消息ID时结束。
	 */
	@Subscriber(tag = NotifyByEventBus.NT_WEALTH_SHORTAGE)
	public void wealthShortage(String packetId)
	{
		toast.show("身家不足");
		LogUtils.i(TAG, "要查询的PacketID=" + packetId + "，总共有：" + listMsg.size());
		for (int i = listMsg.size() - 1; i >= 0; i--)
		{
			LogUtils.i(TAG, "总共有【" + listMsg.size() + "】" + "项" + "，遍历到第【" + i + "】" + "项" + ",body：" + listMsg.get(i).getBody() + "，PacketID=" + listMsg.get(i).getTxtMsgID());
			if (listMsg.get(i).getTxtMsgID().equals(packetId))
			{
				LogUtils.i(TAG, "总共有【" + listMsg.size() + "】" + "项" + "，遍历到第【" + i + "】" + "项停止");
				listMsg.get(i).setSendSuccess(0);
				dwMessageAdapter.notifyDataSetChanged();
				return;
			}
		}
		LogUtils.i(TAG, "要查询的PacketID=" + packetId + "，没有找到！");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		dwMessageAdapter.stopMediaPlayer();
	}

}
