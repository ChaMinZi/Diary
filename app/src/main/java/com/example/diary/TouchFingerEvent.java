package com.example.diary;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;


public class TouchFingerEvent implements TouchScreenEvent {

    public void touch_start(View view, MotionEvent event, Path mPath) {

    }
    public void touch_move(View view, MotionEvent event, Path mPath) {

    }
    public void touch_up(View view, MotionEvent event, Path mPath) {

    }

    @Override
    public boolean onTouchEvent(View view, MotionEvent event, Path mPath) {




        return true;
    }
}

