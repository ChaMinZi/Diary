package com.example.diary.Canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View;

import com.example.diary.Enum.TouchType;
import com.example.diary.GlobalValue;
import com.example.diary.TouchEvent.TouchCropEvent;
import com.example.diary.TouchEvent.TouchFingerEvent;
import com.example.diary.TouchEvent.TouchPenEvent;
import com.example.diary.TouchEvent.TouchScreenEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CanvasView extends View {
    private Bitmap mBitmap;
    private Paint mPaint;

    private ArrayList<Path> pathList;
    private HashMap <Path, Paint> map;

    private TouchPenEvent touchPenEvent;
    private TouchFingerEvent touchFingerEvent;
    private TouchCropEvent touchCropEvent;

    /**
     *  Porting Crop Draw
     */
    private Paint croppedPaint;
    private Paint fillPaint;
    private Path cropPath;

    private Bitmap croppedBitmap;
    private Canvas tCanvas;
    private Context mContext;


    PorterDuffXfermode clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    private Paint initPaints() {
        Paint tPaint = new Paint();
        tPaint.setAntiAlias(true);
        tPaint.setDither(true);
        tPaint.setColor(GlobalValue.get_instance().getColor());
        tPaint.setAlpha(GlobalValue.get_instance().getOpacity());
        tPaint.setStyle(Paint.Style.STROKE);
        tPaint.setStrokeJoin(Paint.Join.ROUND);
        tPaint.setStrokeCap(Paint.Cap.ROUND);

        if (GlobalValue.get_instance().getMode() == TouchType.ERASER)
            tPaint.setStrokeWidth(GlobalValue.get_instance().getEraseSize());
        else
            tPaint.setStrokeWidth(GlobalValue.get_instance().getBrushSize());

        croppedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        croppedPaint.setStyle(Paint.Style.STROKE);
        croppedPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        croppedPaint.setStrokeWidth(5);
        croppedPaint.setColor(Color.BLUE);
        croppedPaint.setStrokeJoin(Paint.Join.ROUND);
        croppedPaint.setStrokeCap(Paint.Cap.ROUND);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setARGB(255,255,255,255);

        return tPaint;
    }

    private void CanvasInit(Context context) {
        mBitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mPaint = initPaints();
        pathList = new ArrayList<Path>();
        map = new HashMap<Path, Paint>();
        touchPenEvent = new TouchPenEvent();
        touchFingerEvent = new TouchFingerEvent();
        touchCropEvent = new TouchCropEvent();
//        invalidate();
    }

    public CanvasView(Context context) { // View를 코드에서 생성할 때 호출
        super(context, null);
        CanvasInit(context);
    }

    private TouchScreenEvent touchEventObject(MotionEvent event) {
        if (event.getTouchMajor() > 0.0f)
            return touchFingerEvent;

        switch (GlobalValue.get_instance().getMode()) {
            case PEN:
                mPaint.setXfermode(null);
                return touchPenEvent;
            case ERASER:
                mPaint.setXfermode(clear);
                return touchPenEvent;
            case CROP:
                mPaint.setXfermode(null);
                return touchCropEvent;
        }
        return touchPenEvent;
    }

    public boolean myTouchEvent(MotionEvent event) {
        if (GlobalValue.get_instance().getMode() == TouchType.CROP) {
            cropPath = touchEventObject(event).onTouchEvent(this, event);
            if (cropPath != null) {
                invalidate();
                return true;
            }
        }
        else {
            Path tempPath = touchEventObject(event).onTouchEvent(this, event);
            if(tempPath != null) {
                pathList.add(tempPath);
                map.put(tempPath, mPaint);
                invalidate();
                return true;
            }
        }
        return  false;
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

        if (GlobalValue.get_instance().getMode() == TouchType.CROP) {
            croppedBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas tCanvas = new Canvas(croppedBitmap);
            tCanvas.drawPath(cropPath, croppedPaint);
            canvas.drawBitmap(croppedBitmap, 0, 0, mPaint);
        }
        else {
            Canvas tCanvas = new Canvas(mBitmap);
            if (pathList.size() > 0) {
                Path tempPath = pathList.get(pathList.size()-1);
                tCanvas.drawPath(tempPath, map.get(tempPath));
            }
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }

}
