/**
 * 
 */
package cn.sx.decentworld.test.cj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.autonavi.amap.mapcore.FileUtil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BaseFragmentActivity;
import cn.sx.decentworld.common.FilePath;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.logSystem.entity.LogFileEntity;
import cn.sx.decentworld.utils.TimeUtils;

/**
 * @ClassName: JackTestActivity.java
 * @Description: cj测试Activity
 * @author: cj
 * @date: 2016年4月19日 上午10:57:04
 */
public class JackTestActivity extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "JackTestActivity";
    private Button mBtnUploadFile;
    private Button mBtnCreateFile;
    private Button mBtnCreateLog1;
    private Button mBtnCreateLog2;

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.activity_jack_test);
        mBtnUploadFile = (Button) findViewById(R.id.btn_test_upload_file);
        mBtnUploadFile.setOnClickListener(this);
        mBtnCreateFile = (Button) findViewById(R.id.btn_test_create_file);
        mBtnCreateFile.setOnClickListener(this);
        
        mBtnCreateLog1 = (Button) findViewById(R.id.btn_test_create_1);
        mBtnCreateLog1.setOnClickListener(this);
        mBtnCreateLog2 = (Button) findViewById(R.id.btn_test_create_2);
        mBtnCreateLog2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_test_create_file:
                createFile();
                break;
            case R.id.btn_test_upload_file:
                uploadFile();
                break;
            case R.id.btn_test_create_1:
                LogUtils.w(TAG, "测试文字1");
                
                break;
            case R.id.btn_test_create_2:
                LogUtils.w(TAG, "测试文字2");
                
                break;
            default:
                break;
        }
    }

    /**
     * 
     */
    private void createFile()
    {
//        String filePath = FilePath.HOME_LOG_REPORT_TO_BMOB;
//        String fileName = System.currentTimeMillis() + ".txt";
//        createLogFile(filePath, fileName);
    }

    /**
     * 
     */
    private void uploadFile()
    {
        LogUtils.uploadLogFile();
    }


}
