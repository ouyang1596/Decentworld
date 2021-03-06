/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: ChatRoomChatComponent.java
 * @Description: 
 * @author: cj
 * @date: 2015年9月21日 下午6:46:47
 */
@EBean
public class ChatRoomChatComponent
{
	private static final String TAG = "ChatRoomChatComponent";
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	
	@Bean
	ToastComponent toast;
	
	private SendUrl sendUrl;
	
	
	@AfterViews
	void init()
	{
		sendUrl = new SendUrl(context);
	}
	
	/**
	 * 获得表情资源
	 * 
	 * @param getSum
	 * @return
	 */
	public List<String> getExpressionRes(int getSum)
	{
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++)
		{
			String filename = "ee_" + x;
			reslist.add(filename);
		}
		return reslist;
	}
	
	/**
	 * 初始化动画资源文件,用于录制语音时
	 */
	public void initVoiceIcon(Drawable[] micImages)
	{
		// 动画资源文件,用于录制语音时
		micImages = new Drawable[]
		{
				context.getResources()
						.getDrawable(R.drawable.record_animate_1),
				context.getResources()
						.getDrawable(R.drawable.record_animate_2),
				context.getResources()
						.getDrawable(R.drawable.record_animate_3),
				context.getResources()
						.getDrawable(R.drawable.record_animate_4),
				context.getResources()
						.getDrawable(R.drawable.record_animate_5),
				context.getResources()
						.getDrawable(R.drawable.record_animate_6),
				context.getResources()
						.getDrawable(R.drawable.record_animate_7),
				context.getResources()
						.getDrawable(R.drawable.record_animate_8),
				context.getResources()
						.getDrawable(R.drawable.record_animate_9),
				context.getResources()
						.getDrawable(R.drawable.record_animate_10),
				context.getResources()
						.getDrawable(R.drawable.record_animate_11),
				context.getResources()
						.getDrawable(R.drawable.record_animate_12),
				context.getResources()
						.getDrawable(R.drawable.record_animate_13),
				context.getResources()
						.getDrawable(R.drawable.record_animate_14), };
	}
	

	
	

	
	

}
