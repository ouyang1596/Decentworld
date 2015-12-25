/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.text.Spannable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ImagePagerActivity;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.bean.DWException.OnExceptionListener;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.MediaManager;
import cn.sx.decentworld.common.SmileUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.TimeUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @ClassName: MessageAdapter.java
 * @Description:
 * @author: cj
 * @date: 2015年9月1日 上午8:44:08
 */
public class DWMessageAdapter extends BaseAdapter {
	public static String TAG = "DWMessageAdapter";
	private Context context;
	private List<DWMessage> listMsg;
	private LayoutInflater inflater;

	private int minItemWidth;
	private int maxItemWidth;
	// 判断每个Item,MediaManager是否被初始调用过
	private boolean isMusicPlaying;
	private boolean isPlayAnimation;
	String icon_tochat = "";
	String icon_me = "";
	String nickname_tochat = "";
	private int nowPosition = -1, prePosition = -1;

	public DWMessageAdapter(Context context, List<DWMessage> listMsg) {
		this.context = context;
		this.listMsg = listMsg;
		this.inflater = LayoutInflater.from(context);
		icon_me = ImageUtils.getIconByDwID(DecentWorldApp.getInstance()
				.getDwID(), ImageUtils.ICON_SMALL);
		if (ChatActivity.chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
			nickname_tochat = "神秘人";
			icon_tochat = "";

		} else {
			nickname_tochat = ChatActivity.otherNickname;
			icon_tochat = ImageUtils.getIconByDwID(ChatActivity.otherID,
					ImageUtils.ICON_SMALL);
		}
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final String my_dwid = DecentWorldApp.getInstance().getDwID();
		final DWMessage message = getItem(position);
		message.setIsRead(1);
		message.save();
		int chatType = message.getChatType();
		int messageType = message.getMessageType();
		final ViewHolder holder = new ViewHolder();
		// 消息为语音消息
		if (messageType == DWMessage.VOICE) {
			// 获取屏幕属性
			WindowManager wm = (WindowManager) context
					.getSystemService(context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			maxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
			minItemWidth = (int) (outMetrics.widthPixels * 0.2F);
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接受语音时
				convertView = inflater.inflate(R.layout.row_received_voice,
						null);
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);

			} else {
				// 发送语音时
				convertView = inflater.inflate(R.layout.row_sent_voice, null);
			}
			holder.pb = (ProgressBar) convertView.findViewById(R.id.pb_sending);

			holder.staus_iv = (ImageView) convertView
					.findViewById(R.id.msg_status);
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.icon_voice = (FrameLayout) convertView
					.findViewById(R.id.iv_voice);// 语音喇叭的图标
			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔

			ImageView ivVoice = (ImageView) convertView
					.findViewById(R.id.iiv_voice);
			setAudioImageBg(position, ivVoice);
			handlerVoice(position, ivVoice);
			LogUtils.i("bm", "getUri--" + listMsg.get(position).getUri());
			setIconVoice(position, holder, ivVoice);
			holder.voice_tv_length = (TextView) convertView
					.findViewById(R.id.tv_length);

			// 设置界面
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接受语音时
				if (CommUtil.isNotBlank(icon_tochat)) {
					ImageLoaderHelper.mImageLoader.displayImage(icon_tochat,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				holder.tv_usernick.setText(nickname_tochat);
			} else {
				// 发送语音时
				if (CommUtil.isNotBlank(icon_me)) {
					// Picasso.with(context).load(icon_me)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_me,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				// 点击重新发送语音
				holder.staus_iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						EventBus.getDefault().post(message,
								NotifyByEventBus.NT_RESET_VOICE);
					}
				});
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
		}
		// 文字消息
		else if (messageType == DWMessage.TXT) {
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接收的文本消息
				convertView = inflater.inflate(R.layout.row_received_message,
						null);
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
			} else {
				// 发送的文本消息
				convertView = inflater.inflate(R.layout.row_sent_message, null);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.pb_sending);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
			}
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.tv = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);// 文字内容
			holder.tv.setTag(Constants.ITEM_KEY, position);
			holder.tv.setOnLongClickListener(mOnLongClickListener);

			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔
			// 设置界面
			if (message.getDirect() == DWMessage.SEND) {
				// 自己发送出去的消息
				if (message.getSendSuccess() == 1) {
					holder.pb.setVisibility(View.GONE);
					holder.staus_iv.setVisibility(View.GONE);
				} else if (message.getSendSuccess() == 2) {
					holder.pb.setVisibility(View.VISIBLE);
					holder.staus_iv.setVisibility(View.GONE);
				} else if (message.getSendSuccess() == 0) {
					holder.staus_iv.setVisibility(View.VISIBLE);
					holder.pb.setVisibility(View.GONE);
				}
				if (CommUtil.isNotBlank(icon_me)) {
					// Picasso.with(context).load(icon_me)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_me,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				holder.staus_iv.setOnClickListener(new OnClickListener() {
					// 文字消息重新发送
					@Override
					public void onClick(View v) {
						EventBus.getDefault().post(message,
								NotifyByEventBus.NT_RESET_TXT);
					}
				});
			} else {
				// 别人发送过来的消息
				holder.tv_usernick.setText(nickname_tochat);
				if (CommUtil.isNotBlank(icon_tochat)) {
					// Picasso.with(context).load(icon_tochat)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_tochat,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
			}
			// 将表情反射处理
			Spannable span = SmileUtils.getSmiledText(context,
					message.getBody());
			// 设置消息内容
			holder.tv.setText(span, BufferType.SPANNABLE);

		}

		// 图片消息
		else if (messageType == DWMessage.IMAGE) {
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接受图片时
				convertView = inflater.inflate(R.layout.row_received_picture,
						null);
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.progressBar);
			} else {
				// 发送图片时
				convertView = inflater.inflate(R.layout.row_sent_picture, null);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.progressBar);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
			}
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.iv_sendPicture = (ImageView) convertView
					.findViewById(R.id.iv_sendPicture);
			holder.iv_sendPicture.setTag(Constants.ITEM_KEY, position);
			holder.iv_sendPicture.setOnLongClickListener(mOnLongClickListener);

			holder.percentage = (TextView) convertView
					.findViewById(R.id.percentage);
			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔
			// 设置界面
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接受
				if (CommUtil.isNotBlank(icon_tochat)) {
					// Picasso.with(context).load(icon_tochat)
					// .config(Config.RGB_565)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_tochat,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				holder.tv_usernick.setText(nickname_tochat);
				// 接收的照片显示
				if (CommUtil.isNotBlank(message.getUri())) {
					// Picasso.with(context).load(new File(message.getUri()))
					// .placeholder(R.drawable.work_default)
					// .error(R.drawable.hollow_heart)
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
						loadPicture(message);
					}
				});
			} else {
				// 发送
				if (CommUtil.isNotBlank(icon_me)) {
					// Picasso.with(context).load(icon_me).config(Config.RGB_565)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_me,
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
				LogUtils.i("bm", "--------" + message.getUri());
				holder.iv_sendPicture.setImageBitmap(ImageUtils
						.getLoacalBitmap(message.getUri()));
				// 图片点击事件
				holder.iv_sendPicture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						loadPicture(message);
					}
				});
				// 点击重新发送图片
				holder.staus_iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						EventBus.getDefault().post(message,
								NotifyByEventBus.NT_RESET_IMAGE);
					}
				});
			}
		}
		// 卡片消息
		else if (message.getMessageType() == DWMessage.CARD) {
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接收的名片
				convertView = inflater
						.inflate(R.layout.row_received_card, null);
				holder.tv_usernick = (TextView) convertView
						.findViewById(R.id.tv_userid);
			} else {
				// 发送的名片
				convertView = inflater.inflate(R.layout.row_sent_card, null);
				holder.pb = (ProgressBar) convertView
						.findViewById(R.id.pb_sending);
				holder.staus_iv = (ImageView) convertView
						.findViewById(R.id.msg_status);
			}
			holder.rl_card_container = (RelativeLayout) convertView
					.findViewById(R.id.rl_card_container);
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_userhead);// 用户头像
			holder.tv_card_name = (TextView) convertView
					.findViewById(R.id.tv_card_name);
			holder.iv_card_icon = (ImageView) convertView
					.findViewById(R.id.iv_card_icon);
			holder.tv_time_stamp = (TextView) convertView
					.findViewById(R.id.timestamp);// 时间间隔
			// 设置界面
			if (message.getDirect() == DWMessage.RECEIVE) {
				// 接收
				holder.tv_usernick.setText(nickname_tochat);
				if (CommUtil.isNotBlank(icon_tochat)) {
					// Picasso.with(context).load(icon_tochat)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_tochat,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
			} else {
				// 发送
				if (message.getSendSuccess() == 1) {
					holder.pb.setVisibility(View.GONE);
					holder.staus_iv.setVisibility(View.GONE);
				} else if (message.getSendSuccess() == 2) {
					holder.pb.setVisibility(View.VISIBLE);
					holder.staus_iv.setVisibility(View.GONE);
				} else if (message.getSendSuccess() == 0) {
					holder.staus_iv.setVisibility(View.VISIBLE);
					holder.pb.setVisibility(View.GONE);
				}
				if (CommUtil.isNotBlank(icon_me)) {
					// Picasso.with(context).load(icon_me)
					// .placeholder(R.drawable.work_default)
					// .resize(120, 120).into(holder.iv_avatar);
					ImageLoaderHelper.mImageLoader.displayImage(icon_me,
							holder.iv_avatar, ImageLoaderHelper.mOptions);
				}
				holder.staus_iv.setOnClickListener(new OnClickListener() {
					// 名片消息重新发送
					@Override
					public void onClick(View v) {
						EventBus.getDefault().post(message,
								NotifyByEventBus.NT_RESET_CARD);
					}
				});
			}
			holder.tv_card_name.setText(message.getForwardName());
			String card_icon = ImageUtils.getIconByDwID(
					message.getForwardDwId(), ImageUtils.ICON_SMALL);
			if (CommUtil.isNotBlank(card_icon)) {
				// Picasso.with(context).load(card_icon)
				// .placeholder(R.drawable.work_default).resize(80, 80)
				// .into(holder.iv_card_icon);
				ImageLoaderHelper.mImageLoader.displayImage(card_icon,
						holder.iv_card_icon, ImageLoaderHelper.mOptions);
			}
			holder.rl_card_container.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							NearCardDetailActivity_.class);
					intent.putExtra(Constants.DW_ID, message.getForwardDwId());
					context.startActivity(intent);
				}
			});
		} else if (messageType == DWMessage.NOTIFY) {
			convertView = inflater.inflate(R.layout.row_notify_message, null);
			holder.tv_notify_message = (TextView) convertView
					.findViewById(R.id.tv_notify_message);
			holder.tv_notify_message.setText(message.getBody());

		}
		if (messageType == DWMessage.TXT || messageType == DWMessage.VOICE
				|| messageType == DWMessage.IMAGE
				|| messageType == DWMessage.CARD) {
			/** 头像点击事件 **/
			holder.iv_avatar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String fromDwId = message.getFrom();
					String toDwId = message.getTo();

					if (!fromDwId.equals(my_dwid)) {
						Intent intent = new Intent(context,
								NearCardDetailActivity_.class);
						intent.putExtra("dwID", fromDwId);
						context.startActivity(intent);
					}
				}
			});
			if (ChatActivity.chatType == DWMessage.CHAT_TYPE_SINGLE_ANONYMITY) {
				// Picasso.with(context).load(R.drawable.default_avatar)
				// .config(Config.RGB_565)
				// .placeholder(R.drawable.work_default).resize(120, 120)
				// .into(holder.iv_avatar);
				holder.iv_avatar.setImageResource(R.drawable.default_avatar);
			}
			showTime(holder.tv_time_stamp, position);
		}
		return convertView;
	}

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

	private void setIconVoice(final int position, final ViewHolder holder,
			ImageView ivVoice) {
		holder.icon_voice.setTag(ivVoice);
		holder.icon_voice.setTag(Constants.ITEM_KEY, position);
		holder.icon_voice.setOnClickListener(mOnClickListener);
		holder.icon_voice.setOnLongClickListener(mOnLongClickListener);
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

	private int isClickPosition = -1;
	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			int position = (Integer) view.getTag(Constants.ITEM_KEY);
			LogUtils.i("bm", "data--" + getItem(position).getUri());
			if (isClickPosition == -1) {
				startAudio(position);
			} else {
				if (isClickPosition == position) {
					// if (MediaManager.isPlaying()) {
					// MediaManager.stop();
					// nowPosition = -1;
					// isClickPosition = -1;
					// notifyDataSetChanged();
					// } else {
					// }
					MediaManager.stop();
					nowPosition = -1;
					isClickPosition = -1;
					notifyDataSetChanged();
				} else {
					startAudio(position);
				}
			}
			// if (!isClick) {
			// isMusicPlaying = false;
			// if (nowPosition != position) {
			// prePosition = nowPosition;
			// nowPosition = position;
			// }
			// notifyDataSetChanged();
			// isClick = true;
			// } else {
			// MediaManager.stop();
			// nowPosition = -1;
			// notifyDataSetChanged();
			// isClick = false;
			// }
		}

		private void startAudio(int position) {
			isMusicPlaying = false;
			if (nowPosition != position) {
				prePosition = nowPosition;
				nowPosition = position;
			}
			notifyDataSetChanged();
			isClickPosition = position;
		}
	};
	private OnLongClickListener mOnLongClickListener = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View view) {
			if (null != mOnMessageClickListener) {
				mOnMessageClickListener.onMessageClick(view);
			}
			return false;
		}
	};

	public void stopMediaPlayer() {
		MediaManager.stop();
	}

	/**
	 * 按点击监听
	 */
	public void setOnMessageClickListener(
			OnMessageClickListener onMessageClickListener) {
		this.mOnMessageClickListener = onMessageClickListener;
	}

	private OnMessageClickListener mOnMessageClickListener;

	public interface OnMessageClickListener {
		public void onMessageClick(View view);
	}

	public void playMusic(final int position, final ImageView ivVoice) {
		isPlayAnimation = true;
		DWMessage message = listMsg.get(position);
		String uri = message.getUri();
		LogUtils.i("bm", "url---" + uri);
		if (null == uri) {
			Toast.makeText(context, "语音路径为空", Toast.LENGTH_LONG).show();
			return;
		}
		if (message.ifFromNet == 0) {
			String audioFileName = uri.substring(uri.lastIndexOf("/"));
			File file = new File(Constants.HomePath + Constants.AUDIO_PATH
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
				isClickPosition = -1;
				notifyDataSetChanged();
			}
		}, new OnExceptionListener() {

			@Override
			public void onException(Exception e) {
				mAnimation.stop();
				isPlayAnimation = false;
				isClickPosition = -1;
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
				isClickPosition = -1;
				notifyDataSetChanged();
			}
		}, new OnExceptionListener() {
			@Override
			public void onException(Exception e) {
				mAnimation.stop();
				isPlayAnimation = false;
				isClickPosition = -1;
				notifyDataSetChanged();
			}
		});
	}

	AnimationDrawable mAnimation;

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

		TextView tv_time_stamp;// 时间

		RelativeLayout rl_card_container;// 卡片相对布局
		TextView tv_card_name;// 在名片上显示被转发人的名字（昵称）
		ImageView iv_card_icon;// 在名片上显示被转发人的头像

		TextView tv_notify_message;// 通知消息
	}

	/**
	 * 点击缩略图加载大图
	 * 
	 * @param message
	 */
	private void loadPicture(DWMessage message) {
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
	 * 显示时间
	 * 
	 * @param timeStamp
	 * @param position
	 */
	private void showTime(TextView timeStamp, int position) {
		if (position == 0)
		{
			timeStamp.setText(TimeUtils.DataLong2String(getItem(position).getTime()));
			timeStamp.setVisibility(View.VISIBLE);
		}
		else
		{
			if (TimeUtils.isShowTime(getItem(position - 1).getTime(), getItem(position).getTime()))
			{
				timeStamp.setText(TimeUtils.DataLong2String(getItem(position).getTime()));
				timeStamp.setVisibility(View.VISIBLE);
			}
			else
			{
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
		httpUtils.download(message.getUri(), Constants.HomePath
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
				mAnimation.stop();
				isPlayAnimation = false;
				isClickPosition = -1;
				notifyDataSetChanged();
			}
		});
	}
}
