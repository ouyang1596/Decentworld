/**
 * 
 */
package cn.sx.decentworld.utils;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

/**
 * @ClassName: ImageLoaderHelper.java
 * @Description:
 * @author: yj
 * @date: 2015年11月10日 上午10:32:58
 */
public class ImageLoaderHelper {
	public static ImageLoader mImageLoader = ImageLoader.getInstance();
	public static DisplayImageOptions mOptions;

	// String picPath = Constants.HomePath + "/temp"
	// + ImageUtils.generateFileName() + ".png";
	public static void initImageLoader(Context context) {
		if (null == mOptions) {
			mOptions = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.default_icon)
					.showImageForEmptyUri(R.drawable.default_icon)
					.showImageOnFail(R.drawable.default_icon)
					.cacheInMemory(true).cacheOnDisk(true)
					.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
					.build();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					context).diskCache(
					new UnlimitedDiskCache(new File(Constants.HomePath
							+ "/imageLoader"))).build();
			mImageLoader.init(config);

			clearCache();
		}
	}

	public static void clearCache() {
		mImageLoader.clearMemoryCache();
		mImageLoader.clearDiskCache();
	}

	/**
	 * 删除指定url对应的图片
	 */
	public static void clearCacheByUrl(String url) {
		if (mImageLoader != null) {
			DiskCacheUtils.removeFromCache(url, mImageLoader.getDiskCache());
			MemoryCacheUtils
					.removeFromCache(url, mImageLoader.getMemoryCache());
		} else {
		}
	}
}
