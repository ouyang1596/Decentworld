package cn.sx.decentworld.activity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.GridView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.HistoryWorthAdapter;
import cn.sx.decentworld.bean.WorthBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.utils.JsonUtils;

public class HistoryWorthActivity extends BaseFragmentActivity {
	private GridView lvHistoryWorth;
	private HistoryWorthAdapter mHistoryWorthAdapter;
	private String historyWorth;
	private List<WorthBean> worthBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_worth);
		HGetIntent();
		parserJson();
		initView();
	}

	private void initView() {
		lvHistoryWorth = (GridView) findViewById(R.id.lv_history_worth);
		mHistoryWorthAdapter = new HistoryWorthAdapter(mContext, null);
		lvHistoryWorth.setAdapter(mHistoryWorthAdapter);
	}

	private void HGetIntent() {
		historyWorth = getIntent().getStringExtra(Constants.HISTORY_WORTH);
		LogUtils.i("bm", "msg--" + historyWorth);
	}

	private void parserJson() {
		try {
			JSONObject object = new JSONObject(historyWorth);
			JSONArray array = object.getJSONArray("changeHistory");
			worthBeans = (List<WorthBean>) JsonUtils.json2BeanArray(array.toString(), WorthBean.class);
		} catch (JSONException e) {

		}
	}
}
