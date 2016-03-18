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
 * @Description: 找回收益独立密码
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class RetrieveIncomePwdDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "RetrieveIncomePwdDialog";
    // 确定找回密码
    private Button btnSubmit;

    private RetrieveIncomePwdListener listener;

    /**
     * 设置监听
     * @param listener
     */
    public void setListener(RetrieveIncomePwdListener listener)
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
        View view = inflater.inflate(R.layout.dialog_retrieve_income_pwd, container, false);
        btnSubmit = (Button) view.findViewById(R.id.btn_retrieve_income_pwd_submit);
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
            case R.id.btn_retrieve_income_pwd_submit:
                listener.onSubmit();
                dismiss();
                break;
            default:
                break;
        }

    }

    /**
     * 监听接口
     */
    public interface RetrieveIncomePwdListener
    {
        // 确定找回密码
        public void onSubmit();
    }

}
