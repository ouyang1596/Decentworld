package cn.sx.decentworld.adapter;

import java.util.List;

import cn.sx.decentworld.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupGridAdapter extends BaseAdapter {
	private Context context;
	private int res;
	public boolean isInDeleteMode;
	private List<String> objects;
	ImageView image_add = null;
	ImageView image_delete = null;
	public boolean candelete = false;
	private Handler handler;

	public GroupGridAdapter(Context context, int res, boolean isInDeleteMode,
			List<String> objects, Handler handler) {
		super();
		this.context = context;
		this.res = res;
		this.isInDeleteMode = isInDeleteMode;
		this.objects = objects;
		this.handler = handler;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(res, null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
			holder.icon_delete = (ImageView) convertView
					.findViewById(R.id.badge_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == getCount() - 1) {
			holder.icon_delete.setVisibility(View.GONE);
			holder.imageView.setImageResource(R.drawable.icon_delete);
			holder.textView.setVisibility(View.INVISIBLE);
			image_delete=(ImageView) convertView.findViewById(R.id.iv_avatar);
			image_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!candelete) {
						candelete = true;
						notifyDataSetChanged();
					} else {
						candelete = false;
						notifyDataSetChanged();
					}
				}
			});
		} else if (position == getCount() - 2) {
			image_add = (ImageView) convertView.findViewById(R.id.iv_avatar);
			holder.icon_delete.setImageResource(View.GONE);
			holder.imageView.setImageResource(R.drawable.add_rectangle);
			holder.textView.setVisibility(View.INVISIBLE);
//			holder.imageView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
		} else {
			if (candelete) {
				holder.imageView.setImageResource(R.drawable.default_avatar);
				holder.textView.setText(objects.get(position));
				if(position!=0)
					holder.icon_delete.setVisibility(View.VISIBLE);
				else{
					holder.icon_delete.setVisibility(View.GONE);
				}
				holder.icon_delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Message msg = new Message();
						msg.what=2;
						msg.obj = position;
						handler.sendMessage(msg);
					}
				});
				
			} else {
				holder.imageView.setImageResource(R.drawable.default_avatar);
				holder.textView.setText(objects.get(position));
				holder.icon_delete.setVisibility(View.GONE);
			}
		}
		if(position==getCount()-1||position == getCount()-2){
			holder.icon_delete.setVisibility(View.GONE);
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView textView;
		ImageView icon_delete;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size() + 2;
	}
}
