/**
 * 
 */
package cn.sx.decentworld.activity;


import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @ClassName: ChatDetailsWhistleblowingActivity.java
 * @Description:设置举报中的补充说明信息
 * @author: cj
 * @date: 2015年7月14日 上午11:24:08
 */

@EActivity(R.layout.chat_setting_whistleblowing_add)
public class ChatSettingWhistleblowingAddActivity extends BaseFragmentActivity
{
	private static final String TAG= "ChatSettingWhistleblowingAddActivity";
	@Bean
	TitleBar titleBar;
	@Bean 
	ToastComponent toast;

	@AfterViews
	public void init()
	{
		titleBar.setTitleBar("补充说明", null, "确定");
	}
	
	@Click(R.id.main_header_right_tv)
	void confirm()
	{
		//处理确定结果
		toast.show("确定");
	}
	
	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void setBackBtn()
	{
		finish();
	}
}
