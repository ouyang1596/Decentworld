/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.SPUtils;
import cn.sx.decentworld.widget.HorizontalListView;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: ChatActivity.java
 * @Description: 单聊
 * @author: cj
 * @date: 2015年8月17日 上午10:15:33
 */
@EBean
public class ChatComponent {
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	// 标记
	public static final String TAG = "ChatComponent";
	// 常量
	public static final int REQUEST_GET_ASSET = 1;// 请求并计费成功
	public static final int REQUEST_UPDATE_ASSET = 2;
	public static final int REQUEST_UPDATE_VALUE = 3;

	// http请求类
	private SendUrl sendUrl;

	// 获取并更新个人身家所需变量
	private String asset;
	private TextView tv_asset;

	// 获取并更新个人身价所需变量
	private String value;
	private TextView tv_value;

	@AfterViews
	public void init() {
		sendUrl = new SendUrl(context);
	}

	public void initVoiceIcon(Drawable[] micImages) {
		// 动画资源文件,用于录制语音时
		micImages = new Drawable[] {
				context.getResources().getDrawable(R.drawable.record_animate_1),
				context.getResources().getDrawable(R.drawable.record_animate_2),
				context.getResources().getDrawable(R.drawable.record_animate_3),
				context.getResources().getDrawable(R.drawable.record_animate_4),
				context.getResources().getDrawable(R.drawable.record_animate_5),
				context.getResources().getDrawable(R.drawable.record_animate_6),
				context.getResources().getDrawable(R.drawable.record_animate_7),
				context.getResources().getDrawable(R.drawable.record_animate_8),
				context.getResources().getDrawable(R.drawable.record_animate_9),
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

	// 填充listview的数据，显示聊天室已存在的人
	public void requestMemberLVData(String ChatRoomId,
			HorizontalListView chat_room_imgList) 
	{

	}


	/**
	 * 发送文件
	 * 
	 * @param user
	 *            文件接受放
	 * @param conn
	 *            XmppConnection对象
	 * @param file
	 *            要发送的文件
	 * @throws Exception
	 */
	public void sendFileWithParams(HashMap<String, String> hashmap, File[] images, String api, final Handler handler, final String txtMsgID)
	{
	    /** token = dwID + randomStr +key**/
	    String dwID = hashmap.get("dwID");
	    long time = System.currentTimeMillis();
	    String randomStr = (String) SPUtils.get(context, SPUtils.randomStr, "");
	    String token = AES.encode(dwID+time, randomStr);
	    hashmap.put("randomStr", String.valueOf(time));
	    hashmap.put("token", token);
	    
		sendUrl.httpRequestWithImage(hashmap, images, Constants.CONTEXTPATH_OPENFIRE + api, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, ResultBean msg)
			{
				LogUtils.i(TAG, "sendFileWithParams...begin,msg.getResultCode="+msg.getResultCode()+",msg.getMsg="+msg.getMsg()+",msg.getData="+msg.getData().toString());
				Message mssg = new Message();
				if (msg.getResultCode() == 2222)
				{
					mssg.what = Constants.SUCC;
					LogUtils.i(TAG, "sendFileWithParams...success");
					showToast("发送成功");
				}
				else
				{
					mssg.what = Constants.FAILURE;
					LogUtils.i(TAG, "sendFileWithParams...failure,cause by:"+msg.getMsg());
					showToast("发送失败");
				}
				mssg.obj = txtMsgID;
				handler.sendMessage(mssg);
			}
			@Override
			public void onFailure(String e) 
			{
				showToast(Constants.NET_WRONG);
				//将消息状态变为叹号
				Message mssg = new Message();
				mssg.what = Constants.FAILURE;
				mssg.obj = txtMsgID;
				handler.sendMessage(mssg);
				
			}
		});
	}

	

	public void transmitMessage(HashMap map) {
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE
				+ Constants.RESEND, Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) 
			{
			    showToast("" + msg.getMsg());
			}

			@Override
			public void onFailure(String e) {
				showToast(Constants.NET_WRONG);
			}
		});
	}

	private void showToast(final String netWrong) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toast.show(netWrong);
			}
		});
	}
}
