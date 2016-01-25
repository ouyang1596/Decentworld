/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.simple.eventbus.EventBus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatRoomChatActivity;
import cn.sx.decentworld.activity.ImagePagerActivity;
import cn.sx.decentworld.bean.DWException.OnExceptionListener;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.MediaManager;
import cn.sx.decentworld.common.SmileUtils;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.manager.ChatRoomManager;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.TimeUtils;

import com.android.volley.Request.Method;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 
 * @ClassName: CRMessageAdapter.java
 * @Description: 聊天室消息适配器
 * @author: cj
 * @date: 2015年10月28日 下午4:51:13
 */
public class CRMessageAdapter extends BaseAdapter {
	public static String TAG = "CRMessageAdapter";
	private Context context;
	private List<DWMessage> listMsg;
	private LayoutInflater inflater;

	private int minItemWidth;
	private int maxItemWidth;

	private String dwID;
	private SendUrl sendUrl;
	private FragmentManager fm;
	private ChatRoomManager chatRoomManager;

	public CRMessageAdapter(Context context, List<DWMessage> listMsg,
			FragmentManager fm) {
		this.context = context;
		this.listMsg = listMsg;
		this.inflater = LayoutInflater.from(context);
		this.dwID = DecentWorldApp.getInstance().getDwID();
		sendUrl = new SendUrl(context);
		this.fm = fm;
		chatRoomManager = new ChatRoomManager(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final DWMessage message = getItem(position);
		DWMessage msg = DWMessage.load(DWMessage.class, message.getId());
		msg.setIsRead(1);
		msg.save();
		int chatType = message.getChatType();
		int messageType = message.getMessageType();
		final ViewHolder holder = new ViewHolder();
		// 聊天室
		if (chatType == DWMessage.CHAT_TYPE_MULTI) {
		} else {
			LogUtils.e(
					TAG,
					"chatType=" + chatType + ",不属于聊天室信息；mid="
							+ message.getMid());
		}
		// 文字消息
		if (messageType == DWMessage.TXT) {
			if (message.getFrom().equals(dwID)) {
				// 自己发出的消息
				if (message.getFrom().equals(ChatRoomChatActivity.ownerID)) {
					convertView = inflater.inflate(
							R.layout.row_sent_message_center, null);
				} else {
					convertView = inflater.inflate(R.layout.row_sent_message,
							null);
				}
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.pb_sending);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
			} else {
				// 别人发送过来的消息
				if (message.getFrom().equals(ChatRoomChatActivity.ownerID)) {
					convertView = inflater.inflate(
							R.layout.row_received_message_center, null);
				} else {
					convertView = inflater.inflate(
							R.layout.row_received_message, null);
				}
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.tvUserWorth = (TextView) convertView
						.findViewById(R.id.tv_user_worth);
				holder.ivBlock = (ImageView) convertView
						.findViewById(R.id.iv_block);
			}
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.tv = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);// 文字内容
			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔
			// 设置界面
			if (message.getFrom().equals(dwID)) {
				// 自己发出的消息
				String iconUrl = ImageUtils.getIconByDwID(dwID,
						ImageUtils.ICON_SMALL);
				if (CommUtil.isNotBlank(iconUrl)) {
					// 从网络获取自己的小头像
					// Picasso.with(context).load(iconUrl).resize(120, 120)
					// .into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(iconUrl,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				} else {
					// 设置默认头像
					holder.iv_avatar.setImageResource(R.drawable.default_icon);
				}
				if (message.getSendSuccess() == 0) {// 失败
					holder.pb.setVisibility(View.INVISIBLE);
					holder.staus_iv.setVisibility(View.VISIBLE);
				} else if (message.getSendSuccess() == 1) {// 成功
					holder.pb.setVisibility(View.GONE);
					holder.staus_iv.setVisibility(View.GONE);
				} else if (message.getSendSuccess() == 2) {
					// 传输中
					holder.pb.setVisibility(View.VISIBLE);
					holder.staus_iv.setVisibility(View.GONE);
				}
			} else {
				// 别人发送过来的消息 from
				String iconUrl = ImageUtils.getIconByDwID(message.getFrom(),
						ImageUtils.ICON_SMALL);
				if (CommUtil.isNotBlank(iconUrl)) {
					// 从网络获取自己的小头像
					// Picasso.with(context).load(iconUrl).resize(120, 120)
					// .placeholder(R.drawable.default_icon)
					// .into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(iconUrl,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				} else {
					// 设置默认头像
					holder.iv_avatar.setImageResource(R.drawable.default_icon);
				}
				chatRoomManager.setNicnameAndWorth(message.getFrom(),
						holder.tv_usernick, holder.tvUserWorth);
				if (!message.getBody().equals("我是土豪，我钱多")) {
					// LogUtils.i(TAG, "聊天室成员" + message.getFrom() +
					// "没有被禁言");
					holder.ivBlock.setVisibility(View.GONE);
				} else {
					// LogUtils.i(TAG, "聊天室成员" + message.getFrom() +
					// "被禁言了");
					holder.ivBlock.setVisibility(View.VISIBLE);
					// 如果当前用户为主持人
					if (ChatRoomChatActivity.ownerID.equals(dwID)) {
						holder.ivBlock
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										// 禁言提示框
										ReminderDialog dialog = new ReminderDialog();
										dialog.setInfo("将" + message.getFrom()
												+ "解除禁言");
										dialog.setListener(new ReminderListener() {
											@Override
											public void confirm() {
												((Activity) context)
														.runOnUiThread(new Runnable() {
															@Override
															public void run() {
																// Toast.makeText(context,
																// "设置禁言",
																// Toast.LENGTH_SHORT).show();
															}
														});

												LogUtils.i(TAG, "点击了解除禁言确定按钮");
												removeBlockUser(
														dwID,
														ChatRoomChatActivity.roomID,
														message.getFrom());
											}
										});
										dialog.show(fm, "blockUser");
									}
								});
					}
				}

				// 如果当前用户为主持人
				if (ChatRoomChatActivity.ownerID.equals(dwID)) {
					holder.tv.setOnLongClickListener(new OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							((Activity) context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(context, "设置禁言",
											Toast.LENGTH_SHORT).show();
								}
							});

							// 禁言提示框
							ReminderDialog dialog = new ReminderDialog();
							dialog.setInfo("将" + message.getFrom() + "禁言");
							dialog.setListener(new ReminderListener() {
								@Override
								public void confirm() {
									((Activity) context)
											.runOnUiThread(new Runnable() {
												@Override
												public void run() {
													// Toast.makeText(context,
													// "设置禁言",
													// Toast.LENGTH_SHORT).show();
												}
											});
									LogUtils.i(TAG, "点击了设置禁言确定按钮");
									blockUser(dwID,
											ChatRoomChatActivity.roomID,
											message.getFrom());
								}
							});
							dialog.show(fm, "blockUser");
							return false;
						}
					});
				}
			}
			// 将表情反射处理
			Spannable span = SmileUtils.getSmiledText(context,
					message.getBody());
			// 设置消息内容
			holder.tv.setText(span, BufferType.SPANNABLE);

		}
		// 消息为语音消息
		else if (messageType == DWMessage.VOICE) {
			// 获取屏幕属性
			WindowManager wm = (WindowManager) context
					.getSystemService(context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			maxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
			minItemWidth = (int) (outMetrics.widthPixels * 0.2F);
			if (message.getFrom().equals(dwID)) {
				// 发送语音时
				if (message.getFrom().equals(ChatRoomChatActivity.ownerID)) {
					convertView = inflater.inflate(
							R.layout.row_sent_voice_center, null);
				} else {
					convertView = inflater.inflate(R.layout.row_sent_voice,
							null);
				}
			} else {
				// 接受语音时
				if (message.getFrom().equals(ChatRoomChatActivity.ownerID)) {
					convertView = inflater.inflate(
							R.layout.row_received_voice_center, null);
				} else {
					convertView = inflater.inflate(R.layout.row_received_voice,
							null);
				}
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.tvUserWorth = (TextView) convertView
						.findViewById(R.id.tv_user_worth);
			}
			holder.pb = (ProgressBar) convertView.findViewById(R.id.pb_sending);
			holder.staus_iv = (ImageView) convertView
					.findViewById(R.id.msg_status);
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.icon_voice = (FrameLayout) convertView
					.findViewById(R.id.iv_voice);// 语音喇叭的图标
			ImageView ivVoice = (ImageView) convertView
					.findViewById(R.id.iiv_voice);
			setAudioImageBg(position, ivVoice);
			handlerVoice(position, ivVoice);
			setIconVoice(position, holder, ivVoice);
			holder.voice_tv_length = (TextView) convertView
					.findViewById(R.id.tv_length);
			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔
			// 设置界面
			if (!message.getFrom().equals(dwID)) {
				// 接受语音时
				String icon = ImageUtils.getIconByDwID(message.getFrom(),
						ImageUtils.ICON_SMALL);
				// Picasso.with(context).load(icon).config(Config.RGB_565)
				// .placeholder(R.drawable.work_default).resize(120, 120)
				// .into(holder.iv_avatar);
				ImageLoaderHelper.mImageLoader.displayImage(icon,
						holder.iv_avatar, ImageLoaderHelper.mOptions);
				chatRoomManager.setNicnameAndWorth(message.getFrom(),
						holder.tv_usernick, holder.tvUserWorth);
			} else {
				// 发送语音时
				String path = ImageUtils.getIconByDwID(dwID,
						ImageUtils.ICON_SMALL);
				if (CommUtil.isNotBlank(path)) {
					// Picasso.with(context).load(path)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(path,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
			}
			if (message.getSendSuccess() == 0) {// 失败
				holder.pb.setVisibility(View.INVISIBLE);
				holder.staus_iv.setVisibility(View.VISIBLE);
			} else if (message.getSendSuccess() == 1) {// 成功
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
			} else if (message.getSendSuccess() == 2) {// 传输中
				holder.pb.setVisibility(View.VISIBLE);
				holder.staus_iv.setVisibility(View.GONE);
			}
			// 获取layout属性
			ViewGroup.LayoutParams ll = holder.icon_voice.getLayoutParams();
			ll.width = (int) (minItemWidth + (maxItemWidth / 60f * message
					.getVoiceTime()));
			holder.voice_tv_length.setText(Math.round(message.getVoiceTime())
					+ "'");
			// 点击重新发送语音
			holder.staus_iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EventBus.getDefault().post(message,
							NotifyByEventBus.NT_RESET_VOICE);
				}
			});
		}
		// 图片消息
		else if (messageType == DWMessage.IMAGE) {
			if (message.getFrom().equals(dwID)) {
				// 发送图片时
				if (message.getFrom().equals(ChatRoomChatActivity.ownerID)) {
					convertView = inflater.inflate(
							R.layout.row_sent_picture_center, null);
				} else {
					convertView = inflater.inflate(R.layout.row_sent_picture,
							null);
				}
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.progressBar);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
			} else {
				// 接受图片时
				if (message.getFrom().equals(ChatRoomChatActivity.ownerID)) {
					convertView = inflater.inflate(
							R.layout.row_received_picture_center, null);
				} else {
					convertView = inflater.inflate(
							R.layout.row_received_picture, null);
				}
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.progressBar);
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.tvUserWorth = (TextView) convertView
						.findViewById(R.id.tv_user_worth);
			}
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.iv_sendPicture = (ImageView) convertView
					.findViewById(R.id.iv_sendPicture);
			holder.percentage = (TextView) convertView
					.findViewById(R.id.percentage);
			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔

			// 设置界面
			if (message.getFrom().equals(dwID)) {
				// 发送
				String path = ImageUtils.getIconByDwID(dwID,
						ImageUtils.ICON_SMALL);
				if (CommUtil.isNotBlank(path)) {
					// Picasso.with(context).load(path).config(Config.RGB_565)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(path,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				if (message.getSendSuccess() == 0) {// 失败
					holder.pb.setVisibility(View.INVISIBLE);
					holder.staus_iv.setVisibility(View.VISIBLE);
				} else if (message.getSendSuccess() == 1) {// 成功
					holder.pb.setVisibility(View.GONE);
					holder.staus_iv.setVisibility(View.GONE);
				} else if (message.getSendSuccess() == 2) {// 传输中
					holder.pb.setVisibility(View.VISIBLE);
					holder.staus_iv.setVisibility(View.GONE);
				}
				holder.percentage.setVisibility(View.INVISIBLE);
				// Picasso.with(context).load(new File(message.getUri()))
				// .placeholder(R.drawable.work_default)
				// .error(R.drawable.hollow_heart)
				// .into(holder.iv_sendPicture);
				ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
						+ message.getUri(), holder.iv_sendPicture,
						ImageLoaderHelper.mOptions);
				// 图片点击事件
				holder.iv_sendPicture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ArrayList<String> list = new ArrayList<String>();
						// 如果local_url为空，则用remote_url进行网络访问，并将图下载保存到本地，地址保存到local_url中
						if (CommUtil.isNotBlank(message.getLocalUrl())) {
							list.add(message.getLocalUrl());
							imageBrower_local(0, list);
						} else {
							list.add(message.getRemoteUrl());
							imageBrower_net(0, list);
						}
					}
				});
				// 点击重新发送图片
				holder.staus_iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// EventBus.getDefault().post(
						// new NotifyByEventBus(message,
						// NotifyByEventBus.NOTIFY_RESET_MESSAGE));
						EventBus.getDefault().post(message,
								NotifyByEventBus.NT_RESET_IMAGE);
					}
				});
			} else {
				// 接收
				String iconUrl = ImageUtils.getIconByDwID(
						message.getFromDwId(), ImageUtils.ICON_SMALL);
				if (CommUtil.isNotBlank(iconUrl)) {
					// Picasso.with(context).load(iconUrl).config(Config.RGB_565)
					// .placeholder(R.drawable.default_icon)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(iconUrl,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				} else {
					// 从网络拿取对应的头像
					String path = ImageUtils.getIconByDwID(message.getFrom(),
							ImageUtils.ICON_SMALL);
					LogUtils.i(TAG, "图片...数据库中没有dwID=" + message.getFrom()
							+ "的图片" + "，从网络获取，路径为：path" + path);
					// Picasso.with(context).load(path)
					// .placeholder(R.drawable.default_icon)
					// .into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(path,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				// 接收的照片显示
				if (!CommUtil.isBlank(message.getUri())) {
					LogUtils.i("dw", "----------------" + message.getBody());
					// Picasso.with(context).load(new File(message.getUri()))
					// .placeholder(R.drawable.work_default)
					// .error(R.drawable.hollow_heart).resize(120, 120)
					// .into(holder.iv_sendPicture);
					ImageLoaderHelper.mImageLoader.displayImage(
							Constants.URI_FILE + message.getUri(),
							holder.iv_sendPicture, ImageLoaderHelper.mOptions);
				}
				// 进度
				holder.pb.setVisibility(View.GONE);
				holder.percentage.setVisibility(View.INVISIBLE);
				// 图片点击事件
				holder.iv_sendPicture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ArrayList<String> list = new ArrayList<String>();
						if (CommUtil.isNotBlank(message.getLocalUrl())) {
							list.add(message.getLocalUrl());
							imageBrower_local(0, list);
						} else {
							list.add(message.getRemoteUrl());
							imageBrower_net(0, list);
						}
					}
				});
				chatRoomManager.setNicnameAndWorth(message.getFrom(),
						holder.tv_usernick, holder.tvUserWorth);
			}
		}
		if (message.getChatType() == DWMessage.CHAT_TYPE_MULTI) {
			showTime(holder.tv_time_stamp, position);
		}
		return convertView;
	}

	private void setIconVoice(int position, final ViewHolder holder,
			ImageView ivVoice) {
		holder.icon_voice.setTag(Constants.ITEM_POSITION, position);
		holder.icon_voice.setOnClickListener(mOnClickListener);
	}

	@Override
	public int getCount() {

		return listMsg.size();
	}

	@Override
	public DWMessage getItem(int position) {
		return listMsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		ImageView iv;
		TextView tv;
		ProgressBar pb;
		ImageView staus_iv;
		ImageView iv_avatar;
		TextView tv_usernick;
		ImageView playBtn;
		TextView timeLength;
		TextView size;
		LinearLayout container_status_btn;
		LinearLayout ll_container;
		ImageView iv_read_status;
		// 显示已读回执状态
		TextView tv_ack;
		// 显示送达回执状态
		TextView tv_delivered;
		TextView tv_file_name;
		TextView tv_file_size;
		TextView tv_file_download_state;
		TextView tvTitle;
		LinearLayout tvList;
		FrameLayout icon_voice;
		TextView voice_tv_length;
		ImageView iv_sendPicture;
		TextView percentage;
		TextView tvUserWorth;// 用户身价
		ImageView ivBlock;// 用户是否被禁言
		TextView tv_time_stamp;// 显示时间
	}

	private int isClickPosition = -1;
	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {

			int position = (Integer) view.getTag(Constants.ITEM_POSITION);
			if (isClickPosition == -1) {
				startAudio(position);
			} else {
				if (isClickPosition == position) {
					MediaManager.stop();
					nowPosition = -1;
					isClickPosition = -1;
					notifyDataSetChanged();
				} else {
					startAudio(position);
				}
			}

			// // if (!isClick) {
			// isMusicPlaying = false;
			// final int position = (Integer) view.getTag(Constants.ITEM_KEY);
			// if (nowPosition != position) {
			// prePosition = nowPosition;
			// nowPosition = position;
			// }
			// notifyDataSetChanged();
			// isClick = true;
			// // } else {
			// // MediaManager.stop();
			// // nowPosition = -1;
			// // notifyDataSetChanged();
			// // isClick = false;
			// // }
		}
	};

	private void startAudio(int position) {
		isMusicPlaying = false;
		if (nowPosition != position) {
			prePosition = nowPosition;
			nowPosition = position;
		}
		notifyDataSetChanged();
		isClickPosition = position;
	}

	/**
	 * 打开图片查看器（网络图片）
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower_net(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

	/**
	 * 本地图片浏览器
	 * 
	 * @param position
	 * @param imagePath
	 *            本地图片绝对路径
	 */
	protected void imageBrower_local(int position, ArrayList<String> imagePath) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.LOCAL_IMAGE_FILEPATH, imagePath);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

	/**
	 * 解析缩略图
	 * 
	 * @param image
	 */
	private File AnalyticThumbnail(String image) {
		byte[] bytes = Base64.decode(image, Base64.DEFAULT);
		// ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		// BufferedImage image = ImageIO.read(in);
		// ImageIO.write(image, "jpg", file2);
		// 父路径名
		String origal_file_path = Constants.HOME_PATH;
		File origal_file = new File(origal_file_path);
		if (!origal_file.exists()) {
			origal_file.mkdirs();
		}
		File per_file = new File(origal_file_path + Constants.thumbnailPath);
		if (!per_file.exists()) {
			per_file.mkdirs();
		}
		File file = new File(per_file, ImageUtils.generateFileName() + ".jpg");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			LogUtils.i(TAG, e1.getMessage());
		}
		// 2、创建OutputStream类，并为此实例化对象
		OutputStream out;
		try {
			out = new FileOutputStream(file);
			try {
				out.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;

	}

	/**
	 * 聊天室禁言
	 * 
	 * @param dwID
	 *            主持人ID
	 * @param roomID
	 *            聊天室ID
	 * @param blockID
	 *            被禁言的ID
	 */
	public void blockUser(String dwID, String roomID, String blockID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("roomID", roomID);
		map.put("blockID", blockID);
		LogUtils.i(TAG, "blockUser...begin");
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE
				+ "/blockUser", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.i(TAG,
						"blockUser...msg.getResultCode=" + msg.getResultCode()
								+ ",msg.getMsg=" + msg.getMsg()
								+ ",msg.getData=" + msg.getData());
				if (msg.getResultCode() == 2222) {
					LogUtils.i(TAG, "blockUser...success");
					((Activity) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(context, "禁言成功", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}
				if (msg.getResultCode() == 3333) {
					LogUtils.e(TAG,
							"blockUser...failure,cause by:" + msg.getMsg());
					((Activity) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(context, "1禁言失败：",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			}

			@Override
			public void onFailure(final String e) {
				LogUtils.e(TAG, "blockUser...onFailure,cause by:" + e);
				((Activity) context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(context, "2禁言失败：", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		});
	}

	/**
	 * 聊天室解除禁言
	 * 
	 * @param dwID
	 * @param roomID
	 * @param blockID
	 * @param mHandler
	 * @param requestCode
	 */
	public void removeBlockUser(String dwID, String roomID, String blockID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("roomID", roomID);
		map.put("blockID", blockID);
		LogUtils.i(TAG, "removeBlockUser...begin");
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE
				+ "/removeBlockUser", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, final ResultBean msg) {
				LogUtils.i(
						TAG,
						"removeBlockUser...msg.getResultCode="
								+ msg.getResultCode() + ",msg.getMsg="
								+ msg.getMsg() + ",msg.getData="
								+ msg.getData());
				if (msg.getResultCode() == 2222) {
					LogUtils.i(TAG, "removeBlockUser...success");
					((Activity) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(context, "解除禁言成功",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
				if (msg.getResultCode() == 3333) {
					LogUtils.e(
							TAG,
							"removeBlockUser...failure,cause by:"
									+ msg.getMsg());
					((Activity) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(context, "1解除禁言失败：",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			}

			@Override
			public void onFailure(final String e) {
				LogUtils.e(TAG, "removeBlockUser...onFailure,cause by:" + e);
				((Activity) context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(context, "2解除禁言失败：", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		});
	}

	private boolean isMusicPlaying;
	private boolean isPlayAnimation;
	private int nowPosition = -1, prePosition = -1;

	public void playMusic(final int position, final ImageView ivVoice) {
		isPlayAnimation = true;
		DWMessage message = listMsg.get(position);
		String uri = message.getUri();
		if (null == uri) {
			Toast.makeText(context, "语音路径为空", Toast.LENGTH_LONG).show();
			return;
		}
		if (message.ifFromNet == 0) {
			String audioFileName = uri.substring(uri.lastIndexOf("/"));
			File file = new File(Constants.HOME_PATH + Constants.AUDIO_PATH
					+ audioFileName);
			if (file.exists()) {
				playLocalMusic(file.getAbsolutePath());
			} else {
				// playNetMusic(uri);
				DownLoadAudio(message);
			}
		} else {
			playLocalMusic(uri);
		}
		isMusicPlaying = true;
	}

	private void playLocalMusic(String uri) {
		MediaManager.startLocal(context, uri, new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mAnimation.stop();
				isPlayAnimation = false;
				notifyDataSetChanged();
			}
		}, new OnExceptionListener() {

			@Override
			public void onException(Exception e) {
				mAnimation.stop();
				isPlayAnimation = false;
				notifyDataSetChanged();
			}
		});
	}

	private void playNetMusic(String uri) {
		MediaManager.startNetwork(context, uri, new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mAnimation.stop();
				isPlayAnimation = false;
				notifyDataSetChanged();
			}
		}, new OnExceptionListener() {

			@Override
			public void onException(Exception e) {
				mAnimation.stop();
				isPlayAnimation = false;
				notifyDataSetChanged();
			}
		});
	}

	AnimationDrawable mAnimation;

	private void handlerVoice(final int position, ImageView ivVoice) {
		if (prePosition == position) {
			setAudioImageBg(position, ivVoice);
		}
		if (nowPosition == position) {
			if (!isMusicPlaying) {
				playAnimation(position, ivVoice);
				playMusic(position, ivVoice);
			}
			if (isPlayAnimation) {
				playAnimation(position, ivVoice);
			} else {
				setAudioImageBg(position, ivVoice);
			}
		}
	}

	public void playAnimation(int position, ImageView ivVoice) {
		if (listMsg.get(position).getDirect() == DWMessage.RECEIVE) {
			ivVoice.setBackgroundResource(R.anim.voice_from_icon);
		} else {
			ivVoice.setBackgroundResource(R.anim.voice_to_icon);
		}
		mAnimation = (AnimationDrawable) ivVoice.getBackground();
		mAnimation.start();
	}

	private void setAudioImageBg(int position, ImageView ivVoice) {
		if (listMsg.get(position).getDirect() == DWMessage.RECEIVE) {
			ivVoice.setBackgroundResource(R.drawable.chatfrom_voice_playing);
		} else {
			ivVoice.setBackgroundResource(R.drawable.chatto_voice_playing);
		}
	}

	public void stopMediaPlayer() {
		MediaManager.stop();
	}

	/**
	 * 显示时间
	 * 
	 * @param timeStamp
	 * @param position
	 */
	private void showTime(TextView timeStamp, int position) {
		if (position == 0) {
			timeStamp.setText(TimeUtils.DataLong2String(getItem(position)
					.getTime()));
		} else {
			if (TimeUtils.isShowTime(getItem(position - 1).getTime(),
					getItem(position).getTime())) {
				timeStamp.setText(TimeUtils.DataLong2String(getItem(position)
						.getTime()));
			} else {
				timeStamp.setVisibility(View.GONE);
			}
		}
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(context, null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		if (null != mProDialog) {
			mProDialog.hide();
		}
	}

	HttpUtils httpUtils = new HttpUtils();

	public void DownLoadAudio(DWMessage message) {
		String uri = message.getUri();
		String fileName = uri.substring(uri.lastIndexOf("/"));
		showProgressDialog();
		httpUtils.download(message.getUri(), Constants.HOME_PATH
				+ Constants.AUDIO_PATH + fileName, new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				hideProgressDialog();
				playLocalMusic(responseInfo.result.getAbsolutePath());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				hideProgressDialog();
				Toast.makeText(context, "语音文件下载失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
