package com.example.diary.TouchEvent;

import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchCropEvent implements TouchScreenEvent {
    private Path mPath;
    private int mStartX, mStartY, mEndX, mEndY;

    public TouchCropEvent() { mPath = new Path(); }

    @Override
    public void touch_start(View view, MotionEvent event) {
        mPath.reset();

        mStartX = (int)event.getX();
        mStartY = (int)event.getY();
    }

    @Override
    public void touch_move(View view, MotionEvent event) {

        mEndX = (int)event.getX();
        mEndY = (int)event.getY();
        if ((mStartX != mEndX) && (mStartY != mEndY)) {
            changeMinMaxPos();
            mPath.addRect((float)mStartX, (float)mStartY, (float)mEndX, (float)mEndY, Path.Direction.CCW);
        }
    }

    @Override
    public void touch_up(View view, MotionEvent event) {
        mEndX = (int)event.getX();
        mEndY = (int)event.getY();
        if ((mStartX != mEndX) && (mStartY != mEndY)) {
            changeMinMaxPos();
            mPath.addRect((float)mStartX, (float)mStartY, (float)mEndX, (float)mEndY, Path.Direction.CCW);
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
                break;
        }
        return mPath;
    }

    private void changeMinMaxPos() {
        int[] coord = new int[] {
                Math.min(mStartX, mEndX),
                Math.min(mStartY, mEndY),
                Math.max(mStartX, mEndX),
                Math.max(mStartY, mEndY)
        };
        mStartX = coord[0];
        mStartY = coord[1];
        mEndX = coord[2];
        mEndY = coord[3];
        Log.e("coord", "mStartX : "+mStartX+"  mStartY : "+mStartY+"  mEndX : "+mEndX+"  mEndY : "+mEndY);
    }
}
