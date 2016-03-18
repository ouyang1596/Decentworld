/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ui.FindComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

/**
 * @ClassName: WorkActivity.java
 * @Description:
 * @author: yj
 * @date: 2015年7月20日 上午11:16:20
 */
@EActivity(R.layout.activity_work)
public class WorkActivity extends BaseFragmentActivity
{
	@Bean
	FindComponent findComponent;
	@Bean
	TitleBar titleBar;

	@AfterViews
	public void init()
	{
		titleBar.setTitleBar("发现", "", "发表");
		findComponent.initFindScrollView();
		findComponent.initListView();
	}

	@Click(R.id.main_header_left)
	public void back()
	{
		this.finish();
	}

	@Click(R.id.main_header_right)
	public void main_header_right()
	{
		Intent intent = new Intent(this, PublicWorkActivity_.class);
		startActivity(intent);
	}

}
