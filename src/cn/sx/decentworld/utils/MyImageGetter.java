package cn.sx.decentworld.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.FilePath;

public class MyImageGetter implements ImageGetter {

	private Context context;
	private View view;
	private String imageName;
	private List<String> filePaths = new ArrayList<String>();

	public MyImageGetter(Context context, View tv) {
		this.context = context;
		this.view = tv;
	}

	@Override
	public Drawable getDrawable(String source) {
		// 将source进行加密并保存至本地
		imageName = AES.encode(source);
		String sdcardPath = Environment.getExternalStorageDirectory().toString(); // 获取SDCARD的路径
		// 获取图片后缀名
		String[] ss = source.split("\\.");
		String ext = ss[ss.length - 1];
		// 最终图片保持的地址
		String savePath = FilePath.HOME + FilePath.CHATROOM + imageName + "." + ext;
		File file = new File(savePath);
		if (file.exists()) {
			// 如果文件已经存在，直接返回
			// Drawable drawable = Drawable.createFromPath(savePath);
			// if (drawable != null) {
			// Log.i("bm", "width--" + drawable.getIntrinsicWidth() + "height--"
			// + drawable.getIntrinsicHeight());
			// drawable.setBounds(0, 0, 1024, 682);
			// }
			filePaths.add(savePath);
			Bitmap decodeFile = BitmapFactory.decodeFile(savePath);
			Log.i("bm", "width--" + decodeFile.getWidth() + "height--" + decodeFile.getHeight());
			Drawable drawable = Drawable.createFromPath(savePath);
			drawable.setBounds(0, 0, decodeFile.getWidth(), decodeFile.getHeight());
			return drawable;
		}

		// 不存在文件时返回默认图片，并异步加载网络图片
		Resources res = context.getResources();
		URLDrawable drawable = new URLDrawable(res.getDrawable(R.drawable.ic_launcher));
		new ImageAsync(drawable).execute(savePath, source);
		return drawable;

	}

	private class ImageAsync extends AsyncTask<String, Integer, Drawable> {

		private URLDrawable drawable;

		public ImageAsync(URLDrawable drawable) {
			this.drawable = drawable;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			String savePath = params[0];
			String url = params[1];

			InputStream in = null;
			try {
				// 获取网络图片
				HttpGet http = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) client.execute(http);
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();
			} catch (Exception e) {
				try {
					if (in != null)
						in.close();
				} catch (Exception e2) {
				}
			}

			if (in == null)
				return drawable;
			try {
				File file = new File(savePath);
				String basePath = file.getParent();
				File basePathFile = new File(basePath);
				if (!basePathFile.exists()) {
					basePathFile.mkdirs();
				}
				file.createNewFile();
				FileOutputStream fileout = new FileOutputStream(file);
				byte[] buffer = new byte[4 * 1024];
				while (in.read(buffer) != -1) {
					fileout.write(buffer);
				}
				fileout.flush();
				filePaths.add(savePath);
				Drawable mDrawable = Drawable.createFromPath(savePath);
				return mDrawable;
			} catch (Exception e) {
				return drawable;
			}
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			if (result != null) {
				drawable.setDrawable(result);
				if (view instanceof TextView) {
					TextView tv = (TextView) view;
					tv.setText(tv.getText()); // 通过这里的重新设置 TextView 的文字来更新UI
				} else if (view instanceof EditText) {
					EditText et = (EditText) view;
					et.setText(et.getText().toString());
				}
			}
		}
	}

	public class URLDrawable extends BitmapDrawable {

		private Drawable drawable;

		public URLDrawable(Drawable defaultDraw) {
			setDrawable(defaultDraw);
		}

		private void setDrawable(Drawable nDrawable) {
			drawable = nDrawable;
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		}

		@Override
		public void draw(Canvas canvas) {
			drawable.draw(canvas);
		}
	}
}
