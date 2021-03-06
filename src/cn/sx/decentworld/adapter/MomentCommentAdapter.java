/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.io.Serializable;
import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.discover.MomentActivity;
import cn.sx.decentworld.activity.discover.MomentActivity.MomentMenu;
import cn.sx.decentworld.adapter.MomentAdapter.CommentListener;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.CommentEntity;
import cn.sx.decentworld.fragment.main.ContactFragment.ContactMenu;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.ToastUtil;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: MomentCommentAdapter.java
 * @Description: 作品圈评论列表
 * @author: cj
 * @date: 2016年4月15日 下午3:11:01
 */
public class MomentCommentAdapter extends BaseAdapter
{
    private static final String TAG = "MainActivity";
    // 评论列表数据
    private List<CommentEntity> mCommentList;
    // 上下文对象
    private Context context;
    // 布局填充器
    private LayoutInflater inflater;
    private CommentListener listener;
    private String userID;

    /**
     * 构造函数
     */
    public MomentCommentAdapter(Context context, List<CommentEntity> mCommentList, CommentListener listener)
    {
        this.context = context;
        this.mCommentList = mCommentList;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.userID = DecentWorldApp.getInstance().getDwID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final CommentEntity commentEntity = getItem(position);
        String dwID = commentEntity.getDwID();
        String reply = commentEntity.getReply();
        String content = commentEntity.getContent();
        final ViewHolder holder = new ViewHolder();
        if (CommUtil.isBlank(reply))
        {
            // 评论朋友圈
            convertView = inflater.inflate(R.layout.item_moment_comment, null);
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_moment_comment_name);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_moment_comment_content);
            holder.mTvTime = (TextView) convertView.findViewById(R.id.tv_moment_comment_time);
        }
        else
        {
            // 回复评论
            convertView = inflater.inflate(R.layout.item_moment_reply, null);
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_moment_reply_name);
            holder.mTvName2 = (TextView) convertView.findViewById(R.id.tv_moment_reply_name2);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_moment_reply_content);
            holder.mTvTime = (TextView) convertView.findViewById(R.id.tv_moment_reply_time);
            holder.mTvName2.setText(getReplyName(commentEntity));
            holder.mTvName2.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "开发中[被回复者]", Toast.LENGTH_SHORT).show();
                }
            });
        }
        // 提取回复者的名字
        holder.mTvName.setText(getName(commentEntity));
        holder.mTvName.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "开发中[回复者]", Toast.LENGTH_SHORT).show();
            }
        });
        String time = TimeUtils.toMsgFormat(commentEntity.getTime());
        holder.mTvTime.setText(time);
        holder.mTvContent.setText(commentEntity.getContent());
        convertView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String dwID3 = commentEntity.getDwID();
                LogUtils.v(TAG, "dwID3=" + dwID3 + ",userID=" + userID);
                if (CommUtil.isNotBlank(dwID3) && !dwID3.equals(userID))
                {
                    MomentActivity.openReply(commentEntity, holder);
                    listener.open();
                }
                else
                {
                    Toast.makeText(context, "不能回复自己", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 设置删除事件(如果评论发布者是自己，则可以删除)
        if (dwID.equals(userID))
        {
            convertView.setOnCreateContextMenuListener(new OnCreateContextMenuListener()
            {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
                {
                    menu.add(Menu.NONE, MomentMenu.DELETE_COMMENT.ordinal(), Menu.NONE, "删除").setIntent(new Intent().putExtra("commentEntity", (Serializable) commentEntity));
                }
            });
        }
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

    public class ViewHolder
    {
        View mContainer;
        TextView mTvName;
        TextView mTvName2;
        TextView mTvContent;
        TextView mTvTime;
    }

    /**
     * 返回评论者名字
     */
    private String getName(CommentEntity commentEntity)
    {
        boolean anonymous = commentEntity.isAnonymous();
        if (anonymous)
        {
            return commentEntity.getPublisherName();
        }
        else
        {
            String dwID = commentEntity.getDwID();
            if (dwID.equals(DecentWorldApp.getInstance().getDwID()))
            {
                return SelfInfoManager.getInstance().getShowName();
            }
            else
            {
                return ContactUserDao.getName(dwID);
            }
        }
    }

    /**
     * 返回回复者的名字
     */
    private String getReplyName(CommentEntity commentEntity)
    {
        boolean replyAnonymous = commentEntity.isReplyAnonymous();
        if (replyAnonymous)
        {
            return commentEntity.getReplyName();
        }
        else
        {
            String reply = commentEntity.getReply();
            if (reply.equals(DecentWorldApp.getInstance().getDwID()))
            {
                return SelfInfoManager.getInstance().getShowName();
            }
            else
            {
                return ContactUserDao.getName(reply);
            }
        }
    }

}
