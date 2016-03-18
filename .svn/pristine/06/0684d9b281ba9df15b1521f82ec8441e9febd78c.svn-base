/**
 * 
 */
package cn.sx.decentworld.component;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: ClipImageComponent.java
 * @Description: 
 * @author: yj
 * @date: 2015-3-16 下午3:08:02
 */
@EBean
public class ClipImageComponent {
		@RootContext
		Activity activity;
		
		public static int CLIP_PICTURE = 88;
		public void startPhotoZoom(Uri uri) {

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 140);
			intent.putExtra("outputY", 140);
			intent.putExtra("return-data", true);
			activity.startActivityForResult(intent, CLIP_PICTURE);
		}
}
