package chestnut.web;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.Map;
import java.util.Set;

import rx.Observable;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年11月25日14:51:25
 *     desc  : 用于封装第三方的Http请求库
 *     thanks To:
 *     dependent on:
 *          HttpUtils
 * </pre>
 */
public class HttpRequest {

    interface Callback {
        void onSuccess(String result);
        void onFailure(String msg);
    }

    /**
     * 封装第三方的get请求
     * RX
     *
     * @param url   地址
     * @param map   请求参数
     * @return  RX
     */
    public static Observable<String> RxGet(final String url, Map<String,String> map) {
        return Observable.create(subscriber -> {
            String URL = chestnut.utils.HttpUtils.assemblyGetParam(url,map);
            RxVolley.get(URL, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    subscriber.onNext(t);
                }
                @Override
                public void onFailure(int errorNo, String strMsg) {
                    subscriber.onError(new Throwable("errorCode:"+errorNo+"msg:"+strMsg));
                }
            });
        });
    }

    /**
     * 封装第三方的post请求
     * RX
     *
     * @param url   地址
     * @param map   请求参数
     * @return  RX
     */
    public static Observable<String> RxPost(String url, Map<String,String> map) {
        return Observable.create(subscriber -> {
            HttpParams httpParams = new HttpParams();
            Set<String> keys = map.keySet();
            for (String key :
                    keys) {
                httpParams.put(key,map.get(key));
            }
            RxVolley.post(url, httpParams, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    subscriber.onNext(t);
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    subscriber.onError(new Throwable("errorCode:"+errorNo+"msg:"+strMsg));
                }
            });
        });
    }

    /**
     * 封装第三方的Get请求
     * 回调
     *
     * @param url   地址
     * @param map   参数
     * @param callback  回调
     */
    public static void Get(String url, Map<String,String> map, Callback callback) {
        String URL = chestnut.utils.HttpUtils.assemblyGetParam(url,map);
        RxVolley.get(URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                if (callback!=null)
                    callback.onSuccess(t);
            }
            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (callback!=null)
                    callback.onFailure("errorCode:"+errorNo+"msg:"+strMsg);
            }
        });
    }

    /**
     * 封装第三方的Post请求
     * 回调
     *
     * @param url   地址
     * @param map   参数
     * @param callback  回调
     */
    public static void Post(String url, Map<String,String> map, Callback callback) {
        HttpParams httpParams = new HttpParams();
        Set<String> keys = map.keySet();
        for (String key :
                keys) {
            httpParams.put(key,map.get(key));
        }
        RxVolley.post(url, httpParams, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                if (callback!=null)
                    callback.onSuccess(t);
            }
            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (callback!=null)
                    callback.onFailure("errorCode:"+errorNo+"msg:"+strMsg);
            }
        });
    }
}
