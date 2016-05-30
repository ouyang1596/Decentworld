/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
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
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.IconPagerAdapter;
import cn.sx.decentworld.adapter.PerDataAdapter;
import cn.sx.decentworld.bean.OtherUserInfoField;
import cn.sx.decentworld.bean.StrangerFriendDetailInfo;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.bean.UserInfoField.FieldGroup;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.dialog.AddFriendDialog;
import cn.sx.decentworld.dialog.AddFriendDialog.AddFriendListener;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.dialog.InputNumberDialog;
import cn.sx.decentworld.dialog.InputNumberDialog.InputNumberListener;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment;
import cn.sx.decentworld.dialog.TrueOrFalseDialogFragment.OnTrueOrFalseClickListener;
import cn.sx.decentworld.engine.ContactEngine;
import cn.sx.decentworld.engine.ContactEngine.AddCallback;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.SearchResult;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.DWMessage.ChatRelationship;
import cn.sx.decentworld.entity.db.DWMessage.ChatType;
import cn.sx.decentworld.fragment.AchievementFragment;
import cn.sx.decentworld.fragment.InfoFragment;
import cn.sx.decentworld.fragment.InfoFragment.OnInfoClickListener;
import cn.sx.decentworld.fragment.WorksFragment;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.utils.DWUtils;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ImageUtils.VerifyUrlResultListener;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.widget.HackyViewPager;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
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
		OnTrueOrFalseClickListener, AddFriendListener, OnCommomPromptListener, OnPageChangeListener {
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
	@ViewById(R.id.tv_enjoy)
	TextView tvEnjoy;
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
	@ViewById(R.id.ll_talk_stranger)
	LinearLayout llTalkToStranger;
	@ViewById(R.id.rel_top_title_root)
	RelativeLayout relTitleRoot;
	@Bean
	GetStrangerInfo getStrangerInfo;
	private PerDataAdapter mPerDataAdapter;
	private String strangerDwID;
	private String introduce, shortIntroduce;
	private List<ImageView> imgvs = new ArrayList<ImageView>();
	private IconPagerAdapter pagerAdapter;
	private StrangerFriendDetailInfo mSFDInfo;
	private boolean isVersionSame;// false不同，true相同
	public String distance;
	private Map map;
	// 储存头像的地址
	ArrayList<String> mUrls = new ArrayList<String>();
	private boolean isNickNameOrRealName;// 昵称为true实名为false
	private boolean isStrangerPage;// 判断是否是在陌生人界面
	private boolean isRecommended;
	private Handler mGetStrangerInfoHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			llTalkToStranger.setEnabled(true);
			switch (msg.what) {
			case 2222:
				isVersionSame = true;
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					String wealth = object.getString("wealth");
					isRecommended = object.getBoolean("isRecommended");
					parser(mSFDInfo.strangerFriendInfo, wealth);
				} catch (JSONException e) {
					LogUtils.e(TAG, "解析错误 " + e.getLocalizedMessage());
				}
				break;
			case 3333:
				isVersionSame = false;
				mSFDInfo.strangerFriendInfo = msg.obj.toString();
				mSFDInfo.save();
				// parserJson(msg.obj.toString(), null);
				parser(msg.obj.toString(), null);
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
			String iconExtra3 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_3);
			String iconExtra4 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_4);
			String iconExtra5 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_5);
			ArrayList<String> t = new ArrayList<String>();
			t.add(iconMain);
			t.add(iconExtra1);
			t.add(iconExtra2);
			t.add(iconExtra3);
			t.add(iconExtra4);
			t.add(iconExtra5);
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
					int size = mUrls.size() + 2;
					LogUtils.d(TAG, "URL SIZE " + size);
					for (int i = 0; i < size; i++) {
						ImageView view = new ImageView(mContext);
						ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
								ViewGroup.LayoutParams.MATCH_PARENT);
						view.setLayoutParams(params);
						view.setScaleType(ImageView.ScaleType.FIT_XY);
						view.setOnClickListener(mOnBackClick);
						imgvs.add(view);
					}
					pagerAdapter = new IconPagerAdapter(mUrls, imgvs);
					if (!isVersionSame) {
						pagerAdapter.clearUrl();
					}
					mPager.setAdapter(pagerAdapter);
					mPager.setOnPageChangeListener(NearCardDetailActivity.this);
					// 设置viewpager在第二个视图显示
					mPager.setCurrentItem(1);
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

	private List<OtherUserInfoField> infos = new ArrayList<OtherUserInfoField>();
	private OtherUserInfoField socialInformation, myHobby, baseInfo;
	private SendUrl mSendUrl;

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
			getStrangerDetail();
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
		String iconExtra3 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_3);
		String iconExtra4 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_4);
		String iconExtra5 = ImageUtils.getIconByDwID(strangerDwID, ImageUtils.ICON_EXTRA_5);
		allUrls.add(iconMain);
		allUrls.add(iconExtra1);
		allUrls.add(iconExtra2);
		allUrls.add(iconExtra3);
		allUrls.add(iconExtra4);
		allUrls.add(iconExtra5);
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
		LogUtils.e(TAG, "getStrangerInfo mSFDInfo--" + mSFDInfo.toString());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, strangerDwID);
		map.put("versionNum", mSFDInfo.versionNum);
		getStrangerInfo.getNearStrangerDetailInfo(map, mGetStrangerInfoHandler);
	}

	private void getStrangerDetail() {
		mSFDInfo = StrangerFriendDetailInfo.queryById(strangerDwID);
		if (null == mSFDInfo) {
			mSFDInfo = new StrangerFriendDetailInfo(strangerDwID);
		} else {
			parser(mSFDInfo.strangerFriendInfo, null);
		}
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
		tvEnjoy.setOnClickListener(this);
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
			relationship = ChatRelationship.FRIEND.getIndex();
			ivAddFriend.setVisibility(View.GONE);
		} else {
			relationship = ChatRelationship.STRANGER.getIndex();
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

	protected void parser(String data, String wealth) {
		resetData();
		com.alibaba.fastjson.JSONObject json = JSON.parseObject(data);
		com.alibaba.fastjson.JSONObject object = json.getJSONObject("data");
		if (CommUtil.isBlank(wealth)) {
			isRecommended = object.getBoolean("isRecommended");
		}
		String infoString = object.getString("info");
		UserInfo info = JSON.parseObject(infoString, UserInfo.class);
		Log.d(TAG, info.toString());
		DWUtils.sortListByOrderNum(info.getInfos());
		DWUtils.sortListByGroupCode(info.getInfos());
		for (int i = 0; i < info.getInfos().size(); i++) {
			if (info.getInfos().get(i).getGroupCode() == 1) {
				if (null == baseInfo) {
					baseInfo = new OtherUserInfoField();
					baseInfo.setFieldName(FieldGroup.getFieldGroup(1).getGroupName());
					baseInfo.setFieldValue("");
					infos.add(baseInfo);
				}
				infos.add(info.getInfos().get(i));
			} else if (info.getInfos().get(i).getGroupCode() == 2) {
				if (null == socialInformation) {
					socialInformation = new OtherUserInfoField();
					socialInformation.setFieldName(FieldGroup.getFieldGroup(2).getGroupName());
					socialInformation.setFieldValue("");
					infos.add(socialInformation);
				}
				infos.add(info.getInfos().get(i));
			} else if (info.getInfos().get(i).getGroupCode() == 3) {
				if (null == myHobby) {
					myHobby = new OtherUserInfoField();
					myHobby.setFieldName(FieldGroup.getFieldGroup(3).getGroupName());
					myHobby.setFieldValue("");
					infos.add(myHobby);
				}
				infos.add(info.getInfos().get(i));
			}
		}
		infoFragment.upData(infos);
		mSFDInfo.versionNum = info.getVersionNum();
		mSFDInfo.save();
		if (CommUtil.isNotBlank(info.getFieldValue(Field.SIGN))) {
			tvPerSign.setText(info.getFieldValue(Field.SIGN));
		} else {
			tvPerSign.setText("");
		}
		distance = info.getFieldName(Field.LOCATION);
		if (CommUtil.isNotBlank(info.getFieldValue(Field.LOCATION))) {
			String[] split = info.getFieldValue(Field.LOCATION).split(" ");
			ln = Double.valueOf(split[0]);
			lt = Double.valueOf(split[1]);
			double distance = DWUtils.getDistance(LocationProvider.longitude, LocationProvider.latitude, ln, lt);
			distance = ((int) (distance * 100)) / 100.0;
			if (CommUtil.isNotBlank(location)) {
				Double loc = Double.valueOf(location);
				distance = ((int) (loc * 100)) / 100.0;
				setDistance(distance);
			} else {
				setDistance(distance);
			}
		} else {
			tvPerDistance.setText("对方已隐藏距离");
		}
		userType = info.getFieldValue(Field.USER_TYPE);
		if ("0".equals(userType)) {
			ivDoubt.setVisibility(View.VISIBLE);
			ivDoubt.setImageResource(R.drawable.stranger_doubt_shadow);
		} else if ("1".equals(userType)) {
			ivDoubt.setVisibility(View.GONE);
		} else if ("2".equals(userType)) {
			ivDoubt.setVisibility(View.VISIBLE);
			ivDoubt.setImageResource(R.drawable.stranger_wan);
		}
		worth = info.getFieldValue(Field.WORTH);
		tvPerWorth.setText(worth);
		if (null == wealth) {
			wealth = info.getFieldValue(Field.WEALTH);
		}
		if ("-1.0".equals(wealth)) {
			tvWealth.setText("此地无银");
		} else {
			tvWealth.setText(wealth);
		}
		tvOccupation.setText(info.getFieldValue(Field.OCCUPATION));
		otherWorth = worth;
		String sex = info.getFieldValue(Field.GENDER);
		if ("男".equals(sex)) {
			ivPerSex.setImageResource(R.drawable.stranger_boy);
		} else {
			ivPerSex.setImageResource(R.drawable.stranger_girl);
		}
		age = info.getFieldValue(Field.AGE);
		if (null == age || "被隐藏".equals(age) || "".equals(age)) {
			tvPerAge.setText("最好的年龄");
		} else {
			tvPerAge.setText(age);
		}
		if (CommUtil.isNotBlank(info.getFieldValue(Field.NICKNAME))) {
			isNickNameOrRealName = true;
			nickname = info.getFieldValue(Field.NICKNAME);
		}
		if (null != nickname && !"".equals(nickname)) {
			tvPerName.setText(nickname);
		}
		introduce = info.getFieldValue(Field.INTRODUCE);
		shortIntroduce = info.getFieldValue(Field.SHORT_INTRODUCE);
		if (CommUtil.isNotBlank(shortIntroduce)) {
			tvPerShortIntroduce.setText(shortIntroduce);
		}
		achievementFragment.setIntroduction(shortIntroduce, introduce);
		if (CommUtil.isNotBlank(info.getFieldValue(Field.REALNAME))) {
			if (!"隐藏".equals(info.getFieldValue(Field.REALNAME))) {
				isNickNameOrRealName = false;
				nickname = info.getFieldValue(Field.REALNAME);
			}
		}
		if (isNickNameOrRealName) {
			ivRealNameOrNickName.setImageResource(R.drawable.stranger_nick);
		} else {
			ivRealNameOrNickName.setImageResource(R.drawable.stranger_real);
		}
		if (null != nickname && !"".equals(nickname)) {
			tvPerName.setText(nickname);
		}
		if (isRecommended) {
			tvEnjoy.setVisibility(View.GONE);
		} else {
			tvEnjoy.setVisibility(View.VISIBLE);
		}
	}

	private void resetData() {
		infos.clear();
		baseInfo = null;
		socialInformation = null;
		myHobby = null;
	}

	private void setDistance(double distance) {
		if (distance > 10000) {
			tvPerDistance.setText("对方已隐藏距离");
		} else {
			tvPerDistance.setText(distance + "km");
		}
	}

	private CommomPromptDialogFragment mCommomPromptDialog;

	private void showCommomDialog(int enter, String prompt) {
		if (null == mCommomPromptDialog) {
			mCommomPromptDialog = new CommomPromptDialogFragment();
		}
		mCommomPromptDialog.setEnter(enter);
		mCommomPromptDialog.setOnCommomPromptListener(this);
		mCommomPromptDialog.setTips(prompt);
		mCommomPromptDialog.show(getSupportFragmentManager(), "mCommomPromptDialog");
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.iv_per_like:
			if (FileUtils.getPromptStatus(this, Constants.LIKE) == CommomPromptDialogFragment.LIKE) {
				intent = new Intent();
				intent.putExtra(POSITION, position);
				setResult(Constants.RESULT_CODE_LIKE, intent);
				finish();
				return;
			}
			showCommomDialog(CommomPromptDialogFragment.LIKE,
					"原来你喜欢他，你的一块大洋，将飞到他身边；他同样喜欢你的时候，将得到你的大洋，并建立匹配对话框。有那么巧？呵呵，让大洋先飞一会吧。大洋探路，实在不行，三天飞回身家！");
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
			if (relationship == ChatRelationship.FRIEND.getIndex()) {
				if (FileUtils.getPromptStatus(this, Constants.FRIEND) == CommomPromptDialogFragment.FRIEND) {
					intentToChatActivity();
				} else {
					showCommomDialog(CommomPromptDialogFragment.FRIEND, "你与他是朋友关系，他与你每说一句话，将按照他的身价向你付费。同理，注意你的身价哦！");
				}
			} else if (relationship == ChatRelationship.STRANGER.getIndex()) {
				if (FileUtils.getPromptStatus(this, Constants.STRANGER) == CommomPromptDialogFragment.STRANGER) {
					intentToChatActivity();
				} else {
					showCommomDialog(CommomPromptDialogFragment.STRANGER,
							"你与他是陌生人关系，每人前三句是不会得到费用的。之后他与你每说一句话，将按照你的身价向你付费。同理，注意他的身价哦！");
				}
			}
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
		case R.id.tv_enjoy:
			if (FileUtils.getPromptStatus(this, Constants.RECOMMEND) == CommomPromptDialogFragment.RECOMMEND) {
				isRecommended(strangerDwID);
				return;
			}
			showCommomDialog(CommomPromptDialogFragment.RECOMMEND, Constants.RECOMMEND_PROMPT);
			break;
		}
	}

	private void intentToChatActivity() {
		try {
			Intent intent;
			intent = new Intent(mContext, ChatActivity_.class);
			LaunchChatEntity entity = new LaunchChatEntity(strangerDwID, nickname, Float.valueOf(worth),
					ChatType.SINGLE.getIndex(), relationship, Integer.valueOf(userType));
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
				ToastUtil.showToast(info);
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
				ToastUtil.showToast(info);
				finish();
			}

			@Override
			public void onFailure(String cause) {
				ToastUtil.showToast(cause);
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
			Intent intent = new Intent(NearCardDetailActivity.this, HistoryWorthActivity.class);
			intent.putExtra(Constants.HISTORY_WORTH, msg.obj.toString());
			startActivity(intent);
		};
	};

	@Override
	public void onCommomPromtClick(View view) {
		switch (mCommomPromptDialog.getEnter()) {
		case CommomPromptDialogFragment.LIKE:
			Intent intent = new Intent();
			intent.putExtra(POSITION, position);
			setResult(Constants.RESULT_CODE_LIKE, intent);
			finish();
			break;
		case CommomPromptDialogFragment.RECOMMEND:
			isRecommended(strangerDwID);
			break;
		default:
			intentToChatActivity();
			break;
		}
	}

	private Handler mIsRecommendedHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			recommendByID();
		};
	};

	private void isRecommended(String dwID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, dwID);
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_IS_RECOMMENDED, Method.POST,
				new HttpCallBack() {

					@Override
					public void onSuccess(String resultJSON, ResultBean resultBean) {
						LogUtils.d(TAG, "isRecommended " + resultJSON);
						if (2222 == resultBean.getResultCode()) {
							mIsRecommendedHandler.sendEmptyMessage(0);
						} else {
							showToastInfo(resultBean.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						showToastInfo(Constants.NET_WRONG);
					}
				});
	}

	private Handler recommendByIDHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ToastUtil.showToast("推荐成功");
			try {
				JSONObject object = new JSONObject(msg.obj.toString());
				String wealth = object.getString("wealth");
				SelfInfoManager.getInstance().notifyWealthChanged(Float.valueOf(wealth));
			} catch (JSONException e) {
				LogUtils.d(TAG, "error " + e.getLocalizedMessage());
			}
		};
	};

	private void recommendByID() {
		InputNumberDialog dialog = new InputNumberDialog();
		dialog.setTitle("请输入推荐金额");
		dialog.setHint("请输入金额(不小于10大洋)");
		dialog.setMinNum(10);
		dialog.setListener(new InputNumberListener() {
			@Override
			public void confirm(int money) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Constants.DW_ID, strangerDwID);
				map.put("recommenderID", DecentWorldApp.getInstance().getDwID());
				map.put("amount", "" + money);
				mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_RECOMMENDED_BY_ID, Method.POST,
						new HttpCallBack() {

							@Override
							public void onSuccess(String resultJSON, ResultBean resultBean) {
								LogUtils.d(TAG, "recommendByID " + resultJSON);
								if (2222 == resultBean.getResultCode()) {
									Message message = recommendByIDHandler.obtainMessage();
									message.obj = resultBean.getData();
									recommendByIDHandler.sendMessage(message);
								} else {
									showToastInfo(resultBean.getMsg());
								}
							}

							@Override
							public void onFailure(String e) {
								showToastInfo(Constants.NET_WRONG);
							}
						});
			}
		});
		dialog.show(getSupportFragmentManager(), "publish");
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		LogUtils.d(TAG, "POSITION " + arg0);
		int pageIndex = arg0;
		if (arg0 == 0) {
			// 当视图在第一个时，将页面号设置为图片的最后一张。
			pageIndex = mUrls.size();
		} else if (arg0 == mUrls.size() + 1) {
			// 当视图在最后一个是,将页面号设置为图片的第一张。
			pageIndex = 1;
		}

		if (arg0 != pageIndex) {
			mPager.setCurrentItem(pageIndex, false);
			return;
		}
	}

}
