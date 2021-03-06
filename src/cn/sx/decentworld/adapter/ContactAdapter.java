/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ContactBean;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;

/**
 * @ClassName: ContactAdapter.java
 * @Description:
 * @author: yj
 * @date: 2016年1月10日 上午8:50:35
 */
public class ContactAdapter extends BaseAdapter {
	private static final String TAG = "ContactAdapter";
	private Context mContext;
	private List<ContactBean> mData;

	public ContactAdapter(Context context, List<ContactBean> data) {
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public ContactBean getItem(int position) {
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
			con = View.inflate(mContext, R.layout.item_contact, null);
			vh = new ViewHolder();
			vh.ivDoubt = (ImageView) con.findViewById(R.id.iv_doubt);
			vh.tvName = (TextView) con.findViewById(R.id.tv_contact_name);
			vh.tvName.setOnClickListener(mOnClickListener);
			vh.ivAvatar = (ImageView) con.findViewById(R.id.iv_contact_avatar);
			vh.ivAvatar.setOnClickListener(mOnClickListener);
			vh.ivAddFriends = (ImageView) con.findViewById(R.id.iv_contact_add);
			vh.ivAddFriends.setOnClickListener(mOnClickListener);
			vh.tvAge = (TextView) con.findViewById(R.id.tv_contact_age);
			vh.tvOccupation = (TextView) con.findViewById(R.id.tv_contact_occupation);
			vh.tvShow = (TextView) con.findViewById(R.id.tv_contact_show);
			vh.tvContactRecommends = (TextView) con.findViewById(R.id.tv_contact_recommends);
			vh.ivSex = (ImageView) con.findViewById(R.id.iv_contact_sex);
			vh.tvWorth = (TextView) con.findViewById(R.id.tv_contact_worth);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		ContactBean contactBean = mData.get(position);
		setTag(position, vh, contactBean);
		ImageLoaderHelper.mImageLoader.displayImage(ImageUtils.getIconByDwID(contactBean.dwID, ImageUtils.ICON_MAIN),
				vh.ivAvatar, ImageLoaderHelper.mOptions);
		LogUtils.d(TAG, "getView() url " + ImageUtils.getIconByDwID(contactBean.dwID, ImageUtils.ICON_MAIN));
		if (CommUtil.isNotBlank(contactBean.age)) {
			vh.tvAge.setText(contactBean.age);
		} else {
			vh.tvAge.setText("最好的年龄");
		}
		vh.tvName.setText(contactBean.name);
		vh.tvOccupation.setText(contactBean.occupation);
		vh.ivSex.setImageResource("男".equals(contactBean.sex) ? R.drawable.stranger_boy : R.drawable.stranger_girl);
		vh.tvShow.setText(contactBean.shortIntroduce);
		vh.tvWorth.setText(contactBean.worth);
		if ("0".equals(contactBean.userType)) {
			vh.ivDoubt.setVisibility(View.VISIBLE);
			vh.ivDoubt.setImageResource(R.drawable.stranger_doubt_shadow);
		} else if ("1".equals(contactBean.userType)) {
			vh.ivDoubt.setVisibility(View.GONE);
		} else if ("2".equals(contactBean.userType)) {
			vh.ivDoubt.setVisibility(View.VISIBLE);
			vh.ivDoubt.setImageResource(R.drawable.stranger_wan);
		}
		if (0 == contactBean.contactRecommends) {
			vh.tvContactRecommends.setTextColor(Color.parseColor("#66ff0000"));
			vh.tvContactRecommends.setText("诚意推荐");
			vh.tvContactRecommends.setVisibility(View.GONE);
		} else if (1 == contactBean.contactRecommends) {
			vh.tvContactRecommends.setTextColor(Color.parseColor("#66F3B800"));
			vh.tvContactRecommends.setText("你可能认识");
			vh.tvContactRecommends.setVisibility(View.GONE);
		}
		return con;
	}

	private void setTag(int position, ViewHolder vh, ContactBean contactBean) {
		vh.ivAvatar.setTag(Constants.ITEM_POSITION, position);
		vh.ivAddFriends.setTag(Constants.ITEM_POSITION, position);
		vh.tvName.setTag(Constants.ITEM_POSITION, position);
		vh.ivAvatar.setTag(Constants.ITEM_TAG, contactBean.random);
		vh.ivAddFriends.setTag(Constants.ITEM_TAG, contactBean.random);
		vh.tvName.setTag(Constants.ITEM_TAG, contactBean.random);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (null != mOnContactClickListener) {
				mOnContactClickListener.OnContactClick(v);
			}
		}
	};

	class ViewHolder {
		TextView tvName, tvOccupation, tvWorth, tvAge, tvShow, tvContactRecommends;
		ImageView ivAddFriends;
		ImageView ivAvatar, ivDoubt;
		ImageView ivSex;
	}

	public interface OnContactClickListener {
		void OnContactClick(View v);
	}

	private OnContactClickListener mOnContactClickListener;

	public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
		mOnContactClickListener = onContactClickListener;
	}

}
