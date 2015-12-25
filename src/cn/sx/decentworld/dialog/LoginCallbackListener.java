/**
 * 
 */
package cn.sx.decentworld.dialog;

/**
 * @ClassName: LoginCallbackListener.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月21日 下午6:59:47
 */
public interface LoginCallbackListener {
		public void oncancel();
		public void save(String username,String password);
}
