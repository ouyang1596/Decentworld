/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.NearCardDetailActivity;
import cn.sx.decentworld.bean.OtherUserInfoField;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.utils.DWUtils;

/**
 * @ClassName: PerInfoAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年1月6日 下午5:39:16
 */
public class PerInfoAdapter extends BaseAdapter {
	private List<OtherUserInfoField> infos;
	private Context mContext;
	private NearCardDetailActivity activity;

	public PerInfoAdapter(Context context, List<OtherUserInfoField> infos) {
		mContext = context;
		activity = (NearCardDetailActivity) mContext;
		this.infos = infos;
	}

	@Override
	public int getCount() {
		return infos == null ? 0 : infos.size();
	}

	@Override
	public OtherUserInfoField getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		ViewHolder vh = null;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_stranger_base_info, null);
			vh = new ViewHolder();
			vh.tvName = (TextView) con.findViewById(R.id.tv_name);
			vh.tvValue = (TextView) con.findViewById(R.id.tv_value);
			vh.relPerInfo = (RelativeLayout) con.findViewById(R.id.rel_per_info);
			vh.ivBlueTag = (ImageView) con.findViewById(R.id.iv_blue_tag);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		OtherUserInfoField otherUserInfoField = infos.get(position);
		if (CommUtil.isBlank(otherUserInfoField.getFieldValue())) {
			vh.relPerInfo.setVisibility(View.VISIBLE);
			vh.ivBlueTag.setVisibility(View.VISIBLE);
		} else if ("隐藏".equals(otherUserInfoField.getFieldValue())) {
			vh.relPerInfo.setVisibility(View.GONE);
		} else {
			vh.relPerInfo.setVisibility(View.VISIBLE);
			vh.ivBlueTag.setVisibility(View.GONE);
		}
		vh.tvName.setText(otherUserInfoField.getFieldName());
		if (activity.distance.equals(otherUserInfoField.getFieldName())) {
			if (CommUtil.isNotBlank(otherUserInfoField.getFieldValue())) {
				String[] split = otherUserInfoField.getFieldValue().split(" ");
				double ln = Double.valueOf(split[0]);
				double lt = Double.valueOf(split[1]);
				double distance = DWUtils.getDistance(LocationProvider.longitude, LocationProvider.latitude, ln, lt);
				distance = ((int) (distance * 100)) / 100.0;
				if (distance > 10000) {
					vh.tvValue.setText("距离已隐藏");
				} else {
					vh.tvValue.setText(distance + "km");
				}
			} else {
				vh.tvValue.setText("距离已隐藏");
			}
		} else {
			vh.tvValue.setText(otherUserInfoField.getFieldValue());
		}
		return con;
	}

	class ViewHolder {
		TextView tvName, tvValue;
		ImageView ivBlueTag;
		RelativeLayout relPerInfo;
	}
}
