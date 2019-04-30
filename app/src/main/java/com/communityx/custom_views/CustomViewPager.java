package com.communityx.custom_views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class CustomViewPager extends ViewPager {

    private Context context;

    public CustomViewPager(@NonNull Context context) {
        super(context);
        this.context = context;
        scroller();
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        scroller();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    private void scroller() {
        try {
            Class cls = ViewPager.class;
            Field scroller = cls.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new CustomScroller(context));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

     class CustomScroller extends Scroller{

         public CustomScroller(Context context) {
             super(context);
         }

         @Override
         public void startScroll(int startX, int startY, int dx, int dy) {
             super.startScroll(startX, startY, dx, dy,350);
         }
     }
}
