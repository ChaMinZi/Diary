package com.example.diary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.Scroller;

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ScaleGestureDetectorCompat;


public class TouchFingerEvent implements TouchScreenEvent {

    private float mX, mY;

    public void touch_start(MotionEvent event, Path mPath) {
        mX = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        mY = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();
        Log.e("touch_start : finger",  ""+mPath.toString());
    }

    public void touch_move(MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        Zoomer.get_instance().setScroll((int)(mX-x), (int)(mY-y));

        mX = x;
        mY = y;

        Log.e("touch_move : finger",  ""+mPath.toString());
    }

    public void touch_up(MotionEvent event, Path mPath) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, Path mPath) {
        boolean retVal = false;
        if(event.getPointerCount() >= 2) {
            retVal = Zoomer.get_instance().getScaleGestureDetector().onTouchEvent(event);
            retVal = Zoomer.get_instance().getGestureDetectorCompat().onTouchEvent(event) || retVal;
        }
        else{
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(event, mPath);
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(event, mPath);
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up(event, mPath);
                    break;
            }
        }
        return retVal;
    }
}

