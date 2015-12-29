/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import cn.sx.decentworld.adapter.ExpressionAdapter;
import cn.sx.decentworld.adapter.ExpressionPagerAdapter;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.manager.DWSMessageManager;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.SmileUtils;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.ChatComponent;
import cn.sx.decentworld.manager.CommHttp;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.TextUtil;
import cn.sx.decentworld.utils.XmppHelper;
import cn.sx.decentworld.widget.ExpandGridView;
import cn.sx.decentworld.widget.PasteEditText;
import cn.sx.decentworld.widget.RecorderButton;
import cn.sx.decentworld.widget.RecorderButton.AudioFinishedRecordeListener;

import com.alibaba.fastjson.JSON;
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
 * @Description: 聊天界面 进入此界面需要5个参数 1.chatType （确定的） 2.otherID（确定的）
 *               3.chatRelationship (判断是否属于好友，即是否在联系人列表中) 4.otherNickname
 *               （如果是好友，且非匿名，则本地拿取，否则从网络获取） 5.otherWorth;
 *               （如果是好友，且非匿名，则本地拿取，否则从网络获取）
 * @author: cj
 * @date: 2015年7月8日 下午12:42:05
 */
@EActivity(R.layout.activity_chat)
public class ChatActivity extends BaseFragmentActivity implements
		OnTouchListener, AudioFinishedRecordeListener,
		OnRefreshListener<ListView>, OnClickListener, OnMessageClickListener {
	private static final String TAG = "ChatActivity";
	public static final int REQUEST_CODE_EMPTY_HISTORY = 0;
	public static final int REQUEST_CODE_SELECT_FILE = 1;// 选择文件
	public static final int REQUEST_CODE_OPEN_GALLERY = 2;// 选择照片
	public static final int REQUEST_CODE_MAP = 3;// 地图
	public static final int REQUEST_CODE_CARD = 4;// 名片
	static int resendPos;
	// 标题栏控件
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_right_btn)
	ImageView main_header_right_btn;
	@ViewById(R.id.main_header_right_tv)
	TextView main_header_right_tv;
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
	@ViewById(R.id.list)
	PullToRefreshListView listView;

	// 表情
	@ViewById(R.id.vPager)
	ViewPager expressionViewpager;
	private List<String> reslist;

	@ViewById(R.id.btn_location_ll)
	LinearLayout btn_location_ll;

	private ClipboardManager clipboard;
	private InputMethodManager manager;

	@ViewById(R.id.recording_container)
	View recordingContainer;

	@ViewById(R.id.recording_hint)
	TextView recordingHint;

	@ViewById(R.id.mic_image)
	ImageView micImage;

	// 工具型
	@Bean
	KeyboardComponent KeyboardComponent;// 对键盘的操作
	@Bean
	ChoceAndTakePictureComponent choceAndTakePictureComponent; // 选择相片
	@Bean
	ChatComponent chatComponent;
	@Bean
	ToastComponent toast;
	@Bean
	DWSMessageManager dwsMessageManager;

	// 聊天型
	private FragmentManager fragmentManager;
	private DWMessageAdapter adapter;
	private List<DWMessage> listMsg = new ArrayList<DWMessage>();
	private ChatManager chatManager;

	// 其它
	private int messagePosition;
	private PopupWindow mPop;
	private Drawable[] micImages;
	public String playMsgId;
	private Handler micImageHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			// 切换msg切换图片
			micImage.setImageDrawable(micImages[msg.what]);
		}
	};
	private File cameraFile;
	// 当创建完组后传过来这参数，即实现按返回键，回到MainActivity的tab1界面
	private int to_tab;
	// 当前用户
	private String dwID;
	// 对方
	public static int chatType;
	private int chatRelationship;
	public static String otherID;
	public static String otherNickname;
	private String otherWorth = "";
	// 标题的内容，不包括身价
	private String title_name = "";
	private String otherIcon;

	/**
	 * 入口
	 */
	@AfterViews
	public void init() {
		new InitAsync().execute();
	}

	private void initView() {
		EventBus.getDefault().register(this);
		dwID = DecentWorldApp.getInstance().getDwID();
		parseIntent();
		chatManager = DecentWorldApp.getInstance().getConnectionImpl()
				.getChatManager();
		setUpView();

		initTitle();
		adapter = new DWMessageAdapter(ChatActivity.this, listMsg);
		adapter.setOnMessageClickListener(this);
		loadHistoryMsg(dwID, otherID);
		setListViewBottom();
		listView.getRefreshableView().setOnTouchListener(this);
		initExpression();
		buttonPressToSpeak.setAudioFishedRecorde(this);
		// 获取自己的身家
		getUserInfo.getWealth(dwID, mhandler, GET_WEALTH);
		listView.setOnRefreshListener(this);
		listView.setAdapter(adapter);
	}

	/**
	 * 加载历史记录
	 * 
	 * @param dwID
	 *            用户ID
	 * @param otherID
	 *            对方ID
	 */
	private void loadHistoryMsg(String dwID, String otherID) {
		int count = listMsg.size();
		if (count > 0) {
			// 再次从本地数据库拿取10条记录，传入列表中最小想id
			long minMsgId = listMsg.get(0).getMid();
			LogUtils.i(TAG, "count = " + count + ",minMsgId=" + minMsgId);
			dwsMessageManager.getNextPageMsg(dwID, otherID, minMsgId, chatType);
		} else {
			// 首先从本地数据库拿取10条记录
			dwsMessageManager.getFistPageMsg(dwID, otherID, chatType);
			// 如果本地数据库提供的记录<=0条，则调用接口从服务器拿取服务器拿取
		}
	}

	/**
	 * 得到历史记录，更新界面
	 * 
	 * @param list
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_SINGLE_HISTORY_MSG)
	public void loadHistoryMsgAndUpdataUI(List<DWMessage> list) {
		LogUtils.i(TAG, "更新界面,list.size =" + list.size());
		if (list.size() > 0) {
			// 排序
			Collections.sort(list);
			listMsg.addAll(0, list);
		} else {
			LogUtils.i(TAG, "从网络上获取的数据为空，或者其它错误");
		}
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
		listView.onRefreshComplete();
	}

	class InitAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			initView();
		}
	}

	// /**
	// * 下载语音
	// */
	// private int downloadAudio(String uri, String fileName) {
	// // InputStream inputStream = null;
	// int result = Constants.SUCC;
	// if (!FileUtils.isFileExist(Constants.HomePath)) {
	// FileUtils.createSDDir(Constants.HomePath);
	// }
	// if (!FileUtils.isFileExist(Constants.HomePath
	// + Constants.AudioReceivePath)) {
	// FileUtils.createSDDir(Constants.HomePath
	// + Constants.AudioReceivePath);
	// }
	// result = HttpDownloader.downFile(uri, fileName);
	// return result;
	// }

	// class DownLoadAsyn extends AsyncTask<String, Void, Void> {
	// private DWMessage message;
	// private JSONObject ob;
	//
	// public DownLoadAsyn(DWMessage dwMessage, JSONObject ob) {
	// message = dwMessage;
	// this.ob = ob;
	// }
	//
	// @Override
	// protected Void doInBackground(String... params) {
	// int send = downloadAudio(params[0], params[1]);
	// message.setBody(params[1]);
	// message.setSendSuccess(send);
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// super.onPostExecute(result);
	// message.setIsRead(0);
	// if (ob.getString("senderID").equals(dwID)) {
	// message.setDirect(DWMessage.SEND);
	// } else {
	// message.setDirect(DWMessage.RECEIVE);
	// }
	// message.save();
	// // 刷新完成
	// listView.onRefreshComplete();
	// // 并通知适配器更新
	// listMsg.clear();
	// initDWMessage(dwID, user_dwID);
	// adapter.notifyDataSetChanged();
	// }
	// }
	/**
	 * 解析启动页面传递过来的参数
	 */
	private void parseIntent() {
		// 启动界面传递过来的数据
		chatType = getIntent().getIntExtra("chatType",
				DWMessage.CHAT_TYPE_SINGLE);// 判断单聊还是单聊匿名
		chatRelationship = getIntent().getIntExtra("chatRelationship",
				DWMessage.CHAT_RELATIONSHIP_FRIEND);
		otherID = getIntent().getStringExtra("user_dwID");// 聊天对方的dwid，群聊则为群的dwid
		otherNickname = getIntent().getStringExtra("user_nickname");// 聊天对方的昵称，如果为群聊或者兴趣组，则传过来的值为组名
		otherWorth = getIntent().getStringExtra("user_worth");
		LogUtils.i(TAG, "dwID=" + dwID + "\nchatType=" + chatType
				+ "\nchatRelationship=" + chatRelationship + "\nuser_dwID="
				+ otherID + "\nuser_nickname=" + otherNickname
				+ "\nuser_worth=" + otherWorth);
		otherIcon = ImageUtils.getIconByDwID(otherID, ImageUtils.ICON_SMALL);// 聊天对方的头像，如果为群，则为群的头像
		to_tab = getIntent().getIntExtra("tab", -1);
	}

	/**
	 * 初始化身家、身价
	 */
	@Bean
	GetUserInfo getUserInfo;
	private static final int GET_WEALTH = 1;
	Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_WEALTH:
				setEditText(msg.obj.toString());
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 设置编辑框显示的内容，格式如下： 1）好友单聊：自己身家 + 2）陌生人单聊 ：自己身家 - 3）好友匿名匿名都是自己身家 -
	 * 4）陌生人匿名都是自己身家-
	 * 
	 * @param wealth
	 */

	private void setEditText(String wealth) {
		if (chatType == DWMessage.CHAT_TYPE_SINGLE) {
			if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
				mEditTextContent.setHint(wealth + "+");
			} else if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
				mEditTextContent.setHint(wealth + "-");
			}
		} else if (chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
			if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
				mEditTextContent.setHint(wealth + "-");
			} else if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
				mEditTextContent.setHint(wealth + "-");
			}
		}
	}

	/**
	 * 从数据库中获取DWMessage消息列表
	 * 
	 * @param dwID
	 * @param otherID
	 */
	private void initDWMessage(String dwID, String otherID) {
		List<DWMessage> list = DWMessage.queryAll();
		for (DWMessage dwMessage : list) {
			if ((dwMessage.getFrom().equals(dwID) && dwMessage.getTo().equals(
					otherID))
					|| (dwMessage.getFrom().equals(otherID) && dwMessage
							.getTo().equals(dwID))) {
				listMsg.add(dwMessage);
			}
		}
	}

	private void setUpView() {
		iv_emoticons_normal.setOnClickListener(this);
		iv_emoticons_checked.setOnClickListener(this);
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	/**
	 * 初始化标题
	 */
	private void initTitle() {
		// 导航栏
		main_header_left.setVisibility(View.VISIBLE);
		main_header_right_btn.setVisibility(View.VISIBLE);
		main_header_right_btn
				.setImageResource(R.drawable.chat_top_right_selector);
		main_header_right_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChatActivity.this,
						ChatSettingActivity_.class);
				intent.putExtra("otherID", otherID);
				intent.putExtra("otherNick", title_name);
				startActivity(intent);
			}
		});
		main_header_right_tv.setVisibility(View.INVISIBLE);
		// 显示昵称和身价
		if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
			if (chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
				if (otherNickname.endsWith("[匿名聊天]")) {
					title_name = otherNickname.substring(0,
							otherNickname.length() - 6);
				} else {
					title_name = otherNickname;
				}
				main_header_title.setText(title_name + "(" + otherWorth + ")");
			}
			if (CommUtil.isBlank(otherWorth)) {
				getUserInfo.getWorth(otherID, handler, 6);
			} else {
				title_name = otherNickname;
				main_header_title.setText(title_name + "(" + otherWorth + ")");
			}
		} else if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
			// 获取匿名和身价
			new CommHttp(this).getNicknameAndWorth(otherID, handler, 5);
		}

	}

	/**
	 * 初始化文本框里面的表情
	 */
	private void initExpression() {
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
		reslist = getExpressionRes(35);
		List<View> views = new ArrayList<View>();
		View gv1 = getGridChildView(1);
		View gv2 = getGridChildView(2);
		views.add(gv1);
		views.add(gv2);
		expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
		edittext_layout.requestFocus();
		mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					edittext_layout
							.setBackgroundResource(R.drawable.input_bar_bg_active);
				} else {
					edittext_layout
							.setBackgroundResource(R.drawable.input_bar_bg_normal);
				}

			}
		});
		mEditTextContent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edittext_layout
						.setBackgroundResource(R.drawable.input_bar_bg_active);
				more.setVisibility(View.GONE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.GONE);
				setListViewBottom();
			}
		});
		// 监听文字框
		mEditTextContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					btnMore.setVisibility(View.GONE);
					buttonSend.setVisibility(View.VISIBLE);
				} else {
					btnMore.setVisibility(View.VISIBLE);
					buttonSend.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * 从图库获取图片
	 */
	private void selectPicFromLocal() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, REQUEST_CODE_OPEN_GALLERY);
	}

	/**
	 * 发送语音
	 * 
	 * @param mTime
	 * @param FilePath
	 * @param dwMessage
	 */
	private void sendAudio(Float mTime, String FilePath, String txtMsgID) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("dwID", dwID);
		hashmap.put("toID", otherID);
		hashmap.put("type", chatType + "");
		hashmap.put("length", Math.round(mTime) + "");
		hashmap.put("msgID", txtMsgID);
		// 语音文件
		File[] images = new File[] { new File(FilePath) };
		chatComponent.sendFileWithParams(hashmap, images, "/sendAudio",
				fileProgHandler, txtMsgID);
	}

	/**
	 * 发送图片
	 * 
	 * @param file
	 * @param dwMessage
	 */
	private void sendPicture(File file, String txtMsgID) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("dwID", dwID);
		hashmap.put("toID", otherID);
		hashmap.put("type", chatType + "");
		hashmap.put("msgID", txtMsgID);
		// 图片文件
		File[] images = new File[] { file };
		chatComponent.sendFileWithParams(hashmap, images, "/sendPicture",
				fileProgHandler, txtMsgID);
	}

	/**
	 * 更新DWMessage的传输状态,这一步可以确保在没有收到身家消息回执时也能改变数据库，
	 * 正常情况下，发送语音和图片成功后，服务器会回执一条subject = wealth的消息。
	 */
	private Handler fileProgHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String txtMsgID = msg.obj.toString();
			DWMessage dwMessage = DWSMessageManager.queryItem(txtMsgID);
			if (dwMessage == null) {
				LogUtils.i(TAG, "不存在这条消息");
				return;
			}
			switch (msg.what) {
			case Constants.SUCC:
				dwMessage.setSendSuccess(1);// 1代表成功
				dwMessage.save();
				break;
			case Constants.FAILURE:
				dwMessage.setSendSuccess(0);// 0代表失败
				dwMessage.save();
				sendFailure(txtMsgID);
				break;
			}
		};
	};

	/**
	 * 点击声音图标
	 */
	@Click(R.id.btn_set_mode_voice)
	public void btn_set_mode_voice() {
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
	 * 显示键盘图标
	 *
	 * @param view
	 */
	public void setModeKeyboard(View view) {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		edittext_layout.setVisibility(View.VISIBLE);
		more.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		btn_set_mode_voice.setVisibility(View.VISIBLE);
		mEditTextContent.requestFocus();
		buttonPressToSpeak.setVisibility(View.GONE);
		if (TextUtils.isEmpty(mEditTextContent.getText())) {
			btnMore.setVisibility(View.VISIBLE);
			buttonSend.setVisibility(View.GONE);
		} else {
			btnMore.setVisibility(View.GONE);
			buttonSend.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 显示语音图标按钮,开启语音聊天模式
	 *
	 * @param view
	 */
	public void setModeVoice(View view) {
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
	public void editClick(View v) {
		if (more.getVisibility() == View.VISIBLE) {
			more.setVisibility(View.GONE);
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 处理接受到的消息的handler
	 */

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String worth;
			switch (msg.what) {
			case 1:
				// 更新聊天列表
				DWMessage dwMessage = (DWMessage) msg.obj;
				if (dwMessage.getWealth() != null) {
					setEditText(dwMessage.getWealth());
				}
				listMsg.add(dwMessage);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				// 服务器发来身家信息，更新身家
				LogUtils.i(TAG, "这段代码有用？handler...msg.what = 2");
				setEditText(msg.obj.toString());
				break;
			case 3:
				// 更新聊天列表(listMsg不增加)
				LogUtils.i(TAG, "这段代码有用？handler...msg.what = 3");
				DWMessage dwMessage2 = (DWMessage) msg.obj;
				if (dwMessage2.getWealth() != null) {
					setEditText(dwMessage2.getWealth());
				}
				adapter.notifyDataSetChanged();
				break;
			case 4:
				// 初始化标题栏处的身价
				worth = msg.obj.toString();
				LogUtils.i(TAG, "进来时user_worth为空，从网络获取的身价worth= " + worth);
				title_name = otherNickname;
				main_header_title.setText(title_name + "(" + worth + ")");
				break;
			case 5:
				String result = msg.obj.toString();
				JSONObject jsonObject = JSON.parseObject(result);
				String nick = jsonObject.getString("nick");
				worth = jsonObject.getString("worth");
				title_name = nick;
				main_header_title.setText(title_name + "(" + worth + ")");
				break;
			case 6:
				worth = msg.obj.toString();
				title_name = otherNickname;
				main_header_title.setText(title_name + "(" + worth + ")");
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 消息图标点击事件
	 * 
	 * @param view
	 */
	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btn_send) {
			// 发送消息
			String textContent = mEditTextContent.getText().toString();
			// 构造Message
			Message message = new Message(otherID, Message.Type.chat);
			LogUtils.i(TAG, "发送一句话，Message.packetID=" + message.getPacketID());
			message.setFrom(dwID);
			message.setSubject("chat");
			// 构造DWMessage
			DWMessage dwMessage = new DWMessage(DWMessage.TXT, DWMessage.SEND);
			dwMessage.setTo(otherID);
			dwMessage.setChatType(chatType);
			dwMessage.setChatRelationship(chatRelationship);
			dwMessage.setBody(textContent);
			dwMessage.setTxtMsgID(message.getPacketID());
			dwMessage.setMid(getTempMid());
			// 发送Message
			if (!XmppHelper.getConn().isConnected()) {
				// 没有连接，处理
				dwMessage.setSendSuccess(0);
				LogUtils.i(TAG, "断开连接");
			} else {
				message.setBody(DWMessage.toJson(dwMessage));
				sendTXTMessage(message);
				LogUtils.i(TAG, "没有断开连接");
			}
			dwMessage.save();
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			mEditTextContent.setText("");
			KeyboardComponent.dismissKeyboard(mEditTextContent);
		} else if (id == R.id.btn_take_picture) {
			// 点击拍照图标
			selectPicFromCamera();
		} else if (id == R.id.btn_picture) {
			// 点击图片图标
			selectPicFromLocal();
		} else if (id == R.id.btn_location) {
			// 位置
			startActivityForResult(new Intent(this, MapActivity.class),
					REQUEST_CODE_MAP);
		} else if (id == R.id.iv_emoticons_normal) {
			// 点击显示表情框
			more.setVisibility(View.VISIBLE);
			iv_emoticons_normal.setVisibility(View.INVISIBLE);
			iv_emoticons_checked.setVisibility(View.VISIBLE);
			btnContainer.setVisibility(View.GONE);
			emojiIconContainer.setVisibility(View.VISIBLE);
			KeyboardComponent.dismissKeyboard(view);
		} else if (id == R.id.iv_emoticons_checked) {
			// 点击隐藏表情框
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			btnContainer.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
		} else if (id == R.id.btn_voice) {
		} else if (id == R.id.btn_video) {
		} else if (id == R.id.btn_card) {
			// 转发名片
			toast.show("转发名片");
			// 打开联系人列表，选择联系人
			Intent intent = new Intent(ChatActivity.this,
					PickContactActivity_.class);
			intent.putExtra(PickContactActivity.PICK_TYPE,
					PickContactActivity.TYPE_SINGLE_PICK);
			startActivityForResult(intent, REQUEST_CODE_CARD);
		}
	}

	/**
	 * 获得临时的mid
	 * 
	 * @return
	 */
	public long getTempMid() {
		long tempMid;
		if (listMsg.size() > 0) {
			tempMid = listMsg.get(listMsg.size() - 1).getMid();
		} else {
			tempMid = 0;
		}
		LogUtils.i(TAG, "临时的mid=" + tempMid);
		return tempMid;
	}

	/**
	 * 发送文字信息
	 */
	private void sendTXTMessage(Message message) {
		Chat chat = chatManager.createChat(otherID + Constants.SERVER_NAME,
				null);
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 照相获取图片
	 */
	public void selectPicFromCamera() {
		choceAndTakePictureComponent.takePicture();
	}

	/**
	 * 发送位置信息
	 *
	 * @param latitude
	 * @param longitude
	 * @param imagePath
	 * @param locationAddress
	 */
	private void sendLocationMsg(double latitude, double longitude,
			String imagePath, String locationAddress) {

	}

	/**
	 * 获得表情资源
	 * 
	 * @param getSum
	 * @return
	 */
	public List<String> getExpressionRes(int getSum) {
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;
			reslist.add(filename);
		}
		return reslist;
	}

	/**
	 * 获取表情的gridview的子view
	 * 
	 * @param i
	 * @return
	 */
	private View getGridChildView(int i) {
		View view = View.inflate(this, R.layout.expression_gridview, null);
		ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();
		if (i == 1) {
			List<String> list1 = reslist.subList(0, 20);
			list.addAll(list1);
		} else if (i == 2) {
			list.addAll(reslist.subList(20, reslist.size()));
		}
		list.add("delete_expression");
		final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
				1, list);
		gv.setAdapter(expressionAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,

			int position, long id) {
				String filename = expressionAdapter.getItem(position);
				try {
					// 文字输入框可见时，才可输入表情
					// 按住说话可见，不让输入表情
					if (buttonSetModeKeyboard.getVisibility() != View.VISIBLE) {

						if (filename != "delete_expression") { // 不是删除键，显示表情
																// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
							Class clz = Class
									.forName("cn.sx.decentworld.common.SmileUtils");
							Field field = clz.getField(filename);
							mEditTextContent.append(SmileUtils.getSmiledText(
									ChatActivity.this, (String) field.get(null)));
						} else { // 删除文字或者表情
							if (!TextUtils.isEmpty(mEditTextContent.getText())) {

								int selectionStart = mEditTextContent
										.getSelectionStart();// 获取光标的位置
								if (selectionStart > 0) {
									String body = mEditTextContent.getText()
											.toString();
									String tempStr = body.substring(0,
											selectionStart);
									int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
									if (i != -1) {
										CharSequence cs = tempStr.substring(i,
												selectionStart);
										if (SmileUtils.containsKey(cs
												.toString()))
											mEditTextContent.getEditableText()
													.delete(i, selectionStart);
										else
											mEditTextContent.getEditableText()
													.delete(selectionStart - 1,
															selectionStart);
									} else {
										mEditTextContent.getEditableText()
												.delete(selectionStart - 1,
														selectionStart);
									}
								}
							}

						}
					}
				} catch (Exception e) {
				}

			}
		});
		return view;
	}

	/**
	 * 点击导航栏的返回按钮
	 */
	@Click(R.id.main_header_left)
	public void back() {
		hideKeyboard();
		this.finish();
	}

	/**
	 * 用户设置界面跳转
	 */
	@Click(R.id.main_header_right)
	public void goDetail() {
		if (chatType == DWMessage.CHAT_TYPE_SINGLE
				&& chatRelationship == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
			Intent intent = new Intent(getApplication(),
					ChatSettingActivity_.class);
			intent.putExtra("otherID", otherID);
			// intent.putExtra(icon, "toChatUserIcon");
			startActivityForResult(intent, REQUEST_CODE_EMPTY_HISTORY);
		}
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	// 发送透传消息
	private void sendCmd(String toUsername) {

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
				// // 清空会话
			} else if (requestCode == ChoceAndTakePictureComponent.TAKE_PICKTURE) {
				// 发送照片
				File file = new File(
						choceAndTakePictureComponent.getImageName());
				Message message = new Message();
				LogUtils.i(TAG,
						"发送一张图片，Message.packetID=" + message.getPacketID());
				// 构造DWMessage
				DWMessage dwMessage = new DWMessage(DWMessage.IMAGE,
						DWMessage.SEND);
				dwMessage.setTo(otherID);
				dwMessage.setChatType(chatType);
				dwMessage.setChatRelationship(chatRelationship);
				dwMessage.setTxtMsgID(message.getPacketID());
				dwMessage.setUri(file.getAbsolutePath());
				dwMessage.setLocalUrl(file.getAbsolutePath());
				dwMessage.setMid(getTempMid());
				dwMessage.save();
				btnContainer.setVisibility(View.GONE);
				sendPicture(file, message.getPacketID());
			} else if (requestCode == REQUEST_CODE_OPEN_GALLERY) {
				// 发送本地图片
				if (null != data.getData()) {
					Uri uri = data.getData();
					String filePath = ImageUtils.getRealFilePath(mContext, uri);
					File file = handlerFile(filePath);
					LogUtils.i(TAG, "照片路径为：" + file.getAbsolutePath());
					// 构造Message
					Message message = new Message();
					LogUtils.i(TAG, "Message.packetID=" + message.getPacketID());
					// 构造DWMessage
					DWMessage dwMessage = new DWMessage(DWMessage.IMAGE,
							DWMessage.SEND);
					dwMessage.setTo(otherID);
					dwMessage.setChatType(chatType);
					dwMessage.setChatRelationship(chatRelationship);
					dwMessage.setTxtMsgID(message.getPacketID());
					dwMessage.setUri(file.getAbsolutePath());
					dwMessage.setLocalUrl(file.getAbsolutePath());
					dwMessage.setMid(getTempMid());
					// 发送Message
					if (!XmppHelper.getConn().isConnected()) {
						// 没有连接，处理
						dwMessage.setSendSuccess(0);
					} else {
						// 发送图片
						sendPicture(file, message.getPacketID());
					}
					dwMessage.save();
					// 处理界面
					refreshListView(dwMessage);
					refreshConversation(dwMessage);
					btnContainer.setVisibility(View.GONE);
				}
			} else if (requestCode == REQUEST_CODE_SELECT_FILE) {
				// 发送选择的文件
				if (data != null) {
					Uri uri = data.getData();
					if (uri != null) {
						// sendFile(uri);
					}
				}
			} else if (requestCode == REQUEST_CODE_MAP) {
				// 地图
				double latitude = data.getDoubleExtra("latitude", 0);
				double longitude = data.getDoubleExtra("longitude", 0);
				String locationAddress = data.getStringExtra("address");
				if (locationAddress != null && !locationAddress.equals("")) {
					toggleMore(more);
					sendLocationMsg(latitude, longitude, "", locationAddress);
				} else {
					String st = getResources().getString(
							R.string.unable_to_get_loaction);
					Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
				}
			} else if (requestCode == REQUEST_CODE_CARD) {
				// 转发名片,被转发人的dwID如下
				String forwardDwId = data.getStringExtra("dwID");
				LogUtils.i(TAG, "被转发的人的dwID=" + forwardDwId);

				// 构造Message
				Message message = new Message(otherID, Message.Type.chat);
				LogUtils.i(TAG,
						"发送一张名片，Message.packetID=" + message.getPacketID());
				message.setFrom(dwID);
				message.setSubject("card");
				// 构造DWMessage
				DWMessage dwMessage = new DWMessage(DWMessage.CARD,
						DWMessage.SEND);
				dwMessage.setTo(otherID);
				dwMessage.setChatType(chatType);
				dwMessage.setChatRelationship(chatRelationship);
				dwMessage.setTxtMsgID(message.getPacketID());
				// /////发送名片添加的
				dwMessage.setForwardDwId(forwardDwId);
				dwMessage.setForwardName(ContactUser
						.getDisplayNameByDwID(forwardDwId));
				// ////////
				dwMessage.save();
				// 发送Message
				message.setBody(DWMessage.toJson(dwMessage));
				sendTXTMessage(message);
				// 处理界面
				refreshListView(dwMessage);
				refreshConversation(dwMessage);
				btnContainer.setVisibility(View.GONE);
			}
		}
		setListViewBottom();
	}

	private void setListViewBottom() {
		listView.getRefreshableView().setSelection(
				listView.getRefreshableView().getCount() - 1);

	}

	private File handlerFile(String filePath) {
		File file = null;
		if (ImageUtils.fileLength(filePath) > 2 * 1024 * 1024) {
			Bitmap bitmap = ImageUtils.scalePic(filePath);
			String picPath = Constants.HOME_PATH + "/temp"
					+ ImageUtils.generateFileName() + ".png";
			ImageUtils.saveBitmap(picPath, bitmap);
			file = new File(picPath);
		} else {
			file = new File(filePath);
		}
		return file;
	}

	private void transmit(int requestCode, Intent data) {
		String dwID = data.getStringExtra(Constants.DW_ID);
		switch (requestCode) {
		case Constants.TEXT_REQUEST_CODE:
			sendTxtMessage(dwID);
			break;
		case Constants.IMAGE_REQUEST_CODE:
			sendImageAndAudioMessage(dwID);
			break;
		case Constants.AUDIO_REQUEST_CODE:
			sendImageAndAudioMessage(dwID);
			break;
		}
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

	private void sendTxtMessage(String userDwID) {
		Chat chat = chatManager.createChat(userDwID + Constants.SERVER_NAME,
				null);
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
		} catch (XMPPException e) {
			toast.show("消息发送失败");
			return;
		}
		toast.show("已发送");
	}

	/**
	 * 设置聊天背景
	 */
	private void getchoiceimage() {
		try {
			File f = (File) getIntent().getSerializableExtra("values");
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(f.getAbsolutePath(), options);
			int inSampleSize = 2;
			options.inJustDecodeBounds = false;
			options.inSampleSize = inSampleSize;
			Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
					options);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			byte[] b = baos.toByteArray();
			Bitmap img = BitmapFactory.decodeByteArray(b, 0, b.length);
			activity_chat_bg.setImageBitmap(img);
			// if (ChatBg.isExsitByUsername(otherID)) {
			// ChatBg.deleteByUsername(otherID);
			// ChatBg bg = new ChatBg();
			// bg.username = otherID;
			// bg.chatbg = Base64.encodeToString(b, Base64.DEFAULT);
			// bg.save();
			// } else {
			// ChatBg bg = new ChatBg();
			// bg.username = otherID;
			// bg.chatbg = Base64.encodeToString(b, Base64.DEFAULT);
			// bg.save();
			// }
		} catch (Exception e) {

		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();

		Log.e("ChatActivity", "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// DecentWorldApp.getInstance().isShowChatActivity = false;
		adapter.stopMediaPlayer();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
			EventBus.getDefault().post(otherID + chatType,
					NotifyByEventBus.NT_CLEAR_CONVERSATION_UNREAD);
		} else if (chatRelationship == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
			EventBus.getDefault().post(otherID + chatType,
					NotifyByEventBus.NT_CLEAR_STRANGER_CONVERSATION_UNREAD);
		}
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		getchoiceimage();
	}

	/**
	 * @return the otherID
	 */
	public String getUser_dwID() {
		return otherID;
	}

	/**
	 * @param otherID
	 *            the otherID to set
	 */
	public void setUser_dwID(String otherID) {
		this.otherID = otherID;
	}

	// public static ChatActivity getInstance() {
	// return activityInstance;
	// }

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
			if (chatType == DWMessage.CHAT_TYPE_SINGLE
					|| chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
				String wealth = jsonObject.getString("wealth");
				long mid = jsonObject.getLongValue("id");
				LogUtils.i(TAG, "收到一条subject = wealth的消息，wealth=" + wealth
						+ "，mid=" + mid + "，chatType=" + chatType);
				if (CommUtil.isNotBlank(wealth)) {
					LogUtils.i(TAG, "updateWealth,更新身家为：" + wealth);
					// tv_chat_user_asset.setText(wealth);
					setEditText(wealth);
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
				LogUtils.i(TAG, "txtMsgID=" + txtMsgID + ",mId=" + mId + ",i="
						+ i);
				listMsg.get(i).setSendSuccess(1);
				listMsg.get(i).setMid(mId);
				adapter.notifyDataSetChanged();
				return;
			}
		}
		LogUtils.i(TAG, "数据库中无此消息");
	}

	/**
	 * 更新收到的消息的通知
	 */
	@Subscriber(tag = NotifyByEventBus.NT_UPDATE_CHAT_LISTVIEW_RECEIVE_FILE)
	public void updateRevFile(DWMessage dwMessage) {
		if (dwMessage.getFrom().equals(otherID)) {
			// 收到图片、语音时，更改（增加item）页面UI
			dwMessage.save();
			android.os.Message osMessage = new android.os.Message();
			osMessage.what = 1;
			osMessage.obj = dwMessage;
			handler.sendMessage(osMessage);
		}
	}

	/**
	 * 由DWMessageAdapter路由过来的【重新发送文字】的通知
	 */
	@Subscriber(tag = NotifyByEventBus.NT_RESET_TXT)
	public void resetText(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送文字信息");
		// 发送Message
		if (XmppHelper.getConn().isConnected()) {
			LogUtils.i(TAG, "有网，重新发送文字信息");
			// 重新发送文字消息
			listMsg.remove(dwMessage);

			// 构造Message
			Message message = new Message(otherID, Message.Type.chat);
			message.setFrom(dwID);
			message.setSubject("chat");
			message.setPacketID(dwMessage.getTxtMsgID());
			// 修改DWMessage
			dwMessage.setSendSuccess(2);
			dwMessage.setTime(String.valueOf(System.currentTimeMillis()));
			dwMessage.setMid(getTempMid());

			message.setBody(DWMessage.toJson(dwMessage));
			sendTXTMessage(message);

			dwMessage.save();
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			KeyboardComponent.dismissKeyboard(mEditTextContent);
		}
	}

	/**
	 * 由DWMessageAdapter路由过来的【重新发送语音】的通知
	 */
	@Subscriber(tag = NotifyByEventBus.NT_RESET_VOICE)
	public void resetVoice(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送语音信息");
		// 发送Message
		if (XmppHelper.getConn().isConnected()) {
			LogUtils.i(TAG, "有网，重新发送语音信息");
			// 重新发送文字消息
			listMsg.remove(dwMessage);
			// 修改DWMessage
			dwMessage.setSendSuccess(2);
			dwMessage.setTime(String.valueOf(System.currentTimeMillis()));
			dwMessage.setMid(getTempMid());
			// 发送语音
			sendAudio(dwMessage.getVoiceTime(), dwMessage.getUri(),
					dwMessage.getTxtMsgID());
			dwMessage.save();
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			// 处理界面
			hideKeyboard();
			KeyboardComponent.dismissKeyboard(mEditTextContent);
		}

	}

	/**
	 * 由DWMessageAdapter路由过来的【重新发送图片】的通知
	 * 
	 * @param dwMessage1
	 */
	@Subscriber(tag = NotifyByEventBus.NT_RESET_IMAGE)
	public void resetImage(DWMessage dwMessage) {
		LogUtils.i(TAG, "重新发送图片信息");
		// 发送Message
		if (XmppHelper.getConn().isConnected()) {
			LogUtils.i(TAG, "有网，重新发送图片信息");
			// 重新发送文字消息
			listMsg.remove(dwMessage);
			// 修改DWMessage
			dwMessage.setSendSuccess(2);
			dwMessage.setTime(String.valueOf(System.currentTimeMillis()));
			dwMessage.setMid(getTempMid());
			// 发送语音
			// 发送图片
			sendPicture(new File(dwMessage.getUri()), dwMessage.getTxtMsgID());
			dwMessage.save();
			// 处理界面
			refreshListView(dwMessage);
			refreshConversation(dwMessage);
			// 处理界面
			btnContainer.setVisibility(View.GONE);
		}
	}

	/**
	 * 由DWMessageAdapter路由过来的【重新发送图片】的通知
	 * 
	 * @param dwMessage1
	 */
	@Subscriber(tag = NotifyByEventBus.NT_RESET_CARD)
	public void resetCard(DWMessage dwMessage1) {
		// 重新发送文件
		String filePath = dwMessage1.getBody();
		// 删除该则消息
		listMsg.remove(dwMessage1);
		adapter.notifyDataSetChanged();
		dwMessage1.setSendSuccess(2);
		dwMessage1.setTime(String.valueOf(System.currentTimeMillis()));
		sendPicture(new File(filePath), dwMessage1.getTxtMsgID());
	}

	View mView;

	private void showPop(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		if (null == mPop || null == mView) {
			mView = View.inflate(mContext, R.layout.layout_copy_transmit, null);
			TextView tvCopy = (TextView) mView.findViewById(R.id.tv_copy);
			tvCopy.setOnClickListener(mOnClicklistener);
			TextView tvTransmit = (TextView) mView
					.findViewById(R.id.tv_transmit);
			tvTransmit.setOnClickListener(mOnClicklistener);
			mPop = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT, true);
			mPop.setBackgroundDrawable(new BitmapDrawable());
			mView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			mPop.setOutsideTouchable(true);
		}
		int popupWidth = mView.getMeasuredWidth();
		int popupHeight = mView.getMeasuredHeight();
		mPop.showAtLocation(v, Gravity.NO_GRAVITY,
				(location[0] + v.getWidth() / 2) - popupWidth / 2, location[1]
						- popupHeight);
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
				Intent intent = new Intent(mContext, TransmitActivity_.class);
				startActivityForResult(intent, Constants.TEXT_REQUEST_CODE);
				break;
			}
			mPop.dismiss();
		}
	};

	@Override
	public void onMessageClick(View view) {
		if (null == view) {
			return;
		}
		messagePosition = (Integer) view.getTag(Constants.ITEM_KEY);
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_chatcontent:
			showPop(view);
			break;
		case R.id.iv_sendPicture:
			intent = new Intent(mContext, TransmitActivity_.class);
			startActivityForResult(intent, Constants.IMAGE_REQUEST_CODE);
			break;
		case R.id.iv_voice:
			intent = new Intent(mContext, TransmitActivity_.class);
			startActivityForResult(intent, Constants.AUDIO_REQUEST_CODE);
			break;
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

		listMsg.add(dwMessage);
		adapter.notifyDataSetChanged();
		listView.getRefreshableView().setSelection(
				listView.getRefreshableView().getCount() - 1);
	}

	/**
	 * 更新会话列表
	 * 
	 * @param dwMessage
	 */
	private void refreshConversation(DWMessage dwMessage) {
		// 通知界面更新
		if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND) {
			EventBus.getDefault().post(dwMessage,
					NotifyByEventBus.NT_REFRESH_CONVERSATION);
		} else if (dwMessage.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_STRANGER) {
			EventBus.getDefault().post(dwMessage,
					NotifyByEventBus.NT_NOTIFY_STRANGER_UPDATA);
		}

	}

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
		LogUtils.i(TAG, "数据库中无此消息");
	}

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
		main_header_title.setText(title_name + "(" + worth + ")");
	}

	/**
	 * 身家不足，消息状态改为发送失败
	 * 
	 * @param msgID
	 *            消息ID 从最后一项向前开始遍历，当找到对应的消息ID时结束。
	 */
	@Subscriber(tag = NotifyByEventBus.NT_WEALTH_SHORTAGE)
	public void wealthShortage(String packetId) {
		toast.show("身家不足");
		for (int i = listMsg.size() - 1; i >= 0; i--) {
			if (listMsg.get(i).getTxtMsgID().equals(packetId)) {
				listMsg.get(i).setSendSuccess(0);
				adapter.notifyDataSetChanged();
				return;
			}
		}
	}

	/**
	 * 触摸聊天界面
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
	 * 语音聊天，按住说话
	 */
	@Override
	public void FinishedRecordeListener(Float mTime, String filePath) {
		LogUtils.e("bm", "filePath--" + filePath);
		// 构造Message
		Message message = new Message();
		LogUtils.i(TAG, "发送一条语音，Message.packetID=" + message.getPacketID());
		// 构造DWMessage
		DWMessage dwMessage = new DWMessage(DWMessage.VOICE, DWMessage.SEND);
		dwMessage.setTo(otherID);
		dwMessage.setChatType(chatType);
		dwMessage.setChatRelationship(chatRelationship);
		dwMessage.setTxtMsgID(message.getPacketID());
		dwMessage.setUri(filePath);
		dwMessage.setMid(getTempMid());
		dwMessage.ifFromNet = 1;
		dwMessage.setVoiceTime(mTime);
		// 发送Message
		if (!XmppHelper.getConn().isConnected()) {
			// 没有连接，处理
			dwMessage.setSendSuccess(0);
		} else {
			// 发送语音
			sendAudio(mTime, filePath, dwMessage.getTxtMsgID());
		}
		dwMessage.save();
		refreshListView(dwMessage);
		refreshConversation(dwMessage);
		// 处理界面
		hideKeyboard();
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("单聊历史记录");
		LogUtils.i(TAG, "开始获取历史聊天记录...begin");
		loadHistoryMsg(dwID, otherID);
		LogUtils.i(TAG, "开始获取历史聊天记录...end");

	}
	// /**
	// * 播放指定uri的声音，轻量级的
	// *
	// * @param context
	// * @param uri
	// */
	// public synchronized static void playShortSound(Context context, Uri uri)
	// {
	// try {
	// if (pool == null) {
	// pool = new SoundPool(1, AudioManager.STREAM_RING, 0);
	// }
	// if (!isPlaying) {
	// isPlaying = true;
	// if (Constants.SCHEME_ASSETS.startsWith(uri.getScheme())) {
	// pool.load(context.getAssets().openFd(uri.getHost()), 0);
	// } else {
	// pool.load(getRealFilePath(context, uri), 0);
	// }
	// pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
	// @Override
	// public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
	// {
	// soundPool.play(sampleId, 1, 1, 0, 0, 1);
	// isPlaying = false;
	// }
	// });
	// }
	// } catch (Exception e) {
	// Log.w(Constants.TAG, "播放声音失败", e);
	// }
	// }
}
