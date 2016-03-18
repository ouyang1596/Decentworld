/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.MainActivity;
import cn.sx.decentworld.adapter.WorkAdapter;
import cn.sx.decentworld.bean.Comment;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.bean.WorkBean;
import cn.sx.decentworld.common.Urls;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: FindComponent.java
 * @Description:
 * @author: yj
 * @date: 2015年7月20日 下午1:21:29
 */

@EBean
public class FindComponent {
	@Bean
	ToastComponent toast;

	@RootContext
	Context context;

	@RootContext
	Activity activity;

	@AfterViews
	void init() {

	}

	@ViewById(R.id.activity_work_lv)
	PullToRefreshListView activity_work_lv;

	WorkBean bean;
	private int Flag;
	ScrollView mScrollView;

	private WorkAdapter adaper;
	private ArrayList<WorkBean> uu;
	private LinearLayout header1;

	public void initFindScrollView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		LinearLayout header = (LinearLayout) inflater.inflate(
				R.layout.main_work_header, null);
		// 点击作品圈自己的头像跳到自己的设置界面
		ImageView avatar = (ImageView) header
				.findViewById(R.id.activity_work_header_icon);
		avatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 写跳转到我的设置
				// activity.finish();
				// ChatFragment.chat_scrollLayout.setToScreen(3);
				// 当创建完组后传过来这参数，即实现按返回键，回到MainActivity的tab1界面
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("tab", 3);
				context.startActivity(intent);
				activity.finish();

			}
		});
		header1 = (LinearLayout) inflater.inflate(R.layout.footer, null);

		ListView listview = activity_work_lv.getRefreshableView();
		listview.addHeaderView(header);
		listview.addFooterView(header1);

		activity_work_lv.setScrollingWhileRefreshingEnabled(activity_work_lv
				.isScrollingWhileRefreshingEnabled());
		activity_work_lv.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				"在加载中...");
		activity_work_lv.getLoadingLayoutProxy(false, true).setReleaseLabel(
				"更多");
		activity_work_lv.getLoadingLayoutProxy(false, true).setPullLabel("123");
		activity_work_lv
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						Flag = 0;
						new GetDataTask().execute();
					}
				});
		activity_work_lv
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// header1.setVisibility(View.VISIBLE);
						Flag = 1;
						new GetDataTask().execute();

					}
				});

	}

	public void initListView() {
		String nickname;
		String time;

		ArrayList<Praise> praises;
		ArrayList<Praise> tramples;
		ArrayList<Comment> comments;

		nickname = null;// DecentWorldApp.getInstance().getUserName();

		// 时间数据
		SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日  HH:mm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		time = formatter.format(curDate);

		// 照片数据
		List<ArrayList<String>> urls = new ArrayList<ArrayList<String>>();
		ArrayList<String> url_temp;
		for (int i = 0; i < 4; i++) {
			url_temp = new ArrayList<String>();
			for (int j = 0; j < 5; j++) {
				url_temp.add(Urls.URL_LIST[i * 5 + j]);
			}
			urls.add(url_temp);
		}

		// 点赞数据
		praises = new ArrayList<Praise>();
		Praise praise = null;
		for (int i = 0; i < 15; i++) {
			praise = new Praise();
			praise.setAvatar(Urls.URL_AVATAR[1]);
			praise.setNickname("功夫巨星");
			praise.setTime(time);
			praises.add(praise);

		}

		// 踩数据
		tramples = new ArrayList<Praise>();
		for (int i = 0; i < 16; i++) {
			praise = new Praise();
			praise.setAvatar(Urls.URL_AVATAR[3]);
			praise.setNickname("天外飞仙");
			praise.setTime(time);
			tramples.add(praise);
		}

		// 评论数据
		comments = new ArrayList<Comment>();
		Comment comment1 = new Comment();
		comment1.setC_name("安妮");
		comment1.setC_content("成龙大哥好帅啊！");
		comment1.setC_date(time);
		Comment comment2 = new Comment();
		comment2.setC_isreply(true);
		comment2.setC_reply_name("熊二");
		comment2.setC_name("安妮");
		comment2.setC_content("武打明星！");
		comment2.setC_date(time);
		comments.add(comment1);
		comments.add(comment2);

		// 加载数据
		ArrayList<String> url = new ArrayList<String>();
		for (int i = 4; i < 9; i++) {
			url.add(Urls.URL_LIST[i]);
		}

		bean = new WorkBean();
		bean.setAvatar(Urls.URL_AVATAR[2]);
		bean.setNickname(nickname);
		bean.setTime(time);
		bean.setDescription("加载成功 ：成龙，国家一级演员，大中华区影坛和国际功夫影星。");
		bean.setUrls(url);
		bean.setPraises(praises);
		bean.setGood(praises.size());
		bean.setTramples(tramples);
		bean.setBad(tramples.size());
		bean.setComments(comments);

		// 其它数据
		uu = new ArrayList<WorkBean>();
		WorkBean bWorkBean;
		for (int i = 0; i < urls.size(); i++) {
			bWorkBean = new WorkBean();

			switch (i) {
			case 0:
				bWorkBean.setAvatar(Urls.URL_AVATAR[i]);
				bWorkBean.setNickname("小王子");

				break;
			case 1:
				bWorkBean.setAvatar(Urls.URL_AVATAR[1]);
				bWorkBean.setNickname(nickname);
				break;
			case 2:
				bWorkBean.setAvatar(Urls.URL_AVATAR[1]);
				bWorkBean.setNickname(nickname);
				break;
			case 3:
				bWorkBean.setAvatar(Urls.URL_AVATAR[i]);
				bWorkBean.setNickname("功夫巨星");
				break;

			default:
				bWorkBean.setAvatar(Urls.URL_AVATAR[i]);
				bWorkBean.setNickname(nickname);
				break;
			}

			bWorkBean.setTime(time);
			bWorkBean.setDescription("成龙，国家一级演员，大中华区影坛和国际功夫影星。");
			bWorkBean.setUrls(urls.get(i));
			bWorkBean.setPraises(praises);
			bWorkBean.setGood(praises.size());
			bWorkBean.setTramples(tramples);
			bWorkBean.setBad(tramples.size());
			bWorkBean.setComments(comments);
			uu.add(bWorkBean);
		}

		adaper = new WorkAdapter(context, uu);
		activity_work_lv.setAdapter(adaper);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected String[] doInBackground(Void... params) {

			try {
				// Simulates a background job.
				Thread.sleep(4000);
				// 实际中是加载数据所用的时间
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here
			// Call onRefreshComplete when the list has been refreshed.

			if (Flag == 1) {

				uu.add(bean);
				// uu.add(bean);
				// uu.add(bean);
				adaper.notifyDataSetChanged();
				// header1.setVisibility(View.INVISIBLE);
			} else if (Flag == 0) {
				// for (int i = 0; i < 10; i++)
				// {
				uu.add(0, bean);
				// }
				adaper.notifyDataSetChanged();
			}

			activity_work_lv.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}
