/**
 * 
 */
package cn.sx.decentworld.bean;

/**
 * @ClassName: UserInfoItem.java
 * @Description: 模拟环境下的 bean 类，用于测试展示设置选项的Item，当从服务器拿取数据成功后可删除；
 * @author: cj
 * @date: 2015年10月11日 上午10:13:18
 */
public class EditUserInfoItem {
    String index;
    String title;
    String key;
    String value;
    boolean isShow;

    /**
	 * 
	 */
    public EditUserInfoItem() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param index
     * @param title
     * @param key
     * @param value
     */
    public EditUserInfoItem(String index, String title, String key, String value) {
        super();
        this.index = index;
        this.title = title;
        this.key = key;
        this.value = value;
    }

    public EditUserInfoItem(boolean isShow, String index, String title, String key, String value) {
        super();
        //是否显示的标识变量
        this.isShow = isShow;
        this.index = index;
        //分类标题
        this.title = title;
        //属性对应Key
        this.key = key;
        //属性的对应的value
        this.value = value;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the isShow
     */
    public boolean isShow() {
        return isShow;
    }

    /**
     * @param isShow the isShow to set
     */
    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

}
