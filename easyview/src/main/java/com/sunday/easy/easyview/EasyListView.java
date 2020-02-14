package com.sunday.easy.easyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EasyListView extends ListView {
    private int maxLine;
    private int minLine;
    public EasyListView(Context context) {
        super(context);
    }

    public EasyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyListView);
        maxLine = typedArray.getInt(R.styleable.EasyListView_maxLine,Integer.MAX_VALUE);
        minLine = typedArray.getInt(R.styleable.EasyListView_minLine,Integer.MIN_VALUE);
    }

    public EasyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        ListAdapter listAdapter = getAdapter();

        int count = 0;
        if(listAdapter != null){
            count = listAdapter.getCount();
        }

        int targetLine = count;

        if(count > maxLine){
            targetLine = maxLine;
        }else if(count < minLine){
            targetLine = minLine;
        }

        height = (int) (((float)height) * targetLine / count);
        setMeasuredDimension(width,height);

    }
}
