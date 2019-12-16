package com.example.diary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class CanvasView extends View {
    private static final float MINP = 0.25f;
    private static final float MAXP = 0.75f;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint;

    private Paint mPaint;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;

    private TouchPenEvent touchPenEvent;
    private TouchFingerEvent touchFingerEvent;
    private Path mPath;

    Scroller mScroller;

    private void CanvasInit(Context context) {
        touchPenEvent = new TouchPenEvent();
        touchFingerEvent = new TouchFingerEvent();

        mPath = new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(1000, 1000);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mBitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);  //고쳐줭~
        initPaints();
        invalidate();
    }

    private void initPaints() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //mPaint.setColor(0xFFFF0000);
        colorChanged(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
    }

    public CanvasView(Context context) { // View를 코드에서 생성할 때 호출
        this(context, null);
        CanvasInit(context);
    }

    public CanvasView(Context context, AttributeSet attrs) { // XML을 통해 View를 inflating 할 때 호출
        this(context, attrs, 0);
        CanvasInit(context);
    }

    public CanvasView(Context context, AttributeSet attrs, int ref) {
        super(context, attrs, ref);
        CanvasInit(context);
    }

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    private TouchScreenEvent touchEventObject(MotionEvent event) {
        //Log.e("type : ",  ""+event.getTouchMajor());
        if (event.getTouchMajor() > 0.0f)
            return touchFingerEvent;
        return touchPenEvent;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.scale(Zoomer.get_instance().getScaleFactor(), Zoomer.get_instance().getScaleFactor());
        canvas.drawPath(mPath, mPaint);
        canvas.save();
        mCanvas = canvas;
    }

    public boolean onTouchEvent(View view, MotionEvent event) {
        // distinguish pen and hand touch
        boolean retVal = touchEventObject(event).onTouchEvent(view, event, mPath);
        invalidate();
        return retVal;
    }
}
