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
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: WorkGridViewAdapter.java
 * @Description: 作品圈gridview的适配器
 * @author: yj
 * @date: 2015年7月20日 下午7:51:49
 */

public class WorkGridViewAdapter extends BaseAdapter {

	private Context context;
	private List<String> urls;

	public WorkGridViewAdapter(Context context, List<String> urls) {
		this.context = context;
		this.urls = urls;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_work_gridview, null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.item_work_gridview_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// Picasso.with(context).load(urls.get(position)).config(Config.RGB_565).placeholder(R.drawable.work_default).resize((int)(Constants.screenWidth*0.3055),(int)(Constants.screenWidth*0.3055)).centerCrop().into(holder.imageView);
		ImageLoaderHelper.mImageLoader.displayImage(urls.get(position),
				holder.imageView, ImageLoaderHelper.mOptions);
		ViewUtil.scaleView(holder.imageView);
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
	}

	@Override
	public int getCount() {
		return urls.size();
	}

	@Override
	public Object getItem(int position) {
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
