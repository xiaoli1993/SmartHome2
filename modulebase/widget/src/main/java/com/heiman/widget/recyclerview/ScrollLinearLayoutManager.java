package com.heiman.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * @Author : 肖力
 * @Time :  2017/6/16 19:19
 * @Description :
 * @Modify record :
 */

public class ScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public ScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
