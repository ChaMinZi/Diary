package com.example.diary;

import android.graphics.Path;
import android.view.MotionEvent;

public interface TouchScreenEvent {
    public void touch_start(MotionEvent event, Path mPath);
    public void touch_move(MotionEvent event, Path mPath);
    public void touch_up(MotionEvent event, Path mPath);
}
