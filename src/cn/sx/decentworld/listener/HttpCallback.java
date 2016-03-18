/**
 * 
 */
package cn.sx.decentworld.listener;

/**
 * @ClassName: HttpCallback.java
 * @Description: 网络请求回调
 * @author: cj
 * @date: 2015年12月14日 下午2:06:53
 */
public interface HttpCallback
{
	public void onSuccess(String result);
	public void onFailure(String cause);
}
