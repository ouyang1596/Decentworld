/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * @ClassName: SchoolAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年3月21日 下午3:23:40
 */
public class UniversityAdapter extends BaseAdapter implements Filterable {
	private Context mContext;
	private List<String> mAllUniveList;
	private ArrayList<String> mUnfilteredData;
	private ArrayFilter mFilter;

	public UniversityAdapter(Context context, List<String> allUniveList) {
		mContext = context;
		mAllUniveList = allUniveList;
	}

	@Override
	public int getCount() {
		return mAllUniveList == null ? 0 : mAllUniveList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAllUniveList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_university, null);
			vh = new ViewHolder();
			vh.tvUniversity = (TextView) con.findViewById(R.id.tv_university);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		vh.tvUniversity.setText(mAllUniveList.get(position));
		return con;
	}

	class ViewHolder {
		TextView tvUniversity;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	private class ArrayFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();
			if (mUnfilteredData == null) {
				mUnfilteredData = new ArrayList<String>(mAllUniveList);
			}
			if (prefix == null || prefix.length() == 0) {
				ArrayList<String> list = mUnfilteredData;
				results.values = list;
				results.count = list.size();
			} else {
				String prefixString = prefix.toString().toLowerCase();
				ArrayList<String> unfilteredValues = mUnfilteredData;
				int count = unfilteredValues.size();
				ArrayList<String> newValues = new ArrayList<String>(count);

				for (int i = 0; i < count; i++) {
					String pc = unfilteredValues.get(i);
					if (pc != null) {

						if (pc != null && pc.startsWith(prefixString)) {

							newValues.add(pc);
						}
					}
				}
				results.values = newValues;
				results.count = newValues.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			mAllUniveList = (List<String>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
