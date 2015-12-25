/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.TitleBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatDetailsBgSetActivity.java
 * @Description: 设置单聊背景
 * @author: cj
 * @date: 2015年7月14日 上午11:26:21
 */
@EActivity(R.layout.chat_setting_set_bg)
public class ChatSettingSetBgActivity extends BaseFragmentActivity
{
	private static final String TAG= "ChatSettingSetBgActivity";
	@Bean
	TitleBar titleBar;

	@Bean
	ChoceAndTakePictureComponent picture;

	@AfterViews
	void init()
	{
		titleBar.setTitleBar("聊天背景", null);
	}

	/**
	 * 启动选择背景图片界面
	 */
	@Click(R.id.chat_setting_set_bg_fromlocal)
	void selectBgFromLocal(View view)
	{
		Intent intent = new Intent(ChatSettingSetBgActivity.this , ChatSettingSetBgFromLocalActivity_.class);
		startActivity(intent);
	}

	@Click(R.id.chat_setting_set_bg_choice_picture)
	public void chat_setting_set_bg_choice_picture()
	{
		picture.choicePicture();
	}

	@Click(R.id.chat_setting_set_bg_take_picture)
	public void chat_setting_set_bg_take_picture()
	{
		picture.takePicture();
	}

	@OnActivityResult(ChoceAndTakePictureComponent.TAKE_PICKTURE)
	void onResult(int resultCode, Intent data)
	{
		if (RESULT_OK == resultCode)
		{
			try
			{

				File file = new File(picture.getImageName());
				Intent intent = new Intent(this , ChatActivity_.class);
				intent.putExtra("values", file);
				startActivity(intent);
				this.finish();

			}
			catch (Exception e)
			{

			}
		}
	}
	
	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void setBack()
	{
		finish();
	}

}
