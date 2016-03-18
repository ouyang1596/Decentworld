/**
 * 
 */
package cn.sx.decentworld.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.DoubtActivity_;
import cn.sx.decentworld.activity.MeSettingActivity_;
import cn.sx.decentworld.activity.RechargeAliPayWXActivity_;
import cn.sx.decentworld.activity.RechargeBenefitActivity_;
import cn.sx.decentworld.adapter.IconPagerAdapter;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserExtraInfo;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.fragment.me.AchievementFragment;
import cn.sx.decentworld.fragment.me.AchievementFragment_;
import cn.sx.decentworld.fragment.me.InnateFragment;
import cn.sx.decentworld.fragment.me.InnateFragment_;
import cn.sx.decentworld.fragment.me.WorkFragment;
import cn.sx.decentworld.fragment.me.WorkFragment_;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ImageUtils.VerifyUrlResultListener;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;
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
public class MeFragment extends BaseFragment implements OnClickListener {
	/**
	 * 常量
	 */
	private static final String TAG = "MeFragment";
	private static final int GET_USER_WORTH = 1;
	/**
	 * 工具类
	 */
	@Bean
	ToastComponent toast;
	@Bean
	GetUserInfo getUserInfo;
	@ViewById(R.id.tv_me_worth)
	TextView tvWorth;
	@ViewById(R.id.vp_me_icon)
	HackyViewPager mPager;
	@ViewById(R.id.iv_me_doubt)
	ImageView iv_me_doubt;
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
	@ViewById(R.id.tv_me_occupation)
	TextView tv_me_occupation;
	@ViewById(R.id.tv_me_worth)
	TextView tv_me_worth;
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

	// 身家容器
	@ViewById(R.id.ll_me_wealth)
	LinearLayout llWealth;
	@ViewById(R.id.tv_me_wealth)
	TextView tvWealth;

