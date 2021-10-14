package com.example.listviewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class ZQview extends LinearLayout {
    private static final String TAG = "SlideView";
    private static final int TAN = 2;
    private int mHolderWidth = 65;
    private float  mLastX = 0;
    private float mLastY = 0;
    private Context mContext;
    private LinearLayout mViewContent;
    private Scroller mScroller;

    public ZQview(Context context) {
        super(context);
        initView();
    }

    public ZQview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ZQview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView(){
        setOrientation(LinearLayout.HORIZONTAL);
        mContext = getContext();
        mScroller = new Scroller(mContext);
        View.inflate(mContext,R.layout.delete_view,this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);
        Log.d(TAG, "initView: "+mViewContent);
        mHolderWidth = Math.round((TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,mHolderWidth,getResources().getDisplayMetrics()
        )));
    }

    public void setContentView(View view){
        mViewContent.addView(view);
    }

    public void shrink(){
        int offset = getScrollX();
        if(offset ==0){
            return;
        }
        scrollTo(0,0);
    }

    public void reset(){
        int offset = getScrollX();
        if(offset==0){
            return;
        }
        smoothScrollTo(0,0);
    }

    public void adjust(boolean left){
        int offset = getScrollX();
        if(offset ==0){
            return;
        }
        if(offset <20){
            this.smoothScrollTo(0,0);
        }else if(offset<mHolderWidth-20){
            if(left){
                this.smoothScrollTo(mHolderWidth,0);
            }else {
                this.smoothScrollTo(0,0);
            }
        }else {
            this.smoothScrollTo(mHolderWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float x  = event.getX();
                float y  = event.getY();
                float deltaX = x-mLastX;
                float deltaY = y-mLastY;
                mLastX = x;
                mLastY = y;
                if(Math.abs(deltaX)<Math.abs(deltaY)*TAN){
                    break;
                }
                if(deltaX!= 0){
                    float newScrollx = getScaleX() - deltaX;
                    if(newScrollx<0){
                        newScrollx =0;
                    }else if (newScrollx>mHolderWidth){
                        newScrollx = mHolderWidth;
                    }
                    this.scrollTo((int)newScrollx, 0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int destX,int destY){
        int scrollx = getScrollX();
        int delta = destX-scrollx;
        mScroller.startScroll(scrollx,0 ,delta ,0,Math.abs(delta)*3 );
        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
