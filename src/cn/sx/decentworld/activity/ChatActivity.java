/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.DWMessageAdapter;
import cn.sx.decentworld.adapter.DWMessageAdapter.OnMessageClickListener;
import cn.sx.decentworld.adapter.ExpressionPagerAdapter;
import cn.sx.decentworld.bean.ChatSetting;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.MsgAndInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserSessionInfo;
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.chat.ChatHelper;
import cn.sx.decentworld.chat.ChatSingleHelper;
import cn.sx.decentworld.chat.interfaces.ReSendMsgListener;
import cn.sx.decentworld.chat.interfaces.SendPictureListener;
import cn.sx.decentworld.chat.interfaces.SendVoiceListener;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.MsgProThread;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.ChatComponent;
import cn.sx.decentworld.listener.MsgProcessListener;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.SPUtils;
import cn.sx.decentworld.utils.TextUtil;
import cn.sx.decentworld.widget.PasteEditText;
import cn.sx.decentworld.widget.RecorderButton;
import cn.sx.decentworld.widget.RecorderButton.AudioFinishedRecordeListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * @ClassName: ChatActivity1.java
 * @Description: 聊天界面 进入此界面需要5个参数 1.chatType 聊天类型 2.chatRelationship
 *               (判断是否属于好友，即是否在联系人列表中) 3.otherID 对方ID
 *               4.otherNickname（如果是好友，且非匿名，
 *               则按优先级显示；如果是好友，但是为匿名，则显示随机名字；如果为陌生人，则显示默认的） 5.otherWorth; 对方身价
 * @author: cj 进入ChatActivity的入口： 1.直接点击联系人； 2.点击联系人头像，再点击会话进入ChatActivity
 *          3.匿名聊天 NearCardDetail2Activity 4.NearCardDetailActivity
 *          5.NearCardDetailForMessageActivity 6.SearchAdapter 7.CardAdapter
 * 
 * @date: 2015年12月28日 上午10:27:36
 */
