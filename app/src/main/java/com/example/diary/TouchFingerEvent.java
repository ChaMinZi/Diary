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

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetectorCompat mGestureDetector;

    private Zoomer mZoomer;
    private float mScaleFactor = 1.f;
    private float mMaxScaleFactor = 8f;

    public TouchFingerEvent(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, mScaleGestureListener);
        mGestureDetector = new GestureDetectorCompat(context, mGestureListener);
    }


    public void touch_start(MotionEvent event, Path mPath) {
        float x = (event.getX() + mZoomer.get_instance().getmClipBounds().left) / mZoomer.get_instance().getZoomFactor();
        float y = (event.getY()  + mZoomer.get_instance().getmClipBounds().top) / mZoomer.get_instance().getZoomFactor();

        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        Log.e("touch_start : finger",  ""+mPath.toString());
    }

    public void touch_move(MotionEvent event, Path mPath) {
        float x = (event.getX() + mZoomer.get_instance().getmClipBounds().left) / mZoomer.get_instance().getZoomFactor();
        float y = (event.getY()  + mZoomer.get_instance().getmClipBounds().top) / mZoomer.get_instance().getZoomFactor();

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
        float x = (event.getX() + mZoomer.get_instance().getmClipBounds().left) / mZoomer.get_instance().getZoomFactor();
        float y = (event.getY()  + mZoomer.get_instance().getmClipBounds().top) / mZoomer.get_instance().getZoomFactor();

        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        Log.e("touch_up : finger",  ""+mPath.toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, Path mPath) {
        boolean retVal = mScaleGestureDetector.onTouchEvent(event);
        retVal = mGestureDetector.onTouchEvent(event) || retVal;
        return retVal;
    }

    /**
        * The scale listener, used for handling multi-finger scale gestures.
     */
    private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
            = new ScaleGestureDetector.SimpleOnScaleGestureListener() {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, mMaxScaleFactor));
            //mScaleFactor =  mScaleFactor > mMaxScaleFactor ? mMaxScaleFactor : mScaleFactor < 0.1f ? 0.1f : mScaleFactor;
            mZoomer.get_instance().setZoomFactor(mScaleFactor);
            return true;
        }
    };

    private final GestureDetector.SimpleOnGestureListener mGestureListener
            = new GestureDetector.SimpleOnGestureListener() {

    };
}

