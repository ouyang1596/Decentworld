/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ToastUtils;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: ChatRoomImgListAdapter.java
 * @Description:
 * @author: cj
 * @date: 2015年9月21日 下午7:28:32
 */
public class ChatRoomImgListAdapter extends BaseAdapter {
	private Context context;
	private List<String> imageList;
	private LayoutInflater inflater;

	/**
	 * 
	 */
	public ChatRoomImgListAdapter(Context context, List<String> imageList) {
		this.context = context;
		this.imageList = imageList;
		this.inflater = LayoutInflater.from(context);
		ImageLoaderHelper.initImageLoader(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View view = null;
		if (convertView == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_chat_room_image_list, null);
			ViewUtil.scaleView(view);
			convertView = view;
			holder.image = (ImageView) convertView
					.findViewById(R.id.iv_item_chatroom_image_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (CommUtil.isNotBlank(imageList.get(position))) {
			String iconUrl = ImageUtils.getIconByDwID(imageList.get(position),
					ImageUtils.ICON_SMALL);
			// Picasso.with(context).load(iconUrl).resize(80,
			// 80).into(holder.image);
			ImageLoaderHelper.mImageLoader.displayImage(iconUrl, holder.image,
					ImageLoaderHelper.mOptions);
		}
		holder.image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtils.toast(context,
						"点击了头像，dwID= " + imageList.get(position));
				Intent intent = new Intent(context,
						NearCardDetailActivity_.class);
				intent.putExtra(Constants.DW_ID, imageList.get(position));
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	@Override
	public int getCount() {
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		ImageView image;
	}
}
