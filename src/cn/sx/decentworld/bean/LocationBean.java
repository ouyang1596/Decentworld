/**
 * 
 */
package cn.sx.decentworld.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * @ClassName: LocationBean.java
 * @Description:
 * @author: yj
 * @date: 2016年4月17日 上午9:09:23
 */
@Table(name = "LocationBean")
public class LocationBean extends Model {
	@Column(name = "dwID")
	public String dwID;
	@Column(name = "latitude")
	public double latitude;
	@Column(name = "longitude")
	public double longitude;

	public LocationBean() {

	}

	public LocationBean(String dwID) {
		this.dwID = dwID;
	}

	/**
	 * 查询指定dwID号的记录并返回结果
	 */
	public static LocationBean queryByDwID(String dwID) {
		return new Select().from(LocationBean.class).where("dwID=?", dwID).executeSingle();
	}

}
