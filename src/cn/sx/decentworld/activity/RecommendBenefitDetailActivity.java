/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.RecommendBenefitDetailAdapter;
import cn.sx.decentworld.bean.RecommendBenefitDetail;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.listener.NetCallback;
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
	
	//列表控件
	@ViewById(R.id.ll_list_container)
	LinearLayout mLlListContainer;
	
	//提示控件
	@ViewById(R.id.tv_prompt_container)
	TextView mTvPromptContainer;

	
	/**
	 * 初始化
	 */
	@AfterViews
	void init()
	{
		initView();
		initData();
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
		new BenefitEngine().getRecommendBenefitDetail(otherID, new NetCallback()
        {
            @Override
            public void onSuccess(String msg)
            {
                mData.clear();
                String result = msg;
                JSONArray array = JSON.parseArray(result);
                RecommendBenefitDetail benefitDetail;
                for(int i = 0;i<array.size();i++)
                {
                    benefitDetail = new RecommendBenefitDetail();
                    JSONObject object = array.getJSONObject(i);
                    benefitDetail.setAmount(object.getFloatValue("amount"));
                    benefitDetail.setTime(object.getString("time"));
                    benefitDetail.setStatusType(object.getIntValue("status"));
                    mData.add(benefitDetail);
                }
                //当没有收益时，给出提示，有收益时，显示收益列表；
                if(mData != null && mData.size()>0)
                {
                    mLlListContainer.setVisibility(View.VISIBLE);
                    mTvPromptContainer.setVisibility(View.GONE);
                }
                else
                {
                    mLlListContainer.setVisibility(View.GONE);
                    mTvPromptContainer.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
            
            @Override
            public void onFailure(String cause)
            {
                toast.show(cause);
            }
        });
	}
	
	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}

}
