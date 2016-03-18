package cn.sx.decentworld.component;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

/** 
* @ClassName: ToastComponent 
* @Description: 消息提醒组件 
* @author rw 
* @date 2014-12-24 下午4:34:26 
*  
*/ 
@EBean(scope = Scope.Singleton)
public class ToastComponent 
{
	@RootContext
	Context context;
	
	@RootContext
	Activity activity;

	public void show(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public void showLong(String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public void showInThread(final String message){
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
