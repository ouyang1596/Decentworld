/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.BenefitDetail;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: MagnateToOtherDetailActivity.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月29日 上午9:50:36
 */
@EActivity(R.layout.activity_magnate_to_other_detail)
public class MagnateToOtherDetailActivity extends BaseFragmentActivity
{
	private static final String TAG= "MagnateToOtherDetailActivity";
	@Bean
	TitleBar titleBar;
	@Bean 
	ToastComponent toast;
	
	@ViewById(R.id.lv_magnate_to_other_detail)
	ListView lv_magnate_to_other_detail;
	
	@Bean
	GetUserInfo getUserInfo;
	
	private String dwID;
	private String otherID;
	private List<BenefitDetail> lists;
	private BenefitDetailAdapter adapter;
	
	@AfterViews
	void init()
	{
		titleBar.setTitleBar("返回", "查看明细");
		dwID = DecentWorldApp.getInstance().getDwID();
		otherID = getIntent().getStringExtra("otherID");
		getUserInfo.getGRBenefitHistory(dwID, otherID, handler, GET_DETAIL);
	}
	
	private static final int GET_DETAIL = 1;
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			switch (msg.what)
			{
				case GET_DETAIL:
					////data:[{"time":"X月X日"，"amount":"123"}
					String json_result = msg.obj.toString();
					lists = (List<BenefitDetail>) JsonUtils.json2BeanArray(json_result, BenefitDetail.class);
					adapter = new BenefitDetailAdapter(MagnateToOtherDetailActivity.this);
					lv_magnate_to_other_detail.setAdapter(adapter);
					LogUtils.i(TAG, "获取指定人的详情记录为："+lists.size()+"条");
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
	void setBackBtn()
	{
		finish();
	}
	
	
	
	class BenefitDetailAdapter extends BaseAdapter
	{
		private Context context;
		private LayoutInflater inflater;
		
		
		/**
		 * 
		 */
		public BenefitDetailAdapter(Context context)
		{
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			return lists.size();
		}

		@Override
		public Object getItem(int position)
		{
			return lists.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if(convertView == null)
			{
				holder =new ViewHolder();
				convertView = inflater.inflate(R.layout.item_benefit_detail, null);
				holder.time = (TextView) convertView.findViewById(R.id.tv_benefit_detail_time);
				holder.amount = (TextView) convertView.findViewById(R.id.tv_benefit_detail_amount);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			BenefitDetail benefitDetail = lists.get(position);
			holder.time.setText(benefitDetail.getTime());
			holder.amount.setText(benefitDetail.getAmount());
			return convertView;
		}
		
		class ViewHolder
		{
			TextView time;
			TextView amount;
		}
		
	}
	
	
	
}
