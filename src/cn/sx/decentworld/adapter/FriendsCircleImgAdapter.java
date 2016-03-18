/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ViewUtil;


/**
 * 
 * @ClassName: FriendsCircleImgAdapter.java
 * @Description: 朋友圈gridview的适配器
 * @author: cj
 * @date: 2016年2月27日 上午10:55:01
 */
public class FriendsCircleImgAdapter extends BaseAdapter
{
    /**
     * 变量
     */
    private Context context;
    private List<String> imageUrls;

    /**
     * 构造函数
     * @param context
     * @param imageUrls 图片路径
     */
    public FriendsCircleImgAdapter(Context context, List<String> imageUrls)
    {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_work_gridview, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_work_gridview_iv);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        //首先检测本地是否有数据，如果没有则从网络获取
        ImageLoaderHelper.mImageLoader.displayImage(imageUrls.get(position), holder.imageView, ImageLoaderHelper.mOptions);
        return convertView;
    }

    private static class ViewHolder
    {
        ImageView imageView;
    }

    @Override
    public int getCount()
    {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position)
    {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

}
