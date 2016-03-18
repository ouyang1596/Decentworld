package com.nui.multiphotopicker.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nui.multiphotopicker.R;
import com.nui.multiphotopicker.adapter.ImageBucketAdapter;
import com.nui.multiphotopicker.adapter.ImageGridAdapter;
import com.nui.multiphotopicker.model.ImageBucket;
import com.nui.multiphotopicker.model.ImageItem;
import com.nui.multiphotopicker.util.ImageFetcher;
import com.nui.multiphotopicker.util.IntentConstants;

/**
 * 图片选择
 */
public class ImageChooseActivity extends Activity {
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private String mBucketName;
	private int availableSize;
	private GridView mGridView;
	private TextView mBucketNameTv;
	private TextView cancelTv;
	private TextView tvAlbum;
	private ListView lvAlbum;
	private ImageGridAdapter mAdapter;
	private Button mFinishBtn;
	private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
	private ImageFetcher mHelper;
	private List<ImageBucket> mImageBucketDataList = new ArrayList<ImageBucket>();
	private ImageBucketAdapter mImageBucketAdapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_choose);
		mHelper = ImageFetcher.getInstance(getApplicationContext());
		mImageBucketDataList = mHelper.getImagesBucketList(false);
		if (mImageBucketDataList.size() > 0) {
			mDataList = mImageBucketDataList.get(0).imageList;
			mBucketName = mImageBucketDataList.get(0).bucketName;
			if (TextUtils.isEmpty(mBucketName)) {
				mBucketName = "请选择";
			}
		}
		availableSize = 9;
		// mDataList = (List<ImageItem>)
		// getIntent().getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
		// if (mDataList == null)
		// mDataList = new ArrayList<ImageItem>();
		// mBucketName =
		// getIntent().getStringExtra(IntentConstants.EXTRA_BUCKET_NAME);
		// if (TextUtils.isEmpty(mBucketName)) {
		// mBucketName = "请选择";
		// }
		// availableSize =
		// getIntent().getIntExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
		// CustomConstants.MAX_IMAGE_SIZE);
		initView();
		initListener();
	}

	private void initView() {
		lvAlbum = (ListView) findViewById(R.id.lv_album);
		mBucketNameTv = (TextView) findViewById(R.id.title);
		mBucketNameTv.setText(mBucketName);
		tvAlbum = (TextView) findViewById(R.id.tv_album);
		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList);
		mGridView.setAdapter(mAdapter);
		mFinishBtn = (Button) findViewById(R.id.finish_btn);
		cancelTv = (TextView) findViewById(R.id.action);
		mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + availableSize + ")");
		mAdapter.notifyDataSetChanged();
	}

	private void initListener() {
		mFinishBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) new ArrayList<ImageItem>(selectedImgs.values()));
				setResult(0, intent);
				finish();
			}
		});

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// ImageItem item = mDataList.get(position);
				ImageItem item = (ImageItem) mAdapter.getItem(position);
				if (item.isSelected) {
					item.isSelected = false;
					selectedImgs.remove(item.imageId);
				} else {
					if (selectedImgs.size() >= availableSize) {
						Toast.makeText(ImageChooseActivity.this, "最多选择" + availableSize + "张图片", Toast.LENGTH_SHORT).show();
						return;
					}
					item.isSelected = true;
					selectedImgs.put(item.imageId, item);
				}
				mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + availableSize + ")");
				mAdapter.notifyDataSetChanged();
			}
		});
		cancelTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ImageChooseActivity.this, PublishActivity.class);
				startActivity(intent);
			}
		});
		tvAlbum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mImageBucketDataList.size() > 0) {
					lvAlbum.setVisibility(View.VISIBLE);
					tvAlbum.setVisibility(View.GONE);
					mImageBucketAdapter = new ImageBucketAdapter(ImageChooseActivity.this, mImageBucketDataList);
					lvAlbum.setAdapter(mImageBucketAdapter);
				} else {
					tvAlbum.setVisibility(View.VISIBLE);
					lvAlbum.setVisibility(View.GONE);
					Toast.makeText(ImageChooseActivity.this, "没有图片可供选择", Toast.LENGTH_SHORT).show();
				}
			}
		});
		lvAlbum.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				lvAlbum.setVisibility(View.GONE);
				tvAlbum.setVisibility(View.VISIBLE);
				selectOne(position);
				ImageBucket imageBucket = mImageBucketDataList.get(position);
				// for (int i = 0; i < imageBucket.imageList.size(); i++) {
				// Log.i("bm", "-----------" +
				// imageBucket.imageList.get(i).sourcePath);
				// }
				mBucketNameTv.setText(imageBucket.bucketName);
				// mDataList.clear();
				// mDataList.addAll(imageBucket.imageList);
				mAdapter.setDataList(imageBucket.imageList);
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	private void selectOne(int position) {
		int size = mImageBucketDataList.size();
		for (int i = 0; i != size; i++) {
			if (i == position)
				mImageBucketDataList.get(i).selected = true;
			else {
				mImageBucketDataList.get(i).selected = false;
			}
		}
		mImageBucketAdapter.notifyDataSetChanged();
	}
}