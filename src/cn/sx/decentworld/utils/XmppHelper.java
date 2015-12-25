package cn.sx.decentworld.utils;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.packet.*;
import org.jivesoftware.smackx.provider.*;
import org.jivesoftware.smackx.search.UserSearch;
import org.simple.eventbus.EventBus;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.filter.PacketCollectorType;
import cn.sx.decentworld.filter.SubjectPacketFilter;
import cn.sx.decentworld.inter.CommCallback;
import cn.sx.decentworld.listener.ViConnectionListener;
import cn.sx.decentworld.task.ProcessAudioThread;
import cn.sx.decentworld.task.ProcessCardThread;
import cn.sx.decentworld.task.ProcessCheckBeautyMessageThread;
import cn.sx.decentworld.task.ProcessFriendMessageThread;
import cn.sx.decentworld.task.ProcessMessageThread;
import cn.sx.decentworld.task.ProcessPictureThread;
import cn.sx.decentworld.task.ProcessRoomMessageThread;
import cn.sx.decentworld.task.ProcessSystemMessageThread;
import cn.sx.decentworld.task.ProcessWealthPacketThread;

/**
 * @ClassName: XmppHelper.java
 * @Description:
 * @author: cj
 * @date: 2015年10月22日 下午3:58:55
 */
public class XmppHelper
{
	private static final String TAG = "XmppHelper";
	private static XMPPConnection conn = null;

	private XmppHelper()
	{
	}

	/**
	 * 获得xmppConnection实例
	 *
	 * @return
	 */
	private static int count = 0;
	private static boolean isNewCon = false;
	
	/**
	 * 是否为新的连接
	 * @return
	 */
	public static boolean isNewCon()
	{
		return isNewCon;
	}

	public static XMPPConnection getConnection(CommCallback callback)
	{
		count++;
		LogUtils.i(TAG, "getConnection...count =" + count);
		// 是否为空
		if (conn == null)
		{
			try
			{
				LogUtils.i(TAG, "getConnection...con is null");
				configure(ProviderManager.getInstance());
				ConnectionConfiguration connConfig = new ConnectionConfiguration(Constants.HOST_OPEN_FIRE , Constants.PORT_OPEN_FIRE , Constants.SERVER_NAME);
				connConfig.setCompressionEnabled(false);// 不启用压缩 （默认值也是不启用）
				connConfig.setSASLAuthenticationEnabled(true);
				connConfig.setSecurityMode(SecurityMode.enabled);
				SASLAuthentication.supportSASLMechanism("PLAIN");
				connConfig.setDebuggerEnabled(true);

				conn = new XMPPConnection(connConfig);
				// COLLECTORS
				PacketFilter chat_filter = new SubjectPacketFilter(new String[]
				{ "chat" });
				conn.createPacketCollector(chat_filter, ProcessMessageThread.class);
				PacketFilter wealth_filter = new SubjectPacketFilter(new String[]
				{ "wealth" });
				conn.createPacketCollector(wealth_filter, ProcessWealthPacketThread.class);
				PacketFilter friend_message_filter = new SubjectPacketFilter(new String[]
				{ "add_friend_reason", "accept_Friend", "refuse_Friend", "delete_Friend" });
				conn.createPacketCollector(friend_message_filter, ProcessFriendMessageThread.class);
				PacketFilter picture = new SubjectPacketFilter(new String[]
				{ "picture" });
				conn.createPacketCollector(picture, ProcessPictureThread.class);
				PacketFilter audio = new SubjectPacketFilter(new String[]
				{ "audio" });
				conn.createPacketCollector(audio, ProcessAudioThread.class);
				PacketFilter room_message_filter = new SubjectPacketFilter(new String[]
				{ "roomMSG", "room_presence", "room_leave" });
				conn.createPacketCollector(room_message_filter, ProcessRoomMessageThread.class);
				PacketFilter system_message_filter = new SubjectPacketFilter(new String[]
				{ "error", "broadcast_benefit", "broadcast_worth" });
				conn.createPacketCollector(system_message_filter, ProcessSystemMessageThread.class);
				PacketFilter broadcast_beauty = new SubjectPacketFilter(new String[]
				{ "broadcast_beauty", "BeautySupport", "BeautyUnSupport" });
				conn.createPacketCollector(broadcast_beauty, ProcessCheckBeautyMessageThread.class);
				PacketFilter card = new SubjectPacketFilter(new String[]
				{ "card" });
				conn.createPacketCollector(card, ProcessCardThread.class);

				conn.connect();
				conn.addConnectionListener(new ViConnectionListener());
				isNewCon = true;
			}
			catch (Exception e)
			{
				LogUtils.e(TAG, "连接异常"+e.toString());
			}
		}
		// 是否连接
		if (!conn.isConnected())
		{
			try
			{
				LogUtils.i(TAG, "getConnection...con is not connected");
				conn.connect();
			}
			catch (Exception e)
			{
				LogUtils.e(TAG, "没有连接"+e.toString());
			}
		}
		// 是否登录
		if (!conn.isAuthenticated())
		{
			try
			{
				LogUtils.i(TAG, "getConnection...con is not Authenticated");
				String dwID = DecentWorldApp.getInstance().getDwID();
				String username = DecentWorldApp.getInstance().getUserName();
				String password = DecentWorldApp.getInstance().getPassword();
				LogUtils.i(TAG, "再次连接时，dwID=" + dwID + ",username=" + username + ",password=" + password);
				if (CommUtil.isNotBlank(username) && CommUtil.isNotBlank(password) && CommUtil.isNotBlank(dwID))
				{
					LogUtils.i(TAG, "重新登录");
					conn.login(username, dwID, password, "Smack");
					conn.sendPacket(new Presence(Presence.Type.available));
				}
				else
				{
					LogUtils.i(TAG, "username||password||dwID 为空");
				}

			}
			catch (Exception e)
			{
				LogUtils.e(TAG, "没有授权"+e.toString());
			}
		}
		if (callback != null)
		{
			callback.execute();
		}
		return conn;
	}

