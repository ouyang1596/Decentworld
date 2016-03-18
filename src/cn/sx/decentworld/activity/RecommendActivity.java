/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: RecommendActivity.java
 * @Description: 
 * @author: cj
 * @date: 2016年1月13日 下午6:17:38
 */
@EActivity(R.layout.activity_recommend)
public class RecommendActivity extends BaseFragmentActivity implements OnClickListener
{
    
    private static final String TAG = "RecommendActivity";
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
    @ViewById(R.id.iv_back)
    ImageView iv_back;
    @ViewById(R.id.iv_recommend_unavaliable)
    ImageView iv_recommend_unavaliable;

    /** 电话号码 **/
    @ViewById(R.id.et_recommend_phoneNum)
    EditText et_recommend_phoneNum;
    /** 金额 **/
    @ViewById(R.id.et_recommend_money)
    EditText et_recommend_money;
    /** 确定 **/
    @ViewById(R.id.btn_recommend_confirm)
    Button btn_recommend_confirm;
    
    
    /**
     * 变量
     */
    private String dwID = "";
    private static final int VALIDATERECOMMEND = 1;
    private static final int NEND_BAND_ACCOUNT = 2;
    private String phoneNum = "";
    private String money = "";
    
    
    @AfterViews
    void init()
    {
        initData();
        initView();
        initListener();
    }


    /**
     * 初始化变量
     */
    private void initData()
    {
        dwID = DecentWorldApp.getInstance().getDwID();
    }


    /**
     * 
     */
    private void initListener()
    {
        iv_back.setOnClickListener(this);
        iv_recommend_unavaliable.setOnClickListener(this);
        btn_recommend_confirm.setOnClickListener(this);
        et_recommend_phoneNum.addTextChangedListener(new TextWatcher()
        {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
                
            }
        });
    }


    /**
     * 初始化界面
     */
    private void initView()
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
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_recommend_unavaliable:
                startActivity(new Intent(this, RecommendUnavaliableActivity_.class));
                break;
            case R.id.btn_recommend_confirm:
                confirm();
                break;
            default:
                break;
        }
    }


    /**
     * 确定
     */
    private void confirm()
    {
        phoneNum = et_recommend_phoneNum.getText().toString().trim();
        money = et_recommend_money.getText().toString().trim();
        //判断电话号码
        if(CommUtil.isBlank(phoneNum))
        {
            LogUtils.i(TAG, "请输入电话号码");
            Toast.makeText(RecommendActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(phoneNum.length()!=11)
            {
                LogUtils.i(TAG, "请输入有效的电话号码");
                toast.show("请输入有效的电话号码");
                return;
            }
        }
        //判断担保金额
        if(CommUtil.isBlank(money))
        {
            LogUtils.i(TAG, "请输入推荐金额");
            toast.show("请输入推荐金额");
            return;
        }
        else
        {
            if(Integer.valueOf(money)<10)
            {
                LogUtils.i(TAG, "推荐金额必须大于10");
                toast.show("推荐金额必须大于10");
                return;
            }
        }
        getUserInfo.validateRecommend(dwID, phoneNum, vHandler);
    }
    

    Handler vHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 2222:
                    Intent intent = new Intent(RecommendActivity.this , RechargePayMethodActivity_.class);
                    intent.putExtra("isRecommend", true);
                    intent.putExtra("phoneNum", phoneNum);
                    intent.putExtra("amount", money);
                    startActivity(intent);
                    break;
                case 3333:
                    final ReminderDialog dialog = new ReminderDialog();
//                    dialog.setInfo("钱汇到哪个账户\n支付宝or微信？");
                    dialog.setInfo("请选择\n微信收钱 或 支付宝收钱");
                    dialog.setButtonText("去选择");
                    dialog.setListener(new ReminderListener()
                    {
                        @Override
                        public void confirm()
                        {
                            /** 跳到绑定界面 **/
                            Intent intent = new Intent(RecommendActivity.this, RechargeBenefitActivity_.class);
                            intent.putExtra("isRecommend", true);
                            startActivity(intent);
                            dialog.dismiss();
                            LogUtils.i(TAG, "");
                        }
                    });
                    dialog.show(getSupportFragmentManager(), "");
                    break;
                default:
                    break;
            }
        };
    };

}
