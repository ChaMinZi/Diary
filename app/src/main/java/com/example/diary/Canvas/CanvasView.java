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
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.diary.Enum.TouchType;
import com.example.diary.GlobalValue;
import com.example.diary.TouchEvent.TouchCropEvent;
import com.example.diary.TouchEvent.TouchOneFingerEvent;
import com.example.diary.TouchEvent.TouchPenEvent;
import com.example.diary.TouchEvent.TouchScreenEvent;
import com.example.diary.TouchEvent.TouchTwoFingerEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap mBitmap;
    private Paint mPaint;

    private ArrayList<Path> pathList;
    private HashMap <Path, Paint> map;

    private TouchPenEvent touchPenEvent;
    private TouchOneFingerEvent touchOneFingerEvent;
    private TouchTwoFingerEvent touchTwoFingerEvent;
    private TouchCropEvent touchCropEvent;

    /**
     *  Porting Crop Draw
     */
    private Paint croppedPaint;
    private Paint fillPaint;
    private Path drawPath;

    private Bitmap croppedBitmap;
    private Canvas mCanvas;
    private Context mContext;

    private SurfaceHolder mHolder;
    private DrawThread mThread;

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
        mCanvas = new Canvas();
        mCanvas.drawColor(Color.BLACK);

        mBitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mPaint = initPaints();
        pathList = new ArrayList<Path>();
        map = new HashMap<Path, Paint>();

        touchPenEvent = new TouchPenEvent();
        touchCropEvent = new TouchCropEvent();
        touchOneFingerEvent = new TouchOneFingerEvent();
        touchTwoFingerEvent = new TouchTwoFingerEvent(context);


        mHolder = getHolder(); // holder 생성
        mHolder.addCallback(this); // holder에 현재 SurfaceView에 대한 콜백 인터페이스를 제공
    }

    public CanvasView(Context context) { // View를 코드에서 생성할 때 호출
        super(context, null);
        CanvasInit(context);
    }

    private TouchScreenEvent touchEventObject(MotionEvent event) {
//        Log.e("touchEventObject", "is touchFingerEvent" + event);
        if (event.getTouchMajor() > 0.0f) {
            switch (event.getPointerCount()) {
                case 1:
                    return touchOneFingerEvent;
                case 2:
                    return touchTwoFingerEvent;
            }
        }
        else {
//            Log.e("touchEventObject", "is touchPenEvent");
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
        }
        return null;
    }

    public boolean myTouchEvent(MotionEvent event) {
        TouchScreenEvent touchScreenEvent = touchEventObject(event);

        if (touchScreenEvent != null) {

            if (touchScreenEvent == touchOneFingerEvent) {
                drawPath = touchEventObject(event).onTouchEvent(this, event);
                if (drawPath != null) {
//                    invalidate();
                    return true;
                }
            }
            else if (touchScreenEvent == touchTwoFingerEvent){
                touchEventObject(event).onTouchEvent(this, event);  // TODO add crop & show crop bitmap by resizing
//                if (drawPath != null) {
                    setScaleX(GlobalValue.get_instance().getmScaleFactor());
                    setScaleY(GlobalValue.get_instance().getmScaleFactor());
//                    requestLayout();
//                    invalidate();
                    return false;
//                }
            }
            else if (GlobalValue.get_instance().getMode() == TouchType.CROP) {
                drawPath = touchEventObject(event).onTouchEvent(this, event);
                if (drawPath != null) {
                    //reDraw();
                    return true;
                }
            } else if (GlobalValue.get_instance().getMode() == TouchType.PEN) {
                drawPath = touchEventObject(event).onTouchEvent(this, event);
                if (drawPath != null) {
                    map.put(drawPath, mPaint);
                    //reDraw();
                    return true;
                }
            } else if (GlobalValue.get_instance().getMode() == TouchType.ERASER) {
                drawPath = touchEventObject(event).onTouchEvent(this, event);
                if (drawPath != null) {
                    map.put(drawPath, mPaint);
                    //reDraw();
//                    invalidate();
                    return true;
                }
            } else Log.e("myTouchEvent", "there is no tempPath");
            return false;
        } else Log.e("myTouchEvent", "there is no touchScreenEvent");
        return false;
    }

    public void surfaceCreated(SurfaceHolder holder){ // surface가 첫번째로 생성되자마자 바로 호출되는 메소드
        Log.e("asd", "surfaceCreated: ");

        mThread = new DrawThread(mHolder); // 스레드를 시작하는 시점
        mThread.start();
    }

    int count = 0;
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){ // surface에 포맷이나 사이즈 등 어떠한 변호가 생기면 바로 호출되는 메소드
        if (mThread != null){
            Log.e("asd", "surfaceChanged: ");
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder){ // surface가 종료되기 직전 바로 호출되는 메소드
        mThread.bExit = true;
        for (;;){
            try{
                mThread.join(); // Thread 종료 기다리기
                break;
            }
            catch (Exception e){;}
        }
    }

    protected void reDraw(Canvas mCanvas) {
        mPaint = initPaints();  //Erase Error
//        mCanvas.drawColor(Color.BLACK);

        if (GlobalValue.get_instance().getMode() == TouchType.CROP) {
            croppedBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas tCanvas = new Canvas(croppedBitmap);
            tCanvas.drawPath(drawPath, croppedPaint);
            mCanvas.drawBitmap(croppedBitmap, 0, 0, mPaint);
        } else {
            if (drawPath != null) {
                pathList.add(drawPath);
                mCanvas.drawPath(drawPath, map.get(drawPath));
            }
        }
    }

    class DrawThread extends Thread {
        boolean bExit;
        SurfaceHolder mHolder;

        DrawThread(SurfaceHolder Holder) {
            mHolder = Holder;
            bExit = false;
        }

        public void run() {
//            while (bExit == false){
////                mCanvas = mHolder.lockCanvas();  //캔버스를 가져와서 아무도 못쓰게 lock.
//
//                synchronized(mHolder){  //canvas 작업 하는 곳.
//
////                    reDraw();
//
//                }
////                mHolder.unlockCanvasAndPost(mCanvas);  lock 해제
//                try {Thread.sleep(10);} catch(Exception e){}
//            }
            while (bExit == false) {
                Canvas c = null;

                try {
                    c = mHolder.lockCanvas();
                    synchronized (mHolder) {
                        reDraw(c);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (c != null)
                        mHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
