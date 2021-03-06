/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import cn.sx.decentworld.activity.NearCardDetailActivity;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.bean.DWException.OnExceptionListener;
import cn.sx.decentworld.chat.interfaces.ReSendMsgListener;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.FilePath;
import cn.sx.decentworld.common.MediaManager;
import cn.sx.decentworld.common.SmileUtils;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.entity.db.DWMessage.MessageStatus;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.entity.db.DWMessage.SendStatus;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.TimeUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @ClassName: MessageAdapter.java
 * @author: cj
 * @date: 2015年9月1日 上午8:44:08
 */
public class DWMessageAdapter extends BaseAdapter
{
    public static String TAG = "DWMessageAdapter";
    private Context context;
    private List<DWMessage> listMsg;
    private LayoutInflater inflater;
    // 屏幕尺寸
    private int minItemWidth;
    private int maxItemWidth;
    // 判断每个Item,MediaManager是否被初始调用过
    private boolean isMusicPlaying;
    private boolean isPlayAnimation;
    private int chatRelationship;
    private String icon_me = "";
    private String otherIcon = "";
    private String otherNickname = "神秘人";
    private int nowPosition = -1, prePosition = -1;
    // 重新发送消息监听
    private ReSendMsgListener reSendMsgListener;
    private LaunchChatEntity launchChatEntity;

    /**
     * 构造函数
     * 
     * @param context
     * @param listMsg
     * @param chatType
     * @param otherNickname
     * @param otherID
     * @param chatRelationship
     */
    public DWMessageAdapter(Context context, List<DWMessage> listMsg, LaunchChatEntity launchChatEntity)
    {
        this.context = context;
        this.listMsg = listMsg;
        this.inflater = LayoutInflater.from(context);

        this.icon_me = ImageUtils.getIconByDwID(DecentWorldApp.getInstance().getDwID(), ImageUtils.ICON_SMALL);
        if (launchChatEntity.getChatType() != ChatType.SINGLE_ANONYMITY.getIndex())
        {
            this.otherNickname = launchChatEntity.getOtherShowName();
            this.otherIcon = ImageUtils.getIconByDwID(launchChatEntity.getOtherID(), ImageUtils.ICON_SMALL);
        }
        this.chatRelationship = launchChatEntity.getChatRelationship();
    }

    /**
     * 设置消息重发监听
     * 
     * @param listener
     */
    public void setReSendMsgListener(ReSendMsgListener listener)
    {
        this.reSendMsgListener = listener;
    }

