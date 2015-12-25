/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatSettingWhistleblowingActivity_;
import cn.sx.decentworld.activity.ImagePagerActivity;
import cn.sx.decentworld.activity.WorkCircleCommentActivity_;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.bean.WorkBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ViewUtil;
import cn.sx.decentworld.widget.ExpandGridView;

/**
 * @ClassName: WorkAdapter.java
 * @Description:
 * @author: yj
 * @date: 2015年7月20日 下午1:36:24
 */

public class WorkAdapter extends BaseAdapter {

	private Context context;
	private List<WorkBean> urls;

	private List<Praise> praises;
	private List<Praise> tramples;

	// more
	private PopupWindow popWindow = null;

	private View vPopWindow;
	private int[] arrayOfInt = new int[2];// 控件的左上角x、y坐标
	private int[] xy = new int[2];// 屏幕的高、宽
	private boolean ifshow = true;
	private int mposition = 0;

	/**
	 * 
	 */
	public WorkAdapter(Context context, List<WorkBean> urls) {
		this.context = context;
		this.urls = urls;

	}

	public WorkAdapter(Context context, List<WorkBean> urls, boolean ifshow) {
		this.context = context;
		this.urls = urls;
		this.ifshow = ifshow;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		mposition = position;
		final ViewHolder holder;
		WorkGridViewAdapter adapter = null;
		if (convertView == null) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.item_work, null);
			ViewUtil.scaleContentView((LinearLayout) view);
			holder = new ViewHolder();
			convertView = view;
			holder.iv_item_work_avatar = (ImageView) convertView
					.findViewById(R.id.iv_item_work_avatar);
			holder.tv_item_work_nickname = (TextView) convertView
					.findViewById(R.id.tv_item_work_nickname);
			holder.tv_item_work_time = (TextView) convertView
					.findViewById(R.id.tv_item_work_time);
			holder.tv_item_work_delete = (TextView) convertView
					.findViewById(R.id.tv_item_work_delete);
			holder.tv_item_work_description = (TextView) convertView
					.findViewById(R.id.tv_item_work_description);
			holder.expandGridView = (ExpandGridView) convertView
					.findViewById(R.id.gv_item_work_picture);

			holder.ll_item_work_report = (LinearLayout) convertView
					.findViewById(R.id.ll_item_work_report);
			holder.ll_item_work_good = (LinearLayout) convertView
					.findViewById(R.id.ll_item_work_good);
			holder.ll_item_work_bad = (LinearLayout) convertView
					.findViewById(R.id.ll_item_work_bad);
			holder.ll_item_work_comment = (LinearLayout) convertView
					.findViewById(R.id.ll_item_work_comment);

