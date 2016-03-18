/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.RecommendBenefitListAdapter;
import cn.sx.decentworld.bean.RecommendBenefitList;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.CheckIdentityDialog;
import cn.sx.decentworld.dialog.CheckIdentityDialog.CheckIdentityListener;
import cn.sx.decentworld.dialog.DrawCashDialog;
import cn.sx.decentworld.dialog.DrawCashDialog.DrawCashListener;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @ClassName: PrivacySettingActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年7月24日 上午10:46:33
 */
@EActivity(R.layout.activity_cash_income)
public class RecommendBenefitActivity extends BaseFragmentActivity
{
	private static final String TAG = "CashIncomeActivity";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.ll_cash_income_root)
	LinearLayout ll_cash_income_root;

	/** 应该获得的收益**/
	@ViewById(R.id.tv_recommend_benefit_all)
	TextView tv_recommend_benefit_all;
	/** 已经获得的收益**/
	@ViewById(R.id.tv_recommend_benefit_has_back)
	TextView tv_recommend_benefit_has_back;
	/** 未提现的收益**/
	@ViewById(R.id.tv_recommend_benefit_not_takeout)
	TextView tv_recommend_benefit_not_takeout;
	

	@ViewById(R.id.lv_recommend_benefit_detail)
	ListView lv_recommend_benefit_detail;// 收益信息
	
	/** 没有推荐人提示 **/
	@ViewById(R.id.tv_recommend_benefit_reminder)
	TextView tv_recommend_benefit_reminder;

	/**
	 * 收益
	 */
	private CheckIdentityDialog checkIdentityDialog;
	private DrawCashDialog drawCashDialog;
	private FragmentManager fm;
	private static final int GET_RECOMMEND_BENEFIT_LIST = 1;// 获得推荐收益列表
	private String userID = "";

	@Bean
	GetUserInfo getUserInfo;

	private RecommendBenefitListAdapter adapterList;
	private List<RecommendBenefitList> listData;

	@AfterViews
	public void init()
	{
		ViewUtil.scaleContentView(ll_cash_income_root);
		titleBar.setTitleBar("设置", "现金收益");
		
		fm = getSupportFragmentManager();
		userID = DecentWorldApp.getInstance().getDwID();
		listData = new ArrayList<RecommendBenefitList>();
		adapterList = new RecommendBenefitListAdapter(mContext , listData);
		lv_recommend_benefit_detail.setAdapter(adapterList);
		getUserInfo.getRecommendBenefitList(userID, handler, GET_RECOMMEND_BENEFIT_LIST);
	}

	// 提现
	@Click(R.id.ll_cash_income_draw_cash)
	void drawCash()
	{
		checkIdentityDialog = new CheckIdentityDialog();
		checkIdentityDialog.setListener(listener);
		checkIdentityDialog.show(fm, "CashIncomeActivity");
	}

	CheckIdentityListener listener = new CheckIdentityListener()
	{
		@Override
		public void onConfirm()
		{
			if (checkIdentityDialog.getPassword().equals("123"))
			{
				toast.show("密码正确");
				// 弹出 输入金额 对话框
				drawCashDialog = new DrawCashDialog();
				drawCashDialog.setListener(listener2);
				drawCashDialog.show(fm, "DrawCashDialog");
			}
			else
			{
				toast.show("密码错误");
			}

		}
	};

	DrawCashListener listener2 = new DrawCashListener()
	{

		@Override
		public void confirm()
		{
			if (drawCashDialog.getAmount().equals(""))
			{
				toast.show("输入金额为空");
			}
			else
			{
				// 处理交易信息
				toast.show("提取现金成功");
			}

		}
	};

	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void setBackBtn()
	{
		finish();
	}

	/**
	 * 请求回调
	 */
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case GET_RECOMMEND_BENEFIT_LIST:
					// 解析数据，保存
					listData.clear();
					String result = msg.obj.toString();
					LogUtils.i(TAG, "数据为：" + result);
					JSONArray array = JSON.parseArray(result);
					RecommendBenefitList temp;
					float allBenefit = 0;//应得总金额
					float hasBenefit = 0;//已经获得的收益
					float notTakeout = 0;//还未提现
					if(array.size()>0)
					{
						for (int i = 0; i < array.size(); i++)
						{
							JSONObject object = array.getJSONObject(i);
							temp = new RecommendBenefitList();
							if (object.getString("isRegister").equals("1"))
							{
								temp.setRegister(true);
								if (object.getString("status").equals("0"))
								{
									temp.setFriend(false);
								}
								else
								{
									temp.setFriend(true);
								}
								float amount = Float.valueOf(object.getString("amount"));
								temp.setAmount(amount * amount);
								allBenefit += amount*amount;
								
								float benefit = Float.valueOf(object.getString("benefit"));
								temp.setBenefit(benefit);
								hasBenefit += benefit;
								
								float notTakeout_t = object.getFloatValue("stored");
								temp.setStored(notTakeout_t);
								notTakeout += notTakeout_t;
								
								temp.setOtherID(object.getString("dwID"));
							}
							else
							{
								temp.setRegister(false);
							}
							temp.setName(object.getString("name"));
							
							listData.add(temp);
							adapterList.notifyDataSetChanged();
							// 计算已经获得的收益和未提现的收益并显示
							tv_recommend_benefit_all.setText(String.valueOf(allBenefit));
							tv_recommend_benefit_has_back.setText(String.valueOf(hasBenefit));
							tv_recommend_benefit_not_takeout.setText(String.valueOf(notTakeout));
						}
					}
					else
					{
						tv_recommend_benefit_reminder.setText("你还没有推荐过任何人哦！");
						tv_recommend_benefit_reminder.setVisibility(View.VISIBLE);
						lv_recommend_benefit_detail.setVisibility(View.INVISIBLE);
					}
					break;

				default:
					break;
			}
		};
	};

}
