package testmodules.chestnut;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

import chestnut.utils.LogUtils;
import chestnut.utils.SPUtils;
import chestnut.web.HttpRequest;

/**
 * Created by Chestnut on 2016/8/31.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashUtils.getInstance().init(this, s ->
//                Log.e("send-Crash-Email:",""+EmailUtils.sendEmail("smtp.163.com","25","13590272662@163.com","13707867Kid","974920378@qq.com",
//                        AppUtils.getAppName(MyApplication.this)+":crash",s))
//        );
        LogUtils.init(this);
        LogUtils.Config.setLogFileSwitch(true);
        SPUtils.getInstance().init(this,"TestModules.chestnut");
        HttpRequest.getInstance().init();
        FileDownloader.init(this);
    }
}
