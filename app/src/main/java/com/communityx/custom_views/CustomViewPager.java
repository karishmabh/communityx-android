package com.communityx.custom_views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class CustomViewPager extends ViewPager {

    private float initialXValue;
    private SwipeDirection direction;
    private Context mContext;
    private int scrollDuration = 350;
    private SwipeDirectionListener mSwipeDirectionListener;

    public enum SwipeDirection {
        all, left, right, none
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.direction = SwipeDirection.all;
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(mSwipeDirectionListener != null) mSwipeDirectionListener.onPageChange(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        scroller();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    private boolean IsSwipeAllowed(MotionEvent event) {
        if (this.direction == SwipeDirection.all) return true;

        if (direction == SwipeDirection.none)
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection.right) {
                    if (mSwipeDirectionListener != null) mSwipeDirectionListener.onSwipe(SwipeDirection.right);
                    return false;
                } else if (diffX < 0 && direction == SwipeDirection.left) {
                    if (mSwipeDirectionListener != null) mSwipeDirectionListener.onSwipe(SwipeDirection.left);
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return true;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }

    public void setScrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
    }

    public void setmSwipeDirectionListener(SwipeDirectionListener swipeDirectionListener) {
        this.mSwipeDirectionListener = swipeDirectionListener;
    }

    private void scroller() {
        try {
            Class cls = ViewPager.class;
            Field scroller = cls.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new CustomScroller(mContext));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public interface SwipeDirectionListener {
        void onSwipe(SwipeDirection direction);
        void onPageChange(int position);
    }

    private class CustomScroller extends Scroller {

        public CustomScroller(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, scrollDuration);
        }
    }
}
