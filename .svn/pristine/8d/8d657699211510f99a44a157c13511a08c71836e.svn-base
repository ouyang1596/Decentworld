package cn.sx.decentworld.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.KeyboardComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_occupation_show_to_me)
public class ShowToMeOccupationActivity extends BaseFragmentActivity {
	@ViewById(R.id.etv_occupation)
	EditText etvOccupation;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.ll_show_to_me_occupation)
	LinearLayout llShowToMeSchool;
	@ViewById(R.id.lv_occupation)
	ListView lvOccupation;
	@ViewById(R.id.tv_cancel)
	TextView tvCancel;
	public static final int RESULTCODE = 200;
	private String[] data = { "学生", "IT/互联网/金融", "媒体/公关", "金融", "法律", "咨询", "文化/艺术", "影视/娱乐", "教育/科研", "房地产/建筑", "医药/健康",
			"能源/环保", "政府机构", "其他（可自己填写）" };

	@AfterViews
	public void init() {
		openKeyboard();
		NGetIntent();
		tvTitle.setText("向我显示");
		ivBack.setVisibility(View.VISIBLE);
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				etvOccupation.setText("");
			}
		});
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
				if (etvOccupation.length() > 0) {
					intent.putExtra(SelectedActivity.VALUE, etvOccupation.getText().toString());
				} else {
					intent.putExtra(SelectedActivity.VALUE, "");
				}
				intent.putExtra(SelectedActivity.NAME, "行业");
				setResult(RESULTCODE, intent);
				closeKeyBoard();
				finish();
			}
		});
		setAdapter();
	}

	private void NGetIntent() {
		String occupation = getIntent().getStringExtra(SelectedActivity.VALUE);
		etvOccupation.setText(occupation);
	}

	private ArrayAdapter<String> adapter;

	private void setAdapter() {
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
		lvOccupation.setAdapter(adapter);
		lvOccupation.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position == adapter.getCount() - 1) {
					etvOccupation.setText("");
					return;
				}
				etvOccupation.setText(adapter.getItem(position));
			}
		});
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
				keyboardComponent.openKeybord(etvOccupation);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etvOccupation);
	}
}
