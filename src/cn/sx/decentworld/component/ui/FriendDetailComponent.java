/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ScrollView;
import cn.sx.decentworld.activity.FriendDetailActivity;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


/**
 * @ClassName: FriendDetailComponent.java
 * @Description:朋友详细信息界面对应的Component组件
 * @author: cj
 * @date: 2015年8月13日 下午4:49:15
 */

@EBean
public class FriendDetailComponent
{
	private static final String TAG = "FriendDetailComponent";
	@Bean
	ToastComponent toast;
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	
	private int Flag;
	
	// http请求类
	private SendUrl sendUrl;
		
	@AfterViews
	public void init()
	{
		sendUrl = new SendUrl(context);
	}

	/**
	 * 初始化me中的scrollview
	 */
	public void initScrollView(final PullToRefreshScrollView sv_friend_detail_pull_to_refresh)
	{
		sv_friend_detail_pull_to_refresh.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上拉加载");
		sv_friend_detail_pull_to_refresh.getLoadingLayoutProxy(false, true).setPullLabel("");
		sv_friend_detail_pull_to_refresh.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
		sv_friend_detail_pull_to_refresh.getLoadingLayoutProxy(false, true).setReleaseLabel("放开以加载");
		sv_friend_detail_pull_to_refresh.setMode(Mode.PULL_UP_TO_REFRESH);
		sv_friend_detail_pull_to_refresh.setScrollingWhileRefreshingEnabled(!sv_friend_detail_pull_to_refresh.isScrollingWhileRefreshingEnabled());
		sv_friend_detail_pull_to_refresh.setOnRefreshListener(new OnRefreshListener<ScrollView>()
		{

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView)
			{

				if (refreshView.isRefreshing())
				{
					// uu.clear();
					// Flag = 0;
					// new GetDataTask().execute();

				} else
				{
//					Flag = 1;// 一次加载一项
//					new GetDataTask(sv_friend_detail_pull_to_refresh).execute();

				}

			}
		});

		FriendDetailActivity.mScrollView = sv_friend_detail_pull_to_refresh.getRefreshableView();
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]>
	{
		private PullToRefreshScrollView sv_friend_detail_pull_to_refresh;

		public GetDataTask(PullToRefreshScrollView sv_friend_detail_pull_to_refresh)
		{
			this.sv_friend_detail_pull_to_refresh = sv_friend_detail_pull_to_refresh;
		}

		@Override
		protected String[] doInBackground(Void... params)
		{
			// Simulates a background job.
			try
			{
				Thread.sleep(4000);
			} catch (InterruptedException e)
			{
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			// Do some stuff here
			// Call onRefreshComplete when the list has been refreshed.
			sv_friend_detail_pull_to_refresh.onRefreshComplete();
			if (Flag == 1)
			{
				toast.show("加载作品圈需要处理");
				FriendDetailActivity.uu.add(FriendDetailActivity.bean);
				FriendDetailActivity.adaper.notifyDataSetChanged();
			}
			super.onPostExecute(result);
		}
	}
	
	public void deleteFriend(HashMap<String, String> hashmap,
			String api, final Handler handler) {
		sendUrl.httpRequestWithParams(hashmap, Constants.CONTEXTPATH_OPENFIRE
				+ api, Method.GET, new HttpCallBack() {
			@Override
			public void onSuccess(String response, ResultBean msg) {
				if (msg.getResultCode() == 2222) {
					Message mssg = new Message();
					mssg.what = Constants.SUCC;
					if (null != handler) {
						handler.sendMessage(mssg);
					}
				} else {
					Message mssg = new Message();
					mssg.what = Constants.FAILURE;
					mssg.obj = msg.getMsg();
					if (null != handler) {
						handler.sendMessage(mssg);
					}
				}
			}

			@Override
			public void onFailure(String e) {
				netWrong(Constants.NET_WRONG);
			}

		});
	}
	
	private void netWrong(final String netWrong) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toast.show(netWrong);
			}
		});
	}


}
