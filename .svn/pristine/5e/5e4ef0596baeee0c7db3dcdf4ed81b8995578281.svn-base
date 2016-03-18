/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * @ClassName: PerInfoAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年1月6日 下午5:39:16
 */
public class PerInfoAdapter extends BaseAdapter {
	private List<String> keyList;
	private List<String> valueList;
	private Context mContext;

	public PerInfoAdapter(Context context, List<String> keyList,
			List<String> valueList) {
		mContext = context;
		this.keyList = keyList;
		this.valueList = valueList;
	}

	@Override
	public int getCount() {
		return keyList == null ? 0 : keyList.size();
	}

	@Override
	public Object getItem(int position) {
		return keyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		ViewHolder vh = null;
		if (null == con) {
			con = View
					.inflate(mContext, R.layout.item_stranger_base_info, null);
			vh = new ViewHolder();
			vh.tvName = (TextView) con.findViewById(R.id.tv_name);
			vh.tvValue = (TextView) con.findViewById(R.id.tv_value);
			vh.llBaseInfo = (LinearLayout) con.findViewById(R.id.ll_baseinfo);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		String key = keyList.get(position);
		String value = valueList.get(position);
		vh.tvName.setText(key);
		vh.tvValue.setText(value);
		return con;
	}

	class ViewHolder {
		TextView tvName, tvValue;
		LinearLayout llBaseInfo;
	}
}
