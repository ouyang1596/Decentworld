package cn.sx.decentworld.adapter;

import java.util.HashMap;
import java.util.List;

import org.simple.eventbus.EventBus;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.NewFriend;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.AddFriendDialog;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;

import com.android.volley.Request.Method;

/**
 * 
 * @ClassName: NewFriendLVAdapter.java
 * @Description:
 * @author: dyq
 * @date: 2015年9月15日 下午3:27:12
 */
public class NewFriendLVAdapter extends BaseAdapter implements OnClickListener {
	public static final String TAG = "NewFriendLVAdapter";
	private Context context;
	private LayoutInflater inflater;
	private List<NewFriend> users;
	private FragmentManager fragmentManager;
	private String my_dwid = DecentWorldApp.getInstance().getDwID();

	public NewFriendLVAdapter(Context context, List<NewFriend> users,
			FragmentManager fragmentManager) {
		super();
		this.context = context;
		this.users = users;
		inflater = LayoutInflater.from(context);
		this.fragmentManager = fragmentManager;
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public NewFriend getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lv_newfriend_item, parent,
					false);
		}
		// 设置已显示过
		final NewFriend ff = getItem(position);
		ff.setIsShown(1);
		ff.save();

		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.avatar = (ImageView) convertView
					.findViewById(R.id.iv_newfriend);
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_new_friend_name);
			holder.info = (TextView) convertView
					.findViewById(R.id.tv_new_fiend_info);
			holder.tv_had_added = (TextView) convertView
					.findViewById(R.id.tv_had_added);
			holder.tv_had_refused = (TextView) convertView
					.findViewById(R.id.tv_had_refused);
			holder.bt_to_add = (Button) convertView
					.findViewById(R.id.bt_to_add);
			holder.bt_to_refuse = (Button) convertView
					.findViewById(R.id.bt_to_refuse);
			holder.tv_had_apply = (TextView) convertView
					.findViewById(R.id.tv_had_apply);
			holder.tv_add_success = (TextView) convertView
					.findViewById(R.id.tv_add_success);
			holder.tv_add_fail = (TextView) convertView
					.findViewById(R.id.tv_add_fail);
			convertView.setTag(holder);
		}

		// 被添加
		if (ff.getMessageType() == NewFriend.message_be_add) {
			holder.bt_to_add.setVisibility(View.VISIBLE);
			holder.bt_to_refuse.setVisibility(View.VISIBLE);
			holder.tv_had_added.setVisibility(View.INVISIBLE);
			holder.tv_had_refused.setVisibility(View.INVISIBLE);
			holder.tv_had_apply.setVisibility(View.INVISIBLE);
			holder.tv_add_success.setVisibility(View.INVISIBLE);
			holder.tv_add_fail.setVisibility(View.INVISIBLE);

			/**
			 * 已过期
			 */
			if (users.get(position).getOutOfDate() == 1) {
				holder.bt_to_add.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AddFriendDialog dialog = AddFriendDialog
								.newInstance();
						dialog.show(fragmentManager, "dialog");
					}
				});
				holder.bt_to_refuse.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AddFriendDialog dialog = AddFriendDialog
								.newInstance();
						dialog.show(fragmentManager, "dialog");
					}
				});
			}
			/**
			 * 未过期
			 */
			else {
				holder.bt_to_add.setOnClickListener(new OnClickListener() {
					/**
					 * 还需要修改bean对象，并且更新数据库
					 */
					@Override
					public void onClick(View v) {
						acceptFriend(my_dwid, ff.getOtherID());
					}

				});
				holder.bt_to_refuse.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						refuseFriend(my_dwid, ff.getOtherID());
					}
				});
			}
		}
		// 主动添加后
		else if (ff.getMessageType() == NewFriend.message_had_apply) {
			holder.bt_to_add.setVisibility(View.INVISIBLE);
			holder.bt_to_refuse.setVisibility(View.INVISIBLE);
			holder.tv_had_apply.setVisibility(View.VISIBLE);

			holder.tv_had_added.setVisibility(View.INVISIBLE);
			holder.tv_had_refused.setVisibility(View.INVISIBLE);
			holder.tv_add_success.setVisibility(View.INVISIBLE);
			holder.tv_add_fail.setVisibility(View.INVISIBLE);
		}
		// 已经添加
		else if (ff.getMessageType() == NewFriend.message_had_add) {
			holder.bt_to_add.setVisibility(View.INVISIBLE);
			holder.bt_to_refuse.setVisibility(View.INVISIBLE);
			holder.tv_had_added.setVisibility(View.VISIBLE);

			holder.tv_had_refused.setVisibility(View.INVISIBLE);
			holder.tv_had_apply.setVisibility(View.INVISIBLE);
			holder.tv_add_success.setVisibility(View.INVISIBLE);
			holder.tv_add_fail.setVisibility(View.INVISIBLE);
		}
		// 被拒绝
		else if (ff.getMessageType() == NewFriend.message_had_refused) {
			holder.bt_to_add.setVisibility(View.INVISIBLE);
			holder.bt_to_refuse.setVisibility(View.INVISIBLE);
			holder.tv_had_refused.setVisibility(View.VISIBLE);

			holder.tv_had_added.setVisibility(View.INVISIBLE);
			holder.tv_had_apply.setVisibility(View.INVISIBLE);
			holder.tv_add_success.setVisibility(View.INVISIBLE);
			holder.tv_add_fail.setVisibility(View.INVISIBLE);
		}
		// 添加成功
		else if (ff.getMessageType() == NewFriend.message_add_success) {
			holder.bt_to_add.setVisibility(View.INVISIBLE);
			holder.bt_to_refuse.setVisibility(View.INVISIBLE);
			holder.tv_add_success.setVisibility(View.VISIBLE);

			holder.tv_had_added.setVisibility(View.INVISIBLE);
			holder.tv_had_refused.setVisibility(View.INVISIBLE);
			holder.tv_had_apply.setVisibility(View.INVISIBLE);
			holder.tv_add_fail.setVisibility(View.INVISIBLE);
		}
		// 添加失败
		else if (ff.getMessageType() == NewFriend.message_add_fail) {
			holder.bt_to_add.setVisibility(View.INVISIBLE);
			holder.bt_to_refuse.setVisibility(View.INVISIBLE);
			holder.tv_add_fail.setVisibility(View.VISIBLE);

			holder.tv_had_added.setVisibility(View.INVISIBLE);
			holder.tv_had_refused.setVisibility(View.INVISIBLE);
			holder.tv_had_apply.setVisibility(View.INVISIBLE);
			holder.tv_add_success.setVisibility(View.INVISIBLE);
		}
		if (!CommUtil.isBlank(ff.getIcon())) {
			// Picasso.with(context).load(ff.getAvatar()).config(Config.RGB_565).placeholder(R.drawable.work_default).resize(120,
			// 120).into(holder.avatar);
			ImageLoaderHelper.mImageLoader.displayImage(ff.getIcon(),
					holder.avatar, ImageLoaderHelper.mOptions);
		}
		holder.name.setText(ff.getUsername());
		holder.info.setText(ff.getAddReason());
		return convertView;
	}

	private class ViewHolder {
		ImageView avatar;
		TextView name;
		TextView info;
		Button bt_to_add;
		Button bt_to_refuse;
		TextView tv_had_added;
		TextView tv_had_refused;
		TextView tv_had_apply;
		TextView tv_add_success;
		TextView tv_add_fail;
	}

	/**
	 * 同意添加为好友
	 * 
	 * @param dwID
	 * @param friendID
	 */
    public void acceptFriend(String dwID, final String friendID)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("friendID", friendID);
        SendUrl sendUrl = new SendUrl(context);
        LogUtils.i(TAG, "acceptFriend...begin");
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_ACCEPT_FRIEND, Method.GET, new HttpCallBack()
        {
            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "acceptFriend...onFailure,cause by:" + e);
            }

            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "acceptFriend...msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "acceptFriend...success");
                    NewFriend friend = NewFriend.queryByDwID(friendID);
                    friend.setMessageType(NewFriend.message_had_add);
                    friend.setIsShown(1);
                    friend.save();
                    EventBus.getDefault().post(friend, NotifyByEventBus.NT_UPDATE_NEW_FRIEND_LIST);
                }

                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "acceptFriend...failure,cause by:" + msg.getMsg());
                }
            }
        });
    }

	/**
	 * 拒绝加为好友
	 * 
	 * @param dwID
	 * @param friendID
	 */
    public void refuseFriend(String dwID, final String friendID)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("friendID", friendID);
        SendUrl sendUrl = new SendUrl(context);
        LogUtils.i(TAG, "refuseFriend...begin");
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_REFUSE_FRIEND, Method.GET, new HttpCallBack()
        {
            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "refuseFriend...onFailure,cause by:" + e);
            }

            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.i(TAG, "refuseFriend...msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "refuseFriend...success");
                    NewFriend friend = NewFriend.queryByDwID(friendID);
                    friend.setMessageType(NewFriend.message_had_refused);
                    friend.save();
                    EventBus.getDefault().post(friend, NotifyByEventBus.NT_UPDATE_NEW_FRIEND_LIST);
                }

                if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "refuseFriend...failure,cause by:" + msg.getMsg());
                }
            }

        });
    }

	@Override
	public void onClick(View v) {

	}
}
