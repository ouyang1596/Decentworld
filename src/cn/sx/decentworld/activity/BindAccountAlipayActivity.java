/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.EditCommon1Activity.CommonAdapter;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;
import cn.sx.decentworld.wxapi.WXEntryActivity;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: BindAccountAlipayActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年12月23日 下午7:29:40
 */
@EActivity(R.layout.activity_bind_account_alipay)
public class BindAccountAlipayActivity extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "BindAccountAlipayActivity";
    @Bean
    ToastComponent toast;
    @ViewById(R.id.et_bind_account_alipay)
    EditText et_bind_account_alipay;

    @ViewById(R.id.btn_bind_account_confirm)
    Button btn_bind_account_confirm;
    
    @ViewById(R.id.ll_band_alipay_back)
    LinearLayout ll_band_alipay_back;

    private int accountType = 3;
    private String accountName = "";

    @AfterViews
    void init()
    {
        accountType = getIntent().getIntExtra("accountType", 3);
        accountName = getIntent().getStringExtra("accountName");
        et_bind_account_alipay.setText(accountName);
        ll_band_alipay_back.setOnClickListener(this);
    }

    /**
     * 确定
     * 
     * @param v
     */
    @Click(R.id.btn_bind_account_confirm)
    void confirm(View v)
    {

        String result = et_bind_account_alipay.getText().toString().trim();
        if (CommUtil.isNotBlank(result))
        {
            int accountType = 0;
            String dwID = DecentWorldApp.getInstance().getDwID();
            setPaycardAccount(dwID, accountType, result);
        }
        else
        {
            toast.show("账号为空");
        }
    }

    /**
     * 设置用户提现的账号（支付宝或微信）
     * 
     * @param dwID
     *            自己的id
     * @param accountType
     *            账号类型（支付宝 0 | 微信 1）
     * @param account
     *            账号
     * @param activity
     */
    public void setPaycardAccount(String dwID, final int accountType, final String account)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("accountType", String.valueOf(accountType));
        map.put("account", account);
        LogUtils.i(TAG, "setPaycardAccount...begin,dwID=" + dwID + ",accountType=" + accountType + ",account=" + account);
        new SendUrl(this).httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/setAccount", Method.POST, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                // 设置成功 设置失败（原因）
                LogUtils.i(TAG, "setPaycardAccount...msg.getResultCode=" + msg.getResultCode() + ",msg.getMsg=" + msg.getMsg() + ",msg.getData=" + msg.getData());
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "setPaycardAccount...success");
                    Intent intent = new Intent();
                    intent.putExtra("accountType", accountType);
                    intent.putExtra("accountName", account);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if (msg.getResultCode() == 3333)
                {
                    LogUtils.i(TAG, "setPaycardAccount...failure,case by:" + msg.getMsg());
                    ToastUtils.toast(BindAccountAlipayActivity.this, "设置失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "setPaycardAccount...onFailure,cause by:" + e);
                ToastUtils.toast(BindAccountAlipayActivity.this, Constants.NET_WRONG);
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_band_alipay_back:
                finish();
                break;
            default:
                break;
        }
        
    }
}
