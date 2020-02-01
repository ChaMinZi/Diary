package com.example.diary.Canvas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.example.diary.ColorPicker.ColorPalette.ColorPalette;
import com.example.diary.ColorPicker.ColorWheel.ColorWheelView;
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

        final ColorPalette colorPalette = new ColorPalette(this);

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
                    case R.id.action_cut:
                        return true;
                    case R.id.action_colorpalette:
                        colorPalette.setOnChooseColorListener(new ColorPalette.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {

                            }

                            @Override
                            public void onCancel() {

                            }
                        }).setColumns(5)
                                .setRoundColorButton(true)
                                .show();
                        //(new CustomDialog(mDrawbar, colorPickerView)).show();
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

    private void onButtonPressed(int id) {

    }
}