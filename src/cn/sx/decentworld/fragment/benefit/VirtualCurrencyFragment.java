/**
 * 
 */
package cn.sx.decentworld.fragment.benefit;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.SetBankCardActivity_;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.VerifyBenefitPwdDialog;
import cn.sx.decentworld.dialog.VerifyBenefitPwdDialog.VerifyBenefitPwdListener;
import cn.sx.decentworld.dialog.incom.VirtualDrawDialog;
import cn.sx.decentworld.dialog.incom.VirtualDrawDialog.VirtualDrawListener;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.engine.UserDataEngine;
import cn.sx.decentworld.engine.UserDataEngine.GetWorthListener;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.engine.UserDataEngine.GetWealthListener;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.DataConvertUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: VirtualCurrencyFragment.java
 * @Description:
 * @author: cj
 * @date: 2016年1月14日 上午11:02:59
 */
@EFragment(R.layout.fragment_virtual_currency)
public class VirtualCurrencyFragment extends BaseFragment implements OnClickListener, GetWealthListener
{
    /**
     * 常量
     */
    private static final String TAG = "VirtualCurrencyFragment";

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    //身价
    @ViewById(R.id.tv_vc_worth)
    TextView mTvWorth;
    //身家
    @ViewById(R.id.tv_vc_wealth)
    TextView mTvWealth;
    /** 提现 **/
    @ViewById(R.id.rl_vc_draw)
    RelativeLayout mDrawWealth;
    /** 银行卡 **/
    @ViewById(R.id.tv_vc_band_card)
    TextView tvSetBankCard;
    @ViewById(R.id.tv_vc_band_bankCard)
    TextView tvBankCard;
    @ViewById(R.id.tv_cash_benefit_not_draw)
    TextView mTvBigWealth;

    /**
     * 变量
     */
    private boolean isPrepared = false;
    private String userID = "";
    private String bankCard = "";

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        initVar();
        initListener();
        initView();
        isPrepared = true;
        lazyLoad();
    }

    /**
     * 初始化变量
     */
    private void initVar()
    {
        EventBus.getDefault().register(this);
        userID = DecentWorldApp.getInstance().getDwID();
        bankCard = UserInfoEngine.getInstance().getUserInfo().getBankCard();
    }

    /**
     * 初始化监听
     */
    private void initListener()
    {
        mDrawWealth.setOnClickListener(this);
        tvSetBankCard.setOnClickListener(this);
    }

    /**
     * 初始化界面
     */
    private void initView()
    {
        if (CommUtil.isBlank(bankCard))
        {
            tvSetBankCard.setText("转账到哪里？");
            tvBankCard.setText("");
        }
        else
        {
            tvSetBankCard.setText("转账到:");
            tvBankCard.setText(bankCard);
        }
    }

    /**
     * 数据延迟加载
     */
    @Override
    protected void lazyLoad()
    {
        if (isPrepared)
        {
            // 获取身家
            UserDataEngine.getInstance().getWealth(this);
        }
    }


    @Override
    public void turnToTab(int tab)
    {

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rl_vc_draw:
                drawWealth();
                break;
            case R.id.tv_vc_band_card:
                setBankcard();
            default:
                break;
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * 设置银行卡账户
     */
    private void setBankcard()
    {
        vefityBenefitPwd(SET_BANK_CARD);
    }

    /**
     * 虚拟币提现
     */
    private void drawWealth()
    {
        vefityBenefitPwd(SET_DRAW_WEALTH);
    }

    //
    private static final int SET_BANK_CARD = 1;
    private static final int SET_DRAW_WEALTH = 2;

    private void vefityBenefitPwd(final int type)
    {
        VerifyBenefitPwdDialog benefitPwdDialog = new VerifyBenefitPwdDialog();
        benefitPwdDialog.setListener(new VerifyBenefitPwdListener()
        {

            @Override
            public void onSuccess(final String tempToken)
            {
                switch (type)
                {
                    case SET_BANK_CARD:
                        Intent intent = new Intent(getActivity() , SetBankCardActivity_.class);
                        if (CommUtil.isNotBlank(bankCard))
                        {
                            intent.putExtra("bankCard", bankCard);
                        }
                        else
                        {
                            intent.putExtra("bankCard", "");
                        }
                        intent.putExtra("tempToken", tempToken);
                        startActivity(intent);
                        break;
                    case SET_DRAW_WEALTH:
                        final BenefitEngine benefitEngine = new BenefitEngine();

                        VirtualDrawDialog drawDialog = new VirtualDrawDialog();
                        drawDialog.setListener(new VirtualDrawListener()
                        {
                            @Override
                            public void onSubmit(final int amount)
                            {
                                benefitEngine.withdrawWealth(String.valueOf(amount), tempToken, new NetCallback()
                                {
                                    @Override
                                    public void onSuccess(String msg)
                                    {
                                        onResume();
                                    }

                                    @Override
                                    public void onFailure(String cause)
                                    {
                                        toast.show(cause);
                                    }
                                });
                            }
                        });
                        drawDialog.show(getFragmentManager(), "drawDialog");
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onFailure(String cause)
            {
                // TODO Auto-generated method stub

            }
        });
        benefitPwdDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        LogUtils.i(TAG, "onResume");
        UserInfo userInfo = UserInfoEngine.getInstance().getUserInfo();
        //更新身家
        mTvWealth.setText(userInfo.getWealth() + "");
        mTvBigWealth.setText(userInfo.getWealth() + "");
        //更新身价
        mTvWorth.setText(userInfo.getWorth()+"");
        //更新银行卡
        String bankCard = userInfo.getBankCard();
        if (CommUtil.isBlank(bankCard))
        {
            tvSetBankCard.setText("转账到哪里？");
            tvBankCard.setText("");
        }
        else
        {
            tvSetBankCard.setText("转账到:");
            tvBankCard.setText(bankCard);
        }
    }
    

    /**
     * 更新身家
     */
    @Subscriber(tag = NotifyByEventBus.NT_REFRESH_WEALTH)
    public void refreshWealth(String info)
    {
        // 获取身家
        LogUtils.i(TAG, info);
        UserDataEngine.getInstance().getWealth(this);
    }

    /**
     * 获取身家完成
     */
    @Override
    public void onGetWealthFinished(String wealth)
    {
        mTvWealth.setText(wealth);
        mTvBigWealth.setText(DataConvertUtils.formatFloat(Float.valueOf(wealth)));
    }
}
