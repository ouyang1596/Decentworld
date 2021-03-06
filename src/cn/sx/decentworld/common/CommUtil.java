package cn.sx.decentworld.common;

import java.io.File;
import java.util.Collection;
import java.util.Random;

import android.os.Environment;
import android.util.Log;
import cn.sx.decentworld.DecentWorldApp;

/**
 * Created by HH Date: 2015/6/11 0011 Time: 下午 13:56
 */
public class CommUtil
{
    /**
     * 对象到字符串
     * @param obj
     * @return 字符串，默认为""
     */
    public static String toStr(Object obj)
    {
        return toStr(obj, Constants.DEFAULT_BLANK);
    }

    /**
     * 对象到字符串
     *
     * @param obj
     * @param defaultVal
     * @return 字符串，默认为 defaultVal 的值
     */
    public static String toStr(Object obj, String defaultVal)
    {
        if (obj == null || "NULL".equalsIgnoreCase(obj.toString()))
        {
            return defaultVal;
        }
        else
        {
            return obj.toString();
        }
    }

    public static Integer toInteger(Object obj)
    {
        return toInteger(obj, null);
    }

    public static Integer toInteger(Object obj, Integer defaultVal)
    {
        if (obj == null)
        {
            return defaultVal;
        }
        try
        {
            return Integer.parseInt(toStr(obj));
        }
        catch (Exception e)
        {
            Log.e(Constants.TAG, "Parse obj to Integer error!", e);
            return defaultVal;
        }
    }

    public static Long toLong(Object obj)
    {
        return toLong(obj, null);
    }

    public static Long toLong(Object obj, Long defaultVal)
    {
        if (obj == null)
        {
            return defaultVal;
        }
        try
        {
            return Long.parseLong(toStr(obj));
        }
        catch (Exception e)
        {
            Log.e(Constants.TAG, "Parse obj to Long error!", e);
            return defaultVal;
        }
    }

    public static Double toDouble(Object obj)
    {
        return toDouble(obj, null);
    }

    public static Double toDouble(Object obj, Double defaultVal)
    {
        if (obj == null)
        {
            return defaultVal;
        }
        try
        {
            return Double.parseDouble(toStr(obj));
        }
        catch (Exception e)
        {
            Log.e(Constants.TAG, "Parse obj to Long error!", e);
            return defaultVal;
        }
    }

    public static boolean isEmpty(Collection coll)
    {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection coll)
    {
        return !isEmpty(coll);
    }

    public static boolean isBlank(Object object)
    {
        if (object == null)
        {
            return true;
        }
        int strLen;
        String str = object.toString();
        if ((strLen = str.length()) == 0)
        {
            return true;
        }
        for (int i = 0; i < strLen; i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(Object object)
    {
        return !isBlank(object);
    }

    public static int min(int... args)
    {
        if (args == null || args.length == 0)
        {
            return 0;
        }
        int res = args[0];
        for (int i = 1; i < args.length; i++)
        {
            if (res > args[i])
            {
                res = args[i];
            }
        }
        return res;
    }

    public static String join(Object[] objs, String seprator)
    {
        if (objs == null)
        {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < objs.length; i++)
        {
            if (i > 0)
            {
                sb.append(seprator);
            }
            sb.append(objs[i]);
        }
        return sb.toString();
    }

    public static String genSpecificName(String seed, int range)
    {
        if (range == 0)
        {
            range = 100;
        }
        if (seed == null)
        {
            seed = Integer.valueOf(new Random().nextInt()).toString();
        }
        return Integer.valueOf(Math.abs(seed.hashCode() % range)).toString();
    }

    /**
     * 计算字符串对应的ASCII字符长度
     *
     * @param str
     * @return
     */
    public static int calcASCIILen(String str)
    {
        if (str == null)
        {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) > 127 || str.charAt(i) < 0)
            {
                // 非ASCII字符统一当作两个ASCII字符
                sum += 2;
            }
            else
            {
                sum++;
            }
        }
        return sum;
    }

    /**
     * <pre>
     * 格式化较长的字符串，中间用分隔字符串分开，使之更易阅读
     * 输入：prettyString("135235677884564564789456132123", " ", true, 3, 4, 5);
     * 输出：135 2356 77884 56456 47894 56132 123
     * </pre>
     *
     * @param cont
     *            原始字符串
     * @param seprator
     *            分隔字符串
     * @param repeatLast
     *            是否重复使用最后一个分隔字符串来分隔剩下的内容
     * @param pos
     *            可变长度分隔数量，3，4，5表示第一节3字符，第二节4字符，第三节5字符
     * @return 分隔后的字符串
     */
    public static String prettyString(String cont, String seprator, boolean repeatLast, int... pos)
    {
        if (pos == null || pos.length == 0)
        {
            return cont;
        }
        if (seprator == null)
        {
            seprator = Constants.DEFAULT_BLANK;
        }
        cont = cont.trim();
        StringBuilder sb = new StringBuilder();
        int currPos = 0;
        int ct = pos[currPos];
        int curr = 1;
        for (int i = 0; i < cont.length(); i++)
        {
            sb.append(cont.charAt(i));
            if (curr++ == ct)
            {
                curr = 1;
                currPos++;
                ct = currPos >= pos.length ? pos[pos.length - 1] : pos[currPos];
                if (cont.indexOf(seprator, i) == i + 1)
                {
                    curr = curr - seprator.length();
                    continue;
                }
                if ((repeatLast || currPos <= pos.length) && ct > 0)
                {
                    sb.append(seprator);
                }
            }
        }
        return sb.toString().replaceAll("(^(" + seprator + ")+)|((" + seprator + ")+$)", "");
    }

    /**
     * 还原经过分隔符分隔后的字符串
     *
     * @param cont
     *            原始字符串
     * @param seprator
     *            分隔字符串，可以是正则表达式
     * @return
     */
    public static String trimSeprator(String cont, String seprator)
    {
        if (seprator == null)
        {
            seprator = Constants.DEFAULT_BLANK;
        }
        return cont.replaceAll(seprator, "");
    }

    /**
     * 获取SD卡相关信息
     *
     * @return
     */
    public static String getSDCardPath()
    {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist)
        {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        if (sdcardDir == null)
        {
            return "";
        }
        else
        {
            return sdcardDir.toString();
        }
    }

    /**
     * 获取可用存储路径，优先使用SD卡，结尾带斜杠
     * 
     * @return
     */
    public static String getAvailableStoragePath()
    {
        String sdCardPath = getSDCardPath();
        StringBuilder sb = new StringBuilder();
        if (CommUtil.isNotBlank(sdCardPath))
        {
            sb.append(sdCardPath).append("/data/").append(DecentWorldApp.getInstance().getPackageName()).append('/');
        }
        else
        {
            sb.append(DecentWorldApp.getInstance().getFilesDir()).append("/data/");
        }
        return sb.toString();
    }

}
