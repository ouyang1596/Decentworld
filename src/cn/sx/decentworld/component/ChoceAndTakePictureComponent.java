package cn.sx.decentworld.component;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import cn.sx.decentworld.activity.PictureChoiceActivity_;
import cn.sx.decentworld.common.FilePath;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

/**
 * @ClassName: ChoceAndTakePictureComponent.java
 * @Description: 选择图片和拍照
 * @author: yj
 * @date: 2015-3-16 下午2:36:53
 */
@EBean
public class ChoceAndTakePictureComponent
{
	@RootContext
	Context context;

	@RootContext
	Activity activity;

	@Bean
	ToastComponent toastComponent;

	public static String localTempImageFileName;
	public static final int CHOICE_PICTURE = 1001;
	public static final int TAKE_PICKTURE = 1002;
	public static File f;

	public void takePicture()
	{
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
		{
			try
			{
				long filename = (new Date()).getTime();
				localTempImageFileName = String.valueOf(filename) + ".jpg";
				File filePath = new File(FilePath.IMG_SCREENSHOT);
				if (!filePath.exists())
				{
					filePath.mkdirs();
				}
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				f = new File(filePath, localTempImageFileName);
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				localTempImageFileName = f.getAbsolutePath();
				activity.startActivityForResult(intent, TAKE_PICKTURE);

			} catch (ActivityNotFoundException e)
			{
			}
		} else
		{
			toastComponent.show("请检查内存卡！");
		}
	}

	public void choicePicture()
	{
		Intent intent = new Intent(context, PictureChoiceActivity_.class);
		intent.putExtra("which", activity.getLocalClassName());
		activity.startActivityForResult(intent, CHOICE_PICTURE);
	}

	public void choicePicture(int requestCode)
	{
		Intent intent = new Intent(context, PictureChoiceActivity_.class);
		intent.putExtra("which", activity.getLocalClassName());
		activity.startActivityForResult(intent, requestCode);
	}

	public String getImageName()
	{
		return localTempImageFileName;
	}
}
