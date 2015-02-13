package com.mekilah.codepath.googleimagessearch.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by mekilah on 2/13/15.
 *
 * This allows a relative layout to capture swipe events and still let children listen to other touch-related events.
 * I needed a child ImageView to capture click events while the RelativeLayout captured swipes.
 *
 * By returning false but also forwarding the MotionEvent in onInterceptTouchEvent, we invisibly process the event to the onSwipeTouchListenerToCall
 * without taking it from children views who might accept it.
 *
 * This can cause two listeners to both handle the same event. I didn't try to deal with priority in these cases, because it'd be messy. Instead,
 * I'm reacting to non-overlapping events (in the usual case, at least): longclick and swipe.
 *
 * http://stackoverflow.com/questions/9181529/detect-fling-gesture-over-clickable-items
 */
public class SwipableRelativeLayout extends RelativeLayout{

    public SwipableRelativeLayout(Context context){
        super(context);
    }

    public SwipableRelativeLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public SwipableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public OnSwipeTouchListener onSwipeTouchListenerToCall;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        if(this.onSwipeTouchListenerToCall != null){
            onSwipeTouchListenerToCall.onTouch(null, ev);
        }
        return false;
    }
}
