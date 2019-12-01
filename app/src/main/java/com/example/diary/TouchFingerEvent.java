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

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ScaleGestureDetectorCompat;


public class TouchFingerEvent implements TouchScreenEvent {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;


    public TouchFingerEvent(Context context) {
        Zoomer.get_instance(context);
    }

    public void touch_start(MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        Log.e("touch_start : finger",  ""+mPath.toString());
    }

    public void touch_move(MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

        Log.e("touch_move : finger",  ""+mPath.toString());
    }

    public void touch_up(MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        Log.e("touch_up : finger",  ""+mPath.toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, Path mPath) {
        boolean retVal = Zoomer.get_instance().getScaleGestureDetector().onTouchEvent(event);
        retVal = Zoomer.get_instance().getGestureDetectorCompat().onTouchEvent(event) || retVal;
        return retVal;
    }
}

