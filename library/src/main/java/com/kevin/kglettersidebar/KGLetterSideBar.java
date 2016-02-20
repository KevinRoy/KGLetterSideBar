package com.kevin.kglettersidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.kevin.kglettersidebar.util.LanguageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kevin on 16/2/5.
 */
public class KGLetterSideBar extends View {
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    public static String[] letter = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();

    private TextView mTextDialog;

    private int mNormalColor;
    private int mPressedColor;
    private int mBackground;

    private AttributeSet mAttrs;
    private Context mContext;

    public KGLetterSideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAttrs = attrs;
        mContext = context;
        init();
    }

    public KGLetterSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttrs = attrs;
        mContext = context;
        init();
    }

    public KGLetterSideBar(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * 设置显示在中间的view(目前是TextView)
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    /**
     * 设置字母的normal颜色
     *
     * @param resColor
     */
    public void setLetterNormalColor(int resColor) {
        this.mNormalColor = resColor;
    }

    /**
     * 设置字母的按下效果颜色
     *
     * @param resColor
     */
    public void setLetterPressedColor(int resColor) {
        this.mPressedColor = resColor;
    }

    private void init() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.LetterSideBarStyle);

        mNormalColor = typedArray.getColor(R.styleable.LetterSideBarStyle_textNormalColor, getResources().getColor(R.color.default_normal_color));
        mPressedColor = typedArray.getColor(R.styleable.LetterSideBarStyle_textPressColor, getResources().getColor(R.color.default_press_color));
        mBackground = typedArray.getResourceId(R.styleable.LetterSideBarStyle_bg, R.drawable.default_bg);

        setBackgroundResource(mBackground);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / letter.length;

        for (int i = 0; i < letter.length; i++) {
            paint.setColor(mNormalColor);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(getResources().getDisplayMetrics().density * 14);
            if (i == choose) {
                paint.setColor(mPressedColor);
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(letter[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(letter[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letter.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < letter.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letter[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(letter[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 滑动监听接口
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }
}
