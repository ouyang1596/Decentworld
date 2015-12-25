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
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.Comment;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: WorkCommentAdapter.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月23日 上午9:44:07
 */
public class WorkCommentAdapter extends BaseAdapter
{
	private Context context;
	private int res;
	private List<Comment> comments;

	public WorkCommentAdapter(Context context,int res,List<Comment> comments)
	{
		this.context = context;
		this.res = res;
		this.comments = comments;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = LayoutInflater.from(context).inflate(res, null);
		ViewUtil.scaleContentView((LinearLayout)view);
		ViewHolder viewHolder;
		if (convertView==null)
		{
			viewHolder = new ViewHolder();
			convertView = view;
			viewHolder.name = (TextView) convertView.findViewById(R.id.item_work_comment_name);
			viewHolder.replyname = (TextView) convertView.findViewById(R.id.item_work_comment_reply_name);
			viewHolder.commenTextView = (TextView) convertView.findViewById(R.id.item_work_comment_tv);
			viewHolder.reply = (LinearLayout) convertView.findViewById(R.id.item_work_comment_reply);
			viewHolder.data = (TextView) convertView.findViewById(R.id.item_work_comment_data);
			convertView.setTag(viewHolder);
		}
		else 
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(comments.get(position).isC_isreply())
		{
			viewHolder.reply.setVisibility(View.VISIBLE);
			viewHolder.replyname.setText(comments.get(position).getC_reply_name());
		}
		
		viewHolder.name.setText(comments.get(position).getC_name());
		viewHolder.commenTextView.setText(comments.get(position).getC_content().trim());
		viewHolder.data.setText(comments.get(position).getC_date());
		return convertView;
	}

	@Override
	public int getCount()
	{
		return comments.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}
	
	private static class ViewHolder 
	{
		LinearLayout reply;
		TextView commenTextView;
		TextView name;
		TextView replyname;
		TextView replytv;
		TextView data;
	}
}
