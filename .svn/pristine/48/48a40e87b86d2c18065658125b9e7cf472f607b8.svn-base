package cn.sx.decentworld.component;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

/** 
* @ClassName: KeyboardComponent 
* @Description: 键盘组件 
* @author yj 
* @date 2014-12-24 下午4:34:10 
*  
*/ 
@EBean(scope = Scope.Singleton)
public class KeyboardComponent {

	@RootContext
	Context context;

	/**
	 * 要强制屏蔽键盘的组件
	 * 
	 * @param objs
	 *            不定参数,可以传入任意数量的参数
	 */
	public void dismissKeyboard(View... objs) {
		dismissKeyboardReadonly(false, objs);
	}

	/**
	 * 要强制屏蔽键盘的组件
	 * 
	 * @param readonly
	 *            是否只读
	 * @param objs
	 *            不定参数,可以传入任意数量的参数
	 */
	public void dismissKeyboardReadonly(boolean readonly, View... objs) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (objs != null) {
			for (View view : objs) {
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				if (readonly) {
					view.setEnabled(false);
				}
			}
		}
	}

	/**
	 * 要强制失去焦点的组件
	 * 
	 * @param objs
	 *            不定参数,可以传入任意数量的参数
	 */
	public void clearfocusKeyboard(View... objs) {
		if (objs != null) {
			for (View view : objs) {
				view.clearFocus();
			}
		}
	}
	
	/**
     * 打卡软键盘
     * 
     * @param mEditText输入框
     * @param mContext上下文
     */
    public  void openKeybord(EditText mEditText)
    {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
 
    /**
     * 关闭软键盘
     * 
     * @param mEditText输入框
     * @param mContext上下文
     */
    public  void closeKeyboard(EditText mEditText)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
 
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

}
