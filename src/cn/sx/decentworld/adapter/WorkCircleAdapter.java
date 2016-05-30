/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ImagePagerActivity;
import cn.sx.decentworld.bean.CommentBean;
import cn.sx.decentworld.bean.DWException.OnExceptionListener;
import cn.sx.decentworld.bean.WorkBean;
import cn.sx.decentworld.bean.WorkComment;
import cn.sx.decentworld.bean.WorkMessage;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.FilePath;
import cn.sx.decentworld.common.MediaManager;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.widget.CircularImageView;
import cn.sx.decentworld.widget.GridViewForScrollView;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @ClassName: WorkCircleAdapter.java
 * @author: yj
 * @date: 2016年3月7日 下午3:05:29
 */
public class WorkCircleAdapter extends BaseAdapter implements OnCommomPromptListener {
	private static final String TAG = "WorkCircleAdapter";
	private Context mContext;
	private List<WorkMessage> mWorkBeans;
	private HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();// 判断点击的位置是否正在播放音乐
	private int playingPosition = -1;// 记录下正在播放的位置
	private static final String comment = "您的每次评论都需要花费一块大洋，请您慎重";
	private static final String zan = "您的每次点赞都需要花费一块大洋，请您慎重";
	private static final String cai = "您的每次踩都需要花费一块大洋，请您慎重";
	private static final String report = "您的每次踩都需要花费一块大洋，请您慎重";

	public int mPosition;

	public WorkCircleAdapter(Context context, List<WorkMessage> workBean) {
		mContext = context;
		mWorkBeans = workBean;
	}

	@Override
	public int getCount() {
		return null == mWorkBeans ? 0 : mWorkBeans.size();
	}

