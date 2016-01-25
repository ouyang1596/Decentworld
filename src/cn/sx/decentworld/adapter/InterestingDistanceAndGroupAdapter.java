/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.GroupInfo;

/**
 * @ClassName: InterestingGroupAdapter.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月24日 下午4:12:04
 */
public class InterestingDistanceAndGroupAdapter extends BaseAdapter {
	private Context context;
	private List<String> list_group;
	private List<GroupInfo> list;
	private LayoutInflater inflater;
	private Handler handler;
	

	public InterestingDistanceAndGroupAdapter(Context context,
			List<String> list_group, List<GroupInfo> list, Handler handler) {
		super();
		this.context = context;
		this.list_group = list_group;
		this.list = list;
		this.handler=handler;
		inflater=LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_group.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_group.get(position);
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
			convertView=inflater.inflate(R.layout.activity_interesting_distance_item, null);
			holder.tv_distance=(TextView) convertView.findViewById(R.id.tv_distance_right);
			holder.lv_all_group=(ListView) convertView.findViewById(R.id.lv_all_distance_item_inter_group);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_distance.setText(list_group.get(position));
		List<GroupInfo> list_info=new ArrayList<GroupInfo>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getDistance().equals(list_group.get(position))){
				list_info.add(list.get(i));
			}
		}
		holder.lv_all_group.setDividerHeight(2);
		holder.lv_all_group.setAdapter(new InterestingGroupAdapter(context, list_info));
		holder.lv_all_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				
			}
		});
		
		return convertView;
	}
	private static class ViewHolder {
		TextView tv_distance;
		ListView lv_all_group;
	}


}
