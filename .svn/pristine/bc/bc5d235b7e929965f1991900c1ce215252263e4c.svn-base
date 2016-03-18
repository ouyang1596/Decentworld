/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;


import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.GroupMember;
import cn.sx.decentworld.utils.ViewUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: InterestingGroupGridAdapter.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月27日 下午2:52:52
 */
public class InterestingGroupGridAdapter extends BaseAdapter {
	public Context context;
	public List<GroupMember> list;
	public LayoutInflater inflater;
	
	public InterestingGroupGridAdapter(Context context, List<GroupMember> list) {
		super();
		this.context = context;
		this.list = list;
		inflater=LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.activity_interesting_member_item,null);
			ViewUtil.scaleContentView((RelativeLayout)convertView);
			holder.name=(TextView) convertView.findViewById(R.id.tv_i_g_name);
			holder.icon=(ImageView) convertView.findViewById(R.id.iv_i_g_icon);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.name.setText(list.get(position).getName());
		holder.icon.setImageResource(list.get(position).getIcon());
		return convertView;
	}
	
	private  static class ViewHolder{
		TextView name;
		ImageView icon;
	}

}
