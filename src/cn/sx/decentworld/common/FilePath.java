/**
 * 
 */
package cn.sx.decentworld.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

/**
 * @ClassName: FilePath.java
 * @Description: 文件保存路径
 * @author: cj
 * @date: 2016年2月15日 下午4:12:06
 */
public class FilePath 
{
	private static List<String> filePaths = new ArrayList<String>();
	// 主目录
	public static final String HOME = Environment.getExternalStorageDirectory() + File.separator + "decentworld";
	// 拍照获得的图片
	public static final String IMG_SCREENSHOT = HOME + "images/screenshots";
	// 临时图片jpg
	public static final String IMG_TEMP_JPG = HOME + "/temp.jpg";
	// 临时图片png
	public static final String IMG_TEMP_PNG = HOME + "/temp.png";
	// 分享
	public static final String SHARE = HOME + "/share";
	// 注册中储存相册中的图片
	public static final String CAMERA_PATH = HOME + "/cameraPic/";
	// 裁剪图片
	public static final String CLIP_IMAGE = HOME + "/clipImage/";
	// 语音文件夹子路径
	public static final String AUDIO_RECEIVE = HOME + "/voiceReceive";
	// 接收到的语音储存路径(单聊/群聊)
	public static final String AUDIO_PATH = HOME + "/audio/";
	// 图片缩略图子路径
	public static final String PATH_THUMBNAIL = HOME + "/pic_thumbnail";
	/**
	 * Log信息
	 */
	// 异常报告存储的文件夹
	public static final String HOME_EXCEPTION_REPORT = CommUtil.getAvailableStoragePath() + "crashLog/";
    // 存储Log信息的文件夹
    public static final String HOME_LOG_REPORT = CommUtil.getAvailableStoragePath() + "log/";

	
	/**
	 * 私有构造函数
	 */
	private FilePath() {

	}

	/**
	 * 初始化文件存储目录
	 */
	public static void initialize() {
		addPath();
		checkPath();
	}

	/**
	 * 增加路径
	 */
	private static void addPath() {
		filePaths.add(HOME);
		filePaths.add(IMG_SCREENSHOT);
		filePaths.add(SHARE);
		filePaths.add(CAMERA_PATH);
		filePaths.add(CLIP_IMAGE);

	}

	/**
	 * 检查路径是否存在
	 */
	private static void checkPath() {
		for (String str : filePaths) {
			File f = new File(str);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
	}

}
