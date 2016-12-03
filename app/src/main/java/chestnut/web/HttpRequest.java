package chestnut.web;

import java.io.IOException;
import java.util.Map;

import chestnut.Interface.web.Http;
import chestnut.Interface.web.HttpCallBack;
import chestnut.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;

/**
 * Created by Chestnut on 2016/11/30.
 */
public class HttpRequest implements Http<HttpCallBack,Map<String,String>,String>{

    private static HttpRequest httpRequest = new HttpRequest();
    private OkHttpClient mOkHttpClient = null;
    private HttpRequest() {}
    public static HttpRequest getInstance() {
        return httpRequest;
    }
    public void init() {
        if (this.mOkHttpClient == null) {
            mOkHttpClient=new OkHttpClient();
        }
    }

    @Override
    public Observable<String> RxGet(String url, Map<String, String> map) {
        init();
        return Observable.create(subscriber -> {
            Request request = new Request.Builder()
                    .get()
                    .url(HttpUtils.assemblyGetParam(url,map))
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    subscriber.onError(new Throwable("fail,msg:"+(e!=null?e.getMessage():"null")));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    subscriber.onNext(response.body().string());
                }
            });
        });
    }

    @Override
    public Observable<String> RxPost(String url, Map<String, String> map) {
        init();
        return Observable.create(subscriber -> {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : map.keySet()) {
                builder.add(key,map.get(key));
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    subscriber.onError(new Throwable("fail,msg:"+(e!=null?e.getMessage():"null")));
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    subscriber.onNext(response.body().string());
                }
            });
        });
    }

    @Override
    public void Get(String url, Map<String, String> map, HttpCallBack callBack) {
        init();
        Request request = new Request.Builder()
                .get()
                .url(HttpUtils.assemblyGetParam(url,map))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack!=null)
                    callBack.onFailure("fail,msg:"+(e!=null?e.getMessage():"null"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack!=null)
                    callBack.onSuccess(response.body().string());
            }
        });
    }

    @Override
    public void Post(String url, Map<String, String> map, HttpCallBack callBack) {
        init();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key,map.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack!=null)
                    callBack.onFailure("fail,msg:"+(e!=null?e.getMessage():"null"));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack!=null)
                    callBack.onSuccess(response.body().string());
            }
        });
    }

    @Override
    public void PostFile(
            String url,
            Map<String,String> map,
            String fileName,
            byte[] fileBytes,
            String fileType,
            HttpCallBack callBack
    ) {
        init();
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody = RequestBody.create(MediaType.parse(fileType),fileBytes);
        body.addFormDataPart(fileName,"fileName"+".mp3",requestBody);
        for (String key : map.keySet()) {
            body.addFormDataPart(key,map.get(key));
        }

        Request request = new Request.Builder()
                .url(url)
                .post(body.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack!=null)
                    callBack.onFailure("fail,msg:"+(e!=null?e.getMessage():"null"));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack!=null)
                    callBack.onSuccess(response.body().string());
            }
        });
    }


    @Override
    public Observable<String> RxPostFile(
            String url,
            Map<String,String> map,
            String fileName,
            String fileType,
            byte[] fileBytes
    ) {
        init();
        return Observable.create(subscriber -> {

            MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
            RequestBody requestBody = RequestBody.create(MediaType.parse(fileType),fileBytes);
            body.addFormDataPart(fileName,"fileName"+".mp3",requestBody);
            for (String key : map.keySet()) {
                body.addFormDataPart(key,map.get(key));
            }

            Request request = new Request.Builder()
                    .url(url)
                    .post(body.build())
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    subscriber.onError(new Throwable("fail,msg:"+(e!=null?e.getMessage():"null")));
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    subscriber.onNext(response.body().string());
                }
            });
        });
    }
}
