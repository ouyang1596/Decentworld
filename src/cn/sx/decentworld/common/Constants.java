package cn.sx.decentworld.common;

import java.io.File;

import android.os.Environment;
import cn.sx.decentworld.R;

/**
 * @ClassName: Constants
 * @Description: 常量定义类
 * @author cj
 * @date 2014-12-27 上午10:51:16
 */
public class Constants {
	public static final String DW_TAG = "dw";
	public static final String DEFAULT_SPACE = " ";
	/**
	 * 适配手机参数
	 */
	public static int screenWidth = 0;
	public static int UI_WIDTH = 720;
	/** UI设计的基准宽度. */
	public static int UI_HEIGHT = 1280;
	/** UI设计的基准高度. */
	public static int UI_DENSITY = 2;
	/** UI设计的密度. */
	/** 与服务器端连接的协议名 */
	public static final String PROTOCAL = "http://";
	/**
	 * IP地址
	 */
	public static final String HOST = "112.74.13.117";// 阿里
	public static final String HOST123 = "192.168.1.123";// LYW
	public static final String HOST115 = "192.168.1.115";// LXD
	public static final int PORT = 5222;

	/**
	 * 端口号
	 */
	public static final String PORT_8080 = ":8080";
	public static final String PORT_9090 = ":9090";