    /**
     * 获取View
     * 
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final DWMessage message = getItem(position);
        message.setIsRead(DWMessage.ReadStatus.YES.getIndex());
        message.save();
        int chatType = message.getChatType();
        int messageType = message.getMessageType();
        final ViewHolder holder = new ViewHolder();
        // 文字消息
        if (messageType == MessageType.TEXT.getIndex())
        {
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接收的文本消息
                convertView = inflater.inflate(R.layout.row_received_message, null);
                holder.tv_usernick = (TextView) convertView.findViewById(R.id.tv_userid);
            }
            else
            {
                // 发送的文本消息
                convertView = inflater.inflate(R.layout.row_sent_message, null);
                holder.pb = (ProgressBar) convertView.findViewById(R.id.pb_sending);
                holder.staus_iv = (ImageView) convertView.findViewById(R.id.msg_status);
            }
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_userhead);// 用户头像
            holder.tv = (TextView) convertView.findViewById(R.id.tv_chatcontent);// 文字内容
            holder.tv.setTag(Constants.ITEM_POSITION, position);
            holder.tv.setOnLongClickListener(mOnLongClickListener);
            // 时间间隔
            holder.llTimestampContainer = (LinearLayout) convertView.findViewById(R.id.ll_chat_time_container);
            holder.tv_time_stamp = (TextView) convertView.findViewById(R.id.timestamp);
            // 设置界面
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接收的文本消息
                holder.tv_usernick.setText(otherNickname);
                if (CommUtil.isNotBlank(otherIcon))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(otherIcon, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
            }
            else
            {
                if (CommUtil.isNotBlank(icon_me))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(icon_me, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
                // 重新发送文本消息
                holder.staus_iv.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        reSendMsgListener.onReSendText(message);
                    }
                });
            }

            // 将表情反射处理
            Spannable span = SmileUtils.getSmiledText(context, message.getBody(), 1);
            // 设置消息内容
            holder.tv.setText(span, BufferType.SPANNABLE);
        }
        // 消息为语音消息
        else if (messageType == MessageType.VOICE.getIndex())
        {
            // 获取屏幕属性
            WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            maxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
            minItemWidth = (int) (outMetrics.widthPixels * 0.2F);
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接受语音时
                convertView = inflater.inflate(R.layout.row_received_voice, null);
                holder.tv_usernick = (TextView) convertView.findViewById(R.id.tv_userid);
            }
            else
            {
                // 发送语音时
                convertView = inflater.inflate(R.layout.row_sent_voice, null);
            }
            holder.pb = (ProgressBar) convertView.findViewById(R.id.pb_sending);
            holder.staus_iv = (ImageView) convertView.findViewById(R.id.msg_status);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_userhead);// 用户头像
            holder.icon_voice = (FrameLayout) convertView.findViewById(R.id.iv_voice);// 语音喇叭的图标
            // 时间间隔
            holder.llTimestampContainer = (LinearLayout) convertView.findViewById(R.id.ll_chat_time_container);
            holder.tv_time_stamp = (TextView) convertView.findViewById(R.id.timestamp);

            ImageView ivVoice = (ImageView) convertView.findViewById(R.id.iiv_voice);
            setAudioImageBg(position, ivVoice);
            handlerVoice(position, ivVoice);
            setIconVoice(position, holder, ivVoice);
            holder.voice_tv_length = (TextView) convertView.findViewById(R.id.tv_length);
            // 设置界面
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接受语音时
                if (CommUtil.isNotBlank(otherIcon))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(otherIcon, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
                holder.tv_usernick.setText(otherNickname);
            }
            else
            {
                // 发送语音时
                if (CommUtil.isNotBlank(icon_me))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(icon_me, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
                // 点击重新发送语音
                holder.staus_iv.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        reSendMsgListener.onReSendVoice(message);
                    }
                });
            }
            // 获取layout属性
            ViewGroup.LayoutParams ll = holder.icon_voice.getLayoutParams();
            ll.width = (int) (minItemWidth + (maxItemWidth / 60f * message.getVoiceTime()));
            holder.voice_tv_length.setText(Math.round(message.getVoiceTime()) + "'");
        }
        // 图片消息
        else if (messageType == MessageType.IMAGE.getIndex())
        {
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接受图片时
                convertView = inflater.inflate(R.layout.row_received_picture, null);
                holder.tv_usernick = (TextView) convertView.findViewById(R.id.tv_userid);
                holder.pb = (ProgressBar) convertView.findViewById(R.id.progressBar);
            }
            else
            {
                // 发送图片时
                convertView = inflater.inflate(R.layout.row_sent_picture, null);
                holder.pb = (ProgressBar) convertView.findViewById(R.id.progressBar);
                holder.staus_iv = (ImageView) convertView.findViewById(R.id.msg_status);
            }
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_userhead);// 用户头像
            holder.iv_sendPicture = (ImageView) convertView.findViewById(R.id.iv_sendPicture);
            holder.iv_sendPicture.setTag(Constants.ITEM_POSITION, position);
            holder.iv_sendPicture.setOnLongClickListener(mOnLongClickListener);
            holder.percentage = (TextView) convertView.findViewById(R.id.percentage);
            // 时间间隔
            holder.llTimestampContainer = (LinearLayout) convertView.findViewById(R.id.ll_chat_time_container);
            holder.tv_time_stamp = (TextView) convertView.findViewById(R.id.timestamp);

            // 设置界面
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接受
                if (CommUtil.isNotBlank(otherIcon))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(otherIcon, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
                holder.tv_usernick.setText(otherNickname);
                // 接收的照片显示
                if (CommUtil.isNotBlank(message.getUri()))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE + message.getUri(), holder.iv_sendPicture, ImageLoaderHelper.mOptions);
                }
                // 进度
                holder.pb.setVisibility(View.GONE);
                holder.percentage.setVisibility(View.INVISIBLE);
            }
            else
            {
                // 发送
                if (CommUtil.isNotBlank(icon_me))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(icon_me, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
                holder.percentage.setVisibility(View.INVISIBLE);
                holder.iv_sendPicture.setImageBitmap(ImageUtils.getLoacalBitmap(message.getUri()));
                // 点击重新发送图片
                holder.staus_iv.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        reSendMsgListener.onReSendPicture(message);
                    }
                });
            }
            // 图片点击事件
            holder.iv_sendPicture.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    loadPicture(message);
                }
            });
        }
        // 名片消息
        else if (message.getMessageType() == MessageType.CARD.getIndex())
        {
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接收的名片
                convertView = inflater.inflate(R.layout.row_received_card, null);
                holder.tv_usernick = (TextView) convertView.findViewById(R.id.tv_userid);
            }
            else
            {
                // 发送的名片
                convertView = inflater.inflate(R.layout.row_sent_card, null);
                holder.pb = (ProgressBar) convertView.findViewById(R.id.pb_sending);
                holder.staus_iv = (ImageView) convertView.findViewById(R.id.msg_status);
            }
            holder.rl_card_container = (RelativeLayout) convertView.findViewById(R.id.rl_card_container);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_userhead);// 用户头像
            holder.tv_card_name = (TextView) convertView.findViewById(R.id.tv_card_name);
            holder.iv_card_icon = (ImageView) convertView.findViewById(R.id.iv_card_icon);

            // 时间间隔
            holder.llTimestampContainer = (LinearLayout) convertView.findViewById(R.id.ll_chat_time_container);
            holder.tv_time_stamp = (TextView) convertView.findViewById(R.id.timestamp);
            // 设置界面
            if (message.getDirect() == DWMessage.Direct.RECEIVE.ordinal())
            {
                // 接收
                holder.tv_usernick.setText(otherNickname);
                if (CommUtil.isNotBlank(otherIcon))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(otherIcon, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
            }
            else
            {
                if (CommUtil.isNotBlank(icon_me))
                {
                    ImageLoaderHelper.mImageLoader.displayImage(icon_me, holder.iv_avatar, ImageLoaderHelper.mOptions);
                }
                holder.staus_iv.setOnClickListener(new OnClickListener()
                {
                    // 名片消息重新发送
                    @Override
                    public void onClick(View v)
                    {
                        reSendMsgListener.onReSendCard(message);
                    }
                });
            }
            holder.tv_card_name.setText(message.getForwardName());
            String card_icon = ImageUtils.getIconByDwID(message.getForwardDwId(), ImageUtils.ICON_SMALL);
            if (CommUtil.isNotBlank(card_icon))
            {
                ImageLoaderHelper.mImageLoader.displayImage(card_icon, holder.iv_card_icon, ImageLoaderHelper.mOptions);
            }
            holder.rl_card_container.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context , NearCardDetailActivity_.class);
                    intent.putExtra(Constants.DW_ID, message.getForwardDwId());
                    intent.putExtra(NearCardDetailActivity.IS_STRANGER_PAGE, false);
                    context.startActivity(intent);
                }
            });

        }
        else if (messageType == MessageType.NOTIFY.getIndex())
        {
            convertView = inflater.inflate(R.layout.row_notify_message, null);
            holder.tv_notify_message = (TextView) convertView.findViewById(R.id.tv_notify_message);
            holder.tv_notify_message.setText(message.getBody());
        }
        if (messageType == MessageType.TEXT.getIndex() || messageType == MessageType.VOICE.getIndex() || messageType == MessageType.IMAGE.getIndex() || messageType == MessageType.CARD.getIndex())
        {
            // 头像点击事件
            holder.iv_avatar.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String fromDwId = message.getFromDwId();
                    String toDwId = message.getTo();
                    LogUtils.i("bm", "relationship---" + message.getChatRelationship());
                    if (!fromDwId.equals(DecentWorldApp.getInstance().getDwID()))
                    {
                        ChatActivity chatActivity = (ChatActivity) context;
                        if (chatRelationship == ChatRelationship.FRIEND.getIndex())
                        {
                            Intent intent = new Intent(context , NearCardDetailActivity_.class);
                            intent.putExtra(Constants.DW_ID, fromDwId);
                            intent.putExtra(NearCardDetailActivity.IS_STRANGER_PAGE, false);
                            context.startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(context , NearCardDetailActivity_.class);
                            intent.putExtra(Constants.DW_ID, fromDwId);
                            context.startActivity(intent);
                        }
                    }
                }
            });
            if (chatType == ChatType.SINGLE_ANONYMITY.getIndex())
            {
                holder.iv_avatar.setImageResource(R.drawable.default_avatar);
            }
            showTime(holder.llTimestampContainer, holder.tv_time_stamp, position);
            // 消息状态提示
            MessageStatus type = MessageStatus.getType(message.getNextStatus());
            if ((type != null) && (type.ordinal() != MessageStatus.DEFAULT.ordinal()))
            {
                holder.llMsgStatusPromptContainer = (LinearLayout) convertView.findViewById(R.id.ll_msg_status_prompt_container);
                holder.llMsgStatusPromptContainer.setVisibility(View.VISIBLE);
                holder.tvMsgStatusContent = (TextView) convertView.findViewById(R.id.tv_msg_status_prompt_content);
                holder.tvMsgStatusContent.setText("currentStatus:" + message.getCurrentStatus() + ",nextStatus:" + message.getNextStatus() + "\n" + type.getContent());
            }
            //如果时发送方，显示消息发送状态
            if (message.getDirect() == DWMessage.Direct.SEND.ordinal())
            {
                // 发送的文本消息
                if (message.getSendSuccess() == SendStatus.SUCCESS.getIndex() || message.getSendSuccess() == SendStatus.SUCCESS_FINISH.getIndex())
                {
                    // 成功
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.GONE);
                }
                else if (message.getSendSuccess() == SendStatus.SENDING.getIndex())
                {
                    // 发送中
                    holder.pb.setVisibility(View.VISIBLE);
                    holder.staus_iv.setVisibility(View.GONE);
                }
                else if (message.getSendSuccess() == SendStatus.FAILURE.getIndex() || message.getSendSuccess() == SendStatus.FAILURE_LACK_WEALTH.getIndex())
                {
                    // 失败
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.VISIBLE);
                }
            }
        }
        return convertView;
    }

    private void handlerVoice(final int position, ImageView ivVoice)
    {
        if (prePosition == position)
        {
            setAudioImageBg(position, ivVoice);
        }
        if (nowPosition == position)
        {
            if (!isMusicPlaying)
            {
                playAnimation(position, ivVoice);
                playMusic(position, ivVoice);
            }
            if (isPlayAnimation)
            {
                playAnimation(position, ivVoice);
            }
            else
            {
                setAudioImageBg(position, ivVoice);
            }
        }
    }

    private void setIconVoice(final int position, final ViewHolder holder, ImageView ivVoice)
    {
        holder.icon_voice.setTag(ivVoice);
        holder.icon_voice.setTag(Constants.ITEM_POSITION, position);
        holder.icon_voice.setOnClickListener(mOnClickListener);
        holder.icon_voice.setOnLongClickListener(mOnLongClickListener);
    }

    @Override
    public int getCount()
    {
        return listMsg.size();
    }

    @Override
    public DWMessage getItem(int position)
    {
        return listMsg.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private int isClickPosition = -1;
    private OnClickListener mOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            int position = (Integer) view.getTag(Constants.ITEM_POSITION);
            LogUtils.i(TAG, "mOnClickListener position=" + position);
            if (isClickPosition == -1)
            {
                startAudio(position);
            }
            else
            {
                if (isClickPosition == position)
                {
                    MediaManager.stop();
                    nowPosition = -1;
                    isClickPosition = -1;
                    notifyDataSetChanged();
                }
                else
                {
                    startAudio(position);
                }
            }
        }

        private void startAudio(int position)
        {
            isMusicPlaying = false;
            if (nowPosition != position)
            {
                prePosition = nowPosition;
                nowPosition = position;
            }
            notifyDataSetChanged();
            isClickPosition = position;
        }
    };
    private OnLongClickListener mOnLongClickListener = new OnLongClickListener()
    {

        @Override
        public boolean onLongClick(View view)
        {
            if (null != mOnMessageClickListener)
            {
                mOnMessageClickListener.onMessageClick(view);
            }
            return false;
        }
    };

    public void stopMediaPlayer()
    {
        MediaManager.stop();
    }

    /**
     * 按点击监听
     */
    public void setOnMessageClickListener(OnMessageClickListener onMessageClickListener)
    {
        this.mOnMessageClickListener = onMessageClickListener;
    }

