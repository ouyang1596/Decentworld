package cn.sx.decentworld.component;

import java.net.SocketException;

import android.content.Context;
import cn.sx.decentworld.common.SystemHelper;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

/**
 * @ClassName: WifiComponent
 * @Description: WIFI判断组件
 * @author yj
 * @date 2014-12-24 下午4:34:43
 * 
 */
@EBean(scope = Scope.Singleton)
public class WifiComponent
{

	@RootContext
	Context context;

	public String getIPAddress()
	{
		try
		{
			return SystemHelper.getLocalIPAddress() == null ? "0" : SystemHelper.getLocalIPAddress();
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
		return "0";
	}

	public String getMacAddress(Context context)
	{
		return SystemHelper.getLocalMacAddress(context) == null ? "0" : SystemHelper.getLocalMacAddress(context);
	}

	public boolean isConnected()
	{
		return SystemHelper.isConnected(context);
	}

}
