package cn.sx.decentworld.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.ImageUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_picture_clip)
public class PictureClipActivity extends BaseFragmentActivity {
	private static final int OPEN_GALLERY = 0;
	private int aspectX, aspectY, outputX, outputY;
	private static final String IMAGE_FILE_LOCATION = Environment
			.getExternalStorageDirectory() + "/temp.jpg";
	private Uri imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));
	public static final int CLIP_IMAGE = 3;

	@AfterViews
	public void init() {
		PGetIntent();
		selectLocalPic();
	}

	public void PGetIntent() {
		aspectX = getIntent().getIntExtra(Constants.ASPECTX, 1);
		aspectY = getIntent().getIntExtra(Constants.ASPECTY, 1);
		outputX = getIntent().getIntExtra(Constants.OUTPUTX, 350);
		outputY = getIntent().getIntExtra(Constants.OUTPUTY, 350);
	}

	private void selectLocalPic() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, OPEN_GALLERY);
	}

	/**
	 * 裁剪图片方法实现
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, CLIP_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			finish();
			return;
		}
		if (OPEN_GALLERY == requestCode) {
			if (null == data.getData()) {
				finish();
				return;
			}
			Uri uri = data.getData();
			startPhotoZoom(uri);
		} else if (CLIP_IMAGE == requestCode) {
			Bitmap photo = data.getParcelableExtra("data");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (null == photo) {
				photo = BitmapFactory.decodeFile(IMAGE_FILE_LOCATION);
			}
			String fileName = ImageUtils.generateFileName();
			String filePath = Constants.HomePath + "/clipImage" + fileName
					+ ".png";
			ImageUtils.saveBitmap(filePath, photo);
			photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] datas = baos.toByteArray();
			Intent intent = new Intent();
			// intent.putExtra("bitmap", datas);
			intent.putExtra("filePath", filePath);
			setResult(RESULT_OK, intent);
			this.finish();
		}
	}
}
