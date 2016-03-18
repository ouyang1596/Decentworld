/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.widget.ListView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.RecommendBenefitDetailAdapter;
import cn.sx.decentworld.bean.RecommendBenefitDetail;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.GetUserInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: RecommendBenefitDetailActivity.java
 * @Description: 获取推荐收益详情
 * @author: cj
 * @date: 2015年12月21日 上午9:17:21
 */
@EActivity(R.layout.activity_recommend_benefit_detail)
public class RecommendBenefitDetailActivity extends BaseFragmentActivity
{
	private static final String TAG = "RecommendBenefitDetailActivity";
	@Bean
	ToastComponent toast;
	
	@ViewById(R.id.lv_recommend_benefit_detail)
	ListView lv_recommend_benefit_detail;
	
	private List<RecommendBenefitDetail> mData;
	private RecommendBenefitDetailAdapter adapter;
	private static final int GET_DETAIL = 1;
	@Bean
	GetUserInfo getUserInfo;
	private String userID = "";
	private String otherID = "";
	
	@Bean
	TitleBar titleBar;
	
	@AfterViews
	void init()
	{
		initView();
		initData();
		initEvent();
	}

	/**
	 * 初始化视图
	 */
	private void initView()
	{
		titleBar.setTitleBar("返回列表", "详情");
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		userID = DecentWorldApp.getInstance().getDwID();
		otherID = getIntent().getStringExtra("otherID");
		mData = new ArrayList<RecommendBenefitDetail>();
		adapter = new RecommendBenefitDetailAdapter(RecommendBenefitDetailActivity.this, mData);
		lv_recommend_benefit_detail.setAdapter(adapter);
		getUserInfo.getRecommendBenefitDetail(userID, otherID, handler, GET_DETAIL);
	}

	/**
	 * 初始化事件
	 */
	private void initEvent()
	{
		
	}
	

	/**
	 * 回调处理
	 */
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			switch (msg.what)
			{
				case GET_DETAIL:
					mData.clear();
					String result = msg.obj.toString();
					JSONArray array = JSON.parseArray(result);
					RecommendBenefitDetail benefitDetail;
					for(int i = 0;i<array.size();i++)
					{
						benefitDetail = new RecommendBenefitDetail();
						JSONObject object = array.getJSONObject(i);
						float amount = object.getFloatValue("amount");
						benefitDetail.setAmount(amount);
						benefitDetail.setTime(object.getString("time"));
						if(object.getString("status").equals("0"))
						{
							benefitDetail.setStatus(false);
						}
						else
						{
							benefitDetail.setStatus(true);
						}
						mData.add(benefitDetail);
					}
					adapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		};
	};
	
	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}

}
