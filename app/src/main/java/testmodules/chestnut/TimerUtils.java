package testmodules.chestnut;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Chestnut on 2016/11/20.
 */

public class TimerUtils {

    private static Timer timer = null;
    private static boolean isSetTimer = false;
    private static Callback callback = null;
    public interface Callback {
        void TimerStar(long time);
        void TimerEnd(long time);
    }

    /**
     * 设置全局定时器的Callback
     * @param callback  传入回调接口
     */
    public static void setAsyncCallback(Callback callback) {
        TimerUtils.callback = callback;
    }

    /**
     * 设置一个全局的定时器，通过回调接口进行回调通知
     * @param timeMS    时间。
     */
    public static boolean setAsyncGlobalTimer(long timeMS) {
        if (timer==null)
            timer = new Timer();
        if (isSetTimer)
            return false;
        timer.cancel();
        isSetTimer = true;
        if (callback!=null)
            callback.TimerStar(timeMS);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                callback.TimerEnd(timeMS);
            }
        },timeMS);
        return true;
    }

    /**
     * 同步
     * @param timeMS
     * @return
     */
    public static boolean setSyncGlobalTimer(long timeMS) {
        return false;
    }
}
