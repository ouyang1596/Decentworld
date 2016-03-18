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
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.MagnateToOtherDetailActivity_;
import cn.sx.decentworld.bean.NoblePerson;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.widget.CircularImage;

/**
 * @ClassName: GrIconAdapter.java
 * @Description: 贵人列表适配器
 * @author: cj
 * @date: 2016年1月15日 下午5:45:40
 */
public class GrListAdapter extends BaseAdapter
{
    private Context context;
    private List<NoblePerson> mDatas;
    private LayoutInflater inflater;
    private FragmentManager fm;
    private ReminderDialog reminderDialog;
    /**
     * 
     */
    public GrListAdapter(Context context,List<NoblePerson> mDatas, FragmentManager fm)
    {
        this.context = context;
        this.mDatas = mDatas;
        this.inflater = LayoutInflater.from(context);
        this.fm = fm;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder  = null;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gr_benefit_icon, null);
            holder.iv_icon =(CircularImage) convertView.findViewById(R.id.iv_item_gr_benefit_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_item_gr_benefit_name);
            holder.tv_worth = (TextView) convertView.findViewById(R.id.tv_item_gr_benefit_worth);
            holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_item_gr_benefit_detail);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        NoblePerson noblePerson = mDatas.get(position);
        String dwID = noblePerson.getOtherID();
        if(CommUtil.isNotBlank(dwID))
        {
            String icon = ImageUtils.getIconByDwID(dwID, ImageUtils.ICON_SMALL);
            ImageLoaderHelper.mImageLoader.displayImage(icon, holder.iv_icon);
        }
        else
        {
            holder.iv_icon.setImageResource(R.drawable.loading_pic);
        }
        holder.tv_name.setText(noblePerson.getShowName());
        holder.tv_worth.setText(noblePerson.getWorth()+"");
        holder.tv_detail.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reminderDialog = new ReminderDialog();
                reminderDialog.setListener(listener);
                reminderDialog.setInfo("查看他对您的贡献明细，需要花费300大洋\n是否继续？");
                reminderDialog.setExtraParam(position);
                reminderDialog.show(fm, "");
                
            }
        });
        return convertView;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }


    @Override
    public NoblePerson getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    class ViewHolder
    {
        CircularImage iv_icon;
        TextView tv_name;
        TextView tv_worth;
        TextView tv_detail;
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