	/**
	 * APP服务器的名字
	 */
	public static final String AppName = "/DecentWorldServer";
	/**
	 * 当服务器发生变更时，只要修改下面一个IP即可 若为 阿里云服务器 IP = HOST;（阿里） 若为本地 服务器 IP = HOST123;（龙）
	 */
	public static String IP = HOST;
	// 调用dw服务器接口的基本路径
	public static final String CONTEXTPATH = PROTOCAL + IP
			+ ":80/DecentWorldServer";// 调用接口
	// 调用openfire服务器接口的基本路径
	public static final String CONTEXTPATH_OPENFIRE = PROTOCAL + IP
			+ ":9090/plugins/decentworld";
	// 连接openfire服务器进行通讯
	public static final String HOST_OPEN_FIRE = IP;// openfire IP
	public static final int PORT_OPEN_FIRE = 5222;// openfire端口
	public static final String PRE_SERVER_NAME = "@";
	public static final String BACK_SERVER_NAME = "decentworld";
	public static final String SERVER_NAME = PRE_SERVER_NAME + BACK_SERVER_NAME;// 服务器名字
	public static final String CONTEXTPATH_LXD = PROTOCAL + "192.168.1.115"
			+ ":80/DecentWorldServer";// 调用接口
	/**
	 * 处理结果配置18307595364
	 */
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAILED = 0;
	public static final int STATUS_SERVER_FAILED = -1;
	public static final int STATUS_NETWORK_ERROR = -2;
	public static final String URI_FILE = "file://";
	// 拍照存储的文件名
	public static final String IMAGE_PATH = "DecentWorld";
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final File FILE_LOCAL = new File(FILE_SDCARD,
			Constants.IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");
	// 环信常量
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String CHAT_ROBOT = "item_robots";
	// 单聊
	public static final String SINGLE_CHAT = CONTEXTPATH + "/chat/singlechat";
	public static final String GET_ASSET = CONTEXTPATH + "/set/getWealth";
	public static final String GET_USER_INFO = CONTEXTPATH + "/set/getUserInfo";
	public static final String UPDATE_SIGN = CONTEXTPATH + "/set/updateSign";
	public static final String UPDATE_NICKNAME = CONTEXTPATH
			+ "/set/updateNickName";
	public static final String UPDATE_WONTH = CONTEXTPATH + "/set/updateWonth";
	public static final String REQUEST_SEND_MESSAGE = CONTEXTPATH
			+ "/validate/singleChat";
	public static final String REQUEST_SINGLECHAT_CHARGE = CONTEXTPATH
			+ "/charge/singleChat";
	public static final String SET_SIGN = CONTEXTPATH + "/user/setSign";
	public static final String SET_WEALTH = CONTEXTPATH + "/user/setWealth";
	public static final String SET_WORTH = CONTEXTPATH + "/user/setWorth";
	public static final String DEFAULT_BLANK = "";
	// 使用EventBus定义的变量
	public static final int NOTIFY_UPDATE_MESSAGELIST = 1;// 通知更新消息表界面
	public static final int REQUEST_CODE = 10;
	public static final int RESULT_CODE_LIKE = 20;
	public static final int RESULT_CODE_DISLIKE = 30;
	public static final int RESULT_CODE_REFRESH = 40;
	// 登录注册常量
	public static final int SUCC = 1004;
	public static final int FAILURE = 1003;
	public static final int SAVE = 1;
	public static final int ENSURE = -1;
	public static final int CANCEL = 0;
	public static final String TALENT = "0";
	public static final String MONEY = "1";
	public static final String APPEARANCE = "2";
	public static final String IS_STUDENT = "1";
	public static final String NO_STUDENT = "0";
	public static final String NET_WRONG = "网络错误";
	// 注册url
	public static final String API_UPDATA_STUDENT = "/register/updateStudent";
	public static final String API_UPDATA_TYPE = "/register/updateType";
	public static final String API_REGISTER_NICK_IMAGES = "/register/register";
	public static final String API_REQUEST_CODE = "/user/resetpwd";
	public static final String API_CHECK_ID = "/user/checkIDCard";
	public static final String API_RESET_PWD = "/user/setpwd";
	public static final String API_FORGET_PWD_GET_IDENTIFYING_CODE = "/user/forgetPassword";
	public static final String API_FORGET_PWD_CHECK_CODE = "/user/checkCode";
	// 获取附近的陌生人
	public static final String API_GET_NEARBY_STRANGER = "/stranger/getNearbyStranger";
	public static final String API_LIKE_STRANGER = "/stranger/like";
	public static final String API_DISLIKE_STRANGER = "/stranger/dislike";
	public static final String API_GET_FRIEND_INFO = "/user/getFriendInfo";
	public static final String API_CREATE_ANONYMOUS_IDENTIFY = "/user/createAnonymousIdentity";
	public static final String API_UPDATE_TYPE_AGAIN = "/checkBeauty/updateTypeAgain";
	public static final String DW_ID = "dwID";
	public static final String IF_LIKE = "ifLike";
	public static final int ITEM_KEY = R.id.etv_occupation;
	// 路径
	public static final String HOME_PATH = Environment
			.getExternalStorageDirectory() + File.separator + "decentworld";
	// 裁剪图片
	public static final String CLIP_IMAGE = "/clipImage/";
	// 语音文件夹子路径
	public static final String AUDIO_RECEIVE_PATH = File.separator
			+ "voiceReceive";
	// 接收到的语音储存路径(单聊/群聊)
	public static final String AUDIO_PATH = "/audio/";
	// 注册中储存相册中的图片
	public static String CAMERA_PATH = "/cameraPic/";
	// 储存临时文件
	public static final String IMAGE_FILE_LOCATION = Constants.HOME_PATH
			+ "/temp.png";
	// 图片缩略图子路径
	public static final String thumbnailPath = File.separator + "pic_thumbnail";
	// 下载路径
	public static final String DOWNLOAD_PATH = "download_path";
	// 登录
	public static final String API_GET_USERID = "/user/getUserId";
	// 隐私设置
	public static final String API_SET_BANKCARD = "/user/setBankCard";
	public static final String API_SET_PHONENUM = "/user/setPhoneNum";
	public static final String API_RESET_PASSWORD = "/user/resetpwd";
	public static final String AUTH_SETTING = "/auth/setAuthority";
	public static final String AUTH_GET = "/auth/getAuthority";
	// 图片种类（分别依次为本地文件绝对路径、网络url）
	public static final int IMAGE_LOCAL = 1;
	public static final int IMAGE_NET = 2;
	public static final String TOKEN = "token";
	public static final String USERNAME = "usernmae";
	public static final String PASSWORD = "password";
	public static final String RESULT_CODE = "result_code";
	public static final String RESEND = "/resend";
	public static final String SETTING = "setting";
	// 聊天室设置
	public static final String API_GET_MY_ROOMS = "/getMyRooms";
	public static final String API_CREATE_CHATROOM = "/createChatRoom";
	public static final String API_CREATE_SUBJECT = "/createSubject";
	public static final String API_SET_OWNER_PROFILE = "/setOwnerProfile";
	public static final String API_GET_CURRENT_SUBJECT = "/getCurrentSubject";
	public static final String API_SET_OWNER_INFO = "/setOwnerInfo";
	public static final String API_UPDATA_SUBJECT = "/updateSubject";
	public static final String API_SET_CHARGE_AMOUNT = "/setChargeAmount";
	public static final String API_GET_SUBJECT_LIST = "/getSubjectList";
	public static final String API_GET_OWNER_INFO = "/getOwnerInfo";
	public static final String API_GET_ANONYMOUS = "/anonymous/getAnonymous";
	public static final String API_CHECK_BEAUTY_PAY_RETRY = "/checkBeauty/payRetry";
	public static final float MAX_PIC_WIDTH_HEIGHT = 1280;
	public static final int CHOICE_ONE_PIC = 1;
	public static final String TAG = "bm";
	// ---------
	public static final String ASPECTX = "ASPECTX";
	public static final String ASPECTY = "ASPECTY";
	public static final String OUTPUTX = "OUTPUTX";
	public static final String OUTPUTY = "OUTPUTY";
	public static final String ADD_NEW_THEME = "0";
	public static final String EDIT_NEW_THEME = "1";
	public static final String FIXED = "0";
	public static final String PERCENT = "1";
	public static final int TEXT_REQUEST_CODE = 100;
	public static final int IMAGE_REQUEST_CODE = 200;
	public static final int AUDIO_REQUEST_CODE = 300;
	public static final int NOTICE_ID = 1222;
	public static final String IS_TO_WORTH = "IS_TO_WORTH";
	public static final int IF_TO_WORTH = 1;// 1代表不去设置身价
	// 审核接口
	public static final String API_EXAMINE_PASS = "/checkBeauty/support";
	public static final String API_EXAMINE_NO_PASS = "/checkBeauty/unsupport";
	// 审核常量
	public static final int CHECK_TRY_AGAIN = -1;
	public static final int CHECK_ANOTHER_METHOD = 1;
	public static final String CHECK = "CHECK";
	public static final String EXCEPTION_LOG_UPLOAD = "EXCEPTION_LOG_UPLOAD";
	public static final String TAG_BM = "bm";
	public static final String DEVELOPING_NOTIFY = "程序员努力开发中...";// 未开发提示
	// 支付接口
	public static final String API_CREATE_ORDER = "/charge/createOrder";
	public static final String API_RECOMMEND = "/user/recommend";
	public static final String API_GUARANTEE = "/set/Guarantee";
	// 微信appID
	public static final String APP_ID = "wx8a6304437033f400";
	// --------- 支付相关常量--------------
	// 商户PID
	public static final String PARTNER = "2088121118480062";
	// 商户收款账号
	public static final String SELLER = "95372537@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICXAIBAAKBgQDUFRSMLh4ZpWol4v/4voWXZJlGFifrf7k1plavy2xwJACL9B8FgcVRqyvZbthTO9BV1frD0/82akBA25viaUcWVNtUc7AJ+KeAXQApxKmbOmYGw+V52+FCO+E7q9g6buO0O9cUrum7vw3/N7kr8QFj2UF8ufJtjo0H0EBqBYRwNQIDAQABAoGABVy3FUhBOJLHGUghArAGfqpreasrvTeQfjvDa1RwXHF8lg1JD4GVdJgoVqoHzCrx/ImZeoQMqtqK31juGw5gXrNZvzyoef6jCYtyXQ6N8kFwFN2lU5skPBZKyIHvu7ys7VDdE6YwkGEh2IfgznlquslSRV4lqfJYPGAdcMXtSa0CQQD9NUQZptuIhWKSFxNF7S09eDmeLyaro1uw8yXnCzVM3oxYzlEwaV6w9N1xTKmGbVwftxh1i5cjhqpRdT04iWWzAkEA1mu6Y6iszFzL/UpXtIAND0MJSdWmFJySQszmOi4/n61nQzmBVCL1vn913RUqKh8zIVpkM92hbLI1RaiVzSAudwJADfOQZAVwlS4cR0ZkNnciBgAI1LFuZQikNbTAAuH4NtsQSphbmtpDcGadiO+ba5+88rZo75kYY8m4urJGBEZ1xwJAfLI/vi9c9qhM1WvDn6wMbEC/CRpfsILxqDmC9njo9VKhx/2MUcLXCUcU41Kop8BnOtFXjrYoZ48n3e/R1m4NtwJBAKaSRswLyjn2TBfFpBt7WseP+BTAeOTIMLBCmyfDtE4PBwe8hCIvdtlaCy/y3VXIxHAYq9GDTaJODZQcc3jPW30=";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static final int SDK_PAY_FLAG = 1;

	public static final int SDK_CHECK_FLAG = 2;
}
