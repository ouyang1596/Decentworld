/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RecommendBenefitDetail;
import cn.sx.decentworld.utils.TimeUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: RecommendBenefitDetailAdapter.java
 * @Description: 推荐获取收益详细列表适配器
 * @author: cj
 * @date: 2015年12月21日 上午11:30:45
 */
public class RecommendBenefitDetailAdapter extends BaseAdapter
{
	private static final String TAG = "RecommendBenefitDetailAdapter";
	private Context context;
	private LayoutInflater inflater;
	private List<RecommendBenefitDetail> mData;

	public RecommendBenefitDetailAdapter(Context context, List<RecommendBenefitDetail> mData)
	{
		this.context = context;
		this.mData = mData;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		return mData.size();
	}

	@Override
	public RecommendBenefitDetail getItem(int position)
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
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_recommend_benefit_detail, null);
			holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_recommend_benefit_detail_amount);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_recommend_benefit_detail_time);
			holder.iv_status = (ImageView) convertView.findViewById(R.id.iv_recommend_benefit_status);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		RecommendBenefitDetail benefitDetail = mData.get(position);
		holder.tv_amount.setText(String.valueOf(benefitDetail.getAmount()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date(Long.valueOf(benefitDetail.getTime())));
		holder.tv_time.setText(time);
		TimeUtils.currentTimeFormate();
		
		if(benefitDetail.isStatus())
		{
			holder.iv_status.setImageResource(R.drawable.switch_circle_open);
		}
		else
		{
			holder.iv_status.setImageResource(R.drawable.switch_circle_close);
		}
		return convertView;
	}
	
	class ViewHolder
	{
		TextView tv_amount;
		TextView tv_time;
		ImageView iv_status;
	}
}
