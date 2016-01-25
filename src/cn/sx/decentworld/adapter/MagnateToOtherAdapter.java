/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.MagnateToOtherDetailActivity_;
import cn.sx.decentworld.bean.NoblePerson;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: MagnateToOtherAdapter.java
 * @Description: 我是谁的贵人的适配器
 * @author: cj
 * @date: 2015年7月28日 下午4:18:27
 */
public class MagnateToOtherAdapter extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<NoblePerson> mDatas;
	private FragmentManager fm;
	private ReminderDialog reminderDialog;

	/**
	 * 
	 */
	public MagnateToOtherAdapter(Context context, List<NoblePerson> mDatas, FragmentManager fm)
	{
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
		this.fm = fm;
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		View view = LayoutInflater.from(context).inflate(R.layout.item_magnate_to_other, null);
		ViewUtil.scaleContentView((LinearLayout) view);

		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = view;
			viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_magnate_to_other_item);
			viewHolder.avater = (ImageView) convertView.findViewById(R.id.iv_magnate_to_other_avatar);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_magnate_to_other_name);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		NoblePerson myProtege = mDatas.get(position);
		viewHolder.linearLayout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				reminderDialog = new ReminderDialog();
				reminderDialog.setListener(listener);
				reminderDialog.setInfo("查看明细需要支付虚拟币 10\n是否继续！");
				reminderDialog.setExtraParam(position);
				reminderDialog.show(fm, "");
			}
		});

		String icon = ImageUtils.getIconByDwID(myProtege.getOtherID(), ImageUtils.ICON_SMALL);
		if (CommUtil.isBlank(icon))
		{
			viewHolder.avater.setImageResource(R.drawable.ic_launcher);
		}
		else
		{
			ImageLoaderHelper.mImageLoader.displayImage(icon, viewHolder.avater, ImageLoaderHelper.mOptions);
		}
		viewHolder.name.setText(myProtege.getShowName());
		return convertView;
	}

	class ViewHolder
	{
		LinearLayout linearLayout;
		ImageView avater;
		TextView name;
	}

	ReminderListener listener = new ReminderListener()
	{
		@Override
		public void confirm()
		{
			int position = reminderDialog.getExtraParam();
			Intent intent = new Intent(context , MagnateToOtherDetailActivity_.class);
			intent.putExtra("otherID", mDatas.get(position).getOtherID());
			context.startActivity(intent);
		}
	};

}
