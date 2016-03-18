/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatSettingWhistleblowingActivity_;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.entity.db.MomentEntity;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.widget.ExpandGridView;

/**
 * 
 * @ClassName: FriendsCircleAdapter.java
 * @Description: 作品圈适配器
 * @author: cj
 * @date: 2016年2月27日 上午10:29:22
 */
public class FriendsCircleAdapter extends BaseAdapter
{
    private static final String TAG = "FriendsCircleAdapter";
    private Context context;
    private List<MomentEntity> momentEntities;
    // more
    private PopupWindow popWindow = null;

    private View vPopWindow;
    private int[] arrayOfInt = new int[2];// 控件的左上角x、y坐标
    private int[] xy = new int[2];// 屏幕的高、宽
    private boolean ifshow = true;
    private int mposition = 0;

    /**
     * 构造函数
     */
    public FriendsCircleAdapter(Context context, List<MomentEntity> momentEntities)
    {
        this.context = context;
        this.momentEntities = momentEntities;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friends_circle, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_friends_circle_icon);
            holder.tvPublisherName = (TextView) convertView.findViewById(R.id.tv_friends_circle_publisherName);
            holder.tvPublisherTime = (TextView) convertView.findViewById(R.id.tv_friends_circle_publisherTime);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_friends_circle_delete);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_friends_circle_content);
            // 图片容器
            holder.llImageContainer = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_image_container);
            holder.gvImage = (ExpandGridView) convertView.findViewById(R.id.gv_friends_circle_image);
            // 语音容器
            holder.llVoiceContainer = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_voice_container);
            holder.tvVoicePath = (TextView) convertView.findViewById(R.id.tv_friends_circle_voice);

            // 按钮容器
            holder.llBtnContainer = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_btn_container);

            holder.llReport = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_report);
            holder.llLike = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_like);
            holder.llDislike = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_dislike);
            holder.llComment = (LinearLayout) convertView.findViewById(R.id.ll_friends_circle_comment);

            holder.tvLike = (TextView) convertView.findViewById(R.id.tv_friends_circle_like);
            holder.tvDislike = (TextView) convertView.findViewById(R.id.tv_friends_circle_dislike);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final MomentEntity momentEntity = momentEntities.get(position);
        LogUtils.i(TAG, "position="+position+",contentType = "+momentEntity.getContentType());
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
        holder.tvPublisherTime.setText(TimeUtils.getFormatTime(momentEntity.getPublishTime(), TimeUtils.MM月dd日0HH11MM));
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
            holder.llImageContainer.setVisibility(View.GONE);
        }
        else if (momentEntity.getContentType() == DWMessage.VOICE)
        {
            // 语音
            holder.llImageContainer.setVisibility(View.GONE);
            holder.llVoiceContainer.setVisibility(View.VISIBLE);
            holder.tvVoicePath.setText(momentEntity.getRemoteUrl());
        }
        else if (momentEntity.getContentType() == DWMessage.IMAGE)
        {
            // 图片
            holder.llImageContainer.setVisibility(View.VISIBLE);
            holder.llVoiceContainer.setVisibility(View.GONE);
            
            String imagePaths = momentEntity.getRemoteUrl();
            String[] u = imagePaths.split(";");
            final ArrayList<String> imageList;
            if (u != null && u.length > 0)
            {
                imageList = new ArrayList<String>();
                for (String str : u)
                {
                    imageList.add(str);
                }
                holder.gvImage.setAdapter(new FriendsCircleImgAdapter(context , imageList));
                holder.gvImage.setOnItemClickListener(new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        ImageUtils.browerBigImage(context, imageList, ImageUtils.IMAGE_FROM_NET, position);
                    }
                });
            }
        }
        else
        {
            // 其它类型，防止遗漏

        }

        ClickListener clickListener = new ClickListener(holder , position);
        holder.llReport.setOnClickListener(clickListener);
        holder.tvLike.setText("" + momentEntity.getLikeCount());
        holder.llLike.setOnClickListener(clickListener);
        holder.tvDislike.setText("" + momentEntity.getDislikeCount());
        holder.llDislike.setOnClickListener(clickListener);
        holder.llComment.setOnClickListener(clickListener);
        return convertView;
    }

    @Override
    public int getCount()
    {
        return momentEntities.size();
    }

    @Override
    public Object getItem(int position)
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
    class ClickListener implements OnClickListener
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
            Intent intent;
            int count;
            switch (v.getId())
            {
                case R.id.tv_friends_circle_delete:
                    momentEntities.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "删除 Iiem " + position + " 成功", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.ll_friends_circle_report:
                    intent = new Intent(context , ChatSettingWhistleblowingActivity_.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "举报", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_friends_circle_like:

                    break;

                case R.id.ll_friends_circle_dislike:
                    break;

                case R.id.ll_friends_circle_comment:
                    // intent = new Intent(context ,
                    // WorkCircleCommentActivity_.class);
                    // intent.putExtra("item", position);
                    // Bundle bundle = new Bundle();
                    // bundle.putSerializable("comment",
                    // momentEntities.get(position));
                    // intent.putExtras(bundle);
                    // context.startActivity(intent);
                    break;

                default:
                    break;
            }
        }
    }

    private static class ViewHolder
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
        LinearLayout llImageContainer;
        // 显示图片
        ExpandGridView gvImage;
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

        // RelativeLayout rl_item_work_to_hide;

    }
}
