/**
 * 
 */
package cn.sx.decentworld.test;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BaseFragmentActivity;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.manager.DWAudioManager;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ServiceUtils;
import cn.sx.decentworld.utils.ThreadUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: JackTest.java
 * @Description:
 * @author: cj
 * @date: 2015年12月3日 上午9:42:51
 */

@EActivity(R.layout.activity_jack_test)
public class JackTest extends BaseFragmentActivity implements OnClickListener
{
	@ViewById(R.id.btn_current_thread)
	Button btn_current_thread;

	@ViewById(R.id.btn_current_service)
	Button btn_current_service;

	@ViewById(R.id.btn_play_audio)
	Button btn_play_audio;

	@ViewById(R.id.btn_remove_cache)
	Button btn_remove_cache;

	@Bean
	ToastComponent toast;

	@AfterViews
	void init()
	{
		btn_current_thread.setOnClickListener(this);
		btn_current_service.setOnClickListener(this);
		btn_play_audio.setOnClickListener(this);
		btn_remove_cache.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_current_thread:
				ThreadUtils.printAllThreads();
				break;
			case R.id.btn_current_service:
				if (ServiceUtils.isServiceRunning(JackTest.this, "cn.sx.decentworld.service.PacketListenerService"))
				{
					toast.show("消息监听服务已经启动");
				}
				else
				{
					toast.show("消息监听服务已经停止");
				}
				break;
			case R.id.btn_play_audio:
				DWAudioManager audioManager = new DWAudioManager(JackTest.this);
				audioManager.playSound(DWAudioManager.RECEIVE_MESSAGE);
				break;
			case R.id.btn_remove_cache:
				// 清除图片缓存
				UserInfo userInfo = UserInfoManager.getUserInfoInstance();
				ImageLoaderHelper.clearCacheByUrl(userInfo.getIcon());
				break;
			default:
				break;
		}
	}


}
