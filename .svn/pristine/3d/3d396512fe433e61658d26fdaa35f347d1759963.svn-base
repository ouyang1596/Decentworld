package cn.sx.decentworld.connect;

import java.io.IOException;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.simple.eventbus.EventBus;

import android.os.Handler;
import android.os.Looper;
import cn.sx.decentworld.DWConnectionListener;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.config.AppConfig;
import cn.sx.decentworld.handleMessage.DWPacketListener;
import cn.sx.decentworld.handleMessage.DWPacketFilter;
import cn.sx.decentworld.listener.CommCallback;
import cn.sx.decentworld.listener.LoginListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

/**
 * @ClassName: XmppHelper.java
 * @Description:
 * @author: cj
 * @date: 2015年10月22日 下午3:58:55
 */
public class XmppHelper
{
    private static final String TAG = "XmppHelper";
    private static XMPPTCPConnection conn = null;

    private XmppHelper()
    {
        // 私有化，防止外部通过构造函数新建对象
    }

    /**
     * 获取XMPPTCPConnection
     * 
     * @return
     */
    public static XMPPTCPConnection getConnection()
    {
        LogUtils.v(TAG, "getConnection()");
        if (conn == null)
        {
            LogUtils.v(TAG, "getConnection() conn is null,then create it");
            return getNewConnection();
        }
        return conn;
    }

    /**
     * 获取一个新的XMPPTCPConnection
     */
    public static XMPPTCPConnection getNewConnection()
    {
        try
        {
            synchronized (XmppHelper.class)
            {
                LogUtils.v(TAG, "getNewConnection() ,conn is null,then create it,begin");
                // 获取Configuration
                ConnectionConfiguration connConfig = getDefaultConfig();
                // 创建连接
                conn = new XMPPTCPConnection(connConfig);
                // 建立连接
                conn.connect();
                // 增加监听，只要不新建连接，那么重连后监听仍然有效 （目前只监听断开连接和被挤下线的消息）
                conn.addConnectionListener(new DWConnectionListener());
                // 开启消息监听
                conn.addPacketListener(new DWPacketListener(), new DWPacketFilter());
                LogUtils.v(TAG, "getNewConnection() ,conn is null,then create it,end");
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "getNewConnection() Exception:" + e.getCause() + "," + e.getMessage());
        }
        return conn;
    }

