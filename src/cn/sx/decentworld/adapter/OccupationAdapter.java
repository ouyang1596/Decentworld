/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * @ClassName: ProfessionAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年1月1日 上午10:57:11
 */
public class OccupationAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mData;

	public OccupationAdapter(Context context, List<String> data) {
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_occupation, null);
			vh = new ViewHolder();
			vh.tvOccupation = (TextView) con.findViewById(R.id.tv_occupation_shortIntroduce);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		vh.tvOccupation.setText(mData.get(position));
		return con;
	}

	public void updataListView(List<String> data) {
		mData = data;
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView tvOccupation;
	}
}
