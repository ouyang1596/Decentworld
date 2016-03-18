/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: SingleCheckResultBean.java
 * @author: yj
 * @date: 2016年1月27日 下午6:22:48
 */
public class SingleCheckResultBean {
	public String dwID;
	public int fakeAmount;
	public int isFinished;
	public int passAmount;
	public SupporterAndCSBean supporter;
	public int unpassAmount;

	@Override
	public String toString() {
		return "SingleCheckResultBean [dwID=" + dwID + ", fakeAmount=" + fakeAmount + ", isFinished=" + isFinished
				+ ", passAmount=" + passAmount + ", supporter=" + supporter + ", unpassAmount=" + unpassAmount + "]";
	}

}
