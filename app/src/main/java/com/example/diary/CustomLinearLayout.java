package com.example.diary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class CustomLinearLayout extends LinearLayout {

    private float mScaleFactor = 1.f, mMaxScaleFactor = 8f;
    private int maxCanvasWidth, maxCanvasHeight;
    private ScaleGestureDetector mScaleGestureDetector;

    private void CustomLinearLayoutInit(Context context){
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            float mScaleCalc, smallScacleCheck;
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                mScaleCalc = detector.getScaleFactor();
                smallScacleCheck = mScaleFactor;
                return super.onScaleBegin(detector);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float temp =  (detector.getScaleFactor() - mScaleCalc);
                if(mScaleFactor < 1) temp *= smallScacleCheck;
                mScaleFactor += temp;
                mScaleCalc = detector.getScaleFactor();
                mScaleFactor = Math.max(0.25f, Math.min(mScaleFactor, mMaxScaleFactor));
                findViewById(R.id.canvas_frame).setScaleX(mScaleFactor);
                findViewById(R.id.canvas_frame).setScaleY(mScaleFactor);
                return false;
            }
        });
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        maxCanvasWidth = xNew;
        maxCanvasHeight = yNew;
    }

    public CustomLinearLayout(Context context) {
        super(context);
        CustomLinearLayoutInit(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        CustomLinearLayoutInit(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomLinearLayoutInit(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        CustomLinearLayoutInit(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }
}
