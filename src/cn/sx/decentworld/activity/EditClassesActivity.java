/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditNicknameActivity.java
 * @Description: 修改昵称
 * @author: cj
 * @date: 2015年10月21日 上午10:43:25
 */

@EActivity(R.layout.activity_edit_classes)
public class EditClassesActivity extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG = "EditClassesActivity";
	@Bean
	ToastComponent toast;
	
	@ViewById(R.id.ll_edit_classes_root)
	LinearLayout ll_edit_classes_root;
	
	@ViewById(R.id.et_edit_classes)
	EditText et_edit_classes;
	
	@ViewById(R.id.btn_edit_classes_back)
	Button btn_edit_classes_back;
	
	@ViewById(R.id.btn_edit_classes_complete)
	Button btn_edit_classes_complete;
	
	private int position = -1;
	private String oldClasses="";
	
	@AfterViews
	void init()
	{
		ViewUtil.scaleContentView(ll_edit_classes_root);
		btn_edit_classes_back.setOnClickListener(this);
		btn_edit_classes_complete.setOnClickListener(this);
		position = getIntent().getIntExtra("position", -1);
		oldClasses = getIntent().getStringExtra("oldClasses");
		LogUtils.i(TAG, "position="+position+",oldClasses="+oldClasses);
		if(!oldClasses.equals(""))
		{
			et_edit_classes.setText(oldClasses);
		}
		
		
		/**
		 * 将光标设置到文字的末尾
		 */
		CharSequence text = et_edit_classes.getText();
		if (text instanceof Spannable)
		{
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_edit_classes_complete:
			{
				String newClasses = et_edit_classes.getText().toString();
				if(!newClasses.equals(oldClasses))
				{
					Intent intent = new Intent();
					intent.putExtra("position", position);
					intent.putExtra("newClasses", newClasses);
					setResult(RESULT_OK, intent);
					LogUtils.i(TAG, "修改院系为："+newClasses);
				}
				else
				{
					LogUtils.i(TAG, "没有修改院系");
				}
				finish();
			}
				
				break;
			case R.id.btn_edit_classes_back:
				finish();
			default:
				break;
		}
	}

}
