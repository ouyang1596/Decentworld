/**
 * 
 */
package cn.sx.decentworld.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.IPBean;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.lidroid.xutils.view.annotation.event.OnLongClick;

/**
 * @ClassName: ModifyIpActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年10月16日 上午10:41:27
 */
@EActivity(R.layout.activity_modify_ip)
public class ModifyIpActivity extends BaseFragmentActivity
{
	private static final String TAG = "ModifyIpActivity";
	@Bean
	ToastComponent toast;
	
	@ViewById(R.id.tv_modify_result)
	TextView tv_modify_result;
	
	
	@ViewById(R.id.et_modify_one)
	EditText et_modify_one;

	@ViewById(R.id.et_modify_two)
	EditText et_modify_two;

	@ViewById(R.id.et_modify_three)
	EditText et_modify_three;

	@ViewById(R.id.et_modify_four)
	EditText et_modify_four;
	
//	@ViewById(R.id.ll_modify_root)
//	LinearLayout ll_modify_root;
	
	@Bean
	TitleBar titleBar;
	
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	
	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;
	
	@ViewById(R.id.lv_modify_ip)
	ListView lv_modify_ip;
	

	@AfterViews
	void init()
	{
//		ViewUtil.scaleContentView(ll_modify_root);
		titleBar.setTitleBar("返回", "设置IP", "完成");
		
		final String[] ips = new String[]{Constants.HOST,Constants.HOST123,Constants.HOST115};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_dropdown_item_1line , ips);
		lv_modify_ip.setAdapter(adapter);
		lv_modify_ip.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				tv_modify_result.setText(ips[arg2]);
			}
		});
		
	}
	
	
	@Click(R.id.main_header_left)
	void back(View view)
	{
		finish();
	}
	
	
	@Click(R.id.main_header_right)
	void right(View view)
	{
		String ip = tv_modify_result.getText().toString().trim();
		if(!ip.equals("请设置Ip"))
		{
			Constants.IP = ip;
			LogUtils.i(TAG, "Constants中的Ip修改为："+Constants.IP);
		}
		finish();
	}
	
	@Click(R.id.btn_modify_complete)
	void complete(View view)
	{
		String one = et_modify_one.getText().toString();
		String two = et_modify_two.getText().toString();
		String three = et_modify_three.getText().toString();
		String four = et_modify_four.getText().toString();
		if(CommUtil.isBlank(one)
				||CommUtil.isBlank(two)
				||CommUtil.isBlank(three)
				||CommUtil.isBlank(four))
		{
			toast.show("IP不能为空");
		}
		else
		{
			String ip = one + "." + two + "." +three + "." + four;
			tv_modify_result.setText(ip);
			LogUtils.i(TAG, "修改的IP = "+one+"."+two+"."+three+"."+four);
		}
	}

}
