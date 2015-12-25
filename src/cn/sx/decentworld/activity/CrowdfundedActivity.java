package cn.sx.decentworld.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RechargeComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * 
 */

/**
 * @ClassName: CrowdfundedActivity.java
 * @Description:
 * @author: yj
 * @date: 2015年7月29日 下午2:44:11
 */
@EActivity(R.layout.activity_crowdfunded)
public class CrowdfundedActivity extends BaseFragmentActivity
{
	private static final String TAG= "CrowdfundedActivity";
	@Bean
	TitleBar titleBar;
	@Bean 
	ToastComponent toast;

	
	@AfterViews
	void init()
	{
		
	}


}
