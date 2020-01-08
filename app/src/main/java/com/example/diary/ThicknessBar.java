package com.example.diary;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.diary.ColorPicker.ColorWheelView;

public class ThicknessBar extends View {

    private static final String STATE_PARENT = "parent";
    private static final String STATE_COLOR = "color";
    private static final String STATE_OPACITY = "opacity";
    private static final String STATE_ORIENTATION = "orientation";
    private static final String STATE_THICKNESS = "thickness";

    private static final boolean ORIENTATION_HORIZONTAL = true;
    private static final boolean ORIENTATION_VERTICAL = false;

    private static final boolean ORIENTATION_DEFAULT = ORIENTATION_HORIZONTAL;

    private int mBarThickness;

    private int mBarLength;
    private int mPreferredBarLength;

    private int mBarPointerRadius;

    private int mBarPointerHaloRadius;

    private int mBarPointerPosition;

    private Paint mBarPaint;

    private Paint mBarPointerPaint;

    private Paint mBarPointerHaloPaint;
    private Paint mBarPointerBubblePaint;

    private RectF mBarRect = new RectF();

    private Shader shader;

    private boolean mIsMovingPointer;

    private int mSize;
    private int mColor;

    private float[] mHSVColor = new float[3];

    private float mPosToOpacFactor;

    private float mOpacToPosFactor;

    private int oldChangedListenerThickness;

    private OnThicknessChangedListener onThicknessChangedListener;

    public interface OnThicknessChangedListener {
        public void onThicknessChanged(int size);
    }

    public void setOnThicknessChangedListener(OnThicknessChangedListener listener) {
        this.onThicknessChangedListener = listener;
    }

    public OnThicknessChangedListener getOnThicknessChangedListener() {
        return this.onThicknessChangedListener;
    }

    private boolean mOrientation;

    public ThicknessBar(Context context) {
        super(context);
        init(null, 0);
    }

    public ThicknessBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ThicknessBar(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.ColorBars, defStyle, 0);
        final Resources b = getContext().getResources();

        mBarThickness = a.getDimensionPixelSize(
                R.styleable.ColorBars_bar_thickness,
                b.getDimensionPixelSize(R.dimen.bar_thickness));
        mBarLength = a.getDimensionPixelSize(R.styleable.ColorBars_bar_length,
                b.getDimensionPixelSize(R.dimen.bar_length));
        mPreferredBarLength = mBarLength;
        mBarPointerRadius = a.getDimensionPixelSize(
                R.styleable.ColorBars_bar_pointer_radius,
                b.getDimensionPixelSize(R.dimen.bar_pointer_radius));
        mBarPointerHaloRadius = a.getDimensionPixelSize(
                R.styleable.ColorBars_bar_pointer_halo_radius,
                b.getDimensionPixelSize(R.dimen.bar_pointer_halo_radius));
        mOrientation = a.getBoolean(
                R.styleable.ColorBars_bar_orientation_horizontal, ORIENTATION_DEFAULT);

        a.recycle();

        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setShader(shader);

        mBarPointerPosition = mBarLength + mBarPointerHaloRadius;

        mBarPointerHaloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPointerHaloPaint.setColor(Color.BLACK);
        mBarPointerHaloPaint.setAlpha(0x50);

        mBarPointerBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPointerBubblePaint.setColor(Color.BLACK);
        mBarPointerBubblePaint.setAlpha(0x50);

        mBarPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPointerPaint.setColor(0xff0d113f);

        mPosToOpacFactor = 0xFF / ((float) mBarLength);
        mOpacToPosFactor = ((float) mBarLength) / 0xFF;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//크기 설정
        final int intrinsicSize = mPreferredBarLength
                + (mBarPointerHaloRadius * 2);

        // Variable orientation
        int measureSpec;
        if (mOrientation == ORIENTATION_HORIZONTAL) {//ORIENTATION_HORIZONTAL=true
            measureSpec = widthMeasureSpec;
        }
        else {
            measureSpec = heightMeasureSpec;
        }
        int lengthMode = MeasureSpec.getMode(measureSpec);
        int lengthSize = MeasureSpec.getSize(measureSpec);

        int length;
        if (lengthMode == MeasureSpec.EXACTLY) {//match_parent
            length = lengthSize;
        }
        else if (lengthMode == MeasureSpec.AT_MOST) {//wrap_content
            length = Math.min(intrinsicSize, lengthSize);
        }
        else {//MODE가 셋팅되지 않은 크기가 넘어올 때
            length = intrinsicSize;
        }

