package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RegisterInfoUseless;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.dialog.BackDialogFragment;
import cn.sx.decentworld.dialog.BackDialogFragment.OnBackClickListener;
import cn.sx.decentworld.utils.ImageUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_money)
public class RegisterMoneyCheckActivity extends BaseFragmentActivity implements
		OnBackClickListener {
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.etv_material_kind)
	EditText etvMaterialKind;
	@ViewById(R.id.iv_upload_pic)
	ImageView ivUploadPic;
	@ViewById(R.id.root_activity_money)
	LinearLayout llRegisterMoney;
	private static final int REQUEST_CODE = 100;
	private static final int CHOICE_ONE_PIC = 1;
	private FragmentManager fragmentManager;
	@Bean
	ToastComponent toast;
	private File fileImg;
	@Bean
	RegisterComponent registerComponent;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("财富");
		ivBack.setVisibility(View.VISIBLE);
		llRegisterMoney.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		initData();
		fragmentManager = getSupportFragmentManager();
		setBtnState();
		etvMaterialKind.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				setBtnState();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		ivUploadPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DecentWorldApp.tempPicPath = null;
				Intent intent = new Intent(getApplicationContext(),
						TakePhotosAndpictureActivity.class);
				intent.putExtra("max_count", CHOICE_ONE_PIC);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				backAction();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						toast.show("上传成功");
						startActivity(new Intent(mContext, LoginActivity_.class));
						finish();
					};
				};
				File[] images = new File[1];
				if (fileImg == null) {
					toast.show("请先选择一张图片");
					return;
				}
				images[0] = fileImg;
				HashMap<String, String> hashmap = new HashMap<String, String>();
				hashmap.put("material", etvMaterialKind.getText().toString());
				hashmap.put(Constants.DW_ID, DecentWorldApp.getInstance()
						.getDwID());
				hashmap.put("type", Constants.MONEY);
				images[0] = fileImg;
				// registerComponent.submitImageType(images, the_switch,
				// handler);
				registerComponent.submitImageWithParams(hashmap, images,
						Constants.API_UPDATE_TYPE_AGAIN, handler);
			}
		});
	}

	private void initData() {
		if (null == DecentWorldApp.registerBean) {
			return;
		}
		RegisterInfoUseless info = RegisterInfoUseless.queryByDwID(registerComponent.tel);
		if (null == info) {
			info = new RegisterInfoUseless(registerComponent.tel);
		}
		String talentMaterial = info.talentMaterial;
		String picPath = info.picTalentPath;
		if (null != talentMaterial) {
			etvMaterialKind.setText(talentMaterial);
		} else {
			if ("1".equals(DecentWorldApp.registerBean.type)) {
				etvMaterialKind.setText(DecentWorldApp.registerBean.material);
			}
		}
		if (null != picPath) {
			Bitmap bitmap = getBitmap(picPath);
			ivUploadPic.setImageBitmap(bitmap);
			mPicPath = picPath;
			fileImg = new File(picPath);
		}
	}

	String mPicPath;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (REQUEST_CODE == requestCode) {
				// 判断返回的数据
				int max_count = data.getExtras().getInt("max_count");
				ArrayList<String> pic_filse = data.getExtras()
						.getStringArrayList("pic_paths");
				if (!pic_filse.isEmpty()) {
					handlePic(pic_filse);
					if (CHOICE_ONE_PIC == max_count) {
						DecentWorldApp.tempPicPath = pic_filse.get(0);
						mPicPath = DecentWorldApp.tempPicPath;
						fileImg = new File(mPicPath);
						try {
							Bitmap bitmapUpload = getBitmap(mPicPath);
							ivUploadPic.setImageBitmap(bitmapUpload);
						} catch (OutOfMemoryError e) {
						}
					}
				}
			}
		}

	}

	private Bitmap getBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bitmapUpload = BitmapFactory.decodeFile(path, options);
		return bitmapUpload;
	}

	private void setBtnState() {
		int length = etvMaterialKind.length();
		if (length <= 0) {
			btnOk.setEnabled(false);
			btnOk.setTextColor(getResources()
					.getColor(R.color.rg_tv_blue_press));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_pressed_shape);
		} else {
			btnOk.setEnabled(true);
			btnOk.setTextColor(getResources().getColor(
					R.drawable.rg_tv_color_selector));
			btnOk.setBackgroundResource(R.drawable.bg_btn_yellow_selector);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DecentWorldApp.tempPicPath = null;
	}

	@Override
	public void backClick(int state) {
		if (state == Constants.SAVE) {
			RegisterInfoUseless info = RegisterInfoUseless.queryByDwID(registerComponent.tel);
			if (info == null) {
				return;
			}
			if (etvMaterialKind.length() > 0) {
				String strMaterialKind = etvMaterialKind.getText().toString();
				info.talentMaterial = strMaterialKind;
			}
			if (null != mPicPath) {
				info.picTalentPath = mPicPath;
			}
			info.save();
			startIntent(RegisterWhatYouHaveActivity_.class);
		} else {
			startIntent(RegisterWhatYouHaveActivity_.class);
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		backAction();
	}

	private void backAction() {
		if (etvMaterialKind.length() > 0 || null != mPicPath) {
			BackDialogFragment bf = BackDialogFragment
					.newInstance("您是否需要保存已经写好的信息？");
			bf.setOnBackClickListener(RegisterMoneyCheckActivity.this);
			bf.show(fragmentManager, "backDialogs");
		} else {
			startIntent(RegisterWhatYouHaveActivity_.class);
		}
	}

	// 对键盘的操作
	@Bean
	KeyboardComponent keyboardComponent;

	/**
	 * 将大于2M的图片进行压缩处理
	 * */
	private void handlePic(ArrayList<String> pic_filse) {
		for (int i = 0; i < pic_filse.size(); i++) {
			String filePath = pic_filse.get(i);
			if (ImageUtils.fileLength(filePath) > 2 * 1024 * 1024) {
				File newFilePath = handleFile(filePath);
				pic_filse.remove(i);
				pic_filse.add(i, newFilePath.getAbsolutePath());
			}
		}
	}

	/**
	 * 将图片压缩到指定的大小
	 * */
	private File handleFile(String filePath) {
		Bitmap bitmap = ImageUtils.scalePic(filePath);
		String picPath = Constants.HOME_PATH + "/temp"
				+ ImageUtils.generateFileName() + ".png";
		ImageUtils.saveBitmap(picPath, bitmap);
		File file = new File(picPath);
		return file;
	}

	/**
	 * 打开软键盘
	 */
	private void openKeyboard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				keyboardComponent.openKeybord(etvMaterialKind);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvMaterialKind);
	}
}
