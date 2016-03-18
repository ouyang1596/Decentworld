/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RecommendUnavaliable;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: RecommendUnavaliableAdapter.java
 * @Description:
 * @author: cj
 * @date: 2016年1月28日 下午8:46:22
 */
public class RecommendUnavaliableAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<RecommendUnavaliable> mData;

    /**
     * @param context
     * @param inflater
     * @param mData
     */
    public RecommendUnavaliableAdapter(Context context, List<RecommendUnavaliable> mData)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.item_recommend_unavaliable, null);
            holder.tvPhoneNum = (TextView) convertView.findViewById(R.id.tv_item_recommend_unavaliable_phoneNum);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_item_recommend_unavaliable_money);
            holder.ivStatus = (ImageView) convertView.findViewById(R.id.iv_item_recommend_unavaliable_status);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_item_recommend_unavaliable_status);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        RecommendUnavaliable unavaliable = getItem(position);
        holder.tvPhoneNum.setText(unavaliable.getPhoneNum());
        holder.tvMoney.setText(unavaliable.getAmount() + "");
        int status = unavaliable.getStatus();
        if (status == RecommendUnavaliable.STATUS_UN_REGISTER)
        {
            // 还没有注册
            holder.ivStatus.setVisibility(View.GONE);
            holder.tvStatus.setText("未注册");
        }
        else if (status == RecommendUnavaliable.STATUS_UN_FRIEND)
        {
            // 已经注册，但是没有加为朋友
            holder.ivStatus.setVisibility(View.GONE);
            holder.tvStatus.setText("未加为朋友");
        }
        else
        {
            // 未知状态
        }
        return convertView;
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public RecommendUnavaliable getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    class ViewHolder
    {
        TextView tvPhoneNum;
        TextView tvMoney;
        ImageView ivStatus;
        TextView tvStatus;
    }

}
