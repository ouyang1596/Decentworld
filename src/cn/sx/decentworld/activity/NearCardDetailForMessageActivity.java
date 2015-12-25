/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.StrangerInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.CardDetailFragment;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.utils.DWUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.JsonHelper;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.widget.HackyViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: NearCardDetailActivity.java
 * @Description:显示陌生人和好友的详细信息 启动界面传入参数 1.dwID 对方dwID
 * @author: yj
 * @date: 2015年10月7日 下午7:05:47
 */
@EActivity(R.layout.activity_near_card_detail)
public class NearCardDetailForMessageActivity extends BaseFragmentActivity {
	private static final String TAG = "NearCardDetailActivity";
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	@ViewById(R.id.vp_near_card_detail_pager)
	HackyViewPager mPager;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	private int pagerPosition;
	@ViewById(R.id.iv_dislike)
	ImageView ivDislike;
	@ViewById(R.id.iv_like)
	ImageView ivLike;
	@ViewById(R.id.item_nearby_stranger_location)
	TextView tvLocation;
	@ViewById(R.id.tv_user_detail_info_sign)
	TextView tvUserSign;
	@ViewById(R.id.ll_detail)
	LinearLayout llDetail;
	@ViewById(R.id.ll_works)
	LinearLayout llWorks;
	@ViewById(R.id.tv_detail)
	TextView tvDetail;
	@ViewById(R.id.tv_works)
	TextView tvWorks;
	private String strangerDwID;
	@ViewById(R.id.iv_talk_stranger)
	ImageView ivTalkToStranger;
	@ViewById(R.id.iv_sex)
	ImageView ivSex;
	@ViewById(R.id.tv_age)
	TextView tvAge;
	@ViewById(R.id.user_detail_info_root)
	LinearLayout llRootDetailInfo;
	@ViewById(R.id.ll_root_works)
	LinearLayout llRootWorks;
	@ViewById(R.id.lv_anonymous_info)
	ListView lvStrangerInfo;
	@ViewById(R.id.tv_realname_nickname)
	TextView tvRealNameNickname;
	@ViewById(R.id.item_nearby_stranger_worth)
	TextView tvWorth;
	@ViewById(R.id.line_iv_detail)
	ImageView ivDetailLine;
	@ViewById(R.id.line_iv_works)
	ImageView ivWorksLine;
	@Bean
	GetStrangerInfo getStrangerInfo;
	@Bean
	ToastComponent toast;
	@ViewById(R.id.activity_near_card_detail_root)
	FrameLayout fCardDetail;
	private StrangerAdapter strangerAdapter;
	private Map<String, String> mapValue;
	public static final String USER_NICKNAME = "user_nickname";
	public static final String USER_WORTH = "user_worth";
	// 身价
	private String worth;
	/**
	 * 装ImageView数组
	 */
	private ImageView[][] mImageViews;
	// 储存头像的地址
	ArrayList<String> mUrls = new ArrayList<String>();
	List<ImageView> imgvs = new ArrayList<ImageView>();
	private String nickname, age, location;

	@AfterViews
	public void init() {
		initView();
		getStrangerInfo();
	}

