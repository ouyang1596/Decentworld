/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.utils.LogUtils;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: GuaranteeDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月20日 下午3:04:19
 */
public class GuaranteeDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "GuaranteeDialog";
	private TextView tv_guarantee_name;
	private EditText et_guarantee_info;
	private EditText et_guarantee_money;
	private Button btn_guarantee_confirm;
	
	private GuaranteeListener guaranteeListener;
	private String name;
	
	/**
	 * 设置点击事件的监听
	 * @param guaranteeListener
	 */
	public void setListener(GuaranteeListener guaranteeListener)
	{
		this.guaranteeListener = guaranteeListener;
	}
	
	/**
	 * 设置对话框要显示的名字
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
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
		View view = inflater.inflate(R.layout.dialog_fragment_guarantee, null);
		tv_guarantee_name = (TextView) view.findViewById(R.id.tv_guarantee_name);
		et_guarantee_info = (EditText) view.findViewById(R.id.et_guarantee_info);
		et_guarantee_money = (EditText) view.findViewById(R.id.et_guarantee_money);
		btn_guarantee_confirm = (Button) view.findViewById(R.id.btn_guarantee_confirm);
		btn_guarantee_confirm.setOnClickListener(this);
		return view;
	}
	

	@Override
	public void onStart()
	{
		super.onStart();
		if(CommUtil.isNotBlank(name))
		{
			tv_guarantee_name.setText(name);
		}
		else
		{
			LogUtils.i(TAG, "名字为空");
			tv_guarantee_name.setText("默认值");
		}
	}
	
	
	/**
	 * 担保监听接口
	 * @ClassName: GuaranteeDialog.java
	 * @Description: 
	 * @author: cj
	 * @date: 2015年10月30日 下午6:49:12
	 */
	public interface GuaranteeListener
	{
		public void confirm(String phoneNum,String money);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_guarantee_confirm:
				String phoneNum = et_guarantee_info.getText().toString().trim();
				String money = et_guarantee_money.getText().toString().trim();
				//判断电话号码
				if(CommUtil.isBlank(phoneNum))
				{
					LogUtils.i(TAG, "请输入电话号码");
					Toast.makeText(getActivity(), "请输入电话号码", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					if(phoneNum.length()!=11)
					{
						LogUtils.i(TAG, "请输入有效的电话号码");
						Toast.makeText(getActivity(), "请输入有效的电话号码", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				//判断担保金额
				if(CommUtil.isBlank(money))
				{
					LogUtils.i(TAG, "请输入担保金额");
					Toast.makeText(getActivity(), "请输入担保金额", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					if(Integer.valueOf(money)<300)
					{
						LogUtils.i(TAG, "担保金额必须大于300");
						Toast.makeText(getActivity(), "担保金额必须大于300", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				//触发确定点击事件
				guaranteeListener.confirm(phoneNum, money);
				break;
			default:
				break;
		}
		
	}
	
}
