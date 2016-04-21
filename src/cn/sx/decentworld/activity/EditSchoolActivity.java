/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.logSystem.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditSignActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年10月21日 上午8:46:07
 */

@EActivity(R.layout.activity_edit_school)
public class EditSchoolActivity extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "EditSchoolActivity";
	private List<String> allUniveList;
	public static final String ENCODING = "UTF-8";
	@ViewById(R.id.act_edit_school)
	AutoCompleteTextView aTvUniversity;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_edit_school_complete)
	Button btn_edit_school_complete;
	@Bean
	ToastComponent toast;
	private String oldData = "";
	private int position = -1;

	@AfterViews
	void init() {
		tvTitle.setText("修改学校名称");
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		parseJsonData();
		setAutoApdater();
		btn_edit_school_complete.setOnClickListener(this);

		position = getIntent().getIntExtra("position", -1);
		oldData = getIntent().getStringExtra("oldData");

		LogUtils.i(TAG, "position=" + position + ",oldData=" + oldData);
		if (!oldData.equals("")) {
			aTvUniversity.setText(oldData);
		}

		/**
		 * 将光标设置到文字的末尾
		 */
		CharSequence text = aTvUniversity.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	/**
	 * 为AutoCompleteTextView设置Adpater
	 */
	private void setAutoApdater() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit_school_complete: {
			String newData = aTvUniversity.getText().toString();
			if (!newData.equals(oldData)) {
				Intent intent = new Intent();
				if (aTvUniversity.length() > 0) {
					ifContainContent(intent);
				} else {
					intent.putExtra("newData", "");
				}
				intent.putExtra("position", position);
				setResult(RESULT_OK, intent);
				LogUtils.i(TAG, "修改学校为：" + newData);
			} else {
				LogUtils.i(TAG, "没有修改学校");
			}
			finish();
		}
			break;
		case R.id.iv_back:
			finish();
		default:
			break;
		}
	}

	private void ifContainContent(Intent intent) {
		if (allUniveList.contains(aTvUniversity.getText().toString())) {
			intent.putExtra("newData", aTvUniversity.getText().toString());
		} else {
			toast.show("所选学校不在条目内");
			intent.putExtra("newData", "");
		}
	}
}
