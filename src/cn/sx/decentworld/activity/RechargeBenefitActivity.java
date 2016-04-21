/**
 * 
 */
package cn.sx.decentworld.activity;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.incom.SetBenefitPwdDialog;
import cn.sx.decentworld.dialog.incom.SetBenefitPwdDialog.SetBenefitPwdListener;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.engine.BenefitEngine.IsSetBenefitListener;
import cn.sx.decentworld.fragment.benefit.BenefitFragment;
import cn.sx.decentworld.fragment.benefit.BenefitFragment_;
import cn.sx.decentworld.fragment.recharge.RechargeFragment;
import cn.sx.decentworld.fragment.recharge.RechargeFragment_;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.widget.CircularImage;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: RechargeBenefitActivity.java
 * @Description:
 * @author: cj
 * @date: 2016年1月14日 上午9:02:56
 */
@EActivity(R.layout.activity_recharge_and_benefit)
public class RechargeBenefitActivity extends BaseFragmentActivity implements OnClickListener {
	private static final String TAG = "RechargeBenefitActivity";
	private static final int TAB_RECHARGE = 1;
	private static final int TAB_BENEFIT = 2;
	private int index = TAB_RECHARGE;
	/**
	 * 工具类
	 */
	@Bean
	ToastComponent toast;

	/**
	 * 界面资源
	 */
	@ViewById(R.id.ll_rb_title_bg)
	LinearLayout ll_rb_title_bg;
	/** 显示充值布局 **/
	@ViewById(R.id.tv_rb_title_recharge)
	TextView tv_rb_title_recharge;

	/** 显示收益布局 **/
	@ViewById(R.id.tv_rb_title_benefit)
	TextView tv_rb_title_benefit;

	@ViewById(R.id.iv_rb_icon)
	CircularImage iv_rb_icon;

	@ViewById(R.id.ll_rb_back)
	LinearLayout ll_rb_back;

	/**
	 * 变量
	 */
	private String userID = "";
	private FragmentManager fragmentManager;
	private RechargeFragment rechargeFragment;
	private BenefitFragment benefitFragment;
	private boolean isRecommend = false;

	@AfterViews
	void init() {
		userID = DecentWorldApp.getInstance().getDwID();
		fragmentManager = getSupportFragmentManager();
		// 如果是推荐，那么切换到收益Fragment
		isRecommend = getIntent().getBooleanExtra("isRecommend", false);
		if (isRecommend) {
			index = TAB_BENEFIT;
		}
		setTab(index);
		initListener();
		iv_rb_icon.setVisibility(View.VISIBLE);
		String icon = ImageUtils.getIconByDwID(userID, ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(icon)) {
			ImageLoaderHelper.mImageLoader.displayImage(icon, iv_rb_icon);
		}
	}

	/**
     * 
     */
	private void initListener() {
		ll_rb_back.setOnClickListener(this);
		tv_rb_title_recharge.setOnClickListener(this);
		tv_rb_title_benefit.setOnClickListener(this);
		iv_rb_icon.setOnClickListener(this);
	}

	private void setTab(int index) {
		setSelectionTitle(index);
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (index) {
		case TAB_RECHARGE:
			if (rechargeFragment == null) {
				rechargeFragment = new RechargeFragment_();
				transaction.add(R.id.fl_rb_container, rechargeFragment);
			} else {
				transaction.show(rechargeFragment);
			}

			break;
		case TAB_BENEFIT:
			if (benefitFragment == null) {
				benefitFragment = new BenefitFragment_();
				transaction.add(R.id.fl_rb_container, benefitFragment);
			} else {
				transaction.show(benefitFragment);
			}
			break;

		default:
			break;
		}
		transaction.commit();
	}

	/**
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (rechargeFragment != null) {
			transaction.hide(rechargeFragment);
		}
		if (benefitFragment != null) {
			transaction.hide(benefitFragment);
		}

	}

	/**
	 * 切换标题栏
	 * 
	 * @param index2
	 */
	private void setSelectionTitle(int index) {
		if (index == TAB_RECHARGE) {
			ll_rb_title_bg.setBackgroundResource(R.drawable.rb_title_btn_bg1);
			tv_rb_title_recharge.setTextColor(getResources().getColor(R.color.white));
			tv_rb_title_benefit.setTextColor(getResources().getColor(R.color.two_black));

		} else if (index == TAB_BENEFIT) {
			ll_rb_title_bg.setBackgroundResource(R.drawable.rb_title_btn_bg2);
			tv_rb_title_recharge.setTextColor(getResources().getColor(R.color.two_black));
			tv_rb_title_benefit.setTextColor(getResources().getColor(R.color.white));
		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_rb_back:
			finish();
			break;
		case R.id.tv_rb_title_recharge:
			/** 点击隐藏充值布局，显示收益布局 **/
			setTab(TAB_RECHARGE);
			break;
		case R.id.tv_rb_title_benefit:
			/** 点击隐藏收益布局，显示充值布局 **/
		    enterBenefit();
			break;
		case R.id.iv_rb_icon:
			/** 跳转到我的资料界面 **/
			// TODO
			// toast.show(Constants.DEVELOPING_NOTIFY);
			break;
		default:
			break;
		}

	}

    //保存提现密码
	private boolean isBenefitPwd = false;
	
    /**
     * 进入收益界面
     */
    private void enterBenefit()
    {
        hasPassword();
    }

    
    /**
     * 判断是否有密码
     */
    private void hasPassword()
    {
        if(!isBenefitPwd)
        {
            new BenefitEngine().isSetBenefitPwd(new IsSetBenefitListener()
            {
                
                @Override
                public void onSuccess(boolean isSet)
                {
                    if(isSet)
                        pwdHandler.sendEmptyMessage(1);
                    else
                        pwdHandler.sendEmptyMessage(2);
                }
                
                @Override
                public void onFailure(String cause)
                {
                    pwdHandler.sendEmptyMessage(0);
                }
            });
        }
        else
        {
            setTab(TAB_BENEFIT);
        }
    }
    
    Handler pwdHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 0:
                    //请求错误
                    
                    break;
                case 1:
                    //设置过
                    isBenefitPwd = true;
                    setTab(TAB_BENEFIT);
                    break;
                case 2:
                    //没有设置,设置密码
                    setBenefitPwd();
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * 设置收益独立密码
     */
    protected void setBenefitPwd()
    {
        //设置收益密码对话框
        SetBenefitPwdDialog dialog = new SetBenefitPwdDialog();
        dialog.setListener(new SetBenefitPwdListener()
        {
            @Override
            public void onSubmit(String password)
            {
                new BenefitEngine().setBenefitPwd(password, new NetCallback()
                {
                    @Override
                    public void onSuccess(String msg)
                    {
                      //设置密码成功
                        isBenefitPwd = true;
                        setTab(TAB_BENEFIT);
                        toast.show(msg);
                    }
                    
                    @Override
                    public void onFailure(String cause)
                    {
                      //设置密码失败
                        setTab(TAB_RECHARGE);
                        toast.show("设置密码失败");
                    }
                });
            }
        });
        dialog.show(getSupportFragmentManager(), "setBenefitPwd");
    }

}
