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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.IconPagerAdapter;
import cn.sx.decentworld.adapter.PerDataAdapter;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.SearchResult;
import cn.sx.decentworld.bean.StrangerFriendDetailInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.AddFriendDialog;
import cn.sx.decentworld.dialog.AddFriendDialog.AddFriendListener;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment.OnTrueOrFalseClickListener;
import cn.sx.decentworld.engine.ContactEngine;
import cn.sx.decentworld.engine.ContactEngine.AddCallback;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.fragment.AchievementFragment;
import cn.sx.decentworld.fragment.InfoFragment;
import cn.sx.decentworld.fragment.InfoFragment.OnInfoClickListener;
import cn.sx.decentworld.fragment.WorksFragment;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.utils.DWUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ImageUtils.VerifyUrlResultListener;
import cn.sx.decentworld.utils.JsonHelper;
import cn.sx.decentworld.utils.ToastUtil;
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
public class NearCardDetailActivity extends BaseFragmentActivity implements OnClickListener, OnInfoClickListener,
		OnTrueOrFalseClickListener, AddFriendListener {
	private static final String TAG = "NearCardDetailActivity";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	public static final String USER_NICKNAME = "user_nickname";
	public static final String USER_WORTH = "user_worth";
	@ViewById(R.id.vp_near_card_detail_pager)
	HackyViewPager mPager;
	@ViewById(R.id.rg_per_data)
	RadioGroup mRgPerData;
	@ViewById(R.id.tv_per_age)
	TextView tvPerAge;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_per_distance)
	TextView tvPerDistance;
	@ViewById(R.id.tv_per_job)
	TextView tvPerJob;
	@ViewById(R.id.tv_occupation)
	TextView tvOccupation;
	@ViewById(R.id.tv_per_wealth)
	TextView tvWealth;
	@ViewById(R.id.pScrollView)
	PullToRefreshScrollView pScrollView;
	@ViewById(R.id.tv_per_name)
	TextView tvPerName;
	@ViewById(R.id.iv_add_friend)
	ImageView ivAddFriend;
	@ViewById(R.id.iv_per_dislike)
	ImageView ivDisLike;
	@ViewById(R.id.iv_per_like)
	ImageView ivLike;
	@ViewById(R.id.iv_per_sex)
	ImageView ivPerSex;
	@ViewById(R.id.tv_finish)
	TextView tvFinish;
	@ViewById(R.id.tv_per_sign)
	TextView tvPerSign;
	@ViewById(R.id.tv_per_short_introduce)
	TextView tvPerShortIntroduce;
	@ViewById(R.id.tv_per_worth)
	TextView tvPerWorth;
	@ViewById(R.id.iv_doubt)
	ImageView ivDoubt;
	@ViewById(R.id.iv_realname_nickname)
	ImageView ivRealNameOrNickName;
	@Bean
	ToastComponent toast;
	@ViewById(R.id.ll_talk_stranger)
	LinearLayout llTalkToStranger;
	@ViewById(R.id.rel_top_title_root)
	RelativeLayout relTitleRoot;
	@Bean
	GetStrangerInfo getStrangerInfo;
	private PerDataAdapter mPerDataAdapter;
	private String strangerDwID;
	private SendUrl mSendUrl;
	private String introduce, shortIntroduce;
	private List<ImageView> imgvs = new ArrayList<ImageView>();
	private IconPagerAdapter pagerAdapter;
	private StrangerFriendDetailInfo mSFDInfo;
	private boolean isVersionSame;// false不同，true相同
	// 储存头像的地址
	ArrayList<String> mUrls = new ArrayList<String>();
	private boolean isNickNameOrRealName;// 昵称为true实名为false
	private boolean isStrangerPage;// 判断是否是在陌生人界面
	private Handler mGetStrangerInfoHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			llTalkToStranger.setEnabled(true);
			switch (msg.what) {
			case 2222:
				isVersionSame = true;
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					String wealth = object.getString("wealth");
					parserJson(mSFDInfo.strangerFriendInfo, wealth);
				} catch (JSONException e) {
				}
				break;
			case 3333:
				isVersionSame = false;
				mSFDInfo.strangerFriendInfo = msg.obj.toString();
				mSFDInfo.save();
				parserJson(msg.obj.toString(), null);
				break;
			}
			verifyIconPath();
		};
	};

	/**
	 * 验证图片回调
	 */
	Handler loadIconHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String iconMain = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_MAIN);
			String iconExtra1 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_1);
			String iconExtra2 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_2);
			ArrayList<String> t = new ArrayList<String>();
			t.add(iconMain);
			t.add(iconExtra1);
			t.add(iconExtra2);
			switch (msg.what) {
			case 1:
				List<String> urls = (List<String>) msg.obj;
				if (urls != null && urls.size() > 0) {
					mUrls.clear();
					// 图片排序
					for (int i = 0; i < t.size(); i++) {
						for (String u : urls) {
							if (u.equals(t.get(i))) {
								mUrls.add(u);
								break;
							}
						}
					}
					for (int i = 0; i < mUrls.size(); i++) {
						ImageView view = new ImageView(mContext);
						view.setOnClickListener(mOnBackClick);
						view.setScaleType(ImageView.ScaleType.CENTER_CROP);
						imgvs.add(view);
					}
					pagerAdapter = new IconPagerAdapter(mUrls, imgvs);
					if (!isVersionSame) {
						pagerAdapter.clearUrl();
					}
					mPager.setAdapter(pagerAdapter);
				} else {
				}
				break;
			default:
				break;
			}
		}

	};

	@AfterViews
	public void init() {
		new EnterAsync().execute();
	}

	class EnterAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			NGetIntent();
			initPerDataPager();
			getStrangerInfo();
			initData();
		}
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

	public static final String POSITION = "POSITION";
	public static final String IS_STRANGER_PAGE = "IS_STRANGER_PAGE";
	public static final String LOCATION = "LOCATION";
	private int position;
	private String location;
	private int relationship;

	private void NGetIntent() {
		strangerDwID = getIntent().getStringExtra(Constants.DW_ID);
		position = getIntent().getIntExtra(POSITION, -1);
		location = getIntent().getStringExtra("location");
		isStrangerPage = getIntent().getBooleanExtra(IS_STRANGER_PAGE, false);
	}

	private InfoFragment infoFragment;
	private AchievementFragment achievementFragment;
	private WorksFragment worksFragment;
	private OnClickListener mOnBackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};

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
		mSFDInfo = StrangerFriendDetailInfo.queryById(strangerDwID);
		if (null == mSFDInfo) {
			mSFDInfo = new StrangerFriendDetailInfo(strangerDwID);
		}
		LogUtils.e("bm", "mSFDInfo--" + mSFDInfo.toString());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, strangerDwID);
		map.put("versionNum", mSFDInfo.versionNum);
		getStrangerInfo.getNearStrangerDetailInfo(map, mGetStrangerInfoHandler);
	}

	private void initData() {
		mSendUrl = new SendUrl(this);
		if (-1 == position) {
			ivDisLike.setVisibility(View.GONE);
			ivLike.setVisibility(View.GONE);
		}
		disableAutoScrollToBottom();
		relTitleRoot.setBackgroundColor(Color.parseColor("#00000000"));
		pScrollView.setMode(Mode.PULL_FROM_END);
		pScrollView.setScrollY(0);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setBackgroundColor(Color.parseColor("#50000000"));
		ivBack.setOnClickListener(this);
		tvPerWorth.setOnClickListener(this);
		ivDisLike.setOnClickListener(this);
		llTalkToStranger.setOnClickListener(this);
		llTalkToStranger.setEnabled(false);
		ivLike.setOnClickListener(this);
		ivAddFriend.setOnClickListener(this);
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
		if (ContactUserDao.isContact(strangerDwID)) {
			relationship = DWMessage.CHAT_RELATIONSHIP_FRIEND;
			ivAddFriend.setVisibility(View.GONE);
		} else {
			relationship = DWMessage.CHAT_RELATIONSHIP_STRANGER;
			ivAddFriend.setVisibility(View.VISIBLE);
		}
		if (isStrangerPage) {
			ivDisLike.setVisibility(View.VISIBLE);
			ivLike.setVisibility(View.VISIBLE);
		} else {
			ivDisLike.setVisibility(View.GONE);
			ivLike.setVisibility(View.GONE);
		}
		tvFinish.setText("发送名片");
		tvFinish.setVisibility(View.VISIBLE);
		tvFinish.setBackgroundColor(Color.parseColor("#90000000"));
		tvFinish.setOnClickListener(this);
	}

	private double ln, lt;
	private String worth, otherWorth, age, nickname, userType;
	private List<String> keyList = new ArrayList<String>();
	private List<String> valueList = new ArrayList<String>();

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

	private void parserJson(String jsonData, String wealth) {
		try {
			LogUtils.i(TAG, "jsonData=" + jsonData);
			JSONObject object = new JSONObject(jsonData);
			mSFDInfo.versionNum = "" + object.getInt("versionNum");
			mSFDInfo.save();
			if (object.has("个性签名")) {
				String sign = object.getString("个性签名");
				tvPerSign.setText(sign);
			} else {
				tvPerSign.setText("");
			}
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
			if (CommUtil.isNotBlank(location)) {
				Double loc = Double.valueOf(location);
				distance = ((int) (loc * 100)) / 100.0;
				setDistance(distance);
			} else {
				setDistance(distance);
			}
			worth = baseInfoObject.getString("身价");
			tvPerWorth.setText(worth);
			if (null == wealth) {
				wealth = baseInfoObject.getString("身家");
			}
			if ("-1.0".equals(wealth)) {
				tvWealth.setText("此地无银");
			} else {
				tvWealth.setText(wealth);
			}
			tvOccupation.setText(baseInfoObject.getString("行业"));
			otherWorth = worth;
			String sex = baseInfoObject.getString("性别");
			if ("男".equals(sex)) {
				ivPerSex.setImageResource(R.drawable.stranger_boy);
			} else {
				ivPerSex.setImageResource(R.drawable.stranger_girl);
			}
			age = baseInfoObject.getString("年龄");
			if ("被隐藏".equals(age)) {
				tvPerAge.setText("最好的年龄");
			} else {
				tvPerAge.setText(age);
			}
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
				tvPerShortIntroduce.setText(shortIntroduce);
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

	private void setDistance(double distance) {
		if (distance > 10000) {
			tvPerDistance.setText("对方已隐藏距离");
		} else {
			tvPerDistance.setText(distance + "km");
		}
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
			if (CommUtil.isBlank(nickname) || CommUtil.isBlank(strangerDwID)) {
				return;
			}
			intentToChatActivity();
			break;
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_add_friend:
			AddFriendDialog dialog = new AddFriendDialog();
			dialog.setOnListener(this);
			dialog.show(getSupportFragmentManager(), "dialog");
			break;
		case R.id.tv_finish:
			if (CommUtil.isBlank(nickname) || CommUtil.isBlank(strangerDwID)) {
				ToastUtil.showToast("获取信息失败");
				return;
			}
			intent = new Intent(this, PickContactForCallingCardActivity_.class);
			intent.putExtra(Constants.NAME, nickname);
			intent.putExtra(Constants.DW_ID, strangerDwID);
			startActivity(intent);
			break;
		case R.id.tv_per_worth:
			TrueOrFalseDialogFragment trueOrFalseDialogFragment = new TrueOrFalseDialogFragment();
			trueOrFalseDialogFragment.setOnTrueOrFalseClickListener(this);
			Float intWorth = Float.valueOf(worth);
			intWorth = intWorth * 3;
			trueOrFalseDialogFragment.setTips("看对方历史身价，要扣费哦。费用为对方当前身价的三倍！\r\n\r\n您当前的身价为" + worth + "\r\n需要扣除" + intWorth + "大洋");
			trueOrFalseDialogFragment.show(getSupportFragmentManager(), "trueOrFalseDialogFragment");
			break;
		}
	}

	private void intentToChatActivity() {
		try {
			Intent intent;
			intent = new Intent(mContext, ChatActivity_.class);
			LaunchChatEntity entity = new LaunchChatEntity(strangerDwID, nickname, Float.valueOf(worth),
					DWMessage.CHAT_TYPE_SINGLE, relationship, Integer.valueOf(userType));
			intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);

			startActivity(intent);
		} catch (NumberFormatException e) {

		}
	}

	@Override
	public void OnInfoClick(int position) {
		intentToChatActivity();
	}

	private void showToastInfo(final String info) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				toast.show(info);
			}
		});
	}

	@Override
	public void withReason(final String reason) {
		SearchResult searchResult = new SearchResult();
		searchResult.dwID = strangerDwID;
		searchResult.name = nickname;
		searchResult.worth = String.valueOf(worth);

		ContactEngine.getInstance().add(searchResult, reason, "3", new AddCallback() {
			public void onSuccess(String info) {
				Toast.makeText(NearCardDetailActivity.this, info, Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onFailure(String cause) {
				Toast.makeText(NearCardDetailActivity.this, cause, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onTrueOrFalseClick(TrueOrFalseDialogFragment dialog, View view) {
		switch (view.getId()) {
		case R.id.tv_cancel:
			break;
		case R.id.tv_ensure:
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("selfID", DecentWorldApp.getInstance().getDwID());
			map.put("dwID", strangerDwID);
			getStrangerInfo.getHistoryWorth(map, mHandler);
			break;
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			LogUtils.i("bm", "msg--" + msg.obj.toString());
			Intent intent = new Intent(NearCardDetailActivity.this, HistoryWorthActivity.class);
			intent.putExtra(Constants.HISTORY_WORTH, msg.obj.toString());
			startActivity(intent);
		};
	};

}
