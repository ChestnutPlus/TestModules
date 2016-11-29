package chestnut.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmModel;
import rx.Observable;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年11月25日14:51:25
 *     desc  : 对第三方数据库框架的封装
 *     thanks To:
 *     dependent on:
 * </pre>
 */
public class DBUtils implements DB<DBUtils.DBCallback,RealmModel>{

    /**
     * 数据库更新的回调接口
     */
    public interface DBCallback {
        void onSuccess();
        void onFail(String msg);
    }

    private static DBUtils dbUtils = new DBUtils();
    private Realm realm;
    private Context context;
    private boolean isInit = false;

    public static DBUtils getInstance() {
        return dbUtils;
    }

    private void getRealm() {
        if (realm==null || realm.isClosed())
            realm = Realm.getDefaultInstance();
    }

    @Override
    public boolean init(Context context) {
        //建议最好在 Application.java中初始化：
        if (isInit)
            return true;
        Realm.init(context.getApplicationContext());
        getRealm();
        this.context = context.getApplicationContext();
        isInit = true;
        return true;
    }

    @Override
    public boolean closeDB() {
        if (!realm.isClosed())
            realm.close();
        return true;
    }


    @Override
    public <E extends RealmModel> boolean addSync(E o) {
        try {
            getRealm();
            realm.beginTransaction();
            realm.copyToRealm(o);
            realm.commitTransaction();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public <E extends RealmModel> boolean addAsync(E o, DBCallback dbCallback) {
        getRealm();
        realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealm(o),
                () -> {
                    if (dbCallback!=null)
                        dbCallback.onSuccess();
                },
                error -> {
                    if (dbCallback!=null)
                        dbCallback.onFail(error!=null ? error.getMessage() : "unknownErr");
                }
        );
        return false;
    }

    @Override
    public <E extends RealmModel> Observable<Boolean> addRx(Class<E> e, E o) {
        return Observable.create(subscriber -> {
            getRealm();
            realm.executeTransactionAsync(
                    realm1 -> realm1.copyToRealm(o),
                    () -> subscriber.onNext(true),
                    error -> subscriber.onNext(false)
            );
        });
    }



    @Override
    public <E extends RealmModel> boolean addOrUpdateSync(E o) {
        try {
            getRealm();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(o);
            realm.commitTransaction();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public <E extends RealmModel> boolean addOrUpdateAsync(E o, DBCallback dbCallback) {
        getRealm();
        realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(o),
                () -> {
                    if (dbCallback!=null)
                        dbCallback.onSuccess();
                },
                error -> {
                    if (dbCallback!=null)
                        dbCallback.onFail(error!=null ? error.getMessage() : "unknownErr");
                }
        );
        return false;
    }

    @Override
    public <E extends RealmModel> Observable<Boolean> addOrUpdateRx(Class<E> e, E o) {
        return Observable.create(subscriber -> {
            getRealm();
            realm.executeTransactionAsync(
                    realm1 -> realm1.copyToRealmOrUpdate(o),
                    () -> subscriber.onNext(true),
                    error -> subscriber.onNext(false)
            );
        });
    }

}
