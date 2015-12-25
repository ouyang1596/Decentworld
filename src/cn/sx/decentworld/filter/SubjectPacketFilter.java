/**
 * 
 */
package cn.sx.decentworld.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * @ClassName: SubjectFilter.java
 * @Description: 
 * @author: cj
 * @date: 2015年11月28日 下午2:01:53
 */
public class SubjectPacketFilter implements PacketFilter {
	private final String[] subject;

	public SubjectPacketFilter(String[] subject) {
		this.subject = subject;
	}

	public boolean accept(Packet packet) {
		if (!(packet instanceof Message)) {
			return false;
		}
		Message m = (Message) packet;
		for(int i=0;i<subject.length;i++){
			if ((m.getSubject().equals(subject[i]))) {
				return true;
			}
		}
		return false;
	}
}
