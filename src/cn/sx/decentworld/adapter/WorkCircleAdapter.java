/**
 * 
 */
package cn.sx.decentworld.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: WorkCircleAdapter.java
 * @author: yj
 * @date: 2016年3月7日 下午3:05:29
 */
public class WorkCircleAdapter extends BaseAdapter {
	private Context mContext;

	public WorkCircleAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.layout_work_circle_item, null);
			vh = new ViewHolder();
			vh.lv = (ListView) con.findViewById(R.id.lv_comment);
			vh.tvComment = (TextView) con.findViewById(R.id.tv_comment);
			// vh.tvComment.setOnClickListener(mOnClickListener);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		// vh.tvComment.setTag(position);
		return con;
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Integer position = (Integer) v.getTag();
			switch (v.getId()) {
			case R.id.tv_comment:
				ToastUtil.showToast("position--" + position);
				break;
			}
		}
	};

	class ViewHolder {
		ListView lv;
		TextView tvComment;
	}
}
