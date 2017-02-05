package testmodules.chestnut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import chestnut.ui.DialogLoading;
import chestnut.ui.DialogNote;
import chestnut.ui.Toastc;
import chestnut.utils.AppUtils;
import chestnut.utils.CameraUtils;
import chestnut.utils.LogUtils;
import testmodules.ArrowView;
import testmodules.R;
import testmodules.WeChatUtils;

public class MainActivity extends RxAppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static boolean OpenLog = true;

    Toastc toast = null;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.ArrowView)
    ArrowView arrowView;

    private Context context = null;
    private DialogLoading dialogLoading;
    private DialogNote dialogNote;
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
        dialogLoading = new DialogLoading(this);
        dialogNote = new DialogNote(this);
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
        Bitmap bitmap = CameraUtils.getBitmapFromCG(this,requestCode,0,data,1,1,300,300,true,this.getCacheDir()+"/cutHeadPhotoTemp.jpg");
        imageView.setImageBitmap(bitmap);
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
                WeChatUtils weChatUtils = new WeChatUtils(this,"wxc7dd48aa3239f58f");
                weChatUtils.sharedTxt(WeChatUtils.FLAG_SESSION,"wagnnimjflsdjlkf");
                break;

            case R.id.button3:
                CameraUtils.getHeadCropPhotoFromGallery(this);
                break;

            case R.id.button4:
                break;

            case R.id.button5:
                break;

            case R.id.button6:
                break;

            case R.id.button7:
                startActivity(new Intent(this,WebActivity.class));
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
