package com.example.diary.TouchEvent;

import android.content.Context;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.diary.GlobalValue;

public class TouchTwoFingerEvent implements TouchScreenEvent {
    private ScaleGestureDetector mScaleGestureDetector;

    public TouchTwoFingerEvent(final Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            float mScaleCalc, smallScaleCheck;
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                mScaleCalc = detector.getScaleFactor();
                smallScaleCheck = GlobalValue.get_instance().getmScaleFactor();
                return super.onScaleBegin(detector);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                GlobalValue.get_instance().setmScaleFactor(GlobalValue.get_instance().getmScaleFactor() + (detector.getScaleFactor() - mScaleCalc) * smallScaleCheck);
                mScaleCalc = detector.getScaleFactor();
                GlobalValue.get_instance().setmScaleFactor(Math.max(0.25f, Math.min(GlobalValue.get_instance().getmScaleFactor(), GlobalValue.get_instance().getmMaxScaleFactor())));
//                findViewById(R.id.canvas_frame).setScaleX(GlobalValue.get_instance().getmScaleFactor());
//                findViewById(R.id.canvas_frame).setScaleY(GlobalValue.get_instance().getmScaleFactor());
                return false;
            }
        });

        Log.e("TouchTwoFingerEvent", "constructor success");
    }


    @Override
    public void touch_start(View view, MotionEvent event) {

    }

    @Override
    public void touch_move(View view, MotionEvent event) {
    }

    @Override
    public void touch_up(View view, MotionEvent event) {

    }

    @Override
    public Path onTouchEvent(View view, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
//        Log.e("TouchTwoFingerEvent", "call onTouchEvent" + GlobalValue.get_instance().getmScaleFactor());
        return null;
    }
}
