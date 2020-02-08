package com.example.diary.Canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.diary.GlobalValue;
import com.example.diary.TouchEvent.TouchOneFingerEvent;
import com.example.diary.TouchEvent.TouchPenEvent;
import com.example.diary.TouchEvent.TouchScreenEvent;
import com.example.diary.TouchEvent.TouchTwoFingerEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CanvasView extends View {
    private Bitmap mBitmap;
    private Paint mPaint;

    private ArrayList<Path> pathList;
    private HashMap <Path, Paint> map;

    private TouchPenEvent touchPenEvent;
    private TouchOneFingerEvent touchOneFingerEvent;
    private TouchTwoFingerEvent touchTwoFingerEvent;

    PorterDuffXfermode clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    public void setToPen() { mPaint.setXfermode(null); }
    public void setToEraser() { mPaint.setXfermode(clear);}

    private Paint initPaints() {
        Paint tPaint = new Paint();
        tPaint.setAntiAlias(true);
        tPaint.setDither(true);
        tPaint.setColor(GlobalValue.get_instance().getColor());
        tPaint.setAlpha(GlobalValue.get_instance().getOpacity());
        tPaint.setStyle(Paint.Style.STROKE);
        tPaint.setStrokeJoin(Paint.Join.ROUND);
        tPaint.setStrokeCap(Paint.Cap.ROUND);

        if (GlobalValue.get_instance().isErase())
            tPaint.setStrokeWidth(GlobalValue.get_instance().getEraseSize());
        else
            tPaint.setStrokeWidth(GlobalValue.get_instance().getBrushSize());

        return tPaint;
    }

    private void CanvasInit(Context context) {
        mBitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mPaint = initPaints();
        pathList = new ArrayList<Path>();
        map = new HashMap<Path, Paint>();

        touchPenEvent = new TouchPenEvent();
        touchOneFingerEvent = new TouchOneFingerEvent();
        touchTwoFingerEvent = new TouchTwoFingerEvent();
    }

    public CanvasView(Context context) { // View를 코드에서 생성할 때 호출
        super(context, null);
        CanvasInit(context);
    }

    private TouchScreenEvent touchEventObject(MotionEvent event) {
        if (event.getTouchMajor() > 0.0f) {
//            Log.e("touchEventObject", "is touchFingerEvent");
            if (event.getPointerCount() == 1) return touchOneFingerEvent;
            if (event.getPointerCount() == 2) return touchTwoFingerEvent;
        }
        else {
//            Log.e("touchEventObject", "is touchPenEvent");
            return touchPenEvent;
        }

//        if (GlobalValue.get_instance().isErase()) setToEraser();
//        else setToPen();  //TODO Change current Erase code...
        return null;
    }

    public boolean myTouchEvent(MotionEvent event) {
        TouchScreenEvent touchScreenEvent = touchEventObject(event);

        if(touchScreenEvent != null){

            if(touchScreenEvent == touchOneFingerEvent || touchScreenEvent == touchTwoFingerEvent){

            }
            else {
                Path tempPath = touchScreenEvent.onTouchEvent(this, event);
                if(tempPath != null) {
                    pathList.add(tempPath);
                    map.put(tempPath, mPaint);
                    invalidate();
                    return true;
                } else Log.e("myTouchEvent", "there is no tempPath");

            }
            return false;
        } else Log.e("myTouchEvent", "there is no touchScreenEvent");
        return false;
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
        mPaint = initPaints();  //Erase Error

        canvas.drawColor(Color.BLACK);

        Canvas tCanvas = new Canvas(mBitmap);
        if (pathList.size() > 0) {
            Path tempPath = pathList.get(pathList.size()-1);
            tCanvas.drawPath(tempPath, map.get(tempPath));
        }

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }
}
