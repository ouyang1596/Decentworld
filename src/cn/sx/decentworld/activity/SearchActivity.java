package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.SearchAdapter;
import cn.sx.decentworld.bean.SearchResult;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.SearchComponent;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.ClearEditText;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: SearchActivity.java
 * @Description: 搜索界面
 * @author: yj
 * @date: 2015年7月17日 上午10:58:08
 */
@EActivity(R.layout.activity_contact_search)
public class SearchActivity extends BaseFragmentActivity implements
		OnClickListener {
	@ViewById(R.id.cet_search)
	ClearEditText cetSearch;
	@ViewById(R.id.tv_cancel)
	TextView tvCancel;
	@ViewById(R.id.tv_search)
	TextView tvSearch;
	@ViewById(R.id.lv_search)
	PullToRefreshListView lvSearch;
	private int page;
	@Bean
	SearchComponent searchComponent;
	@Bean
	ToastComponent toast;
	private String mSearchType;
	// private int searchType = SearchResult.SEARCH_TYPE_PHONE;//
	// 搜索类型,默认为按电话号码搜素
	private List<SearchResult> data; // 搜索结果的list
	private SearchAdapter searchAdapter;

	@AfterViews
	public void init() {
		initAdapter();
		// rgSearchTag.setOnCheckedChangeListener(this);
		tvCancel.setOnClickListener(this);
		tvSearch.setOnClickListener(this);
	}

	@SuppressWarnings("unchecked")
	private void initAdapter() {
		data = new ArrayList<SearchResult>();
		searchAdapter = new SearchAdapter(SearchActivity.this, data);
		lvSearch.setAdapter(searchAdapter);
		lvSearch.setMode(Mode.BOTH);
		lvSearch.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				data.clear();
				page = 0;
				searchPeople(page);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page++;
				searchPeople(page);
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.tv_search:
			data.clear();
			page = 0;
			searchPeople(page);
			break;
		}
	}

	private void searchPeople(int page) {
		if (cetSearch.length() <= 0) {
			toast.show("请输入要搜索的内容");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchKey", cetSearch.getText().toString());
		map.put("page", "" + page);
		searchComponent.searchFriendBy(map, mSearchHandler);
	}

	private Handler mSearchHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			lvSearch.onRefreshComplete();
			switch (msg.what) {
			case 2222:
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					mSearchType = object.getString("searchType");
					JSONArray array = object.getJSONArray("result");
					LogUtils.e("bm", "array--" + array.toString());
					List<SearchResult> datas = (List<SearchResult>) JsonUtils
							.json2BeanArray(array.toString(),
									SearchResult.class);
					searchAdapter.setTag(Constants.SEARCH_TYPE, mSearchType);
					searchAdapter.setTag(Constants.MATCH, cetSearch.getText()
							.toString());
					if (null != datas) {
						data.addAll(datas);
						searchAdapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					toast.show("解析错误");
				}
				break;
			default:
				data.clear();
				searchAdapter.notifyDataSetChanged();
				break;
			}
		};
	};
}
