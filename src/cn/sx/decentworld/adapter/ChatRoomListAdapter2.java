package cn.sx.decentworld.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatRoomChatActivity_;
import cn.sx.decentworld.activity.MainActivity_;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.bean.ChatRoomInfo;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;
import cn.sx.decentworld.widget.CircularImageView;

import com.android.volley.Request.Method;

/**
 * @ClassName: ChatRoomListAdapter2.java
 * @Description: 聊天室列表适配器
 * @author: yj
 */
public class ChatRoomListAdapter2 extends BaseAdapter {
	private static final String TAG = "ChatRoomListAdapter2";
	private Context context;
	private List<ChatRoomInfo> data;
	private SendUrl sendUrl;
	private static String POSITION = "position";

	public ChatRoomListAdapter2(Context context, List<ChatRoomInfo> list) {
		this.context = context;
		this.data = list;
		sendUrl = new SendUrl(context);
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public ChatRoomInfo getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(context, R.layout.item_chatroom_list, null);
			ViewUtil.scaleView(con);
			vh = new ViewHolder();
			vh.ivBg = (ImageView) con.findViewById(R.id.iv_bg);
			vh.ivDetail = (CircularImageView) con.findViewById(R.id.iv_detail);
			vh.ivChatRoomEnter = (ImageView) con
					.findViewById(R.id.iv_chatroom_enter);
			vh.tvChatRoomName = (TextView) con
					.findViewById(R.id.tv_subject_name);
			vh.tvSelfIntroduce = (TextView) con
					.findViewById(R.id.tv_self_introduce);
			vh.tvOnLineCount = (TextView) con
					.findViewById(R.id.tv_online_count);
			vh.tvChargeAmount = (TextView) con
					.findViewById(R.id.tv_chargerAmount);
			vh.tvSelfNickName = (TextView) con
					.findViewById(R.id.tv_self_nickname);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		ChatRoomInfo info = data.get(position);
		String roomBg = info.getRoomBackground();
		if (CommUtil.isNotBlank(roomBg)) {
			// Picasso.with(context).load(roomBg).into(vh.ivBg);
			ImageLoaderHelper.mImageLoader.displayImage(roomBg, vh.ivBg,
					ImageLoaderHelper.mOptions);
		} else {
			vh.ivBg.setImageResource(R.drawable.ic_launcher);
		}
		String ownerIcon = info.getOwnerIcon();
		if (CommUtil.isNotBlank(ownerIcon)) {
			// Picasso.with(context).load(ownerIcon).config(Config.RGB_565)
			// .into(vh.ivDetail);
			ImageLoaderHelper.mImageLoader.displayImage(ownerIcon, vh.ivDetail,
					ImageLoaderHelper.mOptions);
		} else {
			vh.ivDetail.setImageResource(R.drawable.ic_launcher);
		}
		vh.tvSelfNickName.setText(info.getOwnerName());
		LogUtils.i(TAG, info.getSubjectName());
		vh.tvChatRoomName.setText(info.getSubjectName());
		vh.tvSelfIntroduce.setText(info.getOwnerIntroduction());
		vh.tvOnLineCount.setText(info.getOnLineNum() + " 在线");
		vh.tvChargeAmount.setText(info.getChargeAmount() + " 价格");
		vh.ivDetail.setTag(Constants.ITEM_POSITION, position);
		vh.ivDetail.setOnClickListener(mOnlcClickListener);
		vh.ivChatRoomEnter.setTag(Constants.ITEM_POSITION, position);
		vh.ivChatRoomEnter.setOnClickListener(mOnlcClickListener);
		return con;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Intent intent = new Intent(context, ChatRoomChatActivity_.class);
			intent.putExtra("json_data", msg.obj.toString());
			LogUtils.i(TAG, "调用加入聊天室接口返回的数据：" + msg.obj.toString());
			context.startActivity(intent);
		};
	};
	private OnClickListener mOnlcClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			int position = (Integer) view.getTag(Constants.ITEM_POSITION);
			ChatRoomInfo info = getItem(position);
			switch (view.getId()) {
			case R.id.iv_chatroom_enter:
				if (null != info) {
					requestEnterChatRoom(info);
				}
				break;
			case R.id.iv_detail:
				if (null == info.getOwnerID()) {
					return;
				}
				if (DecentWorldApp.getInstance().getDwID()
						.equals(info.getOwnerID())) {
					DecentWorldApp.ifFromAppOwner = true;
					MainActivity_.main_viewpager.setCurrentItem(1);
				} else {
					Intent intent = new Intent(context,
							NearCardDetailActivity_.class);
					intent.putExtra(Constants.DW_ID, info.getOwnerID());
					// intent.putExtra(POSITION, 100);
					context.startActivity(intent);
				}
				break;
			}
		}
	};

	public void requestEnterChatRoom(ChatRoomInfo info) {
		DecentWorldApp.chatRoomInfo = info;
		String dwID = DecentWorldApp.getInstance().getDwID();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("roomID", info.getRoomID());
		map.put("nickName", UserInfo.queryByDwID(dwID).getNickName());
		LogUtils.i(TAG, "requestEnterChatRoom...dwID=" + dwID + ",roomID="
				+ info.getRoomID());
		showProgressDialog();
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE
				+ "/joinChatRoom", Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					Message message = new Message();
					message.obj = msg.getData().toString();
					mHandler.sendMessage(message);
				} else {
					LogUtils.i(TAG, "requestEnterChatRoom...failure,cause by:"
							+ msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				Toast.makeText(context, Constants.NET_WRONG, Toast.LENGTH_LONG)
						.show();
				LogUtils.i(TAG, "requestEnterChatRoom...onFailure,cause by:"
						+ e);
			}
		});
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
		((Activity) context).runOnUiThread(new Runnable() {
			public void run() {
				if (null != mProDialog) {
					mProDialog.hide();
				}
			}
		});
	}

	class ViewHolder {
		ImageView ivBg, ivChatRoomEnter;
		CircularImageView ivDetail;
		TextView tvSelfIntroduce, tvChatRoomName, tvOnLineCount,
				tvChargeAmount, tvSelfNickName;
	}
}
