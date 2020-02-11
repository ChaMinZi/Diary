package com.example.diary.TouchEvent;

import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class TouchOneFingerEvent implements TouchScreenEvent {

    private float x, y;
    private static final float TOUCH_TOLERANCE = 2;
    private Path mPath;

    public TouchOneFingerEvent(){
        mPath = new Path();
        Log.e("TouchOneFingerEvent", "constructor success");
    }

    public void touch_start(View view, MotionEvent event) {
        if(mPath != null) {
            mPath.reset();
            x = event.getX();
            y = event.getY();
        } else Log.e("touch_start", "there is no mPath");
    }

    public void touch_move(View view, MotionEvent event) {
        if(mPath != null) {
//            if(GlobalValue.get_instance().getmScaleFactor() > 1.0f) {
                mPath.reset();
                mPath.moveTo(x, y);
                x = event.getX();
                y = event.getY();
                mPath.lineTo(x, y);
//            } else Log.e("touch_move", "mScaleFactor is less then 1.0f");
        } else Log.e("touch_move", "there is no mPath");
    }

    public void touch_up(View view, MotionEvent event) {
    }

    @Override
    public Path onTouchEvent(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(view, event);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(view, event);
                break;
            case MotionEvent.ACTION_UP:
                touch_up(view, event);
                Log.e("onTouchEvent", "onTouchEvent Up, with Finger");
                break;
            default: Log.e("onTouchEvent", "this is not right flow, with Finger");
        }
        return mPath;
    }
}