	@Override
	public WorkMessage getItem(int position) {
		return mWorkBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View con, ViewGroup parent) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.layout_work_circle_item, null);
			vh = new ViewHolder();
			vh.tvContent = (TextView) con.findViewById(R.id.tv_content);
			vh.tvName = (TextView) con.findViewById(R.id.tv_name);
			vh.ivAvatar = (CircularImageView) con.findViewById(R.id.iv_avatar);
			vh.tvCai = (TextView) con.findViewById(R.id.tv_cai);
			vh.tvCai.setOnClickListener(mOnClickListener);
			vh.tvTime = (TextView) con.findViewById(R.id.tv_time);
			vh.tvZan = (TextView) con.findViewById(R.id.tv_zan);
			vh.tvZan.setOnClickListener(mOnClickListener);
			vh.tvTime = (TextView) con.findViewById(R.id.tv_time);
			vh.lvComment = (ListViewForScrollView) con.findViewById(R.id.lv_comment);
			vh.commentAdapter = new CommentAdapter(mContext);
			vh.lvComment.setAdapter(vh.commentAdapter);
			vh.ivPlaySound = (ImageView) con.findViewById(R.id.iv_play_sound);
			// vh.ivPlaySound.setOnClickListener(mOnClickListener);
			vh.llAudio = (RelativeLayout) con.findViewById(R.id.ll_audio);
			vh.llAudio.setOnClickListener(mOnClickListener);
			vh.ivAudioCover = (ImageView) con.findViewById(R.id.iv_audio_cover);
			// vh.ivAudioCover.setOnClickListener(mOnClickListener);
			vh.tvDelete = (TextView) con.findViewById(R.id.tv_report);
			vh.tvComment = (TextView) con.findViewById(R.id.tv_comment);
			vh.tvComment.setOnClickListener(mOnClickListener);
			vh.tvReport = (TextView) con.findViewById(R.id.tv_report);
			vh.tvReport.setOnClickListener(mOnClickListener);
			vh.gvPic = (GridViewForScrollView) con.findViewById(R.id.gv_pic);
			vh.adapetr = new PicShowAdapter(mContext);
			vh.gvPic.setAdapter(vh.adapetr);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		WorkBean workBean = mWorkBeans.get(position).work;
		if (CommUtil.isBlank(workBean.content)) {
			vh.tvContent.setVisibility(View.GONE);
		} else {
			vh.tvContent.setVisibility(View.VISIBLE);
			vh.tvContent.setText(workBean.content);
		}
		vh.tvName.setText(workBean.publisherName);
		vh.ivPlaySound.setTag(position);
		vh.tvComment.setTag(position);
		vh.llAudio.setTag(position);
		vh.tvZan.setTag(position);
		vh.tvCai.setTag(position);
		vh.tvReport.setTag(position);
		vh.ivAudioCover.setTag(position);
		vh.lvComment.setTag(position);
		vh.lvComment.setOnItemClickListener(mOnItemClickListener);// OnItemClickListener写成一个对象老是会出错，此地方还需要优化一下
		vh.gvPic.setTag(position);
		vh.gvPic.setOnItemClickListener(mImagsItemClickListener);
		ImageLoaderHelper.mImageLoader.displayImage(ImageUtils.getIconByDwID(workBean.dwID, ImageUtils.ICON_MAIN), vh.ivAvatar,
				ImageLoaderHelper.mOptions);
		String time = TimeUtils.getFormatTime(workBean.time, "MM-dd-HH:mm");
		vh.tvZan.setText("赞（" + mWorkBeans.get(position).like.size() + "）");
		vh.tvCai.setText("踩（" + mWorkBeans.get(position).dislike.size() + "）");
		vh.tvReport.setText("举报（" + mWorkBeans.get(position).report.size() + "）");
		vh.tvTime.setText(time);
		if (workBean.contentType == 0) {
			vh.gvPic.setVisibility(View.GONE);
			vh.llAudio.setVisibility(View.GONE);
		} else if (workBean.contentType == 1) {
			vh.gvPic.setVisibility(View.GONE);
			vh.llAudio.setVisibility(View.VISIBLE);
			if (CommUtil.isNotBlank(workBean.imgUrl)) {
				String[] urls = workBean.imgUrl.split(";");
				vh.ivAudioCover.setVisibility(View.VISIBLE);
				ImageLoaderHelper.mImageLoader.displayImage(urls[0], vh.ivAudioCover, ImageLoaderHelper.mOptions);
			} else {
				vh.ivAudioCover.setVisibility(View.GONE);
			}
		} else if (workBean.contentType == 2) {
			vh.gvPic.setVisibility(View.VISIBLE);
			vh.llAudio.setVisibility(View.GONE);
			if (CommUtil.isNotBlank(workBean.imgUrl)) {
				vh.gvPic.setVisibility(View.VISIBLE);
				String[] urls = workBean.imgUrl.split(";");
				vh.adapetr.setUrls(urls);
				vh.adapetr.notifyDataSetChanged();
			} else {
				vh.gvPic.setVisibility(View.GONE);
			}
		}
		if (mWorkBeans.get(position).comment.size() > 0) {
			vh.lvComment.setVisibility(View.VISIBLE);
			vh.commentAdapter.setWorkComment(mWorkBeans.get(position).comment);
			vh.commentAdapter.notifyDataSetChanged();
		} else {
			vh.lvComment.setVisibility(View.GONE);
		}
		// if (map.get(position) == null || map.get(position) == false) {
		// Drawable drawable = vh.ivPlaySound.getBackground();
		// if (drawable instanceof AnimationDrawable) {
		// AnimationDrawable mAnimation = (AnimationDrawable) drawable;
		// mAnimation.stop();
		// vh.ivPlaySound.setBackgroundResource(R.drawable.chatfrom_voice_playing);
		// } else if (drawable instanceof BitmapDrawable) {
		//
		// }
		// } else {
		// vh.ivPlaySound.setBackgroundResource(R.anim.voice_from_icon);
		// AnimationDrawable mAnimation = (AnimationDrawable)
		// vh.ivPlaySound.getBackground();
		// mAnimation.start();
		// }
		return con;
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Integer position = (Integer) v.getTag();
			mPosition = position;
			LogUtils.d(TAG, "position " + mPosition);
			WorkBean workBean = mWorkBeans.get(position).work;
			switch (v.getId()) {
			case R.id.tv_comment:
				if (FileUtils.getPromptStatus(mContext, Constants.COMMENT) == CommomPromptDialogFragment.COMMENT) {
					if (null != mOnWorkCircleAdapterListener) {
						mOnWorkCircleAdapterListener.comment(position, CommentBean.NORMAL);
					}
					return;
				}
				showCommomDialog(CommomPromptDialogFragment.COMMENT, comment, position);
				break;
			case R.id.ll_audio:
				if (map.get(position) == null || map.get(position) == false) {
					if (CommUtil.isBlank(workBean.audioUrl)) {
						return;
					}
					String audioFileName = workBean.audioUrl.substring(workBean.audioUrl.lastIndexOf("/") + 1,
							workBean.audioUrl.length() - 1);
					File file = new File(FilePath.AUDIO_PATH + audioFileName);
					if (file.exists()) {
						playLocalMusic(file.getAbsolutePath(), position);
					} else {
						String url = workBean.audioUrl.substring(0, workBean.audioUrl.length() - 1);
						DownLoadAudio(url, position);
					}
				} else {
					MediaManager.stop();
					map.put(position, false);
					notifyDataSetChanged();
				}
				break;
			case R.id.tv_zan:
				if (FileUtils.getPromptStatus(mContext, Constants.ZAN) == CommomPromptDialogFragment.ZAN) {
					if (null != mOnWorkCircleAdapterListener) {
						mOnWorkCircleAdapterListener.comment(position, CommentBean.LIKE);
					}
					return;
				}
				showCommomDialog(CommomPromptDialogFragment.ZAN, zan, position);
				break;
			case R.id.tv_cai:
				if (FileUtils.getPromptStatus(mContext, Constants.CAI) == CommomPromptDialogFragment.CAI) {
					if (null != mOnWorkCircleAdapterListener) {
						mOnWorkCircleAdapterListener.comment(position, CommentBean.SUPERDISLIKE);
					}
					return;
				}
				showCommomDialog(CommomPromptDialogFragment.CAI, cai, position);
				break;
			case R.id.tv_report:
				if (FileUtils.getPromptStatus(mContext, Constants.REPORT) == CommomPromptDialogFragment.REPORT) {
					if (null != mOnWorkCircleAdapterListener) {
						mOnWorkCircleAdapterListener.comment(position, CommentBean.REPORT);
					}
					return;
				}
				showCommomDialog(CommomPromptDialogFragment.REPORT, report, position);
				break;
			// case R.id.iv_audio_cover:
			// String[] urls = workBean.imgUrl.split(";");
			// ArrayList<String> images = new ArrayList<String>();
			// Collections.addAll(images, urls);
			// Intent intent = new Intent(mContext, ImagePagerActivity.class);
			// intent.putExtra(ImagePagerActivity.IMAGE_PATHS, images);
			// intent.putExtra(ImagePagerActivity.IMAGE_FROM,
			// ImageUtils.IMAGE_FROM_NET);
			// intent.putExtra(ImagePagerActivity.IMAGE_INDEX_FIRST, 0);
			// mContext.startActivity(intent);
			// break;
			}
		}
	};

	private OnItemClickListener mImagsItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int po, long id) {
			int position = (Integer) parent.getTag();
			WorkMessage workMessage = mWorkBeans.get(position);
			String[] urls = workMessage.work.imgUrl.split(";");
			ArrayList<String> images = new ArrayList<String>();
			Collections.addAll(images, urls);
			Intent intent = new Intent(mContext, ImagePagerActivity.class);
			intent.putExtra(ImagePagerActivity.IMAGE_PATHS, images);
			intent.putExtra(ImagePagerActivity.IMAGE_FROM, ImageUtils.IMAGE_FROM_NET);
			intent.putExtra(ImagePagerActivity.IMAGE_INDEX_FIRST, po);
			mContext.startActivity(intent);
		}
	};
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int po, long id) {
			int position = (Integer) parent.getTag();
			WorkMessage workMessage = mWorkBeans.get(position);
			WorkComment workComment = workMessage.comment.get(po);
			if (null != mOnWorkCircleAdapterListener) {
				mOnWorkCircleAdapterListener.reply(position, workComment.dwID, workComment.publisherName, CommentBean.NORMAL);
			}
		}
	};

	private void playLocalMusic(String uri, final int position) {
		map.put(playingPosition, false);// 将前一个正在播放的语音位置设置为false
		map.put(position, true);// 将当前正在播放的语音位置设置为true
		playingPosition = position;// 正在播放的位置改为当前点击的位置
		notifyDataSetChanged();
		MediaManager.startLocal(mContext, uri, new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				map.put(position, false);
				notifyDataSetChanged();
			}
		}, new OnExceptionListener() {

			@Override
			public void onException(Exception e) {
				map.put(position, false);
				notifyDataSetChanged();
			}
		});
	}

	HttpUtils httpUtils = new HttpUtils();

	public void DownLoadAudio(String uri, final int position) {
		String fileName = uri.substring(uri.lastIndexOf("/"));
		showProgressDialog();
		httpUtils.download(uri, FilePath.AUDIO_PATH + fileName, new RequestCallBack<File>() {
			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				hideProgressDialog();
				playLocalMusic(responseInfo.result.getAbsolutePath(), position);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				hideProgressDialog();
				ToastUtil.showToast("语音文件下载失败");
				notifyDataSetChanged();
				map.put(position, false);
			}
		});
	}

	private OnWorkCircleAdapterListener mOnWorkCircleAdapterListener;

	public interface OnWorkCircleAdapterListener {
		/**
		 * @param position
		 *            item位置
		 * @param type
		 *            评论类型
		 * */
		void comment(int position, int type);

		void reply(int position, String replyId, String replyName, int type);
	}

	public void setOnWorkCircleAdapterListener(OnWorkCircleAdapterListener onWorkCircleAdapterListener) {
		mOnWorkCircleAdapterListener = onWorkCircleAdapterListener;
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(mContext, null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		if (null != mProDialog) {
			mProDialog.hide();
		}
	}

	private CommomPromptDialogFragment mCommomPromptDialog;

	private void showCommomDialog(int enter, String prompt, Integer position) {
		if (null == mCommomPromptDialog) {
			mCommomPromptDialog = new CommomPromptDialogFragment();
		}
		mCommomPromptDialog.setEnter(enter);
		mCommomPromptDialog.setObject(position);
		mCommomPromptDialog.setOnCommomPromptListener(this);
		mCommomPromptDialog.setTips(prompt);
		mCommomPromptDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "mCommomPromptDialog");
	}

	class ViewHolder {
		TextView tvContent, tvName, tvCai, tvZan, tvReport, tvDelete, tvComment, tvTime;
		CircularImageView ivAvatar;
		GridViewForScrollView gvPic;
		PicShowAdapter adapetr;
		ImageView ivPlaySound;// 播放语音按钮
		RelativeLayout llAudio;
		ImageView ivAudioCover;// 录音封面
		ListViewForScrollView lvComment;
		CommentAdapter commentAdapter;
	}

	@Override
	public void onCommomPromtClick(View view) {
		Integer position = (Integer) mCommomPromptDialog.getObject();
		switch (mCommomPromptDialog.getEnter()) {
		case CommomPromptDialogFragment.ZAN:
			if (null != mOnWorkCircleAdapterListener) {
				mOnWorkCircleAdapterListener.comment(position, CommentBean.LIKE);
			}
			break;
		case CommomPromptDialogFragment.CAI:
			if (null != mOnWorkCircleAdapterListener) {
				mOnWorkCircleAdapterListener.comment(position, CommentBean.SUPERDISLIKE);
			}
			break;
		case CommomPromptDialogFragment.REPORT:
			if (null != mOnWorkCircleAdapterListener) {
				mOnWorkCircleAdapterListener.comment(position, CommentBean.REPORT);
			}
			break;
		case CommomPromptDialogFragment.COMMENT:
			if (null != mOnWorkCircleAdapterListener) {
				mOnWorkCircleAdapterListener.comment(position, CommentBean.NORMAL);
			}
			break;
		}
	}
}
