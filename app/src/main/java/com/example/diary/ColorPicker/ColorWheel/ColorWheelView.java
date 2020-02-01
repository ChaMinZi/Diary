package com.example.diary.ColorPicker.ColorWheel;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.diary.ColorPicker.ColorWheel.ColorPicker;
import com.example.diary.ColorPicker.ColorWheel.OpacityBar;
import com.example.diary.ColorPicker.ColorWheel.SVBar;
import com.example.diary.ColorPicker.ColorWheel.SaturationBar;
import com.example.diary.ColorPicker.ColorWheel.ValueBar;
import com.example.diary.GlobalValue;
import com.example.diary.R;

public class ColorWheelView extends View {
    public View getView() {
        return view;
    }
    View view = null;

    private ColorPicker picker;

    public ColorWheelView(Context context) {
        super(context);
        createView(context);
    }

    public ColorWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public ColorWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context);
    }

    public ColorWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createView(context);
    }

    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_colorwheel, null, false);

        picker = (ColorPicker) view.findViewById(R.id.colorpicker);
        SVBar svBar = (SVBar) view.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) view.findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) view.findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) view.findViewById(R.id.valuebar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

//To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
// adds listener to the colorpicker which is implemented
//in the activity
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                GlobalValue.get_instance().setColor(color);
            }
        });

//to turn of showing the old color
        picker.setShowOldCenterColor(true);

//adding onChangeListeners to bars
        opacityBar.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
            @Override
            public void onOpacityChanged(int opacity) {
                GlobalValue.get_instance().setOpacity(opacity);
            }
        });
        valueBar.setOnValueChangedListener(new ValueBar.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {

            }
        });
        saturationBar.setOnSaturationChangedListener(new SaturationBar.OnSaturationChangedListener() {
            @Override
            public void onSaturationChanged(int saturation) {

            }
        });

        return view;
    }
}
