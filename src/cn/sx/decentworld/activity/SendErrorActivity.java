package cn.sx.decentworld.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.ZipOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.CrashHandler;
import cn.sx.decentworld.utils.ExitAppUtils;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 *
 */
public class SendErrorActivity extends Activity implements View.OnClickListener
{
	private static final String TAG = "SendErrorActivity";

	@ViewInject(R.id.ok)
	private Button ok;
	@ViewInject(R.id.cancel)
	private Button cancel;
	@ViewInject(R.id.upload_exception_state)
	private TextView uploadExceptionState;
	@ViewInject(R.id.upload_exception_ll)
	private LinearLayout uploadExceptionLl;
	@ViewInject(R.id.upload_exception_dialog)
	private LinearLayout uploadExceptionDialog;

	private String error_msg;
	private String dwID;
	private Context mContext = SendErrorActivity.this;
	private SharedPreferences mSharedPreferences;
	private String IMEI;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ExitAppUtils.getInstance().addActivity(this);
//		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_exception_info);
		ViewUtils.inject(this);
		mSharedPreferences = CommUtil.getPreferences(mContext);
		error_msg = getIntent().getStringExtra("msg");
		dwID = getIntent().getStringExtra("dwID");

		initData();
		initEvent();

	}

	private void initData()
	{
		// 获取IMEI
		TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();

	}

	private void initEvent()
	{
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.ok:
				uploadExceptionLl.setVisibility(View.VISIBLE);
				uploadExceptionDialog.setVisibility(View.GONE);
				Toast.makeText(this, R.string.send_err_msg, Toast.LENGTH_SHORT).show();
				sendExceptionLog();
				break;
			case R.id.cancel:
				rebootLoginActivity();
				finish();
				break;
			default:
				break;
		}

	}

	private void sendExceptionLog()
	{

		String fileName = error_msg;
		if (fileName.endsWith(CrashHandler.FILE_EXT))
		{
			fileName = fileName.substring(0, fileName.length() - CrashHandler.FILE_EXT.length());
		}
		File oldFile = new File(CrashHandler.BASE_PATH + error_msg);
		File newFile = new File(CrashHandler.BASE_PATH + IMEI + "_" + error_msg);
		File zipFile = new File(CrashHandler.BASE_PATH + IMEI + "_" + fileName + ".zip");
		if (!oldFile.exists())
		{
			Log.d("TAG", "异常捕获存储目录不存在");
			return;
		}
		// 文件名添加IMEI信息,
		boolean renameTo = oldFile.renameTo(newFile);
		BufferedWriter attachWriter = null;
		try
		{
			// 向文件中插入imei、time字段
			attachWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile , true)));
			attachWriter.write("imei=" + IMEI + "\r\n");
			attachWriter.write("time=" + fileName + "\r\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (attachWriter != null)
			{
				try
				{
					attachWriter.close();
				}
				catch (IOException e)
				{
					//
				}
			}
		}
		if (renameTo)
		{
			// 压缩文件
			try
			{
				FileUtils.zipFile(newFile, new ZipOutputStream(new FileOutputStream(zipFile)), null);
				// 发送数据
				Log.d("TAG", "异常捕获存_发送数据");
				submitExFile(zipFile);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送日志数据
	 */
	public void submitExFile(File file)
	{
		RequestParams mParams = new RequestParams();
		mParams.addBodyParameter("file", file);
		mParams.addBodyParameter("dwID", dwID);
		LogUtils.i(TAG, "异常捕获—开始上传,filePath="+file.getAbsolutePath()+",dwID="+dwID);
		
		HttpUtils mHttp = new HttpUtils(60 * 1000);
		String url = Constants.CONTEXTPATH + "/exception/upload";
		mHttp.send(HttpRequest.HttpMethod.POST, url, mParams, new RequestCallBack<String>()
		{
			@Override
			public void onStart()
			{
				LogUtils.e(TAG, "开始上传错误报告");
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading)
			{
				if (isUploading)
				{
					uploadExceptionState.setText(current + "/" + total);
					LogUtils.e(TAG, "开始上传错误报告--"+current + "/" + total);
				}
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// {"head":{"retcode":1,"msg":""}}
				LogUtils.e(TAG, "Exception上传成功");
				uploadExceptionState(true);
				rebootLoginActivity();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// Toast.makeText(mContext, "Exception发送失败，请重试",
				// Toast.LENGTH_SHORT).show();
				LogUtils.e(TAG, "Exception上传失败");
				uploadExceptionState(false);
				rebootLoginActivity();
			}
		});

	}

	/**
	 * @param flag
	 *            标记是否上传了错误日志
	 */
	private void uploadExceptionState(boolean flag)
	{
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(Constants.EXCEPTION_LOG_UPLOAD, flag);
		editor.apply();
		if (flag)
		{
			File file = new File(CrashHandler.BASE_PATH);
			if (file.exists())
			{
				File[] files = file.listFiles();
				if (files != null && files.length > 0)
				{
					for (File f : files)
					{
						f.delete();
					}
				}
			}
		}
	}

	private void rebootLoginActivity()
	{
		startActivity(new Intent(mContext , DefaultActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		SendErrorActivity.this.finish();
		ExitAppUtils.getInstance().delActivity(this);
	}

}