			holder.tv_item_work_good = (TextView) convertView
					.findViewById(R.id.tv_item_work_good);
			holder.tv_item_work_bad = (TextView) convertView
					.findViewById(R.id.tv_item_work_bad);
			holder.ll_item_work_refer_2 = (LinearLayout) convertView
					.findViewById(R.id.ll_item_work_refer_2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final WorkBean url = urls.get(position);

		// 设置头像的点击事件
		holder.iv_item_work_avatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent;
				// 如果是自己，则进入自己的设置界面
				// if
				// (urls.get(position).getNickname()==DecentWorldApp.getInstance().getUserName())
				// {
				// // Log.i("workadapter", url.getNickname()+ "   " +
				// DecentWorldApp.getInstance().getUserName());
				// //
				// holder.tv_item_work_delete.setOnClickListener(clickListener);
				// //处理跳转事件
				// ToastComponent_.getInstance_(context).show("待处理的跳转事件");
				//
				// }
				// else//如果是比人，则进入别人的设置界面
				// {
				// intent = new Intent(context, FriendDetailActivity_.class);
				// intent.putExtra("userId", urls.get(position).getNickname());
				// context.startActivity(intent);
				// }
			}
		});

		// Picasso.with(context).load(url.getAvatar()).resize(82,
		// 82).centerCrop().into(holder.iv_item_work_avatar);
		ImageLoaderHelper.mImageLoader.displayImage(url.getAvatar(),
				holder.iv_item_work_avatar, ImageLoaderHelper.mOptions);
		holder.tv_item_work_nickname.setText(url.getNickname());
		holder.tv_item_work_time.setText(url.getTime());
		holder.tv_item_work_description.setText(url.getDescription());

		// 相册
		final ArrayList<String> u = (ArrayList<String>) url.getUrls();
		holder.expandGridView.setAdapter(new WorkGridViewAdapter(context, u));
		holder.expandGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				imageBrower(position, u);
			}
		});

		ClickListener clickListener = new ClickListener(holder, position);
		holder.ll_item_work_report.setOnClickListener(clickListener);
		holder.tv_item_work_good.setText("" + urls.get(position).getGood());
		holder.ll_item_work_good.setOnClickListener(clickListener);
		holder.tv_item_work_bad.setText("" + urls.get(position).getBad());
		holder.ll_item_work_bad.setOnClickListener(clickListener);
		holder.ll_item_work_comment.setOnClickListener(clickListener);

		// 如果是自己发表的作品圈，则显示删除按钮，否则不显示
		// if
		// (urls.get(position).getNickname()==DecentWorldApp.getInstance().getUserName())
		// {
		// Log.i("workadapter", url.getNickname()+ "   " +
		// DecentWorldApp.getInstance().getUserName());
		// holder.tv_item_work_delete.setOnClickListener(clickListener);
		// }
		// else
		// {
		// holder.tv_item_work_delete.setVisibility(View.GONE);
		// }

		return convertView;
	}

	@Override
	public int getCount() {
		return urls.size();
	}

	@Override
	public Object getItem(int position) {
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int[] getWindowDispalyMetrics() {
		int[] xy = new int[2];
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		xy[0] = dm.widthPixels; // 屏幕宽度（像素）
		xy[1] = dm.heightPixels; // 屏幕高度（像素）
		return xy;
	}

	class ClickListener implements OnClickListener {
		private ViewHolder holder;
		private int position;

		public ClickListener(ViewHolder holder, int position) {
			this.holder = holder;
			this.position = position;
		}

		/**
		 * 处理 赞、踩、评论、举报的点击事件
		 */
		@Override
		public void onClick(View v) {
			Intent intent;
			int count;
			switch (v.getId()) {
			case R.id.tv_item_work_delete:
				urls.remove(position);
				notifyDataSetChanged();
				Toast.makeText(context, "删除 Iiem " + position + " 成功",
						Toast.LENGTH_SHORT).show();
				break;

			case R.id.ll_item_work_report:
				intent = new Intent(context,
						ChatSettingWhistleblowingActivity_.class);
				context.startActivity(intent);
				Toast.makeText(context, "举报", Toast.LENGTH_SHORT).show();
				break;

			case R.id.ll_item_work_good:
				// if
				// (urls.get(position).getNickname()==DecentWorldApp.getInstance().getUserName())
				// {
				// intent = new Intent(context, PraiseActivity_.class);
				// intent.putExtra("isPraiseOrTrample", 1);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("praise",
				// urls.get(position).getPraises());
				// intent.putExtras(bundle);
				// context.startActivity(intent);
				// }
				// else
				// {
				// count =
				// Integer.valueOf(holder.tv_item_work_good.getText().toString())
				// + 1;
				// holder.tv_item_work_good.setText(count + "");
				// urls.get(position).setGood(count);
				// }
				break;

			case R.id.ll_item_work_bad:
				// if
				// (urls.get(position).getNickname()==DecentWorldApp.getInstance().getUserName())
				// {
				// intent = new Intent(context, PraiseActivity_.class);
				// intent.putExtra("isPraiseOrTrample", 2);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("trample",
				// urls.get(position).getTramples());
				// intent.putExtras(bundle);
				// context.startActivity(intent);
				// }
				// else
				// {
				// count =
				// Integer.valueOf(holder.tv_item_work_bad.getText().toString())
				// + 1;
				// holder.tv_item_work_bad.setText(count + "");
				// urls.get(position).setBad(count);
				// }
				break;

			case R.id.ll_item_work_comment:
				intent = new Intent(context, WorkCircleCommentActivity_.class);
				intent.putExtra("item", position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("comment", urls.get(position)
						.getComments());
				intent.putExtras(bundle);
				context.startActivity(intent);
				break;

			default:
				break;
			}
		}
	}

	private static class ViewHolder {
		ExpandGridView expandGridView;
		// ListViewForScrollView listViewForScrollView;

		ImageView iv_item_work_avatar;
		TextView tv_item_work_nickname;
		TextView tv_item_work_time;
		TextView tv_item_work_delete;
		TextView tv_item_work_description;

		LinearLayout ll_item_work_report;
		LinearLayout ll_item_work_good;
		LinearLayout ll_item_work_bad;
		LinearLayout ll_item_work_comment;

		TextView tv_item_work_good;
		TextView tv_item_work_bad;

		RelativeLayout rl_item_work_to_hide;
		LinearLayout ll_item_work_refer_2;
	}

	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

}
