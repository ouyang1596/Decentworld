package cn.sx.decentworld;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import cn.bmob.v3.Bmob;
import cn.sx.decentworld.bean.ChatRoomInfo;
import cn.sx.decentworld.bean.RegisterBean;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.CrashHandler;
import cn.sx.decentworld.common.FilePath;
import cn.sx.decentworld.common.SystemHelper;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.SoundPoolUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

import com.activeandroid.ActiveAndroid;
import com.googlecode.androidannotations.annotations.EApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * @ClassName: DecentWorldApp.java
 * @Description: 主要完成的工作 1.保存全局变量 ; 2.初始化ActiveAndroid数据库 ; 3.初始化变量；
 * 
 * @author: cj
 * @date: 2015年10月18日 下午4:55:25
 */
@EApplication
public class DecentWorldApp extends Application {
	private static final String TAG = "DecentWorldApp";
	// 全局变量
	private static DecentWorldApp instance;
	private static Context applicationContext;
	// 当前用户ID
	private String dwID = "";
	// 密码
	private String password = "";
	// 储存多张照片路径
    public static ArrayList<String> mPicList;
    // 储存一张照片路径
    public static String tempPicPath = null;
	public static final String MAIN_KEY = "1";
	public static RegisterBean registerBean;
	// 通过此来判断聊天室退出是否需要调用退出接口
	public static boolean ifFixed;
	public static ChatRoomInfo chatRoomInfo;
	// 判断是否有网络
	public static boolean hasNet = true;
	// 标记MainActivity是否初始化完毕，作为在ReconnectionManager中是否发送上线消息的标志
	private boolean isMainActivityInit = false;
	public static int doubtWan = -1;// 0表示疑1表示真2表示腕
	// 网络连接超时时间
	public static int isFirst = -1;
	// 初始化Bmob服务，用于提交测试数据
	private static final String BmobAppKey = "1f90a4de6def9c5ce28bfafafdf19c39";
	// 最大的消息ID，用于与服务器同步.
	private long maxMsgID = -1;
	public int windowWidth, windowHeight;
	// 是否上传日志文件
	private boolean isUploadLogFile = true;
	// 标记3号消息任务是否启动
	private AtomicBoolean isStart = new AtomicBoolean(false);
	public static int WX_AUTHORIZE_STATE = -1;// 0登录时调用1微信收钱时调用

	/**
	 * 初始化入口
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "onCreate() ");
		// 获取进程名字
		String processName = SystemHelper.getProcessName(this, android.os.Process.myPid());
		if (processName != null) {
			Log.v(TAG, "onCreate() The current process's name is " + processName);
			boolean defaultProcess = processName.equals(Constants.REAL_PACKAGE_NAME);
			if (defaultProcess) {
				Log.v(TAG, "onCreate() init main process");
				initAppForMainProcess();
			} else if (processName.contains(":test")) {
				Log.i(TAG, "onCreate() init service process");
				initAppForServiceProcess();
			} else {
				Log.i(TAG, "onCreate() init other process");
				initAppForOtherProcess();
			}
		}
	}

	/**
	 * 初始化主进程
	 */
	private void initAppForMainProcess() {
		instance = this;
		applicationContext = this;
		// 初始化数据库
		ActiveAndroid.initialize(this);
		// 友盟统计设置为调试模式
		MobclickAgent.setDebugMode(false);
		// 初始化声音池
		SoundPoolUtils.init(this);
		// 初始化提示信息工具
		ToastUtil.initToast(this);
		mPicList = new ArrayList<String>();
		// 初始化文件存储目录
		FilePath.initialize();
		// 初始化ImageLoadHepler
		ImageLoaderHelper.initImageLoader(this);
		// 初始化异常报告工具
		CrashHandler.getInstance().init(this);
		ImageUtils.saveBitmap(getResources());
		// 初始化Bmob
		Bmob.initialize(this, BmobAppKey);
	}

	/**
	 * 初始化服务进程
	 */
	private void initAppForServiceProcess() {
		LogUtils.v(TAG, "initAppForServiceProcess()");

	}

	/**
	 * 初始化其它进程
	 */
	private void initAppForOtherProcess() {
		LogUtils.v(TAG, "initAppForOtherProcess()");

	}

	/**
	 * 获得DecentWorldApp的唯一实例
	 * 
	 * @return
	 */
	public static DecentWorldApp getInstance() {
		return instance;
	}

	/**
	 * 获取全局的Context
	 *
	 * @return
	 */
	public static Context getGlobalContext() {
		return applicationContext;
	}

	/**
	 * 设置与Dw服务器通信的dwID
	 */
	public void setDwID(String dwID) {
		this.dwID = dwID;
	}

	/**
	 * 获取与Dw服务器进行通信的dwID,如果内存中没有，那么冲sp文件中拿取
	 */
	public String getDwID() {
		if (CommUtil.isBlank(dwID)) {
			dwID = UserInfoHelper.getDwID();
		}
		return dwID;
	}

	/**
	 * 设置密码
	 */
	public void setPassword(String pwd) {
		this.password = pwd;
	}

	/**
	 * 获取密码
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public void onTerminate() {
		LogUtils.i(TAG, "销毁Application");
		super.onTerminate();
		/** 完成数据库的清理工作 **/
		ActiveAndroid.dispose();
	}

	/**
	 * @return the isMainActivityInit
	 */
	public boolean isMainActivityInit() {
		return isMainActivityInit;
	}

	/**
	 * @param isMainActivityInit
	 *            the isMainActivityInit to set
	 */
	public void setMainActivityInit(boolean isMainActivityInit) {
		this.isMainActivityInit = isMainActivityInit;
	}

	/**
	 * @return the isUploadLogFile
	 */
	public boolean isUploadLogFile() {
		return isUploadLogFile;
	}

	/**
	 * @param isUploadLogFile
	 *            the isUploadLogFile to set
	 */
	public void setUploadLogFile(boolean isUploadLogFile) {
		this.isUploadLogFile = isUploadLogFile;
	}

	/**
	 * @return the isStart
	 */
	public AtomicBoolean getIsStart() {
		return isStart;
	}

	/**
	 * @param isStart
	 *            the isStart to set
	 */
	public void setIsStart(AtomicBoolean isStart) {
		this.isStart = isStart;
	}

}
