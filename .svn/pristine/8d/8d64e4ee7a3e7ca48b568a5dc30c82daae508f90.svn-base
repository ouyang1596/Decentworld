package cn.sx.decentworld;



import com.lidroid.xutils.DbUtils;

import cn.sx.decentworld.utils.LogUtils;


/**
 * 
 * @ClassName: DWDbUpgradeListener.java
 * @Description: 数据库更新监听，更新代码写到try中
 * @author: cj
 * @date: 2015年10月11日 下午4:05:35
 */
public class DWDbUpgradeListener implements DbUtils.DbUpgradeListener
{
	private static final String TAG = "DWDbUpgradeListener";

	@Override
	public void onUpgrade(DbUtils db, int oldVersion, int newVersion)
	{
		LogUtils.i(TAG, "oldVersion="+oldVersion+",newVersion="+newVersion);
		int upgradeVersion = oldVersion;
		if (1 == upgradeVersion)
		{
			LogUtils.i(TAG, "旧数据库版本为：" + upgradeVersion+",开始更新....");
//			try
//			{
//				// 更新数据库的操作
//				String sql = "ALTER TABLE testBean RENAME TO testBean_old";
//				db.execNonQuery(sql);
//				sql = "CREATE TABLE testBean (id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name TEXT,icon TEXT,time TEXT,height TEXT)";
//				db.execNonQuery(sql);
//				sql = "INSERT INTO testBean (id, name, icon, time) SELECT id, name, icon, time FROM testBean_old";
//				db.execNonQuery(sql);
//				sql = "DROP TABLE testBean_old";
//				db.execNonQuery(sql);
//				// 更新数据库的操作
//
//			} catch (DbException e)
//			{
//				e.printStackTrace();
//			}
			upgradeVersion = 2;
			LogUtils.i(TAG, "更新后数据数据库版本为：" + upgradeVersion+",更新数据库成功。");
		}

//		if (2 == upgradeVersion)
//		{
//			
//			LogUtils.i(TAG, "当前数据库为：" + upgradeVersion);
////			upgradeVersion = 3;
//		}
//
//		if (upgradeVersion != newVersion)
//		{
//
//		}

	}
}
