package com.example.diary;

import android.graphics.Rect;
import android.util.Log;

public class Zoomer {
    private static Zoomer _instance;
    private float ZoomFactor = 1.f;
    private Rect mClipBounds = new Rect();

    public static Zoomer get_instance() {
        if (_instance == null) {
            _instance = new Zoomer();
        }
        return _instance;
    }

    public float getZoomFactor() {
        return ZoomFactor;
    }

    public void setZoomFactor(float zoomFactor) {
        Log.e("Zoomer",""+this.toString());
        this.ZoomFactor = zoomFactor;
    }

    public Rect getmClipBounds() {
        return mClipBounds;
    }

    public void setmClipBounds(Rect mClipBounds) {
        this.mClipBounds = mClipBounds;
    }
}
