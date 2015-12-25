package cn.sx.decentworld.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import cn.sx.decentworld.common.Constants;

/**
 * Created by hh on 2015/7/26 0026.
 */
public class MediaUtil {

	/**
	 * @param bytes
	 * @return
	 * @deprecated 容易OOM，请使用后面两个方法
	 */
	public static Bitmap scaleToSettingSize(byte[] bytes) {
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		float settingSize = Constants.MAX_PIC_WIDTH_HEIGHT;
		try {
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
			int w = options.outWidth;
			int h = options.outHeight;
			options.inJustDecodeBounds = false;
			if (w > settingSize || h > settingSize) {
				Float scale;
				if (w > h) {
					scale = w / settingSize;
				} else {
					scale = h / settingSize;
				}
				options.inSampleSize = scale.intValue();
			}
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
					options);
			w = options.outWidth;
			h = options.outHeight;
			if (w > settingSize || h > settingSize) {
				Float scale;
				if (w > h) {
					scale = settingSize / w;
				} else {
					scale = settingSize / h;
				}
				Matrix matrix = new Matrix();
				matrix.postScale(scale, scale);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
			}
		} catch (Exception e) {
			Log.e(Constants.TAG, "缩放图片失败！", e);
			bitmap = null;
		}
		return bitmap;
	}

	/**
	 * @param path
	 * @return
	 */
	public static Bitmap scaleToSettingSize(Context context, String path) {
		Bitmap bm = null;
		try {
			Uri uri = Uri.parse(path);
			bm = scaleToSettingSize(context, uri);
		} catch (Exception e) {
			Log.e(MediaUtil.class.getName(), "处理图片失败", e);
		}
		return bm;
	}

	/**
	 * 先粗缩，后细缩
	 * 
	 * @param uri
	 * @return
	 */
	public static Bitmap scaleToSettingSize(Context context, Uri uri) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		float settingSize = Constants.MAX_PIC_WIDTH_HEIGHT;
		InputStream boundsInputStream = null;
		InputStream in = null;
		try {
			options.inJustDecodeBounds = true;
			boundsInputStream = context.getContentResolver().openInputStream(
					uri);
			BitmapFactory.decodeStream(boundsInputStream, null, options);
			int w = options.outWidth;
			int h = options.outHeight;
			options.inJustDecodeBounds = false;
			if (w > settingSize || h > settingSize) {
				Float scale;
				if (w > h) {
					scale = w / settingSize;
				} else {
					scale = h / settingSize;
				}
				options.inSampleSize = scale.intValue();
			}
			in = context.getContentResolver().openInputStream(uri);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			w = options.outWidth;
			h = options.outHeight;
			if (w > settingSize || h > settingSize) {
				Float scale;
				if (w > h) {
					scale = settingSize / w;
				} else {
					scale = settingSize / h;
				}
				Matrix matrix = new Matrix();
				matrix.postScale(scale, scale);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
			}
		} catch (Exception e) {
			Log.e(Constants.TAG, "缩放图片失败！", e);
		} finally {
			try {
				boundsInputStream.close();
				in.close();
			} catch (IOException e) {
				Log.e(Constants.TAG, "关闭流出错！", e);
			}
		}
		return bitmap;
	}

	/**
	 * 得到amr的时长
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static long getAmrDuration(String path) throws IOException {
		long duration = -1;
		int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0,
				0, 0 };
		RandomAccessFile randomAccessFile = null;
		try {
			File file = new File(path);
			randomAccessFile = new RandomAccessFile(file, "rw");
			long length = file.length();// 文件的长度
			int pos = 6;// 设置初始位置
			int frameCount = 0;// 初始帧数
			int packedPos = -1;
			// ///////////////////////////////////////////////////
			byte[] datas = new byte[1];// 初始数据值
			while (pos <= length) {
				randomAccessFile.seek(pos);
				if (randomAccessFile.read(datas, 0, 1) != 1) {
					duration = length > 0 ? ((length - 6) / 650) : 0;
					break;
				}
				packedPos = (datas[0] >> 3) & 0x0F;
				pos += packedSize[packedPos] + 1;
				frameCount++;
			}
			// ///////////////////////////////////////////////////
			duration += frameCount * 20;// 帧数*20
		} finally {
			if (randomAccessFile != null) {
				randomAccessFile.close();
			}
		}
		return duration;
	}
}
