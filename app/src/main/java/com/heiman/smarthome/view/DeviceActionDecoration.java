package com.heiman.smarthome.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.ResCompat;

/**
 * @Author : 张泽晋
 * @Time : 2017/6/10 10:11
 * @Description :
 * @Modify record :
 */

public class DeviceActionDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    private int margin;

    public DeviceActionDecoration() {
        mDrawable = ResCompat.getDrawable(BaseApplication.getMyApplication(), R.drawable.scene_divider_recycler);
        margin = BaseApplication.getMyApplication().getResources().getDimensionPixelSize(R.dimen.item_device_action_margin);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 以下计算主要用来确定绘制的位置
            int left = child.getLeft() + params.leftMargin + margin;
            int right = child.getRight() + params.rightMargin;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
    }
}
