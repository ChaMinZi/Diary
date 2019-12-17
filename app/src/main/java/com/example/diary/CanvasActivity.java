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
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class CanvasActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_canvas, null);
        setContentView(v);

        LinearLayout canvasFrame = (LinearLayout)v.findViewById(R.id.canvas_frame);
        final CanvasView baseView = new CanvasView(this);

        ScrollView vertitalScrollView = (ScrollView)v.findViewById(R.id.canvas_vertical_scroll);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView)v.findViewById(R.id.canvas_horizental_scroll);

        Zoomer.setView(baseView);

        vertitalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return baseView.onTouchEvent(view, motionEvent);
            }
        });
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return baseView.onTouchEvent(view, motionEvent);
            }
        });

        Log.e("type : TouchEvent",  ""+baseView.isClickable());
        canvasFrame.addView(baseView);


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) baseView.getLayoutParams();
        for (int i=0; i<5; i++) {
            final CanvasView nextView = new CanvasView(this);
            nextView.setLayoutParams(params);
            canvasFrame.addView(nextView);
        }

        //mEmboss = new EmbossMaskFilter(new float[] {1,1,1,1}, 0.4f, 6,3.5f);
        //mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }
}
