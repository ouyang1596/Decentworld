/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

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
import cn.sx.decentworld.chat.ChatHelper;
import cn.sx.decentworld.chat.interfaces.ReSendMsgListener;
import cn.sx.decentworld.chat.interfaces.SendPictureListener;
import cn.sx.decentworld.chat.interfaces.SendVoiceListener;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.ChatComponent;
import cn.sx.decentworld.connect.XmppHelper;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment;
import cn.sx.decentworld.engine.ChatSingleEngine;
import cn.sx.decentworld.engine.ConversationEngine;
import cn.sx.decentworld.engine.UserDataEngine;
import cn.sx.decentworld.engine.UserDataEngine.GetWealthListener;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.MsgAndInfo;
import cn.sx.decentworld.entity.UserSessionInfo;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.MsgNotifyManager;
import cn.sx.decentworld.stateMachine.Send;
import cn.sx.decentworld.stateMachine.param.RefreshParam;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.TextUtil;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: ChatActivity1.java
 * @Description: 聊天界面 进入此界面需要5个参数 1.chatType 聊天类型 2.chatRelationship
 *               (判断是否属于好友，即是否在联系人列表中) 3.otherID 对方ID
 *               4.otherNickname（如果是好友，且非匿名，
 *               则按优先级显示；如果是好友，但是为匿名，则显示随机名字；如果为陌生人，则显示默认的） 5.otherWorth; 对方身价
 * @author: cj 进入ChatActivity的入口： 1.直接点击联系人； 2.点击联系人头像，再点击会话进入ChatActivity
 *          3.匿名聊天 NearCardDetail2Activity 4.NearCardDetailActivity
 *          5.NearCardDetailForMessageActivity 6.SearchAdapter 7.CardAdapter
 * @date: 2015年12月28日 上午10:27:36
 */
