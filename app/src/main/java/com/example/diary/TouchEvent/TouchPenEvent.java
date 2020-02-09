package com.example.diary.TouchEvent;

        import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchPenEvent implements TouchScreenEvent {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private Path mPath;

    public TouchPenEvent(){
        mPath = new Path();
        Log.e("TouchPenEvent", "constructor success");
    }

    public void touch_start(View view, MotionEvent event) {
        if(mPath != null) {
            mPath.reset();
            float x = event.getX();
            float y = event.getY();

            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        } else Log.e("touch_start", "there is no mPath");
    }

    public void touch_move(View view, MotionEvent event) {
        if(mPath != null) {
            float x = event.getX();
            float y = event.getY();
            float dx = Math.abs(x - mX), dy = Math.abs(y - mY);

            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        } else Log.e("touch_move", "there is no mPath");
    }

    public void touch_up(View view, MotionEvent event) {
        if(mPath != null) {
            mPath.lineTo(mX, mY);
        } else Log.e("touch_up", "there is no mPath");
    }

    @Override
    public Path onTouchEvent(View view, MotionEvent event) {
//        if (GlobalValue.get_instance().getLeft() > event.getX()) return null;
//        if (event.getX() > GlobalValue.get_instance().getRight()) return null;
//        if (GlobalValue.get_instance().getTop() > event.getY()) return null;
//        if (event.getY() > GlobalValue.get_instance().getBom()) return null;

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
            default: Log.e("onTouchEvent", "this is not right flow, with Pen");
        }
        return mPath;
    }
}