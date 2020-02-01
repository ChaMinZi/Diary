package com.example.diary.ColorPicker.ColorPalette;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatDialog;

public class ColorPaletteDialog implements View.OnTouchListener {

    private View triggerView;
    private PopupWindow window;
    protected WindowManager windowManager;

    public ColorPaletteDialog(View triggerView, View containerView) {
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

//    private View view;
//    public ColorPaletteDialog(Context context, View layout) {
//        super(context);
//        view = layout;
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(view);
//    }
}
