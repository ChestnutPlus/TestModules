package chestnut.rx;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年10月20日16:28:09
 *     desc  : Rx   一些常用 小功能 代码集合
 *     thanks To:
 *     dependent on:
 *     updateLog：
 *          1.0.0   倒计时。
 * </pre>
 */
public class RxUtils {

    /**
     * 倒计时，倒计 time 秒
     * @param time  单位：秒
     * @return  Observable
     */
    public static Observable<Integer> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Subject.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1);
    }

}
