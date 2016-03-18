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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.RecommendBenefitDetailActivity_;
import cn.sx.decentworld.bean.RecommendBenefitList;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

/**
 * @ClassName: RecommendBenefitListAdapter.java
 * @Description:推荐收益列表
 * @author: cj
 * @date: 2015年12月20日 下午5:23:24
 */
public class RecommendBenefitListAdapter extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<RecommendBenefitList> mData;

	/**
	 * @param context
	 * @param inflater
	 */
	public RecommendBenefitListAdapter(Context context, List<RecommendBenefitList> mData)
	{
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mData = mData;
	}

	@Override
	public int getCount()
	{
		return mData.size();
	}

	@Override
	public RecommendBenefitList getItem(int position)
	{
		return mData.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		HolderView holder = null;
		if (convertView == null)
		{
			holder = new HolderView();
			convertView = inflater.from(context).inflate(R.layout.item_recommend_benefit_list, null);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_recom_benefit_icon);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_recom_benefit_name);
			holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_recom_benefit_detail);
			holder.pb_grogressBar = (ProgressBar) convertView.findViewById(R.id.pb_recom_benefit_progress);
			holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_recom_benefit_end);
			convertView.setTag(holder);
		}
		else
		{
			holder = (HolderView) convertView.getTag();
		}
		
		final RecommendBenefitList benefitList = mData.get(position);
		if(!benefitList.isRegister())//未注册
		{
			holder.iv_icon.setImageResource(R.drawable.default_avatar);
			holder.tv_name.setText(benefitList.getName());
			holder.tv_detail.setVisibility(View.GONE);
			holder.pb_grogressBar.setVisibility(View.GONE);
			holder.tv_amount.setVisibility(View.GONE);
		}
		else
		{
			String icon = ImageUtils.getIconByDwID(benefitList.getUserID(), ImageUtils.ICON_SMALL);
			ImageLoaderHelper.mImageLoader.displayImage(icon, holder.iv_icon);
			
			holder.tv_name.setText(benefitList.getName());
			holder.tv_detail.setVisibility(View.VISIBLE);
			int amount = (int) (benefitList.getAmount());
			holder.pb_grogressBar.setMax(amount);
			int benefit = (int) benefitList.getBenefit();
			holder.pb_grogressBar.setProgress(benefit);
			holder.tv_amount.setText(benefit+"/"+amount);
			holder.tv_detail.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Toast.makeText(context, "查看详情", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(context,RecommendBenefitDetailActivity_.class);
					intent.putExtra("otherID", benefitList.getOtherID());
					context.startActivity(intent);
				}
			});
		}

		
		
		return convertView;
	}

	class HolderView
	{
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_detail;
		ProgressBar pb_grogressBar;
		TextView tv_amount;
	}

}