@EActivity(R.layout.activity_chat)
public class ChatActivity extends BaseFragmentActivity implements OnClickListener, AudioFinishedRecordeListener,
		OnMessageClickListener, OnTouchListener, OnRefreshListener<ListView>, ReSendMsgListener {
	/**
	 * 提示变量
	 */
	public static final String TAG = "ChatActivity";
	public static final int GET_WEALTH = 1;
	public static final int REQUEST_CODE_CARD = 2;
	public static final int REQUEST_CODE_MAP = 3;
	public static final int REQUEST_CODE_OPEN_GALLERY = 4;
	public static final int REQUEST_CODE_EMPTY_HISTORY = 5;
	public static final int REQUEST_CODE_CHAT_SETTING = 6;// 启动聊天设置界面

	/** 传递参数key **/
	public static final String CHAT_TYPE = "chatType";
	public static final String CHAT_RELATIONSHIP = "chatRelationship";
	public static final String OTHER_ID = "otherID";
	public static final String OTHER_NICKNAME = "otherNickname";
	public static final String OTHER_WORTH = "otherWorth";

	/** 聊天助手 **/
	private ChatHelper chatHelper;

	/**
	 * 工具变量
	 */
	@Bean
	ToastComponent toast;
	@Bean
	KeyboardComponent KeyboardComponent;// 对键盘的操作
	@Bean
	GetUserInfo getUserInfo;
	@Bean
	DWSMessageManager dwsMessageManager;
	@Bean
	ChoceAndTakePictureComponent choceAndTakePictureComponent; // 选择相片
	@Bean
	ChatComponent chatComponent;

	private ClipboardManager clipboard;
	private InputMethodManager manager;

	/**
	 * 对方的信息
	 */
	private int chatType;// 聊天类型(默认为单聊)
	private int chatRelationship = DWMessage.CHAT_RELATIONSHIP_FRIEND;// 聊天关系（默认为好友）
	private String otherID = "";// 对方ID
	private String otherNickname = "";// 对方昵称
	private float otherWorth = -1;// 对方身价
	private String otherIcon = "";// 对方头像

	/**
	 * 当前用户信息
	 */
	private String dwID = "";// 当前用户ID

	/**
	 * 界面资源
	 */
	/** 聊天界面背景 **/
	@ViewById(R.id.activity_chat_bg)
	ImageView iv_chat_bg;
	/** 消息列表 **/
	@ViewById(R.id.list)
	PullToRefreshListView listView;

	/** 切换成语音模式 **/
	@ViewById(R.id.btn_set_mode_voice)
	Button btn_set_mode_voice;
	/** 编辑控件 **/
	@ViewById(R.id.edittext_layout)
	RelativeLayout edittext_layout;
	/** 消息编辑框 **/
	@ViewById(R.id.et_sendmessage)
	PasteEditText mEditTextContent;
	/** 表情1 **/
	@ViewById(R.id.iv_emoticons_normal)
	ImageView iv_emoticons_normal;
	/** 表情2 **/
	@ViewById(R.id.iv_emoticons_checked)
	ImageView iv_emoticons_checked;
	/** 键盘模式 **/
	@ViewById(R.id.btn_set_mode_keyboard)
	Button btn_set_mode_keyboard;
	/** 按住说话按钮 **/
	@ViewById(R.id.btn_press_to_speak)
	RecorderButton buttonPressToSpeak;
	/** 发送按钮 **/
	@ViewById(R.id.btn_send)
	Button btn_send;
	/** + **/
	@ViewById(R.id.btn_more)
	Button btn_more;
	/** 总容器 **/
	@ViewById(R.id.more)
	LinearLayout more;
	/** 表情容器 **/
	@ViewById(R.id.ll_face_container)
	LinearLayout emojiIconContainer;
	/** 附加功能容器 **/
	@ViewById(R.id.ll_btn_container)
	LinearLayout btnContainer;
	// 表情
	@ViewById(R.id.vPager)
	ViewPager expressionViewpager;

	// 聊天型
	private FragmentManager fragmentManager;
	private DWMessageAdapter adapter;
	private List<DWMessage> listMsg;
	//
	private ChatManager chatManager;
	private Chat chat;

	// 其它
	private int messagePosition;
	public PopupWindow mPop;
	public View transmitView;

	/**
	 * 标题栏资源
	 */
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_right_btn)
	ImageView main_header_right_btn;
	@ViewById(R.id.main_header_right_tv)
	TextView main_header_right_tv;

	/**
	 * 界面入口
	 */
	@AfterViews
	void init() {
		LogUtils.i("bm", "time-0---" + System.currentTimeMillis());
		new EnterAsync().execute();
	}

	/**
	 * 解析启动Activity传递过来的5个参数
	 */
	private void parseIntent() {
		/** 聊天类型(默认为单聊) **/
		chatType = getIntent().getIntExtra("chatType", DWMessage.CHAT_TYPE_SINGLE);
		/** 聊天关系（默认为好友） **/
		chatRelationship = getIntent().getIntExtra("chatRelationship", DWMessage.CHAT_RELATIONSHIP_FRIEND);
		/** 对方ID **/
		otherID = getIntent().getStringExtra("otherID");
		/** 对方昵称 **/
		otherNickname = getIntent().getStringExtra("otherNickname");
		/** 对方身价 **/
		otherWorth = getIntent().getFloatExtra("otherWorth", -1);
		LogUtils.i(TAG, "chatType=" + chatType + "\nchatRelationship=" + chatRelationship + "\nuser_dwID=" + otherID
				+ "\nuser_nickname=" + otherNickname + "\nuser_worth=" + otherWorth);
	}

	/**
	 * 初始化基本数据
	 */
	private void initData() {
		chatHelper = new ChatHelper();
		EventBus.getDefault().register(this);
		dwID = DecentWorldApp.getInstance().getDwID();
		/** 聊天对方的头像，如果为群，则为群的头像 **/
		otherIcon = ImageUtils.getIconByDwID(otherID, ImageUtils.ICON_SMALL);
		/** 获取自己的身家 **/
		getUserInfo.getWealth(dwID, mhandler, GET_WEALTH);

		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		// 管理类
		chatManager = ChatManager.getInstanceFor(XmppHelper.getConnection(null));

		listMsg = new ArrayList<DWMessage>();
		adapter = new DWMessageAdapter(ChatActivity.this, listMsg, chatType, otherNickname, otherID);
		// 设置重发监听
		adapter.setReSendMsgListener(this);
		adapter.setOnMessageClickListener(this);
	}

	/**
	 * 初始化界面控件（界面自身资源）
	 */
	private void initView() {
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
		/** 初始化表情 **/
		List<View> views = chatHelper.getExpressionData(ChatActivity.this, btn_set_mode_keyboard, mEditTextContent, 35);
		expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
		/** 编辑框控件聚焦 **/
		edittext_layout.requestFocus();
		/** 初始化消息列表控件 **/
		listView.getRefreshableView().setOnTouchListener(this);
		listView.setOnRefreshListener(this);
		listView.setAdapter(adapter);
		setListViewBottom();
		/** 初始化背景图片 **/
		ChatSetting chatSetting = ChatSetting.queryByDwID(otherID);
		if (chatSetting == null) {
			chatSetting = new ChatSetting();
			chatSetting.setUserID(DecentWorldApp.getInstance().getDwID());
			chatSetting.setOtherID(otherID);
			chatSetting.save();
		} else {
			String bgPath = chatSetting.getChatBg();
			if (CommUtil.isNotBlank(bgPath)) {
				Uri uri = Uri.parse(Constants.URI_FILE + bgPath);
				iv_chat_bg.setImageURI(uri);
			}
		}
	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		iv_emoticons_normal.setOnClickListener(this);
		iv_emoticons_checked.setOnClickListener(this);
		mEditTextContent.setOnClickListener(this);
		chatHelper.initEditText(mEditTextContent, btn_more, btn_send, edittext_layout);
		buttonPressToSpeak.setAudioFishedRecorde(this);
		btn_set_mode_voice.setOnClickListener(this);
		btn_set_mode_keyboard.setOnClickListener(this);
		main_header_left.setOnClickListener(this);
	}

	/**
	 * 初始化标题栏
	 */
	private void initTitle() {
		main_header_left.setVisibility(View.VISIBLE);
		main_header_right_btn.setVisibility(View.VISIBLE);
		main_header_right_btn.setImageResource(R.drawable.chat_top_right_selector);
		main_header_right_btn.setOnClickListener(this);
		main_header_right_tv.setVisibility(View.INVISIBLE);
		main_header_title.setText(otherNickname + "(" + otherWorth + ")");
	}

	/**
	 * 初始化编辑框(填充数据)
	 */
	private void initEditText() {

	}

	/**
	 * 获取消息历史记录
	 */
	private void loadHistoryMsg(String dwID, String otherID) {
		if (listMsg.size() > 0) {
			int count = dwsMessageManager.getMsgCount(listMsg);
			// 再次从本地数据库拿取10条记录，传入列表中最小项消息id
			long minMsgId;
			if (count > 0) {
				minMsgId = dwsMessageManager.getMid(listMsg);
			} else {
				minMsgId = -1;
			}
			LogUtils.i(TAG, "count = " + count + ",minMsgId=" + minMsgId);
			dwsMessageManager.getNextPageMsg(dwID, otherID, minMsgId, chatType);
		} else {
			// 首先从本地数据库拿取10条记录
			dwsMessageManager.getFistPageMsg(dwID, otherID, chatType);
			// 如果本地数据库提供的记录<=0条，则调用接口从服务器拿取服务器拿取
		}
	}

	/**
	 * 点击监听回调
	 */
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.et_sendmessage:
			onClickEditText();
			break;
		case R.id.main_header_right_btn:
			intent = new Intent(ChatActivity.this, ChatSettingActivity_.class);
			intent.putExtra("otherID", otherID);
			intent.putExtra("otherNick", otherNickname);
			startActivityForResult(intent, REQUEST_CODE_CHAT_SETTING);
			break;
		case R.id.btn_set_mode_voice:
			setModeVoice();
			break;
		case R.id.btn_set_mode_keyboard:
			setModeKeyboard(v);
			break;
		case R.id.iv_emoticons_normal:
			setEmotionsNormal(v);
			break;
		case R.id.iv_emoticons_checked:
			setEmotionsChecked();
			break;
		case R.id.btn_send:
			// 发送文本消息
			DWSendText(v);
			break;
		case R.id.btn_take_picture:
			// 点击拍照图标
			choceAndTakePictureComponent.takePicture();
			break;
		case R.id.btn_picture:
			// 点击图片图标
			intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent, REQUEST_CODE_OPEN_GALLERY);
			break;
		case R.id.btn_card:
			sendCard();
			break;
		case R.id.btn_location:
			sendLocationMsg();
			break;
		/** 点击导航栏的返回按钮 **/
		case R.id.main_header_left:
			hideKeyboard();
			finish();
		default:
			break;
		}

	}

	/**
	 * 发送名片
	 */
	private void sendCard() {
		// 转发名片
		toast.show("转发名片");
		// 打开联系人列表，选择联系人
		Intent intent = new Intent(ChatActivity.this, PickContactActivity_.class);
		intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
		startActivityForResult(intent, REQUEST_CODE_CARD);
	}

	/**
	 * 显示表情
	 */
	private void setEmotionsNormal(View view) {
		// 点击显示表情框
		more.setVisibility(View.VISIBLE);
		iv_emoticons_normal.setVisibility(View.INVISIBLE);
		iv_emoticons_checked.setVisibility(View.VISIBLE);
		btnContainer.setVisibility(View.GONE);
		emojiIconContainer.setVisibility(View.VISIBLE);
		KeyboardComponent.dismissKeyboard(view);
	}

	/**
	 * 隐藏表情
	 */
	private void setEmotionsChecked() {
		// 点击隐藏表情框
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		btnContainer.setVisibility(View.VISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
	}

	/**
     * 
     */
	private void sendLocationMsg() {
		// 位置
		startActivityForResult(new Intent(this, MapActivity.class), REQUEST_CODE_MAP);
	}

	/**
	 * 触发重连
	 */
	private void reConnection() {
		LogUtils.i(TAG, "断开了与Openfire连接，重连");
		// XmppHelper.reConnect();
	}

	/**
	 * 为消息添加当前时间和Token
	 * 
	 * @param dwMessage
	 */
	private void addToken(DWMessage dwMessage) {
		long time = System.currentTimeMillis();
		dwMessage.setTime(String.valueOf(time));
		String randomStr = (String) SPUtils.get(mContext, SPUtils.randomStr, "");
		String token = AES.encode(dwID + time, randomStr);
		dwMessage.setToken(token);
	}

	/**
	 * 设置语言模式
	 */
	private void setModeVoice() {
		KeyboardComponent.dismissKeyboard(btn_set_mode_voice);
		edittext_layout.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
		btn_set_mode_voice.setVisibility(View.GONE);
		btn_set_mode_keyboard.setVisibility(View.VISIBLE);
		btn_send.setVisibility(View.GONE);
		btn_more.setVisibility(View.VISIBLE);
		buttonPressToSpeak.setVisibility(View.VISIBLE);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		btnContainer.setVisibility(View.VISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
	}

	/**
	 * 设置键盘模式
	 */
	private void setModeKeyboard(View view) {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		edittext_layout.setVisibility(View.VISIBLE);
		more.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		btn_set_mode_voice.setVisibility(View.VISIBLE);
		mEditTextContent.requestFocus();
		buttonPressToSpeak.setVisibility(View.GONE);
		if (TextUtils.isEmpty(mEditTextContent.getText())) {
			btn_more.setVisibility(View.VISIBLE);
			btn_send.setVisibility(View.GONE);
		} else {
			btn_more.setVisibility(View.GONE);
			btn_send.setVisibility(View.VISIBLE);
		}
	}

	/**
     * 
     */
	private void onClickEditText() {
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_active);
		more.setVisibility(View.GONE);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
		btnContainer.setVisibility(View.GONE);
		setListViewBottom();
	}

	/**
	 * lauchMode= singleTask 如果该实例已经存在，那么再次启动时就会调用onNewIntent
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			setIntent(intent);
			getChatBgImage(); // 设置聊天背景
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (null != adapter) {
			adapter.stopMediaPlayer();
		}
	}

	/**
	 * 销毁窗口
	 */
	@Override
	protected void onDestroy() {
		LogUtils.i(TAG, "onDestroy");
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
			EventBus.getDefault().post(otherID + chatType, NotifyByEventBus.NT_CLEAR_CONVERSATION_UNREAD);
			LogUtils.i(TAG, "onDestroy and send a notification to ChatFragment.");
		} else if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
			EventBus.getDefault().post(otherID + chatType, NotifyByEventBus.NT_CLEAR_STRANGER_CONVERSATION_UNREAD);
			LogUtils.i(TAG, "onDestroy and send a notification to StrangerFragment.");
		}
		/** 设置为可以显示通知 **/
		MsgNotifyManager.getInstance().NotifyStart(MsgNotifyManager.NOTIFY_TYPE_SINGLE);
	}

	/**
	 * 将消息列表滚动 到最后一项
	 */
	private void setListViewBottom() {
		listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
	}

	/**
	 * 按住说话完成回调监听
	 */
	@Override
	public void FinishedRecordeListener(Float mTime, String filePath) {
		// 构造DWMessage
		File voiceFile = new File(filePath);
		DWSendVoice(voiceFile, mTime);
	}

	Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_WEALTH:
				chatHelper.initEditTextData(mEditTextContent, chatType, chatRelationship, msg.obj.toString());
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onMessageClick(View view) {
		if (null == view) {
			return;
		}
		messagePosition = (Integer) view.getTag(Constants.ITEM_POSITION);
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_chatcontent:
			// showPopWindow(mContext, view, mPop,
			// transmitView,mOnClicklistener);
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			if (null == mPop || null == transmitView) {
				transmitView = View.inflate(mContext, R.layout.layout_copy_transmit, null);
				TextView tvCopy = (TextView) transmitView.findViewById(R.id.tv_copy);
				tvCopy.setOnClickListener(mOnClicklistener);
				TextView tvTransmit = (TextView) transmitView.findViewById(R.id.tv_transmit);
				tvTransmit.setOnClickListener(mOnClicklistener);
				mPop = new PopupWindow(transmitView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
						true);
				mPop.setBackgroundDrawable(new BitmapDrawable());
				transmitView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
				mPop.setOutsideTouchable(true);
			}
			int popupWidth = transmitView.getMeasuredWidth();
			int popupHeight = transmitView.getMeasuredHeight();
			mPop.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1]
					- popupHeight);

			break;
		case R.id.iv_sendPicture:
			intent = new Intent(mContext, PickContactActivity_.class);
			intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
			startActivityForResult(intent, Constants.IMAGE_REQUEST_CODE);
			break;
		case R.id.iv_voice:
			intent = new Intent(mContext, PickContactActivity_.class);
			intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
			startActivityForResult(intent, Constants.AUDIO_REQUEST_CODE);
			break;
		}
	}

	private OnClickListener mOnClicklistener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.tv_copy:
				DWMessage item = adapter.getItem(messagePosition);
				String message = item.getBody();
				if (CommUtil.isNotBlank(message)) {
					TextUtil.copy(message, mContext);
					toast.show("已复制到剪切板");
				}
				break;
			case R.id.tv_transmit:
				Intent intent = new Intent(mContext, PickContactActivity_.class);
				intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
				startActivityForResult(intent, Constants.TEXT_REQUEST_CODE);
				break;
			}
			PopupWindow v = mPop;
			LogUtils.i(TAG, "");
			mPop.dismiss();
		}
	};

	/**
	 * 触摸界面回调监听
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		hideKeyboard();
		more.setVisibility(View.GONE);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
		btnContainer.setVisibility(View.GONE);
		return false;
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 下拉刷新回调监听
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("单聊历史记录");
		LogUtils.i(TAG, "开始获取历史聊天记录...begin");
		loadHistoryMsg(dwID, otherID);
		LogUtils.i(TAG, "开始获取历史聊天记录...end");
	}

	/**
	 * 获取到历史记录，更新界面
	 * 
	 * @param list
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_SINGLE_HISTORY_MSG)
	public void loadHistoryMsgAndUpdataUI(List<DWMessage> list) {
		LogUtils.i(TAG, "更新界面,list.size =" + list.size());
		int temp = listMsg.size();
		if (list.size() > 0) {
			// 排序
			Collections.sort(list);
			listMsg.addAll(0, list);
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}

		} else {
			LogUtils.i(TAG, "从网络上获取的数据为空，或者其它错误");
		}
		listView.onRefreshComplete();
		/** 加载历史记录时，跳到第一项 **/
		int t = listMsg.size() - 1 - temp;
		listView.getRefreshableView().setSelection(t);
	}

	/**
	 * 点击文字输入框
	 *
	 * @param v
	 */
	public void editClick(View v) {
		if (more.getVisibility() == View.VISIBLE) {
			more.setVisibility(View.GONE);
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 发送文字信息
	 */
	private void sendText(Message message) {
		Chat chat = chatManager.createChat(otherID + Constants.SERVER_NAME, null);
		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toast.show("没有连接到服务器");
		}
	}

	/**
	 * 发送消息失败后将内存中的消息状态设置为发送失败
	 */
	public void sendFailure(String txtMsgID) {
		LogUtils.i(TAG, "根据openfire消息id，更新UI，即更新内存中的数据");
		for (int i = listMsg.size() - 1; i >= 0; i--) {
			if (listMsg.get(i).getTxtMsgID().equals(txtMsgID)) {
				listMsg.get(i).setSendSuccess(0);
				listMsg.get(i).setMid(0);
				adapter.notifyDataSetChanged();
				return;
			}
		}
	}

	/**
	 * 添加一项数据，刷新界面，滚动到最后一项
	 * 
	 * @param dwMessage
	 */
	private void refreshListView(DWMessage dwMessage) {
		if (dwMessage.getMessageType() == DWMessage.TXT) {
			LogUtils.i(TAG, "发送一条文字消息为" + DWMessage.toJson(dwMessage));
		} else if (dwMessage.getMessageType() == DWMessage.VOICE) {
			LogUtils.i(TAG, "发送一条语音消息为" + DWMessage.toJson(dwMessage));
		} else if (dwMessage.getMessageType() == DWMessage.IMAGE) {
			LogUtils.i(TAG, "发送一条图片消息为" + DWMessage.toJson(dwMessage));
		} else if (dwMessage.getMessageType() == DWMessage.CARD) {
			LogUtils.i(TAG, "发送一张名片消息为" + DWMessage.toJson(dwMessage));
		}
		/**  **/
		listMsg.add(dwMessage);
		adapter.notifyDataSetChanged();
		listView.getRefreshableView().setSelection(listView.getRefreshableView().getCount() - 1);
		/** 启动一个线程去检查是否收到回执，即success == 2，那么设置为 1 **/

		if (dwMessage.getDirect() == DWMessage.SEND) {
			// 将消息传入到线程中，该线程等等4s去检查是否有消息回执，如果4s后还没有回执，则设置为1；
			LogUtils.i(TAG, "线程外：dwMessage.hashCode=" + dwMessage.hashCode());
			MsgProThread msgProThread = new MsgProThread(dwMessage, new MsgProcessListener() {
				@Override
				public void finish() {
					msgProHandler.sendEmptyMessage(1);
				}
			});
			msgProThread.setName(dwMessage.hashCode() + "");
			msgProThread.start();
		}
	}

	/**
	 * 线程处理完消息后更新界面
	 */
	Handler msgProHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		}
	};

	/**
	 * 更新会话列表
	 * 
	 * @param dwMessage
	 */
	private void refreshConversation(DWMessage dwMessage) {
		/** 构建一个UserSessionInfo对象 **/
		UserSessionInfo userSessionInfo = new UserSessionInfo(otherID, otherNickname, otherWorth);
		// 通知界面更新
		if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
			EventBus.getDefault().post(new MsgAndInfo(dwMessage, userSessionInfo), NotifyByEventBus.NT_REFRESH_CONVERSATION);
		} else if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
			EventBus.getDefault().post(new MsgAndInfo(dwMessage, userSessionInfo),
					NotifyByEventBus.NT_REFRESH_STRANGER_CONVERSATION);
		}
	}

	/**
	 * onActivityResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		transmit(requestCode, data);
		/**
		 * 重发消息
		 */
		if (resultCode == RESULT_OK) { // 清空消息
			if (requestCode == REQUEST_CODE_EMPTY_HISTORY) {
				// 清空会话

			} else if (requestCode == ChoceAndTakePictureComponent.TAKE_PICKTURE) {
				// 发送照片
				File file = new File(choceAndTakePictureComponent.getImageName());
				DWSendPicture(file);
			} else if (requestCode == REQUEST_CODE_OPEN_GALLERY) {
				// 发送本地图片
				if (null != data.getData()) {
					Uri uri = data.getData();
					String filePath = ImageUtils.getRealFilePath(mContext, uri);
					File file = FileUtils.handleFile(filePath);
					LogUtils.i(TAG, "照片路径为：" + file.getAbsolutePath());
					DWSendPicture(file);
				}
			} else if (requestCode == REQUEST_CODE_MAP) {
				// 地图
				double latitude = data.getDoubleExtra("latitude", 0);
				double longitude = data.getDoubleExtra("longitude", 0);
				String locationAddress = data.getStringExtra("address");
				if (locationAddress != null && !locationAddress.equals("")) {
					toggleMore(more);
					// sendLocationMsg(latitude, longitude, "",
					// locationAddress);
				} else {
					String st = getResources().getString(R.string.unable_to_get_loaction);
					Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
				}
			} else if (requestCode == REQUEST_CODE_CARD) {
				// 转发名片,被转发人的dwID如下
				ArrayList<String> result = data.getStringArrayListExtra(PickContactActivity.CONTACT_DWID);
				String forwardDwId = result.get(0);
				LogUtils.i(TAG, "被转发的人的dwID=" + forwardDwId);

				// 构造Message
				Message message = new Message(otherID, Message.Type.chat);
				LogUtils.i(TAG, "发送一张名片，Message.packetID=" + message.getPacketID());
				message.setFrom(dwID);
				message.setSubject("card");
				// 构造DWMessage
				DWMessage dwMessage = new DWMessage(DWMessage.CARD, DWMessage.SEND);
				dwMessage.setTo(otherID);
				dwMessage.setChatType(chatType);
				dwMessage.setChatRelationship(chatRelationship);
				dwMessage.setTxtMsgID(message.getPacketID());
				// 发送名片添加的
				dwMessage.setForwardDwId(forwardDwId);
				dwMessage.setForwardName(ContactUser.getContactName(forwardDwId));
				// ////////
				dwMessage.save();
				// 发送Message
				message.setBody(DWMessage.toJson(dwMessage));
				sendText(message);
				// 处理界面
				refreshListView(dwMessage);
				refreshConversation(dwMessage);
				btnContainer.setVisibility(View.GONE);
			} else if (requestCode == REQUEST_CODE_CHAT_SETTING) {
				boolean isMsgClear = data.getBooleanExtra("isMsgClear", false);
				/** 清除消息列表 **/
				if (isMsgClear) {
					listMsg.clear();
					adapter.notifyDataSetChanged();
				}
			}
		}
		setListViewBottom();
	}

	/**
	 * 发送位置信息
	 *
	 * @param latitude
	 * @param longitude
	 * @param imagePath
	 * @param locationAddress
	 */
	// private void sendLocationMsg(double latitude, double longitude, String
	// imagePath, String locationAddress) {
	//
	// <<<<<<< .mine
	// =======
	// }
	//
	// >>>>>>> .r1295
	/**
	 * 显示或隐藏图标按钮页
	 *
	 * @param view
	 */
	public void toggleMore(View view) {
		if (more.getVisibility() == View.GONE) {
			KeyboardComponent.dismissKeyboard(view);
			more.setVisibility(View.VISIBLE);
			btnContainer.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
		} else {
			if (emojiIconContainer.getVisibility() == View.VISIBLE) {
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.VISIBLE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
			} else {
				more.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 转发消息
	 * 
	 * @param requestCode
	 * @param data
	 */
	private void transmit(int requestCode, Intent data) {
		ArrayList<String> result = data.getStringArrayListExtra(PickContactActivity.CONTACT_DWID);
		if (result != null && result.size() > 0) {
			String dwID = result.get(0);
			switch (requestCode) {
			case Constants.TEXT_REQUEST_CODE:
				sendTxtMessage(dwID);
				break;
			case Constants.IMAGE_REQUEST_CODE:
				sendImageAndAudioMessage(dwID);
				break;
			case Constants.AUDIO_REQUEST_CODE:
				LogUtils.i(TAG, "转发语音");
				sendImageAndAudioMessage(dwID);
				break;
			}
		}
	}

	private void sendTxtMessage(String userDwID) {
		Chat chat = chatManager.createChat(userDwID + Constants.SERVER_NAME, null);
		DWMessage item = adapter.getItem(messagePosition);
		item.setFrom(dwID);
		item.setTime(String.valueOf(System.currentTimeMillis()));
		item.setTo(userDwID);
		item.setChatType(DWMessage.CHAT_TYPE_SINGLE);
		item.setChatRelationship(DWMessage.CHAT_RELATIONSHIP_FRIEND);
		item.setIsRead(0);
		// 设置未读
		// openFire聊天
		Message message = new Message();
		message.setType(Message.Type.chat);
		message.setFrom(DecentWorldApp.getInstance().getDwID());
		message.setTo(userDwID);
		message.setSubject("chat");
		item.setTxtMsgID(message.getPacketID());
		item.save();
		message.setBody(DWMessage.toJson(item));
		// 发送消息

		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toast.show("消息发送失败");
			return;
		}
		toast.show("已发送");
	}

	private void sendImageAndAudioMessage(String userDwID) {
		DWMessage item = adapter.getItem(messagePosition);
		long id = item.getMid();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("msgID", "" + id);
		map.put(Constants.DW_ID, dwID);
		map.put("toID", userDwID);
		map.put("chatType", "" + DWMessage.CHAT_TYPE_SINGLE);
		map.put("smackID", item.getTxtMsgID());
		chatComponent.transmitMessage(map);
	}

	/**
	 * 由PacketListenerService路由过来的【更新身家】的通知
	 * 
	 * @param message
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_WEALTH)
	public void updateWealth(Message message) {
		// 服务器直接发送过来的Message
		if (message.getSubject().equals("wealth")) {
			// 1.根据服务器回执，更新身家
			JSONObject jsonObject = JSON.parseObject(message.getBody());
			int chatType = jsonObject.getIntValue("chatType");
			if (chatType == DWMessage.CHAT_TYPE_SINGLE || chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
				String wealth = jsonObject.getString("wealth");
				long mid = jsonObject.getLongValue("id");
				LogUtils.i(TAG, "收到一条subject = wealth的消息，wealth=" + wealth + "，mid=" + mid + "，chatType=" + chatType);
				if (CommUtil.isNotBlank(wealth)) {
					LogUtils.i(TAG, "updateWealth,更新身家为：" + wealth);
					chatHelper.initEditTextData(mEditTextContent, chatType, chatRelationship, wealth);
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
	public void updateUiWithWealthArk(String txtMsgID, long mId) {
		LogUtils.i(TAG, "根据openfire消息id，更新UI，即更新内存中的数据");
		for (int i = listMsg.size() - 1; i >= 0; i--) {
			if (listMsg.get(i).getTxtMsgID().equals(txtMsgID)) {
				LogUtils.i(TAG, "txtMsgID=" + txtMsgID + ",mId=" + mId + ",i=" + i);
				listMsg.get(i).setSendSuccess(1);
				listMsg.get(i).setMid(mId);
				adapter.notifyDataSetChanged();
				setListViewBottom();
				return;
			}
		}
		LogUtils.i(TAG, "数据库中无此消息");
	}

	/**
	 * 更新收到的消息的通知
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_MSG)
	public void updateRevMsg(DWMessage dwMessage) {
		if (dwMessage.getFrom().equals(otherID)) {
			// 收到图片、语音时，更改（增加item）页面UI
			// dwMessage.save();
			android.os.Message osMessage = new android.os.Message();
			osMessage.what = REV_MSG;
			osMessage.obj = dwMessage;
			handler.sendMessage(osMessage);
		}
	}

	private static final int REV_MSG = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String worth;
			switch (msg.what) {
			case REV_MSG:
				// 更新聊天列表
				DWMessage dwMessage = (DWMessage) msg.obj;
				if (dwMessage.getWealth() != null) {
					chatHelper.initEditTextData(mEditTextContent, chatType, chatRelationship, dwMessage.getWealth());
				}
				listMsg.add(dwMessage);
				adapter.notifyDataSetChanged();
				setListViewBottom();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 对方身价发生改变
	 * 
	 * @param jsonObject
	 */
	@Subscriber(tag = NotifyByEventBus.NT_SYSTEM_PUSH_WORTH)
	public void otherWorthChanged(Message message) {
		LogUtils.i(TAG, "收到对方更改身价的通知" + message.getBody());
		String json = message.getBody();
		JSONObject jsonObject = JSON.parseObject(json);
		String fromDwId = jsonObject.getString("dwID");
		String worth = jsonObject.getString("worth");
		// new 一个消息，保存
		DWMessage dwMessage = new DWMessage(DWMessage.NOTIFY, DWMessage.RECEIVE);
		dwMessage.setFrom(fromDwId);
		dwMessage.setBody("对方身价修改为：" + worth);
		dwMessage.save();
		refreshListView(dwMessage);
		// 修改标题
		main_header_title.setText(otherNickname + "(" + worth + ")");
	}

	/**
	 * 获取聊天背景图片并设置成背景
	 */
	private void getChatBgImage() {
		try {
			File f = (File) getIntent().getSerializableExtra("values");
			Bitmap img = ImageUtils.getBitmapFrom(f.getAbsolutePath());
			iv_chat_bg.setImageBitmap(img);
			/** 保存聊天背景图片 **/
			ChatSetting chatSetting = ChatSetting.queryByDwID(otherID);
			if (chatSetting != null) {
				chatSetting.setChatBg(f.getAbsolutePath());
				chatSetting.save();
			}
		} catch (Exception e) {
			LogUtils.e(TAG, "解析背景图片时出现异常");
		}
	}

	class EnterAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			LogUtils.i("bm", "time-1---" + System.currentTimeMillis());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			/** 取消通知栏显示,并且当前页面不在有通知 **/
			MsgNotifyManager.getInstance().Notifyclear(MsgNotifyManager.NOTIFY_TYPE_SINGLE);
			MsgNotifyManager.getInstance().NotifyStop(MsgNotifyManager.NOTIFY_TYPE_SINGLE);
			/** 初始化 **/
			parseIntent();
			initData();
			initView();
			initListener();
			initTitle();
			initEditText();
			loadHistoryMsg(dwID, otherID);
		}
	}

	// ///////////////////////////////////////发送文本、语音、图片消息////////////////////////////////////////////////
	/**
	 * 经过封装的发送文本函数
	 */
	private void DWSendText(View v) {
		// //////////////////////////发送消息///////////////////////////////
		// 需要发送的文字消息
		String textContent = mEditTextContent.getText().toString();
		// 获取单聊管理者实例
		ChatSingleHelper chatSingleHelper = ChatSingleHelper.getInstance();
		// 构造DWMessage
		DWMessage dwMessage = chatSingleHelper.createTextDwMessage(otherID, chatType, chatRelationship, textContent);
		try {
			chatSingleHelper.sendTextMessage(dwMessage, listMsg);
		} catch (NotConnectedException e) {
			LogUtils.i(TAG, "断开连接，cause by:" + e.toString());
			toast.show("没有连接到服务器");
		}

		// ////////////////////////更新界面///////////////////////////////
		refreshListView(dwMessage);
		refreshConversation(dwMessage);
		mEditTextContent.setText("");
		more.setVisibility(View.GONE);
		emojiIconContainer.setVisibility(View.GONE);
		KeyboardComponent.dismissKeyboard(v);
	}

	/**
	 * 经过封装的发送语音函数
	 */
	private void DWSendVoice(File voiceFile, Float mTime) {
		ChatSingleHelper chatSingleHelper = ChatSingleHelper.getInstance();
		// 构造DWMessage
		final DWMessage dwMessage = chatSingleHelper.createVoiceDwMessage(otherID, chatType, chatRelationship);
		chatSingleHelper.sendVoiceMessage(dwMessage, listMsg, voiceFile, mTime, new SendVoiceCallBack(dwMessage));
		refreshListView(dwMessage);
		refreshConversation(dwMessage);
		// 处理界面
		hideKeyboard();
	}

	/**
	 * 
	 * @ClassName: ChatActivity.java
	 * @Description: 发送语音回调
	 * @author: cj
	 * @date: 2016年3月8日 下午6:18:41
	 */
	class SendVoiceCallBack implements SendVoiceListener {
		private DWMessage dwMessage;

		/**
		 * 构造函数
		 */
		public SendVoiceCallBack(DWMessage dwMessage) {
			this.dwMessage = dwMessage;
		}

		@Override
		public void onStart() {
			LogUtils.i(TAG, "发送语音...开始发送");
			dwMessage.setSendSuccess(2);
			dwMessage.save();
		}

		@Override
		public void onNotConnected() {
			LogUtils.i(TAG, "发送语音...没有连接到openfire服务器");
			dwMessage.setSendSuccess(0);
			dwMessage.save();
		}

		@Override
		public void onNetWrong() {
			LogUtils.i(TAG, "发送语音...网络错误");
			dwMessage.setSendSuccess(0);
			dwMessage.save();
		}

		@Override
		public void onSuccess() {
			LogUtils.i(TAG, "发送语音...成功");
			dwMessage.setSendSuccess(1);// 1代表成功
			dwMessage.save();
		}

		@Override
		public void onFailure(String packetID, String cause) {
			LogUtils.i(TAG, "发送语音...失败,原因是：" + cause);
			dwMessage.setSendSuccess(0);// 0代表失败
			dwMessage.save();
			sendFailure(packetID);
		}
	}

	/**
	 * 经过封装的发送图片函数
	 */
	private void DWSendPicture(File file) {
		ChatSingleHelper chatSingleHelper = ChatSingleHelper.getInstance();
		// 构造DWMessage
		final DWMessage dwMessage = chatSingleHelper.createPictureDwMessage(otherID, chatType, chatRelationship);
		chatSingleHelper.sendPictureMessage(dwMessage, listMsg, file, new SendPictureCallBack(dwMessage));
		// 处理界面
		refreshListView(dwMessage);
		refreshConversation(dwMessage);
		btnContainer.setVisibility(View.GONE);
		KeyboardComponent.dismissKeyboard(null);
	}

	/**
	 * 发送图片回调
	 * 
	 * @ClassName: ChatActivity.java
	 * @Description:
	 * @author: cj
	 * @date: 2016年3月8日 下午6:43:52
	 */
	class SendPictureCallBack implements SendPictureListener {

		private DWMessage dwMessage;

		/**
		 * 构造函数
		 */
		public SendPictureCallBack(DWMessage dwMessage) {
			this.dwMessage = dwMessage;
		}

		@Override
		public void onStart() {
			LogUtils.i(TAG, "发送图片...开始发送");
			dwMessage.setSendSuccess(2);
			dwMessage.save();
		}

		@Override
		public void onNotConnected() {
			LogUtils.i(TAG, "发送图片...没有连接到openfire服务器");
			dwMessage.setSendSuccess(0);
			dwMessage.save();
		}

		@Override
		public void onNetWrong() {
			LogUtils.i(TAG, "发送图片...网络错误");
			dwMessage.setSendSuccess(0);
			dwMessage.save();
		}

		@Override
		public void onSuccess() {
			LogUtils.i(TAG, "发送图片...成功");
			dwMessage.setSendSuccess(1);// 1代表成功
			dwMessage.save();
		}

		@Override
		public void onFailure(String packetID, String cause) {
			LogUtils.i(TAG, "发送图片...失败,原因是：" + cause);
			dwMessage.setSendSuccess(0);// 0代表失败
			dwMessage.save();
			sendFailure(packetID);
		}
	}

	// ///////////////////////////////////////重新发送消息////////////////////////////////////////////////
	/**
	 * 重新发送文本消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendText(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送文字信息");
		if (XmppHelper.getConnection().isConnected()) {
			// 将消息从列表中移除
			listMsg.remove(dwMessage);
			// /////////////////////////发送消息//////////////////////////////
			ChatSingleHelper chatSingleHelper = ChatSingleHelper.getInstance();
			try {
				chatSingleHelper.sendTextMessage(dwMessage, listMsg);
			} catch (NotConnectedException e) {
				LogUtils.i(TAG, "没有连接到服务器，cause by:" + e.toString());
				toast.show("没有连接到服务器");
			}
			// ////////////////////////更新界面///////////////////////////////
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			KeyboardComponent.dismissKeyboard(mEditTextContent);
			toast.show("重发成功");
		} else {
			reConnection();
		}
	}

	/**
	 * 重新发送语音消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendVoice(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送语音信息");
		// 发送Message
		if (XmppHelper.getConnection().isConnected()) {
			LogUtils.i(TAG, "有网，重新发送语音信息");
			// 重新发送文字消息
			ChatSingleHelper chatSingleHelper = ChatSingleHelper.getInstance();
			chatSingleHelper.reSendVoiceMessage(dwMessage, listMsg, new SendVoiceCallBack(dwMessage));
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			hideKeyboard();
			KeyboardComponent.dismissKeyboard(mEditTextContent);
		} else {
			reConnection();
		}
	}

	/**
	 * 重新发送图片消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendPicture(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送图片信息");
		// 发送Message
		if (XmppHelper.getConnection().isConnected()) {
			LogUtils.i(TAG, "有网，重新发送图片信息");
			// 重新发送文字消息
			ChatSingleHelper chatSingleHelper = ChatSingleHelper.getInstance();
			chatSingleHelper.reSendPictureMessage(dwMessage, listMsg, new SendPictureCallBack(dwMessage));
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			// 处理界面
			btnContainer.setVisibility(View.GONE);
		} else {
			reConnection();
		}
	}

	/**
	 * 重新发送名片消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendCard(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送名片消息");
		// 发送Message
		if (XmppHelper.getConnection().isConnected()) {
			// 构造Message
			Message message = new Message(otherID, Message.Type.chat);
			LogUtils.i(TAG, "发送一张名片，Message.packetID=" + message.getPacketID());
			message.setFrom(dwID);
			message.setSubject("card");
			message.setPacketID(dwMessage.getTxtMsgID());
			// 构造DWMessage
			dwMessage.setSendSuccess(2);
			dwMessage.setTime(String.valueOf(System.currentTimeMillis()));
			dwMessage.setMid(chatHelper.getTempMid(listMsg));
			/** 给消息添加Token **/
			addToken(dwMessage);
			// 发送Message
			message.setBody(DWMessage.toJson(dwMessage));
			sendText(message);
			dwMessage.setToken("");
			dwMessage.save();
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			btnContainer.setVisibility(View.GONE);
		} else {
			reConnection();
		}

	}
}
