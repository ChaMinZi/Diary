package com.example.diary.ColorPicker.ColorPalette;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;

public class ColorPaletteDialog extends AppCompatDialog {
    private View view;

    public ColorPaletteDialog(Context context, View layout) {
        super(context);
        view = layout;
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
    }
}
