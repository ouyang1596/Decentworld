/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: GridViewAdapter.java
 * @Description: 图片列表
 * @author: cj
 * @date: 2016年2月23日 下午6:20:14
 */
public class GridImageAdapter extends BaseAdapter
{
    private static final String TAG = "GridImageAdapter";
    private Context context;
    private List<String> mData;
    
    /**
     * 构造函数
     */
    public GridImageAdapter(Context context,List<String> mData)
    {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public int getCount()
    {
        return mData.size();
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_view_image, null);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_item_grid_view_image);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        String bean = mData.get(position);
        if(bean.equals("0"))
            holder.ivImage.setImageResource(R.drawable.add_rectangle);
        else
        {
            String imagePath = mData.get(position);
            holder.ivImage.setImageURI(Uri.fromFile(new File(imagePath)));
        }
        return convertView;
    }
    
    /**
     * 
     * @ClassName: GridImageAdapter.java
     * @Description: 
     * @author: cj
     * @date: 2016年2月23日 下午7:48:51
     */
    class ViewHolder
    {
        ImageView ivImage;
    }

}
