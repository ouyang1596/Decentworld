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
import cn.sx.decentworld.entity.SearchResult;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

/**
 * 
 * @ClassName: SearchAdapter.java
 * @Description: 搜索适配器
 * @author: yj
 * @date: 2016年3月22日 下午3:05:21
 */
public class PickContactForCallingCardAdapter extends BaseAdapter {
	private Context mContext;
	private List<SearchResult> datas;

	public PickContactForCallingCardAdapter(Context context, List<SearchResult> list) {
		super();
		mContext = context;
		datas = list;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public SearchResult getItem(int position) {
		return datas.get(position);
	}

	@Override
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
		SearchResult contactUser = datas.get(position);
		vh.tvName.setText(contactUser.name);
		String icon = ImageUtils.getIconByDwID(contactUser.dwID, ImageUtils.ICON_SMALL);
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
