package cn.sx.decentworld.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.FilePath;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.PickPhotoUtil;
import cn.sx.decentworld.utils.PickPhotoUtil.PickPhotoCode;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_picture_clip)
public class PictureClipActivity extends BaseFragmentActivity {
	private static final int OPEN_GALLERY = 0;
	private int aspectX, aspectY, outputX, outputY, cameraOrAlbum;// cameraOrAlbum
																	// 0 相册 1拍照
	private Uri imageUri = Uri.fromFile(new File(FilePath.IMG_TEMP_PNG));
	public static final int CLIP_IMAGE = 3;

	@AfterViews
	public void init() {
		PGetIntent();
		cameraOrAlbum();
	}

	public void PGetIntent() {
		cameraOrAlbum = getIntent().getIntExtra(Constants.CAMERA_ALBUM, 0);
		aspectX = getIntent().getIntExtra(Constants.ASPECTX, 1);
		aspectY = getIntent().getIntExtra(Constants.ASPECTY, 1);
		outputX = getIntent().getIntExtra(Constants.OUTPUTX, 350);
		outputY = getIntent().getIntExtra(Constants.OUTPUTY, 350);
	}

	private void cameraOrAlbum() {
		if (0 == cameraOrAlbum) {
			album();
			return;
		}
		camera();
	}

	private void album() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, OPEN_GALLERY);
	}

	private void camera() {
		PickPhotoUtil.getInstance().takePhoto(this,"tempUser", FilePath.IMG_TEMP_PNG);
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
		// 相机拍照裁剪
		if (requestCode == PickPhotoCode.PICKPHOTO_TAKE) {
			startPhotoZoom(imageUri);
			return;
		}
		if (null == data) {
			finish();
			return;
		}
		// 相册裁剪
		if (OPEN_GALLERY == requestCode) {
			if (null == data.getData()) {
				finish();
				return;
			}
			Uri uri = data.getData();
			startPhotoZoom(uri);
		} else if (CLIP_IMAGE == requestCode) {
			if (resultCode == RESULT_OK) {
				Bitmap photo = data.getParcelableExtra("data");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (null == photo) {
					photo = BitmapFactory.decodeFile(FilePath.IMG_TEMP_PNG);
				}
				String fileName = ImageUtils.generateFileName();
				String filePath = FilePath.CLIP_IMAGE + fileName + ".png";
				ImageUtils.saveBitmap(filePath, photo);
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] datas = baos.toByteArray();
				Intent intent = new Intent();
				// intent.putExtra("bitmap", datas);
				intent.putExtra("filePath", filePath);
				setResult(RESULT_OK, intent);
			}
			this.finish();
		}
	}
}
