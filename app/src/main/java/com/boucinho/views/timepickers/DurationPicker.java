package com.boucinho.views.timepickers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.boucinho.R;

public class DurationPicker extends LinearLayout {

    private EditText mETDuration;

    public DurationPicker(Context context) {
        super(context);
        init(context);
    }

    public DurationPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DurationPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(HORIZONTAL);

        LinearLayout labelContent = new LinearLayout(context);
        labelContent.setOrientation(HORIZONTAL);
        labelContent.setGravity(Gravity.CENTER_VERTICAL);
        addView(labelContent, new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));

        TextView tvLabel = new TextView(context);
        tvLabel.setText(context.getResources().getString(R.string.duration));
        tvLabel.setTextColor(context.getResources().getColor(R.color.colorAccent));
        tvLabel.setTypeface(tvLabel.getTypeface(), Typeface.BOLD);
        tvLabel.setTextSize(16);
        tvLabel.setPadding(0,0,16,0);
        labelContent.addView(tvLabel, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tvDetails = new TextView(context);
        tvDetails.setText(context.getResources().getString(R.string.in_minutes));
        tvDetails.setTextColor(context.getResources().getColor(R.color.text_color));
        tvDetails.setTextSize(12);
        labelContent.addView(tvDetails, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mETDuration = new EditText(context);
        mETDuration.setInputType(InputType.TYPE_CLASS_NUMBER);
        mETDuration.setHint("0");
        mETDuration.setGravity(Gravity.CENTER);
        addView(mETDuration, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
    }

    public int getDuration(){
        return TextUtils.isEmpty(mETDuration.getText()) ?
                0 : Integer.parseInt(mETDuration.getText().toString());
    }

    public void setDuration(int duration){
        mETDuration.setText(String.valueOf(duration));
    }
}
