package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ChoiceInfo;
import cn.sx.decentworld.bean.SelectInfo;
import cn.sx.decentworld.widget.ListViewForScrollView;
import cn.sx.decentworld.widget.RangeSeekBar;
import cn.sx.decentworld.widget.RangeSeekBar.OnRangeSeekBarChangeListener;
import cn.sx.decentworld.widget.SingleSeekBar;
import cn.sx.decentworld.widget.SingleSeekBar.OnSingleSeekBarChangeListener;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_selected)
public class SelectedActivity extends BaseFragmentActivity implements
		OnSeekBarChangeListener,/* OnClickListener, */
		OnRangeSeekBarChangeListener<Integer>,
		OnSingleSeekBarChangeListener<Integer> {
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_sex)
	ImageView ivBoy;
	@ViewById(R.id.iv_girl)
	ImageView ivGirl;
	@ViewById(R.id.tv_money)
	TextView tvMoney;
	// @ViewById(R.id.tv_age)
	// TextView tvAge;
	// @ViewById(R.id.tv_distance)
	// TextView tvDistacne;
	// @ViewById(R.id.sb_money)
	// SeekBar sbMoney;
	@ViewById(R.id.tv_age_scope)
	TextView tvAgeScope;
	@ViewById(R.id.tv_distance_scope)
	TextView tvDistanceScope;
	@ViewById(R.id.tv_worth_scope)
	TextView tvWorthScope;
	// @ViewById(R.id.sb_age)
	// SeekBar sbAge;
	// @ViewById(R.id.sb_distance)
	// SeekBar sbDistance;
	LinearLayout llReligion;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.lv_select)
	ListViewForScrollView lvSelect;
	// @ViewById(R.id.ll_ageJudge)
	// LinearLayout llAgeJudge;
	// @ViewById(R.id.ll_worthJudge)
	// LinearLayout llWorthJudge;
	// @ViewById(R.id.tv_ageJudge)
	// TextView tvAgejudge;
	// @ViewById(R.id.tv_worthJudge)
	// TextView tvWorthJudge;
	@ViewById(R.id.ll_container_age)
	LinearLayout llContainerAge;
	@ViewById(R.id.ll_container_distance)
	LinearLayout llContainerDistance;
	@ViewById(R.id.ll_container_worth)
	LinearLayout llContainerWorth;
	private static final int OCCUPATION = 0;
	private static final int SCHOOL = 1;
	private static final int OTHER = 2;
	public static final String NAME = "name";
	public static final String VALUE = "value";
	// private int ageCount;
	// private int moneyCount;
	// private int distanceCount;
	private String sex;
	private boolean boyFlag;
	private boolean girlFlag;
	SelectInfo[] selectInfo = { new SelectInfo("行业"), new SelectInfo("学校"),
			new SelectInfo("车"), new SelectInfo("书名"), new SelectInfo("故乡"),
			new SelectInfo("偶像"), new SelectInfo("宗教"), new SelectInfo("电影"),
			new SelectInfo("运动"), new SelectInfo("星座"), new SelectInfo("属相"),
			new SelectInfo("婚否") };
	private RangeSeekBar<Integer> ageRangeSeekBar, worthRangeSeekBar;
	private SingleSeekBar<Integer> distanceSingleSeekBar;

	@AfterViews
	public void init() {
		initSeekBar();
		initSelected();
		initLv();
		tvTitle.setText("向我显示");
		// sbAge.setOnSeekBarChangeListener(this);
		// sbMoney.setOnSeekBarChangeListener(this);
		// sbDistance.setOnSeekBarChangeListener(this);
		ivBoy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setBoySex();
			}
		});
		ivGirl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setGirlSex();
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				saveToSqlite();
				finish();
			}
		});
		// llAgeJudge.setOnClickListener(this);
		// llWorthJudge.setOnClickListener(this);
	}

	/**
	 * 初始化RangSeekBar
	 */
	private void initSeekBar() {
		ageRangeSeekBar = new RangeSeekBar<Integer>(16, 80, this);
		llContainerAge.addView(ageRangeSeekBar);
		ageRangeSeekBar.setTag(R.id.ll_container_age);
		tvAgeScope.setText(ageRangeSeekBar.getSelectedMinValue() + "-"
				+ ageRangeSeekBar.getSelectedMaxValue());
		ageRangeSeekBar.setOnRangeSeekBarChangeListener(this);
		distanceSingleSeekBar = new SingleSeekBar<Integer>(0, 200, this);
		llContainerDistance.addView(distanceSingleSeekBar);
		tvDistanceScope.setText(distanceSingleSeekBar.getSelectedMaxValue()
				+ "km");
		distanceSingleSeekBar.setOnSingleSeekBarChangeListener(this);
		worthRangeSeekBar = new RangeSeekBar<Integer>(1, 100, this);
		llContainerWorth.addView(worthRangeSeekBar);
		worthRangeSeekBar.setTag(R.id.ll_container_worth);
		tvWorthScope.setText(worthRangeSeekBar.getSelectedMinValue() + "-"
				+ worthRangeSeekBar.getSelectedMaxValue());
		worthRangeSeekBar.setOnRangeSeekBarChangeListener(this);
	}

	SelectAdapter adapter;

	private void initLv() {
		adapter = new SelectAdapter();
		lvSelect.setAdapter(adapter);
		lvSelect.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				SelectInfo item = adapter.getItem(position);
				if ("学校".equals(item.getName())) {
					Intent intent = new Intent(mContext,
							ShowToMeSchoolActivity_.class);
					intent.putExtra(VALUE, item.getValue());
					startActivityForResult(intent, OTHER);
				} else if ("行业".equals(item.getName())) {
					Intent intent = new Intent(mContext,
							ShowToMeOccupationActivity_.class);
					intent.putExtra(VALUE, item.getValue());
					startActivityForResult(intent, OTHER);
				} else {
					Intent intent = new Intent(mContext,
							ShowToMeLvActivity_.class);
					intent.putExtra(NAME, item.getName());
					intent.putExtra(VALUE, item.getValue());
					startActivityForResult(intent, OTHER);
				}
			}

		});
	}

	private void saveToSqlite() {
		ChoiceInfo info = ChoiceInfo.queryByDwID("1");
		if (null == info) {
			info = new ChoiceInfo();
		}
		// info.ageJudge = tvAgejudge.getText().toString();
		// info.worthJudge = tvWorthJudge.getText().toString();
		if (boyFlag && girlFlag) {
			sex = "2";
		} else if (boyFlag && !girlFlag) {
			sex = "0";
		} else {
			sex = "1";
		}
		info.sex = sex;
		info.minWorth = "" + worthMinValue;
		info.maxWorth = "" + worthMaxValue;
		// info.worth = "" + moneyCount;
		info.scope = "" + distanceMaxValue;
		// info.scope = "" + distanceCount;
		info.minAge = "" + ageMinValue;
		info.maxAge = "" + ageMaxValue;
		// info.age = "" + ageCount;
		info.occupation = selectInfo[0].getValue();
		info.school = selectInfo[1].getValue();
		info.car = selectInfo[2].getValue();
		info.book = selectInfo[3].getValue();
		info.hometown = selectInfo[4].getValue();
		info.idol = selectInfo[5].getValue();
		info.religion = selectInfo[6].getValue();
		info.movie = selectInfo[7].getValue();
		info.sport = selectInfo[8].getValue();
		info.star = selectInfo[9].getValue();
		info.animal = selectInfo[10].getValue();
		info.marry = selectInfo[11].getValue();
		info.save();
	}

	private void initSelected() {
		ChoiceInfo info = ChoiceInfo.queryByDwID("1");
		if (null == info) {
			info = new ChoiceInfo();
			info.save();
		}
		// tvAgejudge.setText(info.ageJudge);
		// tvWorthJudge.setText(info.worthJudge);
		if ("0".equals(info.sex)) {
			boyFlag = true;
			girlFlag = false;
			ivBoy.setImageResource(R.drawable.open);
			ivGirl.setImageResource(R.drawable.close);
		} else if ("1".equals(info.sex)) {
			boyFlag = false;
			girlFlag = true;
			ivBoy.setImageResource(R.drawable.close);
			ivGirl.setImageResource(R.drawable.open);
		} else {
			boyFlag = true;
			girlFlag = true;
			ivBoy.setImageResource(R.drawable.open);
			ivGirl.setImageResource(R.drawable.open);
		}
		ageMaxValue = Integer.valueOf(info.maxAge);
		ageMinValue = Integer.valueOf(info.minAge);
		ageRangeSeekBar.setSelectedMaxValue(ageMaxValue);
		ageRangeSeekBar.setSelectedMinValue(ageMinValue);
		tvAgeScope.setText(ageMinValue + "-" + ageMaxValue);
		// ageCount = Integer.valueOf(info.age);
		// sbAge.setProgress(ageCount);
		// setAge(ageCount);
		// moneyCount = Integer.valueOf(info.worth);
		// sbMoney.setProgress(moneyCount);
		// setMoney(moneyCount);
		worthMaxValue = Integer.valueOf(info.maxWorth);
		worthMinValue = Integer.valueOf(info.minWorth);
		tvWorthScope.setText(worthMinValue + "-" + worthMaxValue);
		worthRangeSeekBar.setSelectedMinValue(worthMinValue);
		worthRangeSeekBar.setSelectedMaxValue(worthMaxValue);
		distanceMaxValue = Integer.valueOf(info.scope);
		distanceSingleSeekBar.setSelectedMaxValue(Integer.valueOf(info.scope));
		tvDistanceScope.setText(200 == distanceMaxValue ? distanceMaxValue
				+ "km+" : distanceMaxValue + "km");
		// distanceCount = Integer.valueOf(info.scope);
		// sbDistance.setProgress(distanceCount);
		// setDistance(distanceCount);
		selectInfo[0].setValue(info.occupation);
		selectInfo[1].setValue(info.school);
		selectInfo[2].setValue(info.car);
		selectInfo[3].setValue(info.book);
		selectInfo[4].setValue(info.hometown);
		selectInfo[5].setValue(info.idol);
		selectInfo[6].setValue(info.religion);
		selectInfo[7].setValue(info.movie);
		selectInfo[8].setValue(info.sport);
		selectInfo[9].setValue(info.star);
		selectInfo[10].setValue(info.animal);
		selectInfo[11].setValue(info.marry);
	}

	private void setGirlSex() {
		if (!girlFlag) {
			ivGirl.setImageResource(R.drawable.open);
			girlFlag = true;
		} else {
			ivGirl.setImageResource(R.drawable.close);
			girlFlag = false;
			if (!boyFlag) {
				ivBoy.setImageResource(R.drawable.open);
				boyFlag = true;
			}
		}
	}

	private void setBoySex() {
		if (!boyFlag) {
			ivBoy.setImageResource(R.drawable.open);
			boyFlag = true;
		} else {
			ivBoy.setImageResource(R.drawable.close);
			boyFlag = false;
			if (!girlFlag) {
				ivGirl.setImageResource(R.drawable.open);
				girlFlag = true;
			}
		}
	}

	/**
	 * 拖动条停止拖动的时候调用
	 */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * 拖动条开始拖动的时候调用
	 */
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * 拖动条进度改变的时候调用
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		// case R.id.sb_age:
		// if (progress < 1) {
		// progress = 1;
		// }
		// setAge(progress);
		// ageCount = progress;
		// break;
		// case R.id.sb_money:
		// if (progress < 1) {
		// progress = 1;
		// }
		// setMoney(progress);
		// moneyCount = progress;
		// break;
		// case R.id.sb_distance:
		// setDistance(progress);
		// distanceCount = progress;
		// break;
		}
	}

	// public void setDistance(int progress) {
	// tvDistacne.setText(progress + "KM");
	// }

	public void setMoney(int progress) {
		tvMoney.setText("¥" + progress);
	}

	// public void setAge(int progress) {
	// tvAge.setText(progress + "岁");
	// }

	class SelectAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// return selectInfo.length;
			return 2;
		}

		@Override
		public SelectInfo getItem(int position) {
			return selectInfo[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View con, ViewGroup arg2) {
			ViewHolder vh;
			if (null == con) {
				con = View.inflate(mContext, R.layout.item_select, null);
				vh = new ViewHolder();
				vh.tvSelectName = (TextView) con
						.findViewById(R.id.tv_select_name);
				vh.tvSelectValue = (TextView) con
						.findViewById(R.id.tv_select_value);
				con.setTag(vh);
			} else {
				vh = (ViewHolder) con.getTag();
			}
			vh.tvSelectName.setText(selectInfo[position].getName());
			vh.tvSelectValue.setText(selectInfo[position].getValue());
			return con;
		}

		class ViewHolder {
			TextView tvSelectName, tvSelectValue;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		switch (requestCode) {
		case OTHER:
			String name = data.getStringExtra(NAME);
			String value = data.getStringExtra(VALUE);
			for (int i = 0; i < selectInfo.length; i++) {
				if (selectInfo[i].getName().equals(name)) {
					selectInfo[i].setValue(value);
				}
			}
			adapter.notifyDataSetChanged();
			break;
		}
	}

	// @Override
	// public void onClick(View view) {
	// switch (view.getId()) {
	// case R.id.ll_ageJudge:
	// String ageJudge = tvAgejudge.getText().toString();
	// if ("0".equals(ageJudge)) {
	// tvAgejudge.setText("1");
	// } else {
	// tvAgejudge.setText("0");
	// }
	// break;
	// case R.id.ll_worthJudge:
	// String worthJudge = tvWorthJudge.getText().toString();
	// if ("0".equals(worthJudge)) {
	// tvWorthJudge.setText("1");
	// } else {
	// tvWorthJudge.setText("0");
	// }
	// break;
	// }
	// }

	private int worthMaxValue, worthMinValue, ageMinValue, ageMaxValue,
			distanceMaxValue;

	@Override
	public void onSingleSeekBarValuesChanged(SingleSeekBar<?> bar,
			Integer minValue, Integer maxValue) {
		distanceSingleSeekBar.setSelectedMinValue(0);
		String distance = 200 == maxValue ? maxValue + "km+" : maxValue + "km";
		tvDistanceScope.setText(distance);
		distanceMaxValue = maxValue;
	}

	@Override
	public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
			Integer minValue, Integer maxValue) {
		switch ((Integer) bar.getTag()) {
		case R.id.ll_container_age:
			tvAgeScope.setText(minValue + "-" + maxValue);
			ageMinValue = minValue;
			ageMaxValue = maxValue;
			break;
		case R.id.ll_container_worth:
			tvWorthScope.setText(minValue + "-" + maxValue);
			worthMinValue = minValue;
			worthMaxValue = maxValue;
			break;
		}

	}
}