    /**
     * 关闭连接
     */
    public static void closeConnection()
    {
        LogUtils.v(TAG, "closeConnection()");
        if (conn != null)
        {
            if (conn.isConnected())
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            LogUtils.i(TAG, "closeConnection() 即将关系连接，并置为null");
                            conn.disconnect();
                            conn = null;
                        }
                        catch (NotConnectedException e)
                        {
                            LogUtils.e(TAG, "closeConnection() NotConnectedException:" + e.toString());
                        }
                    }
                }).start();
            }
        }
    }

    /**
     * 发送信息
     * 
     * @param message
     */
    public static void sendMessage(Message message)
    {
        sendPacket(message);
    }

    /**
     * 发送信息
     * 
     * @param packet
     */
    public static void sendPacket(final Packet packet)
    {
        // 属于 连接为空、断开连接、没有登录 情况之一都将 重新连接
        try
        {
            conn.sendPacket(packet);
        }
        catch (NotConnectedException e)
        {
            LogUtils.e(TAG, "sendPacket() NotConnectedException:" + e.toString());
        }
    }

    /**
     * 发送上线通知
     */
    public static void sendPresence()
    {
        try
        {
            if (DecentWorldApp.getInstance().isMainActivityInit())
            {
                EventBus.getDefault().post(NetworkUtils.RECONNECTION_SUCCESSFUL, NotifyByEventBus.NT_NETWORK_STATE_OF);
                getConnection().sendPacket(new Presence(Presence.Type.available));
                LogUtils.v(TAG, "sendPresence() ConverstationFragment has init");
            }
            else
            {
                LogUtils.v(TAG, "sendPresence() ConverstationFragment is not init");
            }
        }
        catch (NotConnectedException e)
        {
            LogUtils.e(TAG, "sendPresence() NotConnectedException:" + e.toString());
        }
    }

    /**
     * 第一次登录
     * 
     * @param dwID
     *            用户ID
     * @param password
     *            密码（加密后）
     * @param listener
     *            回调监听
     */
    public static void firstLogin(final String dwID, final String password, final LoginListener listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what)
                {
                    case -1:
                        //连接为空
                        Exception exception = (Exception) msg.obj;
                        listener.onLoginFailure(exception);
                        break;
                    case 0:
                        //登录成功
                        String info = msg.obj.toString();
                        listener.onLoginSuccess(info);
                        break;
                    case 1:
                        //登录异常
                        Exception exception1 = (Exception) msg.obj;
                        listener.onLoginFailure(exception1);
                        break;
                    default:
                        break;
                }
            };
        };
        Thread loginThread = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    LogUtils.v(TAG, "firstLogin() params[dwID=" + dwID + ",password=" + password + "]");
                    Looper.prepare();
                    XMPPConnection conn = getNewConnection();
                    if (conn != null)
                    {
                        conn.login(dwID, password, "Smack");
                        sendHandlerMsg(handler, 0, "登录成功");
                    }
                    else
                    {
                        //下面代码需要优化，处理conn == null的情况
                        LogUtils.e(TAG, "firstLogin() run() con == nulll params[dwID=" + dwID + "]");
                        sendHandlerMsg(handler, -1, new NullPointerException("the conn is null"));
                    }
                }
                catch (SaslException e)
                {
                    LogUtils.e(TAG, "firstLogin() SaslException:" + e.toString());
                    sendHandlerMsg(handler, 1, e);
                }
                catch (XMPPException e)
                {
                    // 登录密码错误，提示：XMPPException...casue
                    // by:org.jivesoftware.smack.sasl.SASLErrorException:
                    // SASLError using PLAIN: not-authorized
                    LogUtils.e(TAG, "firstLogin()  XMPPException:" + e.toString());
                    sendHandlerMsg(handler, 1, e);
                }
                catch (SmackException e)
                {
                    LogUtils.e(TAG, "firstLogin() SmackException:" + e.toString());
                    sendHandlerMsg(handler, 1, e);
                }
                catch (IOException e)
                {
                    LogUtils.e(TAG, "firstLogin() IOException:" + e.toString());
                    sendHandlerMsg(handler, 1, e);
                }
            }
        });
        loginThread.setName("loginThread");
        loginThread.start();
    }
    
    /**
     * 重新建立连接
     */
    public static void reConnect(final LoginListener listener)
    {
        LogUtils.v(TAG, "reConnect() ");
        reConnect(listener, false);
    }
    
    /**
     * 重新连接
     * @param listener 回调监听
     * @param newConn 是否启用新的连接
     */
    public static void reConnect(final LoginListener listener,final boolean newConn)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what)
                {
                    case 0:
                        //登录成功
                        String info = msg.obj.toString();
                        listener.onLoginSuccess(info);
                        break;
                    case 1:
                        //登录异常
                        Exception exception1 = (Exception) msg.obj;
                        listener.onLoginFailure(exception1);
                        break;
                    default:
                        break;
                }
            };
        };
        Thread thread = new Thread()
        {
            public void run() {
              try
              {
                  String userID = DecentWorldApp.getInstance().getDwID();
                  String password = DecentWorldApp.getInstance().getPassword();
                  XMPPTCPConnection conn;
                  if(newConn)
                      conn = getNewConnection();
                  else
                      conn = getConnection();
                  LogUtils.v(TAG, "reLogin() " + conn.toString());
                  if (!conn.isConnected())
                  {
                      LogUtils.v(TAG, "reLogin() 没有连接到服务器，开始连接...");
                      conn.connect();
                  }
                  if (!conn.isAuthenticated())
                  {
                      LogUtils.i(TAG, "reLogin() 已经连接到服务器，但是没有授权，开始登录授权...");
                      conn.login(userID, password, "Smack");
                  }
                  XmppHelper.sendPresence();
                  sendHandlerMsg(handler, 0, "登录成功");
              }
              catch (Exception e)
              {
                  LogUtils.e(TAG, "openFireLogin() Exception:" + e.toString());
                  sendHandlerMsg(handler, 1, e);
              }
            };
        };
        thread.setName("relogin");
        thread.start();
    }

    /**
     * 获取默认配置
     */
    private static ConnectionConfiguration getDefaultConfig()
    {
        ConnectionConfiguration connConfig = new ConnectionConfiguration(Constants.HOST_OPEN_FIRE , Constants.PORT_OPEN_FIRE , Constants.SERVER_NAME);
        connConfig.setCompressionEnabled(false);// 不启用压缩 （默认值也是不启用）
        connConfig.setSecurityMode(SecurityMode.enabled);
        connConfig.setSendPresence(true);//
        connConfig.setReconnectionAllowed(true);
        SASLAuthentication.supportSASLMechanism("PLAIN");
        connConfig.setDebuggerEnabled(AppConfig.XMPP_DEBUGGER_ENABLED);
        return connConfig;
    }
    
    /**
     * 发送通知
     */
    private static void sendHandlerMsg(Handler handler ,int what,Object object)
    {
        android.os.Message message = android.os.Message.obtain();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }

}
