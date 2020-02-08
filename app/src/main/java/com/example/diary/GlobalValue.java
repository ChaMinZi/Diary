package com.example.diary;

import android.util.Log;

import com.example.diary.Enum.TouchType;

public class GlobalValue {
    private static GlobalValue _instance;

    public float getmScaleFactor() {
        return mScaleFactor;
    }

    public void setmScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
    }

    public float getmMaxScaleFactor() {
        return mMaxScaleFactor;
    }
    public int getMaxCanvasWidth() {
        return maxCanvasWidth;
    }

    public void setMaxCanvasWidth(int maxCanvasWidth) {
        this.maxCanvasWidth = maxCanvasWidth;
    }

    public int getMaxCanvasHeight() {
        return maxCanvasHeight;
    }

    public void setMaxCanvasHeight(int maxCanvasHeight) {
        this.maxCanvasHeight = maxCanvasHeight;
    }

    public float getLeft() {
        return (maxCanvasWidth - (maxCanvasWidth * mScaleFactor) )/ 2;
    }
    public float getTop() {
        return (maxCanvasHeight - (maxCanvasHeight * mScaleFactor)) / 2;
    }
    public float getRight() {
        return (maxCanvasWidth - (maxCanvasWidth * mScaleFactor)) / 2 + (maxCanvasWidth * mScaleFactor);
    }
    public float getBom() {
        return (maxCanvasHeight - (maxCanvasHeight * mScaleFactor)) / 2 + (maxCanvasHeight * mScaleFactor);
    }

    private float mScaleFactor = 1.f, mMaxScaleFactor = 8f;
    private int maxCanvasWidth, maxCanvasHeight;

    private GlobalValue(){
        Log.e("Zoomer","initialize");
    }

    public static GlobalValue get_instance() {
        if (_instance == null) { _instance = new GlobalValue(); }
        return _instance;
    }

    private int mColor;
    public void setColor(int color) {this.mColor = color;}
    public int getColor() {return mColor;}

    private int opacity;
    public void setOpacity(int opacity) { this.opacity = opacity; }
    public int getOpacity() { return opacity; }

    private TouchType mMode;
    public TouchType getMode() {
        return mMode;
    }
    public void setMode(TouchType mode) {
        this.mMode = mode;
        if (mode == TouchType.PEN) {
            setBrushSize(getBrushSize());
        }
        else if (mode == TouchType.ERASER) {
            setEraseSize(getEraseSize());
        }
    }

    private int eraseSize = 12;
    public void setEraseSize(int eraseSize) { this.eraseSize = eraseSize; }
    public int getEraseSize() { return eraseSize;}

    private int brushSize = 12;
    public void setBrushSize(int size) { this.brushSize = size;}
    public int getBrushSize() {return brushSize;}
}
