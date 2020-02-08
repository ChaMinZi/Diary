package com.example.diary.Canvas;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.diary.ColorPicker.ColorPalette.ColorPalette;
import com.example.diary.ColorPicker.ColorWheel.ColorWheelView;
import com.example.diary.CustomDialog;
import com.example.diary.Enum.TouchType;
import com.example.diary.GlobalValue;
import com.example.diary.R;



public class CanvasActivity extends AppCompatActivity {

    private CanvasViewPager viewPager;
    private CanvasViewPagerAdapter pagerAdapter;

    private ColorPalette.OnFastChooseColorListener onFastChooseColorListener;

    private int selectedColorPicker;
    private FrameLayout colorPickerFrame;

    public CanvasActivity() {
        onFastChooseColorListener = new ColorPalette.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                GlobalValue.get_instance().setColor(color);
            }

            @Override
            public void onCancel() {

            }
        };
        selectedColorPicker = 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View colorPickerView = inflater.inflate(R.layout.dialog_colorpicker, null, false);
        final ColorPalette colorPalette = new ColorPalette(this, colorPickerView);
        final ColorWheelView colorWheelView = new ColorWheelView(this);
        colorPickerFrame = ((FrameLayout)colorPickerView.findViewById(R.id.dialog_colorpicker));

        final Toolbar mDrawbar = (Toolbar)findViewById(R.id.drawbar);
        (colorPickerView.findViewById(R.id.wheel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColorPicker = 0;
                colorPickerFrame.removeAllViews();
                colorPickerFrame.addView(colorWheelView.getView());
            }
        });
        (colorPickerView.findViewById(R.id.palette_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColorPicker = 1;
                colorPalette.refresh();
            }
        });
        (colorPickerView.findViewById(R.id.classic_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColorPicker = 2;
            }
        });

        final View penDialogView = inflater.inflate(R.layout.dialog_thickness, null, false);
        final View eraseDialogView = inflater.inflate(R.layout.dialog_thickness, null, false);

        //init
        GlobalValue.get_instance().setMode(TouchType.PEN);

        mDrawbar.inflateMenu(R.menu.drawbar_action);
        mDrawbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_pen:
                        if (GlobalValue.get_instance().getMode() == TouchType.PEN)
                            (new CustomDialog(mDrawbar, penDialogView)).show();
                        else
                            GlobalValue.get_instance().setMode(TouchType.PEN);
                        return true;
                    case R.id.action_highlighter:
                        return true;
                    case R.id.action_eraser:
                        if (GlobalValue.get_instance().getMode() == TouchType.ERASER)
                            (new CustomDialog(mDrawbar, eraseDialogView)).show();
                        else
                            GlobalValue.get_instance().setMode(TouchType.ERASER);
                        return true;
                    case R.id.action_cut:
                        GlobalValue.get_instance().setMode(TouchType.CROP);
                        return true;
                    case R.id.action_colorpalette:
                        if (selectedColorPicker == 0) {
                            colorPickerFrame.removeAllViews();
                            colorPickerFrame.addView(colorWheelView.getView());
                            (new CustomDialog(mDrawbar, colorPickerView)).show();
                        }
                        else if (selectedColorPicker == 1) {
                            colorPalette.setOnFastChooseColorListener(onFastChooseColorListener)
                                    .setColumns(5)
                                    .setRoundColorButton(true)
                                    .show(mDrawbar);
                        }
                        else {

                        }
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
}