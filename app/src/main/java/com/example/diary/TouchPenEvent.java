package com.example.diary;

import android.graphics.Path;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;

public class TouchPenEvent implements TouchScreenEvent {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public void touch_start(MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        Log.e("touch_start : pen",  ""+mPath.toString());
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

        Log.e("touch_move : pen",  ""+mPath.toString());
    }

    public void touch_up(MotionEvent event, Path mPath) {
        float x = (event.getX() + Zoomer.get_instance().getmClipBounds().left) / Zoomer.get_instance().getScaleFactor();
        float y = (event.getY()  + Zoomer.get_instance().getmClipBounds().top) / Zoomer.get_instance().getScaleFactor();

        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw

        Log.e("touch_up : pen",  ""+mPath.toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, Path mPath) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(event, mPath);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(event, mPath);
                break;
            case MotionEvent.ACTION_UP:
                touch_up(event, mPath);
                break;
        }
        return true;
    }
}