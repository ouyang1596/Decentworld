/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import cn.sx.decentworld.R;
import cn.sx.decentworld.entity.db.CommentEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @ClassName: MomentCommentAdapter.java
 * @Description: 作品圈评论列表
 * @author: cj
 * @date: 2016年4月15日 下午3:11:01
 */
public class MomentCommentAdapter extends BaseAdapter
{
    //评论列表数据
    private List<CommentEntity> mCommentList;
    //上下文对象
    private Context context;
    //布局填充器
    private LayoutInflater inflater;
    
    /**
     * 构造函数
     */
    public MomentCommentAdapter(Context context,List<CommentEntity> mCommentList)
    {
        this.context = context;
        this.mCommentList = mCommentList;
        this.inflater = LayoutInflater.from(context);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_moment_comment, null);
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_moment_comment_name);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_moment_comment_context);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        CommentEntity commentEntity = getItem(position);
        holder.mTvName.setText(commentEntity.getReply());
        holder.mTvContent.setText(commentEntity.getContent());
        return convertView;
    }

    @Override
    public int getCount()
    {
        return mCommentList.size();
    }

    @Override
    public CommentEntity getItem(int position)
    {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    class ViewHolder
    {
        TextView mTvName;
        TextView mTvContent;
    }

}
