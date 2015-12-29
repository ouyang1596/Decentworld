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
/**
 * 
 * @ClassName: ChatSettingGridAdapter.java
 * @Description: 聊天设置界面，好友有头像列表适配器
 * @author: cj
 * @date: 2015年12月27日 上午10:49:14
 */
public class ChatSettingGridAdapter extends BaseAdapter
{
	private Context context;
	private int res;
	private List<String[]> mData;

	public ChatSettingGridAdapter(Context context, int res, List<String[]> mData)
	{
		super();
		this.context = context;
		this.res = res;
		this.mData = mData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(res, null);
			holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == getCount() - 1)
		{
			holder.iv_avatar.setImageResource(R.drawable.add_rectangle);
			holder.tv_name.setText("");
		}
		else
		{
			String path = ImageUtils.getIconByDwID(mData.get(position)[0], ImageUtils.ICON_SMALL);
			if (!CommUtil.isBlank(path))
			{
				ImageLoaderHelper.mImageLoader.displayImage(path, holder.iv_avatar, ImageLoaderHelper.mOptions);
			}
			holder.tv_name.setText(mData.get(position)[1]);
		}
		return convertView;
	}

	private static class ViewHolder
	{
		ImageView iv_avatar;
		TextView tv_name;
	}

	@Override
	public Object getItem(int position)
	{
		return mData.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public int getCount()
	{
		return mData.size() + 1;
	}
}
