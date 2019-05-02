package com.boucinho.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.boucinho.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CardEventWithPicture extends CardEvent {

    private static final int DEFAULT_IMAGE_DIMENSION = 150;

    private ImageView mIVPicture;

    public CardEventWithPicture(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CardEventWithPicture(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardEventWithPicture(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void init(Context context) {
        super.init(context);

        mIVPicture = new ImageView(context);
        mIVPicture.setId(View.generateViewId());
        mIVPicture.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
        RelativeLayout.LayoutParams rlp =
                new RelativeLayout.LayoutParams(DEFAULT_IMAGE_DIMENSION, DEFAULT_IMAGE_DIMENSION);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rlp.setMargins(DEFAULT_INNER_PADDING, 0, DEFAULT_INNER_PADDING, 0);
        getRLContainer().addView(mIVPicture, rlp);

        LinearLayout llEventDetails = getLLEventDetailContainer();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)
                llEventDetails.getLayoutParams();
        lp.removeRule(RelativeLayout.ALIGN_PARENT_START);
        lp.addRule(RelativeLayout.END_OF, mIVPicture.getId());
        llEventDetails.setLayoutParams(lp);
        llEventDetails.invalidate();

        // Preview mode
        if(isInEditMode()){
            setImageDrawable(R.mipmap.ic_launcher_round);
        }
    }

    public void setImageDrawable(Drawable drawable){
        mIVPicture.setImageDrawable(drawable);
    }

    public void setImageDrawable(int drawableId){
        setImageDrawable(getContext().getResources().getDrawable(drawableId));
    }
}
