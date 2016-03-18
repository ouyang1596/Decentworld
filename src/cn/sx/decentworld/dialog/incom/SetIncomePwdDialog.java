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
 * @Description: 设置收益独立密码
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class SetIncomePwdDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "SetIncomePwdDialog";
    //密码
    private EditText etPwd;
    //确认
    private Button btnSubmit;
    private SetIncomePwdListener listener;
    
    public void setListener(SetIncomePwdListener listener)
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
        View view = inflater.inflate(R.layout.dialog_set_income_pwd, container, false);
        etPwd = (EditText) view.findViewById(R.id.et_set_income_pwd);
        btnSubmit = (Button) view.findViewById(R.id.btn_set_income_pwd_confirm);
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
            case R.id.btn_set_income_pwd_confirm:
                //判断密码
                String pwd = etPwd.getText().toString();
                if(pwd.length()<6)
                {
                    toastInfo("密码必须大于6位");
                }else
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
    public interface SetIncomePwdListener
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
