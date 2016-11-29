package chestnut.Interface.web;

import java.util.Map;

import rx.Observable;

/**
 * Created by Chestnut on 2016/11/29.
 */

public interface Http {
    Observable<String> RxGet(String url, Map<String,String> map);
    Observable<String> RxPost(String url, Map<String,String> map);
    void Get(String url, Map<String,String> map, HttpCallBack callBack);
    void Post(String url, Map<String,String> map, HttpCallBack callBack);
}
