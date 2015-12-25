/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.packet.DiscoverInfo;
import org.jivesoftware.smackx.packet.DiscoverItems;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.activity.ChatRoomTopicDetail_;
import cn.sx.decentworld.adapter.ChatRoomListAdapter2;
import cn.sx.decentworld.bean.ChatRoomInfo;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: ChatRoomComponent.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月25日 下午2:29:15
 */
@EBean
public class ChatRoomComponent {
	@RootContext
	Context context;
	@RootContext
	Activity activity;
	@Bean
	ToastComponent toast;
	@Bean
	MainFragmentComponent mainComponent;
	private static String TAG = "ChatRoomComponent";
	private SendUrl sendUrl;
	private String listData;

	// 获取到的聊提室列表
	List<ChatRoomInfo> list;
	// 需要适配的组件
	PullToRefreshListView pull_listview;

	private XMPPConnection con;
	String serverName;

	// 存放服务会议的链表
	List<HostedRoom> roominfos = new ArrayList<HostedRoom>();
	// 存放聊天室房间
	List<DiscoverItems.Item> listDiscoverItems;
	List<String> answer;
	public static ChatRoomListAdapter2 adapter;

	@AfterViews
	void init() {
		Log.i(TAG, "ChatRoomComponent init()");
		sendUrl = new SendUrl(context);
		con = DecentWorldApp.getInstance().getConnectionImpl();
		serverName = con.getServiceName();
		// serverName=Constants.BACK_SERVER_NAME;
		Log.i(TAG, serverName + "---------");
	}

