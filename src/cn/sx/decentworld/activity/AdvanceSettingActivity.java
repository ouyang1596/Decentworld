/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.*;

/**
 * @ClassName: PrivacySettingActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年7月24日 上午10:46:33
 */
@EActivity(R.layout.activity_advance_setting)
public class AdvanceSettingActivity extends BaseFragmentActivity implements OnClickListener
{
	private static String TAG = "AdvanceSettingActivity";
	private SendUrl sendUrl;
	private String dwID;

	@ViewById(R.id.ll_advance_setting_root)
	LinearLayout ll_advance_setting_root;
	
	// 开关控件
	@ViewById(R.id.iv_advance_setting_dw)
	ImageView iv_advance_setting_dw;
	@ViewById(R.id.iv_advance_setting_phone)
	ImageView iv_advance_setting_phone;
	@ViewById(R.id.iv_advance_setting_nickname)
	ImageView iv_advance_setting_nickname;
	@ViewById(R.id.iv_advance_setting_realname)
	ImageView iv_advance_setting_realname;
	@ViewById(R.id.iv_advance_setting_1)
	ImageView iv_advance_setting_1;
	@ViewById(R.id.iv_advance_setting_2)
	ImageView iv_advance_setting_2;
	/** 接收貌的推送 **/
	@ViewById(R.id.iv_advance_setting_push_appearance)
	ImageView iv_advance_setting_push_appearance;
	/** 自动转账 **/
	@ViewById(R.id.iv_advance_setting_autoTransfer)
	ImageView iv_advance_setting_autoTransfer;
	
	private int items = 8;
	private Boolean[] checkBoxIsChecked = new Boolean[items];
	private List<ImageView> imageViewList;
	private List<String> indexs;

	@Bean
	TitleBar titleBar;

	@AfterViews
	public void init()
	{
		ViewUtil.scaleContentView(ll_advance_setting_root);
		sendUrl = new SendUrl(this);
		dwID = DecentWorldApp.getInstance().getDwID();
		titleBar.setTitleBar("设置", "高级设置");

		iv_advance_setting_dw.setOnClickListener(this);
		iv_advance_setting_phone.setOnClickListener(this);
		iv_advance_setting_nickname.setOnClickListener(this);
		iv_advance_setting_realname.setOnClickListener(this);
		iv_advance_setting_1.setOnClickListener(this);
		iv_advance_setting_2.setOnClickListener(this);
		iv_advance_setting_push_appearance.setOnClickListener(this);
		iv_advance_setting_autoTransfer.setOnClickListener(this);

		imageViewList = new ArrayList<ImageView>();
		imageViewList.add(iv_advance_setting_dw);
		imageViewList.add(iv_advance_setting_phone);
		imageViewList.add(iv_advance_setting_nickname);
		imageViewList.add(iv_advance_setting_realname);
		imageViewList.add(iv_advance_setting_1);
		imageViewList.add(iv_advance_setting_2);
		imageViewList.add(iv_advance_setting_push_appearance);
		imageViewList.add(iv_advance_setting_autoTransfer);

		indexs = new ArrayList<String>();
		indexs.add("canFindByUserID");
		indexs.add("canFindByPhoneNum");
		indexs.add("canFindByNickname");
		indexs.add("canFindByRelname");
		indexs.add("strangerMessageNotice");
		indexs.add("acceptPush");
		indexs.add("acceptBeautyPush");
		indexs.add("autoTransfer");

		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		UserInfo info = UserInfo.queryByDwID(dwID);
		if (info == null || info.isCanFindByID() == null || info.isCanFindByNickName() == null || info.isCanFindByPhoneNum() == null || info.isCanFindByRealName() == null
				|| info.isNoticeStrangerMessage() == null || info.getAcceptPush() == null||info.getAcceptBeautyPush()||
				info.getAutoTransfer()==null)
		{
			getAuthFromServer();
		}
		else
		{
			checkBoxIsChecked[0] = info.isCanFindByID();
			checkBoxIsChecked[1] = info.isCanFindByPhoneNum();
			checkBoxIsChecked[2] = info.isCanFindByNickName();
			checkBoxIsChecked[3] = info.isCanFindByRealName();
			checkBoxIsChecked[4] = info.isNoticeStrangerMessage();
			checkBoxIsChecked[5] = info.getAcceptPush();
			checkBoxIsChecked[6] = info.getAcceptBeautyPush();
			checkBoxIsChecked[7] = info.getAutoTransfer();
			
			initSwitch(checkBoxIsChecked);
		}
	}

