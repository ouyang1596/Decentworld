/**
 * 
 */
package cn.sx.decentworld.fragment.me;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.EditUserInfoActivity_;
import cn.sx.decentworld.adapter.UserInfoItemAdapter;
import cn.sx.decentworld.bean.DisplayAuthority;
import cn.sx.decentworld.bean.EditUserInfoItem;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.fragment.main.MeFragment;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: InnateFragment.java
 * @Description: 天生信息
 * @author: cj
 * @date: 2016年1月18日 下午7:48:34
 */
@EFragment(R.layout.fragment_me_innate)
public class InnateFragment extends BaseFragment implements OnClickListener {
	/**
	 * 常量
	 */
	private static final String TAG = "InnateFragment";
	private static final int R_GET_USER_THREE_ICON = 1;
	private static final int GET_USER_WEALTH = 2;

	/**
	 * 工具类
	 */
	@Bean
	ToastComponent toast;
	@Bean
	GetUserInfo getUserInfo;

	/**
	 * 界面资源
	 */
	@ViewById(R.id.iv_me_innate_edit)
	ImageView iv_me_innate_edit;

	@ViewById(R.id.lv_me_innate)
	ListViewForScrollView lv_me_innate;

	/**
	 * 变量
	 */
	private String userID = "";
	private boolean isPrepared = false;
	// 原始数据
	public static List<EditUserInfoItem> mDatas;
	// 处理过的数据
	private List<EditUserInfoItem> proDatas;
	private UserInfoItemAdapter userInfoItemAdapter;

	/**
	 * 入口
	 */
	@AfterViews
	void init() {
		initVar();
		initListener();
		isPrepared = true;
		lazyLoad();
	}

	/**
	 * 初始化变量
	 */
	private void initVar() {
		EventBus.getDefault().register(this);
		userID = DecentWorldApp.getInstance().getDwID();
		proDatas = new ArrayList<EditUserInfoItem>();
		mDatas = new ArrayList<EditUserInfoItem>();
		userInfoItemAdapter = new UserInfoItemAdapter(getActivity(), proDatas);
		lv_me_innate.setAdapter(userInfoItemAdapter);
	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		iv_me_innate_edit.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtils.i(TAG, "onResume");
	}

	/**
	 * 数据延迟加载
	 */
	@Override
	protected void lazyLoad() {
		if (isPrepared) {
			initUserInfoDatas();
			dataPrePro(mDatas);
		}

	}

	@Override
	public void turnToTab(int tab) {

	}

