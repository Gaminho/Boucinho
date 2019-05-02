package com.boucinho.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boucinho.R;

import androidx.annotation.Nullable;

public class TextWithLabel extends LinearLayout {

    private TextView mTVFieldLabel;
    private TextView mTVValue;

    private String mFieldLabel;
    private String mValue;

    public TextWithLabel(Context context) {
        super(context);
        init(context);
    }

    public TextWithLabel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextWithLabel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(VERTICAL);

        mTVFieldLabel = new TextView(context);
        mTVFieldLabel.setTextColor(context.getResources().getColor(R.color.colorAccent));
        mTVFieldLabel.setTextSize(14);
        mTVFieldLabel.setTypeface(Typeface.DEFAULT_BOLD);

        mTVValue = new TextView(context);
        mTVValue.setTextColor(context.getResources().getColor(R.color.text_color));
        mTVValue.setTextSize(16);

        addView(mTVFieldLabel, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mTVValue, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (isInEditMode()){
            setContent("Label", "Value");
        }
    }

    public String getFieldLabel() {
        return mFieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        mFieldLabel = fieldLabel;
        mTVFieldLabel.setText(fieldLabel);
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
        mTVValue.setText(value);
    }

    public void setContent(String fieldLabel, String value){
        setFieldLabel(fieldLabel);
        setValue(value);
    }
}
