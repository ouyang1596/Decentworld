/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @ClassName: MiniPrompt
 * @Description: 迷你提示对话框
 * @author: Jackchen
 * @date: 2016年5月17日 下午3:25:11
 */
public class MiniPromptDlg extends DialogFragment
{
    private static final String TAG = "MiniPromptDlg";
    private String info;
    private TextView tvInfo;

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
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
        View view = inflater.inflate(R.layout.dialog_mini_prompt, container, false);
        tvInfo = (TextView) view.findViewById(R.id.tv_prompt_info);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(CommUtil.isNotBlank(info))
            tvInfo.setText(info);
        else
            tvInfo.setText("提示信息");
    }
}
