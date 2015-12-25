/**
 * 
 */
package cn.sx.decentworld.manager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

/**
 * @ClassName: XmppConnectionManager.java
 * @Description: 
 * @author: cj
 * @date: 2015年9月22日 下午4:10:52
 */
public class XmppConnectionManager
{
	private String TAG = "XmppConnectionManager";
	private XMPPConnection connection;
	private static XmppConnectionManager xmppConnectionManager;
	public static final String DOMAIN = "@zhanNeng/Smack";
	private static ConnectionConfiguration connectionConfig;
	private final String CONFERENCE = "@conference.";
	
	/**
	 * 
	 */
	public XmppConnectionManager()
	{
	}
	
	public static XmppConnectionManager getInstance()
	{
		if (xmppConnectionManager == null)
		{
			xmppConnectionManager = new XmppConnectionManager();
		}
		return xmppConnectionManager;
	}
	
	public XMPPConnection getConnection()
	{
		if (connection == null)
		{
			throw new RuntimeException("请先初始化XMPPConnection连接");
		}
		return connection;
	}

}
