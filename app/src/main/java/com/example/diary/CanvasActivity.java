package com.example.diary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


public class CanvasActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_canvas, null);
        setContentView(v);
        RelativeLayout canvasFrame = (RelativeLayout)v.findViewById(R.id.canvas_frame);
        //canvasFrame.setPadding(30,30,30,30);
        CanvasView baseView = new CanvasView(this);
        Log.e("type : TouchEvent",  ""+baseView.isClickable());
        canvasFrame.addView(baseView);

        //mEmboss = new EmbossMaskFilter(new float[] {1,1,1,1}, 0.4f, 6,3.5f);
        //mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }
}
