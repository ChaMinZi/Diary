package com.example.diary;

import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchPenEvent implements TouchScreenEvent {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public void touch_start(View view, MotionEvent event, Path mPath) {
        float x = (event.getX() - GlobalValue.get_instance().getLeft()) * (1 / GlobalValue.get_instance().getmScaleFactor());
        float y = (event.getY() - GlobalValue.get_instance().getTop()) * (1 / GlobalValue.get_instance().getmScaleFactor());

        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    public void touch_move(View view, MotionEvent event, Path mPath) {
        float x = (event.getX() - GlobalValue.get_instance().getLeft()) * (1 / GlobalValue.get_instance().getmScaleFactor());
        float y = (event.getY() - GlobalValue.get_instance().getTop()) * (1 / GlobalValue.get_instance().getmScaleFactor());
        float dx = Math.abs(x - mX), dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void touch_up(View view, MotionEvent event, Path mPath) {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(View view, MotionEvent event, Path mPath) {
        if (GlobalValue.get_instance().getLeft() > event.getX()) return false;
        if (event.getX() > GlobalValue.get_instance().getRight()) return false;
        if (GlobalValue.get_instance().getTop() > event.getY()) return false;
        if (event.getY() > GlobalValue.get_instance().getBom()) return false;

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