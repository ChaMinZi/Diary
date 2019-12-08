package com.example.diary;

import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchPenEvent implements TouchScreenEvent {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public void touch_start(View view, MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        Log.e("touch_start : pen",  ""+mPath.toString());
    }

    public void touch_move(View view,  MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

        Log.e("touch_move : pen",  ""+mPath.toString());
    }

    public void touch_up(View view, MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw

        Log.e("touch_up : pen",  ""+mPath.toString());
    }

    @Override
    public boolean onTouchEvent(View view, MotionEvent event, Path mPath) {
        Log.e("view", ""+view.toString());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(view, event, mPath);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(view, event, mPath);
                break;
            case MotionEvent.ACTION_UP:
                touch_up(view, event, mPath);
                break;
        }
        return true;
    }
}