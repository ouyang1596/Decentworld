/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatSettingWhistleblowingActivity_;
import cn.sx.decentworld.activity.discover.MomentActivity;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.engine.MomentEngine;
import cn.sx.decentworld.entity.db.CommentEntity;
import cn.sx.decentworld.entity.db.CommentEntity.CommentType;
import cn.sx.decentworld.entity.db.MomentEntity;
import cn.sx.decentworld.listener.CommentCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.widget.ExpandGridView;
import cn.sx.decentworld.widget.ListViewForScrollView;

/**
 * 
 * @ClassName: FriendsCircleAdapter.java
 * @Description: 作品圈适配器
 * @author: cj
 * @date: 2016年2月27日 上午10:29:22
 */
public class MomentAdapter extends BaseAdapter
{
    private static final String TAG = "MomentAdapter";
    private Context context;
    private List<MomentEntity> momentEntities;
    private ClickChildItemCallback listener;

    /**
     * 构造函数
     */
    public MomentAdapter(Context context, List<MomentEntity> momentEntities, ClickChildItemCallback listener)
    {
        this.context = context;
        this.momentEntities = momentEntities;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moment, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_moment_icon);
            holder.tvPublisherName = (TextView) convertView.findViewById(R.id.tv_moment_publisherName);
            holder.tvPublisherTime = (TextView) convertView.findViewById(R.id.tv_moment_publishTime);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_moment_delete);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_moment_content);
            // 图片容器
            holder.llImgContainer = (LinearLayout) convertView.findViewById(R.id.ll_moment_img_container);
            holder.gvImgs = (ExpandGridView) convertView.findViewById(R.id.gv_moment_imgs);
            // 语音容器
            holder.llVoiceContainer = (LinearLayout) convertView.findViewById(R.id.ll_moment_voice_container);
            holder.tvVoicePath = (TextView) convertView.findViewById(R.id.tv_moment_voice_path);
            // 按钮容器
            holder.llBtnContainer = (LinearLayout) convertView.findViewById(R.id.ll_moment_btn_container);
            holder.llReport = (LinearLayout) convertView.findViewById(R.id.ll_moment_report);
            holder.llLike = (LinearLayout) convertView.findViewById(R.id.ll_moment_like);
            holder.llDislike = (LinearLayout) convertView.findViewById(R.id.ll_moment_dislike);
            holder.llComment = (LinearLayout) convertView.findViewById(R.id.ll_moment_comment);
            holder.tvLike = (TextView) convertView.findViewById(R.id.tv_moment_like);
            holder.tvDislike = (TextView) convertView.findViewById(R.id.tv_moment_dislike);
            holder.tvPublishStatus = (TextView) convertView.findViewById(R.id.tv_moment_publish_status);
            // 评论列表
            holder.mLvComment = (ListViewForScrollView) convertView.findViewById(R.id.lv_moment_comment);
            //是否显示评论列表
            holder.mTvCommentShow = (TextView) convertView.findViewById(R.id.tv_moment_comment_show);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final MomentEntity momentEntity = momentEntities.get(position);
        // 设置头像的点击事件
        holder.ivIcon.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ToastUtil.showToast("开发中 ...");
            }
        });

        // 设置发布者的头像
        String iconPath = ImageUtils.getIconByDwID(momentEntity.getPublisherID(), ImageUtils.ICON_SMALL);
        ImageLoaderHelper.mImageLoader.displayImage(iconPath, holder.ivIcon, ImageLoaderHelper.mOptions);
        // 设置发布者的名字
        holder.tvPublisherName.setText(momentEntity.getPublisherName());
        // 设置发布的时间
        String time = TimeUtils.toMsgFormat(momentEntity.getPublishTime());
        holder.tvPublisherTime.setText(time);
        // 是否显示删除按钮
        if (momentEntity.getPublisherID().equals(DecentWorldApp.getInstance().getDwID()))
        {
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvDelete.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ToastUtil.showToast("开发中...");
                }
            });
        }
        else
        {
            holder.tvDelete.setVisibility(View.GONE);
        }
        holder.tvContent.setText(momentEntity.getContent());
        // 根据发布类型选择不同布局
        if (momentEntity.getContentType() == DWMessage.TXT)
        {
            // 文字
            // 设置文字信息
            holder.llVoiceContainer.setVisibility(View.GONE);
            holder.llImgContainer.setVisibility(View.GONE);
        }
        else if (momentEntity.getContentType() == DWMessage.VOICE)
        {
            // 语音
            holder.llImgContainer.setVisibility(View.GONE);
            holder.llVoiceContainer.setVisibility(View.VISIBLE);
            if (CommUtil.isNotBlank(momentEntity.getLocalUrl()))
                holder.tvVoicePath.setText(momentEntity.getLocalUrl());
            else
                holder.tvVoicePath.setText(momentEntity.getRemoteUrl());
        }
        else if (momentEntity.getContentType() == DWMessage.IMAGE)
        {
            // 图片
            holder.llImgContainer.setVisibility(View.VISIBLE);
            holder.llVoiceContainer.setVisibility(View.GONE);
            final int imageType[] = new int[2];
            String imagePaths = "";
            if (CommUtil.isNotBlank(momentEntity.getLocalUrl()))
            {
                imageType[0] = ImageUtils.IMAGE_FROM_LOCAL;
                imagePaths = momentEntity.getLocalUrl();
            }
            else
            {
                imageType[0] = ImageUtils.IMAGE_FROM_NET;
                imagePaths = momentEntity.getRemoteUrl();
            }

            if (CommUtil.isNotBlank(imagePaths))
            {
                String[] u = imagePaths.split(";");
                final ArrayList<String> imageList;
                if (u != null && u.length > 0)
                {
                    imageList = new ArrayList<String>();
                    for (String str : u)
                    {
                        imageList.add(str);
                    }
                    holder.gvImgs.setAdapter(new MomentImgAdapter(context , imageList , imageType[0]));
                    holder.gvImgs.setOnItemClickListener(new OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            ImageUtils.browerBigImage(context, imageList, imageType[0], position);
                        }
                    });
                }
            }
        }
        else
        {
            // 其它类型，防止遗漏

        }

        // 发布状态
        holder.tvPublishStatus.setText("" + momentEntity.getMomentState());
        // 点击事件
        ClickListener clickListener = new ClickListener(holder , position);
        holder.llReport.setOnClickListener(clickListener);
        holder.tvLike.setText("" + momentEntity.getLikeCount());
        holder.llLike.setOnClickListener(clickListener);
        holder.tvDislike.setText("" + momentEntity.getDislikeCount());
        holder.llDislike.setOnClickListener(clickListener);
        holder.llComment.setOnClickListener(clickListener);
        // 显示评论
        holder.mLvComment.setAdapter(new MomentCommentAdapter(context ,momentEntity.getCommentList()));
        // 设置评论列表的显示状态
        holder.mTvCommentShow.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(holder.mTvCommentShow.getText().equals("隐藏评论"))
                {
                    holder.mTvCommentShow.setText("显示评论");
                    holder.mLvComment.setVisibility(View.GONE);
                }else
                {
                    holder.mTvCommentShow.setText("隐藏评论");
                    holder.mLvComment.setVisibility(View.VISIBLE);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getCount()
    {
        return momentEntities.size();
    }

    @Override
    public MomentEntity getItem(int position)
    {
        return momentEntities.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * @Description:点击事件
     */
    public class ClickListener implements OnClickListener
    {
        private ViewHolder holder;
        private int position;

        public ClickListener(ViewHolder holder, int position)
        {
            this.holder = holder;
            this.position = position;
        }

        /**
         * 处理 赞、踩、评论、举报的点击事件
         */
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_moment_delete:
                    listener.onDelete();
                    break;
                case R.id.ll_moment_report:
                    Intent intent = new Intent(context , ChatSettingWhistleblowingActivity_.class);
                    intent.putExtra("position", position);
                    ((Activity)context).startActivityForResult(intent, MomentActivity.REQUEST_CODE.REPORT.getIndex());
                    break;
                case R.id.ll_moment_like:
                    like(getItem(position), holder, CommentType.LIKE.getTypeValue(), "");
                    break;
                case R.id.ll_moment_dislike:
                    like(getItem(position), holder, CommentType.DISLIKE.getTypeValue(), "");
                    break;
                case R.id.ll_moment_comment:
                    listener.onComment();
                    like(getItem(position), holder, CommentType.NOMAL.getTypeValue(), getItem(position).getContent()+1);
                    break;
                default:
                    break;
            }
        }
    }

    public static class ViewHolder
    {
        // 发布者头像
        ImageView ivIcon;
        // 发布者名字
        TextView tvPublisherName;
        // 发布时间
        TextView tvPublisherTime;
        // 删除按钮
        TextView tvDelete;
        // 发布的文字
        TextView tvContent;
        // 语音显示控件
        LinearLayout llVoiceContainer;
        // 语音路径
        TextView tvVoicePath;
        // 图片显示容器
        LinearLayout llImgContainer;
        // 显示图片
        ExpandGridView gvImgs;
        // 按钮容器
        LinearLayout llBtnContainer;
        // 举报
        LinearLayout llReport;
        // 喜欢
        LinearLayout llLike;
        // 不喜欢
        LinearLayout llDislike;
        // 评论
        LinearLayout llComment;

        TextView tvLike;
        TextView tvDislike;
        // 发布状态
        TextView tvPublishStatus;

        // 评论列表
        ListViewForScrollView mLvComment;
        
        //是否显示评论列表
        TextView mTvCommentShow;
    }

    /** 点击 举报、踩、赞、评论 **/
    public interface ClickChildItemCallback
    {
        // 举报
        void onReport();

        // 喜欢
        void onLike(int position, MomentEntity entity, ViewHolder holder);

        // 不喜欢
        void onDisLike();

        // 评论
        void onComment();

        // 删除
        void onDelete();
    }

    /**
     * 
     */
    public void like(final MomentEntity momentEntity, final ViewHolder holder, final int commentType, String commentContent)
    {
        long momentID = momentEntity.getRemoteID();
        String publisherID = momentEntity.getPublisherID();
        String publisherName = momentEntity.getPublisherName();
        CommentEntity commentEntity = new CommentEntity(momentID , publisherID , publisherName , commentType , commentContent);
        new MomentEngine().comment(commentEntity, new CommentCallback()
        {
            @Override
            public void onSuccess(String info, CommentEntity commentEntity)
            {
                if (commentType == CommentType.LIKE.getTypeValue())
                {
                    // 喜欢成功
                    int count = Integer.valueOf(holder.tvLike.getText().toString()) + 1;
                    holder.tvLike.setText("" + count);

                    momentEntity.setLikeCount(count);
                    momentEntity.save();
                }
                else if (commentType == CommentType.DISLIKE.getTypeValue())
                {
                    // 喜欢成功
                    int count = Integer.valueOf(holder.tvDislike.getText().toString()) + 1;
                    holder.tvDislike.setText("" + count);
                    momentEntity.setDislikeCount(count);
                    momentEntity.save();
                }else if(commentType == CommentType.NOMAL.getTypeValue())
                {
                    LogUtils.i(TAG, "评论成功");
                    momentEntity.getCommentList().add(commentEntity);
                    MomentAdapter.this.notifyDataSetChanged();
                }
                else if(commentType == CommentType.REPORT.getTypeValue())
                {
                    LogUtils.i(TAG, "举报成功");
                    
                    
                }
            }

            @Override
            public void onFailure(String cause, CommentEntity commentEntity)
            {
                // 喜欢失败
                ToastUtil.showToast(cause);
            }
        });
    }
    
    /**
     * 举报
     * @param position 位置信息
     * @param info 举报理由
     */
    public void report(int position,String info)
    {
        like(getItem(position), null, CommentType.REPORT.getTypeValue(), info);
    }
}
