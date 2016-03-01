
package com.xuanwu.art.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author xxl
 */

@SuppressLint("NewApi")
public class BaikeTextView extends TextView {
    private String TAG = "BaikeTextView";

    public Context mContext = null;
    public Paint mTextPaint = null;
    public int mTextHeight = 1080;
    public int mBaikeTextHeight = 0;
    public int mTextWidth = 1920;
    public String mText = "";
    public float mLineSpacingExtra = 5;
    public float mTextScaleX = 1.0f;
    public int mOffset = -2;
    public float mTextSize = 0;
    public int mTextColor = 0xffbbbbbb;
    public int mFontHeight = 0;
    public int mPaddingLeft = 0;
    public int mPaddingRight = 0;

    public BaikeTextView(Context context, AttributeSet set) {
        super(context, set);
        this.mContext = context;
        Log.i(TAG, "BaikeTextView(Context context, AttributeSet set)");
        init();
        initData();
    }


    
    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextSize = this.getTextSize();
        mFontHeight = (int) mTextSize;
        mPaddingLeft = this.getPaddingLeft();
        mPaddingRight = this.getPaddingRight();
        mLineSpacingExtra = this.getLineSpacingExtra();
        mTextScaleX = this.getTextScaleX();
        mTextColor = this.getCurrentTextColor();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextScaleX(mTextScaleX);
        mTextPaint.setTextLocale(Locale.CHINESE);
    }

    private void initWidthAndHeight() {
        mTextWidth = this.getWidth();
        setmTextHeight(this.getHeight());
    }
    
    private void initData() {
        mText = this.getText().toString().trim();
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        mText = getTextString(mContext, mText);
    }
    
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            int size = getLineParList(mText).size();
//            result = getPaddingTop()
//                    + getPaddingBottom()+(int)(mLineSpacingExtra+mFontHeight)*getTextLineCount() + (int)mLineSpacingExtra;
            result = getPaddingTop()
                    + getPaddingBottom()+(int)(mLineSpacingExtra+mFontHeight)*getTextLineCount() + (int)mLineSpacingExtra;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    
    private int getTextLineCount() {
        int lineCount = 0;
        //getWidth要在onLayout调用之后才能获取正确数值
        //getMeasureWidth要在onMeasure之后才能获取正确数值
        //执行顺序为measure----->layout------>draw
        int viewWidth = this.getWidth();
        String[] strArrays = mText.split("\n");

        if (viewWidth > 0 ) {
//            initWidthAndHeight();
//            mTextPaint.setTextSize(mTextSize);
//            mTextPaint.setTextScaleX(mTextScaleX);
            for(String str : strArrays){
                int textWidth = BaikeConstant.getWidthofString(str, mTextPaint);

                if (textWidth%viewWidth == 0) {
                    lineCount = textWidth/viewWidth;
                } else {
                    lineCount = textWidth/viewWidth + 1;
                }
            }

        }
        return lineCount;
    }
    
    public void setBaikeText(String text) {
        mText = text;
        this.setText(text);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        initWidthAndHeight();
        ArrayList<LinePar> tempLineArray = getLineParList(mText);
        drawText(tempLineArray, mText, canvas);
    }

    private double getWordSpaceOffset(String text, int index) {
        double offset = 0;
        if (TextUtils.isEmpty(text)) {
            return offset;
        }
        char c = text.charAt(index);
        String str = String.valueOf(c);
        int width = BaikeConstant.getWidthofString(str, mTextPaint);
        offset = Math.ceil(width);
        return offset;
    }
    /*
     * Obtain the information of each row
     */
    public ArrayList<LinePar> getLineParList(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        int start = 0;
        int lineWidth = 0;
        int lineCount = 0;
        ArrayList<LinePar> linePars = new ArrayList<LinePar>();
        /** 循环取文本 */
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            double offset = getWordSpaceOffset(text, i);
            /** 如果是换行符，将这一行的信息存入列表中 */
            if (ch == '\n' && start != i) {
                lineCount++;
                addLinePar(start, i, lineCount, 0, linePars);
                if (i == (text.length() - 1)) {
                    break;
                } else {
                    start = i + 1;
                    lineWidth = 0;
                }
                continue;
            } else {
                lineWidth += offset;
                if (lineWidth >= mTextWidth - mPaddingLeft - mPaddingRight) {
                    i--;
                    lineCount++;
                    float tempWordSpaceOffset = (float) (mTextWidth - lineWidth
                            + offset - mPaddingLeft - mPaddingRight)
                            / (float) (i - start);
                    addLinePar(start, i, lineCount,
                            tempWordSpaceOffset, linePars);
                    if (i == (text.length() - 1)) {
                        break;
                    } else {
                        start = i + 1;
                        lineWidth = 0;
                    }
                    continue;
                } else {
                    if (i == (text.length() - 1)) {
                        lineCount++;
                        addLinePar(start, i, lineCount, 0,
                                linePars);
                        break;
                    }
                    continue;
                }
            }
        }
        Log.i(TAG, "getLineParList =====> tempLineArray.size() :"+linePars.size());
        return linePars;
    }

    public void addLinePar(int start, int end, int lineCount,
            float wordSpaceOffset, ArrayList<LinePar> lineList) {
        if (lineList != null) {
            LinePar linePar = new LinePar();
            linePar.setLineCount(lineCount);
            linePar.setStart(start);
            linePar.setEnd(end);
            linePar.setWordSpaceOffset(wordSpaceOffset);
            lineList.add(linePar);
        }
    }

    public void drawText(ArrayList<LinePar> tempLineArray, String mTextStr,
            Canvas canvas) {
        if (tempLineArray == null || canvas == null || mTextStr == null
                || mTextStr.equals("") == true) {
            return;
        }
        for (int lineNum = 0; lineNum < tempLineArray.size(); lineNum++) {
            LinePar linePar = tempLineArray.get(lineNum);

            int start = linePar.getStart();
            int end = linePar.getEnd();

            float width = linePar.getWordSpaceOffset();
            int lineCount = linePar.getLineCount();

            if (lineNum > 0 && lineNum == tempLineArray.size() - 1) {
                mBaikeTextHeight = (int) (lineCount * (mLineSpacingExtra + mTextSize));
            }
            if (start > end || end > mTextStr.length() - 1) {
                continue;
            }
            float lineWidth = 0;
            float baseLine = this.getPaddingTop() + this.getPaddingTop() + lineCount
                    * mTextSize  + (lineCount - 1) * mLineSpacingExtra;
            for (int strNum = start; strNum <= end; strNum++) {
                char ch = mTextStr.charAt(strNum);
                String str = String.valueOf(ch);
                if (str == null || str.equals("") == true) {
                    continue;
                }
                if (ch == '\n') {
                    str = "";
                }
                if (strNum > end) {
                    break;
                }
                if (strNum >= start && strNum <= end && lineCount >= 1) {
                    canvas.drawText(str, mPaddingLeft + lineWidth, (this.getPaddingTop() + lineCount
                    * mTextSize  + (lineCount - 1)
                    * mLineSpacingExtra), mTextPaint);

//                    canvas.drawText(str, mPaddingLeft + lineWidth, (this.getPaddingTop() + lineCount
//                    * mTextSize  + (lineCount - 1)
//                    * mLineSpacingExtra), mTextPaint);
                    lineWidth += BaikeConstant.getWidthofString(str, mTextPaint);
                    lineWidth = lineWidth + width;
                }
            }
        }
    }

    public int getBaikeTextHeight() {
        return mBaikeTextHeight;
    }

    public String getTextString(Context mContext, String mText) {
        if (mContext != null && mText != null && mText.equals("") == false) {
            return BaikeConstant.replaceTABToSpace(mText);
        }
        return "";
    }

    public void setmTextHeight(int mTextHeight) {
        this.mTextHeight = mTextHeight;
    }

    public int getmTextHeight() {
        return mTextHeight;
    }

    public class LinePar {
        private int mStart;
        private int mEnd;
        private int mLineCount;
        private float mWordSpaceOffset;

        public void setStart(int mStart) {
            this.mStart = mStart;
        }

        public void setEnd(int mEnd) {
            this.mEnd = mEnd;
        }

        public void setLineCount(int count) {
            this.mLineCount = count;
        }

        public void setWordSpaceOffset(float mWordSpaceOffset) {
            this.mWordSpaceOffset = mWordSpaceOffset;
        }

        public int getStart() {
            return mStart;
        }

        public int getEnd() {
            return mEnd;
        }

        public int getLineCount() {
            return mLineCount;
        }

        public float getWordSpaceOffset() {
            return mWordSpaceOffset;
        }

        @Override
        public String toString() {
            return "LinePar{" +
                    "mStart=" + mStart +
                    ", mEnd=" + mEnd +
                    ", mLineCount=" + mLineCount +
                    ", mWordSpaceOffset=" + mWordSpaceOffset +
                    '}';
        }
    }
}
