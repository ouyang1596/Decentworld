/**
 * 
 */
package cn.sx.decentworld.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sx.decentworld.bean.UserInfoField.Field;

/**
 * @author Sammax 从数据库查出数据组成的的userInfo对象 非线程安全
 */
public class UserInfoBean {
	private List<UserInfoField> list = new ArrayList<UserInfoField>(60);
	private HashMap<Field, UserInfoField> map = null;
	private static String HIDE_NOTICE = "隐藏";

	public List<UserInfoField> getList() {
		return list;
	}

	public void setList(List<UserInfoField> list) {
		this.list = list;
	}

	public HashMap<Field, UserInfoField> getMap() {
		if (map == null) {
			initIndex();
		}
		return map;
	}

	public void setMap(HashMap<Field, UserInfoField> map) {
		this.map = map;
	}

	public UserInfoField getField(Field field) {
		if (map == null) {
			initIndex();
		}
		return map.get(field);
	}

	public void addField(UserInfoField field) {
		list.add(field);
		map.put(field.getField(), field);
	}

	private void initIndex() {
		map = new HashMap<UserInfoField.Field, UserInfoField>(60);
		for (UserInfoField field : list) {
			map.put(field.getField(), field);
		}
	}

	public UserInfoBean(List<UserInfoField> list) {
		super();
		this.list = list;
	}

	public UserInfoBean() {
		super();
	}

	public String getFieldValue(Field field) {
		UserInfoField userField = getField(field);
		if (userField.isDisplayAuth()) {
			return userField.getFieldValue();
		} else {
			return HIDE_NOTICE;
		}
	}

}
