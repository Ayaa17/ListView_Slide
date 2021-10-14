package com.example.listviewslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
/**
 * Created by MooreLi on 2016/8/8.
 */
public class SlideListView extends ListView {
    private String TAG = getClass().getSimpleName();
    private int mScreenWidth;
    private int mDownX;
    private int mDownY;
    private int mMenuWidth;
    private boolean isMenuShow;
    private boolean isMoving;
    private int mOperatePosition = -1;
    private ViewGroup mPointChild;
    private LinearLayout.LayoutParams mLayoutParams;
    public SlideListView(Context context) {
        super(context);
        getScreenWidth(context);
    }
    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenWidth(context);
    }
    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getScreenWidth(context);
    }
    private void getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                performActionMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                performActionUp();
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void performActionDown(MotionEvent ev) {
        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
//如果點選的不是同一個item，則關掉正在顯示的選單
        int position = pointToPosition(mDownX, mDownY);
        if (isMenuShow && position != mOperatePosition) {
            turnToNormal();
        }
        mOperatePosition = position;
        mPointChild = (ViewGroup) getChildAt(position - getFirstVisiblePosition());
        if (mPointChild != null) {
            mMenuWidth = mPointChild.getChildAt(1).getLayoutParams().width;
            mLayoutParams = (LinearLayout.LayoutParams) mPointChild.getChildAt(0).getLayoutParams();
            mLayoutParams.width = mScreenWidth;
            setChildLayoutParams();
        }
    }
    private boolean performActionMove(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
//    if (isMoving) {
//      if (Math.abs(nowY - mDownY) > 0) {
//        Log.e(TAG, "kkkkkkk");
//        onInterceptTouchEvent(ev);
//      }
//    }
        if (Math.abs(nowX - mDownX) > 0) {
//左滑 顯示選單
            if (nowX < mDownX) {
                if (isMenuShow) {
                    mLayoutParams.leftMargin = -mMenuWidth;
                } else {
//計算顯示的寬度
                    int scroll = (nowX - mDownX);
                    if (-scroll >= mMenuWidth) {
                        scroll = -mMenuWidth;
                    }
                    mLayoutParams.leftMargin = scroll;
                }
            }
//右滑 如果選單顯示狀態，則關閉選單
            if (isMenuShow && nowX > mDownX) {
                int scroll = nowX - mDownX;
                if (scroll >= mMenuWidth) {
                    scroll = mMenuWidth;
                }
                mLayoutParams.leftMargin = scroll - mMenuWidth;
            }
            setChildLayoutParams();
            isMoving = true;
            return true;
        }
        return super.onTouchEvent(ev);
    }
    private void performActionUp() {
//超過一半時，顯示選單，否則隱藏
        if (-mLayoutParams.leftMargin >= mMenuWidth / 2) {
            mLayoutParams.leftMargin = -mMenuWidth;
            setChildLayoutParams();
            isMenuShow = true;
        } else {
            turnToNormal();
        }
        isMoving = false;
    }
    private void setChildLayoutParams(){
        if(mPointChild != null){
            mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
        }
    }
    /**
     * 正常顯示
     */
    public void turnToNormal() {
        mLayoutParams.leftMargin = 0;
        mOperatePosition = -1;
        setChildLayoutParams();
        isMenuShow = false;
    }
}