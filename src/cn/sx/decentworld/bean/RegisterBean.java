/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: RegisterBean.java
 * @Description:
 * @author: yj
 * @date: 2015年10月12日 上午9:29:16
 */
public class RegisterBean {
	// 身份证
	public String card;
	// 大学
	public String school;
	// 昵称
	public String nick;
	// 学院
	public String department;
	// 真实姓名
	public String realName;
	// 班级
	public String classes;
	// 是否是学生
	public String ifStudent;
	// 材料类型
	public String type;
	// 材料
	public String material;
	@Override
	public String toString() {
		return "RegisterBean [card=" + card + ", school=" + school + ", nick="
				+ nick + ", department=" + department + ", realName="
				+ realName + ", classes=" + classes + ", ifStudent="
				+ ifStudent + ", type=" + type + ", material=" + material + "]";
	}

}
