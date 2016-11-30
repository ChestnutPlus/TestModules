package chestnut.Interface.web;

import java.util.Map;

import rx.Observable;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年11月30日14:52:41
 *     desc  : 规范定义了Http请求
 *     thanks To:
 *     dependent on:
 *     updateLog：
 * </pre>
 */
public interface Http {
    Observable<String> RxGet(String url, Map<String,String> map);
    Observable<String> RxPost(String url, Map<String,String> map);
    void Get(String url, Map<String,String> map, HttpCallBack callBack);
    void Post(String url, Map<String,String> map, HttpCallBack callBack);
}
