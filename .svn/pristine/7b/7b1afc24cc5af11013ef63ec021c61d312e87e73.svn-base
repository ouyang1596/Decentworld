package cn.sx.decentworld.activity;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

@EActivity(R.layout.activity_register_student_green_pass)
public class RegisterStudentGreenPassActivity extends BaseFragmentActivity
		implements OnBackClickListener {
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.act_university)
	AutoCompleteTextView actUniversity;
	@ViewById(R.id.etv_college)
	EditText etvCollege;
	@ViewById(R.id.etv_classroom)
	EditText etvClassroom;
	@ViewById(R.id.iv_upload_oic)
	ImageView ivUploadPhoto;
	@ViewById(R.id.root_activity_register_student_green_pass)
	LinearLayout llRegisterStudentPass;
	@ViewById(R.id.btn_OK)
	Button btnOk;

	@ViewById(R.id.ll_upload_pic)
	LinearLayout llUploadPic;
	@ViewById(R.id.iv_upload_oic)
	ImageView ivUpload;
	@Bean
	ToastComponent toast;
	private static final int REQUEST_CODE = 100;
	private FragmentManager fragmentManager;
	private static final int CHOICE_ONE_PIC = 1;
	private String mPicPath;
	private List<String> allUniveList;
	public static final String ENCODING = "UTF-8";
	private File fileImg;
	@Bean
	RegisterComponent registerComponent;

	@AfterViews
	public void init() {
		openKeyboard();
		tvTitle.setText("学生绿色通道");
		ivBack.setVisibility(View.VISIBLE);
		llRegisterStudentPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		initData();
		parseJsonData();
		setAutoApdater();
		fragmentManager = getSupportFragmentManager();
		setBtnState();
		llUploadPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DecentWorldApp.tempPicPath = null;
				Intent intent = new Intent(getApplicationContext(),
						TakePhotosAndpictureActivity.class);
				intent.putExtra("max_count", CHOICE_ONE_PIC);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		actUniversity.addTextChangedListener(new TextWatcher() {

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
		etvCollege.addTextChangedListener(new TextWatcher() {

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
		etvClassroom.addTextChangedListener(new TextWatcher() {

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
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						if ("0".equals(DecentWorldApp.ifGuarantee)) {
							startActivity(new Intent(mContext,
									RegisterWhatYouHaveActivity_.class));
						} else if ("1".equals(DecentWorldApp.ifGuarantee)) {
							startActivity(new Intent(mContext,
									RegisterNickActivity_.class));
						}
						finish();
					};
				};
				File[] images = new File[1];
				if (fileImg == null) {
					toast.show("请先选择一张图片");
					return;
				}
				HashMap<String, String> hashmap = new HashMap<String, String>();
				hashmap.put("school", actUniversity.getText().toString());
				hashmap.put("department", etvCollege.getText().toString());
				hashmap.put("classes", etvClassroom.getText().toString());
				hashmap.put("phoneNum", registerComponent.tel);
				hashmap.put("isStudent", Constants.IS_STUDENT);
				images[0] = fileImg;
				// registerComponent.submitImageType(images, the_switch,
				// handler);
				registerComponent.submitImageWithParams(hashmap, images,
						Constants.API_UPDATA_STUDENT, handler);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
				backAction();
			}

		});
	}

	/**
	 * 为AutoCompleteTextView设置Adpater
	 */
	private void setAutoApdater() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				RegisterStudentGreenPassActivity.this,
				android.R.layout.simple_dropdown_item_1line, allUniveList);
		actUniversity.setAdapter(adapter);
	}

	// json解析各个学校数据
	private void parseJsonData() {
		allUniveList = new ArrayList<String>();
		String jsonDat = getFromAssets("alluniveList.js");
		try {
			JSONArray ja = new JSONArray(jsonDat);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject object = ja.getJSONObject(i);
				JSONArray jsA = object.getJSONArray("univs");
				for (int j = 0; j < jsA.length(); j++) {
					JSONObject jO = jsA.getJSONObject(j);
					String data = jO.getString("name");
					allUniveList.add(data);
				}
			}
		} catch (JSONException e) {
			return;
		}
	}

	private void initData() {
		if (null == DecentWorldApp.registerBean) {
			return;
		}
		RegisterInfoUseless info = RegisterInfoUseless.queryByDwID(registerComponent.tel);
		if (info != null) {
			String university = info.university;
			String college = info.college;
			String classRoom = info.classRoom;
			String picPath = info.picStuIdPath;
			if (null != university) {
				actUniversity.setText(university);
			} else {
				actUniversity.setText(DecentWorldApp.registerBean.school);
			}
			if (null != college) {
				etvCollege.setText(college);
			} else {
				etvCollege.setText(DecentWorldApp.registerBean.department);
			}
			if (null != classRoom) {
				etvClassroom.setText(classRoom);
			} else {
				etvClassroom.setText(DecentWorldApp.registerBean.classes);
			}
			if (null != picPath) {
				Bitmap bitmap = getBitmap(picPath);
				ivUpload.setImageBitmap(bitmap);
				mPicPath = picPath;
				fileImg = new File(picPath);
			}
		}
	}

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
							ivUpload.setImageBitmap(bitmapUpload);
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
		int universityLength = actUniversity.length();
		int collegeLength = etvCollege.length();
		int classroomLength = etvClassroom.length();
		if (universityLength <= 0 || collegeLength <= 0 || classroomLength <= 0) {
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

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void backClick(int state) {
		if (state == Constants.SAVE) {
			RegisterInfoUseless info = RegisterInfoUseless.queryByDwID(registerComponent.tel);
			if (info == null) {
				info = new RegisterInfoUseless(registerComponent.tel);
			}
			if (etvClassroom.length() > 0) {
				String strClassRoom = etvClassroom.getText().toString();
				info.classRoom = strClassRoom;
			}
			if (etvCollege.length() > 0) {
				String strCollege = etvCollege.getText().toString();
				info.college = strCollege;
			}
			if (actUniversity.length() > 0) {
				String strUniversity = actUniversity.getText().toString();
				info.university = strUniversity;
			}
			if (null != mPicPath) {
				info.picStuIdPath = mPicPath;
			}
			info.save();
			startIntent(RegisterIsStudentActivity_.class);
		} else {
			startIntent(RegisterIsStudentActivity_.class);
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		backAction();
	}

	private void backAction() {
		if (etvClassroom.length() > 0 || etvCollege.length() > 0
				|| actUniversity.length() > 0 || null != mPicPath) {
			BackDialogFragment bf = BackDialogFragment
					.newInstance("您是否需要保存已经写好的信息？");
			bf.setOnBackClickListener(RegisterStudentGreenPassActivity.this);
			bf.show(fragmentManager, "backDialogs");
		} else {
			startIntent(RegisterIsStudentActivity_.class);
		}
	}

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

	// 对键盘的操作
	@Bean
	KeyboardComponent keyboardComponent;

	/**
	 * 打开软键盘
	 */
	private void openKeyboard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				keyboardComponent.openKeybord(etvClassroom);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvClassroom);
	}
}
