/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: SubjectBean.java
 * @Description:
 * @author: yj
 * @date: 2015年11月8日 下午2:12:07
 */
@Table(name = "SubjectBean")
public class SubjectBean extends Model {
	private static String TAG = "SubjectBean";
	// 1 主键
	@Column(name = "roomID")
	public String roomID = "1";
	// 判断是添加主题还是修改主题 0添加 1修改
	@Column(name = "isNewOrEdit")
	public String isNewOrEdit;
	@Column(name = "beginDate")
	public String beginDate;
	@Column(name = "endDate")
	public String endDate;
	@Column(name = "imgUrl")
	public String imgUrl;
	@Column(name = "imgUrl1")
	public String imgUrl1;
	@Column(name = "imgUrl2")
	public String imgUrl2;
	@Column(name = "subjectBackground")
	public String subjectBackground;
	@Column(name = "subjectName")
	public String subjectName;
	@Column(name = "subjectContent")
	public String subjectContent;
	@Column(name = "subjectContent1")
	public String subjectContent1;
	@Column(name = "subjectContent2")
	public String subjectContent2;
	@Column(name = "isFixedOrPercent")
	public String isFixedOrPercent;
	@Column(name = "chargeAmount")
	public String chargeAmount;

	public SubjectBean() {

	}

	public SubjectBean(String roomID, String isNewOrEdit) {
		this.roomID = roomID;
		this.isNewOrEdit = isNewOrEdit;
	}

	/**
	 * 查询指定dwID号的记录并返回结果
	 * 
	 * @param dwID
	 */
	public static SubjectBean queryByRoomIDAndIsNewOrEdit(String roomID,
			String isNewOrEdit) {
		return new Select().from(SubjectBean.class)
				.where("roomID=? and isNewOrEdit=? ", roomID, isNewOrEdit)
				.executeSingle();
	}
}
