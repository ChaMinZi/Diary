package com.example.diary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CanvasView extends View {
    private static final float MINP = 0.25f;
    private static final float MAXP = 0.75f;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint;

    class Drawing{
        private Paint mPaint;
        private Path  mPath;
        Drawing(Path mPath, Paint mPaint){
            this.mPath = mPath;
            this.mPaint = mPaint;
        }
    }
    ArrayList<Path> drawingList;
    HashMap <Path, Paint> map;

    private TouchPenEvent touchPenEvent;
    private TouchFingerEvent touchFingerEvent;

    Paint mPaint;

    PorterDuffXfermode clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    private void CanvasInit(Context context) {
        touchPenEvent = new TouchPenEvent();
        touchFingerEvent = new TouchFingerEvent();
        drawingList = new ArrayList<Path>();
        map = new HashMap<Path, Paint>();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mBitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        initPaints();
        Log.e("initPaints","");
        invalidate();
    }

    private void initPaints() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(GlobalValue.get_instance().getColor());
        mPaint.setAlpha(GlobalValue.get_instance().getOpacity());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        if (GlobalValue.get_instance().isErase())
            mPaint.setStrokeWidth(GlobalValue.get_instance().getEraseSize());
        else
            mPaint.setStrokeWidth(GlobalValue.get_instance().getBrushSize());
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

    public void settingChanged() {
        initPaints();
    }

    private TouchScreenEvent touchEventObject(MotionEvent event) {
        //Log.e("type : ",  ""+event.getTouchMajor());
        if (event.getTouchMajor() > 0.0f)
            return touchFingerEvent;

        if (GlobalValue.get_instance().isErase()) setToEraser();
        else setToPen();

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
        settingChanged();
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        for (Path drawing : drawingList) {
            canvas.drawPath(drawing, map.get(drawing));
        }
//        canvas.save();
        mCanvas = canvas;
    }

    public boolean myTouchEvent(MotionEvent event) {
        touchEventObject(event).onTouchEvent(this, event);
        Path tempPath = touchEventObject(event).getmPath();
        drawingList.add(tempPath);
        map.put(tempPath, mPaint);
        invalidate();
        return true;
    }

    public void setToPen() { mPaint.setXfermode(null); }
    public void setToEraser() { mPaint.setXfermode(clear);}

}
