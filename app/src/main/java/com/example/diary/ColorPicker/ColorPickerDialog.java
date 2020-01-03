package com.example.diary.ColorPicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.diary.R;

public class ColorPickerDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_colorpicker, container, false);

        Button paletteBtn = view.findViewById(R.id.palette_button);
        Button wheelBtn = view.findViewById(R.id.wheel_button);
        Button classicBtn = view.findViewById(R.id.classic_button);

        paletteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(0);
            }
        });

        wheelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(1);
            }
        });

        classicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(2);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        ColorWheelFragment wheelFragment = new ColorWheelFragment();
        transaction.replace(R.id.dialog_colorpicker, wheelFragment);
        transaction.commit();
    }

    private void onButtonPressed(int btn_id) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (btn_id) {
            case 0:
                ColorPaletteFragment paletteFragment = new ColorPaletteFragment();
                transaction.replace(R.id.dialog_colorpicker, paletteFragment);
                transaction.addToBackStack("paletteFragment");
                break;
            case 1:
                ColorWheelFragment wheelFragment = new ColorWheelFragment();
                transaction.replace(R.id.dialog_colorpicker, wheelFragment);
                transaction.addToBackStack("wheelFragment");
                break;
            case 2:
                break;
        }
        transaction.commit();
    }
}
