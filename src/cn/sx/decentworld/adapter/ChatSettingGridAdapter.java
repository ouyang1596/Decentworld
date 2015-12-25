package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

public class ChatSettingGridAdapter extends BaseAdapter {
	private Context context;
	private int res;
	private List<String> objects;

	public ChatSettingGridAdapter(Context context, int res, List<String> objects) {
		super();
		this.context = context;
		this.res = res;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(res, null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == getCount() - 1) {
			holder.imageView.setImageResource(R.drawable.add_rectangle);
			holder.textView.setText("");
		} else {
			String path = ImageUtils.getIconByDwID(objects.get(position),
					ImageUtils.ICON_SMALL);
			if (!CommUtil.isBlank(path)) {
				// Picasso.with(context).load(path).config(Config.RGB_565).placeholder(R.drawable.default_avatar).into(holder.imageView);
				ImageLoaderHelper.mImageLoader.displayImage(path,
						holder.imageView, ImageLoaderHelper.mOptions);
			}
			holder.textView.setText(ContactUser.queryByDwID(
					objects.get(position)).getNickName());
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView textView;
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
		return objects.size() + 1;
	}
}
