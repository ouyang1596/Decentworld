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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.IconPagerAdapter;
import cn.sx.decentworld.adapter.PerDataAdapter;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.fragment.AchievementFragment;
import cn.sx.decentworld.fragment.InfoFragment;
import cn.sx.decentworld.fragment.InfoFragment.OnInfoClickListener;
import cn.sx.decentworld.fragment.WorksFragment;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.utils.DWUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ImageUtils.VerifyUrlResultListener;
import cn.sx.decentworld.utils.JsonHelper;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.HackyViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * @ClassName: NearCardDetailActivity.java
 * @author: yj
 * @date: 2015年10月7日 下午7:05:47
 */
@EActivity(R.layout.activity_near_card_detail)
public class NearCardDetailForMessageActivity extends BaseFragmentActivity implements OnClickListener, OnInfoClickListener {
	private static final String TAG = "NearCardDetailForMessageActivity";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	public static final String USER_NICKNAME = "user_nickname";
	public static final String USER_WORTH = "user_worth";
	@ViewById(R.id.vp_near_card_detail_pager)
	HackyViewPager mPager;
	@ViewById(R.id.rg_per_data)
	RadioGroup mRgPerData;
	@ViewById(R.id.chat_room_title_root)
	RelativeLayout relTitle;
	@ViewById(R.id.tv_per_age)
	TextView tvPerAge;
	@ViewById(R.id.tv_per_distance)
	TextView tvPerDistance;
	@ViewById(R.id.tv_per_job)
	TextView tvPerJob;
	@ViewById(R.id.tv_per_name)
	TextView tvPerName;
	@ViewById(R.id.pScrollView)
	PullToRefreshScrollView pScrollView;
	@ViewById(R.id.iv_per_dislike)
	ImageView ivDisLike;
	@ViewById(R.id.iv_add_friend)
	ImageView ivAddFriend;
	@ViewById(R.id.iv_realname_nickname)
	ImageView ivRealNameOrNickName;
	@ViewById(R.id.iv_per_like)
	ImageView ivLike;
	@ViewById(R.id.tv_per_sex)
	TextView tvPerSex;
	@ViewById(R.id.tv_per_sign)
	TextView tvPerSign;
	@ViewById(R.id.iv_doubt)
	ImageView ivDoubt;
	@ViewById(R.id.tv_per_worth)
	TextView tvPerWorth;
	@ViewById(R.id.ll_talk_stranger)
	LinearLayout llTalkToStranger;
	@ViewById(R.id.fl_fragment_container)
	FrameLayout flContainer;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@Bean
	GetStrangerInfo getStrangerInfo;
	private PerDataAdapter mPerDataAdapter;
	private String strangerDwID;
	private String introduce, shortIntroduce;
	// 储存头像的地址
	ArrayList<String> mUrls = new ArrayList<String>();
	private List<ImageView> imgvs = new ArrayList<ImageView>();
	private boolean isNickNameOrRealName;// 昵称为true实名为false
	private IconPagerAdapter pagerAdapter;
	// 装ImageView数组
	// private ImageView[][] mImageViews;
	private Handler mGetStrangerInfoHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.i("bm", "detail-----=" + msg.obj.toString());
			parserJson(msg.obj.toString());
		};
	};
	/**
	 * 验证图片回调
	 */
	Handler loadIconHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				List<String> urls = (List<String>) msg.obj;
				if (urls != null && urls.size() > 0) {
					mUrls.addAll(urls);
					for (int i = 0; i < mUrls.size(); i++) {
						ImageView v = new ImageView(mContext);
						v.setScaleType(ImageView.ScaleType.CENTER_CROP);
						imgvs.add(v);
					}
					pagerAdapter = new IconPagerAdapter(mUrls, imgvs);
					mPager.setAdapter(pagerAdapter);
				} else {
					LogUtils.i("bm", "图片有效张为0");
				}
				break;
			default:
				break;
			}
		};
	};

	@AfterViews
	public void init() {
		NGetIntent();
		initIntentData();
		verifyIconPath();
		// initPicPager();
		initPerDataPager();
		getStrangerInfo();
		initData();
		disableAutoScrollToBottom();
	}

	private static String POSITION = "position";
	private int position;

	private void NGetIntent() {
		strangerDwID = getIntent().getStringExtra(Constants.DW_ID);
		position = getIntent().getIntExtra(POSITION, -1);
	}

	private int chatType, chatRelationship;
	private String otherID;

	/**
	 * 初始化参数
	 */
	private void initIntentData() {
		chatType = getIntent().getIntExtra("chatType", -1);
		chatRelationship = getIntent().getIntExtra("chatRelationship", -1);
		otherWorth = worth;
		otherID = strangerDwID;
	}

	/**
	 * 验证图片地址是否有效
	 */
	private void verifyIconPath() {
		ArrayList<String> allUrls = new ArrayList<String>();
		String iconMain = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_MAIN);
		String iconExtra1 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_1);
		String iconExtra2 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_2);
		allUrls.add(iconMain);
		allUrls.add(iconExtra1);
		allUrls.add(iconExtra2);
		new ImageUtils().verifyUrl(allUrls, new VerifyUrlResultListener() {
			@Override
			public void onCompleted(List<String> urls) {
				Message message = Message.obtain();
				message.obj = urls;
				message.what = 1;
				loadIconHandler.sendMessage(message);
			}
		});
	}

	// private void initPicPager() {
	// mImageViews = new ImageView[3][];
	// // 将图片装载到数组中,其中一组类似缓冲，防止图片少时出现黑色图片，即显示不出来
	// mImageViews[0] = new ImageView[3];
	// mImageViews[1] = new ImageView[3];
	// mImageViews[2] = new ImageView[3];
	// for (int i = 0; i < 3; i++) {
	// // imgvs.add(new ImageView(mContext));
	// for (int j = 0; j < mImageViews[i].length; j++) {
	// ImageView imageView = new ImageView(this);
	// imageView.setImageResource(R.drawable.default_icon);
	// mImageViews[i][j] = imageView;
	// }
	// switch (i) {
	// case 0:
	// mUrls.add(ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_MAIN));
	// break;
	// case 1:
	// mUrls.add(ImageUtils.getIconByDwID(strangerDwID,
	// ImageUtils.ICON_EXTRA_1));
	// break;
	// case 2:
	// mUrls.add(ImageUtils.getIconByDwID(strangerDwID,
	// ImageUtils.ICON_EXTRA_2));
	// break;
	// }
	// }
	// PerPicPagerAdapter adapter = new PerPicPagerAdapter(mUrls, mImageViews);
	// mPager.setAdapter(adapter);
	// mPager.setCurrentItem((mUrls.size()) * 50);
	// }

	private InfoFragment infoFragment;
	private AchievementFragment achievementFragment;
	private WorksFragment worksFragment;

	private void initPerDataPager() {
		infoFragment = new InfoFragment();
		infoFragment.setOnInfoClickListener(this);
		achievementFragment = new AchievementFragment();
		worksFragment = new WorksFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container, infoFragment, "inf")
				.add(R.id.fl_fragment_container, achievementFragment, "acf")
				.add(R.id.fl_fragment_container, worksFragment, "wof").hide(achievementFragment).hide(worksFragment).commit();
	}

	private void getStrangerInfo() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, strangerDwID);
		getStrangerInfo.getNearStrangerDetailInfo(map, mGetStrangerInfoHandler);
	}

	private void initData() {
		ivAddFriend.setVisibility(View.GONE);
		// relTitle.setBackgroundColor(Color.parseColor("#00000000"));
		ivDisLike.setVisibility(View.GONE);
		ivLike.setVisibility(View.GONE);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		pScrollView.setMode(Mode.PULL_FROM_END);
		ivDisLike.setOnClickListener(this);
		llTalkToStranger.setOnClickListener(this);
		ivLike.setOnClickListener(this);
		mRgPerData.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_info:
					getSupportFragmentManager().beginTransaction().hide(achievementFragment).hide(worksFragment)
							.show(infoFragment).commit();
					break;
				case R.id.rb_achievement:
					getSupportFragmentManager().beginTransaction().hide(worksFragment).hide(infoFragment)
							.show(achievementFragment).commit();
					break;
				case R.id.rb_works:
					getSupportFragmentManager().beginTransaction().hide(achievementFragment).hide(infoFragment)
							.show(worksFragment).commit();
					break;
				}
			}
		});
	}

	private double ln, lt;
	private String worth, otherWorth, age, nickname, userType;
	private List<String> keyList = new ArrayList<String>();
	private List<String> valueList = new ArrayList<String>();

	private void parserJson(String jsonData) {
		LogUtils.i(TAG, "获取的对方信息为：" + jsonData);
		try {
			JSONObject object = new JSONObject(jsonData);

			String sign = object.getString("个性签名");
			tvPerSign.setText(sign);
			JSONObject baseInfoObject = object.getJSONObject("基本信息");
			if (CommUtil.isNotBlank(baseInfoObject.getString("ln"))) {
				ln = Double.valueOf(baseInfoObject.getString("ln"));
			}
			if (CommUtil.isNotBlank(baseInfoObject.getString("lt"))) {
				lt = Double.valueOf(baseInfoObject.getString("lt"));
			}
			userType = baseInfoObject.getString("userType");
			if ("0".equals(userType)) {
				ivDoubt.setVisibility(View.VISIBLE);
				ivDoubt.setImageResource(R.drawable.stranger_doubt_shadow);
			} else if ("1".equals(userType)) {
				ivDoubt.setVisibility(View.GONE);
			} else if ("2".equals(userType)) {
				ivDoubt.setVisibility(View.VISIBLE);
				ivDoubt.setImageResource(R.drawable.stranger_wan);
			}
			double distance = DWUtils.getDistance(LocationProvider.longitude, LocationProvider.latitude, ln, lt);
			distance = ((int) (distance * 100)) / 100.0;
			tvPerDistance.setText(distance + "km");
			worth = baseInfoObject.getString("身价");
			tvPerWorth.setText(worth);
			otherWorth = worth;
			String sex = baseInfoObject.getString("性别");
			tvPerSex.setText(sex);
			age = baseInfoObject.getString("年龄");
			tvPerAge.setText(age);
			if (CommUtil.isNotBlank(baseInfoObject.getString("昵称"))) {
				isNickNameOrRealName = true;
				nickname = baseInfoObject.getString("昵称");
			}

			if (null != nickname && !"".equals(nickname)) {
				tvPerName.setText(nickname);
			}
			introduce = baseInfoObject.getString("introduce");
			shortIntroduce = baseInfoObject.getString("shorIntroduce");
			if (CommUtil.isNotBlank(shortIntroduce)) {
				tvPerSign.setText(shortIntroduce);
			}
			achievementFragment.setIntroduce(shortIntroduce, introduce);
			if (CommUtil.isNotBlank(baseInfoObject.getString("实名"))) {
				isNickNameOrRealName = false;
				nickname = baseInfoObject.getString("实名");
			}
			if (isNickNameOrRealName) {
				ivRealNameOrNickName.setImageResource(R.drawable.stranger_nick);
			} else {
				ivRealNameOrNickName.setImageResource(R.drawable.stranger_real);
			}

			if (null != nickname && !"".equals(nickname)) {
				tvPerName.setText(nickname);
			}
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

	private void disableAutoScrollToBottom() {
		pScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		pScrollView.setFocusable(true);
		pScrollView.setFocusableInTouchMode(true);
		pScrollView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.requestFocusFromTouch();
				return false;
			}
		});
	}

	Map map;

	/**
	 * 将json转化成map
	 * */
	private void mapToList(JSONObject jo) throws JSONException {
		map = JsonHelper.toMap(jo.toString());
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			String value = entry.getValue();
			if (!"".equals(value) && !entry.getKey().equals("身价")) {
				keyList.add(entry.getKey());
				valueList.add(value);
			}
		}
		infoFragment.upData(keyList, valueList);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.iv_per_like:
			intent = new Intent();
			intent.putExtra(POSITION, position);
			setResult(Constants.RESULT_CODE_LIKE, intent);
			finish();
			break;

		case R.id.iv_per_dislike:
			intent = new Intent();
			intent.putExtra(POSITION, position);
			setResult(Constants.RESULT_CODE_DISLIKE, intent);
			finish();
			break;
		case R.id.ll_talk_stranger:
			if (chatType != -1 && chatRelationship != -1) {
				intentToChatActivity();
			} else {
				LogUtils.i(Constants.TAG, "chatType || chatRelationship 为-1");
			}
			break;
		case R.id.iv_back:
			finish();
			break;
		}
	}

	private void intentToChatActivity() {
		Intent intent;
		intent = new Intent(mContext, ChatActivity_.class);
		intent.putExtra(ChatActivity.OTHER_ID, otherID);
		intent.putExtra(ChatActivity.OTHER_NICKNAME, nickname);
		intent.putExtra(ChatActivity.CHAT_TYPE, chatType);
		intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, chatRelationship);
		intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(otherWorth));
		startActivity(intent);
	}

	@Override
	public void OnInfoClick(int position) {
		intentToChatActivity();
	};
}
