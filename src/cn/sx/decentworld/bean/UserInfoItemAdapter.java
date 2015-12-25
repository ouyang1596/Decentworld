/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.dialog.EditAndModificationDialog;
import cn.sx.decentworld.dialog.EditAndModificationDialog.EditAndModificationListener;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: UserInfoItemAdapter.java
 * @Description:
 * @author: cj
 * @date: 2015年10月11日 上午10:19:55
 */
public class UserInfoItemAdapter extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<UserInfoItem> mDatas;
	private FragmentManager fm;
	
	private boolean isEdit = false;

	/**
	 * 
	 */
	public UserInfoItemAdapter(FragmentManager fm,Context context, List<UserInfoItem> mDatas)
	{
		this.context = context;
		this.mDatas = mDatas;
		inflater = LayoutInflater.from(context);
		this.fm = fm;
	}
	
	
	public UserInfoItemAdapter(FragmentManager fm,Context context, List<UserInfoItem> mDatas,boolean isEdit)
	{
		this.context = context;
		this.mDatas = mDatas;
		inflater = LayoutInflater.from(context);
		this.fm = fm;
		this.isEdit = isEdit;
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
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.activity_edit_user_info_item, null);

			holder.ll_title = (LinearLayout) convertView.findViewById(R.id.ll_edit_user_info_item_title);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_title);
			holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_edit_user_info_item_content);
			holder.tv_key = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_key);
			holder.tv_value = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_value);
			holder.iv_more = (ImageView) convertView.findViewById(R.id.iv_edit_user_info_item_more);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final UserInfoItem infoItem = mDatas.get(position);
		
		 if((position == 0)||(position == 4)||(position == 10))
		 {
			 holder.tv_title.setVisibility(View.VISIBLE);
			 holder.tv_title.setText(infoItem.getTitle());
		 }
		 else
		 {
			 holder.tv_title.setVisibility(View.GONE);
		 }

		holder.tv_key.setText(infoItem.getKey());
		holder.tv_value.setText(infoItem.getValue());

		if(isEdit)
		{
			//如果是编辑页面，则出现more图标，Item可以点击
			holder.iv_more.setVisibility(View.VISIBLE);
			holder.rl_content.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					final EditAndModificationDialog editAndModificationDialog = new EditAndModificationDialog();
					editAndModificationDialog.setTitle("修改 "+infoItem.getKey());
					editAndModificationDialog.setListener(new EditAndModificationListener()
					{
						@Override
						public void confirm(String info)
						{
							if(!CommUtil.isBlank(info))
							{
								mDatas.get(position).setValue(info);
								notifyDataSetChanged();
							}
							else
							{
								Toast.makeText(context, "请输入有效信息", Toast.LENGTH_SHORT).show();
							}
							
						}
					});

					editAndModificationDialog.show(fm, "editUserInfo");
				}
			});
		}
		else
		{
			//如果是不是编辑页面，只是信息展示界面，则隐藏more图标，Item不可以点击
			holder.iv_more.setVisibility(View.GONE);
		}
		

		return convertView;
	}

	class ViewHolder
	{
		LinearLayout ll_title;
		RelativeLayout rl_content;
		TextView tv_title;
		TextView tv_key;
		TextView tv_value;
		ImageView iv_more;
	}


}
