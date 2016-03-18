/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

import android.os.AsyncTask;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.listener.onConnectOpenFireListener;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: ConnectOpenFireTask.java
 * @Description:
 * @author: Frewen.W
 * @date: 2015-12-24 下午10:18:04
 */
public class ConnectOpenFireTask extends AsyncTask<String, Void, Integer> {

    private static final String TAG = ConnectOpenFireTask.class.getSimpleName();
    public static final Integer SUCCESS = 1;
    public static final int FAILURE = 0;

    private onConnectOpenFireListener mConnectedOpenFireListener;
    /** XMPP服务器的连接器 */
    private XMPPConnection conn;

    public void setOnConnectOpenFireListener(onConnectOpenFireListener listener) {
        this.mConnectedOpenFireListener = listener;
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Integer doInBackground(String... params) {
        //TODO 这句话还是有问题，第二个ElseIf明显不可能实现嘛！！！
        try {
//            conn = DecentWorldApp.getInstance().getConnectionImpl();
            conn = XmppHelper.getConnection(null);
            if (conn.isConnected()&&conn.isAuthenticated()) {
            	LogUtils.i("test", "1");
//            	conn.connect();
//            	conn.login(params[0], params[1], params[2], "Smack");
//              conn.sendPacket(new Presence(Presence.Type.available));
                return SUCCESS;
            }else{
//            	conn.connect();
            	LogUtils.i("test", "2");
            	LogUtils.i("test", "conn.isConnected()"+conn.isConnected());
            	LogUtils.i("test", "conn.isAuthenticated()"+conn.isAuthenticated());
            	LogUtils.i("test", "conn.wasAuthenticated()"+conn.wasAuthenticated());
            	if(!conn.isConnected()){
            		conn.connect();
            	}
            	if(!conn.isAuthenticated()&&!conn.wasAuthenticated()&&conn.isConnected()){
                	LogUtils.i("test", "3");
            		conn.login(params[0], params[1], params[2], "Smack");
            	}
            	return SUCCESS;
            }
        } catch (Exception e) {
        	LogUtils.i("test", "4");
            LogUtils.e("test", "connected onpenfire error");
            LogUtils.e("test", e.getMessage()+e.getCause());

            return FAILURE;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        //回调连接成功或者失败的监听接口
        if (result == SUCCESS) {
            if (null != mConnectedOpenFireListener) {
                mConnectedOpenFireListener.onConnetedOpenFire(SUCCESS);
            }
        } else if (result == FAILURE) {
            if (null != mConnectedOpenFireListener) {
                mConnectedOpenFireListener.onConnetedOpenFire(FAILURE);
            }
        }
    }

}
