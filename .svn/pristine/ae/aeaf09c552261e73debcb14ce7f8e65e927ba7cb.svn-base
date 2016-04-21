/**
 * 
 */
package cn.sx.decentworld.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.sx.decentworld.R;

/**
 * 
 * @ClassName: SetIncomePwdDialog.java
 * @Description: 验证收益独立密码错误
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class VerifyBenefitPwdErrorDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "VerifyIncomePwdErrorDialog";
    // 重试
    private TextView tvReset;
    // 忘记密码
    private TextView tvForget;
    private VerifyIncomePwdErrorListener listener;

    /**
     * 设置监听
     * @param listener
     */
    public void setListener(VerifyIncomePwdErrorListener listener)
    {
        this.listener = listener;
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
        View view = inflater.inflate(R.layout.dialog_verify_benefit_pwd_error, container, false);
        tvReset = (TextView) view.findViewById(R.id.tv_verify_benefit_pwd_error_reset);
        tvForget = (TextView) view.findViewById(R.id.tv_verify_benefit_pwd_error_forget);
        tvReset.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        /** 设置提示信息 **/

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_verify_benefit_pwd_error_reset:
                listener.onReset();

                break;
            case R.id.tv_verify_benefit_pwd_error_forget:
                listener.onForget();
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * 监听接口
     */
    public interface VerifyIncomePwdErrorListener
    {
        // 重试
        public void onReset();

        // 忘记密码
        public void onForget();
    }

}
