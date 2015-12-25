/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: MyException.java
 * @Description:
 * @author: cj
 * @date: 2015年11月24日 下午4:42:02
 */
public class DWException {
	public void setOnExceptionListener(OnExceptionListener onExceptionListener) {
		this.mOnExceptionListener = onExceptionListener;
	}

	public void setException(Exception e) {
		if (null != mOnExceptionListener) {
			mOnExceptionListener.onException(e);
		}

	}

	private OnExceptionListener mOnExceptionListener;

	public interface OnExceptionListener {
		public void onException(Exception e);
	}
}