@EActivity(R.layout.activity_chat)
public class ChatActivity extends BaseFragmentActivity implements OnClickListener, AudioFinishedRecordeListener,
		OnMessageClickListener, OnTouchListener, OnRefreshListener2, ReSendMsgListener, GetWealthListener {
	/**
	 * 提示变量
	 */
	public static final String TAG = "ChatActivity";
	public static final int GET_WEALTH = 1;
	public static final int REQUEST_CODE_EMPTY_HISTORY = 2;
	public static final int REQUEST_CODE_CHAT_SETTING = 3;// 启动聊天设置界面
	public static final int REQUEST_CODE_OPEN_GALLERY = 4;
	public static final int REQUEST_CODE_MAP = 5;
	public static final int REQUEST_CODE_TRANSMIT_TEXT = 6;
	public static final int REQUEST_CODE_TRANSMIT_VOICE = 7;
	public static final int REQUEST_CODE_TRANSMIT_IMAGE = 8;
	public static final int REQUEST_CODE_TRANSMIT_CARD = 9;
	/** 传递参数key **/
	public static final String CHAT_TYPE = "chatType";
	public static final String CHAT_RELATIONSHIP = "chatRelationship";
	public static final String OTHER_ID = "otherID";
	public static final String OTHER_NICKNAME = "otherNickname";
	public static final String OTHER_WORTH = "otherWorth";

	// 传递参数对象
	public static final String LAUNCH_CHAT_KEY = "launchChatEntity";

	/** 聊天助手 **/
	private ChatHelper chatHelper;
	// 控制下拉刷新
	private Semaphore mGetHistoryMsgSemaphore = new Semaphore(1);

	/**
	 * 工具变量
	 */
	@Bean
	ToastComponent toast;
	@Bean
	KeyboardComponent KeyboardComponent;// 对键盘的操作
	@Bean
	ChoceAndTakePictureComponent choceAndTakePictureComponent; // 选择相片
	@Bean
	ChatComponent chatComponent;

	private ClipboardManager clipboard;
	private InputMethodManager manager;

	/** 当前用户信息 **/
	private String dwID = "";
	/** 对方的信息 **/
	private LaunchChatEntity launchChatEntity;
	private int chatType;// 聊天类型(默认为单聊)
	private int chatRelationship = ChatRelationship.FRIEND.getIndex();// 聊天关系（默认为好友）
	private String otherID = "";// 对方ID
	private String otherNickname = "";// 对方昵称
	private float otherWorth = -1;// 对方身价
	private int userType;

	/** 聊天界面背景 **/
	@ViewById(R.id.activity_chat_bg)
	ImageView iv_chat_bg;
	/** 消息列表 **/
	@ViewById(R.id.list)
	PullToRefreshListView mLvDWMessage;
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
	private DWMessageAdapter mAdapter;
	private List<DWMessage> mListMsg;

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
	
	//开启界面的类型
	public enum NewIntent_type
	{
	    MODIFY_BG,OTHER_CHANGED;
	}

	/**
	 * 界面入口
	 */
	@AfterViews
	void init() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		new EnterAsync().execute();
	}

	/**
	 * 异步任务
	 */
	class EnterAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				long sleepTime = 10;
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			LogUtils.v(TAG, "EnterAsync() 取消通知栏显示,并且当前页面不在有通知");
			parseIntent();
			MsgNotifyManager.getInstance().Notifyclear(MsgNotifyManager.NOTIFY_TYPE_SINGLE);
			MsgNotifyManager.getInstance().NotifyStop(MsgNotifyManager.NOTIFY_TYPE_SINGLE);
			ConversationEngine.getInstance().addUnReadRedPoint(chatRelationship + otherID + chatType);
			initData();
			initView();
			initChatBg();
			initListener();
			initTitle();
			// loadHistoryMsg(dwID, otherID, chatType);
		}
	}

	/**
	 * 解析启动Activity传递过来的6个参数
	 */
	private void parseIntent() {
		launchChatEntity = (LaunchChatEntity) getIntent().getSerializableExtra(LAUNCH_CHAT_KEY);
		if (launchChatEntity != null) {
			LogUtils.v(TAG, launchChatEntity.toString());
			otherID = launchChatEntity.getOtherID();
			otherNickname = launchChatEntity.getOtherShowName();
			otherWorth = launchChatEntity.getOtherWorth();
			chatType = launchChatEntity.getChatType();
			chatRelationship = launchChatEntity.getChatRelationship();
			userType = launchChatEntity.getUserType();
		} else {
			LogUtils.e(TAG, "parseIntent()...launchChatEntity==null");
		}
	}

	/**
	 * 初始化基本数据
	 */
	private void initData() {
		EventBus.getDefault().register(this);
		chatHelper = new ChatHelper();
		dwID = DecentWorldApp.getInstance().getDwID();
		/** 获取自己的身家 **/
		UserDataEngine.getInstance().getWealth(this);
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 管理类
		chatManager = ChatManager.getInstanceFor(XmppHelper.getConnection());
		// mListMsg = new ArrayList<DWMessage>();
		mListMsg = cn.sx.decentworld.manager.ChatManager.getInstance().getMessageCache(otherID, chatType).loadFromMemory();
		mListMsg.clear();
		LogUtils.v(TAG, "initData() loadFromMemory:" + mListMsg.size());
		if (mListMsg.size() <= 0) {
			cn.sx.decentworld.manager.ChatManager.getInstance().getMessageCache(otherID, chatType).loadFromLocal();
			LogUtils.v(TAG, "initData() loadFromLocal:" + mListMsg.size());
		}
		if (mListMsg.size() <= 0) {
			LogUtils.v(TAG, "initData() loadFromNet:" + mListMsg.size());
			cn.sx.decentworld.manager.ChatManager.getInstance().getMessageCache(otherID, chatType).loadFromNet();
		}
		LogUtils.v(TAG, "initData() mListMsg.size:" + mListMsg.size());
		Collections.sort(mListMsg);
		mAdapter = new DWMessageAdapter(this, mListMsg, launchChatEntity);
		mAdapter.setReSendMsgListener(this);
		mAdapter.setOnMessageClickListener(this);
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
		mLvDWMessage.getRefreshableView().setOnTouchListener(this);
		mLvDWMessage.setOnRefreshListener(this);
		mLvDWMessage.setAdapter(mAdapter);
		setListViewBottom();
	}

	/**
	 * 初始化聊天背景图片
	 */
	private void initChatBg() {
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
		buttonPressToSpeak.setAudioFishedRecorde(this);
		btn_set_mode_voice.setOnClickListener(this);
		btn_set_mode_keyboard.setOnClickListener(this);
		main_header_left.setOnClickListener(this);
		chatHelper.initEditText(mEditTextContent, btn_more, btn_send, edittext_layout);
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
	 * 点击监听回调
	 */
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.et_sendmessage:
			onClickEditText();
			break;
		case R.id.btn_send:
			LogUtils.v(TAG, "点击了[发送]按钮");
			DWSendText(v);
			break;
		case R.id.btn_take_picture:
			LogUtils.v(TAG, "点击了[拍照]按钮");
			choceAndTakePictureComponent.takePicture();
			break;
		case R.id.btn_picture:
			LogUtils.v(TAG, "点击了[图片]按钮");
			intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent, REQUEST_CODE_OPEN_GALLERY);
			break;
		case R.id.btn_set_mode_voice:
			LogUtils.v(TAG, "点击了[语音]按钮");
			setModeVoice();
			break;
		case R.id.btn_location:
			LogUtils.v(TAG, "点击了[发送地理位置]按钮...发送地理位置的功能还未开发");
			break;
		case R.id.btn_card:
			sendCard();
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
		case R.id.main_header_left:
			hideKeyboard();
			finish();
			break;
		case R.id.main_header_right_btn:
			intent = new Intent(ChatActivity.this, ChatSettingActivity_.class);
			intent.putExtra("otherID", otherID);
			intent.putExtra("otherNick", otherNickname);
			startActivityForResult(intent, REQUEST_CODE_CHAT_SETTING);
			break;
		default:
			break;
		}

	}

	/**
	 * 发送名片
	 */
	private void sendCard() {
		LogUtils.v(TAG, "点击了发送名片按钮，打开选择联系人列表");
		Intent intent = new Intent(ChatActivity.this, PickContactActivity_.class);
		intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
		startActivityForResult(intent, REQUEST_CODE_TRANSMIT_CARD);
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
	 * 触发重连
	 */
	private void reConnection() {
		reConnection(null);
	}

	/**
	 * 触发重连
	 * 
	 * @param dwMessage
	 *            消息对象，用于打印信息
	 */
	private void reConnection(DWMessage dwMessage) {
		String info = "";
		if (dwMessage != null)
			info = dwMessage.toString();
		LogUtils.w(TAG, "reConnection...重新发送消息时检测到没有连接到服务器，dwMessage.toString()=" + info);
		LogUtils.w(TAG, "没有连接到服务器，开启重连机制");
		if (XmppHelper.getConnection().getReconnectionManager() != null) {
			LogUtils.w(TAG, "开启重连机制...dwMessage.toString()=" + info, true);
			XmppHelper.getConnection().getReconnectionManager().reconnect();
		}
	}

	/**
	 * 为消息添加当前时间和Token
	 * 
	 * @param dwMessage
	 */
	private void addToken(DWMessage dwMessage) {
		long time = System.currentTimeMillis();
		dwMessage.setTime(String.valueOf(time));
		String randomStr = UserInfoHelper.getRandomStr();
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

	private void onClickEditText() {
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_active);
		more.setVisibility(View.GONE);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		emojiIconContainer.setVisibility(View.GONE);
		btnContainer.setVisibility(View.GONE);
		new SetListViewBottomAsyn().execute();
	}

	class SetListViewBottomAsyn extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setListViewBottom();
		}
	}

	/**
	 * lauchMode= singleTask 如果该实例已经存在，那么再次启动时就会调用onNewIntent
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			setIntent(intent);
			int type = intent.getIntExtra("type", -1);
			if(type !=-1)
			{
			    if(type == NewIntent_type.MODIFY_BG.ordinal())
			    {
			         // 设置聊天背景
		            getChatBgImage();
			    }
			}
			else
			{
			    chatPersonChanged();
			}
		}
	}

	/**
	 * 聊天对方改变
	 */
	private void chatPersonChanged() {
		String tempOtherID = "";
		launchChatEntity = (LaunchChatEntity) getIntent().getSerializableExtra(LAUNCH_CHAT_KEY);
		if (launchChatEntity != null) {
			LogUtils.v(TAG, launchChatEntity.toString());
			tempOtherID = launchChatEntity.getOtherID();
		} else {
			LogUtils.e(TAG, "parseIntent()...launchChatEntity==null");
		}
		LogUtils.v(TAG, "chatPersonChanged()...聊天对方改变为otherID=" + tempOtherID);
		if (tempOtherID != null && !tempOtherID.equals(otherID)) {
			mListMsg.clear();
			new EnterAsync().execute();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (null != mAdapter)
			mAdapter.stopMediaPlayer();
	}

	/**
	 * 销毁窗口
	 */
	@Override
	protected void onDestroy() {
		LogUtils.v(TAG, "onDestroy() 退出界面，去掉会话列表中对应小红点");
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		ConversationEngine.getInstance().removeUnReadRedPoint();
		// 设置为可以显示通知
		MsgNotifyManager.getInstance().NotifyStart(MsgNotifyManager.NOTIFY_TYPE_SINGLE);
		buttonPressToSpeak.mTheMediaRecorder = null;
	}

	/**
	 * 将消息列表滚动 到最后一项
	 */
	private void setListViewBottom() {
		mLvDWMessage.getRefreshableView().setSelection(mLvDWMessage.getRefreshableView().getCount() - 1);
	}

	/**
	 * 按住说话完成回调监听
	 */
	@Override
	public void FinishedRecordeListener(Float mTime, String filePath) {
		if (ImageUtils.fileLength(filePath) < 100) {
			TrueOrFalseDialogFragment trueOrFalseDialogFragment = new cn.sx.decentworld.dialog.TrueOrFalseDialogFragment();
			trueOrFalseDialogFragment.hideTvCancel = true;
			trueOrFalseDialogFragment.setTips(Constants.APPLICATION_AUTHORITY);// "您的录音权限没打开(解决办法：打开权限或者重新安装)或者文件损坏(解决办法：退出后再重新登录)"
			trueOrFalseDialogFragment.show(getSupportFragmentManager(), "trueOrFalseDialogFragment");
			return;
		}
		// 构造DWMessage
		File voiceFile = new File(filePath);
		DWSendVoice(voiceFile, mTime);
	}

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
		case R.id.iv_voice:
			intent = new Intent(mContext, PickContactActivity_.class);
			intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
			startActivityForResult(intent, REQUEST_CODE_TRANSMIT_VOICE);
			break;
		case R.id.iv_sendPicture:
			intent = new Intent(mContext, PickContactActivity_.class);
			intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
			startActivityForResult(intent, REQUEST_CODE_TRANSMIT_IMAGE);
			break;
		}
	}

	private OnClickListener mOnClicklistener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.tv_copy:
				DWMessage item = mAdapter.getItem(messagePosition);
				String message = item.getBody();
				if (CommUtil.isNotBlank(message)) {
					TextUtil.copy(message, mContext);
					toast.show("已复制到剪切板");
				}
				break;
			case R.id.tv_transmit:
				// 转发文字
				Intent intent = new Intent(mContext, PickContactActivity_.class);
				intent.putExtra(PickContactActivity.PICK_TYPE, PickContactActivity.PICK_TYPE_SINGLE);
				startActivityForResult(intent, REQUEST_CODE_TRANSMIT_TEXT);
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
	private void sendCardMsg(Message message) {
		Chat chat = chatManager.createChat(otherID + Constants.SERVER_NAME, null);
		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
			toast.show("没有连接到服务器");
			LogUtils.w(TAG, "sendCardMsg...没有连接到服务器，发送的消息为：" + message.toXML().toString());
		}
	}

	/**
	 * 发送消息失败后将内存中的消息状态设置为发送失败
	 */
	public void sendFailure(String txtMsgID) {
		for (int i = mListMsg.size() - 1; i >= 0; i--) {
			if (mListMsg.get(i).getTxtMsgID().equals(txtMsgID)) {
				mListMsg.get(i).setSendSuccess(0);
				mListMsg.get(i).setMid(0);
				LogUtils.w(TAG, "sendFailure...发送消息失败，消息为：" + mListMsg.get(i).toString());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mAdapter.notifyDataSetChanged();
					}
				});
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
		LogUtils.v(TAG, "refreshListView...发送一条消息为:" + dwMessage.toString());
		cn.sx.decentworld.manager.ChatManager.getInstance().getMessageCache(otherID, chatType).addMsg(dwMessage);
	}

	/**
	 * 更新会话列表
	 * 
	 * @param dwMessage
	 */
	private void refreshConversation(DWMessage dwMessage) {
		LogUtils.v(TAG, "refreshConversation() " + dwMessage.toString());
		UserSessionInfo userSessionInfo = new UserSessionInfo(otherID, otherNickname, otherWorth, userType);
		ConversationEngine.getInstance().refreshConversation(new MsgAndInfo(dwMessage, userSessionInfo), "【发送消息】");
	}

	/**
	 * onActivityResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogUtils.v(TAG, "onActivityResult() requestCode " + requestCode + " resultCode " + resultCode);
		super.onActivityResult(requestCode, resultCode, data);
		if (null != data) {
			if (resultCode == RESULT_OK) { // 清空消息
				if (requestCode == REQUEST_CODE_EMPTY_HISTORY) {
					// 清空会话

				} else if (requestCode == REQUEST_CODE_CHAT_SETTING) {
					// 清除消息列表
					boolean isMsgClear = data.getBooleanExtra("isMsgClear", false);
					if (isMsgClear) {
						mListMsg.clear();
						mAdapter.notifyDataSetChanged();
					}
				} else if (requestCode == REQUEST_CODE_OPEN_GALLERY) {
					// 发送本地图片
					if (null != data.getData()) {
						Uri uri = data.getData();
						String filePath = ImageUtils.getRealFilePath(mContext, uri);
						File file = FileUtils.handleFile(filePath);
						LogUtils.i(TAG, "onActivityResult...照片路径为：" + file.getAbsolutePath());
						DWSendPicture(file);
					}
				} else if (requestCode == REQUEST_CODE_MAP) {
					// 地图
					double latitude = data.getDoubleExtra("latitude", 0);
					double longitude = data.getDoubleExtra("longitude", 0);
					String locationAddress = data.getStringExtra("address");
					if (locationAddress != null && !locationAddress.equals("")) {
						toggleMore(more);
					} else {
						String st = getResources().getString(R.string.unable_to_get_loaction);
						Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
					}
				} else if (requestCode == REQUEST_CODE_TRANSMIT_TEXT || requestCode == REQUEST_CODE_TRANSMIT_VOICE
						|| requestCode == REQUEST_CODE_TRANSMIT_IMAGE || requestCode == REQUEST_CODE_TRANSMIT_CARD) {
					// 转发
					transmit(requestCode, data);
				}
			}
		} else {
			if (resultCode == RESULT_OK) {
				if (requestCode == ChoceAndTakePictureComponent.TAKE_PICKTURE) {
					// 发送照片
					LogUtils.d(TAG, "onActivityResult() take photo");
					File file = new File(choceAndTakePictureComponent.getImageName());
					DWSendPicture(file);
				}
			}
		}
		setListViewBottom();
	}

	/**
	 * 转发消息
	 * 
	 * @param requestCode
	 * @param data
	 */
	private void transmit(int requestCode, Intent data) {
		ArrayList<ContactUser> arrayList = (ArrayList<ContactUser>) data.getSerializableExtra(PickContactActivity.CONTACT_DWID);
		if (arrayList != null && arrayList.size() > 0) {
			ContactUser contactUser = arrayList.get(0);
			switch (requestCode) {
			case REQUEST_CODE_TRANSMIT_TEXT:
				LogUtils.v(TAG, "transmit...转发文字");
				transmitTextMsg(contactUser);
				break;
			case REQUEST_CODE_TRANSMIT_VOICE:
				LogUtils.v(TAG, "transmit...转发语音");
				transmitVoiceMsg(contactUser);
				break;
			case REQUEST_CODE_TRANSMIT_IMAGE:
				LogUtils.v(TAG, "transmit...转发图片");
				transmitImageMsg(contactUser);
				break;
			case REQUEST_CODE_TRANSMIT_CARD:
				LogUtils.v(TAG, "transmit...转发名片");
				transmitCardMsg(contactUser);
				break;
			}
		}
	}

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
	 * 对方身价发生改变
	 * 
	 * @param message
	 */
	@Subscriber(tag = NotifyByEventBus.NT_SYSTEM_PUSH_WORTH)
	public void otherWorthChanged(Message message) {
		LogUtils.i(TAG, "otherWorthChanged...收到对方更改身价的通知" + message.getBody());
		String json = message.getBody();
		JSONObject jsonObject = JSON.parseObject(json);
		String fromDwId = jsonObject.getString("dwID");
		String worth = jsonObject.getString("worth");
		// new 一个消息，保存
		DWMessage dwMessage = new DWMessage(MessageType.NOTIFY.getIndex(), DWMessage.Direct.RECEIVE);
		dwMessage.setFromDwId(fromDwId);
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
			if (f != null) {
				Bitmap img = ImageUtils.getBitmapFrom(f.getAbsolutePath());
				iv_chat_bg.setImageBitmap(img);
				/** 保存聊天背景图片 **/
				ChatSetting chatSetting = ChatSetting.queryByDwID(otherID);
				if (chatSetting != null) {
					chatSetting.setChatBg(f.getAbsolutePath());
					chatSetting.save();
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, "解析背景图片时出现异常,Exception:" + e.toString(), true);
		}
	}

	// ///////////////////////////////////////发送文本、语音、图片消息////////////////////////////////////////////////
	/**
	 * 经过封装的发送文本函数
	 */
	private void DWSendText(View v) {
		String textContent = mEditTextContent.getText().toString();
		// 获取单聊管理者实例
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		DWMessage dwMessage = chatSingleHelper.createTextDwMessage(otherID, chatType, chatRelationship, maxID, textContent);
		LogUtils.d(TAG, "DWSendText() " + dwMessage.toString());
		refreshListView(dwMessage);
		Send send = new Send(dwMessage);// save
		send.execute();
		// ////////////////////////更新界面///////////////////////////////
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
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		String voicePath = voiceFile.getAbsolutePath();
		DWMessage dwMessage = chatSingleHelper.createVoiceDwMessage(otherID, chatType, chatRelationship, maxID, voicePath, mTime);
		refreshListView(dwMessage);
		chatSingleHelper.sendVoiceMessage(dwMessage, new SendVoiceCallBack(dwMessage));
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
			LogUtils.i(TAG, "发送语音...onStart");
			dwMessage.setSendSuccess(DWMessage.SendStatus.SENDING.getIndex());
			dwMessage.save();
		}

		@Override
		public void onNotConnected() {
			LogUtils.w(TAG, "发送语音...onNotConnected");
			dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
			dwMessage.save();
			handleNotConnectedException("SendVoiceCallBack");
		}

		@Override
		public void onNetWrong() {
			LogUtils.e(TAG, "发送语音...onNetWrong");
			dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
			dwMessage.save();
		}

		@Override
		public void onSuccess() {
			LogUtils.i(TAG, "发送语音...onSuccess");
			dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());// 1代表成功
			dwMessage.save();
		}

		@Override
		public void onFailure(String packetID, String cause) {
			LogUtils.w(TAG, "发送语音...失败,原因是：" + cause);
			dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());// 0代表失败
			dwMessage.save();
			sendFailure(packetID);
		}
	}

	/**
	 * 经过封装的发送图片函数
	 */
	private void DWSendPicture(File file) {
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
		// 构造DWMessage
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		String picturePath = file.getAbsolutePath();
		DWMessage dwMessage = chatSingleHelper.createImageDwMessage(otherID, chatType, chatRelationship, maxID, picturePath);
		refreshListView(dwMessage);
		chatSingleHelper.sendPictureMessage(dwMessage, new SendPictureCallBack(dwMessage));
		// 处理界面
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
			LogUtils.v(TAG, "SendPictureCallBack.class onStart()");
			dwMessage.setSendSuccess(DWMessage.SendStatus.SENDING.getIndex());
			dwMessage.save();
		}

		@Override
		public void onNotConnected() {
			LogUtils.w(TAG, "SendPictureCallBack.class onNotConnected()");
			dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
			dwMessage.save();
		}

		@Override
		public void onNetWrong() {
			LogUtils.w(TAG, "SendPictureCallBack.class onNetWrong()");
			dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
			dwMessage.save();
		}

		@Override
		public void onSuccess() {
			LogUtils.i(TAG, "SendPictureCallBack.class onSuccess()");
			dwMessage.setSendSuccess(DWMessage.SendStatus.SUCCESS.getIndex());
			dwMessage.save();
		}

		@Override
		public void onFailure(String packetID, String cause) {
			LogUtils.w(TAG, "SendPictureCallBack...发送图片...onFailure,cause by：" + cause);
			dwMessage.setSendSuccess(DWMessage.SendStatus.FAILURE.getIndex());
			dwMessage.save();
			sendFailure(packetID);
		}
	}

	/**
	 * 转发文本消息
	 * 
	 * @param userDwID
	 */
	private void transmitTextMsg(ContactUser contactUser) {
		DWMessage item = mAdapter.getItem(messagePosition);
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();

		String userDwID = contactUser.getFriendID();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		String textContent = item.getBody();
		DWMessage dwMessage = chatSingleHelper.createTextDwMessage(userDwID, ChatType.SINGLE.getIndex(),
				ChatRelationship.FRIEND.getIndex(), maxID, textContent);
		try {
			Message message = chatSingleHelper.createXmppMessage(dwMessage.getTo());
			message.setPacketID(dwMessage.getTxtMsgID());
			chatSingleHelper.sendTextMessage(dwMessage, message);
		} catch (NotConnectedException e) {
			LogUtils.w(TAG, "transmitTextMsg...没有连接到服务器,将要发送的信息为：" + dwMessage.toString(), true);
			toast.show("没有连接到服务器");
		}
		// 更新会话列表
		UserSessionInfo userSessionInfo = new UserSessionInfo(contactUser.getFriendID(), contactUser.getShowName(),
				contactUser.getWorth(), userType);
		ConversationEngine.getInstance().refreshConversation(new MsgAndInfo(dwMessage, userSessionInfo), "【发送消息】");
	}

	/**
	 * 转发语音
	 * 
	 * @param contactUser
	 */
	private void transmitVoiceMsg(ContactUser contactUser) {
		DWMessage item = mAdapter.getItem(messagePosition);
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();

		String userDwID = contactUser.getFriendID();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		String voicePath = item.getLocalUrl();
		float voiceTime = item.getVoiceTime();
		// 构造DWMessage
		DWMessage dwMessage = chatSingleHelper.createVoiceDwMessage(userDwID, ChatType.SINGLE.getIndex(),
				ChatRelationship.FRIEND.getIndex(), maxID, voicePath, voiceTime);
		chatSingleHelper.transmitVoiceMessage(dwMessage, item, maxID, new SendVoiceCallBack(dwMessage));
		// 刷新会话列表
		UserSessionInfo userSessionInfo = new UserSessionInfo(contactUser.getFriendID(), contactUser.getShowName(),
				contactUser.getWorth(), userType);
		ConversationEngine.getInstance().refreshConversation(new MsgAndInfo(dwMessage, userSessionInfo), "【发送消息】");
	}

	/**
	 * 转发图片
	 * 
	 * @param contactUser
	 */
	private void transmitImageMsg(ContactUser contactUser) {
		DWMessage item = mAdapter.getItem(messagePosition);
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();

		String userDwID = contactUser.getFriendID();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		String imgPath = item.getLocalUrl();
		// 构造DWMessage
		DWMessage dwMessage = chatSingleHelper.createImageDwMessage(userDwID, ChatType.SINGLE.getIndex(),
				ChatRelationship.FRIEND.getIndex(), maxID, imgPath);
		chatSingleHelper.transmitImageMessage(dwMessage, item.getMid(), new SendPictureCallBack(dwMessage));
		// 刷新会话列表
		UserSessionInfo userSessionInfo = new UserSessionInfo(contactUser.getFriendID(), contactUser.getShowName(),
				contactUser.getWorth(), userType);
		ConversationEngine.getInstance().refreshConversation(new MsgAndInfo(dwMessage, userSessionInfo), "【发送消息】");
	}

	/**
	 * 转发名片消息
	 * 
	 * @param contactUser
	 */
	private void transmitCardMsg(ContactUser contactUser) {
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		DWMessage dwMessage = chatSingleHelper.createCardDwMessage(otherID, chatType, chatRelationship);
		try {
			chatSingleHelper.sendCardMessage(dwMessage, maxID, contactUser);
		} catch (NotConnectedException e) {
			LogUtils.w(TAG, "transmitCardMsg...检测到与服务器断开了连接");
			reConnection(dwMessage);
			toast.show("没有连接到服务器");
		}
		// 处理界面
		refreshListView(dwMessage);
		refreshConversation(dwMessage);
		btnContainer.setVisibility(View.GONE);
	}

	// ///////////////////////////////////////重新发送消息////////////////////////////////////////////////
	/**
	 * 重新发送文本消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendText(DWMessage dwMessage) {
		LogUtils.i(TAG, "onReSendText...重新发送文字信息");
		ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
		long maxID = chatSingleHelper.getTempMaxID(mListMsg);
		dwMessage.setMid(maxID);
		try {
			Message message = chatSingleHelper.createXmppMessage(dwMessage.getTo());
			message.setPacketID(dwMessage.getTxtMsgID());
			dwMessage.setSendSuccess(DWMessage.SendStatus.SENDING.getIndex());
			chatSingleHelper.sendTextMessage(dwMessage, message);
			// 将消息从列表中移除
			mListMsg.remove(dwMessage);
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			KeyboardComponent.dismissKeyboard(mEditTextContent);
		} catch (NotConnectedException e) {
			handleNotConnectedException("onReSendText");
		}
	}

	/**
	 * 重新发送语音消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendVoice(DWMessage dwMessage) {
		LogUtils.i(TAG, "onReSendVoice...重新发送语音信息");
		// 发送Message
		if (XmppHelper.getConnection().isConnected()) {
			LogUtils.i(TAG, "有网，重新发送语音信息");
			// 重新发送文字消息
			ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
			long maxID = chatSingleHelper.getTempMaxID(mListMsg);
			chatSingleHelper.reSendVoiceMessage(dwMessage, maxID, new SendVoiceCallBack(dwMessage));
			// 处理界面
			mListMsg.remove(dwMessage);
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			hideKeyboard();
			KeyboardComponent.dismissKeyboard(mEditTextContent);
		} else {
			handleNotConnectedException("onReSendVoice", dwMessage);
		}
	}

	/**
	 * 重新发送图片消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendPicture(DWMessage dwMessage) {
		LogUtils.i(TAG, "onReSendPicture...重新发送图片信息");
		// 发送Message
		if (XmppHelper.getConnection().isConnected()) {
			// 重新发送文字消息
			ChatSingleEngine chatSingleHelper = ChatSingleEngine.getInstance();
			long maxID = chatSingleHelper.getTempMaxID(mListMsg);
			chatSingleHelper.reSendPictureMessage(dwMessage, maxID, new SendPictureCallBack(dwMessage));
			// 处理界面
			mListMsg.remove(dwMessage);
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			btnContainer.setVisibility(View.GONE);
		} else {
			handleNotConnectedException("onReSendPicture", dwMessage);
		}
	}

	/**
	 * 重新发送名片消息
	 * 
	 * @param dwMessage
	 */
	@Override
	public void onReSendCard(DWMessage dwMessage) {
		LogUtils.i(TAG, "onReSendCard...重新发送名片消息");
		// 发送Message
		if (XmppHelper.getConnection().isConnected()) {
			// 构造Message
			Message message = new Message(otherID, Message.Type.chat);
			LogUtils.i(TAG, "发送一张名片，Message.packetID=" + message.getPacketID());
			message.setFrom(dwID);
			message.setSubject("card");
			message.setPacketID(dwMessage.getTxtMsgID());
			// 构造DWMessage
			dwMessage.setSendSuccess(DWMessage.SendStatus.SENDING.getIndex());
			dwMessage.setTime(String.valueOf(System.currentTimeMillis()));
			dwMessage.setMid(chatHelper.getTempMid(mListMsg));
			/** 给消息添加Token **/
			addToken(dwMessage);
			// 发送Message
			message.setBody(DWMessage.toJson(dwMessage));
			sendCardMsg(message);
			dwMessage.setToken("");
			dwMessage.save();
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			btnContainer.setVisibility(View.GONE);
		} else {
			handleNotConnectedException("onReSendCard", dwMessage);
		}

	}

	// //////////////////////////////////下拉刷新加载历史记录/////////////////////////////
	/**
	 * 上拉刷新回调监听
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		LogUtils.i(TAG, "onPullUpToRefresh...上拉刷新");
	}

	/**
	 * 下拉刷新回调监听
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		LogUtils.v(TAG, "onPullDownToRefresh() params[dwID =" + dwID + ",otherID=" + otherID);
		loadStart();
		cn.sx.decentworld.manager.ChatManager.getInstance().getMessageCache(otherID, chatType).onPullDownToRefresh();
	}

	/**
	 * 开始获取历史记录，开启刷新，请求一个信号量
	 */
	private void loadStart() {
		try {
			LogUtils.v(TAG, "loadStart() ");
			mGetHistoryMsgSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取历史记录完成，停止刷新，释放一个信号量
	 */
	private void loadCompleted() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mLvDWMessage.onRefreshComplete();
					}
				});
				mGetHistoryMsgSemaphore.release();
			}
		}).start();
	}

	/**
	 * 获取身家完成
	 */
	@Override
	public void onGetWealthFinished(String wealth) {
		// 更新界面
		chatHelper.initEditTextData(mEditTextContent, chatType, chatRelationship, wealth);
	}

	// 发送消息确认回执
	@Subscriber(tag = NotifyByEventBus.NT_SEND_SUCCESS_ACK)
	public void confirmAck(String maxIDParam) {
		long maxID = Long.valueOf(maxIDParam);
		LogUtils.i(TAG, "confirmAck...根据maxID=" + maxID + "更新消息的显示状态");
		for (int i = mListMsg.size() - 1; i >= 0; i--) {
			if (mListMsg.get(i).getMid() == maxID) {
				LogUtils.v(TAG, "confirmAck...maxID=" + maxID + ",i=" + i);
				DWMessage dwMessage = mListMsg.get(i);
				dwMessage.setBody(dwMessage.getBody() + "\n" + "消息已送达[界面添加]" + "["
						+ TimeUtils.getCurrentTime(TimeUtils.HH11mm11ss) + "]");
				dwMessage.save();
				Collections.sort(mListMsg);
				mAdapter.notifyDataSetChanged();
				LogUtils.i(TAG, "confirmAck刷新列表");
				return;
			}
		}
		LogUtils.w(TAG, "confirmAck...内存中无maxID=" + maxID + "的消息");
	}

	/**
	 * 处理没有连接到服务器异常
	 * 
	 * @param info
	 *            定位信息，一般用函数名
	 */
	private void handleNotConnectedException(String info) {
		LogUtils.w(TAG, "handleNotConnectedException..." + info + "...NotConnectedException");
		toast.show("没有连接到服务器");
		reConnection();
	}

	/**
	 * 处理没有连接到服务器异常
	 * 
	 * @param info
	 *            定位信息，一般用函数名
	 * @param dwMessage
	 *            将要发送的消息
	 */
	private void handleNotConnectedException(String info, DWMessage dwMessage) {
		LogUtils.w(TAG, info + "...NotConnectedException");
		toast.show("没有连接到服务器");
		reConnection(dwMessage);
	}

	/**
	 * 刷新消息列表
	 * 
	 * @param packetID
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_MSG_LIST)
	public void refreshMsgList(RefreshParam param) {
		LogUtils.v(TAG, "refreshMsgList() " + param.toString());
		DWMessage dwMessage = getDWMessage(param.getPacketID());
		if (dwMessage != null) {
			dwMessage.setSendSuccess(param.getSendStatus().getIndex());
			dwMessage.save();
			mAdapter.notifyDataSetChanged();
			LogUtils.v(TAG, "refreshMsgList() 刷新完毕" + param.getPacketID());
		} else {
			LogUtils.w(TAG, "refreshMsgList() 要查询的PacketID=" + param.getPacketID() + "，没有找到！");
		}
	}

	/**
	 * 获取列表中对应的DWMessage
	 * 
	 * @param packetID
	 * @return
	 */
	private DWMessage getDWMessage(String packetID) {
		DWMessage dwMessage = null;
		for (int i = mListMsg.size() - 1; i >= 0; i--) {
			if (mListMsg.get(i).getTxtMsgID().equals(packetID)) {
				dwMessage = mListMsg.get(i);
				return dwMessage;
			}
		}
		return dwMessage;
	}

	/**
	 * 下拉刷新完毕
	 */
	@Subscriber(tag = NotifyByEventBus.NT_CHAT_ACTIVITY_PULL_DOWN_FINISH)
	void loadHistoryFinish(String otherID_chatType) {
		LogUtils.v(TAG, "loadHistoryFinish() " + otherID_chatType);
		refreshList(otherID_chatType);
		loadCompleted();
	}

	/**
	 * 收到一条消息后，解析身家，并刷新身家
	 */
	@Subscriber(tag = NotifyByEventBus.NT_CHAT_ACTIVITY_REFRESH)
	void notifyRefreshList(String otherID_chatType) {
		LogUtils.v(TAG, "notifyRefreshList() " + otherID_chatType);
		refreshList(otherID_chatType);
		setListViewBottom();
	}

	/**
	 * 刷新列表
	 */
	private void refreshList(String otherID_chatType) {
		if (otherID_chatType.equals(otherID + chatType)) {
			if (mAdapter != null) {
				Collections.sort(mListMsg);
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 更新身家
	 */
	@Subscriber(tag = NotifyByEventBus.NT_NOTIFY_WEALTH_CHANGED)
	void notifyWealthChanged(String wealth) {
		LogUtils.v(TAG, "notifyWealthChanged() params[wealth=" + wealth + "]");
		chatHelper.initEditTextData(mEditTextContent, chatType, chatRelationship, wealth);
	}

}
