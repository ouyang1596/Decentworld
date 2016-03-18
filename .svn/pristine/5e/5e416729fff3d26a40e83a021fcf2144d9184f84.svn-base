/**
 * 
 */
package cn.sx.decentworld.component;

import android.content.Context;
import android.widget.ImageView;
import cn.sx.decentworld.common.BitmapCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

/**
 * @ClassName: ImageComponent.java
 * @Description:
 * @author: yj
 * @date: 2015-3-6 下午2:12:38
 */
@EBean(scope = Scope.Singleton)
public class ImageComponent {
	@RootContext
	Context context;

	private ImageLoader mImageLoader;

	private ImageListener imageListener;

	private RequestQueue mQueue;
	@AfterInject
	public void afterInject(){
		 mQueue = Volley.newRequestQueue(context);
	}
	/**
	 * 将网络图片加载到指定imageview控件中
	 * @param imageview   指定的imageview
	 * @param url	      网络图片URL
	 */
	public void imageToImageView(ImageView imageview, String url) {
		
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
		imageListener = ImageLoader.getImageListener(imageview, 0, 0);
		mImageLoader.get(url, imageListener, 0, 0);
	}
	
	/**
	 * 将网络图片加载到指定imageview控件中
	 * @param imageview   指定的imageview
	 * @param url         网络图片URL
	 * @param maxWidth    最大宽度
	 * @param maxHeight   最大高度
	 */
	public void imageToImageView(ImageView imageview, String url,int maxWidth,int maxHeight) {
		imageListener = ImageLoader.getImageListener(imageview, 0, 0);
		mImageLoader.get(url, imageListener, maxWidth, maxHeight);
	}
	
	/**
	 * 将网络图片加载到指定imageview控件中
	 * @param imageview   指定的imageview
	 * @param url         网络图片URL
	 * @param defaultImageResId  默认加载的图片资源ID
	 * @param errorImageResId    图片加载错误的图片资源ID
	 * @param maxWidth    最大宽度
	 * @param maxHeight   最大高度
	 */
	public void imageToImageView(ImageView imageview, String url,int defaultImageResId,int errorImageResId,int maxWidth,int maxHeight) {
		RequestQueue mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
		imageListener = ImageLoader.getImageListener(imageview, defaultImageResId, errorImageResId);
		mImageLoader.get(url, imageListener, maxWidth, maxHeight);
	}
}
