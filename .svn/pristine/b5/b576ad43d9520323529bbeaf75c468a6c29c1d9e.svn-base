package cn.sx.decentworld.adapter;


import cn.sx.decentworld.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatSelectBgAdapter extends BaseAdapter
{
	private Context context;
	private int res;
	private int[] pictures;

	public ChatSelectBgAdapter(Context context, int res, int[] pictures)
	{
		super();
		this.context = context;
		this.res = res;
		this.pictures = pictures;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(res, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.chat_setting_bg_avatar);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.imageView.setImageResource(pictures[position]);
		return convertView;
	}

	private static class ViewHolder
	{
		ImageView imageView;
		TextView textView;
	}

	@Override
	public Object getItem(int position)
	{
		return pictures[position];
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public int getCount()
	{
		return pictures.length;
	}
}
