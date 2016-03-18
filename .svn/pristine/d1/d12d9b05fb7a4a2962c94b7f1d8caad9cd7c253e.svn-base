package cn.sx.decentworld.activity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.CheckResultBean;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.SupporterBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.widget.VerticalSeekBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_examine_welcome)
public class ExamineWelcomeActivity extends BaseFragmentActivity implements
		OnClickListener {
	@ViewById(R.id.vs_examine_support)
	VerticalSeekBar vsSupport;
	@ViewById(R.id.vs_examine_no_support)
	VerticalSeekBar vsNoSupport;
	@ViewById(R.id.lv_examine_support)
	ListView lvExamineSupport;
	@ViewById(R.id.ll_check_failure)
	LinearLayout llCheckFailure;
	@ViewById(R.id.rel_check_success)
	RelativeLayout relCheckedSuccess;
	@ViewById(R.id.tv_back_login)
	TextView tvBackLogin;
	@ViewById(R.id.tv_try_again)
	TextView tvTryAgain;
	@ViewById(R.id.tv_another_method)
	TextView tvAnotherMethod;
	private ExamineSupportAdapter adapter;
	@Bean
	GetUserInfo getUserInfo;
	@Bean
	ToastComponent toast;
	private SendUrl mSendUrl;
	private CheckResultBean checkResultBean;

	@AfterViews
	public void init() {
		EventBus.getDefault().register(this);
		vsSupport.setEnabled(false);
		vsNoSupport.setEnabled(false);
		mSendUrl = new SendUrl(this);
		EGetIntent();
		initList();
		tvTryAgain.setOnClickListener(this);
		tvAnotherMethod.setOnClickListener(this);
		tvBackLogin.setOnClickListener(this);
	}

	private void initList() {
		adapter = new ExamineSupportAdapter();
		lvExamineSupport.setAdapter(adapter);
	}

	private void showToast(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

			}
		});
	}

	// private String beginTime, support, unsupport, standard, retryTimes;

	private void EGetIntent() {
		// beginTime = getIntent().getStringExtra("beginTime");
		// support = getIntent().getStringExtra("support");
		// // support = "5";
		// unsupport = getIntent().getStringExtra("unsupport");
		// // unsupport = "21";
		// standard = getIntent().getStringExtra("standard");
		// // standard = "20";
		// retryTimes = getIntent().getStringExtra("retryTimes");
		// setView();
		checkResultBean = (CheckResultBean) getIntent().getSerializableExtra(
				"checkResult");
	}

	// private void setView() {
	// if (null == checkResultBean) {
	// return;
	// }
	// vsNoSupport.setMax(Integer.valueOf(checkResultBean.standard));
	// vsNoSupport.setProgress(Integer.valueOf(unsupport));
	// vsSupport.setMax(Integer.valueOf(checkResultBean.standard));
	// vsSupport.setProgress(Integer.valueOf(support));
	// ifCheckedPassForIntentAndRequest();
	// }

	// private void ifCheckedPassForIntentAndRequest() {
	// if (null == standard) {
	// return;
	// }
	// if (null != support
	// && Integer.valueOf(support) >= Integer.valueOf(standard)) {
	// toast.show("审核已通过");
	// showSuccessRel();
	// } else if (null != unsupport
	// && Integer.valueOf(unsupport) >= Integer.valueOf(standard)) {
	// toast.show("审核没通过");
	// showFailureLl();
	// } else {
	// hideView();
	// }
	// }

	private void showSuccessRel() {
		relCheckedSuccess.setVisibility(View.VISIBLE);
		llCheckFailure.setVisibility(View.GONE);
	}

	private void showFailureLl() {
		relCheckedSuccess.setVisibility(View.GONE);
		llCheckFailure.setVisibility(View.VISIBLE);
	}

	private void hideView() {
		relCheckedSuccess.setVisibility(View.GONE);
		llCheckFailure.setVisibility(View.GONE);
	}

	private String supportAmount;

	// {"nickName":"test22","supportAmount":"28","supportID":"153344"}
	@Subscriber(tag = NotifyByEventBus.NT_CHECK_RESULT)
	public void receiveSupport(String data) {
		// try {
		// JSONObject object = new JSONObject(data);
		// Supportbean bean = new Supportbean();
		// bean.nickName = object.getString("nickName");
		// supportAmount = object.getString("supportAmount");
		// vsSupport.setProgress(Integer.valueOf(supportAmount));
		// bean.supportID = object.getString("supportID");
		// supportbeans.add(bean);
		// LogUtils.e("bm", "supportAmount--" + supportAmount);
		// // ifCheckedPass();
		// adapter.notifyDataSetChanged();
		// } catch (JSONException e) {
		// toast.show("解析错误");
		// }
	}

	private String unSupportAmount;

	// 不支持只返回一个数字
	// @Subscriber(tag = NotifyByEventBus.NT_APPEARANCE_CHECK_UNSUPPORT)
	// public void receiveUnSupport(String data) {
	// unSupportAmount = data;
	// vsNoSupport.setProgress(Integer.valueOf(unSupportAmount));
	// ifCheckedPass();
	// }

	// private void ifCheckedPass() {
	// if (null == standard) {
	// return;
	// }
	// if (null != supportAmount
	// && Integer.valueOf(supportAmount) >= Integer.valueOf(standard)) {
	// toast.show("审核已通过");
	// showSuccessRel();
	// } else if (null != unSupportAmount
	// && Integer.valueOf(unSupportAmount) >= Integer
	// .valueOf(standard)) {
	// toast.show("审核没通过");
	// showFailureLl();
	// }
	// }

	class ExamineSupportAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return checkResultBean == null ? 0 : checkResultBean.supporters
					.size();
		}

		@Override
		public SupporterBean getItem(int position) {
			return checkResultBean.supporters.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View con, ViewGroup arg2) {
			ViewHolder vh;
			if (null == con) {
				con = View.inflate(mContext, R.layout.item_examine_support,
						null);
				vh = new ViewHolder();
				vh.tvNickName = (TextView) con.findViewById(R.id.tv_nickname);
				vh.ivSupport = (ImageView) con
						.findViewById(R.id.iv_examine_support);
				con.setTag(vh);
			} else {
				vh = (ViewHolder) con.getTag();
			}
			SupporterBean bean = checkResultBean.supporters.get(position);
			vh.tvNickName.setText(bean.showName);
			String supportID = bean.id;
			if (null != supportID) {
				ImageLoaderHelper.mImageLoader.displayImage(ImageUtils
						.getIconByDwID(supportID, ImageUtils.ICON_MAIN),
						vh.ivSupport, ImageLoaderHelper.mOptions);
			} else {
				vh.ivSupport.setImageResource(R.drawable.default_icon);
			}
			return con;
		}

		class ViewHolder {
			ImageView ivSupport;
			TextView tvNickName;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_try_again:
			intent = new Intent(mContext, PayDialogActivity_.class);
			// intent.putExtra("retryTimes", retryTimes);
			intent.putExtra(Constants.CHECK, Constants.CHECK_TRY_AGAIN);
			startActivityForResult(intent, Constants.REQUEST_CODE);
			break;

		case R.id.tv_another_method:
			intent = new Intent(mContext,
					RegisterWhatYouHaveCheckActivity_.class);
			startActivity(intent);
			break;
		case R.id.tv_back_login:
			intent = new Intent(mContext, LoginActivity_.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// switch (msg.what) {
			// // case 2222:
			// // try {
			// // LogUtils.e("bm", "2222--" + msg.obj.toString());
			// // JSONObject object = new JSONObject(msg.obj.toString());
			// // String dwID = object.getString("dwID");
			// // String token = object.getString("token");
			// // } catch (JSONException e) {
			// // toast.show("解析错误");
			// // }
			// // break;
			// case 2010:
			// try {
			// LogUtils.e("bm", "2010--" + msg.obj.toString());
			// JSONObject jsonObject = new JSONObject(msg.obj.toString());
			// String dwID = jsonObject.getString("dwID");
			// String token = jsonObject.getString("token");
			// beginTime = jsonObject.getString("beginTime");
			// support = jsonObject.getString("support");
			// unsupport = jsonObject.getString("unsupport");
			// standard = jsonObject.getString("standard");
			// } catch (JSONException e) {
			// toast.show("解析错误");
			// }
			// break;
			// case 2011:
			// try {
			// LogUtils.e("bm", "2011--" + msg.obj.toString());
			// JSONObject jsonObject = new JSONObject(msg.obj.toString());
			// String dwID = jsonObject.getString("dwID");
			// String token = jsonObject.getString("token");
			// beginTime = jsonObject.getString("beginTime");
			// support = jsonObject.getString("support");
			// unsupport = jsonObject.getString("unsupport");
			// standard = jsonObject.getString("standard");
			// String retryTimes = jsonObject.getString("retryTimes");
			// } catch (JSONException e) {
			// toast.show("解析错误");
			// }
			// break;
			// }
			// setView();
			adapter.notifyDataSetChanged();
		};
	};

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (requestCode == Constants.REQUEST_CODE) {
	// getUserInfo.getUserdwID(DecentWorldApp.getInstance().getUserName(),
	// mHandler);
	// }
	// }
}
