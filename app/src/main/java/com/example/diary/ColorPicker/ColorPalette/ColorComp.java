package com.example.diary.ColorPicker.ColorPalette;

import androidx.annotation.Nullable;

public class ColorComp {
    private int color;
    private boolean check;

    public ColorComp(int color, boolean check) {
        this.color = color;
        this.check = check;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof ColorComp && ((ColorComp) obj).color == color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
