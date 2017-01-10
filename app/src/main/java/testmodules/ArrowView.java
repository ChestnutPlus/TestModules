package testmodules;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;

import java.util.concurrent.TimeUnit;

import chestnut.utils.LogUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.ActionN;

/**
 * Created by Chestnut on 2017/1/10.
 */

public class ArrowView extends LinearLayout {

    private final static String TAG = "ArrowView";
    private final static boolean OpenLog = true;

    private View arrow_1 = null;
    private View arrow_2 = null;
    private View arrow_3 = null;
    private Subscription subscription1 = null;

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
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        if (arrow_1==null || arrow_2==null || arrow_3==null) {
            LogUtils.i(OpenLog,TAG,"startAnim:null");
            return;
        }
        stopAnim();
        Observable.just(1)
                .map(integer -> {
                    arrow_1.startAnimation(getAlphaAnimation());
                    return integer;
                })
                .delay(200,TimeUnit.MILLISECONDS)
                .map(integer -> {
                    arrow_2.startAnimation(getAlphaAnimation());
                    return integer;
                })
                .delay(200,TimeUnit.MILLISECONDS)
                .map(integer -> {
                    arrow_3.startAnimation(getAlphaAnimation());
                    return integer;
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {},throwable -> {LogUtils.i(OpenLog,TAG,"error:"+ throwable.getMessage());});
    }

    public void stopAnim() {
        if (subscription1!=null && !subscription1.isUnsubscribed()) {
            LogUtils.i(OpenLog,TAG,"stopAnim");
            subscription1.unsubscribe();
        }
    }

    private AlphaAnimation getAlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(500);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                arrow_1.startAnimation(animation);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return alphaAnimation;
    }
}
