package com.example.diary;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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

    private float mScaleFactor = 1.f;
    public static Context mContext;

    public void setmScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
    }

    public CanvasView(Context context) { // View를 코드에서 생성할 때 호출
        this(context, null);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        /**
         * ANTI_ALIAS_FLAG
         * DITHER_FLAG
         */
    }

    public CanvasView(Context context, AttributeSet attrs) { // XML을 통해 View를 inflating 할 때 호출
        this(context, attrs, 0);

        touchPenEvent = new TouchPenEvent();
        touchFingerEvent = new TouchFingerEvent(context);

        mPath = new Path();
//        mPath.moveTo(0,0);
//        mPath.lineTo(800, 800);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
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

    public CanvasView(Context context, AttributeSet attrs, int ref) {
        super(context, attrs, ref);

        touchPenEvent = new TouchPenEvent();
        touchFingerEvent = new TouchFingerEvent(context);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        initPaints();
    }


    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    private TouchScreenEvent touchEventObject(MotionEvent event) {
        //Log.e("type : ",  ""+event.getTouchMajor());
        if (event.getTouchMajor() > 0.0)
            return touchFingerEvent;
        return touchPenEvent;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(mScaleFactor, mScaleFactor);

        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);
        canvas.save();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // distinguish pen and hand touch
        touchEventObject(event).onTouchEvent(event, mPath);
        mScaleFactor = touchFingerEvent.getmScaleFactor();
        invalidate();
        return true;
    }
}
