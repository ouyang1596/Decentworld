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
import android.widget.Button;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;

/**
 * @ClassName: ReminderDialog.java
 * @Description: 删除对话框
 * @author: cj
 * @date: 2015年7月24日 下午7:20:17
 */
public class DeleteDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "DeleteDialog";
    private DeleteListener listener;
    private TextView tvCancel;
    private TextView tvDelete;

    public void setListener(DeleteListener listener)
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
        View view = inflater.inflate(R.layout.dialog_delete, container, false);
        tvCancel = (TextView) view.findViewById(R.id.tv_dig_delete_cancel);
        tvDelete = (TextView) view.findViewById(R.id.tv_dig_delete_detele);
        tvCancel.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_dig_delete_cancel:
                listener.cancel();
                dismiss();
                break;
            case R.id.tv_dig_delete_detele:
                listener.delete();
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface DeleteListener
    {
        public void delete();

        public void cancel();
    }

}
