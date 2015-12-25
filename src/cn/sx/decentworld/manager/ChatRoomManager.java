/**
 * 
 */
package cn.sx.decentworld.manager;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.bean.OccupantList;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;
import cn.sx.decentworld.widget.CircularImage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.EBean;

/**
 * @ClassName: ChatRoomManager.java
 * @Description:聊天室管理者
 * @author: cj
 * @date: 2015年11月30日 下午6:22:02
 */
@EBean
public class ChatRoomManager {
	private static final String TAG = "ChatRoomManager";
	private Map<String, ChatRoomOccupant> mOccupant;
	private static final int GET_NICKNAME_AND_WORTH = 1;// 获取昵称和身价
	private SendUrl sendUrl;
	private Context context;

	/**
	 * 
	 */
	public ChatRoomManager(Context context) {
		this.context = context;
		this.mOccupant = new HashMap<String, ChatRoomManager.ChatRoomOccupant>();
		this.sendUrl = new SendUrl(context);
	}

	/**
	 * 设置聊天室头像下的昵称和身价
	 * 
	 * @param dwID
	 * @param nickname
	 * @param worth
	 */
	public void setNicnameAndWorth(final String dwID,
			final TextView tv_nickname, final TextView tv_worth) {
		if (CommUtil.isBlank(dwID)) {
			LogUtils.i(TAG, "dwID为空");
			return;
		}
		if (CommUtil.isBlank(tv_worth) || CommUtil.isBlank(tv_nickname)) {
			LogUtils.i(
					TAG,
					"setNicnameAndWorth(final String dwID,final TextView tv_nickname ,final TextView tv_worth)参数为空");
			return;
		}
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case GET_NICKNAME_AND_WORTH:
					String result = msg.obj.toString();
					JSONObject jsonObject = JSON.parseObject(result);
					String nick = jsonObject.getString("nick");
					String worth = jsonObject.getString("worth");
					ChatRoomOccupant temp = new ChatRoomOccupant();
					temp.setNickname(nick);
					temp.setWorth(worth);
					mOccupant.put(dwID, temp);

					// 设置成员昵称
					tv_nickname.setText(nick);
					// 设置成员身价
					tv_worth.setVisibility(View.VISIBLE);
					tv_worth.setText(worth);
					break;
				default:
					break;
				}
			};
		};
		LogUtils.i(TAG, "setNicnameAndWorth...begin");
		ChatRoomOccupant occupant = mOccupant.get(dwID);
		if (occupant != null) {
			// 设置成员昵称
			tv_nickname.setText(occupant.getNickname());
			// 设置成员身价
			tv_worth.setVisibility(View.VISIBLE);
			tv_worth.setText(occupant.getWorth());
		} else {
			// 从网络获取
			new CommHttp(context).getNicknameAndWorth(dwID, handler,
					GET_NICKNAME_AND_WORTH);
		}
	}

	// 聊天室成员
	class ChatRoomOccupant {
		String nickname;// 昵称
		String worth;// 身价

		/**
		 * @return the nickname
		 */
		public String getNickname() {
			return nickname;
		}

		/**
		 * @param nickname
		 *            the nickname to set
		 */
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		/**
		 * @return the worth
		 */
		public String getWorth() {
			return worth;
		}

		/**
		 * @param worth
		 *            the worth to set
		 */
		public void setWorth(String worth) {
			this.worth = worth;
		}

	}

	/**
	 * 初始化主持人的头像
	 * 
	 * @param owner
	 *            主持人的bean类
	 * @param iv_ownerIcon
	 *            主持人头像控件
	 */
	public void initOwnerIcon(final OccupantList owner,
			CircularImage iv_ownerIcon) {
		LogUtils.i(TAG, "initOwnerIcon");
		if (owner != null)
		{
			if (CommUtil.isNotBlank(owner.getDwID()))
			{
				ImageLoaderHelper.mImageLoader.displayImage(ImageUtils.getIconByDwID(owner.getDwID(), ImageUtils.ICON_SMALL), iv_ownerIcon, ImageLoaderHelper.mOptions);
				iv_ownerIcon.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(context , NearCardDetailActivity_.class);
						intent.putExtra(Constants.DW_ID, owner.getDwID());
						context.startActivity(intent);
					}
				});
			}
		} else {
			// Picasso.with(context)
			// .load(R.drawable.default_avatar).resize(80, 80)
			// .into(iv_ownerIcon);
			iv_ownerIcon.setImageResource(R.drawable.default_avatar);
			iv_ownerIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.toast(context, "主持人没有上线");
				}
			});

		}
	}

}
