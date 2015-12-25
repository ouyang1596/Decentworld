/**
 * 
 */
package cn.sx.decentworld.dialog;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.UserInfo;
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
 * @ClassName: RecommandDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月20日 下午1:48:17
 */

public class RecommandDialog extends DialogFragment implements OnClickListener
{
	private static final String TAG = "RecommandDialog";
	private ImageView iv_recommend_icon;
	private TextView tv_recommend_name;
	private EditText et_recommend_phoneNum;
	private EditText et_recommend_money;
	private Button btn_recommend_confirm;
	
	private RecommondListener listener;
	private String name = "";
	
	/**
	 * 推荐接口
	 * @ClassName: RecommandDialog.java
	 * @Description: 
	 * @author: cj
	 * @date: 2015年10月30日 下午8:12:28
	 */
	public interface RecommondListener 
	{
		public void confirm(String phoneNum,String money);
	}
	
	/**
	 * 设置监听
	 * @param listener
	 */
	public void setListener(RecommondListener listener)
	{
		this.listener = listener;
	}
	
	/**
	 * 设置名字
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
		View view = inflater.inflate(R.layout.dialog_fragment_recommand, null);
		iv_recommend_icon = (ImageView) view.findViewById(R.id.iv_recommend_icon);
		tv_recommend_name = (TextView) view.findViewById(R.id.tv_recommend_name);
		et_recommend_phoneNum = (EditText) view.findViewById(R.id.et_recommend_phoneNum);
		et_recommend_money = (EditText) view.findViewById(R.id.et_recommend_money);
		btn_recommend_confirm = (Button) view.findViewById(R.id.btn_recommend_confirm);
		btn_recommend_confirm.setOnClickListener(this);
		return view;
	}
	

	/**
	 * 初始化一些界面要显示的信息
	 * 1.头像
	 * 2.推荐人姓名
	 */
	@Override
	public void onStart()
	{
		super.onStart();
		if(CommUtil.isNotBlank(name))
		{
			tv_recommend_name.setText(name);
		}
		else
		{
			LogUtils.i(TAG, "名字为空");
			tv_recommend_name.setText("默认值");
		}
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			//推荐别人 确定
			case R.id.btn_recommend_confirm:
				String phoneNum = et_recommend_phoneNum.getText().toString().trim();
				String money = et_recommend_money.getText().toString().trim();
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
					LogUtils.i(TAG, "请输入推荐金额");
					Toast.makeText(getActivity(), "请输入推荐金额", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					if(Integer.valueOf(money)<1)
					{
						LogUtils.i(TAG, "推荐金额必须大于10");
						Toast.makeText(getActivity(), "推荐金额必须大于10", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				//触发确定点击事件
				listener.confirm(phoneNum, money);
				break;
			default:
				break;
		}
	}
	
	
	
}
