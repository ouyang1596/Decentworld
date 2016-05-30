/**
 * 
 */
package cn.sx.decentworld.test.cj;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.entity.SelfUserInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: TestSelfInfo
 * @Description: 测试当前用户信息代码
 * @author: Jackchen
 * @date: 2016年5月4日 上午9:49:59
 */
public class TestSelfInfo
{
    private static final String TAG = "TestSelfInfo";
    
    
    /**
     * 读取数据
     */
    public String readData(String filePath)
    {
        String str = "";
        try
        {
            FileInputStream fis = new FileInputStream(filePath);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            str = new String(b);
            System.out.println("" + str);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 解析数据
     */
    public SelfUserInfo parseData(String data)
    {
        JSONObject json = JSON.parseObject(data);
        String infoString = json.getString("info");
        SelfUserInfo info = JSON.parseObject(infoString, SelfUserInfo.class);
        return info;
    }

    /**
     * 打印数据
     */
    public void printData(SelfUserInfo info)
    {
        System.out.println("dwID:" + info.getDwID());
        System.out.println("version:" + info.getVersionNum());

        System.out.println("dwID field name:" + info.getFieldName(Field.ID));
        System.out.println("dwID field value:" + info.getFieldValue(Field.ID));
        System.out.println("dwID display auth:" + info.isDispaly(Field.ID));
        System.out.println("dwID anonymous auth:" + info.isAnonymous(Field.ID));
        System.out.println("dwID group name:" + info.getFieldGroup(Field.ID).getGroupName());
    }

}
