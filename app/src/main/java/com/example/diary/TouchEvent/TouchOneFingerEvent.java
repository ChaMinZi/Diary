package com.example.diary.TouchEvent;

import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class TouchOneFingerEvent implements TouchScreenEvent {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE_WIDTH = 100;
    private static final float TOUCH_TOLERANCE_HEIGHT = 30;
    private Path mPath;

    public TouchOneFingerEvent(){
        mPath = new Path();
        Log.e("TouchOneFingerEvent", "constructor success");
    }

    public void touch_start(View view, MotionEvent event) {
        if(mPath != null) {
            mPath.reset();

//            float x = (event.getX() - GlobalValue.get_instance().getLeft()) * (1 / GlobalValue.get_instance().getmScaleFactor());
//            float y = (event.getY() - GlobalValue.get_instance().getTop()) * (1 / GlobalValue.get_instance().getmScaleFactor());
            float x = event.getX(), y = event.getY();

            mPath.moveTo(x, y);
        } else Log.e("touch_start", "there is no mPath");
    }

    public void touch_move(View view, MotionEvent event) {
    }

    public void touch_up(View view, MotionEvent event) {
        if (true) {  // TODO change to scale &  scroll
            float x = event.getX(), y = event.getY();
            float dx = Math.abs(x - mX), dy = Math.abs(y - mY);
            Log.e("touch_up", ""+dx +" " + dy);

            if (dx >= TOUCH_TOLERANCE_WIDTH && dy >= TOUCH_TOLERANCE_HEIGHT) {
                mPath.lineTo(x, y);
                Log.e("touch_up", "move page!!");
            } else Log.e("touch_up", "move Insufficient");
        }
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