	/**
	 * 初始化个人详细信息，从数据库拿取
	 */
	public void initUserInfoDatas() {
		List<EditUserInfoItem> userInfoDatas = mDatas;
		// 清除UserInfoDatas里面的数据库中的值
		userInfoDatas.clear();
		EditUserInfoItem infoItem;
		// 从数据库中获取用户的信息
		UserInfo userInfo = UserInfoEngine.getInstance().getUserInfo();
		// 数据库中的获取用户的权限
		DisplayAuthority displayAuthority = DisplayAuthority.queryByDwID(userID);
		if (userInfo != null && displayAuthority != null) {
			infoItem = new EditUserInfoItem(displayAuthority.getShowSign(), "sign", "个性签名", "", userInfo.getSign());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowRealName(), "realName", "基本信息", "实名", userInfo.getRealName());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowNickName(), "nickName", "基本信息", "昵称", userInfo.getNickName());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowAge(), "age", "基本信息", "年龄", userInfo.getAge() + "");
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowGender(), "gender", "基本信息", "性别", userInfo.getGender());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowPosition(), "position", "基本信息", "位置", userInfo.getPosition());
			userInfoDatas.add(infoItem);
			if (userInfo.getUserType() == 0) {
				infoItem = new EditUserInfoItem(displayAuthority.getShowUserType(), "userType", "基本信息", "用户类型", "疑");
				userInfoDatas.add(infoItem);
				meFragment.getIvMeDoubt().setImageResource(R.drawable.stranger_doubt_shadow);
			} else if (userInfo.getUserType() == 1) {
				infoItem = new EditUserInfoItem(displayAuthority.getShowUserType(), "userType", "基本信息", "用户类型", "真");
				userInfoDatas.add(infoItem);
				meFragment.getIvMeDoubt().setImageResource(R.drawable.stranger_true);
			} else if (userInfo.getUserType() == 2) {
				infoItem = new EditUserInfoItem(displayAuthority.getShowUserType(), "userType", "基本信息", "用户类型", "腕");
				userInfoDatas.add(infoItem);
				meFragment.getIvMeDoubt().setImageResource(R.drawable.stranger_wan);
			}
			infoItem = new EditUserInfoItem(displayAuthority.getShowWealth(), "wealth", "基本信息", "身家", String.valueOf(userInfo
					.getWealth()));
			userInfoDatas.add(infoItem);

			infoItem = new EditUserInfoItem(false, "userId", "基本信息", "DW号", userInfo.getUserId());
			userInfoDatas.add(infoItem);

			if (userInfo.getIfStudent().equals("0")) {
				infoItem = new EditUserInfoItem(displayAuthority.getShowOccupation(), "occupation", "基本信息", "行业",
						userInfo.getOccupation());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowCorporation(), "corporation", "基本信息", "公司",
						userInfo.getCorporation());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowjob(), "job", "基本信息", "职位", userInfo.getJob());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowSchool(), "school", "我的历史", "毕业学校", userInfo.getSchool());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowDepartment(), "department", "我的历史", "毕业院系",
						userInfo.getDepartment());
				userInfoDatas.add(infoItem);
			} else if (userInfo.getIfStudent().equals("1")) {
				infoItem = new EditUserInfoItem(displayAuthority.getShowSchool(), "school", "基本信息", "学校", userInfo.getSchool());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowDepartment(), "department", "基本信息", "院系",
						userInfo.getDepartment());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowClasses(), "classes", "基本信息", "班级", userInfo.getClasses());
				userInfoDatas.add(infoItem);

				infoItem = new EditUserInfoItem(displayAuthority.getShowOccupation(), "occupation", "基本信息", "行业",
						userInfo.getOccupation());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowCorporation(), "corporation", "基本信息", "公司",
						userInfo.getCorporation());
				userInfoDatas.add(infoItem);
				infoItem = new EditUserInfoItem(displayAuthority.getShowjob(), "job", "基本信息", "职位", userInfo.getJob());
				userInfoDatas.add(infoItem);
			}

			// Map<String,String[]> map = new HashMap<String,String[]>();
			// map.put("hometown", new String[]{"我的历史","故乡"});
			// map.put("nation", new String[]{"我的历史","民族"});
			//
			// for(Map.Entry<String, String[]> entry:map.entrySet())
			// {
			// String key = entry.getKey();
			// String[] value = entry.getValue();
			//
			// try
			// {
			// Field dis = displayAuthority.getClass().getDeclaredField(key);
			// boolean result1 = (Boolean) dis.get(displayAuthority);
			//
			// Field info = userInfo.getClass().getDeclaredField(key);
			// String result2 = (String) info.get(userInfo);
			// infoItem = new EditUserInfoItem(result1,key, value[0],
			// value[1],result2);
			// userInfoDatas.add(infoItem);
			// }
			// catch (Exception e)
			// {
			// LogUtils.i(TAG, "数据转换异常"+e.toString());
			// }
			// }
			infoItem = new EditUserInfoItem(displayAuthority.getShowHometown(), "hometown", "我的历史", "故乡", userInfo.getHometown());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowNation(), "nation", "我的历史", "民族", userInfo.getNation());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowPhoneNum(), "phoneNum", "我的历史", "电话", userInfo.getPhoneNum());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowEmail(), "email", "我的历史", "邮箱", userInfo.getEmail());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowQQ(), "qq", "我的历史", "QQ", userInfo.getQq());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowWechat(), "wechat", "我的历史", "微信", userInfo.getWechat());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowBlog(), "blog", "我的历史", "微博", userInfo.getBlog());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowVehicle(), "vehicle", "我的历史", "车子", userInfo.getVehicle());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowBloodType(), "bloodType", "我的历史", "血型",
					userInfo.getBloodType());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowConstellatory(), "constellatory", "我的历史", "星座",
					userInfo.getConstellatory());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowMaritalStatus(), "maritalStatus", "我的历史", "婚否",
					userInfo.getMaritalStatus());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowReligion(), "religion", "我的历史", "宗教", userInfo.getReligion());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowSpeciality(), "speciality", "我的历史", "特长",
					userInfo.getSpeciality());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowIdol(), "idol", "我的历史", "偶像", userInfo.getIdol());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowMotto(), "motto", "我的历史", "座右铭", userInfo.getMotto());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowFootprint(), "footprint", "我的历史", "足迹",
					userInfo.getFootprint());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowLikebooks(), "likebooks", "我的爱好", "喜欢的书",
					userInfo.getLikebooks());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowLikemusic(), "likemusic", "我的爱好", "喜欢的音乐",
					userInfo.getLikemusic());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowLikesmovies(), "likemovie", "我的爱好", "喜欢的电影",
					userInfo.getLikemovie());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowLikesport(), "likesport", "我的爱好", "喜欢的运动",
					userInfo.getLikesport());
			userInfoDatas.add(infoItem);
			infoItem = new EditUserInfoItem(displayAuthority.getShowCate(), "cate", "我的爱好", "美食", userInfo.getCate());
			userInfoDatas.add(infoItem);
		}
	}

	/**
	 * 数据预处理
	 */
	private void dataPrePro(List<EditUserInfoItem> mDatas) {
		if (proDatas == null || mDatas == null) {
			return;
		}
		proDatas.clear();
		for (EditUserInfoItem item : mDatas) {
			if (CommUtil.isNotBlank(item.getValue()) && !item.getValue().equals("-1") && item.isShow()) {
				proDatas.add(item);
			}
		}
		if (userInfoItemAdapter != null) {
			userInfoItemAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 界面销毁
	 */
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	/**
	 * 编辑完资料后调用，用于更新界面
	 */
	@Subscriber(tag = NotifyByEventBus.NT_EDIT_INFO_COMPLETE)
	public void refreshView(String tag) {
		// 更新我的资料的"天生"部分资料
		LogUtils.i(TAG, tag + ",更新我的资料的\"天生\"部分资料");
		initUserInfoDatas();
		dataPrePro(mDatas);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_me_innate_edit: {
			// 编辑资料
			Intent intent = new Intent(getActivity(), EditUserInfoActivity_.class);
			startActivityForResult(intent, R_GET_USER_THREE_ICON);
		}
			break;

		default:
			break;
		}

	}

	/**
	 * 更新身家
	 */
	@Subscriber(tag = NotifyByEventBus.NT_REFRESH_WEALTH)
	public void refreshWealth(String info) {
		LogUtils.i(TAG, info);
		getUserInfo.getWealth(userID, handler, GET_USER_WEALTH);
	}

	/**
	 * 网络请求回调
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_USER_WEALTH:
				String wealth = msg.obj.toString();
				LogUtils.i(TAG, "获取的身家为" + wealth);
				// 更新列表中的身家项
				UserInfo userInfo = UserInfoEngine.getInstance().getUserInfo();
				userInfo.setWealth(Float.valueOf(wealth));
				// 保存到数据库
				userInfo.save();
				initUserInfoDatas();
				dataPrePro(mDatas);
				break;
			default:
				break;
			}
		};
	};
	private MeFragment meFragment;

	public void setMeFragment(MeFragment fragment) {
		meFragment = fragment;
	}
}