	public static final int REQUEST_CHATROOM_INFO = 1;
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_CHATROOM_INFO:
				setChatRoomDetail();
				break;
			default:
				break;
			}
		};
	};

	public void setChatRoomInfo(String dwid, final PullToRefreshListView list) {
		// 暂时跳过dw服务器，直接与openfire联系
		// this.pull_listview=list;
		// GetAllRoom(dwid);
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (!roominfos.equals(null) || roominfos.size() != 0) {
						List<ChatRoomInfo> thedate = new ArrayList<ChatRoomInfo>();
						for (int i = 0; i < roominfos.size(); i++) {
							ChatRoomInfo chatinfo;
							// try
							// {
							// chatinfo = new ChatRoomInfo(roominfos.get(i)
							// .getJid(), roominfos.get(i).getName(),
							// MultiUserChat.getRoomInfo(con,
							// roominfos.get(i).getJid())
							// .getOccupantsCount()
							// + "", MultiUserChat
							// .getRoomInfo(
							// con,
							// roominfos.get(i)
							// .getJid())
							// .getDescription());
							// thedate.add(chatinfo);
							// } catch (XMPPException e)
							// {
							// // TODO Auto-generated catch block
							// e.printStackTrace();
							// }
						}
						adapter = new ChatRoomListAdapter2(context, thedate);
						list.setAdapter(adapter);
					}
					break;

				default:
					break;
				}
			};
		};
		getConferenceServices();
		initHostRoom(list, handler);

		// initHostRoom11();
	}

	/**
	 * 查询服务器上聊天室
	 */
	private void initHostRoom(PullToRefreshListView list, Handler handler) {
		Log.i(TAG,
				"2222222222222222222222222222222222222222222222222222222222222serverName="
						+ serverName);
		try {
			for (Object aCol : answer) {
				String service = (String) aCol;
				Log.i(TAG, "server=" + service + "");
				// 查询服务器上的聊天室
				Collection<HostedRoom> rooms = MultiUserChat.getHostedRooms(
						con, service);
				for (HostedRoom room : rooms) {
					// 查看Room消息
					Log.i(TAG, room.getName() + " - " + room.getJid());
					RoomInfo roomInfo = MultiUserChat.getRoomInfo(con,
							room.getJid());
					if (roomInfo != null) {
						Log.i(TAG, roomInfo.getOccupantsCount() + " : "
								+ roomInfo.getSubject());
					}
					roominfos.add(room);
				}
			}
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);

		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查找服务列表
	 */
	public void getConferenceServices() {
		try {
			answer = new ArrayList<String>();
			// ServiceDiscoveryManager discoManager =
			// ServiceDiscoveryManager.getInstanceFor(con);
			ServiceDiscoveryManager discoManager = new ServiceDiscoveryManager(
					con);
			// 获取制定xmpp实体的项目
			DiscoverItems discoItems = discoManager.discoverItems(con
					.getServiceName());
			// DiscoverItems discoItems;
			// discoItems =
			// discoManager.discoverItems("conference.decentworld");
			// 获取被查询的XMPP实体的要查看的项目
			for (Iterator<DiscoverItems.Item> it = discoItems.getItems(); it
					.hasNext();) {

				Log.i(TAG,
						"111111111111111111111111111111getConferenceServices");
				DiscoverItems.Item item = (DiscoverItems.Item) it.next();
				if (item.getEntityID().startsWith("conference")
						|| item.getEntityID().startsWith("private")) {
					answer.add(item.getEntityID());
				} else {
					try {
						DiscoverInfo info = discoManager.discoverInfo(item
								.getEntityID());
						if (info.containsFeature("http://jabber.org/protocol/muc")) {
							answer.add(item.getEntityID());
						}
					} catch (XMPPException e) {
					}
				}
			}
			Log.i(TAG, "111111111111111111111111111111getConferenceServices");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化聊天服务会议列表
	 */
	private void initHostRoom11() {
		Collection<HostedRoom> hostrooms;
		try {
			// 查询服务器上的全部聊天室
			hostrooms = MultiUserChat.getHostedRooms(con, con.getServiceName());
			// 查看每个聊天室的详细资料
			for (HostedRoom entry : hostrooms) {
				Log.i(TAG, "名字：" + entry.getName() + " - ID:" + entry.getJid());
				if (entry != null) {
					RoomInfo roominfo = MultiUserChat.getRoomInfo(
							DecentWorldApp.getInstance().getConnectionImpl(),
							entry.getJid());
					Log.i(TAG,
							roominfo.getOccupantsCount() + "//"
									+ roominfo.getDescription());
				}
			}
		} catch (Exception e) {
			Log.i(TAG, "Exception------------------");
			e.printStackTrace();
		}
	}

	/**
	 * 填充pulltoreflashlistview数据
	 */
	protected void setChatRoomDetail() {
		ChatRoomListAdapter2 adapter = new ChatRoomListAdapter2(activity, list);
		pull_listview.setAdapter(adapter);
		pull_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mainComponent.toActivity(ChatRoomTopicDetail_.class);
			}
		});
	}

	/**
	 * 从dw服务器中获取全部聊天室信息
	 * 
	 * @param currentusername
	 */
	// public void GetAllRoom(String currentusername)
	// {
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("currentusername", currentusername);
	//
	// sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH2
	// + "/chatroom /GetAllRoom", Method.GET, new HttpCallBack()
	// {
	// @SuppressWarnings("unchecked")
	// @Override
	// public void onSuccess(String response, ResultBean msg)
	// {
	// if (msg.getResultCode() == 2000)
	// {
	// listData = msg.getData().toString();
	// Log.v(TAG, "success" + listData);
	//
	// list = (List<ChatRoomInfo>) JsonUtils.json2BeanArray(
	// listData, ChatRoomInfo.class);
	// if (list != null)
	// {
	// for (int i = 0; i < list.size(); i++)
	// {
	// Log.v(TAG, "第" + i + "个聊天室"
	// + list.get(i).toString());
	// }
	// }
	// Message mssg = new Message();
	// mssg.what = REQUEST_CHATROOM_INFO;
	// mssg.obj = msg.getMsg();
	// handler.sendMessage(mssg);
	// }
	// }
	//
	// @Override
	// public void onFailure(String e)
	// {
	// Log.v(TAG, e + "failure");
	// }
	// });
	// }

	// /**
	// * 获取聊天室列表
	// * @param handler 用于返回待处理的结果
	// */
	// public void getRoomList(final Handler handler)
	// {
	// HashMap<String, String> map = new HashMap<String, String>();
	// sendUrl.httpRequestWithParams(map,
	// "http://192.168.1.123:9090/plugins/decentworld/getChatRoomList",
	// Method.GET, new HttpCallBack()
	// {
	// @Override
	// public void onSuccess(String response, ResultBean msg)
	// {
	// LogUtils.i(TAG,
	// "msg.getResultCode="+msg.getResultCode()+"\nmsg.getData="+msg.getData().toString());
	// if(msg.getResultCode() == 2222)
	// {
	// Message message = new Message();
	// message.what = NewsFragment.CHAT_ROOM_LIST;
	// message.obj = msg.getData().toString();//处理需要携带的结果
	// handler.sendMessage(message);
	// }
	//
	// if(msg.getResultCode() == 3333)
	// {
	// LogUtils.i(TAG, "msg.getResultCode="+msg.getResultCode());
	// }
	// }
	//
	// @Override
	// public void onFailure(String error)
	// {
	// LogUtils.e(TAG, "error="+error);
	// }
	// });
	//
	// }

}