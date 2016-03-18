/**
 * 
 */
package cn.sx.decentworld.fragment.benefit;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BindAccountAlipayActivity_;
import cn.sx.decentworld.activity.RechargePayMethodActivity;
import cn.sx.decentworld.adapter.RecommendBenefitListAdapter;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.RecommendBenefitList;
import cn.sx.decentworld.bean.UserExtraInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.EditAndModificationDialog;
import cn.sx.decentworld.dialog.EditAndModificationDialog.EditAndModificationListener;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.dialog.incom.CashDrawDialog;
import cn.sx.decentworld.dialog.incom.CashDrawDialog.CashDrawListener;
import cn.sx.decentworld.dialog.incom.IncomeRemindDialog;
import cn.sx.decentworld.dialog.incom.IncomeRemindDialog.IncomeRemindListener;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.request.SetUserInfo;
import cn.sx.decentworld.utils.AES;
import cn.sx.decentworld.utils.DataConvertUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.widget.ListViewForScrollView;
import cn.sx.decentworld.wxapi.WXEntryActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: CashBenefitFragment.java
 * @Description: 现金收益界面
 * @author: cj
 * @date: 2016年1月14日 上午11:01:28
 */
@EFragment(R.layout.fragment_cash_benefit)
public class CashBenefitFragment extends BaseFragment implements OnClickListener
{
    /**
     * 常量
     */
    private static final String TAG = "CashBenefitFragment";
    /** 绑定的账号类型 **/
    private static final int ALIPAY = RechargePayMethodActivity.ALIPAY;// 支付宝
    private static final int WX = RechargePayMethodActivity.WX;// 微信
    private static final int NO = 2;// 没有绑定
    /** 请求码 **/
    private static final int GET_RECOMMEND_BENEFIT_LIST = 3;// 获得推荐收益列表
    private static final int GET_ADVANCE_AUTH = 4;// 获取用户是否自动转账的开关权限
    private static final int GET_USER_ACCOUNT = 5;// 获取用户已经绑定的账号
    private static final int RECOM_DRAW = 6;// 推荐收益提现
    private static final int SET_RECOM_BENEFIT_ACCOUNT = 7;// 设置推荐收益账号

    /**
     * 工具类
     */
    @Bean
    GetUserInfo getUserInfo;
    @Bean
    SetUserInfo serUserInfo;
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    /** 可预期总额 **/
    @ViewById(R.id.tv_cash_benefit_total_big)
    TextView tv_cash_benefit_total_big;
    @ViewById(R.id.tv_cash_benefit_total_small)
    TextView tv_cash_benefit_total_small;
    /** 历史返现 **/
    @ViewById(R.id.tv_cash_benefit_back_big)
    TextView tv_cash_benefit_back_big;
    @ViewById(R.id.tv_cash_benefit_back_small)
    TextView tv_cash_benefit_back_small;
    /** 未提零钱 **/
    @ViewById(R.id.tv_cash_benefit_not_big)
    TextView tv_cash_benefit_not_big;
    @ViewById(R.id.tv_cash_benefit_not_small)
    TextView tv_cash_benefit_not_small;

    /** 提现按钮 **/
    @ViewById(R.id.rl_cash_benefit_draw)
    RelativeLayout rl_cash_benefit_draw;
    /** 可提现的零钱 **/
    @ViewById(R.id.tv_cash_benefit_not_draw)
    TextView tv_cash_benefit_not_draw;

    /** 变为手动提取按钮 **/
    @ViewById(R.id.tv_cash_benefit_autoTransfer)
    TextView tv_cash_benefit_autoTransfer;
    /** 绑定支付宝按钮 **/
    @ViewById(R.id.tv_cash_benefit_alipay)
    TextView tv_cash_benefit_alipay;
    /** 绑定微信按钮 **/
    @ViewById(R.id.tv_cash_benefit_wx)
    TextView tv_cash_benefit_wx;

    /** 推荐收益账单 **/
    @ViewById(R.id.lv_cash_benefit)
    ListViewForScrollView listView;

