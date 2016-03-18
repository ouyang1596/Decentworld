/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: RegisterInfo.java
 * @Description:用来保存用户注册时的信息
 * @author: yj
 */
@Table(name = "registerInfo")
public class RegisterInfo extends Model {
	private static String TAG = "RegisterInfo";
	// 1 主键
	@Column(name = "dwID")
	public String dwID = "1";
	// 学校
	@Column(name = "university")
	public String university;
	// 院系
	@Column(name = "college")
	public String college;
	// 班级
	@Column(name = "password")
	public String password;
	// 密码
	@Column(name = "classRoom")
	public String classRoom;
	// 姓名
	@Column(name = "name")
	public String name;
	// 昵称
	@Column(name = "nickname")
	public String nickname;
	// 才华材料种类
	@Column(name = "talentMaterial")
	public String talentMaterial;
	// 财富材料种类
	@Column(name = "moneyMaterial")
	public String moneyMaterial;
	// 身份证
	@Column(name = "identifyCard")
	public String identifyCard;
	// 学生证图片路径
	@Column(name = "picStuIdPath")
	public String picStuIdPath;
	// talent图片路径
	@Column(name = "picTalentPath")
	public String picTalentPath;
	// money图片路径
	@Column(name = "picMoneyPath")
	public String picMoneyPath;
	// 身份证图片路径
	@Column(name = "picIdPath")
	public String picIdPath;
	// 靓照图片路径
	@Column(name = "picbeautyOnePath")
	public String picbeautyOnePath;
	// 靓照图片路径
	@Column(name = "picbeautyTwoPath")
	public String picbeautyTwoPath;
	// 靓照图片路径
	@Column(name = "picbeautyThreePath")
	public String picbeautyThreePath;

	public RegisterInfo() {

	}

	public RegisterInfo(String dwID) {
		this.dwID = dwID;
	}

	/**
	 * 查询一条指定 字段 名 和 字段值 的记录
	 * 
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @return
	 */
	public static RegisterInfo queryByField(String fieldName, String fieldValue) {
		return new Select().from(RegisterInfo.class)
				.where(fieldName + "=?", fieldValue).executeSingle();
	}

	/**
	 * 查询指定dwID号的记录并返回结果
	 * 
	 * @param dwID
	 */
	public static RegisterInfo queryByDwID(String dwID) {
		return new Select().from(RegisterInfo.class).where("dwID=?", dwID)
				.executeSingle();
	}
	// public RegisterInfo(String university, String college, String classRoom)
	// {
	// this.university = university;
	// this.college = college;
	// this.classRoom = classRoom;
	// }
}
