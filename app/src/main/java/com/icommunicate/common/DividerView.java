package com.icommunicate.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.icommunicate.R;


public class DividerView extends View {
    public DividerView(Context context) {
        super(context);
        this.setBackgroundColor(getResources().getColor(R.color.popup_transparent));
    }

    public DividerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(getResources().getColor(R.color.popup_transparent));
    }

    public DividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(getResources().getColor(R.color.popup_transparent));

    }
}
