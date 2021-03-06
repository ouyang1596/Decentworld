/**
 * 
 */
package cn.sx.decentworld.component.ui;

import android.app.Activity;
import android.content.Context;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: ChatRoomBuildComponent.java
 * @Description: 
 * @author: cj
 * @date: 2015年9月18日 下午3:29:50
 */
@EBean
public class ChatRoomBuildComponent
{
	public static final String TAG = "ChatRoomBuildComponent";
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
	
//	public void creatChatRoom(String roomName,String roomDescription ,String ownerName ,String ownerID,File[] images,final Handler handler)
//	{
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("roomName", roomName);
//		map.put("roomDescription", roomDescription);
//		map.put("ownerName", ownerName);
//		map.put("ownerID", ownerID);
//
//		sendUrl.httpRequestWithImage(map,images,Constants.PROTOCAL+Constants.HOST123+Constants.PORT_9090+"/plugins/decentworld/createChatRoom", new HttpCallBack()
//		{
//			@Override
//			public void onSuccess(String response, ResultBean msg)
//			{
//				LogUtils.i(TAG, "msg.getResultCode="+msg.getResultCode());
//				
//				JSONObject json =(JSONObject) JSON.parse(msg.getData().toString());
//				String roomID = json.getString("roomID");
//				LogUtils.i(TAG, "roomID="+roomID);
//				
//				//创建成功，返回聊天室的ID
//				if (msg.getResultCode() == 2222)
//				{
//					Message message = new Message();
//					message.what = 1;
//					message.obj = roomID;
//					handler.sendMessage(message);
//				} 
//				if(msg.getResultCode() == 3333)
//				{
//					LogUtils.i(TAG, "msg.getResultCode="+msg.getResultCode());
//				}
//			}
//
//			@Override
//			public void onFailure(String e)
//			{
//				LogUtils.i(TAG, "failure caused by:"+e);
//			}
//		});
//	}


}
