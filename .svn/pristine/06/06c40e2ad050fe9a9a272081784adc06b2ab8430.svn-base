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
 * @Description: 设置支付宝账号
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class SetAlipayAccountDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "SetAlipayAccountDialog";
    //密码
    private EditText etAccount;
    //确认
    private Button btnSubmit;
    private SetAlipayAccountListener listener;
    
    public void setListener(SetAlipayAccountListener listener)
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
        View view = inflater.inflate(R.layout.dialog_set_alipay_account, container, false);
        etAccount = (EditText) view.findViewById(R.id.et_set_alipay_account);
        btnSubmit = (Button) view.findViewById(R.id.btn_set_alipay_account_submit);
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
            case R.id.btn_set_alipay_account_submit:
                //判断密码
                String pwd = etAccount.getText().toString();
                if(CommUtil.isNotBlank(pwd))
                {
                    listener.onSubmit(pwd);
                    dismiss();
                }else
                {
                    toastInfo("账号不能为空");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 监听接口
     */
    public interface SetAlipayAccountListener
    {
        public void onSubmit(String pwd);
    }
    
    /**
     * 提示信息
     * @param info
     */
    public void toastInfo(String info)
    {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }

}
