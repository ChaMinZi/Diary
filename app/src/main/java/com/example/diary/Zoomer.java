package com.example.diary;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;

import androidx.core.view.GestureDetectorCompat;

public class Zoomer {
    private static Zoomer _instance;
    private static Context context;

    private Rect mClipBounds = new Rect();

    private float mScaleFactor = 1.f;
    private float mMaxScaleFactor = 8f;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetectorCompat mGestureDetector;

    private Zoomer(){

        Log.e("Zoomer","initialize");
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, mMaxScaleFactor));
                Log.e("Zoomer","onScale"+mScaleFactor);
                return true;
            }
        });

        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {

        });
    }

    public static Zoomer get_instance() {
        if (_instance == null) {
            Log.e("Zoomer","_instance is create");
            _instance = new Zoomer();
        }
        return _instance;
    }

    public static Zoomer get_instance(Context _context) {
        if (_instance == null) {
            Log.e("Zoomer","_instance is create with context");
            context = _context;  //must set before detectors.
            _instance = new Zoomer();
        }
        return _instance;
    }

    public Rect getmClipBounds() { return mClipBounds; }

    public void setmClipBounds(Rect mClipBounds) { this.mClipBounds = mClipBounds; }

    public float getScaleFactor() { return mScaleFactor; }

    public ScaleGestureDetector getScaleGestureDetector() { return mScaleGestureDetector; }

    public GestureDetectorCompat getGestureDetectorCompat() { return mGestureDetector; }
}
