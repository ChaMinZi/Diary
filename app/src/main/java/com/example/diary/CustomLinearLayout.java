package com.example.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class CustomLinearLayout extends LinearLayout {


    private ScaleGestureDetector mScaleGestureDetector;


    private void CustomLinearLayoutInit(Context context){
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            float mScaleCalc, smallScacleCheck;
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                mScaleCalc = detector.getScaleFactor();
                smallScacleCheck = GlobalValue.get_instance().getmScaleFactor();
                return super.onScaleBegin(detector);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float temp =  (detector.getScaleFactor() - mScaleCalc);
                if(GlobalValue.get_instance().getmScaleFactor() < 1) temp *= smallScacleCheck;
                GlobalValue.get_instance().setmScaleFactor(GlobalValue.get_instance().getmScaleFactor() + temp);
                mScaleCalc = detector.getScaleFactor();
                GlobalValue.get_instance().setmScaleFactor(Math.max(0.25f, Math.min(GlobalValue.get_instance().getmScaleFactor(), GlobalValue.get_instance().getmMaxScaleFactor())));
                findViewById(R.id.canvas_frame).setScaleX(GlobalValue.get_instance().getmScaleFactor());
                findViewById(R.id.canvas_frame).setScaleY(GlobalValue.get_instance().getmScaleFactor());
                return false;
            }
        });
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        GlobalValue.get_instance().setMaxCanvasWidth(xNew);
        GlobalValue.get_instance().setMaxCanvasHeight(yNew);
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
        if (event.getPointerCount() > 1) {
            mScaleGestureDetector.onTouchEvent(event);
            requestLayout();
        }
        else {
            ViewGroup viewGroup = findViewById(R.id.canvas_frame);
            for(int index = 0; index<viewGroup.getChildCount(); ++index) {
                CanvasView nextChild = (CanvasView)viewGroup.getChildAt(index);
                nextChild.myTouchEvent(event);
            }
        }
        return true;
    }
}
