/**
 * 
 */
package cn.sx.decentworld.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatDetailsWhistleblowingActivity.java
 * @Description:设置举报信息
 * @author: cj
 * @date: 2015年7月14日 上午11:24:08
 */

@EActivity(R.layout.chat_setting_whistleblowing)
public class ChatSettingWhistleblowingActivity extends BaseFragmentActivity
{
	private static final String TAG= "ChatSettingWhistleblowingActivity";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	
	private Boolean[] mWBRadioCheck = new Boolean[6];
	
	//Radio选项控件
	@ViewById(R.id.activity_whistleblowing_reason_1)
	RadioButton activity_whistleblowing_reason_1;
	@ViewById(R.id.activity_whistleblowing_reason_2)
	RadioButton activity_whistleblowing_reason_2;
	@ViewById(R.id.activity_whistleblowing_reason_3)
	RadioButton activity_whistleblowing_reason_3;
	@ViewById(R.id.activity_whistleblowing_reason_4)
	RadioButton activity_whistleblowing_reason_4;
	@ViewById(R.id.activity_whistleblowing_reason_5)
	RadioButton activity_whistleblowing_reason_5;
	@ViewById(R.id.activity_whistleblowing_reason_6)
	RadioButton activity_whistleblowing_reason_6;
	
	@ViewById(R.id.ll_chat_setting_whistleblowing_1)
	LinearLayout ll_chat_setting_whistleblowing_1;
	@ViewById(R.id.ll_chat_setting_whistleblowing_2)
	LinearLayout ll_chat_setting_whistleblowing_2;
	@ViewById(R.id.ll_chat_setting_whistleblowing_3)
	LinearLayout ll_chat_setting_whistleblowing_3;
	@ViewById(R.id.ll_chat_setting_whistleblowing_4)
	LinearLayout ll_chat_setting_whistleblowing_4;
	@ViewById(R.id.ll_chat_setting_whistleblowing_5)
	LinearLayout ll_chat_setting_whistleblowing_5;
	@ViewById(R.id.ll_chat_setting_whistleblowing_6)
	LinearLayout ll_chat_setting_whistleblowing_6;
	
	//补充说明
	@ViewById(R.id.chat_setting_whistleblowing_supplement)
	LinearLayout chat_setting_whistleblowing_supplement;

	@AfterViews
	public void init()
	{
		titleBar.setTitleBar("举报", null, "提交");
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		for (int i = 0; i < mWBRadioCheck.length; i++)
		{
			mWBRadioCheck[i] = false;
		}
	}
	
	/**
	 * 提交举报信息
	 */
	@Click(R.id.main_header_right_tv)
	void submit()
	{
		int n=0;
		for (int i = 0; i < mWBRadioCheck.length; i++)
		{
			if (mWBRadioCheck[i])
			{
				n++;
			}
		}
		
		if (n>0)
		{
			toast.show("提交成功！");
			finish();
		}
		else {
			toast.show("请选择举报类别！");
		}
		
	}

	@Click(
	{ R.id.ll_chat_setting_whistleblowing_1, R.id.ll_chat_setting_whistleblowing_2, R.id.ll_chat_setting_whistleblowing_3, R.id.ll_chat_setting_whistleblowing_4, R.id.ll_chat_setting_whistleblowing_5, R.id.ll_chat_setting_whistleblowing_6 })
	void radioCheck(View view)
	{
		switch (view.getId())
		{
		case R.id.ll_chat_setting_whistleblowing_1:
			if (mWBRadioCheck[0])
			{
				activity_whistleblowing_reason_1.setButtonDrawable(R.drawable.radio_whistleblowing_close);
				mWBRadioCheck[0] = false;
			} else
			{ 
				activity_whistleblowing_reason_1.setButtonDrawable(R.drawable.radio_whistleblowing_open);
				mWBRadioCheck[0] = true;
			}
			break;

		case R.id.ll_chat_setting_whistleblowing_2:
			if (mWBRadioCheck[1])
			{
				activity_whistleblowing_reason_2.setButtonDrawable(R.drawable.radio_whistleblowing_close);
				mWBRadioCheck[1] = false;
			} else
			{
				activity_whistleblowing_reason_2.setButtonDrawable(R.drawable.radio_whistleblowing_open);
				mWBRadioCheck[1] = true;
			}
			break;

		case R.id.ll_chat_setting_whistleblowing_3:
			if (mWBRadioCheck[2])
			{
				activity_whistleblowing_reason_3.setButtonDrawable(R.drawable.radio_whistleblowing_close);
				mWBRadioCheck[2] = false;
			} else
			{
				activity_whistleblowing_reason_3.setButtonDrawable(R.drawable.radio_whistleblowing_open);
				mWBRadioCheck[2] = true;
			}
			break;

		case R.id.ll_chat_setting_whistleblowing_4:
			if (mWBRadioCheck[3])
			{
				activity_whistleblowing_reason_4.setButtonDrawable(R.drawable.radio_whistleblowing_close);
				mWBRadioCheck[3] = false;
			} else
			{
				activity_whistleblowing_reason_4.setButtonDrawable(R.drawable.radio_whistleblowing_open);
				mWBRadioCheck[3] = true;
			}
			break;

		case R.id.ll_chat_setting_whistleblowing_5:
			if (mWBRadioCheck[4])
			{
				activity_whistleblowing_reason_5.setButtonDrawable(R.drawable.radio_whistleblowing_close);
				mWBRadioCheck[4] = false;
			} else
			{
				activity_whistleblowing_reason_5.setButtonDrawable(R.drawable.radio_whistleblowing_open);
				mWBRadioCheck[4] = true;
			}
			break;

		case R.id.ll_chat_setting_whistleblowing_6:
			if (mWBRadioCheck[5])
			{
				activity_whistleblowing_reason_6.setButtonDrawable(R.drawable.radio_whistleblowing_close);
				mWBRadioCheck[5] = false;
			} else
			{
				activity_whistleblowing_reason_6.setButtonDrawable(R.drawable.radio_whistleblowing_open);
				mWBRadioCheck[5] = true;
			}
			break;

		default:
			break;
		}
	}
	
	//补充说明
	@Click(R.id.chat_setting_whistleblowing_supplement)
	void supplement()
	{
		Intent intent = new Intent(ChatSettingWhistleblowingActivity.this,ChatSettingWhistleblowingAddActivity_.class);
		startActivity(intent);
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
