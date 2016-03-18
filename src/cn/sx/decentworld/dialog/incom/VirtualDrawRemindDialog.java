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
 * @Description: 虚拟币提现提示对话框
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class VirtualDrawRemindDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "VirtualDrawRemindDialog";
    private String realname;
    private String bankcardName;
    private String account;
    
    private TextView tvRealname;
    private TextView tvBankcardName;
    private TextView tvAccount;
    private Button btnSubmit;
    // 监听
    private VirtualDrawRemindListener listener;

    /**
     * 设置监听
     * 
     * @param listener
     */
    public void setListener(VirtualDrawRemindListener listener)
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
        View view = inflater.inflate(R.layout.dialog_virtual_draw_remind, container, false);
        tvRealname = (TextView) view.findViewById(R.id.tv_virtual_draw_remind_realname);
        tvBankcardName = (TextView) view.findViewById(R.id.tv_virtual_draw_remind_cardname);
        tvAccount = (TextView) view.findViewById(R.id.tv_virtual_draw_remind_account);
        btnSubmit = (Button) view.findViewById(R.id.btn_virtual_draw_remind_submit);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
       
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_virtual_draw_remind_submit:
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
    public interface VirtualDrawRemindListener
    {
        // 确定
        public void onSubmit();
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
