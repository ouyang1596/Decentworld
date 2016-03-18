/**
 * 
 */
package cn.sx.decentworld.fragment.recharge;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.RechargePayMethodActivity_;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.component.KeyboardComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.BaseFragment;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: RechargeFragment.java
 * @Description: 充值界面
 * @author: cj
 * @date: 2016年1月14日 上午9:17:20
 */
@EFragment(R.layout.fragment_recharge)
public class RechargeFragment extends BaseFragment {
	private static final String TAG = "RechargeFragment";
	/**
	 * 工具类
	 */
	@Bean
	ToastComponent toast;
	// 对键盘的操作
	@Bean
	KeyboardComponent keyboardComponent;

	/**
	 * 界面资源
	 */
	@ViewById(R.id.et_money)
	EditText etMoney;
	@ViewById(R.id.btn_ensure)
	Button btnEnsure;

	/**
	 * 变量
	 */
	public static String MONEY = "money";

	/**
	 * 入口
	 */
	@AfterViews
	public void init() {
		openKeyboard();
		btnEnsure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (etMoney.length() <= 0) {
					toast.show("请先输入金额");
					return;
				}
				String money = etMoney.getText().toString();
				Integer data = Integer.valueOf(money);
				if (data < 2) {
					toast.show("输入金额不能小于2元");
					return;
				}
				Intent intent = new Intent(getActivity(), RechargePayMethodActivity_.class);
				intent.putExtra(MONEY, etMoney.getText().toString());
				intent.putExtra(ConstantIntent.COMMOM_IS_TO_SOCIALSTATUSACTIVITY, 0);
				startActivity(intent);
			}
		});
	}

	/**
	 * 数据延迟加载
	 */
	@Override
	protected void lazyLoad() {

	}

	@Override
	public void turnToTab(int tab) {

	}

	/**
	 * 打开软键盘
	 */
	private void openKeyboard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				keyboardComponent.openKeybord(etMoney);
			}
		}, 500);
	}

	/**
	 * 关闭软键盘
	 */
	private void closeKeyBoard() {
		keyboardComponent.closeKeyboard(etMoney);
	}

	/**
	 * Fragment显隐性改变时调用
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		/** 控制键盘的显示与隐藏 **/
		if (hidden)
			closeKeyBoard();
		else
			openKeyboard();

		super.onHiddenChanged(hidden);
	}

}
