package com.example.diary.Canvas;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.diary.GlobalValue;
import com.example.diary.R;

public class CanvasViewPager extends ViewPager {

    private int currentId = 0;

    public CanvasViewPager(@NonNull Context context) {
        super(context);
        ViewPagetInit();
    }

    public CanvasViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewPagetInit();
    }

    void ViewPagetInit(){
        addOnPageChangeListener(onPageChangeListener);
        setPageTransformer(true, zoomOutPageTransformer);
    }

    PageTransformer zoomOutPageTransformer =  new PageTransformer() {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    };

    OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
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
            for(int i = 0 ; i < getChildCount(); i++){
//                getChildAt(i).findViewById(R.id.canvas_frame).setScaleX(GlobalValue.get_instance().getmScaleFactor());
//                getChildAt(i).findViewById(R.id.canvas_frame).setScaleY(GlobalValue.get_instance().getmScaleFactor());
            }

        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(event == null)
            return false;
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getSize() > 0 && ev.getPointerCount() == 1) {  //손꾸락 & 1개
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    onInterceptTouchEvent(ev);
                    break;
                default:
                    onInterceptTouchEvent(null);
            }
            return super.onTouchEvent(ev);
        }
        return getChildAt(0).onTouchEvent(ev); //TODO : fix to current child
    }
}
