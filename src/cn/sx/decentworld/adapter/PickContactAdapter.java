/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.PickContactActivity;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

/**
 * @ClassName: ContactAdapter.java
 * @Description：选择联系人适配器
 * @author: cj
 * @date: 2015年7月9日 上午9:59:54
 */
public class PickContactAdapter extends BaseAdapter {
	private static final String TAG = "PickContactAdapter";
	private List<ContactUser> list = null;
	private Context mContext;
	private int pickType;

	/**
	 * 构造函数
	 * 
	 * @param mContext
	 * @param list
	 */

	public PickContactAdapter(Context mContext, List<ContactUser> list, int pickType) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.pickType = pickType;
	}

	public int getCount() {
		return this.list.size();
	}

	public ContactUser getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_pick_contact, null);
			viewHolder.ll_pick_contact = (RelativeLayout) view.findViewById(R.id.ll_pick_contact);
			viewHolder.name = (TextView) view.findViewById(R.id.tv_pick_contact_name);
			viewHolder.icon = (ImageView) view.findViewById(R.id.iv_pick_contact_icon);
			viewHolder.check = (CheckBox) view.findViewById(R.id.cb_pick_contact_check);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final ContactUser contactUser = list.get(position);
		if (pickType == PickContactActivity.PICK_TYPE_SINGLE) {
			viewHolder.check.setVisibility(View.INVISIBLE);
			viewHolder.ll_pick_contact.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					ArrayList<ContactUser> value = new ArrayList<ContactUser>();
					value.add(list.get(position));
					Bundle bundle = new Bundle();
					bundle.putSerializable(PickContactActivity.CONTACT_DWID, value);
					intent.putExtras(bundle);
					Activity activity = (Activity) mContext;
					activity.setResult(Activity.RESULT_OK, intent);
					activity.finish();
				}
			});
		} else if (pickType == PickContactActivity.PICK_TYPE_MULTI) {
			viewHolder.check.setVisibility(View.VISIBLE);
			if (contactUser.isChecked())
				viewHolder.check.setChecked(true);
			else
				viewHolder.check.setChecked(false);
			viewHolder.ll_pick_contact.setOnClickListener(new CheckClick(viewHolder, position));
		}
		viewHolder.name.setText(contactUser.getShowName());
		String icon_url = ImageUtils.getIconByDwID(contactUser.getFriendID(), ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(icon_url)) {
			ImageLoaderHelper.mImageLoader.displayImage(icon_url, viewHolder.icon, ImageLoaderHelper.mOptions);
		}

		return view;
	}

	class ViewHolder {
		RelativeLayout ll_pick_contact;
		ImageView icon;
		TextView name;
		CheckBox check;
	}

	class CheckClick implements OnClickListener {
		ViewHolder holder;
		int position;

		/**
         * 
         */
		public CheckClick(ViewHolder holder, int position) {
			this.holder = holder;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			ContactUser contactUser = list.get(position);
			LogUtils.d(TAG, "onClick---position---" + position);
			if (contactUser.isChecked()) {
				list.get(position).setChecked(false);
				holder.check.setChecked(false);
			} else {
				list.get(position).setChecked(true);
				holder.check.setChecked(true);
			}

		}

	}

}
