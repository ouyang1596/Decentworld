/**
 * 
 */
package cn.sx.decentworld.task;

import org.jivesoftware.smack.packet.Packet;

import android.content.Context;

/**
 * 
 * 
 * @ClassName: DWMessageHandler.java
 * @Description: 
 * @author: cj
 * @date: 2015年12月16日 上午9:15:17
 */
public abstract class DWPacketHandler implements Runnable{
	protected Packet packet;
	protected Context context;
	public DWPacketHandler(){};
	public DWPacketHandler(Packet packet,Context context){
		this.packet=packet;
		this.context=context;
	}
	public void init(Packet packet,Context context){
		this.packet=packet;
		this.context=context;
	}
	
}
