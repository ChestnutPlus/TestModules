package testmodules.chestnut;

import android.app.Application;


import chestnut.db.DBUtils;
import chestnut.utils.CrashUtils;
import chestnut.utils.LogUtils;
import chestnut.utils.SPUtils;
import io.realm.Realm;

/**
 * Created by Chestnut on 2016/8/31.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtils.getInstance().init(this);
        LogUtils.init(this);
        SPUtils.getInstance().init(this,"My");
        DBUtils.getInstance().init(this);
    }

}
