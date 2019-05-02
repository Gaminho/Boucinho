package com.boucinho.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boucinho.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class CardEvent extends CardView {

    private String mTitle = "";
    private String mDetail = "";
    private Date mDate;

    private static final int DEFAULT_INNER_PADDING = 20;

    private static final SimpleDateFormat mSDF =
            new SimpleDateFormat("EEEE, MMM dd yyyy", Locale.FRANCE);

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

    private void init(Context context){

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

        // Floating Action Button = Handle user actions
        FloatingActionButton fab = new FloatingActionButton(context);
        fab.setId(View.generateViewId());
        fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        MaterialButton materialButton =
                new MaterialButton(context, null, R.attr.borderlessButtonStyle);
        materialButton.setText("More");

        // Layout Params for Event details container
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.START_OF, materialButton.getId());

        // Layout Params for Action Button
        RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        rlp2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        mRLContainer.addView(mLLEventDetailContainer, rlp);
        mRLContainer.addView(materialButton, rlp2);

        // Init title TextView
        mTVTitle = new TextView(context);
        mTVTitle.setAllCaps(true);
        mTVTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mLLEventDetailContainer.addView(mTVTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Init details TextView
        mTVDetail = new TextView(context);
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
}
