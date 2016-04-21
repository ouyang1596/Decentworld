/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.WorthBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @ClassName: HistoryWorthAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年4月3日 下午8:06:31
 */
public class HistoryWorthAdapter extends BaseAdapter {
	private Context mContext;
	private List<WorthBean> worthBeans;

	public HistoryWorthAdapter(Context context, List<WorthBean> worthBeans) {
		mContext = context;
		this.worthBeans = worthBeans;
	}

	@Override
	public int getCount() {
		return null == worthBeans ? 0 : worthBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return worthBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_history_worth, null);
			vh = new ViewHolder();
			vh.tvWorth = (TextView) con.findViewById(R.id.tv_worth);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		WorthBean worthBean = worthBeans.get(position);
		vh.tvWorth.setText(worthBean.worth + "¥");
		return con;
	}

	class ViewHolder {
		TextView tvWorth;
	}
}
