/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditCommon1Activity.java
 * @Description: 一个EditText、一个ListView（既可以自定义，也可以选择）
 * @author: cj
 * @date: 2015年10月21日 下午7:41:59
 */
@EActivity(R.layout.activity_edit_common_1)
public class EditCommon1Activity extends BaseFragmentActivity implements
		OnClickListener {
	private static final String TAG = "EditCommon1Activity";
	@Bean
	ToastComponent toast;
	@ViewById(R.id.tv_finish)
	TextView tvFinish;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.et_edit_common1_content)
	EditText etEditContent;

	@ViewById(R.id.gv_edit_common1_list)
	GridView gvCommon1;
	private int position = -1;
	private String oldData = "";
	private String title = "";
	private String[] dataList;
	private CommonAdapter adapter;
	private HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
	private StringBuffer sb;

	@AfterViews
	void init() {
		sb = new StringBuffer();
		tvFinish.setVisibility(View.VISIBLE);
		tvFinish.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ivBack.setVisibility(View.VISIBLE);
		position = getIntent().getIntExtra("position", -1);
		oldData = getIntent().getStringExtra("oldData");
		title = getIntent().getStringExtra("title");
		initData(title);
		adapter = new CommonAdapter();
		gvCommon1.setAdapter(adapter);
		gvCommon1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (null == map.get(position) || false == map.get(position)) {
					map.put(position, true);
				} else {
					map.put(position, false);
				}
				LogUtils.i("bm", "map--" + map.get(position));
				sb.setLength(0);
				etEditContent.setText("");
				adapter.notifyDataSetChanged();
			}
		});
		LogUtils.i(TAG, "position=" + position + ",oldData=" + oldData);
		if (!oldData.equals("")) {
			etEditContent.setText(oldData);
		}
		/**
		 * 将光标设置到文字的末尾
		 */
		CharSequence text = etEditContent.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	/**
	 * 初始化ListView的数据
	 */
	private void initData(String title) {
		tvTitle.setText(title);
		if (title.equals("宗教")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_religion);
		} else if (title.equals("特长")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_speciality);
		} else if (title.equals("偶像")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_idol);
		} else if (title.equals("足迹")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_footprint);
		} else if (title.equals("喜欢的书")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_likebooks);
		} else if (title.equals("喜欢的音乐")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_likemusic);
		} else if (title.equals("喜欢的电影")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_likesmovies);
		} else if (title.equals("喜欢的运动")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_likesport);
		} else if (title.equals("美食")) {
			dataList = getResources().getStringArray(
					R.array.edit_user_info_cate);
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_finish:
			String newData = etEditContent.getText().toString();
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
			if (null == map.get(position) || false == map.get(position)) {
				vh.tvName.setBackgroundResource(R.drawable.stroke_bg_gray);
			} else {
				vh.tvName.setBackgroundResource(R.drawable.stroke_bg_yellow);
				if (!sb.toString().contains("《" + dataList[position] + "》")) {
					sb.append("《" + dataList[position] + "》" + ",");
				}
				LogUtils.i("bm", "sb.toString()--" + sb.toString());
				etEditContent.setText(sb.toString());
			}
			vh.tvName.setText(dataList[position]);
			return con;
		}

		class ViewHolder {
			TextView tvName;
		}
	}
}
