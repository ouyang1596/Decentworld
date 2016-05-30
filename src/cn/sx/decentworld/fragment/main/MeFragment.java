/**
 * 
 */
package cn.sx.decentworld.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.DoubtActivity_;
import cn.sx.decentworld.activity.MainActivity;
import cn.sx.decentworld.activity.MeSettingActivity_;
import cn.sx.decentworld.activity.RechargeAliPayWXActivity_;
import cn.sx.decentworld.activity.RechargeBenefitActivity_;
import cn.sx.decentworld.adapter.IconPagerAdapter;
import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.UserDataEngine;
import cn.sx.decentworld.engine.UserDataEngine.GetWealthListener;
import cn.sx.decentworld.entity.SelfUserField;
import cn.sx.decentworld.entity.SelfUserInfo;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.entity.db.SelfExtraInfo;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.fragment.me.AchievementFragment;
import cn.sx.decentworld.fragment.me.AchievementFragment_;
import cn.sx.decentworld.fragment.me.InnateFragment;
import cn.sx.decentworld.fragment.me.InnateFragment_;
import cn.sx.decentworld.fragment.me.WorkFragment;
import cn.sx.decentworld.fragment.me.WorkFragment_;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfExtraInfoManager;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ImageUtils.VerifyUrlResultListener;
import cn.sx.decentworld.widget.CustomScrollView;
import cn.sx.decentworld.widget.CustomScrollView.ScrollViewListener;
import cn.sx.decentworld.widget.HackyViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: MeFragment.java
 * @Description:我的信息界面
 * @author: cj
 * @date: 2016年1月11日 上午11:17:51
 */
