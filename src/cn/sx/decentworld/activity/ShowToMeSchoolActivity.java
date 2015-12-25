package cn.sx.decentworld.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_school_show_to_me)
public class ShowToMeSchoolActivity extends BaseFragmentActivity {
	@ViewById(R.id.act_university)
	AutoCompleteTextView aTvUniversity;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.ll_show_to_me_school)
	LinearLayout llShowToMeSchool;
	@ViewById(R.id.tv_cancel)
	TextView tvCancel;
	@Bean
	ToastComponent toast;
	private List<String> allUniveList;
	public static final String ENCODING = "UTF-8";
	public static final int RESULT_CODE = 100;

	@AfterViews
	public void init() {
		openKeyboard();
		NGetIntent();
		parseJsonData();
		setAutoApdater();
		tvTitle.setText("向我显示");
		ivBack.setVisibility(View.VISIBLE);
		llShowToMeSchool.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				closeKeyBoard();
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if (aTvUniversity.length() > 0) {
					ifContainContent(intent);
				} else {
					intent.putExtra(SelectedActivity.VALUE, "");
				}
				intent.putExtra(SelectedActivity.NAME, "学校");
				setResult(RESULT_CODE, intent);
				closeKeyBoard();
				finish();
			}

			private void ifContainContent(Intent intent) {
				if (allUniveList.contains(aTvUniversity.getText().toString())) {
					intent.putExtra(SelectedActivity.VALUE, aTvUniversity
							.getText().toString());
				} else {
					toast.show("所选学校不在条目内");
					intent.putExtra(SelectedActivity.VALUE, "");
				}
			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				aTvUniversity.setText("");
			}
		});
	}

	private void NGetIntent() {
		String school = getIntent().getStringExtra(SelectedActivity.VALUE);
		aTvUniversity.setText(school);
	}

	/**
	 * 为AutoCompleteTextView设置Adpater
	 */
	private void setAutoApdater() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ShowToMeSchoolActivity.this,
				android.R.layout.simple_dropdown_item_1line, allUniveList);
		aTvUniversity.setAdapter(adapter);
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
				keyboardComponent.openKeybord(aTvUniversity);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(aTvUniversity);
	}
}
