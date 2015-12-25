/**
 * 
 */
package cn.sx.decentworld.network.request;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: SetRoomInfo.java
 * @Description: 设置聊天室的相关信息
 * 1.创建聊天室
 * 2.
 * @author: cj
 * @date: 2015年9月25日 上午10:31:11
 */
@EBean
public class SetRoomInfo
{
	private static String TAG = "SetUserInfo";
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
	 * 创建聊天室【完成】
	 * @param roomName
	 * @param roomDescription
	 * @param ownerName
	 * @param ownerID
	 * @param images
	 * @param handler
	 */
	public void creatChatRoom(String roomName,String roomDescription ,String ownerName ,String ownerID,String ownerIntroduction,File[] images,final Handler handler)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("roomName", roomName);
		map.put("roomDescription", roomDescription);
		map.put("ownerName", ownerName);
		map.put("ownerID", ownerID);
		map.put("ownerIntroduction", ownerIntroduction);
		LogUtils.i(TAG, "creatChatRoom...begin");
		sendUrl.httpRequestWithImage(map,images,Constants.CONTEXTPATH_OPENFIRE+"/createChatRoom", new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, ResultBean msg)
			{
				LogUtils.i(TAG, "creatChatRoom...begin,msg.getResultCode="+msg.getResultCode());
				//创建成功，返回聊天室的ID
				if (msg.getResultCode() == 2222)
				{
					JSONObject json =(JSONObject) JSON.parse(msg.getData().toString());
					String roomID = json.getString("roomID");
					
					Message message = new Message();
					message.what = 1;
					message.obj = roomID;
					handler.sendMessage(message);
					LogUtils.i(TAG, "creatChatRoom...success,roomID="+roomID);
				} 
				if(msg.getResultCode() == 3333)
				{
					LogUtils.e(TAG, "creatChatRoom...failure,cause by:"+msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e)
			{
				LogUtils.e(TAG, "creatChatRoom...onFailure,cause by:"+e);
			}
		});
	}
	
	/**
	 * 退出聊天室
	 * @param dwID 自己的ID
	 * @param roomID 聊天室ID
	 */
	public void leaveChatRoom(String dwID,String roomID)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("roomID", roomID);
		LogUtils.i(TAG, "leaveChatRoom...begin");
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE+"/leaveChatRoom", Method.GET, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, ResultBean msg)
			{
				LogUtils.i(TAG, "leaveChatRoom...msg.getResultCode="+msg.getResultCode()+",msg.getMsg="+msg.getMsg()+",msg.getData="+msg.getData());
				if(msg.getResultCode() == 2222)
				{
					LogUtils.i(TAG, "leaveChatRoom...success");
				}
				if(msg.getResultCode() == 3333)
				{
					LogUtils.e(TAG, "leaveChatRoom...failure,cause by:"+msg.getMsg());
				}
			}
			
			@Override
			public void onFailure(String e)
			{
				LogUtils.e(TAG, "leaveChatRoom...onFailure,cause by:"+e);
			}
		});
	}
	
	/**
	 * 聊天室禁言
	 * @param dwID 主持人ID
	 * @param roomID 聊天室ID
	 * @param blockID 被禁言的ID
	 */
	public void blockUser(String dwID,String roomID,String blockID,final Handler handler,final int requestCode)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("roomID", roomID);
		map.put("blockID", blockID);
		LogUtils.i(TAG, "blockUser...begin");
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE+"/blockUser", Method.GET, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				LogUtils.i(TAG, "blockUser...msg.getResultCode="+msg.getResultCode()+",msg.getMsg="+msg.getMsg()+",msg.getData="+msg.getData());
				if(msg.getResultCode() == 2222)
				{
					LogUtils.i(TAG, "blockUser...success");
					Message message = Message.obtain();
					message.obj = msg.getData();
					message.what = requestCode;
					message.arg1 = 1;
					handler.sendMessage(message);
				}
				if(msg.getResultCode() == 3333)
				{
					LogUtils.e(TAG, "blockUser...failure,cause by:"+msg.getMsg());
					activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							toast.show("1禁言失败："+msg.getMsg());
						}
					});
				}
			}
			
			@Override
			public void onFailure(final String e)
			{
				LogUtils.e(TAG, "blockUser...onFailure,cause by:"+e);
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						toast.show("2禁言失败："+e);
					}
				});
			}
		});
	}
	
	/**
	 * 聊天室解除禁言
	 * @param dwID
	 * @param roomID
	 * @param blockID
	 * @param handler
	 * @param requestCode
	 */
	public void removeBlockUser(String dwID,String roomID,String blockID,final Handler handler,final int requestCode)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("roomID", roomID);
		map.put("blockID", blockID);
		LogUtils.i(TAG, "removeBlockUser...begin");
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE+"/removeBlockUser", Method.GET, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, final ResultBean msg)
			{
				LogUtils.i(TAG, "removeBlockUser...msg.getResultCode="+msg.getResultCode()+",msg.getMsg="+msg.getMsg()+",msg.getData="+msg.getData());
				if(msg.getResultCode() == 2222)
				{
					LogUtils.i(TAG, "removeBlockUser...success");
					Message message = Message.obtain();
					message.obj = msg.getData();
					message.what = requestCode;
					message.arg1 = 1;
					handler.sendMessage(message);
				}
				if(msg.getResultCode() == 3333)
				{
					LogUtils.e(TAG, "removeBlockUser...failure,cause by:"+msg.getMsg());
					activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							toast.show("1解除禁言失败："+msg.getMsg());
						}
					});
				}
			}
			
			@Override
			public void onFailure(final String e)
			{
				LogUtils.e(TAG, "removeBlockUser...onFailure,cause by:"+e);
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						toast.show("2解除禁言失败："+e);
					}
				});
			}
		});
	}
	
}
