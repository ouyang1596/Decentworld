package cn.sx.decentworld.swipecards;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NearbyStrangerInfo;
import cn.sx.decentworld.bean.StrangerInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.ImageLoader;
import cn.sx.decentworld.fragment.StrangerFragment;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.bumptech.glide.Glide;

/**
 * Created by Shall on 2015-06-23.
 */
public class CardAdapter extends BaseAdapter {
	private static final String TAG = "CardAdapter";
	private Context mContext;
	private List<NearbyStrangerInfo> mCardList;
	private LayoutInflater inflater;
	private StrangerFragment strangerFragment;

	public CardAdapter(Context mContext, StrangerFragment fragment,
			List<NearbyStrangerInfo> mCardList) {
		this.mContext = mContext;
		this.mCardList = mCardList;
		strangerFragment = fragment;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mCardList.size();
	}

	@Override
	public NearbyStrangerInfo getItem(int position) {
		return mCardList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh = null;
		if (con == null) {
			con = inflater
					.inflate(R.layout.item_nearby_stranger, parent, false);
			ViewUtil.scaleView(con);
			vh = new ViewHolder();
			vh.mCardIcon = (ImageView) con
					.findViewById(R.id.item_nearby_stranger_icon);
			vh.mCardHeadPic = (ImageView) con.findViewById(R.id.iv_head_pic);
			vh.mCardHeadPic.setOnClickListener(mOnClickListener);
			vh.mCardName = (TextView) con
					.findViewById(R.id.item_nearby_stranger_name);
			vh.mCardAge = (TextView) con
					.findViewById(R.id.item_nearby_stranger_age);
			vh.mCardWorth = (TextView) con
					.findViewById(R.id.item_nearby_stranger_worth);
			vh.mCardJob = (TextView) con
					.findViewById(R.id.item_nearby_stranger_work);
			vh.mCardSex = (ImageView) con.findViewById(R.id.iv_sex_girl_boy);
			vh.mCardDistance = (TextView) con
					.findViewById(R.id.item_nearby_stranger_location);
			vh.mCardSign = (TextView) con
					.findViewById(R.id.item_nearby_stranger_sign);
			vh.mCardLike = (ImageView) con
					.findViewById(R.id.item_nearby_stranger_like);
			vh.mCardLike.setOnClickListener(mOnClickListener);
			vh.mCardSayHi = (ImageView) con
					.findViewById(R.id.item_nearby_stranger_sayhi);
			vh.mCardSayHi.setOnClickListener(mOnClickListener);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		vh.mCardHeadPic.setTag(position);
		vh.mCardLike.setTag(position);
		vh.mCardSayHi.setTag(position);
		NearbyStrangerInfo info = mCardList.get(position);
		// if (info.getIfLike()) {
		// vh.mCardLike.setImageResource(R.drawable.iv_new_heart_like);
		// } else {
		// vh.mCardLike.setImageResource(R.drawable.iv_new_heart_);
		// }
		// Glide.with(mContext).load(info.getIcon()).into(vh.mCardIcon);
		ImageLoaderHelper.mImageLoader.displayImage(
				ImageUtils.getIconByDwID(info.getDwID(), ImageUtils.ICON_MAIN),
				vh.mCardIcon, ImageLoaderHelper.mOptions);
		vh.mCardName.setText(info.getNickName());
		if (null != info.getRealName() && !"".equals(info.getRealName())) {
			vh.mCardName.setText(info.getRealName());
		}
		vh.mCardSex
				.setImageResource("0".equals(info.getSex()) ? R.drawable.me_sex_women
						: R.drawable.me_sex_man);
		vh.mCardAge.setText(info.getAge());
		vh.mCardWorth.setText(info.getWorth());
		vh.mCardJob.setText(info.getJob());
		Double distance = Double.valueOf(info.getDistance());
		distance = ((int) (distance * 100)) / 100.0;
		vh.mCardDistance.setText(distance + "km");
		vh.mCardSign.setText(info.getSign());
		return con;
	}

	private static String POSITION = "position";
	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			Integer position = (Integer) view.getTag();
			switch (view.getId()) {
			case R.id.item_nearby_stranger_like:
				NearbyStrangerInfo info = CardAdapter.this.getItem(position);
				info.setIfLike(true);
				// Intent intent = new Intent(mContext,
				// NearCardDetailActivity_.class);
				// intent.putExtra(Constants.DW_ID, info.getDwID());
				// intent.putExtra(Constants.IF_LIKE, info.getIfLike());
				// intent.putExtra(POSITION, position);
				// strangerFragment.startActivityForResult(intent,
				// Constants.REQUEST_CODE);
				strangerFragment.flingContainer.getTopCardListener()
						.selectRight();
				strangerFragment.like(info.getDwID());
				// ImageView civ = (ImageView) view;
				// civ.setImageResource(R.drawable.solid_heart);
				break;
			case R.id.item_nearby_stranger_sayhi:
				NearbyStrangerInfo item = CardAdapter.this.getItem(position);
				item.setIfLike(true);
				Intent intent = new Intent(mContext, ChatActivity_.class);
				intent.putExtra("user_dwID", item.getDwID());
				intent.putExtra("user_nickname", item.getNickName());
				intent.putExtra("user_worth", item.getWorth());
				intent.putExtra("chatType", DWMessage.CHAT_TYPE_SINGLE);
				if (ContactUser.isContact(item.getDwID())) {
					// 朋友关系
					intent.putExtra("chatRelationship",
							DWMessage.CHAT_RELATIONSHIP_FRIEND);
				} else {
					// 陌生人关系
					intent.putExtra("chatRelationship",
							DWMessage.CHAT_RELATIONSHIP_STRANGER);
				}
				mContext.startActivity(intent);
				saveStrangerInfo(position);
				break;
			case R.id.iv_head_pic:
				strangerFragment.flingContainer.getTopCardListener()
						.selectLeft();
				break;
			}
		}
	};

	private void saveStrangerInfo(int position) {
		NearbyStrangerInfo nearStrangerInfo = this.getItem(position);
		String dwID = nearStrangerInfo.getDwID();
		StrangerInfo info = StrangerInfo.queryByDwID(dwID);
		if (null == info) {
			info = new StrangerInfo();
		}
		info.strangerDwID = dwID;
		info.age = nearStrangerInfo.getAge();
		info.nickname = nearStrangerInfo.getNickName();
		info.icon = nearStrangerInfo.getIcon();
		info.save();
	}

	class ViewHolder {
		ImageView mCardIcon, mCardSayHi, mCardLike, mCardSex;
		ImageView mCardHeadPic;
		TextView mCardName;
		TextView mCardAge;
		TextView mCardWorth;
		TextView mCardJob;
		TextView mCardDistance;
		TextView mCardSign;
	}
}
