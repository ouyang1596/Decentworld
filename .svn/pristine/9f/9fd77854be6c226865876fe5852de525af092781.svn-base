/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.AnonymousBean;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.UserExtraInfo;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.GetStrangerInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: NearCardDetailActivity.java
 * @Description:匿名聊天界面
 * @author: yj
 * @date: 2015年10月7日 下午7:05:47
 */
@EActivity(R.layout.activity_near_card_detail1)
public class NearCardDetail2Activity extends BaseFragmentActivity {
	private static final String TAG = "NearCardDetailActivity";
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	private int pagerPosition;
	@ViewById(R.id.tv_addr)
	TextView tvAddr;
	@ViewById(R.id.btn_OK)
	Button btnOK;
	@ViewById(R.id.tv_user_detail_info_sign)
	TextView tvUserSign;
	private String strangerDwID;
	@ViewById(R.id.iv_sex)
	ImageView ivSex;
	@ViewById(R.id.tv_age)
	TextView tvAge;
	@ViewById(R.id.imgv1)
	ImageView imgv1;
	@ViewById(R.id.iv_show_icon)
	ImageView ivShowIcon;
	@ViewById(R.id.imgv2)
	ImageView imgv2;
	@ViewById(R.id.imgv3)
	ImageView imgv3;
	public static final int GET_ANONYMOUSINFO = 0;
	public static final int CREATE_ANONYMOUS_OBJ = 1;
	@ViewById(R.id.lv_anonymous_info)
	ListView lvAnonymousInfo;
	@ViewById(R.id.tv_realname_nickname)
	TextView tvRealNameNickname;
	@Bean
	GetStrangerInfo getStrangerInfo;
	@Bean
	ToastComponent toast;