	public static XMPPConnection getNewConnection()
	{
		conn = null;
		return getConnection(null);
	}

	/**
	 * 修复登录
	 */
	public static void fixLogin()
	{
		final String dwID = DecentWorldApp.getInstance().getDwID();
		final String username = DecentWorldApp.getInstance().getUserName();
		final String password = DecentWorldApp.getInstance().getPassword();
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					conn.disconnect();
					conn.connect();
					conn.login(username, dwID, password, "Smack");
				}
				catch (XMPPException e)
				{
					e.printStackTrace();
				}
			}
		}).start();

	}

	public static void reConnect(CommCallback callback)
	{
		if (conn == null || !conn.isConnected())
		{
			EventBus.getDefault().post(callback, NotifyByEventBus.NT_CONNECTION);
		}
		else
		{
			callback.execute();
		}
	}

	/**
	 * 测试代码，获取连接的状态
	 * 
	 * @return
	 */
	public static XMPPConnection getConn()
	{
		if (conn == null)
		{
			LogUtils.i(TAG, "测试连接的状态...null");
		}
		else
		{
			LogUtils.i(TAG, "测试连接的状态...not null");
			conn.showRecvListeners();
			if (conn.isConnected())
			{
				LogUtils.i(TAG, "测试连接的状态...connected");
			}
			else
			{
				LogUtils.i(TAG, "测试连接的状态...not connected");
			}
			if (conn.isAuthenticated())
			{
				LogUtils.i(TAG, "测试连接的状态...Authenticated");
			}
			else
			{
				LogUtils.i(TAG, "测试连接的状态...not Authenticated");
			}
		}
		return conn;
	}

	public static void sendMessage(final Message message)
	{
		sendPacket(message);
	}

	public static void sendPacket(final Packet packet)
	{
		// 属于 连接为空、断开连接、没有登录 情况之一都将 重新连接
		if (conn == null || !conn.isConnected() || !conn.isAuthenticated())
		{
			// if(conn)
			if (conn == null)
				LogUtils.i(TAG, "sendPacket...连接为空");
			else if (!conn.isConnected())
				LogUtils.i(TAG, "sendPacket...断开了连接");
			else if (!conn.isAuthenticated())
				LogUtils.i(TAG, "sendPacket...没有登录服务器");
			CommCallback callback = new CommCallback()
			{
				@Override
				public void execute()
				{
					conn.sendPacket(packet);
				}
			};
			EventBus.getDefault().post(callback, NotifyByEventBus.NT_CONNECTION);
		}
		else
		{
			conn.sendPacket(packet);
		}
	}

	public static void closeConnection()
	{
		if (conn != null)
		{
			if (conn.isConnected())
			{
				conn.disconnect();
			}
			conn = null;
		}
	}

	/**
	 * 判断是否登录过服务器
	 * 
	 * @return
	 */
	public static boolean WasAuthenticated()
	{
		if (conn != null)
		{
			if (conn.isAuthenticated())
				return true;
			else
				return false;
		}
		else
			return false;
	}

	private static void configure(ProviderManager pm)
	{
		// Private Data Storage
		pm.addIQProvider("query", "jabber:iq:private", new PrivateDataManager.PrivateDataIQProvider());
		// Time
		try
		{
			pm.addIQProvider("query", "jabber:iq:time", Class.forName("org.jivesoftware.smackx.packet.Time"));
		}
		catch (ClassNotFoundException e)
		{
			Log.w("TestClient", "Can't load class for org.jivesoftware.smackx.packet.Time");
		}
		// Roster Exchange
		pm.addExtensionProvider("x", "jabber:x:roster", new RosterExchangeProvider());

		// Message Events
		pm.addExtensionProvider("x", "jabber:x:event", new MessageEventProvider());

		// Chat State
		pm.addExtensionProvider("active", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

		pm.addExtensionProvider("composing", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

		pm.addExtensionProvider("paused", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

		pm.addExtensionProvider("inactive", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

		pm.addExtensionProvider("gone", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

		// XHTML
		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im", new XHTMLExtensionProvider());

		// Group Chat Invitations
		pm.addExtensionProvider("x", "jabber:x:conference", new GroupChatInvitation.Provider());

		// Service Discovery # Items
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());

		// Service Discovery # Info
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info", new DiscoverInfoProvider());

		// Data Forms
		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());

		// MUC User
		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user", new MUCUserProvider());

		// MUC Admin
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin", new MUCAdminProvider());

		// MUC Owner
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner", new MUCOwnerProvider());

		// Delayed Delivery
		pm.addExtensionProvider("x", "jabber:x:delay", new DelayInformationProvider());

		// Version
		try
		{
			pm.addIQProvider("query", "jabber:iq:version", Class.forName("org.jivesoftware.smackx.packet.Version"));
		}
		catch (ClassNotFoundException e)
		{
			// Not sure what's happening here.
		}

		// VCard
		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());

		// Offline Message Requests
		pm.addIQProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageRequest.Provider());

		// Offline Message Indicator
		pm.addExtensionProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageInfo.Provider());

		// Last Activity
		pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());

		// User Search
		pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());

		// SharedGroupsInfo
		pm.addIQProvider("sharedgroup", "http://www.jivesoftware.org/protocol/sharedgroup", new SharedGroupsInfo.Provider());

		// JEP-33: Extended Stanza Addressing
		pm.addExtensionProvider("addresses", "http://jabber.org/protocol/address", new MultipleAddressesProvider());

		// FileTransfer
		pm.addIQProvider("si", "http://jabber.org/protocol/si", new StreamInitiationProvider());

		pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams", new BytestreamsProvider());

		pm.addIQProvider("open", "http://jabber.org/protocol/ibb", new IBBProviders.Open());

		pm.addIQProvider("close", "http://jabber.org/protocol/ibb", new IBBProviders.Close());

		pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb", new IBBProviders.Data());

		// Privacy
		pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());

		pm.addIQProvider("command", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider());
		pm.addExtensionProvider("malformed-action", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.MalformedActionError());
		pm.addExtensionProvider("bad-locale", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadLocaleError());
		pm.addExtensionProvider("bad-payload", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadPayloadError());
		pm.addExtensionProvider("bad-sessionid", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadSessionIDError());
		pm.addExtensionProvider("session-expired", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.SessionExpiredError());
	}
}
