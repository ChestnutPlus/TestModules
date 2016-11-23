package chestnut.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年10月7日
 *     desc  : 崩溃相关工具类
 *     thanks To :
 *          http://blog.csdn.net/hehe9737/article/details/7662123
 *          http://www.yiibai.com/java/lang/read_getdefaultuncaughtexceptionhandler.html
 *     dependent on:
 *           FileUtils
 *           TimeUtils
 *           AppUtils
 * </pre>
 */
public class CrashUtils implements UncaughtExceptionHandler {

    private volatile static CrashUtils mInstance;
    private Context mContext;
    private UncaughtExceptionHandler mHandler;
    private boolean mInitialized;

    private CrashUtils() {

    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        synchronized (CrashUtils.class) {
            if (null == mInstance) {
                mInstance = new CrashUtils();
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param application 应用
     */
    public void init(Application application) {
        if (mInitialized) return;
        mInitialized = true;
        mContext = application.getApplicationContext();
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        if (mInitialized) return;
        mInitialized = true;
        mContext = context.getApplicationContext();
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        String dir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                dir = mContext.getExternalCacheDir().getPath();
            } catch (Exception e) {
                dir = mContext.getCacheDir().getPath();
            }
        } else {
            dir = mContext.getCacheDir().getPath();
        }
        String fullPath = dir + File.separator + "Crash_" + TimeUtils.getCurTimeString() + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        StringBuilder sb = new StringBuilder();
        sb.append(getCrashHead());
        Writer writer = new StringWriter();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(writer);
            throwable.printStackTrace(pw);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
        } finally {
            FileUtils.closeIO(pw);
        }
        sb.append(writer.toString());
        sb.append("\n************* Crash Log End ****************\n\n");
        Log.e("Unknown-Crash", sb.toString());
        FileUtils.writeFileFromString(fullPath, sb.toString(), false);

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常...",
                        Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        SystemClock.sleep(1000);
        AppUtils.exitApp(mContext);
//        if (mHandler != null) {
//            mHandler.uncaughtException(thread, throwable);
//        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    public StringBuilder getCrashHead() {
        StringBuilder sb = new StringBuilder();
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            sb.append("\n************* Crash Log Head ****************");
            sb.append("\nDevice Manufacturer: ").append(Build.MANUFACTURER);// 设备厂商
            sb.append("\nDevice Model       : ").append(Build.MODEL);// 设备型号
            sb.append("\nAndroid Version    : ").append(Build.VERSION.RELEASE);// 系统版本
            sb.append("\nAndroid SDK        : ").append(Build.VERSION.SDK_INT);// SDK版本
            sb.append("\nApp VersionName    : ").append(pi.versionName);
            sb.append("\nApp VersionCode    : ").append(pi.versionCode);
            sb.append("\nCrash Des          : ").append("As follows\n");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sb;
    }
}
