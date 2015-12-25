/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: PraiseAdapter.java
 * @Description:
 * @author: cj
 * @date: 2015年8月12日 下午5:51:04
 */
public class PraiseAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Praise> mDatas;
	public static final int PRAISE = 1;
	public static final int TRAMPLE = 2;
	private int isPraiseOrTrample = 1;

	/**
	 * 
	 */
	public PraiseAdapter(Context context, ArrayList<Praise> mDatas,
			int isPraiseOrTrample) {
		this.context = context;
		this.mDatas = mDatas;
		this.isPraiseOrTrample = isPraiseOrTrample;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_praise,
				null);
		ViewUtil.scaleContentView((LinearLayout) view);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = view;
			viewHolder = new ViewHolder();
			viewHolder.avatar = (ImageView) convertView
					.findViewById(R.id.iv_praise_avatar);
			viewHolder.nickname = (TextView) convertView
					.findViewById(R.id.tv_praise_nickname);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.tv_praise_time);
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.iv_praise_icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// Picasso.with(context).load(mDatas.get(position).getAvatar()).resize(82,
		// 82).centerCrop().into(viewHolder.avatar);
		ImageLoaderHelper.mImageLoader.displayImage(mDatas.get(position)
				.getAvatar(), viewHolder.avatar, ImageLoaderHelper.mOptions);
		viewHolder.nickname.setText(mDatas.get(position).getNickname());
		viewHolder.time.setText(mDatas.get(position).getTime());
		if (isPraiseOrTrample == PRAISE) {
			viewHolder.icon.setImageResource(R.drawable.praise_heart);
		}
		if (isPraiseOrTrample == TRAMPLE) {
			viewHolder.icon.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Praise getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		ImageView avatar;
		TextView nickname;
		TextView time;
		ImageView icon;
	}

}
