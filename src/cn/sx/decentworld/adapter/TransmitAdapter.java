/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

/**
 * @ClassName: TransmitAdapter.java
 * @Description:
 * @author: yj
 * @date: 2015年11月23日 上午8:33:42
 */
public class TransmitAdapter extends BaseAdapter {
	private Context mContext;
	private List<ContactUser> mData;

	public TransmitAdapter(Context context, List<ContactUser> data) {
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public ContactUser getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_contact, null);
			vh = new ViewHolder();
			vh.tvName = (TextView) con.findViewById(R.id.tv_contact_name);
			vh.ivHead = (ImageView) con.findViewById(R.id.iv_contact_head);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		ContactUser user = mData.get(position);
		setUserName(vh, user);
		String icon = ImageUtils.getIconByDwID(user.getDwID(),
				ImageUtils.ICON_SMALL);
		setImageView(vh, icon);
		return con;
	}

	private void setImageView(ViewHolder vh, String icon) {
		if (CommUtil.isNotBlank(icon)) {
			// Picasso.with(mContext).load(icon).into(vh.ivHead);
			ImageLoaderHelper.mImageLoader.displayImage(icon, vh.ivHead,
					ImageLoaderHelper.mOptions);
		}
	}

	private void setUserName(ViewHolder vh, ContactUser user) {
		if (CommUtil.isNotBlank(user.getRemark())) {
			vh.tvName.setText(user.getRemark());
		} else if (CommUtil.isNotBlank(user.getRealName())) {
			vh.tvName.setText(user.getRemark());
		} else {
			vh.tvName.setText(user.getNickName());
		}
	}

	class ViewHolder {
		ImageView ivHead;
		TextView tvName;
	}

}
