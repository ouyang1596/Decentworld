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
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;

/**
 * 
 * @ClassName: SetIncomePwdDialog.java
 * @Description: 设置收益独立密码
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class SetBenefitPwdDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "SetIncomePwdDialog";
    //密码
    private EditText etPwd;
    private EditText etPwd1;
    //确认
    private Button btnSubmit;
    private SetBenefitPwdListener listener;
    
    public void setListener(SetBenefitPwdListener listener)
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
        View view = inflater.inflate(R.layout.dialog_set_benefit_pwd, container, false);
        etPwd = (EditText) view.findViewById(R.id.et_set_benefit_pwd);
        etPwd1 = (EditText) view.findViewById(R.id.et_set_benefit_pwd1);
        btnSubmit = (Button) view.findViewById(R.id.btn_belong_dialog_submit);
        btnSubmit.setOnClickListener(this);
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
            case R.id.btn_belong_dialog_submit:
                //判断密码
                String pwd = etPwd.getText().toString();
                String pwd1 = etPwd1.getText().toString();
                if(CommUtil.isBlank(pwd))
                {
                    toastInfo("密码不能为空");
                }else if(pwd.length()<6)
                {
                    toastInfo("密码必须为6位数字");
                }else if(CommUtil.isBlank(pwd1))
                {
                    toastInfo("请确认密码");
                }
                else if(!pwd.equals(pwd1))
                {
                    toastInfo("两次输入的密码不相同");
                }
                else
                {
                    listener.onSubmit(pwd);
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
    public interface SetBenefitPwdListener
    {
        public void onSubmit(String password);
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
