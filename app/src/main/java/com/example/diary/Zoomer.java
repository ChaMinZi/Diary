package com.example.diary;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import androidx.core.view.GestureDetectorCompat;

import static java.lang.Math.abs;

public class Zoomer {
    private static Zoomer _instance;
    private static View view;

    private Rect mClipBounds = new Rect();

    private float mScaleFactor = 1.f;
    private float mMaxScaleFactor = 8f;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetectorCompat mGestureDetector;

    private Zoomer(){

        Log.e("Zoomer","initialize");
        mScaleGestureDetector = new ScaleGestureDetector(view.getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, mMaxScaleFactor));
                return true;
            }
        });

        mGestureDetector = new GestureDetectorCompat(view.getContext(), new GestureDetector.SimpleOnGestureListener() {

        });
    }

    public static Zoomer get_instance() {
        if (_instance == null) {
            Log.e("Zoomer","_instance is create");
            _instance = new Zoomer();
        }
        return _instance;
    }

    public static void setView(View _view) {
        view = _view;
    }

    public Rect getmClipBounds() { return mClipBounds; }

    public void setmClipBounds(Rect mClipBounds) { this.mClipBounds = mClipBounds; }

    public float getScaleFactor() { return mScaleFactor; }

    public ScaleGestureDetector getScaleGestureDetector() { return mScaleGestureDetector; }

    public GestureDetectorCompat getGestureDetectorCompat() { return mGestureDetector; }

    private static final float TOUCH_TOLERANCE = 40;

    public void setScroll(int dx, int dy){
        if (abs(dx) <= TOUCH_TOLERANCE || abs(dy) <= TOUCH_TOLERANCE) {
            view.scrollBy((int)(dx*mScaleFactor), (int)(dy*mScaleFactor));
        }
    }
}
