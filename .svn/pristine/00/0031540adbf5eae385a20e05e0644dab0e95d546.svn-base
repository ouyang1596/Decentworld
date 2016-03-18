/**
 * 
 */
package cn.sx.decentworld.component;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: TitleBar.java
 * @Description: 标题栏
 * @author: cj
 * @date: 2015年9月21日 下午2:24:02
 */
@EBean
public class TitleBar
{
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	
	@Bean
	ToastComponent toast;
	// 导航栏控件
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;//默认不显示
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;//默认为聊天记录
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_right_btn)
	ImageView main_header_right_btn;
	@ViewById(R.id.main_header_right_tv)
	TextView main_header_right_tv;
	
	/**
	 * 初始化
	 */
	@AfterViews
	void init()
	{
		
	}
	
	/**
	 * 
	 * @param title 标题
	 */
	public void setTitleBar(String title)
	{
		main_header_left.setVisibility(View.GONE);
		main_header_title.setText(title);
		main_header_right_btn.setVisibility(View.GONE);
		main_header_right_tv.setVisibility(View.GONE);
	}
	
	/**
	 * 
	 * @param title 标题
	 */
	public void setTitleBar(String leftText,String title)
	{
		isBlank(leftText, title);
		main_header_right_btn.setVisibility(View.GONE);
		main_header_right_tv.setVisibility(View.GONE);
	}
	
	public void setTitleBar(String leftText,String title,String rightText)
	{
		isBlank(leftText, title);
		if((rightText==null)||rightText.equals(""))
		{
			main_header_right_btn.setVisibility(View.GONE);
			main_header_right_tv.setVisibility(View.GONE);
		}
		else
		{
			main_header_right_btn.setVisibility(View.GONE);
			main_header_right_tv.setVisibility(View.VISIBLE);
			main_header_right_tv.setText(rightText);
		}
	}
	
	public void setTitleBar(String leftText,String title,int rightImage)
	{
		isBlank(leftText, title);
		main_header_right_tv.setVisibility(View.GONE);
		main_header_right_btn.setVisibility(View.VISIBLE);
		main_header_right_btn.setImageResource(rightImage);
	}
	
	private void isBlank(String leftText,String title)
	{
		if((leftText==null)||leftText.equals(""))
		{
			main_header_left.setVisibility(View.GONE);
		}
		else
		{
			main_header_left.setVisibility(View.VISIBLE);
			main_header_left_tv.setText(leftText);
		}
		
		
		if((title==null)||title.equals(""))
		{
			main_header_title.setVisibility(View.GONE);
		}
		else
		{
			main_header_title.setVisibility(View.VISIBLE);
			main_header_title.setText(title);
		}
	}
	
	
	public void setRightText(String rightText)
	{
		main_header_right_tv.setText(rightText);
	}

	
	

}
