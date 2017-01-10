package testmodules.chestnut;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import chestnut.utils.AppUtils;
import chestnut.utils.ConvertUtils;
import chestnut.utils.FileUtils;
import chestnut.utils.LogUtils;
import chestnut.utils.SDCardUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import testmodules.ArrowView;
import testmodules.R;
import chestnut.ui.Toastc;

public class MainActivity extends RxAppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static boolean OpenLog = true;

    Toastc toast = null;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.ArrowView)
    ArrowView arrowView;

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

    AlphaAnimation alphaAnimation = null;

    @OnClick({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7})
    public void btnClicks(Button button) {
        switch (button.getId()) {
            case R.id.button1:
                startActivity(new Intent(MainActivity.this,DialogActivity.class));
                break;

            case R.id.button2:
                arrowView.startAnim();
                break;

            case R.id.button3:
                arrowView.stopAnim();
                break;

            case R.id.button4:
                alphaAnimation = new AlphaAnimation(1.0f,0.0f);
                alphaAnimation.setRepeatCount(1);
                alphaAnimation.setRepeatMode(Animation.REVERSE);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        LogUtils.i(OpenLog,TAG,"onAnimationStart.");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (animation!=null)
                            arrowView.startAnimation(animation);
                        LogUtils.i(OpenLog,TAG,"onAnimationEnd.");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                arrowView.startAnimation(alphaAnimation);
                break;

            case R.id.button5:
                if (alphaAnimation!=null) {
                    LogUtils.i(OpenLog,TAG,"end.");
                    arrowView.clearAnimation();
                    alphaAnimation.cancel();
                    alphaAnimation = null;
                }
                break;

            case R.id.button6:
                break;

            case R.id.button7:
                startActivity(new Intent(this,DialogActivity.class));
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
