package chestnut.web;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import chestnut.Interface.web.Http;
import chestnut.Interface.web.HttpCallBack;
import chestnut.utils.LogUtils;
import rx.Observable;

/**
 * Created by Chestnut on 2016/11/30.
 */
public class HttpRequest implements Http{

    private static HttpRequest httpRequest = new HttpRequest();
    private Context context = null;
    private RequestQueue m = null;
    private HttpRequest() {}
    public static HttpRequest getInstance() {
        return httpRequest;
    }

    public void init(Context context) {
        if (context==null) {
            this.context = context.getApplicationContext();
            m = Volley.newRequestQueue(context);
        }
    }

    @Override
    public Observable<String> RxGet(String url, Map<String, String> map) {
        return null;
    }

    @Override
    public Observable<String> RxPost(String url, Map<String, String> map) {
        return Observable.create(subscriber -> {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    subscriber::onNext,
                    volleyError -> subscriber.onError(new Throwable("RxPost-error:"+(volleyError==null?"null":volleyError.getMessage())))
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return map;
                }
            };
            m.add(stringRequest);
        });
    }

    @Override
    public void Get(String url, Map<String, String> map, HttpCallBack callBack) {

        

    }

    @Override
    public void Post(String url, Map<String, String> map, HttpCallBack callBack) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                callBack::onSuccess,
                volleyError -> callBack.onFailure("Post-error:"+(volleyError==null?"null":volleyError.getMessage()))
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                LogUtils.e("");
                return map;
            }
        };
        m.add(stringRequest);
    }
}
