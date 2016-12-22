package testmodules.chestnut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import chestnut.utils.AppUtils;
import chestnut.utils.ConvertUtils;
import chestnut.utils.EncryptUtils;
import chestnut.utils.FileUtils;
import chestnut.utils.LogUtils;
import chestnut.utils.StringUtils;
import chestnut.web.HttpRequest;
import rx.Observable;
import rx.functions.Func1;
import testmodules.R;
import chestnut.ui.Toastc;

public class MainActivity extends RxAppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static boolean OpenLog = true;

    Toastc toast = null;
    @Bind(R.id.imageView)
    ImageView imageView;

    private Context context = null;
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivity;
        MyHandler(MainActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    break;
            }
        }
    }
    private MyHandler myHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toast = new Toastc(this, Toast.LENGTH_SHORT);
        initView(this);
        context = this;
        myHandler = new MyHandler(this);
    }
    @Override
    protected void onPause() {
        LogUtils.e("onPause");
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {
        AppUtils.pressTwiceExitApp(this, "王尼玛你要退出！？", 3000, new AppUtils.ExitAppCallBack() {
            @Override
            public void firstAsk() {
                LogUtils.e("firstAsk");
            }

            @Override
            public void beginExit() {
                LogUtils.e("beginExit");
            }
        });
    }

    private void initView(Activity activity) {
        ((Button)activity.findViewById(R.id.button1)).setText(btnNames[0]);
        ((Button)activity.findViewById(R.id.button2)).setText(btnNames[1]);
        ((Button)activity.findViewById(R.id.button3)).setText(btnNames[2]);
        ((Button)activity.findViewById(R.id.button4)).setText(btnNames[3]);
        ((Button)activity.findViewById(R.id.button5)).setText(btnNames[4]);
        ((Button)activity.findViewById(R.id.button6)).setText(btnNames[5]);
        ((Button)activity.findViewById(R.id.button7)).setText(btnNames[6]);
    }
    private String[] btnNames = {
            "1_"+"get",
            "2_"+"RxGet",
            "3_"+"ToastNote",
            "4_"+"DialogLoading",
            "5_"+"",
            "6_"+"post",
            "7_"+"Main2Activity",
    };

    @OnClick({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7})
    public void btnClicks(Button button) {
        switch (button.getId()) {
            case R.id.button1:
                startActivity(new Intent(MainActivity.this,DialogActivity.class));
                break;

            case R.id.button2:

                Integer[] a = {1,2,3,4,5};
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                list.add(5);
                Observable.from(list)
                        .map(integer -> {
                            LogUtils.e(OpenLog,TAG,"map:"+integer);
                            return integer;
                        })
                        .flatMap(new Func1<Integer, Observable<Result>>() {
                            @Override
                            public Observable<Result> call(Integer integer) {

                                HttpParams httpParams = new HttpParams();
                                httpParams.put("1","key");

                                return new RxVolley.Builder()
                                        .url("http://119.29.221.55/Test/TestPost.php")
                                        .params(httpParams)
                                        .httpMethod(RxVolley.Method.POST)
                                        .getResult();
//                                return HttpRequest
//                                        .getInstance()
//                                        .RxPost("http://119.29.221.55/Test/TestPost.php",new HashMap<>());
                            }
                        })
                        .map(result -> new String(result.data))
                        .subscribe(
                                s -> LogUtils.e(OpenLog,TAG,"onNext"+s),
                                throwable -> LogUtils.e(OpenLog,TAG,"throwable"+throwable.getMessage()),
                                () -> LogUtils.e(OpenLog,TAG,"completed")
                        );


                break;

            case R.id.button3:
                break;

            case R.id.button4:
                break;

            case R.id.button5:
                break;

            case R.id.button6:
                break;

            case R.id.button7:
                startActivity(new Intent(this,Main2Activity.class));
                break;
        }
    }

    @OnLongClick({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7})
    public boolean longClicks(Button button) {
        switch (button.getId()) {
            case R.id.button1:
                break;

            case R.id.button2:
                break;

            case R.id.button3:
                break;

            case R.id.button4:
                break;

            case R.id.button5:
                break;

            case R.id.button6:
                break;

            case R.id.button7:
                break;
        }
        return true;
    }
}
