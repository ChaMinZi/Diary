package com.example.diary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class CanvasActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        LinearLayout canvasFrame = (LinearLayout)findViewById(R.id.canvas_frame);
        final CanvasView baseView = new CanvasView(this);

        Zoomer.setView(baseView);

        Log.e("type : TouchEvent",  ""+baseView.isClickable());
        canvasFrame.addView(baseView);

        for (int i=0; i<5; i++) {
            addView(savedInstanceState, baseView, canvasFrame);
        }

        //mEmboss = new EmbossMaskFilter(new float[] {1,1,1,1}, 0.4f, 6,3.5f);
        //mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    private void addView(Bundle savedInstanceState, CanvasView baseView, LinearLayout canvasFrame) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) baseView.getLayoutParams();
        final CanvasView nextView = new CanvasView(this);
        nextView.setLayoutParams(params);

        canvasFrame.addView(nextView);
    }
}
