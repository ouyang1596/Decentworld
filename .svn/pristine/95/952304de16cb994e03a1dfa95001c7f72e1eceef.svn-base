/**
 * 
 */
package cn.sx.decentworld.fragment.benefit;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.BaseFragment;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: BenefitFragment.java
 * @Description: 收益(包括现金收益、贵人收益、虚拟币)
 * @author: cj
 * @date: 2016年1月14日 上午9:18:52
 */
@EFragment(R.layout.fragment_benefit)
public class BenefitFragment extends BaseFragment implements OnClickListener
{
    private static final String TAG = "BenefitFragment";
    private boolean isPrepared = false;
    private static final int CASHB_ENEFIT = 1;
    private static final int GR_BENEFIT = 2;
    private static final int VIRTUAL_CURRENCY = 3;
    private int index = CASHB_ENEFIT;

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    @ViewById(R.id.ll_benefit_titile_switch_bg)
    LinearLayout ll_benefit_titile_switch_bg;
    /** 现金收益 **/
    @ViewById(R.id.tv_benefit_1)
    TextView tv_benefit_1;
    @ViewById(R.id.tv_benefit_2)
    TextView tv_benefit_2;
    @ViewById(R.id.tv_benefit_3)
    TextView tv_benefit_3;
    
    
    /**
     * 变量
     */
    private CashBenefitFragment cashBenefitFragment;
    private GrBenefitFragment grBenefitFragment;
    private VirtualCurrencyFragment virtualCurrencyFragment;
    private FragmentManager fragmentManager; 
    private String userID = "";
    


    @AfterViews
    void init()
    {
        initVar();
        setTab(index);
        initListener();
        isPrepared = true;
        lazyLoad();
    }

  
    /**
     * 初始化变量
     */
    private void initVar()
    {
        fragmentManager = getFragmentManager();
        userID = DecentWorldApp.getInstance().getDwID();
    }

    /**
     * 
     */
    private void initListener()
    {
        tv_benefit_1.setOnClickListener(this);
        tv_benefit_2.setOnClickListener(this);
        tv_benefit_3.setOnClickListener(this);
    }

    /**
     * 跳转到指定界面
     * @param index
     */
    private void setTab(int index)
    {
        setSelectionTab(index);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (index)
        {
            case CASHB_ENEFIT:
                if(cashBenefitFragment == null)
                {
                    cashBenefitFragment = new CashBenefitFragment_();
                    transaction.add(R.id.fl_benefitcontainer, cashBenefitFragment);
                }
                else
                {
                    transaction.show(cashBenefitFragment);
                }
                
                break;
            case GR_BENEFIT:
                if(grBenefitFragment == null)
                {
                    grBenefitFragment = new GrBenefitFragment_();
                    transaction.add(R.id.fl_benefitcontainer, grBenefitFragment);
                }
                else
                {
                    transaction.show(grBenefitFragment);
                }
                break;
            case VIRTUAL_CURRENCY:
                if(virtualCurrencyFragment == null)
                {
                    virtualCurrencyFragment = new VirtualCurrencyFragment_();
                    transaction.add(R.id.fl_benefitcontainer, virtualCurrencyFragment);
                }
                else
                {
                    transaction.show(virtualCurrencyFragment);
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
    private void hideAllFragment(FragmentTransaction transaction)
    {
        if(cashBenefitFragment != null)
        {
            transaction.hide(cashBenefitFragment);
        }
        
        if(grBenefitFragment != null)
        {
            transaction.hide(grBenefitFragment);
        }
        
        if(virtualCurrencyFragment != null)
        {
            transaction.hide(virtualCurrencyFragment);
        }
        
    }

    /**
     * @param index2
     */
    private void setSelectionTab(int index)
    {
        switch (index)
        {
            case CASHB_ENEFIT:
                ll_benefit_titile_switch_bg.setBackgroundResource(R.drawable.benefit_title_switch_bg1);
                break;
            case GR_BENEFIT:
                ll_benefit_titile_switch_bg.setBackgroundResource(R.drawable.benefit_title_switch_bg2);
                break;
            case VIRTUAL_CURRENCY:
                ll_benefit_titile_switch_bg.setBackgroundResource(R.drawable.benefit_title_switch_bg3);
                break;
            default:
                break;
        }
    }

    /**
     * 数据加载
     */
    @Override
    protected void lazyLoad()
    {

    }

    /**
     * 跳转到指定TAB
     */
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
            case R.id.tv_benefit_1:
                setTab(CASHB_ENEFIT);
                break;
            case R.id.tv_benefit_2:
                setTab(GR_BENEFIT);
                break;
            case R.id.tv_benefit_3:
                setTab(VIRTUAL_CURRENCY);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
    }

}
