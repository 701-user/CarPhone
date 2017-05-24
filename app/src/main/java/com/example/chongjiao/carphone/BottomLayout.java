package com.example.chongjiao.carphone;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by chongjiao on 17-5-16.
 */

public class BottomLayout extends LinearLayout {
    private ImageView mImageView;
    private TextView mTextView;
    public BottomLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView(context);
    }
    public void setNormalIcon(int normalIcon){
        mImageView.setImageResource(normalIcon);
    }
    public void setIconText(String Text){
        mTextView.setText(Text);
    }
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_layout, null);
        mImageView = (ImageView) view.findViewById(R.id.image_view);
        mTextView = (TextView) view.findViewById(R.id.text_view);
        addView(view);
    }
    public void setSelectState() {
        mImageView.setSelected(true);
        mTextView.setSelected(true);
    }

    public void setUnSelectState() {
        mImageView.setSelected(false);
        mTextView.setSelected(false);
    }
}
