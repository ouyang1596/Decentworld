/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: PromptBean.java
 * @Description:
 * @author: yj
 * @date: 2016年4月5日 上午8:55:19
 */
@Table(name = "PromptBean")
public class PromptBean extends Model {
	// 1 主键
	@Column(name = "dwID")
	public String dwID = "1";
	@Column(name = "isFirst")
	public int isFirst = -1;// 0是第一次，其他则不是

	public PromptBean() {

	}

	public PromptBean(String dwID) {
		this.dwID = dwID;
	}

	/**
	 * 查询指定dwID号的记录并返回结果
	 */
	public static PromptBean queryByDwID(String dwID) {
		return new Select().from(PromptBean.class).where("dwID=?", dwID).executeSingle();
	}
}
