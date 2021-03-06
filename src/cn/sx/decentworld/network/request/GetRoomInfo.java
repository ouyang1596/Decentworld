/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: GetRoomInfo.java
 * @Description: 获取聊天室相关信息 1.聊天过程中向服务器传文件（图片和语音） 2.获取聊天室历史记录 3.根据dwID获取昵称和身价
 * @author: cj
 * @date: 2015年9月25日 上午10:31:43
 */
@EBean
public class GetRoomInfo {
	private static String TAG = "GetRoomInfo";
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	private SendUrl sendUrl;

	@AfterViews
	void init() {
		sendUrl = new SendUrl(context);
	}

	/**
	 * 
	 */
	public GetRoomInfo(Context context) {
		if (sendUrl == null) {
			sendUrl = new SendUrl(context);
		}
	}

	



	
	
}
