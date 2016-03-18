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
import cn.sx.decentworld.common.Constants;

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

	public static final int TAKE_PICKTURE = 1;

	public static final int CHOICE_PICTURE = 2;

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
				File filePath = Constants.FILE_PIC_SCREENSHOT;
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

	// public void showDialog(){
	// AlertDialog.Builder builder = new AlertDialog.Builder(context,3);
	// builder.setTitle("选择相片").setItems(R.array.choicepicture_method, new
	// OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// if(which==0){
	// String status = Environment.getExternalStorageState();
	// if (status.equals(Environment.MEDIA_MOUNTED)) {
	// try {
	// long filename = (new Date()).getTime();
	//
	// localTempImageFileName = String.valueOf(filename)
	// + ".jpg";
	// File filePath = Constants.FILE_PIC_SCREENSHOT;
	// if (!filePath.exists()) {
	// filePath.mkdirs();
	// }
	// Intent intent = new Intent(
	// android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	// File f = new File(filePath, localTempImageFileName);
	// Uri u = Uri.fromFile(f);
	// intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
	//
	// activity.startActivityForResult(intent, TAKE_PICKTURE);
	//
	// } catch (ActivityNotFoundException e) {
	// }
	// } else {
	// toastComponent.show("请检查内存卡！");
	// }
	// }else {
	// Intent intent = new Intent(context,PictureChoiceActivity.class);
	// intent.putExtra("which", activity.getLocalClassName());
	// activity.startActivityForResult(intent, 22);
	// }
	// }
	// });
	// builder.setNegativeButton("取消", new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	// }).show();
	// }

	public String getImageName()
	{
		return localTempImageFileName;
	}
}
