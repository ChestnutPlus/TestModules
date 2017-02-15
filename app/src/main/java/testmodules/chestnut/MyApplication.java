package testmodules.chestnut;

import android.app.Application;

import chestnut.utils.CrashUtils;
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
        CrashUtils.getInstance().init(this);
        LogUtils.init(this);
        LogUtils.Config.setLogFileSwitch(true);
        SPUtils.getInstance().init(this,"My");
        HttpRequest.getInstance().init();
    }
}
