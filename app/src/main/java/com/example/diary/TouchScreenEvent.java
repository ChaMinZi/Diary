package com.example.diary;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public interface TouchScreenEvent {
    public void touch_start(View view, MotionEvent event, Path mPath);
    public void touch_move(View view, MotionEvent event, Path mPath);
    public void touch_up(View view, MotionEvent event, Path mPath);
    public boolean onTouchEvent(View view, MotionEvent event, Path mPath);
}
