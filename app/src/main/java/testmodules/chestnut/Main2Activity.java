package testmodules.chestnut;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import chestnut.utils.BarUtils;
import testmodules.R;
import testmodules.VIewPager.MyViewPagerAdapter;
import testmodules.VIewPager.RecyclerView.TestAdapter;

public class Main2Activity extends RxAppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<View> viewContainter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.hideStatusBar(this);
        setContentView(R.layout.activity_main2);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerTabStrip tabTxt = (PagerTabStrip) this.findViewById(R.id.tabTxt);
        //取消tab下面的长横线
        tabTxt.setDrawFullUnderline(false);
        //设置tab的背景色
        tabTxt.setBackgroundColor(this.getResources().getColor(R.color.blue_btn_bg_color));
        //设置当前tab页签的下划线颜色
        tabTxt.setTabIndicatorColor(this.getResources().getColor(R.color.black_overlay));
        tabTxt.setTextSpacing(200);

        View view1 = LayoutInflater.from(this).inflate(R.layout.view_1,null);
        RecyclerView recyclerView1 = (RecyclerView)view1.findViewById(R.id.recyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new TestAdapter());

        View view2 = LayoutInflater.from(this).inflate(R.layout.view_2,null);
        RecyclerView recyclerView2 = (RecyclerView)view2.findViewById(R.id.recyclerView);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(new TestAdapter());

        viewContainter.add(view1);
        viewContainter.add(view2);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(viewContainter);
        viewPager.setAdapter(myViewPagerAdapter);
    }
}
