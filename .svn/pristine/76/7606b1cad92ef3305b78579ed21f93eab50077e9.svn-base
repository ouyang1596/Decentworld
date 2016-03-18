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
 * @Description: 虚拟币提现对话框
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class VirtualDrawDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "VirtualDrawDialog";
    private EditText etAmount;
    private Button btnSubmit;
    // 监听
    private VirtualDrawListener listener;

    /**
     * 设置监听
     * 
     * @param listener
     */
    public void setListener(VirtualDrawListener listener)
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
        View view = inflater.inflate(R.layout.dialog_virtual_draw, container, false);
        etAmount = (EditText) view.findViewById(R.id.et_virtual_draw_amount);
        btnSubmit = (Button) view.findViewById(R.id.btn_virtual_draw_submit);
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
            case R.id.btn_virtual_draw_submit:
                String str = etAmount.getText().toString();
                if(CommUtil.isBlank(str))
                {
                    toastInfo("请输入虚拟币数额");
                }
                else
                {
                    float mAmount = Float.valueOf(str);
                    if(mAmount<100)
                    {
                        toastInfo("提现不少于100");
                    }else
                    {
                        listener.onSubmit(mAmount);
                        dismiss();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 监听接口
     */
    public interface VirtualDrawListener
    {
        // 确定
        public void onSubmit(float amount);
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