    private OnMessageClickListener mOnMessageClickListener;

    public interface OnMessageClickListener
    {
        public void onMessageClick(View view);
    }

    public void playMusic(final int position, final ImageView ivVoice)
    {
        isPlayAnimation = true;
        DWMessage message = listMsg.get(position);
        String localUrl = message.getLocalUrl();
        if(CommUtil.isNotBlank(localUrl))
        {
            //本地语音
            LogUtils.v(TAG, "playMusic() params[position="+position + ",localUrl="+localUrl);
            playLocalMusic(localUrl);
        }
        else
        {
            String remoteUrl = message.getRemoteUrl();
            LogUtils.v(TAG, "playMusic() params[position="+position + ",remoteUrl="+remoteUrl);
            if(CommUtil.isNotBlank(remoteUrl))
            {
                //网络语音
                String audioFileName = remoteUrl.substring(remoteUrl.lastIndexOf("/"));
                File file = new File(FilePath.AUDIO_PATH + audioFileName);
                if (file.exists())
                {
                    playLocalMusic(file.getAbsolutePath());
                    message.setLocalUrl(file.getAbsolutePath());
                    message.save();
                }
                else
                {
                    // playNetMusic(uri);
                    DownLoadAudio(remoteUrl,message);
                }
            }
            else
            {
                Toast.makeText(context, "语音路径为空", Toast.LENGTH_LONG).show();
                return;
            }
        }
        isMusicPlaying = true;
    }

