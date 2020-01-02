package com.example.diary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


public class CanvasActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CanvasViewPagerAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

//        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        pagerAdapter = new CanvasViewPagerAdapter(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("onPageScrolled: ", positionOffset + " " + position + " " +  positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
//                Log.e("onPageSelected: ", "" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                for(int i = 0 ; i < viewPager.getChildCount(); i++){
                    (viewPager.getChildAt(i)).findViewById(R.id.canvas_frame).setScaleX(GlobalValue.get_instance().getmScaleFactor());
                    (viewPager.getChildAt(i)).findViewById(R.id.canvas_frame).setScaleY(GlobalValue.get_instance().getmScaleFactor());
                }

            }
        });
        viewPager.setAdapter(pagerAdapter);


//        LinearLayout canvasFrame = (LinearLayout)findViewById(R.id.canvas_frame);
//        final CanvasView baseView = new CanvasView(this);
//        canvasFrame.addView(baseView);
    }
}
