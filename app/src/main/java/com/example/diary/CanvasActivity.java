package com.example.diary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.diary.ColorPicker.ColorPicker;
import com.example.diary.ColorPicker.ColorPickerDialog;
import com.example.diary.ColorPicker.ColorWheelFragment;
import com.example.diary.ColorPicker.ColorWheelView;

public class CanvasActivity extends AppCompatActivity {

    private CanvasViewPager viewPager;
    private CanvasViewPagerAdapter pagerAdapter;
    private ColorPickerDialog colorPickerDialog = new ColorPickerDialog();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ColorWheelView colorWheelView = new ColorWheelView(this);

        final Toolbar mDrawbar = (Toolbar)findViewById(R.id.drawbar);
        mDrawbar.inflateMenu(R.menu.drawbar_action);
        mDrawbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_pen:
                        return true;
                    case R.id.action_highlighter:
                        return true;
                    case R.id.action_eraser:
                        return true;
                    case R.id.action_colorpalette:
                        CustomDialog customDialog = new CustomDialog(mDrawbar, colorWheelView);
                        customDialog.show();
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