    private void playLocalMusic(String uri)
    {
        MediaManager.startLocal(context, uri, new OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mAnimation.stop();
                isPlayAnimation = false;
                isClickPosition = -1;
                notifyDataSetChanged();
            }
        }, new OnExceptionListener()
        {

            @Override
            public void onException(Exception e)
            {
                mAnimation.stop();
                isPlayAnimation = false;
                isClickPosition = -1;
                notifyDataSetChanged();
            }
        });
    }

    // private void playNetMusic(String uri) {
    // MediaManager.startNetwork(context, uri, new OnCompletionListener() {
    // @Override
    // public void onCompletion(MediaPlayer mp) {
    // mAnimation.stop();
    // isPlayAnimation = false;
    // isClickPosition = -1;
    // notifyDataSetChanged();
    // }
    // }, new OnExceptionListener() {
    // @Override
    // public void onException(Exception e) {
    // mAnimation.stop();
    // isPlayAnimation = false;
    // isClickPosition = -1;
    // notifyDataSetChanged();
    // }
    // });
    // }

    AnimationDrawable mAnimation;

    public void playAnimation(int position, ImageView ivVoice)
    {
        if (listMsg.get(position).getDirect() == DWMessage.Direct.RECEIVE.ordinal())
        {
            ivVoice.setBackgroundResource(R.anim.voice_from_icon);
        }
        else
        {
            ivVoice.setBackgroundResource(R.anim.voice_to_icon);
        }
        mAnimation = (AnimationDrawable) ivVoice.getBackground();
        mAnimation.start();
    }

    private void setAudioImageBg(int position, ImageView ivVoice)
    {
        if (listMsg.get(position).getDirect() == DWMessage.Direct.RECEIVE.ordinal())
        {
            ivVoice.setBackgroundResource(R.drawable.chatfrom_voice_playing);
        }
        else
        {
            ivVoice.setBackgroundResource(R.drawable.chatto_voice_playing);
        }
    }

    public static class ViewHolder
    {
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

        // 时间容器
        LinearLayout llTimestampContainer;
        TextView tv_time_stamp;// 时间

        RelativeLayout rl_card_container;// 卡片相对布局
        TextView tv_card_name;// 在名片上显示被转发人的名字（昵称）
        ImageView iv_card_icon;// 在名片上显示被转发人的头像

        TextView tv_notify_message;// 通知消息

        // 消息状态提示容器
        LinearLayout llMsgStatusPromptContainer;
        TextView tvMsgStatusContent;
    }

    /**
     * 点击缩略图加载大图
     * 
     * @param message
     */
    private void loadPicture(DWMessage message)
    {
        ArrayList<String> list = new ArrayList<String>();
        // 如果local_url为空，则用remote_url进行网络访问，并将图下载保存到本地，地址保存到local_url中
        if (CommUtil.isNotBlank(message.getLocalUrl()))
        {
            list.add(message.getLocalUrl());
            ImageUtils.browerBigImage(context, list, ImageUtils.IMAGE_FROM_LOCAL, 0);
        }
        else
        {
            list.add(message.getRemoteUrl());
            ImageUtils.browerBigImage(context, list, ImageUtils.IMAGE_FROM_NET, 0);
        }
    }

    /**
     * 显示时间
     * 
     * @param timeStamp
     * @param position
     */
    private void showTime(LinearLayout timeContainer, TextView timeStamp, int position)
    {
        try
        {
            if (position == 0)
            {
                timeContainer.setVisibility(View.VISIBLE);
                timeStamp.setVisibility(View.VISIBLE);
                timeStamp.setText(TimeUtils.toMsgFormat(Long.valueOf(getItem(position).getTime())));
            }
            else
            {
                if (TimeUtils.isShowTime(getItem(position - 1).getTime(), getItem(position).getTime()))
                {
                    timeContainer.setVisibility(View.VISIBLE);
                    timeStamp.setVisibility(View.VISIBLE);
                    timeStamp.setText(TimeUtils.toMsgFormat(Long.valueOf(getItem(position).getTime())));
                }
                else
                {
                    timeContainer.setVisibility(View.GONE);
                }
            }
        }
        catch (NumberFormatException e)
        {
            LogUtils.e(TAG, "showTime() NumberFormatException:" + e + ",info=" + getItem(position).toString());
        }
    }

    private ProgressDialog mProDialog;

    private void showProgressDialog()
    {
        if (null == mProDialog)
        {
            mProDialog = ProgressDialog.show(context, null, "loading");
        }
        else
        {
            mProDialog.show();
        }
    }

    private void hideProgressDialog()
    {
        if (null != mProDialog)
        {
            mProDialog.hide();
        }
    }

    HttpUtils httpUtils = new HttpUtils();

    public void DownLoadAudio(String uri,final DWMessage dwMessage)
    {
        String fileName = uri.substring(uri.lastIndexOf("/"));
        showProgressDialog();
        httpUtils.download(uri, FilePath.AUDIO_PATH + fileName, new RequestCallBack<File>()
        {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo)
            {
                hideProgressDialog();
                String localPath = responseInfo.result.getAbsolutePath();
                playLocalMusic(localPath);
                dwMessage.setLocalUrl(localPath);
                dwMessage.save();
            }

            @Override
            public void onFailure(HttpException error, String msg)
            {
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
