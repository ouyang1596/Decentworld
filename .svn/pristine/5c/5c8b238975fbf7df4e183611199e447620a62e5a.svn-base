/**
 * 
 */
package cn.sx.decentworld.dialog.incom;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * 
 * @ClassName: SetIncomePwdDialog.java
 * @Description: 推荐收益提现对话框
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class CashDrawDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "CashDrawDialog";
    //金额
    private float mAmount;
    //账号类型
    private int mAccountType;
    //账号
    private String mAccount;
    
    
    private TextView tvAmount;
    private TextView tvAccountType;
    private TextView tvAccount;
    private Button btnSubmit;
    private EditText etPassword;
    // 监听
    private CashDrawListener listener;

    /**
     * 设置监听
     * 
     * @param listener
     */
    public void setListener(CashDrawListener listener)
    {
        this.listener = listener;
    }
    
    /**
     * 提现到支付宝
     * @param amount
     * @param account
     */
    public void setAlipay(float amount,String account)
    {
        this.mAmount = amount;
        this.mAccountType = 0;
        this.mAccount = account;
    }
    
    /**
     * 提现到微信
     * @param amount
     */
    public void setWx(float amount)
    {
        this.mAmount = amount;
        this.mAccountType = 1;
        this.mAccount = "";
    }

   

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity() , R.style.ShouchangDialog);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_cash_draw, container, false);
        tvAmount = (TextView) view.findViewById(R.id.tv_cash_draw_amount);
        tvAccountType = (TextView) view.findViewById(R.id.tv_cash_draw_account_type);
        tvAccount = (TextView) view.findViewById(R.id.tv_cash_draw_account);
        btnSubmit = (Button) view.findViewById(R.id.btn_cash_draw_submit);
        btnSubmit.setOnClickListener(this);
        etPassword = (EditText) view.findViewById(R.id.et_cash_draw_password);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // 初始化 金额
        if (CommUtil.isBlank(mAmount))
            tvAmount.setText("0.0");
        else
            tvAmount.setText(mAmount+"");
        // 初始化账号类型
        if(mAccountType == 0)
        {
            tvAccountType.setText("支付宝账号");
            tvAccount.setText(mAccount);
        }
        else
        {
            tvAccountType.setText("微信收钱");
            tvAccount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_cash_draw_submit:
                String password = etPassword.getText().toString();
                if(CommUtil.isBlank(password))
                {
                    Toast.makeText(getActivity(), "请输入提现密码", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    listener.onSubmit(mAmount,mAccountType,password);
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 监听接口
     */
    public interface CashDrawListener
    {
        // 确定
        public void onSubmit(float amount,int accountType,String password);
    }

}
