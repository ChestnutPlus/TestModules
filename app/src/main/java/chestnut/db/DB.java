package chestnut.db;

import android.content.Context;

import rx.Observable;

/**
 * Created by Chestnut on 2016/11/12.
 * 规范定义了DB的行为：
 *
 *      R   :   Async回调的接口
 *      T   :   继承数据库bean的类
 */
public interface DB<R,T> {

    boolean init(Context context);
    boolean closeDB();

    <E extends T> boolean addSync(E o);             //E:要是需要继承T的Bean
    <E extends T> boolean addAsync(E o,R r);        //R:回掉接口
    <E extends T> Observable<Boolean> addRx(Class<E> e, E o);

    <E extends T> boolean addOrUpdateSync(E o);
    <E extends T> boolean addOrUpdateAsync(E o,R r);
    <E extends T> Observable<Boolean> addOrUpdateRx(Class<E> e, E o);
}