	/**
	 * 变量
	 */
	/** 用户ID **/
	private String userID;
	private int index = 1;
	/** 顶部头像 **/
	private List<String> picUrls;
	private List<ImageView> imgvs;
	private IconPagerAdapter pagerAdapter;
	/** Fragment切换 **/
	private FragmentManager fragmentManager;
	private InnateFragment innateFragment;
	private AchievementFragment achievementFragment;
	private WorkFragment workFragment;

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
	}

	/**
	 * 初始化变量
	 */
	private void initVar() {
		// 注册订阅
		EventBus.getDefault().register(this);
		userID = DecentWorldApp.getInstance().getDwID();
		// 修复Fragment不显示的问题
		// fragmentManager = getFragmentManager();
		fragmentManager = getChildFragmentManager();
		picUrls = new ArrayList<String>();
		imgvs = new ArrayList<ImageView>();
		pagerAdapter = new IconPagerAdapter(picUrls, imgvs);
		mPager.setAdapter(pagerAdapter);
		verifyIconPath();
	}

	public ImageView getIvMeDoubt() {
		return iv_me_doubt;
	}

	/**
	 * 验证图片地址是否有效
	 */
	private void verifyIconPath() {
		ArrayList<String> allUrls = new ArrayList<String>();
		String iconMain = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_MAIN);
		String iconExtra1 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_1);
		String iconExtra2 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_2);
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

	/**
	 * 验证图片回调
	 */
	// Handler loadIconHandler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// switch (msg.what) {
	// case 1:
	// List<String> urls = (List<String>) msg.obj;
	// if (urls != null && urls.size() > 0) {
	// picUrls.clear();
	// for (String u : urls) {
	// picUrls.add(u);
	// }
	// initViewPager(picUrls);
	// // toast.show("有效图片" + urls.size() + "张");
	// } else {
	// toast.show("有效图片为0");
	// }
	// break;
	// default:
	// break;
	// }
	// };
	// };

	// <<<<<<< .mine
	/**
	 * 验证图片回调
	 */
	Handler loadIconHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String iconMain = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_MAIN);
			String iconExtra1 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_1);
			String iconExtra2 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_2);
			ArrayList<String> t = new ArrayList<String>();
			t.add(iconMain);
			t.add(iconExtra1);
			t.add(iconExtra2);
			switch (msg.what) {
			case 1:
				List<String> urls = (List<String>) msg.obj;
				if (urls != null && urls.size() > 0) {
					picUrls.clear();
					// 图片排序
					for (int i = 0; i < t.size(); i++) {
						for (String u : urls) {
							if (u.equals(t.get(i))) {
								picUrls.add(u);
								break;
							}
						}
					}
					// 加载图片
					initViewPager(picUrls);
					if (LogUtils.IS_TOAST_TEST_CONN) {
						toast.show("有效图片" + urls.size() + "张");
					}
				} else {
					if (LogUtils.IS_TOAST_TEST_CONN) {
						toast.show("有效图片为0");
					}
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
		String iconMain = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_MAIN);
		String iconExtra1 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_1);
		String iconExtra2 = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_EXTRA_2);
		UserExtraInfo extraInfo = UserExtraInfo.queryBy(userID);
		if (extraInfo == null)
			extraInfo = new UserExtraInfo();

		for (int i = 0; i < urls.size(); i++) {
			ImageView v = new ImageView(getActivity());
			v.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imgvs.add(v);

			if (urls.get(i).equals(iconMain)) {
				extraInfo.setIcon(iconMain);
			} else if (urls.get(i).equals(iconExtra1)) {
				extraInfo.setIcon2(iconExtra1);
			} else if (urls.get(i).equals(iconExtra2)) {
				extraInfo.setIcon3(iconExtra2);
			}
			extraInfo.save();
		}
		if (null != pagerAdapter) {
			pagerAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		UserInfo info = UserInfoEngine.getInstance().getUserInfo();
		if (info != null) {
			tv_me_distance.setVisibility(View.GONE);
			/** 实名 **/
			String showName = UserInfoEngine.getInstance().getShowName();
			String[] typeAndname = showName.split(";");
			tv_me_name.setText(typeAndname[1]);
			if (typeAndname[0].equals("1")) {
				// 昵称
				iv_me_name_type.setImageResource(R.drawable.stranger_nick);
			} else if (typeAndname[0].equals("2")) {
				// 实名
				iv_me_name_type.setImageResource(R.drawable.stranger_real);
			}

			/** 年龄，若为-1，则显示"最好的年龄"五个字 **/
			if (CommUtil.isNotBlank(info.getAge())) {
				if (info.getAge() == -1)
					tv_me_age.setText(getResources().getString(R.string.default_age_description));
				else
					tv_me_age.setText(info.getAge() + "");
			}
			/** 性别 **/
			if (CommUtil.isNotBlank(info.getGender())) {
				if (info.getGender().equals("男")) {
					ivGender.setImageResource(R.drawable.com_gender_man);
				} else {
					ivGender.setImageResource(R.drawable.com_gender_women);
				}
			}

			/** 职业 **/
			if (CommUtil.isNotBlank(info.getOccupation()))
				tv_me_occupation.setText(info.getOccupation());
			/** 个性签名 ，没有则显示"未设置个性签名"七个字 **/
			if (CommUtil.isNotBlank(info.getSign()))
				tv_me_sign.setText(info.getSign());
			else
				tv_me_sign.setText(getResources().getString(R.string.default_sign_description));

			boolean isShowWealth = UserInfoEngine.getInstance().isShowWealth();
			if (isShowWealth) {
				llWealth.setVisibility(View.VISIBLE);
				tvWealth.setText(info.getWealth() + "");
			} else {
				llWealth.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 加载数据
	 */
	@Override
	protected void lazyLoad() {
		LogUtils.i(TAG, "lazyLoad");
		if (isPrepared) {
			if (NetworkUtils.isNetWorkConnected(getActivity())) {
				getUserInfo.getWorth(userID, meHandler, GET_USER_WORTH);
			} else {
				/** 没有网络 **/
				UserInfo info = UserInfoEngine.getInstance().getUserInfo();
				if (CommUtil.isNotBlank(info.getWorth()))
					tv_me_worth.setText(info.getWorth() + "");
			}
		}
	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		tvWorth.setOnClickListener(this);
		iv_me_doubt.setOnClickListener(this);
		iv_me_recharge.setOnClickListener(this);
		iv_me_setting.setOnClickListener(this);
		tv_me_innate.setOnClickListener(this);
		tv_me_achievement.setOnClickListener(this);
		tv_me_work.setOnClickListener(this);
	}

	Handler meHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_USER_WORTH:
				String worth = msg.obj.toString();
				tv_me_worth.setText(worth);
				break;
			default:
				break;
			}
		};
	};

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
	 * 
	 * @param index
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
				innateFragment.setMeFragment(this);
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
	 * 
	 * @param transaction
	 */
	private void hideAllFragment(FragmentTransaction transaction) {
		if (innateFragment != null) {
			transaction.hide(innateFragment);
		}
		if (achievementFragment != null) {
			transaction.hide(achievementFragment);
		}
		if (workFragment != null) {
			transaction.hide(workFragment);
		}
	}

	/**
	 * 设置默认状态
	 */
	private void setTabDefaultBackground() {
		tv_me_innate.setTextColor(getResources().getColor(R.color.black));
		tv_me_achievement.setTextColor(getResources().getColor(R.color.black));
		tv_me_work.setTextColor(getResources().getColor(R.color.black));
	}

	@Override
	public void onResume() {
		super.onResume();
		ImageLoaderHelper.clearCache();
		verifyIconPath();
	}

	@Override
	public void onDestroy() {
		// 取消订阅
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	/**
	 * 编辑完资料后调用，用于更新界面
	 */
	@Subscriber(tag = NotifyByEventBus.NT_EDIT_INFO_COMPLETE)
	public void refreshView(String tag) {
		// 更新我的资料的头部信息，包括：昵称、身价、年龄、性别、职业、个性签名
		LogUtils.i(TAG, tag + ",更新我的资料的头部信息，包括：昵称、身价、年龄、性别、职业、个性签名");
		initView();
	}

	/**
	 * 更新身价
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_WORTH)
	public void refreshWorth(String info) {
		LogUtils.i(TAG, info);
		lazyLoad();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (0 == DecentWorldApp.doubtWan) {
			iv_me_doubt.setImageResource(R.drawable.stranger_doubt_shadow);
		} else if (1 == DecentWorldApp.doubtWan) {
			iv_me_doubt.setImageResource(R.drawable.stranger_true);
		} else if (2 == DecentWorldApp.doubtWan) {
			iv_me_doubt.setImageResource(R.drawable.stranger_wan);
		}
	}
}
