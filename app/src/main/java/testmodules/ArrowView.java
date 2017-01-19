package testmodules;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import java.util.concurrent.TimeUnit;

import chestnut.utils.LogUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Chestnut on 2017/1/10.
 */

public class ArrowView extends LinearLayout {

    private final static String TAG = "ArrowView";
    private final static boolean OpenLog = true;

    private View arrow_1 = null;
    private View arrow_2 = null;
    private View arrow_3 = null;
    private Subscription subscription = null;
    private boolean isRepeat_1 = true;
    private boolean isRepeat_2 = true;
    private boolean isRepeat_3 = true;
    private AlphaAnimation alphaAnimation1;
    private AlphaAnimation alphaAnimation2;
    private AlphaAnimation alphaAnimation3;
    private int arrowDownFlag = 2;  //默认为向上：0，向下：2.

    public ArrowView(Context context) {
        super(context);
    }

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //导入布局
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View linearLayout = inflater.inflate(R.layout.diy_view_arrow_layout,this,true);
        arrow_1 = linearLayout.findViewById(R.id.arrow_1);
        arrow_2 = linearLayout.findViewById(R.id.arrow_2);
        arrow_3 = linearLayout.findViewById(R.id.arrow_3);
        arrow_1.setVisibility(VISIBLE);
        arrow_2.setVisibility(VISIBLE);
        arrow_3.setVisibility(VISIBLE);
        LogUtils.i(OpenLog,TAG,"ArrowView");
    }

    /**
     * 设置箭头向上
     */
    public ArrowView setArrowUp() {
        if (arrow_1==null || arrow_2==null || arrow_3==null) {
            LogUtils.i(OpenLog,TAG,"setArrowUp:null");
            return this;
        }
        arrowDownFlag = 0;
        arrow_1.setBackgroundResource(R.drawable.arrow_up);
        arrow_2.setBackgroundResource(R.drawable.arrow_up);
        arrow_3.setBackgroundResource(R.drawable.arrow_up);
        return this;
    }

    /**
     * 设置箭头向下
     */
    public ArrowView setArrowDown() {
        if (arrow_1==null || arrow_2==null || arrow_3==null) {
            LogUtils.i(OpenLog,TAG,"setArrowDown:null");
            return this;
        }
        arrowDownFlag = 2;
        arrow_1.setBackgroundResource(R.drawable.arrow_down);
        arrow_2.setBackgroundResource(R.drawable.arrow_down);
        arrow_3.setBackgroundResource(R.drawable.arrow_down);
        return this;
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        LogUtils.i(OpenLog,TAG,"startAnim");
        if (arrow_1==null || arrow_2==null || arrow_3==null) {
            LogUtils.i(OpenLog,TAG,"startAnim:null");
            return;
        }
        stopAnim();
        isRepeat_1 = true;
        isRepeat_2 = true;
        isRepeat_3 = true;
        Observable<Long> observable = Observable.interval(200,TimeUnit.MILLISECONDS);
        subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer==(2-arrowDownFlag)) {
                        LogUtils.i(OpenLog,TAG,"1");
                        alphaAnimation1 = new AlphaAnimation(0.1f,1.0f);
                        alphaAnimation1.setRepeatCount(1);
                        alphaAnimation1.setRepeatMode(Animation.REVERSE);
                        alphaAnimation1.setDuration(500);
                        alphaAnimation1.setFillAfter(true);
                        alphaAnimation1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (isRepeat_1)
                                    arrow_1.startAnimation(animation);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        arrow_1.startAnimation(alphaAnimation1);
                    }
                    else if (integer==1) {
                        LogUtils.i(OpenLog,TAG,"2");
                        alphaAnimation2 = new AlphaAnimation(0.1f,1.0f);
                        alphaAnimation2.setRepeatCount(1);
                        alphaAnimation2.setRepeatMode(Animation.REVERSE);
                        alphaAnimation2.setDuration(500);
                        alphaAnimation2.setFillAfter(true);
                        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (isRepeat_2)
                                    arrow_2.startAnimation(animation);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        arrow_2.startAnimation(alphaAnimation2);
                    }
                    else if (integer==arrowDownFlag) {
                        LogUtils.i(OpenLog,TAG,"3");
                        alphaAnimation3 = new AlphaAnimation(0.1f,1.0f);
                        alphaAnimation3.setRepeatCount(1);
                        alphaAnimation3.setRepeatMode(Animation.REVERSE);
                        alphaAnimation3.setDuration(500);
                        alphaAnimation3.setFillAfter(true);
                        alphaAnimation3.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (isRepeat_3)
                                    arrow_3.startAnimation(animation);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        arrow_3.startAnimation(alphaAnimation3);
                    }
                    else {
                        subscription.unsubscribe();
                    }
                },throwable -> {LogUtils.i(OpenLog,TAG,"error:"+ throwable.getMessage());});
    }

    public void stopAnim() {
        if (alphaAnimation1!=null && alphaAnimation2!=null && alphaAnimation3!=null) {
            LogUtils.i(OpenLog, TAG, "stopAnim");
            isRepeat_1 = false;
            isRepeat_2 = false;
            isRepeat_3 = false;
            alphaAnimation1.cancel();
            alphaAnimation2.cancel();
            alphaAnimation3.cancel();
            arrow_1.clearAnimation();
            arrow_2.clearAnimation();
            arrow_3.clearAnimation();
            alphaAnimation1 = null;
            alphaAnimation2 = null;
            alphaAnimation3 = null;
        }
    }
}
