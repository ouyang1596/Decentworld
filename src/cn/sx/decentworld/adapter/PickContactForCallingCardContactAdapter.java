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
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

/**
 * @ClassName: ContactAdapter.java
 * @Description:
 * @author: yj
 * @date: 2015年7月9日 上午9:59:54
 */
public class PickContactForCallingCardContactAdapter extends BaseAdapter {
	private static final String TAG = "FragmentContactAdapter";
	private List<ContactUser> data = null;
	private Context mContext;

	public PickContactForCallingCardContactAdapter(Context mContext, List<ContactUser> list) {
		this.mContext = mContext;
		this.data = list;
	}

	public int getCount() {
		return data.size();
	}

	public ContactUser getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View con, ViewGroup arg2) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_contact_calling_card, null);
			vh = new ViewHolder();
			vh.ivIcon = (ImageView) con.findViewById(R.id.iv_icon);
			vh.tvName = (TextView) con.findViewById(R.id.tv_name);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		ContactUser contactUser = data.get(position);
		vh.tvName.setText(contactUser.getShowName());
		String icon = ImageUtils.getIconByDwID(contactUser.getFriendID(), ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(icon)) {
			ImageLoaderHelper.mImageLoader.displayImage(icon, vh.ivIcon, ImageLoaderHelper.mOptions);
		}
		return con;
	}

	class ViewHolder {
		ImageView ivIcon;
		TextView tvName;
	}
}
