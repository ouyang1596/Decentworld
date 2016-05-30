/**
 * 
 */
package cn.sx.decentworld.test.cj;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * @ClassName: TestPre
 * @Description: TODO (在这里用一句话描述类的作用)
 * @author: Jackchen
 * @date: 2016年5月17日 下午8:14:50
 */
public class TestPre
{
    private static final String TAG = "TestPre";

    public void test()
    {
        StringBuffer appNameAndPermissions = new StringBuffer();
        PackageManager pm = DecentWorldApp.getGlobalContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : packages)
        {
            try
            {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                appNameAndPermissions.append(packageInfo.packageName + "*:\n");
                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null)
                {
                    for (int i = 0; i < requestedPermissions.length; i++)
                    {
                        Log.d("test", requestedPermissions[i]);
                        appNameAndPermissions.append(requestedPermissions[i] + "\n");
                    }
                    appNameAndPermissions.append("\n");
                }
            }
            catch (NameNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean get()
    {
        PackageManager pm = DecentWorldApp.getGlobalContext().getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECORD_AUDIO", "packageName"));
        if (permission)
        {
//            showToast("有这个权限");
            return true;
        }
        else
        {
//            showToast("木有这个权限");
            return false;
        }
    }
}
