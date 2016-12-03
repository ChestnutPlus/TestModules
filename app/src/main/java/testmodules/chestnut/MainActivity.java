package testmodules.chestnut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import chestnut.Interface.web.HttpCallBack;
import chestnut.utils.AppUtils;
import chestnut.utils.LogUtils;
import chestnut.web.HttpRequest;
import testmodules.R;
import chestnut.ui.Toastc;

public class MainActivity extends RxAppCompatActivity {

    Toastc toast = null;
    @Bind(R.id.imageView)
    ImageView imageView;

    private Context context = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toast = new Toastc(this, Toast.LENGTH_SHORT);
        initView(this);
        context = this;
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
        AppUtils.pressTwiceExitApp(this);
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
        Map<String,String> map = new HashMap<>();
        map.put("1","2");
        switch (button.getId()) {
            case R.id.button1:
                startActivity(new Intent(MainActivity.this,DialogActivity.class));
                break;

            case R.id.button2:

                HttpRequest.getInstance().Get("http://119.29.221.55/Test/TestGet.php", map, new HttpCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtils.e("onSuccess:"+result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        LogUtils.e("onFailure:"+msg);
                    }
                });
                break;

            case R.id.button3:

                HttpRequest.getInstance().RxGet("http://119.29.221.55/Test/TestGet.php",map)
                        .subscribe(
                                s -> {
                                    LogUtils.e("onSuccess:"+s);
                                },
                                throwable -> {
                                    LogUtils.e("onFailure:"+throwable.getMessage());
                                });

                break;

            case R.id.button4:
                break;

            case R.id.button5:
                HttpRequest.getInstance().RxPost("http://119.29.221.55/Test/TestGet.php",map)
                        .subscribe(
                                s -> {
                                    LogUtils.e("onSuccess:"+s);
                                },
                                throwable -> {
                                    LogUtils.e("onFailure:"+throwable.getMessage());
                                });
                break;

            case R.id.button6:

                HttpRequest.getInstance().Post("http://119.29.221.55/Test/TestPost.php", null, new HttpCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtils.e("onSuccess:"+result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        LogUtils.e("onFailure:"+msg);
                    }
                });

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