	/**
	 * 搜索开关控制
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.iv_advance_setting_dw:
				clickSwitch(0);
				break;
			case R.id.iv_advance_setting_phone:
				clickSwitch(1);
				break;
			case R.id.iv_advance_setting_nickname:
				clickSwitch(2);
				break;
			case R.id.iv_advance_setting_realname:
				clickSwitch(3);
				break;
			case R.id.iv_advance_setting_1:
				clickSwitch(4);
				break;
			case R.id.iv_advance_setting_2:
				clickSwitch(5);
				break;
			case R.id.iv_advance_setting_push_appearance:
				clickSwitch(6);
				break;
			case R.id.iv_advance_setting_autoTransfer:
				clickSwitch(7);
				break;
			default:
				break;
		}
	}

	/**
	 * 点击开关时
	 * 
	 * @param i
	 */
	private void clickSwitch(int i)
	{
		if (checkBoxIsChecked[i])
		{
			imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_close);
			checkBoxIsChecked[i] = false;
		}
		else
		{
			imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_open);
			checkBoxIsChecked[i] = true;
		}
	}

	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void back()
	{
		updateAuthtoServer();
		finish();
	}

	private void updateAuthtoServer()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", dwID);
		for (int i = 0; i < checkBoxIsChecked.length; i++)
		{
			if (checkBoxIsChecked[i])
				map.put(indexs.get(i), "true");
			else
				map.put(indexs.get(i), "false");
		}

		HashMap<String, String> container = new HashMap<String, String>();
		container.put("auth", JSON.toJSONString(map));
		String requestUrl = Constants.CONTEXTPATH + Constants.AUTH_SETTING;
		LogUtils.i(TAG, "updateAuthtoServer...begin");
		sendUrl.httpRequestWithParams(container, requestUrl, Method.GET, new HttpCallBack()
		{

			@Override
			public void onSuccess(String response, ResultBean msg)
			{
				if (msg.getResultCode() == 2222)
				{
					LogUtils.i(TAG, "updateAuthtoServer...success");
					handler.sendEmptyMessage(1);
				}
				if (msg.getResultCode() == 3333)
				{
					LogUtils.i(TAG, "updateAuthtoServer...failure,cause by:"+msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e)
			{
				LogUtils.i(TAG, "updateAuthtoServer...onFailure,cause by:"+e);
			}
		});
	}

	Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					initSwitch(checkBoxIsChecked);
					break;
				case 1:
					UserInfo userInfo = UserInfo.queryByDwID(dwID);
					if (userInfo != null)
					{
						if (checkBoxIsChecked[0])
							userInfo.setCanFindByID(true);
						else
							userInfo.setCanFindByID(false);

						if (checkBoxIsChecked[1])
							userInfo.setCanFindByPhoneNum(true);
						else
							userInfo.setCanFindByPhoneNum(false);

						if (checkBoxIsChecked[2])
							userInfo.setCanFindByNickName(true);
						else
							userInfo.setCanFindByNickName(false);

						if (checkBoxIsChecked[3])
							userInfo.setCanFindByRealName(true);
						else
							userInfo.setCanFindByRealName(false);

						if (checkBoxIsChecked[4])
							userInfo.setNoticeStrangerMessage(true);
						else
							userInfo.setNoticeStrangerMessage(false);

						if (checkBoxIsChecked[5])
							userInfo.setAcceptPush(true);
						else
							userInfo.setAcceptPush(false);
						
						if (checkBoxIsChecked[6])
							userInfo.setAcceptBeautyPush(true);
						else
							userInfo.setAcceptBeautyPush(false);
						
						if (checkBoxIsChecked[7])
							userInfo.setAutoTransfer(true);
						else
							userInfo.setAutoTransfer(false);
						userInfo.save();
					}
					else
					{
						LogUtils.i(TAG, "保存修改的数据时，检测到UserInfo为空");
					}

					break;
				default:
					break;
			}
		}
	};

	/**
	 * 用网络请求的数据初始化开关
	 * 
	 * @param SW
	 */
	private void initSwitch(Boolean[] SW)
	{
		for (int i = 0; i < SW.length; i++)
		{
			if (!SW[i])
				imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_close);
			else
				imageViewList.get(i).setImageResource(R.drawable.switch_rectangle_open);
		}
	};

	/**
	 * 从服务器获取开关权限
	 */
	private void getAuthFromServer()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		String requestUrl = Constants.CONTEXTPATH + Constants.AUTH_GET;
		LogUtils.i(TAG, requestUrl);
		LogUtils.i(TAG, "getAuthFromServer...begin");
		sendUrl.httpRequestWithParams(map, requestUrl, Method.GET, new HttpCallBack()
		{

			@Override
			public void onSuccess(String response, ResultBean msg)
			{
				if (msg.getResultCode() == 2222)
				{
					JSONObject jObject = JSON.parseObject(msg.getData().toString());
					LogUtils.i(TAG, "getAuthFromServer...获取的权限为：" + msg.getData().toString());
					Boolean canFindByUserID = jObject.getBooleanValue("dwID");
					UserInfo info = UserInfo.queryByDwID(dwID);
					if (info == null)
					{
						info = new UserInfo();
					}
					if (canFindByUserID != null)
					{
						checkBoxIsChecked[0] = canFindByUserID;
						info.setCanFindByID(canFindByUserID);
					}
					Boolean canFindByPhoneNum = jObject.getBooleanValue("phoneNum");
					if (canFindByPhoneNum != null)
					{
						checkBoxIsChecked[1] = canFindByPhoneNum;
						info.setCanFindByPhoneNum(canFindByPhoneNum);
					}
					Boolean canFindByNickName = jObject.getBooleanValue("nickName");
					if (canFindByNickName != null)
					{
						checkBoxIsChecked[2] = canFindByNickName;
						info.setCanFindByNickName(canFindByNickName);
					}
					Boolean canFindByRealName = jObject.getBooleanValue("realName");
					if (canFindByRealName != null)
					{
						checkBoxIsChecked[3] = canFindByRealName;
						info.setCanFindByRealName(canFindByRealName);
					};

					Boolean noticeStrangerMsg = jObject.getBooleanValue("strangerMsg");
					if (noticeStrangerMsg != null)
					{
						checkBoxIsChecked[4] = noticeStrangerMsg;
						info.setNoticeStrangerMessage(noticeStrangerMsg);
					}

					Boolean acceptPush = jObject.getBooleanValue("acceptPush");
					if (acceptPush != null)
					{
						checkBoxIsChecked[5] = acceptPush;
						info.setAcceptPush(acceptPush);
					}
					
					Boolean acceptBeautyPush = jObject.getBooleanValue("acceptBeautyPush");
					if (acceptBeautyPush != null)
					{
						checkBoxIsChecked[6] = acceptBeautyPush;
						info.setAcceptBeautyPush(acceptBeautyPush);
					}
					Boolean autoTransfer = jObject.getBooleanValue("autoTransfer");
					if (autoTransfer != null)
					{
						checkBoxIsChecked[7] = autoTransfer;
						info.setAcceptBeautyPush(autoTransfer);
					}
					info.save();
					handler.sendEmptyMessage(0);
				}
				if (msg.getResultCode() == 3333)
				{
					LogUtils.i(TAG, "getAuthFromServer...failure,cause by:"+msg.getMsg());
					for (int i = 0; i < items; i++)
					{
						checkBoxIsChecked[i] = false;
					}
				}

			}

			@Override
			public void onFailure(String e)
			{
				LogUtils.i(TAG, "getAuthFromServer...onFailure,cause by:"+e);
				for (int i = 0; i < items; i++)
				{
					checkBoxIsChecked[i] = false;
				}
			}
		});
	}

}
