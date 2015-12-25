/**
 * 
 */
package cn.sx.decentworld.utils;

import java.io.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.content.Context;
import android.util.Log;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;

/**
 * @ClassName: FileUtils.java
 * @Description:
 * @author: dyq
 * @date: 2015年10月24日 下午4:02:52
 */
public class FileUtils {
	private static final String TAG = "FileUtils";
    private static final int BUFF_SIZE = 1024; // 1k Byte

	/**
	 * 在SD卡上创建文件
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 *
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 *
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File writeToSDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			// 清缓存，将流中的数据保存到文件中
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 *
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File writeToSDFromInput(String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			file = createSDFile(fileName);// 全路径//绝对路径
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			// 清缓存，将流中的数据保存到文件中
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 随机生成文件名（不带后缀）
	 * 
	 * @return
	 */
	public static String generateFileName() {
		// TODO Auto-generated method stub

		return UUID.randomUUID().toString();
	}

	/**
	 * 重命名文件
	 * 
	 * @param path
	 * @param oldname
	 * @param newname
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	public static void renameFile1(File file, String newname) {
		if (!file.getName().equals(newname)) {
			// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(file.getAbsolutePath());
			File newfile = new File(file.getParent() + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	public static void deleteFileDir(Context context) {
		File file = context.getCacheDir();
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				f.delete();
			}
			file.delete();
		}

	}

	/**
	 * 删除一个文件
	 * 
	 * @param context
	 * @param file
	 *            可以为一个文件，也可以为一个目录
	 */
	public static void deleteFile(Context context, File file) {
		LogUtils.i(TAG, "deleteFile");
		if (null == file) {
			return;
		}
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFiles) {
				deleteFile(context, f);
			}
			file.delete();
		}
	}

    public static void closeInputStream(InputStream... ins) {
        if (ins == null) {
            return;
        }
        for (InputStream in : ins) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }


    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param zipout   压缩的目的文件
     * @param rootpath 压缩的文件路径
     */
    public static void zipFile(File resFile, ZipOutputStream zipout, String rootpath) {
        try {
            if (CommUtil.isBlank(rootpath)) {
                rootpath = resFile.getName();
            } else {
                rootpath = rootpath + (rootpath.endsWith("/") ? Constants.DEFAULT_BLANK : File.separator) + resFile.getName();
            }

            rootpath = new String(rootpath.getBytes("8859_1"), "UTF-8");

            if (resFile.isDirectory()) {
                File[] fileList = resFile.listFiles();
                for (File file : fileList) {
                    zipFile(file, zipout, rootpath);
                }
            } else {
                byte buffer[] = new byte[BUFF_SIZE];
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
                zipout.putNextEntry(new ZipEntry(rootpath));
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, realLength);
                }
                in.close();
                zipout.flush();
                zipout.closeEntry();
            }
            Log.d("TAG", "压缩完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
