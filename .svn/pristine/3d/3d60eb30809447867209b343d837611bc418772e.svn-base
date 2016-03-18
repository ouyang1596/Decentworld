/**
 * 
 */
package cn.sx.decentworld.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.ActivityCollector;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ModificationBankCardOne.java
 * @Description:
 * @author: cj
 * @date: 2015年7月25日 下午3:00:47
 */
@EActivity(R.layout.activity_modification_password_two)
public class ModificationPasswordTwo extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG= "ModificationPasswordTwo";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.btn_modification_password_two_confirm)
	Button btn_modification_password_two_confirm;

	@AfterViews
	public void init()
	{
		ActivityCollector.addActivity(this);
		titleBar.setTitleBar("返回", "重置密码");
		btn_modification_password_two_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_modification_password_two_confirm:
			toast.show("重置密码成功！");
			ActivityCollector.finishAll();
			break;
			
		case R.id.main_header_left:
			finish();
			break;

		default:
			break;
		}
	}

}