        int barPointerHaloRadiusx2 = mBarPointerHaloRadius * 2;
        mBarLength = length - barPointerHaloRadiusx2;
        if(mOrientation == ORIENTATION_VERTICAL) {
            setMeasuredDimension(barPointerHaloRadiusx2,
                    (mBarLength + barPointerHaloRadiusx2));
        }
        else {
            setMeasuredDimension((mBarLength + barPointerHaloRadiusx2),
                    barPointerHaloRadiusx2);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//onMeasure 호출
        super.onSizeChanged(w, h, oldw, oldh);

        // Fill the rectangle instance based on orientation
        int x1, y1;
        if (mOrientation == ORIENTATION_HORIZONTAL) {
            x1 = (mBarLength + mBarPointerHaloRadius);
            y1 = mBarThickness;
            mBarLength = w - (mBarPointerHaloRadius * 2);
            mBarRect.set(mBarPointerHaloRadius,
                    (mBarPointerHaloRadius - (mBarThickness / 2)),
                    (mBarLength + (mBarPointerHaloRadius)),
                    (mBarPointerHaloRadius + (mBarThickness / 2)));
        }
        else {
            x1 = mBarThickness;
            y1 = (mBarLength + mBarPointerHaloRadius);
            mBarLength = h - (mBarPointerHaloRadius * 2);
            mBarRect.set((mBarPointerHaloRadius - (mBarThickness / 2)),
                    mBarPointerHaloRadius,
                    (mBarPointerHaloRadius + (mBarThickness / 2)),
                    (mBarLength + (mBarPointerHaloRadius)));
        }

        // Update variables that depend of mBarLength.
        shader = new LinearGradient(mBarPointerHaloRadius, 0,
                x1, y1, new int[] {
                0xdd909090, 0xdd909090 }, null,
                Shader.TileMode.CLAMP);

        mBarPaint.setShader(shader);
        mPosToOpacFactor = 0xFF / ((float) mBarLength);
        mOpacToPosFactor = ((float) mBarLength) / 0xFF;

        if (!isInEditMode()){
            mBarPointerPosition = Math.round((mOpacToPosFactor * Color.alpha(mColor))
                    + mBarPointerHaloRadius);
        } else {
            mBarPointerPosition = mBarLength + mBarPointerHaloRadius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the bar.
        canvas.drawRect(mBarRect, mBarPaint);

        // Calculate the center of the pointer.
        int cX, cY;
        if (mOrientation == ORIENTATION_HORIZONTAL) {
            cX = mBarPointerPosition;
            cY = mBarPointerHaloRadius;
        }
        else {
            cX = mBarPointerHaloRadius;
            cY = mBarPointerPosition;
        }

        // Draw the pointer halo.
        canvas.drawCircle(cX, cY, mBarPointerHaloRadius, mBarPointerHaloPaint);
        canvas.drawCircle(cX, cY-50, mBarPointerHaloRadius, mBarPointerBubblePaint);

        // Draw the pointer.
        canvas.drawCircle(cX, cY, mBarPointerRadius, mBarPointerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);

        // Convert coordinates to our internal coordinate system
        float dimen;
        if (mOrientation == ORIENTATION_HORIZONTAL) {
            dimen = event.getX();
        }
        else {
            dimen = event.getY();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsMovingPointer = true;
                // Check whether the user pressed on (or near) the pointer
                if (dimen >= (mBarPointerHaloRadius)
                        && dimen <= (mBarPointerHaloRadius + mBarLength)) {
                    mBarPointerPosition = Math.round(dimen);
                    calculateColor(Math.round(dimen));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsMovingPointer) {
                    // Move the the pointer on the bar.
                    if (dimen >= mBarPointerHaloRadius
                            && dimen <= (mBarPointerHaloRadius + mBarLength)) {
                        mBarPointerPosition = Math.round(dimen);
                        calculateColor(Math.round(dimen));
                        invalidate();
                    } else if (dimen < mBarPointerHaloRadius) {
                        mBarPointerPosition = mBarPointerHaloRadius;
                        invalidate();
                    } else if (dimen > (mBarPointerHaloRadius + mBarLength)) {
                        mBarPointerPosition = mBarPointerHaloRadius + mBarLength;
                        invalidate();
                    }
                }
                if(onThicknessChangedListener != null && oldChangedListenerThickness != getSize()){
                    onThicknessChangedListener.onThicknessChanged(getSize());
                    oldChangedListenerThickness = getSize();
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsMovingPointer = false;
                break;
        }
        calculateThickenss();
        if (GlobalValue.get_instance().isErase()) {
            GlobalValue.get_instance().setEraseSize(getSize());
        }
        else {
            GlobalValue.get_instance().setBrushSize(getSize());
        }
        return true;
    }

    public void setSize(int size) {
        mBarPointerPosition = Math.round(mOpacToPosFactor + size)
                + mBarPointerHaloRadius;
        invalidate();
    }

    private void calculateColor(int coord) {
        coord = coord - mBarPointerHaloRadius;
        if (coord < 0) {
            coord = 0;
        } else if (coord > mBarLength) {
            coord = mBarLength;
        }

        mColor = Color.HSVToColor(
                Math.round(mPosToOpacFactor * coord),
                mHSVColor);
        if (Color.alpha(mColor) > 250) {
            mColor = Color.HSVToColor(mHSVColor);
        } else if (Color.alpha(mColor) < 5) {
            mColor = Color.TRANSPARENT;
        }
    }

    private void calculateThickenss() {
        int thickness = Math
                .round((mPosToOpacFactor * (mBarPointerPosition - mBarPointerHaloRadius)));

        thickness /= 4;
        if (thickness < 1) {
            mSize = 1;
        } else if (thickness > 60) {
            mSize = 60;
        }
        else
            mSize = thickness;
    }

    public int getSize() {
        return mSize;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        state.putParcelable(STATE_PARENT, superState);
        state.putInt(STATE_THICKNESS, getSize());

        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;

        Parcelable superState = savedState.getParcelable(STATE_PARENT);
        super.onRestoreInstanceState(superState);

        setSize(savedState.getInt(STATE_THICKNESS));
    }
}
