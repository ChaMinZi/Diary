package com.example.diary;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;

public class Zoomer {
    private static Zoomer _instance;
    private static View rootView;

    private Rect mClipBounds = new Rect();

    private float mScaleFactor = 1.f;
    private float mMaxScaleFactor = 8f;

    private ScaleGestureDetector mScaleGestureDetector;

    LinearLayout canvasFrame;

    private int maxCanvasWidth, maxCanvasHeight;
    private Zoomer(){
        canvasFrame = (LinearLayout)rootView.findViewById(R.id.canvas_frame);
        maxCanvasWidth = rootView.getWidth();
        maxCanvasHeight = rootView.getHeight();

        Log.e("Zoomer","initialize");
        mScaleGestureDetector = new ScaleGestureDetector(rootView.getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.25f, Math.min(mScaleFactor, mMaxScaleFactor));
                rootView.getLayoutParams().width = (int) (maxCanvasWidth * mScaleFactor);
                rootView.getLayoutParams().height = (int) (maxCanvasHeight * mScaleFactor);
                rootView.requestLayout();
                return false;
            }
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
        rootView = _view;
    }

    public Rect getmClipBounds() { return mClipBounds; }

    public void setmClipBounds(Rect mClipBounds) { this.mClipBounds = mClipBounds; }

    public float getScaleFactor() { return mScaleFactor; }

    public ScaleGestureDetector getScaleGestureDetector() { return mScaleGestureDetector; }

}
