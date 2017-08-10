package com.example.cpu11341_local.bannerviewtest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by CPU11341-local on 8/10/2017.
 */

public class CustomViewPager extends ViewPager {

    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       float x1 = 0, x2, y1 = 0, y2, dx, dy;

        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE: {

                x2 = event.getX();
                y2 = event.getY();
                dx = x2 - x1;
                dy = y2 - y1;

                // Use dx and dy to determine the direction
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        Log.e("right...", "moving..");
                    } else {
                        Log.e("left...", "moving..");
                    }
                }

            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}