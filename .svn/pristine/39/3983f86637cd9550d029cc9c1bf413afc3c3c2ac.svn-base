/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.GroupInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @ClassName: InterestingGroupAdapter.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月24日 下午4:28:57
 */
public class InterestingGroupAdapter extends BaseAdapter {
	private Context context;
	private List<GroupInfo> list;
	private LayoutInflater inflater;

	public InterestingGroupAdapter(Context context, List<GroupInfo> list2) {
		super();
		this.context = context;
		this.list = list2;
		inflater = LayoutInflater.from(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView =inflater.inflate(
					R.layout.activity_interesting_group_item, null);
			holder.interesting_group_icon = (ImageView) convertView
					.findViewById(R.id.iv_inter_group_icon);
			holder.interesting_group_title = (TextView) convertView
					.findViewById(R.id.tv_inter_group_name);
			holder.interesting_group_info = (TextView) convertView
					.findViewById(R.id.tv_inter_group_info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.interesting_group_icon.setImageResource(list.get(position)
				.getAvatar());
		holder.interesting_group_title.setText(list.get(position)
				.getGroupname()
				+ "("
				+ list.get(position).getGroup_people()
				+ "/" + list.get(position).getGroup_capacity() + ")");
		holder.interesting_group_info.setText(list.get(position).getGroup_info());
		return convertView;
	}

	private static class ViewHolder {
		ImageView interesting_group_icon;
		TextView interesting_group_title;
		TextView interesting_group_info;
	}

}
