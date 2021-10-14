package com.example.listviewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ZQListView extends ListView {
    private static final String TAG = "SilderListView";
    private  ZQview mFocusedItemView;

    float mX = 0;
    float mY = 0;
    private  int mPosition = -1;
    boolean isSlider =false;

    public ZQListView(Context context) {
        super(context);
    }

    public ZQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZQListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isSlider = false;
                mX = x;
                mY =y;
                int position = pointToPosition((int)x,(int)y);
                if(mPosition!= position){
                    mPosition = position;
                    if(mFocusedItemView != null){
                        mFocusedItemView.reset();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mPosition != -1){
                    if(Math.abs(mY-y)<30&&Math.abs(mX-x)>20){
                        int first = this.getFirstVisiblePosition();
                        int index = mPosition -first ;
                        mFocusedItemView = (ZQview)getChildAt(index);
                        mFocusedItemView.onTouchEvent(ev);
                        isSlider = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isSlider){
                    isSlider = false;
                    if(mFocusedItemView!=null){
                        mFocusedItemView.adjust(mX-x>0);
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
