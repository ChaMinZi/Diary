package com.example.diary;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class CustomDialog implements View.OnTouchListener {

    private View triggerView;
    private PopupWindow window;
    protected final WindowManager windowManager;

    public CustomDialog(View triggerView, View containerView) {
        this.triggerView = triggerView;
        window = new PopupWindow(triggerView.getContext());
        window.setTouchable(true);
        window.setTouchInterceptor(this);
        windowManager = (WindowManager) triggerView.getContext().getSystemService(Context.WINDOW_SERVICE);

        window.setContentView(containerView);
        window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            this.window.dismiss();
            return true;
        }
        return false;
    }

    public void show() {
        int[] location = new int[2];
        triggerView.getLocationOnScreen(location);
        window.showAtLocation(triggerView, Gravity.NO_GRAVITY,
                location[0] + 50, location[1] + (triggerView.getHeight() / 2));
    }

}
