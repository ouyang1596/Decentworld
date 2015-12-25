/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.EditCommon1Activity.CommonAdapter.ViewHolder;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditCommon1Activity.java
 * @Description: 一个TextView、一个ListView（选择）
 * @author: cj
 * @date: 2015年10月21日 下午7:41:59
 */
@EActivity(R.layout.activity_edit_common_3)
public class EditCommon3Activity extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "EditCommon3Activity";
	@Bean
	ToastComponent toast;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_finish)
	TextView tvFinish;
	@ViewById(R.id.et_edit_common3_content)
	EditText etCommon3Content;

	@ViewById(R.id.lv_edit_common3_list)
	GridView lv_edit_common3_list;

	private int position = -1;
	private String oldData = "";
	private String title = "";
	private String[] dataList;
	private CommonAdapter adapter;

	@AfterViews
	void init() {
		position = getIntent().getIntExtra("position", -1);
		oldData = getIntent().getStringExtra("oldData");
		title = getIntent().getStringExtra("title");
		tvTitle.setText(title);
		tvFinish.setVisibility(View.VISIBLE);
		tvFinish.setOnClickListener(this);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		initData(title);
		etCommon3Content.setText(oldData);
		adapter = new CommonAdapter();
		lv_edit_common3_list.setAdapter(adapter);
		lv_edit_common3_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				etCommon3Content.setText(dataList[arg2]);
			}
		});
	}

	/**
	 * 初始化ListView的数据
	 */
	private void initData(String title) {
		if (title.equals("民族")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_nation);
		} else if (title.equals("血型")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_bloodType);
		} else if (title.equals("星座")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_constellatory);
		} else if (title.equals("婚否")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_maritalStatus);
		}

	}

	class CommonAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return dataList == null ? 0 : dataList.length;
		}

		@Override
		public String getItem(int position) {

			return dataList[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View con, ViewGroup arg2) {
			ViewHolder vh = null;
			if (null == con) {
				con = View.inflate(mContext, R.layout.item_gv_common1, null);
				vh = new ViewHolder();
				vh.tvName = (TextView) con.findViewById(R.id.tv_name);
				con.setTag(vh);
			} else {
				vh = (ViewHolder) con.getTag();
			}
			vh.tvName.setText(dataList[position]);
			return con;
		}

		class ViewHolder {
			TextView tvName;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_finish:
			String newData = etCommon3Content.getText().toString();
			if (!newData.equals(oldData)) {
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.putExtra("newData", newData);
				setResult(RESULT_OK, intent);
				LogUtils.i(TAG, "修改" + title + "为：" + newData);
			} else {
				LogUtils.i(TAG, "没有修改" + title);
			}
			finish();
			break;

		case R.id.iv_back:
			finish();
			break;
		}
	}
}