	private String data = "[{\"name\":\"showBlog\",\"show\":\"true\",\"value\":\"\"}]";
	private String nickname, age, location;
	private List<AnonymousBean> anonymousBeanDatas;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
                case GET_ANONYMOUSINFO:
                    anonymousBeanDatas = (List<AnonymousBean>) JsonUtils.json2BeanArray(msg.obj.toString(), AnonymousBean.class);
                    selectIcon(anonymousBeanDatas);
                    mAdapter.notifyDataSetChanged();
                    break;
                case CREATE_ANONYMOUS_OBJ:
                    toast.show("succ");
                    Intent intent = new Intent(mContext , ChatActivity_.class);
                    intent.putExtra(ChatActivity.OTHER_ID, strangerDwID);
                    intent.putExtra(ChatActivity.OTHER_NICKNAME, user_nickname + "[匿名聊天]");
                    intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE_ANONYMITY);
                    if (ContactUser.isContact(strangerDwID))
                    {
                        intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_FRIEND);
                    }
                    else
                    {
                        intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_STRANGER);
                    }
                    intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(user_worth));
                    startActivity(intent);
                    break;
			}
		}
	};
	private HashMap<String, String> map = new HashMap<String, String>();
	private HashMap<String, String> ifShowMap = new HashMap<String, String>();
	AnonymousBean mAnonymousBean;

	private void selectIcon(List<AnonymousBean> anonymousBeanDatas) {
		for (int i = 0; i < anonymousBeanDatas.size(); i++) {
			if ("showIcon".equals(anonymousBeanDatas.get(i).getName())) {
				mAnonymousBean = anonymousBeanDatas.get(i);
				if ("true".equals(mAnonymousBean.getShow())) {
					ivShowIcon.setImageResource(R.drawable.solid_heart);
				} else {
					ivShowIcon.setImageResource(R.drawable.hollow_heart);
				}
				ifShowMap.put("showIcon", mAnonymousBean.getShow());
				anonymousBeanDatas.remove(i);
				break;
			}
		}
	};

	@AfterViews
	public void init() {
		tvTitle.setText("匿名资料");
		ivBack.setVisibility(View.VISIBLE);
		ImageLoaderHelper.initImageLoader(mContext);
		initMap();
		initIfShowmap();
		NGetIntent();
		initData();
		initImage();
		initAdapter();
		getAnonymousInfo();
	}

	GetAnonymouAdapter mAdapter;

	private void initAdapter() {
		mAdapter = new GetAnonymouAdapter();
		lvAnonymousInfo.setAdapter(mAdapter);
	}

	private void getAnonymousInfo() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		getStrangerInfo.getAnonymousInfo(map, mHandler);
	}

	private void initData() {
		ivBack.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		btnOK.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				traverMap();
				createAnonymousObj();
			}
		});
		ivShowIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (null == mAnonymousBean) {
					return;
				}
				if ("true".equals(mAnonymousBean.getShow())) {
					mAnonymousBean.setShow("false");
					ivShowIcon.setImageResource(R.drawable.hollow_heart);
				} else {
					mAnonymousBean.setShow("true");
					ivShowIcon.setImageResource(R.drawable.solid_heart);
				}
				ifShowMap.put("showIcon", mAnonymousBean.getShow());
			}
		});
	}

	private void createAnonymousObj() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("authority", buffer.toString());
		getStrangerInfo.createAnonymousIdentify(map, mHandler);
	}

	private StringBuffer buffer;

	public void traverMap() {
		buffer = new StringBuffer();
		buffer.append("{");
		Iterator<Entry<String, String>> iterator = ifShowMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> nextKeyValue = iterator.next();
			buffer.append("\"" + nextKeyValue.getKey() + "\"" + ":" + "\""
					+ nextKeyValue.getValue() + "\"" + ",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("}");
		LogUtils.i("bm", "msg--" + buffer.toString());
	}

	private void initImage() {
	    UserExtraInfo userInfo = UserExtraInfo.queryBy(DecentWorldApp.getInstance()
                .getDwID());
		if (null == userInfo) {
			return;
		}
		if (CommUtil.isNotBlank(userInfo.getIcon())) {
			LogUtils.i("bm", "icon---" + userInfo.getIcon());
			ImageLoaderHelper.mImageLoader.displayImage(userInfo.getIcon(),
					imgv1, ImageLoaderHelper.mOptions);
		}
		if (CommUtil.isNotBlank(userInfo.getIcon2())) {
			ImageLoaderHelper.mImageLoader.displayImage(userInfo.getIcon2(),
					imgv2, ImageLoaderHelper.mOptions);
		}

		if (CommUtil.isNotBlank(userInfo.getIcon3())) {
			ImageLoaderHelper.mImageLoader.displayImage(userInfo.getIcon3(),
					imgv3, ImageLoaderHelper.mOptions);
		}

	}

	private String user_nickname;
	private String user_worth;

	private void NGetIntent() {
		strangerDwID = getIntent().getStringExtra(Constants.DW_ID);
		user_nickname = getIntent().getStringExtra(
				NearCardDetailActivity.USER_NICKNAME);
		user_worth = getIntent().getStringExtra(
				NearCardDetailActivity.USER_WORTH);
		LogUtils.i(TAG, "strangerDwID="+strangerDwID+",user_nickname="+user_nickname+",user_worth="+user_worth);
	}

	class GetAnonymouAdapter extends BaseAdapter implements OnClickListener {
		@Override
		public int getCount() {
			return anonymousBeanDatas == null ? 0 : anonymousBeanDatas.size();
		}

		@Override
		public AnonymousBean getItem(int position) {
			return anonymousBeanDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View con, ViewGroup arg2) {
			ViewHolder vh = null;
			if (null == con) {
				con = View.inflate(mContext,
						R.layout.item_anonymous_info_setting, null);
				vh = new ViewHolder();
				vh.ivIfShow = (ImageView) con.findViewById(R.id.iv_ifshow);
				vh.ivIfShow.setTag(position);
				vh.ivIfShow.setOnClickListener(this);
				vh.tvName = (TextView) con.findViewById(R.id.tv_name);
				vh.tvValue = (TextView) con.findViewById(R.id.tv_value);
				con.setTag(vh);
			} else {
				vh = (ViewHolder) con.getTag();
			}
			AnonymousBean bean = anonymousBeanDatas.get(position);
			if ("true".equals(bean.getShow())) {
				vh.ivIfShow.setImageResource(R.drawable.solid_heart);
			} else {
				vh.ivIfShow.setImageResource(R.drawable.hollow_heart);
			}
			ifShowMap.put(bean.getName(), bean.getShow());
			vh.tvName.setText(map.get(bean.getName()));
			vh.tvValue.setText(bean.getValue());
			return con;
		}

		class ViewHolder {
			ImageView ivIfShow;
			TextView tvName, tvValue;
		}

		@Override
		public void onClick(View view) {
			int position = (Integer) view.getTag();
			ImageView iv = (ImageView) view;
			AnonymousBean bean = getItem(position);
			if ("true".equals(bean.getShow())) {
				bean.setShow("false");
			} else {
				bean.setShow("true");
			}
			LogUtils.i("bm",
					"name--" + bean.getName() + "show--" + bean.getShow());
			notifyDataSetChanged();
		}
	}

	private void initIfShowmap() {
		ifShowMap.put("showWealth", "false");
		ifShowMap.put("showWorth", "false");
		ifShowMap.put("showRealName", "false");
		ifShowMap.put("showPhoneNum", "false");
		ifShowMap.put("showWorkChart", "false");
		ifShowMap.put("showVehicle", "false");
		ifShowMap.put("showStature", "false");
		ifShowMap.put("showSpeciality", "false");
		ifShowMap.put("showSchool", "false");
		ifShowMap.put("showQRCode", "false");
		ifShowMap.put("showPosition", "false");
		ifShowMap.put("showOccupation", "false");
		ifShowMap.put("showQQ", "false");
		ifShowMap.put("showNation", "false");
		ifShowMap.put("showMaritalStatus", "false");
		ifShowMap.put("showLikesport", "false");
		ifShowMap.put("showLikesmovies", "false");
		ifShowMap.put("showLikemusic", "false");
		ifShowMap.put("showLikebooks", "false");
		ifShowMap.put("showjob", "false");
		ifShowMap.put("showIdol", "false");
		ifShowMap.put("showHometown", "false");
		ifShowMap.put("showFootprint", "false");
		ifShowMap.put("showWechat", "false");
		ifShowMap.put("showType", "false");
		ifShowMap.put("showSign", "false");
		ifShowMap.put("showEmail", "false");
		ifShowMap.put("showDepartment", "false");
		ifShowMap.put("showCorporation", "false");
		ifShowMap.put("showConstellatory", "false");
		ifShowMap.put("showClasses", "false");
		ifShowMap.put("showCate", "false");
		ifShowMap.put("showBloodType", "false");
		ifShowMap.put("showSex", "false");
		ifShowMap.put("showAge", "false");
		ifShowMap.put("showBlog", "false");
		ifShowMap.put("showIcon", "false");
		ifShowMap.put("showMotto", "false");
		ifShowMap.put("showNickName", "false");
		ifShowMap.put("showReligion", "false");
	}

	private void initMap() {
		map.put("showWealth", "身家");
		map.put("showWorth", "身价");
		map.put("showRealName", "实名");
		map.put("showPhoneNum", "电话号码");
		map.put("showWorkChart", "作品圈");
		map.put("showVehicle", "车子");
		map.put("showStature", "身高");
		map.put("showSpeciality", "特长");
		map.put("showSchool", "学校");
		map.put("showQRCode", "二维码");
		map.put("showPosition", "常去的地点");
		map.put("showOccupation", "职业");
		map.put("showQQ", "QQ");
		map.put("showNation", "民族");
		map.put("showMaritalStatus", "婚姻状况");
		map.put("showLikesport", "喜欢的运动");
		map.put("showLikesmovies", "喜欢的电影");
		map.put("showLikemusic", "喜欢的音乐");
		map.put("showLikebooks", "喜欢的书籍");
		map.put("showjob", "职位");
		map.put("showIdol", "偶像");
		map.put("showHometown", "家乡");
		map.put("showFootprint", "旅行足迹");
		map.put("showWechat", "微信");
		map.put("showType", "类型");
		map.put("showSign", "个性签名");
		map.put("showEmail", "电子邮箱");
		map.put("showDepartment", "院系");
		map.put("showCorporation", "公司");
		map.put("showConstellatory", "星座");
		map.put("showClasses", "班级");
		map.put("showCate", "喜欢的美食");
		map.put("showBloodType", "血型");
		map.put("showSex", "性别");
		map.put("showAge", "年龄");
		map.put("showBlog", "博客");
		map.put("showIcon", "图片");
		map.put("showMotto", "座右铭");
		map.put("showNickName", "昵称");
		map.put("showReligion", "宗教");
	}
}
