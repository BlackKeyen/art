package com.xuanwu.art.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Description:
 * User: <a href="wuzhifeng@wxchina.com">ZhiFeng.WU</a>
 * Date: 2015-10-26
 * Version 1.0.0
 */
public class ChnTextView extends TextView {
    private static final String TAG = "ChnTextView";
    private final int mDefaultTextSize = 12;
    private String mText;
    private float mTextSize;
    private int mLineHeight;            //行间距
    private float mLineSpaceingExtra;   //字间距
    private Paint mTextPaint;


    public ChnTextView(Context context) {
        super(context);
    }

    public ChnTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChnTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initData(){
        mText = this.getText().toString().trim();
        mTextSize = this.getTextSize();
        mLineHeight = this.getLineHeight();
        //mLineSpaceingExtra = this.getLineSpacingExtra();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
    }

    private void caculateLines(){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "draw finished.....");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST){
            Log.i(TAG, "width AT_MOST.....");
        }
        if(widthSpecMode == MeasureSpec.EXACTLY){
            Log.i(TAG, "width EXACTLY.....");
        }
        if(widthSpecMode == MeasureSpec.UNSPECIFIED){
            Log.i(TAG, "width UNSPECIFIED.....");
        }
        if (heightSpecMode == MeasureSpec.AT_MOST){
            Log.i(TAG, "height AT_MOST.....");
        }
        if(heightSpecMode == MeasureSpec.EXACTLY){
            Log.i(TAG, "height EXACTLY.....");
        }
        if(heightSpecMode == MeasureSpec.UNSPECIFIED){
            Log.i(TAG, "height UNSPECIFIED.....");
        }
//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
//            Log.i(TAG, "all AT_MOST");
////           int width = Math.min();
////            int height = Math.min()
//        }else if (widthSpecMode == MeasureSpec.AT_MOST){
//            Log.i(TAG, "width  AT_MOST");
//        }else if (heightSpecMode == MeasureSpec.AT_MOST){
//            Log.i(TAG, "height AT_MOST");
//        }
//
//        Log.i(TAG, "onMeasure finished");
    }
}
