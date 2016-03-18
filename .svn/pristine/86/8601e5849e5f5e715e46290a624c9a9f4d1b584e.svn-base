/**
 * 
 */
package cn.sx.decentworld.dialog.incom;

import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;

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
 * @Description: 收益模块提示对话框
 * @author: cj
 * @date: 2016年3月17日 上午9:51:24
 */
public class IncomeRemindDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "IncomeRemindDialog";

    private String mTitle;
    private String mRemindInfo;
    // 标题
    private TextView tvTitle;
    // 提示信息
    private TextView tvRemindInfo;
    // 确认
    private Button btnSubmit;
    private EditText etPassword;
    // 监听
    private IncomeRemindListener listener;
    private boolean isShow = false;
    
    /**
     * 
     */
    public IncomeRemindDialog()
    {
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * 设置是否显示编辑框
     */
    public void setIsShowEditText(boolean isShowEditText)
    {
        this.isShow = isShowEditText;
    }
    
    
    

    /**
     * 设置监听
     * 
     * @param listener
     */
    public void setListener(IncomeRemindListener listener)
    {
        this.listener = listener;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title)
    {
        this.mTitle = title;
    }

    /**
     * 设置提示内容
     */
    public void setRemindInfo(String remindInfo)
    {
        this.mRemindInfo = remindInfo;
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
        View view = inflater.inflate(R.layout.dialog_income_remind, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tv_income_remind_title);
        tvRemindInfo = (TextView) view.findViewById(R.id.tv_income_remind_info);
        btnSubmit = (Button) view.findViewById(R.id.btn_income_remind_submit);
        btnSubmit.setOnClickListener(this);
        etPassword = (EditText) view.findViewById(R.id.et_income_remind_password);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // 设置标题
        if (CommUtil.isBlank(mTitle))
            tvTitle.setText("请设置标题");
        else
            tvTitle.setText(mTitle);
        // 设置提示信息
        if (CommUtil.isBlank(mRemindInfo))
            tvRemindInfo.setText("请设置提示内容");
        else
            tvRemindInfo.setText(mRemindInfo);
        
        if(isShow)
            etPassword.setVisibility(View.VISIBLE);
        else
            etPassword.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_income_remind_submit:
                String password = etPassword.getText().toString();
                if(isShow)
                {
                    if(CommUtil.isBlank(password))
                    {
                        toastInfo("请填写密码");
                    }
                    else
                    {
                        listener.onSubmit(isShow,password);
                        dismiss();
                    }
                }
                else
                {
                    listener.onSubmit(isShow,"");
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
    public interface IncomeRemindListener
    {
        // 确定
        public void onSubmit(boolean hasResult,String password);
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
