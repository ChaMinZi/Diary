package com.example.diary;

import android.content.Context;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class CanvasViewPagerAdapter extends PagerAdapter {
    private Context mContext = null;
    private ArrayList<View> pageList = new ArrayList<View>();

    public CanvasViewPagerAdapter() {

    }

    public CanvasViewPagerAdapter(Context context) {
        mContext = context;

        //notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        if (position >= pageList.size()) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.content_canvas, container, false);
            ((LinearLayout) view.findViewById(R.id.canvas_frame)).addView(new CanvasView(mContext));

            pageList.add(view);  //확인용
        }
        else {
            view = pageList.get(position);
        }
        container.addView(view);  //컨테이너에 추가

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
