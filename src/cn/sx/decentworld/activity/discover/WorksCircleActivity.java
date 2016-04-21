/**
 * 
 */
package cn.sx.decentworld.activity.discover;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BaseFragmentActivity;
import cn.sx.decentworld.adapter.WorkCircleAdapter;
import cn.sx.decentworld.adapter.WorkCircleAdapter.OnWorkCircleAdapterListener;
import cn.sx.decentworld.bean.CommentBean;
import cn.sx.decentworld.bean.WorkComment;
import cn.sx.decentworld.bean.WorkMessage;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.MediaManager;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.fragment.PublishFragment;
import cn.sx.decentworld.fragment.PublishFragment.OnPublishChangeListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nui.multiphotopicker.model.ImageItem;

/**
 * @ClassName: WorksCircleActivity.java
 * @Description: 作品圈界面
 * @author: yj
 * @date: 2016年2月22日 下午3:42:08
 */
@EActivity(R.layout.activity_works_cicle)
public class WorksCircleActivity extends BaseFragmentActivity implements OnPublishChangeListener, OnRefreshListener2,
		OnClickListener {
	private static final String TAG = "WorksCircleActivity";
	@ViewById(R.id.plv_work_circle)
	PullToRefreshListView plvWorkCircle;
	@ViewById(R.id.view_mask)
	View viewMask;
	@Bean
	KeyboardComponent KeyboardComponent;
	private WorkCircleAdapter mWorkCircleAdapter;
	public PublishFragment mPublishFragment;
	private int height;
	private String data = "[{\"content\":\"阿里郎\",\"dislike\":0,\"down\":0,\"dwID\":\"487066\",\"id\":3,\"like\":0,\"money\":5,\"publisherName\":\"??\",\"report\":0,\"score\":0,\"status\":0,\"time\":1460356498121,\"url\":\"\"}]";
	private SendUrl mSendurl;
	CommentBean commentBean;
	WorkMessage message;// 评论对象包括赞，踩，评论，举报等
	private boolean isRequest;// 判断网络是否正在请求当中
	private int page;
	List<WorkMessage> mWorkMessages = new ArrayList<WorkMessage>();
	private Handler mLoadDataHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			List<WorkMessage> workMessages = (List<WorkMessage>) JsonUtils.json2BeanArray(msg.obj.toString(), WorkMessage.class);
			// 对WorkMessage里的评论，赞，踩进行分类
			for (int i = 0; i < workMessages.size(); i++) {
				workMessages.get(i).filter();
			}
			mWorkMessages.addAll(workMessages);
			mWorkCircleAdapter.notifyDataSetChanged();
			// JSONObject object = new JSONObject(msg.obj.toString());
			// String array = object.getString("new");
			// LogUtils.i("bm", "array--" + array);
			// List<WorkBean> workBeans = (List<WorkBean>)
			// JsonUtils.json2BeanArray(array, WorkBean.class);
			// mWorkBeans.addAll(workBeans);
			// mWorkCircleAdapter.notifyDataSetChanged();
		};
	};
	private Handler mCommentHandler = new Handler() {
		public void handleMessage(Message msg) {
			mPublishFragment.clearContent();
			KeyboardComponent.closeKeyboard(mPublishFragment.etContent);
			try {
				JSONObject object = new JSONObject(msg.obj.toString());
				String comment = object.getString("comment");
				WorkComment workComment = JsonUtils.json2Bean(comment, WorkComment.class);
				switch (workComment.type) {
				case CommentBean.NORMAL:
					message.comment.add(workComment);
					break;
				case CommentBean.LIKE:
					message.like.add(workComment);
					break;
				case CommentBean.SUPERDISLIKE:
					message.dislike.add(workComment);
					break;
				case CommentBean.REPORT:
					message.report.add(workComment);
					break;
				}
				mWorkCircleAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
			}
		};
	};
	private Handler mPublishHandler = new Handler() {
		public void handleMessage(Message msg) {
			mPublishFragment.clearText();
			KeyboardComponent.closeKeyboard(mPublishFragment.etText);
		};
	};

	@AfterViews
	void inti() {
		measureHeight();
		initView();
		loadData();
	}

	/**
	 * 获取屏幕的宽高
	 */
	private void measureHeight() {
		WindowManager wm = this.getWindowManager();
		height = wm.getDefaultDisplay().getHeight();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initView() {
		mSendurl = new SendUrl(this);
		viewMask.setVisibility(View.GONE);
		viewMask.setOnClickListener(this);
		mPublishFragment = new PublishFragment();
		mPublishFragment.setOnPublishChangeListener(this);
		plvWorkCircle.setMode(Mode.BOTH);
		View view = View.inflate(mContext, R.layout.layout_work_circle_head, null);
		plvWorkCircle.getRefreshableView().addHeaderView(view);
		plvWorkCircle.setOnRefreshListener(this);
		mWorkCircleAdapter = new WorkCircleAdapter(mContext, mWorkMessages);
		mWorkCircleAdapter.setOnWorkCircleAdapterListener(new OnWorkCircleAdapterListener() {

			@Override
			public void comment(int position, int type) {
				LogUtils.d(TAG, "comment---position--" + position + "type--" + type);
				message = mWorkCircleAdapter.getItem(position);
				commentBean = new CommentBean();
				commentBean.dwID = DecentWorldApp.getInstance().getDwID();
				commentBean.publisherID = message.work.dwID;
				commentBean.workID = message.work.id;
				commentBean.type = type;
				commentBean.publisherName = UserInfoEngine.getInstance().getShowName();
				if (type == CommentBean.NORMAL) {
					mPublishFragment.relPublish.setVisibility(View.GONE);
					mPublishFragment.relComment.setVisibility(View.VISIBLE);
					mPublishFragment.etContent.requestFocus();
					plvWorkCircle.getRefreshableView().smoothScrollToPosition(position);
					KeyboardComponent.openKeybord(mPublishFragment.etContent);
					viewMask.setVisibility(View.VISIBLE);
				} else {
					workComment();
				}
			}

			@Override
			public void reply(int position, String replyId, String replyName, int type) {
				if (DecentWorldApp.getInstance().getDwID().equals(replyId)) {
					ToastUtil.showToast("是同一个人，无法回复");
					return;
				}
				message = mWorkCircleAdapter.getItem(position);
				commentBean = new CommentBean();
				commentBean.dwID = DecentWorldApp.getInstance().getDwID();
				commentBean.publisherID = message.work.dwID;
				commentBean.workID = message.work.id;
				commentBean.type = type;
				commentBean.publisherName = UserInfoEngine.getInstance().getShowName();
				commentBean.reply = replyId + ";" + replyName;

				mPublishFragment.relPublish.setVisibility(View.GONE);
				mPublishFragment.relComment.setVisibility(View.VISIBLE);
				mPublishFragment.etContent.requestFocus();
				plvWorkCircle.getRefreshableView().smoothScrollToPosition(position);
				KeyboardComponent.openKeybord(mPublishFragment.etContent);
				viewMask.setVisibility(View.VISIBLE);
			}

		});
		plvWorkCircle.setAdapter(mWorkCircleAdapter);
		getSupportFragmentManager().beginTransaction().add(R.id.fl_publish, mPublishFragment, "mpf").commit();
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		if (NetworkUtils.isNetWorkConnected(this)) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
			map.put("page", "" + page);
			isRequest = true;
			showProgressDialog();
			mSendurl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_WORK_REFRESH, Method.POST,
					new HttpCallBack() {

						@Override
						public void onSuccess(String resultJSON, ResultBean resultBean) {
							LogUtils.i(TAG, "loadData---" + resultBean.toString());
							hideProgressDialog();
							isRequest = false;
							onRefreshComplete();
							if (resultBean.getResultCode() == 2222) {
								Message mesg = mLoadDataHandler.obtainMessage();
								mesg.obj = resultBean.getData().toString();
								mLoadDataHandler.sendMessage(mesg);
							} else {
								showToast(resultBean.getMsg());
							}
						}

						@Override
						public void onFailure(String e) {
							LogUtils.e(TAG, "loadData---error---" + e);
							hideProgressDialog();
							isRequest = false;
							onRefreshComplete();
							showToast(Constants.NET_WRONG);
						}
					});
		} else {
			ToastUtil.showToast("网络连接断开");
		}
	}

	private void showToast(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(msg);
			}
		});
	}

	/**
	 * 获取屏幕高度
	 * */
	public int getHeight() {
		return height;
	}

	private String text, voiceCoverPath, voiceFilePath;
	private List<ImageItem> imageItems;

	@Override
	public void onPublishChange(int choose) {
		text = mPublishFragment.getText();
		if (CommUtil.isBlank(text)) {
			ToastUtil.showToast("请输入文字内容");
			return;
		}
		File[] file = null;
		if (mPublishFragment.isVoice()) {
			voiceCoverPath = mPublishFragment.getVoiceCoverPath();
			voiceFilePath = mPublishFragment.getVoiceFilePath();
			if (null == voiceFilePath) {
				mPublishFragment.type = PublishFragment.TYPE_TEXT;
			} else {
				mPublishFragment.type = PublishFragment.TYPE_VOICE;
				file = new File[1];
				file[0] = new File(voiceFilePath);
			}
		} else {
			imageItems = mPublishFragment.getImageItems();
			if (null != imageItems && imageItems.size() > 0) {
				file = new File[imageItems.size()];
				for (int i = 0; i < imageItems.size(); i++) {
					file[i] = new File(imageItems.get(i).sourcePath);
				}
				mPublishFragment.type = PublishFragment.TYPE_PICTURE;
			} else {
				file = new File[0];
				mPublishFragment.type = PublishFragment.TYPE_TEXT;
			}
		}
		publish(choose, file);
	}

	private void publish(int choose, File[] file) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("content", text);
		map.put("type", "" + mPublishFragment.type);
		LogUtils.i("bm", "type--" + mPublishFragment.type);
		map.put("level", "" + choose);
		map.put("publisherName", UserInfoEngine.getInstance().getShowName());
		map.put("money", "5");// 取值范围5-100
		showProgressDialog();
		mSendurl.httpRequestWithImage(map, file, Constants.CONTEXTPATH + ConstantNet.API_MOMENT_PUBLISH, new HttpCallBack() {

			@Override
			public void onSuccess(String resultJSON, ResultBean resultBean) {
				hideProgressDialog();
				LogUtils.d(TAG, "publish---" + resultBean.toString());
				if (resultBean.getResultCode() == 2222) {
					showToast("succ");
					mPublishHandler.sendEmptyMessage(resultBean.getResultCode());
				} else {
					showToast(resultBean.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "publish---error---" + e);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	@Override
	public void onHeightChnage(int height) {
		LogUtils.i(TAG, "onHeightChnage---height---" + height);
		LayoutParams layoutParams = plvWorkCircle.getLayoutParams();
		layoutParams.height = this.height - height - 50;
		plvWorkCircle.setLayoutParams(layoutParams);
	}

	@Override
	public void comment(String content) {
		commentBean.content = content;
		workComment();
	}

	private void workComment() {
		String comment = JsonUtils.bean2json(commentBean);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("comment", comment);
		showProgressDialog();
		mSendurl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_WORK_COMMENT, Method.POST,
				new HttpCallBack() {

					@Override
					public void onSuccess(String resultJSON, ResultBean resultBean) {
						hideProgressDialog();
						LogUtils.i(TAG, "workComment---" + resultBean.toString());
						if (2222 == resultBean.getResultCode()) {
							Message message = mCommentHandler.obtainMessage();
							message.obj = resultBean.getData().toString();
							mCommentHandler.sendMessage(message);
							showToast("请求成功");
						} else {
							showToast(resultBean.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.e(TAG, "workComment---error---" + e);
						hideProgressDialog();
						showToast(Constants.NET_WRONG);
					}
				});
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		if (isRequest) {
			ToastUtil.showToast("正在请求中。。。");
			return;
		}
		mWorkMessages.clear();
		page = 0;
		loadData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		if (isRequest) {
			ToastUtil.showToast("正在请求中。。。");
			return;
		}
		page++;
		loadData();
	}

	public void onRefreshComplete() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				plvWorkCircle.onRefreshComplete();
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		MediaManager.stop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_mask:
			hideComment();
			break;
		}
	}

	private void hideComment() {
		mPublishFragment.relPublish.setVisibility(View.VISIBLE);
		mPublishFragment.relComment.setVisibility(View.GONE);
		viewMask.setVisibility(View.GONE);
		KeyboardComponent.closeKeyboard(mPublishFragment.etContent);
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
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null != mProDialog) {
					mProDialog.hide();
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mPublishFragment.relComment.isShown()) {
			hideComment();
		} else {
			super.onBackPressed();
		}
	}
}
