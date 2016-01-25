/**
 * 
 */
package cn.sx.decentworld.activity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.WorkAdapter;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.WorkBean;
import cn.sx.decentworld.common.Constants;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: InterestingGroupSomeThing.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月29日 下午4:50:47
 */

@EActivity(R.layout.activity_interesting_group_something)
public class InterestingGroupSomeThing extends BaseFragmentActivity {
	ScrollView mScrollView;
	WorkBean bean2;
	private int Flag;
	private WorkAdapter adaper;
	private ArrayList<WorkBean> uu;

	
	@ViewById(R.id.sv_something)
	com.handmark.pulltorefresh.library.PullToRefreshScrollView sv_something;
	@ViewById(R.id.lv_something)
	cn.sx.decentworld.widget.ListViewForScrollView lv_something;
	
	
	
	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;
	
	@Click(R.id.main_header_left)
	public void toback(){
		this.finish();
	}
	
	
	@AfterViews
	void init() {
		main_header_left.setVisibility(View.VISIBLE);
		main_header_left_tv.setText(R.string.interesting_group_info);
		main_header_title.setText(R.string.something);
		main_header_right.setVisibility(View.INVISIBLE);
		
		
		initFindScrollView();
		initListView();
	}

	/**
	 * 初始化me中的scrollview
	 */
	public void initFindScrollView() {

		sv_something.getLoadingLayoutProxy(false, true)
				.setLastUpdatedLabel("上拉加载");
		sv_something.getLoadingLayoutProxy(false, true).setPullLabel("");
		sv_something.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel("正在加载...");
		sv_something.getLoadingLayoutProxy(false, true).setReleaseLabel(
				"放开以加载");
		sv_something.setMode(Mode.PULL_UP_TO_REFRESH);
		sv_something
				.setScrollingWhileRefreshingEnabled(!sv_something
						.isScrollingWhileRefreshingEnabled());
		sv_something
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {

						if (refreshView.isRefreshing()) {
						} else {
							Flag = 1;
							new GetDataTask().execute();

						}

					}
				});

		mScrollView = sv_something.getRefreshableView();

	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here
			// Call onRefreshComplete when the list has been refreshed.
			sv_something.onRefreshComplete();
			if (Flag == 1) {

				uu.add(bean2);
				adaper.notifyDataSetChanged();
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 初始化me中的listview
	 */
	public void initListView() {
		ArrayList<String> url = new ArrayList<String>();
		url.add("http://img15.3lian.com/2015/f2/176/d/44.jpg");
		url.add("http://www.haxiu.com/uploads/130111/3053-1301111P41a11.jpg");
		url.add("http://www.lady8844.com/h030/h52/img201308201021183.jpg");
		url.add("http://pic4.nipic.com/20090731/2901523_103337072_2.jpg");
		url.add("http://pic.nipic.com/2008-01-08/2008189404327_2.jpg");

		ContactUser user = new ContactUser();
		user.setShowName("ddd");
		user.setIcon("");
		user.save();

		ArrayList<String> url1 = new ArrayList<String>();
		url1.add("http://pic4.nipic.com/20090811/2439584_133728001_2.jpg");
		url1.add("http://pic13.nipic.com/20110315/6903636_174730783000_2.jpg");
		url1.add("http://img15.3lian.com/2015/f2/176/d/45.jpg");
		url1.add("http://gzdaily.dayoo.com/img/2006-04/24/xin_1704032409587012317012.jpg");
		url1.add("http://pic.nipic.com/2008-01-08/2008189404327_2.jpg");
		url1.add("http://image2.sina.com.cn/ent/m/c/p/2007-02-04/U1326P28T3D1438183F326DT20070204115228.jpg");

		ArrayList<String> url2 = new ArrayList<String>();
		url2.add("http://img.beelink.com/FileUpload/2014/10/10/141010101466021.jpg");
		url2.add("http://pic28.nipic.com/20130416/426060_162930352376_2.jpg");
		url2.add("http://i2.sinaimg.cn/ent/r/m/2012-12-21/U996P28T3D3817799F326DT20121221162219.jpg");
		url2.add("http://y3.ifengimg.com/a/2014_51/9735a27f74f6b82.jpg");
		url2.add("http://pic8.nipic.com/20100630/5223151_140513824298_2.jpg");
		url2.add("http://www.jznews.com.cn/pic/0/10/99/47/10994749_580622.jpg");

		List<ArrayList<String>> urls = new ArrayList<ArrayList<String>>();
		urls.add(url);
		urls.add(url1);
		urls.add(url2);

		WorkBean bean = new WorkBean();
		bean.setUrls(url);
		uu = new ArrayList<WorkBean>();
		WorkBean bean1 = new WorkBean();
		bean1.setUrls(url1);

		bean2 = new WorkBean();
		bean2.setUrls(url2);
		uu.add(bean);
		uu.add(bean1);
		uu.add(bean2);

		adaper = new WorkAdapter(InterestingGroupSomeThing.this, uu, false);

		lv_something.setAdapter(adaper);
		mScrollView.scrollTo(0, 0);

	}
}
