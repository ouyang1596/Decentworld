package cn.sx.decentworld.activity;

import java.util.Calendar;

import org.jivesoftware.smack.XMPPConnection;

import android.content.Intent;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.listener.onConnectOpenFireListener;
import cn.sx.decentworld.task.ConnectOpenFireTask;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.LoginHelper;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.XmppHelper;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_default)
public class DefaultActivity extends BaseFragmentActivity implements
		onConnectOpenFireListener {
	private static final String TAG = "DefaultActivity";
	// private static final long DELAY_TIME = 1000;
	private XMPPConnection con;
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;

	private Calendar mCalendar;
	@ViewById(R.id.tv_year)
	TextView tvYear;
	@ViewById(R.id.tv_month)
	TextView tvMonth;
	@ViewById(R.id.tv_week)
	TextView tvWeek;
	@ViewById(R.id.tv_day)
	TextView tvDay;
	@ViewById(R.id.tv_motto)
	TextView tvMotto;
	@ViewById(R.id.tv_motto_six)
	TextView tvMottoSix;
	@Bean
	ToastComponent toast;

	@AfterViews
	public void init() {
		initDate();
		redirectToActivity();
	}

	private void initDate() {
		mCalendar = Calendar.getInstance();
		int year = mCalendar.get(Calendar.YEAR);
		tvYear.setText("" + year);
		int m = mCalendar.get(Calendar.MONTH) + 1;
		String month = TimeUtils.MonthHandle(m);
		tvMonth.setText(month);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		tvDay.setText("" + day);
		int w = mCalendar.get(Calendar.DAY_OF_WEEK);
		String week = TimeUtils.weekHandle(w);
		tvWeek.setText(week);
		LogUtils.e("bm", "year--" + year + "month--" + month + "day--" + day
				+ "week--" + w);
	}

	private void redirectToActivity() {
		// 这条代码完全没有必要，因为我看逻辑，只要用户登录成功过。这里面肯定是2222
		// 有时候会返回2010或2011进入审核页面
		if (!("2222".equals(LoginHelper.getResultCode(this)))) {
			// 跳转到登录界面
			Intent intent = new Intent(DefaultActivity.this,
					LoginActivity_.class);
			String username = LoginHelper.getUsername(this);
			intent.putExtra("username", username);
			startActivity(intent);
			finish();
		} else if (LoginHelper.isLogined(this)) {
			// TODO 其实这个地方还可以优化，不用判断是否鉴权，先进MainActivity再判断鉴权
			if (NetworkUtils.isNetWorkConnected(mContext)) {
				if (!XmppHelper.WasAuthenticated()) {
					String dwID = DecentWorldApp.getInstance().getDwID();
					String username = DecentWorldApp.getInstance()
							.getUserName();
					String password = DecentWorldApp.getInstance()
							.getPassword();
					openfireLogin(username, dwID, password);
				} else {
					Intent intent = new Intent(DefaultActivity.this,
							MainActivity_.class);
					startActivity(intent);
					finish();
				}
			} else {
				Intent intent = new Intent(DefaultActivity.this,
						MainActivity_.class);
				startActivity(intent);
				finish();
			}

		} else {
			// 确保内存中的数据为空
			DecentWorldApp.getInstance().setToken("");
			DecentWorldApp.getInstance().setDwID("");
			DecentWorldApp.getInstance().setUserName("");
			DecentWorldApp.getInstance().setPassword("");
			// 跳转到登录界面
			Intent intent = new Intent(DefaultActivity.this,
					LoginActivity_.class);
			String username = LoginHelper.getUsername(this);
			intent.putExtra("username", username);
			startActivity(intent);
			finish();
		}
	}

	private void openfireLogin(String username, String dwID, String password) {
		LogUtils.i(TAG, "登录过，");
		ConnectOpenFireTask task = new ConnectOpenFireTask();
		task.execute(username, dwID, password);
		task.setOnConnectOpenFireListener(this);
	}

	@Override
	public void onConnetedOpenFire(int state) {
		if (state == SUCCESS) {
			Intent intent = new Intent(DefaultActivity.this,
					MainActivity_.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(DefaultActivity.this,
					LoginActivity_.class);
			startActivity(intent);
		}
		finish();
	}
}
