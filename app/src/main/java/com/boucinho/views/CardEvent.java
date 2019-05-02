package com.boucinho.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class CardEvent extends MaterialCardView {

    protected String mTitle = "";
    protected String mDetail = "";
    protected String mLocation = "";
    protected String mDate = "";
    protected Event mEvent;

    protected static final int DEFAULT_INNER_PADDING = 20;

    private ClickOnEventListener mClickListener;
    private LinearLayout mLLEventDetailContainer;
    private RelativeLayout mRLContainer;
    private TextView mTVTitle, mTVDetail, mTVDate;

    public CardEvent(Context context) {
        super(context);
        init(context);
    }

    public CardEvent(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CardEvent);
        mTitle = attributes.getString(R.styleable.CardEvent_eventTitle);
        mDetail = attributes.getString(R.styleable.CardEvent_eventDetails);
        mLocation = attributes.getString(R.styleable.CardEvent_eventLocation);
        mDate = attributes.getString(R.styleable.CardEvent_eventDate);
        init(context);
        attributes.recycle();
    }

    public CardEvent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DEFAULT_INNER_PADDING,DEFAULT_INNER_PADDING,
                DEFAULT_INNER_PADDING,DEFAULT_INNER_PADDING);
        setLayoutParams(lp);

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

        MaterialButton MButton = new MaterialButton(context, null, R.attr.borderlessButtonStyle);
        MButton.setId(View.generateViewId());
        MButton.setText(getContext().getString(R.string.more));
        MButton.setOnClickListener(view -> {
            if(mClickListener != null){
                mClickListener.clickOnEvent(getEvent());
            }
        });

        // Layout Params for Event details container
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.START_OF, MButton.getId());

        // Layout Params for Action Button
        RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        rlp2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        mRLContainer.addView(MButton, rlp2);
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
            mDate = getContext().getString(R.string.event_date);
            mLocation = getContext().getString(R.string.event_location);
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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
        mTVDate.setText(mDate);
    }

    public void setEvent(Event event){
        mEvent = event;
        setDate(event.getFriendlyDate());
        setDetail(event.getDetails());
        setTitle(event.getTitle());
    }

    public Event getEvent() {
        return mEvent;
    }

    public LinearLayout getLLEventDetailContainer() {
        return mLLEventDetailContainer;
    }

    public RelativeLayout getRLContainer() {
        return mRLContainer;
    }

    public ClickOnEventListener getClickListener() {
        return mClickListener;
    }

    public void setClickOnEventListener(ClickOnEventListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickOnEventListener {
        void clickOnEvent(Event event);
    }

}
