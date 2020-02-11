package com.example.diary.Canvas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class CoreCanvasLinearLayout extends LinearLayout {

//    private ScaleGestureDetector mScaleGestureDetector;

    CanvasView canvasView;

    private void CustomLinearLayoutInit(Context context){
        canvasView = new CanvasView(context);
        addView(canvasView);

//        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
//            float mScaleCalc, smallScaleCheck;
//            @Override
//            public boolean onScaleBegin(ScaleGestureDetector detector) {
//                mScaleCalc = detector.getScaleFactor();
//                smallScaleCheck = GlobalValue.get_instance().getmScaleFactor();
//                return super.onScaleBegin(detector);
//            }
//
//            @Override
//            public boolean onScale(ScaleGestureDetector detector) {
//                GlobalValue.get_instance().setmScaleFactor(GlobalValue.get_instance().getmScaleFactor() + (detector.getScaleFactor() - mScaleCalc) * smallScaleCheck);
//                mScaleCalc = detector.getScaleFactor();
//                GlobalValue.get_instance().setmScaleFactor(Math.max(0.25f, Math.min(GlobalValue.get_instance().getmScaleFactor(), GlobalValue.get_instance().getmMaxScaleFactor())));
//                findViewById(R.id.canvas_frame).setScaleX(GlobalValue.get_instance().getmScaleFactor());
//                findViewById(R.id.canvas_frame).setScaleY(GlobalValue.get_instance().getmScaleFactor());
//                return false;
//            }
//        });
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
//        super.onSizeChanged(xNew, yNew, xOld, yOld);
//        GlobalValue.get_instance().setMaxCanvasWidth(xNew);
//        GlobalValue.get_instance().setMaxCanvasHeight(yNew);
    }

    public CoreCanvasLinearLayout(Context context) {
        super(context);
        CustomLinearLayoutInit(context);
    }
    public CoreCanvasLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        CustomLinearLayoutInit(context);
    }
    public CoreCanvasLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomLinearLayoutInit(context);
    }
    public CoreCanvasLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        CustomLinearLayoutInit(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return canvasView.myTouchEvent(event);
    }
}
