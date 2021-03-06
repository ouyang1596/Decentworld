/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.SmileUtils;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.entity.db.Conversation;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.entity.db.DWMessage.MessageType;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.TimeUtils;

/**
 * @ClassName: MessageListAdapter.java
 * @Description: 消息列表适配器
 * @author: cj
 * @date: 2015年10月5日 下午5:32:38
 */
public class ConversationListAdapter2 extends BaseAdapter {
	private static final String TAG = "ConversationListAdapter2";
	private Context context;
	private LayoutInflater inflater;
	private List<Conversation> messageLists;

	public ConversationListAdapter2(Context context, List<Conversation> messageLists) {
		this.context = context;
		this.messageLists = messageLists;
	}

	@Override
	public int getCount() {
		return messageLists.size();
	}

	@Override
	public Conversation getItem(int position) {
		return messageLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.from(context).inflate(R.layout.item_stranger_recorde, null);
			holder.list_item_layout = (LinearLayout) convertView.findViewById(R.id.list_item_layout);
			holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_fragment_chat_chat_avatar);
			holder.iv_avatar.setOnClickListener(mOnClickListener);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_fragment_chat_chat_title);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_fragment_chat_chat_content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_fragment_chat_chat_time);
			holder.unread_msg_number = (TextView) convertView.findViewById(R.id.tv_fragment_chat_chat_unread_msg_number);
			holder.ivUserType = (ImageView) convertView.findViewById(R.id.tv_fragment_chat_chat_userType);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_avatar.setTag(Constants.ITEM_POSITION, position);
		Conversation messageList = messageLists.get(position);
		if (CommUtil.isNotBlank(messageList.getIcon())) {
			ImageLoaderHelper.mImageLoader.displayImage(messageList.getIcon(), holder.iv_avatar, ImageLoaderHelper.mOptions);
		}
		holder.tv_title.setText(messageList.getTitle());
		if (messageList.getMessageType() == MessageType.TEXT.getIndex()) {
			Spannable spannable = SmileUtils.getSmiledText(context, messageList.getContent(), 2);
			holder.tv_content.setText(spannable, BufferType.SPANNABLE);
			holder.tv_content.setTextColor(Color.parseColor("#B3B3B3"));
		} else if (messageList.getMessageType() == MessageType.VOICE.getIndex()) {
			holder.tv_content.setText("[语音]");
			holder.tv_content.setTextColor(Color.parseColor("#C0FF3E"));
		} else if (messageList.getMessageType() == MessageType.IMAGE.getIndex()) {
			holder.tv_content.setText("[图片]");
			holder.tv_content.setTextColor(Color.parseColor("#EE0000"));
		} else if (messageList.getMessageType() == MessageType.CARD.getIndex()) {
			holder.tv_content.setText("[名片]");
			holder.tv_content.setTextColor(Color.parseColor("#0000FF"));
		}
		holder.tv_time.setText(TimeUtils.toConversationFormat(messageList.getTime()));
		if (messageList.getUnReadCount() != 0) {
			holder.unread_msg_number.setVisibility(View.VISIBLE);
			holder.unread_msg_number.setText(messageList.getUnReadCount() + "");
		} else {
			holder.unread_msg_number.setVisibility(View.INVISIBLE);
		}

		// 处理“疑”、“真”、“腕”
		if (messageList.getUserType() == ContactUser.UserType.IMPEACH.getIndex()) {
			holder.ivUserType.setVisibility(View.VISIBLE);
			holder.ivUserType.setImageResource(R.drawable.stranger_doubt_shadow);
		} else if (messageList.getUserType() == ContactUser.UserType.NORMAL.getIndex()) {
			holder.ivUserType.setVisibility(View.GONE);
		} else if (messageList.getUserType() == ContactUser.UserType.VIP.getIndex()) {
			holder.ivUserType.setVisibility(View.VISIBLE);
			holder.ivUserType.setImageResource(R.drawable.stranger_wan);
		}
		return convertView;
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			Integer position = (Integer) view.getTag(Constants.ITEM_POSITION);
			Intent intent = new Intent(context, NearCardDetailActivity_.class);
			intent.putExtra(Constants.DW_ID, getItem(position).getDwID());
			context.startActivity(intent);
		}
	};

	class ViewHolder {
		LinearLayout list_item_layout;
		ImageView iv_avatar;// 头像
		TextView tv_title;// 标题
		TextView tv_content;// 内容
		TextView tv_time;// 时间
		TextView unread_msg_number;// 未读消息
		ImageView ivUserType;

	}

}
