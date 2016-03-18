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
import cn.sx.decentworld.activity.DrawCashSettingActivity_;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.EditAndModificationDialog;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;

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
public class VirtualCurrencyFragment extends BaseFragment implements OnClickListener
{
    /**
     * 常量
     */
    private static final String TAG = "VirtualCurrencyFragment";
    private static final int GET_USER_WEALTH = 1;
    private static final int GET_USER_WORTH = 2;

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;
    @Bean
    GetUserInfo getUserInfo;

    /**
     * 界面资源
     */
    /** 我的身价 **/
    @ViewById(R.id.tv_vc_worth)
    TextView tv_vc_worth;
    /** 我的身家 **/
    @ViewById(R.id.tv_vc_wealth)
    TextView tv_vc_wealth;
    /** 提现 **/
    @ViewById(R.id.rl_vc_draw)
    RelativeLayout rl_vc_draw;
    /** 设置密码 **/
    @ViewById(R.id.tv_vc_set_pwd)
    TextView tvSetPwd;
    /** 银行卡 **/
    @ViewById(R.id.tv_vc_band_card)
    TextView tvSetBankCard;

    /**
     * 变量
     */
    private boolean isPrepared = false;
    private String userID = "";

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        initVar();
        initListener();
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
    }

    /**
     * 初始化监听
     */
    private void initListener()
    {
        rl_vc_draw.setOnClickListener(this);
        tvSetPwd.setOnClickListener(this);
        tvSetBankCard.setOnClickListener(this);
    }

    /**
     * 数据延迟加载
     */
    @Override
    protected void lazyLoad()
    {
        if (isPrepared)
        {
            // 获取身价
            getUserInfo.getWorth(userID, meHandler, GET_USER_WORTH);
            // 获取身家
            getUserInfo.getWealth(userID, meHandler, GET_USER_WEALTH);
        }
    }

    Handler meHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case GET_USER_WEALTH:
                    tv_vc_wealth.setText(msg.obj.toString());
                    break;
                case GET_USER_WORTH:
                    tv_vc_worth.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        };
    };

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
                drawCard();
               
                break;
            case R.id.tv_vc_set_pwd:
                setPwd();
                break;
            case R.id.tv_vc_band_card:
                setAccount();

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
     * 更新身价
     */
    @Subscriber(tag = NotifyByEventBus.NT_REFRESH_WORTH)
    public void refreshWorth(String info)
    {
        // 获取身价
        LogUtils.i(TAG, info);
        getUserInfo.getWorth(userID, meHandler, GET_USER_WORTH);
    }

    /**
     * 更新身家
     */
    @Subscriber(tag = NotifyByEventBus.NT_REFRESH_WEALTH)
    public void refreshWealth(String info)
    {
        // 获取身家
        LogUtils.i(TAG, info);
        getUserInfo.getWealth(userID, meHandler, GET_USER_WEALTH);
    }

    /**
     * 设置提现的账号
     */
    private void setAccount()
    {
        openSetting();
    }

    /**
     * 设置提现的密码
     */
    private void setPwd()
    {
        openSetting();
    }
    
    private void openSetting()
    {
        Intent intent = new Intent(getActivity(), DrawCashSettingActivity_.class);
        startActivity(intent);
    }
    
    /**
     * 提现
     */
    private void drawCard()
    {
        openSetting();
    }

}