    /** 没有任何推荐人的提示 **/
    @ViewById(R.id.ll_cash_benefit_remind)
    LinearLayout ll_cash_benefit_remind;

    /**
     * 变量
     */
    private String userID = "";
    private boolean isPrepared = false;
    private boolean isAutoTransfer = false;
    private int accountType = NO;// 绑定的账号类型
    private String accountName = "";// 账号名字
    private List<RecommendBenefitList> listData;
    private RecommendBenefitListAdapter listAdapter;

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
        listData = new ArrayList<RecommendBenefitList>();
        listAdapter = new RecommendBenefitListAdapter(getActivity() , listData);
        listView.setAdapter(listAdapter);
    }

    /**
     * 初始化监听
     */
    private void initListener()
    {
        rl_cash_benefit_draw.setOnClickListener(this);
        tv_cash_benefit_autoTransfer.setOnClickListener(this);
        tv_cash_benefit_alipay.setOnClickListener(this);
        tv_cash_benefit_wx.setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    @Override
    protected void lazyLoad()
    {
        if (isPrepared)
        {
            if (NetworkUtils.isNetWorkConnected(getActivity()))
            {
                /** 有网络 从服务器获取推荐收益信息 **/
                getUserInfo.getRecommendBenefitList(userID, handler, GET_RECOMMEND_BENEFIT_LIST);
                /** 有网络 从服务器获取是否自动转账 **/
                getUserInfo.getAutoAuthority(userID, handler, GET_ADVANCE_AUTH);
                /** 有网络 从服务器获取绑定的账号信息 **/
                getUserInfo.getUserAccount(userID, handler, GET_USER_ACCOUNT);
            }
            else
            {
                UserExtraInfo extraInfo = UserExtraInfo.queryBy(DecentWorldApp.getInstance().getDwID());
                /** 没有网络 从本地获取推荐收益信息 **/
                listData.clear();
                listData = RecommendBenefitList.queryBy(userID);
                updateUiWithRec(listData, extraInfo);

                /** 没有网络 从本地获取是否自动转账 **/
                if (extraInfo != null)
                {
                    if (extraInfo.getAutoTransfer() != null)
                    {
                        updateUiWithAuth(extraInfo.getAutoTransfer());
                    }
                    else
                    {
                        updateUiWithAuth(true);
                    }
                }
                /** 没有网络 从本地获取绑定的账号信息 **/
                if (extraInfo != null)
                {
                    int t_accountType = extraInfo.getAccountType();
                    String t_accountName = extraInfo.getAccountName();
                    if (t_accountType != -1 && CommUtil.isNotBlank(t_accountName))
                    {
                        accountType = t_accountType;
                        accountName = t_accountName;
                    }
                }
                updateUiWithAccount();
            }
        }
    }

    /**
     * 请求回调
     */
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case GET_RECOMMEND_BENEFIT_LIST:
                    processBenefitListResult(msg);
                    break;
                case GET_ADVANCE_AUTH:
                    processAutoTransferResult(msg);
                    break;
                case GET_USER_ACCOUNT:
                    processAccountResult(msg);
                    break;
                case RECOM_DRAW:
                    processRecomDraw(msg);
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * 处理从服务器获得的推荐收益结果
     * 
     * @param msg
     */
    private void processBenefitListResult(Message msg)
    {
        /** 清理历史数据 **/
        listData.clear();
        RecommendBenefitList.deleteBy(userID);
        /** 获取数据、定义变量 **/
        String result = msg.obj.toString();
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("recommendList");

        /** 解析数据 **/
        if (jsonArray.size() > 0)
        {
            for (int i = 0; i < jsonArray.size(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                RecommendBenefitList temp = new RecommendBenefitList();
                float amount = object.getFloatValue("amount");
                temp.setAmount(amount * amount);
                float benefit = object.getFloatValue("benefit");
                temp.setBenefit(benefit);
                temp.setName(object.getString("name"));
                temp.setPhoneNum(object.getString("phoneNum"));
                float stored = object.getFloatValue("stored");
                temp.setStored(stored);
                temp.setUserID(userID);
                temp.setOtherID(object.getString("dwID"));
                temp.setStatus(object.getIntValue("status"));
                temp.save();
                listData.add(temp);
            }
        }

        UserExtraInfo extraInfo = UserExtraInfo.queryBy(userID);
        if (extraInfo == null)
        {
            extraInfo = new UserExtraInfo();
        }
        float totalBenefit = jsonObject.getFloatValue("totalBenefit");
        extraInfo.setRecomTotalBenefit(totalBenefit);
        float storedBenefit = jsonObject.getFloatValue("storedBenefit");
        extraInfo.setRecomStoredBenefit(storedBenefit);
        extraInfo.save();

        updateUiWithRec(listData, extraInfo);
    }

    /**
     * 获得推荐收益数据后更新界面
     * 
     * @param listData
     */
    private void updateUiWithRec(List<RecommendBenefitList> listData, UserExtraInfo extraInfo)
    {
        if (listData.size() > 0)
        {
            float allBenefit = 0;// 应得总金额
            for (RecommendBenefitList temp : listData)
            {
                if (temp.getStatus() != RecommendBenefitList.status_DELETED)
                {
                    allBenefit += temp.getAmount();
                }
            }
            listAdapter.notifyDataSetChanged();
            // 计算已经获得的收益和未提现的收益并显示
            String[] total = DataConvertUtils.splitFloat(allBenefit);
            tv_cash_benefit_total_big.setText(total[0]);
            tv_cash_benefit_total_small.setText(total[1]);

            listView.setVisibility(View.VISIBLE);
            ll_cash_benefit_remind.setVisibility(View.GONE);
        }
        else
        {
            LogUtils.i(TAG, "你还没有推荐过任何人哦！");
            listView.setVisibility(View.GONE);
            ll_cash_benefit_remind.setVisibility(View.VISIBLE);
        }

        /** 初始化历史总收益 **/
        if (extraInfo != null)
        {
            float totalBenefit = extraInfo.getRecomTotalBenefit();
            float storedBenefit = extraInfo.getRecomStoredBenefit();

            String[] back = DataConvertUtils.splitFloat(totalBenefit);
            tv_cash_benefit_back_big.setText(back[0]);
            tv_cash_benefit_back_small.setText(back[1]);

            String[] not = DataConvertUtils.splitFloat(storedBenefit);
            tv_cash_benefit_not_big.setText(not[0]);
            tv_cash_benefit_not_small.setText(not[1]);
            /** 显示处理，则保留2位小数 **/
            tv_cash_benefit_not_draw.setText(DataConvertUtils.formatFloat(storedBenefit));
        }
    }

    /**
     * 处理获取的自动转账权限的结果
     * 
     * @param msg
     */
    private void processAutoTransferResult(Message msg)
    {
        if (msg.arg1 == 0)
        {
            /** 失败 **/
            getAdvanceAuthFailure();

        }
        else if (msg.arg1 == 1)
        {
            /** 成功 **/
            getAdvanceAuthSuccess(msg);
        }
    }

    /**
     * 从服务器获取高级设置开关权限失败
     */
    private void getAdvanceAuthFailure()
    {
        isAutoTransfer = false;
    }

    /**
     * 从服务器获取高级设置开关权限成功
     * 
     * @param msg
     */
    private void getAdvanceAuthSuccess(Message msg)
    {
        JSONObject jObject = JSON.parseObject(msg.obj.toString());
        UserExtraInfo info = UserExtraInfo.queryBy(userID);
        if (info == null)
        {
            info = new UserExtraInfo();
        }

        Boolean autoTransfer = jObject.getBooleanValue("autoTransfer");
        if (autoTransfer != null)
        {
            isAutoTransfer = autoTransfer;
            info.setAutoTransfer(autoTransfer);
            info.save();
            updateUiWithAuth(autoTransfer);
        }
    }

    /**
     * 获取权限数据后更新界面
     */
    private void updateUiWithAuth(boolean isAutoTransfer)
    {
        if (isAutoTransfer)
        {
            /** 蓝色代表开 **/
            tv_cash_benefit_autoTransfer.setBackgroundResource(R.drawable.rb_btn_bg_open);
            // tv_cash_benefit_autoTransfer.setText("关闭自动提现");
        }
        else
        {
            /** 白色代表关 **/
            tv_cash_benefit_autoTransfer.setBackgroundResource(R.drawable.rb_btn_bg_close);
            // tv_cash_benefit_autoTransfer.setText("开启自动提取");
        }
    }

    /**
     * 处理从服务器获取的推荐返利账号结果
     * 
     * @param msg
     */
    private void processAccountResult(Message msg)
    {
        String result = msg.obj.toString();
        JSONObject object = JSON.parseObject(result);
        int type = object.getIntValue("type");
        String account = object.getString("account");
        LogUtils.i(TAG, "type=" + type + "，account = " + account);
        UserExtraInfo extraInfo = UserExtraInfo.queryBy(DecentWorldApp.getInstance().getDwID());
        if (extraInfo == null)
            extraInfo = new UserExtraInfo();
        accountType = type;
        extraInfo.setAccountType(type);
        if (CommUtil.isNotBlank(account))
        {
            accountName = account;
            extraInfo.setAccountName(account);
        }
        else
        {
            accountName = "";
            extraInfo.setAccountName(account);
        }
        extraInfo.save();
        updateUiWithAccount();
    }

    /**
     * 获取到账号信息后处理界面
     */
    private void updateUiWithAccount()
    {
        if (accountType == NO)
        {
            tv_cash_benefit_wx.setBackgroundResource(R.drawable.rb_btn_bg_close);
            tv_cash_benefit_alipay.setBackgroundResource(R.drawable.rb_btn_bg_close);
        }
        else if (accountType == ALIPAY)
        {
            tv_cash_benefit_wx.setBackgroundResource(R.drawable.rb_btn_bg_close);
            tv_cash_benefit_alipay.setBackgroundResource(R.drawable.rb_btn_bg_open);

        }
        else if (accountType == WX)
        {
            tv_cash_benefit_wx.setBackgroundResource(R.drawable.rb_btn_bg_open);
            tv_cash_benefit_alipay.setBackgroundResource(R.drawable.rb_btn_bg_close);

        }
    }

    /**
     * 
     * 处理推荐收益提现，提现成功，将界面的"未提零钱"/"提取"显示数目置为零
     * 
     * @param msg
     */
    protected void processRecomDraw(Message msg)
    {
        float notTakeout = 0.0f;
        String[] not = DataConvertUtils.splitFloat(notTakeout);
        tv_cash_benefit_not_big.setText(not[0]);
        tv_cash_benefit_not_small.setText(not[1]);
        tv_cash_benefit_not_draw.setText("0.00");
        getUserInfo.getRecommendBenefitList(userID, handler, GET_RECOMMEND_BENEFIT_LIST);
    }

    @Override
    public void turnToTab(int tab)
    {
        // TODO Auto-generated method stub

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rl_cash_benefit_draw:
                /** 提现 **/
                drawCash(v);
                break;
            case R.id.tv_cash_benefit_autoTransfer:
                /** 自动提现与手动提现切换 **/
                isAutoTransfer(isAutoTransfer);
                break;
            case R.id.tv_cash_benefit_alipay:
                /** 绑定支付宝 **/
                bindAlipay(v);
                break;
            case R.id.tv_cash_benefit_wx:
                /** 绑定微信 **/
                bindWx(v);
                break;
            default:
                break;
        }
    }

    /**
     * 提现
     * 
     * @param v
     */
    private void drawCash(View v)
    {
//        ReminderDialog dialog = new ReminderDialog();
//        dialog.setInfo("确认提取?");
//        dialog.setButtonText("提取");
//        dialog.setListener(new ReminderListener()
//        {
//            @Override
//            public void confirm()
//            {
//                getUserInfo.extraRecomBenefit(userID, DecentWorldApp.getInstance().getDwID(), handler, RECOM_DRAW);
//            }
//        });
//        dialog.show(getFragmentManager(), "drawCash");

        if(accountType == NO || accountType == -1)
        {
            toast.show("你还没有绑定账号");
        }
        else
        {
            CashDrawDialog cashDrawDialog = new CashDrawDialog();
            String amount = tv_cash_benefit_not_draw.getText().toString();
            if(accountType == ALIPAY)
            {
                cashDrawDialog.setAlipay(Float.valueOf(amount),"" );
            }
            else
            {
                cashDrawDialog.setWx(Float.valueOf(amount));
            }
            cashDrawDialog.setListener(new CashDrawListener()
            {
                @Override
                public void onSubmit(float amount, int accountType, String password)
                {
                    getUserInfo.extraRecomBenefit(userID, password, handler, RECOM_DRAW);
                    
                }
            });
            cashDrawDialog.show(getFragmentManager(), "");
        }
    }

    /**
     * 点击自动提现开关时提示
     * 
     * @param isAutoTransfer
     */
    private void isAutoTransfer(boolean isAutoTransfer1)
    {
        /** 此开关仅对微信有效，关闭时提示“关闭开关后，需要手动提现”,打开时提示“打开开关后，需要自己缴纳手续费” **/
        String info = "";
        if (isAutoTransfer1)
            info = "你的选择很好，这样手续费最低！原因：...";// 手动提现的好处
        else
            info = "你的选择很对，这样最方便！但是：小于2元时。。微信。。支付宝。。。；大于2时。。微信。。支付宝。。由此我们帮你避免。。。";// 自动提现的好处
        
        IncomeRemindDialog dialog = new IncomeRemindDialog();
        dialog.setTitle("设置返现状态");
        dialog.setRemindInfo(info);
        dialog.setIsShowEditText(true);
        dialog.setListener(new IncomeRemindListener()
        {
            @Override
            public void onSubmit(boolean hasResult,String password)
            {
                if(hasResult)
                    setAutoTransferSwitch(password);
            }
        });
        dialog.show(getFragmentManager(), "setAutoTransferSwitch");
    }

    /**
     * 切换自动提现开关的状态
     */
    private void setAutoTransferSwitch(String password)
    {
        password = AES.encodeWithRandomStr(password);
        new BenefitEngine().setAutoTransfer(userID, isAutoTransfer,password, new NetCallback()
        {
            @Override
            public void onSuccess(String msg)
            {
                setAutoHandler.sendEmptyMessage(1);
            }
            
            @Override
            public void onFailure(String cause)
            {
                setAutoHandler.sendEmptyMessage(0);
            }
        });
    }
    
    
    Handler setAutoHandler = new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    //设置失败
                    toast.show("设置失败");
                    break;
                case 1:
                    //设置成功
                    if (isAutoTransfer)
                    {
                        /** 设置为关闭 **/
                        tv_cash_benefit_autoTransfer.setBackgroundResource(R.drawable.rb_btn_bg_close);
                        tv_cash_benefit_autoTransfer.setText("手动返现");
                        isAutoTransfer = false;
                    }
                    else
                    {
                        /** 设置为开 **/
                        tv_cash_benefit_autoTransfer.setBackgroundResource(R.drawable.rb_btn_bg_open);
                        tv_cash_benefit_autoTransfer.setText("自动返现");
                        isAutoTransfer = true;
                    }
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * 处理绑定微信的点击事件
     * 
     * @param v
     */
    private void bindWx(View v)
    {
        /** 无网络 **/
        if (!NetworkUtils.isNetWorkConnected(getActivity()))
        {
            toast.show("请检查网络");
            return;
        }
        /** 有网络 **/
        if (accountType != ALIPAY)
        {
            EditAndModificationDialog dialog2 = new EditAndModificationDialog();
            dialog2.setTitle("选择微信收钱");
            dialog2.setHint("请输入登录密码验证");
            dialog2.setListener(new EditAndModificationListener()
            {
                @Override
                public void confirm(String info)
                {
//                    if(DecentWorldApp.getInstance().getPassword().equals(AES.encode(info)))
//                    {
//                        // 跳转
//                        
//                    }
//                    else
//                    {
//                        toast.show("密码错误");
//                    }
                    setWx(0,info);
                }
            });
            dialog2.show(getFragmentManager(), "wx");
        }
        else
        {
            EditAndModificationDialog dialog2 = new EditAndModificationDialog();
            dialog2.setTitle("你当前为支付宝收钱，\n确定修改吗？");
            dialog2.setHint("请输入登录密码验证");
            dialog2.setListener(new EditAndModificationListener()
            {
                @Override
                public void confirm(String info)
                {
//                    if(DecentWorldApp.getInstance().getPassword().equals(AES.encode(info)))
//                    {
//                        
//                    }
//                    else
//                    {
//                        toast.show("密码错误");
//                    }
                    setWx(1,info);
                }
            });
            dialog2.show(getFragmentManager(), "wx");
        }
    }

    /**
     * 跳转到绑定微信账号的界面
     */
    private void setWx(int type,String password)
    {
        Intent intent = new Intent(getActivity() , WXEntryActivity.class);
        intent.putExtra("accountType", WX);
        if (type == 0)
            intent.putExtra("accountName", accountName);
        else
            intent.putExtra("accountName", "");
        intent.putExtra("password", password);
        startActivityForResult(intent, SET_RECOM_BENEFIT_ACCOUNT);
    }

    /**
     * 处理绑定支付宝的点击事件
     * 
     * @param v
     */
    private void bindAlipay(View v)
    {
        /** 无网络 **/
        if (!NetworkUtils.isNetWorkConnected(getActivity()))
        {
            toast.show("请检查网络");
            return;
        }
        /** 有网络 **/
        if (accountType != WX)
        {
            EditAndModificationDialog dialog2 = new EditAndModificationDialog();
            dialog2.setTitle("选择支付宝收钱");
            dialog2.setHint("请输入登录密码");
            dialog2.setListener(new EditAndModificationListener()
            {
                @Override
                public void confirm(String info)
                {
//                    if(DecentWorldApp.getInstance().getPassword().equals(AES.encode(info)))
//                    {
//                        // 跳转
//                        setAlipay(0);
//                    }
//                    else
//                    {
//                        toast.show("密码错误");
//                    }
                    
                    setAlipay(0,info);
                    
                }
            });
            dialog2.show(getFragmentManager(), "alipay");
           
        }
        else
        {
            EditAndModificationDialog dialog2 = new EditAndModificationDialog();
            dialog2.setTitle("你当前为微信收钱，\n确定修改吗？");
            dialog2.setHint("请输入登录密码");
            dialog2.setListener(new EditAndModificationListener()
            {
                @Override
                public void confirm(String info)
                {
//                    if(DecentWorldApp.getInstance().getPassword().equals(AES.encode(info)))
//                    {
//                        setAlipay(1);
//                    }
//                    else
//                    {
//                        toast.show("密码错误");
//                    }
                    
                    setAlipay(1,info);
                    
                }
            });
            dialog2.show(getFragmentManager(), "alipay");
        }
    }

    /**
     * 跳转到设置支付宝账号界面
     */
    private void setAlipay(int type,String password)
    {
        Intent intent = new Intent(getActivity() , BindAccountAlipayActivity_.class);
        intent.putExtra("accountType", ALIPAY);
        if (type == 0)
        {
            intent.putExtra("accountName", accountName);
        }
        else
            intent.putExtra("accountName", "");
        intent.putExtra("password", password);
        startActivityForResult(intent, SET_RECOM_BENEFIT_ACCOUNT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult...resultCode=" + resultCode + ",requestCode=" + requestCode);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SET_RECOM_BENEFIT_ACCOUNT)
            {
                if (data.getIntExtra("accountType", 3) != 3)
                {
                    accountType = data.getIntExtra("accountType", 3);
                    accountName = data.getStringExtra("accountName");
                    updateUiWithAccount();
                    LogUtils.i(TAG, "进入onNewIntent，并重新修改数据，accountType=" + accountType + ",accountName=" + accountName);
                }
            }
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 绑定微信成功后更新界面；
     * 
     * @param tag
     */
    @Subscriber(tag = NotifyByEventBus.NT_BAND_WX_SUCC)
    public void updateUIAccount(String tag)
    {
        LogUtils.i(TAG, "updateUIAccount," + tag);
        accountType = WX;
        accountName = "";
        updateUiWithAccount();
    }

}
