package com.example.diary;

import android.util.Log;

import com.example.diary.Enum.TouchType;

public class GlobalValue {
    private static GlobalValue _instance;
    private GlobalValue(){
        Log.e("GlobalValue", "constructor success");
    }
    public static GlobalValue get_instance() {
        if (_instance == null) { _instance = new GlobalValue(); }
        return _instance;
    }

    private float mScaleFactor = 1.f, mMaxScaleFactor = 8f;
    private int maxCanvasWidth, maxCanvasHeight;

    private int mColor;
    public void setColor(int color) {this.mColor = color;}
    public int getColor() {return mColor;}

    private int opacity;
    public void setOpacity(int opacity) { this.opacity = opacity; }
    public int getOpacity() { return opacity; }

    private TouchType mMode;
    public void setMode(TouchType mode) {
        this.mMode = mode;
        if (mode == TouchType.PEN) {
            setBrushSize(getBrushSize());
        }
        else if (mode == TouchType.ERASER) {
            setEraseSize(getEraseSize());
        }
    }
    public TouchType getMode() {
        return mMode;
    }

    private int eraseSize = 12;
    public void setEraseSize(int eraseSize) { this.eraseSize = eraseSize; }
    public int getEraseSize() { return eraseSize;}

    private int brushSize = 12;
    public void setBrushSize(int size) { this.brushSize = size;}
    public int getBrushSize() {return brushSize;}
}
