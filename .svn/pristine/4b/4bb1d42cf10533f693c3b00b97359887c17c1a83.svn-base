/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.common.CommUtil;
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

	/**
	 * 构造函数
	 * 
	 * @param mContext
	 * @param list
	 */

	public PickContactAdapter(Context mContext, List<ContactUser> list) {
		super();
		this.mContext = mContext;
		this.list = list;
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
		final ContactUser mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_pick_contact, null);
			viewHolder.name = (TextView) view
					.findViewById(R.id.tv_pick_contact_name);
			viewHolder.icon = (ImageView) view
					.findViewById(R.id.iv_pick_contact_icon);
			viewHolder.ll_pick_contact = (LinearLayout) view
					.findViewById(R.id.ll_pick_contact);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		ContactUser contactUser = list.get(position);
		viewHolder.name.setText(ContactUser.getDisplayNameByBean(contactUser));
		String icon_url = ImageUtils.getIconByDwID(contactUser.getDwID(),
				ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(icon_url)) {
			// Picasso.with(mContext).load(icon_url).into(viewHolder.icon);
			ImageLoaderHelper.mImageLoader.displayImage(icon_url,
					viewHolder.icon, ImageLoaderHelper.mOptions);
		}
		return view;
	}

	static class ViewHolder {
		LinearLayout ll_pick_contact;
		ImageView icon;
		TextView name;
	}
}