	private void initView() {
		TimeUtils.currentTimeFormate();
		NGetIntent();
		initMapValue();
		ivDetailLine.setVisibility(View.VISIBLE);
		ivWorksLine.setVisibility(View.GONE);
		View view = View.inflate(mContext, R.layout.item_chat_anonymous, null);
		lvStrangerInfo.addFooterView(view);
		strangerAdapter = new StrangerAdapter();
		lvStrangerInfo.setAdapter(strangerAdapter);
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		ArrayList<String> urls = getIntent().getStringArrayListExtra(
				EXTRA_IMAGE_URLS);
		llDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				tvDetail.setTextColor(Color.parseColor("#62DCC0"));
				ivDetailLine.setVisibility(View.VISIBLE);
				tvWorks.setTextColor(Color.parseColor("#797979"));
				ivWorksLine.setVisibility(View.GONE);
				llRootDetailInfo.setVisibility(View.VISIBLE);
				llRootWorks.setVisibility(View.GONE);
			}
		});
		llWorks.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				tvWorks.setTextColor(Color.parseColor("#62DCC0"));
				ivWorksLine.setVisibility(View.VISIBLE);
				tvDetail.setTextColor(Color.parseColor("#797979"));
				ivDetailLine.setVisibility(View.GONE);
				llRootDetailInfo.setVisibility(View.GONE);
				llRootWorks.setVisibility(View.VISIBLE);
			}
		});
		lvStrangerInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				if (position == strangerAdapter.getCount()) {
					Intent intent = new Intent(mContext,
							NearCardDetail2Activity_.class);
					intent.putExtra(Constants.DW_ID, strangerDwID);
					intent.putExtra(USER_NICKNAME, nickname);
					intent.putExtra(USER_WORTH, worth);
					startActivity(intent);
				}

			}
		});
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// item_nearby_stranger
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator,
						arg0 + 1, mPager.getAdapter().getCount());
				// indicator.setText(text);
			}
		});
		mPager.setCurrentItem(pagerPosition);
		ivTalkToStranger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				LogUtils.i(TAG, "跳转到ChatActivity的信息不完整【缺少worth】，请完善");
				Intent intent = new Intent(mContext, ChatActivity_.class);
				intent.putExtra("user_dwID", strangerDwID);
				if (null != nickname) {
					intent.putExtra("user_nickname", nickname);
				} else {
					intent.putExtra("user_nickname", "");
				}
				intent.putExtra("chatType", DWMessage.CHAT_TYPE_SINGLE);
				intent.putExtra("chatRelationship",
						DWMessage.CHAT_RELATIONSHIP_STRANGER);
				intent.putExtra("user_worth", worth);
				startActivity(intent);
				saveStrangerInfo();
			}

		});
		ivDislike.setVisibility(View.GONE);
		ivDislike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ivLike.setImageResource(R.drawable.me_setting);
		ivLike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplication(),
						ChatSettingActivity_.class);
				intent.putExtra("otherID", strangerDwID);
				startActivity(intent);
			}
		});
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void getStrangerInfo() {
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				String jsonData = msg.obj.toString();
				LogUtils.i("bm", "detail-----=" + msg.obj.toString());
				parserJson(jsonData);
				MyAdapter adapter = new MyAdapter();
				mPager.setAdapter(adapter);
				mPager.setCurrentItem((mUrls.size()) * 100);
				strangerAdapter.notifyDataSetChanged();
			}
		};
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, strangerDwID);
		getStrangerInfo.getNearStrangerDetailInfo(map, handler);
	}

	private List<String> keyList = new ArrayList<String>();
	private List<String> valueList = new ArrayList<String>();
	/**
	 * 将json转化成map
	 * */
	Map map;

	private void mapToList(JSONObject jo) throws JSONException {
		map = JsonHelper.toMap(jo.toString());
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator
					.next();
			String value = entry.getValue();
			if (!"".equals(value) && !entry.getKey().equals("身价")) {
				keyList.add(entry.getKey());
				valueList.add(value);
			}
		}
	};

	private static String POSITION = "position";
	private boolean ifLike;
	private int position;

	private void NGetIntent() {
		location = getIntent().getStringExtra("location");
		tvLocation.setText(location + "km");
		strangerDwID = getIntent().getStringExtra(Constants.DW_ID);
		ifLike = getIntent().getBooleanExtra(Constants.IF_LIKE, false);
		position = getIntent().getIntExtra(POSITION, -1);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			if (mUrls.size() == 1)
				((ViewPager) container).removeView(mImageViews[position
						/ mUrls.size() % 2][0]);
			else
				((ViewPager) container).removeView(mImageViews[position
						/ mUrls.size() % 2][position % mUrls.size()]);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			if (mUrls.size() == 1) {
				((ViewPager) container).addView(mImageViews[position
						/ mUrls.size() % 2][0]);
				ImageLoaderHelper.mImageLoader.displayImage(
						mUrls.get(position % 3),
						mImageViews[position / mUrls.size() % 2][0],
						ImageLoaderHelper.mOptions);
				LogUtils.i("bm", "position--" + position);
				return mImageViews[position / mUrls.size() % 2][0];
			} else
				((ViewPager) container).addView(
						mImageViews[position / mUrls.size() % 2][position
								% mUrls.size()], 0);
			ImageLoaderHelper.mImageLoader.displayImage(
					mUrls.get(position % 3),
					mImageViews[position / mUrls.size() % 2][position
							% mUrls.size()], ImageLoaderHelper.mOptions);
			LogUtils.i("bm", "position--" + position);
			return mImageViews[position / mUrls.size() % 2][position
					% mUrls.size()];
		}
	}

	private static final String GRAY = "#C0C0C0";

	class StrangerAdapter extends BaseAdapter {

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
				con = View.inflate(mContext, R.layout.item_stranger_base_info,
						null);
				vh = new ViewHolder();
				vh.tvName = (TextView) con.findViewById(R.id.tv_name);
				vh.tvValue = (TextView) con.findViewById(R.id.tv_value);
				vh.llBaseInfo = (LinearLayout) con
						.findViewById(R.id.ll_baseinfo);
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

	private void saveStrangerInfo() {
		StrangerInfo info = StrangerInfo.queryByDwID(strangerDwID);
		if (null == info) {
			info = new StrangerInfo();
		}
		info.strangerDwID = strangerDwID;
		info.age = age;
		info.nickname = nickname;
		info.location = location;
		if (null != mUrls && mUrls.size() > 0) {
			info.icon = mUrls.get(0);
		}
		info.save();
	}

	private Double ln = 0.0, lt = 0.0;

	private void parserJson(String jsonData) {
		try {
			JSONObject object = new JSONObject(jsonData);
			String sign = object.getString("个性签名");
			tvUserSign.setText(sign);
			JSONObject baseInfoObject = object.getJSONObject("基本信息");
			if (CommUtil.isNotBlank(baseInfoObject.getString("ln"))) {
				ln = Double.valueOf(baseInfoObject.getString("ln"));
			}
			if (CommUtil.isNotBlank(baseInfoObject.getString("lt"))) {
				lt = Double.valueOf(baseInfoObject.getString("lt"));
			}
			double distance = DWUtils.getDistance(LocationProvider.longitude,
					LocationProvider.latitude, ln, lt);
			distance = ((int) (distance * 100)) / 100.0;
			tvLocation.setText(distance + "km");
			worth = baseInfoObject.getString("身价");
			tvWorth.setText(worth);
			// location = baseInfoObject.getString("位置");
			// tvAddr.setText(location);
			String sex = baseInfoObject.getString("性别");
			if ("男".equals(sex)) {
				ivSex.setImageResource(R.drawable.dot_boy);
			} else {
				ivSex.setImageResource(R.drawable.dot_girl);
			}
			age = baseInfoObject.getString("年龄");
			tvAge.setText(age);
			nickname = baseInfoObject.getString("昵称");
			if (null != nickname && !"".equals(nickname)) {
				tvRealNameNickname.setText(nickname);
				tvTitle.setText(nickname);
			}
			nickname = baseInfoObject.getString("实名");
			if (null != nickname && !"".equals(nickname)) {
				tvRealNameNickname.setText(nickname);
				tvTitle.setText(nickname);
			}
			setImageViews();
			JSONObject historyObject = object.getJSONObject("我的历史");
			keyList.add("我的历史");
			valueList.add("");
			mapToList(historyObject);
			JSONObject cateObject = object.getJSONObject("我的爱好");
			keyList.add("我的爱好");
			valueList.add("");
			mapToList(cateObject);
		} catch (JSONException e) {
		}
	}

	private void setImageViews() {

		mImageViews = new ImageView[3][];
		// 将图片装载到数组中,其中一组类似缓冲，防止图片少时出现黑色图片，即显示不出来
		mImageViews[0] = new ImageView[3];
		mImageViews[1] = new ImageView[3];
		mImageViews[2] = new ImageView[3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < mImageViews[i].length; j++) {
				ImageView imageView = new ImageView(this);
				mImageViews[i][j] = imageView;
			}
			switch (i) {
			case 0:
				mUrls.add(ImageUtils.getIconByDwID(strangerDwID,
						ImageUtils.ICON_MAIN));
				break;
			case 1:
				mUrls.add(ImageUtils.getIconByDwID(strangerDwID,
						ImageUtils.ICON_EXTRA_1));
				break;
			case 2:
				mUrls.add(ImageUtils.getIconByDwID(strangerDwID,
						ImageUtils.ICON_EXTRA_2));
				break;
			}
		}
	}

	private void initMapValue() {
		mapValue = new HashMap<String, String>();
		Resources res = getResources();
		String[] keys = res.getStringArray(R.array.user_info_indexs);
		String[] values = res.getStringArray(R.array.user_info_keys);
		for (int i = 0; i < keys.length; i++) {
			mapValue.put(keys[i], values[i]);
		}
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {
		public ArrayList<String> fileList;

		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return CardDetailFragment.newInstance(url);
		}
	}
}