@EFragment(R.layout.fragment_me)
public class MeFragment extends BaseFragment implements OnClickListener, GetWealthListener, ScrollViewListener,
		android.support.v4.view.ViewPager.OnPageChangeListener {
	/**
	 * 常量
	 */
	private static final String TAG = "MeFragment";
	/**
	 * 工具类
	 */
	@Bean
	ToastComponent toast;
	@Bean
	GetUserInfo getUserInfo;
	@ViewById(R.id.tv_me_worth)
	TextView mTvWorth;
	@ViewById(R.id.vp_me_icon)
	HackyViewPager mPager;
	// 用户类型
	@ViewById(R.id.iv_me_doubt)
	ImageView mIvUserType;
	@ViewById(R.id.iv_me_recharge)
	ImageView iv_me_recharge;
	@ViewById(R.id.iv_me_setting)
	ImageView iv_me_setting;
	// 显示的名字
	@ViewById(R.id.tv_me_name)
	TextView tv_me_name;
	// 显示名字类型（实名、昵称）
	@ViewById(R.id.iv_me_name_type)
	ImageView iv_me_name_type;
	// 年龄
	@ViewById(R.id.tv_me_age)
	TextView tv_me_age;
	@ViewById(R.id.tv_me_gender)
	TextView tv_me_gender;
	@ViewById(R.id.tv_me_distance)
	TextView tv_me_distance;
	// 职业
	@ViewById(R.id.tv_me_occupation)
	TextView tv_me_occupation;
	// 一句话简介
	@ViewById(R.id.tv_me_short_introduce)
	TextView tv_me_short_introduce;
	// 个性签名
	@ViewById(R.id.tv_me_sign)
	TextView tv_me_sign;
	@ViewById(R.id.tv_me_innate)
	TextView tv_me_innate;
	@ViewById(R.id.tv_me_achievement)
	TextView tv_me_achievement;
	@ViewById(R.id.tv_me_work)
	TextView tv_me_work;
	// 性别图标
	@ViewById(R.id.iv_me_gender)
	ImageView ivGender;
	@ViewById(R.id.sv_chat_me)
	CustomScrollView cScroll;
	// 身家容器
	@ViewById(R.id.ll_me_wealth)
	LinearLayout mLlWealthContainer;
	@ViewById(R.id.tv_me_wealth)
	TextView mTvWealth;
	@ViewById(R.id.ll)
	LinearLayout ll;
	/**
	 * 变量
	 */
	/** 用户ID **/
	private String userID;
	private int index = 1;
	/** 顶部头像 **/
	private List<String> iconUrls;
	private List<ImageView> iconImgViews;
	private IconPagerAdapter pagerAdapter;
	/** Fragment切换 **/
	private FragmentManager fragmentManager;
	private InnateFragment innateFragment;
	private AchievementFragment achievementFragment;
	private WorkFragment workFragment;
	private boolean isPromptShow;
	public static final int WORTH_REQUESTCODE = 100;

	/**
	 * 入口
	 */
	@AfterViews
	void init() {
		initVar();
		initListener();
		initView();
		setTabSelection(index);
		isPrepared = true;
		lazyLoad();
		if (DecentWorldApp.isFirst != 0) {
			initPopLeft();
			initPopRight();
		}
	}

	private void initPopEdit() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						View view = View.inflate(getActivity(), R.layout.item_navigation, null);
						final PopupWindow mPop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						mPop.setBackgroundDrawable(new ColorDrawable(0xffffff));
						mPop.setOutsideTouchable(true);
						mPop.showAsDropDown(innateFragment.getIvEdit(), 0, 0);
						TextView tvNavigation = (TextView) view.findViewById(R.id.tv_navigation);
						tvNavigation.setText("完善头像、资料");
						ImageView ivPromptTop = (ImageView) view.findViewById(R.id.iv_prompt_top);
						ivPromptTop.setVisibility(View.VISIBLE);
						LinearLayout llNv = (LinearLayout) view.findViewById(R.id.ll_nv);
						llNv.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								mPop.dismiss();
							}
						});
					}
				});
			}
		}).start();
	}

	private void initPopAchievement() {
		View view = View.inflate(getActivity(), R.layout.item_navigation, null);
		final PopupWindow mPop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPop.setBackgroundDrawable(new ColorDrawable(0xffffff));
		mPop.setOutsideTouchable(true);
		mPop.showAsDropDown(tv_me_achievement, -50, -420);
		TextView tvNavigation = (TextView) view.findViewById(R.id.tv_navigation);
		tvNavigation.setText("完善名片，提高收入");
		ImageView ivPromptBottom = (ImageView) view.findViewById(R.id.iv_prompt_bottom);
		ivPromptBottom.setVisibility(View.VISIBLE);
		LinearLayout llNv = (LinearLayout) view.findViewById(R.id.ll_nv);
		llNv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPop.dismiss();
			}
		});
	}

	private void initPopLeft() {
		View view = View.inflate(getActivity(), R.layout.item_navigation2, null);
		final PopupWindow mPop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPop.setBackgroundDrawable(new ColorDrawable(0xffffff));
		mPop.setOutsideTouchable(true);
		mPop.showAtLocation(ll, Gravity.LEFT, 0, 50);
		TextView tvNavigation = (TextView) view.findViewById(R.id.tv_navigation);
		tvNavigation.setText("滑动去看陌生人");
		ImageView ivNext = (ImageView) view.findViewById(R.id.iv_next_left);
		ivNext.setVisibility(View.VISIBLE);
		ivNext.setImageResource(R.drawable.next_left);
		LinearLayout llNv = (LinearLayout) view.findViewById(R.id.ll_nv);
		llNv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPop.dismiss();
			}
		});
	}

	private void initPopRight() {
		View view = View.inflate(getActivity(), R.layout.item_navigation2, null);
		final PopupWindow mPop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPop.setBackgroundDrawable(new ColorDrawable(0xffffff));
		mPop.setOutsideTouchable(true);
		mPop.showAtLocation(ll, Gravity.RIGHT, 0, 50);
		TextView tvNavigation = (TextView) view.findViewById(R.id.tv_navigation);
		tvNavigation.setText("滑动到聊天室");
		ImageView ivNext = (ImageView) view.findViewById(R.id.iv_next_right);
		ivNext.setVisibility(View.VISIBLE);
		ivNext.setImageResource(R.drawable.next);
		LinearLayout llNv = (LinearLayout) view.findViewById(R.id.ll_nv);
		llNv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPop.dismiss();
			}
		});
	}

	/**
	 * 初始化变量
	 */
	private void initVar() {
		EventBus.getDefault().register(this);
		userID = DecentWorldApp.getInstance().getDwID();
		fragmentManager = getChildFragmentManager();
		iconUrls = new ArrayList<String>();
		iconImgViews = new ArrayList<ImageView>();
	}

	private void disableAutoScrollToBottom() {
		cScroll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		cScroll.setFocusable(true);
		cScroll.setFocusableInTouchMode(true);
		cScroll.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.requestFocusFromTouch();
				return false;
			}
		});
		cScroll.setOnScrollViewChangeListener(this);
	}

	/**
	 * 验证图片地址是否有效
	 */
	private void verifyIconPath() {
		LogUtils.v(TAG, "verifyIconPath() ");
		SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
		ArrayList<String> allUrls = new ArrayList<String>();
		allUrls.add(extraInfo.getIcon());
		allUrls.add(extraInfo.getIcon2());
		allUrls.add(extraInfo.getIcon3());
		allUrls.add(extraInfo.getIcon4());
		LogUtils.d(TAG, "IMAGEURL " + extraInfo.getIcon4());
		allUrls.add(extraInfo.getIcon5());
		allUrls.add(extraInfo.getIcon6());
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

	/**
	 * 验证图片回调
	 */
	Handler loadIconHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
			ArrayList<String> t = new ArrayList<String>();
			t.add(extraInfo.getIcon());
			t.add(extraInfo.getIcon2());
			t.add(extraInfo.getIcon3());
			t.add(extraInfo.getIcon4());
			t.add(extraInfo.getIcon5());
			t.add(extraInfo.getIcon6());
			switch (msg.what) {
			case 1:
				List<String> urls = (List<String>) msg.obj;
				if (urls != null && urls.size() > 0) {
					iconUrls.clear();
					// 图片排序
					for (int i = 0; i < t.size(); i++) {
						for (String u : urls) {
							if (u.equals(t.get(i))) {
								iconUrls.add(u);
								break;
							}
						}
					}
					// 加载图片
					initViewPager(iconUrls);
				}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 初始化头像
	 */
	private void initViewPager(List<String> urls) {
		iconImgViews.clear();
		try {
			for (int i = 0; i < urls.size() + 2; i++) {
				ImageView v = new ImageView(getActivity());
				v.setScaleType(ImageView.ScaleType.CENTER_CROP);
				iconImgViews.add(v);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, "initViewPager() Exception=" + e.toString());
		}
		pagerAdapter = new IconPagerAdapter(iconUrls, iconImgViews);
		mPager.setAdapter(pagerAdapter);
		mPager.setOnPageChangeListener(this);
		mPager.setCurrentItem(1);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		disableAutoScrollToBottom();
	}

	private void initHeader() {
		// 初始化我的界面头部数据
		SelfUserInfo selfUserInfo = SelfInfoManager.getInstance().getSelfUserInfo();
	   
		if (selfUserInfo != null) {
			/** 显示昵称 **/
			SelfUserField nickNameField = selfUserInfo.getSelfUserField(Field.NICKNAME);
			if (nickNameField != null) {
				boolean displayAuth = nickNameField.isDisplayAuth();
				if (displayAuth) {
					String fieldValue = nickNameField.getFieldValue();
					if (CommUtil.isNotBlank(fieldValue)) {
						tv_me_name.setText(fieldValue);
						iv_me_name_type.setImageResource(R.drawable.stranger_nick);
					}
				}
			}

			/** 显示实名 **/
			SelfUserField realNameField = selfUserInfo.getSelfUserField(Field.REALNAME);
			if (realNameField != null) {
				boolean displayAuth = realNameField.isDisplayAuth();
				if (displayAuth) {
					String fieldValue = realNameField.getFieldValue();
					if (CommUtil.isNotBlank(fieldValue)) {
						tv_me_name.setText(fieldValue);
						iv_me_name_type.setImageResource(R.drawable.stranger_real);
					}
				}
			}

			// 先不显示距离
			tv_me_distance.setVisibility(View.GONE);
			/** 年龄，若为-1，则显示"最好的年龄"五个字 **/
			SelfUserField ageField = selfUserInfo.getSelfUserField(Field.AGE);
			if (ageField != null) {
				String fieldValue = ageField.getFieldValue();
				if (CommUtil.isNotBlank(fieldValue)) {
					if (!fieldValue.equals("-1"))
						tv_me_age.setText(fieldValue);
					else
						tv_me_age.setText(getResources().getString(R.string.default_age_description));
				}
			}

			/** 性别 **/
			SelfUserField genderField = selfUserInfo.getSelfUserField(Field.GENDER);
			if (genderField != null) {
				// 服务器返回“男”、“女”；
				String fieldValue = genderField.getFieldValue();
				if (CommUtil.isNotBlank(fieldValue)) {
					if (fieldValue.equals("男"))
						ivGender.setImageResource(R.drawable.com_gender_man);
					else
						ivGender.setImageResource(R.drawable.com_gender_women);
				}
			}

			/** 职业 **/
			SelfUserField occupationField = selfUserInfo.getSelfUserField(Field.OCCUPATION);
			if (occupationField != null) {
				String fieldValue = occupationField.getFieldValue();
				if (CommUtil.isNotBlank(fieldValue))
					tv_me_occupation.setText(fieldValue);
				else
					tv_me_occupation.setVisibility(View.GONE);
			}
			
		     //一句话简介
			 String shortIntroduce = SelfInfoManager.getInstance().getShortIntroduce();
			 if(CommUtil.isNotBlank(shortIntroduce))
			     tv_me_short_introduce.setText(shortIntroduce);
			 else
			     tv_me_short_introduce.setVisibility(View.GONE);
			 
			 //个性签名 ，没有则显示"未设置个性签名"七个字
			 String sign = SelfInfoManager.getInstance().getSign();
			 tv_me_sign.setText(sign);

			/** 是否显示身家 **/
			SelfUserField wealthField = selfUserInfo.getSelfUserField(Field.WEALTH);
			if (wealthField != null) {
				boolean displayAuth = wealthField.isDisplayAuth();
				if (displayAuth) {
					String fieldValue = wealthField.getFieldValue();
					mLlWealthContainer.setVisibility(View.VISIBLE);
					mTvWealth.setText(fieldValue);
				} else {
					mLlWealthContainer.setVisibility(View.VISIBLE);
					mTvWealth.setText("此地无银");
				}
			}

			/** 显示身价 **/
			SelfUserField worthField = selfUserInfo.getSelfUserField(Field.WORTH);
			if (worthField != null) {
				String fieldValue = worthField.getFieldValue();
				mTvWorth.setText(fieldValue);
			}

			/** 设置用户类型 **/
			SelfUserField userTypeField = selfUserInfo.getSelfUserField(Field.USER_TYPE);
			if (userTypeField != null) {
				// 服务器返回“0”、“1”、“2”
				String fieldValue = userTypeField.getFieldValue();
				setUserType(Integer.valueOf(fieldValue));
			}
		}
	}

	/**
	 * 加载数据
	 */
	@Override
	protected void lazyLoad() {
		LogUtils.v(TAG, "lazyLoad");

	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		mTvWorth.setOnClickListener(this);
		mIvUserType.setOnClickListener(this);
		iv_me_recharge.setOnClickListener(this);
		iv_me_setting.setOnClickListener(this);
		tv_me_innate.setOnClickListener(this);
		tv_me_achievement.setOnClickListener(this);
		tv_me_work.setOnClickListener(this);
	}

	@Override
	public void turnToTab(int tab) {

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.iv_me_doubt:
			intent = new Intent(getActivity(), DoubtActivity_.class);
			startActivityForResult(intent, Constants.REQUEST_CODE);
			break;
		case R.id.iv_me_recharge:
			/** 跳转到充值与收益 界面 **/
			intent = new Intent(getActivity(), RechargeBenefitActivity_.class);
			startActivity(intent);
			break;
		case R.id.iv_me_setting:
			/** 跳转到看自己的设置界面 **/
			intent = new Intent(getActivity(), MeSettingActivity_.class);
			startActivity(intent);
			break;
		case R.id.tv_me_innate:
			setTabSelection(1);
			break;
		case R.id.tv_me_achievement:
			setTabSelection(2);
			break;
		case R.id.tv_me_work:
			setTabSelection(3);
			break;
		case R.id.tv_me_worth:
			intent = new Intent(getActivity(), RechargeAliPayWXActivity_.class);
			startActivity(intent);
			break;
		}
	}

	/**
	 * 选择
	 */
	private void setTabSelection(int index) {
		setTabDefaultBackground();
		/** 开启一个Fragment事务 **/
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		/** 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况 **/
		hideAllFragment(transaction);
		switch (index) {
		case 1:
			/** 天生 **/
			tv_me_innate.setTextColor(getResources().getColor(R.color.new_blue));
			if (innateFragment == null) {
				innateFragment = new InnateFragment_();
				transaction.add(R.id.fl_me_container, innateFragment);
			} else {
				transaction.show(innateFragment);
			}
			break;
		case 2:
			/** 成就 **/
			tv_me_achievement.setTextColor(getResources().getColor(R.color.new_blue));
			if (achievementFragment == null) {
				achievementFragment = new AchievementFragment_();
				transaction.add(R.id.fl_me_container, achievementFragment);
			} else {
				transaction.show(achievementFragment);
			}
			break;
		case 3:
			/** 作品 **/
			tv_me_work.setTextColor(getResources().getColor(R.color.new_blue));
			if (workFragment == null) {
				workFragment = new WorkFragment_();
				transaction.add(R.id.fl_me_container, workFragment);
			} else {
				transaction.show(workFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	/**
	 * 隐藏所有的Fragment
	 */
	private void hideAllFragment(FragmentTransaction transaction) {
		if (innateFragment != null)
			transaction.hide(innateFragment);
		if (achievementFragment != null)
			transaction.hide(achievementFragment);
		if (workFragment != null)
			transaction.hide(workFragment);
	}

	/**
	 * 设置默认状态
	 */
	private void setTabDefaultBackground() {
		tv_me_innate.setTextColor(getResources().getColor(R.color.black));
		tv_me_achievement.setTextColor(getResources().getColor(R.color.black));
		tv_me_work.setTextColor(getResources().getColor(R.color.black));
	}

	/**
	 * 更新数据
	 */
	@Override
	public void onResume() {
		super.onResume();
		LogUtils.v(TAG, "onResume() ");
		verifyIconPath();
		initHeader();
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		setUserType(DecentWorldApp.doubtWan);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 更新身家
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_WEALTH)
	public void refreshWealth(String info) {
		LogUtils.v(TAG, "refreshWealth..." + info);
		UserDataEngine.getInstance().getWealth(this);
	}

	/**
	 * 获取身家完成
	 */
	@Override
	public void onGetWealthFinished(String wealth) {
		mTvWealth.setText("" + wealth);
	}

	@Override
	public void onYScrolled(int scrollY) {
		if (scrollY >= 200) {
			if (DecentWorldApp.isFirst != 0) {
				if (!isPromptShow) {
					initPopAchievement();
					initPopEdit();
					MainActivity mActivity = (MainActivity) getActivity();
					mActivity.getViewMask().setVisibility(View.VISIBLE);
					isPromptShow = true;
				}
			}
		}
	}

	/**
	 * 设置用户类型
	 * 
	 * @param userType
	 */
	private void setUserType(int userType) {
		if (userType == ContactUser.UserType.IMPEACH.getIndex()) {
			mIvUserType.setImageResource(R.drawable.stranger_doubt_shadow);
		} else if (userType == ContactUser.UserType.NORMAL.getIndex()) {
			mIvUserType.setImageResource(R.drawable.stranger_true);
		} else if (userType == ContactUser.UserType.VIP.getIndex()) {
			mIvUserType.setImageResource(R.drawable.stranger_wan);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		int pageIndex = arg0;
		if (arg0 == 0) {
			// 当视图在第一个时，将页面号设置为图片的最后一张。
			pageIndex = iconUrls.size();
		} else if (arg0 == iconUrls.size() + 1) {
			// 当视图在最后一个是,将页面号设置为图片的第一张。
			pageIndex = 1;
		}

		if (arg0 != pageIndex) {
			mPager.setCurrentItem(pageIndex, false);
			return;
		}
	}
}
