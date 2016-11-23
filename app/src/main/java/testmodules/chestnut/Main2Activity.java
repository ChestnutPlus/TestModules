package testmodules.chestnut;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import chestnut.utils.LogUtils;
import testmodules.R;

public class Main2Activity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
