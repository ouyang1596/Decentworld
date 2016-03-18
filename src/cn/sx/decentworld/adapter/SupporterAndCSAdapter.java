/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.SupporterAndCSBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.widget.CircularImageView;

/**
 * @ClassName: ExamineSupportAdapter.java
 * @author: yj
 * @date: 2016年1月27日 下午6:40:51
 */
public class SupporterAndCSAdapter extends BaseAdapter {
	private Context mContext;
	private List<SupporterAndCSBean> mSupporters;

	public SupporterAndCSAdapter(Context context, List<SupporterAndCSBean> supporters) {
		mContext = context;
		mSupporters = supporters;
	}

	@Override
	public int getCount() {
		return mSupporters == null ? 0 : mSupporters.size();
	}

	@Override
	public SupporterAndCSBean getItem(int position) {
		return mSupporters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_examine_support, null);
			vh = new ViewHolder();
			vh.tvNickName = (TextView) con.findViewById(R.id.tv_nickname);
			vh.ivSupport = (CircularImageView) con.findViewById(R.id.iv_examine_support);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		SupporterAndCSBean bean = mSupporters.get(position);
		vh.tvNickName.setText(bean.showName);
		String supportID = bean.id;
		if (null != supportID) {
			ImageLoaderHelper.mImageLoader.displayImage(ImageUtils.getIconByDwID(supportID, ImageUtils.ICON_MAIN), vh.ivSupport,
					ImageLoaderHelper.mOptions);
		} else {
			vh.ivSupport.setImageResource(R.drawable.default_icon);
		}
		return con;
	}

	class ViewHolder {
		CircularImageView ivSupport;
		TextView tvNickName;
	}
}
