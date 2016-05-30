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
@Table(name = "ChoiceInfo")
public class ChoiceInfo extends Model {
	private static String TAG = "ChoiceInfo";
	// 1 主键
	@Column(name = "dwID")
	public String dwID = "1";
	// @Column(name = "ageJudge")
	// public String ageJudge = "1";// 0负数，1正数
	@Column(name = "minAge")
	public String minAge = "0";
	@Column(name = "maxAge")
	public String maxAge = "80";
	// @Column(name = "worthJudge")
	// public String worthJudge = "1";// 0负数，1正数
	// @Column(name = "worth")
	// public String worth = "200";
	@Column(name = "minWorth")
	public String minWorth = "1";
	@Column(name = "maxWorth")
	public String maxWorth = "100";
	@Column(name = "scope")
	public String scope = "200";
	@Column(name = "school")
	public String school = "";
	@Column(name = "occupation")
	public String occupation = "";
	@Column(name = "sex")
	public String sex = "2";
	@Column(name = "car")
	public String car = "";
	@Column(name = "book")
	public String book = "";
	@Column(name = "hometown")
	public String hometown = "";
	@Column(name = "idol")
	public String idol = "";
	@Column(name = "religion")
	public String religion = "";
	@Column(name = "movie")
	public String movie = "";
	@Column(name = "sport")
	public String sport = "";
	// 星座
	@Column(name = "star")
	public String star = "";
	@Column(name = "animal")
	public String animal = "";
	@Column(name = "marry")
	public String marry = "";

	// SelectInfo[] selectInfo = { new SelectInfo("行业"), new SelectInfo("学校"),
	// new SelectInfo("车"), new SelectInfo("书名"), new SelectInfo("故乡"),
	// new SelectInfo("偶像"), new SelectInfo("宗教"), new SelectInfo("电影"),
	// new SelectInfo("运动"), new SelectInfo("星座"), new SelectInfo("属相"),
	// new SelectInfo("婚否") };
	public ChoiceInfo() {

	}

	public ChoiceInfo(String dwID) {
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
	public static ChoiceInfo queryByField(String fieldName, String fieldValue) {
		return new Select().from(ChoiceInfo.class).where(fieldName + "=?", fieldValue).executeSingle();
	}

	/**
	 * 查询指定dwID号的记录并返回结果
	 */
	public static ChoiceInfo queryByDwID(String dwID) {
		return new Select().from(ChoiceInfo.class).where("dwID=?", dwID).executeSingle();
	}
	// public RegisterInfo(String university, String college, String classRoom)
	// {
	// this.university = university;
	// this.college = college;
	// this.classRoom = classRoom;
	// }
}
