/**
 * 
 */
package cn.sx.decentworld.component.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: PublicWorkComponent.java
 * @Description: 
 * @author: dyq
 * @date: 2015年8月26日 上午10:48:59
 */
@EBean
public class PublicWorkComponent
{
	@Bean
	ToastComponent toast;
	@RootContext
	Activity activity;
	@RootContext
	Context context;
	
	@AfterViews
	void init()
	{
		
	}
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;
	
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;

	@ViewById(R.id.main_header_right_tv)
	TextView main_header_right_tv;

	@ViewById(R.id.main_header_right_btn)
	ImageView main_header_right_btn;

	//初始化导航栏
	public void initNavbar()
	{
		main_header_left.setVisibility(View.VISIBLE);
		main_header_left_tv.setText("作品圈");
		main_header_title.setText("发表作品圈");
		main_header_right_btn.setVisibility(View.GONE);
		main_header_right_tv.setVisibility(View.GONE);
	}
	
	@Click(R.id.main_header_left)
	void back()
	{
		activity.finish();
	}
}
