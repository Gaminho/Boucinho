package com.boucinho.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boucinho.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class CardEvent extends CardView {

    protected String mTitle = "";
    protected String mDetail = "";
    protected String mLocation = "";
    protected Date mDate;

    protected static final int DEFAULT_INNER_PADDING = 20;

    private static final SimpleDateFormat mSDF =
            new SimpleDateFormat("yyyy/MM/dd, E HH'h'mm", Locale.FRANCE);

    private LinearLayout mLLEventDetailContainer;
    private RelativeLayout mRLContainer;
    private TextView mTVTitle, mTVDetail, mTVDate;

    public CardEvent(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CardEvent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardEvent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context){

        // RelativeLayout = Global container
        mRLContainer = new RelativeLayout(context);
        addView(mRLContainer, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // LinearLayout = Event details container
        mLLEventDetailContainer = new LinearLayout(context);
        mLLEventDetailContainer.setId(View.generateViewId());
        mLLEventDetailContainer.setOrientation(LinearLayout.VERTICAL);
        mLLEventDetailContainer.setPadding(DEFAULT_INNER_PADDING, DEFAULT_INNER_PADDING,
                DEFAULT_INNER_PADDING, DEFAULT_INNER_PADDING);

        MaterialButton materialButton =
                new MaterialButton(context, null, R.attr.borderlessButtonStyle);
        materialButton.setText(getContext().getString(R.string.more));
        materialButton.setId(View.generateViewId());

        // Layout Params for Event details container
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.START_OF, materialButton.getId());

        // Layout Params for Action Button
        RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        rlp2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        mRLContainer.addView(materialButton, rlp2);
        mRLContainer.addView(mLLEventDetailContainer, rlp);

        // Init title TextView
        mTVTitle = new TextView(context);
        mTVTitle.setAllCaps(true);
        mTVTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mLLEventDetailContainer.addView(mTVTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Init details TextView
        mTVDetail = new TextView(context);
        mTVDetail.setMaxLines(1);
        mTVDetail.setSingleLine(true);
        mTVDetail.setEllipsize(TextUtils.TruncateAt.END);
        mLLEventDetailContainer.addView(mTVDetail, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Init date TextView
        mTVDate = new TextView(context);
        mLLEventDetailContainer.addView(mTVDate, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Preview mode
        if(isInEditMode()){
            mTitle = context.getString(R.string.event_title);
            mDetail = context.getString(R.string.event_details);
            mDate = new Date();
        }

        setTitle(mTitle);
        setDetail(mDetail);
        setDate(mDate);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        mTVTitle.setText(mTitle);
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
        mTVDetail.setText(mDetail);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        if(null != date){
            mDate = date;
        }

        String strDate;

        try {
            strDate = null != date ? mSDF.format(date) : "Unknown";
        } catch (Exception e){
            strDate = "Unknown";
        }

        mTVDate.setText(strDate);
    }

    public LinearLayout getLLEventDetailContainer() {
        return mLLEventDetailContainer;
    }

    public RelativeLayout getRLContainer() {
        return mRLContainer;
    }
}


