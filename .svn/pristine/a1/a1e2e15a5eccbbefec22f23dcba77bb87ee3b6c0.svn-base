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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.RecommendBenefitDetailActivity_;
import cn.sx.decentworld.bean.RecommendBenefitList;
import cn.sx.decentworld.common.CommUtil;
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
            holder.ll_root = (LinearLayout) convertView.findViewById(R.id.ll_recom_benefit);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_recom_benefit_name);
            holder.tv_phoneNum = (TextView) convertView.findViewById(R.id.tv_recom_benefit_phoneNum);
            holder.tv_back = (TextView) convertView.findViewById(R.id.tv_benefit_item_back);
            holder.tv_total = (TextView) convertView.findViewById(R.id.tv_benefit_item_total);
            holder.pb_grogressBar = (ProgressBar) convertView.findViewById(R.id.pb_recom_benefit_progress);
            convertView.setTag(holder);
        }
        else
        {
            holder = (HolderView) convertView.getTag();
        }

        final RecommendBenefitList benefitList = mData.get(position);

        holder.tv_name.setText(benefitList.getName());
        holder.tv_phoneNum.setText(benefitList.getPhoneNum());
        
        holder.tv_back.setText(""+benefitList.getBenefit());
        holder.tv_total.setText(""+benefitList.getAmount());
        
        int benefit = (int) benefitList.getBenefit();
        holder.pb_grogressBar.setProgress(benefit);

        int amount = (int) (benefitList.getAmount());
        holder.pb_grogressBar.setMax(amount);

        holder.ll_root.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "查看详情", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context , RecommendBenefitDetailActivity_.class);
                intent.putExtra("otherID", benefitList.getOtherID());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class HolderView
    {
        LinearLayout ll_root;
        TextView tv_name;
        TextView tv_phoneNum;
        TextView tv_back;
        ProgressBar pb_grogressBar;
        TextView tv_total;
    }

}
