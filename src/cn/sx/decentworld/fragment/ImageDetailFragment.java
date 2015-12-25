package cn.sx.decentworld.fragment;

import java.io.File;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.ImageLoaderHelper;

/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment
{
	private String mImageUrl;
	private int localOrNet;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;

	public static ImageDetailFragment newInstance(String imageUrl, int localOrNet)
	{
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		// 路径种类（url或者本地文件路径）
		args.putInt("kind", localOrNet);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
		localOrNet = getArguments() != null ? getArguments().getInt("kind") : null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);
		mAttacher.setOnLongClickListener(new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View arg0)
			{
				// TODO Auto-generated method stub

				return false;
			}
		});
		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener()
		{

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2)
			{
				getActivity().finish();
			}
		});

		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		progressBar.setVisibility(View.VISIBLE);
		if (localOrNet == Constants.IMAGE_NET)
		{
//			 Picasso.with(getActivity()).load(mImageUrl).config(Config.RGB_565)
//			 .into(mImageView, new Callback() {
//			
//			 @Override
//			 public void onSuccess() {
//			 progressBar.setVisibility(View.GONE);
//			 mAttacher.update();
//			 }
//			
//			 @Override
//			 public void onError() {
//			
//			 }
//			 });
//			ImageLoaderHelper.mImageLoader.displayImage(mImageUrl, mImageView, ImageLoaderHelper.mOptions);
			
			ImageLoaderHelper.mImageLoader.displayImage(mImageUrl, mImageView, ImageLoaderHelper.mOptions, new ImageLoadingListener()
			{
				
				@Override
				public void onLoadingStarted(String imageUri, View view)
				{
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
				{
					 progressBar.setVisibility(View.GONE);
					 mAttacher.update();
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view)
				{
					// TODO Auto-generated method stub
					
				}
			});
		}
		else if (localOrNet == Constants.IMAGE_LOCAL)
		{
			if (new File(mImageUrl) != null)
			{
//				 Picasso.with(getActivity()).load(new File(mImageUrl))
//				 .config(Config.RGB_565)
//				 .into(mImageView, new Callback() {
//				 @Override
//				 public void onSuccess() {
//				 progressBar.setVisibility(View.GONE);
//				 mAttacher.update();
//				 }
//				
//				 @Override
//				 public void onError() {
//				
//				 }
//				 });
//				 ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
//				 + mImageUrl, mImageView, ImageLoaderHelper.mOptions);
				ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
						 + mImageUrl, mImageView, ImageLoaderHelper.mOptions, new ImageLoadingListener()
				{
					
					@Override
					public void onLoadingStarted(String imageUri, View view)
					{
						
					}
					
					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason)
					{
						
					}
					
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
					{
						 progressBar.setVisibility(View.GONE);
						 mAttacher.update();
					}
					
					@Override
					public void onLoadingCancelled(String imageUri, View view)
					{
						
					}
				});
			}
		}

		// ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new
		// SimpleImageLoadingListener() {
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// progressBar.setVisibility(View.VISIBLE);
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view, FailReason
		// failReason) {
		// String message = null;
		// switch (failReason.getType()) {
		// case IO_ERROR:
		// message = "下载错误";
		// break;
		// case DECODING_ERROR:
		// message = "图片无法显示";
		// break;
		// case NETWORK_DENIED:
		// message = "网络有问题，无法下载";
		// break;
		// case OUT_OF_MEMORY:
		// message = "图片太大无法显示";
		// break;
		// case UNKNOWN:
		// message = "未知的错误";
		// break;
		// }
		// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		// progressBar.setVisibility(View.GONE);
		// }
		//
		// @Override
		// public void onLoadingComplete(String imageUri, View view, Bitmap
		// loadedImage) {
		// progressBar.setVisibility(View.GONE);
		// mAttacher.update();
		// }
		// });
	}
}
