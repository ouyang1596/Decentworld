/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: GroupInfo.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月24日 上午10:54:51
 */
public class GroupInfo {
	private String groupname;  //用户名
	private int avatar;// 头像
	private String group_info;// 添加理由
	private int group_capacity;//兴趣组的规模
	private int group_people;//兴趣组已有的人数
	private String distance;//距离
	
	public GroupInfo(String groupname, int avatar, String group_info,
			int group_capacity, int group_people, String distance) {
		super();
		this.groupname = groupname;
		this.avatar = avatar;
		this.group_info = group_info;
		this.group_capacity = group_capacity;
		this.group_people = group_people;
		this.distance = distance;
	}
	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}
	/**
	 * @param groupname the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	/**
	 * @return the avatar
	 */
	public int getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	/**
	 * @return the group_info
	 */
	public String getGroup_info() {
		return group_info;
	}
	/**
	 * @param group_info the group_info to set
	 */
	public void setGroup_info(String group_info) {
		this.group_info = group_info;
	}
	/**
	 * @return the group_capacity
	 */
	public int getGroup_capacity() {
		return group_capacity;
	}
	/**
	 * @param group_capacity the group_capacity to set
	 */
	public void setGroup_capacity(int group_capacity) {
		this.group_capacity = group_capacity;
	}
	/**
	 * @return the group_people
	 */
	public int getGroup_people() {
		return group_people;
	}
	/**
	 * @param group_people the group_people to set
	 */
	public void setGroup_people(int group_people) {
		this.group_people = group_people;
	}
	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
}
