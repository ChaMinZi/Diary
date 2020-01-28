package com.example.diary.Canvas;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.diary.ColorPicker.ColorWheelView;
import com.example.diary.CustomDialog;
import com.example.diary.GlobalValue;
import com.example.diary.R;

public class CanvasActivity extends AppCompatActivity {

    private CanvasViewPager viewPager;
    private CanvasViewPagerAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // if you want to fix colorWheelView.getView() Refer to "Fix ColorPickerDialog" in sourcetree
        final ColorWheelView colorWheelView = new ColorWheelView(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View colorPickerView = inflater.inflate(R.layout.dialog_colorpicker, null, false);
        ((FrameLayout)colorPickerView.findViewById(R.id.dialog_colorpicker)).addView(colorWheelView.getView());

        final View penDialogView = inflater.inflate(R.layout.dialog_thickness, null, false);
        final View eraseDialogView = inflater.inflate(R.layout.dialog_thickness, null, false);

        //init
        GlobalValue.get_instance().setPenMode();

        final Toolbar mDrawbar = (Toolbar)findViewById(R.id.drawbar);
        mDrawbar.inflateMenu(R.menu.drawbar_action);
        mDrawbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_pen:
                        if (GlobalValue.get_instance().getMode() == 1)
                            (new CustomDialog(mDrawbar, penDialogView)).show();
                        else
                            GlobalValue.get_instance().setPenMode();
                        return true;
                    case R.id.action_highlighter:
                        return true;
                    case R.id.action_eraser:
                        if (GlobalValue.get_instance().getMode() == 3)
                            (new CustomDialog(mDrawbar, eraseDialogView)).show();
                        else
                            GlobalValue.get_instance().setEraseMode();
                        return true;
                    case R.id.action_colorpalette:
                        (new CustomDialog(mDrawbar, colorPickerView)).show();
                        //colorPickerDialog.show(getSupportFragmentManager()," tag");
                        return true;
                }
                return false;
            }
        });

        viewPager = (CanvasViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new CanvasViewPagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_plus:
                return true;
        }
        return false;
    }

    public void showPopup(final Context context, View triggerView) {
        LinearLayout viewGroup = (LinearLayout) triggerView.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_colorwheel, viewGroup);

        PopupWindow popup = new PopupWindow(context);

        popup.setContentView(layout);
        popup.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        // Clear the default translucent background
        popup.setBackgroundDrawable(new ColorDrawable());

        // Displaying the popup at the specified location, + offsets.

        int[] location = new int[2];
        triggerView.getLocationOnScreen(location);
        popup.showAtLocation(triggerView, Gravity.NO_GRAVITY,
                location[0] + 50, location[1] + (triggerView.getHeight() / 2));
    }
}